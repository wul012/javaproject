# Production Readiness

本文件集中说明当前 Java 订单平台的生产就绪边界。它不是部署授权书，也不是回滚执行计划。
截至 v1795，本项目处于“单项目验证 + 跨项目契约对齐”状态：CI、覆盖率、静态分析、
prod profile smoke、只读 evidence 和运行诊断能力已经增强，但真实生产部署、真实密钥、
真实支付网关、真实回滚执行和 managed audit 连接仍然关闭。

## Version Policy

- Git tag `vNNNN-*` 是当前权威版本证据。
- Maven artifact 仍保持 `0.1.0-SNAPSHOT`，避免每个高频工程版本都重写制品版本。
- 每个完成版本必须有提交、tag、测试证据、必要文档和中文代码讲解。
- 版本历史从 v1795 开始记录在 `CHANGELOG.md`，并只回填最近若干关键版本。

## Runtime Profiles

- 默认 profile 使用 H2 内存库，面向本地开发和 headless CI。
- `prod` profile 通过 `--spring.profiles.active=prod` 激活。
- `application-prod.yml` 关闭 `spring.h2.console.enabled`。
- `application-prod.yml` 关闭 `spring.jpa.show-sql` 和 hibernate `format_sql`。
- `server.shutdown=graceful`，`spring.lifecycle.timeout-per-shutdown-phase=30s`。
- CI 会在构建 jar 后用 prod profile 启动，并请求 `/actuator/health`。

## Observability

- actuator 暴露范围限定为 `health,info,metrics`。
- `/actuator/health`、`/actuator/info`、`/actuator/metrics` 是只读诊断入口。
- Micrometer Tracing Brave bridge 提供 trace/span 日志相关性。
- `ApiExceptionHandler` 会在业务异常、校验异常和缺失 header 场景记录 trace/span。
- 当前没有配置 Zipkin、Tempo 或其他外部 tracing 后端。

## Persistence And Migrations

- 默认 H2 只用于本地和 CI。
- PostgreSQL 通过 compose 和 profile 显式启用。
- Flyway migration 是 schema 演进入口。
- 本项目没有自动执行 rollback SQL 的能力。
- rollback SQL review gate、rollback approval handoff 和 deployment rollback evidence 都是只读样本或人工审批交接材料。

## Payment Boundary

- 支付是模拟的。
- `PaymentTransaction.succeeded` 和 `PaymentTransaction.refunded` 使用 provider `SIMULATED`。
- 本项目没有接入真实支付网关、扣款渠道、退款渠道或清结算系统。
- 订单支付记录只表达应用内状态演练，不代表真实资金流。

## Messaging Boundary

- Outbox 表和 scheduler 存在。
- `outbox.publisher.enabled` 在默认配置中为 `true`，但 RabbitMQ transport 默认关闭。
- `outbox.rabbitmq.enabled=false` 时使用 database-only dispatcher，不向 RabbitMQ 发布。
- CI prod smoke 显式设置 `--outbox.publisher.enabled=false`，避免短生命周期冒烟等待后台扫描。
- RabbitMQ outbox 发布必须显式启用 RabbitMQ profile 或相关配置。
- 通知消费者默认关闭：`notification.rabbitmq.enabled=false`。

## Failed Event Replay Boundary

failed-event replay 有明确角色矩阵，配置位于 `src/main/resources/application.yml` 的
`failed-event.replay.*` 段：

| 能力 | 默认允许角色 |
| --- | --- |
| manage failed event | `ORDER_SUPPORT`, `SRE`, `SYSTEM` |
| request replay approval | `ORDER_SUPPORT`, `SRE`, `SYSTEM` |
| review replay approval | `SRE`, `SYSTEM` |
| replay failed event | `ORDER_SUPPORT`, `SRE`, `SYSTEM` |

审批 review 默认不允许普通 `ORDER_SUPPORT` 单独完成。真实 replay 仍要经过已有审批、
digest 和 readiness 规则重新校验。

## Release Approval Rehearsal Boundary

- release approval rehearsal 相关接口是只读 evidence、hint、receipt 或 handoff。
- 它们不部署、不回滚、不写 ledger、不连接 managed audit、不读取 credential value。
- credential resolver 相关内容保持 echo、preflight、fake shell 或 disabled implementation 边界。
- Node 可以消费 Java evidence，但 Java evidence 不授权 Node 执行部署、回滚、SQL、secret 或 managed audit connection。

## Explicitly Not Production Authorized

以下能力截至 v1795 仍然关闭或不存在：

- 真实支付网关
- 生产 secret manager 读取
- credential value 输出
- raw endpoint resolution/execution
- managed audit HTTP/TCP connection
- deployment execution
- rollback execution
- rollback SQL execution
- Node 自动启动或停止 Java / mini-kv
- mini-kv 自动启动
- 未经审批的 failed-event replay

## Required Release Gate

发布一个 Java 版本至少需要：

- `mvnw` 默认 verify 通过。
- Spotless ratchet check 通过。
- SpotBugs check 通过。
- JaCoCo floors 通过。
- docker profile verify 通过，允许无 Docker 环境下 Testcontainers 测试按标签跳过。
- prod profile boot smoke 通过。
- GitHub Actions 远端 run 通过。
- tag 推送到 canonical remote `javaproject`。
