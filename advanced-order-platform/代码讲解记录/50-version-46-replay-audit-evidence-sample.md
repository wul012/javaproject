> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 第四十六版代码讲解：replay audit evidence 稳定样本

本版目标是补两份 replay audit evidence 静态样本，让控制面能判断一次失败事件重放“是否可追溯”。

它不是新的重放接口，也不是新的审计表迁移。它只把 Java 当前已有的审批历史、重放尝试审计、执行前契约和操作员上下文，用 approved / blocked 两个稳定 JSON 样本固化下来。

## 样本位置

新增两个静态资源：

```text
src/main/resources/static/contracts/failed-event-replay-audit-approved.sample.json
src/main/resources/static/contracts/failed-event-replay-audit-blocked.sample.json
```

Spring Boot 启动后可以直接读取：

```text
GET /contracts/failed-event-replay-audit-approved.sample.json
GET /contracts/failed-event-replay-audit-blocked.sample.json
```

这两个文件和 v43/v44 的 execution-contract 样本放在同一个 `contracts` 目录，方便 Node 或其他控制面统一做 fixture smoke。

## approved 样本

approved 样本的核心字段是：

```json
{
  "auditEvidenceVersion": "failed-event-replay-audit-evidence.v1",
  "scenario": "APPROVED_REPLAY_AUDIT",
  "requestId": "req-replay-audit-20260512-0001",
  "decisionId": "decision-replay-audit-approved-20260512-0001",
  "dryRun": false,
  "executionAllowed": true
}
```

它表达的是：审批已通过，真实 replay 可以被执行，并且执行后应该能查到重放尝试审计。

操作员信息单独放在 `operator` 中：

```json
{
  "operator": {
    "operatorId": "sre-user",
    "operatorRole": "SRE",
    "operatorAction": "REPLAY_FAILED_EVENT"
  }
}
```

这对应真实服务里统一操作员上下文的作用：

```java
operatorContextResolver.resolve(operatorId, operatorRole, FailedEventOperatorAction.REPLAY_FAILED_EVENT)
```

文件位置：

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageService.java
```

## 执行审计字段

approved 样本中 `execution` 固定描述真实重放动作会留下什么证据：

```json
{
  "execution": {
    "attemptAuditType": "FAILED_EVENT_REPLAY_ATTEMPT",
    "attemptStatus": "SUCCEEDED",
    "contractDigest": "sha256:cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc",
    "approvalDigest": "sha256:aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
    "replayEligibilityDigest": "sha256:bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
  }
}
```

真实代码里成功 replay 会保存尝试审计：

```java
failedMessage.markReplayed(eventId, replayedAt);
saveReplayAttempt(
        failedMessage,
        request,
        normalizedOperatorId,
        normalizedOperatorRole,
        reason,
        eventId,
        eventType,
        aggregateType,
        aggregateId,
        payload,
        FailedEventReplayAttemptStatus.SUCCEEDED,
        null,
        replayedAt
);
```

`saveReplayAttempt` 最终写入：

```java
failedEventReplayAttemptRepository.save(FailedEventReplayAttempt.record(
        failedMessage,
        operatorId,
        operatorRole,
        reason,
        request,
        eventId,
        eventType,
        aggregateType,
        aggregateId,
        payload,
        status,
        errorMessage,
        attemptedAt
));
```

这就是样本里 `attemptAuditType=FAILED_EVENT_REPLAY_ATTEMPT` 的来源。

## auditTrail

approved 样本的 `auditTrail` 有三步：

```json
[
  {
    "step": "REQUEST_REPLAY_APPROVAL",
    "auditType": "FAILED_EVENT_REPLAY_APPROVAL_HISTORY",
    "result": "REQUESTED"
  },
  {
    "step": "APPROVE_REPLAY",
    "auditType": "FAILED_EVENT_REPLAY_APPROVAL_HISTORY",
    "result": "APPROVED"
  },
  {
    "step": "REPLAY_FAILED_EVENT",
    "auditType": "FAILED_EVENT_REPLAY_ATTEMPT",
    "result": "SUCCEEDED"
  }
]
```

它把“申请审批 -> 审批通过 -> 执行重放”串成一条可读审计链。

## blocked 样本

blocked 样本表达审批未通过时不能执行：

```json
{
  "scenario": "BLOCKED_REPLAY_AUDIT",
  "dryRun": true,
  "executionAllowed": false,
  "approval": {
    "requiredApprovalStatus": "APPROVED",
    "approvalStatus": "PENDING"
  },
  "blockedBy": [
    "REPLAY_APPROVAL_NOT_APPROVED"
  ]
}
```

这里 `dryRun=true` 的意思是：这只是执行前证据，不是一次真实 replay。

blocked 样本还明确：

```json
{
  "execution": {
    "attemptStatus": "NOT_ATTEMPTED",
    "expectedSideEffects": []
  }
}
```

也就是说，审批未通过时不应该创建 `FailedEventReplayAttempt`，更不应该发布 RabbitMQ 重放消息。

## 测试覆盖

新增测试文件：

```text
src/test/java/com/codexdemo/orderplatform/FailedEventReplayAuditEvidenceSampleTests.java
```

approved 样本测试锁定：

```java
mockMvc.perform(get("/contracts/failed-event-replay-audit-approved.sample.json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.auditEvidenceVersion").value("failed-event-replay-audit-evidence.v1"))
        .andExpect(jsonPath("$.dryRun").value(false))
        .andExpect(jsonPath("$.executionAllowed").value(true))
        .andExpect(jsonPath("$.execution.attemptStatus").value("SUCCEEDED"));
```

blocked 样本测试锁定：

```java
mockMvc.perform(get("/contracts/failed-event-replay-audit-blocked.sample.json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.dryRun").value(true))
        .andExpect(jsonPath("$.executionAllowed").value(false))
        .andExpect(jsonPath("$.execution.attemptStatus").value("NOT_ATTEMPTED"))
        .andExpect(jsonPath("$.blockedBy[0]").value("REPLAY_APPROVAL_NOT_APPROVED"));
```

这样后续如果样本字段被误删，测试会立即失败。

## 一句话总结

v46 用 approved / blocked 两份 replay audit evidence 样本，把 operator、requestId、decisionId、dryRun、executionAllowed 和 auditTrail 固定成可验证契约，帮助控制面判断 Java replay 是否具备可追溯证据。
