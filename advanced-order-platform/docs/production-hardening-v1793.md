# v1793 Production Hardening Evidence

本文件记录 J3 的生产配置与安全边界硬化结果。它不是新的业务契约，也不改变
Node、mini-kv 或历史 evidence archive 的路径。

## Prod Profile

`src/main/resources/application-prod.yml` 增加了生产 profile 的最小硬化覆盖：

- `spring.h2.console.enabled=false`，避免生产 profile 暴露 H2 console。
- `spring.jpa.show-sql=false`，`hibernate.format_sql=false`，避免 SQL 调试输出成为默认生产行为。
- `server.shutdown=graceful` 与 `spring.lifecycle.timeout-per-shutdown-phase=30s`，给 Spring
  lifecycle bean 和调度任务一个有界停机窗口。
- `management.endpoint.health.probes.enabled=true`，让 CI 和运行环境可以请求健康探针。
- RabbitMQ health indicator 在基础 prod profile 中仍关闭；只有显式启用 RabbitMQ profile
  时才应让 RabbitMQ 成为健康检查依赖。

## CI Prod Smoke

GitHub Actions 的主 job 在 `verify` 之后复用已经生成的 jar，使用：

```text
--spring.profiles.active=prod
--server.port=18080
--order.expiration.enabled=false
--outbox.publisher.enabled=false
```

启动一次应用并请求 `/actuator/health`。这里关闭两个 scheduler 是为了让 CI smoke 聚焦
prod profile 能否启动和响应健康检查，而不是让短生命周期的冒烟作业等待后台扫描周期。
失败时 CI 会上传 `prod-smoke.log` 和 `prod-health.json`。

## Compose Credential Boundary

`compose.yaml` 与 `docker-compose.yml` 都改为 `${VAR:-default}` 形式，默认值只用于本地开发。
`.env.example` 只给占位值，真实 `.env` 已加入 ignore，不应提交到 git。

## Scheduler Shutdown Observation

`OrderExpirationScheduler` 和 `OutboxPublisherScheduler` 都是 Spring 管理的 `@Scheduled`
组件，没有直接创建线程、`ExecutorService`、外部进程或手写无限循环。启用
`server.shutdown=graceful` 后，它们跟随 Spring 应用上下文生命周期停止；`30s` timeout
给正在执行的一次扫描留出有限收尾时间。当前版本没有把它们改造成自定义线程池，因为这会扩大
J3 的行为半径。后续如果引入专用 `TaskScheduler`，需要补一条显式 shutdown/await test。

## Input Validation Boundary

订单创建入口现在对 `Idempotency-Key` 增加非空和长度约束，对订单行数增加上限，对
`productId` 增加正数约束。失败事件写请求 DTO 增加 `ids/status/note/reason/review status`
等边界约束，同时保留 replay 请求对已存事件字段的回退逻辑。统一异常处理将
`MethodArgumentNotValidException`、`ConstraintViolationException` 与
`HandlerMethodValidationException` 都映射为 `VALIDATION_FAILED` ProblemDetail。
