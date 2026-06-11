# 155. Java v153：shard readiness echo

## 背景

Node v369 已冻结 `shard-readiness.v1`，并明确 Java / mini-kv 可以并行产出只读 shard readiness 证据。Java v153 只做 echo，不抢改订单事务、支付、库存、ledger 或 SQL。

## 改动入口

- `OpsOverviewController` 新增 `GET /api/v1/ops/shard-readiness`。
- `OpsShardReadinessService` 固定输出 Node v369 要求的 10 个字段。
- `OpsShardReadinessResponse` 保持纯 record，只承载契约字段。
- `java-shard-readiness-v153.fixture.json` 提供 fixture-first 静态证据。

## 字段含义

```text
project: advanced-order-platform
version: Java v153
readOnly: true
executionAllowed: false
shardEnabled: false
shardCount: 0
slotCount: 0
routingMode: fixture
evidencePath: e/153/evidence/java-shard-readiness-v153.json
status: passed
```

`shardEnabled=false` 是有意的：本版只是 shard readiness echo，不启用真实分片路由。`routingMode=fixture` 对齐 Node v369 冻结的允许值，给 Node v370 做 contract consumer gate。

## 为什么这样拆

这版没有继续膨胀 `OpsEvidenceService`，只是把新 endpoint 登记到既有只读 evidence 清单。真正的输出逻辑放在 29 行的 `OpsShardReadinessService`，后续如果需要 live readiness 再沿这个 service 扩展。

## 测试

- `OpsShardReadinessServiceTests`：校验 service 输出的 10 个契约字段。
- `OpsShardReadinessIntegrationTests`：校验 live endpoint 和 static fixture。
- `OpsEvidenceServiceTests`：校验 ops evidence 已登记 shard readiness endpoint / fixture。

全量 `mvn -q test` 已通过。
