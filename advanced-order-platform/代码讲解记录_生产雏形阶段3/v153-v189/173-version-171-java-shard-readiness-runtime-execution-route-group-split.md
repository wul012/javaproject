# 173. Java v171 runtime execution route group split

## 背景

Node v410 已把 Java / mini-kv runtime execution 审计路由从中央 route 表拆到独立 route group。Java 侧对应的问题是 `OpsShardReadinessController` 已经承载了基础 readiness、evidence、plan、lifecycle 和 runtime execution 多条链路，继续新增会让构造器和路由方法膨胀。

## 本版处理

- 新增 `OpsShardReadinessRuntimeExecutionController`。
- 将 v162-v170 runtime execution 相关 9 个 GET endpoint 移入独立控制器。
- `OpsShardReadinessController` 继续保留基础 readiness、hardening、evidence、active-shard plan、live-read plan、operator lifecycle 和 declared operator lifecycle。
- 所有 API path 保持不变。

## 保守边界

- 本版只做 controller boundary refactor。
- 不新增 evidence gate。
- 不改变 response record、service 输出或静态 fixture。
- 不启动 Java / mini-kv，不连接 managed audit，不允许写路径。

## 验证

- `mvn -q -DskipTests compile`
- runtime execution 相关集成测试
- `mvn -q test`
- v171 归档页截图和浏览器快照
