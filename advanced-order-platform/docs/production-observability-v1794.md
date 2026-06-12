# J4 Production Observability v1794

本文件记录 J4 的可观测性补齐结果。该版本不新增业务写入口，不改变失败事件重放、
审批、回滚、凭据、SQL 或部署边界，只把运行诊断和请求日志相关性做成可验证能力。

## Runtime Endpoints

- actuator 暴露范围固定为 `health,info,metrics`。
- `/actuator/health` 继续服务 CI prod profile smoke 和运行探针。
- `/actuator/info` 用于轻量运行信息入口，当前不承载敏感字段。
- `/actuator/metrics` 用于本地和 CI 环境下的 JVM/HTTP/应用指标检查。
- prod profile 显式保留同一组暴露范围，避免默认配置和生产 profile 漂移。

## Trace And Span Logging

- 新增 `io.micrometer:micrometer-tracing-bridge-brave`。
- `management.tracing.sampling.probability=1.0`，保证本项目当前无外部 tracing 后端时，
  每个请求仍能在本地日志中形成 trace/span 相关性。
- `logging.pattern.level` 输出 `traceId` 和 `spanId`。
- `ApiExceptionHandler` 在以下路径写入 trace/span：
  - `BusinessException`
  - `MethodArgumentNotValidException`
  - `ConstraintViolationException`
  - `HandlerMethodValidationException`
  - `MissingRequestHeaderException`

## Boundary

J4 的 actuator 和 tracing 都是只读诊断面。它们不打开：

- deployment / rollback
- credential value
- raw endpoint execution
- managed audit connection
- failed-event replay execution
- SQL execution

异常响应 body 没有为了 trace 相关性新增字段。trace/span 只进入日志，避免把错误响应
悄悄扩展成新的下游契约。

## Verification

聚焦验证命令：

```powershell
.\mvnw.cmd -B "-Dtest=ApiExceptionHandlerTests,RequestLogCorrelationTests,ActuatorHealthIntegrationTests,ObservabilityConfigurationTests,ApiExceptionTraceIntegrationTests" test
```

已验证：

- `/actuator/health`、`/actuator/health/liveness`、`/actuator/health/readiness` 返回 UP。
- `/actuator/info` 返回 200。
- `/actuator/metrics` 返回 200 且包含 JVM metric 名称。
- `Tracer` bean 可用。
- tracing sampling 和 logging pattern 配置存在。
- MDC 中有 trace/span 时，异常处理器日志会输出相同值。
- MDC 中没有 trace/span 时，日志相关性工具返回 `unavailable`。
- 真实 HTTP 校验错误会由 Micrometer/Brave 生成请求 trace/span，并进入
  `ApiExceptionHandler` 日志。

本地收尾已通过 Spotless、默认 `verify`、docker profile verify 和 Java 21 prod smoke。
远端 GitHub Actions 仍需在推送后确认。
