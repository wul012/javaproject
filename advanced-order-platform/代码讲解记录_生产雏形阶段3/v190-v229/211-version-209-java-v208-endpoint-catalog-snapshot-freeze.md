# 211. Java v209 v208 endpoint catalog snapshot freeze

本版新增 `OpsShardReadinessV1ContractEndpointCatalogSnapshot`，把 v208 catalog response 的构造集中到 `v208Catalog()`。

`OpsShardReadinessV1ContractEndpointCatalogService.catalog()` 改为直接返回该 snapshot。这样 service 保持薄，snapshot 成为历史 receipt 的稳定来源。

`OpsShardReadinessV1ContractEndpointCatalogSnapshotTests` 验证：

- version 固定为 `Java v208`；
- artifact count 固定为 6；
- endpoint entries 来自 `v208EndpointEntries()`；
- blocked operations 来自 `v208BlockedOperations()`；
- receiptId 和 evidencePath 不漂移。
