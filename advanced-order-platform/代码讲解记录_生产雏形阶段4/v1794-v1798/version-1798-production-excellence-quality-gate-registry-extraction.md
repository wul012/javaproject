# v1798 生产卓越代码讲解：代码讲解质量门注册表簇抽离

本版本是 v1797 之后的第二刀 ops 根包瘦身。它延续 v1797 建立的拆分范式，把代码讲解质量门注册表（code walkthrough quality gate registry）这一成熟只读证据簇，从拥挤的 `ops` 根包移动到更窄的 `ops.maintenance.walkthrough.qualitygate` 子包。选择这个簇有三条理由：其一，它结构上与 v1797 的合规注册表几乎同构，拥有 controller、service、response、support、renderer、若干 catalog 和一个本簇 route paths，复用 v1797 范式风险最低；其二，它的 service 在生成 endpoint 时只引用本簇自己的 route 常量 `CODE_WALKTHROUGH_QUALITY_GATE_REGISTRY`，没有跨簇引用别的 route family，迁移时不需要扩大无关包的可见性；其三，它已经有完整的 service/renderer/boundary/immutability/controller/routePaths 测试，能在不新增业务测试的前提下证明字节级行为不变。

## 入口路由

入口路由保持完全不变，仍是 `/api/v1/ops/shard-readiness/code-walkthrough-quality-gate-registry`。和 v1797 一样，`OpsShardReadinessCodeWalkthroughQualityGateRegistryController` 没有移出根包，它继续用根包里的 `OpsShardReadinessRoutePaths.BASE_PATH` 和 `OpsShardReadinessRoutePaths.CODE_WALKTHROUGH_QUALITY_GATE_REGISTRY` 作为 `@RequestMapping` 与 `@GetMapping` 的常量来源。Spring 的组件扫描覆盖 `com.codexdemo.orderplatform` 全包，因此 controller 即使保留在根包、依赖类移动到子包，也不会影响 bean 装配或路由注册。外部调用方看到的 URL、HTTP 方法、响应结构都没有任何漂移。

真正被移动的是 controller 之后的实现层：九个实现文件（boundary rule catalog、evidence anchor catalog、explanation rubric catalog、registry renderer、registry response、registry service、registry support、review checklist catalog、version rule catalog）加上本簇 route paths 类，共十份 main 源文件迁入新子包。这沿用了 v1797 的“根包管外部契约、子包管实现细节”边界：controller 是公开入口，留在根包；route 聚合表 `OpsShardReadinessRoutePaths` 是旧世界的聚合中心，也留在根包，只通过一个 import 继续委托到子包公开的后缀常量。

## 响应模型

`OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse` 被移动到新子包，但字段语义一字未改：它仍然记录 project、version、各类只读边界布尔位（readOnly、executionAllowed、startsJavaService、startsMiniKvService 等）、endpoint、profile、各类 count，以及 version rules、explanation rubrics、evidence anchors、review checklists、boundary rules、markdown sections、checks 等列表。响应继续保持 public，因为它是根 controller 的返回类型，且 Spring 需要稳定的公开类型做 JSON 序列化。catalog、renderer、support 多数仍是 package-private，只在子包内部协作——可见性分层与 v1797 一致：只公开 route paths、service、response 这些跨包确实需要的类型，不把整组实现一口气变成全局 API 面。

本版本刻意没有对 response 做字段重排或命名缩短，原因与 v1797 相同：本刀目标是证明拆包安全，不是顺手做模型优化；若同版本既移动文件又改字段，失败时难以区分是包边界问题还是模型行为问题。

## 上游证据配置

质量门注册表是对上游讲解质量治理规则的只读再表达，读取的是本项目已固化的版本规则、讲解评分细则（explanation rubric）、证据锚点（evidence anchor）、复核清单（review checklist）和边界规则，而不是数据库或远端服务。本版本移动了这些 catalog 类，迁移后它们同处 `qualitygate` 子包，主题内聚度反而更高：不再是散落在一千多个根包文件中的普通 Readiness 文件，而是“代码讲解质量门”这一维护主题的内聚证据配置。catalog 的数据本身没有改动——版本规则、评分细则、禁止写路由、禁止读取凭据实值、禁止解析裸 endpoint URL、禁止 managed audit 连接、禁止自动启动 Java 或 mini-kv 等规则全部保持旧值。结构整理与证据事实变更被严格分开。

新文档 `docs/ops/quality-gate-registry-extraction-v1798.md` 承担证据账本职责：列出十份被移动文件、声明 controller 与 route 聚合表留在根包、记录根包计数从 1,319 到 1,309 的变化，并重申 `a/` 到 `f/`、`e/<version>/`、evidence JSON、截图归档与历史代码讲解目录都不移动。

## 服务层核心流程

服务层核心流程仍由 `OpsShardReadinessCodeWalkthroughQualityGateRegistryService.registry()` 完成：依次读取 version rule、explanation rubric、evidence anchor、review checklist、boundary rule，再经 support 组装 response、经 renderer 输出 markdown。流程没有重排、没有新增副作用、没有把静态 catalog 改成注入式 bean。

唯一的服务层变化在 endpoint 生成方式，这也是复用 v1797 范式最关键的一点。原来 service 在根包内直接读取根路由表的 `BASE_PATH` 与 `CODE_WALKTHROUGH_QUALITY_GATE_REGISTRY`。移动到子包后，若继续访问根包 package-private 的 `OpsShardReadinessRoutePaths`，就必须把整张根路由表改成 public——这会让所有未来子包都倾向继续依赖根表，把“拥挤的文件夹”变成“被所有新包依赖的全局中心”。本版本没有这样做，而是把本簇 `OpsShardReadinessCodeWalkthroughQualityGateRoutePaths` 改为 public 并在其中新增包内 `BASE_PATH`，service 的 `ENDPOINT` 改由本簇 route paths 拼接。拼出的字符串与迁移前逐字节相同，对内却不再依赖根包私有类。根路由表仍通过一个 import 继续委托本簇公开的后缀常量，依赖方向因此从“子包回流根包”被切断为“根包代理子包”。

## Java 证据检查

第一层证据是文件计数。v1796 盘点记录 ops 主源码 1,352、根包直放 1,330、Readiness 命名 1,210；v1797 将根包直放降到 1,319。v1798 之后，ops 总数仍是 1,352、Readiness 仍是 1,210，根包直放下降到 1,309，正好是移动十份 main 文件的结果。`ReadabilityUpkeepGovernanceConsolidationPlanTests` 的 root ratchet 常量 `MAX_ROOT_OPS_MAIN_JAVA_FILES` 从 1,319 收紧到 1,309；后续若有人把文件放回根包，测试立即失败。

第二层证据是新增 `ReadabilityUpkeepOpsConsolidationExtractionV1798Tests`。它逐个检查十份实现文件在 `maintenance/walkthrough/qualitygate` 目录存在、在根 `ops` 目录不存在，确认 root controller 与 `OpsShardReadinessRoutePaths` 仍在根包，并读取 v1798 文档校验 `contract-preserving`、`1,309`、`Do not rename or move archive roots` 与原 endpoint 等关键短语；同时校验 `docs/ops/README.md` 已包含 `quality-gate-registry-extraction-v1798.md`、`ops.maintenance.walkthrough.qualitygate` 与 `1,319 to 1,309`。文档、源码、计数三者形成闭环。

第三层证据来自原有质量门簇测试。移动后的 service、renderer、boundary、immutability 测试与 test support 进入新包，仍验证 version、endpoint、profile、readOnly、executionAllowed、边界规则、markdown sections 与不可变列表；根包的 route path test 与 controller test 留在原处，继续证明根聚合表与 Spring controller 对外不漂移。

## 与 v1797 的一处差异：SpotBugs EI_EXPOSE 排除项迁移

本版本相对 v1797 多处理了一个真实障碍，值得如实记录。质量门 response record 含多个 `List` 成分，SpotBugs 会对其 accessor 与构造器报 `EI_EXPOSE_REP/REP2`（暴露内部可变表示）。在旧位置，这些 finding 是被 `config/spotbugs-exclude.xml` 按根包 FQN 显式排除并接受的——这正是本仓库对大量 Response DTO 的统一处理方式。把 response 移到子包后，类的全限定名变了，按旧 FQN 写的排除项不再匹配，于是这 16 条本已被接受的 finding 在新位置重新冒出，首轮 `mvnw verify` 因 SpotBugs 报 16 bugs 而失败。

修复方式是把这 16 条已接受 finding 对应的 4 条排除项（response 类与其嵌套 `MarkdownSection`，分属 REP 与 REP2 两段）的 FQN 从根包更新到 `ops.maintenance.walkthrough.qualitygate` 子包。这是“同一组已接受 finding 随类改名而迁移排除位置”，既不是新增 finding，也不是新增一条放松项，shrink-only 基线语义被完整保留。这与 v1797 的差别仅在于：v1797 的合规 response 当时用 `List.copyOf` 紧凑构造器消除了 finding，因而无需排除项；本簇 response 沿用的是仓库主流的“filter 排除”模式，因此随迁移更新 FQN 是与全仓库一致的处理。第二轮全量 verify 因此通过。

另外，为了让留在根包的 `OpsShardReadinessCodeWalkthroughQualityGateRoutePathsTests` 能跨包断言 endpoint，service 的 `ENDPOINT` 常量被改为 public——这与 v1797 合规 service 的处理完全一致。

## mini-kv 证据检查

本版本没有读取、启动、修改或整理 mini-kv。mini-kv 在此仍只是边界对象：讲解文档声明不自动启动，测试断言 `startsMiniKvService=false`，边界 catalog 保留 no-minikv-autostart 规则，但 Java 侧不会借这次拆包去触碰 C++ 仓库，也不新增任何跨仓耦合。结论明确：没有进程启动、没有文件移动、没有 archive 迁移、没有新增依赖、没有把 Java service 与 mini-kv runtime 绑定。

## 阻断与安全边界

安全边界没有放松。质量门注册表仍是 read-only registry：不接受请求体、不写库、不发消息、不连管理审计、不解析裸 endpoint URL、不读取凭据实值、不触发部署或回滚、不启动 Java 或 mini-kv 进程。移动包名不改变运行态行为，测试继续断言 `executionAllowed=false`、`readOnly=true` 与相关边界规则。历史归档不可移动这一条同样被文档与 extraction test 的关键短语检查固定。可见性边界也被克制处理：只把 route paths、service、response 暴露到跨包可用，其余 catalog/renderer/support 仍 package-private。

## 测试覆盖

闭环以 Spotless 规整开始（`spotless:apply` 退出 0），随后跑全量 `mvnw verify`。首轮在 SpotBugs 阶段失败（16 条 EI_EXPOSE，原因如上节所述）；按 shrink-only 语义迁移排除项 FQN 后复跑。第二轮 `mvnw verify` **BUILD SUCCESS，用时 6 分 23 秒**：1492 个测试 0 失败 0 错误（含新增 v1798 extraction 测试与迁移后的质量门簇测试）、JaCoCo「All coverage checks have been met」（逐包 floor 在文件移动后依然满足）、SpotBugs check 通过（16 条 finding 由迁移后的排除项重新覆盖，0 条新增）、Spotless ratchet 干净。这一全量门禁同时覆盖编译、包边界、文档护栏、覆盖率逐包 floor、静态分析 ratchet 与格式 ratchet。

## 实际工作量说明

本版工作量集中在“安全地复用一套已验证的拆分范式，并处理它与本簇细微差异带来的真实障碍”。具体步骤：先对 CandidateDocument 与剩余 CodeWalkthrough 各子簇做形态普查，发现 CandidateDocument 各子簇的非 controller 文件都引用根包私有路由表、且 Handoff 子簇还跨簇引用了 OperatorEvidenceValueSupply 常量，不如质量门簇干净；据此选定质量门注册表作为本刀对象。随后用 `git mv` 移动十份 main 文件与五份包内测试，改写包声明；把本簇 route paths 改 public 并新增 `BASE_PATH`；repoint service 的 `ENDPOINT` 到本簇常量、并把 `ENDPOINT` 改 public；为根 controller、根路由聚合表、根 controller test、根 route paths test 补齐对子包公开类型的 import，并把根 controller test 的 `TestSupport.service()` 改为 `new Service()`（复刻 v1797 让根测试不依赖包内 test support 的做法）。

首轮全量 verify 暴露 SpotBugs 障碍后，没有绕过、没有放宽 ratchet、也没有删字段，而是把 response 与其嵌套类已接受的 EI_EXPOSE 排除项 FQN 从根包迁到子包，保持 shrink-only 基线不被破坏，再复跑确认全绿。文档层不是事后补行：新增 `docs/ops/quality-gate-registry-extraction-v1798.md`，更新 `docs/ops/README.md` 入口表与说明段，新增 `CHANGELOG.md` v1798 条目，更新 `docs/production-excellence-progress.md` 的 J8 行，并新增 extraction 守护测试与收紧 ratchet。

这段说明也回应“禁止硬凑”的要求：真正需要被记录的，是为什么 controller 不动、为什么 route paths 只公开后缀、为什么 service 改用本簇常量而不把根表 public、为什么 catalog 数据不改、为什么 SpotBugs 排除项是迁移而非新增、为什么根包计数收紧、为什么归档与 mini-kv 不参与。把这些工程判断写清，后续继续抽离相邻簇（depth、quality audit 等）时才能直接复用，而不必重新踩坑。需要特别提醒后续维护者：凡是用 filter 排除 EI_EXPOSE 的 Response，被移动时都要同步迁移其排除项 FQN，否则全量 verify 会在 SpotBugs 阶段失败——这是本簇与 v1797 合规簇（用 copyOf）最容易被忽略的差异。

## 一句话总结

v1798 把代码讲解质量门注册表的实现层从拥挤的 Java ops 根包抽到 `ops.maintenance.walkthrough.qualitygate`，在不改路由、不改响应、不动归档、不启动外部系统的前提下把根包直放文件从 1,319 降到 1,309；其间按 shrink-only 语义把 response 已接受的 SpotBugs EI_EXPOSE 排除项随类改名迁移，最终全量 `mvnw verify` BUILD SUCCESS（1492 测试、覆盖率达标、SpotBugs 与 Spotless 干净），并用文档与测试把这条拆分范式继续固定。
