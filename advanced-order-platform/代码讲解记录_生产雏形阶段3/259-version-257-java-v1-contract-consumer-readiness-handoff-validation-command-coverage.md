# 259. Java v257 v1 contract consumer readiness handoff validation command coverage

v257 把 validation 字段纳入测试。

测试解析 catalog 中每个 evidence JSON，要求 validation 数组同时包含 Maven 和 Playwright 证据描述。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffValidationCommandCoverageTests,OpsShardReadinessV1ContractConsumerReadinessHandoffReceiptIdUniquenessTests,OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogArchivePresenceTests" test
```
