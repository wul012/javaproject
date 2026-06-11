# 171. Java v169 runtime execution live-read gate

本版接收 Node v406 live-read gate。Node v405 已确认三份 canonical approval input 的值有效，Node v406 进一步确认 Java 和 mini-kv 的 owner、loopback target、port、cleanup proof 要求都已准备好。Java v169 只记录这个 gate，仍不启动服务、不执行 smoke。

## 入口和归档

- Live endpoint: `GET /api/v1/ops/shard-readiness/runtime-execution-live-read-gate`
- Static fixture: `GET /contracts/java-shard-readiness-runtime-execution-live-read-gate-v169.fixture.json`
- Archive evidence: `e/169/evidence/java-shard-readiness-runtime-execution-live-read-gate-v169.json`
- Browser page: `e/169/java-shard-readiness-runtime-execution-live-read-gate-v169.html`
- Screenshot: `e/169/图片/java-shard-readiness-runtime-execution-live-read-gate-v169.png`

## 代码变更

- `OpsShardReadinessRuntimeExecutionLiveReadGateResponse` 定义 v169 JSON contract。
- `OpsShardReadinessRuntimeExecutionLiveReadGateService` 从 Java v168 value validation receipt 派生 live-read gate receipt。
- `OpsShardReadinessController` 增加 v169 live endpoint。
- `OpsShardReadinessEvidenceEndpoints` 把 v169 live/fixture 纳入 evidence endpoint 顺序。
- `OpsEvidenceServiceTests` 更新 read-only probe endpoint 期望。
- 新增 service 和 MockMvc integration tests，覆盖 Node v406 gate fields、runtime targets、cleanup proof requirements 和 fail-closed rules。

## 运行边界

v169 明确保持：

- `runtimeSmokeAttempted=false`
- `startsJavaService=false`
- `startsMiniKvService=false`
- `executionAllowed=false`
- `connectsManagedAudit=false`
- `credentialValueRead=false`
- `rawEndpointUrlParsed=false`
- `writeOperationsAllowed=false`

## 后续

下一步应由 Java v170 接收 Node v407-v409 pass evidence chain：确认 approved local-loopback read-only smoke 已通过、archive verification 已完成、cleanup proof 已验证。v169 本身不能作为 smoke pass evidence。

## 验证记录

定向测试和全量 `mvn -q test` 均已通过；测试期间仍会出现既有 Testcontainers Docker 探测日志、Mockito/JDK 动态 agent 警告，以及静态页面 favicon 404，但 Maven 退出码为 0。
