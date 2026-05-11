# 第四十版：失败事件重放 approval-status

## 本版目标

v40 按 `D:\nodeproj\orderops-node\docs\plans\v59-post-preflight-control-roadmap.md` 推进，目标是给 Java 增加一个只读 replay approval read model，让 Node 后续能确认 Java 自己看到的审批状态。

```text
GET /api/v1/failed-events/{id}/approval-status
 -> approvalStatus
 -> requiredApprovalStatus
 -> approvedForReplay
 -> request / review 字段
 -> historyCount
 -> latestApproval
 -> approvalBlockedBy
 -> nextAllowedActions
```

本版不申请审批，不审核审批，不执行重放，不调用 Node，不依赖 mini-kv。

## 改动文件

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatusController.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatusService.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatusResponse.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalHistoryRepository.java
src/test/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatusServiceTests.java
src/test/java/com/codexdemo/orderplatform/FailedEventReplayApprovalStatusIntegrationTests.java
README.md
a/40/解释/说明.md
```

## 一、接口入口保持单一 GET

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatusController.java`

Controller 仍然挂在失败事件资源下面：

```java
@RestController
@RequestMapping("/api/v1/failed-events")
public class FailedEventReplayApprovalStatusController {
```

接口路径：

```java
@GetMapping("/{id}/approval-status")
public FailedEventReplayApprovalStatusResponse approvalStatus(@PathVariable Long id) {
    return failedEventReplayApprovalStatusService.approvalStatus(id);
}
```

它和已有接口的分工是：

```text
GET /replay-readiness
 -> 判断整体能不能 replay，包含 RabbitMQ、字段完整性、是否已重放等阻断

GET /replay-simulation
 -> 在 readiness 基础上预演真实 replay 预计副作用

GET /approval-status
 -> 只回答 Java 当前保存的 replay approval 状态
```

这样 Node 后续做 approval evidence 时，不需要从通用失败事件详情里自己拼审批状态。

## 二、响应对象专门服务审批状态核对

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatusResponse.java`

响应对象先给出采样和事件是否存在：

```java
public record FailedEventReplayApprovalStatusResponse(
        Instant sampledAt,
        Long failedEventId,
        boolean exists,
```

然后返回失败事件状态和管理状态：

```java
        FailedEventMessageStatus failedEventStatus,
        FailedEventManagementStatus managementStatus,
```

这两个字段不是审批字段，但对控制面有用：

```text
failedEventStatus
 -> 让 Node 知道该事件是不是已经 REPLAYED

managementStatus
 -> 让 Node 知道人工管理状态是否已经 RESOLVED / IGNORED / INVESTIGATING
```

审批核心字段集中在这里：

```java
        FailedEventReplayApprovalStatus approvalStatus,
        FailedEventReplayApprovalStatus requiredApprovalStatus,
        boolean approvalRequested,
        boolean approvalPending,
        boolean approvedForReplay,
        boolean rejected,
```

`requiredApprovalStatus` 固定表达真实 replay 所需的审批状态：

```text
APPROVED
```

`approvedForReplay=true` 只说明审批门禁已经通过，不等于整体 replay 一定可执行。整体资格仍然看 v38 readiness。

## 三、not found 也返回稳定结构

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatusResponse.java`

不存在的失败事件不会抛 404，而是返回稳定 JSON：

```java
public static FailedEventReplayApprovalStatusResponse notFound(Long failedEventId, Instant sampledAt) {
    return new FailedEventReplayApprovalStatusResponse(
            sampledAt,
            failedEventId,
            false,
```

关键字段：

```java
            FailedEventReplayApprovalStatus.APPROVED,
            false,
            false,
            false,
            false,
```

阻断原因：

```java
            List.of("FAILED_EVENT_NOT_FOUND"),
            List.of()
```

这样 Node 可以把不存在 ID 当作 evidence 的一种稳定状态，而不是把 HTTP 错误和业务状态混在一起。

## 四、Service 只读读取事件和审批历史

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatusService.java`

Service 明确是只读事务：

```java
@Transactional(readOnly = true)
public FailedEventReplayApprovalStatusResponse approvalStatus(Long id) {
```

ID 基础校验仍然保留：

```java
if (id == null || id < 1) {
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "failed event id must be positive");
}
```

查询逻辑只有一条主线：

```java
return failedEventMessageRepository.findById(id)
        .map(failedMessage -> approvalStatus(failedMessage, sampledAt))
        .orElseGet(() -> FailedEventReplayApprovalStatusResponse.notFound(id, sampledAt));
```

这里没有调用：

```text
FailedEventMessage.requestReplayApproval(...)
FailedEventMessage.approveReplay(...)
FailedEventMessage.rejectReplay(...)
FailedEventMessage.markReplayed(...)
RabbitTemplate.convertAndSend(...)
```

所以它不会改变审批、重放或消息投递状态。

## 五、审批布尔字段都从当前状态派生

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatusService.java`

Service 先拿当前审批状态：

```java
FailedEventReplayApprovalStatus status = failedMessage.getReplayApprovalStatus();
```

然后派生几个给控制面直接消费的布尔值：

```java
status != FailedEventReplayApprovalStatus.NOT_REQUESTED,
status == FailedEventReplayApprovalStatus.PENDING,
status == FailedEventReplayApprovalStatus.APPROVED,
status == FailedEventReplayApprovalStatus.REJECTED,
```

对应语义：

```text
approvalRequested
 -> 只要不是 NOT_REQUESTED，就说明已经进入过审批链

approvalPending
 -> 当前还在等审批

approvedForReplay
 -> Java 当前审批状态满足 replay 门禁

rejected
 -> 最近审批结论是拒绝
```

这种设计避免 Node 侧反复写 enum 判断。

## 六、latestApproval 优先使用审批流水

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatusService.java`

最新审批动作从历史表取：

```java
return failedEventReplayApprovalHistoryRepository
        .findTopByFailedEventMessageIdOrderByChangedAtDescIdDesc(failedMessage.getId())
        .map(FailedEventReplayApprovalStatusResponse.LatestApproval::fromHistory)
        .orElseGet(() -> FailedEventReplayApprovalStatusResponse.LatestApproval.fromMessage(failedMessage));
```

优先用历史表的原因是：

```text
1. 历史表带 operatorRole。
2. 历史表能表达 REQUESTED / APPROVED / REJECTED 的动作。
3. 当前消息表只保存当前状态和最近请求/审核字段。
```

但如果历史表缺失，仍然可以从消息表字段兜底。

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatusResponse.java`

从历史动作映射审批状态：

```java
FailedEventReplayApprovalStatus status = switch (history.getAction()) {
    case REQUESTED -> FailedEventReplayApprovalStatus.PENDING;
    case APPROVED -> FailedEventReplayApprovalStatus.APPROVED;
    case REJECTED -> FailedEventReplayApprovalStatus.REJECTED;
};
```

从消息表兜底时，优先看审核字段：

```java
if (failedMessage.getReplayApprovalReviewedAt() != null) {
```

再看申请字段：

```java
if (failedMessage.getReplayApprovalRequestedAt() != null) {
```

都没有时返回 `null`，表示这条失败事件还没有审批活动。

## 七、approvalBlockedBy 只表达审批阻断

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatusService.java`

审批阻断和 replay readiness 的硬阻断不是一回事。v40 只表达审批阻断：

```java
private List<String> approvalBlockedBy(FailedEventReplayApprovalStatus status) {
    return switch (status) {
        case NOT_REQUESTED -> List.of("REPLAY_APPROVAL_NOT_REQUESTED");
        case PENDING -> List.of("REPLAY_APPROVAL_PENDING");
        case REJECTED -> List.of("REPLAY_APPROVAL_REJECTED");
        case APPROVED -> List.of();
    };
}
```

例如 RabbitMQ Outbox 是否开启、payload 是否为空、事件是否已经重放，这些仍然由：

```text
FailedEventReplayReadinessService
```

负责判断。

## 八、nextAllowedActions 给 Node 一个轻量提示

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatusService.java`

如果失败事件已经重放，不再建议动作：

```java
if (failedMessage.getStatus() == FailedEventMessageStatus.REPLAYED) {
    return List.of();
}
```

否则按审批状态给下一步建议：

```java
return switch (status) {
    case NOT_REQUESTED, REJECTED -> List.of("REQUEST_REPLAY_APPROVAL");
    case PENDING -> List.of("REVIEW_REPLAY_APPROVAL");
    case APPROVED -> List.of("REPLAY_FAILED_EVENT");
};
```

这里的 `REPLAY_FAILED_EVENT` 只表示审批层面已经通过。真正能不能 replay，还要看：

```text
GET /api/v1/failed-events/{id}/replay-readiness
GET /api/v1/failed-events/{id}/replay-simulation
```

## 九、Repository 只新增派生 count 查询

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalHistoryRepository.java`

本版只补一个 Spring Data 派生方法：

```java
long countByFailedEventMessageId(Long failedEventMessageId);
```

它不需要新 SQL 迁移，因为表结构已经在 v27/v11 migration 中存在。v40 只是读取审批历史数量，帮助 Node 判断是否有历史证据。

## 十、测试覆盖只读行为和稳定 JSON

文件：`src/test/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatusServiceTests.java`

不存在 ID：

```java
assertThat(response.exists()).isFalse();
assertThat(response.approvalBlockedBy()).containsExactly("FAILED_EVENT_NOT_FOUND");
assertThat(response.nextAllowedActions()).isEmpty();
```

审批通过：

```java
assertThat(response.approvalStatus()).isEqualTo(FailedEventReplayApprovalStatus.APPROVED);
assertThat(response.approvedForReplay()).isTrue();
assertThat(response.nextAllowedActions()).containsExactly("REPLAY_FAILED_EVENT");
```

历史缺失时从消息字段兜底：

```java
assertThat(response.approvalStatus()).isEqualTo(FailedEventReplayApprovalStatus.PENDING);
assertThat(response.latestApproval().operatorId()).isEqualTo("ops-user");
```

文件：`src/test/java/com/codexdemo/orderplatform/FailedEventReplayApprovalStatusIntegrationTests.java`

MockMvc 验证 HTTP JSON：

```java
mockMvc.perform(get("/api/v1/failed-events/{id}/approval-status", savedTarget.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.approvalStatus").value("APPROVED"))
        .andExpect(jsonPath("$.historyCount").value(2))
        .andExpect(jsonPath("$.latestApproval.operatorRole").value("SRE"));
```

这保证 Node 后续按 HTTP 接入时，字段名和 JSON 结构是稳定的。

## 一句话总结

v40 把失败事件重放审批从“可以查流水”升级成“可以按单条失败事件读取审批状态快照”，让 Node 后续做 approval evidence 时能直接核对 Java 侧当前审批结论，同时保持完全只读。
