# 156. Java v154：shard readiness hardening

## 背景

Node v374 是 v370-v373 shard readiness 证据链的 regular gate 收束版。Java 可以并行继续做 hardening，但不能回写或改动 Node 正在消费的归档证据链。

本版选择旁路新增，而不是修改 Java v153 的 `GET /api/v1/ops/shard-readiness`。

## 改动入口

- `OpsShardReadinessHardeningService`：构造 Java v154 hardening 证据。
- `OpsShardReadinessHardeningResponse`：承载字段解释、错误语义、兼容保证和禁止变更项。
- `OpsOverviewController`：新增 `GET /api/v1/ops/shard-readiness/hardening`。
- `OpsEvidenceService`：把 hardening endpoint 和 fixture 加入只读探针列表。
- `java-shard-readiness-hardening-v154.fixture.json`：提供静态 fixture。

## 为什么这样拆

v153 的核心 echo 已经被 Node v370-v373 归档链消费过，所以不适合在同一个响应里追加解释字段。v154 单独开 hardening endpoint，可以让后续 Node 版本消费新证据，同时保留 v153 十字段的稳定性。

新增逻辑没有塞进 `OpsEvidenceService`，只在它里面登记 endpoint。真正的 hardening 内容放到独立 service 和 record，避免主文件继续膨胀。

## 关键边界

- `readOnly=true`
- `executionAllowed=false`
- `sourceEvidenceVersion=Java v153`
- `v153-shard-readiness-core-fields-unchanged`
- `v370-v373-node-archive-chain-not-mutated`
- `hardening-output-is-additive-sibling-evidence`

## 测试

- `OpsShardReadinessHardeningServiceTests`：验证 service 输出字段解释、错误语义和兼容保证。
- `OpsShardReadinessHardeningIntegrationTests`：验证 live endpoint 和 static fixture。
- `OpsEvidenceServiceTests`：验证 ops evidence 已登记 v154 hardening endpoint 和 fixture。

定向测试命令：

```text
mvn -q "-Dtest=OpsShardReadinessHardeningServiceTests,OpsShardReadinessHardeningIntegrationTests,OpsShardReadinessServiceTests,OpsShardReadinessIntegrationTests,OpsEvidenceServiceTests" test
```

定向测试和全量 `mvn -q test` 均已通过；测试期间仍会出现既有 Testcontainers Docker 探测警告，但 Maven 退出码为 0。
