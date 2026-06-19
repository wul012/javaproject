# v1819 生产卓越拆分讲解：签批草稿文本包接收注册表迁出

## 入口路由

v1819 处理的是签批草稿证据链里的 `TextPackageIntake`。名字里的“接收”很容易让人误解，
仿佛系统已经能够上传、解析或保存一份真正的草稿文本包；实际上它只是一组只读注册表，
负责说明未来人工准备文本包时应该具备哪些字段、哪些摘要、哪些来源引用以及哪些阻断条件。
它不读取文件内容，不接收签名原文，不保存审批结论，也不把任何值送进运行时。最简单的理解
是：它像一张入场检查表，列出“以后真要递交文本包时必须带什么”，但检查表本身既不是文本包，
也不是递交动作，更不是批准动作。

外部请求仍然通过原有的九个 GET 路由进入，包括 catalog、identity correlation、
digest binding、signature envelope、source evidence、operator value handle、
policy review state、execution lock 和 archive closeout。两个 Controller 继续留在根
`ops` 包，因为它们承担的是稳定的 Spring Web 入口：`@RequestMapping` 仍指向原来的
`/api/v1/ops/shard-readiness`，每个 `@GetMapping` 仍读取根聚合器里的同名常量。调用方
看到的 URL、HTTP 方法和响应类型没有变化，Spring 组件扫描也不需要理解新的实现包。

这次改变的是路由事实的内部所有权。过去九个后缀直接写在根
`OpsShardReadinessRoutePaths` 中，服务类也依赖这个 package-private 聚合器。实现迁入
子包后，如果把根聚合器简单改成 public，会让一个原本只服务历史兼容的巨型类变成新的跨包
公共 API；如果在每个服务里复制字符串，又会出现多个真相源。v1819 因此新增
`OpsShardReadinessSignedApprovalArtifactDraftTextPackageIntakeRoutePaths`，由它公开
`BASE_PATH` 和九个后缀，根聚合器只做委托，迁出的服务直接读取这个叶子所有者。

请求的完整流向可以通俗地写成：浏览器或测试请求旧 URL，根 Controller 接住请求，Controller
调用新包里的 Service，Service 从新的 RoutePaths 所有者组合出相同 endpoint，再调用
FieldCatalog、GuardCatalog 和 Support 构造只读 Response。入口没搬，内部实现搬了；
字符串没变，所有权变清楚了。这正是后期保养需要的低风险切法。

## 响应模型

核心输出是
`OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse`。
它记录项目名、版本、只读标志、执行许可、接收准备状态、上游计划版本、草稿文本状态、签名状态、
审批状态、值导入状态、运行时状态、字段数量、Guard 数量、Gate 数量以及对应的明细列表。这里
最重要的不是字段多，而是所有字段共同表达同一件事：系统只展示“预期结构和关闭边界”，不宣称
真实材料已经进入平台。

例如 `readyForDraftTextPackageIntake` 可以说明注册表已经能列出接收要求，但
`readyForDraftTextPackageReview`、`readyForSignedDraftText`、
`readyForDetachedSignature`、`readyForApprovalGrant` 和
`readyForRuntimePayload` 仍然保持关闭。读者不能只看一个“ready”就推断业务已经可执行，
必须结合整个响应的状态位和 Gate 一起理解。Support 构造器还会复制输入列表、统计通过数量、
加入固定检查项，并根据只读边界计算最终状态，避免服务各自拼接一套不一致的响应语义。

响应中的 `IntakeField` 不是用户提交的字段值，而是字段定义。它描述代码、来源版本范围、字段名、
用途、敏感性边界、对应 Guard 和来源 endpoint。`IntakeGuard` 描述字段缺失或状态不合法时的
拒绝规则；`IntakeGate` 描述当前阶段明确没有开放的能力。三者分别回答“将来需要什么”、
“缺少时如何拒绝”和“即使字段齐全也不能做什么”。把这三个层次分开，能防止维护者把结构齐全
误读成执行许可。

本版只移动响应类型的包位置，没有增删 record 组件，没有改变列表顺序，没有修改状态算法。
SpotBugs 配置中两处 `EI_EXPOSE_REP` 与 `EI_EXPOSE_REP2` 排除项也只是把旧 FQN 改为新 FQN，
继续覆盖同一个含列表的响应类型，并没有新增静态分析豁免。也就是说，输出内容不变，Java 类型
的归属更准确，静态分析仍然能找到同一个对象。

## 上游证据配置

`TextPackageIntake` 的输入不是数据库记录，也不是文件流，而是上一站
`ArtifactDraftInstructionPreflight` 已公开的 endpoint 常量。FoundationFieldCatalog
引用 catalog、digest instruction、operator instruction 和 signature instruction；
AssuranceFieldCatalog 引用 evidence instruction、value policy instruction、
embargo instruction、draft text lock 和 closeout。每一个字段定义都能指向一类明确的
上游只读证据，说明这项要求从哪里来。

这种输入方式的价值在于“只传证据地址，不传敏感内容”。例如文本包需要一个摘要绑定，字段目录
只引用 digest instruction 的 endpoint，表示摘要规则来自哪里，而不是把真实摘要或文件
复制进代码；需要签名信封信息时，只引用 signature instruction endpoint，不读取签名原文；
需要值策略时，只引用 value policy endpoint，不读取操作员值。上游把边界说清楚，本阶段
再把这些边界整理成接收字段，整个链条始终是只读的。

v1818 已经把 InstructionPreflight 九个服务的 `ENDPOINT` 公开为不可变常量，并让
TextPackageIntake 的字段目录从新包导入它们。因此 v1819 迁移时，出站依赖已经满足，不需要
回头修改 v1818 的内部目录，更不需要把 package-private 类型强行公开。这体现了链式拆分的
顺序原则：先迁出被下游读取的一站并建立 public endpoint 边界，再迁出读取它的下一站。

如果顺序反过来，TextPackageIntake 迁出时会同时碰到根聚合器、上游私有常量和下游读者，
一次改动的耦合面会明显变大。现在每一版只处理一条相邻边：v1818 负责公开上游 endpoint，
v1819 消费这些 endpoint 并公开自己的 endpoint，v1820 可以继续迁出
TextPackageReviewPreflight。这个节奏让编译器成为可靠的依赖审计器，也让每个版本都能独立
回滚和解释。

## 服务层核心流程

九个服务的结构相似，但每个服务选择不同的字段、Guard 和 Gate 切片。CatalogService 返回
完整目录，用来观察 25 个字段、25 个 Guard 和 20 个 Gate 的总体状态；IdentityCorrelation
只展示请求清单、关联编号、操作员和包身份；DigestBinding 聚焦各类摘要；SignatureEnvelope
聚焦签名元数据；SourceEvidence 聚焦来源计划和引用；OperatorValueHandle 只展示脱敏句柄；
PolicyReviewState 展示策略状态；ExecutionLock 展示写入和运行时锁；ArchiveCloseout
展示归档收尾条件。

每个服务都以 `@Transactional(readOnly = true)` 明确只读语义。它们不注入 Repository，
不持有消息队列客户端，不调用外部连接，也不启动进程。调用过程只是选择静态目录切片，然后把
版本、endpoint、profile 和额外检查项交给 Support。Support 对列表做不可变复制，统计状态，
补齐项目级固定边界，最后返回 response。整个流程没有隐藏的副作用入口。

两个 Controller 只负责依赖注入和方法映射。FoundationController 组合前四个基础服务，
AssuranceController 组合后五个保障服务。v1819 保留这种分组，没有把 Controller 搬进
实现包，也没有为了减少根文件数把两个入口合成一个巨型 Controller。根包压力由实现类迁出
解决，而不是靠牺牲入口可读性解决。这符合“写代码不要出现难于维护的巨型文件”的规则。

迁移后的新包把同一阶段的 Response、Support、FieldCatalog、FoundationFieldCatalog、
AssuranceFieldCatalog、GuardCatalog 和九个 Service 放在一起。维护者想看某个 endpoint
为何返回这些字段，只需在一个包里沿 Service 到 Catalog 再到 Support 阅读，不用在近千个
根包文件中搜索超长前缀。包名本身承担了领域上下文，长类名虽然仍为历史兼容保留，但查找范围
已经从整个根包缩小到一个明确阶段。

## GateCatalog 为什么合并

新增 RoutePaths 叶子会增加一个 Java 文件。如果只是机械搬走 15 个文件并新增一个路由所有者，
整体 `ops` 文件数会从 1,352 增到 1,353。后期治理不能一边整理根包，一边让总量无止境增长，
所以本版检查了该家族内部是否存在职责相近、生命周期一致、可以合理合并的目录。结论是原
GateCatalog 与 GuardCatalog 都属于同一组接收边界规则，适合放在同一个内部目录类中。

Guard 回答“输入定义不满足时拒绝什么”，Gate 回答“当前阶段即使定义满足也仍然关闭什么”。
它们都只被同一家族的 Service 和白盒测试使用，都是 package-private，不参与 Spring 注入，
也没有独立外部消费者。合并后 GuardCatalog 同时暴露 `allGuards()`、`guards(...)`、
`allGates()` 和 `gates(...)`，两套概念仍由不同方法、不同 record 类型和不同测试断言区分，
并没有把语义揉成一团。

原 GateCatalogTests 被迁入新测试包，测试名称继续保留，因为它表达的是业务概念而不是物理
文件名；测试内部改为调用合并后的 GuardCatalog。这样既能证明二十个 Gate 的类别和
fail-closed 语义没有丢失，也能证明文件所有权调整没有删除概念。GuardCatalogTests 继续
独立检查二十五个 Guard 的拒绝码、类别和切片，两个测试类从不同角度保护同一个内部目录。

这不是为了数字好看而硬凑。合并成立的前提是职责相邻、可见性相同、变化原因相同、调用方相同；
如果这些条件不成立，本版应接受总量暂时增加，而不应强行把无关类塞进大文件。当前合并后的
GuardCatalog 规模仍然可读，内容是静态目录，没有复杂分支和状态机，因此不会形成难维护的
巨型代码文件。它吸收 RoutePaths 的文件成本，同时降低了一处目录跳转。

## 下游消费如何对齐

本阶段迁出后，直接下游是 `TextPackageReviewPreflight`。它的 FoundationCriteriaCatalog
读取 identity correlation、digest binding 和 signature envelope endpoint，
AssuranceCriteriaCatalog 读取 source evidence、operator value handle、
policy review state、execution lock 和 archive closeout endpoint。这些引用不是调用
服务执行业务，而是把“审查标准来自哪一段接收证据”写进只读 criterion。

v1819 将九个 Service 的 `ENDPOINT` 改为 `public static final`，并在两个 ReviewPreflight
criteria catalog 中显式导入新包类。公开的是已经存在的不可变 URL 字符串，不是 Service
内部方法、FieldCatalog 或 GuardCatalog。这样下游只能读取稳定证据地址，不能越过边界操作
本阶段的内部目录。下一版迁出 ReviewPreflight 时，它的出站依赖已经预先满足。

另一个下游是 `SignedApprovalDraftTextPackageProfileSectionRegistryService`。它会依次读取
intake、review preflight、submission preflight 等多个阶段的 catalog response，组装一个
跨阶段的只读 profile section。v1819 更新了该服务对 Intake CatalogService 的导入，也更新
SourceCatalog 对 IntakeResponse 的导入。这样综合视图仍能看到文本包接收阶段，没有因包迁移
丢掉一段证据链。

Controller 测试、RoutePaths 测试、ReviewPreflight Support 测试和 ProfileSection 测试
辅助类也同步导入新 FQN。这里不能只依赖主代码编译，因为很多跨包消费者只存在于测试构造器中；
如果只修生产代码，`test-compile` 才会暴露遗漏。先跑 compile，再跑 focused test-compile
和测试，是本项目在长类名、大量白盒测试环境下更稳妥的迁移顺序。

## Java 证据检查

第一层证据是编译。主代码编译通过，说明两个根 Controller 能注入新包 Service，ProfileSection
能读取新 Response，ReviewPreflight 能访问公开 endpoint，迁出 Service 能访问新的 RoutePaths。
第二层证据是测试编译，它覆盖根测试、迁移后的包内白盒测试以及跨家族测试辅助类。任何遗漏导入、
错误可见性或旧 FQN 都会在这里直接失败。

第三层证据是路由一致性测试。原测试继续验证九个 Service endpoint 等于根 `BASE_PATH` 加根
后缀；新增断言验证根后缀又等于新 RoutePaths 所有者的后缀。于是形成“Service 到根聚合器、
根聚合器到叶子所有者”的双层等式。未来有人修改其中任意一层而忘记同步，测试会立即指出路由
事实发生分裂。

第四层证据是
`ReadabilityUpkeepOpsConsolidationExtractionV1819Tests`。它检查说明文档被 ops 索引引用，
代表性 Service、Response、Support 和 GuardCatalog 已在窄包，旧根位置不再存在，两个
Controller 仍在根包，旧 GateCatalog 不再留在根目录，根包直接 Java 文件不超过 977，整体
`ops` Java 文件不超过 1,352。它保护的是维护目标，而不只是某个业务返回值。

第五层证据是三组历史闸门同步下降：治理计划的 `MAX_ROOT_OPS_MAIN_JAVA_FILES`、质量收尾测试的
`EXPECTED_ROOT_OPS_MAIN_JAVA_FILES`，以及 v1809 中记录当前精确值的断言都由 993 调整为 977。
只改其中一处会让全量测试失败，避免数字在不同文档和测试中漂移。Spotless、JaCoCo、SpotBugs
和完整 `mvnw verify` 则负责格式、覆盖率和静态分析的最终闭环。

## mini-kv 证据检查

mini-kv 在 v1819 中没有输入，也没有输出。本版没有修改 C++ 仓库，没有启动它的服务，没有读取
它的 WAL、快照或分片状态，也没有要求它新增命令、路由或证据文件。Java 此处处理的是自己的包
所有权和只读 endpoint 依赖，属于四项目规则允许并行推进的内部维护工作。

不触碰 mini-kv 是有意的边界控制。类名包含 shard readiness、operator evidence 和 signed
approval，并不意味着每次 Java 拆包都需要底层存储配合。只有契约字段、证据 schema、跨项目
路径或真实运行流程变化时，才需要按 mini-kv 到 Java 再到 Node 的顺序协同。本版所有 URL 和
响应组件都保持不变，因此没有上游契约变化。

历史 `e/<version>/` 归档也保持原位。Node 中存在对 Java 和 mini-kv 历史路径的精确字符串与
摘要引用，删除 remote 或移动 Java 类并不构成改名归档的理由。文档明确保留“Do not rename
or move archive roots”规则，防止维护者误把源码包整理扩展成证据目录搬家。

因此本版对 mini-kv 的检查结论很朴素：没有新依赖，没有进程，没有文件改动，没有运行时请求。
这并不是缺少工作，而是证明变更范围被控制在真正需要处理的 Java 模块里。后期工程质量的一部分
就是知道哪些系统不该被拉进当前改动。

## 阻断与安全边界

v1819 没有开放 write routing、active shard router、credential value、raw endpoint、
managed audit connection、deployment 或 rollback。Service 仍然只读，Response 继续把
executionAllowed、approval grant、runtime payload 和 sibling mutation 保持关闭。迁包
不会改变这些业务事实，新的 public endpoint 也只是一组不可变字符串。

文本包接收目录不包含真实文本、附件、签名、密钥、操作员值或审批意见。FieldCatalog 只列字段
名称和来源，SourceEvidence 只列来源引用，OperatorValueHandle 只描述脱敏句柄要求，
SignatureEnvelope 只描述元数据边界。任何未来实现如果试图在这里读取原始值，都应被视为越过
本阶段职责，而不是顺着现有代码自然扩展。

Gate 中持续声明：文本尚未被接受，分离签名尚未被接受，审批授权关闭，值导入锁定，运行时载荷
锁定，Java 和 mini-kv 启动不在范围内，兄弟仓库变更被阻断。Guard 则为缺失身份、摘要、来源、
策略和锁状态提供 fail-closed 拒绝码。拆分后这两层仍由测试分别检查，安全语义没有因为文件
合并而模糊。

如果需要回滚 v1819，回滚范围也清楚：恢复旧包声明和导入、删除叶子 RoutePaths、让根聚合器
重新持有字符串、恢复独立 GateCatalog、把三组计数闸门恢复为 993，并恢复 SpotBugs 旧 FQN。
不需要迁移数据库、不需要重放消息、不需要回滚部署，因为本版没有产生运行时状态变化。

## 测试覆盖

包内测试覆盖 Catalog、四个 Foundation Service、五个 Assurance Service、FieldCatalog、
Guard、Gate 和 Support。迁移这些测试的原因是它们验证 package-private 内部协作，留在根包
就会迫使内部目录公开化。Controller 测试和 RoutePaths 测试继续留在根包，因为它们验证的是
公共入口和根兼容层。测试位置跟随被测职责，而不是为了目录整齐全部搬走。

下游测试覆盖 ReviewPreflight 对 intake endpoint 的引用，以及 ProfileSection 对 intake
catalog response 的组合。它们能发现“主家族自己测试都绿，但下游综合视图断链”的问题。
SpotBugs 排除配置的双 FQN 更新会在全量静态分析中验证；如果漏改一处，含 List 的 Response
可能重新触发现有告警，或者旧排除成为无效配置。

Focused 测试会优先运行本家族、下游 ReviewPreflight、ProfileSection、路由和 readability
治理测试，以较短反馈周期发现结构错误。随后执行 Spotless 检查，确保长类名导入和换行符合
Google Java Format。最后执行完整 `mvnw verify`，让全部测试、JaCoCo 包级阈值和 SpotBugs
一起验证，不能用局部测试替代最终门禁。

讲解本身也被 `OpsCodeWalkthroughArchiveComplianceTests` 检查。文件名包含 version-1819，
正文使用规定章节，中文汉字数不少于三千，并明确写出实际工作量、禁止硬凑和本项目边界。这样
代码解释不是提交后的随手摘要，而是可被自动审计的正式维护产物。

## 实际工作量说明

本版实际处理了十五个实现文件迁移、六个白盒测试迁移、一个旧 GateCatalog 删除、一个新
RoutePaths 所有者新增、两个根 Controller 导入修复、两个 ReviewPreflight criteria 目录
导入修复、ProfileSection 服务与来源目录修复、多个根测试与测试辅助类修复、九个 endpoint
公开、九个根路由委托、两处 SpotBugs FQN 迁移、三组计数闸门下降、新增治理测试、ops 文档、
索引、进度表、changelog 和本篇中文讲解。它不是把文件批量改个 package 就结束。

工作量的核心在依赖闭环，而不是文件数量。每个搬走的类型都可能被 Controller、同包白盒测试、
下游 criteria、综合 ProfileSection 或静态分析配置引用；必须逐层确认哪一类应该公开，哪一类
应该留在包内，哪一类测试应该跟随实现，哪一类入口应该留在根包。公开过多会扩大 API，公开不足
会编译失败，二者都不是高质量拆分。

“至少三千字”不是让讲解重复同一句话。禁止硬凑意味着文字必须对应真实设计：为什么 TextPackage
Intake 不是真实上传、为什么路由要有叶子所有者、为什么 Gate 可以与 Guard 合并、为什么
Controller 留根、为什么下游 endpoint 要公开、为什么 mini-kv 不参与、为什么总文件数不能
增长、为什么需要三组 ratchet。只有真实工作足够厚，解释才能自然达到深度。

本项目后期保养的目标也不是追求每版看起来很大，而是持续降低维护者定位成本，同时不破坏旧契约。
v1819 把根包从 993 压到 977，整体文件数不增，线性链条继续可拆，下游已经为 v1820 做好边界。
这些可测量结果比“重构完成”四个字更有价值。

## 后续维护怎么读这一刀

复查时先看新的 RoutePaths，确认九个 suffix 与历史字符串一致；再看两个根 Controller，确认
它们只做请求映射和 Service 委托；随后进入新包，按 CatalogService、FieldCatalog、
GuardCatalog、Support、Response 的顺序阅读。这样能从入口、字段来源、阻断规则一路看到最终
响应，不必先理解所有超长类名。

然后看两个 ReviewPreflight criteria catalog，确认它们只读取 public endpoint，而没有依赖
Intake 的 package-private 目录；再看 ProfileSection，确认综合视图仍然消费 Intake catalog。
最后看 v1819 extraction test、RoutePaths test 和三组 count ratchet，确认目录和数字都被
自动保护。这个阅读顺序比从完整 diff 开始更容易抓住设计意图。

下一刀自然是 `TextPackageReviewPreflight`。它的出站依赖已经全部指向 v1819 新包的 public
endpoint；迁出时需要处理自己的 RoutePaths、Controller 导入、ProfileSection 读边以及可能的
内部目录合并。若编译器显示额外读者，应以实际调用边为准，不应只依赖文件名前缀猜测。

维护者还应继续遵守两个停止条件：找不到合理合并点时，不为维持总量而制造巨型文件；发现契约、
响应 schema 或归档路径需要变化时，停止把它当作纯内部拆分，重新进入跨项目协同评估。自由推进
并不等于无边界推进，好的自主性来自边界清楚。

## 一句话总结

v1819 在不改 URL、不改响应结构、不接收真实文本包、不打开写入或运行能力、不触碰历史归档的
前提下，把签批草稿文本包接收注册表迁入专属维护包，将根 `ops` 直接 Java 文件数由 993 降到
977，并为下一站 `TextPackageReviewPreflight` 建好了公开、只读、单向的 endpoint 边界。
