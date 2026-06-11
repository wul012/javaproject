# 249. Java v247 v1 contract consumer readiness handoff blocked operation catalog

v247 进入第二组 guard：运行边界和消费边界。

新增 `OpsShardReadinessV1ContractConsumerReadinessHandoffBlockedOperationCatalogTests`，把七个禁止操作抽成测试内的明确 catalog，并确认 checklist、digest、handoff 三层完全一致。

这版的重点是防止某一层未来误删一个 blocked operation。
如果 handoff 少了 `credential-value-read` 或 `node-start-or-stop-java-or-mini-kv`，测试会直接失败。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffBlockedOperationCatalogTests,OpsShardReadinessV1ContractConsumerReadinessHandoffWalkthroughIndexTests,OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogArchivePresenceTests" test
```
