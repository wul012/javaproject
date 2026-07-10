# Java v1835：把失败事件巨型服务的查询职责拆成可审查流水线

## 实际工作量说明

v1835 是本轮深度维护的第一刀运行时代码重构。目标不是增加一个失败事件查询功能，而是把已经存在、已经上线、已经被控制器和集成测试依赖的查询能力，从 1126 行的 `FailedEventMessageService` 中完整剥离。原服务同时承担死信消息落库、失败事件搜索、四类历史查询、CSV 导出、管理状态变更、重放审批、RabbitMQ 重放、摘要与字段规范化，阅读任何一段代码都要同时背负其他职责。这样的类即使测试很多，修改风险仍然高，因为一个导入、一个仓储字段或一个私有帮助方法都可能跨越多个流程。

本版本先使用 CodeGraph 查看 `FailedEventMessageService` 的调用者和影响范围。控制器、监听器以及大量 Spring 集成测试都依赖这个 public 服务，而外部没有直接构造它的代码。因此最稳妥的结构是保留它作为公共事务门面，保留全部 public 方法签名和 `@Transactional` 注解，只把内部只读查询委托给包内协作者。这样 Spring AOP 仍在原服务入口开启只读事务，控制器无需改注入类型，测试也继续走真实门面，而不是绕过业务边界去测试新类。

第一项代码工作是新增 `FailedEventQueryService`。它集中承接失败消息搜索与导出、管理历史列表与搜索导出、重放审批历史列表与搜索导出、重放尝试列表与搜索。四个仓储和 `FailedEventReplayProperties` 由它持有，排序白名单也在这里与相应查询相邻。这个类是包可见的 Spring `@Component`，不会形成新的跨包 API。它最终为 310 行，低于五百行警戒线。

第二项工作是新增 `FailedEventSearchSpecifications`。旧服务里四段 JPA Specification 混在消息发布和审批代码之间，现在它们集中表达实体字段到 predicate 的映射。失败消息条件包括状态、管理状态、审批状态、事件类型、聚合类型、聚合标识和失败时间；重放尝试条件包括失败消息 id、结果状态、操作员、角色和尝试时间；两类历史条件分别保留状态迁移或审批动作。重复的可空精确相等判断被提炼成泛型 `addEquals`，重复的时间区间被收束为 `addChangedAtRange`。该类最终 159 行。

第三项工作是新增 `FailedEventSearchPageSupport`。它独立负责 page、size、兼容 limit、导出 limit、排序白名单、方向解析、稳定并列排序、正数 id 和时间区间校验。三种数字输入统一走 `normalizeBounded`，避免原服务中三个相似方法各自维护默认值和边界。默认搜索页仍是零，默认大小仍是五十，搜索最大值仍是二百；导出默认一千、最大五千。该类最终 103 行。

第四项工作是为拆分本身增加结构门和直接单测。`FailedEventQueryArchitectureTests` 读取真实源码，证明公共门面仍委托四组查询，同时不再出现 `Specification`、排序白名单或分页规范化实现；它也证明三个协作者保持包可见。`FailedEventSearchPageSupportTests` 直接验证默认分页、显式分页、排序方向、非 id 排序追加 `id desc`、导出上下限以及原异常状态和文本。既有四组 Spring/H2/JPA 集成测试继续证明查询结果、CSV 和校验行为。

第五项工作是收紧 v1834 建立的维护预算。正式逐行口径下，原服务从 1126 行下降到 662 行，净减少 464 行；三个新类没有一个超过五百行。生产源码超过七百五十行的文件数从五降到四，超过一千行的文件数从三降到二。`JavaMaintainabilityBudgetTests` 的聚合上限和单文件上限同时降低，后续代码无法把查询逻辑重新塞回门面。

## 入口路由

本版本没有新增或改动控制器路由。失败事件页面和 API 仍通过 `FailedEventMessageController` 进入，主要只读入口包括失败消息搜索与导出、重放尝试搜索、管理历史搜索与导出、审批历史搜索与导出，以及按失败消息 id 查询三类历史。调用者看到的仍是 `FailedEventMessageService`，没有任何控制器改为依赖内部查询组件。

请求进入控制器后，条件对象的构造方式不变。例如失败消息条件仍由状态、事件类型、聚合类型、聚合标识、管理状态、审批状态、失败时间区间、page、size、sort 和 limit 组成；重放尝试条件仍包含失败消息 id、结果状态、操作员、角色和尝试时间。控制器把条件交给原 public 服务，原服务在只读事务内委托 `FailedEventQueryService`，再由它调用仓储。

这种“外部入口不动、内部拥有者变化”的做法避免了路由迁移的额外风险。请求映射注解、路径参数、查询参数名字、CSV content type、Content-Disposition 文件名都没有变化。客户端无法从 HTTP 层观察到本次拆分，维护者却能从源码结构中明确看到查询边界。

## 响应模型

分页查询仍返回项目公共的 `PagedResponse<T>`。内容列表继续由实体的 `from` 映射方法生成；page、size、totalElements、totalPages 和对外 sort 表达式都由原有逻辑产生。内部为了稳定分页而追加的 `id desc` 仍不写入对外 sort 文本，因此客户端看到的仍是例如 `eventType,asc`，而数据库排序实际为 `eventType asc, id desc`。

列表接口仍返回不可变 `toList()` 结果。管理历史映射为 `FailedEventManagementHistoryResponse`，审批历史映射为 `FailedEventReplayApprovalHistoryResponse`，重放尝试映射为 `FailedEventReplayAttemptResponse`。字段、顺序、时间值和枚举值没有改变。失败消息仍使用 `FailedEventMessageResponse.from`，不会因查询组件更名而改变 JSON schema。

CSV 导出继续调用原来的 `FailedEventCsvExporter.failedMessages`、`managementHistory` 和 `replayApprovalHistory`。新查询组件只负责取出同样的 response 列表，没有复制 CSV 格式化逻辑。列标题、列顺序、逗号和引号转义、空值表达以及换行字节因此保持不变。这个边界很关键：若重构同时重写 CSV，就难以判断差异来自结构还是功能。

## 上游证据配置

查询行为的上游输入是四个 criteria record 和 `FailedEventReplayProperties`。本版本没有修改这些 record，也没有改变它们的兼容构造器。旧调用只传七个参数时，page、size 与 sort 仍为空，limit 仍兼容地作为搜索大小使用。角色输入仍由 `FailedEventReplayProperties.normalize` 统一去空白并规范为系统使用的形式，Specification 没有另造一套大小写规则。

排序白名单按实体分别保留。失败消息允许 id、failedAt、status、eventType、aggregateId、replayCount、managementStatus、managedAt、replayApprovalStatus、replayApprovalRequestedAt 和 replayApprovalReviewedAt；重放尝试允许 id、attemptedAt、status、operatorId 和 operatorRole；管理历史与审批历史分别保留原字段集合。未知字段仍返回 400，并包含原来的 `sort field is not allowed` 文本。

维护证据的上游则是 `scripts/java-maintainability-census.ps1 -Json`。它使用 StreamReader 逐行计数，和 JUnit 的 `Files.lines().count()` 都包含空白行。开发过程中曾临时使用 `Measure-Object -Line` 检查新类，该命令忽略空白行，先后造成两个错误的预设 cap。因为同一错误出现两次，本版本将正确口径升格写入 `AGENTS.md`：以后 ratchet 只能使用正式脚本或显式 ReadLine 循环。

## 服务层核心流程

失败消息搜索的流程是：门面进入只读事务，查询编排器把空 criteria 替换为原默认对象，校验 failedFrom 与 failedTo，分页支持生成 `NormalizedPageRequest`，Specification 根据非空字段建立 predicate，仓储通过 `JpaSpecificationExecutor.findAll` 执行，最后 `PagedResponse.from` 映射实体并保留对外排序文本。每一层只回答一个问题。

管理历史和审批历史的搜索流程相似，但多了一条正数 id 校验。若 criteria 中包含 failedEventMessageId 且小于一，仍返回 400；搜索本身不要求该 id 一定存在，因为零结果是合法搜索结果。按 id 的列表接口则先确认对应失败消息存在，不存在时仍返回 404。这个细微差异在拆分时完整保留，没有为了代码统一而改变语义。

重放尝试搜索保留一个历史边界：按 id 列表只做仓储存在性检查，不在此前额外调用正数校验；搜索条件中的 id 也直接进入 Specification。虽然从新设计角度可以统一校验，但那会改变对 null 或非法 id 的异常路径，不属于行为保持型重构。本版只移动现有逻辑，把是否优化该契约留给明确的功能版本。

分页支持先确定 page 与 size。size 非空时优先于兼容 limit；两者都空时使用五十。排序表达式去除首尾空白后按逗号拆分，只允许一段字段或字段加方向。方向缺失时默认 desc；方向非法时保留原 400 文本。字段映射到实体属性后生成 Spring `Sort`，如果属性不是 id，再追加 id desc，避免多条相同业务字段记录在翻页时漂移。

Specification 层只构造查询条件，不做分页、不访问仓储、不映射响应。`addTextEquals` 仍先判断有文本再 strip，并使用精确 equal；它没有偷偷改成模糊查询或忽略大小写。时间条件仍使用 greaterThanOrEqualTo 和 lessThanOrEqualTo，保持闭区间。抽出的 `addEquals` 只减少可空枚举与 id 判断重复，不改变 predicate 顺序对结果的意义。

## Java 证据检查

首先执行编译与测试编译，证明新的包内组件能被 Spring 扫描，构造器依赖能够解析，原 public 门面没有丢失类型。随后聚焦运行四组现有集成测试：失败消息与重放尝试搜索、输入校验、管理历史与 CSV、审批历史与重放前置审批。这些测试使用真实 Spring 上下文、Flyway 十二个迁移、H2 数据库和 JPA 仓储，不是只验证字符串的轻量替身。

新增的分页支持测试直接检查默认 page 为零、默认 size 为五十、显式 size 优先于 limit、导出默认一千和最大五千。测试读取 `Sort.Order`，证明业务字段方向与 id desc 并列键都存在；同时断言对外 sort 表达式仍只包含请求字段。非法 page、size、export limit、排序字段和反向时间区间都断言 400 状态及完整 reason。

结构测试则防止未来“重构回潮”。它要求 `FailedEventMessageService` 中存在对查询组件的四组委托，且不存在 `Specification<`、排序白名单、`normalizePageRequest` 或 `normalizeExportPageRequest`。三个协作者源码必须存在预期职责，并且不能声明为 public。维护预算再从行数层阻止门面或协作者膨胀。

最终聚焦结果为 17 个测试全部通过，Spotless 对七个相关 Java 文件检查通过。最终全量 `mvnw verify` 会在本讲解完成后执行，覆盖本项目全部订单、库存、支付、Outbox、失败事件、运维证据、JaCoCo 和 SpotBugs 门。只有完整结果和远端 Actions 都绿，本版本才进入 tag。

## mini-kv 证据检查

v1835 没有改动 mini-kv，也没有改变 Java 对 mini-kv 证据、fixture、摘要或历史归档的消费方式。失败事件模块属于 Java 自身的通知与补偿域，不读取 mini-kv shard map，不请求 mini-kv 运行时，也不写任何上游仓库文件。因此本版可以独立推进，不需要跨项目 schema 协调。

这并不意味着忽略系统边界。项目的 ReleaseApproval 与 shard readiness 代码仍受冻结证据规则保护，本版没有触碰那些文件。新维护性预算只统计 Java 源码，不递归扫描 `D:\C\mini-kv`。后续 v1837 拆证据构建链时，仍必须保持 mini-kv 路径和摘要输入顺序；本版的查询拆分不预支那部分风险。

## 阻断与安全边界

本版没有新增写操作。失败消息搜索、历史搜索和 CSV 仍在 `@Transactional(readOnly = true)` 门面内执行。管理状态变更、审批、重放和消息持久化仍留在原服务，事务注解和调用路径没有移动。RabbitTemplate、Outbox 配置、消息头、交换机和 routing key 都没有变化。

数据库层没有新增 migration、索引或查询语句。Specification 仍使用同样字段名；如果字段拼写错误，现有集成测试会在真实 JPA 执行时失败。没有引入原生 SQL，也没有放宽搜索白名单。凭据、外部托管审计、部署、回滚和进程启动边界完全不在此版本作用域。

代码可见性也是安全边界。三个新类都是 package-private，方法也不公开到 notification 包之外。控制器不能绕过事务门面直接调用它们，其他模块也不能逐渐形成新耦合。Spring 只负责实例化查询编排组件，Specification 与分页支持是纯包内工具。

预算失败处理同样明确。第一次和第二次聚焦失败来自错误的临时行数口径，而不是业务测试；没有通过提高 v1834 历史上限掩盖问题。修正后正式上限为门面 662、查询编排 310、Specification 159、分页支持 103，均使用包含空白行的统一口径。禁止硬凑更小数字，也禁止拿少算空白行制造虚假成绩。

## 测试覆盖

行为覆盖分三层。第一层是 `FailedEventSearchPageSupportTests`，验证纯输入规范化和排序对象；第二层是 `FailedEventQueryArchitectureTests`，验证职责不回流和包可见边界；第三层是四组 Spring 集成测试，验证实际查询、分页、CSV、404/400 和角色规范化。`JavaMaintainabilityBudgetTests` 作为第四条横向门，验证拆分有真实结构收益。

聚焦测试数量不大，但每类对应明确风险：默认分页漂移会破坏页面；稳定并列键丢失会导致翻页重复或遗漏；CSV 查询上限改变会影响导出；角色未规范化会查不到历史；正数 id 校验位置改变会改变错误语义；协作者 public 化会扩大 API；门面重新增长会让重构失效。测试不是为了提高数字，而是把这些失败条件机械化。

本讲解在最终 verify 前完成，中文字符需超过三千并以中文为主，包含规定的十个主题。内容全部来自本项目真实类、方法、默认值、异常和测试，不用空泛口号补篇幅。若后续实现发生变化，应先更新代码和讲解，再重新跑 verify，不能在验证后偷偷补充关键设计。

## 一句话总结

v1835 在不改变任何外部行为的前提下，把 1126 行失败事件巨型服务削减为 662 行事务门面，并将查询编排、JPA 条件和分页校验拆成 310、159、103 行的包内组件；同时用行为测试、结构门和只收紧预算证明这不是搬文件，而是让查询修改终于不必穿过审批、重放与 RabbitMQ 逻辑。

本项目向完美靠近的方式不是宣称“已重构”，而是让职责边界、输入输出、失败条件和收益都可复核。v1835 完成了查询半边，v1836 将继续拆管理、审批、重放与消息持久化，使公共门面真正降到五百行以下；禁止硬凑版本，也不把一次编译通过当作完成。
