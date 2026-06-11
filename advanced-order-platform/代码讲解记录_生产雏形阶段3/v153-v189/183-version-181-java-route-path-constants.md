# 183. Java v181 route path constants

## 目标

v180 拆出 read-only evidence controller 后，controller 层和 service `ENDPOINT` 仍有一组重复路径字符串。v181 将 evidence 相关路径收敛为共享常量，降低后续新增只读证据入口时手抄路径的风险。

## 代码变更

- `OpsShardReadinessRoutePaths`
  - 新增 base path 常量 `/api/v1/ops/shard-readiness`。
  - 新增 6 个 evidence route 常量。
- 两个 controller
  - `@RequestMapping` 使用共享 base path。
  - `@GetMapping` 使用对应 route 常量。
- 6 个 evidence service
  - `ENDPOINT` 由 `BASE_PATH + route constant` 组成。
- `OpsShardReadinessRoutePathsTests`
  - 校验 service endpoint 与 route constants 拼装结果一致。

## 验证与边界

- focused 测试覆盖 route constants、controller split、endpoint registry 和 6 个既有 HTTP 路径。
- endpoint registry 不扩张，仍为 22 live / 22 fixture。
- 本版没有新增执行入口，没有打开 write routing、active shard router、credential 读取、raw endpoint parse、managed audit connection、deployment / rollback。
