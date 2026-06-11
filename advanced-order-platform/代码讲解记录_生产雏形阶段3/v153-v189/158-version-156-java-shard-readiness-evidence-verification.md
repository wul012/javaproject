> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 158. Java v156：shard readiness evidence verification

## 背景

Node v376 计划强调：历史证据不能读取 rolling current 文件作为旧版本基线。Java v155 已经把 v153 core contract 和 v154 hardening 做成冻结索引；v156 再补一个验证回执，给后续 Node 消费时直接判断这组 Java 证据是否仍然安全。

## 改动入口

- `OpsShardReadinessEvidenceVerificationService`：基于 Java v155 index 生成验证回执。
- `OpsShardReadinessEvidenceVerificationResponse`：承载 verified entries、checks、fallback policy。
- `OpsShardReadinessController`：新增 `GET /api/v1/ops/shard-readiness/evidence-verification`。
- `OpsShardReadinessEvidenceEndpoints`：集中维护 shard readiness live endpoint / fixture endpoint / GET probe endpoint。
- `OpsEvidenceService`：改为调用 helper，不再重复逐项硬编码 shard readiness endpoint。
- `java-shard-readiness-evidence-verification-v156.fixture.json`：提供静态 fixture。

## 验证项

```text
index-read-only-and-non-executable
required-contract-fields-covered
source-entry-count
all-sources-frozen
no-rolling-current-pointer
versioned-fixture-endpoints
versioned-archive-paths
node-archive-mutation-forbidden
```

## 为什么这样拆

继续往 `OpsEvidenceService` 追加 endpoint 常量会让主服务越来越像配置杂物间。本版把 shard readiness 的 endpoint 分组挪到 `OpsShardReadinessEvidenceEndpoints`，以后新增 Java v157/v158 证据时只改一个小 helper，并由 `OpsShardReadinessEvidenceEndpointsTests` 保证顺序。

## 边界

- 不修改 Java v153 / v154 / v155 输出。
- 不修改 Node v370-v376 归档。
- 不启用真实 shard 路由。
- 不改订单、支付、库存、ledger 或 SQL。

## 测试

- `OpsShardReadinessEvidenceVerificationServiceTests`：验证 v156 回执的检查项。
- `OpsShardReadinessEvidenceVerificationIntegrationTests`：验证 live endpoint 和 static fixture。
- `OpsShardReadinessEvidenceEndpointsTests`：验证 helper 输出顺序。
- `OpsEvidenceServiceTests`：验证 ops evidence 登记 v156 endpoint 和 fixture。

定向测试命令：

```text
mvn -q "-Dtest=OpsShardReadinessEvidenceVerificationServiceTests,OpsShardReadinessEvidenceVerificationIntegrationTests,OpsShardReadinessEvidenceEndpointsTests,OpsShardReadinessEvidenceIndexServiceTests,OpsShardReadinessEvidenceIndexIntegrationTests,OpsShardReadinessHardeningServiceTests,OpsShardReadinessHardeningIntegrationTests,OpsShardReadinessServiceTests,OpsShardReadinessIntegrationTests,OpsEvidenceServiceTests" test
```

定向测试和全量 `mvn -q test` 均已通过；测试期间仍会出现既有 Testcontainers Docker 探测警告，但 Maven 退出码为 0。
