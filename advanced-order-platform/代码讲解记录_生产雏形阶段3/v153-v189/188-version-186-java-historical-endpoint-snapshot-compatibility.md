> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 188. Java v186 historical endpoint snapshot compatibility

## 目标

v183 和 v185 分别冻结了 v179、v184 的历史 endpoint 证据。v186 补一层兼容性守护：
当前滚动 registry 继续覆盖这些历史冻结快照，避免后续版本误删旧 endpoint 或 fixture 时没有测试拦截。

## 代码变更

- 新增 `OpsShardReadinessHistoricalEndpointSnapshotCompatibilityTests`
  - 校验 `OpsShardReadinessEvidenceEndpoints.liveEndpoints()` 覆盖 v179 和 v184 live snapshot。
  - 校验 `OpsShardReadinessEvidenceEndpoints.fixtureEndpoints()` 覆盖 v179 和 v184 fixture snapshot。
  - 校验 v184 snapshot 包含 v179 snapshot。
  - 校验 v179 snapshot 不包含 v184 integrity endpoint，防止旧 receipt 被反向污染。

## 边界

- 没有新增生产代码。
- 没有新增 controller route。
- 没有修改 rolling registry。
- 没有打开写路由、active shard router、credential 读取、raw endpoint parse、managed audit connection、deployment / rollback。
- 没有让 Node 启停 Java 或 mini-kv。
