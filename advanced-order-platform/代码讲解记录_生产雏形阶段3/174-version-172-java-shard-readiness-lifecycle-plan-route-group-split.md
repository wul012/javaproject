# 174. Java v172 lifecycle plan route group split

## 背景

v171 已拆出 runtime execution controller。剩余 `OpsShardReadinessController` 仍同时承载 evidence、active-shard plan、live-read gate plan、operator lifecycle 和 declared operator lifecycle。Node v411-v412 已分别推进 declared lifecycle 与 active-shard plan 路由拆分，Java 侧可以继续做同类维护性拆分。

## 本版处理

- 新增 `OpsShardReadinessLifecyclePlanController`。
- 迁出 4 个 endpoint：
  - `/api/v1/ops/shard-readiness/active-shard-plan-handoff`
  - `/api/v1/ops/shard-readiness/live-read-gate-plan`
  - `/api/v1/ops/shard-readiness/operator-service-lifecycle`
  - `/api/v1/ops/shard-readiness/declared-operator-lifecycle`
- `OpsShardReadinessController` 保留基础 readiness、hardening 和 evidence 入口。

## 保守边界

- 不改变 API path。
- 不改变 response record 或 service 输出。
- 不新增 evidence gate。
- 不启动 Java / mini-kv，不连接 managed audit，不允许写路径。

## 验证

- `mvn -q -DskipTests compile`
- lifecycle/plan 相关集成测试
- `mvn -q test`
- v172 归档页截图和浏览器快照
