> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 第三十八版：失败事件重放 readiness

## 本版目标

v38 按 `D:\nodeproj\orderops-node\docs\plans\v56-post-dashboard-control-roadmap.md` 推进，目标是新增一个只读接口，让 Node 控制面在真正调用重放接口之前，先知道某条失败事件现在是否具备重放条件、为什么被阻断、下一步应该走哪类动作。

```text
GET /api/v1/failed-events/{id}/replay-readiness
 -> exists
 -> replayApprovalStatus
 -> replayBacklogPosition
 -> eligibleForReplay
 -> requiresApproval
 -> blockedBy
 -> warnings
 -> nextAllowedActions
 -> latestReplayAttempt
 -> latestApproval
```

本版不执行 replay，不创建 approval，不修改管理状态，也不依赖 Node 或 mini-kv。

## 改动文件

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayReadinessController.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayReadinessService.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayReadinessResponse.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageRepository.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayAttemptRepository.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalHistoryRepository.java
src/test/java/com/codexdemo/orderplatform/notification/FailedEventReplayReadinessServiceTests.java
src/test/java/com/codexdemo/orderplatform/FailedEventReplayReadinessIntegrationTests.java
README.md
a/38/解释/说明.md
```

## 一、接口入口保持很薄

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayReadinessController.java`

Controller 只负责把路径参数交给服务层：

```java
@RestController
@RequestMapping("/api/v1/failed-events")
public class FailedEventReplayReadinessController {
```

实际路由：

```java
@GetMapping("/{id}/replay-readiness")
public FailedEventReplayReadinessResponse replayReadiness(@PathVariable Long id) {
    return failedEventReplayReadinessService.readiness(id);
}
```

它和已有写接口形成鲜明边界：

```text
GET  /api/v1/failed-events/{id}/replay-readiness
 -> 只读预演，说明能不能重放

POST /api/v1/failed-events/{id}/replay
 -> 真实重放，会尝试发布 RabbitMQ 消息并写审计
```

## 二、响应对象面向“操作前解释”

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayReadinessResponse.java`

主响应 record 把上下文、结论、阻断和证据放在一起：

```java
public record FailedEventReplayReadinessResponse(
        Instant sampledAt,
        Long failedEventId,
        boolean exists,
        String eventType,
        String aggregateType,
        String aggregateId,
        Instant failedAt,
        FailedEventManagementStatus managementStatus,
        FailedEventReplayApprovalStatus replayApprovalStatus,
        Long replayBacklogPosition,
        boolean eligibleForReplay,
        boolean requiresApproval,
        List<String> blockedBy,
        List<String> warnings,
        List<String> nextAllowedActions,
        LatestReplayAttempt latestReplayAttempt,
        LatestApproval latestApproval
) {
```

这比直接返回 `FailedEventMessageResponse` 更适合控制面，因为 Node 不需要 payload 明细来判断风险，它需要的是：

```text
是否存在
是否可重放
被什么硬条件挡住
有没有软预警
下一步允许走申请、审批还是重放
最近一次尝试和审批证据是什么
```

不存在 ID 也返回稳定 JSON：

```java
public static FailedEventReplayReadinessResponse notFound(Long failedEventId, Instant sampledAt) {
    return new FailedEventReplayReadinessResponse(
            sampledAt,
            failedEventId,
            false,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            false,
            false,
            List.of("FAILED_EVENT_NOT_FOUND"),
            List.of(),
            List.of(),
            null,
            null
    );
}
```

这样 Node 调用不存在 ID 时不用解析错误页或异常结构，可以直接读取：

```json
{
  "exists": false,
  "blockedBy": ["FAILED_EVENT_NOT_FOUND"]
}
```

## 三、服务层只读聚合 readiness

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayReadinessService.java`

入口使用只读事务：

```java
@Transactional(readOnly = true)
public FailedEventReplayReadinessResponse readiness(Long id) {
```

路径 ID 的基础校验仍然保留：

```java
if (id == null || id < 1) {
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "failed event id must be positive");
}
```

查不到失败事件时不抛 404，而是返回稳定 body：

```java
return failedEventMessageRepository.findById(id)
        .map(failedMessage -> readiness(failedMessage, sampledAt))
        .orElseGet(() -> FailedEventReplayReadinessResponse.notFound(id, sampledAt));
```

这里选择 `exists=false`，是为了后续 Node 的 dry-run evidence 更稳定：不存在也是一种可归档的预演结果。

## 四、硬阻断 blockedBy 对齐真实 replay 前置条件

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayReadinessService.java`

真实重放接口里已经要求审批通过：

```java
if (!failedMessage.isReplayApproved()) {
    throw new ResponseStatusException(HttpStatus.CONFLICT, "failed event replay must be approved before replay");
}
```

readiness 把这条规则提前解释成结构化阻断：

```java
switch (failedMessage.getReplayApprovalStatus()) {
    case NOT_REQUESTED -> blockedBy.add("REPLAY_APPROVAL_NOT_REQUESTED");
    case PENDING -> blockedBy.add("REPLAY_APPROVAL_PENDING");
    case REJECTED -> blockedBy.add("REPLAY_APPROVAL_REJECTED");
    case APPROVED -> {
    }
}
```

真实重放还要求 RabbitMQ Outbox 开启：

```java
if (!outboxRabbitMqProperties.isEnabled()) {
    throw new ResponseStatusException(HttpStatus.CONFLICT, "RabbitMQ outbox is disabled");
}
```

readiness 同样提前暴露：

```java
if (!outboxRabbitMqProperties.isEnabled()) {
    blockedBy.add("RABBITMQ_OUTBOX_DISABLED");
}
```

另外，真实重放最终需要事件类型、聚合类型、聚合 ID 和 payload：

```java
String eventType = requiredReplayField("eventType", firstNonBlank(requestEventType(request), failedMessage.getEventType()));
String aggregateType = requiredReplayField(
        "aggregateType",
        firstNonBlank(requestAggregateType(request), failedMessage.getAggregateType())
);
String aggregateId = requiredReplayField(
        "aggregateId",
        firstNonBlank(requestAggregateId(request), failedMessage.getAggregateId())
);
String payload = requiredReplayField("payload", firstNonBlank(requestPayload(request), failedMessage.getPayload()));
```

readiness 将这些字段缺失也变成硬阻断：

```java
if (isBlank(failedMessage.getEventType())) {
    blockedBy.add("EVENT_TYPE_REQUIRED");
}
if (isBlank(failedMessage.getAggregateType())) {
    blockedBy.add("AGGREGATE_TYPE_REQUIRED");
}
if (isBlank(failedMessage.getAggregateId())) {
    blockedBy.add("AGGREGATE_ID_REQUIRED");
}
if (isBlank(failedMessage.getPayload())) {
    blockedBy.add("PAYLOAD_REQUIRED");
}
```

最后结论很直接：

```java
boolean eligibleForReplay = blockedBy.isEmpty();
boolean requiresApproval = failedMessage.getReplayApprovalStatus() != FailedEventReplayApprovalStatus.APPROVED;
```

## 五、warnings 表示软风险，不改变真实语义

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayReadinessService.java`

readiness 没有把管理状态强行变成阻断，因为现有真实 replay 逻辑没有用管理状态拦截。它只做预警：

```java
if (failedMessage.getManagementStatus() == FailedEventManagementStatus.IGNORED) {
    warnings.add("MANAGEMENT_STATUS_IGNORED");
}
if (failedMessage.getManagementStatus() == FailedEventManagementStatus.RESOLVED) {
    warnings.add("MANAGEMENT_STATUS_RESOLVED");
}
```

同理，历史尝试失败也只是提示：

```java
if (failedMessage.getReplayCount() > 0) {
    warnings.add("HAS_PREVIOUS_REPLAY_ATTEMPTS");
}
if (failedMessage.getStatus() == FailedEventMessageStatus.REPLAY_FAILED) {
    warnings.add("LATEST_REPLAY_ATTEMPT_FAILED");
}
```

这让接口语义更精确：

```text
blockedBy
 -> 当前不允许重放的硬条件

warnings
 -> 可以给人看、给 Node 展示，但不直接改变后端 replay 判定的风险提示
```

## 六、nextAllowedActions 给控制面下一步

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayReadinessService.java`

如果已经可重放：

```java
if (eligibleForReplay) {
    return List.of("REPLAY_FAILED_EVENT");
}
```

如果审批还没有走完：

```java
return switch (failedMessage.getReplayApprovalStatus()) {
    case NOT_REQUESTED, REJECTED -> List.of("REQUEST_REPLAY_APPROVAL");
    case PENDING -> List.of("REVIEW_REPLAY_APPROVAL");
    case APPROVED -> replayRepairActions(failedMessage);
};
```

如果审批已经通过但仍被技术条件阻断，就返回修复方向：

```java
if (!outboxRabbitMqProperties.isEnabled()) {
    actions.add("ENABLE_RABBITMQ_OUTBOX");
}
if (isBlank(failedMessage.getEventType())
        || isBlank(failedMessage.getAggregateType())
        || isBlank(failedMessage.getAggregateId())
        || isBlank(failedMessage.getPayload())) {
    actions.add("SUPPLY_REPLAY_REQUEST_FIELDS");
}
```

这样 Node 后续可以把 readiness 结果显示成：

```text
当前不能重放
原因：REPLAY_APPROVAL_PENDING
下一步：REVIEW_REPLAY_APPROVAL
```

## 七、replayBacklogPosition 是单条事件的积压位置

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageRepository.java`

v37 的 `replayBacklog` 是全局积压数量，v38 的 `replayBacklogPosition` 则是单条失败事件在未重放队列里的位置。

Repository 用 JPQL 计算当前事件之前还有多少未成功 `REPLAYED` 的事件：

```java
@Query("""
        select count(message)
        from FailedEventMessage message
        where message.status <> :replayedStatus
          and (
                message.failedAt < :failedAt
                or (message.failedAt = :failedAt and message.id < :id)
          )
        """)
long countReplayBacklogBefore(
        @Param("replayedStatus") FailedEventMessageStatus replayedStatus,
        @Param("failedAt") Instant failedAt,
        @Param("id") Long id
);
```

服务层再加 1：

```java
return failedEventMessageRepository.countReplayBacklogBefore(
        FailedEventMessageStatus.REPLAYED,
        failedMessage.getFailedAt(),
        failedMessage.getId()
) + 1;
```

如果事件已经重放成功，位置返回 `null`：

```java
if (failedMessage.getStatus() == FailedEventMessageStatus.REPLAYED) {
    return null;
}
```

## 八、latestReplayAttempt 和 latestApproval 是证据，不是动作

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayAttemptRepository.java`

最新重放尝试查询：

```java
Optional<FailedEventReplayAttempt> findTopByFailedEventMessageIdOrderByAttemptedAtDescIdDesc(
        Long failedEventMessageId
);
```

响应对象只暴露摘要，不返回 payload：

```java
public record LatestReplayAttempt(
        Long id,
        FailedEventReplayAttemptStatus status,
        String operatorId,
        String operatorRole,
        String errorMessage,
        Instant attemptedAt
) {
```

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalHistoryRepository.java`

最新审批流水查询：

```java
Optional<FailedEventReplayApprovalHistory> findTopByFailedEventMessageIdOrderByChangedAtDescIdDesc(
        Long failedEventMessageId
);
```

响应对象把审批动作映射成当前审批状态：

```java
FailedEventReplayApprovalStatus status = switch (history.getAction()) {
    case REQUESTED -> FailedEventReplayApprovalStatus.PENDING;
    case APPROVED -> FailedEventReplayApprovalStatus.APPROVED;
    case REJECTED -> FailedEventReplayApprovalStatus.REJECTED;
};
```

如果历史流水里没有记录，也会从失败事件当前字段回退生成 `latestApproval`：

```java
.orElseGet(() -> FailedEventReplayReadinessResponse.LatestApproval.fromMessage(failedMessage));
```

这保证老数据、测试数据或手工构造数据也能给出可解释结果。

## 九、测试覆盖

文件：`src/test/java/com/codexdemo/orderplatform/notification/FailedEventReplayReadinessServiceTests.java`

不存在 ID：

```java
when(failedEventMessageRepository.findById(404L)).thenReturn(Optional.empty());

FailedEventReplayReadinessResponse response = service.readiness(404L);

assertThat(response.exists()).isFalse();
assertThat(response.blockedBy()).containsExactly("FAILED_EVENT_NOT_FOUND");
```

审批通过且技术条件满足：

```java
outboxRabbitMqProperties.setEnabled(true);
failedEvent.requestReplayApproval("need replay", "ops-user", Instant.parse("2026-05-11T09:05:00Z"));
failedEvent.approveReplay("sre-user", "approved", Instant.parse("2026-05-11T09:10:00Z"));
```

断言：

```java
assertThat(response.eligibleForReplay()).isTrue();
assertThat(response.requiresApproval()).isFalse();
assertThat(response.blockedBy()).isEmpty();
assertThat(response.nextAllowedActions()).containsExactly("REPLAY_FAILED_EVENT");
assertThat(response.replayBacklogPosition()).isEqualTo(3L);
```

审批待审核：

```java
assertThat(response.eligibleForReplay()).isFalse();
assertThat(response.requiresApproval()).isTrue();
assertThat(response.blockedBy()).containsExactly("REPLAY_APPROVAL_PENDING");
assertThat(response.nextAllowedActions()).containsExactly("REVIEW_REPLAY_APPROVAL");
```

文件：`src/test/java/com/codexdemo/orderplatform/FailedEventReplayReadinessIntegrationTests.java`

MockMvc 验证真实 HTTP JSON：

```java
mockMvc.perform(get("/api/v1/failed-events/{id}/replay-readiness", savedTarget.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.exists").value(true))
        .andExpect(jsonPath("$.replayApprovalStatus").value("APPROVED"))
        .andExpect(jsonPath("$.replayBacklogPosition").value(2))
        .andExpect(jsonPath("$.eligibleForReplay").value(true))
        .andExpect(jsonPath("$.nextAllowedActions[0]").value("REPLAY_FAILED_EVENT"))
        .andExpect(jsonPath("$.latestReplayAttempt.status").value("FAILED"))
        .andExpect(jsonPath("$.latestApproval.status").value("APPROVED"));
```

## 十、本版边界

v38 不做：

```text
不调用 RabbitMQ
不执行 POST /replay
不创建 replay approval
不审核 replay approval
不修改 managementStatus
不改数据库结构
不改 Node
不改 mini-kv
```

它只把已有 Java 治理规则提前整理成一个只读可解释结果。

## 一句话总结

v38 把失败事件重放从“审批后再试试看”推进到“操作前先有 readiness 证据”，让 Node 后续可以做受控操作预演，而不是盲目触发真实写动作。
