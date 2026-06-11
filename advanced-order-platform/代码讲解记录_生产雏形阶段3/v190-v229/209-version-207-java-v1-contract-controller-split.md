# 209. Java v207 shard readiness v1 contract controller split

本版做的是结构拆分，不改变对外语义。

`OpsShardReadinessController` 现在只负责 `/api/v1/ops/shard-readiness`、`/hardening`、`/echo` 三个基础只读入口。六个 v1 contract 入口迁移到 `OpsShardReadinessV1ContractController`，该 controller 使用 `OpsShardReadinessRoutePaths.BASE_PATH` 作为统一前缀。

`OpsShardReadinessRoutePaths` 增加了 v1 contract 的六个 route constant，相关 service 的 `ENDPOINT` 常量改为 `BASE_PATH + route`。测试侧新增 `OpsShardReadinessV1ContractControllerSplitTests`，并扩展 `OpsShardReadinessRoutePathsTests`，确保路径仍由同一组常量控制。

验证重点：

- v1 contract route 归属 dedicated controller；
- 主 readiness controller 不再声明 v1 contract mappings；
- alignment 与 consumer probe plan 的既有集成端点仍然可用；
- 全程不触碰写执行、凭据、部署、回滚、进程控制边界。
