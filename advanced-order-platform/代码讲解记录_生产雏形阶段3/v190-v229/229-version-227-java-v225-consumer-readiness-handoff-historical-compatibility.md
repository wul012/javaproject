> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 229. Java v227 v225 consumer readiness handoff historical compatibility

v227 是 v225 readiness handoff 的历史兼容护栏。它解决的问题是：新版本发布之后，旧 snapshot 是否还保持旧样子。

## 为什么要做历史兼容

Java 的 shard readiness 证据一直采用“版本化证据 + frozen snapshot”的方式推进。这样每个版本都能回答两个问题：

- 当前滚动 registry 里有哪些 endpoint；
- 某个历史版本发布时，它当时承诺的 endpoint 和证据链是什么。

如果后续新增 endpoint 后，把它回填到 v179 或 v184 这类旧 snapshot 里，历史证据就会变得不可信。v227 明确锁住这个边界：
v225 readiness handoff 可以进入当前滚动 registry，但不能污染旧 snapshot。

## 测试结构

`OpsShardReadinessV1ContractConsumerReadinessHandoffHistoricalCompatibilityTests` 包含三组判断。

第一组看旧 snapshot：

- `v179LiveEndpoints()` 不包含 readiness handoff live endpoint；
- `v184LiveEndpoints()` 不包含 readiness handoff live endpoint；
- 对应 fixture endpoint 也同样不出现。

第二组看 frozen v225 handoff：

- handoff 的 guard evidence 包含 v221-v224；
- handoff 的 guard evidence 不包含 v226 和 v227；
- handoff 自己的 evidence path 仍指向 `e/225/...`。

第三组看当前 rolling registry：

- live endpoint 顺序包含 `evidence digest -> readiness handoff -> read-only catalog`；
- fixture endpoint 也保持相同相对顺序。

## 设计边界

v227 没有新增 endpoint，也没有修改 v225 response schema。它只增加测试和证据常量。

这点很重要：历史兼容版本如果顺手改 schema，会让“兼容性验证”变成“行为变更”。本版保持克制，只证明：

- 旧历史不被回填；
- 当前 registry 位置正确；
- frozen v225 不吃掉未来 guard evidence。

## 测试证据

验证命令：

```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffHistoricalCompatibilityTests,OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshotTests,OpsShardReadinessEvidenceEndpointsTests" test
```

结果：通过。
