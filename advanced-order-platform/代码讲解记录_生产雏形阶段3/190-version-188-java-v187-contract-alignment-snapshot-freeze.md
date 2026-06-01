# 190. Java v188 v187 contract alignment snapshot freeze

## 目标

v187 新增了 `shard-readiness.v1` contract alignment receipt。v188 冻结这个 receipt 的输入源，
避免后续 root readiness 或 contract helper 演进时改写 v187 的历史证据。

## 代码变更

- 新增 `OpsShardReadinessV1ContractAlignmentSnapshot`
  - 固定 contract name。
  - 固定 Java v153 root readiness 字段。
  - 固定 v187 使用的 10 个最小字段。
- 更新 `OpsShardReadinessV1ContractAlignmentService`
  - 不再读取滚动 `OpsShardReadinessService.readiness()`。
  - 改为读取 v187 frozen snapshot。
- 新增 `OpsShardReadinessV1ContractAlignmentSnapshotTests`
  - 锁定 snapshot 内容。
  - 确认 service 读取冻结输入并保持 v187 receipt。

## 边界

- 没有新增路由。
- 没有修改 endpoint registry。
- 没有打开写路由、active shard router、credential 读取、raw endpoint parse、managed audit connection、deployment / rollback。
- 没有让 Node 启停 Java 或 mini-kv。
