# 257. Java v255 v1 contract consumer readiness handoff fixture contract boundary

v255 验证 fixture contract 不随 post-handoff 验证版本漂移。

测试确认 handoff response 暴露的 fixture endpoint 仍然是 v225，并且对应静态 fixture 文件存在。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffFixtureContractBoundaryTests,OpsShardReadinessV1ContractConsumerReadinessHandoffReadOnlyAdjacencyTests,OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogArchivePresenceTests" test
```
