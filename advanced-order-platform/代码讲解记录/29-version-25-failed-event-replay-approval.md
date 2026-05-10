# 第二十五版：失败事件重放审批门禁

## 本版目标

v24 已经把页面重放做成了“预览 -> 风险提示 -> 二次确认 -> 提交”。v25 继续往生产化推进一层：真正重放前必须先走审批。

新的链路是：
```text
失败事件进入 DLQ
 -> 运维在页面选择失败事件
 -> 申请重放审批
 -> 审批人通过或拒绝
 -> 只有 APPROVED 状态才能进入重放确认弹窗
 -> 后端 replay() 再次检查审批状态
 -> RabbitMQ 重新投递并记录重放审计
```

这版的重点不是多一个按钮，而是把“危险操作必须被授权”沉到数据库、服务层、接口层和页面层四个位置。

## 改动文件

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatus.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessage.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageResponse.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageSearchCriteria.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageController.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageService.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventCsvExporter.java
src/main/resources/db/migration/h2/V10__failed_event_replay_approval.sql
src/main/resources/db/migration/postgresql/V10__failed_event_replay_approval.sql
src/main/resources/static/failed-events.html
src/main/resources/static/failed-events.js
src/main/resources/static/failed-events.css
src/test/java/com/codexdemo/orderplatform/FailedEventSearchIntegrationTests.java
src/test/java/com/codexdemo/orderplatform/RabbitMqNotificationFailureIntegrationTests.java
src/test/java/com/codexdemo/orderplatform/FailedEventManagementPageTests.java
src/test/java/com/codexdemo/orderplatform/PostgresMigrationIntegrationTests.java
README.md
```

## 审批状态枚举

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatus.java`

```java
public enum FailedEventReplayApprovalStatus {
    NOT_REQUESTED,
    PENDING,
    APPROVED,
    REJECTED
}
```

这四个状态对应完整生命周期：
```text
NOT_REQUESTED -> 失败事件刚被记录，还没有人申请重放
PENDING       -> 已申请，等待审批人判断
APPROVED      -> 审批通过，允许进入真正 replay()
REJECTED      -> 审批拒绝，可重新申请
```

## 实体：把审批落到失败事件表

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessage.java`

v25 在 `failed_event_messages` 上直接扩展审批字段：

```java
@Enumerated(EnumType.STRING)
@Column(name = "replay_approval_status", nullable = false, length = 32)
private FailedEventReplayApprovalStatus replayApprovalStatus;

@Column(name = "replay_approval_reason", length = 500)
private String replayApprovalReason;

@Column(name = "replay_approval_requested_by", length = 80)
private String replayApprovalRequestedBy;

@Column(name = "replay_approval_requested_at")
private Instant replayApprovalRequestedAt;
```

审批复核字段用 `reviewed` 命名，而不是只写 `approved`，因为拒绝也需要记录审批人和时间：

```java
@Column(name = "replay_approval_reviewed_by", length = 80)
private String replayApprovalReviewedBy;

@Column(name = "replay_approval_reviewed_at")
private Instant replayApprovalReviewedAt;

@Column(name = "replay_approval_review_note", length = 500)
private String replayApprovalReviewNote;
```

创建失败事件时，默认状态是未申请：

```java
this.status = FailedEventMessageStatus.RECORDED;
this.replayCount = 0;
this.managementStatus = FailedEventManagementStatus.OPEN;
this.replayApprovalStatus = FailedEventReplayApprovalStatus.NOT_REQUESTED;
```

申请审批会重置上一轮复核信息：

```java
public void requestReplayApproval(String reason, String requestedBy, Instant requestedAt) {
    this.replayApprovalStatus = FailedEventReplayApprovalStatus.PENDING;
    this.replayApprovalReason = reason;
    this.replayApprovalRequestedBy = requestedBy;
    this.replayApprovalRequestedAt = requestedAt;
    this.replayApprovalReviewedBy = null;
    this.replayApprovalReviewedAt = null;
    this.replayApprovalReviewNote = null;
}
```

审批通过和拒绝共用内部复核逻辑：

```java
public void approveReplay(String reviewedBy, String reviewNote, Instant reviewedAt) {
    reviewReplayApproval(FailedEventReplayApprovalStatus.APPROVED, reviewedBy, reviewNote, reviewedAt);
}

public void rejectReplay(String reviewedBy, String reviewNote, Instant reviewedAt) {
    reviewReplayApproval(FailedEventReplayApprovalStatus.REJECTED, reviewedBy, reviewNote, reviewedAt);
}
```

最后，`replay()` 只关心一个清晰判断：

```java
public boolean isReplayApproved() {
    return replayApprovalStatus == FailedEventReplayApprovalStatus.APPROVED;
}
```

## 迁移：H2 和 PostgreSQL 同步加列

文件：
```text
src/main/resources/db/migration/h2/V10__failed_event_replay_approval.sql
src/main/resources/db/migration/postgresql/V10__failed_event_replay_approval.sql
```

核心 SQL：
```sql
alter table failed_event_messages
    add column replay_approval_status varchar(32) not null default 'NOT_REQUESTED';

alter table failed_event_messages
    add column replay_approval_reason varchar(500);

alter table failed_event_messages
    add column replay_approval_requested_by varchar(80);

alter table failed_event_messages
    add column replay_approval_requested_at timestamp(6) with time zone;
```

复核字段：
```sql
alter table failed_event_messages
    add column replay_approval_reviewed_by varchar(80);

alter table failed_event_messages
    add column replay_approval_reviewed_at timestamp(6) with time zone;

alter table failed_event_messages
    add column replay_approval_review_note varchar(500);
```

查询索引：
```sql
create index idx_failed_event_messages_replay_approval
    on failed_event_messages (replay_approval_status, replay_approval_requested_at);
```

这个索引用于管理台筛选“待审批失败事件”，也支持按申请时间倒序排查。

## Service：申请、审批、重放门禁

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageService.java`

申请审批入口：

```java
@Transactional
public FailedEventMessageResponse requestReplayApproval(
        Long id,
        RequestFailedEventReplayApprovalRequest request,
        String operatorId,
        String operatorRole
) {
    FailedEventMessage failedMessage = failedEventMessageRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "failed event message not found"));
    String normalizedOperatorId = normalizeOperatorId(operatorId);
    requireAllowedOperatorRole(operatorRole);
    String reason = resolveReplayApprovalReason(request);
    ...
    failedMessage.requestReplayApproval(reason, normalizedOperatorId, Instant.now());
    return FailedEventMessageResponse.from(failedMessage);
}
```

这里复用了 v16 的操作者校验：
```java
String normalizedOperatorId = normalizeOperatorId(operatorId);
requireAllowedOperatorRole(operatorRole);
```

所以审批动作不是匿名的，也不是任意角色都能做。

审批复核入口：

```java
@Transactional
public FailedEventMessageResponse reviewReplayApproval(
        Long id,
        ReviewFailedEventReplayApprovalRequest request,
        String operatorId,
        String operatorRole
) {
    FailedEventReplayApprovalStatus reviewStatus = requireReplayApprovalReviewStatus(request);
    String note = resolveReplayApprovalReviewNote(reviewStatus, request == null ? null : request.note());
    if (failedMessage.getReplayApprovalStatus() != FailedEventReplayApprovalStatus.PENDING) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "replay approval is not pending");
    }
    ...
}
```

拒绝审批必须写原因：

```java
private String resolveReplayApprovalReviewNote(FailedEventReplayApprovalStatus status, String note) {
    if (status == FailedEventReplayApprovalStatus.REJECTED && (note == null || note.isBlank())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "replay approval rejection note is required");
    }
    return note == null || note.isBlank() ? null : truncate(note.strip(), 500);
}
```

最关键的门禁在真正重放前：

```java
String normalizedOperatorId = normalizeOperatorId(operatorId);
String normalizedOperatorRole = requireAllowedOperatorRole(operatorRole);
String reason = resolveReplayReason(request);
if (!failedMessage.isReplayApproved()) {
    throw new ResponseStatusException(HttpStatus.CONFLICT, "failed event replay must be approved before replay");
}
if (!outboxRabbitMqProperties.isEnabled()) {
    throw new ResponseStatusException(HttpStatus.CONFLICT, "RabbitMQ outbox is disabled");
}
```

这个顺序有意义：
```text
先校验操作者
 -> 再校验重放原因
 -> 再校验审批状态
 -> 最后才检查 RabbitMQ 是否开启
```

也就是说，即使 RabbitMQ 没开，未审批重放也会优先被审批门禁拦住，测试可以稳定验证这条规则。

## Controller：三个操作接口

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageController.java`

查询接口新增审批状态过滤：

```java
@RequestParam(required = false) FailedEventReplayApprovalStatus replayApprovalStatus,
```

申请审批：

```java
@PostMapping("/{id}/replay-approval")
public FailedEventMessageResponse requestReplayApproval(
        @PathVariable Long id,
        @RequestHeader(value = "X-Operator-Id", required = false) String operatorId,
        @RequestHeader(value = "X-Operator-Role", required = false) String operatorRole,
        @RequestBody(required = false) RequestFailedEventReplayApprovalRequest request
) {
    return failedEventMessageService.requestReplayApproval(id, request, operatorId, operatorRole);
}
```

审批通过/拒绝：

```java
@PostMapping("/{id}/replay-approval/review")
public FailedEventMessageResponse reviewReplayApproval(
        @PathVariable Long id,
        @RequestHeader(value = "X-Operator-Id", required = false) String operatorId,
        @RequestHeader(value = "X-Operator-Role", required = false) String operatorRole,
        @RequestBody(required = false) ReviewFailedEventReplayApprovalRequest request
) {
    return failedEventMessageService.reviewReplayApproval(id, request, operatorId, operatorRole);
}
```

真正重放接口保留原路径：

```java
@PostMapping("/{id}/replay")
public FailedEventMessageResponse replayFailedMessage(...) {
    return failedEventMessageService.replay(id, request, operatorId, operatorRole);
}
```

调用顺序应该是：
```text
POST /api/v1/failed-events/{id}/replay-approval
POST /api/v1/failed-events/{id}/replay-approval/review
POST /api/v1/failed-events/{id}/replay
```

## 查询和导出：审批状态可筛选、可排序、可导出

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageSearchCriteria.java`

搜索条件增加：

```java
FailedEventReplayApprovalStatus replayApprovalStatus,
```

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageService.java`

排序白名单增加审批字段：

```java
entry("replayApprovalStatus", "replayApprovalStatus"),
entry("replayApprovalRequestedAt", "replayApprovalRequestedAt"),
entry("replayApprovalReviewedAt", "replayApprovalReviewedAt")
```

Specification 动态条件增加：

```java
if (criteria.replayApprovalStatus() != null) {
    predicates.add(criteriaBuilder.equal(root.get("replayApprovalStatus"), criteria.replayApprovalStatus()));
}
```

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventCsvExporter.java`

CSV 也带出审批字段：

```java
"replayApprovalStatus",
"replayApprovalReason",
"replayApprovalRequestedBy",
"replayApprovalRequestedAt",
"replayApprovalReviewedBy",
"replayApprovalReviewedAt",
"replayApprovalReviewNote",
```

这样导出的失败事件可以直接用于事后复盘：是谁申请、何时申请、谁审批、最终通过还是拒绝。

## 页面：审批按钮和前端门禁

文件：`src/main/resources/static/failed-events.html`

筛选区新增：

```html
<select id="replayApprovalStatusFilter">
    <option value="">All</option>
    <option value="NOT_REQUESTED">NOT_REQUESTED</option>
    <option value="PENDING">PENDING</option>
    <option value="APPROVED">APPROVED</option>
    <option value="REJECTED">REJECTED</option>
</select>
```

重放工作台新增两个文本域：

```html
<textarea id="replayApprovalReasonInput" rows="2">request replay approval after DLQ verification</textarea>
<textarea id="replayApprovalReviewNoteInput" rows="2">checked failure reason and replay payload</textarea>
```

新增三个按钮：

```html
<button id="requestReplayApprovalButton" class="secondary-button" type="button">Request approval</button>
<button id="approveReplayButton" class="secondary-button" type="button">Approve</button>
<button id="rejectReplayButton" class="ghost-button" type="button">Reject</button>
```

文件：`src/main/resources/static/failed-events.js`

页面启动时绑定事件：

```javascript
document.getElementById("requestReplayApprovalButton").addEventListener("click", requestReplayApproval);
document.getElementById("approveReplayButton").addEventListener("click", () => reviewReplayApproval("APPROVED"));
document.getElementById("rejectReplayButton").addEventListener("click", () => reviewReplayApproval("REJECTED"));
```

申请审批：

```javascript
async function requestReplayApproval() {
    const id = replayTargetId();
    const reason = elements.replayApprovalReasonInput.value.trim() || elements.replayReasonInput.value.trim();
    const response = await fetch(`${apiBase}/${id}/replay-approval`, {
        method: "POST",
        headers: replayOperatorHeaders(),
        body: JSON.stringify({ reason })
    });
    ...
}
```

审批通过/拒绝：

```javascript
async function reviewReplayApproval(status) {
    const id = replayTargetId();
    const note = elements.replayApprovalReviewNoteInput.value.trim();
    const response = await fetch(`${apiBase}/${id}/replay-approval/review`, {
        method: "POST",
        headers: replayOperatorHeaders(),
        body: JSON.stringify({ status, note })
    });
    ...
}
```

重放前页面也会挡一次：

```javascript
if ((state.activeEvent?.replayApprovalStatus || "NOT_REQUESTED") !== "APPROVED") {
    showToast("重放前必须先审批通过", true);
    return;
}
```

注意：页面门禁只是体验保护，真正的安全边界仍然是后端 `FailedEventMessageService.replay()`。

## 测试：验证审批链路和真实 RabbitMQ 重放

文件：`src/test/java/com/codexdemo/orderplatform/FailedEventSearchIntegrationTests.java`

新增测试先验证未审批重放会被拒绝：

```java
assertThatThrownBy(() -> failedEventMessageService.replay(
        failedMessage.getId(),
        replayRequest,
        "ops-user",
        "SRE"
))
        .isInstanceOfSatisfying(ResponseStatusException.class, ex -> {
            assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
            assertThat(ex.getReason()).contains("approved before replay");
        });
```

然后验证申请审批：

```java
FailedEventMessageResponse pending = failedEventMessageService.requestReplayApproval(
        failedMessage.getId(),
        new RequestFailedEventReplayApprovalRequest("operator verified the fixed event headers"),
        "ops-user",
        "sre"
);
```

查询 `PENDING`：

```java
new FailedEventMessageSearchCriteria(
        null,
        null,
        null,
        null,
        null,
        FailedEventReplayApprovalStatus.PENDING,
        null,
        null,
        0,
        10,
        "replayApprovalRequestedAt,desc",
        null
)
```

拒绝后可重新申请，再审批通过：

```java
new ReviewFailedEventReplayApprovalRequest(
        FailedEventReplayApprovalStatus.REJECTED,
        "payload repair is incomplete"
)
```

```java
new ReviewFailedEventReplayApprovalRequest(FailedEventReplayApprovalStatus.APPROVED, null)
```

文件：`src/test/java/com/codexdemo/orderplatform/RabbitMqNotificationFailureIntegrationTests.java`

真实 RabbitMQ 链路也改成必须先审批：

```java
FailedEventMessageResponse pendingApproval = failedEventMessageService.requestReplayApproval(
        failedMessage.getId(),
        new RequestFailedEventReplayApprovalRequest("DLQ headers repaired and ready for replay"),
        "qa-operator",
        "ORDER_SUPPORT"
);
```

```java
FailedEventMessageResponse approvedApproval = failedEventMessageService.reviewReplayApproval(
        failedMessage.getId(),
        new ReviewFailedEventReplayApprovalRequest(
                FailedEventReplayApprovalStatus.APPROVED,
                "approved after DLQ verification"
        ),
        "qa-lead",
        "SRE"
);
```

最后才允许：

```java
FailedEventMessageResponse replayed = failedEventMessageService.replay(
        failedMessage.getId(),
        replayRequest,
        "qa-operator",
        "ORDER_SUPPORT"
);
```

这个测试证明的是完整链路：
```text
非法角色拒绝
 -> 合法角色但未审批拒绝
 -> 申请审批
 -> 审批通过
 -> RabbitMQ 真实重放
 -> notification_messages 落库
 -> replay_attempts 记录 SUCCEEDED
```

## 本版总结

v25 把失败事件重放从“有权限、有原因、有确认”继续升级为“有申请、有审批、有门禁”。页面可以更顺手地操作，但真正的规则落在服务层和数据库状态上。

这一步让失败事件管理模块更像生产系统：不是谁看到按钮就能重放，而是每一次重放都先留下审批意图，再由审批人明确放行。
