# v1882 Release-Acceptance Sustainment Renderer 代码讲解

## 入口路由

本版本处理的是 release-acceptance route-path-split 的 sustainment registry。它不是新增业务接口，也不是把只读准备态升级成可执行态，而是整理一个已经存在的证据展示链。外部入口仍由根包中的 `OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentController` 暴露，Controller 注入公开 Service 后调用 `registry()`，没有增加第二个入口、别名路由或重定向。Service 的 `ENDPOINT` 仍由 `OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH` 与 `RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_SUSTAINMENT_REGISTRY` 拼接，最终值仍是 `/api/v1/ops/shard-readiness/release-acceptance-route-path-split-sustainment-registry`。因此调用方看到的 HTTP 方法、路径、响应类型和状态语义都不变。

旧 Controller 测试中有一个值得认真处理的小问题：它把路由常量与同一个路由常量比较，这种断言无论值如何漂移都会通过，形式上有测试，实际上没有报警能力。v1882 将测试收短为 `SustainmentControllerTests`，并直接断言 suffix 是 `/release-acceptance-route-path-split-sustainment-registry`，再通过真实 Service 图断言最终 endpoint 与 profile。这样 route owner、Service 拼接和 Controller 暴露三层都能被观察。重构没有借机改短生产 Controller 或公开 Service 的名字，因为那些长名已经进入公开 Java 边界；本版本只缩短新增或内部测试标识符，避免用“优雅”之名破坏兼容。

从请求进入后的方向看，链路仍是 Controller 到 Sustainment Service，再到 v1840 已抽取的 route-path-split closeout Service。下游 acceptance-package 继续依赖 Sustainment Service 与 Response，而不会接触新建的 package-private renderer。路由边界、上游边界与下游边界因此形成单向依赖：根适配层进入本包，本包读取 closeout，本包的公开结果再被 acceptance-package 消费。`SustainmentStructureTests` 将这条方向写成源码级机械断言，未来若有人绕过公开边界引用内部 renderer，编译或结构门会立即失败。

## 响应模型

公开响应仍是 `OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse`。它同时保存版本、只读标志、来源计划、上游版本与 endpoint、各类条目计数、七组强类型证据、Markdown 章节、checks 和最终 status。v1882 没有增删 record component，也没有调整 JSON 字段顺序或嵌套 record。`SourceSnapshot`、`OwnershipRule`、`DriftGuard`、`BoundaryGuard`、`CiGate`、`ConsumerHandoff`、`ScorecardEntry` 与 `MarkdownSection` 都保留原定义。这样下游 acceptance-package 继续读取结构化事实，而前端或人工检查者继续读取同样的展示文本。

这里必须区分“响应拥有的数据”和“旧 Markdown 实际展示的数据”。例如 SourceSnapshot 还拥有 endpoint，OwnershipRule 还拥有 rule，CiGate 还拥有 command，ConsumerHandoff 还拥有 handoffRule；旧 renderer 并没有把这些字段写进 Markdown。若重构者根据 record 猜测“这些信息有用”并顺手补入文本，就会改变稳定输出，即使 JSON 没变也会给文本消费者造成漂移。v1882 先通过临时探针调用真实旧 Service 图，打印每个 heading 与 line，确认旧报告恰好七节、三十八行。随后建立 `ReportMarkdownTests`，逐字固定所有行，包括空格、等号、布尔值、顺序和那些刻意省略的字段。探针完成取证后删除，不进入版本产物。

七个章节仍依次是 Source Closeout、Ownership Rules、Drift Guards、Boundary Guards、CI Gates、Consumer Handoffs 与 Sustainment Scorecard。各节正文数量依次为一、六、六、七、五、五、八，总计三十八。新 `ReportRenderer` 使用共享的 `MarkdownSections.mapped`，因为旧输出没有 count 前缀；如果错误地改用 `counted`，oracle 会立刻在每一节第一行发现多余文本。每个 `MarkdownSection` 仍由 record 构造器复制不可变 lines，外层列表也由 `List.of` 固定。不可变性测试继续覆盖所有结构化集合、Markdown 外层、第一节 lines 与 checks，保证“只有展示重构”不会悄悄换成可变容器。

## 上游证据配置

Sustainment Service 的唯一运行时上游仍是 `OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService`。它调用 `closeout()` 得到 Java v1579 的已通过结果，其中又保留 Java v1570 的 split 版本、十一条 route path、十一条 compatibility check 和六条 closeout item。v1882 没有复制这些事实，也没有新建第二份 fixture；Source Catalog 仍从真实 closeout response 生成一个 SourceSnapshot，其他 Catalog 继续以该 response 为依据建立 ownership、drift、boundary、CI 与 consumer 证据。这种复用使上游若真实漂移，Sustainment status 与下游 acceptance-package 会一起暴露问题，而不是各自维护互相失真的常量岛。

`SOURCE_PLAN = Node v1878` 与 `NODE_PARALLEL_PLAN = Node v1867-v1878` 仍只是历史协调元数据。它们说明这一只读 Java registry 诞生时参考了哪段 Node 规划，不表示 Java 会访问 Node 仓库、启动 Node 进程或调用 Node endpoint。Drift Catalog 将这些版本和上游计数固定为 guard；Support 在计算 status 时要求来源 status、Java v1579、Java v1570 以及所有条目数量同时满足预期。v1882 没有修改这些 status 条件，因为 renderer 不应拥有业务通过标准。

本版本也没有移动 Catalog。七个 Catalog 仍分别拥有事实生成职责，Support 仍拥有响应复制、checks 与 status 聚合。新 renderer 只接收已经构造好的强类型列表，不接收上游 Service，也不自行调用 Catalog。这样数据与行为分界很明确：Catalog 回答“有什么证据”，Support 回答“是否满足门槛”，renderer 回答“如何按旧格式展示”。如果未来要增加证据字段，应先改变 Catalog/Response 与跨项目契约并经过上游到下游对齐；如果只是改变展示，则必须另起版本并明确处理 Markdown 兼容，而不能藏在内部整理中。

## 服务层核心流程

`registry()` 仍标注 `@Transactional(readOnly = true)`。第一步读取 closeout；第二步依次构建 sourceSnapshots、ownershipRules、driftGuards、boundaryGuards、ciGates 与 consumerHandoffs；第三步把这六组事实交给 Scorecard Catalog，得到八条通过项；第四步将七组最终展示数据交给 `ReportRenderer.render`；最后由 Support 复制集合、生成三十条 checks、计算 passed 或 blocked，并构造 Response。调用次序、变量含义、事务边界和最终构造参数都没有改变，唯一替换是最后一跳从超长聚合 renderer 转到短名输出所有者。

旧实现为了七个章节存在一个聚合 renderer、七个章节 renderer 和一个 renderer support，共九个实现文件、二百二十七行，其中真正以 `Renderer` 结尾的八个文件占二百一十三行。每个章节类只被聚合类调用一次，support 也只包装 `new MarkdownSection(heading, List.copyOf(lines))`。这种拆法没有形成可复用策略，反而要求读者在九个超长文件名之间跳转才能看懂一份三十八行报告，正好触发“三次规则”：第三个结构相似章节出现时就应抽共享原语或由一个输出所有者用数据映射表达。

新 `ReportRenderer` 是 package-private、无 Spring 注解、无状态的 final 类。顶层 `render` 一眼给出七节顺序，七个 private 方法各自只描述一种强类型条目的文本格式，公共复制算法由 `MarkdownSections.mapped` 提供。这里没有做一个接受任意字段名、任意分隔符、任意反射 getter 的万能模板，因为那会把编译期类型信息换成字符串配置。七个映射方法虽然保留必要差异，但共享“标题、列表、mapper、section factory”的稳定算法；代码既能局部阅读，也不会重新散成一类一文件。

## Java 证据检查

Java 侧的首要证据是同一 oracle 在替换前后都通过。临时探针只负责取得旧结果；正式 `ReportMarkdownTests` 在旧九文件仍存在时先通过三项测试，然后不修改任何 expected，删除旧实现并接入 `ReportRenderer`，同三项再次通过。oracle 不只比较章节名，而是比较完整 `List<MarkdownSection>`、七节数量、三十八行总数、三十条 checks、五个 CI gate 名称，以及渲染行包含 required 且不泄露 `mvnw` command。通过修改 fixture 或测试期望让迁移变绿被明确禁止。

第二层证据覆盖行为链。`SustainmentCatalogTests` 继续验证 Java v1604 响应、Node 计划字符串、Java v1579/v1570 上游、1/6/6/7/5/5/8 数量、passed、readOnly 与 executionAllowed=false；`SustainmentImmutabilityTests` 验证所有集合不可修改；根 Controller 测试验证精确 route；下游 acceptance-package 的 Catalog、immutability、renderer、receipt、archive index 与 Controller 测试全部使用真实 `SustainmentTestData.service()`。第一次扩展选择共二十七项通过，随后名称、变更、v1866 与当前结构组合二十八项通过。

第三层证据是不可放宽的 census。生产 ops 文件从 1266 降到 1258，renderer 从 45 降到 38，总行数从 3616 降到 3521，长 renderer 文件名从 30 降到 22；目标包从 19 个生产文件降到 11 个，并被要求恰好只有 `ReportRenderer.java`。生产 Java 文件从 1398 降到 1390，长 stem、长标识符使用和唯一名降到 1188/20495/2747。所有全局历史 cap 同步收紧，脚本新增 SustainmentJavaFiles，保证评审者能复现同一口径，而不是相信手写数字。

## mini-kv 证据检查

mini-kv 在这条链里没有运行时调用。旧报告的 Boundary Guards 有一条 sibling-autostart，文本说明并行计划不授权 Java 或 mini-kv 启动；checks 也保留 no-node-or-minikv-auto-start。v1882 的 exact oracle 将这两处文字原样冻结，因此重构不会把“只读协调证据”误写成“已做联合执行”。Java 没有寻找 `minikv_cli`，没有打开 TCP 连接，没有执行 HEALTH、INFOJSON、STATSJSON，更没有 SET、DEL、快照、WAL 或 shard 写命令。

从四项目关系看，本版本属于 Java 仓库内部的非契约重构。它没有改 mini-kv fixture、digest、archive 路径或命令拼写，也没有要求 mini-kv 发布新版本。下游 Node 若消费 Java Response，看到的公开 schema 与 endpoint 均不变；文本消费者看到的三十八行也逐字不变。因此不需要让 mini-kv 或 Node 同步升级。若未来真的要让 Java 启动外部进程或读取实时 mini-kv 输出，那将进入跨项目 capstone 或新的明确授权范围，必须有 env gate、进程清理、超时和 no-write 证明，不能借用本次 renderer 收敛的名义打开。

本节保留是为了机理透明，而不是硬凑跨项目内容。读者需要知道“报告里出现 mini-kv”与“Java 能控制 mini-kv”完全不同：前者是边界声明，后者是执行权限。当前实现只有前者。任何未来代码若在 sustainment 包加入 `ProcessBuilder`、socket client、raw endpoint 解析、凭证读取或自动启动逻辑，都将违反 Support checks、文档边界与系统协调规则，即便单元测试表面仍绿也不应接受。

## 阻断与安全边界

本版本继续关闭 write routing、active shard router、credential value read、raw endpoint resolution、managed audit connection、deployment/rollback 与 sibling autostart 七类边界。Boundary Catalog 的七条证据不变，Scorecard 仍要求 boundaries passed，Support 仍要求所有 guard locked 才返回 passed。renderer 只读取不可变条目并连接字符串，不持有 repository、HTTP client、DataSource、credential provider、shell 或部署接口，因此从类型依赖上也没有执行通道。

事务边界仍在 Service，而不是 renderer。`@Transactional(readOnly = true)` 没有被移动、删掉或改成默认事务；Controller 仍只做代理；新 renderer 不是 Spring bean，不能被外部注入为策略以绕开固定输出。公开 Service 与 Response 保持原名和 public 可见性，新 renderer 则保持 package-private。下游只能依赖稳定边界，不能直接调用 private 映射方法。这种可见性设计既保留兼容，也防止内部展示细节演化成第二套公共 API。

失败条件不仅是测试红。任一旧 renderer 或 support 文件回流、目标包多于 11 个文件、出现第二个 renderer、全局 cap 放宽、长名 baseline 新增、route suffix 漂移、三十八行任一字符变化、下游 acceptance-package 不再走真实 Service、Response 增删字段、只读事务丢失、SpotBugs 豁免扩张、归档 manifest 不精确、中文讲解少于门槛、CI 或 tag 缺失，都意味着 v1882 未完成。特别禁止通过改 oracle、fixture 字节或历史文档来“修”失败；应修实现或撤销错误迁移。

## 测试覆盖

测试侧没有只做类名替换。旧的 CatalogTests、ImmutabilityTests、RendererTests、TestSupport 和根 ControllerTests 都带着完整生产前缀，掩盖职责；v1882 将它们分别改为 `SustainmentCatalogTests`、`SustainmentImmutabilityTests`、`ReportMarkdownTests`、`SustainmentTestData` 与 `SustainmentControllerTests`。v1841 的长名历史结构门也改为 `SustainmentStructureTests`，同时从“只验证当年搬了十九个文件”升级为验证当前十一文件精确集合、九个旧文件永久不存在、四个包级短测试精确集合、根 Controller 保留、上游 closeout 和下游 acceptance-package 方向、SpotBugs FQN、全局计数与历史文档冻结。

`SustainmentTestData` 仍公开最小必要的 `service()` 与 `registry()`，因为根 Controller 测试和下游 acceptance-package 的 `PackageTestData` 跨包复用它。它没有手造 Response，而是从 v1840 closeout TestSupport 构造真实上游，再创建当前 Service。这样一次 fixture 修正会沿链传播，避免上游测试一套数据、下游测试另一套数据。文件名变短不等于测试变浅，恰恰通过共享真实图减少了复制和失真。

名称门在第一次 baseline 重建时抓到五个新测试方法超过四十字符。它们随即被改成 `exposesStableSustainmentRoute`、`registryPinsCloseoutAndParallelPlan`、`reportMatchesLegacyOutput`、`keepsBoundaryDirection` 与 `spotbugsAndCountsFollowFamily`，再次重建后新增 baseline 条目为零，净删除三十五条旧记录。这个过程说明命名预算不是文档口号，而是会失败并促使设计者收敛表达的生成时门。测试文件总数仍为 900，没有用删测试换取指标。

## 实际工作量说明

本版生产侧删除一个聚合 renderer、七个章节 renderer 和一个 renderer support，新增一个 `ReportRenderer`，净减八个生产 Java 文件。目标包 19 降到 11，全仓 ops 1266 降到 1258，生产 Java 1398 降到 1390。八个旧 Renderer 共 213 行，新 renderer 经格式化为 118 行，因此 renderer 总行数净减 95；renderer 文件数净减 7，长 renderer 文件名净减 8。Catalog、Service、Response、Support 的事实与判断没有迁移，避免为了数字好看制造巨型文件或万能抽象。

测试侧不是净删文件：临时探针完成取证后删除，正式 oracle 新增；五个旧长名测试/工厂与一个历史结构门各自被短名职责文件替换，最终测试文件数仍为 900。除了精确输出，还修复了 route 常量自比较这一真实盲点。脚本新增 sustainment family census，OpsElegance 增加精确 renderer 与文件上限，JavaElegance 收紧生产和测试名称上限，八个历史全局 cap 与 v1866 cap 同步从 1266 收到 1258。baseline 只有删除没有新增。

文档侧先写 family design，再写本篇中文讲解、技术证据矩阵、CHANGELOG、进度账本、最终证据候选与优雅路线图；讲解在最终 verify 前完成。归档会通过 long-path-safe 脚本重建精确 SHA-256 manifest，并把文件数与 raw bytes 同步写入 policy 和 ArchiveRetentionTests。最后还要经过完整 Maven verify、实现 commit/push/CI、closeout commit/push/CI 与 annotated tag。禁止硬凑版本号或字数；本项目把每个数字绑定到脚本、测试、commit、run 与 tag，只有整条链闭合才称为这一版本完成。

## 一句话总结

v1882 在不改变任何路由、响应、Catalog、事务、权限或跨项目契约的前提下，用一个有类型的 `ReportRenderer` 和一套先旧后新的逐字 oracle 取代九个一次性展示壳，同时修复无效路由断言、收短六类测试职责、净删三十五条长名基线，并把所有收益固化为只能继续收紧的机械门。
