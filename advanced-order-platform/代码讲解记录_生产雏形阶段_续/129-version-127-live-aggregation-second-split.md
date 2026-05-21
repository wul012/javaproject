# Java v127: Ops Release Approval Live Aggregation 二次拆分

本版执行 Node v289 之后的 Java v127：继续拆 `OpsReleaseApprovalRehearsalLiveAggregationIntegrationTests`。目标不是做大改，而是把一个超长 live aggregation 回归按响应职责切成多个独立测试类，减少后续维护时的交叉噪音。

## 改动

- 原主类只保留基础 live readiness 断言，行数从 976 行降到 225 行。
- 新增 `AuditHandoff`、`AdapterReceipt`、`AdapterReadiness`、`AdapterGuard`、`SandboxConnection` 五个测试类。
- 共享的 `seedReleaseApprovalReplayApprovals()` 仍保留在 support，清理顺序也保持统一。

## 维护收益

- audit / adapter / sandbox connection 三个职责块不再堆在同一个类里。
- `AdapterReceipt` 再次被压到 89 行，说明最肥的一块已经被切成多个更小的局部测试。
- `AdapterGuard` 和 `SandboxConnection` 仍有后续再拆空间，但已经从“一个 400+ 行文件”变成可维护的职责块。
- 本版没有改生产行为，也没有引入 fake harness runtime、credential value/raw endpoint/managed audit/ledger/SQL 行为变化。

## 验证

```text
mvn -q -DskipTests test-compile
mvn -q "-Dtest=OpsReleaseApprovalRehearsalLiveAggregationIntegrationTests,OpsReleaseApprovalRehearsalAuditHandoffLiveAggregationIntegrationTests,OpsReleaseApprovalRehearsalAdapterReceiptLiveAggregationIntegrationTests,OpsReleaseApprovalRehearsalAdapterReadinessLiveAggregationIntegrationTests,OpsReleaseApprovalRehearsalAdapterGuardLiveAggregationIntegrationTests,OpsReleaseApprovalRehearsalSandboxConnectionLiveAggregationIntegrationTests" test
```
