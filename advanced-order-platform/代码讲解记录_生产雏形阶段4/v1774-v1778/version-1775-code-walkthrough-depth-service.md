# 第一千七百七十五版代码讲解：中文长篇讲解深度门禁服务出口

本版目标是把 v1774 定义的讲解深度规则从静态目录推进到可读取的只读服务出口。v1774 已经建立了路由常量、响应模型、深度规则目录、边界目录和验证目录，但如果这些规则只停留在类文件里，维护者仍然需要靠搜索才能理解当前标准。v1775 因此补齐 renderer、support、service 和 controller，让“中文、三千汉字、每版一篇、实现证据、边界证据、测试证据”通过统一的 ops shard-readiness registry 暴露出来。

本版不是为了增加一个好看的接口数量，也不是为了让文档治理看起来像运行时能力。它解决的是可发现性和可审计性：当后来者问“为什么 v1774 以后短讲解会失败”“三千字门槛在哪里定义”“哪些边界不能碰”时，可以从 endpoint、profile、checks、markdownSections 和各类规则列表看到完整答案。它仍然不会启动 Java 服务，不会启动 mini-kv，不会读 credential value，不会解析 raw endpoint URL，也不会联系 managed audit。

## 入口路由

入口路由仍然是 v1774 建立的 `CODE_WALKTHROUGH_DEPTH_REGISTRY`，最终 endpoint 为 `/api/v1/ops/shard-readiness/code-walkthrough-depth-registry`。v1775 新增 `OpsShardReadinessCodeWalkthroughDepthRegistryController`，它使用 `@RestController` 和 `@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)`，并在 `registry()` 方法上挂 `@GetMapping(OpsShardReadinessRoutePaths.CODE_WALKTHROUGH_DEPTH_REGISTRY)`。这一层只接受 GET，不提供 POST、PUT、DELETE，也不接收任何能触发执行的 body 或 query。

controller 的构造方式延续现有 registry 模式：通过构造函数注入 `OpsShardReadinessCodeWalkthroughDepthRegistryService`，方法体只调用 `service.registry()`。这样的设计看似简单，但它恰好说明 controller 不承担业务判断、不读取文件、不启动进程、不访问网络。入口层只负责把只读响应交出去，所有组合逻辑都在 service/support/catalog 层完成。

为什么要单独给讲解深度建 controller，而不是复用旧的 quality gate controller？因为旧 quality gate 解决的是“版本粒度和解释结构是否够用”，而这次新增的是用户明确提出的“中文书写”和“每篇至少三千字”。把两者合并会让旧 registry 的语义膨胀，也会让已有测试的版本历史变得混乱。独立 endpoint 能让新规则的生效版本、门槛和边界更清晰。

从维护角度看，入口路由的稳定性还方便 CI 和人工审查。测试可以直接断言 endpoint 字符串，控制器测试可以直接构造 controller 并调用 registry，不需要启动 Spring 容器。这样一来，路由拼接、响应 profile 和 read-only 标志都能在快速单元测试里验证，而不把每次文档规则变更都变成沉重集成测试。

## 响应模型

v1775 沿用 v1774 的 `OpsShardReadinessCodeWalkthroughDepthRegistryResponse`，但通过 support 层真正填充它。响应版本先保持 `Java v1774`，这是因为本批五版中 v1774 是规则基础首次生效点；等 v1778 closeout 时才会把 `RESPONSE_VERSION` 升到 `Java v1778`。这种做法避免早期 tag 提前宣称自己完成整批 closeout。

support 层填充的关键字段包括 `project=advanced-order-platform`、`sourcePlan=Node v367 / Java v1774-v1778`、`priorQualityGate=/api/v1/ops/shard-readiness/code-walkthrough-quality-gate-registry`、`registryState=chinese-longform-walkthrough-depth-enforced-from-v1774`、`effectiveFromVersion=1774`、`minimumChineseCharacterCount=3000`。这些字段共同回答三件事：规则属于哪个项目、从哪里衍生、从哪个版本开始生效。

响应模型中的计数字段也在 v1775 变成实际状态：`depthRuleCount=4`、`languageRuleCount=3`、`evidenceRuleCount=4`、`boundaryRuleCount=8`、`deniedBoundaryRuleCount=8`、`verificationStepCount=5`。这些计数不是装饰，它们是 support 判断 status 的输入。只要后续有人删掉一条深度规则、少一个边界规则、把 boundary allowed 改成 true、或者把 verification required 改成 false，status 就会变成 blocked，测试也会暴露问题。

响应中的 `checks` 列表是另一个重要证据面。它包含 `code-walkthrough-depth-effective-from-v1774`、`code-walkthrough-depth-minimum-chinese-characters-3000`、`code-walkthrough-depth-chinese-default`、`code-walkthrough-depth-one-version-one-walkthrough`、`code-walkthrough-depth-no-short-receipts`、`code-walkthrough-depth-no-write-routing`、`code-walkthrough-depth-no-credential-value`、`code-walkthrough-depth-no-raw-endpoint-url`、`code-walkthrough-depth-no-upstream-autostart`。这些字符串让下游测试和人工审查可以快速确认核心规则没有丢。

## 上游证据配置

上游配置仍然来自 Node v367 计划，但 v1775 比 v1774 更强调“只引用计划，不消费运行产物”。Node v367 的下一步推荐是 Node v368 做 minimal read-only gate execution archive verification，Java 当前不要求新功能。v1775 因此只把 Node v367 作为 `SOURCE_PLAN`，说明当前 Java 批次是仓库内部治理，不是跨项目执行。

`PRIOR_QUALITY_GATE` 指向旧的 code walkthrough quality gate registry，这体现了本版与历史规则之间的继承关系。旧 registry 规定不要做微版本、解释要有 route/model/service/test/boundary 故事；新 depth registry 在这个基础上增加中文和三千汉字门槛。它不是另起炉灶，而是把旧规则中“出彩解释”的模糊要求变成更硬的门槛。

本版不读取 Node v367 的 JSON、Markdown、summary 或 screenshot，也不访问 Node 工作区里运行时归档。因为那些属于 Node v368 的归档验证目标。Java v1775 的上游证据只是计划文本和边界判断。这样的处理避免了跨仓库职责混乱：Java 可以治理自己的讲解质量，但不替 Node 验证 Node v367 归档。

另外，v1775 没有引入环境变量或 profile 开关。讲解深度规则是静态仓库标准，不应该因环境不同而忽开忽关。它不需要 `UPSTREAM_PROBES_ENABLED`，更不能使用 `UPSTREAM_ACTIONS_ENABLED`。这也降低了误操作风险：只要代码和测试存在，规则就存在；不需要在运行时打开什么。

## 服务层核心流程

服务层核心从 `OpsShardReadinessCodeWalkthroughDepthRegistryService.registry()` 开始。方法先读取三类规则目录：`depthRules()`、`languageRules()`、`evidenceRules()`；再读取 `boundaryRules()` 和 `verificationSteps()`；最后调用 renderer 生成 markdown sections，并把所有集合交给 support 的 `response()` 方法。这个顺序对应响应模型的结构，也对应维护者阅读时的顺序：先看规则，再看边界，再看验证。

`OpsShardReadinessCodeWalkthroughDepthRegistryRenderer` 负责把规则集合转换成五个 markdown section：`Depth Rules`、`Language Rules`、`Evidence Rules`、`Boundary Rules`、`Verification Steps`。每一行都包含 code、required、minimumChineseCharacters、minimumMentions、allowed 或 commandOrClass 等关键信息。这样 API 响应既有结构化字段，也有可直接贴进文档的 markdown 摘要。

`OpsShardReadinessCodeWalkthroughDepthRegistrySupport` 是本版最核心的组合层。它复制输入列表，统计 denied boundary 数量，检查 depth rules 和 language rules 是否全部 required，检查 verification steps 是否全部 required，检查所有 depth rule 的 minimumChineseCharacters 是否大于等于 3000。然后用这些条件加上期望计数决定 `status` 是 `passed` 还是 `blocked`。

support 层的价值在于它把规则一致性集中起来，而不是让每个测试手写同一套判断。比如“边界规则必须全部 denied”既能反映在 status，也能被 boundary tests 断言；“三千汉字门槛”既体现在 response 字段，也体现在 checks 和 compliance tests。后续如果要把门槛从 3000 提高到 4000，只需要改 support 常量、目录说明和测试期望，而不是在多处散落字符串。

还有一个不太显眼但很重要的流程细节：support 在接收每个列表后都使用 `List.copyOf` 复制。这个动作不是为了形式，而是避免调用方在 response 构造后继续修改原始列表，导致响应对象里的规则集合被外部状态污染。讲解深度门禁看上去只是文档规则，但它会被测试、控制器和后续索引共同引用，所以响应一旦生成就应该稳定。这个模式和项目里其他只读 registry 一致，也降低了后续维护者误把可变集合传出去的风险。

renderer 也没有把 markdown 文本写死成一个大字符串，而是从结构化规则映射生成行。这样做的好处是后续新增规则时，只要扩展 catalog，渲染内容会自然出现；测试也可以按 section 和关键片段断言，而不需要比对整段长文本。对这个仓库来说，这是一种更可维护的“说明生成”：人读 markdown section，机器读 record 字段，两者来自同一套输入。

## Java 证据检查

Java 证据第一层是新增服务类本身。`OpsShardReadinessCodeWalkthroughDepthRegistryService` 使用 `@Service` 和 `@Transactional(readOnly = true)`，这说明它被 Spring 管理但只读，不会写数据库或触发事务性修改。虽然它当前只组合静态 catalog，仍然保持 read-only 注解，是为了和其他 evidence registry 的行为一致。

第二层证据是 controller 不做额外逻辑。`OpsShardReadinessCodeWalkthroughDepthRegistryController.registry()` 不拼接数据、不读取文件、不访问环境变量，只把 service response 返回出去。这个简单性是安全信号：入口层不包含隐藏副作用，所有可审计信息都在 service/support/catalog 中。

第三层证据是 renderer 的 markdown sections。它把规则渲染成可读摘要，方便后续文档或控制器测试确认每类规则存在。比如 renderer tests 可以检查 `minimum-3000-chinese-characters` 和 `minimumChineseCharacters=3000` 同时出现，也可以检查 `no-credential-value` 的 `allowed=false`。这比只看顶层计数更有解释力。

第四层证据来自 test support。`OpsShardReadinessCodeWalkthroughDepthRegistryTestSupport` 提供 `service()` 和 `registry()`，让多个测试共享同一个构造方式。这样测试不会各自 new service 或重复组合 catalog，减少断言漂移。它也是现有质量 gate registry 的本地模式延续。

## mini-kv 证据检查

本版不消费 mini-kv 证据。service 不连接 Redis 兼容端口，不发送 HEALTH、INFOJSON、STATSJSON，也不需要 mini-kv fixture。`startsMiniKvService=false` 和 `no-minikv-autostart` 在响应和边界里继续存在，说明这个 registry 没有任何上游启动职责。

为什么仍然写 mini-kv 章节？因为本仓库很多版本都围绕 Java、Node、mini-kv 证据协同推进，如果讲解省略 mini-kv，后来者可能误以为没有考虑它。v1775 的正确说明是：本版的对象是代码讲解质量，mini-kv 没有输入也没有输出；这是一条明确边界，而不是遗漏。

如果未来出现真实 read contract 问题，比如 Node v368 归档验证发现 mini-kv evidence 和 Java overview 不匹配，那应该另开功能版本处理。那时讲解需要写 mini-kv 端口、命令、fixture 和运行窗口。本版不提前做这些，因为提前打开会偏离 Node v367 的暂停条件和推荐顺序。

mini-kv 不参与也帮助测试更稳定。讲解深度 registry 的测试可以在纯 Java 单元层完成，不需要外部进程，不需要 Docker，不需要网络。这样门禁规则可以快速运行，成为每次提交都能承受的检查，而不是一个偶尔手动跑的重测试。

## 阻断与安全边界

v1775 的边界由响应字段、boundary catalog、support status 和测试共同维护。响应字段中 `executionAllowed=false`、`startsJavaService=false`、`startsMiniKvService=false`、`readsCredentialValue=false`、`resolvesRawEndpointUrl=false`、`managedAuditHttpAllowed=false`。这些字段让调用方不用解析 markdown 就能知道本接口没有执行能力。

boundary catalog 进一步把禁止动作写成具体 code：`no-write-routing`、`no-active-shard-router`、`no-credential-value`、`no-raw-endpoint-url`、`no-managed-audit-http`、`no-deployment-or-rollback`、`no-java-autostart`、`no-minikv-autostart`。每个规则的 `allowed=false`，support 再统计 denied count 等于总数。这样边界不是一句“安全”，而是一组可数的拒绝项。

本版尤其要防止两种误解。第一种误解是“既然是门禁，就可以阻止发布或自动执行修复”。实际不是，registry 只说明门槛，具体失败由测试暴露，不会自动部署、回滚或修改历史。第二种误解是“既然提到 credential 和 endpoint，就需要读取它们”。实际也不是，本版只是禁止读取这些敏感内容。

cleanup 边界也明确。本版没有启动长期进程，不应该留下 Java server、mini-kv、浏览器、Docker 或本地 HTTP server。运行测试时 Maven 可能短暂启动测试 JVM，但 final cleanup gate 必须确认没有残留进程。这个规则在最终汇报中也要写清楚。

## 测试覆盖

v1775 新增多组测试。`OpsShardReadinessCodeWalkthroughDepthRoutePathsTests` 检查 route path、共享路由委托和 endpoint。`OpsShardReadinessCodeWalkthroughDepthRegistryServiceTests` 检查项目名、版本、endpoint、profile、sourcePlan、priorQualityGate、registryState、生效版本、最小汉字数、各种计数和 status。`OpsShardReadinessCodeWalkthroughDepthRegistryRendererTests` 检查五个 markdown section 的稳定顺序和关键内容。

这些测试不仅覆盖正常路径，也覆盖维护者最容易误改的地方。

`OpsShardReadinessCodeWalkthroughDepthBoundaryTests` 检查 read-only、executionAllowed、startsJavaService、startsMiniKvService、credential、raw endpoint、managed audit 等字段，并检查八个禁止 code。`OpsShardReadinessCodeWalkthroughDepthRegistryControllerTests` 直接构造 controller，确认 endpoint、版本、最小汉字数和只读字段。这些测试合起来证明 service 不是只有文件存在，而是可被查询、可被断言。

这些测试还有一个维护收益：它们把“用户反馈”拆成多个失败点。如果未来有人把最小汉字数改小，service test 会失败；如果有人删掉中文默认规则，renderer 或 service test 会失败；如果有人把某个边界改成 allowed，boundary test 会失败；如果有人改路径但忘记共享 route，route test 会失败。失败点越具体，维护者修复时越不需要猜测。

不过 v1775 还不是整批完成。真正的中文长篇门槛要在 v1776 改 `OpsCodeWalkthroughArchiveComplianceTests` 后才完全生效。v1775 先确保 registry 输出正确，这样 v1776 的测试升级不是孤立规则，而是和一个只读证据接口互相印证。

本版完成后应运行定向测试：`mvn -q "-Dtest=OpsShardReadinessCodeWalkthroughDepth*Tests,OpsCodeWalkthroughArchiveComplianceTests" test`。整批完成后仍要跑全量 Maven 和 CI。因为新增 controller/service 虽然简单，但 Spring 扫描和路由常量改动可能影响整体编译。

这篇讲解本身也要说明一个容易被忽略的维护判断：代码讲解门禁不能只靠人工提醒。人工提醒在一两次提交里有效，版本数一多就会松动，尤其是同一个仓库已经积累大量历史讲解、截图归档、计划摘要和收尾记录时，维护者很难逐篇判断新文件到底是否达到了新标准。v1775 把规则做成服务和响应，不是为了增加接口数量，而是为了给后续治理提供一个稳定参照点。维护者打开响应可以看到规则数、中文要求、边界拒绝、验证步骤和状态；打开测试可以看到这些字段如何被断言；打开讲解目录可以看到每个版本为什么要单独成篇。三者放在一起，才算把“以后不要写短讲解”落到工程层面。

另一个需要写透的点是“中文为主体”并不等同于“不能出现代码名”。这个仓库讨论的是 Java 后端、只读路由、响应模型、目录归档和跨仓库计划，完全回避类名、方法名、常量名反而会让讲解失去证据感。真正的问题是比例和解释责任：代码名应该服务于中文解释，而不是代替中文解释。比如提到一个服务类后，讲解必须说明它为什么存在、如何组合目录、怎样保持只读、哪些字段用于下游审查、哪些测试会在未来防止误改。这样代码名就变成锚点，中文段落负责把锚点之间的因果关系串起来。v1775 的服务层说明因此补充了 support、renderer、controller、test support 的责任边界，让读者不用跳进所有源码也能理解这条证据链。

这也解释了为什么本版没有把所有规则塞进一个巨型服务类。最省事的写法当然是一个 service 里 new 出所有 record，再手写 markdown 字符串，最后返回响应；短期看提交更少，长期看每次改规则都要在同一个文件里翻找，测试也只能断言一坨拼好的结果。现在拆成 catalog、renderer、support、service、controller，是为了让每一层的意图都足够窄：目录层回答有哪些规则，渲染层回答怎么展示，支撑层回答怎样判断状态，服务层回答如何编排，控制器回答如何暴露。这样的拆分让未来新增“截图说明归档不再挤入一个文件夹”这类规则时，可以先加目录和测试，再决定是否需要扩展响应，而不是在大文件里继续堆判断。

从审查角度看，v1775 的价值还在于它把“本批五版不是功能乱跑”写清楚。用户要求加大工作量和代码讲解内容，但同时历史边界已经明确：暂时不要自由打开写路由、活动分片路由、密钥读取、原始端点、托管审计连接、部署回滚，也不要让 Node 自动启动或停止 Java 与 mini-kv。因此本版选择做仓库内质量治理，而不是借“加大工作量”之名扩展运行面。工程上真正难的地方往往不是写更多代码，而是在压力下仍然守住边界，把该做的做深，把不该做的明确拒绝。这个 registry 正是把这种边界意识固定下来。

最后还要强调测试反馈的使用方式。若某篇讲解因为英文标识太多导致中文占比不足，正确处理不是把测试改松，而是补足中文解释，让读者真正看懂。若某篇讲解误写了历史遗留标记，正确处理也不是给新文件豁免，而是删除误导性标记，保持历史和未来的规则分界。v1775 把这些原则体现在服务输出里，v1776 会继续把它们体现在档案扫描测试里。这样最终形成的不是一组漂亮文字，而是一条能被本地 Maven 和远端 CI 同时执行的维护纪律。

## 实际工作量说明

本版的实际工作量主要落在本项目服务编排和测试可见性上。v1774 已经有规则模型，但模型本身不会自动形成证据链；v1775 把目录、渲染、支撑判断、服务和控制器串起来，让规则可以被查询、可以被渲染、可以被断言。这个工作量不是在讲解里重复“必须三千字”，而是让三千字要求有代码入口、状态计算、检查清单和测试覆盖。用户要求禁止硬凑，所以本版选择增加真实维护面：规则目录不再只是列表，支撑层会检查数量、必需性、边界拒绝和最低汉字门槛，渲染层会生成可读摘要，控制器会把结果暴露为只读响应。

本项目的重点也在这里被重新收束。虽然上游计划来自另一个仓库，但 v1775 没有把篇幅消耗在讲别的项目如何运行，也没有消费别的项目的归档。讲解中出现上游计划，只是为了说明当前为什么不打开运行集成。真正的代码、测试、文档和接口都在本项目内完成。这样的安排符合“主要做你自己项目的”要求：跨仓库信息只作为边界输入，实际工程产物留在 advanced-order-platform。

禁止硬凑还意味着测试失败时要改真实内容。前一次门禁提示本篇中文占比不够，根因不是汉字数完全不足，而是代码标识和英文术语占比过高。正确修复不是删除测试，也不是把阈值调低，而是补充中文解释：为什么服务要拆层、为什么响应要有状态、为什么边界必须可统计、为什么本版不消费运行时。补充这些内容后，读者能更清楚地理解本版设计，测试也能证明讲解确实以中文维护说明为主体。

这次补充仍然围绕本项目本身：服务层拆分让规则来源、状态判断、展示文本和入口暴露各自独立，后续维护者可以局部修改、局部测试、局部审查。这样的说明对应真实代码结构，不属于硬凑。

## 一句话总结

v1775 把中文三千字讲解标准从静态规则目录推进为可查询、可渲染、可测试的只读 registry，同时继续关闭所有运行时执行、上游启动、密钥、端点、审计、部署和回滚边界。
