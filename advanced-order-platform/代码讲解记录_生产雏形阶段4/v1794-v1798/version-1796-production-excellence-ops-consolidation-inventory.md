# 第一千七百九十六版代码讲解：ops 整合前盘点与拆分边界

## 入口路由

本版本没有新增任何 HTTP 路由，也没有改变已有 `/api/v1/ops` 下的只读入口。J6 的第一刀选择做“整合前盘点”，原因很直接：当前 `ops` 包已经不是一个普通业务模块，而是长期承载跨项目只读 evidence、readiness、handoff、approval、route cleanup、release acceptance 和 code walkthrough 证据的历史层。如果马上移动类，很容易在局部看起来变整齐，在全局却破坏 Node 仍然引用的历史路径、测试里依赖的 route path、或者维护者正在查找的证据语义。因此 v1796 把入口放在文档和测试上：新增 `docs/ops/ops-consolidation-inventory-v1796.md`，并让 `docs/ops/README.md` 指向它，再用 `ReadabilityUpkeepOpsConsolidationInventoryTests` 和既有 `ReadabilityUpkeepDocsTests` 保护这个入口。

这个入口和普通功能入口不一样。普通功能入口强调调用者如何发请求，J6 的入口强调维护者如何进入一个过大的包。当前主源码里 `src/main/java/com/codexdemo/orderplatform/ops` 一共有 1,352 个 Java 文件，根包直放 1,330 个，名称包含 `Readiness` 的文件 1,210 个。这样的数量已经不能靠“打开 IDE 搜索一下”来可靠理解。维护者需要先知道哪些群组是路由族，哪些群组是历史证据，哪些路径是绝对不能移动的下游依赖，哪些地方适合先试小规模拆分。v1796 就是给这个阅读动作建立入口。

本版本也修正了一个执行判断：我原本以为 J6 要先补 ops 文件数 ratchet，但复查后发现 v1789 已经有 `ReadabilityUpkeepGovernanceConsolidationPlanTests`，里面固定了 1,352、1,330、1,210 三个上限，并检查 `a/` 到 `f/`、`e/<version>/` 和 evidence JSON 不移动。既然已有计数闸门，本版本就不重复做相同测试，而是补一层更贴近后续拆分的地图：route family inventory、load-bearing archive boundary、reduction candidate list 和 v1796 stop line。这个选择避免重复堆测试，也让 J6 真正往可维护拆分推进。

## 响应模型

v1796 的响应模型是维护文档响应。新增 inventory 文档按四层组织。第一层是 count baseline，记录当前 1,352 个 ops 主源码文件、1,330 个根包直放文件、1,210 个 Readiness 命名文件，以及仅有 `maintenance` 和 `maintenance/readability` 两个直接支持子包的事实。这里的数字不是为了制造压力，而是给后续批次提供可比较基线。只要下一版移动类、提取 helper 或迁移 registry，就能对照这个基线判断是否真的降低根包压力，而不是只把文件从一个长名字换成另一个长名字。

第二层是 route family inventory。它把当前最明显的文件名族群列出来：`OperatorEvidenceValueSupply` 约 301 个文件，`RouteCleanup` 约 184 个文件，`ReleaseAcceptance` 约 161 个文件，`MinimalReadOnlyGate` 约 130 个文件，`CandidateDocument` 约 65 个文件，`CodeWalkthrough` 约 44 个文件。这些不是最终架构分包名，而是拆分前的安全观察标签。它们能帮助维护者识别哪些类可能属于同一条 evidence 链，避免拆分时把一个族群的 controller、service、catalog、renderer、support 分散到不相干位置。

第三层是 suffix pressure。文档列出 `Service=375`、`Catalog=347`、`Response=179`、`Renderer=121`、`Controller=102`、`Support=97`、`Builder=49`、`RoutePaths=17`。这个信息对后续重构很关键，因为它说明问题不只是 controller 太多，也不是 response 太多，而是整个 evidence registry 模式在根包反复复制。真正的优化方向应该是把支持层和 registry 族群迁到窄包里，减少根包扫描成本；不能只移动一个 controller，然后留下 catalog 和 response 继续淹没根包。

第四层是 stop line。文档明确写出 `No class moves in v1796`。这句话很重要，因为它把本版本和后续拆分版本分开。v1796 的工作是测量、标记、建守卫；下一版如果要移动类，必须带 route path 检查、response schema 检查和 archive 引用检查。这样每一版都有清楚的验收边界，不会把“盘点”和“重构”混在一次提交里，降低回滚和 review 难度。

## 上游证据配置

上游依据来自 Java production excellence playbook 的 J6，以及 Java 仓库已经存在的 v1789 ops governance consolidation roadmap。v1789 已经说明 Java 可以在不改 Node 契约的前提下并行推进 ops 整合，也已经写明不得移动 `a/` through `f/`、`e/<version>/` 和 evidence JSON。v1796 继承这个边界，只做 Java 本项目内部的维护盘点，不请求 Node 新接口，不消费 mini-kv 新证据，不改跨项目 schema。

J6 的真实上游约束不是“马上把文件搬走”，而是“后续搬的时候不能伤到历史证据”。Node 侧曾经存在大量绝对路径和 digest 引用，Java 的历史归档目录在这种关系里是 load-bearing 的。所谓 load-bearing，不是说这些目录代码优雅，而是说下游依赖它们的路径稳定性。如果为了让 Java 仓库目录看起来清爽，直接重命名或移动历史 archive，就可能让 Node 的 evidence verification 在完全不同的仓库中失败。v1796 把这个约束写进 inventory，并新增测试确保文档持续包含这些词。

本版本还把 J5 的发布纪律延续下来：`CHANGELOG.md` 增加 v1796，`docs/production-excellence-progress.md` 把 J5 远端 CI 通过的 run id 补齐，并把 J6 状态改成继续推进。这样版本链条保持清楚：v1794 是 observability，v1795 是 release readiness docs，v1796 是 ops consolidation inventory。它们都是生产卓越阶段的一部分，但每版关注的维护问题不同。

## 服务层核心流程

如果把 J6 看成服务层工作，它服务的对象不是外部 HTTP 调用方，而是后续维护者和后续 Codex 执行批次。流程第一步是确认已有护栏。通过 `ReadabilityUpkeepGovernanceConsolidationPlanTests` 可以看到 ops 文件总数、根包文件数、Readiness 文件数已经有 ratchet。因此本版本不再重复添加一个只会检查同样数字的测试，而是把注意力放到“这些数字内部是什么结构”。这一步避免了机械扩充测试数量。

第二步是从包结构和文件名提取可操作信息。当前真正存在的子包只有 `maintenance/readability`，说明绝大多数历史 evidence 仍在 root ops 里。文件名前缀显示几个巨大族群，后续可以先挑较小且测试齐全的族群做迁移演练，比如 `CodeWalkthrough*` 或 `CandidateDocument*`。相比之下，`OperatorEvidenceValueSupply*` 体量最大，但也最容易牵动 approval、artifact、text package、preflight 等多条证据链，不适合第一刀就动。这个判断写进文档后，后续版本就有了优先级。

第三步是建立可发现入口。`docs/ops/README.md` 原本已经列出 shard readiness、walkthrough registry、archive layout、root package pressure、readability upkeep cycle 等维护地图。v1796 在同一个表中加入 `ops-consolidation-inventory-v1796.md`，让维护者从 ops docs 首页就能找到当前 J6 盘点。它不是孤立文件，也不是写完就丢在角落的说明。

第四步是测试守卫。新增 `ReadabilityUpkeepOpsConsolidationInventoryTests` 检查 inventory 文件存在、README 指针存在、核心数字存在、主要 route family 存在、不可移动 archive 规则存在、v1796 不搬类停线存在。既有 `ReadabilityUpkeepDocsTests` 也补充读取这个 inventory，确保它纳入 docs map 的维护网络。这样文档被删除、入口断开、关键边界被改写时，默认测试会提醒。

## Java 证据检查

Java 证据第一组是新增文档 `docs/ops/ops-consolidation-inventory-v1796.md`。它记录了真实测量结果：ops 主源码 1,352，根包直放 1,330，Readiness 命名 1,210，直接支持子包只有两个。它也记录了 route family 近似规模：`OperatorEvidenceValueSupply` 301，`RouteCleanup` 184，`ReleaseAcceptance` 161，`MinimalReadOnlyGate` 130，`CandidateDocument` 65，`CodeWalkthrough` 44。这里用“近似文件数”而不是绝对契约，是因为这些是文件名族群，不是业务协议；它们适合指导拆分，不适合被当成永远不变的 API。

第二组证据是 `docs/ops/README.md`。它新增 inventory 行，并在 active consolidation plan 段落后说明第一份 J6 inventory 记录 root-package pressure、route family clusters、load-bearing archive boundaries 和 first reduction candidates。README 的位置很关键，因为 ops 目录文档已经是后期可读性保养入口，新增 inventory 必须从这里可达。

第三组证据是测试。`ReadabilityUpkeepOpsConsolidationInventoryTests` 专门保护 J6 inventory，`ReadabilityUpkeepDocsTests` 则把它纳入总体 docs index。`ProductionReadinessDocumentationTests` 增加 `v1796`，确保 changelog 的当前版本链没有断。这里的测试粒度有意保持在“骨架稳定”，不把 Markdown 每一句话锁死。后续可以优化文档表达，但不能丢掉关键数字、路径规则和停线。

第四组证据是进度和版本记录。`CHANGELOG.md` 增加 v1796，说明新增 inventory、固化 route family/load-bearing archive/reduction candidate 清单，以及新增文档守卫。`docs/production-excellence-progress.md` 把 J5 的远端 CI run `27420759966` 标成通过，并记录 J6 正在进行。这个小更新让版本链不会出现“代码已经推进，进度还停在 blocked”的错位。

第五组证据是构建配置修正。Spotless 的默认 ratchet 基准原来仍指向旧的 `origin/master`，但当前 Java 仓库已经只保留 canonical remote `javaproject`，本地运行格式化门会因此失败。v1796 把默认值改成 `javaproject/master`，并新增 `BuildConfigurationTests` 固定这个事实。远端 GitHub Actions 不依赖这个默认值，它在 workflow 里显式传入 pull request 或 push 事件对应的基准，因此这次修正主要服务本地 Java 仓库规范，和之前清理错误 remote 的方向一致。

## mini-kv 证据检查

v1796 不消费 mini-kv 新证据，也不启动 mini-kv。这里仍然要专门说明，是因为 Java ops 历史证据和 Node、mini-kv 的跨项目关系很深，容易让人误以为每次 Java 整理都要同步碰 C++ 仓库。事实上，这一版只做 Java 本项目自己的维护盘点；它尊重四项目统筹规则，不改变 mini-kv 的 WAL、snapshot、RESP 命令、C++ 文件结构或 archive retention。

mini-kv 在本版本中的唯一相关性是边界提醒：历史 archive 和 evidence JSON 不能随便动，因为下游可能以路径和 digest 方式引用多个项目的证据。Java 侧把这个规则写清楚，反而能减少后续对 mini-kv 的误伤。等 mini-kv 自己做文件拆分或归档治理时，应在 mini-kv 仓库里建立自己的 inventory、tests 和 progress，而不是由 Java 版本替它执行。

这也符合用户之前强调的“主要做你自己项目”。本项目这版没有为了凑篇幅而讲 mini-kv 内部实现，也没有把 mini-kv 任务塞进 Java commit。mini-kv 只是出现在禁止自动启动、禁止跨项目误改和历史路径保护的上下文中。

## 阻断与安全边界

v1796 的最大安全边界是“不搬类、不改路由、不动 archive”。这个边界看起来保守，但它是后续大胆重构的前提。没有盘点就直接重构，会让每一次移动都像猜谜；先把 route family、load-bearing archive 和 reduction candidate 写清楚，后面才能对一个小族群做 contract-preserving extraction，并用 route path tests 和 response tests 证明没有破坏行为。

本版本没有打开 write routing，没有添加 active shard router，没有读取 credential value，没有解析 raw endpoint，没有建立 managed audit connection，没有执行 deployment 或 rollback，没有启动 Node/Java/mini-kv 的外部进程，也没有改失败事件重放权限。所有新增内容都是 Markdown 文档和测试。测试读取本地文件系统，不发 HTTP 请求，不连接数据库，不启动 Spring 容器，也不读取凭据。

另一个边界是不要把“整合”理解成“删除历史”。ops 包里许多类确实命名冗长、数量庞大，但它们承载过很多只读证据和历史回执。后续整合应该优先迁移支持层、catalog、renderer、test helper、maintenance-only code；对公开 route、response schema、archive file 和 evidence JSON 要更谨慎。如果某个族群太 load-bearing，正确做法是记录 skipped cluster，而不是为了降低数字强行搬动。

## 测试覆盖

本版本新增的聚焦测试是 `ReadabilityUpkeepOpsConsolidationInventoryTests`。第一条测试检查 inventory 从 ops docs index 可发现，避免新增文档成为孤岛。第二条测试检查 1,352、1,330、1,210 以及几个主要 route family 和 suffix 线索，确保盘点的核心信息还在。第三条测试检查 archive 不搬迁规则和 v1796 停线，防止后续维护时把“盘点版”误改成“已搬迁版”。

同时更新 `ReadabilityUpkeepDocsTests`，把 inventory 纳入 docs entry maps 和 topic-specific reading signals。这样 docs/ops 的维护测试仍然是一个完整地图，而不是新增文件单独存在。更新 `ProductionReadinessDocumentationTests` 则是为了让 J5 建立的 changelog 纪律继续有效：当前版本已经是 v1796，测试应当知道 changelog 包含它。

新增的 `BuildConfigurationTests` 覆盖本地构建配置，断言 `pom.xml` 的 Spotless 默认 ratchet 指向 `javaproject/master`，且不再保留旧的 `origin/master` 默认值。这个测试不是为了限制 GitHub Actions 的内部 checkout 名称，而是为了保护 Java 本地仓库规范：日常执行不应该要求恢复一个不该存在的 remote。

后续收尾还需要运行聚焦测试、讲解合规测试、Spotless、完整 `verify`、docker profile verify、prod profile smoke 和远端 GitHub Actions。v1796 虽然主要是文档和测试，但不能跳过这些门，因为这个仓库已经把文档、讲解和生产卓越证据纳入默认质量体系。尤其是讲解合规测试会检查中文长篇深度和“禁止硬凑”说明，确保本篇不是几段短评。

## 实际工作量说明

本版本的实际工作量包括：复查 J6 上游 playbook 和 v1789 consolidation roadmap；确认已有 `ReadabilityUpkeepGovernanceConsolidationPlanTests`，避免重复添加计数 ratchet；重新测量 ops 主源码总数、根包直放数和 Readiness 命名数；统计直接子包；按文件名前缀识别 `OperatorEvidenceValueSupply`、`RouteCleanup`、`ReleaseAcceptance`、`MinimalReadOnlyGate`、`CandidateDocument`、`CodeWalkthrough` 等主要族群；按后缀统计 service、catalog、response、renderer、controller、support、builder、route paths 压力；新增 inventory 文档；更新 ops docs 首页；新增专门文档守卫测试；更新已有 docs 测试、changelog、production excellence progress 和代码讲解索引；最后撰写本篇中文长篇讲解。

格式化门第一次运行时暴露出旧 remote 默认值，这也被纳入本版工作量处理。修复方式不是临时创建 `origin`，也不是绕过 Spotless，而是让项目配置符合当前 Java 仓库规范：只使用 `javaproject/master` 作为本地默认 ratchet 基准。这个修正和 ops inventory 同属维护质量建设，都是为了让后续版本能一气呵成地跑本地门，不被旧协作残留卡住。

这里继续遵守“禁止硬凑”。本项目这一版没有为了看起来像功能版本而新增一个只读 echo 接口，没有为了降低文件数而盲目移动类，没有把 Node 或 mini-kv 的任务拿来填充，也没有把已有 ratchet 原样复制一遍当成果。工作量来自真实维护需求：在一个 1,352 文件的 ops 包里建立拆分前地图，区分可迁移候选和 load-bearing 历史证据，让后续每一版拆分都有依据。这样的版本不改变用户可见 API，却会显著降低后续维护的盲目性。

从顶级工程师视角看，重构优化最忌讳第一刀切在最复杂处。`OperatorEvidenceValueSupply*` 数量最大，但也最复杂；如果第一版就动它，局部收益可能很大，失败面也会很大。v1796 的价值是把“小而可验证”的路线铺出来：先盘点，再选较小且测试齐全的族群演练，再推广到更大的历史链路。这个节奏比一口气搬几百个文件更稳，也更符合当前仓库仍需保持跨项目证据路径稳定的现实。

还有一层实际工作量是取舍。当前仓库已经有很多只读路线、证据回执和归档说明，如果每次看到重复就马上抽象，很容易得到一个更抽象但更难追踪的系统。v1796 没有先追求表面文件数下降，而是先回答三个维护问题：第一，哪些文件只是维护入口，可以放进新的窄包；第二，哪些文件虽然冗长却承担历史路径和下游引用，不能贸然移动；第三，哪个族群足够小、测试足够集中，适合当下一版迁移演练。这个取舍本身就是工程工作，不是写文档绕开重构。没有这一步，后面的拆分很可能只是在目录之间转移混乱。

我也刻意把本版本做成可测试的盘点，而不是一次性“大整理”。大整理看起来速度快，但在这个项目里风险更高，因为历史证据和跨项目读取关系太密。先用测试固定盘点入口，后面每次迁移一小族群，就能回答“路径有没有变、响应有没有变、归档有没有动、根包压力有没有降”这四个问题。只要这四个问题能连续回答，二十个后续版本也不会变成杂乱提交；反过来，如果没有这样的基线，再多版本也只是累积新债。

## 一句话总结

v1796 不新增业务能力，而是把 ops 包 1,352 个主源码文件的拆分前地图固化下来：当前根包压力、主要 route family、不可移动历史归档、优先 reduction candidates 和“不搬类”的停线都有文档和测试守卫，后续重构可以从更小、更可验证的位置开始。
