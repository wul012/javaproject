> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 189. Java v187 shard-readiness.v1 contract alignment

## 目标

plans3 已经冻结 `shard-readiness.v1` 的最小字段。v187 在 Java 侧新增一个只读
contract alignment receipt，证明当前 root readiness 输出仍满足这个最小契约。

## 代码变更

- 新增 `OpsShardReadinessV1Contract`
  - 固定 `shard-readiness.v1` 名称。
  - 固定 10 个最小字段。
  - 提供 root readiness 的只读契约校验。
- 新增 `OpsShardReadinessV1ContractAlignmentService` 和 response record。
- `OpsShardReadinessController` 增加只读 GET `/shard-readiness/v1-contract-alignment`。
- `OpsShardReadinessEvidenceEndpoints` 增加 v187 live / fixture pair。
- `OpsEvidenceServiceTests` 和 endpoint registry 测试同步到 24 pair。

## 边界

- 没有修改 root `/api/v1/ops/shard-readiness` 的 Java v153 schema。
- 没有打开写路由、active shard router、credential 读取、raw endpoint parse、managed audit connection、deployment / rollback。
- 没有让 Node 启停 Java 或 mini-kv。
