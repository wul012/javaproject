# 253. Java v251 v1 contract consumer readiness handoff process-control boundary

v251 锁住进程控制边界。

当前 readiness handoff 只是只读交接，不负责启动 Java、启动 mini-kv，也不授权 Node 去启停这些服务。

测试确认 response 字段、blocked operations、handoff checks 三处一致表达这个边界。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffProcessControlBoundaryTests,OpsShardReadinessV1ContractConsumerReadinessHandoffAuditDeploymentBoundaryTests,OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogArchivePresenceTests" test
```
