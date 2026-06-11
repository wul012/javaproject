# 250. Java v248 v1 contract consumer readiness handoff GET-only probe boundary

v248 继续加固消费边界。

新增测试确认 readiness handoff 相关消费链保持 GET-only：
- checklist；
- evidence digest；
- readiness handoff；
- rolling evidence live/fixture probe endpoint strings。

这个测试的价值是把“只读消费”落实到方法语义。
即使未来有人把字段仍写成 readOnly，但在 probe 列表中加入 POST 或 DELETE，也会被这版测试拦住。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffGetOnlyProbeBoundaryTests,OpsShardReadinessV1ContractConsumerReadinessHandoffBlockedOperationCatalogTests,OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogArchivePresenceTests" test
```
