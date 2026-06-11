# 262. Java v260 v1 contract consumer readiness handoff evidence scope summary

v260 是新一轮十五版的第一版，目标是提升 evidence JSON 的语义完整性。

新增 `OpsShardReadinessV1ContractConsumerReadinessHandoffEvidenceScopeSummaryTests`，遍历 post-handoff catalog 中的每个 JSON，确认：
- `scope` 包含 readiness handoff；
- `summary` 包含 readiness handoff。

这可以防止归档文件虽然存在，但人工阅读时无法判断它属于哪条合同链。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffEvidenceScopeSummaryTests,OpsShardReadinessV1ContractConsumerReadinessHandoffTwentyVersionCompletionTests,OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogArchivePresenceTests" test
```
