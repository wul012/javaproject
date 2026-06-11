# 187. Java v185 v184 registry integrity snapshot freeze

## 目标

v184 新增了只读 endpoint registry integrity receipt，但它最初直接读取滚动的
`OpsShardReadinessEvidenceEndpoints`。这样后续版本如果继续增加只读 endpoint，v184 的
23/23/23 历史计数会跟着变化。v185 的目标是冻结 v184 证据，让后续版本可以继续推进而不冲坏旧 receipt。

## 代码变更

- 新增 `OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot`
  - 固定 v184 当时的 endpoint pair 清单。
  - 暴露 `v184EndpointPairs()`、`v184LiveEndpoints()`、`v184FixtureEndpoints()`。
- 更新 `OpsShardReadinessReadOnlyEndpointRegistryIntegrityService`
  - 改为从 v184 snapshot 读取计数和 distinct 校验输入。
  - 保持对外 HTTP contract、fixture path、receipt id、evidence path 不变。
- 新增 `OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshotTests`
  - 锁定 v184 snapshot 为 23 pair / 23 live / 23 fixture。
  - 确认 service 仍返回冻结后的 23/23/23 receipt。

## 边界

- 没有新增 controller 路由。
- 没有修改当前滚动 endpoint registry。
- 没有打开写路由、active shard router、credential 读取、raw endpoint parse、managed audit connection、deployment / rollback。
- 没有让 Node 启停 Java 或 mini-kv。
