# 第二十八版：重放审批职责分离

## 本版目标

v25 引入了重放审批门禁，v27 又把每次审批动作沉淀成历史流水。到这里，系统已经知道：

```text
谁申请了重放
谁审批了重放
审批是通过还是拒绝
审批动作发生在什么时候
```

v28 补一条非常常见的生产内控规则：

```text
申请人不能审批自己的重放申请
```

原因很简单：失败事件重放会重新投递业务事件，可能影响下游状态。申请和审批如果都是同一个人，就失去了“第二双眼睛”的意义。

## 改动文件

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageService.java
src/main/resources/static/failed-events.js
src/test/java/com/codexdemo/orderplatform/FailedEventSearchIntegrationTests.java
src/test/java/com/codexdemo/orderplatform/FailedEventManagementPageTests.java
README.md
代码讲解记录/README.md
a/28/解释/说明.md
```

## 一、后端强制拦截

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageService.java`

审批入口仍然是：

```java
@Transactional
public FailedEventMessageResponse reviewReplayApproval(
        Long id,
        ReviewFailedEventReplayApprovalRequest request,
        String operatorId,
        String operatorRole
) {
```

v28 在确认当前审批状态是 `PENDING` 之后，加了一道职责分离检查：

```java
if (failedMessage.getReplayApprovalStatus() != FailedEventReplayApprovalStatus.PENDING) {
    throw new ResponseStatusException(HttpStatus.CONFLICT, "replay approval is not pending");
}
ensureReplayApprovalReviewerIsDifferent(failedMessage, normalizedOperatorId);
```

为什么放在这里？

```text
先检查 PENDING
 -> 只有待审批状态才有“谁来审批”的问题

再检查申请人和审批人
 -> 如果同一个人，直接 409 Conflict

最后才 approve / reject
 -> 不会污染当前状态，也不会写审批历史
```

具体规则封装成小方法：

```java
private void ensureReplayApprovalReviewerIsDifferent(FailedEventMessage failedMessage, String reviewerId) {
    String requesterId = failedMessage.getReplayApprovalRequestedBy();
    if (requesterId != null && requesterId.equals(reviewerId)) {
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "replay approval requester cannot review own request"
        );
    }
}
```

这里比较的是规范化后的 `operatorId`：

```java
String normalizedOperatorId = normalizeOperatorId(operatorId);
```

所以输入头里的前后空格会先被 `normalizeOperatorId(...)` 处理掉。

## 二、前端提前提示

文件：`src/main/resources/static/failed-events.js`

后端一定要拦截，因为前端不可信；但前端提前提示能减少无效提交。

审批按钮调用：

```javascript
document.getElementById("approveReplayButton").addEventListener("click", () => reviewReplayApproval("APPROVED"));
document.getElementById("rejectReplayButton").addEventListener("click", () => reviewReplayApproval("REJECTED"));
```

v28 在提交前增加判断：

```javascript
if (isSelfReviewAttempt()) {
    showToast("申请人不能审批自己的重放申请，请切换审批人", true);
    return;
}
```

判断逻辑：

```javascript
function isSelfReviewAttempt() {
    const event = state.activeEvent || {};
    return event.replayApprovalStatus === "PENDING"
            && event.replayApprovalRequestedBy
            && event.replayApprovalRequestedBy === elements.replayOperatorIdInput.value.trim();
}
```

这段逻辑只在页面上做友好提示。真正的安全边界仍然是后端 `ensureReplayApprovalReviewerIsDifferent(...)`。

## 三、集成测试固定规则

文件：`src/test/java/com/codexdemo/orderplatform/FailedEventSearchIntegrationTests.java`

原来的审批测试已经覆盖：

```text
未审批不能重放
申请审批
重复申请被拒绝
拒绝审批
再次申请
批准审批
批准后进入重放门禁下一步
审批历史流水查询和导出
```

v28 在第一次申请之后，插入自提自审断言：

```java
assertThatThrownBy(() -> failedEventMessageService.reviewReplayApproval(
        failedMessage.getId(),
        new ReviewFailedEventReplayApprovalRequest(
                FailedEventReplayApprovalStatus.APPROVED,
                "self approval should be blocked"
        ),
        "ops-user",
        "SRE"
))
        .isInstanceOfSatisfying(ResponseStatusException.class, ex -> {
            assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
            assertThat(ex.getReason()).contains("cannot review own request");
        });
```

这里 `ops-user` 正是前面提交申请的人：

```java
FailedEventMessageResponse pending = failedEventMessageService.requestReplayApproval(
        failedMessage.getId(),
        new RequestFailedEventReplayApprovalRequest("operator verified the fixed event headers"),
        "ops-user",
        "sre"
);
```

再断言这次失败审批不会写历史：

```java
assertThat(failedEventMessageService.listReplayApprovalHistory(failedMessage.getId()))
        .extracting(FailedEventReplayApprovalHistoryResponse::action)
        .containsExactly(FailedEventReplayApprovalHistoryAction.REQUESTED);
```

也就是说：

```text
自提自审被拒绝
 -> 当前审批状态仍然是 PENDING
 -> 审批历史只有 REQUESTED
 -> 没有 APPROVED，也没有 REJECTED
```

后面再由 `sre-lead` 正常审批拒绝：

```java
FailedEventMessageResponse rejected = failedEventMessageService.reviewReplayApproval(
        failedMessage.getId(),
        new ReviewFailedEventReplayApprovalRequest(
                FailedEventReplayApprovalStatus.REJECTED,
                "payload repair is incomplete"
        ),
        "sre-lead",
        "SRE"
);
```

这就证明新规则没有破坏正常审批链路。

## 四、页面静态测试

文件：`src/test/java/com/codexdemo/orderplatform/FailedEventManagementPageTests.java`

静态页面测试新增两个断言：

```java
"isSelfReviewAttempt",
"申请人不能审批自己的重放申请",
```

这类测试不跑浏览器，但能防止以后改页面时把关键函数或提示文案删掉。

## 五、本版后的审批链路

现在失败事件重放审批链路变成：

```text
操作人 A 申请重放
 -> failed_event_messages.replay_approval_requested_by = A
 -> failed_event_replay_approval_history 写 REQUESTED

操作人 A 尝试审批
 -> 后端 409 Conflict
 -> 不改当前审批状态
 -> 不写 APPROVED / REJECTED 历史

操作人 B 审批
 -> 后端允许 APPROVED / REJECTED
 -> 当前审批状态更新
 -> 审批历史写 APPROVED / REJECTED
```

这条规则暂时还是基于 `X-Operator-Id` 请求头，后续接入真实登录后，可以把它替换成认证上下文里的用户 ID。

## 一句话总结

v28 给失败事件重放审批加上了“申请人不能审批自己”的职责分离规则，让审批流从“形式上有审批”继续向真实生产内控靠近。
