# 254. Java v252 v1 contract consumer readiness handoff write/router boundary

v252 把 write routing、active shard router 和 shard enablement 单独检查。

这些能力属于后续执行或路由阶段，当前只读 handoff 不提前打开。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffWriteRouterBoundaryTests,OpsShardReadinessV1ContractConsumerReadinessHandoffProcessControlBoundaryTests,OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogArchivePresenceTests" test
```
