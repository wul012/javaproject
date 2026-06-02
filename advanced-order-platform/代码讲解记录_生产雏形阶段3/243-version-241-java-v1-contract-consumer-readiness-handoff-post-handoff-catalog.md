# 243. Java v241 v1 contract consumer readiness handoff post-handoff catalog

v241 是一次结构拆分版。

从 v226 到 v240，readiness handoff 周围已经产生了 snapshot freeze、historical compatibility、integrity、route inventory、evidence chain、controller mapping、artifact presence、completion、legacy registry alignment 等多类 receipt。

如果后续每个测试都自己维护一份路径列表，维护成本会越来越高。
v241 因此新增 `OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog`。

## 新增结构

catalog 暴露三个入口：
- `receipts()`：返回 version、scope、evidencePath 的 record 列表；
- `versions()`：只返回版本号列表；
- `evidencePaths()`：只返回 evidence path 列表。

这里刻意没有把 catalog 做成 Spring bean。
原因是它不是运行时服务，而是同 package 下的静态证据索引，主要服务于测试和后续证据闭环。

## 测试重点

`OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffCatalogTests` 验证：
- catalog 覆盖 v226-v241；
- 一共 16 条 receipt；
- v240 legacy registry alignment 和 v241 自身都已被记录；
- 所有 post-handoff evidence path 都没有进入 frozen v225 的 `digestEvidence`；
- 所有 post-handoff evidence path 都没有进入 frozen v225 的 `handoffGuardEvidence`。

这可以防止后续版本无意把“验证 v225 的证据”写回“v225 当时发布的合同 payload”。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffCatalogTests,OpsShardReadinessV1ContractConsumerReadinessHandoffLegacyRegistryAlignmentTests" test
```
