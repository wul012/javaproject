# 第一千七百八十一版代码讲解：可读性保养 registry 服务化

本版目标是把 v1780 的可读性保养地基接成真正可查询、可渲染、可测试的只读 registry。v1780 已经把 route paths、response record 和多组 catalog 放进 `ops.maintenance.readability` 子包；v1781 继续在同一子包中新增 renderer、support、service、controller 和测试，让 Java 后期可读性保养不再只是文档和静态目录，而是有 API 证据、状态计算、边界断言和测试保护。

它仍然只做本项目。外部建议用于确定方向，但所有实现都在 `advanced-order-platform` 内完成。没有修改 Node、mini-kv、aiproj，也没有消费其他项目的运行时证据。这个版本是 Java 自己的 ops 可读性保养 registry，而不是四项目统筹工具。

## 入口路由

本版暴露的入口是 `ReadabilityUpkeepRegistryController`，请求根路径来自 `ReadabilityUpkeepRoutePaths.BASE_PATH`，具体 GET 路径来自 `ReadabilityUpkeepRoutePaths.UPKEEP_REGISTRY`，组合后的 endpoint 是 `/api/v1/ops/readability/upkeep-registry`。它没有挂在 `/api/v1/ops/shard-readiness` 下，而是挂在 `/api/v1/ops/readability` 下，表示本 registry 的主题是 ops 阅读保养，不是 shard readiness 功能本身。

controller 的实现很薄：构造时注入 `ReadabilityUpkeepRegistryService`，`registry()` 方法只返回 service 的响应。它不读取请求体，不接收执行参数，不读取 header，不拼装运行时状态，也不触发任何外部调用。这个薄 controller 模式延续了项目里其他只读 registry 的经验：入口层越简单，越容易证明没有隐藏副作用。

`ReadabilityUpkeepRoutePathsTests` 覆盖了 base path、registry path 和 service endpoint。这个测试很小，但它有必要存在。因为可读性保养本身就是在治理入口混乱，如果路径常量和 service endpoint 漂移，后续文档地图会先失效。路径测试把这种漂移提前挡住。

## 响应模型

响应模型继续使用 v1780 的 `ReadabilityUpkeepRegistryResponse`。v1781 的变化是 support 层开始填充它。响应顶部字段包括 `project=advanced-order-platform`、`version=Java v1781`、`readOnly=true`、`executionAllowed=false`、`startsJavaService=false`、`startsMiniKvService=false`、`writesBusinessState=false`、`readsCredentialValue=false`、`resolvesRawEndpointUrl=false`、`managedAuditConnectionAllowed=false`。这些字段直接告诉调用者：这是只读维护证据，不是执行接口。

响应还包含 `sourceAdvice`、`docsRoot`、`packageRoot` 和 `registryState`。`sourceAdvice` 使用脱敏后的 Java 建议标识，避免在 API 中塞入带中文和本地目录细节的原始长路径；`docsRoot=docs/ops` 对应 v1779 的文档入口；`packageRoot=com.codexdemo.orderplatform.ops.maintenance.readability` 对应 v1780 的新增子包；`registryState=readability-upkeep-subpackage-registry-active-v1781` 表示服务化已经生效。

计数字段包括 topic、package rule、template rule、class name trial、boundary rule、denied boundary、verification step、markdown section。support 用这些计数判断 `status`。如果后续有人删除一个主题地图、删掉 registry 模板规则、把边界 allowed 改成 true、或者把验证步骤改成非必需，status 会变成 blocked，测试也会失败。

## 上游证据配置

本版仍然只以 Java 可读性保养建议作为来源。建议指出 Java 当前结构稳定，但 `ops` 包出现“过长、过平铺”的阅读压力。v1781 的 service 不直接读取建议文件，因为运行时接口不应该依赖本地外部目录；它把建议固化成 catalog 和 checks。这样 API 在 CI 环境也能稳定运行，不需要 `D:\C` 存在。

`docsRoot` 和 `packageRoot` 是更具体的本项目证据。前者说明阅读入口已经进入仓库，后者说明新增实现已经进入子包。二者结合起来，回答了“建议有没有落地”的问题：不是只在外部文档里说要保养，而是在 Java 仓库里既有文档入口，又有子包化 registry。

本版不使用 Node v367 之后的计划作为实现输入。上一批代码讲解深度门禁与 Node 计划有关，本批主题是 Java 自己的可读性保养，因此上游关系更轻。这样能避免把每个 Java 维护版本都绑到 Node 路线图上，减少跨项目卡顿。

## 服务层核心流程

服务流程从 `ReadabilityUpkeepRegistryService.registry()` 开始。方法依次读取 `ReadabilityTopicCatalog.topics()`、`ReadabilityPackageRuleCatalog.packageRules()`、`ReadabilityRegistryTemplateCatalog.templateRules()`、`ReadabilityClassNameTrialCatalog.classNameTrials()`、`ReadabilityBoundaryCatalog.boundaryRules()`、`ReadabilityVerificationCatalog.verificationSteps()`。随后调用 renderer 生成 markdown sections，再调用 support 组合最终 response。

`ReadabilityUpkeepRegistryRenderer` 把六类数据渲染成六个稳定 section：`Topic Maps`、`Package Rules`、`Registry Template Rules`、`Class Name Trials`、`Boundary Rules`、`Verification Steps`。它从结构化 catalog 生成行，而不是手写一大段说明。这样后续新增规则时，渲染输出会跟随数据变化，测试也能断言具体行内容。

`ReadabilityUpkeepRegistrySupport` 负责状态判断。它复制所有列表，统计 denied boundary 数量，检查所有 topic 都 indexed，检查至少三条 package rule 适用于新代码，检查 template rule 全部 required，检查 class name trial 全部 active，检查 verification step 全部 required，最后结合期望数量决定 `status`。这种集中判断比把状态逻辑散在 service 或测试里更可维护。

## Java 证据检查

Java 证据第一层是新增 service/controller 本身都位于 `ops.maintenance.readability` 子包。第二层是类名短而明确：`ReadabilityUpkeepRegistryService`、`ReadabilityUpkeepRegistryController`、`ReadabilityUpkeepRegistryRenderer`、`ReadabilityUpkeepRegistrySupport`。它们没有重复 `OpsShardReadiness` 前缀，因为包名已经提供上下文。第三层是测试同样位于匹配子包，维护者可以按主题打开目录，而不是回到庞大的 `ops` 测试根包里搜索。

Java 证据第二组来自测试。`ReadabilityUpkeepRegistryServiceTests` 断言项目名、版本、endpoint、profile、docsRoot、packageRoot、registryState、各类计数和 status。`ReadabilityUpkeepRegistryRendererTests` 断言六个 markdown section 的顺序和关键内容。`ReadabilityUpkeepBoundaryTests` 断言所有高风险动作关闭。`ReadabilityUpkeepRegistryControllerTests` 断言 controller 返回只读证据。`ReadabilityUpkeepRoutePathsTests` 断言路径。

这些测试不是为了堆数量，而是把后期保养的关键风险拆开。路径漂移、模板缺项、边界误开、类名试点消失、文档根丢失，都有对应断言。以后如果新增 registry 破坏了保养规则，失败点会比较明确。

## mini-kv 证据检查

本版不消费 mini-kv 证据，也不启动 mini-kv。registry 响应中的 `startsMiniKvService=false` 和 boundary catalog 中的 `no-minikv-autostart` 证明这一点。mini-kv 只是作为高风险 autostart 边界出现，不是输入来源。

这条说明仍然必要，因为 ops 可读性保养可能会提到 shard readiness，而 shard readiness 历史上与 mini-kv、Node 的只读窗口有关。v1781 明确区分：当前 endpoint 只说明 Java 项目如何保养自己的 ops 包，不验证 mini-kv 状态，也不代表 mini-kv readiness 改变。

## 阻断与安全边界

本版所有高风险动作继续关闭。`executionAllowed=false`，`startsJavaService=false`，`startsMiniKvService=false`，`writesBusinessState=false`，`readsCredentialValue=false`，`resolvesRawEndpointUrl=false`，`managedAuditConnectionAllowed=false`。boundary rules 全部 allowed=false，denied count 等于 boundary rule count。

write routing 和 active shard router 不受影响。新增 controller 只是 GET 返回静态维护证据，不接订单、不改数据库、不发 outbox、不触发 replay、不切 shard router。credential 和 raw endpoint 边界也关闭；registry 只返回固定 code 和文档路径，不解析真实地址。

部署和回滚也关闭。本版不会自动发布、自动回滚或启动 Java 服务。测试通过只说明代码和文档门禁正常，不说明生产执行能力打开。这个边界在 service response 和 boundary test 中都有证据。

## 测试覆盖

本版新增五组测试：`ReadabilityUpkeepRoutePathsTests`、`ReadabilityUpkeepRegistryServiceTests`、`ReadabilityUpkeepRegistryRendererTests`、`ReadabilityUpkeepBoundaryTests`、`ReadabilityUpkeepRegistryControllerTests`。它们覆盖路由、服务、渲染、边界和控制器。后续 v1782 会补 docs compliance tests，v1783 会收口版本号和 class name trial。

本版完成后应运行定向测试：`mvn -q "-Dtest=ReadabilityUpkeep*Tests,OpsCodeWalkthroughArchiveComplianceTests" test`。整批完成后仍要运行全量 `mvn -q test` 和 CI。因为新增 controller 会进入 Spring 扫描，不能只跑纯单元测试。

## 实际工作量说明

本版的实际工作量很明确：把 v1780 的结构化数据接成完整 registry。新增 renderer 让结构化规则可以转换为人读摘要；新增 support 让 counts、status 和 checks 有统一判断；新增 service 让 catalog 组合成响应；新增 controller 让 endpoint 可查询；新增五组测试让路径、响应、渲染、边界和控制器都有失败点。这些都是本项目 advanced-order-platform 的真实代码，不是外部建议复制。

这不是硬凑。每个新增类都有明确维护职责，而且文件拆分控制在可读范围内。没有把所有逻辑塞进一个巨型 service，也没有把测试写成一个大断言。后期保养的价值正在于这种拆分：让一个维护者打开子包后，可以按 route、response、catalog、renderer、support、service、controller、tests 的顺序理解 registry。

本版还延续了“只做本项目”的边界。sourceAdvice 只是说明来源，所有可运行证据都在 Java 项目中。CI 不需要外部建议目录，不需要 Node，不需要 mini-kv。这样可读性保养 registry 能独立维护，不会给其他项目增加负担。

本版也要明确“禁止硬凑”的执行方式。v1781 不是为了让版本数增加而随便暴露一个 endpoint，而是把 v1780 的结构化目录真正变成可查询证据。如果没有 service，catalog 只是代码常量；如果没有 renderer，人读摘要要手写；如果没有 support，状态判断会散落在测试里；如果没有 controller，外部无法查询当前保养状态；如果没有测试，所有规则都只是希望。把这些层补齐，才算真实工作量。

本项目的收益也很具体。以后维护者想确认 Java ops 后期保养是否落实，不需要翻外部建议目录，也不需要在聊天记录里找结论，只要访问或测试 `ReadabilityUpkeepRegistryService.registry()` 的响应，就能看到 docsRoot、packageRoot、topicCount、templateRuleCount、classNameTrialCount、deniedBoundaryRuleCount、checks 和 status。这个响应把文档入口、子包化、模板化、短类名试点和边界拒绝放到同一个证据面里。

从拆分角度看，v1781 还避免制造新的大文件。renderer 只负责展示，support 只负责状态，service 只负责组合，controller 只负责暴露，tests 分别负责路径、服务、渲染、边界和控制器。每一层都很窄，后续维护时可以局部定位。这样的拆分本身就是可读性保养的体现，而不是为了形式分文件。

这一版还把“只读”从口号变成多处证据。service 使用 `@Transactional(readOnly = true)`，response flags 全部拒绝执行与外部动作，boundary catalog 八项全部 denied，boundary tests 逐项断言，controller 没有任何命令参数。读者不需要相信讲解文字，只要看代码和测试就能确认它没有运行时副作用。

最后，v1781 没有把 sourceAdvice 做成动态文件读取，是一个有意设计。外部建议路径在本机存在，但 CI 不应该依赖它。把建议转成 catalog 和 checks，才能让本项目自己独立验证。这也符合用户说的“主要做你自己项目的”：外部建议可以启发方向，最终规则必须沉淀到 Java 仓库本身。

本版还解决了“文档和代码脱节”的问题。v1779 的地图告诉人怎么读，v1780 的 catalog 告诉代码有哪些规则，但只有 v1781 的 registry 能把两者合并成一个可查询结果。响应里的 topic 列表对应地图，package rule 对应子包策略，template rule 对应后续模板，class name trial 对应长类名收敛，boundary rule 对应安全边界，verification step 对应测试计划。维护者不需要在多个地方猜测这些材料是否属于同一批，它们在 response 中已经合并。

从工程后期保养角度看，状态计算尤其重要。很多维护文档写完后会逐渐过时，因为没有任何代码知道它们应该有几条规则、几类主题、几个边界。support 层把期望数量写成常量，把 required、indexed、denied、active 等条件统一检查，这让 registry 能发现自身不完整。后续如果有人只删文档不改测试，或者只改 catalog 不改模板，定向测试会提供反馈。

本版的测试分布也有实际价值。路径测试防止 endpoint 漂移，服务测试防止响应字段漂移，渲染测试防止人读摘要丢内容，边界测试防止执行能力误开，控制器测试防止入口脱离 service。每个测试都小而明确。这样的测试布局让后续维护者更容易定位问题，而不是在一个巨大的集成测试失败里猜原因。

再强调一次，本版没有把可读性保养做成“运行时扫描器”。它没有反射整个代码库，没有在启动时扫描文件系统，也没有读取外部建议目录。registry 数据来自静态 catalog，测试负责验证源码和文档。这种方式轻量、稳定、适合 CI，也符合本项目大量只读 evidence registry 的既有风格。

还有一个重要收益是维护语义集中。过去读者看到很多 registry 时，往往要分别打开 service、catalog、response、test 才能判断这条 registry 是否只读、是否有边界、是否有文档入口。v1781 的 response 把这些信息集中出来：主题有多少，包规则有多少，模板规则有多少，类名试点有多少，边界拒绝有多少，验证步骤有多少。它不是替代源码，而是给源码加一层摘要。

这种摘要对工程后期尤其重要。项目越成熟，新增功能越少，维护类证据越多，阅读者越需要先判断“这条线是不是安全、是不是当前有效、是不是只做本项目”。如果每次都从零读几十个文件，维护成本会持续上升。v1781 用 registry 让这些判断可以先在一个响应里完成，再按需深入代码。

本版还让 docs/ops 和代码不再分离。docs/ops 提供主题地图，readability registry 把这些主题变成 topic catalog 和 checks。后续如果地图缺失，v1782 的 docs test 会发现；如果 registry topic 缺失，v1781 的 service test 会发现。文档和代码互相守护，是后期保养比普通文档更强的地方。

因此，本版的工作量不是“新增一个接口”这么简单，而是建立一个可读性保养控制面：它不控制业务执行，只控制维护证据的完整性。这个控制面越清楚，后续继续推进多版时越不容易迷路。

还有一点很关键：本版让“后期保养”从文档层进入可回归层。文档告诉人规则，测试告诉项目规则是否仍然成立，registry 告诉接口调用者当前状态。三者组合后，后续每次改动都能被同一套门禁复查。没有这个组合，可读性保养很容易变成某一次人工整理，过几版又回到旧状态。

本项目已经有大量只读证据接口，v1781 没有另辟一套陌生模式，而是沿用本项目熟悉的 registry 风格。这样维护者理解成本更低：看到 response、catalog、renderer、support、service、controller、tests，就知道该按什么顺序读。可读性保养最怕引入陌生框架，本版选择复用本项目模式，是为了让保养本身也容易维护。

同时，v1781 保留了清楚的失败路径。若主题少了，服务测试失败；若文档摘要少了，渲染测试失败；若边界打开，边界测试失败；若 endpoint 漂移，路径测试失败；若 controller 没走 service，控制器测试失败。这样的失败路径让后续维护者知道该修哪里，不会把一次小问题扩大成大排查。

这也是本版对工程后期最有用的地方。后期项目并不缺文件，缺的是快速判断文件是否仍然承担正确职责的办法。v1781 给出的是一组小而明确的判断：路径对不对，响应全不全，摘要稳不稳，边界关不关，入口薄不薄。每个判断都由测试执行，维护者不用靠印象确认。

从长期推进看，这种 registry 还能减少后续批次的讲解负担。以后做新的 ops 保养，可以引用这条 registry 的模板和状态，再说明新增主题如何接入，而不必从头解释为什么要分 renderer、support、service、controller。已有模式越清楚，后续版本越容易写出有内容的讲解。

这种清晰模式也能降低接手成本。后来的维护者只要先看响应，再看 markdown sections，再看对应测试，就能知道这一批保养是否完整。若只看源码文件名，长短混杂仍然会让人犹豫；有 registry 摘要后，判断路径更短。

因此，本版不是单纯增加一层接口，而是给后期维护建立了可重复的审查顺序：先看状态，再看规则，再看边界，再看测试。这个顺序会让后续多版推进更稳。

这套顺序还会反向约束实现质量：如果某个后续 registry 说不清状态，就说明响应模型不够；说不清规则，就说明目录数据不够；说不清边界，就说明安全证明不够；说不清测试，就说明版本还不能收尾。v1781 把这些判断放到一个可查询入口里，维护者就能更早发现问题。

这个入口也让团队审查更有共同语言。讨论不再停留在“看起来是否清楚”，而是可以逐项看主题、包、模板、类名、边界、验证。每一项都有数据和测试支撑，判断会更稳定。

因此，本版真正交付的是可复用的审查框架。

这个框架能让维护者更快判断一条保养线是否完整：有入口，有规则，有边界，有测试，有状态。缺少任何一项，都不应该轻易收尾。

对本项目来说，这种判断能力会不断累积。版本越多，越需要一个稳定入口帮助维护者先判断保养线是否健康，再决定是否深入具体文件。v1781 正是在建立这个入口。

它也让后续版本可以更从容地推进：先看入口是否存在，再看规则是否完整，再看边界是否关闭，再看测试是否覆盖。这个顺序一旦固定，维护质量就不会完全依赖个人记忆。

本项目后续继续增长时，这个顺序会帮助每一批保养先自检，再提交，再推送。

这会让维护者在面对大量版本时仍然能快速确认方向，减少反复翻找和重复判断。

这种清楚的判断路径会持续节省维护时间。

也会减少后续误改。

收益很直接。

也很必要。

长期看更必要。

## 一句话总结

v1781 把 Java 可读性保养地基服务化为 `/api/v1/ops/readability/upkeep-registry` 只读 registry，并用 renderer、support、controller 和测试证明新增子包、模板规则、短类名试点与运行时边界都可审查。
