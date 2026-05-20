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
- 订单平台 release approval rehearsal 只读 operator-window hint，回显 Node v198 真实只读窗口身份头和 approval correlation 头
- 订单平台 release approval rehearsal 只读失败分类，区分上游就绪、身份上下文和审计关联 warning
- 订单平台 release approval rehearsal 只读验证提示，提供响应 schema、warning digest 和 no-ledger-write proof
- 订单平台 release approval rehearsal 只读 CI evidence hint，回显 Node CI manifest 摘要且不上传 artifact
- 订单平台 release approval rehearsal 只读 artifact retention hint，回显 Node dry-run upload contract 与 Java retention fixture
- 订单平台 release approval rehearsal 只读 live-readiness hint，回显真实只读 runtime smoke 上下文
- 订单平台 release approval rehearsal 只读 audit-persistence handoff hint，列出未来可进入 Node managed audit 的只读字段
- 订单平台 release approval rehearsal 只读 approval-record handoff hint，标注可进入 Node audit record 的审批字段
- 订单平台 release approval rehearsal 只读 approval-handoff verification marker，标注 Node v211 已消费 Java v75 handoff 的 dry-run packet 边界
- 订单平台 release approval rehearsal 只读 managed-audit adapter boundary receipt，标注 Node v215 只能写本地 dry-run 文件且不能触发 Java/审计/SQL/部署/回滚/restore
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

v69 起，release approval rehearsal 在只读响应中增加验证提示字段，给 Node 导入人工窗口结果前做 schema 与 warning 校验：

```text
verificationHint.hintVersion=java-release-approval-rehearsal-verification-hint.v1
verificationHint.responseSchemaVersion=java-release-approval-rehearsal-response-schema.v3
verificationHint.warningDigest=sha256:...
verificationHint.noLedgerWriteProof=NO_LEDGER_WRITE_PROOF_BY_RESPONSE_FIELDS
verificationHint.noLedgerWriteProved=true
verificationHint.nodeMayTreatAsProductionAuthorization=false
verificationHint.warningDigestInputs 包含 contextWarnings、failureCategories、taxonomyWarnings、executionAllowed、approvalLedgerWritten、nodeMayWriteApprovalLedger
verificationHint.proofClaims 包含 executionAllowed=false、requestContext.approvalLedgerWritten=false、executionBoundaries.nodeMayWriteApprovalLedger=false
```

`warningDigest` 只覆盖 warning 和 no-ledger 相关字段，不包含 `sampledAt`，因此同一类 closed-window / operator-window 读取在 warning 状态不变时 digest 稳定，warning 状态变化时 digest 会变化。该字段只用于 Node v196/v197 归档校验，不代表生产授权，也不允许 Node 打开 `UPSTREAM_ACTIONS_ENABLED=true`。

v70 起，release approval rehearsal 在只读响应中增加 `operatorWindowHint`，专门回显 Node v198 real-read window operator identity binding 使用的请求头：

```powershell
Invoke-RestMethod `
  -Uri http://localhost:8080/api/v1/ops/release-approval-rehearsal `
  -Headers @{
    "X-Rehearsal-Request-Id" = "rehearsal-v70-001"
    "X-Operator-Identity" = "release-operator@example.test"
    "X-Audit-Correlation-Id" = "audit-correlation-v70"
    "x-orderops-operator-id" = "operator-198"
    "x-orderops-roles" = "operator,auditor"
    "x-orderops-operator-verified" = "true"
    "x-orderops-approval-correlation-id" = "approval-v198-operator-window"
  }
```

响应中的关键字段：

```text
operatorWindowHint.hintVersion=java-release-approval-rehearsal-operator-window-hint.v1
operatorWindowHint.operatorId=operator-198
operatorWindowHint.operatorIdSource=x-orderops-operator-id
operatorWindowHint.operatorRoles=operator,auditor
operatorWindowHint.operatorRolesSource=x-orderops-roles
operatorWindowHint.operatorVerifiedClaim=true
operatorWindowHint.operatorVerifiedClaimSource=x-orderops-operator-verified
operatorWindowHint.approvalCorrelationId=approval-v198-operator-window
operatorWindowHint.approvalCorrelationIdSource=x-orderops-approval-correlation-id
operatorWindowHint.operatorWindowContextComplete=true
operatorWindowHint.productionIdpVerifiedByJava=false
operatorWindowHint.persistedApprovalRecordByJava=false
operatorWindowHint.nodeMayTreatAsProductionIdentity=false
verificationHint.responseSchemaVersion=java-release-approval-rehearsal-response-schema.v4
verificationHint.warningDigestInputs 包含 contextWarnings、operatorWindowEchoWarnings、failureCategories、taxonomyWarnings、executionAllowed、approvalLedgerWritten、nodeMayWriteApprovalLedger
```

这一步只证明 Java 只读响应“看见了” Node v198 的窗口身份和审批关联字段，不认证 operator，不连接生产 IdP，不持久化 approval record，不写 approval ledger，也不授权 Node 打开生产窗口或执行任何上游写操作。

v71 起，release approval rehearsal 在同一个只读响应中增加 `ciEvidenceHint`，给 Node v201 复核 Node v200 CI archive artifact manifest 时使用。调用方可以传入 Node v200 manifest 的只读摘要字段：

```powershell
Invoke-RestMethod `
  -Uri http://localhost:8080/api/v1/ops/release-approval-rehearsal `
  -Headers @{
    "X-Rehearsal-Request-Id" = "rehearsal-v71-001"
    "X-Operator-Identity" = "release-operator@example.test"
    "X-Audit-Correlation-Id" = "audit-correlation-v71"
    "x-orderops-operator-id" = "operator-198"
    "x-orderops-roles" = "operator,auditor"
    "x-orderops-operator-verified" = "true"
    "x-orderops-approval-correlation-id" = "approval-v198-operator-window"
    "x-orderops-ci-manifest-version" = "real-read-window-ci-archive-artifact-manifest.v1"
    "x-orderops-ci-manifest-digest" = "sha256:<node-v200-manifest-digest>"
    "x-orderops-ci-manifest-endpoint" = "/api/v1/production/real-read-window-ci-archive-artifact-manifest"
    "x-orderops-ci-artifact-record-count" = "9"
    "x-orderops-ci-approval-correlation-id" = "approval-v198-operator-window"
  }
```

响应中的关键字段：

```text
ciEvidenceHint.hintVersion=java-release-approval-rehearsal-ci-evidence-hint.v1
ciEvidenceHint.manifestProfileVersion=real-read-window-ci-archive-artifact-manifest.v1
ciEvidenceHint.manifestProfileVersionSource=x-orderops-ci-manifest-version
ciEvidenceHint.manifestDigest=sha256:<node-v200-manifest-digest>
ciEvidenceHint.manifestDigestSource=x-orderops-ci-manifest-digest
ciEvidenceHint.manifestEndpoint=/api/v1/production/real-read-window-ci-archive-artifact-manifest
ciEvidenceHint.manifestEndpointSource=x-orderops-ci-manifest-endpoint
ciEvidenceHint.artifactRecordCount=9
ciEvidenceHint.artifactRecordCountSource=x-orderops-ci-artifact-record-count
ciEvidenceHint.approvalCorrelationId=approval-v198-operator-window
ciEvidenceHint.approvalCorrelationIdSource=x-orderops-ci-approval-correlation-id
ciEvidenceHint.ciEvidenceContextComplete=true
ciEvidenceHint.noLedgerWriteProved=true
ciEvidenceHint.ciArtifactUploadedByJava=false
ciEvidenceHint.githubArtifactAccessedByJava=false
ciEvidenceHint.productionWindowAllowedByJava=false
ciEvidenceHint.nodeMayTreatAsCiArtifactPublication=false
verificationHint.responseSchemaVersion=java-release-approval-rehearsal-response-schema.v5
verificationHint.warningDigestInputs 包含 contextWarnings、operatorWindowEchoWarnings、ciEvidenceEchoWarnings、failureCategories、taxonomyWarnings、executionAllowed、approvalLedgerWritten、nodeMayWriteApprovalLedger
```

这一步只证明 Java 只读响应能回显 Node v200 manifest 相关字段，并继续证明 Java 没有上传 CI artifact、没有访问 GitHub artifact、没有写 approval ledger、没有打开生产窗口。真实 artifact 上传、artifact store 权限、GitHub secret 和生产窗口授权仍必须留在 Java 外部的后续 CI / Node gate 中处理。

v72 起，release approval rehearsal 继续增加 `artifactRetentionHint`，用于给 Node v203 的 cross-project CI artifact retention gate 提供 Java 侧只读保留期证据。调用方可把 Node v202 upload dry-run contract 的摘要字段传入：

```powershell
Invoke-RestMethod `
  -Uri http://localhost:8080/api/v1/ops/release-approval-rehearsal `
  -Headers @{
    "X-Rehearsal-Request-Id" = "rehearsal-v72-001"
    "X-Operator-Identity" = "release-operator@example.test"
    "X-Audit-Correlation-Id" = "audit-correlation-v72"
    "x-orderops-operator-id" = "operator-198"
    "x-orderops-roles" = "operator,auditor"
    "x-orderops-operator-verified" = "true"
    "x-orderops-approval-correlation-id" = "approval-v198-operator-window"
    "x-orderops-ci-manifest-version" = "real-read-window-ci-archive-artifact-manifest.v1"
    "x-orderops-ci-manifest-digest" = "sha256:<node-v200-manifest-digest>"
    "x-orderops-ci-manifest-endpoint" = "/api/v1/production/real-read-window-ci-archive-artifact-manifest"
    "x-orderops-ci-artifact-record-count" = "9"
    "x-orderops-ci-approval-correlation-id" = "approval-v198-operator-window"
    "x-orderops-ci-upload-contract-version" = "real-read-window-ci-artifact-upload-dry-run-contract.v1"
    "x-orderops-ci-upload-contract-digest" = "sha256:<node-v202-upload-contract-digest>"
    "x-orderops-ci-artifact-name" = "orderops-real-read-window-evidence-v191-v201"
    "x-orderops-ci-artifact-root" = "c/"
    "x-orderops-ci-retention-days" = "30"
    "x-orderops-ci-upload-mode" = "dry-run-contract-only"
  }
```

响应中的关键字段：

```text
artifactRetentionHint.hintVersion=java-release-approval-rehearsal-artifact-retention-hint.v1
artifactRetentionHint.sourceRetentionFixtureVersion=java-release-audit-retention-fixture.v1
artifactRetentionHint.sourceRetentionFixtureEndpoint=/contracts/release-audit-retention.fixture.json
artifactRetentionHint.javaRetentionDays=180
artifactRetentionHint.ciUploadContractVersion=real-read-window-ci-artifact-upload-dry-run-contract.v1
artifactRetentionHint.ciUploadContractDigest=sha256:<node-v202-upload-contract-digest>
artifactRetentionHint.ciArtifactName=orderops-real-read-window-evidence-v191-v201
artifactRetentionHint.ciArtifactRoot=c/
artifactRetentionHint.ciRetentionDays=30
artifactRetentionHint.ciUploadMode=dry-run-contract-only
artifactRetentionHint.artifactRetentionContextComplete=true
artifactRetentionHint.retentionDaysWithinJavaRetention=true
artifactRetentionHint.javaRetentionFixtureReadOnly=true
artifactRetentionHint.auditExportReadOnly=true
artifactRetentionHint.ciArtifactUploadedByJava=false
artifactRetentionHint.githubArtifactAccessedByJava=false
artifactRetentionHint.productionWindowAllowedByJava=false
artifactRetentionHint.nodeMayTreatAsRetentionAuthorization=false
verificationHint.responseSchemaVersion=java-release-approval-rehearsal-response-schema.v6
verificationHint.warningDigestInputs 包含 artifactRetentionEchoWarnings
```

这一步只证明 Java 能把 Node v202 dry-run upload contract 的 artifact name、artifact root、retention days 和 upload mode 回显到只读响应，并把这些字段与 Java 现有 release audit retention fixture 放在同一个证据面里。它不上传 GitHub artifact，不读取 GitHub token，不写 audit export，不创建 approval decision，不写 approval ledger，也不授权真实生产窗口。

v73 起，release approval rehearsal 增加 `liveReadinessHint`，用于给 Node v205 的 three-project real-read runtime smoke 做 Java 侧只读运行提示。调用方可以把 Node v204 preflight 和 Node v205 smoke session 的上下文字段传入：

```powershell
Invoke-RestMethod `
  -Uri http://localhost:8080/api/v1/ops/release-approval-rehearsal `
  -Headers @{
    "X-Rehearsal-Request-Id" = "rehearsal-v73-001"
    "X-Operator-Identity" = "release-operator@example.test"
    "X-Audit-Correlation-Id" = "audit-correlation-v73"
    "x-orderops-operator-id" = "operator-198"
    "x-orderops-roles" = "operator,auditor"
    "x-orderops-operator-verified" = "true"
    "x-orderops-approval-correlation-id" = "approval-v198-operator-window"
    "x-orderops-ci-manifest-version" = "real-read-window-ci-archive-artifact-manifest.v1"
    "x-orderops-ci-manifest-digest" = "sha256:<node-v200-manifest-digest>"
    "x-orderops-ci-manifest-endpoint" = "/api/v1/production/real-read-window-ci-archive-artifact-manifest"
    "x-orderops-ci-artifact-record-count" = "9"
    "x-orderops-ci-approval-correlation-id" = "approval-v198-operator-window"
    "x-orderops-ci-upload-contract-version" = "real-read-window-ci-artifact-upload-dry-run-contract.v1"
    "x-orderops-ci-upload-contract-digest" = "sha256:<node-v202-upload-contract-digest>"
    "x-orderops-ci-artifact-name" = "orderops-real-read-window-evidence-v191-v201"
    "x-orderops-ci-artifact-root" = "c/"
    "x-orderops-ci-retention-days" = "30"
    "x-orderops-ci-upload-mode" = "dry-run-contract-only"
    "x-orderops-runtime-preflight-version" = "three-project-real-read-runtime-smoke-preflight.v1"
    "x-orderops-runtime-preflight-digest" = "sha256:<node-v204-preflight-digest>"
    "x-orderops-runtime-smoke-session-id" = "runtime-smoke-v205-session-001"
    "x-orderops-runtime-read-target-id" = "java-release-approval-rehearsal"
    "x-orderops-runtime-window-mode" = "manual-open-window-plan"
  }
```

响应中的关键字段：

```text
liveReadinessHint.hintVersion=java-release-approval-rehearsal-live-readiness-hint.v1
liveReadinessHint.serverTimestamp=<sampledAt>
liveReadinessHint.serverTimestampSource=sampledAt
liveReadinessHint.readOnlyEndpointVersion=java-release-approval-rehearsal-response-schema.v15
liveReadinessHint.readOnlyEndpoint=/api/v1/ops/release-approval-rehearsal
liveReadinessHint.healthEndpoint=/actuator/health
liveReadinessHint.sourcePreflightVersion=three-project-real-read-runtime-smoke-preflight.v1
liveReadinessHint.sourcePreflightDigest=sha256:<node-v204-preflight-digest>
liveReadinessHint.runtimeSmokeSessionId=runtime-smoke-v205-session-001
liveReadinessHint.runtimeReadTargetId=java-release-approval-rehearsal
liveReadinessHint.runtimeWindowMode=manual-open-window-plan
liveReadinessHint.liveReadinessContextComplete=true
liveReadinessHint.readyForRuntimeSmokeRead=true
liveReadinessHint.readOnlyEndpointReady=true
liveReadinessHint.runtimeSmokeExecutedByJava=false
liveReadinessHint.nodeMustRecordPidAndCleanup=true
liveReadinessHint.javaStartedProcessForNode=false
liveReadinessHint.processCleanupRecordedByJava=false
liveReadinessHint.nodeMayTreatAsProductionAuthorization=false
verificationHint.responseSchemaVersion=java-release-approval-rehearsal-response-schema.v13
verificationHint.warningDigestInputs 包含 liveReadinessEchoWarnings
```

这一步只证明 Java 的只读 rehearsal 端点适合被 Node v205 纳入真实 HTTP smoke 读取目标，并回显 Node v204/v205 的运行上下文。Java 不启动 Node 的 smoke 流程，不记录 Node PID，不替 Node 做 cleanup 证据，也不把 runtime smoke 结果当作生产窗口授权。

v74 起，release approval rehearsal 增加 `auditPersistenceHandoffHint`，用于给 Node v208 的 managed audit persistence boundary candidate 提供 Java 侧只读交接字段。调用方可以继续读取同一个只读端点，并额外传入 Node v208 候选 contract 摘要：

```powershell
Invoke-RestMethod `
  -Uri http://localhost:8080/api/v1/ops/release-approval-rehearsal `
  -Headers @{
    "X-Rehearsal-Request-Id" = "rehearsal-v74-001"
    "X-Operator-Identity" = "release-operator@example.test"
    "X-Audit-Correlation-Id" = "audit-correlation-v74"
    "x-orderops-operator-id" = "operator-198"
    "x-orderops-roles" = "operator,auditor"
    "x-orderops-operator-verified" = "true"
    "x-orderops-approval-correlation-id" = "approval-v198-operator-window"
    "x-orderops-ci-manifest-version" = "real-read-window-ci-archive-artifact-manifest.v1"
    "x-orderops-ci-manifest-digest" = "sha256:<node-v200-manifest-digest>"
    "x-orderops-ci-manifest-endpoint" = "/api/v1/production/real-read-window-ci-archive-artifact-manifest"
    "x-orderops-ci-artifact-record-count" = "9"
    "x-orderops-ci-approval-correlation-id" = "approval-v198-operator-window"
    "x-orderops-ci-upload-contract-version" = "real-read-window-ci-artifact-upload-dry-run-contract.v1"
    "x-orderops-ci-upload-contract-digest" = "sha256:<node-v202-upload-contract-digest>"
    "x-orderops-ci-artifact-name" = "orderops-real-read-window-evidence-v191-v201"
    "x-orderops-ci-artifact-root" = "c/"
    "x-orderops-ci-retention-days" = "30"
    "x-orderops-ci-upload-mode" = "dry-run-contract-only"
    "x-orderops-runtime-preflight-version" = "three-project-real-read-runtime-smoke-preflight.v1"
    "x-orderops-runtime-preflight-digest" = "sha256:<node-v204-preflight-digest>"
    "x-orderops-runtime-smoke-session-id" = "runtime-smoke-v205-session-001"
    "x-orderops-runtime-read-target-id" = "java-release-approval-rehearsal"
    "x-orderops-runtime-window-mode" = "manual-open-window-plan"
    "x-orderops-managed-audit-candidate-version" = "managed-audit-persistence-boundary-candidate.v1"
    "x-orderops-managed-audit-candidate-digest" = "sha256:<node-v208-managed-audit-candidate-digest>"
    "x-orderops-managed-audit-sink-mode" = "file-or-sqlite-dry-run-candidate"
    "x-orderops-managed-audit-retention-days" = "30"
    "x-orderops-managed-audit-rotation-policy" = "size-and-age-rotation-candidate"
  }
```

响应中的关键字段：

```text
auditPersistenceHandoffHint.hintVersion=java-release-approval-rehearsal-audit-persistence-handoff-hint.v1
auditPersistenceHandoffHint.sourceRetentionFixtureVersion=java-release-audit-retention-fixture.v1
auditPersistenceHandoffHint.sourceRetentionFixtureEndpoint=/contracts/release-audit-retention.fixture.json
auditPersistenceHandoffHint.javaRetentionDays=180
auditPersistenceHandoffHint.managedAuditCandidateVersion=managed-audit-persistence-boundary-candidate.v1
auditPersistenceHandoffHint.managedAuditCandidateDigest=sha256:<node-v208-managed-audit-candidate-digest>
auditPersistenceHandoffHint.managedAuditSinkMode=file-or-sqlite-dry-run-candidate
auditPersistenceHandoffHint.managedAuditRetentionDays=30
auditPersistenceHandoffHint.managedAuditRotationPolicy=size-and-age-rotation-candidate
auditPersistenceHandoffHint.auditPersistenceHandoffContextComplete=true
auditPersistenceHandoffHint.managedAuditRetentionWithinJavaRetention=true
auditPersistenceHandoffHint.javaAuditSourceReadOnly=true
auditPersistenceHandoffHint.javaLedgerWriteAllowed=false
auditPersistenceHandoffHint.javaManagedAuditWriteAllowed=false
auditPersistenceHandoffHint.javaExternalAuditSystemAccessed=false
auditPersistenceHandoffHint.nodeMayUseAsManagedAuditInput=true
auditPersistenceHandoffHint.nodeMayTreatAsProductionAuditRecord=false
verificationHint.responseSchemaVersion=java-release-approval-rehearsal-response-schema.v13
verificationHint.warningDigestInputs 包含 auditPersistenceHandoffEchoWarnings
```

这一步只说明哪些 Java 只读字段未来可进入 Node managed audit dry-run 存储。Java 不写 approval ledger，不写 managed audit store，不连接外部生产审计系统，不创建真实 approval decision，也不把该响应当作生产审计记录。

v75 起，release approval rehearsal 增加 `approvalRecordHandoffHint`，用于给 Node v211 的 managed audit dry-run packet 标注哪些审批上下文字段可以进入 Node audit record。调用方继续读取同一个只读端点，并可额外传入 Node v210 approval binding contract 上下文：

```powershell
Invoke-RestMethod `
  -Uri http://localhost:8080/api/v1/ops/release-approval-rehearsal `
  -Headers @{
    "X-Rehearsal-Request-Id" = "rehearsal-v75-001"
    "X-Operator-Identity" = "release-operator@example.test"
    "X-Audit-Correlation-Id" = "audit-correlation-v75"
    "x-orderops-operator-id" = "operator-198"
    "x-orderops-roles" = "operator,auditor"
    "x-orderops-operator-verified" = "true"
    "x-orderops-approval-correlation-id" = "approval-v198-operator-window"
    "x-orderops-managed-audit-candidate-version" = "managed-audit-persistence-boundary-candidate.v1"
    "x-orderops-managed-audit-candidate-digest" = "sha256:<node-v208-managed-audit-candidate-digest>"
    "x-orderops-managed-audit-sink-mode" = "file-or-sqlite-dry-run-candidate"
    "x-orderops-managed-audit-retention-days" = "30"
    "x-orderops-managed-audit-rotation-policy" = "size-and-age-rotation-candidate"
    "x-orderops-approval-binding-contract-version" = "managed-audit-identity-approval-binding-contract.v1"
    "x-orderops-approval-binding-contract-digest" = "sha256:<node-v210-approval-binding-digest>"
    "x-orderops-approval-request-id" = "approval-request-v210-001"
    "x-orderops-approval-decision-state" = "APPROVED_DRY_RUN_ONLY"
    "x-orderops-approval-record-correlation-id" = "approval-record-correlation-v210"
  }
```

响应中的关键字段：

```text
approvalRecordHandoffHint.hintVersion=java-release-approval-rehearsal-approval-record-handoff-hint.v1
approvalRecordHandoffHint.sourceApprovalRecordFixtureVersion=java-rollback-approval-record-fixture.v1
approvalRecordHandoffHint.sourceApprovalRecordFixtureEndpoint=/contracts/rollback-approval-record.fixture.json
approvalRecordHandoffHint.reviewerPlaceholder=rollback-reviewer-placeholder
approvalRecordHandoffHint.approvalTimestampPlaceholder=approval-timestamp-placeholder
approvalRecordHandoffHint.rollbackTarget=release-tag-or-artifact-version-placeholder
approvalRecordHandoffHint.selectedMigrationDirection=no-database-change
approvalRecordHandoffHint.approvalBindingContractVersion=managed-audit-identity-approval-binding-contract.v1
approvalRecordHandoffHint.approvalBindingContractDigest=sha256:<node-v210-approval-binding-digest>
approvalRecordHandoffHint.approvalRequestId=approval-request-v210-001
approvalRecordHandoffHint.approvalDecisionState=APPROVED_DRY_RUN_ONLY
approvalRecordHandoffHint.approvalRecordCorrelationId=approval-record-correlation-v210
approvalRecordHandoffHint.approvalRecordHandoffContextComplete=true
approvalRecordHandoffHint.approvalRecordFixtureReadOnly=true
approvalRecordHandoffHint.javaApprovalDecisionCreated=false
approvalRecordHandoffHint.javaApprovalLedgerWritten=false
approvalRecordHandoffHint.javaApprovalRecordPersisted=false
approvalRecordHandoffHint.javaApprovalRecordAuthenticated=false
approvalRecordHandoffHint.productionApprovalStoreRequired=false
approvalRecordHandoffHint.nodeMayUseAsAuditApprovalInput=true
approvalRecordHandoffHint.nodeMayTreatAsProductionApprovalRecord=false
verificationHint.responseSchemaVersion=java-release-approval-rehearsal-response-schema.v13
verificationHint.warningDigestInputs 包含 approvalRecordHandoffEchoWarnings、javaApprovalRecordPersisted、nodeMayTreatAsProductionApprovalRecord
```

这一步只说明 Node 后续 dry-run audit packet 可读取哪些 Java 只读审批字段。Java 不创建 approval decision，不写 approval ledger，不持久化 approval record，不做生产身份认证，不连接生产 approval store，也不把该响应当作生产 approval record。

v76 起，release approval rehearsal 增加 `approvalHandoffVerificationMarker`，用于把 Node v211 已经消费 Java v75 approval-record handoff 的结果回写成只读 marker，给后续 Node v213 restore drill plan 做前置核对。该 marker 不新增写入路径，也不代表生产 audit/approval 记录：

```text
approvalHandoffVerificationMarker.markerVersion=java-release-approval-rehearsal-approval-handoff-verification-marker.v1
approvalHandoffVerificationMarker.sourceApprovalRecordHandoffHintVersion=java-release-approval-rehearsal-approval-record-handoff-hint.v1
approvalHandoffVerificationMarker.sourceApprovalRecordHandoffSchemaVersion=java-release-approval-rehearsal-response-schema.v9
approvalHandoffVerificationMarker.consumedByNodeProfileVersion=managed-audit-identity-approval-provenance-dry-run-packet.v1
approvalHandoffVerificationMarker.consumedByNodePacketState=dry-run-packet-verified
approvalHandoffVerificationMarker.consumedByNodeEndpoint=/api/v1/audit/managed-identity-approval-provenance-dry-run-packet
approvalHandoffVerificationMarker.consumedByNodeRequestId=managed-audit-v211-identity-approval-provenance-request
approvalHandoffVerificationMarker.consumedByNodePacketVersion=managed-audit-dry-run-record.v2-candidate
approvalHandoffVerificationMarker.consumedByNodeBindingContractVersion=managed-audit-identity-approval-binding-contract.v1
approvalHandoffVerificationMarker.consumedByNodeDryRunDirectoryLabel=.tmp
approvalHandoffVerificationMarker.consumedByNodeDryRunDirectoryPrefix=managed-audit-v211-
approvalHandoffVerificationMarker.consumedByNodeDryRunFileName=managed-audit-packet.jsonl
approvalHandoffVerificationMarker.nodeV211HandoffAccepted=true
approvalHandoffVerificationMarker.nodeV211NoWriteBoundaryAccepted=true
approvalHandoffVerificationMarker.nodeV211PacketAppendCovered=true
approvalHandoffVerificationMarker.nodeV211PacketQueryCovered=true
approvalHandoffVerificationMarker.nodeV211PacketDigestCovered=true
approvalHandoffVerificationMarker.nodeV211PacketCleanupCovered=true
approvalHandoffVerificationMarker.nodeV211JavaWriteAttempted=false
approvalHandoffVerificationMarker.nodeV211MiniKvWriteAttempted=false
approvalHandoffVerificationMarker.nodeV211ExternalAuditSystemAccessed=false
approvalHandoffVerificationMarker.nodeV211RealApprovalDecisionCreated=false
approvalHandoffVerificationMarker.nodeV211RealApprovalLedgerWritten=false
approvalHandoffVerificationMarker.nodeV211ProductionAuditRecordAllowed=false
approvalHandoffVerificationMarker.javaApprovalRecordPersisted=false
approvalHandoffVerificationMarker.javaApprovalLedgerWritten=false
approvalHandoffVerificationMarker.readyForNodeV213RestoreDrillPlan=true
approvalHandoffVerificationMarker.nodeMayTreatAsProductionAuditRecord=false
verificationHint.responseSchemaVersion=java-release-approval-rehearsal-response-schema.v13
verificationHint.warningDigestInputs 包含 approvalHandoffVerificationMarkerWarnings、managedAuditAdapterBoundaryReceiptWarnings、nodeV211ProductionAuditRecordAllowed、nodeV211RealApprovalDecisionCreated
```

没有传入完整 Node v210 approval binding header 时，`nodeV211HandoffAccepted=false` 且 `readyForNodeV213RestoreDrillPlan=false`，marker 会给出 `NODE_V211_APPROVAL_HANDOFF_CONTEXT_INCOMPLETE`。这一步只证明 Java v75 handoff 可被 Node v211 dry-run packet 读取并已保持 append/query/digest/cleanup 覆盖；Java 不创建真实 approval decision，不写 approval ledger，不写 approval record，不连接外部 audit system，不执行 restore，也不允许 Node 把它当生产 audit record。

v77 起，release approval rehearsal 增加 `managedAuditAdapterBoundaryReceipt`，用于承接 Node v214 archive verification，并给 Node v215 managed audit dry-run adapter candidate 明确只读边界。该 receipt 允许 Node v215 消费响应、写 Node 本地 `.tmp` 或受控测试文件；不允许 Node 连接真实 managed audit，不允许创建 approval decision，不允许写 approval ledger，不允许持久化 approval record，不允许执行 Java SQL、部署、回滚或 restore：

```text
managedAuditAdapterBoundaryReceipt.receiptVersion=java-release-approval-rehearsal-managed-audit-adapter-boundary-receipt.v1
managedAuditAdapterBoundaryReceipt.sourceApprovalHandoffMarkerVersion=java-release-approval-rehearsal-approval-handoff-verification-marker.v1
managedAuditAdapterBoundaryReceipt.sourceApprovalHandoffSchemaVersion=java-release-approval-rehearsal-response-schema.v10
managedAuditAdapterBoundaryReceipt.consumedByNodeArchiveVerificationVersion=managed-audit-restore-drill-archive-verification.v1
managedAuditAdapterBoundaryReceipt.consumedByNodeArchiveVerificationState=verified-restore-drill-archive
managedAuditAdapterBoundaryReceipt.consumedByNodeArchiveVerificationEndpoint=/api/v1/audit/managed-audit-restore-drill-archive-verification
managedAuditAdapterBoundaryReceipt.nextNodeCandidateVersion=Node v215
managedAuditAdapterBoundaryReceipt.nextNodeCandidateProfile=managed-audit-dry-run-adapter-candidate.v1
managedAuditAdapterBoundaryReceipt.nodeV215MayConsume=true
managedAuditAdapterBoundaryReceipt.nodeV215MayWriteLocalDryRunFiles=true
managedAuditAdapterBoundaryReceipt.nodeV215MayConnectManagedAudit=false
managedAuditAdapterBoundaryReceipt.nodeV215MayCreateApprovalDecision=false
managedAuditAdapterBoundaryReceipt.nodeV215MayWriteApprovalLedger=false
managedAuditAdapterBoundaryReceipt.nodeV215MayPersistApprovalRecord=false
managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteSql=false
managedAuditAdapterBoundaryReceipt.nodeV215MayTriggerDeployment=false
managedAuditAdapterBoundaryReceipt.nodeV215MayTriggerRollback=false
managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteRestore=false
managedAuditAdapterBoundaryReceipt.javaApprovalDecisionCreated=false
managedAuditAdapterBoundaryReceipt.javaApprovalLedgerWritten=false
managedAuditAdapterBoundaryReceipt.javaApprovalRecordPersisted=false
managedAuditAdapterBoundaryReceipt.javaManagedAuditWriteExecuted=false
managedAuditAdapterBoundaryReceipt.javaRollbackSqlExecuted=false
managedAuditAdapterBoundaryReceipt.javaDeploymentTriggered=false
managedAuditAdapterBoundaryReceipt.javaRollbackTriggered=false
managedAuditAdapterBoundaryReceipt.javaRestoreExecuted=false
managedAuditAdapterBoundaryReceipt.readyForNodeV215DryRunAdapterCandidate=true
managedAuditAdapterBoundaryReceipt.readyForProductionAudit=false
managedAuditAdapterBoundaryReceipt.readyForProductionWindow=false
managedAuditAdapterBoundaryReceipt.nodeMayTreatAsProductionAuditRecord=false
verificationHint.responseSchemaVersion=java-release-approval-rehearsal-response-schema.v13
verificationHint.warningDigestInputs 包含 managedAuditAdapterBoundaryReceiptWarnings、nodeV215MayConnectManagedAudit、nodeV215MayCreateApprovalDecision、nodeV215MayWriteApprovalLedger、nodeV215MayExecuteSql、nodeV215MayTriggerDeployment、nodeV215MayTriggerRollback、nodeV215MayExecuteRestore
```

没有传入完整 Node v210 approval binding header 时，`managedAuditAdapterBoundaryReceipt.readyForNodeV215DryRunAdapterCandidate=false` 且 `receiptWarnings` 包含 `NODE_V215_SOURCE_APPROVAL_HANDOFF_MARKER_NOT_READY`。这一步只证明 Node v215 可在 dry-run adapter candidate 中读取 Java v77 receipt，并只能落本地 dry-run/test evidence；Java 不写 audit store，不执行 SQL/部署/回滚/restore，也不把该响应当生产 audit record。

v78 起，release approval rehearsal 增加 `managedAuditProductionAdapterPrerequisiteReceipt`，用于承接 Node v216 dry-run adapter archive verification，并给 Node v217 production-hardening readiness gate 明确生产适配器前置条件。该 receipt 只证明前置条件清单和禁止操作边界已经公开：Java 不创建真实 approval decision，不写 approval ledger，不持久化 production approval record，不写真实 managed audit store，不执行 SQL、部署、回滚或 restore；Node v217 也不得把它当作连接真实 managed audit 或执行生产窗口的授权：
```text
managedAuditProductionAdapterPrerequisiteReceipt.receiptVersion=java-release-approval-rehearsal-managed-audit-production-adapter-prerequisite-receipt.v1
managedAuditProductionAdapterPrerequisiteReceipt.sourceManagedAuditAdapterBoundaryReceiptVersion=java-release-approval-rehearsal-managed-audit-adapter-boundary-receipt.v1
managedAuditProductionAdapterPrerequisiteReceipt.sourceManagedAuditAdapterBoundarySchemaVersion=java-release-approval-rehearsal-response-schema.v11
managedAuditProductionAdapterPrerequisiteReceipt.consumedByNodeArchiveVerificationVersion=managed-audit-dry-run-adapter-archive-verification.v1
managedAuditProductionAdapterPrerequisiteReceipt.consumedByNodeArchiveVerificationState=verified-dry-run-adapter-archive
managedAuditProductionAdapterPrerequisiteReceipt.consumedByNodeArchiveVerificationEndpoint=/api/v1/audit/managed-audit-dry-run-adapter-archive-verification
managedAuditProductionAdapterPrerequisiteReceipt.nextNodeGateVersion=Node v217
managedAuditProductionAdapterPrerequisiteReceipt.nextNodeGateProfile=managed-audit-adapter-production-hardening-readiness-gate.v1
managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayConsume=true
managedAuditProductionAdapterPrerequisiteReceipt.operatorIdentityPrerequisiteDocumented=true
managedAuditProductionAdapterPrerequisiteReceipt.approvalDecisionSourcePrerequisiteDocumented=true
managedAuditProductionAdapterPrerequisiteReceipt.ledgerHandoffPrerequisiteDocumented=true
managedAuditProductionAdapterPrerequisiteReceipt.externalManagedAuditStorageConfigRequired=true
managedAuditProductionAdapterPrerequisiteReceipt.productionIdentityProviderRequired=true
managedAuditProductionAdapterPrerequisiteReceipt.approvalDecisionSourceRequired=true
managedAuditProductionAdapterPrerequisiteReceipt.ledgerHandoffRequired=true
managedAuditProductionAdapterPrerequisiteReceipt.javaCreatesApprovalDecision=false
managedAuditProductionAdapterPrerequisiteReceipt.javaWritesApprovalLedger=false
managedAuditProductionAdapterPrerequisiteReceipt.javaPersistsApprovalRecord=false
managedAuditProductionAdapterPrerequisiteReceipt.javaWritesManagedAuditStore=false
managedAuditProductionAdapterPrerequisiteReceipt.javaExecutesSql=false
managedAuditProductionAdapterPrerequisiteReceipt.javaTriggersDeployment=false
managedAuditProductionAdapterPrerequisiteReceipt.javaTriggersRollback=false
managedAuditProductionAdapterPrerequisiteReceipt.javaExecutesRestore=false
managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayConnectManagedAudit=false
managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayWriteApprovalLedger=false
managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayExecuteSql=false
managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayTriggerDeployment=false
managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayTriggerRollback=false
managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayExecuteRestore=false
managedAuditProductionAdapterPrerequisiteReceipt.readyForNodeV217ProductionHardeningReadinessGate=true
managedAuditProductionAdapterPrerequisiteReceipt.readyForProductionAudit=false
managedAuditProductionAdapterPrerequisiteReceipt.readyForProductionWindow=false
managedAuditProductionAdapterPrerequisiteReceipt.readyForProductionOperations=false
managedAuditProductionAdapterPrerequisiteReceipt.nodeMayTreatAsProductionAuditRecord=false
verificationHint.responseSchemaVersion=java-release-approval-rehearsal-response-schema.v13
verificationHint.warningDigestInputs includes managedAuditProductionAdapterPrerequisiteReceiptWarnings, nodeV217MayConnectManagedAudit, nodeV217MayWriteApprovalLedger, nodeV217MayExecuteSql, nodeV217MayTriggerDeployment, nodeV217MayTriggerRollback, nodeV217MayExecuteRestore
```

没有传入完整 Node v210 approval binding header 时，上游 v77 receipt 还未 ready，`managedAuditProductionAdapterPrerequisiteReceipt.readyForNodeV217ProductionHardeningReadinessGate=false` 且 `receiptWarnings` 包含 `NODE_V217_SOURCE_MANAGED_AUDIT_ADAPTER_BOUNDARY_RECEIPT_NOT_READY`。传入完整 header 后该 ready 字段可为 true，但仍不代表生产 audit/window/operations 授权，只允许 Node v217 继续做 production-hardening readiness gate。

v79 起，release approval rehearsal 增加 `opsEvidenceServiceQualitySplitReceipt`，用于承接 Node v218 audit route + managed-audit helper quality pass，并给 Node v219 managed audit adapter implementation precheck 标注 Java 侧 `OpsEvidenceService` 的 receipt / digest / hint / render / record 职责边界。该 receipt 是质量收口回执，不是大规模拆分类结果；Java 仍不创建 approval decision、不写 approval ledger、不持久化 approval record、不写 managed audit store、不执行 SQL、部署、回滚或 restore：
```text
opsEvidenceServiceQualitySplitReceipt.receiptVersion=java-release-approval-rehearsal-ops-evidence-service-quality-split-receipt.v1
opsEvidenceServiceQualitySplitReceipt.sourceProductionAdapterPrerequisiteReceiptVersion=java-release-approval-rehearsal-managed-audit-production-adapter-prerequisite-receipt.v1
opsEvidenceServiceQualitySplitReceipt.sourceProductionAdapterPrerequisiteSchemaVersion=java-release-approval-rehearsal-response-schema.v12
opsEvidenceServiceQualitySplitReceipt.consumedByNodeQualityPassVersion=Node v218
opsEvidenceServiceQualitySplitReceipt.consumedByNodeQualityPassProfile=audit-route-managed-audit-helper-quality-pass.v1
opsEvidenceServiceQualitySplitReceipt.nextNodePrecheckVersion=Node v219
opsEvidenceServiceQualitySplitReceipt.nextNodePrecheckProfile=managed-audit-adapter-implementation-precheck-packet.v1
opsEvidenceServiceQualitySplitReceipt.nodeV219MayConsume=true
opsEvidenceServiceQualitySplitReceipt.receiptResponsibilityDocumented=true
opsEvidenceServiceQualitySplitReceipt.digestResponsibilityDocumented=true
opsEvidenceServiceQualitySplitReceipt.hintResponsibilityDocumented=true
opsEvidenceServiceQualitySplitReceipt.renderResponsibilityDocumented=true
opsEvidenceServiceQualitySplitReceipt.recordResponsibilityDocumented=true
opsEvidenceServiceQualitySplitReceipt.firstSafeSplitApplied=false
opsEvidenceServiceQualitySplitReceipt.broadServiceSplitDeferred=true
opsEvidenceServiceQualitySplitReceipt.apiShapeChanged=false
opsEvidenceServiceQualitySplitReceipt.approvalDecisionCreated=false
opsEvidenceServiceQualitySplitReceipt.approvalLedgerWritten=false
opsEvidenceServiceQualitySplitReceipt.approvalRecordPersisted=false
opsEvidenceServiceQualitySplitReceipt.managedAuditStoreWritten=false
opsEvidenceServiceQualitySplitReceipt.sqlExecuted=false
opsEvidenceServiceQualitySplitReceipt.deploymentTriggered=false
opsEvidenceServiceQualitySplitReceipt.rollbackTriggered=false
opsEvidenceServiceQualitySplitReceipt.restoreExecuted=false
opsEvidenceServiceQualitySplitReceipt.readyForNodeV219ImplementationPrecheck=true
opsEvidenceServiceQualitySplitReceipt.readyForProductionAudit=false
opsEvidenceServiceQualitySplitReceipt.readyForProductionWindow=false
opsEvidenceServiceQualitySplitReceipt.nodeMayTreatAsProductionAuditRecord=false
verificationHint.responseSchemaVersion=java-release-approval-rehearsal-response-schema.v13
verificationHint.warningDigestInputs includes opsEvidenceServiceQualitySplitReceiptWarnings, qualitySplitApiShapeChanged, qualitySplitApprovalDecisionCreated, qualitySplitApprovalLedgerWritten, qualitySplitManagedAuditStoreWritten, qualitySplitSqlExecuted
```

没有传入完整 Node v210 approval binding header 时，上游 v78 receipt 还未 ready，`opsEvidenceServiceQualitySplitReceipt.readyForNodeV219ImplementationPrecheck=false` 且 `receiptWarnings` 包含 `NODE_V219_SOURCE_PRODUCTION_ADAPTER_PREREQUISITE_RECEIPT_NOT_READY`。传入完整 header 后该 ready 字段可为 true，但仍只允许 Node v219 做 implementation precheck；真实 managed audit adapter wiring、生产审计写入和生产窗口仍然关闭。

v80 起，release approval rehearsal 增加 `managedAuditAdapterImplementationGuardReceipt`，用于承接 Node v220 managed audit adapter disabled shell，并给 Node v221 local file/sqlite adapter candidate dry-run 提供 Java 侧只读 guard digest。该 receipt 只确认 Java 在 adapter shell 存在后仍不创建 approval decision、不写 approval ledger、不持久化 approval record、不写 managed audit store、不执行 SQL、部署、回滚或 restore；Node v220 仍保持 disabled adapter，local-dry-run 只被声明为后续候选：
```text
managedAuditAdapterImplementationGuardReceipt.receiptVersion=java-release-approval-rehearsal-managed-audit-adapter-implementation-guard-receipt.v1
managedAuditAdapterImplementationGuardReceipt.sourceQualitySplitReceiptVersion=java-release-approval-rehearsal-ops-evidence-service-quality-split-receipt.v1
managedAuditAdapterImplementationGuardReceipt.sourceQualitySplitSchemaVersion=java-release-approval-rehearsal-response-schema.v13
managedAuditAdapterImplementationGuardReceipt.consumedByNodeDisabledShellVersion=Node v220
managedAuditAdapterImplementationGuardReceipt.consumedByNodeDisabledShellProfile=managed-audit-adapter-disabled-shell.v1
managedAuditAdapterImplementationGuardReceipt.consumedByNodeDisabledShellEndpoint=/api/v1/audit/managed-audit-adapter-disabled-shell
managedAuditAdapterImplementationGuardReceipt.consumedByNodeDisabledShellState=disabled-shell-ready
managedAuditAdapterImplementationGuardReceipt.nextNodeCandidateVersion=Node v221
managedAuditAdapterImplementationGuardReceipt.nextNodeCandidateProfile=managed-audit-local-adapter-candidate-dry-run.v1
managedAuditAdapterImplementationGuardReceipt.nodeV221MayConsume=true
managedAuditAdapterImplementationGuardReceipt.nodeV220DisabledShellReady=true
managedAuditAdapterImplementationGuardReceipt.nodeV220SelectedAdapterDisabled=true
managedAuditAdapterImplementationGuardReceipt.nodeV220LocalDryRunOnlyDeclared=true
managedAuditAdapterImplementationGuardReceipt.nodeV220AppendWritten=false
managedAuditAdapterImplementationGuardReceipt.nodeV220QueryReturnedRecords=false
managedAuditAdapterImplementationGuardReceipt.nodeV220ExternalManagedAuditAccessed=false
managedAuditAdapterImplementationGuardReceipt.nodeV220LocalDryRunWritePerformed=false
managedAuditAdapterImplementationGuardReceipt.javaApprovalDecisionCreated=false
managedAuditAdapterImplementationGuardReceipt.javaApprovalLedgerWritten=false
managedAuditAdapterImplementationGuardReceipt.javaApprovalRecordPersisted=false
managedAuditAdapterImplementationGuardReceipt.javaManagedAuditStoreWritten=false
managedAuditAdapterImplementationGuardReceipt.javaSqlExecuted=false
managedAuditAdapterImplementationGuardReceipt.javaDeploymentTriggered=false
managedAuditAdapterImplementationGuardReceipt.javaRollbackTriggered=false
managedAuditAdapterImplementationGuardReceipt.javaRestoreExecuted=false
managedAuditAdapterImplementationGuardReceipt.readyForNodeV221LocalAdapterCandidateDryRun=true
managedAuditAdapterImplementationGuardReceipt.readyForProductionAudit=false
managedAuditAdapterImplementationGuardReceipt.readyForProductionWindow=false
managedAuditAdapterImplementationGuardReceipt.nodeMayTreatAsProductionAuditRecord=false
managedAuditAdapterImplementationGuardReceipt.guardDigest=sha256:<stable-java-v80-guard-digest>
verificationHint.responseSchemaVersion=java-release-approval-rehearsal-response-schema.v14
verificationHint.warningDigestInputs includes managedAuditAdapterImplementationGuardReceiptWarnings, implementationGuardDigest, implementationGuardJavaApprovalLedgerWritten, implementationGuardJavaManagedAuditStoreWritten, implementationGuardJavaSqlExecuted, implementationGuardNodeV220AppendWritten, implementationGuardNodeV220ExternalManagedAuditAccessed, implementationGuardNodeV220LocalDryRunWritePerformed
```

没有传入完整 Node v210 approval binding header 时，上游 v79 receipt 还未 ready，`managedAuditAdapterImplementationGuardReceipt.readyForNodeV221LocalAdapterCandidateDryRun=false` 且 `guardWarnings` 包含 `NODE_V221_SOURCE_OPS_EVIDENCE_SERVICE_QUALITY_SPLIT_RECEIPT_NOT_READY`。传入完整 header 后该 ready 字段可为 true，但仍只允许 Node v221 做本地 file/sqlite candidate dry-run；真实外部 managed audit、生产审计写入和生产窗口仍然关闭。

v81 起，release approval rehearsal 增加 `managedAuditExternalAdapterMigrationGuardReceipt`，用于承接 Node v222 local adapter candidate verification report，并给 Node v223 external adapter connection readiness review 提供真实外部 adapter 连接前的只读 migration / credential guard。该 receipt 只说明真实外部 adapter 前仍需要 owner approval、schema migration review、credential review；Java 不读取或保存 credential value，不打开外部 managed audit 连接，不执行 schema migration SQL，不写 ledger 或 managed audit store：
```text
managedAuditExternalAdapterMigrationGuardReceipt.receiptVersion=java-release-approval-rehearsal-managed-audit-external-adapter-migration-guard-receipt.v1
managedAuditExternalAdapterMigrationGuardReceipt.sourceImplementationGuardReceiptVersion=java-release-approval-rehearsal-managed-audit-adapter-implementation-guard-receipt.v1
managedAuditExternalAdapterMigrationGuardReceipt.sourceImplementationGuardSchemaVersion=java-release-approval-rehearsal-response-schema.v14
managedAuditExternalAdapterMigrationGuardReceipt.consumedByNodeVerificationReportVersion=Node v222
managedAuditExternalAdapterMigrationGuardReceipt.consumedByNodeVerificationReportProfile=managed-audit-local-adapter-candidate-verification-report.v1
managedAuditExternalAdapterMigrationGuardReceipt.consumedByNodeVerificationReportEndpoint=/api/v1/audit/managed-audit-local-adapter-candidate-verification-report
managedAuditExternalAdapterMigrationGuardReceipt.consumedByNodeVerificationReportState=local-adapter-candidate-verification-ready
managedAuditExternalAdapterMigrationGuardReceipt.nextNodeReviewVersion=Node v223
managedAuditExternalAdapterMigrationGuardReceipt.nextNodeReviewProfile=managed-audit-external-adapter-connection-readiness-review.v1
managedAuditExternalAdapterMigrationGuardReceipt.nodeV223MayConsume=true
managedAuditExternalAdapterMigrationGuardReceipt.nodeV222VerificationReportReady=true
managedAuditExternalAdapterMigrationGuardReceipt.nodeV222ReadOnlyReport=true
managedAuditExternalAdapterMigrationGuardReceipt.nodeV222SourceEndpointRerunPerformed=false
managedAuditExternalAdapterMigrationGuardReceipt.nodeV222AdditionalLocalDryRunWritePerformed=false
managedAuditExternalAdapterMigrationGuardReceipt.nodeV222ConnectsManagedAudit=false
managedAuditExternalAdapterMigrationGuardReceipt.nodeV222ReadyForProductionAudit=false
managedAuditExternalAdapterMigrationGuardReceipt.ownerApprovalRequiredBeforeConnection=true
managedAuditExternalAdapterMigrationGuardReceipt.schemaMigrationReviewRequired=true
managedAuditExternalAdapterMigrationGuardReceipt.credentialReviewRequired=true
managedAuditExternalAdapterMigrationGuardReceipt.credentialValueReadByJava=false
managedAuditExternalAdapterMigrationGuardReceipt.credentialValueStoredByJava=false
managedAuditExternalAdapterMigrationGuardReceipt.externalManagedAuditConnectionOpened=false
managedAuditExternalAdapterMigrationGuardReceipt.externalManagedAuditSchemaMigrated=false
managedAuditExternalAdapterMigrationGuardReceipt.javaApprovalDecisionCreated=false
managedAuditExternalAdapterMigrationGuardReceipt.javaApprovalLedgerWritten=false
managedAuditExternalAdapterMigrationGuardReceipt.javaApprovalRecordPersisted=false
managedAuditExternalAdapterMigrationGuardReceipt.javaManagedAuditStoreWritten=false
managedAuditExternalAdapterMigrationGuardReceipt.javaSqlExecuted=false
managedAuditExternalAdapterMigrationGuardReceipt.javaDeploymentTriggered=false
managedAuditExternalAdapterMigrationGuardReceipt.javaRollbackTriggered=false
managedAuditExternalAdapterMigrationGuardReceipt.javaRestoreExecuted=false
managedAuditExternalAdapterMigrationGuardReceipt.readyForNodeV223ExternalAdapterConnectionReadinessReview=true
managedAuditExternalAdapterMigrationGuardReceipt.readyForProductionAudit=false
managedAuditExternalAdapterMigrationGuardReceipt.readyForProductionWindow=false
managedAuditExternalAdapterMigrationGuardReceipt.nodeMayTreatAsProductionAuditRecord=false
managedAuditExternalAdapterMigrationGuardReceipt.guardDigest=sha256:<stable-java-v81-guard-digest>
verificationHint.responseSchemaVersion=java-release-approval-rehearsal-response-schema.v15
verificationHint.warningDigestInputs includes managedAuditExternalAdapterMigrationGuardReceiptWarnings, externalAdapterMigrationGuardDigest, externalAdapterMigrationCredentialValueReadByJava, externalAdapterMigrationConnectionOpened, externalAdapterMigrationSchemaMigrated, externalAdapterMigrationJavaManagedAuditStoreWritten, externalAdapterMigrationJavaSqlExecuted, externalAdapterMigrationNodeV222SourceEndpointRerunPerformed, externalAdapterMigrationNodeV222AdditionalLocalDryRunWritePerformed
```

没有传入完整 Node v210 approval binding header 时，上游 v80 receipt 还未 ready，`managedAuditExternalAdapterMigrationGuardReceipt.readyForNodeV223ExternalAdapterConnectionReadinessReview=false` 且 `guardWarnings` 包含 `NODE_V223_SOURCE_IMPLEMENTATION_GUARD_RECEIPT_NOT_READY`。传入完整 header 后该 ready 字段可为 true，但仍只允许 Node v223 做 connection readiness review；不得读取生产 credential、不得连接真实外部 managed audit、不得打开生产审计窗口。

v82 起，release approval rehearsal 增加 `managedAuditSandboxAdapterApprovalSchemaGuardReceipt`，用于承接 Node v224 managed audit sandbox adapter dry-run plan，并给 Node v225 sandbox adapter dry-run package 提供 Java 侧 approval / schema rehearsal guard。该 receipt 按 owner approval、schema rehearsal、credential handle、execution、quality gate 分组，避免在 `OpsEvidenceService` 继续堆长布尔构造链；Java 仍不读取 credential value，不连接外部 managed audit，不执行 schema migration SQL，不写 approval ledger 或 managed audit store：
```text
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.receiptVersion=java-release-approval-rehearsal-managed-audit-sandbox-adapter-approval-schema-guard-receipt.v1
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.sourceExternalAdapterMigrationGuardReceiptVersion=java-release-approval-rehearsal-managed-audit-external-adapter-migration-guard-receipt.v1
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.sourceExternalAdapterMigrationGuardSchemaVersion=java-release-approval-rehearsal-response-schema.v15
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.consumedByNodeSandboxPlanVersion=Node v224
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.consumedByNodeSandboxPlanProfile=managed-audit-sandbox-adapter-dry-run-plan.v1
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.consumedByNodeSandboxPlanEndpoint=/api/v1/audit/managed-audit-sandbox-adapter-dry-run-plan
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.consumedByNodeSandboxPlanState=sandbox-adapter-dry-run-plan-ready
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.nextNodePackageVersion=Node v225
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.nextNodePackageProfile=managed-audit-sandbox-adapter-dry-run-package.v1
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.nodeV225MayConsume=true
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.nodeV224SandboxPlan.readyForManagedAuditSandboxAdapterDryRunPlan=true
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.nodeV224SandboxPlan.readyForManagedAuditSandboxAdapterDryRunPackage=false
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.nodeV224SandboxPlan.readOnlyPlan=true
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.nodeV224SandboxPlan.connectsManagedAudit=false
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.nodeV224SandboxPlan.readsManagedAuditCredential=false
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.ownerApprovalBoundary.ownerApprovalArtifactRequired=true
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.ownerApprovalBoundary.ownerApprovalArtifactProvidedByJava=false
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.schemaRehearsalBoundary.schemaMigrationRehearsalRequired=true
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.schemaRehearsalBoundary.schemaMigrationChecklistRequired=true
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.schemaRehearsalBoundary.schemaMigrationExecutionAllowed=false
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.schemaRehearsalBoundary.schemaMigrationSqlExecutedByJava=false
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.credentialBoundary.sandboxCredentialHandleRequired=true
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.credentialBoundary.sandboxCredentialHandleName=ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.credentialBoundary.productionCredentialAllowed=false
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.credentialBoundary.credentialValueReadByJava=false
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.executionBoundary.externalManagedAuditConnectionOpened=false
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.executionBoundary.javaManagedAuditStoreWritten=false
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.executionBoundary.javaSqlExecuted=false
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.qualityGateBoundary.builderOrHelperSplitApplied=true
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.qualityGateBoundary.longBooleanConstructorAvoided=true
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.qualityGateBoundary.receiptFieldsGroupedByBoundary=true
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.qualityGateBoundary.opsEvidenceServiceOnlyWiresReceipt=true
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.readyForNodeV225SandboxAdapterDryRunPackage=true
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.readyForProductionAudit=false
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.readyForProductionWindow=false
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.nodeMayTreatAsProductionAuditRecord=false
managedAuditSandboxAdapterApprovalSchemaGuardReceipt.guardDigest=sha256:<stable-java-v82-guard-digest>
verificationHint.responseSchemaVersion=java-release-approval-rehearsal-response-schema.v16
verificationHint.warningDigestInputs includes managedAuditSandboxAdapterApprovalSchemaGuardReceiptWarnings, sandboxAdapterApprovalSchemaGuardDigest, sandboxAdapterOwnerApprovalArtifactProvidedByJava, sandboxAdapterSchemaMigrationSqlExecutedByJava, sandboxAdapterCredentialValueReadByJava, sandboxAdapterExternalManagedAuditConnectionOpened, sandboxAdapterJavaManagedAuditStoreWritten, sandboxAdapterJavaSqlExecuted, sandboxAdapterQualityGateBuilderOrHelperSplitApplied
```

没有传入完整 Node v210 approval binding header 时，上游 v81 receipt 还未 ready，`managedAuditSandboxAdapterApprovalSchemaGuardReceipt.readyForNodeV225SandboxAdapterDryRunPackage=false` 且 `guardWarnings` 包含 `NODE_V225_SOURCE_EXTERNAL_ADAPTER_MIGRATION_GUARD_RECEIPT_NOT_READY`。传入完整 header 后该 ready 字段可为 true，但仍只允许 Node v225 生成 sandbox adapter dry-run package；owner approval artifact、schema rehearsal checklist、sandbox credential handle 和 mini-kv v91 evidence 必须由后续只读证据链提供，Java 不执行任何连接、SQL、部署、回滚或 restore。

v83 起，release approval rehearsal 继续做 contract-preserving refactor，把 `verificationHint` 的构造与 `warningDigest` / `proofClaims` / `nodeVerificationActions` 从 `OpsEvidenceService` 抽到 `ReleaseApprovalVerificationHintBuilder`。这次拆分不改任何响应字段名、不改 digest 顺序、不改 proof claims、不改 read-only 边界；`OpsEvidenceService` 只保留一层转发，避免继续在主服务里堆 verification hint 的长方法。

v84 起，release approval rehearsal 继续做 contract-preserving refactor，把 v77-v80 managed-audit receipt 构造链、verification warning digest 和共用 digest helper 从 `OpsEvidenceService` / `ReleaseApprovalVerificationHintBuilder` 拆到专用 builder。该版本不新增响应字段，不修改 schema version、warning digest 输入、proof claims 或 Node verification action；`OpsEvidenceService` 只负责串联 receipt builder。

v85 起，release approval rehearsal 继续做更大幅度的 contract-preserving refactor，把 response 组装、header 归一化、request/operator/CI/artifact/live hint、audit/approval handoff hint、approval handoff marker 和 failure taxonomy 拆到专用 builder。该版本不新增响应字段，不修改 schema version、warning digest 输入、proof claims 或 read-only 边界；`OpsEvidenceService` 从 2605 行降到 1443 行，主服务只保留对外重载和 evidence 编排。
v86 起，继续收口 builder 内部残留的裸布尔位置参数，把 request / operator / CI / artifact / live / handoff / marker 的内部状态改成语义 record / flags helper。该版本不改 response 契约，不改 schema version，不改 warning digest 输入顺序；`OpsEvidenceService` 仍保持 1443 行，外部可读路径不变，只是 builder 内部不再直接散落长串 `true/false`。
v87 起，release approval rehearsal 新增 `managedAuditSandboxConnectionOperatorHandoffMarker`，用于承接 Node v228 `managed-audit-manual-sandbox-connection-operator-packet.v1`，并给 Node v229 packet verification 提供 Java 侧只读 handoff marker。该 marker 按 sandbox window、operator packet、credential、schema rehearsal、rollback path、Java execution 分组，说明 Java 只识别 owner artifact / schema rehearsal / credential handle / rollback / timeout / abort marker 字段；Java 仍不打开 sandbox connection，不读取 credential value，不执行 schema migration SQL，不写 approval ledger 或 managed audit store：

```text
managedAuditSandboxConnectionOperatorHandoffMarker.markerVersion=java-release-approval-rehearsal-managed-audit-sandbox-connection-operator-handoff-marker.v1
managedAuditSandboxConnectionOperatorHandoffMarker.sourceSandboxAdapterApprovalSchemaGuardReceiptVersion=java-release-approval-rehearsal-managed-audit-sandbox-adapter-approval-schema-guard-receipt.v1
managedAuditSandboxConnectionOperatorHandoffMarker.sourceSandboxAdapterApprovalSchemaGuardSchemaVersion=java-release-approval-rehearsal-response-schema.v16
managedAuditSandboxConnectionOperatorHandoffMarker.consumedByNodeEvidenceChecklistVersion=Node v227
managedAuditSandboxConnectionOperatorHandoffMarker.consumedByNodeEvidenceChecklistProfile=managed-audit-manual-sandbox-connection-evidence-checklist.v1
managedAuditSandboxConnectionOperatorHandoffMarker.consumedByNodeOperatorPacketVersion=Node v228
managedAuditSandboxConnectionOperatorHandoffMarker.consumedByNodeOperatorPacketProfile=managed-audit-manual-sandbox-connection-operator-packet.v1
managedAuditSandboxConnectionOperatorHandoffMarker.consumedByNodeOperatorPacketEndpoint=/api/v1/audit/managed-audit-manual-sandbox-connection-operator-packet
managedAuditSandboxConnectionOperatorHandoffMarker.nextNodePacketVerificationVersion=Node v229
managedAuditSandboxConnectionOperatorHandoffMarker.nextNodePacketVerificationProfile=managed-audit-manual-sandbox-connection-packet-verification.v1
managedAuditSandboxConnectionOperatorHandoffMarker.nodeV229MayConsume=true
managedAuditSandboxConnectionOperatorHandoffMarker.sandboxConnectionWindowBoundary.manualSandboxConnectionWindowRequired=true
managedAuditSandboxConnectionOperatorHandoffMarker.sandboxConnectionWindowBoundary.manualSandboxConnectionWindowOpenedByJava=false
managedAuditSandboxConnectionOperatorHandoffMarker.sandboxConnectionWindowBoundary.connectionExecutionAllowed=false
managedAuditSandboxConnectionOperatorHandoffMarker.operatorPacketBoundary.ownerApprovalArtifactIdField=ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID
managedAuditSandboxConnectionOperatorHandoffMarker.operatorPacketBoundary.schemaRehearsalIdField=ORDEROPS_MANAGED_AUDIT_SCHEMA_REHEARSAL_ID
managedAuditSandboxConnectionOperatorHandoffMarker.operatorPacketBoundary.operatorPacketReadOnly=true
managedAuditSandboxConnectionOperatorHandoffMarker.operatorPacketBoundary.packetCreatesApprovalDecision=false
managedAuditSandboxConnectionOperatorHandoffMarker.credentialBoundary.credentialHandleNameField=ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE
managedAuditSandboxConnectionOperatorHandoffMarker.credentialBoundary.credentialValueReadByJava=false
managedAuditSandboxConnectionOperatorHandoffMarker.schemaRehearsalBoundary.schemaMigrationSqlExecutedByJava=false
managedAuditSandboxConnectionOperatorHandoffMarker.rollbackPathBoundary.rollbackPathIdField=ORDEROPS_MANAGED_AUDIT_ROLLBACK_PATH_ID
managedAuditSandboxConnectionOperatorHandoffMarker.rollbackPathBoundary.manualAbortMarkerField=ORDEROPS_MANAGED_AUDIT_MANUAL_ABORT
managedAuditSandboxConnectionOperatorHandoffMarker.rollbackPathBoundary.timeoutBudgetMs=15000
managedAuditSandboxConnectionOperatorHandoffMarker.rollbackPathBoundary.rollbackExecutionAllowedByJava=false
managedAuditSandboxConnectionOperatorHandoffMarker.javaExecutionBoundary.externalManagedAuditConnectionOpenedByJava=false
managedAuditSandboxConnectionOperatorHandoffMarker.javaExecutionBoundary.approvalLedgerWrittenByJava=false
managedAuditSandboxConnectionOperatorHandoffMarker.javaExecutionBoundary.sqlExecutedByJava=false
managedAuditSandboxConnectionOperatorHandoffMarker.readyForNodeV229ManualSandboxConnectionPacketVerification=true
managedAuditSandboxConnectionOperatorHandoffMarker.readyForManagedAuditSandboxAdapterConnection=false
managedAuditSandboxConnectionOperatorHandoffMarker.readyForProductionAudit=false
managedAuditSandboxConnectionOperatorHandoffMarker.nodeMayTreatAsProductionAuditRecord=false
managedAuditSandboxConnectionOperatorHandoffMarker.markerDigest=sha256:<stable-java-v87-marker-digest>
verificationHint.responseSchemaVersion=java-release-approval-rehearsal-response-schema.v17
verificationHint.warningDigestInputs includes managedAuditSandboxConnectionOperatorHandoffMarkerWarnings, sandboxConnectionOperatorHandoffMarkerDigest, sandboxConnectionOperatorWindowOpenedByJava, sandboxConnectionCredentialValueReadByJava, sandboxConnectionSchemaMigrationSqlExecutedByJava, sandboxConnectionRollbackTriggeredByJava, sandboxConnectionExternalManagedAuditConnectionOpenedByJava
```

没有传入完整 Node v210 approval binding header 时，上游 v82 sandbox guard 还未 ready，`managedAuditSandboxConnectionOperatorHandoffMarker.readyForNodeV229ManualSandboxConnectionPacketVerification=false` 且 `markerWarnings` 包含 `NODE_V229_SOURCE_SANDBOX_ADAPTER_APPROVAL_SCHEMA_GUARD_RECEIPT_NOT_READY`。传入完整 header 后该 ready 字段可为 true，但仍只允许 Node v229 做 packet verification；Java 不执行连接、credential value 读取、SQL、部署、回滚或 restore。

v88 起，release approval rehearsal 新增 `managedAuditSandboxConnectionPreflightEchoMarker`，用于承接 Node v230 `managed-audit-manual-sandbox-connection-preflight-gate.v1`，并给 Node v231 preflight verification 提供 Java 侧只读 echo marker。该 marker 按 manual window、preflight fields、credential、schema rehearsal、rollback path、Java execution 分组，固定 Node v230 预检 gate 的 7 个字段，同时声明 Java 仍不打开 sandbox connection、不读取 credential value、不执行 schema migration SQL、不写 approval ledger 或 managed audit store，也不自动启动 Java / mini-kv / 外部 audit 服务：

```text
managedAuditSandboxConnectionPreflightEchoMarker.markerVersion=java-release-approval-rehearsal-managed-audit-sandbox-connection-preflight-echo-marker.v1
managedAuditSandboxConnectionPreflightEchoMarker.sourceSandboxConnectionOperatorHandoffMarkerVersion=java-release-approval-rehearsal-managed-audit-sandbox-connection-operator-handoff-marker.v1
managedAuditSandboxConnectionPreflightEchoMarker.sourceSandboxConnectionOperatorHandoffSchemaVersion=java-release-approval-rehearsal-response-schema.v17
managedAuditSandboxConnectionPreflightEchoMarker.consumedByNodePreflightGateVersion=Node v230
managedAuditSandboxConnectionPreflightEchoMarker.consumedByNodePreflightGateProfile=managed-audit-manual-sandbox-connection-preflight-gate.v1
managedAuditSandboxConnectionPreflightEchoMarker.consumedByNodePreflightGateEndpoint=/api/v1/audit/managed-audit-manual-sandbox-connection-preflight-gate
managedAuditSandboxConnectionPreflightEchoMarker.consumedByNodePreflightGateState=manual-sandbox-connection-preflight-gate-ready
managedAuditSandboxConnectionPreflightEchoMarker.nextNodePreflightVerificationVersion=Node v231
managedAuditSandboxConnectionPreflightEchoMarker.nextNodePreflightVerificationProfile=managed-audit-manual-sandbox-connection-preflight-verification.v1
managedAuditSandboxConnectionPreflightEchoMarker.nodeV231MayConsume=true
managedAuditSandboxConnectionPreflightEchoMarker.sandboxConnectionWindowBoundary.manualWindowFlagName=ORDEROPS_MANAGED_AUDIT_MANUAL_SANDBOX_WINDOW_APPROVED
managedAuditSandboxConnectionPreflightEchoMarker.sandboxConnectionWindowBoundary.manualWindowOpenByDefault=false
managedAuditSandboxConnectionPreflightEchoMarker.sandboxConnectionWindowBoundary.manualWindowOpenedByJava=false
managedAuditSandboxConnectionPreflightEchoMarker.sandboxConnectionWindowBoundary.nodeAutoStartAllowed=false
managedAuditSandboxConnectionPreflightEchoMarker.preflightFieldBoundary.ownerApprovalArtifactIdField=ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID
managedAuditSandboxConnectionPreflightEchoMarker.credentialBoundary.credentialHandleNameField=ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE
managedAuditSandboxConnectionPreflightEchoMarker.preflightFieldBoundary.schemaRehearsalIdField=ORDEROPS_MANAGED_AUDIT_SCHEMA_REHEARSAL_ID
managedAuditSandboxConnectionPreflightEchoMarker.preflightFieldBoundary.rollbackPathIdField=ORDEROPS_MANAGED_AUDIT_ROLLBACK_PATH_ID
managedAuditSandboxConnectionPreflightEchoMarker.preflightFieldBoundary.timeoutBudgetMs=15000
managedAuditSandboxConnectionPreflightEchoMarker.preflightFieldBoundary.manualAbortMarkerField=ORDEROPS_MANAGED_AUDIT_MANUAL_ABORT
managedAuditSandboxConnectionPreflightEchoMarker.preflightFieldBoundary.preflightGateReadOnly=true
managedAuditSandboxConnectionPreflightEchoMarker.preflightFieldBoundary.gateCreatesConnectionCommand=false
managedAuditSandboxConnectionPreflightEchoMarker.credentialBoundary.credentialValueReadByJava=false
managedAuditSandboxConnectionPreflightEchoMarker.schemaRehearsalBoundary.schemaMigrationSqlExecutedByJava=false
managedAuditSandboxConnectionPreflightEchoMarker.rollbackPathBoundary.rollbackExecutionAllowedByJava=false
managedAuditSandboxConnectionPreflightEchoMarker.javaExecutionBoundary.externalManagedAuditConnectionOpenedByJava=false
managedAuditSandboxConnectionPreflightEchoMarker.javaExecutionBoundary.approvalLedgerWrittenByJava=false
managedAuditSandboxConnectionPreflightEchoMarker.javaExecutionBoundary.sqlExecutedByJava=false
managedAuditSandboxConnectionPreflightEchoMarker.readyForNodeV231ManualSandboxConnectionPreflightVerification=true
managedAuditSandboxConnectionPreflightEchoMarker.readyForManagedAuditSandboxAdapterConnection=false
managedAuditSandboxConnectionPreflightEchoMarker.readyForProductionAudit=false
managedAuditSandboxConnectionPreflightEchoMarker.nodeMayTreatAsProductionAuditRecord=false
managedAuditSandboxConnectionPreflightEchoMarker.markerDigest=sha256:<stable-java-v88-marker-digest>
managedAuditSandboxConnectionPreconditionReceipt.receiptVersion=java-release-approval-rehearsal-managed-audit-sandbox-connection-precondition-receipt.v1
managedAuditSandboxConnectionPreconditionReceipt.consumedByNodeBlockedExecutionRehearsalProfile=managed-audit-manual-sandbox-connection-blocked-execution-rehearsal.v1
managedAuditSandboxConnectionPreconditionReceipt.nextNodePreconditionIntakeVersion=Node v235
managedAuditSandboxConnectionPreconditionReceipt.ownerApprovalBoundary.ownerApprovalArtifactIdField=ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID
managedAuditSandboxConnectionPreconditionReceipt.credentialBoundary.credentialHandleReviewRequired=true
managedAuditSandboxConnectionPreconditionReceipt.credentialBoundary.credentialValueReadByJava=false
managedAuditSandboxConnectionPreconditionReceipt.schemaRehearsalBoundary.schemaMigrationSqlExecutedByJava=false
managedAuditSandboxConnectionPreconditionReceipt.rollbackPathBoundary.timeoutBudgetMs=15000
managedAuditSandboxConnectionPreconditionReceipt.javaExecutionBoundary.externalManagedAuditConnectionOpenedByJava=false
managedAuditSandboxConnectionPreconditionReceipt.javaExecutionBoundary.actualConnectionAttemptedByJava=false
managedAuditSandboxConnectionPreconditionReceipt.readyForNodeV235ManualSandboxConnectionPreconditionIntake=true
managedAuditSandboxConnectionPreconditionReceipt.readyForManagedAuditSandboxAdapterConnection=false
managedAuditSandboxConnectionPreconditionReceipt.receiptDigest=sha256:<stable-java-v91-receipt-digest>
managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.receiptVersion=java-release-approval-rehearsal-managed-audit-sandbox-connection-dry-run-envelope-echo-receipt.v1
managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.sourceSandboxConnectionPreconditionReceiptSchemaVersion=java-release-approval-rehearsal-response-schema.v19
managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.consumedByNodeDryRunRequestEnvelopeProfile=managed-audit-manual-sandbox-connection-dry-run-request-envelope.v1
managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.nextNodeReadinessGateVersion=Node v237
managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.envelopeFieldBoundary.ownerApprovalArtifactIdField=ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID
managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.envelopeFieldBoundary.credentialHandleNameField=ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE
managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.envelopeFieldBoundary.timeoutBudgetField=timeoutBudgetMs
managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.credentialBoundary.credentialHandleOnly=true
managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.credentialBoundary.credentialValueIncludedInEnvelope=false
managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.credentialBoundary.credentialValueReadByJava=false
managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.javaExecutionBoundary.actualConnectionAttemptedByJava=false
managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.javaExecutionBoundary.schemaMigrationSqlExecutedByJava=false
managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava=false
managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.readyForNodeV237ManualSandboxConnectionReadinessGate=true
managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.readyForManagedAuditSandboxAdapterConnection=false
managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.receiptDigest=sha256:<stable-java-v92-receipt-digest>
verificationHint.responseSchemaVersion=java-release-approval-rehearsal-response-schema.v20
verificationHint.warningDigestInputs includes managedAuditSandboxConnectionPreflightEchoMarkerWarnings, sandboxConnectionPreflightEchoMarkerDigest, sandboxConnectionPreflightManualWindowOpenedByJava, sandboxConnectionPreflightManualWindowOpenByDefault, sandboxConnectionPreflightCredentialValueReadByJava, sandboxConnectionPreflightSchemaMigrationSqlExecutedByJava, sandboxConnectionPreflightExternalManagedAuditConnectionOpenedByJava, sandboxConnectionPreflightNodeAutoStartAllowed
verificationHint.warningDigestInputs includes managedAuditSandboxConnectionPreconditionReceiptWarnings, sandboxConnectionPreconditionReceiptDigest, sandboxConnectionPreconditionCredentialValueReadByJava, sandboxConnectionPreconditionSchemaMigrationSqlExecutedByJava, sandboxConnectionPreconditionExternalManagedAuditConnectionOpenedByJava, sandboxConnectionPreconditionActualConnectionAttemptedByJava
verificationHint.warningDigestInputs includes managedAuditSandboxConnectionDryRunEnvelopeEchoReceiptWarnings, sandboxConnectionDryRunEnvelopeEchoReceiptDigest, sandboxConnectionDryRunEnvelopeCredentialValueIncluded, sandboxConnectionDryRunEnvelopeCredentialValueReadByJava, sandboxConnectionDryRunEnvelopeActualConnectionAttemptedByJava, sandboxConnectionDryRunEnvelopeSchemaMigrationSqlExecutedByJava, sandboxConnectionDryRunEnvelopeApprovalLedgerWrittenByJava
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.receiptVersion=java-release-approval-rehearsal-managed-audit-sandbox-connection-operator-window-checklist-echo-receipt.v1
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.sourceSandboxConnectionDryRunEnvelopeEchoReceiptSchemaVersion=java-release-approval-rehearsal-response-schema.v20
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.consumedByNodeOperatorWindowChecklistProfile=managed-audit-manual-sandbox-connection-operator-window-checklist.v1
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.nextNodeEvidenceVerificationVersion=Node v239
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.checklistFieldBoundary.requiredApprovalCount=3
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.checklistFieldBoundary.checklistStepCount=8
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.checklistFieldBoundary.pauseConditionCount=8
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.checklistFieldBoundary.forbiddenOperationCount=6
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.checklistFieldBoundary.operatorChecklistReadOnly=true
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.checklistFieldBoundary.checklistCreatesConnectionCommand=false
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.approvalBoundary.approvalItemCount=3
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.approvalBoundary.approvalLedgerWrittenByJava=false
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.credentialBoundary.credentialHandleOnly=true
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.credentialBoundary.credentialValueIncludedInChecklist=false
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.credentialBoundary.credentialValueReadByJava=false
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.javaExecutionBoundary.actualConnectionAttemptedByJava=false
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.javaExecutionBoundary.schemaMigrationSqlExecutedByJava=false
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava=false
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.readyForNodeV239ManualSandboxConnectionEvidenceVerification=true
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.readyForManagedAuditSandboxAdapterConnection=false
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.receiptDigest=sha256:<stable-java-v93-receipt-digest>
verificationHint.responseSchemaVersion=java-release-approval-rehearsal-response-schema.v21
verificationHint.warningDigestInputs includes managedAuditSandboxConnectionOperatorWindowChecklistEchoReceiptWarnings, sandboxConnectionOperatorWindowChecklistEchoReceiptDigest, sandboxConnectionOperatorWindowChecklistCredentialValueIncluded, sandboxConnectionOperatorWindowChecklistCredentialValueReadByJava, sandboxConnectionOperatorWindowChecklistActualConnectionAttemptedByJava, sandboxConnectionOperatorWindowChecklistSchemaMigrationSqlExecutedByJava, sandboxConnectionOperatorWindowChecklistApprovalLedgerWrittenByJava
```

没有传入完整 Node v210 approval binding header 时，上游 v87 operator handoff marker 还未 ready，`managedAuditSandboxConnectionPreflightEchoMarker.readyForNodeV231ManualSandboxConnectionPreflightVerification=false` 且 `markerWarnings` 包含 `NODE_V231_SOURCE_SANDBOX_CONNECTION_OPERATOR_HANDOFF_MARKER_NOT_READY`；v91 precondition receipt 也会保持 `readyForNodeV235ManualSandboxConnectionPreconditionIntake=false` 并追加 `NODE_V235_SOURCE_SANDBOX_CONNECTION_PREFLIGHT_ECHO_MARKER_NOT_READY`，v92 dry-run envelope echo receipt 会继续保持 `readyForNodeV237ManualSandboxConnectionReadinessGate=false` 并追加 `NODE_V237_SOURCE_SANDBOX_CONNECTION_PRECONDITION_RECEIPT_NOT_READY`，v93 operator checklist echo receipt 会保持 `readyForNodeV239ManualSandboxConnectionEvidenceVerification=false` 并追加 `NODE_V239_SOURCE_SANDBOX_CONNECTION_DRY_RUN_ENVELOPE_ECHO_RECEIPT_NOT_READY`。传入完整 header 后 v91 receipt 可为 Node v235 intake 提供只读前置条件证据，v92 receipt 可只读回显 Node v236 envelope 的六个字段名，v93 receipt 可只读回显 Node v238 checklist 字段和 count/id/code；但仍不是真实连接许可，Java 不执行连接、credential value 读取、SQL、部署、回滚、restore、ledger 写入或服务自启动。
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
  -> v69 增强 release approval rehearsal 只读验证提示，提供 response schema version、warning digest 和 no-ledger-write proof，供 Node 导入窗口结果前校验
  -> v70 增强 release approval rehearsal 只读 operator-window hint，回显 Node v198 窗口身份与 approval correlation 头，但不认证、不持久化、不授权生产身份
  -> v71 增强 release approval rehearsal 只读 CI evidence hint，回显 Node v200 manifest 摘要，不上传 GitHub artifact，不写 ledger
  -> v72 增强 release approval rehearsal 只读 artifact retention hint，回显 Node v202 dry-run upload contract 与 Java retention fixture
  -> v73 增强 release approval rehearsal 只读 live-readiness hint，回显 Node v204/v205 runtime smoke 上下文，不启动或清理 Node 进程
  -> v74 增强 release approval rehearsal 只读 audit-persistence handoff hint，列出未来可进入 Node managed audit 的字段，不写 Java ledger 或审计存储
  -> v75 增强 release approval rehearsal 只读 approval-record handoff hint，标注可进入 Node audit record 的审批字段，不创建或持久化 Java approval record
  -> v76 增强 release approval rehearsal 只读 approval-handoff verification marker，标注 Node v211 已消费 Java v75 handoff 的 dry-run packet 覆盖和 no-write 边界
  -> v77 增强 release approval rehearsal 只读 managed-audit adapter boundary receipt，标注 Node v215 只能写本地 dry-run 文件，不能连接真实审计、写审批 ledger、执行 SQL、部署、回滚或 restore
  -> v78 增强 release approval rehearsal 只读 managed-audit production adapter prerequisite receipt，承接 Node v216 archive verification，给 Node v217 production-hardening readiness gate 标注前置条件和 no-production-operation 边界
  -> v79 增强 release approval rehearsal 只读 OpsEvidenceService quality split receipt，承接 Node v218 质量收口，给 Node v219 implementation precheck 标注 receipt/digest/hint/render/record 职责拆分边界
  -> v80 增强 release approval rehearsal 只读 managed-audit adapter implementation guard receipt，承接 Node v220 disabled shell，给 Node v221 local adapter candidate dry-run 提供 guard digest 和 no-write 边界
  -> v81 增强 release approval rehearsal 只读 managed-audit external adapter migration guard receipt，承接 Node v222 verification report，给 Node v223 connection readiness review 标注 owner approval、schema migration review、credential review 和 no-credential/no-connection/no-SQL 边界
  -> v82 增强 release approval rehearsal 只读 managed-audit sandbox adapter approval/schema guard receipt，承接 Node v224 sandbox plan，给 Node v225 dry-run package 标注 owner approval artifact、schema rehearsal checklist、sandbox credential handle 和 helper split 质量门禁

  -> v83 contract-preserving refactor: extract release approval verification hint builder from OpsEvidenceService without changing response fields, digest order, proof claims, or read-only boundaries
  -> v84 contract-preserving refactor: extract managed-audit receipt builders, verification warning digest builder, and shared digest support; OpsEvidenceService now only wires the receipt chain
  -> v85 contract-preserving refactor: extract release approval rehearsal response, hint, handoff hint, and failure taxonomy builders; OpsEvidenceService now keeps the public overloads and evidence entry point at 1443 lines
  -> v86 contract-preserving refactor: wrap the remaining positional booleans inside release approval rehearsal builder internals with semantic record/flags helpers; OpsEvidenceService remains at 1443 lines
  -> v87 adds release approval rehearsal managed-audit sandbox connection operator handoff marker for Node v228/v229 while keeping Java no-connection, no-credential-value, no-SQL, and no-ledger boundaries
  -> v88 adds release approval rehearsal managed-audit sandbox connection preflight echo marker for Node v230/v231 while keeping manual window closed by default, no auto-start, no credential-value read, no connection, no SQL, and no ledger boundaries
  -> v89 contract-preserving refactor: add ContextHeaderField record to group header value/source/echoed triples in release approval rehearsal hint builders; response fields and read-only boundaries are unchanged
  -> v90 contract-preserving refactor: centralize release approval context normalization and missing-warning attachment in ContextHeaderField; response fields and read-only boundaries are unchanged
  -> v91 adds release approval rehearsal managed-audit sandbox connection precondition receipt for Node v235 intake while keeping no connection, no credential-value read, no SQL, no ledger, and no auto-start boundaries
  -> v92 adds release approval rehearsal managed-audit sandbox connection dry-run envelope echo receipt for Node v236/v237 while echoing only field names and keeping credential values, connection, SQL, ledger, and auto-start blocked
  -> v93 adds release approval rehearsal managed-audit sandbox connection operator window checklist echo receipt for Node v238/v239 while echoing checklist fields/counts/ids and keeping connection, credential values, SQL, ledger, and auto-start blocked
  -> v94 contract-preserving refactor: move OpsEvidenceService release/static evidence builders into a dispatch table, keeping response contracts unchanged while reducing OpsEvidenceService to 1032 lines
  -> v95 contract-preserving refactor: move static release version/endpoint strings into OpsEvidenceStaticReleaseArtifact enum, keeping response contracts unchanged while reducing OpsEvidenceService to 966 lines
  -> v96 contract-preserving refactor: replace release approval rehearsal null overload chain with ReleaseApprovalRehearsalRequest records, keeping HTTP headers and response contracts unchanged while reducing OpsEvidenceService to 606 lines
  -> v97 contract-preserving refactor: split release approval rehearsal builder into normalized request, rehearsal sections, and managed-audit receipt chain contexts while keeping digest and response contracts unchanged
  -> v98 adds release approval rehearsal managed-audit sandbox dry-run command package echo receipt for Node v241/v244 while echoing commandCount, credential handle, schema rehearsal, rollback path, timeout, and abort marker only; Java still blocks credential values, managed audit connections, SQL, ledger writes, auto-start, and mini-kv writes
  -> v99 adds release approval rehearsal managed-audit sandbox connection precheck packet echo receipt for Node v245/v246 while echoing owner approval artifact, credential handle review, schema migration rehearsal, operator window, rollback path, abort marker, and timeout policy only; Java still blocks credential values, managed audit connections, SQL, ledger writes, auto-start, and mini-kv writes
  -> v100 adds GitHub Actions Maven CI bootstrap and documents large-file split guards for ReleaseApprovalRehearsalResponse/OpsEvidenceService without changing business semantics or managed-audit boundaries
  -> v101 adds Dependabot security maintenance for Maven and GitHub Actions while leaving dependency versions, business semantics, and managed-audit boundaries unchanged
  -> v102 adds release approval rehearsal disabled adapter client precheck echo receipt for Node v252/v254 while keeping credential values, real client instantiation, external requests, managed-audit connections, SQL, ledger writes, auto-start, and mini-kv writes blocked
  -> v103 adds release approval rehearsal fake transport dry-run packet echo marker for Node v255/v257 while keeping credential values, raw endpoints, external requests, managed-audit connections, SQL, ledger writes, temp files, auto-start, and mini-kv writes blocked
  -> v104 adds release approval rehearsal sandbox endpoint handle preflight echo marker for Node v258/v259 while keeping credential values, raw endpoints, external requests, managed-audit connections, SQL, ledger writes, auto-start, and mini-kv managed-audit storage blocked
  -> v105 adds release approval rehearsal sandbox endpoint credential resolver decision echo marker for Node v260/v261 while echoing only handles, policy markers, no-go conditions, and read-only boundaries; Java still blocks resolver execution, credential values, raw endpoints, external requests, managed-audit connections, SQL, ledger writes, auto-start, and mini-kv managed-audit storage
  -> v106 adds release approval rehearsal sandbox endpoint credential resolver disabled precheck echo marker for Node v262/v263 while echoing env handles, opt-in gates, failure classes, dry-run response shape, inherited no-go conditions, and side-effect boundaries; Java still blocks resolver implementation, secret providers, credential values, raw endpoints, external requests, managed-audit connections, SQL, ledger writes, auto-start, and mini-kv managed-audit storage
  -> v107 adds release approval rehearsal sandbox endpoint credential resolver test-only shell echo marker for Node v264/v265 while echoing fake-only request/response/failure mapping/guard/probe and no-side-effect boundaries; Java still blocks real resolver implementation, secret providers, credential values, raw endpoints, external requests, managed-audit connections, SQL, ledger writes, auto-start, and mini-kv managed-audit storage
  -> v108 adds echo marker support optimization for v104-v107 so warning inputs / warning lines / conditional warning collection share a small helper; no contract, schema, or managed-audit boundary changes
  -> v109 splits ReleaseApprovalRehearsalResponse into a thin response shell plus ReleaseApprovalRehearsalResponseRecords so the nested record catalog stops living inside the top-level response type; no business contract or boundary changes
  -> v110 adds release approval rehearsal sandbox endpoint credential resolver fake-shell archive echo receipt for Node v266/v267 while keeping credential values, raw endpoints, external requests, managed-audit connections, SQL, ledger writes, schema migrations, and auto-start blocked
  -> v111 adds release approval rehearsal sandbox endpoint credential resolver production-readiness blocked-decision echo receipt for Node v268/v269 while keeping real resolver implementation, credential values, raw endpoints, external requests, managed-audit connections, SQL, ledger writes, schema migrations, and auto-start blocked
  -> v112 adds release approval rehearsal sandbox endpoint credential resolver pre-implementation plan intake echo receipt for Node v270/v272 while echoing 10 defined-for-review boundaries and keeping credential values, raw endpoints, external requests, managed-audit connections, SQL, ledger writes, schema migrations, and auto-start blocked
  -> v113 adds release approval rehearsal sandbox endpoint credential resolver disabled implementation candidate echo receipt for Node v273/v274 while using echo workflow template support and keeping credential values, raw endpoints, real resolver execution, managed-audit connections, SQL, ledger writes, schema migrations, and auto-start blocked
  -> v114 refactors release approval verification hint catalogs out of ReleaseApprovalVerificationHintBuilder, reducing the builder from 903 to 648 lines while keeping response contracts, digest order, proof claims, and managed-audit boundaries unchanged
  -> v115 refines credential resolver approval-required boundary echo evidence with six read-only explanations, moves decision echo records out of ReleaseApprovalRehearsalResponseRecords, and keeps credential values, raw endpoints, resolver execution, managed-audit connections, SQL, ledger writes, rollback, and auto-start blocked

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
