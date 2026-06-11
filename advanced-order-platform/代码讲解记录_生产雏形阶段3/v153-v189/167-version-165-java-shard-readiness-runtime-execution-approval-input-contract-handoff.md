> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 167. Java v165：runtime execution approval input contract handoff

## 背景

Node v400 已消费 Java v164、mini-kv v155 和 Node v399，并把下一步 runtime execution approval input intake 写成 owner-by-owner contract。Java v164 已是 complete Java-side input，所以 Java v165 不替换 v164，而是提供 Java 侧 contract handoff，确认 v164 仍是 canonical input，并列出非 Java owner 的缺口。

## 主要改动

- `OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffResponse`：定义 v165 contract handoff 响应结构。
- `OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService`：读取 Java v164 input，输出 Java v165 handoff。
- `OpsShardReadinessController`：新增 `GET /api/v1/ops/shard-readiness/runtime-execution-approval-input-contract-handoff`。
- `OpsShardReadinessEvidenceEndpoints`：把 v165 live endpoint 和 fixture 加入 evidence/probe 列表。
- `java-shard-readiness-runtime-execution-approval-input-contract-handoff-v165.fixture.json`：提供静态 fixture。
- `e/165/`：保存 JSON 归档、HTML、截图、快照和中文说明。

## 关键字段

```text
version=Java v165
sourceApprovalGateInputVersion=Java v164
lastContractedByNodeVersion=Node v400
nextNodeConsumerHint=Node v401
javaApprovalInputContractHandoffPresent=true
javaApprovalInputContractHandoffComplete=true
javaInputRemainsCanonical=true
javaInputChangedByThisVersion=false
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

v165 只做 Java 侧 handoff，不做 runtime approval，也不重新生成 Java input。它保留 Java v164 作为 canonical input，并明确仍需以下非 Java owner 输入：

- `final-mini-kv-approval-gate-input`
- `node-approved-runtime-window`
- `correlated-operator-approval-record`
- `complete-cross-project-runtime-execution-packet`

同时通过 stop conditions 阻止以下误用：

- 从 contract handoff 启动或停止 Java。
- 从 contract handoff 执行 runtime probe。
- 把 handoff 当作 Node-approved runtime window、correlated operator approval 或 complete cross-project packet。
- 读取 credential/raw endpoint 或启用 active shard/write routing。

## 测试

- `OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffServiceTests`：验证 v165 服务输出和 fail-closed 语义。
- `OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffIntegrationTests`：验证 live endpoint 和静态 fixture。
- `OpsShardReadinessEvidenceEndpointsTests`：验证 endpoint/probe 顺序。
- `OpsEvidenceServiceTests`：验证 ops evidence 汇总包含 v165。

定向测试和全量 `mvn -q test` 均已通过；测试期间仍会出现既有 Testcontainers Docker 探测日志、Mockito/JDK 动态 agent 警告，以及静态页面 favicon 404，但 Maven 退出码为 0。
