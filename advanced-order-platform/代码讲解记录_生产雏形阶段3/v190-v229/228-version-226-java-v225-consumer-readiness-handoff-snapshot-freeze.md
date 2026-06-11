> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 228. Java v226 v225 consumer readiness handoff snapshot freeze

本版的核心是“把已经发布的 v225 handoff 固化成 snapshot”。v225 新增了面向下游消费者的 readiness
handoff endpoint；v226 不扩大接口，而是整理内部结构，让这份 handoff 以后被测试和后续证据版本反复引用时，
不会依赖 service 内部重新拼装。

## 改动内容

- 新增 `OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot`。
- `v225Handoff()` 负责构造 Java v225 handoff response。
- `v225HandoffGuardEvidence()` 固定 v221-v224 四条 digest guard evidence。
- `v225HandoffChecks(...)` 固定由 Java v220 digest 派生出的检查项。
- `OpsShardReadinessV1ContractConsumerReadinessHandoffService` 变薄，只保留只读事务入口和证据常量。
- 新增 `OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshotTests`。

## 为什么要拆

service 的职责是对外暴露只读业务入口；snapshot 的职责是保存已经发布的历史合同。如果把构造逻辑继续留在
service 里，后续 v227、v228 这类历史兼容和完整性测试就只能通过 service 间接理解 v225 合同。拆出 snapshot
以后，测试可以明确表达：“这里验证的是 frozen v225 handoff 本身”，语义更清楚，也更不容易被未来 service
实现细节影响。

## 冻结了哪些字段

v226 冻结的是完整 handoff 形状，而不是只冻结 receipt 字符串：

- 项目、版本、合同名；
- readiness handoff live endpoint 和 fixture endpoint；
- upstream Java v220 digest endpoint、fixture、evidence path、receipt；
- digest evidence 的 5 条 v215-v219 输入证据；
- handoff guard evidence 的 4 条 v221-v224 防回填证据；
- digest 派生出的检查项；
- 所有执行边界布尔值和 blocked operations；
- v225 自身 receipt、evidence path 和状态。

## 边界说明

本版没有新增 controller route，没有新增 live endpoint，也没有改变 endpoint pair 数量。它只改变内部代码组织和测试证据。
所有 runtime 边界仍然保持 false：不启用写路由、不启用 active shard router、不读取 credential value、不解析 raw endpoint、
不创建 managed audit connection、不触发 deployment/rollback，也不允许 Node 启停 Java 或 mini-kv。

## 测试证据

focused test 覆盖三层：

- snapshot 自身是否稳定；
- service 是否返回 frozen snapshot；
- 既有 service 和 integration 行为是否仍保持 v225 输出。

验证命令：

```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshotTests,OpsShardReadinessV1ContractConsumerReadinessHandoffServiceTests,OpsShardReadinessV1ContractConsumerReadinessHandoffIntegrationTests" test
```

结果：通过。
