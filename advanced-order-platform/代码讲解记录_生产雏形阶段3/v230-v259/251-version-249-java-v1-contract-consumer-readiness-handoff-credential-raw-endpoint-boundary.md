# 251. Java v249 v1 contract consumer readiness handoff credential/raw endpoint boundary

v249 把 credential 和 raw endpoint 两个高风险字段单独拆出来验证。

这版不是新增业务功能，而是明确禁止边界：
- 不读取 credential value；
- 不解析 raw endpoint；
- 这两项必须继续出现在 blocked operations 中。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffCredentialRawEndpointBoundaryTests,OpsShardReadinessV1ContractConsumerReadinessHandoffGetOnlyProbeBoundaryTests,OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogArchivePresenceTests" test
```
