> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 245. Java v243 v1 contract consumer readiness handoff catalog archive presence

v243 的目标是把 post-handoff catalog 和实际归档文件绑定起来。

v241 解决了“路径列表在哪里维护”的问题。
v242 解决了“版本是否连续、路径是否唯一”的问题。
v243 则继续追问：catalog 里的每条路径，仓库里是否真的有完整证据归档？

## 新增测试

`OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogArchivePresenceTests` 会遍历 catalog 的每个 receipt，并推导五类文件：
- `e/<version>/evidence/<slug>-v<version>.json`；
- `e/<version>/evidence/<slug>-v<version>-browser-snapshot.md`；
- `e/<version>/<slug>-v<version>.html`；
- `e/<version>/图片/<slug>-v<version>.png`；
- `e/<version>/解释/说明.md`。

只要其中任何一个缺失，测试就会给出具体文件路径。

## 为什么重要

持续推进多版本时，最容易出现的不是 Java 代码逻辑错误，而是 evidence 归档不完整。
v243 用 catalog-driven archive check 把这类问题前置到单元测试里。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogArchivePresenceTests,OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogContinuityTests" test
```
