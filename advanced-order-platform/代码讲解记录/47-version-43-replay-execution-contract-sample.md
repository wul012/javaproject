> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 第四十三版代码讲解：execution-contract 稳定样本

本版目标很小：给 Node 后续 fixture-driven smoke 一个稳定、可直接读取的 Java replay execution-contract 样本。

它不访问数据库，不执行 replay，不调用 RabbitMQ，也不修改 v42 的真实只读契约接口。

## 样本位置

新增文件：

```text
src/main/resources/static/contracts/failed-event-replay-execution-contract-approved.sample.json
```

因为它放在 Spring Boot 的 `static` 目录下，所以应用启动后可直接通过 HTTP 读取：

```text
GET /contracts/failed-event-replay-execution-contract-approved.sample.json
```

这让 Node v74 可以在 smoke 阶段读取 Java 项目打包后的真实静态资源，而不是继续维护一份完全手写的 mock 样本。

## 样本覆盖字段

计划要求样本覆盖：

```text
contractVersion
contractDigest
approvalDigest
replayEligibilityDigest
replayPreconditionsSatisfied
digestVerificationMode
expectedSideEffects
```

样本中对应字段是：

```json
{
  "contractVersion": "failed-event-replay-execution-contract.v1",
  "contractDigest": "sha256:cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc",
  "approvalDigest": "sha256:aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
  "replayEligibilityDigest": "sha256:bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb",
  "replayPreconditionsSatisfied": true,
  "digestVerificationMode": "CLIENT_PRECHECK_ONLY"
}
```

这几个值和 Node v71-v73 的 mock 样本保持一致，目的是让 Node 后续把 smoke 从“临时 mock”逐步切换到“Java 项目提供的稳定格式样本”。

## 执行前检查样本

样本保留 v42 的 `executionChecks` 结构：

```json
{
  "checkId": "REPLAY_APPROVAL_APPROVED",
  "source": "FailedEventMessageService.replay",
  "category": "APPROVAL",
  "required": true,
  "status": "PASSED",
  "requiredValue": "approvalStatus=APPROVED",
  "currentValue": "approvalStatus=APPROVED",
  "evidenceDigest": "sha256:aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
  "blockedBy": []
}
```

这里的含义是：

```text
真实 POST /api/v1/failed-events/{id}/replay 会要求审批状态已 APPROVED。
样本中的 evidenceDigest 指向 approvalDigest。
blockedBy 为空，表示这是一个 approved happy-path 样本。
```

## 请求要求样本

样本也保留 `requestRequirements`：

```json
{
  "field": "reason",
  "requiredForPost": true,
  "rule": "non-blank replay reason is required",
  "fallback": null
}
```

它对应真实服务里的：

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageService.java
```

真实 replay 会调用：

```java
private String resolveReplayReason(ReplayFailedEventRequest request)
```

所以样本不是只给字段名，而是把真实 POST 的请求规则也固定下来。

## 副作用样本

`expectedSideEffects` 固定为：

```json
[
  "PUBLISH_RABBITMQ_REPLAY_MESSAGE",
  "SAVE_REPLAY_ATTEMPT_AUDIT",
  "MARK_FAILED_EVENT_REPLAYED_ON_SUCCESS",
  "MARK_FAILED_EVENT_REPLAY_FAILED_ON_BROKER_ERROR"
]
```

这和 v42 的真实 contract 保持一致，方便 Node smoke 判断 Java 样本是否仍包含 diagnostics 需要的副作用字段。

## 测试

本版更新：

```text
src/test/java/com/codexdemo/orderplatform/FailedEventReplayExecutionContractIntegrationTests.java
```

新增测试：

```java
mockMvc.perform(get("/contracts/failed-event-replay-execution-contract-approved.sample.json"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith("application/json"))
        .andExpect(jsonPath("$.contractVersion").value("failed-event-replay-execution-contract.v1"))
        .andExpect(jsonPath("$.digestVerificationMode").value("CLIENT_PRECHECK_ONLY"))
        .andExpect(jsonPath("$.expectedSideEffects[0]").value("PUBLISH_RABBITMQ_REPLAY_MESSAGE"));
```

测试关注两件事：

```text
1. 样本确实能通过 Spring 静态资源路径被访问。
2. 样本覆盖 Node v74 计划要求的关键字段。
```

## 一句话总结

v43 把 Java execution-contract 的 happy-path 响应沉淀成稳定静态样本，为 Node 后续 fixture-driven smoke 降低 mock 漂移风险。
