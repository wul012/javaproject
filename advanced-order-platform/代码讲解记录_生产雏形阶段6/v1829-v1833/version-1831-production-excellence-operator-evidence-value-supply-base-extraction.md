# version-1831 production excellence operator evidence value supply base extraction

## 实际工作量说明
本版本做的是 OperatorEvidenceValueSupply 基础族的抽取，不是单纯移动几个文件。它处在 ValueSupply 证据链的中层：上游还有 OperatorEvidenceValueDraft，旁边有已经抽出的 AdapterPreflight，下游又被 ApprovalPreflight 和签名审批草稿相关族读取。也就是说，这一刀如果只看文件名会显得机械，但真正的风险在于路径常量、响应模型、slot 工厂、下游导入、SpotBugs 镜像豁免和根包 ratchet 必须同时保持一致。为了禁止硬凑，本篇只解释本项目这一版确实改过、测过、可能出错的部分，不拿无关架构故事填字数。

这次的核心目标有三层。第一层是把基础实现从根 `ops` 包移进 `ops.maintenance.operatorevidencevaluesupply`，让根包只保留两个 HTTP 控制器和全局路由聚合器。第二层是把服务自己的 endpoint 所有权交给新的叶子路由类，让后续族读取它时不再依赖根包常量。第三层是控制总文件数，不让抽取过程为了好看而增加新的治理文件。本来根包里有一个 Support helper，如果直接随服务一起移动，总的 `ops` Java 文件数会从 1352 增加到 1353。这个增长没有业务价值，所以本版本把 Support 的响应工厂和 slot 工厂折进 `SlotCatalog`，让总数继续保持 1352，同时根包直连 Java 文件从 848 降到 833，剩余可迁移根文件从 743 降到 728。

实际操作上，本版本移动了十四个实现文件：catalog、archive plan、digest blueprint、envelope template、missing value policy、operator review checklist、provenance requirement、redaction policy、side effect gate、source evidence guard、validation matrix、closeout、response record 和 slot catalog。两个控制器仍留在根包，因为它们是 Spring MVC 入口，短期内根包仍负责承载外部 HTTP 映射。这个边界符合当前 Java 拆分策略：服务实现尽量下沉，控制器暂时保留，路由字面量逐步交给更窄的族 owner。

文档和测试不是附属品。这个版本同步更新了 `docs/ops/README.md`、`docs/ops/extraction-endgame-census-v1828.md`、`docs/production-excellence-progress.md`、`CHANGELOG.md`，并新增 `docs/ops/operator-evidence-value-supply-base-extraction-v1831.md`。测试侧新增 v1831 专用 readability guard，同时收紧 v1806、v1809、v1828 的根包文件数断言。这样以后如果有人把这些实现偷偷挪回根包，或者为了省事放宽 root count，机械门会失败，而不是靠人回忆这次做过什么。

## 入口路由
入口路由的原则是“外部路径不变，内部所有权变窄”。控制器仍然是 `OpsShardReadinessOperatorEvidenceValueSupplyFoundationController` 和 `OpsShardReadinessOperatorEvidenceValueSupplyAssuranceController`，它们继续在根 `ops` 包中暴露 HTTP 入口。这样做不是偷懒，而是为了避免同时移动控制器带来的 Spring 扫描、MockMvc 测试和路由索引风险。当前拆分阶段追求的是稳定缩小根包实现面，不在同一版本里改变外部入口位置。

新的 `OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths` 放在 `ops.maintenance.operatorevidencevaluesupply` 包内。它声明同一个 `BASE_PATH`，值仍然是 `/api/v1/ops/shard-readiness`，并声明本族十二个 suffix，例如 catalog、closeout、digest blueprint、envelope template、missing value policy、operator review checklist、provenance requirement、redaction policy、side effect gate、source evidence guard 和 validation matrix 等路径。每个服务的 `ENDPOINT` 都改为用这个叶子 route owner 拼接，而不是继续从根聚合器读取同名常量。

根 `OpsShardReadinessRoutePaths` 没有被删除，它仍然是历史兼容层。它现在导入叶子路由类，并把 ValueSupply 基础族的 suffix 委托给叶子类。这样旧控制器、旧路由测试或仍然依赖根聚合器的代码不会被立刻打断；与此同时，新迁出的服务已经拥有更清楚的路径归属。这个设计是渐进式拆分的关键：不改字节级 endpoint，不破坏调用者，但把未来维护者最该看的常量放到服务族旁边。

这一步还有一个隐蔽收益。后续 ComparedEvidence 或 ReleaseAcceptance 族再读取 ValueSupply endpoint 时，可以直接导入 `ops.maintenance.operatorevidencevaluesupply` 包，而不是通过根包绕路。导入关系会直接表达依赖方向：下游读取上游的 read-only readiness endpoint。代码图和普通 grep 都能看懂这条边，维护者不需要再从一千多个根包文件里猜哪个常量属于哪个业务族。

## 响应模型
响应模型仍然是 `OpsShardReadinessOperatorEvidenceValueSupplyResponse`，但它的包从根 `ops` 移到了 `ops.maintenance.operatorevidencevaluesupply`。这个 record 承载的是只读证据视图，不是命令输入，也不打开写路径。它的字段组合表达了本族 readiness 的基本形态：版本、endpoint、profile、slot 列表、阻断原因、以及是否允许操作员提交、证据导入、人工录入、运行时 payload、现场执行和生产执行等布尔状态。

迁移响应模型时最容易漏的是静态分析配置。项目里 SpotBugs 对这个 response record 有 EI_EXPOSE_REP 和 EI_EXPOSE_REP2 两组镜像豁免，用来描述列表字段的不可变边界。如果只移动 Java 文件而不改 `config/spotbugs-exclude.xml`，本地编译可能过，但完整 verify 会在 SpotBugs 阶段把旧 FQN 找不到或新 FQN 未声明的问题暴露出来。本版本把两个镜像块都改成新的完整包名，避免“代码 moved，治理配置 still root”的半迁移状态。

Support helper 被折进 `SlotCatalog` 后，响应模型的构造入口也更集中。过去 Support 负责 `response(...)` 和 `slot(...)` 这类工厂方法，SlotCatalog 负责整理 slot 列表，两者职责很接近。保留两个文件会让总数增加，也会让后续读者在“support 到底是公共工厂还是测试辅助”之间犹豫。现在 SlotCatalog 同时拥有 profile 常量、slot 构造和 fail-closed response 构造，含义更直观：这里就是本族证据槽位和默认响应的唯一目录。

这个折叠没有改变响应语义。所有 ready 标志仍然保持保守值，所有 endpoint 和 profile 字段仍然来自服务自己的常量，slot 列表仍然表达每个证据来源的用途、阻断原因和执行边界。测试里原来的 SupportTests 没有删除，而是改成调用 SlotCatalog 的静态工厂。这样它继续覆盖同一行为，只是不再要求生产代码保留一个薄薄的 Support 文件。

## 上游证据配置
OperatorEvidenceValueSupply 基础族的上游不是外部数据库写入，而是一组只读证据定义。它需要知道草稿证据值从哪里来、哪些字段可以被操作员补录、哪些缺失值必须阻断、哪些 payload 只能作为运行时只读材料、哪些 endpoint 只能在预演阶段被消费。这个族的服务名看起来很多，但每个服务都对应一块证据配置表，而不是复杂业务流程。

迁移后，上游证据配置的读取关系变得更清楚。AdapterPreflight 的 slot catalog 和 ApprovalPreflight 的 item catalog 都要读取 ValueSupply 基础族的 endpoint 常量。本版本把这些下游导入改成新包名，显式引用已经迁移的基础服务。这样下游不再从根包读“看似全局”的服务类，而是读一个已经成包的 ValueSupply 基础能力。这个变化不会改变配置内容，但会改变维护者看到的依赖结构。

上游证据配置还有一个重要边界：本版本不改 fixture 字节，不改历史归档路径，不改 mini-kv 或 Node 的证据文件。Java 项目有不少文档会指向其他仓库的历史 evidence，如果为了拆分 Java 包就重命名 archive 根目录，Node 的硬编码校验会被破坏。v1831 的文档明确保留 `Do not rename or move archive roots`，这是跨项目协作里的安全线。拆包只能改变 Java 源码归属，不能顺手整理别的项目的历史证据。

这一刀也没有提前打开 credential、raw endpoint、active router 或 write routing。ValueSupply 仍是 read-only readiness 侧的证据目录，负责告诉操作员哪些值可以进入后续审批，哪些还缺来源，哪些因为生产边界不能执行。计划书要求暂时不打开写路由和真实执行连接，本版本遵守这个边界。

## 服务层核心流程
服务层的核心流程可以理解成“固定入口，读取目录，生成保守响应”。控制器调用对应服务，服务读取 SlotCatalog 或自己的静态配置，拼出一个 `OpsShardReadinessOperatorEvidenceValueSupplyResponse`。这个 response 不代表系统已经可以生产写入，它只告诉运维或评审者：当前 ValueSupply 证据值准备到了哪一步，哪些输入来源已被列出，哪些执行阶段仍被阻断。

迁移前，这些服务都挤在根 `ops` 包中。根包同时装着控制器、路由、readiness 服务、各种 preflight 族和历史治理类。对维护者来说，查一个 ValueSupply 证据项要在根包中和上千个类同级浏览，认知成本很高。迁移后，这些服务被放在 `maintenance/operatorevidencevaluesupply` 目录下，目录名直接说明它们只属于 OperatorEvidenceValueSupply 基础族。控制器是入口，叶子包是实现，根路由是兼容聚合器，三者关系更透明。

SlotCatalog 是本版本最有代表性的重构点。它不只是“被移动的目录文件”，还吸收了 Support helper 的职责。这样服务层构造 response 时不再跨两个同族 helper 来回跳。对读代码的人来说，想知道每个 slot 的名称、endpoint、profile、阻断原因和 ready 标志，只需要打开 SlotCatalog；想知道 HTTP 入口，打开两个根控制器；想知道路径字面量，打开叶子 RoutePaths。这个结构虽然没有引入新抽象，却把查找路径变短了。

服务层仍然保持 `@Transactional(readOnly = true)` 这一类只读语义。迁移没有把任何服务改成写事务，也没有让 ready 标志变得更激进。这里的“生产卓越”不是把功能说得更大，而是在不改变契约的前提下，让同族代码可定位、可测试、可继续抽。后续 ComparedEvidence 或 ReleaseAcceptance 的切分可以复用这个模式：先迁被下游读取的叶子，公开 endpoint，再让依赖方改 import，最后收紧 census。

## Java 证据检查
Java 侧证据首先来自 root census。`scripts/ops-root-census.ps1 -Json` 应报告 direct-root Java files 为 833，retained-root files 为 105，remaining direct-root non-controller files 为 728，unassigned files 为空，并且 `OperatorEvidenceValueSupply base` bucket 为 0。这个数字组合说明三件事：本族基础实现已经离开根包；根包只保留被允许保留的入口类和聚合类；剩余 backlog 按计划继续下降。

第二组证据来自 v1831 readability guard。新增测试检查迁移说明文档存在，README 和 endgame census 能发现它，十四个实现文件确实在新包里，根包中不存在同名实现文件，Support helper 没有作为独立生产文件留下，两个控制器仍然留在根包，叶子 RoutePaths 声明了不变的 `BASE_PATH` 和关键 suffix，根聚合器委托到叶子路由，下游 ApprovalPreflight 和 AdapterPreflight 已经导入新包，SpotBugs 只保留新 response FQN，root count 和 total ops count 都没有放松。

第三组证据来自服务和控制器测试。迁移后的 FoundationServiceTests、AssuranceServiceTests、SlotCatalogTests、SupportTests 都移动到新包或改用新包导入；保留在根包的 RoutePathsTests 显式导入新服务，证明服务的 public ENDPOINT 仍然可被外部测试读取；AdapterPreflight 与 ApprovalPreflight 的测试继续覆盖下游读取链，证明 ValueSupply 基础族移动后没有打断后继族的证据目录。

第四组证据是全局门。v1806、v1809、v1828 这些历史治理测试不只是历史记录，它们现在也被更新为 833 这个更严格的 root count。这样本版本的成果会被老门一起守住，而不是只靠 v1831 新测试。讲解归档也被放进标准目录 `代码讲解记录_生产雏形阶段6/v1829-v1833`，并在最终 verify 前写好，满足“讲解先于 verify”的规则。

## mini-kv 证据检查
mini-kv 本版本没有改代码，也不应该被 Java 拆包顺手改动。它在四项目链条里是上游基础存储，Java 这里只消费已经归档和冻结的 evidence 语义。v1831 的 mini-kv 检查重点不是运行 C++ 测试，而是确认 Java 没有改 mini-kv fixture、没有移动跨仓库 archive 根、没有把只读 readiness 变成真实写连接。

这条边界很重要。当前系统的协作方式是 mini-kv 提供底层只读证据或历史产物，Java 负责订单平台和 ops readiness，Node 是下游汇总与消费门禁。Java 包迁移属于本项目内部维护工作，不需要 mini-kv 同步发布，也不能把 mini-kv 的归档目录当成“顺便整理”的对象。v1831 文档把 archive roots 的禁止移动写进抽取说明，是为了让后续维护者看到这条跨仓库约束。

如果将来要做真实联合验收，mini-kv 的检查应由跨项目 capstone 来完成：Java 启动自身 jar，Node 读取 Java 端点，另一路执行真实 `minikv_cli` 并生成新鲜输出，而不是复用旧 fixture。但那不是 v1831 的范围。当前版本只证明 Java 内部 ValueSupply 基础族迁移没有改变 mini-kv 证据边界，也没有触碰 C++ 工作区。

## 阻断与安全边界
v1831 的阻断边界可以概括为“只读、保守、可追踪”。只读意味着服务层仍然只是 readiness 查询，不做数据库写入，不连接真实审计系统，不发起部署或回滚，不改 credential value。保守意味着 response 中的 ready 标志不会因为抽包而突然打开，缺失值、人工录入和运行时 payload 仍然通过 slot 和阻断原因表达。可追踪意味着每个 endpoint 的 owner、每个下游 import、每个 SpotBugs FQN 和每个 root count 都有测试或文档证据。

安全边界还包括对根包控制器的保留。现在移动控制器会带来更大的行为面：Spring request mapping、MockMvc 测试、API 文档引用、外部路径索引都可能受影响。本版本只移动实现，不移动 HTTP 入口，是为了让本刀聚焦在可维护性收益最高、行为风险最低的地方。等根包实现面下降到更小规模后，再规划 controller split 会更稳。

另一个安全边界是不会为了通过测试而修改 fixture 或放宽 ratchet。本版本遇到文件数问题时，没有把 total ops limit 从 1352 放宽到 1353，而是回头合并 Support helper，让结构和数字同时成立。这个选择比调测试更费时间，但它保住了项目长期规则：ratchet 只收紧，门禁要能失败，迁移不能靠改期望蒙混过关。

最后是讲解和编码边界。中文讲解必须在 verify 前完成，而且要能被合规测试识别标准章节、中文深度、实际工作量和“本项目”语义。如果讲解文件因为 Windows 编码被写坏，测试应该失败，修复方式是重写文件为正常 UTF-8 中文，而不是降低合规测试。这也是本版本收尾时额外处理的真实风险点。

## 测试覆盖
本版本的测试覆盖分为行为测试、迁移测试和治理测试。行为测试覆盖 FoundationService、AssuranceService、SlotCatalog 和原 Support 行为，确保迁移后 response 仍然按保守规则生成，slot 列表仍然表达同一组证据项，旧 helper 的工厂语义没有丢。RoutePathsTests 覆盖服务 ENDPOINT 与路径拼接，证明外部路径没有因为 route owner 下沉而变化。

迁移测试覆盖文件位置和导入边界。v1831 guard 会逐一检查十四个实现文件是否在 `ops.maintenance.operatorevidencevaluesupply`，根包是否已经不存在这些文件，两个根控制器是否还在，新的 RoutePaths 是否含有关键 suffix，根聚合器是否委托到叶子 owner，下游两个读取者是否导入新包而不是旧根包。这个测试不是为了追求覆盖率数字，而是把本版本最容易回退的维护边界写成机械检查。

治理测试覆盖 root count、total count、SpotBugs 和文档发现性。根包 direct count 必须等于 833，总 ops Java 文件数不能超过 1352；SpotBugs 不允许旧 response FQN 残留；README、census、抽取说明和 progress ledger 都要能互相指向。`OpsCodeWalkthroughArchiveComplianceTests` 则负责检查讲解目录里的新文章是否具备标准章节、中文长文深度、实际工作量说明、禁止硬凑和本项目语义。

最终收尾应先跑聚焦门，再跑完整 `mvnw verify`。聚焦门可以快速定位本刀相关问题，完整 verify 则覆盖全部 1600 多个测试、JaCoCo、SpotBugs、Spotless 和构建生命周期。只有两层都过，才能提交、打 tag、push 并等待 GitHub Actions。远端 CI 成功后，J41 才能在 progress ledger 中从 “pending” 变成 “completed; remote CI passed”。

## 一句话总结
v1831 把 OperatorEvidenceValueSupply 基础实现从拥挤的根 `ops` 包迁到独立 maintenance 包，新增叶子路由所有者，折叠无必要的 Support helper，保持 endpoint 字节、只读边界和总文件数不变，并用 root census、下游导入、SpotBugs、文档索引、中文讲解和完整测试把这一刀固定下来。
