# Java v1836 失败事件命令职责拆分

## 目标与非目标

本版暂停新增功能，只处理 `FailedEventMessageService` 在 v1835 查询拆分后仍然承担过多命令职责的问题。目标是让公共服务回到稳定的事务门面：它继续承接控制器、死信监听器和现有集成测试的全部公开调用，继续负责操作员身份解析与事务注解，但不再直接处理 Repository、RabbitMQ 发布、摘要生成、唯一键并发冲突、管理历史、审批历史或重放尝试持久化。

本版不新增 HTTP 路由，不修改请求和响应模型，不修改 Flyway，不改变 RabbitMQ exchange、routing key、消息体或消息头，不改变异常状态码和异常文本，不改变审批请求人与复核人必须不同的安全要求，也不改变已重放事件再次执行时写入 `SKIPPED_ALREADY_REPLAYED` 尝试记录的行为。

## 需求—实现—证据矩阵

| 需求 | 实现 | 机械证据 | 状态 |
| --- | --- | --- | --- |
| 公共入口与事务边界不变 | `FailedEventMessageService` 保留全部 public 方法与原 `@Transactional` / `readOnly` 注解 | 现有控制器和监听器零改动；Spring 集成测试启动成功 | 聚焦门通过 |
| 死信记录独立 | `FailedEventRecorder` 负责 payload、message id、header、SHA-256、唯一键并发兜底 | RabbitMQ 死信集成测试与结构门 | 聚焦门通过 |
| 管理状态独立 | `FailedEventManagementService` 负责批量 id、状态、备注、历史记录 | 管理页面和搜索集成测试 | 聚焦门通过 |
| 双人审批独立 | `FailedEventReplayApprovalService` 负责请求、复核、拒绝备注和请求人隔离 | 审批状态集成测试 | 聚焦门通过 |
| 重放执行独立 | `FailedEventReplayService` 负责有效事件解析、RabbitMQ 发布、成功/失败/跳过尝试 | 重放执行契约与 Docker RabbitMQ 回归 | 聚焦门通过；Docker 由全量 CI 复现 |
| 参数错位风险降低 | 五个有效事件字段封装为 `EffectiveReplayEvent` | 编译器固定字段名，结构门固定消息头顺序 | 通过 |
| 门面不得重新直连基础设施 | `FailedEventCommandArchitectureTests` 禁止 Repository、RabbitTemplate、Outbox 和摘要实现回流 | 源码结构测试会在回流时失败 | 通过 |
| 维护预算只收紧 | 门面 662→199；新类 71/89/129/222/33；生产 >500 文件 39→38 | 普查脚本和 `JavaMaintainabilityBudgetTests` | 通过 |

## 新职责地图

`FailedEventMessageService` 是唯一公共类型。字符串形式的操作员 id/role 仍在这个门面调用 `FailedEventOperatorContextResolver`，因此角色白名单、大小写规范化、缺失身份处理和动作授权没有下沉或绕过。解析得到的 `FailedEventOperatorContext` 随后交给包内命令组件。门面上的事务代理在进入协作者之前已经开启，JPA 实体变更、历史记录和重放尝试仍位于同一个事务中。

`FailedEventRecorder` 只处理来自死信队列的 AMQP `Message`。它先按 UTF-8 解码 payload，再按原顺序选择 message id：消息属性 id、`eventId` header、最后才是 payload 与 headers 的 SHA-256。并发插入触发唯一键异常时仍回读既有记录，保证同一死信不会产生重复实体。

`FailedEventManagementService` 只处理人工管理状态。它保留请求体必填、id 去重、1 至 100 条限制、正数校验、状态必填、备注必填与 500 字符截断；批量查询数量不一致仍返回 404。每个实体先记录旧状态，再更新状态并写一条管理历史。

`FailedEventReplayApprovalService` 只处理审批状态机。请求审批时继续禁止已重放、已有待审和已经批准的事件；复核时只接受 `APPROVED` 或 `REJECTED`，拒绝必须给出备注，请求人不能复核自己的请求。审批实体变更与审批历史在同一门面事务内完成。

`FailedEventReplayService` 只处理执行。它先验证已批准与 Outbox 开启，再解析 event id、event type、aggregate type、aggregate id 和 payload。解析后的五项值被放入不可变 `EffectiveReplayEvent`，后续发布、实体状态更新和尝试审计都读取同一对象，消除了长参数列表中字段错位的可能。消息头写入顺序保持 eventId、aggregateType、aggregateId、eventType、来源失败事件 id、来源 message id；失败仍截断错误文本到 500 字符并写 `FAILED` 尝试。

`FailedEventCommandSupport` 只有三个纯粹且跨命令重复的规则：操作员上下文必填、选择第一个非空字符串、按最大长度截断。领域状态判断没有塞进这个类，防止它演化成无法维护的通用工具箱。

## 预算变化

正式数字由 `scripts/java-maintainability-census.ps1 -Json` 产生。生产 Java 文件从 1478 增加到 1483，这是用五个窄职责类替代单个巨型类的有意结构变化；最大文件仍为 1530 行。超过 500 行的文件数从 39 降到 38，超过 750 行和 1000 行的文件数继续为 4 和 2。测试 Java 文件从 830 增加到 832，测试热点预算没有上升。

所有新生产类均加入单文件上限：门面 199、记录器 71、管理器 89、审批器 129、重放器 222、命令支持 33。后续任何类增长一行都必须先解释并收紧设计，不能靠提高上限让测试变绿。

## 验证策略

第一层是直接单测：共享命令支持验证空值、空白、原值保留、截断边界和 400 异常文本。第二层是结构门：验证门面包含五类委托、禁止五种基础设施实现泄漏、协作者保持包内 `@Component`、重放消息头顺序与三种尝试状态仍显式存在。第三层是真实 Spring 集成测试：覆盖管理页面、审批状态与重放执行契约，证明依赖注入、Flyway、JPA 和事务代理可正常工作。第四层是完整 Maven verify、JaCoCo、SpotBugs、Spotless 与远端 GitHub Actions，最终结果在版本封存时写回。

## 失败条件

- 任何公开方法签名、事务注解、路由、DTO、数据库迁移或 RabbitMQ 契约变化，版本失败。
- 为通过拆分而修改现有 fixture 字节、放宽角色或审批规则、改变异常文本，版本失败。
- 门面仍直接依赖 Repository 或 RabbitTemplate，或者新协作者成为公共 API，版本失败。
- 新类只是复制旧逻辑而原门面未实质缩小，版本失败。
- 生产 >500、>750、>1000 聚合预算或任一命名单文件上限回升，版本失败。
- 中文讲解晚于最终 verify、完整 verify、远端 CI、tag、push 或账本任一缺失，版本不算完成。

## 当前证据

聚焦 Spring、结构与共享支持回归已经通过；维护预算、讲解质量门、Spotless 与 SpotBugs 也已通过。最终全量 `mvnw verify` 用时 9 分 35 秒：1689 个测试，0 失败、0 错误、0 跳过；JaCoCo 全部覆盖率门达标；SpotBugs `BugInstance=0`、`Error=0`；BUILD SUCCESS。提交、tag、push 和远端 CI 结果仍需在本版收口时补入，不提前自报完成。
