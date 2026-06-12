# 第一千七百七十四版代码讲解：中文长篇讲解深度门禁基础

本版目标是把“代码讲解不能再写成几段短收据”这件事落到 Java 仓库的可见实现里。此前 v1769-v1773 已经把 `f/` 截图解释归档做成可机器检查的布局清单，但对应的代码讲解本身仍然偏短，更多是在说明“做了什么”，没有充分解释“为什么这样拆、入口在哪里、模型如何证明边界、测试如何防止回退”。v1774 因此不是继续推进截图归档功能，而是建立一个新的只读 registry：`code-walkthrough-depth-registry`。它的职责是描述从 v1774 开始所有新讲解必须遵守的中文长篇标准，包括三千汉字门槛、中文主体、每版一篇、实现面证据、边界证据和测试证据。

它仍然不会启动 Java 服务，不会启动 mini-kv，不会读取 credential value，不会解析 raw endpoint URL，不会向 managed audit 发 HTTP 或 TCP 请求，也不会把文档治理包装成生产就绪能力。它只是把讲解质量债变成可测试、可查询、可复盘的 Java 侧证据。

## 入口路由

本版新增的入口从路由常量开始，而不是直接写控制器。新增 `OpsShardReadinessCodeWalkthroughDepthRoutePaths`，其中声明 `CODE_WALKTHROUGH_DEPTH_REGISTRY = "/code-walkthrough-depth-registry"`。随后在共享的 `OpsShardReadinessRoutePaths` 中增加 `CODE_WALKTHROUGH_DEPTH_REGISTRY` 委托，让这个路径和其他 ops shard-readiness 只读证据接口一样，经由统一的 `BASE_PATH` 暴露。这样做的意义是避免把新接口做成孤立路径：后续控制器、测试、文档都可以使用共享常量，路径变化时只有一个源头。

为什么 v1774 只先建立路由基础，而不是一次把全部服务逻辑堆完？因为这次用户指出的是“工作量和讲解内容都不够”，不是要求一个更大的单文件。把路由、模型、目录、服务、测试分层推进，可以让每个版本都有可解释的工程边界：v1774 负责定义“这条证据线是什么”，v1775 负责“怎么渲染和对外读”，v1776 负责“怎么用测试强制执行”，v1777 负责“怎么写进长期文档”，v1778 负责“怎么收尾验证”。这也是避免巨型文件的维护策略。

路由本身不会执行任何业务动作。它不承接 POST，不接受操作命令，不引入审批执行，也不和订单写路径连接。它只为一个只读 registry 准备路径，和既有 `code-walkthrough-quality-gate-registry`、`code-walkthrough-quality-audit-registry` 保持同类形态。后续如果有人问“为什么讲解要求突然变严”，可以从这个路由找到 Java 侧证据，而不是翻聊天记录。

## 响应模型

本版新增 `OpsShardReadinessCodeWalkthroughDepthRegistryResponse`，它是一个 Java record，用来承载讲解深度门禁的完整响应。顶层字段包括 `project`、`version`、`readOnly`、`executionAllowed`、`startsJavaService`、`startsMiniKvService`、`readsCredentialValue`、`resolvesRawEndpointUrl`、`managedAuditHttpAllowed`、`endpoint`、`profile`、`sourcePlan`、`priorQualityGate`、`registryState`、`effectiveFromVersion`、`minimumChineseCharacterCount` 以及各类规则计数。这样的字段设计不是为了形式主义，而是让讲解质量门禁本身也能说清楚安全边界。

其中最关键的两个字段是 `effectiveFromVersion=1774` 和 `minimumChineseCharacterCount=3000`。前者说明规则从哪个 Java 版本开始生效，避免回头要求历史遗留文件全部重写；后者直接把用户要求的“三千字”转成可检测门槛。响应里还保留了 `priorQualityGate`，指向已有的 `code-walkthrough-quality-gate-registry`，说明本版不是推翻旧规则，而是在旧规则之上加一层深度约束。旧规则要求结构完整、证据可找；新规则要求中文主体、篇幅足够、解释密度够。

模型内部继续拆分成多个子 record：`DepthRule` 记录篇幅和解释深度；`LanguageRule` 记录中文默认和拒绝信号；`EvidenceRule` 记录维护者必须能从讲解里找到哪些证据；`BoundaryRule` 记录禁止动作；`VerificationStep` 记录验证入口；`MarkdownSection` 记录渲染后的可读摘要。这种拆分让后续扩展不会把所有内容塞进一个列表，也让测试可以针对不同维度写断言。

本版的响应模型还专门保留了 `deniedBoundaryRuleCount`。这和运行时证据接口的风格一致：不仅说“我不会做危险动作”，还把所有禁止动作逐项列出，并通过计数确认每一条都是 denied。讲解门禁虽然是文档治理，但它靠近 CI、版本发布和证据归档，因此必须明确不会启动服务、不会读取密钥、不会解析真实端点、不会部署回滚。

## 上游证据配置

上游证据仍然来自 Node v367 计划。这个计划已经说明：Node v367 完成了真实最小只读 gate execution，下一步优先属于 Node v368 的归档验证，并不要求 Java 或 mini-kv 产生新运行时功能。因此 v1774 的定位很清楚：它不是补 Java read contract，不是修 mini-kv 协议，也不是参与真实读窗口。它是 Java 仓库自己的讲解质量治理。

为什么还要在响应里写 `sourcePlan = "Node v367 / Java v1774-v1778"`？因为 Java 项目的长期推进一直受 Node 计划约束，尤其要防止在 Node 明确“不要求 Java 新版本”的情况下强行打开不必要的运行时集成。本版把 Node v367 作为边界来源，用来说明我们知道当前跨仓库状态，也知道本批次不该越界。这样后续审计时不会误解为 Java 自己发明了一个新集成阶段。

同时，本版的上游证据配置并不读取 Node 工作区之外的 runtime artifacts，也不消费 Node v367 的 JSON、Markdown、summary、screenshot 或 walkthrough。那些是 Node v368 应做的归档验证任务，不属于 Java v1774。Java 这里仅消费计划文字中的边界判断：当前不读取 credential value、不解析 raw endpoint、不实例化 provider/client、不发 managed audit HTTP/TCP、不启动上游、不写 ledger/schema/SQL。

这样的边界选择很重要。如果为了“加大工作量”就打开真实 Java 服务、连接 mini-kv 或读 secret handle，反而违背当前项目节奏。真正的工作量应该体现在实现质量和维护约束上：把规则设计成模型，把规则拆成 catalog，把规则接进测试，把讲解写到足够让后来者理解，而不是用运行时动作制造复杂度。

## 服务层核心流程

v1774 还没有引入 service 的完整装配，但已经定义了后续服务需要消费的目录结构。`OpsShardReadinessCodeWalkthroughDepthRuleCatalog` 负责三类规则：深度规则、语言规则和证据规则。深度规则包含 `minimum-3000-chinese-characters`、`one-version-one-walkthrough`、`implementation-surface-required`、`boundary-proof-required`；语言规则包含 `chinese-default`、`section-headings-preserved`、`not-release-receipt`；证据规则包含 `route-model-service-test-chain`、`upstream-plan-boundary`、`safety-denials`、`verification-commands`。

这些 catalog 的设计意图是把“写长一点”拆成可维护的工程要求。三千汉字只是底线，如果内容仍然只有空话，维护价值仍然不足。因此 `implementation-surface-required` 要求讲解必须说清 route、model、service、catalog、test 文件；`boundary-proof-required` 要求说明读写边界、credential 边界、raw endpoint 边界、managed audit 边界、deployment/rollback 边界和 autostart 边界；`verification-commands` 要求讲清楚定向测试、全量 Maven、CI 和 cleanup gate。

`OpsShardReadinessCodeWalkthroughDepthBoundaryCatalog` 独立出来，是为了避免把安全边界混在语言规则里。它列出八个 denied 行为：不启 write routing、不启 active shard router、不读 credential value、不解析 raw endpoint URL、不发 managed audit HTTP、不部署或回滚、不自动启动 Java、不自动启动 mini-kv。后续 support 会统计 denied count，确保所有边界都是显式禁止。

`OpsShardReadinessCodeWalkthroughDepthVerificationCatalog` 记录验证步骤，包括 route tests、service tests、boundary tests、walkthrough archive compliance tests 和 full Maven regression。这个目录让后续测试不是靠记忆运行，而是通过 registry 自身说明“应该怎么证明这批规则有效”。它也为 v1775 的 renderer 和 service 装配做好准备。

## Java 证据检查

Java 侧证据首先来自新增文件本身。路由类说明入口名，响应 record 说明字段契约，规则 catalog 说明门槛，边界 catalog 说明禁止动作，验证 catalog 说明测试路径。它们都放在 `com.codexdemo.orderplatform.ops` 包内，和已有 shard-readiness 证据接口保持一致，没有引入新的模块边界，也没有把文档规则写进订单业务服务。

第二类证据来自共享路由表的更新。`OpsShardReadinessRoutePaths` 增加 `CODE_WALKTHROUGH_DEPTH_REGISTRY`，这意味着后续控制器不会硬编码路径，而是走同一套 ops route 常量。这个改动很小，但维护价值很高：如果未来 API base path 或 registry path 需要调整，测试可以在共享路径层及时暴露问题。

第三类证据来自即将新增的测试。v1774 定义了规则，v1775 会让服务返回规则，v1776 会把合规测试接上三千汉字和中文主体门槛。这样的推进顺序保证每一层都有证据：不是先写五篇长文然后口头承诺以后继续，而是让 Java 测试可以扫描讲解目录，发现 v1774 以后任何短讲解或英文主体讲解。

本版也保留一个清晰限制：v1774 不修改历史讲解文件，不试图重写 v1769-v1773 的 tag，也不移动已有目录。已经发布的短讲解可以通过新批次解释和制度修正来弥补，但不应该篡改已推送 tag。这个处理更符合版本化证据链：承认问题，向前建立强约束，用新版本记录修正。

## mini-kv 证据检查

本版不消费 mini-kv 证据。原因不是忽略 mini-kv，而是这次工作对象是 Java 仓库内的代码讲解质量门禁，和 mini-kv 的 HEALTH、INFOJSON、STATSJSON、写命令、压缩命令或恢复命令都没有直接关系。强行把 mini-kv 纳入本版，只会制造虚假的跨系统复杂度。

从 Node v367 计划看，mini-kv 当前仍然只在真实读窗口中作为上游被 Node 消费，Java 这边没有新的协议修复任务。讲解门禁也不会要求 mini-kv 启动，不会验证端口，不会写入 key，不会读取生产状态。`startsMiniKvService=false` 和 `no-minikv-autostart` 会在响应与边界规则中持续出现。

这部分仍然要在讲解里写出来，是为了防止“文档治理”被误解成“跨项目集成”。一个合格讲解不仅要说做了什么，也要说没有做什么以及为什么没有做。尤其在 Java、Node、mini-kv 长期协作的项目里，很多风险来自边界模糊：本来只是 README 或测试规则，最后被描述成运行时能力。v1774 明确拒绝这种误读。

后续如果 Node v368 或 v369 发现 Java/mini-kv 只读契约不匹配，那会另起功能版本处理。那类版本需要真实读取窗口、fixture、可能的端口和接口证据；而本版只处理代码讲解质量，不应该提前打开那条线。

## 阻断与安全边界

本版最重要的安全边界是“质量门禁不等于执行门禁”。它可以阻止短讲解进入标准档案，但不能触发订单写入、审计连接、部署回滚或 runtime shell。`executionAllowed=false` 是响应模型中的硬字段；`readOnly=true` 说明这个 registry 只提供证据，不接收执行命令。

credential 边界也必须明确。讲解里可以提到 credential handle、credential value、secret provider、resolver client 这些概念，但本版不会读取、打印、解析或传递真实 credential value。`readsCredentialValue=false` 和 `no-credential-value` 是为了防止把“解释安全边界”变成“接触安全资产”。

raw endpoint 边界同理。讲解可以说不要解析 raw endpoint URL，但不能为了举例输出真实地址。`resolvesRawEndpointUrl=false` 和 `no-raw-endpoint-url` 保持这个限制。managed audit 边界也一样：本版不会发 HTTP/TCP 请求，`managedAuditHttpAllowed=false` 明确说明没有连接外部审计系统。

部署、回滚和 autostart 也是禁止项。代码讲解质量再差，也不能用自动启动服务或部署动作来“证明”修复；修复应该体现在规则、测试、文档和 CI 上。v1774 因此只添加静态 Java 文件和测试准备，不启动任何后台进程，也不留下需要清理的服务。

## 测试覆盖

v1774 的直接测试会在后续版本补齐，但它已经预设了验证目录。计划中的第一层是 `OpsShardReadinessCodeWalkthroughDepthRoutePathsTests`，验证 route path 与共享路由委托。第二层是 service tests，验证响应版本、endpoint、profile、sourcePlan、priorQualityGate、effectiveFromVersion、minimumChineseCharacterCount 和各类计数。第三层是 boundary tests，验证所有 runtime 风险都处于 denied 状态。

真正和用户反馈直接相关的是 `OpsCodeWalkthroughArchiveComplianceTests` 的升级。该测试会扫描所有 `代码讲解记录*` 目录下的 Markdown 文件，并对 v1774 以后的 `version-*.md` 文件执行中文长篇门禁。它不仅检查标准章节，也会统计 Han script 字符数量，要求不少于 3000，同时要求中文字符在字母/汉字总量里占主体。这样以后再写五百字或英文短说明，测试会直接失败。

本版完成后需要运行定向测试：`mvn -q "-Dtest=OpsShardReadinessCodeWalkthroughDepth*Tests,OpsCodeWalkthroughArchiveComplianceTests" test`。整批完成后还需要跑全量 `mvn -q test`，再 push 到 `javaproject` 并等待 GitHub Actions。测试通过才算收尾，不再只说“已经写了文档”。

这次讲解本身也会被测试检查。也就是说，v1774 的这篇长文不是额外装饰，而是新规则的第一批样本。它必须覆盖入口、模型、上游、服务、Java 证据、mini-kv、边界、测试，且达到中文长篇门槛；如果达不到，说明规则和执行又脱节了。

## 实际工作量说明

本版的实际工作量集中在本项目内部的规则地基，而不是用几句承诺替代工程约束。用户提出以后讲解必须中文书写、每版独立成篇、至少三千字，并且字数不够就加大实际工作量，禁止硬凑。v1774 因此先把这个要求拆成可维护的代码结构：独立路由常量负责入口，响应模型负责承载规则，深度规则目录负责表达三千字、独立版本、实现面、边界证明和反凑字要求。这个拆分让后续版本可以继续扩展，而不必把所有规则塞进一个巨型文件。

本项目以前已经有不少只读证据接口，如果这次只新增一篇文档说明，很容易在后续几十版推进中被遗忘。v1774 选择做 registry foundation，是为了让规则成为源码的一部分。维护者以后看到响应模型，就能知道讲解质量不是随口要求，而是和路由、模型、目录、服务、测试同级的工程对象。即使本版还没有暴露 controller，基础类型已经把字段边界摆出来：是否只读、是否允许执行、是否启动上游、是否读取密钥、是否解析原始端点、是否允许托管审计连接，都有明确位置。

这一版也没有借用户要求“加大工作量”去打开无关功能。真正加大的工作量是把讲解治理做细：把可读说明拆成结构化规则，把边界拒绝做成后续可统计对象，把三千字要求写成数值字段，把每版一篇写成规则编码。这些都是本项目长期维护会复用的部分。若未来某个版本讲不出三千字，不应该在文档里重复同一句话，而应该回到源码、测试、文档归档或边界证明中补足真实改动。v1774 把这个原则放进地基，就是为了防止以后再出现短讲解和硬凑讲解。

## 一句话总结

v1774 把“以后 Java 代码讲解必须中文、长篇、可复盘”从口头要求升级为只读 registry 的模型、目录和边界基础，同时保持所有运行时、密钥、端点、部署和上游启动边界关闭。
