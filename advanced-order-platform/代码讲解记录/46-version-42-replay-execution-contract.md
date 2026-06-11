> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 第四十二版代码讲解：失败事件重放 execution-contract

本版新增一个只读接口，给 Node execution gate 提供 Java 侧“真实 replay 前会检查什么”的契约证据。

它不执行 replay，不写审批，不调用 RabbitMQ，也不改变失败事件状态。

## 入口

新增 Controller：

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayExecutionContractController.java
```

核心代码：

```java
@GetMapping("/{id}/replay-execution-contract")
public FailedEventReplayExecutionContractResponse replayExecutionContract(@PathVariable Long id) {
    return executionContractService.executionContract(id);
}
```

这里继续沿用失败事件统一路由：

```text
/api/v1/failed-events/{id}/replay-execution-contract
```

它和旧接口的分工是：

```text
replay-readiness
 -> 解释能不能 replay、阻断原因和下一步动作

replay-simulation
 -> 解释如果 replay，预计会有哪些副作用

approval-status
 -> 解释 Java 当前保存的审批状态和 digest

replay-execution-contract
 -> 把真实 replay 前的状态、审批、digest、请求字段和副作用契约汇总成执行前证据
```

## 响应对象

新增响应对象：

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayExecutionContractResponse.java
```

第一组字段是契约身份：

```java
String contractVersion,
String contractDigest,
String approvalEvidenceVersion,
String approvalDigest,
String replayEligibilityDigest,
```

含义：

```text
contractVersion
 -> 当前契约格式版本

contractDigest
 -> 对本次执行前契约做 SHA-256 摘要，不包含 sampledAt

approvalDigest / replayEligibilityDigest
 -> 复用 v41 approval-status 证据摘要，方便 Node 判断上游证据是否漂移
```

第二组字段明确真实 replay POST 的边界：

```java
boolean realReplayEndpointEnforcesApprovalDigest,
boolean realReplayEndpointEnforcesReplayEligibilityDigest,
String digestVerificationMode,
String realExecutionMethod,
String realExecutionPath,
String requiredOperatorAction,
```

这里最重要的是：

```text
realReplayEndpointEnforcesApprovalDigest=false
realReplayEndpointEnforcesReplayEligibilityDigest=false
digestVerificationMode=CLIENT_PRECHECK_ONLY
```

意思是：当前 Java 真实 replay POST 仍按数据库状态和后端校验执行最终判断；digest 是 Node 或人工操作台执行前复核证据，不是 POST 的强制入参。

## 执行检查

核心服务：

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayExecutionContractService.java
```

它先读取两个既有只读模型：

```java
FailedEventReplayApprovalStatusResponse approvalStatus = approvalStatusService.approvalStatus(id);
FailedEventReplayReadinessResponse readiness = readinessService.readiness(id);
```

这样 v42 不复制审批状态算法，而是复用 v41 的稳定 digest 和 v38 的 replay 阻断判断。

然后构造 `executionChecks`：

```java
check("FAILED_EVENT_EXISTS", ...);
check("REPLAY_APPROVAL_APPROVED", ...);
check("REPLAY_ELIGIBILITY_DIGEST_AVAILABLE", ...);
existingStateCheck("FAILED_EVENT_NOT_REPLAYED", ...);
readinessBlockerCheck("RABBITMQ_OUTBOX_ENABLED", ...);
readinessBlockerCheck("EVENT_TYPE_PRESENT", ...);
readinessBlockerCheck("AGGREGATE_TYPE_PRESENT", ...);
readinessBlockerCheck("AGGREGATE_ID_PRESENT", ...);
readinessBlockerCheck("PAYLOAD_PRESENT", ...);
```

这些检查对应真实重放路径：

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageService.java
```

真实 replay 会做：

```java
if (!failedMessage.isReplayApproved()) {
    throw new ResponseStatusException(HttpStatus.CONFLICT, "failed event replay must be approved before replay");
}
if (!outboxRabbitMqProperties.isEnabled()) {
    throw new ResponseStatusException(HttpStatus.CONFLICT, "RabbitMQ outbox is disabled");
}
```

还会检查请求和失败事件字段：

```java
String eventType = requiredReplayField("eventType", firstNonBlank(requestEventType(request), failedMessage.getEventType()));
String aggregateType = requiredReplayField("aggregateType", firstNonBlank(requestAggregateType(request), failedMessage.getAggregateType()));
String aggregateId = requiredReplayField("aggregateId", firstNonBlank(requestAggregateId(request), failedMessage.getAggregateId()));
String payload = requiredReplayField("payload", firstNonBlank(requestPayload(request), failedMessage.getPayload()));
```

所以 execution-contract 不是凭空发明字段，而是把真实 replay 服务里的隐性前置条件显性化。

## 请求要求

响应里还会返回：

```java
List<RequestRequirement> requestRequirements
```

当前包含：

```text
reason
 -> POST replay 必填，必须非空

eventId
 -> 可选；如果传入，必须是 UUID

eventType / aggregateType / aggregateId / payload
 -> 可选覆盖字段；最终必须能从请求或原失败事件中得到非空值
```

这对应真实服务：

```java
private String resolveReplayReason(ReplayFailedEventRequest request)
private String resolveReplayEventId(FailedEventMessage failedMessage, ReplayFailedEventRequest request)
private String requiredReplayField(String fieldName, String value)
```

## 稳定摘要

`contractDigest` 的构造排除了 `sampledAt`：

```java
lines.add(line("contractVersion", CONTRACT_VERSION));
lines.add(line("failedEventId", readiness.failedEventId()));
lines.add(line("approvalDigest", approvalStatus.approvalDigest()));
lines.add(line("replayEligibilityDigest", approvalStatus.replayEligibilityDigest()));
lines.add(line("replayPreconditionsSatisfied", replayPreconditionsSatisfied));
addChecks(lines, executionChecks);
addRequirements(lines, requestRequirements);
```

因此同一条失败事件在状态不变时重复读取，`contractDigest` 应保持一致；审批、状态、阻断原因、请求要求或预期副作用变化时，摘要会变化。

## 测试

新增服务测试：

```text
src/test/java/com/codexdemo/orderplatform/notification/FailedEventReplayExecutionContractServiceTests.java
```

覆盖：

```text
not found 契约
approved 契约
contractDigest 稳定性
approval pending 阻断
```

新增集成测试：

```text
src/test/java/com/codexdemo/orderplatform/FailedEventReplayExecutionContractIntegrationTests.java
```

通过 MockMvc 验证真实 HTTP JSON：

```text
contractVersion
contractDigest
approvalDigest
replayEligibilityDigest
executionChecks
requestRequirements
expectedSideEffects
```

## 一句话总结

v42 把“真实 replay 前 Java 到底会看什么”从代码里的隐性逻辑，提升成一个可被 Node execution gate 读取、归档和复核的只读契约。
