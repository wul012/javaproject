# 157. Java v155：shard readiness frozen evidence index

## 背景

Node v376 已完成 Java v154 + mini-kv v145 shard readiness evidence consumption。Node 计划中特别强调：历史证据不能读取 rolling current 文件作为旧版本基线，旧版本必须使用冻结文件名。

Java v155 因此做一版证据索引，把 Java v153 core contract 和 Java v154 hardening 作为冻结来源列清楚。

## 改动入口

- `OpsShardReadinessEvidenceIndexService`：输出 Java v155 evidence index。
- `OpsShardReadinessEvidenceIndexResponse`：承载 required fields、frozen entries、fallback policy。
- `OpsShardReadinessController`：承接三个 shard readiness GET endpoint。
- `OpsOverviewController`：移除 shard readiness 依赖和路由，回到 overview/evidence/rehearsal 职责。
- `OpsEvidenceService`：登记 v155 endpoint 和 fixture。
- `java-shard-readiness-evidence-index-v155.fixture.json`：提供静态 fixture。

## 为什么这样拆

这版没有继续往 `OpsOverviewController` 加 endpoint，而是新建 `OpsShardReadinessController`。这样后续 shard readiness 继续加索引、兼容性报告或 live-read guard，也不会把 overview controller 变成难维护的入口杂烩。

`OpsEvidenceService` 仍然只追加只读列表登记，不承载 v155 输出逻辑。

## 核心字段

```text
version=Java v155
lastConsumedByNodeVersion=Node v376
requiredContractFields=project/version/readOnly/executionAllowed/shardEnabled/shardCount/slotCount/routingMode/status
evidenceEntries=Java v153, Java v154
rollingCurrentPointer=false
```

## 测试

- `OpsShardReadinessEvidenceIndexServiceTests`：验证 frozen entries、required fields、fallback policy。
- `OpsShardReadinessEvidenceIndexIntegrationTests`：验证 live endpoint 和 fixture。
- `OpsShardReadinessIntegrationTests` / `OpsShardReadinessHardeningIntegrationTests`：验证拆 controller 后旧 endpoint 保持可用。
- `OpsEvidenceServiceTests`：验证 ops evidence 已登记 v155 endpoint 和 fixture。

定向测试命令：

```text
mvn -q "-Dtest=OpsShardReadinessEvidenceIndexServiceTests,OpsShardReadinessEvidenceIndexIntegrationTests,OpsShardReadinessHardeningServiceTests,OpsShardReadinessHardeningIntegrationTests,OpsShardReadinessServiceTests,OpsShardReadinessIntegrationTests,OpsEvidenceServiceTests" test
```

定向测试和全量 `mvn -q test` 均已通过；测试期间仍会出现既有 Testcontainers Docker 探测警告，但 Maven 退出码为 0。
