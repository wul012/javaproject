# 215. Java v213 v211 consumer handoff bundle historical compatibility

本版新增 `OpsShardReadinessV1ContractConsumerHandoffBundleHistoricalCompatibilityTests`。

测试关注三件事：

- v208 endpoint catalog 不被回填 v211 bundle endpoint；
- v179/v184 旧 registry snapshot 不被回填；
- v211 requiredEvidence 不包含 v212 snapshot freeze evidence。

这和前面的 snapshot freeze 配合，形成“rolling registry 可以前进，历史 receipt 不回写”的稳定边界。
