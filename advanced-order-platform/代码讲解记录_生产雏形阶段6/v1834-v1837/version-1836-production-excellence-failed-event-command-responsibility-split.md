# v1836 失败事件命令职责拆分：从巨型服务到可审计事务门面

## 实际工作量说明

这一版没有增加新的业务能力，实际工作却不是改几个类名或移动几段代码。v1835 把查询、分页、排序、JPA 条件和 CSV 导出从 `FailedEventMessageService` 拆出去以后，这个类仍有 662 行，并且同时掌握死信消息解析、数据库去重、人工管理、审批请求、审批复核、重放字段补全、RabbitMQ 发布、失败尝试审计以及大量输入校验。任何一个小改动都可能碰到其他流程，审查者也很难一眼判断事务边界和安全边界是否仍然成立。

v1836 的工作是先建立职责地图，再按事务语义拆成五个包内协作者，最后把原服务压缩成 199 行的公共门面。新增的 `FailedEventRecorder`、`FailedEventManagementService`、`FailedEventReplayApprovalService`、`FailedEventReplayService` 和 `FailedEventCommandSupport` 分别只有 71、89、129、222、33 行。这里不是把一个大文件平均切片，而是让每个类只拥有完成一种领域动作所需的依赖。例如记录器只知道失败事件仓储与 AMQP 消息；审批器只知道失败事件与审批历史；重放器才允许知道 RabbitTemplate 和 Outbox 配置。依赖关系本身就是职责证明。

本版还增加两组测试。共享支持单测锁定空值、空白、截断和操作员上下文异常；结构测试锁定公共门面不能重新出现 Repository、RabbitTemplate、Outbox、摘要算法和唯一键异常，同时检查消息头的写入顺序与三类重放尝试仍然显式存在。维护预算也同步收紧：生产源码超过 500 行的文件数从 39 降为 38，门面及五个协作者都有各自不可回升的行数上限。

这些工作遵守“禁止硬凑”的要求。篇幅来自真实的事务、鉴权、状态机、消息和审计机理，而不是重复结论。本项目暂停新功能的目的，就是把已经有价值的失败恢复能力整理成后来者能读、能改、能机械验证的结构。

## 入口路由

外部入口没有改变。失败事件管理控制器、重放审批控制器、搜索控制器和 RabbitMQ 死信监听器仍然只依赖公共的 `FailedEventMessageService`。这点非常重要，因为如果控制器直接改为依赖多个内部服务，调用方就需要理解命令分层，公开 API 会扩张，事务注解也可能散落到不同入口。v1836 反而把内部协作者全部保持为包可见的 `final @Component`，跨包代码无法把它们当成新的业务 API。

可以把入口理解成医院的统一分诊台。调用方只说明要记录死信、更新管理状态、发起审批、复核审批或执行重放，分诊台先确认操作者身份和动作权限，再把已经规范化的上下文交给对应科室。科室不自行接受外部字符串身份，也不绕过分诊台开新入口。因此已有 HTTP 路由、请求头解析、控制器异常映射和监听器调用方式完全不变。

门面还保留原来的每一个 `@Transactional`。只读搜索仍是 `readOnly = true`；记录、管理、审批和重放仍是普通事务。Spring 代理在调用公共门面方法时开启事务，随后包内组件使用相同线程和连接参与事务。组件本身不再重复声明事务，避免出现外层提交、内层新开事务或部分审计记录提前落库的隐性变化。也就是说，拆的是源码职责，不是运行时原子性。

字符串形式的 `operatorId` 和 `operatorRole` 仍然在门面调用 `FailedEventOperatorContextResolver`。不同动作分别使用 `MANAGE_FAILED_EVENT`、`REQUEST_REPLAY_APPROVAL`、`REVIEW_REPLAY_APPROVAL` 和 `REPLAY_FAILED_EVENT`。系统重放重载仍使用配置中的系统角色。只有解析和授权成功后，包内组件才接收到 `FailedEventOperatorContext`。这使入口层继续承担身份边界，内部服务只处理可信的结构化操作者。

## 响应模型

本版没有新增或修改响应记录。管理操作仍返回 `FailedEventManagementBatchResponse`，其中包含目标管理状态、实际更新数量和按请求实体生成的 `FailedEventMessageResponse` 列表。审批请求、审批复核与重放执行仍返回单个 `FailedEventMessageResponse`。只读列表、分页和 CSV 则继续委托 v1835 的 `FailedEventQueryService`，其分页默认值、稳定排序和 CSV 列顺序没有被命令拆分触碰。

响应模型保持稳定的关键，不只是 Java 类型名没变，还包括实体变更发生的先后。管理服务先读取旧管理状态，再调用实体的 `markManagementStatus`，随后写历史，最后把已经更新的实体转换成响应。审批服务先完成状态机检查，再修改实体并写审批历史，最后转换响应。重放服务在发送成功后先标记实体已重放，再写成功尝试；发送失败则标记失败信息并写失败尝试。返回值看到的状态因此与旧实现一致。

重放流程中新增了包内不可变记录 `EffectiveReplayEvent`，它不是对外 DTO。过去 eventId、eventType、aggregateType、aggregateId 和 payload 作为五个并行字符串在多个私有方法之间传递，参数位置相近，很容易在以后重构时误换。现在字段一旦解析完就带着名字进入一个值对象，发布消息、更新实体和保存尝试都从同一对象读取。对外 JSON 没有多一个字段，但内部类型系统能阻止一类真实的错位缺陷。

异常响应也保持原样。请求体缺失仍是 400；管理 id 非正数、数量超过 100、状态或备注缺失仍是 400；失败事件不存在仍是 404；审批状态冲突、请求人复核自己的申请、未批准就重放、Outbox 关闭仍是 409；操作员动作未授权仍由原解析器给出既有错误。拆分没有借机统一文案，因为这些文案可能已经被前端、脚本或运维手册依赖。

## 上游证据配置

失败事件命令链依赖两类上游配置。第一类是 `FailedEventReplayProperties`，它定义列表限制、导出限制、允许角色以及系统重放角色。v1836 的公共门面仍持有该属性，只在无显式角色的重放重载中读取系统角色；角色规范化和动作授权继续由操作员解析器完成。内部审批器与重放器没有复制角色表，因此以后角色策略变化只有一个权威入口。

第二类是 `OutboxRabbitMqProperties`。它只被 `FailedEventReplayService` 持有，因为只有执行重放需要判断 RabbitMQ Outbox 是否开启、读取 exchange，并按 event type 计算 routing key。公共门面、记录器、管理器和审批器都不知道这些连接细节。这样一来，审批逻辑不会因为消息基础设施配置而变得难测，管理状态也不会意外获得发送消息的能力。

死信记录的上游输入不是新的配置文件，而是 RabbitMQ 传入的 `Message` 和监听器明确给出的 dead-letter queue。记录器按原顺序读取消息属性 id、`eventId` header，并在两者都不可用时根据 payload 与 headers 计算 SHA-256。这个顺序本身就是幂等证据：生产者提供的稳定 id 优先，事件 id 次之，内容摘要只作最后兜底。v1836 只是把算法移动到专属类，没有改变任何输入字节。

本版不读取凭据值、不解析原始端点、不创建新的 RabbitMQ 连接，也不改变 Spring profile。配置依赖的缩小反而让安全审查更直接：搜索、管理和审批组件看不到 exchange；记录器看不到角色；重放器看不到凭据提供者。每个组件拥有的能力与它的职责对齐。

## 服务层核心流程

死信记录流程从 `record(Message, deadLetterQueue)` 开始。门面开启事务后调用记录器。记录器用 UTF-8 解码 body，解析 message id，然后先查询 `findByMessageId`。不存在时才构造 `FailedEventMessage.record`，保留 eventId、eventType、aggregateType、aggregateId、首次死亡队列、当前死信队列、死亡原因和 payload。若并发线程同时插入相同 message id，数据库唯一键只允许一个成功；失败线程捕获 `DataIntegrityViolationException` 后回读已存在实体。这样既不吞掉其他数据库异常，也保持幂等。

管理流程先检查请求体，再对 id 去重。去重后数量必须在 1 至 100 之间，每个 id 必须是正数。状态不能为空，备注去除首尾空白后最多保留 500 字符。服务一次查询全部实体，数量不一致就整体返回 404，不做部分更新。对每个实体，代码先保存 previousStatus，再更新当前状态，并以同一个时间戳和操作者写 `FailedEventManagementHistory`。门面事务保证任意历史写入失败时实体变更一起回滚。

审批请求流程先查找失败事件并验证操作员上下文，再提取最多 500 字符的审批理由。已重放事件不能重新申请；已有待审申请不能重复申请；已经批准也不能覆盖。通过后记录请求人和时间，并写一条 `REQUESTED` 历史。审批复核流程只允许 `APPROVED` 或 `REJECTED`，拒绝时备注必填。它还读取实体上的请求人，与当前复核人比较；相同就返回冲突，落实双人控制。批准和拒绝分别调用实体方法，历史 action 由状态名映射，保证实体与历史一致。

重放执行先确认事件存在、操作员上下文有效、理由不空、审批已经通过且 Outbox 开启。接着解析有效事件：eventId 优先使用请求值，其次原失败事件值，最后生成 UUID；最终值必须能被 `UUID.fromString` 接受。其余四个字段也按“请求修复值优先、原记录兜底”选择，并保持缺失字段的原 400 文案。五项结果组成 `EffectiveReplayEvent`。

如果实体已经是 `REPLAYED`，服务不再次发送消息，而是写一条 `SKIPPED_ALREADY_REPLAYED` 尝试并返回现有状态。如果尚未重放，服务按既有顺序写 content type、message id、eventId、aggregateType、aggregateId、eventType、来源失败事件 id 和来源 message id，然后发送到配置的 exchange/routing key。成功时更新重放次数和时间并写 `SUCCEEDED`；`AmqpException` 时截断错误信息、标记失败并写 `FAILED`。无论成功还是失败，审计字段都来自同一个有效事件对象和同一个操作员对象。

共享命令支持没有吸收领域决策。`requireOperatorContext` 只保证内部命令不会在缺少可信身份时运行；`firstNonBlank` 只实现有序兜底且保留选中值原样；`truncate` 只实现稳定长度限制。管理备注必填、拒绝备注必填、UUID 格式和审批状态判断仍留在最了解语义的组件中，避免工具类成为新的巨型依赖中心。

## Java 证据检查

第一项机械证据是编译器。所有控制器与监听器没有修改，却能在 Spring 集成测试中成功创建上下文，说明公共构造依赖、方法签名和 Bean 注入仍然成立。包内组件是 `final @Component`，只有同包门面能在类型层面直接使用它们，其他模块没有获得新的公共入口。

第二项是 `FailedEventCommandArchitectureTests`。它读取门面源码，要求五条委托语句存在，并明确禁止 `Repository`、`RabbitTemplate`、`OutboxRabbitMqProperties`、`MessageDigest` 和 `DataIntegrityViolationException` 回流。这个门不是风格建议，而是会失败的回归测试。测试还限制门面不超过 250 行，检查四个领域组件保持包内可见，并按顺序搜索七个消息头写入表达式。

第三项是 `FailedEventCommandSupportTests`。它验证 `firstNonBlank` 跳过 null 与空白却不擅自 strip 选中值；验证截断对 null、等长和超长输入的行为；验证缺少操作员上下文仍返回 400 以及原错误原因。直接测试这些小规则，可以防止以后为了“清理字符串”而无意改变 payload、备注或 header 值。

第四项是维护普查。`scripts/java-maintainability-census.ps1 -Json` 使用显式 `StreamReader.ReadLine()` 和 Windows 长路径前缀统计 1483 个生产 Java 文件。正式结果为最大 1530 行、超过 500 行 38 个、超过 750 行 4 个、超过 1000 行 2 个。`JavaMaintainabilityBudgetTests` 同时固定聚合上限和六个命令文件上限。这里禁止用会漏掉空白行的 `Measure-Object -Line`，避免错误数字进入账本。

第五项是 Spotless 和 SpotBugs。Spotless 统一仓库要求的 CRLF 与 Java 格式；SpotBugs 独立顺序运行，避免与另一个 Maven 进程争用 `target`。本版不新增 SpotBugs 排除项。最终还必须执行完整 `mvnw verify`，由全部 JUnit、JaCoCo、SpotBugs 和 Spotless 共同给出结果，再由远端 GitHub Actions 在另一台机器复现。

## mini-kv 证据检查

本版只修改 Java 仓库，没有写入 `D:\C\mini-kv`，也没有更名、移动或重算任何 mini-kv 历史 fixture。失败事件管理和 RabbitMQ 重放属于 Java 运行时内部能力，不改变跨项目只读证据 schema，因此按四项目协调规则属于可独立推进的非合同维护工作。

Java 现有证据聚合仍可能读取 mini-kv 的冻结输出或摘要，但 v1836 没有触碰 `ops` 响应模型、证据构建器、静态工件或路径常量。完整回归会继续覆盖这些读取路径，若命令拆分意外影响应用上下文、序列化或证据服务，相关测试会失败。这里的“mini-kv 检查”不是声称启动了真实 C++ 进程，而是明确本版没有扩大合同，也没有修改被冻结的跨项目输入。

不能把单仓库回归写成三项目联调。本版能证明的是：Java 内部重构通过自身测试，跨项目合同字节没有被编辑；它不能单独证明 Node、Java、mini-kv 的实时联合运行。真实联合验证仍属于最终 integration capstone。这样的边界表达比笼统说“全系统正常”更诚实，也更便于评审复现。

## 阻断与安全边界

第一个阻断边界是操作员授权。外部字符串身份只能经 `FailedEventOperatorContextResolver` 进入命令组件，组件还会拒绝 null 上下文。拆分没有提供绕过角色检查的公开 Bean。请求审批、复核审批和执行重放使用不同 action，审批通过也不等于任何角色都能执行重放。

第二个边界是双人审批。请求人 id 保存在失败事件上；复核时审批器必须比较请求人与当前操作员。相同人员复核自己的请求仍返回 409。拒绝必须有备注，只有待审状态能被复核，已经批准或拒绝的记录不能被静默覆盖。这个状态机与历史写入处于同一事务，避免出现实体显示批准而历史缺失的不可审计状态。

第三个边界是消息发布。只有 `FailedEventReplayService` 持有 RabbitTemplate，且执行前同时要求审批通过和 Outbox 开启。管理器、审批器和记录器无法发送消息。发布只捕获 `AmqpException` 并转化为失败状态与审计尝试，其他编程错误不会被宽泛吞掉。错误文本仍限制 500 字符，避免外部异常无限写入数据库。

第四个边界是幂等与重复执行。死信记录通过稳定 message id 和数据库唯一键去重。已经重放的事件再次调用不会再发 RabbitMQ，只记录跳过尝试。eventId 必须是合法 UUID，必需字段缺失时在发送前阻断。所有这些检查都位于真正拥有数据和基础设施的组件，门面只负责编排，不复制判断。

第五个边界是项目范围。本版不打开 write routing、不启用新的 shard router、不读取 credential value、不解析 raw endpoint、不建立 managed audit connection，也不执行 deployment/rollback。它只整理现有 Java 失败恢复代码。任何对路由、数据库迁移、消息 schema、fixture 或跨项目路径的修改都会超出 v1836 边界并使版本失败。

## 测试覆盖

聚焦验证先运行共享支持、命令结构、查询结构、管理页面、审批状态和重放执行契约测试。Spring Boot 测试真实启动 Flyway 的 12 个迁移、H2、JPA Repository、Web 上下文和事务代理，证明拆分后的构造注入可用。审批集成测试覆盖待审、批准、拒绝、自审阻断和不存在记录；重放执行契约覆盖稳定 404 响应与批准后的执行合同；管理测试覆盖批量状态更新和响应。

RabbitMQ 的真实容器测试由 `docker` tag 隔离，普通本地 verify 不依赖 Docker；GitHub Actions 的独立 Docker 作业会启动真实 RabbitMQ，覆盖坏消息进入死信、记录字段、未授权阻断、未审批阻断、申请与复核、重放消息被消费者接收，以及重放尝试所有审计字段。这是消息头和发布行为没有改变的最强证据。

结构测试不是行为测试的替代品。它负责防止代码以后长回原样，例如有人为了方便把 Repository 注入门面，或把内部组件改成 public；行为测试负责证明运行结果没变。维护预算又从另一个方向防止新类膨胀。三者组合后，既能抓语义回归，也能抓架构回退和体积回退。

最终封版前，必须在本讲解已经存在的前提下运行完整 `mvnw verify`。通过标准是全部测试零失败、零错误、零跳过，JaCoCo 所有覆盖门达标，SpotBugs `BugInstance=0` 与 `Error=0`，Spotless 清洁。随后提交、tag 和分支一起推送，等待远端主作业与 Docker 作业都成功，再回写 run id。任何一步缺失都只能称为“实现完成”，不能称为版本完成。

## 一句话总结

v1836 在不改变任何外部合同的前提下，把失败事件巨型服务收缩成一个可审计事务门面和四条能力隔离的命令链，让死信幂等、人工管理、双人审批与 RabbitMQ 重放各自有清晰输入、输出、依赖、阻断条件和机械回归证据。
