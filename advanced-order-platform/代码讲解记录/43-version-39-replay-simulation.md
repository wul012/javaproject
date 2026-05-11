# 第三十九版：失败事件重放 simulation

## 本版目标

v39 按 `D:\nodeproj\orderops-node\docs\plans\v59-post-preflight-control-roadmap.md` 推进，目标是新增一个只读 replay simulation 接口，让 Node 在真实 replay 前能看见预计影响和阻断原因。

```text
GET /api/v1/failed-events/{id}/replay-simulation
 -> eligibleForReplay
 -> wouldReplay
 -> wouldPublishOutbox
 -> wouldChangeManagementStatus
 -> requiredApprovalStatus
 -> idempotencyKeyHint
 -> expectedAggregateId
 -> expectedSideEffects
 -> blockedBy
 -> warnings
 -> nextAllowedActions
```

本版只做预演，不执行 replay，不创建审批，不修改失败事件状态，也不依赖 Node 或 mini-kv。

## 改动文件

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplaySimulationController.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplaySimulationService.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplaySimulationResponse.java
src/test/java/com/codexdemo/orderplatform/notification/FailedEventReplaySimulationServiceTests.java
src/test/java/com/codexdemo/orderplatform/FailedEventReplaySimulationIntegrationTests.java
README.md
a/39/解释/说明.md
```

## 一、接口入口仍然保持只读

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplaySimulationController.java`

Controller 只暴露一个 GET：

```java
@RestController
@RequestMapping("/api/v1/failed-events")
public class FailedEventReplaySimulationController {
```

路由：

```java
@GetMapping("/{id}/replay-simulation")
public FailedEventReplaySimulationResponse replaySimulation(@PathVariable Long id) {
    return failedEventReplaySimulationService.simulation(id);
}
```

它和真实重放接口的关系是：

```text
GET /replay-simulation
 -> 只读预演，说明如果现在 replay 预计会发生什么

POST /replay
 -> 真实执行，可能发布 RabbitMQ 消息并写重放尝试审计
```

## 二、响应对象面向 execution preview

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplaySimulationResponse.java`

响应对象没有返回 payload，而是返回“预期影响”：

```java
public record FailedEventReplaySimulationResponse(
        Instant sampledAt,
        Long failedEventId,
        boolean exists,
        boolean eligibleForReplay,
        boolean wouldReplay,
        boolean wouldPublishOutbox,
        boolean wouldChangeManagementStatus,
        FailedEventReplayApprovalStatus requiredApprovalStatus,
        String idempotencyKeyHint,
        String expectedAggregateId,
        List<String> expectedSideEffects,
        List<String> blockedBy,
        List<String> warnings,
        List<String> nextAllowedActions
) {
}
```

字段分成三类：

```text
资格判断
 -> exists / eligibleForReplay / blockedBy / warnings / nextAllowedActions

预计执行
 -> wouldReplay / wouldPublishOutbox / wouldChangeManagementStatus

证据摘要
 -> requiredApprovalStatus / idempotencyKeyHint / expectedAggregateId / expectedSideEffects
```

这正好给 Node 后续 `execution-preview` 使用：不用触发真实写动作，也能展示“如果执行，大概会碰到什么”。

## 三、服务层复用 readiness，不重复写规则

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplaySimulationService.java`

v39 没有重新实现一套审批、RabbitMQ、字段缺失判断，而是直接依赖 v38 的 readiness：

```java
private final FailedEventReplayReadinessService failedEventReplayReadinessService;
```

核心入口：

```java
@Transactional(readOnly = true)
public FailedEventReplaySimulationResponse simulation(Long id) {
    FailedEventReplayReadinessResponse readiness = failedEventReplayReadinessService.readiness(id);
    boolean wouldReplay = readiness.exists() && readiness.eligibleForReplay();
```

这有两个好处：

```text
1. readiness 和 simulation 对“是否可重放”的判断保持一致。
2. 后续如果真实 replay 前置条件变化，只需要先维护 readiness，再由 simulation 自动继承。
```

## 四、wouldReplay 表示预计会进入真实重放路径

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplaySimulationService.java`

simulation 的核心结论来自 readiness：

```java
boolean wouldReplay = readiness.exists() && readiness.eligibleForReplay();
```

然后把它映射到两个执行预期：

```java
wouldReplay,
wouldReplay,
false,
```

对应字段是：

```text
wouldReplay
wouldPublishOutbox
wouldChangeManagementStatus
```

也就是说：

```text
如果 readiness 可重放
 -> 预计真实 replay 会尝试发布 RabbitMQ Outbox 消息
 -> 预计不会修改 managementStatus
```

`wouldChangeManagementStatus=false` 是对现有真实逻辑的说明。真实 replay 会更新失败事件 replay 状态和审计尝试，但不会把管理状态从 `OPEN` 改成 `RESOLVED`。

## 五、expectedSideEffects 描述可能副作用

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplaySimulationService.java`

如果当前不能重放，副作用为空：

```java
if (!wouldReplay) {
    return List.of();
}
```

如果预计可以重放，返回四个可能影响：

```java
return List.of(
        "PUBLISH_RABBITMQ_REPLAY_MESSAGE",
        "SAVE_REPLAY_ATTEMPT_AUDIT",
        "MARK_FAILED_EVENT_REPLAYED_ON_SUCCESS",
        "MARK_FAILED_EVENT_REPLAY_FAILED_ON_BROKER_ERROR"
);
```

这些对应真实 replay 里的逻辑：

```java
publishReplay(failedMessage, eventId, eventType, aggregateType, aggregateId, payload);
failedMessage.markReplayed(eventId, replayedAt);
saveReplayAttempt(... FailedEventReplayAttemptStatus.SUCCEEDED ...);
```

以及失败路径：

```java
failedMessage.markReplayFailed(eventId, errorMessage, replayedAt);
saveReplayAttempt(... FailedEventReplayAttemptStatus.FAILED ...);
```

simulation 不执行这些代码，只把它们变成可读证据。

## 六、idempotencyKeyHint 是预演提示，不是持久化锁

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplaySimulationService.java`

接口返回一个稳定 hint：

```java
return "failed-event-replay:%s:%s".formatted(readiness.failedEventId(), aggregate);
```

如果事件不存在：

```java
if (!readiness.exists()) {
    return null;
}
```

这个字段不是数据库唯一键，也不是实际幂等锁。它的作用是给 Node 报告展示一个可读的幂等线索：

```text
failed-event-replay:<failedEventId>:<aggregateId>
```

后续 Node 可以把它放进 preview 或审批报告里，帮助人工复核同一个失败事件是否被重复预演。

## 七、阻断、预警和下一步完全继承 readiness

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplaySimulationService.java`

simulation 响应最后直接带回 readiness 的判断结果：

```java
readiness.blockedBy(),
readiness.warnings(),
readiness.nextAllowedActions()
```

这意味着：

```text
审批未申请
 -> blockedBy = REPLAY_APPROVAL_NOT_REQUESTED
 -> nextAllowedActions = REQUEST_REPLAY_APPROVAL

审批待审核
 -> blockedBy = REPLAY_APPROVAL_PENDING
 -> nextAllowedActions = REVIEW_REPLAY_APPROVAL

审批通过且 RabbitMQ Outbox 可用
 -> blockedBy = []
 -> nextAllowedActions = REPLAY_FAILED_EVENT
```

Node 后续不需要分别调 readiness 和 simulation 再手动合并基础结论；simulation 已经提供一份“资格 + 预计副作用”的合并视图。

## 八、测试覆盖

文件：`src/test/java/com/codexdemo/orderplatform/notification/FailedEventReplaySimulationServiceTests.java`

不存在事件时，断言不会有任何副作用：

```java
assertThat(response.exists()).isFalse();
assertThat(response.wouldReplay()).isFalse();
assertThat(response.wouldPublishOutbox()).isFalse();
assertThat(response.expectedSideEffects()).isEmpty();
assertThat(response.blockedBy()).containsExactly("FAILED_EVENT_NOT_FOUND");
```

ready 事件时，断言预计副作用：

```java
assertThat(response.wouldReplay()).isTrue();
assertThat(response.wouldPublishOutbox()).isTrue();
assertThat(response.wouldChangeManagementStatus()).isFalse();
assertThat(response.idempotencyKeyHint()).isEqualTo("failed-event-replay:10:order-1001");
```

副作用列表：

```java
assertThat(response.expectedSideEffects()).containsExactly(
        "PUBLISH_RABBITMQ_REPLAY_MESSAGE",
        "SAVE_REPLAY_ATTEMPT_AUDIT",
        "MARK_FAILED_EVENT_REPLAYED_ON_SUCCESS",
        "MARK_FAILED_EVENT_REPLAY_FAILED_ON_BROKER_ERROR"
);
```

文件：`src/test/java/com/codexdemo/orderplatform/FailedEventReplaySimulationIntegrationTests.java`

MockMvc 验证 HTTP JSON：

```java
mockMvc.perform(get("/api/v1/failed-events/{id}/replay-simulation", savedTarget.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.exists").value(true))
        .andExpect(jsonPath("$.eligibleForReplay").value(true))
        .andExpect(jsonPath("$.wouldReplay").value(true))
        .andExpect(jsonPath("$.wouldPublishOutbox").value(true))
        .andExpect(jsonPath("$.wouldChangeManagementStatus").value(false))
        .andExpect(jsonPath("$.requiredApprovalStatus").value("APPROVED"))
        .andExpect(jsonPath("$.expectedSideEffects[0]").value("PUBLISH_RABBITMQ_REPLAY_MESSAGE"));
```

## 九、本版边界

v39 不做：

```text
不执行 replay
不创建 replay approval
不审核 replay approval
不写 replay attempt
不调用 RabbitMQ
不修改 failed event 状态
不修改 managementStatus
不改数据库结构
不改 Node
不改 mini-kv
```

它只是把真实 replay 之前的“预计影响”提前整理成一个只读 JSON。

## 一句话总结

v39 把失败事件治理从 readiness 推进到 simulation，让 Node 后续可以生成更接近真实执行前的 operation execution preview，但仍然不触发任何真实上游写动作。
