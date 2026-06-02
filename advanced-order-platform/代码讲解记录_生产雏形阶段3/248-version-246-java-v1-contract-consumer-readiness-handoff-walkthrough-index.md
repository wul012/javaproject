# 248. Java v246 v1 contract consumer readiness handoff walkthrough index

v246 是第一组 v240-v246 的收口版。

它新增 `OpsShardReadinessV1ContractConsumerReadinessHandoffWalkthroughIndexTests`，把代码讲解记录纳入 catalog 驱动验证。

## 检查方式

测试读取目录：

```text
代码讲解记录_生产雏形阶段3
```

然后遍历 post-handoff catalog 的每个 receipt，要求至少存在一个文件名包含：

```text
version-<version>-
```

例如 v246 必须有 `version-246-...md`。

## 为什么这版重要

持续推进多版本时，证据归档和代码讲解很容易不同步。
v246 让讲解归档变成自动检查项：
- 代码和测试推进；
- evidence JSON/HTML/截图推进；
- README 推进；
- 代码讲解也必须推进。

这让 v240-v246 的第一组 guard 形成闭环。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffWalkthroughIndexTests,OpsShardReadinessV1ContractConsumerReadinessHandoffReadmeIndexTests,OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogJsonBoundaryTests,OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogArchivePresenceTests" test
```
