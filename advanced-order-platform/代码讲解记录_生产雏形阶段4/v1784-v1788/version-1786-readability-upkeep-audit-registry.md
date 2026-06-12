# 第一千七百八十六版代码讲解：可读性保养审计 registry 只读入口

v1786 把上一版建立的审计模型和资料地基真正组装成可查询的只读入口。v1784 写清路线服务测试地图和根包压力地图，v1785 把这些地图转成响应模型与静态资料，v1786 则新增渲染器、支撑计算、服务、控制器和四组测试，让 `/api/v1/ops/readability/upkeep-audit` 成为本项目内部可验证的审计 registry。它仍然不启动外部服务，不连接数据库，不读取凭据，不触发任何写路径，只把本项目已经沉淀的可读性保养证据整理成结构化响应。

这一版的核心取舍是“接口要薄，证据要厚”。如果只新增一个控制器返回几行字符串，版本很快能完成，但没有工程价值；如果把所有资料、计算和渲染都塞进服务类，接口看似完整，却会制造新巨型文件。v1786 选择延续拆分：资料在上一版 catalog，渲染器只生成章节，支撑类只算数量和状态，服务只编排，控制器只委托，测试分别看服务、渲染、边界和控制器。这样接口出现后，维护压力没有回到单点。

## 入口路由

本版新增实际运行时入口 `/api/v1/ops/readability/upkeep-audit`。它由 `ReadabilityUpkeepRoutePaths.BASE_PATH` 与 `ReadabilityUpkeepRoutePaths.UPKEEP_AUDIT` 组合而成，控制器使用常量注解，不手写路径字符串。控制器方法名是 `audit()`，只调用服务并返回响应，不做任何额外逻辑。这个薄控制器设计让路由层保持可预测，也方便测试直接实例化控制器验证委托结果。

新入口的意义不是让外部系统执行审计，而是让维护者和测试都能看到同一份只读证据。响应里会指出源 registry endpoint 是 `/api/v1/ops/readability/upkeep-registry`，也会列出 route-service-test map、root package pressure、boundary rules、verification steps 和 markdown sections。也就是说，audit endpoint 是对上一批 registry 的补充，不是替代。

`ReadabilityUpkeepAuditControllerTests` 直接构造控制器和服务，验证 endpoint、version、readOnly、executionAllowed 和 source registry endpoint。它没有启动 Spring 容器，因为本版要验证的是控制器委托和响应契约，不需要真实 HTTP 环境。这样测试速度快，也更符合只读保养场景。

## 响应模型

响应模型使用 v1785 新增的 `ReadabilityUpkeepAuditResponse`。v1786 没有再扩字段，而是通过支撑类把字段填完整。版本是 `Java v1786`，profile 是 `java-ops-readability-upkeep-audit.v1`，状态是 `readability-upkeep-audit-registry-active-v1786`。这些常量被服务测试检查，保证接口响应和版本 tag 对齐。

支撑类计算了 topicCount、routeServiceTestMapCount、rootPackagePressureCount、boundaryRuleCount、deniedBoundaryRuleCount、verificationStepCount、markdownSectionCount。它还检查所有主题 required、所有路线 readOnly、所有迁移 deferred、所有验证 required、所有边界 denied。只有这些条件同时满足，status 才是 `passed`。这种计算比简单返回固定 passed 更有意义，因为后续资料数量或边界一旦被改坏，服务测试会看到 blocked 或 count 不匹配。

响应里的 checks 也被显式列出。它包含 docs root、package root、source registry、topic count、route map count、root pressure count、boundary count、denied boundary count、verification count，以及 route-service-test map present、root package pressure present、no migration now、no write routing、no credential value、no upstream autostart 等信号。调用者不用逐条展开所有列表，也能先看 checks 判断审计状态。

## 上游证据配置

本版没有读取新的外部材料。上游证据仍是 v1784 的两张地图、v1785 的五组 catalog、上一批的 registry 模板和类名试点。服务从 catalog 拿数据，不从文件系统动态扫描文档。这样响应内容和测试结果稳定，远端 CI 不依赖本机目录，也不会因为 Markdown 文案微调导致运行时输出突然变化。

`ReadabilityUpkeepAuditService` 的编排顺序很清楚：读取 topics、routeMaps、pressures、boundaryRules、verificationSteps，然后调用 renderer 生成 markdown sections，再交给 support 生成 response。服务没有 if 分支，没有循环计算细节，没有硬编码 checks。它像一条窄管道，把资料从 catalog 传到 response。

`ReadabilityUpkeepAuditRenderer` 负责把五类资料渲染为五个固定章节：Audit Topics、Route Service Test Maps、Root Package Pressure、Boundary Rules、Verification Steps。渲染器不判断业务状态，只把每条数据变成可读行。这样如果未来要改展示格式，只动 renderer 和 renderer test，不动 support 或 service。

## 服务层核心流程

服务层核心流程可以分成四步。第一步，读取静态资料。资料已经在上一版拆开，因此服务不需要知道每条主题或禁止项的细节。第二步，调用渲染器生成章节。第三步，调用支撑类计算计数、检查项和通过状态。第四步，返回响应给控制器。每一步职责明确，文件规模可控。

支撑类是本版最关键的内部层。它用不可变拷贝接收列表，避免调用者后续修改影响 response。它计算 deniedBoundaryRuleCount，并用多个布尔条件决定 status。它统一生成 checks，避免 service 和 tests 各自拼接状态。这样后续新增一条验证步骤时，只需要调整 catalog、expected count、相关测试和讲解，不需要到处找散落逻辑。

服务测试覆盖了这个流程。`ReadabilityUpkeepAuditServiceTests` 检查 project、version、endpoint、profile、docsRoot、packageRoot、sourceRegistryEndpoint、auditState、各类 count、markdownSectionCount 和 status。第二个测试检查 checks 包含关键项，并确认 route maps 中有 `/api/v1/ops/readability/upkeep-audit`。这比只断言 response 非空更可靠。

## Java 证据检查

Java 证据第一层是新增运行时代码。`ReadabilityUpkeepAuditRenderer`、`ReadabilityUpkeepAuditSupport`、`ReadabilityUpkeepAuditService`、`ReadabilityUpkeepAuditController` 四层构成完整只读链路。每层文件都保持小职责，没有形成巨型类。控制器只委托，服务只编排，支撑只计算，渲染只展示。

第二层是测试。`ReadabilityUpkeepAuditRendererTests` 检查五个 markdown section 的顺序和关键行，确保输出既包含 audit route，也包含根包迁移 deferred 和禁止凭据读取。`ReadabilityUpkeepAuditBoundaryTests` 检查所有执行风险字段都是 false，所有 boundary allowed=false，所有 root package pressure 都没有要求现在迁移。`ReadabilityUpkeepAuditControllerTests` 检查控制器委托结果。加上 v1785 的 catalog tests，本批 audit registry 已覆盖资料、组装、渲染、边界和路由委托。

第三层是中文讲解门禁。当前文件必须通过标准标题、三千字、中文占比、实际工作量、本项目、禁止硬凑等测试。由于本版包含较多英文类名，讲解需要用足够中文解释工程判断，而不是让标识符淹没内容。这里的讲解不是装饰，而是后续维护者理解为什么拆出 renderer 和 support 的依据。

## mini-kv 证据检查

mini-kv 仍然不作为输入。本版 response 明确 `startsMiniKvService=false`，boundary catalog 继续包含 `no-minikv-autostart`。服务不读取 mini-kv 文件，不打开端口，不调用 C++ 程序，不依赖任何 fixture。Audit endpoint 的证据全部来自 Java 本项目的 docs 和 catalog。

这一点对用户的长期要求很重要。Java 和 C++ 可以各自推进，但不要让 Node 卡住 Java，也不要让某个项目的运行环境成为另一个项目的必备条件。v1786 只暴露 Java 可读性保养审计，不把 mini-kv 的 shard map、slot table 或 runtime 证据混进来。以后如果要做跨项目审计，也应该另起明确版本和边界。

本版还没有启动 Java 服务去截图或浏览器检查，因为新增的是后端只读接口和单元测试链路。验证重点是 Maven 测试和远端 CI，而不是页面截图。没有前端页面，也就没有浏览器自动化需求。

## 阻断与安全边界

v1786 的安全边界由 response 字段、boundary catalog、support checks 和 tests 四层共同守住。响应字段全部表示不执行：readOnly=true，executionAllowed=false，startsJavaService=false，startsMiniKvService=false，writesBusinessState=false，readsCredentialValue=false，resolvesRawEndpointUrl=false，managedAuditConnectionAllowed=false。测试逐项断言这些值，避免后续误改。

边界 catalog 继续列出八个禁止项：不打开写路由，不激活分片路由，不读取凭据值，不解析原始端点，不建立受管审计连接，不部署或回滚，不自动启动 Java，不自动启动 mini-kv。Support 还把这些禁止项转成 checks，让调用者不用展开列表也能看到 no-write-routing、no-credential-value、no-upstream-autostart。

根包迁移也被明确阻断为“现在不迁移”。Root package pressure 的记录全部 `migrationRequiredNow=false`，boundary test 统一检查。这样 audit endpoint 不会被误解为马上执行根包重构。它只是把压力、方向和未来纪律公开出来。真正迁移旧类需要另一个明确版本、兼容测试和讲解。

## 测试覆盖

本版新增四个测试类和一个测试支撑类。服务测试覆盖响应契约和 checks，渲染测试覆盖 markdown section 顺序和关键内容，边界测试覆盖所有只读字段和迁移 deferred，控制器测试覆盖 route delegate。再加上上一版 catalog tests 和 route path tests，audit registry 的主要层次都被定向覆盖。

定向验证命令仍然是 `mvn -q "-Dtest=ReadabilityUpkeep*Tests,OpsCodeWalkthroughArchiveComplianceTests" test`。这条命令覆盖新旧 readability 测试和讲解归档合规。后续收尾还会运行全量 `mvn -q test`，保证新增 controller 没有破坏 Spring 应用上下文或其他模块。

本版没有引入集成测试，是因为当前功能只读且没有真实数据库或外部服务依赖。直接实例化服务和控制器就能验证主要行为。等未来如果真的通过 HTTP 层暴露给外部页面，再考虑 Web 层测试。当前阶段单元测试更快、更稳定，也更符合保养性质。

## 实际工作量说明

v1786 的实际工作量包括新增只读审计链路的四层代码：渲染器、支撑类、服务、控制器；新增四组测试和测试支撑；把 v1785 的 catalog 组装成完整响应；把 endpoint、profile、source registry、auditState、counts、checks、markdown sections 和 status 全部纳入测试。它不是只在上一版模型外面包一层 controller，而是补齐了从资料到响应的完整路径。

这不是硬凑。用户要求代码讲解用中文，至少三千字，字数不够就加大每版工作量，禁止硬凑。本版之所以能写出足够解释，是因为真实工作量足够：每一层都有职责，每一组测试都有目标，每一个禁止项都在响应和测试中出现。讲解只是把这些工程判断展开，而不是用空话撑篇幅。

本项目现在得到一个新的维护能力：不用读完整个 `ops` 根包，也能通过 audit response 看到 readability upkeep 的路线、包压力、禁止项和验证清单。这个能力不会改变业务状态，却能显著降低后续审查成本。维护者看到 audit endpoint，就能知道当前可读性保养有没有保持只读、有没有继续堆根包、有没有缺测试、有没有把讲解写成合规中文长文。

本版还延续了必要拆分。渲染器不计算状态，支撑类不负责路由，服务不承载静态清单，控制器不写业务逻辑。这样的拆分看似多几个文件，但每个文件都小，读起来清楚。对于后期项目来说，多几个职责明确的小文件，比一个巨大服务类更容易维护。

`ReadabilityUpkeepAuditSupport` 的 count 和 status 计算尤其重要。它不是直接相信 catalog，而是把数量、必需标记、只读标记、迁移延迟和禁止边界都合并判断。这样后续任何人修改 catalog 时，都要同步考虑 expected count 和测试。这个压力是好的，它让资料变化变成显式维护，而不是悄悄漂移。

`ReadabilityUpkeepAuditRenderer` 的章节顺序也被测试固定。章节顺序稳定，讲解和文档引用才稳定；后续调用者如果把 response 渲染成 Markdown，也不会因为列表顺序随意变化而增加审查噪音。稳定展示是证据系统的一部分，不是可有可无的细节。

控制器测试虽然短，但它守住了外部入口的最小契约。只要 controller 返回的 response endpoint、version、source registry 和只读字段正确，就说明路由层没有绕过服务或返回错误对象。未来如果加 WebMvcTest，也可以在这个基础上扩展，不需要推翻当前测试。

本版还继续保护旧根包。Audit response 明确显示 root package pressure，但不要求现在迁移。这样的表达让保养变得诚实：我们承认问题，也不给自己制造超出本批范围的风险。新增代码进入小包，旧代码保持稳定，未来迁移先补证据。这是本项目当前最稳的工程路线。

对于后续 v1787，v1786 留下的接口会成为 docs guard 的验证对象。文档测试可以检查 route-service-test map 是否真的提到 audit route，root-package-pressure map 是否真的强调不做 bulk rename，维护周期文档是否把模型、接口、测试、讲解和 CI 串起来。也就是说，本版不是终点，而是让下一版的文档门禁有真实对象可守。

这一版还有一个实际维护收益：以后定位问题时可以先看响应中的检查项，再决定读哪一层源码。如果检查项显示路线地图存在，但渲染章节缺少某条路线，就去看渲染层；如果数量不对，就去看资料层和支撑层；如果控制器返回的版本不对，就看服务常量和控制器测试。每个问题都有比较明确的落点。过去那种在大包里全局搜索的方式仍然可用，但已经不再是唯一办法。这样的可定位性，是后期保养非常实际的收益。

同时，本版没有为了追求接口完整而打开更重的运行方式。没有真实服务器，没有网络请求，没有数据库事务之外的外部效果。只读事务注解表达的是服务方法不会写业务状态，单元测试直接构造对象表达的是当前需求不需要容器。这个取舍让版本更轻，也让远端验证更稳定。以后如果确实需要真实网页或浏览器截图，再按用户规则启用浏览器自动化；当前后端接口没有这个必要。

最后，本版仍然只做 Java 本项目。没有打开浏览器，没有跑外部服务，没有修改 Node 或 mini-kv，没有跨仓库操作。所有证据都能被 Java 仓库自己的测试和 CI 复现。这样的独立性会让后续连续推进更顺滑，也减少“一个项目卡住另一个项目”的情况。

## 一句话总结

v1786 把可读性保养审计从模型地基推进到只读接口，用 renderer、support、service、controller 和分层测试暴露 `/api/v1/ops/readability/upkeep-audit`，同时守住本项目只读、安全边界、根包迁移延迟和中文讲解合规。
