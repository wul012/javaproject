# 213. Java v211 shard readiness v1 contract consumer handoff bundle

本版新增 `OpsShardReadinessV1ContractConsumerHandoffBundleService` 与 response。

bundle 从 `OpsShardReadinessV1ContractEndpointCatalogSnapshot.v208Catalog()` 读取 catalog 信息，并额外串联：

- v208 endpoint catalog evidence；
- v209 endpoint catalog snapshot freeze evidence；
- v210 endpoint catalog historical compatibility evidence；
- v211 bundle 自身 evidence。

它被加入 `OpsShardReadinessV1ContractController` 和 `OpsShardReadinessV1ContractEndpointPairs`，因此也进入 `OpsShardReadinessEvidenceEndpoints` 与 `OpsEvidenceService` 的只读探针列表。测试覆盖 service、MockMvc、registry exact order、historical snapshot 不回填。
