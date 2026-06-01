# 214. Java v212 v211 consumer handoff bundle snapshot freeze

本版新增 `OpsShardReadinessV1ContractConsumerHandoffBundleSnapshot`。

`OpsShardReadinessV1ContractConsumerHandoffBundleService.bundle()` 现在直接返回 `v211Bundle()`，把 v211 handoff bundle 的历史 receipt 固化下来。

测试 `OpsShardReadinessV1ContractConsumerHandoffBundleSnapshotTests` 重点确认：

- version 固定为 `Java v211`；
- endpoint catalog receipt 固定引用 v208；
- requiredEvidence 与 handoffEvidence 由 snapshot helper 提供；
- receiptId 与 evidencePath 不漂移。
