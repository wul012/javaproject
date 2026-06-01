# 238. Java v236 v1 contract consumer readiness handoff receipt uniqueness

v236 是 receipt uniqueness guard。它把 consumer readiness 链上的主要版本串起来，检查 receipt 和 evidence path 是否唯一。

## 为什么 receipt 需要唯一

receipt 是证据交接的身份标识。如果两个版本共享同一个 receipt，下游消费者在做审计或回放时就很难判断到底消费的是哪一版。

本版检查四个节点：

- Java v211 consumer handoff bundle；
- Java v215 consumer verification checklist；
- Java v220 consumer evidence digest；
- Java v225 consumer readiness handoff。

## 测试内容

新增 `OpsShardReadinessV1ContractConsumerReadinessHandoffReceiptUniquenessTests`：

- 收集四层 receipt id；
- 断言没有重复；
- 断言每个 receipt 都符合 `java-shard-readiness-...-receipt-v...` 形式；
- 收集四层 evidence path；
- 断言路径没有重复并且精确等于各自版本目录；
- 单独验证 v225 handoff receipt 和 evidence path 与 service 常量对齐。

## 工程价值

这个 guard 防的是归档类错误：代码行为可能没坏，但证据身份混了。对于持续推进多版本来说，这类检查很值钱。

## 测试证据

验证命令：

```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffReceiptUniquenessTests,OpsShardReadinessV1ContractConsumerReadinessHandoffEvidenceChainTests,OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshotTests" test
```

结果：通过。
