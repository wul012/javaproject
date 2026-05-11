# Advanced Order Platform

一个面向高级 Java 练手的订单交易平台雏形。当前采用模块化单体架构，重点训练 Spring Boot 业务建模、事务编排、库存一致性、审计流水、Outbox 和数据库工程化。

## 当前能力

- 商品目录查询
- 幂等下单
- 库存预占、扣减、释放、退款回补
- 库存变更流水查询
- 订单支付模拟
- 支付交易流水查询
- 支付后退款
- 订单取消
- 未支付订单自动过期取消
- 订单发货与完成
- 订单状态历史查询
- Outbox 事件表
- Outbox 后台发布标记
- RabbitMQ Outbox 真实消息发布
- RabbitMQ 通知消费者
- 通知消息幂等落库与查询
- RabbitMQ 消费失败重试和死信队列
- 失败事件消息落库与查询
- 失败事件消息修复重放
- 失败事件重放操作审计查询
- 失败事件重放角色校验和原因记录
- 失败事件与重放审计多条件筛选
- 失败事件查询分页响应和排序白名单
- 失败事件管理状态和批量标记
- 失败事件管理状态变更流水查询
- 失败事件和管理状态流水 CSV 导出
- 失败事件管理静态页面
- 失败事件管理页面内置重放工作台
- 失败事件重放二次确认和 Payload 覆盖风险提示
- 失败事件重放申请/审批/拒绝门禁
- 失败事件重放审批历史流水查询和 CSV 导出
- 失败事件重放审批职责分离，申请人不能审批自己的申请
- 失败事件写操作统一操作员上下文解析和页面身份校验
- 失败事件写操作按动作区分允许角色，管理、申请、审批、重放可独立配置
- 失败事件操作员上下文返回当前角色可执行和不可执行动作快照
- 失败事件管理页面按校验后的动作权限禁用未授权写按钮
- 失败事件管理页面写操作本地权限守卫，防止绕过禁用按钮触发未授权动作
- Actuator 健康检查
- 默认本地健康检查不依赖未启用的 RabbitMQ
- Flyway 数据库迁移
- H2 本地快速启动
- PostgreSQL profile
- RabbitMQ profile
- Testcontainers PostgreSQL / RabbitMQ 集成测试入口

## Tech Stack

- Java 21
- Spring Boot 3.5.9
- Spring MVC
- Spring Data JPA
- Bean Validation
- Spring AMQP
- Flyway
- H2 / PostgreSQL
- RabbitMQ
- Testcontainers
- Maven

## Run

默认使用 H2 内存数据库：

```powershell
mvn spring-boot:run
```

默认地址：

```text
http://localhost:8080
```

健康检查：

```powershell
Invoke-RestMethod http://localhost:8080/actuator/health
```

默认 H2 本地模式下，RabbitMQ 业务能力未启用，因此 RabbitMQ health indicator 也默认关闭，避免本地未启动 RabbitMQ 时根健康检查误报 `DOWN`。

失败事件管理页面：

```text
http://localhost:8080/failed-events.html
```

## PostgreSQL Run

本地启动 PostgreSQL：

```powershell
docker compose -f compose.yaml up -d postgres
```

使用 PostgreSQL profile 启动应用：

```powershell
mvn spring-boot:run -Dspring-boot.run.profiles=postgres
```

打包后启动：

```powershell
java -jar target\advanced-order-platform-0.1.0-SNAPSHOT.jar --spring.profiles.active=postgres
```

默认连接信息在 [application-postgres.yml](<D:/javaproj/advanced-order-platform/src/main/resources/application-postgres.yml:1>) 中，可通过环境变量覆盖：

```text
DB_URL
DB_USERNAME
DB_PASSWORD
```

## RabbitMQ Run

本地启动 RabbitMQ：

```powershell
docker compose -f compose.yaml up -d rabbitmq
```

RabbitMQ 管理页面：

```text
http://localhost:15672
```

默认账号密码：

```text
order_app / order_app
```

启用 RabbitMQ Outbox 发布：

```powershell
mvn spring-boot:run -Dspring-boot.run.profiles=rabbitmq
```

同时使用 PostgreSQL 和 RabbitMQ：

```powershell
docker compose -f compose.yaml up -d postgres rabbitmq
mvn spring-boot:run -Dspring-boot.run.profiles=postgres,rabbitmq
```

RabbitMQ profile 会启用：

```yaml
management:
  health:
    rabbit:
      enabled: true

outbox:
  rabbitmq:
    enabled: true
    exchange: order-platform.outbox
    queue: order-platform.outbox.events
    routing-key-prefix: orders
    dead-letter-exchange: order-platform.outbox.dlx
    dead-letter-queue: order-platform.outbox.events.dlq
    dead-letter-routing-key: orders.dead-letter

notification:
  rabbitmq:
    enabled: true
    retry:
      max-attempts: 3
      initial-interval-ms: 200
      multiplier: 2.0
      max-interval-ms: 1000
```

## API Quick Start

查询商品：

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/products
```

查询商品库存流水：

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/inventory/products/1/movements
```

创建订单：

```powershell
$body = @{
  customerId = "11111111-1111-1111-1111-111111111111"
  items = @(
    @{ productId = 1; quantity = 2 }
  )
} | ConvertTo-Json -Depth 5

Invoke-RestMethod `
  -Method Post `
  -Uri http://localhost:8080/api/v1/orders `
  -ContentType "application/json" `
  -Headers @{ "Idempotency-Key" = "demo-order-001" } `
  -Body $body
```

支付订单：

```powershell
Invoke-RestMethod -Method Post http://localhost:8080/api/v1/orders/1/pay
```

查询订单支付流水：

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/orders/1/payments
```

退款订单：

```powershell
Invoke-RestMethod -Method Post http://localhost:8080/api/v1/orders/1/refund
```

取消订单：

```powershell
Invoke-RestMethod -Method Post http://localhost:8080/api/v1/orders/1/cancel
```

发货订单：

```powershell
Invoke-RestMethod -Method Post http://localhost:8080/api/v1/orders/1/ship
```

完成订单：

```powershell
Invoke-RestMethod -Method Post http://localhost:8080/api/v1/orders/1/complete
```

查询订单状态历史：

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/orders/1/history
```

查看 Outbox 事件：

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/outbox/events
```

查询通知消息：

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/notifications
```

查询某个订单的通知消息：

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/notifications/orders/1
```

查询失败事件消息：

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/failed-events
```

按条件查询失败事件消息：

```powershell
Invoke-RestMethod "http://localhost:8080/api/v1/failed-events?status=RECORDED&eventType=OrderCreated&aggregateType=ORDER&aggregateId=404&page=0&size=20&sort=failedAt,desc"
```

按时间窗口查询失败事件消息：

```powershell
Invoke-RestMethod "http://localhost:8080/api/v1/failed-events?failedFrom=2026-05-10T00:00:00Z&failedTo=2026-05-11T00:00:00Z"
```

失败事件查询返回分页对象：

```json
{
  "content": [],
  "page": 0,
  "size": 20,
  "totalElements": 0,
  "totalPages": 0,
  "first": true,
  "last": true,
  "empty": true,
  "sort": "failedAt,desc"
}
```

失败事件允许排序字段：

```text
id, failedAt, status, eventType, aggregateId, replayCount, managementStatus, managedAt
replayApprovalStatus, replayApprovalRequestedAt, replayApprovalReviewedAt
```

按管理状态查询失败事件：

```powershell
Invoke-RestMethod "http://localhost:8080/api/v1/failed-events?managementStatus=INVESTIGATING&page=0&size=20&sort=managedAt,desc"
```

按重放审批状态查询失败事件：

```powershell
Invoke-RestMethod "http://localhost:8080/api/v1/failed-events?replayApprovalStatus=PENDING&page=0&size=20&sort=replayApprovalRequestedAt,desc"
```

批量标记失败事件管理状态：

```powershell
$body = @{
  ids = @(1, 2)
  status = "INVESTIGATING"
  note = "support is checking customer impact"
} | ConvertTo-Json

Invoke-RestMethod `
  -Method Post `
  -Uri http://localhost:8080/api/v1/failed-events/management-status `
  -ContentType "application/json" `
  -Headers @{
    "X-Operator-Id" = "local-admin"
    "X-Operator-Role" = "SRE"
  } `
  -Body $body
```

管理状态：

```text
OPEN, INVESTIGATING, IGNORED, RESOLVED
```

查询单个失败事件的管理状态变更流水：

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/failed-events/1/management-history
```

全局筛选管理状态变更流水：

```powershell
Invoke-RestMethod "http://localhost:8080/api/v1/failed-events/management-history?failedEventMessageId=1&previousStatus=OPEN&newStatus=INVESTIGATING&operatorRole=SRE&page=0&size=20&sort=changedAt,desc"
```

管理状态变更流水允许排序字段：

```text
id, changedAt, previousStatus, newStatus, operatorId, operatorRole
```

导出失败事件 CSV：

```powershell
Invoke-WebRequest `
  -Uri "http://localhost:8080/api/v1/failed-events/export?managementStatus=RESOLVED&sort=managedAt,desc&limit=1000" `
  -OutFile failed-events.csv
```

导出管理状态变更流水 CSV：

```powershell
Invoke-WebRequest `
  -Uri "http://localhost:8080/api/v1/failed-events/management-history/export?newStatus=RESOLVED&operatorRole=ORDER_SUPPORT&sort=changedAt,desc&limit=1000" `
  -OutFile failed-event-management-history.csv
```

CSV 导出限制：

```text
limit
 -> 默认 1000，最大 5000
```

页面入口：

```text
http://localhost:8080/failed-events.html
```

页面能力：

```text
筛选失败事件
批量标记管理状态
查看单条失败事件管理状态流水
发起单条失败事件重放
查看单条失败事件重放审计
重放前二次确认
Payload 覆盖风险提示
下载失败事件 CSV
下载管理状态流水 CSV
申请/审批/拒绝失败事件重放
查看失败事件重放审批流水
阻止申请人自提自审
校验当前操作员身份和角色是否会被后端接受
查看当前角色在管理、申请、审批、重放动作上的允许范围
查看当前角色可执行和不可执行动作摘要
校验后自动禁用当前角色未授权的管理、申请、审批或重放按钮
校验后写操作处理函数会再次校验本地动作权限，防止脚本绕过禁用按钮触发未授权动作
```

校验失败事件操作员上下文：

```powershell
Invoke-RestMethod `
  -Uri http://localhost:8080/api/v1/failed-events/operator-context `
  -Headers @{
    "X-Operator-Id" = " local-admin "
    "X-Operator-Role" = " sre "
  }
```

响应会返回规范化后的操作员信息和当前允许角色：

```json
{
  "operatorId": "local-admin",
  "operatorRole": "SRE",
  "allowedRoles": ["ORDER_SUPPORT", "SRE", "SYSTEM"],
  "allowedRolesByAction": {
    "MANAGE_FAILED_EVENT": ["ORDER_SUPPORT", "SRE", "SYSTEM"],
    "REQUEST_REPLAY_APPROVAL": ["ORDER_SUPPORT", "SRE", "SYSTEM"],
    "REVIEW_REPLAY_APPROVAL": ["SRE", "SYSTEM"],
    "REPLAY_FAILED_EVENT": ["ORDER_SUPPORT", "SRE", "SYSTEM"]
  },
  "allowedActions": [
    "MANAGE_FAILED_EVENT",
    "REQUEST_REPLAY_APPROVAL",
    "REVIEW_REPLAY_APPROVAL",
    "REPLAY_FAILED_EVENT"
  ],
  "deniedActions": []
}
```

如果当前角色是 `ORDER_SUPPORT`，默认会返回：

```json
{
  "operatorRole": "ORDER_SUPPORT",
  "allowedActions": [
    "MANAGE_FAILED_EVENT",
    "REQUEST_REPLAY_APPROVAL",
    "REPLAY_FAILED_EVENT"
  ],
  "deniedActions": [
    "REVIEW_REPLAY_APPROVAL"
  ]
}
```

页面会根据 `allowedActions` / `deniedActions` 做操作前预检：

```text
ORDER_SUPPORT 校验后：
 -> Request approval 可用
 -> Approve / Reject 禁用
 -> 提交重放可用

SRE 校验后：
 -> Request approval / Approve / Reject / 提交重放 全部可用
```

切换操作人或角色后，页面会恢复到“未校验”状态并重新放开按钮，提示操作者重新校验当前身份。

v33 起，页面写操作处理函数也会读取本地校验结果；若当前身份已校验且不包含目标动作，即使按钮状态被脚本改回可点击，也会先提示未授权并停止发起请求。未校验时保留原兼容流程；切换操作人或角色会重置为未校验。

失败事件动作级角色配置：

```yaml
failed-event:
  replay:
    allowed-roles:
      - ORDER_SUPPORT
      - SRE
      - SYSTEM
    management-roles:
      - ORDER_SUPPORT
      - SRE
      - SYSTEM
    replay-approval-request-roles:
      - ORDER_SUPPORT
      - SRE
      - SYSTEM
    replay-approval-review-roles:
      - SRE
      - SYSTEM
    replay-roles:
      - ORDER_SUPPORT
      - SRE
      - SYSTEM
```

默认策略里 `ORDER_SUPPORT` 可以管理失败事件、申请审批和执行重放，但不能审批重放申请；审批动作默认只允许 `SRE` 和 `SYSTEM`。

申请重放审批：

```powershell
$approvalBody = @{
  reason = "DLQ payload and replay headers verified"
} | ConvertTo-Json

Invoke-RestMethod `
  -Method Post `
  -Uri http://localhost:8080/api/v1/failed-events/1/replay-approval `
  -ContentType "application/json" `
  -Headers @{
    "X-Operator-Id" = "local-admin"
    "X-Operator-Role" = "SRE"
  } `
  -Body $approvalBody
```

审批通过或拒绝：

```powershell
$reviewBody = @{
  status = "APPROVED"
  note = "checked failure reason and replay payload"
} | ConvertTo-Json

Invoke-RestMethod `
  -Method Post `
  -Uri http://localhost:8080/api/v1/failed-events/1/replay-approval/review `
  -ContentType "application/json" `
  -Headers @{
    "X-Operator-Id" = "sre-lead"
    "X-Operator-Role" = "SRE"
  } `
  -Body $reviewBody
```

审批职责分离规则：

```text
同一个 X-Operator-Id 不能审批自己刚提交的重放申请。
如果申请人和审批人相同，后端返回 409 Conflict：
replay approval requester cannot review own request
```

查询单个失败事件的重放审批流水：

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/failed-events/1/replay-approval-history
```

全局筛选重放审批流水：

```powershell
Invoke-RestMethod "http://localhost:8080/api/v1/failed-events/replay-approval-history?failedEventMessageId=1&action=REJECTED&operatorRole=SRE&page=0&size=20&sort=changedAt,desc"
```

重放审批流水允许排序字段：

```text
id, changedAt, action, operatorId, operatorRole
```

导出重放审批流水 CSV：

```powershell
Invoke-WebRequest `
  -Uri "http://localhost:8080/api/v1/failed-events/replay-approval-history/export?action=APPROVED&sort=changedAt,desc&limit=1000" `
  -OutFile failed-event-replay-approval-history.csv
```

修复并重放失败事件消息：

```powershell
$body = @{
  eventId = "14141414-1414-1414-1414-141414141414"
  reason = "repair missing eventId after checking DLQ payload"
} | ConvertTo-Json

Invoke-RestMethod `
  -Method Post `
  -Uri http://localhost:8080/api/v1/failed-events/1/replay `
  -ContentType "application/json" `
  -Headers @{
    "X-Operator-Id" = "local-admin"
    "X-Operator-Role" = "SRE"
  } `
  -Body $body
```

查询失败事件重放审计记录：

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/failed-events/1/replay-attempts
```

全局筛选重放审计记录：

```powershell
Invoke-RestMethod "http://localhost:8080/api/v1/failed-events/replay-attempts?failedEventMessageId=1&status=SUCCEEDED&operatorRole=SRE&page=0&size=20&sort=attemptedAt,desc"
```

重放审计允许排序字段：

```text
id, attemptedAt, status, operatorId, operatorRole
```

## Database Migration

第十版开始，项目使用 Flyway 管理数据库结构，Hibernate 只做结构校验：

```yaml
spring:
  flyway:
    locations: "classpath:db/migration/{vendor}"
  jpa:
    hibernate:
      ddl-auto: validate
```

默认 H2 执行：

```text
src/main/resources/db/migration/h2/V1__initial_schema.sql
src/main/resources/db/migration/h2/V2__notification_messages.sql
src/main/resources/db/migration/h2/V3__failed_event_messages.sql
src/main/resources/db/migration/h2/V4__failed_event_replay_state.sql
src/main/resources/db/migration/h2/V5__failed_event_replay_attempts.sql
src/main/resources/db/migration/h2/V6__failed_event_replay_authorization.sql
src/main/resources/db/migration/h2/V7__failed_event_search_indexes.sql
src/main/resources/db/migration/h2/V8__failed_event_management_status.sql
src/main/resources/db/migration/h2/V9__failed_event_management_history.sql
src/main/resources/db/migration/h2/V10__failed_event_replay_approval.sql
src/main/resources/db/migration/h2/V11__failed_event_replay_approval_history.sql
```

PostgreSQL profile 执行：

```text
src/main/resources/db/migration/postgresql/V1__initial_schema.sql
src/main/resources/db/migration/postgresql/V2__notification_messages.sql
src/main/resources/db/migration/postgresql/V3__failed_event_messages.sql
src/main/resources/db/migration/postgresql/V4__failed_event_replay_state.sql
src/main/resources/db/migration/postgresql/V5__failed_event_replay_attempts.sql
src/main/resources/db/migration/postgresql/V6__failed_event_replay_authorization.sql
src/main/resources/db/migration/postgresql/V7__failed_event_search_indexes.sql
src/main/resources/db/migration/postgresql/V8__failed_event_management_status.sql
src/main/resources/db/migration/postgresql/V9__failed_event_management_history.sql
src/main/resources/db/migration/postgresql/V10__failed_event_replay_approval.sql
src/main/resources/db/migration/postgresql/V11__failed_event_replay_approval_history.sql
```

如果 Docker 未启动，Testcontainers 的 PostgreSQL / RabbitMQ 集成测试会自动跳过；启动 Docker 后重新执行 `mvn test` 即可跑真实中间件验证。

## Order Expiration

默认每 60 秒扫描一次超过 15 分钟仍未支付的 `CREATED` 订单，并自动取消：

```yaml
order:
  expiration:
    enabled: true
    unpaid-timeout: PT15M
    scan-delay-ms: 60000
```

本地调试时可以临时缩短：

```powershell
mvn spring-boot:run `
  -Dspring-boot.run.arguments="--order.expiration.unpaid-timeout=PT5S --order.expiration.scan-delay-ms=1000"
```

## Outbox Publisher

默认每 60 秒扫描一次未发布的 Outbox 事件。普通模式下只把 `publishedAt` 标记为当前时间；启用 `rabbitmq` profile 后，会先发送 RabbitMQ 消息，再标记 `publishedAt`：

```yaml
outbox:
  publisher:
    enabled: true
    scan-delay-ms: 60000
```

本地调试时可以临时缩短：

```powershell
mvn spring-boot:run `
  -Dspring-boot.run.arguments="--outbox.publisher.scan-delay-ms=1000"
```

## Architecture Direction

当前代码按业务边界分包：

```text
catalog
 -> 商品目录

inventory
 -> 库存、并发控制、库存预占/扣减/释放/回补、库存变更流水

order
 -> 订单模型、幂等下单、支付、退款、取消、发货、完成、状态历史、超时过期

payment
 -> 支付成功和退款交易流水

outbox
 -> 事件表、事件查询、后台发布标记、RabbitMQ 真实消息发布

notification
 -> RabbitMQ 订单事件消费者、通知消息、幂等落库、消费失败重试、死信记录、失败事件分页筛选查询、管理状态批量标记、管理状态变更流水查询、CSV 导出、重放接口、角色校验和重放审计分页筛选查询
 -> v25 增加重放审批状态、申请审批、审批通过/拒绝和重放前门禁
 -> v26 对齐 RabbitMQ profile 和 Actuator Rabbit health，默认本地启动不再因为未启用 RabbitMQ 而 health DOWN
 -> v27 增加重放审批历史流水，保留每次申请、拒绝和批准记录，并支持查询/导出
 -> v28 增加审批职责分离，禁止申请人审批自己的重放申请
 -> v29 增加失败事件操作员上下文解析器，把 X-Operator-* 头统一解析为可替换的操作员上下文，并提供页面身份校验入口
 -> v30 增加失败事件动作级角色策略，让管理、申请、审批、重放按不同角色集合独立授权
 -> v31 增加操作员动作权限快照，身份探针直接返回当前角色可执行和不可执行动作
 -> v32 增加页面动作权限预检，身份校验后禁用当前角色未授权的失败事件写按钮
 -> v33 增加页面写操作本地权限守卫，防止禁用按钮被脚本绕过后继续触发未授权动作

common
 -> 业务异常和统一错误响应

static
 -> 失败事件管理静态页面、重放工作台、操作员身份校验、动作级角色提示、操作员动作权限摘要、动作权限预检按钮禁用、写操作本地权限守卫、重放审批按钮、自提自审拦截提示、审批历史面板、二次确认弹窗、风险提示、样式和浏览器端交互脚本
```

后续建议升级顺序：

1. 在 `FailedEventOperatorContextResolver` 后面接入真实认证鉴权，把 `X-Operator-*` 请求头替换成登录态和权限上下文。
2. 给失败事件管理页面增加真实登录态、权限控制和当前用户展示。
3. 接入 Redis，训练热点商品缓存、限流、幂等 token。
4. 接入 OpenTelemetry、Prometheus、Grafana。
5. 增加并发库存压测和更多 Testcontainers 多中间件集成测试。
