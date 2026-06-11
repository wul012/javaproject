# 166. Java v164：runtime execution approval gate input

## 背景

Node v399 已验证 v398 blocked approval gate archive，并指出 Node v400 只有在 approval-gate inputs 齐备时才应进入 intake。Java v164 因此不重复 Node 归档验证，而是把 Java v163 contribution 转成 Java 侧 approval-gate input。

## 主要改动

- `OpsShardReadinessRuntimeExecutionApprovalGateInputResponse`：定义 v164 的 Java approval-gate input 响应结构。
- `OpsShardReadinessRuntimeExecutionApprovalGateInputService`：读取 Java v163 contribution，输出 Java v164 approval-gate input。
- `OpsShardReadinessController`：新增 `GET /api/v1/ops/shard-readiness/runtime-execution-approval-gate-input`。
- `OpsShardReadinessEvidenceEndpoints`：把 v164 live endpoint 和 fixture 加入 evidence/probe 列表。
- `java-shard-readiness-runtime-execution-approval-gate-input-v164.fixture.json`：提供静态 fixture。
- `e/164/`：保存 JSON 归档、HTML、截图、快照和中文说明。

## 关键字段

```text
version=Java v164
sourcePacketContributionVersion=Java v163
lastReviewedByNodeVersion=Node v397
lastArchiveVerifiedByNodeVersion=Node v399
nextNodeConsumerHint=Node v400
javaApprovalGateInputPresent=true
javaApprovalGateInputComplete=true
runtimeGateApprovalPresent=false
nodeApprovedRuntimeWindowPresent=false
correlatedOperatorApprovalRecordPresent=false
crossProjectRuntimeExecutionPacketPresent=false
crossProjectRuntimeExecutionPacketExecutable=false
readyForRuntimeExecutionPacket=false
readyForRuntimeLiveReadGate=false
executionAllowed=false
```

## 设计边界

v164 只代表 Java 侧 input 已准备好，不能代表跨项目 approval gate 已通过。它显式列出仍需的 sibling inputs：

- `mini-kv-approval-gate-input`
- `node-approved-runtime-window`
- `correlated-operator-approval-record`
- `complete-cross-project-runtime-execution-packet`

同时通过 stop conditions 阻止以下误用：

- 从 approval-gate input 启动或停止 Java。
- 从 approval-gate input 执行 runtime probe。
- 把 Java-only input 当作 correlated approval。
- 声明 Node-approved runtime window 或完整跨项目 packet 已存在。
- 读取 credential/raw endpoint 或启用 active shard/write routing。

## 测试

- `OpsShardReadinessRuntimeExecutionApprovalGateInputServiceTests`：验证 v164 服务输出和 fail-closed 语义。
- `OpsShardReadinessRuntimeExecutionApprovalGateInputIntegrationTests`：验证 live endpoint 和静态 fixture。
- `OpsShardReadinessEvidenceEndpointsTests`：验证 endpoint/probe 顺序。
- `OpsEvidenceServiceTests`：验证 ops evidence 汇总包含 v164。

定向测试和全量 `mvn -q test` 均已通过；测试期间仍会出现既有 Testcontainers Docker 探测日志、Mockito/JDK 动态 agent 警告，以及静态页面 favicon 404，但 Maven 退出码为 0。
