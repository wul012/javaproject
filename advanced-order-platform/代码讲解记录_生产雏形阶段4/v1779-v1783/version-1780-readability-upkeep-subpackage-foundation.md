# 第一千七百八十版代码讲解：可读性保养 registry 子包地基

本版目标是落实 Java 可读性保养建议中的第二步：新增文件先进入子包，不再继续把后期治理类平铺到 `com.codexdemo.orderplatform.ops` 根包。v1779 已经建立 `docs/ops` 阅读入口，v1780 则开始把新增 registry 的代码地基放进 `com.codexdemo.orderplatform.ops.maintenance.readability`。这一版只增加 route paths、response record 和 catalog，不暴露 controller，也不启动服务。

它不是搬迁旧代码。已有 `OpsShardReadiness*`、`OpsShardReadinessCodeWalkthrough*`、`OpsScreenshotExplanation*` 文件暂时保留在原位置。用户要求“工程后期保养”，不是一次性大重构；Java 建议也明确说不建议一次性大拆。v1780 的原则是新增代码先按主题入包，旧文件等待后续有明确收益时再做小范围迁移。

## 入口路由

本版新增的路由入口是 `ReadabilityUpkeepRoutePaths`，包路径是 `src/main/java/com/codexdemo/orderplatform/ops/maintenance/readability/ReadabilityUpkeepRoutePaths.java`。它定义 `BASE_PATH = "/api/v1/ops/readability"` 和 `UPKEEP_REGISTRY = "/upkeep-registry"`。目前这只是子包内的路径地基，尚未由 controller 暴露为 HTTP 接口。

为什么不继续把常量写进 `OpsShardReadinessRoutePaths`？因为本版的主题不是 shard readiness 功能推进，而是 ops 可读性保养。继续追加到根包大 route paths 文件，会和建议目标相反：我们想降低 `ops` 根包阅读成本，而不是让它继续变长。新子包自己的 route paths 能表达新主题，也能避免跨 package-private 常量访问。

路径选择为 `/api/v1/ops/readability/upkeep-registry`，不是 `/api/v1/ops/shard-readiness/...`。这能让读者看出本 registry 面向 ops 可读性保养，而不只是 shard readiness 的一条新能力。后续 controller 暴露时，入口会自然对齐 v1779 的 `docs/ops` 主题索引。

## 响应模型

本版新增 `ReadabilityUpkeepRegistryResponse`。这个 response record 是后续可读性保养 registry 的结构化模型，包含项目名、版本、只读边界、endpoint、profile、sourceAdvice、docsRoot、packageRoot、registryState，以及 topic、package rule、template rule、class name trial、boundary rule、verification step、markdown section、checks 和 status。

响应模型拆成多个 nested record：`TopicMap` 表达主题地图，`PackageRule` 表达新增文件入子包规则，`RegistryTemplateRule` 表达 registry 模板层要求，`ClassNameTrial` 表达长类名收敛试点，`BoundaryRule` 表达禁止动作，`VerificationStep` 表达验证步骤，`MarkdownSection` 表达人读摘要。这样模型不是一堆字符串，而是把 Java 建议拆成可查询的结构。

这个 response 还提前保留了运行时边界字段：`readOnly`、`executionAllowed`、`startsJavaService`、`startsMiniKvService`、`writesBusinessState`、`readsCredentialValue`、`resolvesRawEndpointUrl`、`managedAuditConnectionAllowed`。这些字段会在后续 service/support 层填充并测试。v1780 先把字段放进模型，是为了保证后续实现不会遗漏边界。

## 上游证据配置

本版的上游证据仍然只来自 Java 专属建议文件：`D:\C\四项目理解统筹\06-四项目可读性保养建议\Java订单治理\Java订单治理可读性保养建议.md`。该文件提出 Java 保养节奏：ops 索引、新增文件入子包、registry 模板、长类名收敛试点。v1780 对应的是第二步和第四步的准备：新增代码进入子包，并建立 class name trial catalog。

本版不读取 Node、mini-kv、aiproj 的建议文件，也不修改它们。虽然总索引提到四项目，但实现必须回到 Java 本项目。把新增包命名为 `ops.maintenance.readability`，就是为了把工作量放在 advanced-order-platform 的 ops 维护面，而不是跨仓库铺开。

上游建议还强调 Java 当前结构“清晰但过长、过平铺”。v1780 的响应模型和目录规则正是对这个判断的工程化回应：不否定既有结构，不推倒旧代码，只让新增维护类从一开始就带主题包名和较短类名。这样后续版本可以用实际子包作为试点，而不是只在文档里说“以后应该拆包”。

## 服务层核心流程

本版还没有 `ReadabilityUpkeepRegistryService`，但已经建立后续服务会组合的目录。`ReadabilityTopicCatalog` 返回五个主题：shard readiness、walkthrough quality、archive layout、blocked execution context、evidence registry。`ReadabilityPackageRuleCatalog` 返回四条包规则：当前可读性 registry 子包、未来 walkthrough depth 子包、未来 archive layout 子包、旧根包保留。`ReadabilityRegistryTemplateCatalog` 返回十条 registry 模板规则，覆盖 response version、endpoint、profile、read-only transaction、response record、catalog data、renderer、support、controller、tests。

`ReadabilityClassNameTrialCatalog` 是本版最贴近“长类名收敛试点”的地基。它没有改旧类名，而是记录新子包中可以去掉根前缀的策略。例如旧模式可能写成 `OpsShardReadinessReadabilityUpkeepRegistryService`，新子包里使用 `ReadabilityUpkeepRegistryService` 就足够，因为包名已经表达 ops maintenance readability 的上下文。这个试点是温和的：新文件先短名，旧文件不强迁。

`ReadabilityBoundaryCatalog` 和 `ReadabilityVerificationCatalog` 分别记录禁止动作和后续测试步骤。这样 service 出现前，数据地基已经足够完整。后续 v1781 只需要组合这些 catalog、渲染 markdown sections、计算 counts 和 status，并用 controller 暴露。

## Java 证据检查

Java 证据第一层是新增包路径本身：`src/main/java/com/codexdemo/orderplatform/ops/maintenance/readability`。这证明本版没有继续平铺到 `ops` 根包。第二层是类名收敛试点：新增类名使用 `ReadabilityUpkeep...`、`ReadabilityTopicCatalog`、`ReadabilityBoundaryCatalog` 这类短名，而不是继续重复 `OpsShardReadinessCodeWalkthrough...` 全路径。第三层是 catalog 拆分：主题、包规则、模板规则、类名试点、边界、验证分别在独立文件里，避免形成新的巨型类。

这些 Java 证据都属于后期保养，而不是业务功能。没有修改订单 domain、repository、application service、outbox、approval、failed event replay，也没有改任何数据库迁移。新增代码处在 ops maintenance readability 子包，表达的是治理元信息和可读性规则。

本版还为后续测试保留清晰断言点。测试可以断言 topics 数量、package rules 数量、template rules 数量、class name trials 数量、boundary denied 数量和 verification steps 数量；也可以断言新增类名不含 `OpsShardReadiness` 前缀。可测试性是这版地基的重要价值。

## mini-kv 证据检查

本版不消费 mini-kv 证据。新增 catalog 里有 `no-minikv-autostart` 边界，说明可读性保养不启动 mini-kv。除此之外，不读取 mini-kv 文件，不连接 mini-kv 端口，不要求 mini-kv 运行，也不生成 mini-kv fixture。

mini-kv 在本版讲解中只作为禁止动作出现。这样写不是为了凑篇幅，而是因为当前项目长期涉及 Java、Node、mini-kv 的只读协作。如果不说明 mini-kv 边界，读者可能误以为 ops readability registry 会触发跨项目检查。v1780 明确：不会。

## 阻断与安全边界

本版阻断所有业务写和运行时动作。`ReadabilityBoundaryCatalog` 中的八条规则全部 `allowed=false`：no-write-routing、no-active-shard-router、no-credential-value、no-raw-endpoint-url、no-managed-audit-connection、no-deployment-or-rollback、no-java-autostart、no-minikv-autostart。后续 support 层会统计这些 denied boundary，测试也会确保数量一致。

credential value 和 raw endpoint 尤其要单独强调。可读性地图可能提到 endpoint、route、profile、evidence、registry，但这不代表可以解析真实 endpoint 或读取密钥。catalog 中的禁止项把这种误读提前挡住。

部署和回滚也关闭。后期保养版本即使修改了 docs 和 registry，也不是发布执行工具。它只提供维护者阅读和审查的证据。任何 runtime read window、deployment、rollback、managed audit 连接，都不属于本批五版。

## 测试覆盖

本版先增加可测试数据结构，后续 v1781 会新增 `ReadabilityUpkeepRoutePathsTests`、`ReadabilityUpkeepRegistryServiceTests`、`ReadabilityUpkeepRegistryRendererTests`、`ReadabilityUpkeepBoundaryTests` 和 controller tests。v1782 会新增 docs compliance tests。v1783 会收口 class name trial 和版本响应。

当前本版至少需要通过编译和现有讲解合规测试，因为新增 v1780 讲解会被扫描。整批完成后需要跑定向测试，覆盖 readability 子包、docs/ops、OpsCodeWalkthroughArchiveComplianceTests，再跑全量 Maven 和 CI。

## 实际工作量说明

本版的实际工作量在本项目 Java 源码结构上。它没有靠文档空谈“以后拆包”，而是实际新增 `ops.maintenance.readability` 子包，并把 route paths、response record、topic catalog、package rule catalog、registry template catalog、class name trial catalog、boundary catalog、verification catalog 都放进该子包。这个工作量直接回应 Java 建议中的“新增文件入子包”和“长类名收敛试点”。

这不是硬凑。每个 catalog 都对应后续维护要用的真实信息：topic catalog 对应 v1779 的三张地图和两个治理主题，package rule catalog 对应新增文件入子包的执行边界，registry template catalog 对应后续新增 registry 的标准层次，class name trial catalog 对应长类名可读性风险，boundary catalog 对应只读安全边界，verification catalog 对应后续测试计划。若没有这些文件，v1781 的服务只能临时拼字符串，v1782 的模板也没有代码侧参照。

本版还刻意控制范围，避免把工作量变成危险重构。它没有移动旧文件，没有改根路由，没有删除长类名，没有试图把所有 ops 主题一次性拆完。它做的是新增代码的“止血”：从这一版开始，后期保养相关的新代码有自己的包、自己的短名、自己的模板数据和自己的边界目录。

这里还要把“本项目”和“禁止硬凑”说得更具体。本版新增的每个 Java 文件都放在 advanced-order-platform 自己的源码树里，包路径、类名、响应模型、目录规则、边界规则、验证计划都围绕这个项目的 `ops` 可读性问题展开。它没有为了凑版本去修改外部建议目录，也没有把 Node、mini-kv 或 aiproj 的内容搬进 Java 代码。所谓只做本项目，就是让所有可编译、可测试、可提交的产物都归属于当前 Java 仓库。

禁止硬凑在本版里体现为“用结构补工作量”。如果只是写一段文档说以后应该进子包，那很快会被忘记；如果只新建一个空包，也没有维护意义。v1780 同时新增 route paths、response record、topic catalog、package rule catalog、template catalog、class name trial catalog、boundary catalog、verification catalog，是为了让后续 service 可以直接组合这些结构。每个 catalog 都有稳定 code 和明确职责，后续测试能按数量和内容断言。

再从维护者视角看，这一版解决的是阅读成本里的两个根因。第一个根因是主题混杂：所有治理类都挤在 `ops` 根包时，读者分不清哪些是 shard readiness，哪些是讲解质量，哪些是归档布局。第二个根因是名字重复：类名为了准确不断叠加上下文，最后每个文件都像一条完整业务路径。v1780 用子包承载上下文，让类名回到局部职责，这就是后期保养，不是风格洁癖。

本版没有写测试并不是遗漏，而是有意把版本切开。v1780 建地基，v1781 服务化并补测试，v1782 增加模板门禁，v1783 做类名试点收口。这样的拆分让每一版都能解释自己的实际工程面。如果把全部代码和测试压进一版，讲解会变成巨大混合说明，也不利于审查。五版连续推进的意义正在于让保养动作一层一层落地。

最后，本版没有移动旧类，是为了保护历史稳定性。旧类名虽然长，但已有测试、tag 和讲解引用；贸然改名会影响大量文件。新增子包先试点短名，是一种可回滚、可观察、可扩展的做法。未来如果某个旧 registry 确实需要维护，再按这个模板迁移，而不是为了追求整齐一次性改动全部历史。

从实际维护场景看，本版会减少三类未来成本。第一类是查找成本：新增可读性保养代码集中在一个子包，读者不用在根包里混着找。第二类是命名成本：包名承担上下文后，类名可以更短，新增文件列表更容易扫读。第三类是审查成本：catalog 拆开后，审查者可以单独看主题、包规则、模板规则、边界规则，不必在一个大类里判断所有变化。

这个收益也会体现在后续测试中。v1781 会把这些 catalog 组合为 registry，v1782 会把模板写入文档门禁，v1783 会检查新子包文件名没有退回长前缀。也就是说，v1780 的文件不是一次性说明，而是后续版本的输入。若缺少这一版，后面三版只能变成空壳；有了这一版，后续每个测试都有明确数据来源。

更具体地说，`ReadabilityPackageRuleCatalog` 不只是列包名，它表达“新增先入子包、旧根包暂保留”的迁移策略；`ReadabilityClassNameTrialCatalog` 不只是列名字，它表达“包名承载上下文后类名才可收敛”的条件；`ReadabilityBoundaryCatalog` 不只是列禁止动作，它把可读性保养和运行时执行隔开。这些都对应本项目已经出现的阅读压力。

这版还把“先做新增、暂不搬旧”的工程判断写进了代码。很多后期保养失败，是因为一开始就追求全局整齐，结果把历史接口、测试、讲解、tag 引用一起搅动。v1780 反过来做：旧代码保持稳定，新代码先按正确形态生长。这样项目会从新增长点开始变清楚，历史部分则等真正需要维护时再迁移。

对审查者来说，这个策略也更友好。审查 v1780 时，只需要确认新子包是否合理、catalog 是否覆盖建议、边界是否全部拒绝，而不用同时评估几百个旧文件改名是否安全。后期保养不是炫技，能让审查者看明白、能让测试逐步跟上，才是长期可维护的做法。

这也是本版和普通整理的区别。普通整理可能只改目录名，实际没有降低理解难度；本版把主题、模板、命名、边界、验证都拆成可组合数据，后续服务可以直接使用。维护者看到这些目录，就能知道下一步应该如何接 service、如何写测试、如何写文档，而不是重新设计一套规则。

因此，v1780 的工作量虽然集中在地基，却不是轻量到可以忽略。它把后期保养的方向压进源码结构，让后续版本有明确承接点。这种地基版本如果做薄了，后续服务和测试都会缺少真实内容。

更直白地说，本版是在给后续维护立规矩：新增治理代码先问主题归属，再决定包名，再决定类名，再决定目录数据，最后才考虑暴露接口。这个顺序能防止代码继续无序增长，也能让每个新增文件都有明确位置。对本项目这种长期快速推进的仓库来说，这种规矩比一次局部美化更有价值。

这条规矩后续可以反复复用：先定位主题，再写窄包，再补目录，再补测试。它让新增维护代码天然带着阅读路径。

这会让本项目后续每次新增治理能力时都少一次迷路，少一次重复命名，少一次无边界扩张。

这正是本版最朴素也最关键的维护价值。

后续维护会因此更顺。

读者也能更快确认新增文件属于哪条保养线。

这会让项目在继续增长时仍然保持清楚入口，也让每次新增维护能力都有稳定归宿。

这种归宿感很重要。

它能让后续阅读更省力。

## 一句话总结

v1780 在 Java 本项目新增 `ops.maintenance.readability` 子包和可读性保养 registry 地基，用主题包名、结构化响应、拆分 catalog 和短类名试点阻止新增治理代码继续平铺到 `ops` 根包，同时保持所有运行时边界关闭。
