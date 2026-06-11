> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 212. Java v210 v208 endpoint catalog historical compatibility

本版新增 `OpsShardReadinessV1ContractEndpointCatalogHistoricalCompatibilityTests`。

测试覆盖两个方向：

- v208 catalog snapshot 本身只包含六个 v1 contract artifact，不自包含 endpoint catalog route，也不混入 read-only evidence catalog；
- 当前 rolling registry 能发现 endpoint catalog，并确认它排在 consumer-probe-plan 后、read-only evidence catalog 前。

这让后续继续扩展 v1 contract catalog 时，有明确边界：历史 artifact 不回填，rolling registry 可以继续向前滚动。
