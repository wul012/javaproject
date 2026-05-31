# 168. Java v166：runtime execution approval input template compatibility

## 背景

Node v402 发布 runtime approval input template validator，为三个仍缺输入提供机器可校验模板。Java v166 不创建真实批准文件，也不把模板当批准，只提供 Java 侧 compatibility receipt，证明 Java v164/v165 的 canonical input 和 handoff 可被后续真实 canonical input 绑定。

## 主要改动

- `OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityResponse`：定义 v166 template compatibility 响应结构。
- `OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService`：读取 Java v165 handoff，输出 Java v166 compatibility receipt。
- `OpsShardReadinessController`：新增 `GET /api/v1/ops/shard-readiness/runtime-execution-approval-input-template-compatibility`。
- `OpsShardReadinessEvidenceEndpoints`：把 v166 live endpoint 和 fixture 加入 evidence/probe 列表。
- `java-shard-readiness-runtime-execution-approval-input-template-compatibility-v166.fixture.json`：提供静态 fixture。
- `e/166/`：保存 JSON 归档、HTML、截图、快照和中文说明。

## 关键字段

```text
version=Java v166
sourceContractHandoffVersion=Java v165
sourceCanonicalJavaInputVersion=Java v164
lastTemplateValidatorNodeVersion=Node v402
nextNodeConsumerHint=Node v403
templateCompatibilityReceiptPresent=true
templateCompatibilityReceiptComplete=true
sourceJavaInputCanonical=true
nodeTemplateValidatorPresent=true
templatesAreApprovalInputs=false
canonicalApprovalInputsCreatedByJava=false
runtimeGateApprovalPresent=false
nodeApprovedRuntimeWindowPresent=false
correlatedOperatorApprovalRecordPresent=false
completeCrossProjectRuntimeExecutionPacketPresent=false
crossProjectRuntimeExecutionPacketExecutable=false
readyForRuntimeExecutionPacket=false
readyForRuntimeLiveReadGate=false
executionAllowed=false
```

## 设计边界

v166 只证明 Java 侧字段与 Node v402 模板可绑定，不负责生成模板，也不负责把模板转换成批准。它显式阻止：

- 把 template 复制到 canonical input path。
- 从 compatibility receipt 启动或停止 Java。
- 从 compatibility receipt 执行 runtime probe。
- 把 template 当作 Node-approved runtime window、correlated operator approval 或 complete cross-project packet。
- 读取 credential/raw endpoint 或启用 active shard/write routing。

## 测试

- `OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityServiceTests`：验证 v166 服务输出和 fail-closed 语义。
- `OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntegrationTests`：验证 live endpoint 和静态 fixture。
- `OpsShardReadinessEvidenceEndpointsTests`：验证 endpoint/probe 顺序。
- `OpsEvidenceServiceTests`：验证 ops evidence 汇总包含 v166。

定向测试和全量 `mvn -q test` 均已通过；测试期间仍会出现既有 Testcontainers Docker 探测日志、Mockito/JDK 动态 agent 警告，以及静态页面 favicon 404，但 Maven 退出码为 0。
