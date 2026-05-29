# 159. Java v157：completed shard readiness evidence handoff

## 背景

Node v378 已完成 Java v156/v155 + mini-kv v146 completed shard-readiness evidence intake。Java v157 不做 live read，也不进入 active sharding，而是补一份 Java 侧 handoff，说明哪些 Java 证据已经完成、可冻结、可被后续 Node 版本消费。

## 改动入口

- `OpsShardReadinessEvidenceHandoffService`：组合 Java v155 index 和 Java v156 verification。
- `OpsShardReadinessEvidenceHandoffResponse`：承载 completed evidence、handoff artifacts、consumer rules、stop conditions。
- `OpsShardReadinessController`：新增 `GET /api/v1/ops/shard-readiness/evidence-handoff`。
- `OpsShardReadinessEvidenceEndpoints`：登记 v157 live endpoint 和 fixture endpoint。
- `java-shard-readiness-evidence-handoff-v157.fixture.json`：提供静态 fixture。

## 核心输出

```text
version=Java v157
sourceIndexVersion=Java v155
sourceVerificationVersion=Java v156
lastConsumedByNodeVersion=Node v378
completedEvidenceVersions=Java v155, Java v156
status=passed
```

## 消费边界

- 只消费已完成、已提交/tag 的 Java 证据。
- 使用 versioned fixture 和 archive path。
- 不读 rolling current 作为历史基线。
- active sharding 仍然 disabled。
- Node evidence consumption 不启动或停止 Java。

## 测试

- `OpsShardReadinessEvidenceHandoffServiceTests`：验证 handoff 内容。
- `OpsShardReadinessEvidenceHandoffIntegrationTests`：验证 live endpoint 和 fixture。
- `OpsShardReadinessEvidenceEndpointsTests`：验证 endpoint helper 顺序。
- `OpsEvidenceServiceTests`：验证 ops evidence 登记 v157 endpoint 和 fixture。

定向测试命令：

```text
mvn -q "-Dtest=OpsShardReadinessEvidenceHandoffServiceTests,OpsShardReadinessEvidenceHandoffIntegrationTests,OpsShardReadinessEvidenceVerificationServiceTests,OpsShardReadinessEvidenceVerificationIntegrationTests,OpsShardReadinessEvidenceEndpointsTests,OpsShardReadinessEvidenceIndexServiceTests,OpsShardReadinessEvidenceIndexIntegrationTests,OpsShardReadinessHardeningServiceTests,OpsShardReadinessHardeningIntegrationTests,OpsShardReadinessServiceTests,OpsShardReadinessIntegrationTests,OpsEvidenceServiceTests" test
```

定向测试和全量 `mvn -q test` 均已通过；测试期间仍会出现既有 Testcontainers Docker 探测警告，但 Maven 退出码为 0。
