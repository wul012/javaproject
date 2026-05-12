# advanced-order-platform 代码讲解记录

本目录用于永久记录 `advanced-order-platform` 项目的分版本代码讲解。

讲解风格参照 `D:\C\mini-kv\代码讲解记录`：

```text
先说明文件或类的角色
再给核心流程
然后多代码引用解释关键实现
最后做一句话总结
```

## 讲解目录

```text
01-project-entry-config.md
 -> 项目入口、Maven 依赖、配置文件和演示数据初始化

02-catalog-inventory.md
 -> 商品目录、库存模型、库存锁、库存预占/扣减/释放/回补和库存流水

03-order-domain-api.md
 -> 订单领域模型、请求响应对象、Controller 和下单 API

04-order-application-flow.md
 -> OrderApplicationService 的幂等下单、商品校验、库存预占、支付、退款、履约和状态流转

05-outbox-exception-tests.md
 -> Outbox 事件表、统一异常、测试用例、运行方式和后续升级路线

06-version-2-cancel-flow.md
 -> 第二版订单取消、释放预占库存、取消事件和测试补强

07-version-3-expiration-flow.md
 -> 第三版超时未支付订单自动过期取消、调度器、配置和测试补强

08-version-4-outbox-publisher.md
 -> 第四版 Outbox 后台发布器、publishedAt 标记、调度器和测试补强

09-version-5-fulfillment-flow.md
 -> 第五版订单发货、完成履约、状态机扩展、事件和测试补强

10-version-6-status-history.md
 -> 第六版订单状态历史、操作流水、历史查询接口和测试补强

11-version-7-payment-transactions.md
 -> 第七版支付交易流水、模拟支付成功记录、支付查询接口和测试补强

12-version-8-refund-flow.md
 -> 第八版支付后退款、库存回补、退款流水、退款事件和测试补强

13-version-9-inventory-movements.md
 -> 第九版库存变更流水、库存审计查询接口和测试补强

14-version-10-postgres-flyway-testcontainers.md
 -> 第十版 PostgreSQL 配置、Flyway 迁移脚本、Hibernate validate、Testcontainers 集成测试和 Docker 配合点

15-version-11-rabbitmq-outbox-publisher.md
 -> 第十一版 RabbitMQ Outbox 真实消息发布、exchange/queue/binding、消息头元数据和 Testcontainers 集成验证

16-version-12-rabbitmq-notification-consumer.md
 -> 第十二版 RabbitMQ 通知消费者、通知消息幂等落库、通知查询接口、Flyway V2 和消费者集成测试

17-version-13-rabbitmq-retry-dlq-failed-events.md
 -> 第十三版 RabbitMQ 消费失败重试、死信队列、失败事件消息表、失败查询接口和 Testcontainers 失败链路验证

18-version-14-failed-event-replay.md
 -> 第十四版失败事件消息状态字段、修复重放接口、RabbitMQ 重新投递和重放链路集成测试

19-version-15-replay-audit.md
 -> 第十五版失败事件重放审计表、操作者记录、重放尝试查询接口和审计链路验证

20-version-16-replay-authorization.md
 -> 第十六版失败事件重放角色校验、重放原因强制记录、审计字段扩展和权限链路验证

21-version-17-failed-event-search.md
 -> 第十七版失败事件和重放审计的多条件筛选、Specification 动态查询、查询索引和接口验证

22-version-18-failed-event-pagination.md
 -> 第十八版失败事件查询分页响应、page/size/sort 参数、排序字段白名单和分页元数据验证

23-version-19-failed-event-management-status.md
 -> 第十九版失败事件管理状态、批量标记接口、管理状态筛选、V8 迁移和权限校验

24-version-20-failed-event-management-history.md
 -> 第二十版失败事件管理状态变更流水、历史查询接口、V9 迁移和审计查询验证

25-version-21-failed-event-csv-export.md
 -> 第二十一版失败事件和管理状态流水 CSV 导出、导出上限、CSV 转义和接口验证

26-version-22-failed-event-management-page.md
 -> 第二十二版失败事件管理静态页面、筛选表格、批量标记、流水侧栏和 CSV 下载入口

27-version-23-failed-event-replay-workbench.md
 -> 第二十三版失败事件管理页面重放工作台、单条重放、覆盖字段、审计侧栏和浏览器冒烟验证

28-version-24-failed-event-replay-confirmation.md
 -> 第二十四版失败事件重放二次确认、Payload 覆盖风险提示和确认弹窗验证
29-version-25-failed-event-replay-approval.md
 -> 第二十五版失败事件重放审批状态、申请审批、审批通过/拒绝、服务层门禁和页面审批入口
30-version-26-actuator-rabbit-health.md
 -> 第二十六版 Actuator RabbitMQ 健康检查与 profile 对齐，修复默认本地 health 误报 DOWN
31-version-27-failed-event-replay-approval-history.md
 -> 第二十七版失败事件重放审批历史流水、查询导出和审批动作复盘
32-version-28-replay-approval-separation.md
 -> 第二十八版失败事件重放审批职责分离，禁止申请人审批自己的重放申请
33-version-29-operator-context.md
 -> 第二十九版失败事件操作员上下文解析、统一 Header 规范化和页面身份校验
34-version-30-action-role-policy.md
 -> 第三十版失败事件动作级角色策略，按管理、申请、审批、重放拆分允许角色
35-version-31-operator-action-snapshot.md
 -> 第三十一版失败事件操作员动作权限快照，探针返回当前角色可执行和不可执行动作
36-version-32-page-action-permission-precheck.md
 -> 第三十二版失败事件管理页面动作权限预检，校验身份后禁用未授权写按钮
37-version-33-page-action-guard.md
 -> 第三十三版失败事件管理页面写操作本地权限守卫，防止绕过禁用按钮触发未授权写动作
38-version-34-action-decision-summary.md
 -> 第三十四版失败事件操作员动作权限决策明细，后端返回每个动作是否允许并在页面展示
39-version-35-role-policy-validation.md
 -> 第三十五版失败事件动作级角色策略启动期一致性校验，提前发现越界角色和 system-role 不可重放

40-version-36-ops-overview.md
 -> 第三十六版订单平台只读运行概览，给 Node 控制面提供 Java 业务健康信号

41-version-37-failed-event-summary.md
 -> 第三十七版失败事件治理摘要，给 Node 风险观察提供失败事件积压和审批状态信号

42-version-38-replay-readiness.md
 -> 第三十八版失败事件重放 readiness，给 Node 受控操作预演提供单条失败事件的只读资格判断

43-version-39-replay-simulation.md
 -> 第三十九版失败事件重放 simulation，给 Node execution preview 提供只读副作用预演

44-version-40-replay-approval-status.md
 -> 第四十版失败事件重放 approval-status，给 Node approval evidence 提供 Java 侧审批状态只读核对入口

45-version-41-replay-approval-status-digest.md
 -> 第四十一版失败事件重放 approval-status digest，给 Node digest-aware verification 提供稳定上游证据摘要

46-version-42-replay-execution-contract.md
 -> 第四十二版失败事件重放 execution-contract，给 Node execution gate 提供 Java 执行前契约证据
```

## 项目整体理解

`advanced-order-platform` 是一个用 Java 21 / Spring Boot 3.5.9 编写的订单交易平台雏形。

它不是完整商城，也不是一开始就拆成多个微服务，而是一个适合练高级 Java 的模块化单体：

```text
HTTP 请求
 -> Controller 接收参数
 -> ApplicationService 编排业务
 -> Repository 读写数据库
 -> Domain Entity 保存业务状态
 -> OutboxEvent 记录领域事件
 -> Controller 返回 JSON 响应
```

核心业务链路：

```text
创建订单
 -> 幂等校验
 -> 商品校验
 -> 库存预占
 -> 保存订单和订单行
 -> 写订单状态历史
 -> 写 Outbox 事件

支付订单
 -> 确认预占库存
 -> 写支付成功流水
 -> 写订单状态历史
 -> 写 Outbox 事件

退款订单
 -> 回补已扣减库存
 -> 写退款流水
 -> 写库存变更流水
 -> 写订单状态历史
 -> 写 Outbox 事件
```

第十版之后，数据库结构不再靠 Hibernate 自动更新，而是由 Flyway SQL 脚本版本化管理，再由 Hibernate validate 校验实体和表结构是否一致。

## 推荐阅读顺序

```text
README.md
 -> 先建立项目总图

01-project-entry-config.md
 -> 知道项目怎么启动、依赖从哪里来、配置如何生效、初始化数据怎么入库

02-catalog-inventory.md
 -> 理解商品和库存关系，尤其是 available / reserved / inventory_movements

03-order-domain-api.md
 -> 理解订单表、订单行、请求响应对象和 HTTP 接口

04-order-application-flow.md
 -> 重点理解下单、支付、退款、取消、发货、完成的完整业务编排

05-outbox-exception-tests.md
 -> 理解事件、异常、测试和后续可升级点

06-version-2-cancel-flow.md
 -> 理解取消订单和释放 reserved 库存

07-version-3-expiration-flow.md
 -> 理解自动扫描并取消超时未支付订单

08-version-4-outbox-publisher.md
 -> 理解 Outbox 发布器如何扫描并标记 publishedAt

09-version-5-fulfillment-flow.md
 -> 理解订单从 PAID 到 SHIPPED 再到 COMPLETED

10-version-6-status-history.md
 -> 理解订单状态变化如何记录成可查询时间线

11-version-7-payment-transactions.md
 -> 理解支付动作如何沉淀成可查询、幂等的支付交易流水

12-version-8-refund-flow.md
 -> 理解 PAID 订单如何退款，并同步回补库存、记录退款流水和事件

13-version-9-inventory-movements.md
 -> 理解库存 available/reserved 的每次变化如何记录成可查询流水

14-version-10-postgres-flyway-testcontainers.md
 -> 理解建表责任如何从 Hibernate update 迁到 Flyway，并用 PostgreSQL/Testcontainers 验证真实数据库兼容性

15-version-11-rabbitmq-outbox-publisher.md
 -> 理解 Outbox 如何从数据库发布标记升级为 RabbitMQ 真实投递，并保留默认无消息队列运行模式

16-version-12-rabbitmq-notification-consumer.md
 -> 理解 RabbitMQ 消息如何被消费者处理成通知消息，并通过 eventId 唯一键实现幂等消费

17-version-13-rabbitmq-retry-dlq-failed-events.md
 -> 理解 RabbitMQ 消费失败如何重试、进入 DLQ，并沉淀为可查询的失败事件消息

18-version-14-failed-event-replay.md
 -> 理解失败事件如何从“可查询”继续升级到“可修复、可重放、可追踪”

19-version-15-replay-audit.md
 -> 理解失败事件重放如何沉淀成可审计、可追责、可排查的操作记录

20-version-16-replay-authorization.md
 -> 理解失败事件重放如何从“有审计”继续升级为“有权限、有原因、有拒绝路径”

21-version-17-failed-event-search.md
 -> 理解失败事件排查如何从“最近列表”升级为“按状态、事件、聚合、角色、时间窗口筛选”

22-version-18-failed-event-pagination.md
 -> 理解失败事件查询如何从“筛选列表”升级为“可支撑管理端表格的分页响应”

23-version-19-failed-event-management-status.md
 -> 理解失败事件如何从“可查可重放”继续升级为“可分派、可忽略、可关闭”的运维处理闭环

24-version-20-failed-event-management-history.md
 -> 理解失败事件管理状态每次变更如何沉淀成可分页、可筛选、可追溯的操作流水

25-version-21-failed-event-csv-export.md
 -> 理解失败事件管理数据如何从在线查询继续升级为可下载、可交接、可复盘的 CSV 文件

26-version-22-failed-event-management-page.md
 -> 理解如何用 Spring Boot 静态资源把失败事件查询、批量处理、流水查看和 CSV 下载串成一个管理页面

27-version-23-failed-event-replay-workbench.md
 -> 理解管理页面如何把后端重放接口、重放审计和失败事件表格串成一个可操作的修复入口

28-version-24-failed-event-replay-confirmation.md
 -> 理解页面如何把高风险重放动作拆成预览、确认、提交三步

29-version-25-failed-event-replay-approval.md
 -> 理解失败事件重放如何从“确认后提交”升级为“申请、审批、通过后才能重放”

30-version-26-actuator-rabbit-health.md
 -> 理解健康检查如何跟随默认本地模式和 rabbitmq profile 切换

31-version-27-failed-event-replay-approval-history.md
 -> 理解失败事件重放审批如何从“当前状态”升级为“可查询、可导出、可复盘的审批流水”

32-version-28-replay-approval-separation.md
 -> 理解失败事件重放审批如何增加职责分离，禁止申请人审批自己的重放申请

33-version-29-operator-context.md
 -> 理解失败事件写操作如何把 X-Operator-* 请求头收拢成统一操作员上下文，为后续真实登录态替换做准备

34-version-30-action-role-policy.md
 -> 理解失败事件写操作如何在统一操作员上下文之上继续细分动作权限，让管理、申请、审批和重放可以独立授权

35-version-31-operator-action-snapshot.md
 -> 理解身份探针如何从“返回系统策略”继续升级为“返回当前操作员实际可执行动作”

36-version-32-page-action-permission-precheck.md
 -> 理解页面如何消费 allowedActions / deniedActions，把后端动作权限快照转成按钮级预检禁用
37-version-33-page-action-guard.md
 -> 理解页面写操作处理函数如何复用本地权限快照，防止脚本绕过禁用按钮触发未授权动作
38-version-34-action-decision-summary.md
 -> 理解 operator-context 如何从动作列表升级为动作决策明细，并让页面直接展示允许/禁止原因
39-version-35-role-policy-validation.md
 -> 理解失败事件角色配置如何在启动期 fail fast，避免全局允许角色、动作角色和 system-role 互相矛盾

40-version-36-ops-overview.md
 -> 理解订单平台如何通过只读运行概览向 Node 控制面暴露业务健康信号

41-version-37-failed-event-summary.md
 -> 理解失败事件治理如何从明细查询升级为只读摘要，暴露积压、审批状态和最近治理活动

42-version-38-replay-readiness.md
 -> 理解失败事件重放如何从“审批后直接尝试”升级为“先只读解释是否可重放、为什么被阻断、下一步做什么”

43-version-39-replay-simulation.md
 -> 理解失败事件重放如何从 readiness 继续升级为 simulation，提前展示真实 replay 预计副作用

44-version-40-replay-approval-status.md
 -> 理解失败事件重放审批如何从“流水查询”继续升级为“单条审批状态只读核对模型”

45-version-41-replay-approval-status-digest.md
 -> 理解 approval-status 如何增加 evidenceVersion、approvalDigest 和 replayEligibilityDigest，支持上游证据复核

46-version-42-replay-execution-contract.md
 -> 理解 replay-execution-contract 如何只读说明真实 replay 前 Java 会检查哪些状态、审批、digest 和请求条件
```

## 一句话总览

这个项目的核心不是“能创建订单”这么简单，而是用 Spring Boot 把下单幂等、库存一致性、库存流水、支付/退款流水、取消/过期/发货/完成状态流转、状态历史、Outbox 发布标记、RabbitMQ 真实消息投递、RabbitMQ 消费者、通知消息幂等落库、消费失败重试、死信队列、失败事件表、失败事件重放、失败事件重放 readiness、失败事件重放 simulation、失败事件重放 approval-status、失败事件重放 approval-status digest、失败事件重放 execution-contract、重放权限校验、统一操作员上下文、动作级角色策略、动作级角色策略启动期校验、操作员动作权限快照、动作权限决策明细、页面动作权限预检、页面写操作本地权限守卫、重放审计、重放审批门禁、重放审批历史流水、审批职责分离、失败事件分页筛选查询、失败事件治理摘要、失败事件管理状态、管理状态变更流水、失败事件 CSV 导出、失败事件管理页面、失败事件重放工作台、重放二次确认、订单平台只读运行概览、Actuator 健康检查、Flyway 数据库迁移和 Testcontainers 真实中间件验证串成一个可继续升级的后端系统。
