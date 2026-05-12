# 第四十七版代码讲解：replay evidence index

本版目标是给 Java 失败事件重放链路补一个只读“证据目录”。

v43-v46 已经有 execution-contract approved/blocked 样本、replay audit approved/blocked 样本和 live 只读证据接口。v47 不再新增某一个样本，而是把这些证据入口汇总成一个稳定索引，让控制面先知道“Java 有哪些证据可读、哪些字段必须保留、哪些安全规则不能绕过”。

## Controller 入口

新增文件：

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayEvidenceIndexController.java
```

核心入口是：

```java
@GetMapping("/replay-evidence-index")
public FailedEventReplayEvidenceIndexResponse index() {
    return failedEventReplayEvidenceIndexService.index();
}
```

完整 HTTP 路径：

```text
GET /api/v1/failed-events/replay-evidence-index
```

这个接口没有请求体，没有操作员 Header，也不接收 failedEventId，说明它不是针对某一条失败事件做执行判断，而是返回整条 replay evidence 能力目录。

## 响应模型

新增响应对象：

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayEvidenceIndexResponse.java
```

最外层字段是：

```java
public record FailedEventReplayEvidenceIndexResponse(
        Instant sampledAt,
        String evidenceVersion,
        boolean readOnly,
        boolean executionAllowed,
        List<LiveEvidenceEndpoint> liveEvidenceEndpoints,
        List<StaticEvidenceSample> staticEvidenceSamples,
        List<String> auditIdentityFields,
        List<String> executionSafetyRules,
        List<String> productionReadinessNotes
) {
}
```

这里最重要的边界是：

```text
readOnly=true
executionAllowed=false
```

也就是说，这个 index 本身只做说明，不代表 Java 允许真实 replay。

## live evidence 目录

服务层在这里列出 Java 当前可读的 live endpoint：

```java
new FailedEventReplayEvidenceIndexResponse.LiveEvidenceEndpoint(
        "replay-execution-contract",
        "GET",
        "/api/v1/failed-events/{id}/replay-execution-contract",
        "Describe the preconditions and expected side effects of real replay.",
        true,
        false
)
```

文件位置：

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayEvidenceIndexService.java
```

`changesReplayState=false` 很关键，它告诉控制面：这些 GET 入口不能改变 replay 状态。

本版索引的 live endpoint 包括：

```text
/api/v1/failed-events/summary
/api/v1/failed-events/{id}/replay-readiness
/api/v1/failed-events/{id}/approval-status
/api/v1/failed-events/{id}/replay-execution-contract
/api/v1/failed-events/{id}/replay-attempts
/api/v1/failed-events/{id}/replay-approval-history
```

## static sample 目录

静态样本目录这样描述：

```java
new FailedEventReplayEvidenceIndexResponse.StaticEvidenceSample(
        "replay-audit-approved",
        "/contracts/failed-event-replay-audit-approved.sample.json",
        "APPROVED_REPLAY_AUDIT",
        "failed-event-replay-audit-evidence.v1",
        List.of(
                "operator",
                "requestId",
                "decisionId",
                "dryRun",
                "executionAllowed",
                "auditTrail"
        )
)
```

它把 v46 的样本字段要求显式列出来，后续控制面做 fixture 校验时不需要猜字段。

本版索引的静态样本包括：

```text
/contracts/failed-event-replay-execution-contract-approved.sample.json
/contracts/failed-event-replay-execution-contract-blocked.sample.json
/contracts/failed-event-replay-audit-approved.sample.json
/contracts/failed-event-replay-audit-blocked.sample.json
```

## 审计身份字段

`auditIdentityFields` 统一列出审计追溯必须关注的身份字段：

```java
return List.of(
        "operator.operatorId",
        "operator.operatorRole",
        "requestId",
        "decisionId",
        "approval.requestedBy",
        "approval.reviewedBy",
        "execution.attemptAuditType",
        "execution.attemptStatus"
);
```

这些字段对应 v46 样本里表达的核心问题：

```text
谁申请的
谁审批的
谁执行的
执行决策是哪一次
执行是否真的产生了 replay attempt
```

## 执行安全规则

`executionSafetyRules` 把真实 replay 之前不可绕过的条件列出来：

```java
return List.of(
        "REAL_REPLAY_REQUIRES_APPROVED_STATUS",
        "REAL_REPLAY_REQUIRES_OPERATOR_ACTION_REPLAY_FAILED_EVENT",
        "REAL_REPLAY_REQUIRES_NON_BLANK_REASON",
        "REAL_REPLAY_REQUIRES_RABBITMQ_OUTBOX_ENABLED",
        "READ_ONLY_EVIDENCE_ENDPOINTS_MUST_NOT_CHANGE_REPLAY_STATE",
        "BLOCKED_PRECHECK_MUST_NOT_CREATE_REPLAY_ATTEMPT"
);
```

这些规则不是新的执行逻辑，而是把已有 Java 逻辑和样本边界翻译成稳定说明，方便 Node v103 之类的生产 readiness 汇总读取。

## 测试覆盖

新增测试：

```text
src/test/java/com/codexdemo/orderplatform/FailedEventReplayEvidenceIndexIntegrationTests.java
```

核心断言包括：

```java
mockMvc.perform(get("/api/v1/failed-events/replay-evidence-index"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.evidenceVersion").value("failed-event-replay-evidence-index.v1"))
        .andExpect(jsonPath("$.readOnly").value(true))
        .andExpect(jsonPath("$.executionAllowed").value(false));
```

同时测试还锁定：

```text
liveEvidenceEndpoints
staticEvidenceSamples
auditIdentityFields
executionSafetyRules
productionReadinessNotes
```

这样 endpoint 将来如果误删样本路径、审计字段或安全规则，会被测试拦住。

## 一句话总结

v47 把 Java 失败事件重放链路的 live endpoint、静态样本、审计身份字段和执行安全规则整理成只读 evidence index，为后续控制面做生产 readiness 汇总提供稳定上游说明。
