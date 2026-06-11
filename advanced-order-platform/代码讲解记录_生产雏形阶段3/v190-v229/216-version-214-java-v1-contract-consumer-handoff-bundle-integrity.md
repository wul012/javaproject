# 216. Java v214 v1 contract consumer handoff bundle integrity

本版新增 `OpsShardReadinessV1ContractConsumerHandoffBundleIntegrityTests`。

测试将三条线合在一起校验：

- `OpsShardReadinessV1ContractEndpointPairs` 的 v1 group 保持 8 个 pair，并排除 read-only/runtime execution group；
- v211 bundle 的 catalogedArtifactCount、read targets、fixture targets 与 v208 catalog snapshot 一致；
- requiredEvidence 不包含 v211 bundle 自身 evidence，自身 evidence 只出现在 handoffEvidence 中。

这层守卫用于防止后续扩展 v1 contract handoff 时出现 registry 顺序漂移、scope 混入或 evidence 自引用位置错误。
