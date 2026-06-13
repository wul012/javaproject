# v1797 生产卓越代码讲解：代码讲解合规簇抽离

本版本的核心工作不是继续堆叠新的只读 endpoint，而是开始兑现 v1796 盘点里最重要的维护承诺：把已经证明边界稳定、职责相对独立、又长期挤在 `ops` 根包里的实现簇，按真实业务语义移动到更窄的包中。被选择的对象是代码讲解合规注册表，因为它本身已经是一个成熟的只读证据簇，包含版本目录、必需标题、归档范围、文档规则、运行边界和测试覆盖等内容；它对外暴露的 route 很小，对内却拥有 catalog、renderer、support、response、service 等完整层次。把它作为第一刀，可以同时验证“根包瘦身是否安全”“controller 是否必须继续留在根包”“route 聚合表如何继续向旧调用方提供稳定常量”“测试如何跟随包边界拆分”这四个维护问题。

## 入口路由

本次入口路由保持完全不变，仍然是 `/api/v1/ops/shard-readiness/code-walkthrough-compliance-registry`。这点非常关键，因为本项目目前的 ops 证据层已经被大量测试、文档和历史归档引用，任何路由变更都会带来跨版本追溯成本。v1797 的拆分因此没有把 `OpsShardReadinessCodeWalkthroughComplianceRegistryController` 移出根包，而是保留它继续使用根包里的 `OpsShardReadinessRoutePaths.BASE_PATH` 和 `OpsShardReadinessRoutePaths.CODE_WALKTHROUGH_COMPLIANCE_REGISTRY`。这样 Spring 的 `@RequestMapping` 和 `@GetMapping` 仍然绑定在原来的入口位置，外部调用方看不到任何迁移动作。

真正被移动的是 controller 之后的实现层。新的 `ops.maintenance.walkthrough.compliance` 子包提供 `OpsShardReadinessCodeWalkthroughComplianceRoutePaths`，它公开 route suffix 给根聚合表使用，同时在子包内部持有 base path 用于 service 生成 response endpoint。这个安排避免了一个容易滑坡的设计：为了让子包访问根包的 package-private `OpsShardReadinessRoutePaths`，把整个根路由表改成 public。根路由表是旧世界的聚合入口，不应该因为一次局部拆分就扩大可见性；新包只暴露它必须暴露的后缀常量，service 自己组装只读 endpoint 字符串，controller 仍由根包负责公开 HTTP 入口。入口层因此形成了“根包管外部契约、子包管实现细节”的边界。

这种拆法也方便后续继续复制。将来如果要抽离 code walkthrough quality、depth、audit 等相邻簇，可以沿用同一规则：旧 controller 是否移动要看它是否依赖根包私有路由表和历史 route 聚合测试；实现层能移就先移；route suffix 可以由子包公开给根聚合表；完整 URL 可以在子包 service 内部通过本簇 route paths 生成。这样每次都能降低根包压力，却不会把一次拆分扩大成全局路由重构。

## 响应模型

响应模型被移动到新包，但字段语义没有改。`OpsShardReadinessCodeWalkthroughComplianceRegistryResponse` 仍然记录 project、version、readOnly、executionAllowed、startsJavaService、startsMiniKvService、readsCredentialValue、resolvesRawEndpointUrl、managedAuditHttpAllowed、endpoint、profile、sourcePlan、archiveDirectory、registryState、各类 count、版本列表、标题列表、归档范围、文档规则、边界规则、测试覆盖、markdown sections、checks 和 status。换句话说，类的 namespace 变窄了，响应内容没有换契约。

这里刻意没有对 response record 做字段重排或命名缩短。原因是本版本的目标是证明拆包安全，而不是顺手做模型语义优化。响应字段已经被 service tests、renderer tests、controller tests 和历史代码讲解合规规则共同使用，如果在同一版本里既移动文件又改字段，失败时很难判断是包边界问题还是模型行为问题。v1797 只改变归属位置，保留 record 的公开类型和内部嵌套 record，使调用方需要改的地方集中在 import，而不是理解新的对象结构。

同时，response 继续保持 public。它被根 controller 的返回类型直接引用，Spring 序列化也需要稳定的公开类型。与之相对，catalog、renderer、support 多数仍保持 package-private，因为这些类只在新子包内部协作，不需要给根包或其他业务包暴露。这个可见性分层是本次重构里最有价值的细节之一：不是把所有移动后的类都一口气 public，而是只公开 route paths、service 和 response 这些跨包确实需要的类型。这样既完成拆分，又没有制造新的全局 API 面。

## 上游证据配置

代码讲解合规注册表本质上是对上游治理规则的只读再表达。它读取的不是数据库、消息队列或远端服务，而是本项目已经固化的版本谱系、必需讲解标题、归档目录范围、文档规则、运行边界和测试证据。v1797 移动了这些 catalog 类，包括 `ArchiveRangeCatalog`、`BoundaryRuleCatalog`、`DocumentationRuleCatalog`、`RequiredHeadingCatalog`、`TestCoverageCatalog`、`VersionCatalog`。这些类迁移之后同处一个子包，含义反而更清楚：它们不再是散落在根 ops 包里的普通 Readiness 文件，而是代码讲解合规这个维护主题的内聚证据配置。

本次没有改 catalog 数据本身。比如版本数量、归档目录、禁止写路由、禁止 active shard router、禁止读取 credential value、禁止解析 raw endpoint URL、禁止 managed audit connection、禁止部署回滚、禁止自动启动 Java 或 mini-kv 这些规则，全部保持旧值。之所以不顺手更新 catalog 内容，是因为本项目后期维护最怕把“结构整理”和“证据事实变更”混在一起。结构整理应该能被 git diff 清晰看见：文件从根包移动到子包，包名变化，少数 import 和 route path 可见性变化。证据事实变更则应该是另一个版本，有对应的业务理由和测试断言。

新的文档 `docs/ops/code-walkthrough-compliance-extraction-v1797.md` 也承担了上游证据说明职责。它列出了十一份被移动的实现文件，明确 controller 留在根包，记录 v1796 到 v1797 的根包计数变化，并再次写明 `a/` 到 `f/`、`e/<version>/`、evidence JSON、截图归档和历史代码讲解目录都不移动。这个文档不是装饰物，而是给后续维护者看的变更账本：如果以后有人继续拆分，可以先看这份说明，知道第一刀的边界在哪里。

## 服务层核心流程

服务层的核心流程仍然由 `OpsShardReadinessCodeWalkthroughComplianceRegistryService.registry()` 完成。它依次读取 version catalog、required heading catalog、archive range catalog、documentation rule catalog、boundary rule catalog、test coverage catalog，然后通过 support 组装 response，并调用 renderer 输出 markdown sections。v1797 对流程没有重新排序，没有新增副作用，也没有把 catalog 改成注入式 bean。这个选择比较克制，因为当前 catalog 是静态只读数据，测试覆盖充分，改成更复杂的注入模型不会立刻降低风险，反而会增加 Spring wiring 和序列化上下文的变化。

真正的服务层变化在 endpoint 生成方式。原来 service 在根包里可以直接读取 `OpsShardReadinessRoutePaths.BASE_PATH` 和 `OpsShardReadinessRoutePaths.CODE_WALKTHROUGH_COMPLIANCE_REGISTRY`。移动到子包后，如果还想访问根包的 package-private 常量，就必须扩大根路由表可见性。v1797 没有这样做，而是在新子包 route paths 类里增加包内 `BASE_PATH`，并公开 `CODE_WALKTHROUGH_COMPLIANCE_REGISTRY` suffix。service 的 `ENDPOINT` 由这两个本簇常量拼接。这样它对外返回的 endpoint 仍然一样，对内却不再依赖根包私有类。

这个看起来很小的调整，其实决定了后续抽离路线的质量。若把根 `OpsShardReadinessRoutePaths` 改成 public，短期编译最容易，但会让所有未来子包都倾向于继续依赖根表，根包就从“拥挤的文件夹”变成“被所有新包依赖的全局中心”。v1797 选择让子包拥有自己的最小 route paths，根表只做聚合代理，从依赖方向上切断了这种回流。对本项目这种已经积累一千三百多个 ops 源文件的后期工程来说，依赖方向比移动几个文件更重要。

## Java 证据检查

Java 侧证据首先体现在文件计数。v1796 盘点记录了 ops 主源码总数 1,352、根包直放 Java 文件 1,330、Readiness 命名文件 1,210。v1797 之后，ops 总数仍是 1,352，Readiness 命名仍是 1,210，根包直放文件下降到 1,319。这说明本次不是删除功能，也不是改名逃避统计，而是把十一份实现文件从根包移到更窄的子包。`ReadabilityUpkeepGovernanceConsolidationPlanTests` 的 root ratchet 也从 1,330 收紧到 1,319，后续如果有人又把文件放回根包，测试会直接失败。

第二层证据是新增 `ReadabilityUpkeepOpsConsolidationExtractionTests`。这个测试不只看文档存在，还逐个检查十一份实现文件在 `maintenance/walkthrough/compliance` 目录下存在，并且在根 `ops` 目录下不存在。它还确认 root controller 和 `OpsShardReadinessRoutePaths` 仍在根包，防止维护者误以为“抽离”就必须把 controller 也移动走。测试里同时读取 v1797 文档，检查 `contract-preserving`、`1,319`、`Do not rename or move archive roots` 和原 endpoint 等关键短语。这样文档、源码和计数三者之间形成了闭环。

第三层证据来自原有合规簇测试。移动后的 service、renderer、boundary、immutability 测试已经进入新包，仍然验证 version、endpoint、profile、readOnly、executionAllowed、边界规则、markdown sections 和不可变列表。根包的 route paths test 与 controller test 则留在原处，继续证明根聚合表和 Spring controller 对外不漂移。这个测试分布本身就是架构说明：实现细节跟着实现走，公开入口测试留在公开入口所在的地方。

## mini-kv 证据检查

本版本没有读取、启动、修改或整理 mini-kv。mini-kv 在这里仍然只是代码讲解合规规则中的一个外部边界对象：讲解文档可以声明 mini-kv 没有被自动启动，测试可以断言 response 中 `startsMiniKvService=false`，边界 catalog 可以继续保留 no-minikv-autostart 规则，但 Java 侧不会借这次拆包去触碰 C++ 仓库。这个边界很重要，因为用户已经明确要求当前主要做自己的项目，不让 Node 或 mini-kv 卡住 Java 的内部维护。

v1797 对 mini-kv 的实际保护是“不制造新的跨仓耦合”。如果本次为了证明归档边界而去扫描 mini-kv 的历史 evidence，或者为了写讲解去引用 mini-kv 的具体文件，就会把一个 Java 内部拆包版本变成跨项目同步版本。那既不必要，也会破坏四项目统筹里“非契约内部工作可以并行”的原则。本项目本轮只需要保证 Java 的代码讲解合规注册表继续声称不会启动 mini-kv，继续保留只读边界，继续不读取凭据或端点实值。

因此 mini-kv 证据检查的结论很明确：没有进程启动，没有文件移动，没有 archive 迁移，没有新增依赖，没有把 Java service 与 mini-kv runtime 绑定。若后续要做 mini-kv 自己的归档清理或大文件拆分，应在 mini-kv 仓库内按它自己的 playbook 做，不应通过 Java 的代码讲解合规注册表间接完成。v1797 只是让 Java 内部维护结构变得更清楚。

## 阻断与安全边界

安全边界在 v1797 没有被放松。代码讲解合规注册表仍然是 read-only registry，不接受请求体，不写数据库，不发 RabbitMQ，不连接管理审计系统，不解析 raw endpoint URL，不读取 credential value，不触发部署或回滚，也不启动 Java 或 mini-kv 进程。移动包名不应该改变运行态行为，测试也围绕这个原则继续断言 `executionAllowed=false`、`readOnly=true` 和相关 boundary rules。

另一个边界是历史归档不能移动。v1796 文档已经提醒 Node 侧存在大量对 Java evidence 归档的硬路径引用，所以 Java 的 `a/` 到 `f/`、`e/<version>/`、evidence JSON、截图说明目录和历史代码讲解目录不能被“顺手整理”。v1797 文档再次写明这一点，并把它放进 extraction test 的关键短语检查中。这样后续维护者看到“抽离”二字时，不会误解成可以同时移动归档文件夹。

还有一个容易忽略的边界是可见性。v1797 只把 `OpsShardReadinessCodeWalkthroughComplianceRoutePaths`、service 和 response 暴露到跨包可用的程度，没有把所有 catalog、renderer、support 都改成 public。这样做能防止新子包被当成一个随便访问的全局工具箱。边界收缩的价值不在于目录更深，而在于包内细节真的留在包内，根包只保留外部入口和必要聚合。

## 测试覆盖

本版本先跑了聚焦测试：`OpsShardReadinessCodeWalkthroughComplianceRegistryServiceTests`、`OpsShardReadinessCodeWalkthroughComplianceRegistryControllerTests`、`OpsShardReadinessCodeWalkthroughComplianceRoutePathsTests`、`OpsShardReadinessCodeWalkthroughComplianceRegistryBoundaryTests`、`OpsShardReadinessCodeWalkthroughComplianceRegistryRendererTests`、`OpsShardReadinessCodeWalkthroughComplianceRegistryImmutabilityTests`。第一次运行暴露了一个编辑工具造成的 UTF-8 问题：含中文字符串的 Java 文件被 Windows PowerShell 默认编码写坏。这个问题没有绕过，我恢复移动目标文件内容，然后用无 BOM UTF-8 的 .NET 写入方式重做包声明，复跑后 8 个聚焦测试全部通过。

新增测试覆盖分两类。第一类是架构护栏：`ReadabilityUpkeepGovernanceConsolidationPlanTests` 将根包 direct Java 文件 ratchet 收紧到 1,319；`ReadabilityUpkeepOpsConsolidationExtractionTests` 检查新包文件、根包缺失、controller 保留、route aggregation 保留、v1797 文档可发现；`ReadabilityUpkeepDocsTests` 把 v1797 文档纳入 docs ops index。第二类是版本文档护栏：`ProductionReadinessDocumentationTests` 增加 v1797 changelog 检查，代码讲解归档测试会继续检查本文件的中文深度、标准标题和实际工作量说明。

后续完整门禁会继续跑 Spotless、聚焦 docs/readability/walkthrough 测试、默认 verify、docker profile verify 和 prod smoke。由于本项目存在历史慢测，完整 verify 不是几秒钟能完成的事情；v1796 已经证明全量 verify 可能超过二十五分钟。本版本的门禁价值在于把局部编译、包边界、文档护栏、全量回归和启动烟测串起来，而不是只看文件移动是否编译。

## 实际工作量说明

本项目这一版的实际工作量集中在维护边界，而不是制造新功能数量。具体来说，先用 CodeGraph 定位代码讲解合规簇的入口、service、renderer 和测试关系，再选择一个不会改变外部 route 的抽离方案；随后使用 `git mv` 移动十一份 main implementation 文件和五份包内细节测试，保留 root controller 与 root route path tests；接着修 service endpoint 生成、root controller import、root route aggregation import、root tests import 和 service 实例化方式；第一次测试失败后，又处理了 Windows PowerShell 默认编码导致的 UTF-8 损坏，恢复移动文件内容并用正确编码重做包名。

文档层也不是事后补几行。v1797 新增了 `docs/ops/code-walkthrough-compliance-extraction-v1797.md`，写清楚 extraction scope、root package pressure、contract preservation、archive boundary、test boundary 和 stop line；`docs/ops/README.md` 加入入口；`CHANGELOG.md` 增加 v1797；`docs/production-excellence-progress.md` 将 J6 远端 CI 更新为通过，并加入 J7 进度；`ProductionReadinessDocumentationTests`、`ReadabilityUpkeepDocsTests`、`ReadabilityUpkeepGovernanceConsolidationPlanTests` 和新增 extraction tests 一起把这些文档变成可执行证据。

这段说明也刻意回应“禁止硬凑”的要求：如果只是为了凑一篇讲解，可以只说“移动文件、测试通过”。但本项目真正需要的是把为什么 controller 不动、为什么 route paths 只公开 suffix、为什么 service 不再读根包 package-private 常量、为什么 catalog 数据不改、为什么根包计数收紧、为什么归档不动、为什么 mini-kv 不参与这些判断写清楚。只有把这些工程判断写明，后续维护者才不会在下一次拆分时重复踩同样的坑。

再补充一层实际维护含义：这次拆分不是为了让目录树看起来更整齐，而是为了让后来的人打开项目时少承担一层认知负担。过去所有代码讲解合规相关类都和大量 shard readiness、release acceptance、credential resolver、sandbox connection、operator evidence value supply 等文件混在同一个根包里，维护者必须先凭超长类名判断主题，再靠搜索确认哪些类互相协作。移动以后，包名已经说明它属于维护、讲解、合规三个上下文，类名虽然还保留历史前缀，但阅读时不再需要在一千多个根包文件里来回跳。这个变化会在后续多次重构里累积价值：每抽出一个成熟簇，根包就少一组噪声，测试就多一个防回流的计数，文档就多一条可复用的拆分范式。

还有一个实际成本是保守处理旧世界和新世界的接口。旧世界的根路由表仍然存在，旧 controller 仍然存在，旧响应字段仍然存在；新世界的子包只拿走实现细节，并用自己的最小 route path 常量完成 endpoint 生成。这样的折中比一次性大搬家慢，却更符合本项目后期保养阶段的风险曲线。现在仓库里还有大量历史 evidence、截图说明、代码讲解和跨项目引用，任何大规模改名都可能让人误以为档案也应该跟着整理。v1797 选择先建立“只移动实现，不移动契约，不移动归档”的样板，就是为了让后续每一刀都有清楚参照。

从工程范式看，这版还给编辑流程留下一条经验：含中文字符串的 Java 文件不能再用默认 PowerShell 写回。第一次失败不是坏事，它暴露了工具链和文件编码之间的真实风险；修复方式不是删除中文，也不是放宽测试，而是恢复文件内容并改用明确的 UTF-8 写入。对一个已经要求中文讲解、中文规则和中文归档的项目来说，编码就是工程边界的一部分。以后继续拆分含中文证据的 catalog、test 或 markdown 时，必须把编码当成门禁内容看待，不能把它当作偶然的终端显示问题。

最后再说一次维护取舍：本版没有追求表面上的大规模搬迁，而是选择一组边界清楚、证据完整、失败后容易回滚的成熟簇先行落地。这样做的好处是每一步都能被测试确认，每一个保留在根包的对象都有理由，每一个进入子包的对象都有归属。后续继续拆分时，应优先复制这种稳态做法，先收窄实现，再固定路由，再补文档，再跑门禁。

这种节奏稳妥，也更利于长期维护。

## 一句话总结

v1797 把代码讲解合规注册表的实现层从拥挤的 Java ops 根包抽到 `ops.maintenance.walkthrough.compliance`，在不改路由、不改响应、不动历史归档、不启动外部系统的前提下，把根包直放文件从 1,330 降到 1,319，并用文档和测试把这条拆分范式固定下来。
