# v1821 生产卓越拆分讲解：草稿文本包提交预检与收尾整族迁出

## 入口路由

v1821 处理的是 `TextPackageSubmissionPreflight`。它位于 v1820
`TextPackageReviewPreflight` 之后，也位于后续 `TextPackageComparisonPreflight` 之前。
通俗地说，上一站回答“审查一份未来草稿文本包时应看哪些条件”，这一站回答“在真正接收这份包
以前，提交方必须摆出哪些只读证据槽位，以及哪些能力必须继续关闭”。它不是上传接口，也不是审批
按钮，而是一份可由机器枚举的提交前检查目录。

对外入口由两个 Controller 承担。主 Controller 暴露五条 GET 路由，分别返回完整目录、身份
槽位、摘要与签名槽位、证据值槽位、策略与执行收尾槽位。Closeout Controller 暴露六条 GET
路由，分别返回收尾总目录、交接账本、路由证据、归档清单、运行时边界和完整性摘要。合计十一条
HTTP 路由的字符串在本版完全不变，调用方仍使用历史地址，Spring 的映射位置也没有改变。

两个 Controller 继续留在根 `ops` 包。这个决定不是因为根包适合继续堆积，而是 Controller
就是现有公开兼容入口。将内部实现迁入窄包，同时让旧入口只保留映射和委托，可以降低根包压力，
又不会把一次维护性拆分伪装成 API 迁移。请求到达旧 Controller 后，依赖注入会把调用转交给
`ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight` 中的新归属 Service。

新的
`OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths`
拥有十一条后缀常量和统一 `BASE_PATH`。根 `OpsShardReadinessRoutePaths` 不再自己保存这些
字面量，而是委托叶子所有者；迁出的 Service 直接引用叶子所有者。这样，路径只有一个事实来源，
根聚合器仍向历史代码提供兼容常量，未来维护者也能从类名直接判断路由属于哪个业务阶段。

Closeout 内还有三个细节视图：slot comparison、guardrail summary 和 operator handoff。
它们的 endpoint 是 Closeout catalog 地址后追加 URI fragment，不是新的 Spring HTTP 路由。
本版保留这种表达，没有误把内部证据锚点升级为外部接口。入口层最终形成清楚的两级结构：
十一条真实 GET 路由负责可访问响应，三个 fragment 负责在同一份收尾证据中定位细节。

## 响应模型

主响应 `SubmissionPreflightResponse` 接收的输入不是文件内容，而是静态目录和状态描述。它包含
项目名、版本、只读与执行许可、来源计划、上游 Node 与 Java 审查版本、提交预检状态、包状态、
文本解析状态、签名解析状态、批准状态、值导入状态、运行时状态和兄弟仓库变更状态。后半部分是
布尔边界以及 `SubmissionSlot`、`ComparisonControl`、`SubmissionGate`、checks 等列表。

二十五个 `SubmissionSlot` 是本阶段的核心输入清单。基础槽位十一项，保障槽位十四项。基础部分
关注身份、摘要、签名信封和来源审查链；保障部分关注证据句柄、策略状态、执行锁、归档收尾等。
每个槽位记录标识、版本、用途、检查问题、拒绝原因和来源 endpoint。它表达的是“未来提交必须
引用哪项证据”，并不携带证据中的原始敏感值。

二十五个 `ComparisonControl` 给每个槽位配套 fail-closed 控制。槽位回答要看什么，控制回答
不满足时如何拒绝。十个 `SubmissionGate` 则站在更高层声明能力仍然关闭，例如不能接受真实包、
不能解析签名文本、不能授予批准、不能导入操作员值、不能打开运行时载荷。三种对象同时出现，
是为了防止“目录完整”被误读成“执行获准”。

主 Support 统一复制列表、计算数量、设置状态并附加固定检查项。五个主 Service 只选择不同切片，
不各自复制响应组装逻辑。CatalogService 返回完整二十五槽位、二十五控制和十个 Gate；
IdentityService 返回身份相关切片；DigestSignatureService 聚焦摘要与签名；
EvidenceValueService 聚焦来源证据和脱敏值；PolicyExecutionCloseoutService 聚焦策略、
执行锁和归档边界。

Closeout 使用独立的 `SubmissionPreflightCloseoutResponse`。它包含收尾是否就绪、来源版本、
交接状态、路由状态、归档状态、运行时状态、完整性状态，以及接受包、解析文本、解析签名、授予
批准、打开运行时载荷等布尔开关。交接项、护栏和 checks 仍是不可变列表。主响应说明提交前应该
具备什么，Closeout 响应说明本轮证据如何被封口并交给下一阶段，两者职责相邻但不混同。

SpotBugs 对这两个带列表组件的 record 原本就有 `EI_EXPOSE_REP` 与 `EI_EXPOSE_REP2` 的既有
基线条目。类型迁包后，本版同步修改四处 FQN。这里没有增加新的静态分析豁免，只是让同一条历史
接受边界跟随类型的新位置，否则 SpotBugs 会把“排除找不到旧类”误认为新问题。

## 上游证据配置

本阶段的直接上游是 v1820 的 `TextPackageReviewPreflight`。主流程的 FoundationSlotCatalog
和 AssuranceSlotCatalog 读取上一站已经公开的 endpoint 常量，把审查目录变成提交槽位的来源。
输入是不可变地址与元数据，不是上一站的内部 Criteria 列表，也不是文件、密钥、令牌或数据库
对象。v1820 先迁出并公开需要的边界，使 v1821 可以只处理自己的包归属。

举一个简单例子。上游审查阶段有一条“摘要复核标准”，它的 endpoint 表示该标准在哪里被描述。
提交预检阶段创建“摘要绑定槽位”，要求未来提交包引用这条审查证据。如果缺少引用，
ComparisonControl 给出拒绝码，SubmissionGate 仍声明签名解析关闭。整个过程只搬运证据句柄，
不会读取摘要原文，更不会验证真实签名。

Closeout 的 FoundationHandoffCatalog 与 AssuranceHandoffCatalog 会把主流程和相邻阶段的
endpoint 整理成交接项。每个交接项说明来源、用途、预期状态和地址。CloseoutCatalogService
再把这些交接项、护栏和检查结果组装为总目录。输入仍然是已经存在的只读声明，输出是收尾视图，
没有隐藏的网络调用。

本版没有修改 Node、mini-kv 或 C++ 项目的证据 schema。源码中保留的 Node 与 Java 来源版本
只是历史计划锚点，不意味着运行时去访问兄弟仓库。四项目统筹规则要求契约变化按依赖顺序推进，
而本版没有契约变化，所以属于可以独立进行的 Java 内部维护。

历史归档路径也不是上游源码包的一部分。Node 可能按绝对路径和摘要引用 Java 或 mini-kv 的
`e/<version>/` 证据，因此本版只移动 Java 源文件和包内测试，绝不重命名归档目录、JSON、
截图或交接文件。源码所有权变清楚，历史证据仍保持原位置和原摘要。

## 服务层核心流程

一次主目录请求的流程可以拆成四步。第一步，根 Controller 接收只读 GET 请求。第二步，
CatalogService 从 SlotCatalog 取得二十五槽位，从 ComparisonControlCatalog 取得二十五控制
和十个 Gate。第三步，Support 复制列表、汇总计数、设置所有禁止执行的布尔值并添加 checks。
第四步，Controller 返回 record。流程中没有 Repository、消息队列、文件系统写入或外部客户端。

分片 Service 复用同一条流程，只是输入切片不同。例如 IdentityService 选择身份范围，
DigestSignatureService 选择摘要与签名范围。切片方法返回 `List.copyOf`，既避免调用方修改
内部目录，也让每个 Service 的职责保持薄而清楚。`@Transactional(readOnly = true)` 继续保留，
它在当前静态目录中不是写事务开关，而是把只读意图固定在 Spring 边界上。

一次 Closeout catalog 请求也有四步。Controller 调用 CloseoutCatalogService；
Service 收集 Foundation 与 Assurance handoff item；CloseoutSupport 组合交接项、十一项
guardrail、状态和 checks；最终返回 CloseoutResponse。其他五条 HTTP Service 只选取不同
观察面，例如路由证据只强调十一条入口，归档清单强调不写文件，运行时边界强调载荷未打开。

三个 fragment Service 从 CloseoutCatalogService 的 endpoint 派生地址。SlotComparisonService
比较主流程槽位与收尾交接是否对齐；GuardrailSummaryService 汇总关闭能力；
OperatorHandoffService 强调操作员可读的交接清单。它们没有 Controller，所以不是额外入口。
将它们保留在同一窄包，能让维护者从 Closeout 总目录顺着代码读到每个细节视图。

本版新增的窄包最终包含主流程与 Closeout 的实现、响应、Support、目录和白盒测试。两个根
Controller 只剩公开适配职责。维护者今后查提交预检，不必在九百多个根文件里搜索同一超长前缀，
而是进入一个明确目录，按 Controller、Service、Catalog、Support、Response 的顺序阅读。

## 为什么 Closeout 必须同刀迁移

最初可以考虑把主流程和 Closeout 拆成两个版本，但源码依赖表明这样并不划算。Closeout 的多个
目录直接读取主流程 Service endpoint，主流程与 Closeout 共享同一阶段语义，根 Controller
和下游 ComparisonPreflight 又同时消费两侧。如果只迁主流程，Closeout 留根，就必须把更多内部
成员临时改成 public；下一版迁 Closeout 时再收回或继续背负这些 API，反而扩大维护成本。

反过来只迁 Closeout 也有同样问题。Closeout 的交接项需要主流程身份、摘要、证据值和策略执行
地址，窄包会倒过来依赖仍在根包的实现类。虽然编译可以通过，但所有权会呈现“收尾属于模块，
主体仍属于杂物间”的奇怪状态，文档和代码都难解释。

因此 v1821 选择整族迁移。它一次处理二十八个物理实现文件，删除根包中独立 GateCatalog，
把 Gate 构造和 `allGates()` 合入 ComparisonControlCatalog，并新增一个 RoutePaths 文件。
根包净减二十九个文件，从 961 降到 932；新包增加二十八个迁移文件，路由目录增加一个文件，
旧 Gate 文件消失，总 `ops` Java 文件数仍是 1,352。

Gate 与 ComparisonControl 的合并有语义依据。两者都描述提交阶段的拒绝边界，由同一组 Service
和白盒测试消费，变化节奏一致。合并后控制和 Gate 仍使用独立 record、独立方法、独立计数，
没有把两种概念揉成一个对象。文件规模仍然可读，不是把逻辑塞进巨型类来迎合数字。

这就是本版“刀到肉”的地方：移动的不只是名字相似的文件，而是一个能独立解释输入、输出、
关闭能力和下游读边的完整模块。若只为了追求版本数量把它切成两刀，表面每版更小，实际会增加
临时 public API、重复导入和重复验证，不符合后期维护目标。

## 下游输入与输出

主流程的直接下游之一是 `TextPackageComparisonPreflight`。它的 FoundationLaneCatalog
读取 IdentityService 和 CloseoutHandoffLedgerService，DigestSignatureLaneCatalog 读取
DigestSignatureService，AssuranceLaneCatalog 读取 EvidenceValueService。迁包后，这四个
Service 的 `ENDPOINT` 成为明确的 `public static final` 边界，下游只导入需要的类。

另一个下游是 `ComparedPackageEvidenceIntakeSourceSubmissionSlotCatalog`。它读取主 Catalog
endpoint，表示未来比较证据必须追溯到提交预检总目录。ProfileSectionRegistryService 也调用
主 CatalogService，ProfileSectionSourceCatalog 持有主 Response，用来把 Intake、Review、
Submission、Comparison 等阶段拼成只读综合视图。

这些下游的输入都是 endpoint 或 response，没有读取 package-private SlotCatalog、
ComparisonControlCatalog 或 CloseoutHandoffCatalog。新包暴露的是稳定、最小的公共表面，
内部目录仍可以在模块内演进。编译器在第一次主编译时准确列出了两个 Controller 和六类下游
缺失导入，本版逐项补齐，而不是使用通配符掩盖耦合。

输出契约没有变化。Controller 返回的 record 类型和组件顺序不变，endpoint 字符串不变，
列表顺序、版本字符串、profile、拒绝码和 checks 不变。对调用者而言，v1821 前后收到的是
同一份只读证据；对维护者而言，源码从根包噪声中进入了可定位模块。

下一刀自然可以考虑 `TextPackageComparisonPreflight`，因为它对本版的出边已经公开满足。
不过是否执行仍需按当前根包族群和耦合扫描决定，不能仅凭名称连续移动。本版只为下一阶段提供
干净边界，没有提前移动比较实现，也没有修改比较规则。

## Java 证据检查

第一层证据是主编译。移动完成后，`mvnw -DskipTests compile` 首次失败，缺失符号集中在两个
根 Controller、ComparisonPreflight 三个 lane 目录、ComparedPackageEvidenceIntake 和
ProfileSection。逐项添加显式 import 后主编译通过，证明生产调用图已闭合。

第二层证据是测试编译。`mvnw -DskipTests test-compile` 首次列出四个根测试、ProfileSection
测试支持和迁入包 CatalogTests 的旧引用。根 ControllerTests 与 RoutePathsTests 导入公开
Service；测试支持导入主 CatalogService；迁入白盒测试改用叶子 RoutePaths。再次编译通过，
证明测试代码与新所有权一致。

第三层证据是文件预算。根 `ops` 直接 Java 文件实测为 932，递归总数为 1,352。三处长期钉子
`MAX_ROOT_OPS_MAIN_JAVA_FILES`、`EXPECTED_ROOT_OPS_MAIN_JAVA_FILES` 和 v1809 精确计数断言
同步从 961 降到 932。新增 v1821 readability 测试检查代表性主流程与 Closeout 文件的新位置、
两个 Controller 的旧位置、GateCatalog 消失、文档索引、根上限和总量上限。

第四层证据是路由所有权。十一条 Service endpoint 由新 RoutePaths 的 BASE_PATH 与后缀组合，
根聚合器委托同一常量。RoutePathsTests 继续将 Service endpoint 与历史根常量比较，能够发现
多一个斜杠、少一个片段或拼写变化。三个 fragment Service 仍以 Closeout catalog endpoint
为前缀，保持原有语义。

第五层证据是质量门。Spotless 统一长导入和换行，SpotBugs 验证两个 Response 的 FQN 迁移，
JaCoCo 验证包迁移没有让覆盖率基线失效，完整 `mvnw verify` 覆盖单元、集成、文档与静态分析。
版本只有在这些门全部通过后才允许提交、打 tag 和推送。

## mini-kv 证据检查

mini-kv 在 v1821 中没有运行，也没有修改。Java 提交预检仍只声明未来证据句柄和关闭能力，
没有访问 WAL、快照、RESP 端口、分片表或运行时状态。把 C++ 服务拉起来不会增加本版可信度，
反而会把内部包维护误写成跨项目联调。

本版也没有要求 Node 消费新的 schema。ComparisonPreflight 读取的是 Java 内部 endpoint 常量，
Node 的历史绝对路径引用继续指向原归档。只要路由、响应、证据字段和归档位置不变，就不需要
mini-kv 或 Node 同步版本。

如果未来某版真正新增分片写路由、读取 mini-kv 实时槽位、改变证据 JSON 或让 Java 启停 C++
进程，那时必须按 mini-kv 到 Java 再到 Node 的依赖顺序设计和验证。v1821 明确没有打开这些
能力，所以正确证据是“不触碰、不启动、不伪造联调结果”。

归档规则继续生效：Do not rename or move archive roots。特别是 `e/<version>/`、截图说明、
historical fixture 和跨项目 handoff 路径不能因为源码包名更清楚就一起整理。源码重构与历史
证据保全是两件事，本版只做前者。

## 阻断与安全边界

主 Support 明确添加 no package acceptance、no draft text parsing、no detached signature
parsing、no approval grant、no runtime or sibling mutation 等检查。Closeout GuardrailCatalog
进一步列出不接受提交包、不解析签名文本、不授予批准、不导入操作员值、不打开运行时载荷、
不打开写路由、不修改兄弟仓库、不连接托管审计、不部署回滚、不启停服务。

这些边界不是文档口号，而是响应字段、目录项、拒绝码和测试共同表达的状态。所有 readiness
布尔值与 executionAllowed 仍保持关闭。公开 Service endpoint 只让下游能够引用证据地址，
不提供执行方法，也不携带凭据或原始端点值。

本版没有新增 POST、PUT、PATCH 或 DELETE 路由，没有 Repository.save，没有 RabbitMQ 发布，
没有 ProcessBuilder，没有文件写入，没有网络客户端。`@Transactional(readOnly = true)`
继续标记只读服务。即使未来调用者请求全部十一条路由，得到的也只是静态、不可变的证据视图。

回滚同样透明。若需要撤销，只需恢复包声明、导入、路由委托、Gate 独立文件、SpotBugs FQN、
文档和计数钉，不需要数据库迁移、消息补偿或归档搬迁。较小的运行时风险正是维护性拆分应有的
性质。

## 测试覆盖

七个白盒测试随实现迁入窄包：主 Catalog、Service、Support，以及 Closeout 的 GuardrailRoute、
HandoffCatalog、Service、Support 测试。它们需要访问 package-private 目录，因此测试与实现
同包是合理的，不应为了留在根测试包而把内部目录公开。

四个兼容测试继续留根：主 ControllerTests、主 RoutePathsTests、Closeout ControllerTests、
Closeout RoutePathsTests。它们从外部视角构造公开 Service，验证旧入口仍能返回既有版本、
数量、关闭状态和路径。测试分布本身就反映模块边界：白盒测试跟实现走，入口测试跟兼容层留。

下游测试覆盖 ComparisonPreflight、ComparedPackageEvidenceIntake 和 ProfileSection。
它们证明新 public endpoint 与 Response 不只是编译存在，也能被真实组合。readability 测试
防止文件悄悄回流根包，walkthrough compliance 测试要求本讲解包含标准章节、中文主体、
三千以上汉字、实际工作量说明、“禁止硬凑”和“本项目”。

Focused 测试用于快速定位本家族和相邻消费者，全量 verify 用于发现包级 JaCoCo、SpotBugs、
Spotless、Spring 上下文或其他历史测试的回归。两者不能互相替代。只有局部测试通过时，
版本仍处于施工状态；只有全量门和远端 CI 通过，版本才完成。

Windows 上这些类名很长，普通路径 API 可能超过传统限制。移动阶段采用长路径安全方式，
仓库保持 `core.longpaths=true`，最终仍由 Maven 编译、Git 索引和 GitHub Actions 共同验证。
这不是绕过工具，而是让工具在仓库既有命名现实下可靠工作。

## 实际工作量说明

本版不是把几个 package 声明替换一下。实际处理了主流程与 Closeout 共三十一个根族文件：
两个 Controller 保留，二十八个物理实现和七个白盒测试迁入新包，独立 GateCatalog 被删除并
合入 ComparisonControlCatalog，新增一个拥有十一条后缀的 RoutePaths。

生产代码侧修复两个 Controller 的十三个公开类型导入，修复 ComparisonPreflight 的身份、
摘要签名、证据值和 Closeout 交接四类读边，修复 ComparedPackageEvidenceIntake 的 Catalog
读边，以及 ProfileSection 的 Service 与 Response 读边。测试侧修复四个根兼容测试、
ProfileSection TestSupport 和迁入 CatalogTests 的路由所有者。

治理侧更新四处 SpotBugs FQN、三处根文件预算钉、新增五项结构断言的 v1821 readability
测试、新增 ops 说明、索引、changelog、进度记录和本篇中文讲解。还要执行格式化、Focused
测试、完整 verify、提交、tag、branch/tag 同步推送和远端 CI 观察。

工作量来自真实依赖和验证，不靠重复句子制造篇幅。规则中的“禁止硬凑”在本项目里意味着：
如果一个版本不足以解释输入、转换、输出、边界和验证，就应扩大有意义的工程范围；但如果两个
族群没有强依赖，也不能为了写长文硬塞进同一版。v1821 之所以够厚，是因为主流程与 Closeout
本来就是一条完整提交预检链。

本版也克制了无关动作：没有顺手整理根包其他族群，没有改版本字符串，没有重排目录项，没有
重写历史进度乱码，没有触碰 `docs/project-explanation/` 用户目录，没有修改 Node 或 mini-kv。
大工作量不等于大范围漂移，真正的工程范式是把一个明确模块做完整，并让每个变化都有对应证据。

## 一句话总结

v1821 在不改变十一条路由、两个响应、三类证据目录和所有执行阻断的前提下，把提交预检主流程与
Closeout 整族迁入单一维护包，使根 `ops` 从 961 降到 932、总量保持 1,352，并为后续
TextPackageComparisonPreflight 提供了清楚、最小、已验证的公开读边。
