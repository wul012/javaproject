# 176. Java v174 shard readiness echo

## 背景

v171-v173 已完成 shard-readiness controller 拆分。下一步不应继续拆空文件，而是给 Java 侧独立消费提供一个版本化只读 echo：既保留 v153 根 readiness 的旧 schema，又让新消费者能看到 controller split、证据目录和禁用边界。

## 本版处理

- 新增 `OpsShardReadinessEchoService`。
- 新增 `OpsShardReadinessEchoResponse`。
- 在 `OpsShardReadinessController` 增加 `GET /api/v1/ops/shard-readiness/echo`。
- 在 `OpsShardReadinessEvidenceEndpoints` 纳入 echo live endpoint 和 fixture endpoint。
- 新增 `/contracts/java-shard-readiness-echo-v174.fixture.json`。
- 新增 `e/174/` 证据归档。

## 保守边界

- `/api/v1/ops/shard-readiness` 的 v153 schema 不变。
- 不打开 write routing。
- 不启用 active shard router。
- 不读取 credential value 或 raw endpoint。
- 不连接 managed audit。
- 不启动或停止 Java / mini-kv。

## 验证

- `mvn -q -DskipTests compile`
- echo 服务和接口测试
- `mvn -q test`
- v174 归档页截图和浏览器快照
