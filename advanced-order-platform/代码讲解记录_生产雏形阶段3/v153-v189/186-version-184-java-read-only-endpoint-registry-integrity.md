> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 186. Java v184 read-only endpoint registry integrity

## 目标

v182 将 endpoint registry 改为 paired descriptor，v183 冻结了 v179 snapshot。v184 在这个基础上新增一个只读 integrity receipt，显式证明当前 registry 的 pair/live/fixture 数量一致且没有重复。

## 代码变更

- `OpsShardReadinessReadOnlyEndpointRegistryIntegrityService`
  - 读取 `OpsShardReadinessEvidenceEndpoints.endpointPairs()`、`liveEndpoints()`、`fixtureEndpoints()`。
  - 校验 23 pair / 23 live / 23 fixture。
  - 校验 live/fixture endpoint 去重。
  - 校验 registry 包含 v184 自己的 live endpoint 和 fixture endpoint。
- `OpsShardReadinessReadOnlyEvidenceController`
  - 增加只读 GET 路由 `/read-only-endpoint-registry-integrity`。
- `OpsShardReadinessEvidenceEndpoints`
  - 增加 v184 endpoint pair。
- `OpsEvidenceServiceTests` 和 endpoint registry 测试同步新增 v184 endpoint / fixture。
- `OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshotTests`
  - 明确 v179 snapshot 不包含 v184 endpoint，证明旧 receipt 不漂移。

## 验证与边界

- focused 测试覆盖 v184 service、HTTP integration、static fixture、route constants、controller split、endpoint registry、Ops evidence 汇总和 v179 snapshot。
- endpoint registry 从 22/22 增长到 23/23，但 v179 snapshot 保持 22/22。
- 本版没有新增执行入口，没有打开 write routing、active shard router、credential 读取、raw endpoint parse、managed audit connection、deployment / rollback。
