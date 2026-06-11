# 244. Java v242 v1 contract consumer readiness handoff catalog continuity

v242 是 v241 catalog 的连续性验证版。

新增测试 `OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogContinuityTests`，它不关心业务 payload 的内容，而是验证证据 catalog 本身是否可信。

## 为什么要做连续性

现在 v226 之后的验证 receipt 已经形成一条长链。
如果某个版本忘记加入 catalog，后续归档存在性测试和 README 索引测试都会少看一段历史。
如果路径重复，则两个版本会共享一份 evidence，追溯时会混乱。
如果路径版本不匹配，例如 v242 常量指向 `e/241/...v242.json`，也会让归档结构变得不可审计。

v242 把这些问题提前用单元测试锁住。

## 验证内容

测试确认：
- `versions()` 正好等于 `226..242`；
- `receipts()` 没有重复；
- `evidencePaths()` 没有重复；
- 每条 path 都包含 `/<version>/`；
- 每条 path 都以 `-v<version>.json` 结尾。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogContinuityTests,OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffCatalogTests" test
```
