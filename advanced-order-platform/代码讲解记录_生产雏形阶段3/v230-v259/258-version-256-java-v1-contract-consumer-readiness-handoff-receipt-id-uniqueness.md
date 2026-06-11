# 258. Java v256 v1 contract consumer readiness handoff receipt id uniqueness

v256 把 evidence JSON 的 receiptId 纳入测试。

测试遍历 post-handoff catalog，读取每个 JSON 的 `receiptId`：
- 要求以 `-v<version>` 结尾；
- 要求所有 receiptId 不重复。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffReceiptIdUniquenessTests,OpsShardReadinessV1ContractConsumerReadinessHandoffFixtureContractBoundaryTests,OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogArchivePresenceTests" test
```
