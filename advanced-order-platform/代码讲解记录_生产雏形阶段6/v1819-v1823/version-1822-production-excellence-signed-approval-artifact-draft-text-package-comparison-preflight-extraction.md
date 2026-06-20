# v1822 生产卓越拆分讲解：草稿文本包比较预检注册表迁出

## 入口路由

v1822 处理 `TextPackageComparisonPreflight`。它承接 v1821 的提交预检，但仍然不是“执行一次
比较”的接口。提交预检回答未来材料必须带哪些证据槽位，比较预检则把这些槽位翻译成一组离线
比较车道：身份与请求怎么对齐、摘要和签名元数据怎么对齐、来源证据与策略怎么对齐、执行关闭
状态怎么对齐。这里的关键词是“定义怎么比”，不是“现在开始比”。

对外共有五条 GET 路由。catalog 返回完整比较预检目录；identity-request 返回身份和请求范围；
digest-signature 返回摘要绑定与分离签名元数据范围；evidence-value-policy 返回来源证据句柄、
操作员值句柄和策略范围；execution-closeout 返回执行锁、批准隔离与归档收尾范围。所有路由
继续挂在 `/api/v1/ops/shard-readiness` 下，路径字节没有变化。

根包中的 Controller 保持原位。它仍由 Spring 扫描，仍使用根 `OpsShardReadinessRoutePaths`
上的历史常量进行映射，仍返回同一个 Response。迁移的是 Controller 后面的 Service、Response、
Support 和目录实现。这样调用者不需要知道 Java 包结构发生变化，维护者却可以从新的窄包直接
定位整个比较预检模块。

本版新增
`OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparisonPreflightRoutePaths`。
它成为五个后缀的唯一事实来源。根路由聚合器只做委托，迁出的五个 Service 直接引用叶子所有者。
这与前几版形成一致规则：Controller 兼容入口留根，业务阶段拥有自己的路由叶子，根聚合器不再
继续累积重复字符串。

一次请求的入口链很短：客户端访问旧 URL，根 Controller 调用新包 Service，Service 选择
ComparisonLane 切片，CatalogService 与 Support 补齐 AcceptanceControl、ComparisonGate、
状态和 checks，最后返回不可变 record。入口链没有文件上传、请求体解析、数据库查询、外部网络
访问或进程启动。

## 响应模型

`ComparisonPreflightResponse` 是一份只读计划。它记录项目、版本、只读标志、执行许可、来源计划、
上游 Node 提交预检版本、Java 提交预检版本、Java Closeout 版本、预检状态、材料状态、比较状态、
文本解析状态、签名解析状态、批准状态、值导入状态、运行时状态和兄弟仓库变更状态。

这些状态的组合刻意保守：预检目录可以 ready，但 submitted package 仍是 not-accepted，
comparison 仍是 not-performed，draft text 和 detached signature 仍是 not-parsed，approval
仍是 not-emitted，value import、runtime 和 sibling mutation 仍是 locked。也就是说，系统可以
知道将来该检查什么，却没有获得执行这些动作的权限。

响应中的第一类列表是 `ComparisonLane`。本家族共有二十五条车道。FoundationLaneCatalog
描述身份、请求和来源基础关系；DigestSignatureLaneCatalog 描述摘要绑定、摘要算法、来源句柄
与签名信封；AssuranceLaneCatalog 描述证据句柄、脱敏值、策略、审查状态、执行锁和归档状态。
Lane 包含代码、版本范围、比较对象、比较问题、接受控制和来源 endpoint。

第二类列表是 `AcceptanceControl`。它不是批准结果，而是每条 Lane 的 fail-closed 配套控制。
AcceptanceControlCatalog 根据 Lane 动态生成同数量控制：如果某条车道缺失、尚未比较或结果
不可接受，就拒绝未来材料。这样目录新增一条 Lane 时不会忘记添加对应控制，二十五条 Lane 与
二十五条 Control 的数量关系由代码和测试共同保护。

第三类列表是 `ComparisonGate`。十个 Gate 继续关闭包接受、文本解析、签名解析、批准授予、
证据导入、操作员值导入、运行时载荷、写路由、兄弟仓库变更，并要求未比较或不可接受的材料
保持 fail-closed。Lane 说明检查维度，Control 说明失败行为，Gate 说明整个阶段仍不具备哪些
能力，三层职责不能互换。

Support 使用 `List.copyOf` 固化列表，统计通过的 Lane 与 Control，添加来源版本和安全边界
检查项，然后创建 Response。本版没有改变 record 组件、顺序、计数算法、状态字符串、版本号、
profile 或 checks，只改变源码所有权和公共引用位置。

## 上游证据配置

直接上游是 v1821 `TextPackageSubmissionPreflight`。FoundationLaneCatalog 读取提交身份 Service
和 Closeout handoff ledger 的 endpoint；DigestSignatureLaneCatalog 读取摘要签名 Service；
AssuranceLaneCatalog 读取证据值 Service 和执行收尾相关 endpoint。它们消费的是公开、不可变的
地址，不是上游内部 Slot、Control 或 Gate 对象。

举例来说，提交预检声明一个“身份信封槽位”，比较预检据此创建“身份主体比较车道”。Lane 的
问题是未来材料中的身份是否与提交预检句柄一致，来源 endpoint 指向 v1821 的身份视图。如果
车道证据缺失，配套 AcceptanceControl 要求拒绝；即使车道存在，Gate 仍阻止系统接受真实材料。
输入、转换和输出因此很透明。

摘要与签名也是同样机制。v1821 只暴露摘要和签名元数据槽位，v1822 将它们转换成摘要 pin、
算法声明、来源句柄和签名信封比较车道。没有任何 Service 接收草稿文本字节，没有调用摘要算法，
没有读取分离签名载荷。这里保存的是“未来比较规则”和“证据地址”，不是比较结果。

来源证据与策略车道只处理脱敏句柄、策略声明、审查状态和锁状态。操作员值仍是 handle-only，
凭据值与 raw endpoint 都不进入 Response。ExecutionCloseoutService 只列出执行锁、批准隔离、
兄弟仓库变更锁和归档收尾车道，不会启动 Java、mini-kv 或 Node。

上游迁移顺序很重要。v1821 已先把五个主 Service 和 Closeout handoff ledger 变成明确公共读边，
所以 v1822 不需要同时修改提交预检内部可见性。每版只处理一条已经准备好的依赖链，能够让编译器
准确暴露当前入边，而不是在一个大提交中混合多阶段错误。

## 服务层核心流程

CatalogService 是完整入口。它从 LaneCatalog 取得全部二十五 Lane，再调用自己的 response
辅助方法。该方法把 Lane 交给 AcceptanceControlCatalog 生成二十五 Control，并取得十个 Gate，
最后交给 Support 统一组装。CatalogService 不持有仓库、不访问外部系统，只协调静态目录。

IdentityRequestService 选择基础车道前段，描述请求标识、身份主体和身份信封等问题。
DigestSignatureService 选择摘要与签名切片。EvidenceValuePolicyService 选择来源证据、值句柄和
策略切片。ExecutionCloseoutService 选择执行锁、批准隔离与归档切片。每个 Service 都保留
`@Transactional(readOnly = true)`，并复用 CatalogService 的 response 逻辑。

LaneCatalog 负责把 Foundation、DigestSignature 和 Assurance 三组列表按既有顺序拼接。
各子目录只负责自己领域的静态条目，Support 只负责构造 record。这里没有把所有条目塞进一个
巨型类，也没有让 Service 自己复制列表构建逻辑。迁包后，维护者可以在一个目录内沿
Service、LaneCatalog、AcceptanceControlCatalog、Support、Response 阅读完整流程。

本版把 GateCatalog 合并进 AcceptanceControlCatalog。两者都属于“比较结果尚不可信时如何
fail-closed”的控制层，都由 CatalogService 和同一个 CatalogTests 消费，生命周期一致。
合并后 `controlsFor()` 与 `allGates()` 仍是独立方法，AcceptanceControl 和 ComparisonGate
仍是独立 record，没有消除概念边界。

合并的工程原因也很具体：新增 RoutePaths 会增加一个 Java 文件。若原样迁移十三个实现，再增加
路由所有者，总 `ops` 文件数会从 1,352 增至 1,353。将职责相邻的 Gate 与 Control 合并后，
新包有十二个实现文件，加一个路由文件，根包保留一个 Controller，总数仍是 1,352。文件预算
没有靠删除测试或压缩无关逻辑达成。

## 下游读取与最小公共面

第一类下游是 `ComparisonAcceptancePrecheck`。它的 CheckpointCatalog 读取五个 Service
endpoint，把比较预检目录转成十个接受前检查点。身份、摘要、签名、来源证据、值句柄、策略、
执行锁、批准隔离和归档状态都能追溯到 v1822，但 AcceptancePrecheck 仍不会接受材料。

第二类下游是 `ComparedPackageEvidenceIntake`。ComparisonIdentitySlotCatalog 读取 Catalog
和 IdentityRequest endpoint；DigestSignatureSlotCatalog 读取 DigestSignature endpoint；
AssuranceSlotCatalog 读取 EvidenceValuePolicy 与 ExecutionCloseout endpoint。它们只是为未来
比较结果列出证据槽位，不执行比较或导入证据。

第三类下游是 `SignedApprovalDraftTextPackageProfileSection`。RegistryService 构造
ComparisonPreflight CatalogService，SourceCatalog 持有 ComparisonPreflightResponse，
从而把 Intake、Review、Submission、Comparison 等阶段组合成统一只读视图。本版同步修改了
生产代码与测试支持中的 import，综合视图的顺序和响应内容没有变化。

公共面只包含五个 Service 类的 `public static final ENDPOINT` 和公开 Response 类型。LaneCatalog、
AcceptanceControlCatalog、Support 仍是 package-private。下游不能越过 Service 去依赖内部列表，
未来调整 Lane 的组织方式时不会扩大兼容负担。

第一次主编译准确列出了这些下游：保留 Controller、ProfileSection、三种 EvidenceIntake
目录和 AcceptancePrecheck。补齐显式 import 后编译通过，没有出现其他隐藏读边。这说明
CodeGraph 与编译器共同得到的边界是完整的。

## Java 证据检查

文件层面，原根族有十四个主文件。一个 Controller 保留，十三个非 Controller 文件离开根包。
GateCatalog 合并后，新窄包实际有十二个实现文件；新增一个 RoutePaths 文件。因此根包直接
Java 文件从 932 降至 919，递归总数保持 1,352。

三处长期预算同步改为 919：治理计划的最大根包数、v1806 质量收尾的精确根包数、v1809 历史
提取测试中的当前实测数。新增 `ReadabilityUpkeepOpsConsolidationExtractionV1822Tests`
检查文档索引、代表性文件新位置、Controller 旧位置、GateCatalog 消失、根包上限和总量上限。

路由层面，五条 Service endpoint 改为由新叶子 RoutePaths 组合。根聚合器委托同一组常量，
RoutePathsTests 继续比较 Service endpoint 和历史根常量。任何路径拼写、斜杠或后缀变化都会
触发测试，而不是仅靠人工阅读确认。

类型层面，ComparisonPreflightResponse 在 SpotBugs 中有两处既有列表暴露基线。本版把两处
旧根包 FQN 改为新包 FQN，没有增加排除数量。若遗漏其中一处，全量 SpotBugs 会暴露出基线失配，
因此静态分析配置也参与验证迁包完整性。

测试编译进一步发现根 ControllerTests、RoutePathsTests、ProfileSectionRegistryTestSupport
需要显式 import，迁入 CatalogTests 需要把旧 GateCatalog 调用改为合并后的
AcceptanceControlCatalog。修复后 test-compile 通过，白盒测试和兼容测试的归属各自清楚。

## mini-kv 证据检查

mini-kv 不参与本版运行。ComparisonPreflight 只定义离线比较车道，不读取分片表、WAL、快照、
RESP 服务或 C++ 运行时状态。源码中的 mini-kv 只出现在“不能启动、不能变更兄弟系统”的 Gate
描述中，不是连接配置。

本版也没有生成新的跨项目 schema。v1821 endpoint 是 Java 内部不可变地址，v1822 输出仍是
Java 只读 Response。Node 不需要同步消费新字段，mini-kv 不需要提供新证据，四项目之间没有
契约变更。

若未来真正执行比较并需要读取 mini-kv 分片数据，那将是新的功能阶段，需要定义认证、超时、
一致性、重试、审计和失败回滚，不能偷偷塞进当前 Service。当前目录只允许计划和边界描述，
测试会持续确认 executionAllowed 为 false。

历史归档继续遵守 Do not rename or move archive roots。源码从根包迁入窄包，不代表可以整理
`e/<version>/`、证据 JSON、截图或 handoff 文件。Node 可能按绝对路径和摘要引用这些历史资产，
因此本版没有触碰任何兄弟仓库或归档布局。

## 阻断与安全边界

ComparisonPreflight 最容易被误解为“比较接口”，所以阻断必须明确。Response 中
readyForComparisonPreflight 可以为真，但 readyForSubmittedPackageAcceptance、
readyForDraftTextParsing、readyForDetachedSignatureParsing、readyForApprovalGrant、
readyForEvidenceImport、readyForOperatorValueImport、readyForRuntimePayload 全部为假。

十个 Gate 再次声明：不接受包、不解析文本、不解析签名、不发批准、不导入证据、不导入操作员值、
不生成运行时载荷、不打开写路由、不修改 Java/Node/mini-kv，未比较或不可接受材料必须失败关闭。
这些 Gate 在合并文件后仍原样存在，CatalogTests 继续验证数量和状态。

代码中没有 POST、PUT、PATCH、DELETE，没有 Repository.save，没有消息发布，没有 ProcessBuilder，
没有文件写入，没有凭据读取，也没有 raw endpoint 解析。五个 Controller 方法都是 GET 委托，
五个 Service 都是 readOnly 事务。

包迁移不改变安全能力。把 ENDPOINT 设为 public 只是允许下游引用证据地址，不会暴露 Service
内部目录，更不会把地址变成执行令牌。公开字符串和开放能力是两回事，本版只做前者。

回滚也保持简单：恢复包声明、导入、路由委托、Gate 独立文件、SpotBugs FQN 和计数钉即可，
没有数据迁移、消息补偿或外部状态清理。这种可逆性是内部治理版本的重要质量指标。

## 测试覆盖

CatalogTests、ServiceTests 和 SupportTests 随实现进入新包，因为它们需要访问 package-private
Lane、Control 和 Support。ControllerTests 与 RoutePathsTests 留根，从外部兼容视角构造公开
Service 并验证旧入口。测试位置直接表达了白盒边界和公共边界。

聚焦测试应覆盖本家族五条 Service、Controller、路由、Lane/Control/Gate、AcceptancePrecheck、
ComparedPackageEvidenceIntake、ProfileSection、readability 和 walkthrough compliance。
这样既验证自身，又验证三类真实下游。

全量 `mvnw verify` 还会运行所有 Spring 集成测试、打包、JaCoCo、SpotBugs 与 Spotless。
包迁移可能影响包级覆盖率或静态分析 FQN，因此只跑本家族测试不够。完整门通过后才允许提交、
打 tag 和推送。

讲解文件由 `OpsCodeWalkthroughArchiveComplianceTests` 自动检查。它必须包含标准章节、中文主体、
至少三千个汉字、实际工作量说明、“禁止硬凑”和“本项目”。讲解不是装饰，而是后续维护者理解
输入、输出和安全边界的版本化证据。

Windows 长路径也是验证内容。迁移目标目录与超长类名组合会超过传统 260 字符限制，第一次普通
移动没有成功且没有丢失文件；随后使用 `\\?\` 长路径 API 完成迁移，并通过源/目标数量核对、
Maven 编译和 Git 状态确认。这个过程被真实工具链检验，而不是假设文件已经移动。

## 实际工作量说明

本版处理十四个主族文件和五个测试文件。十三个非 Controller 实现离开根包，三个白盒测试随包
移动；新增五路由叶子所有者；GateCatalog 合入 AcceptanceControlCatalog；一个 Controller、
两个兼容测试继续留根。

生产依赖修复覆盖 Controller 的六个类型、AcceptancePrecheck 的五个 Service endpoint、
EvidenceIntake 三个目录中的五类 endpoint、ProfileSection 的 CatalogService 和 Response。
测试依赖修复覆盖 ControllerTests、RoutePathsTests、ProfileSection TestSupport 和合并后的
Gate 测试入口。

治理证据包括两处 SpotBugs FQN、三处根包预算、一个五项结构测试、ops 说明、README 索引、
CHANGELOG、进度表和本篇中文讲解。后续还要执行 Spotless、聚焦测试、全量 verify、提交、tag、
branch/tag 推送和远端 CI 观察。

这里的工作量来自真实模块边界，不是为了满足篇幅而重复概念。规则中的“禁止硬凑”在本项目中
意味着：解释必须对应实际代码、依赖、失败模式和验证；如果工程范围只能支撑几句话，就不应冒充
中大版本。v1822 能形成完整讲解，是因为它同时处理路由所有权、包所有权、上下游边界、文件预算、
静态分析和测试归属。

本版也保持克制：没有顺带迁移 AcceptancePrecheck 或 EvidenceIntake，没有修改比较规则和版本号，
没有触碰用户的 `docs/project-explanation/`，没有修改 Node 或 mini-kv，没有移动历史归档。
工作量加厚不等于范围失控，而是把一个明确模块从分析、实现到证据闭环做完整。

从维护者视角再复盘一次，本版最重要的产出不是目录变短，而是阅读顺序终于稳定。以后定位比较
预检问题，可以先看保留在根包的入口，确认请求落在哪条只读路由；再进入窄包查看对应服务选择了
哪些车道；随后查看接受控制如何为每条车道生成失败关闭规则；最后查看统一支持类如何组装状态和
检查项。若问题发生在下一阶段，则沿公开证据地址进入接受前检查或比较证据接收，不需要反向搜索
九百多个根文件。这样的路径能缩短故障定位时间，也能防止维护者误把预检目录当成真实比较引擎。
它还明确了未来扩展方式：新增比较维度时先补车道，再由接受控制自动生成拒绝规则，最后补测试；
若需要真实读取材料，则必须另建执行模块和安全设计，不能污染当前只读模块。

## 一句话总结

v1822 在五条路由、响应模型、二十五条 Lane、二十五条 AcceptanceControl 和十个关闭 Gate
全部保持兼容的前提下，将比较预检实现迁入独立维护包，使根 `ops` 从 932 降到 919、总量保持
1,352，并为下一阶段 ComparisonAcceptancePrecheck 提供了最小、公开、已编译验证的只读边界。
