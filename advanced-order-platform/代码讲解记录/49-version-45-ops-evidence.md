# 第四十五版代码讲解：订单平台 ops evidence

本版目标是给 Java 订单平台补一个面向控制面的只读证据接口。

它不是新的执行入口，不会申请审批、不会审批、不会 replay、不会写 Outbox，也不会改订单状态。它的角色是把 Java 侧已经存在的失败事件摘要、审批状态、Outbox 配置、服务版本和执行阻断原因聚合成一个稳定 JSON，方便后续控制面先读证据再决定是否展示操作。

## 入口 Controller

本版在原有 ops overview 旁边新增 evidence 入口：

```java
@GetMapping("/evidence")
public OpsEvidenceResponse evidence() {
    return opsEvidenceService.evidence();
}
```

文件位置：

```text
src/main/java/com/codexdemo/orderplatform/ops/OpsOverviewController.java
```

这里没有接收请求体，也没有接收操作员参数，说明它天然不承载写操作。Controller 只负责把 HTTP 请求转交给 `OpsEvidenceService`，所有证据聚合规则放在服务层。

## 响应模型

新增响应对象：

```text
src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceResponse.java
```

最外层字段是：

```java
public record OpsEvidenceResponse(
        Instant sampledAt,
        String evidenceVersion,
        Service service,
        boolean readOnly,
        boolean executionAllowed,
        FailedEventReplay failedEventReplay,
        Outbox outbox,
        ApprovalExecution approvalExecution,
        List<String> blockers,
        List<String> warnings,
        List<String> evidenceEndpoints
) {
}
```

几个关键字段的语义：

```text
readOnly=true
executionAllowed=false
```

这两个字段是给控制面看的硬边界：当前接口只读，不能把 evidence 响应误当成执行许可。

## 服务版本证据

`OpsEvidenceService` 会读取应用名、版本和 profile：

```java
return new OpsEvidenceResponse.Service(
        environment.getProperty("spring.application.name", "advanced-order-platform"),
        environment.getProperty("info.app.version", "0.1.0-SNAPSHOT"),
        profiles(),
        startedAt,
        Math.max(Duration.between(startedAt, sampledAt).toSeconds(), 0)
);
```

这样 Node 或其他控制面可以在同一个响应里看到：

```text
当前接的是哪个 Java 服务
当前服务版本是什么
当前 profile 是 default / local / rabbitmq 还是其他
服务已经运行多久
```

本项目当前没有单独配置 build info，所以版本默认取 `0.1.0-SNAPSHOT`。

## 失败事件重放证据

失败事件部分复用已有摘要服务：

```java
FailedEventSummaryResponse failedEventSummary = failedEventSummaryService.summary();
```

然后转换成 evidence 里的 replay 视图：

```java
return new OpsEvidenceResponse.FailedEventReplay(
        summary.totalFailedEvents(),
        summary.replayBacklog(),
        summary.pendingReplayApprovals(),
        summary.approvedReplayApprovals(),
        summary.rejectedReplayApprovals(),
        summary.latestFailedAt(),
        summary.latestApprovalAt(),
        REAL_REPLAY_ENDPOINT,
        false
);
```

注意最后一个字段：

```text
realReplayAllowedByEvidence=false
```

意思是 evidence 只告诉你真实 replay endpoint 在哪里，但不允许因为读到了 evidence 就直接执行。真正执行仍要走：

```text
POST /api/v1/failed-events/{id}/replay
```

并且由 Java 现有审批、digest、readiness 和 execution-contract 再次校验。

## Outbox 证据

Outbox 部分读取未发布事件数量和 RabbitMQ 配置：

```java
return new OpsEvidenceResponse.Outbox(
        pendingOutboxEvents,
        outboxPublisherProperties.isEnabled(),
        outboxRabbitMqProperties.isEnabled(),
        outboxRabbitMqProperties.getExchange(),
        outboxRabbitMqProperties.getQueue(),
        outboxRabbitMqProperties.getDeadLetterQueue(),
        outboxBlockers
);
```

如果本地默认没有启用发布器或 RabbitMQ，会返回阻断原因：

```java
if (!outboxPublisherProperties.isEnabled()) {
    blockers.add("OUTBOX_PUBLISHER_DISABLED");
}
if (!outboxRabbitMqProperties.isEnabled()) {
    blockers.add("RABBITMQ_OUTBOX_DISABLED");
}
```

这能让控制面区分：

```text
Java 服务在线
但是当前环境不是可真实投递 RabbitMQ 的执行环境
```

## 审批执行证据

`approvalExecution` 描述真实 replay 前应该满足的执行前提：

```java
return new OpsEvidenceResponse.ApprovalExecution(
        "APPROVED",
        "contractDigest must match latest approval-status/readiness evidence before POST /replay",
        true,
        true,
        executionBlockers,
        List.of(
                "GET /api/v1/failed-events/summary",
                "GET /api/v1/failed-events/{id}/replay-readiness",
                "GET /api/v1/failed-events/{id}/replay-execution-contract"
        )
);
```

其中：

```text
requiredApprovalStatus=APPROVED
approvalRequired=true
dryRun=true
```

表达的是：控制面可以把这份 evidence 当成预演证据，但不能把它当成真实执行结果。

## 阻断与预警

执行阻断从失败事件摘要里推导：

```java
blockers.add("READ_ONLY_EVIDENCE_ENDPOINT");
if (summary.pendingReplayApprovals() > 0) {
    blockers.add("REPLAY_APPROVAL_PENDING");
}
if (summary.rejectedReplayApprovals() > 0) {
    blockers.add("REPLAY_APPROVAL_REJECTED");
}
if (summary.replayBacklog() > 0) {
    blockers.add("REPLAY_BACKLOG_PRESENT");
}
```

预警则用于提示当前状态值得关注，但不一定是硬阻断：

```java
if (pendingOutboxEvents > 0) {
    warnings.add("OUTBOX_PENDING_EVENTS");
}
if (summary.approvedReplayApprovals() > 0) {
    warnings.add("APPROVED_REPLAY_REQUIRES_DIGEST_CHECK");
}
```

这种拆法让控制面可以分别展示：

```text
不能执行的原因
可以继续检查的风险点
下一步应该调用哪些只读证据接口
```

## 测试覆盖

服务层测试：

```text
src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java
```

核心断言包括：

```java
assertThat(evidence.readOnly()).isTrue();
assertThat(evidence.executionAllowed()).isFalse();
assertThat(evidence.outbox().blockers())
        .containsExactly("OUTBOX_PUBLISHER_DISABLED", "RABBITMQ_OUTBOX_DISABLED");
```

HTTP 集成测试：

```text
src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java
```

核心断言包括：

```java
mockMvc.perform(get("/api/v1/ops/evidence"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.evidenceVersion").value("java-ops-evidence.v1"))
        .andExpect(jsonPath("$.readOnly").value(true))
        .andExpect(jsonPath("$.executionAllowed").value(false));
```

这两个测试分别保证：

```text
服务层聚合规则稳定
HTTP JSON 契约对控制面稳定
```

## 一句话总结

v45 把 Java 侧 replay、审批、Outbox、服务版本和执行边界汇总成一个只读 ops evidence endpoint，为后续控制面做统一观察和受控操作预演提供稳定证据。
