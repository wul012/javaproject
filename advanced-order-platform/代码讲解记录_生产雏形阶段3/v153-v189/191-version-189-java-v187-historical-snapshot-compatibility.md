> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 191. Java v189 v187 historical snapshot compatibility

## 目标

v187 新增了 `shard-readiness.v1` contract alignment endpoint，v188 又冻结了它的输入源。
v189 补一层兼容性守护：当前 rolling registry 必须覆盖 v187，但 v179/v184 旧快照不能被 v187 反向污染。

## 代码变更

- 更新 `OpsShardReadinessHistoricalEndpointSnapshotCompatibilityTests`
  - rolling live registry 至少 24 个 endpoint，并包含 v187 contract alignment endpoint。
  - rolling fixture registry 至少 24 个 fixture，并包含 v187 fixture。
  - v179/v184 frozen snapshots 不包含 v187 endpoint / fixture。
  - v187 alignment snapshot 仍指向 root readiness，并固定 10 个最小字段。

## 边界

- 没有新增生产代码。
- 没有新增路由。
- 没有打开写路由、active shard router、credential 读取、raw endpoint parse、managed audit connection、deployment / rollback。
- 没有让 Node 启停 Java 或 mini-kv。
