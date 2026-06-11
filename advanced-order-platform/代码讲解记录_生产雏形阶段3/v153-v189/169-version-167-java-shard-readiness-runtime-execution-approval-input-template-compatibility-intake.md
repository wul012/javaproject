> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 169. Java v167 runtime execution approval input template compatibility intake

本版继续沿着 Node v403 的计划推进，但 Java 只做只读回执。v167 的核心意思是：Node v403 已经把 Java v166 和 mini-kv v157 的 template compatibility 证据纳入 intake，Java 侧也记录这个 intake 边界；同时三份真正的 canonical approval input 仍然缺失，所以 runtime execution 继续关闭。

## 入口和归档

- Live endpoint: `GET /api/v1/ops/shard-readiness/runtime-execution-approval-input-template-compatibility-intake`
- Static fixture: `GET /contracts/java-shard-readiness-runtime-execution-approval-input-template-compatibility-intake-v167.fixture.json`
- Archive evidence: `e/167/evidence/java-shard-readiness-runtime-execution-approval-input-template-compatibility-intake-v167.json`
- Browser page: `e/167/java-shard-readiness-runtime-execution-approval-input-template-compatibility-intake-v167.html`
- Screenshot: `e/167/图片/java-shard-readiness-runtime-execution-approval-input-template-compatibility-intake-v167.png`

## 代码变更

- `OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeResponse` 定义 v167 JSON contract。
- `OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService` 从 v166 service 读取 source receipt，生成 v167 intake receipt。
- `OpsShardReadinessController` 增加 v167 live endpoint。
- `OpsShardReadinessEvidenceEndpoints` 把 v167 live/fixture 纳入 evidence endpoint 顺序。
- `OpsEvidenceServiceTests` 更新 read-only evidence 的 probe endpoint 期望。
- 新增 service 和 MockMvc integration tests，覆盖 live endpoint、fixture、blocked canonical inputs、production blockers 和 fail-closed rules。

## 运行边界

v167 明确保持：

- `readyForRuntimeExecutionPacket=false`
- `readyForRuntimeLiveReadGate=false`
- `executionAllowed=false`
- `runtimeGateApprovalPresent=false`
- `runtimeExecutionPacketPresent=false`
- `startsJavaService=false`
- `startsMiniKvService=false`
- `connectsManagedAudit=false`
- `credentialValueRead=false`
- `rawEndpointUrlParsed=false`

## 后续

下一步仍然不是从 template 或 intake 启动 runtime。Node v404 只有在以下三个真实 canonical input 都出现，并且共享同一个 `approvalCorrelationId` 后，才适合继续验证：

- `e/398/input/node-approved-runtime-window-v398.json`
- `e/398/input/correlated-operator-approval-record-v398.json`
- `e/398/input/cross-project-runtime-execution-packet-v398.json`

## 验证记录

定向测试和全量 `mvn -q test` 均已通过；测试期间仍会出现既有 Testcontainers Docker 探测日志、Mockito/JDK 动态 agent 警告，以及静态页面 favicon 404，但 Maven 退出码为 0。
