# 252. Java v250 v1 contract consumer readiness handoff audit/deployment boundary

v250 单独锁住 managed audit connection 和 deployment/rollback。

这版测试聚焦 readiness handoff response 自身：
- 不允许 managed audit connection；
- 不允许 deployment or rollback；
- 两个动作必须继续列在 blocked operations 中。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffAuditDeploymentBoundaryTests,OpsShardReadinessV1ContractConsumerReadinessHandoffCredentialRawEndpointBoundaryTests,OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogArchivePresenceTests" test
```
