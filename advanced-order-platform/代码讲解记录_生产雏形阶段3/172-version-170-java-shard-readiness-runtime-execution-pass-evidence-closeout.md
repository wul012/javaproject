# 172. Java v170 runtime execution pass evidence closeout

本版接收 Node v407-v409 的 pass evidence chain。Node v407 完成 approved local-loopback read-only smoke，Node v408 验证 archive 和 cleanup proof，Node v409 形成 closeout ledger。Java v170 只记录这条链已被 Java 侧接收，不重跑 smoke，不启动或停止服务。

## 入口和归档

- Live endpoint: `GET /api/v1/ops/shard-readiness/runtime-execution-pass-evidence-closeout`
- Static fixture: `GET /contracts/java-shard-readiness-runtime-execution-pass-evidence-closeout-v170.fixture.json`
- Archive evidence: `e/170/evidence/java-shard-readiness-runtime-execution-pass-evidence-closeout-v170.json`
- Browser page: `e/170/java-shard-readiness-runtime-execution-pass-evidence-closeout-v170.html`
- Screenshot: `e/170/图片/java-shard-readiness-runtime-execution-pass-evidence-closeout-v170.png`

## 代码变更

- `OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutResponse` 定义 v170 JSON contract。
- `OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService` 从 Java v169 live-read gate receipt 派生 pass evidence closeout receipt。
- `OpsShardReadinessController` 增加 v170 live endpoint。
- `OpsShardReadinessEvidenceEndpoints` 把 v170 live/fixture 纳入 evidence endpoint 顺序。
- `OpsEvidenceServiceTests` 更新 read-only probe endpoint 期望。
- 新增 service 和 MockMvc integration tests，覆盖 Node v407/v408/v409 source evidence、cleanup proof、archive verification、closeout handoff checks 和 fail-closed rules。

## 运行边界

v170 明确保持：

- `runtimeSmokeRerunByJava=false`
- `startsJavaService=false`
- `startsMiniKvService=false`
- `stopsJavaService=false`
- `stopsMiniKvService=false`
- `executionAllowed=false`
- `connectsManagedAudit=false`
- `credentialValueRead=false`
- `rawEndpointUrlParsed=false`
- `writeOperationsAllowed=false`

## 后续

Node v410-v418 当前都是 route group split / maintainability refactor，不是新的 Java runtime approval 或 pass-evidence gate。Java 侧继续推进时应等待新的跨项目证据需求，或者转向 Java 自身的维护性拆分。

## 验证记录

定向测试和全量 `mvn -q test` 均已通过；测试期间仍会出现既有 Testcontainers Docker 探测日志、Mockito/JDK 动态 agent 警告，以及静态页面 favicon 404，但 Maven 退出码为 0。
