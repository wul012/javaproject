# Advanced Order Platform

一个面向高级 Java 练手的订单交易平台雏形。当前采用模块化单体架构，重点训练 Spring Boot 业务建模、事务编排、库存一致性、审计流水、Outbox 和数据库工程化。

## 当前能力

- 商品目录查询
- 幂等下单，支持同 key 同请求重放和同 key 不同请求稳定拒绝
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
- 失败事件动作级角色策略启动期一致性校验，防止动作角色越过全局允许角色或系统角色不可重放
- 失败事件操作员上下文返回当前角色可执行和不可执行动作快照
- 失败事件操作员上下文返回动作权限决策明细，包含每个动作是否允许和允许角色
- 失败事件管理页面按校验后的动作权限禁用未授权写按钮
- 失败事件管理页面写操作本地权限守卫，防止绕过禁用按钮触发未授权动作
- 失败事件管理页面可视化展示当前操作员动作权限决策
- 订单平台只读运行概览接口，汇总应用、订单、库存、Outbox 和失败事件风险信号
- 订单平台只读运行证据接口和静态样本，汇总 replay、审批、Outbox、版本和执行阻断信号
- 订单平台 release approval rehearsal 只读聚合接口，汇总审批演练输入、live 信号和禁止执行边界
- 订单平台 release approval rehearsal 只读请求上下文，回显 request id、operator identity 和 audit correlation 来源
- 订单平台 release approval rehearsal 只读失败分类，区分上游就绪、身份上下文和审计关联 warning
- 失败事件治理摘要接口，汇总失败事件积压、审批状态和最近治理活动时间
- 失败事件重放 readiness 接口，只读说明某条失败事件能否重放、阻断原因和下一步动作
- 失败事件重放 simulation 接口，只读预演真实重放可能产生的副作用和阻断原因
- 失败事件重放 approval-status 接口，只读暴露 Java 保存的审批状态、最近审批动作和下一步动作
- 失败事件重放 approval-status digest，给 Node 上游证据校验提供稳定摘要
- 失败事件重放 execution-contract 接口，只读说明真实重放前 Java 会检查的状态、审批、digest 和请求条件
- 失败事件重放 execution-contract 稳定样本，给 Node fixture-driven smoke 提供 approved / blocked 真实格式参考
- 失败事件重放 audit evidence 稳定样本，给控制面判断真实执行是否可追溯提供 approved / blocked 参考
- 失败事件重放 evidence index 接口，只读汇总 live evidence、静态样本、operator/auth 边界、审计身份字段和执行安全规则
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

下单幂等边界：

```text
同一个 Idempotency-Key + 相同 customerId/items
 -> 返回已有订单，HTTP 200，不再次预占库存，不再次写 Outbox

同一个 Idempotency-Key + 不同 customerId/items
 -> 返回 HTTP 409，错误码 IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST
 -> 在库存预占和 Outbox 写入之前拒绝
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
校验后直接展示每个动作的允许/禁止决策和允许角色
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
  "actionDecisions": {
    "MANAGE_FAILED_EVENT": {
      "action": "MANAGE_FAILED_EVENT",
      "allowed": true,
      "allowedRoles": ["ORDER_SUPPORT", "SRE", "SYSTEM"]
    },
    "REQUEST_REPLAY_APPROVAL": {
      "action": "REQUEST_REPLAY_APPROVAL",
      "allowed": true,
      "allowedRoles": ["ORDER_SUPPORT", "SRE", "SYSTEM"]
    },
    "REVIEW_REPLAY_APPROVAL": {
      "action": "REVIEW_REPLAY_APPROVAL",
      "allowed": true,
      "allowedRoles": ["SRE", "SYSTEM"]
    },
    "REPLAY_FAILED_EVENT": {
      "action": "REPLAY_FAILED_EVENT",
      "allowed": true,
      "allowedRoles": ["ORDER_SUPPORT", "SRE", "SYSTEM"]
    }
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

v34 起，页面会优先读取 `actionDecisions`，把当前区域涉及的动作渲染成可见决策标签。例如 `ORDER_SUPPORT` 在重放工作台会显示：

```text
申请 允许 ORDER_SUPPORT/SRE/SYSTEM
审批 禁止 SRE/SYSTEM
重放 允许 ORDER_SUPPORT/SRE/SYSTEM
```

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
    system-role: SYSTEM
```

默认策略里 `ORDER_SUPPORT` 可以管理失败事件、申请审批和执行重放，但不能审批重放申请；审批动作默认只允许 `SRE` 和 `SYSTEM`。

v35 起，应用启动时会校验失败事件角色策略：

```text
allowed-roles
 -> 必须至少包含一个角色

management-roles / replay-approval-request-roles / replay-approval-review-roles / replay-roles
 -> 必须至少包含一个角色
 -> 不能包含 allowed-roles 之外的角色

system-role
 -> 必须包含在 allowed-roles 中
 -> 必须包含在 replay-roles 中
```

如果配置不一致，应用会直接启动失败并提示 `invalid failed-event.replay role policy`，避免运行后才出现“页面显示允许角色，但后端永远不允许”的隐性配置问题。

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

查询订单平台只读运行概览：

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/ops/overview
```

返回结构示例：

```json
{
  "sampledAt": "2026-05-11T08:20:00.000Z",
  "application": {
    "name": "advanced-order-platform",
    "profiles": ["default"],
    "startedAt": "2026-05-11T08:19:30.000Z",
    "uptimeSeconds": 30
  },
  "orders": {
    "total": 0
  },
  "inventory": {
    "items": 3
  },
  "outbox": {
    "pending": 0
  },
  "failedEvents": {
    "total": 0,
    "pendingReplayApprovals": 0,
    "latestFailedAt": null
  }
}
```

该接口只做聚合读取，不触发重放，不修改订单、库存、Outbox 或失败事件状态，适合作为 Node 控制面的 Java 业务健康入口。

查询订单平台只读运行证据：

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/ops/evidence
```

返回结构示例：

```json
{
  "sampledAt": "2026-05-12T11:58:00.000Z",
  "evidenceVersion": "java-ops-evidence.v1",
  "service": {
    "name": "advanced-order-platform",
    "version": "0.1.0-SNAPSHOT",
    "profiles": ["default"],
    "startedAt": "2026-05-12T11:57:30.000Z",
    "uptimeSeconds": 30
  },
  "healthProbe": {
    "endpoint": "/actuator/health",
    "method": "GET",
    "expectedStatus": "UP",
    "evidenceEndpoint": "/api/v1/ops/evidence",
    "additionalProbeEndpoints": [
      "/api/v1/ops/overview",
      "/contracts/ops-read-only-evidence.sample.json",
      "/contracts/order-idempotency-boundary.sample.json",
      "/contracts/order-idempotency-store-abstraction.sample.json",
      "/contracts/release-verification-manifest.sample.json",
      "/contracts/deployment-rollback-evidence.sample.json"
    ],
    "liveProbeRequiredForPass": true,
    "staticSampleOnly": false
  },
  "readOnly": true,
  "executionAllowed": false,
  "readOnlyWindow": {
    "windowVersion": "java-read-only-window.v1",
    "operatorStartRequired": true,
    "nodeAutoStartAllowed": false,
    "upstreamProbesRequired": true,
    "upstreamActionsAllowed": false,
    "readyForReadOnlyLiveProbe": true,
    "readyForProductionOperations": false,
    "allowedProbeEndpoints": [
      "GET /actuator/health",
      "GET /api/v1/ops/overview",
      "GET /api/v1/ops/evidence",
      "GET /contracts/ops-read-only-evidence.sample.json",
      "GET /contracts/order-idempotency-boundary.sample.json",
      "GET /contracts/order-idempotency-store-abstraction.sample.json",
      "GET /contracts/release-verification-manifest.sample.json",
      "GET /contracts/deployment-rollback-evidence.sample.json"
    ],
    "forbiddenOperations": [
      "POST /api/v1/orders",
      "POST /api/v1/failed-events/{id}/replay",
      "RabbitMQ replay publish",
      "Outbox mutation",
      "Any non-GET Node upstream action"
    ],
    "requiredNodeEnvironment": [
      "UPSTREAM_PROBES_ENABLED=true",
      "UPSTREAM_ACTIONS_ENABLED=false"
    ],
    "replayPostBoundary": "Node real-read window must not call POST /api/v1/failed-events/{id}/replay"
  },
  "orderIdempotency": {
    "boundaryVersion": "java-order-idempotency-boundary.v1",
    "storeAbstractionVersion": "java-idempotency-store.v1",
    "createOrderEndpoint": "/api/v1/orders",
    "createOrderMethod": "POST",
    "requiredHeader": "Idempotency-Key",
    "maxKeyLength": 120,
    "requestFingerprintVersion": "order-create-request-sha256.v1",
    "sameKeySameRequestOutcome": "HTTP 200 replay of the existing order without a second inventory reservation or outbox event",
    "sameKeyDifferentRequestOutcome": "HTTP 409 conflict before inventory reservation and before outbox mutation",
    "sameKeyDifferentRequestErrorCode": "IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST",
    "activeStore": "jpa-order-idempotency-store",
    "activeStoreImplementation": "JpaIdempotencyStore",
    "activeStoreMode": "JPA_DATABASE",
    "authoritativeStore": "orders table via orders.idempotency_key and orders.idempotency_request_fingerprint",
    "storeCandidates": [
      {
        "name": "jpa-order-idempotency-store",
        "role": "ORDER_CREATE_IDEMPOTENCY_AUTHORITY",
        "enabled": true,
        "connected": true,
        "mode": "JPA_DATABASE"
      },
      {
        "name": "mini-kv-ttl-token-adapter",
        "role": "TTL_TOKEN_CANDIDATE",
        "enabled": false,
        "connected": false,
        "mode": "DISABLED_CANDIDATE_ONLY"
      }
    ],
    "miniKvConnected": false,
    "externalTokenStoreConnected": false,
    "changesPaymentOrInventoryTransaction": false
  },
  "releaseVerification": {
    "manifestVersion": "java-release-verification-manifest.v1",
    "manifestEndpoint": "/contracts/release-verification-manifest.sample.json",
    "verificationMode": "LOCAL_OPERATOR_EXECUTES_AND_ARCHIVES_RESULTS",
    "requiredChecks": [
      "focused-maven-tests",
      "non-docker-regression-tests",
      "maven-package",
      "http-smoke",
      "static-contract-json-validation"
    ],
    "staticContractEndpoints": [
      "/contracts/ops-read-only-evidence.sample.json",
      "/contracts/ops-evidence-field-guide.sample.json",
      "/contracts/order-idempotency-boundary.sample.json",
      "/contracts/order-idempotency-store-abstraction.sample.json",
      "/contracts/release-verification-manifest.sample.json",
      "/contracts/deployment-rollback-evidence.sample.json"
    ],
    "nodeMayExecuteBuild": false,
    "nodeMayTriggerWrites": false,
    "changesBusinessSemantics": false,
    "requiresProductionSecrets": false
  },
  "deploymentRollback": {
    "evidenceVersion": "java-deployment-rollback-evidence.v1",
    "evidenceEndpoint": "/contracts/deployment-rollback-evidence.sample.json",
    "rollbackMode": "READ_ONLY_BOUNDARY_SAMPLE",
    "rollbackSubjects": [
      "java-package",
      "runtime-configuration",
      "database-migrations",
      "static-contracts"
    ],
    "requiresOperatorConfirmation": [
      "artifact-version-target",
      "configuration-secret-source",
      "database-migration-direction"
    ],
    "packageRollbackSupported": true,
    "configRollbackSupported": true,
    "databaseMigrationRollbackAutomatic": false,
    "contractsRollbackByArtifactVersion": true,
    "nodeMayTriggerRollback": false,
    "requiresProductionDatabase": false,
    "changesOrderTransactionSemantics": false
  },
  "failedEventReplay": {
    "totalFailedEvents": 2,
    "replayBacklog": 2,
    "pendingReplayApprovals": 1,
    "approvedReplayApprovals": 1,
    "rejectedReplayApprovals": 0,
    "latestFailedAt": "2026-05-12T11:57:45.000Z",
    "latestApprovalAt": "2026-05-12T11:57:50.000Z",
    "realReplayEndpoint": "/api/v1/failed-events/{id}/replay",
    "realReplayAllowedByEvidence": false
  },
  "outbox": {
    "pendingEvents": 0,
    "publisherEnabled": false,
    "rabbitMqEnabled": false,
    "exchange": "order-platform.outbox",
    "queue": "order-platform.outbox.events",
    "deadLetterQueue": "order-platform.outbox.events.dlq",
    "blockers": ["OUTBOX_PUBLISHER_DISABLED", "RABBITMQ_OUTBOX_DISABLED"]
  },
  "approvalExecution": {
    "requiredApprovalStatus": "APPROVED",
    "digestVerificationMode": "contractDigest must match latest approval-status/readiness evidence before POST /replay",
    "approvalRequired": true,
    "dryRun": true,
    "executionBlockers": ["READ_ONLY_EVIDENCE_ENDPOINT", "REPLAY_APPROVAL_PENDING"],
    "nextEvidenceActions": [
      "GET /api/v1/failed-events/summary",
      "GET /api/v1/failed-events/{id}/replay-readiness",
      "GET /api/v1/failed-events/{id}/replay-execution-contract"
    ]
  },
  "blockers": ["READ_ONLY_EVIDENCE_ENDPOINT", "OUTBOX_PUBLISHER_DISABLED", "RABBITMQ_OUTBOX_DISABLED"],
  "warnings": ["APPROVED_REPLAY_REQUIRES_DIGEST_CHECK"],
  "evidenceEndpoints": [
    "/api/v1/ops/overview",
    "/api/v1/ops/evidence",
    "/contracts/ops-read-only-evidence.sample.json",
    "/contracts/ops-evidence-field-guide.sample.json",
    "/contracts/order-idempotency-boundary.sample.json",
    "/contracts/order-idempotency-store-abstraction.sample.json",
    "/contracts/release-verification-manifest.sample.json",
    "/contracts/deployment-rollback-evidence.sample.json",
    "/api/v1/failed-events/summary",
    "/api/v1/failed-events/{id}/replay-execution-contract",
    "/api/v1/failed-events/replay-evidence-index"
  ]
}
```

该接口服务于控制面读证据，不执行 replay，不申请/审批 replay approval，不写 Outbox，不改订单状态。`executionAllowed=false` 是接口自身的安全边界；真正执行仍必须走已有 `POST /api/v1/failed-events/{id}/replay`，并由 Java 的审批、digest 和 readiness 规则重新校验。

v49 起，应用随包提供 ops read-only evidence 静态样本：

```text
src/main/resources/static/contracts/ops-read-only-evidence.sample.json
```

启动应用后可直接读取：

```powershell
Invoke-RestMethod http://localhost:8080/contracts/ops-read-only-evidence.sample.json
```

该样本固定表达：

```text
readOnly=true
executionAllowed=false
realReplayAllowedByEvidence=false
publisherEnabled=false
rabbitMqEnabled=false
productionPassBoundary.readyForProductionPassEvidence=false
allowedProbeEndpoints 只允许 GET 健康、ops 只读接口和随包静态契约样本
forbiddenOperations 明确禁止订单写操作、失败事件 replay POST 和 RabbitMQ replay publish
```

它服务于 Node production pass evidence archive verification 的上游引用位，只证明 Java 只读 evidence 的稳定结构，不代表 live upstream pass，也不允许任何写操作。

v50 起，动态 `/api/v1/ops/evidence` 补充真实只读窗口自描述：

```text
healthProbe.endpoint=/actuator/health
healthProbe.expectedStatus=UP
readOnlyWindow.windowVersion=java-read-only-window.v1
readOnlyWindow.operatorStartRequired=true
readOnlyWindow.nodeAutoStartAllowed=false
readOnlyWindow.upstreamProbesRequired=true
readOnlyWindow.upstreamActionsAllowed=false
readOnlyWindow.readyForReadOnlyLiveProbe=true
readOnlyWindow.readyForProductionOperations=false
readOnlyWindow.requiredNodeEnvironment=UPSTREAM_PROBES_ENABLED=true + UPSTREAM_ACTIONS_ENABLED=false
readOnlyWindow.forbiddenOperations 包含订单写入、失败事件 replay POST、RabbitMQ replay publish、Outbox mutation 和任何非 GET Node 上游动作
```

静态样本中的 `readOnlyWindow.readyForReadOnlyLiveProbe=false`，因为样本只证明字段形状；只有启动后的动态 `/api/v1/ops/evidence` 才能作为真实只读 probe 的 Java 侧输入。

v51 起，应用随包提供 ops evidence 字段说明样本：

```text
src/main/resources/static/contracts/ops-evidence-field-guide.sample.json
```

启动应用后可直接读取：

```powershell
Invoke-RestMethod http://localhost:8080/contracts/ops-evidence-field-guide.sample.json
```

该说明固定表达：

```text
guideVersion=java-ops-evidence-field-guide.v1
sourceEvidenceEndpoint=/api/v1/ops/evidence
sourceSampleEndpoint=/contracts/ops-read-only-evidence.sample.json
releaseReviewUse.mayBeUsedForProductionPass=false
fieldGroups 覆盖 service、healthProbe、readOnlyWindow、orderIdempotency、executionBoundaries
forbiddenOperations 继续包含订单写入、失败事件 replay POST、RabbitMQ replay publish、Outbox mutation 和任何非 GET Node 上游动作
```

它服务于 Node read-only capture release evidence review，只解释 Java 字段语义和稳定性，不代表 live upstream pass，也不授予生产操作权限。

v52 起，应用随包提供订单幂等边界样本：

```text
src/main/resources/static/contracts/order-idempotency-boundary.sample.json
```

启动应用后可直接读取：

```powershell
Invoke-RestMethod http://localhost:8080/contracts/order-idempotency-boundary.sample.json
```

该样本固定表达：

```text
boundaryVersion=java-order-idempotency-boundary.v1
requestFingerprint.version=order-create-request-sha256.v1
sameKeySameRequest.httpStatus=200
sameKeyDifferentRequest.httpStatus=409
sameKeyDifferentRequest.errorCode=IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST
storage.miniKvConnected=false
storage.orderAuthoritativeStoreRemainsJavaDatabase=true
storage.abstraction=IdempotencyStore
storage.activeStore=jpa-order-idempotency-store
```

它服务于 Node idempotency vertical readiness review，只说明 Java 内部订单幂等边界；当前版本不接 mini-kv，不改变支付、库存或失败事件 replay 行为。

v53 起，应用随包提供订单幂等存储抽象样本：

```text
src/main/resources/static/contracts/order-idempotency-store-abstraction.sample.json
```

启动应用后可直接读取：

```powershell
Invoke-RestMethod http://localhost:8080/contracts/order-idempotency-store-abstraction.sample.json
```

该样本固定表达：

```text
abstractionVersion=java-idempotency-store.v1
activeStore.name=jpa-order-idempotency-store
activeStore.implementation=JpaIdempotencyStore
activeStore.mode=JPA_DATABASE
disabledCandidates[0].name=mini-kv-ttl-token-adapter
disabledCandidates[0].enabled=false
disabledCandidates[0].connected=false
boundaries.orderAuthoritativeStoreRemainsJavaDatabase=true
boundaries.changesPaymentOrInventoryTransaction=false
boundaries.nodeMayTriggerWrites=false
```

它把 Java 内部幂等查找/保存封装成 `IdempotencyStore`，默认仍使用 `orders` 表；mini-kv 只作为后续短 TTL token 候选适配器被说明，不接入 `POST /api/v1/orders` 交易主链路。

v54 起，应用随包提供发布验证 manifest 样本：

```text
src/main/resources/static/contracts/release-verification-manifest.sample.json
```

启动应用后可直接读取：

```powershell
Invoke-RestMethod http://localhost:8080/contracts/release-verification-manifest.sample.json
```

该样本固定表达：

```text
manifestVersion=java-release-verification-manifest.v1
verificationChecks 包含 focused-maven-tests、non-docker-regression-tests、maven-package、http-smoke、static-contract-json-validation
staticContracts 列出 ops evidence、field guide、order idempotency boundary、idempotency store abstraction、release manifest、deployment rollback evidence、release bundle manifest 和 rollback approval handoff
releaseGate.nodeMayExecuteMaven=false
releaseGate.nodeMayTriggerJavaWrites=false
boundaries.changesOrderCreateSemantics=false
boundaries.connectsMiniKv=false
archiveExpectation.runtimeArchiveRoot=c/<version>
```

它服务于 Node cross-project release verification intake gate，只作为 Java 发布验证清单；Node 可以读取归档证据，但不替 Java 执行 Maven、不触发 Java 写接口。

v55 起，应用随包提供部署回退证据样本：

```text
src/main/resources/static/contracts/deployment-rollback-evidence.sample.json
```

启动应用后可直接读取：

```powershell
Invoke-RestMethod http://localhost:8080/contracts/deployment-rollback-evidence.sample.json
```

该样本固定表达：

```text
evidenceVersion=java-deployment-rollback-evidence.v1
rollbackSubjects 包含 java-package、runtime-configuration、database-migrations、static-contracts
packageRollback.supported=true
configurationRollback.supported=true
databaseMigrationRollback.automatic=false
staticContractRollback.byArtifactVersion=true
boundaries.nodeMayTriggerRollback=false
boundaries.requiresProductionDatabase=false
boundaries.changesOrderTransactionSemantics=false
```

它服务于部署回退前的只读证据审查：说明包版本、运行配置、数据库迁移和静态契约各自需要哪些人工确认；Node 可以读取该样本作为操作台依据，但不能触发 Java 回退、不能执行 Maven、不能连接生产库，也不改变订单事务语义。

v56 起，应用随包提供发布包 bundle manifest 样本：

```text
src/main/resources/static/contracts/release-bundle-manifest.sample.json
```

启动应用后可直接读取：

```powershell
Invoke-RestMethod http://localhost:8080/contracts/release-bundle-manifest.sample.json
```

该样本固定表达：

```text
manifestVersion=java-release-bundle-manifest.v1
bundleMode=READ_ONLY_RELEASE_BUNDLE
releaseSubject.artifact=target/advanced-order-platform-0.1.0-SNAPSHOT.jar
bundleInputs.releaseVerificationManifest=/contracts/release-verification-manifest.sample.json
bundleInputs.deploymentRollbackEvidence=/contracts/deployment-rollback-evidence.sample.json
bundleInputs.rollbackSqlReviewGate=/contracts/rollback-sql-review-gate.sample.json
verificationEvidence 包含 focused-maven-tests、non-docker-regression-tests、maven-package、http-smoke、static-contract-json-validation
nodeConsumption.nodeMayConsume=true
nodeConsumption.nodeMayExecuteMaven=false
nodeConsumption.nodeMayTriggerRollback=false
boundaries.requiresProductionDatabase=false
boundaries.changesOrderTransactionSemantics=false
```

它服务于 Node v164 cross-project release bundle gate 的 Java 上游输入：把 jar、静态 contracts、发布验证清单、回退证据和本版归档要求收成一份只读 bundle。Node 可以读取 bundle，但不能替 Java 执行 Maven、不能触发 Java 写接口或回退，也不需要生产数据库。

v57 起，应用随包提供 rollback approval handoff 样本：

```text
src/main/resources/static/contracts/rollback-approval-handoff.sample.json
```

启动应用后可直接读取：

```powershell
Invoke-RestMethod http://localhost:8080/contracts/rollback-approval-handoff.sample.json
```

该样本固定表达：

```text
handoffVersion=java-rollback-approval-handoff.v1
approvalMode=OPERATOR_CONFIRMATION_REQUIRED
requiredConfirmationFields 包含 artifact-version-target、runtime-config-profile、configuration-secret-source、database-migration-direction、release-bundle-manifest、deployment-rollback-evidence
handoffArtifacts 包含 release bundle manifest、deployment rollback evidence 和 release verification manifest
nodeConsumption.nodeMayConsume=true
nodeConsumption.nodeMayTriggerRollback=false
nodeConsumption.nodeMayExecuteRollbackSql=false
boundaries.rollbackSqlExecutionAllowed=false
boundaries.requiresProductionDatabase=false
boundaries.requiresProductionSecrets=false
```

它服务于后续 rollback window readiness checklist：Java 只提供人工审批交接字段和只读样本，不执行 rollback SQL，不读取生产密钥，不连接生产库，也不授权 Node 触发 Java 回退。

v58 起，应用随包提供 rollback SQL review gate 样本：

```text
src/main/resources/static/contracts/rollback-sql-review-gate.sample.json
```

启动应用后可直接读取：

```powershell
Invoke-RestMethod http://localhost:8080/contracts/rollback-sql-review-gate.sample.json
```

该样本固定表达：

```text
gateVersion=java-rollback-sql-review-gate.v1
gateMode=READ_ONLY_SQL_REVIEW_GATE
reviewOwner=database-release-owner
requiredReviewFields 包含 rollback-sql-review-owner、migration-direction、operator-approval-placeholder、rollback-sql-artifact-reference、production-database-access-boundary
migrationDirectionOptions 包含 forward-only、rollback-script-reviewed、no-database-change
operatorApprovalPlaceholder=operator-approval-required-before-any-sql-execution
nodeConsumption.nodeMayConsume=true
nodeConsumption.nodeMayTriggerRollback=false
nodeConsumption.nodeMayExecuteRollbackSql=false
boundaries.sqlExecutionAllowed=false
boundaries.requiresProductionDatabase=false
```

它服务于后续 rollback execution preflight contract：Java 只说明 SQL review owner、迁移方向和人工审批占位，不嵌入生产 SQL 明文，不执行 rollback SQL，不连接生产数据库，也不授权 Node 触发 Java 回退。

v59 起，应用随包提供 production secret source contract 样本：

```text
src/main/resources/static/contracts/production-secret-source-contract.sample.json
```

启动应用后可直接读取：

```powershell
Invoke-RestMethod http://localhost:8080/contracts/production-secret-source-contract.sample.json
```

该样本固定表达：

```text
contractVersion=java-production-secret-source-contract.v1
contractMode=READ_ONLY_SECRET_SOURCE_CONTRACT
selectedSourceType=external-secret-manager
secretManagerOwner=platform-security-owner
rotationOwner=security-operations-owner
reviewCadence=quarterly-or-before-production-cutover
requiredConfirmationFields 包含 secret-manager-or-source-type、secret-manager-owner、rotation-owner、review-cadence、secret-value-access-boundary
secretValueBoundaries 明确不读取、不写入、不嵌入 secret value
nodeConsumption.nodeMayConsume=true
nodeConsumption.nodeMayReadSecretValues=false
boundaries.requiresProductionSecrets=false
boundaries.requiresProductionDatabase=false
```

它服务于后续 production environment preflight checklist：Java 只记录生产密钥来源类型、负责人和轮换审查节奏，不读取 secret value，不写入 secret 名称或原始环境变量值，也不授权 Node 修改 Java 运行配置。

v60 起，应用随包提供 production deployment runbook contract 样本：

```text
src/main/resources/static/contracts/production-deployment-runbook-contract.sample.json
```

启动应用后可直接读取：

```powershell
Invoke-RestMethod http://localhost:8080/contracts/production-deployment-runbook-contract.sample.json
```

该样本固定表达：

```text
contractVersion=java-production-deployment-runbook-contract.v1
contractMode=READ_ONLY_DEPLOYMENT_RUNBOOK_CONTRACT
deploymentWindow.owner=release-window-owner
deploymentWindow.rollbackApprover=rollback-approval-owner
databaseMigration.selectedDirection=no-database-change
secretSourceConfirmation.endpoint=/contracts/production-secret-source-contract.sample.json
requiredConfirmationFields 包含 deployment-window-owner、rollback-approver、database-migration-direction、secret-source-confirmation、rollback-sql-review-gate、operator-approval-placeholder
nodeConsumption.nodeMayConsume=true
nodeConsumption.nodeMayTriggerDeployment=false
nodeConsumption.nodeMayTriggerRollback=false
boundaries.sqlExecutionAllowed=false
boundaries.requiresProductionDatabase=false
```

它服务于后续 deployment evidence intake gate：Java 只说明部署窗口 owner、rollback approver、迁移方向和密钥来源确认，不执行部署、不执行 rollback SQL、不连接生产数据库，也不授权 Node 触发 Java 发布或回退。

v61 起，应用随包提供 rollback approval record fixture：

```text
src/main/resources/static/contracts/rollback-approval-record.fixture.json
```

启动应用后可直接读取：

```powershell
Invoke-RestMethod http://localhost:8080/contracts/rollback-approval-record.fixture.json
```

该样本固定表达：

```text
fixtureVersion=java-rollback-approval-record-fixture.v1
fixtureMode=READ_ONLY_APPROVAL_RECORD_FIXTURE
approvalRecord.reviewer=rollback-reviewer-placeholder
approvalRecord.approvalTimestampPlaceholder=approval-timestamp-placeholder
approvalRecord.rollbackTarget=release-tag-or-artifact-version-placeholder
databaseMigration.selectedDirection=no-database-change
requiredRecordFields 包含 reviewer、approval-timestamp-placeholder、rollback-target、database-migration-direction、rollback-sql-review-gate、no-secret-value-boundary
nodeConsumption.nodeMayConsume=true
nodeConsumption.nodeMayTriggerRollback=false
nodeConsumption.nodeMayExecuteRollbackSql=false
boundaries.rollbackExecutionAllowed=false
boundaries.rollbackSqlExecutionAllowed=false
boundaries.requiresProductionDatabase=false
```

它服务于后续 release window readiness packet：Java 只提供人工审批记录形状，记录 reviewer、审批时间占位、rollback target、迁移方向和 no-secret-value 边界；它不执行 rollback，不执行 SQL，不连接生产数据库，也不读取或嵌入 secret value。

v62 起，应用随包提供 release handoff checklist fixture：

```text
src/main/resources/static/contracts/release-handoff-checklist.fixture.json
```

启动应用后可直接读取：

```powershell
Invoke-RestMethod http://localhost:8080/contracts/release-handoff-checklist.fixture.json
```

该样本固定表达：

```text
fixtureVersion=java-release-handoff-checklist-fixture.v1
fixtureMode=READ_ONLY_RELEASE_HANDOFF_CHECKLIST_FIXTURE
releaseChecklist.releaseOperator=release-operator-placeholder
releaseChecklist.rollbackApprover=rollback-approver-placeholder
releaseChecklist.artifactTarget=release-tag-or-artifact-version-placeholder
databaseMigration.selectedDirection=no-database-change
secretSourceConfirmation.endpoint=/contracts/production-secret-source-contract.sample.json
requiredChecklistFields 包含 release-operator、rollback-approver、artifact-target、database-migration-direction、secret-source-confirmation、deployment-runbook-contract、rollback-approval-record-fixture、no-secret-value-boundary
nodeConsumption.nodeMayConsume=true
nodeConsumption.nodeMayTriggerDeployment=false
nodeConsumption.nodeMayTriggerRollback=false
boundaries.deploymentExecutionAllowed=false
boundaries.rollbackSqlExecutionAllowed=false
boundaries.requiresProductionDatabase=false
```

它服务于后续 Node v175 release handoff readiness review：Java 只记录发布执行前人工 checklist 的字段形状和证据引用，不执行部署、不执行回退、不执行 SQL、不连接生产数据库，也不读取或嵌入 secret value。

同时 v62 对 ops evidence 的静态 contract endpoint 列表做了轻量收口：`healthProbe`、`readOnlyWindow`、`releaseVerification`、`releaseBundle` 和 `evidenceEndpoints` 共享 helper 生成静态 contract 清单，后续新增 fixture 时不需要在多处重复维护同一串 endpoint。

v64 起，应用随包提供 release operator signoff fixture：

```powershell
Invoke-RestMethod http://localhost:8080/contracts/release-operator-signoff.fixture.json
```

该样本固定表达：

```text
fixtureVersion=java-release-operator-signoff-fixture.v1
fixtureMode=READ_ONLY_RELEASE_OPERATOR_SIGNOFF_FIXTURE
signoffRecord.releaseOperator=release-operator-placeholder
signoffRecord.rollbackApprover=rollback-approver-placeholder
signoffRecord.releaseWindow=release-window-placeholder
signoffRecord.artifactTarget=release-tag-or-artifact-version-placeholder
signoffRecord.operatorSignoffPlaceholder=operator-signoff-placeholder
requiredSignoffFields 包含 release-operator、rollback-approver、release-window、artifact-target、operator-signoff-placeholder、release-audit-retention-fixture、no-secret-value-boundary
nodeConsumption.nodeMayConsume=true
nodeConsumption.nodeMayCreateApprovalDecision=false
nodeConsumption.nodeMayTriggerDeployment=false
nodeConsumption.nodeMayTriggerRollback=false
boundaries.approvalDecisionCreated=false
boundaries.approvalLedgerWriteAllowed=false
boundaries.deploymentExecutionAllowed=false
boundaries.rollbackSqlExecutionAllowed=false
boundaries.requiresProductionDatabase=false
```

它服务于后续 Node v180 approval decision prerequisite gate：Java 只记录审批决定前需要人工确认的 release operator signoff 元数据，不创建 approval decision，不写 approval ledger，不执行部署、不执行回退、不执行 SQL，也不读取或嵌入 secret value。

v65 起，应用随包提供 rollback approver evidence fixture：

```powershell
Invoke-RestMethod http://localhost:8080/contracts/rollback-approver-evidence.fixture.json
```

该样本固定表达：

```text
fixtureVersion=java-rollback-approver-evidence-fixture.v1
fixtureMode=READ_ONLY_ROLLBACK_APPROVER_EVIDENCE_FIXTURE
approverEvidence.rollbackApprover=rollback-approver-placeholder
approverEvidence.evidenceStatus=PENDING_OPERATOR_CONFIRMATION
databaseMigration.selectedDirection=no-database-change
databaseMigration.rollbackSqlArtifactReference=rollback-sql-artifact-reference-placeholder
databaseMigration.rollbackSqlTextEmbedded=false
databaseMigration.rollbackSqlExecutionAllowed=false
databaseMigration.requiresProductionDatabase=false
databaseMigration.productionDatabaseBoundary=production-database-connection-outside-this-fixture
nodeConsumption.nodeMayConsume=true
nodeConsumption.nodeMayCreateApprovalDecision=false
nodeConsumption.nodeMayTriggerRollback=false
nodeConsumption.nodeMayExecuteRollbackSql=false
boundaries.approvalDecisionCreated=false
boundaries.approvalLedgerWriteAllowed=false
boundaries.rollbackExecutionAllowed=false
boundaries.rollbackSqlExecutionAllowed=false
boundaries.requiresProductionDatabase=false
```

它服务于后续 Node v182 release approval decision rehearsal packet：Java 只记录 rollback approver、migration direction、rollback SQL artifact reference 和生产数据库边界的只读证据，不创建 approval decision，不写 approval ledger，不执行 rollback，不执行 rollback SQL，也不连接生产数据库。

v66 起，应用提供 release approval rehearsal 只读聚合入口：

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/ops/release-approval-rehearsal
```

该入口固定表达：

```text
rehearsalVersion=java-release-approval-rehearsal.v1
rehearsalMode=READ_ONLY_RELEASE_APPROVAL_REHEARSAL
sourceEvidenceEndpoint=/api/v1/ops/evidence
releaseApprovalInputs 包含 release operator signoff、rollback approver evidence、approval record、release bundle、verification manifest、deployment rollback、runbook、secret source 和 rollback SQL review gate
liveSignals 汇总 pending/approved/rejected replay approvals、replay backlog、pending outbox events 和 dry-run 状态
executionBoundaries.nodeMayConsume=true
executionBoundaries.nodeMayCreateApprovalDecision=false
executionBoundaries.nodeMayWriteApprovalLedger=false
executionBoundaries.nodeMayTriggerDeployment=false
executionBoundaries.nodeMayTriggerRollback=false
executionBoundaries.nodeMayExecuteRollbackSql=false
executionBoundaries.requiresProductionDatabase=false
executionBoundaries.requiresProductionSecrets=false
requiredNodeEnvironment 包含 UPSTREAM_PROBES_ENABLED=true、UPSTREAM_ACTIONS_ENABLED=false
```

它服务于 Node v185 real-read rehearsal intake 前的 Java 侧真实运行纵深准备：Node 可以只读读取这个聚合响应，减少拼接多个 Java evidence 的成本；但 Java 仍不创建 approval decision，不写 approval ledger，不执行 deployment、rollback 或 rollback SQL，也不接触生产数据库和生产密钥。

v67 起，release approval rehearsal 支持只读请求上下文头：

```powershell
Invoke-RestMethod `
  -Uri http://localhost:8080/api/v1/ops/release-approval-rehearsal `
  -Headers @{
    "X-Rehearsal-Request-Id" = "rehearsal-v67-001"
    "X-Operator-Identity" = "release-operator@example.test"
    "X-Audit-Correlation-Id" = "audit-correlation-v67"
  }
```

响应中的 `requestContext` 会回显这些只读线索，并标明来源：

```text
requestContext.contextVersion=java-release-approval-rehearsal-context.v1
requestContext.requestId=rehearsal-v67-001
requestContext.requestIdSource=X-Rehearsal-Request-Id
requestContext.operatorIdentity=release-operator@example.test
requestContext.operatorIdentitySource=X-Operator-Identity
requestContext.auditCorrelationId=audit-correlation-v67
requestContext.auditCorrelationSource=X-Audit-Correlation-Id
requestContext.operatorAuthenticatedByJava=false
requestContext.persistedByJava=false
requestContext.approvalLedgerWritten=false
requestContext.requiresProductionIdentityProvider=false
requestContext.acceptedReadOnlyHeaders 包含 X-Rehearsal-Request-Id、X-Operator-Identity、X-Audit-Correlation-Id
```

如果没有传入这些 header，接口会返回稳定占位值，并在 `contextWarnings` 中标出 `REHEARSAL_REQUEST_ID_MISSING`、`OPERATOR_IDENTITY_MISSING`、`AUDIT_CORRELATION_ID_MISSING`。这一步只是为后续真实认证、持久化审计和 Node real-read adapter 提供前置证据形状：Java 不认证该身份、不写数据库、不写 approval ledger，也不授权任何审批、部署、回滚或 SQL 执行。

v68 起，release approval rehearsal 在只读响应中增加失败分类字段，方便 Node real-read adapter 后续把读取失败或前置条件不足分成可操作原因：

```text
failureTaxonomy.taxonomyVersion=java-release-approval-rehearsal-failure-taxonomy.v1
failureTaxonomy.upstreamReadiness=READY
failureTaxonomy.authContextReadiness=WARNING
failureTaxonomy.auditCorrelationReadiness=WARNING
failureTaxonomy.javaReadOnlyUpstreamReady=true
failureTaxonomy.authContextComplete=false
failureTaxonomy.auditCorrelationPresent=false
failureTaxonomy.retryableByReadOnlyAdapter=true
failureTaxonomy.writeActionRequired=false
failureTaxonomy.failureCategories 包含 AUTH_CONTEXT_WARNING、AUDIT_CORRELATION_WARNING、READ_ONLY_EXECUTION_BLOCKED
failureTaxonomy.taxonomyWarnings 包含 REQUEST_ID_OR_OPERATOR_IDENTITY_MISSING、AUDIT_CORRELATION_ID_MISSING、REHEARSAL_REMAINS_READ_ONLY
```

当 `X-Rehearsal-Request-Id`、`X-Operator-Identity` 和 `X-Audit-Correlation-Id` 都存在时，`authContextReadiness` 与 `auditCorrelationReadiness` 会变成 `READY`，对应 warning 分类会消失；但 `READ_ONLY_EXECUTION_BLOCKED` 仍会保留，继续说明本接口只是 rehearsal evidence，不是审批、ledger、部署、回滚或 SQL 的执行授权。

查询失败事件治理摘要：

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/failed-events/summary
```

返回结构示例：

```json
{
  "sampledAt": "2026-05-11T10:40:00.000Z",
  "totalFailedEvents": 4,
  "pendingReplayApprovals": 1,
  "approvedReplayApprovals": 1,
  "rejectedReplayApprovals": 1,
  "latestFailedAt": "2026-05-11T10:35:00.000Z",
  "latestApprovalAt": "2026-05-11T10:38:00.000Z",
  "replayBacklog": 3
}
```

`replayBacklog` 表示尚未成功进入 `REPLAYED` 状态的失败事件数量。该接口只做聚合读取，不触发重放，不审批重放申请，也不修改失败事件状态。

查询单个失败事件的重放 readiness：

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/failed-events/1/replay-readiness
```

返回结构示例：

```json
{
  "sampledAt": "2026-05-11T12:20:00Z",
  "failedEventId": 1,
  "exists": true,
  "eventType": "OrderNotificationFailed",
  "aggregateType": "ORDER",
  "aggregateId": "1001",
  "failedAt": "2026-05-11T12:00:00Z",
  "managementStatus": "OPEN",
  "replayApprovalStatus": "APPROVED",
  "replayBacklogPosition": 3,
  "eligibleForReplay": true,
  "requiresApproval": false,
  "blockedBy": [],
  "warnings": [],
  "nextAllowedActions": ["REPLAY_FAILED_EVENT"],
  "latestReplayAttempt": null,
  "latestApproval": {
    "action": "APPROVED",
    "status": "APPROVED",
    "operatorId": "sre-user",
    "operatorRole": "SRE",
    "note": "approved",
    "changedAt": "2026-05-11T12:10:00Z"
  }
}
```

如果失败事件不存在，接口仍返回稳定 JSON：

```json
{
  "failedEventId": 999999,
  "exists": false,
  "eligibleForReplay": false,
  "blockedBy": ["FAILED_EVENT_NOT_FOUND"],
  "nextAllowedActions": []
}
```

该接口只读取失败事件、审批历史和重放尝试，不执行 `POST /replay`，不创建审批，也不修改管理状态。`eligibleForReplay=false` 时，`blockedBy` 会说明硬阻断原因，例如审批未通过、RabbitMQ Outbox 未开启、事件关键字段缺失或已经重放；`nextAllowedActions` 给 Node 控制面展示下一步可走的预演动作。

查询单个失败事件的重放 simulation：

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/failed-events/1/replay-simulation
```

返回结构示例：

```json
{
  "sampledAt": "2026-05-12T13:20:00Z",
  "failedEventId": 1,
  "exists": true,
  "eligibleForReplay": true,
  "wouldReplay": true,
  "wouldPublishOutbox": true,
  "wouldChangeManagementStatus": false,
  "requiredApprovalStatus": "APPROVED",
  "idempotencyKeyHint": "failed-event-replay:1:1001",
  "expectedAggregateId": "1001",
  "expectedSideEffects": [
    "PUBLISH_RABBITMQ_REPLAY_MESSAGE",
    "SAVE_REPLAY_ATTEMPT_AUDIT",
    "MARK_FAILED_EVENT_REPLAYED_ON_SUCCESS",
    "MARK_FAILED_EVENT_REPLAY_FAILED_ON_BROKER_ERROR"
  ],
  "blockedBy": [],
  "warnings": [],
  "nextAllowedActions": ["REPLAY_FAILED_EVENT"]
}
```

simulation 复用 readiness 的资格判断，再补充真实 replay 可能产生的影响。`wouldReplay=true` 表示如果此时调用真实重放接口，预计会进入 RabbitMQ Outbox 发布路径；`wouldChangeManagementStatus=false` 表示当前真实 replay 逻辑不会修改失败事件管理状态。该接口不调用 RabbitMQ，不写重放审计，不改变 `REPLAYED` / `REPLAY_FAILED` 状态，适合 Node 在 operation execution preview 里展示预计副作用。

查询单个失败事件的重放审批状态：

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/failed-events/1/approval-status
```

返回结构示例：

```json
{
  "sampledAt": "2026-05-12T13:30:00Z",
  "failedEventId": 1,
  "exists": true,
  "evidenceVersion": "failed-event-approval-status.v1",
  "approvalDigest": "sha256:...",
  "replayEligibilityDigest": "sha256:...",
  "failedEventStatus": "RECORDED",
  "managementStatus": "OPEN",
  "approvalStatus": "APPROVED",
  "requiredApprovalStatus": "APPROVED",
  "approvalRequested": true,
  "approvalPending": false,
  "approvedForReplay": true,
  "rejected": false,
  "requestReason": "need replay",
  "requestedBy": "ops-user",
  "requestedAt": "2026-05-12T08:05:00Z",
  "reviewedBy": "sre-user",
  "reviewedAt": "2026-05-12T08:10:00Z",
  "reviewNote": "approved",
  "historyCount": 2,
  "latestApproval": {
    "action": "APPROVED",
    "status": "APPROVED",
    "operatorId": "sre-user",
    "operatorRole": "SRE",
    "note": "approved",
    "changedAt": "2026-05-12T08:10:00Z"
  },
  "approvalBlockedBy": [],
  "nextAllowedActions": ["REPLAY_FAILED_EVENT"]
}
```

如果失败事件不存在，接口仍返回稳定 JSON：

```json
{
  "failedEventId": 999999,
  "exists": false,
  "evidenceVersion": "failed-event-approval-status.v1",
  "approvalDigest": "sha256:...",
  "replayEligibilityDigest": "sha256:...",
  "requiredApprovalStatus": "APPROVED",
  "approvedForReplay": false,
  "historyCount": 0,
  "approvalBlockedBy": ["FAILED_EVENT_NOT_FOUND"],
  "nextAllowedActions": []
}
```

`approval-status` 只读取失败事件当前审批字段和最近审批流水，不申请审批、不审核审批、不执行重放，也不修改失败事件状态。它和 readiness / simulation 的边界是：readiness 判断整体是否可重放，simulation 预演真实重放副作用，approval-status 只回答 Java 当前保存的 replay approval 状态，方便 Node 后续核对审批证据链。

v41 起，`approval-status` 增加三个证据字段：

```text
evidenceVersion
 -> 当前响应证据格式版本，固定为 failed-event-approval-status.v1

approvalDigest
 -> 对审批证据字段计算的 SHA-256 摘要，不包含 sampledAt

replayEligibilityDigest
 -> 对审批层面的可重放判断字段计算的 SHA-256 摘要，不包含 sampledAt
```

两个 digest 都以 `sha256:` 开头。同一条失败事件在审批状态不变时重复读取，digest 应保持稳定；审批状态、请求/审核字段、审批历史数量、阻断原因或下一步动作变化时，digest 会变化。digest 只用于证据复核，不代表自动放行真实 replay。

查询单个失败事件的重放执行契约：

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/failed-events/1/replay-execution-contract
```

返回结构示例：

```json
{
  "sampledAt": "2026-05-12T10:00:00Z",
  "failedEventId": 1,
  "exists": true,
  "contractVersion": "failed-event-replay-execution-contract.v1",
  "contractDigest": "sha256:...",
  "approvalEvidenceVersion": "failed-event-approval-status.v1",
  "approvalDigest": "sha256:...",
  "replayEligibilityDigest": "sha256:...",
  "failedEventStatus": "RECORDED",
  "managementStatus": "OPEN",
  "approvalStatus": "APPROVED",
  "requiredApprovalStatus": "APPROVED",
  "replayPreconditionsSatisfied": true,
  "realReplayEndpointEnforcesApprovalDigest": false,
  "realReplayEndpointEnforcesReplayEligibilityDigest": false,
  "digestVerificationMode": "CLIENT_PRECHECK_ONLY",
  "realExecutionMethod": "POST",
  "realExecutionPath": "/api/v1/failed-events/{id}/replay",
  "requiredOperatorAction": "REPLAY_FAILED_EVENT",
  "idempotencyKeyHint": "failed-event-replay:1:1001",
  "expectedAggregateId": "1001",
  "executionChecks": [
    {
      "checkId": "REPLAY_APPROVAL_APPROVED",
      "source": "FailedEventMessageService.replay",
      "category": "APPROVAL",
      "required": true,
      "status": "PASSED",
      "requiredValue": "approvalStatus=APPROVED",
      "currentValue": "approvalStatus=APPROVED",
      "evidenceDigest": "sha256:...",
      "blockedBy": []
    }
  ],
  "requestRequirements": [
    {
      "field": "reason",
      "requiredForPost": true,
      "rule": "non-blank replay reason is required"
    }
  ],
  "blockedBy": [],
  "warnings": [],
  "expectedSideEffects": [
    "PUBLISH_RABBITMQ_REPLAY_MESSAGE",
    "SAVE_REPLAY_ATTEMPT_AUDIT",
    "MARK_FAILED_EVENT_REPLAYED_ON_SUCCESS",
    "MARK_FAILED_EVENT_REPLAY_FAILED_ON_BROKER_ERROR"
  ],
  "nextAllowedActions": ["REPLAY_FAILED_EVENT"]
}
```

`replay-execution-contract` 只读组合 approval-status 与 readiness 的结论，用 `contractDigest` 固化当前执行前证据链。它明确暴露真实 `POST /api/v1/failed-events/{id}/replay` 会依赖的核心条件：

```text
失败事件存在
approvalStatus 必须是 APPROVED
失败事件不能已经 REPLAYED
RabbitMQ Outbox 必须启用
eventType / aggregateType / aggregateId / payload 必须在请求覆盖或原失败事件中存在
请求 reason 必须非空
eventId 如果由请求传入，必须是 UUID
```

边界说明：

```text
realReplayEndpointEnforcesApprovalDigest=false
realReplayEndpointEnforcesReplayEligibilityDigest=false
digestVerificationMode=CLIENT_PRECHECK_ONLY
```

这表示当前真实 replay POST 仍按 Java 内部状态做最终判断；digest 用于 Node 或人工操作台在执行前复核证据是否漂移，不会让 Java 自动执行，也不会绕过审批、角色或 RabbitMQ 前置条件。

v43 起，应用随包提供一个稳定 execution-contract approved 样本：

```text
src/main/resources/static/contracts/failed-event-replay-execution-contract-approved.sample.json
```

启动应用后可直接读取：

```powershell
Invoke-RestMethod http://localhost:8080/contracts/failed-event-replay-execution-contract-approved.sample.json
```

该样本固定覆盖 Node fixture-driven smoke 需要的关键字段：

```text
contractVersion
contractDigest
approvalDigest
replayEligibilityDigest
replayPreconditionsSatisfied
digestVerificationMode
expectedSideEffects
```

v44 起，应用额外提供 blocked 样本：

```text
src/main/resources/static/contracts/failed-event-replay-execution-contract-blocked.sample.json
```

启动应用后可直接读取：

```powershell
Invoke-RestMethod http://localhost:8080/contracts/failed-event-replay-execution-contract-blocked.sample.json
```

blocked 样本固定表达：

```text
replayPreconditionsSatisfied=false
approvalStatus=PENDING
blockedBy=["REPLAY_APPROVAL_NOT_APPROVED"]
executionChecks 中 REPLAY_APPROVAL_APPROVED 为 FAILED
expectedSideEffects=[]
nextAllowedActions=["REQUEST_REPLAY_APPROVAL"]
```

两个样本只用于测试、文档、Node smoke 和 diagnostics 对齐，不代表生产数据，不触发真实 replay，也不替代实时 `replay-execution-contract` 查询。

v46 起，应用随包提供 replay audit evidence approved / blocked 样本：

```text
src/main/resources/static/contracts/failed-event-replay-audit-approved.sample.json
src/main/resources/static/contracts/failed-event-replay-audit-blocked.sample.json
```

启动应用后可直接读取：

```powershell
Invoke-RestMethod http://localhost:8080/contracts/failed-event-replay-audit-approved.sample.json
Invoke-RestMethod http://localhost:8080/contracts/failed-event-replay-audit-blocked.sample.json
```

审计样本固定覆盖：

```text
auditEvidenceVersion
scenario
operator.operatorId / operator.operatorRole
requestId
decisionId
dryRun
executionAllowed
approval.requiredApprovalStatus / approval.approvalStatus
execution.attemptAuditType / execution.attemptStatus
execution.contractDigest / approvalDigest / replayEligibilityDigest
auditTrail
blockedBy
warnings
relatedEvidence
```

approved 样本表达真实 replay 通过后应该能追溯到审批申请、审批通过和 `FAILED_EVENT_REPLAY_ATTEMPT`；blocked 样本表达执行前审批未通过，只保留只读预检证据，不产生真实 replay attempt。两个样本仍然只用于证据对齐和 smoke，不代表生产数据，不执行 RabbitMQ 投递。

v47 起，应用提供 replay evidence index 说明接口：

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/failed-events/replay-evidence-index
```

返回结构示例：

```json
{
  "evidenceVersion": "failed-event-replay-evidence-index.v2",
  "readOnly": true,
  "executionAllowed": false,
  "liveEvidenceEndpoints": [
    {
      "name": "replay-execution-contract",
      "method": "GET",
      "path": "/api/v1/failed-events/{id}/replay-execution-contract",
      "readOnly": true,
      "changesReplayState": false
    }
  ],
  "staticEvidenceSamples": [
    {
      "name": "replay-audit-approved",
      "path": "/contracts/failed-event-replay-audit-approved.sample.json",
      "scenario": "APPROVED_REPLAY_AUDIT",
      "evidenceVersion": "failed-event-replay-audit-evidence.v1",
      "requiredFields": ["operator", "requestId", "decisionId", "dryRun", "executionAllowed", "auditTrail"]
    }
  ],
  "operatorAuthBoundary": {
    "identitySource": "HEADER_DERIVED_OPERATOR_CONTEXT",
    "requiredHeaders": ["X-Operator-Id", "X-Operator-Role"],
    "anonymousAllowed": false,
    "javaAuthenticatesCredentials": false,
    "enforcementMode": "ROLE_POLICY_PRECHECK_AND_SERVICE_GATE",
    "globalAllowedRoles": ["ORDER_SUPPORT", "SRE", "SYSTEM"],
    "allowedRolesByAction": {
      "MANAGE_FAILED_EVENT": ["ORDER_SUPPORT", "SRE", "SYSTEM"],
      "REQUEST_REPLAY_APPROVAL": ["ORDER_SUPPORT", "SRE", "SYSTEM"],
      "REVIEW_REPLAY_APPROVAL": ["SRE", "SYSTEM"],
      "REPLAY_FAILED_EVENT": ["ORDER_SUPPORT", "SRE", "SYSTEM"]
    },
    "productionAuthGaps": [
      "Java does not validate JWT, session cookies, or external identity-provider signatures yet.",
      "Upstream gateway or control plane must prevent client-side spoofing of X-Operator-* headers."
    ]
  },
  "auditIdentityFields": ["operator.operatorId", "operator.operatorRole", "requestId", "decisionId"],
  "executionSafetyRules": [
    "REAL_REPLAY_REQUIRES_APPROVED_STATUS",
    "OPERATOR_HEADERS_ARE_REQUIRED_BUT_NOT_CREDENTIAL_AUTHENTICATION",
    "UPSTREAM_MUST_PREVENT_X_OPERATOR_HEADER_SPOOFING",
    "BLOCKED_PRECHECK_MUST_NOT_CREATE_REPLAY_ATTEMPT"
  ]
}
```

这个接口只做说明索引，不读取具体失败事件，不创建审计，不执行 replay。它让控制面可以先知道 Java 提供了哪些 live evidence endpoint、哪些静态样本、哪些字段是审计身份字段，以及真实 replay 之前必须遵守哪些安全规则。

v48 起，`operatorAuthBoundary` 明确说明当前 Java 的身份边界：

```text
身份来源仍是 X-Operator-Id / X-Operator-Role Header
Java 会校验必填、角色白名单和动作级角色策略
Java 目前不校验 JWT、session cookie 或外部身份系统签名
上游网关或控制面必须防止 X-Operator-* Header 被客户端伪造
```

这让 Node 生产 readiness 汇总可以把 Java 当前状态区分为“有 operator/role rehearsal 和服务端动作门禁”，但还不是“完整生产级认证”。

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
src/main/resources/db/migration/h2/V12__order_idempotency_request_fingerprint.sql
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
src/main/resources/db/migration/postgresql/V12__order_idempotency_request_fingerprint.sql
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
 -> v52 增加订单创建请求指纹，同 Idempotency-Key 不同请求稳定 409，避免误把不同订单当作重放
 -> v53 增加 IdempotencyStore 抽象，默认仍走 JPA/DB 幂等存储，mini-kv 只作为 disabled candidate

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
 -> v34 增加动作权限决策明细，身份探针返回每个动作是否允许和允许角色
 -> v35 增加失败事件角色策略启动期一致性校验，提前发现动作角色越界和 system-role 不可重放
 -> v37 增加失败事件治理摘要接口，按只读方式汇总失败事件总量、审批状态、最近失败/审批活动和重放积压
 -> v38 增加失败事件重放 readiness 接口，按只读方式返回单条失败事件的重放资格、阻断原因、预警、积压位置和下一步动作
 -> v39 增加失败事件重放 simulation 接口，复用 readiness 结果并只读预演真实重放可能产生的副作用
 -> v40 增加失败事件重放 approval-status 接口，只读暴露 Java 自己保存的审批状态、最近审批流水和下一步动作
 -> v41 增加 approval-status 证据版本和稳定 digest，便于 Node 校验 Java 上游审批证据是否漂移
 -> v42 增加失败事件重放 execution-contract，只读说明真实 replay 前 Java 会检查哪些状态、审批、digest 和请求条件
 -> v43 增加 execution-contract 稳定样本 JSON，给 Node fixture-driven smoke 使用真实格式参考
 -> v44 增加 execution-contract blocked 稳定样本 JSON，给 Node 多场景矩阵提供负向样本
 -> v46 增加 replay audit evidence approved/blocked 稳定样本 JSON，给控制面判断 replay 执行是否可追溯
 -> v47 增加 replay evidence index 说明接口，汇总 live evidence、静态样本、审计身份字段和执行安全规则
 -> v48 增强 replay evidence index，补 operator/auth boundary 字段，说明 Header 身份、动作角色策略和生产认证缺口

ops
 -> v36 增加订单平台只读运行概览，汇总 application、orders、inventory、outbox、failedEvents，为 Node 统一观察台提供稳定业务信号
 -> v45 增加订单平台只读运行证据，汇总 service version、failed-event replay、审批、Outbox 和执行阻断信号
 -> v49 增加 ops read-only evidence 静态样本，给 Node production pass evidence verification 提供 Java 只读证据引用位
 -> v50 增强 ops evidence 启动后自描述，固定 healthProbe 和 readOnlyWindow 字段，服务真实只读 live probe capture
 -> v51 增加 ops evidence 字段说明样本，解释 service、healthProbe、readOnlyWindow 和执行边界字段稳定性
 -> v52 增加订单幂等边界 evidence 和静态样本，说明同 key 同请求重放、同 key 不同请求拒绝以及 mini-kv 未接入边界
  -> v53 增加订单幂等存储抽象 evidence 和静态样本，说明活动存储、候选适配器和 Node 不触发写操作边界
  -> v54 增加 release verification manifest，固化 Maven 测试、打包、HTTP smoke 和静态 contracts 发布验证清单
  -> v55 增加 deployment rollback evidence，说明包、配置、数据库迁移和静态契约回退边界，Node 只读消费且不触发回退
  -> v56 增加 release bundle manifest，把 jar、contracts、发布验证和回退证据收成 Node 可消费但不可执行的只读 bundle
  -> v57 增加 rollback approval handoff，固化 artifact、runtime config、secret source 和 database migration direction 人工确认字段
  -> v58 增加 rollback SQL review gate，固化 SQL review owner、migration direction 和 operator approval placeholder
  -> v59 增加 production secret source contract，固化 secret source、rotation owner、review cadence 和 secret value 访问边界
  -> v60 增加 production deployment runbook contract，固化 deployment window owner、rollback approver、migration direction 和 no-execution 边界
  -> v61 增加 rollback approval record fixture，固化 reviewer、approval timestamp placeholder、rollback target 和 no-secret-value 边界
  -> v62 增加 release handoff checklist fixture，固化 release operator、rollback approver、artifact target、migration direction 和 secret source confirmation，并收口静态 contract endpoint helper
  -> v63 增加 release audit retention fixture，固化 release evidence retention id、operator placeholder、artifact target、retention days、audit export 字段和 no-secret-value 边界
  -> v64 增加 release operator signoff fixture，固化 release operator、rollback approver、release window、artifact target 和 operator signoff placeholder 的审批决定前置证据边界
  -> v65 增加 rollback approver evidence fixture，固化 rollback approver、migration direction、rollback SQL artifact reference 和 production database boundary 的只读证据边界
  -> v66 增加 release approval rehearsal 只读聚合入口，汇总审批演练输入、live replay/outbox 信号和禁止审批/ledger/deploy/rollback/SQL 的执行边界
  -> v67 增强 release approval rehearsal 只读请求上下文，回显 request id、operator identity 和 audit correlation 来源，但不认证、不持久化、不写 ledger
  -> v68 增强 release approval rehearsal 只读失败分类，区分 upstream readiness、auth context warning 和 audit correlation warning，继续禁止写入和执行

common
 -> 业务异常和统一错误响应

static
 -> 失败事件管理静态页面、重放工作台、操作员身份校验、动作级角色提示、操作员动作权限摘要、动作权限决策标签、动作权限预检按钮禁用、写操作本地权限守卫、重放审批按钮、自提自审拦截提示、审批历史面板、二次确认弹窗、风险提示、样式和浏览器端交互脚本
```

后续建议升级顺序：

1. 在 `FailedEventOperatorContextResolver` 后面接入真实认证鉴权，把 `X-Operator-*` 请求头替换成登录态和权限上下文。
2. 给失败事件管理页面增加真实登录态、权限控制和当前用户展示。
3. 在 `IdempotencyStore` 后面实验 Redis / mini-kv TTL token candidate，先保持订单数据库为权威存储。
4. 接入 OpenTelemetry、Prometheus、Grafana。
5. 增加并发库存压测和更多 Testcontainers 多中间件集成测试。
