> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 256. Java v254 v1 contract consumer readiness handoff read-only adjacency

v254 验证 read-only catalog 的位置。

滚动证据链中，readiness handoff 后面继续接 read-only catalog 是合理的。
但 frozen v1 consumer endpoint registry 不能因此扩展到 read-only catalog。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffReadOnlyAdjacencyTests,OpsShardReadinessV1ContractConsumerReadinessHandoffConsumerBoundaryCompletionTests,OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogArchivePresenceTests" test
```
