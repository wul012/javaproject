# 第四十四版代码讲解：execution-contract blocked 稳定样本

本版目标是补齐 Java replay execution-contract 的负向样本，让 Node 后续做 scenario matrix 时不只看到 approved happy-path。

它仍然是只读样本：不访问数据库，不执行 replay，不写审计，不调用 RabbitMQ。

## 样本位置

新增文件：

```text
src/main/resources/static/contracts/failed-event-replay-execution-contract-blocked.sample.json
```

Spring Boot 启动后可以直接读取：

```text
GET /contracts/failed-event-replay-execution-contract-blocked.sample.json
```

这个路径和 v43 approved 样本放在同一目录，方便 Node v78 统一读取多个 Java fixture。

## blocked 语义

样本核心字段是：

```json
{
  "approvalStatus": "PENDING",
  "requiredApprovalStatus": "APPROVED",
  "replayPreconditionsSatisfied": false,
  "blockedBy": [
    "REPLAY_APPROVAL_NOT_APPROVED"
  ],
  "expectedSideEffects": []
}
```

这表示失败事件本身存在，但审批还没有通过，所以真实 replay POST 不应该被执行。

## failed check

`executionChecks` 中保留具体阻断点：

```json
{
  "checkId": "REPLAY_APPROVAL_APPROVED",
  "source": "FailedEventMessageService.replay",
  "category": "APPROVAL",
  "required": true,
  "status": "FAILED",
  "requiredValue": "approvalStatus=APPROVED",
  "currentValue": "approvalStatus=PENDING",
  "blockedBy": [
    "REPLAY_APPROVAL_NOT_APPROVED"
  ]
}
```

这样 Node 可以在矩阵里区分：

```text
approved replay -> preconditions true, side effects present
blocked replay  -> preconditions false, blockedBy present, side effects empty
```

## 副作用为空

本版故意让 `expectedSideEffects` 为空：

```json
{
  "expectedSideEffects": []
}
```

原因是 blocked 样本表达的是执行前门禁失败，不应该给外部控制面造成“可以发布 RabbitMQ 重放消息”的误解。

## 测试

更新测试文件：

```text
src/test/java/com/codexdemo/orderplatform/FailedEventReplayExecutionContractIntegrationTests.java
```

新增测试会通过 MockMvc 读取静态资源，并断言：

```java
mockMvc.perform(get("/contracts/failed-event-replay-execution-contract-blocked.sample.json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.replayPreconditionsSatisfied").value(false))
        .andExpect(jsonPath("$.executionChecks[1].status").value("FAILED"))
        .andExpect(jsonPath("$.blockedBy[0]").value("REPLAY_APPROVAL_NOT_APPROVED"))
        .andExpect(jsonPath("$.expectedSideEffects").isEmpty());
```

## 一句话总结

v44 把 replay 被审批阻断的场景沉淀成稳定静态样本，为 Node v78 多场景 fixture matrix 提供负向证据。
