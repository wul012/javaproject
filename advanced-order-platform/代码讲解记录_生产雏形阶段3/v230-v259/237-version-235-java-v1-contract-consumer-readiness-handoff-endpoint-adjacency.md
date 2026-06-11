> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 237. Java v235 v1 contract consumer readiness handoff endpoint adjacency

v235 是 endpoint adjacency guard，专门验证 readiness handoff 在 endpoint 链里的位置。

## 关键顺序

readiness handoff 的位置有两层含义：

- 在 v1 consumer pair 内，它应该直接跟在 evidence digest 后面；
- 在全局 rolling evidence registry 中，它应该位于 consumer digest 之后、read-only catalog 之前。

也就是说，下游消费者看到的顺序应该是：

```text
endpoint catalog -> consumer handoff bundle -> checklist -> digest -> readiness handoff -> read-only catalog
```

## 测试内容

新增 `OpsShardReadinessV1ContractConsumerReadinessHandoffEndpointAdjacencyTests`：

- live endpoint 中 handoff index 等于 digest index + 1；
- fixture endpoint 中 handoff fixture index 等于 digest fixture index + 1；
- rolling registry 包含 catalog -> bundle -> checklist -> digest -> handoff -> read-only catalog 的 subsequence；
- readiness handoff 是 v1 contract endpoint pairs 的最后一项。

## 为什么这比 contains 更严格

仅仅 `contains` 只能证明 endpoint 存在，不能证明它在正确的位置。adjacency 测试能发现“新 endpoint 插到了 digest 和 handoff 中间”这种问题。

## 测试证据

验证命令：

```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffEndpointAdjacencyTests,OpsShardReadinessV1ContractEndpointPairsTests,OpsShardReadinessEvidenceEndpointsTests" test
```

结果：通过。
