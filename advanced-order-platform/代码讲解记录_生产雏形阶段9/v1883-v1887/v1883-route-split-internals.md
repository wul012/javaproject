# v1883 Route Split 内部模型收束代码讲解

## 入口路由

本版本处理的是 `releaseacceptanceroutepathsplit` 包内部的可读性债务，而不是新增一个业务入口。对外入口仍然由 `OpsShardReadinessReleaseAcceptanceRoutePaths`、主 Service、主 Response、closeout Service 和 closeout Response 五个公共类型承载。主登记接口继续使用 `/api/v1/ops/shard-readiness/release-acceptance-route-path-split-registry`，收尾接口继续使用 `/api/v1/ops/shard-readiness/release-acceptance-route-path-split-closeout-registry`。控制器、Spring 映射、事务注解、响应版本和 profile 都没有改变，所以调用方看不到包内重构。

这里最值得解释的是“稳定 barrel”和“窄 owner”的关系。根包 `OpsShardReadinessRoutePaths` 是历史兼容入口，已有控制器与测试可以继续从它读取常量；窄包中的 `OpsShardReadinessReleaseAcceptanceRoutePaths` 是 release acceptance 路由的领域所有者。`RouteCatalog` 同时读取两边的十一组常量，逐项比较 base path 和相对路径。匹配时产生 `legacyCompatible=true`，不匹配时产生 blocked 条目。也就是说，拆分不是复制一份常量后靠人工承诺同步，而是运行时只读证据会显式告诉审查者两边是否一致。

旧测试里有两条看似验证兼容、实际却把同一个常量与自身比较的断言。v1883 将它们改成根 barrel 与窄 owner 的真实比较；closeout 路由没有根别名，因此改为对固定后缀和最终 endpoint 分别取证。这个修复没有改变生产行为，却让测试真正具备发现漂移的能力。入口层由此形成三重证据：公共常量值不变、控制器最终 endpoint 不变、稳定入口与领域 owner 的重叠常量逐项相等。

## 响应模型

主响应 `OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse` 仍保存来源快照、路由条目、兼容检查、安全边界、消费者交接、计分卡、Markdown 分节、checks 与最终 status。它不是随意拼装的字符串袋，而是一组有类型的不可变记录。路由条目同时保存 stable path、split path、两个入口名称与兼容结果；因此审查者既能看到结论，也能追溯结论由哪两个值比较而来。主响应的数量契约仍是一个来源、十一条路由、十一项兼容检查、七项边界、五项消费者交接、八项计分卡和六个 Markdown 分节。

closeout 响应继续承担第二层结论：它不重新复制所有主响应字段，而是引用主版本、主 endpoint、route path 数量与 compatibility 数量，再增加六项 closeout item、七项 boundary assertion、三段 Markdown 和十五条 checks。这种分层保留了“事实”和“对事实的收尾判断”之间的区别。主响应回答路由拆分是否兼容，closeout 回答稳定 barrel 是否保留、未来路由归属规则是否建立、Node 并行计划是否无需新证据以及只读边界是否继续锁定。

本版本没有为了缩短名称而改动公共 record，也没有把嵌套记录替换为 `Map<String, Object>`。这种替换虽然会让内部代码短一些，却会牺牲编译期约束、JSON 结构透明度和下游调用者的类型安全。真正需要缩短的是包内实现壳，而不是已经构成兼容契约的响应类型。`RegistryAssembler` 与 `CloseoutAssembler` 在接收目录结果时立即使用 `List.copyOf` 建立所有权，最终响应继续暴露不可变列表；新增测试还验证 Markdown 分节内部的 lines 同样不可修改，避免只保护外层列表而遗漏第二层可变入口。

## 上游证据配置

主 Service 的唯一上游仍是 `OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffService`。它提供 Java v1547 的 archive verification handoff，主登记服务只调用其只读 registry 方法，然后由 `SourceCatalog` 投影为一条来源快照。来源快照保留 source 名称、版本、endpoint 与 status，不读取文件系统之外的新数据，也不根据 Node 或 mini-kv 的当前进程状态改变结果。上游若不是 passed，主装配器会把最终状态判为 blocked；因此上游事实被消费，但上游不能绕过本族的本地数量、兼容和边界检查。

下游是 sustainment 服务。它只导入公共 closeout Service 与 closeout Response，不接触本版本新命名的 `CloseoutAssembler`、`CloseoutRenderer` 或任何目录类。这个依赖方向由 `RouteSplitStructureTests` 读取源码机械验证。包内实现即使以后继续调整，只要五个公共边界保持兼容，sustainment 以及更下游的 acceptance package 就无需同步修改。测试数据也遵循同样方向：`SustainmentTestData` 复用短名 `CloseoutTestData` 构造公共服务链，不复制一套假的 closeout 响应。

Node 计划名称仍作为证据文本存在：主登记记录 Node v1846 与 Node v1822-v1846，closeout 记录 Node v1847-v1866。这些字符串说明历史协作上下文，不是启动授权。Java 不会因为看到某个 Node 版本就读取 Node 工作区、访问 Node 端口或生成新的跨项目事实。上游配置的机理可以概括为：真实 Java handoff 提供输入，目录类做纯投影，装配器执行本地判定，下游只消费公共输出；任何内部类都不能跨过这条边界成为新的隐式接口。

## 服务层核心流程

重构前，主 Service 依次调用六个长命名 Catalog、一个长命名 Support、一个聚合 Renderer；聚合 Renderer 又转发给 Source、Route、Compatibility、Boundary、Consumer、Scorecard 六个 section renderer，最后通过 RendererSupport 创建 MarkdownSection。每个 section renderer 只有十几行，真正行为只是一次 stream map。文件很多，却没有形成可以独立演进的策略：阅读者必须在八个长文件之间跳转，才能确认六段报告的顺序和格式。

重构后，主 Service 的流程直接呈现领域步骤：`SourceCatalog.snapshots` 获取来源，`RouteCatalog.routes` 建立十一组稳定与窄路由对照，`CompatibilityCatalog.checks` 投影兼容结论，`BoundaryCatalog.guards` 声明七条只读边界，`ConsumerCatalog.handoffs` 声明五类消费者，`ScorecardCatalog.scorecard` 汇总八个决策维度。随后 `RegistryAssembler.response` 负责复制列表、计算通过数量、建立 checks 并判定 status；`ReportRenderer.render` 只负责把已经确定的数据渲染为六节报告。

这种划分让数据与行为边界清楚。Catalog 是确定性数据工厂，不负责最终状态；Assembler 是响应不变量的唯一所有者，不负责展示格式；Renderer 是输出顺序和行格式的唯一所有者，不重新计算业务结论；Service 只编排依赖。closeout 采用同样结构，但没有为了追求形式统一把两套报告硬合成一个万能引擎。主报告有六种不同记录类型，closeout 有两种记录和一段计划文本；两个短 renderer 分别拥有各自稳定输出，比引入字符串 key、反射或大量泛型参数更直接。

状态判定也保持透明。主装配器先确认 1/11/11/7/5/8/6 七组数量，再要求来源 passed、所有路由兼容、所有 compatibility matched、所有边界 locked、所有 handoff passed、所有 scorecard passed；全部满足才返回 passed。closeout 装配器确认 6/7/3 三组数量，要求主响应 passed 且路由与兼容数量都是十一，再要求所有 closeout item passed、所有边界 locked。这里没有异常吞噬，也没有“只要大多数通过”之类模糊规则。

## Java 证据检查

本版本先建立新 oracle，再删除旧实现。`SplitMarkdownTests` 冻结六节四十三行：一行 Source Handoff、十一行 Route Path Split、十一行 Compatibility Checks、七行 Boundary Guards、五行 Consumer Handoffs、八行 Scorecard。`CloseoutMarkdownTests` 冻结三节十五行：六行 Closeout Items、七行 Boundary Assertions、两行 Parallel Plan。两份测试比较的是完整 `MarkdownSection` 列表，不只是标题、数量或几个抽样字符串。

新 oracle 第一次运行时，生产代码仍是旧的八个 renderer 结构，十九项 focused 测试全部通过；随后才进行重命名、删除七个 section renderer 并写入两个短 renderer。替换后使用完全相同的十九项测试再次运行，仍为十九项通过、零失败、零错误、零跳过。这一顺序非常关键：若只在新实现完成后写期望值，开发者可能无意中把新输出当成历史契约；先在旧实现上通过，才能证明期望值来自既有行为而不是重构者的偏好。

`RouteCatalog` 的十一项对照仍由真实根常量和窄常量产生，`ReportRenderer` 只是读取记录字段。因而精确输出测试不是用重复常量模拟生产逻辑，而是穿过真实 service、上游 handoff test data、目录、装配器和渲染器。下游 sustainment 的 catalog 与 report oracle 也一起进入 focused 组，证明公共 closeout 方向没有被破坏。最终还会运行全量 Maven verify，让 Spring 上下文、JaCoCo、SpotBugs、Spotless、归档摘要和其余一千九百余项测试共同约束这一刀。

## mini-kv 证据检查

mini-kv 在系统关系里是更上游的只读基础设施证据源，但 Route Split 本身不读取 mini-kv 进程、不执行 `minikv_cli`、不连接 RESP 端口，也不改动 `D:\C\mini-kv` 的文件。主响应和 closeout 响应出现 mini-kv 的地方只有边界说明，例如 sibling-autostart 明确表示 Java 的并行工作不能启动 Java 或 mini-kv，Parallel Plan 也明确说本次 closeout 不要求新的 Node 或 mini-kv evidence。

这种“没有调用”不是遗漏，而是依赖治理的结果。v1883 是 Java 包内非契约重构，输入输出、路由和 fixture 字节均保持不变，所以不需要迫使 mini-kv 跟随发布一个版本。若为了证明重构而启动 mini-kv，反而会把内部可维护性工作错误地升级成跨项目运行依赖，增加环境波动并模糊授权边界。真正的跨项目 capstone 已有独立 env-gated 入口；本版本只需保证不破坏它所消费的 Java 公共边界。

测试通过三种方式证明零交互。第一，生产服务依赖图只包含 Java handoff Service 与本包纯类；第二，边界目录持续产出 credential-value-read、raw-endpoint-resolution、managed-audit-connection、sibling-autostart 等 locked 项；第三，精确 Markdown oracle 保留“不需要 fresh Node or mini-kv evidence”的输出。它们共同说明本版本没有以文档措辞掩盖新的外部调用，也没有把 mini-kv 的历史 fixture 改写成适配新实现的样子。

## 阻断与安全边界

本版本的七条主边界保持原值：禁止 write routing，禁止 active shard router，禁止读取 credential value，禁止解析 raw endpoint，禁止建立 managed audit connection，禁止 deployment/rollback，禁止 sibling autostart。它们不是注释，而是进入响应、计分卡、Markdown 和最终 status 的数据。任一 guard 变为 unlocked，主响应就 blocked；closeout 又从主响应映射同一组边界，任一 assertion 未锁定也会 blocked。

重构过程中没有引入 HTTP 客户端、数据库仓储、线程、进程启动或文件写入。两个 Service 继续使用 `@Transactional(readOnly = true)`；Renderer 和 Catalog 都是 package-private 的静态纯函数类，没有 Spring 注解和可变全局状态。五个公共类型之外的十二个生产文件全部使用不超过四十字符的领域短名，并由结构测试确认它们不是 public。这样既防止长内部名字回流，也防止为了让下游“方便调用”而扩大可见性。

失败条件被写入版本设计文档：公共路由、响应字段、事务边界或任一输出行变化，版本不得提交；旧壳残留或包外依赖内部类，结构门失败；修改历史 fixture、放宽 ratchet 或降低覆盖率来迁就重构，版本失败。发布流程还新增固定基准检查：`verify-release.ps1` 先解析上一枚正式 `v*-order-platform-*` tag，剥离到四十位 commit SHA，再把该 SHA 传给 Spotless，最后才运行 verify。它修复了 v1882 中远端 master 在 push 后前移、导致普通 ratchet 漏检当前改动的问题。

## 测试覆盖

测试职责从长命名壳收束为可扫描的角色。包内有 `SplitTestData`、`SplitCatalogTests`、`SplitCompatibilityTests`、`SplitImmutabilityTests`、`SplitMarkdownTests`，以及对应的 `CloseoutTestData`、`CloseoutCatalogTests`、`CloseoutImmutabilityTests`、`CloseoutMarkdownTests`。根包控制器测试改为 `SplitControllerTests` 与 `CloseoutControllerTests`。文件数量没有靠删除覆盖率下降来换取优雅，而是用同样九个包内文件承载更精确的职责。

行为层验证版本、计划、数量、只读标志、最终状态以及每类条目的 passed/locked 属性；兼容层验证根 barrel 与窄 owner 的真实常量；不可变层验证响应集合、checks、Markdown sections 和 section lines 都拒绝修改；输出层逐行冻结两份报告；控制器层验证完整 endpoint 与 profile。下游测试继续验证 sustainment 的七节三十八行报告和目录状态，确保这次包内重构没有只在本族自洽。

结构层的 `RouteSplitStructureTests` 精确列出十七个生产文件和九个包内测试，要求两个短 renderer 是本族唯一渲染 owner，要求十九个旧内部文件永远不存在，要求十二个内部类型短名且 package-private，并检查 sustainment 只引用公共 closeout 边界。全局 `OpsEleganceCensusTests` 将 renderer 上限从三十八收紧到三十二、总行数从三千五百二十一收紧到三千四百五十七、长 renderer 文件名从二十二收紧到十四；ops Java 总量的九处历史兼容门同步从一千二百五十八收紧到一千二百五十一。

## 实际工作量说明

本版本不是简单改文件名。生产侧从二十四个文件降到十七个，删除七个只有转发价值的 renderer；十二个内部实现改成 BoundaryCatalog、RouteCatalog、RegistryAssembler、ReportRenderer 等可在一屏内理解的名称，五个公共兼容类型保持不动。ops Java 文件由 1258 降到 1251，全项目生产 Java 文件由 1390 降到 1383；renderer 由 38 降到 32，总行数由 3521 降到 3448，长 renderer 文件名由 22 降到 14。

命名债务也出现可测下降。最终 census 显示生产长文件名由 1188 降到 1169，长标识符出现次数由 20495 降到 20376，唯一长标识符由 2747 降到 2728。测试侧把九个包内长测试名和两个根控制器测试名改成角色短名，并让 sustainment 测试数据复用新的 CloseoutTestData。exact name baseline 已据此重建，相对 v1882 删除六十六项且新增为零，后续提交若重新引入任何已清除长身份会直接失败。

工程工作还包括两轮同组 focused 回归、两个完整输出 oracle、三条失效断言修复、结构测试重写、census 脚本扩展、九处 ops count ratchet 收紧、发布脚本新增、AGENTS 规则升级、closeout 机械测试、归档 manifest 和进度证据更新。这里遵守“禁止硬凑”：讲解篇幅来自真实输入、输出、状态机、依赖方向和失败条件，本项目的每一项指标都能由仓库脚本或 Maven 测试重现。

## 一句话总结

v1883 在不改变任何公共路由、响应字段、事务边界和输出字节的前提下，把 Route Split 从二十四个长命名分散文件收束为十七个有明确职责的文件，并用旧实现先通过、重构后再通过的精确 oracle、下游回归、结构 ratchet 与固定 tag 发布脚本共同证明这次“变短”没有变松。
