> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 182. Java v180 read-only evidence controller split

## 目标

v179 后 read-only catalog、handoff、handoff verification 三个入口都挂在 `OpsShardReadinessEvidenceController` 上，构造器继续膨胀。v180 做一次边界内拆分，把只读 catalog 入口移到专用 controller。

## 代码变更

- `OpsShardReadinessReadOnlyEvidenceController`
  - 新增专用 Spring controller。
  - 保留原有 base path `/api/v1/ops/shard-readiness`。
  - 继续提供三个既有路径：
    - `/read-only-evidence-catalog`
    - `/read-only-evidence-catalog-handoff`
    - `/read-only-evidence-catalog-handoff-verification`
- `OpsShardReadinessEvidenceController`
  - 只保留 evidence index、evidence verification、evidence handoff。
  - 构造器从 6 个依赖收窄到 3 个依赖。
- `OpsShardReadinessReadOnlyEvidenceControllerSplitTests`
  - 固定 read-only catalog 路由在专用 controller 内。
  - 防止后续维护时又把 catalog 路由塞回总 evidence controller。

## 验证与边界

- focused 测试覆盖拆分守卫和六个既有 HTTP 路径。
- endpoint registry 没有扩张，仍为 v179 后的 22 live / 22 fixture。
- 本版没有新增执行入口，没有打开 write routing、active shard router、credential 读取、raw endpoint parse、managed audit connection、deployment / rollback。
