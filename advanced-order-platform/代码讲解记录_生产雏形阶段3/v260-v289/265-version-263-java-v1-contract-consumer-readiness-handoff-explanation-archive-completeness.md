# 265. Java v263 v1 contract consumer readiness handoff explanation archive completeness

v263 把每个版本的 `解释/说明.md` 纳入非空校验。

测试遍历 post-handoff catalog，检查对应说明文件大小大于 20 字节。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffExplanationArchiveCompletenessTests,OpsShardReadinessV1ContractConsumerReadinessHandoffArchiveSlugParityTests,OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogArchivePresenceTests" test
```
