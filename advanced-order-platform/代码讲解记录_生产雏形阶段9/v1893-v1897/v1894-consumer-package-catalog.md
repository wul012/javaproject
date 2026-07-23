# v1894：consumer package 证据目录收敛讲解

本版处理的是一个典型的后期维护问题：接口行为已经稳定，但内部为了分别生成 manifest、audience、section、acceptance、CI matrix、boundary lock、checklist 与 scorecard，逐渐形成了九个同形 Catalog。它们都读取同一个上游 digest response，却要求 service 知道九个构件的生成次序、参数和相互关系。v1894 不增加新路由，不改变任何公开 record，也不修改 fixture 字节；它先在 released v1893 上冻结完整响应，再把九次零散投影收敛成一次 `PackageCatalog.evidence(sourceDigest)`。本文只解释本项目真实代码和可复现证据，禁止硬凑概念或把尚未发生的联调写成事实。

## 入口路由

外部读者仍通过 GET 请求访问 `/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-registry`。前半段由 `OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH` 提供，后半段由同一个 RoutePaths owner 的 consumer-package 常量提供。v1894 没有改路径字符串，也没有新建第二套路由。Controller 仍只把请求委托给公开 registry service，然后把公开 response 交给 Spring 序列化。调用者看到的入口、HTTP 方法和 JSON 形状因此都保持不变。

这个入口的输入不是订单、密钥或执行参数。调用者不上传 request body，也不能传入 Java 进程地址、mini-kv 地址、数据库语句或凭证。真正的数据输入来自进程内已经存在的上游 `ArchiveDigestRegistryService`。当前 service 的 `registry()` 先调用一次 `sourceDigestService.registry()`，得到可信的只读 digest response；随后只调用一次 `PackageCatalog.evidence(sourceDigest)`。前者回答“事实从哪里来”，后者回答“这些事实如何组织成消费包”。入口本身不参与评分，也不尝试把 blocked 修成 passed。

`@Transactional(readOnly = true)` 继续固定在 service 的公开方法上。这不只是注释，而是本项目对未来维护者给出的事务约束：即使以后上游实现增加仓储读取，这条路线的默认意图仍是观察而非写入。Controller、RoutePaths 与 response 都未改名，所以下游 verification dossier 仍通过原公开类型和 endpoint 消费本接口。内部 release 名称是 v1894，但公开 response 的既有 `Java v1432` 值保持冻结，因为本次任务是重构实现，不是假借版本更新改变协议。

从输入输出角度，可以把入口概括为一条很短的管线：无副作用 GET 触发一次 Java 内部读取，读取得到一份 archive digest，Catalog 把它投影为九组强类型证据，Renderer 生成九段 Markdown，Support 计算计数、checks 与最终状态，最后返回原 response。任何环节发现数量或状态不满足预期，只会使结果变成 blocked，不会启动补偿动作。这样的入口让调用者能重复读取，也让测试可以冻结完整输出，而不是依赖不透明的运行时副作用。

## 响应模型

公开 response 仍包含项目、版本、只读与执行安全位、endpoint、profile、三个来源计划、上游 digest 身份、consumer package 状态、各组总数与通过数、九组证据列表、九段 Markdown、二十八条 checks 和最终 status。九组结构化列表依次是：一条 `SourceDigestSnapshot`、五条 `ManifestEntry`、四条 `ConsumerAudience`、五条 `PackageSection`、五条 `AcceptanceCriterion`、五条 `CiMatrixEntry`、八条 `BoundaryLock`、五条 `HandoffChecklistItem` 和八条 `ScorecardEntry`。在展示层之后还有九个 `MarkdownSection`，最后是二十八条机器可读检查说明。

因此完整数量向量是 `1/5/4/5/5/5/8/5/8/9/28`。这个向量不是文档里的估计值，而是 `PackageResponseOracleTests` 对真实 `ConsumerPackageTestData.registry()` 响应逐项读取的结果。为了捕获“数量没变但字段值或顺序变了”的情况，测试还用排序属性的 Jackson 配置序列化整个 response，以 UTF-8 字节计算 SHA-256。released v1893 上先得到并锁定 `1ae92cfe8926ecb9ae772c8eec70dd8cddfbc1b0654e11685ef6304249803c60`，生产重构后同一摘要继续通过。

内部新建的 `PackageCatalog.Evidence` 不是新的 HTTP 类型，而是包内数据所有权边界。它一次持有上述九组结构化列表，每个组件都在紧凑构造器中执行 `List.copyOf`。这意味着 Catalog 返回后，调用方不能通过保留原集合引用去篡改 evidence，也不能向 evidence 暴露的集合追加元素。`PackageCatalogTests` 会先用可变列表构建一份 Evidence，再清空来源并尝试修改结果，从而证明这九次复制确实形成不可变快照。公开 response 没有被替换为 Map，也没有失去 record 的字段类型，所以编译器和 Jackson 仍能共同约束协议。

响应里的 count 字段并不信任文档描述，而是从最终将要返回的那些列表重新统计。比如 `passedManifestEntryCount` 来自 manifest 中 status 为 passed 的元素，`readyConsumerAudienceCount` 来自 audience 的 ready 位，`lockedBoundaryLockCount` 来自 lock 的 locked 位。最终 status 同时要求上游 passed、九组形状等于 Catalog 常量、每组通过数等于实际总数、九段 Markdown 齐全。换言之，状态是对真实输出的校验，而不是一枚先写好的绿色标签。

## 上游证据配置

唯一上游是 `minimalreadonlygateoperatorcihandoffarchivedigest` 包中的公开 registry service。它提供 version、endpoint、profile、archive version、digest state，以及 digest section、consumer packet、replay instruction、boundary lock 等已验证事实。本版没有读取磁盘归档目录来重新猜测这些值，也没有从 Markdown 反向解析数据。service 直接拿到上游强类型 response，Catalog 再按字段进行确定性投影，因此输入来源、字段来源和输出位置都可以沿 Java 调用链追踪。

`PackageCatalog` 的 source snapshot 保留上游身份和数量；manifest 把 source plan、required archive plan、operator handoff plan、source digest version 与 digest state 组织成五个必需条目；audience 描述 operator、CI、operator-CI 和 release review 四种消费者所需的包；section 说明 source digest、manifest、audience、CI matrix 与 boundary locks 五块内容由谁负责以及取自哪组证据。它们不是九个互不相关的列表，而是同一次消费包装配的不同视图，所以由一个 Evidence owner 持有更贴近真实领域概念。

来源计划常量仍由 `PackageSupport` 保留：`Node v367`、`Node v368` 和 `Node v369` 只是已有契约中的来源标识，不会在本次 Java 请求中启动 Node。consumer package state 也继续使用既有字符串。Catalog 不读取环境变量，不解析 raw endpoint，不接触 credential，不发 HTTP；它的全部输入就是一个 Java record。配置与投影因此被明确分开：service 知道上游 service 和本接口身份，Catalog 知道如何从输入生成证据，Support 知道如何按固定政策判定完整性。

这种分界还消除了原实现中的隐含耦合。过去九个 Catalog 分别拥有一组列表，service 必须逐项调用，并把九个局部变量按相同顺序同时传给 renderer 和 Support。只要新增一组证据，多个签名和多个调用点就会一起扩张。现在 service 对目录只有一个入口，Renderer 与 Support 对数据只有一个 typed 参数；未来若协议真的获准增加一组证据，编译器会在 Evidence 构造、渲染和判定处给出明确缺口，而不是允许某个散落的列表被忘记。

## 服务层核心流程

当前 `registry()` 的核心流程只有四步。第一步读取 `sourceDigestService.registry()`；第二步建立 `var evidence = PackageCatalog.evidence(sourceDigest)`；第三步调用 `ReportRenderer.render(evidence)`；第四步由 `PackageSupport.response(...)` 组装公开 response。结构测试要求 `PackageCatalog.evidence(` 在 service 中恰好出现一次，避免后续为了不同消费者重复构造两份可能漂移的快照。Catalog 也被禁止依赖 Support，依赖只能按“source response 到 Catalog，再到 Renderer/Support，最后到 public response”的方向流动。

`PackageCatalog.evidence` 按依赖顺序生成列表。source、manifest、audience 和 section 直接由上游与稳定计划映射；acceptance 把 source status、digest 完整度、consumer packet、read-only replay 和 boundary lock 转成五条准入条件；CI matrix 将五条上游 replay instruction 保序映射为批次、命令族、只读位和状态；locks 保序复制八条边界；checklist 根据整体来源是否 ready 生成五步交接清单；scorecard 最后统计已经生成的 manifest、audience、section、acceptance、CI、lock 和 checklist，而不是再次从上游推导另一套影子结果。

原九个 Catalog 合计约五百行，而且存在一个 183 行的 Scorecard Catalog。它们没有独立生命周期，也没有不同依赖方向，拆成九个文件只增加导航和协调成本。新 `PackageCatalog` 经格式化后 262 行，低于 300 行门。它不是把所有职责塞进巨型类：HTTP、事务、Markdown、checks、最终 status 和 public response 构造都仍在其他 owner 中。这里合并的是同一个抽象内被人为分开的投影，而不是取消合理分层。

旧的长名 Support 有 346 行，其中八个方法只是重复执行 `stream().filter(...).count()`。本版将它改为 203 行的 `PackageSupport`，并用一个 `<T> count(List<T>, Predicate<T>)` 表达共同机制。这个泛型方法只消除重复，不隐藏领域条件；每个调用点仍清楚写出 passed、ready、readOnly 或 locked 谓词。形状常量改由 Catalog 所有，Support 只引用 `SOURCE_COUNT` 到 `SCORECARD_COUNT`，从而避免“数据生产者反向询问状态判定者应生产多少数据”的倒置依赖。

## Java 证据检查

Java 检查分为来源真实性、形状完整性和状态一致性。来源真实性由 source snapshot 与五项 manifest 表达：版本、endpoint、profile、计划和 digest state 都必须来自既有常量或上游 response。形状完整性由九个 Catalog 常量与九个列表实际 size 比较。状态一致性则要求每一组 passed、ready、readOnly 或 locked 的数量等于该组总数。只有三层都通过，最终 status 才能是 passed；任意一处不足都会得到 blocked。

二十八条 checks 为机器和人工审阅者提供逐项诊断。前三条绑定三个计划，随后记录上游 digest 版本与状态；中间成对记录九组列表的总数和通过数；最后固定 no-upstream-autostart、no-write-routing、no-secret-value、no-raw-endpoint-resolution 与 no-managed-audit-http。它们的价值在于把一个总状态拆成可定位原因：看到 blocked 时，可以判断是 source 未通过、列表缺项、边界未锁还是展示段缺失，而不必根据一串布尔表达式猜测。

本版先写 oracle 再删除旧实现。oracle 最初使用待定摘要故意失败，从失败输出取得 released v1893 的真实 SHA，然后写入固定期望；随后才替换 Catalog 与 Support。这样测试证明的是“新实现等于旧发布行为”，而不是“新实现等于自己刚写的期望”。局部语义测试继续检查每个列表的关键内容和顺序，完整 oracle 锁全部字段，结构测试锁文件清单、装配次数、复制次数、依赖方向和尺寸，三类证据覆盖不同风险。

全局机械门也随收益立即收紧。生产 Java 从 1332 降到 1324，ops 从 1200 降到 1192，Catalog 从 283 降到 275，当前包从 13 降到 5；renderer 数量仍为 30，但总行数从 3228 降到 3219。`Readiness` 文件精确为 996。测试 Java 从 906 变为 907，是因为新增完整响应 oracle 与职责化测试后仍删除了旧重复测试。所有数字都来自脚本或文件系统 census，不靠手工估算，并写入会在回退时失败的断言。

## mini-kv 证据检查

consumer package 中出现 mini-kv，是为了声明跨项目证据消费的安全边界，不代表这个 Java endpoint 会调用 mini-kv。公开 response 的 `startsMiniKvService` 保持 false，`executionAllowed` 保持 false，入口没有 host、port、socket、CLI 参数或 key/value。Catalog 只接收 Java 上游 record，Renderer 只把内存中的 typed evidence 转成 Markdown，Support 只做本地计数和布尔判断。整条调用链不存在进程启动器、网络客户端或命令解释器。

CI matrix 的五条记录来源于上游 replay instructions。每条记录保留 batch、command family、sourcePassed，并要求 readOnly；它们描述怎样复用已经归档的证据，不是可以执行的 PowerShell、Bash 或 RESP 命令。boundary locks 同样是审计事实，不能打开连接。即使最终 scorecard 全绿，consumer package 也只告诉审阅者“当前证据包满足只读交接条件”，不会获得写 mini-kv、触发 WAL、生成 snapshot、改变 shard routing 或启动服务的权限。

因此本版对 mini-kv 的运行时输入和输出都为零。Java 可以验证自身是否仍然遵守“不启动、不写入、不读取秘密、不解析原始端点”的承诺，但不能用这次单仓重构替代真实跨项目联调。若未来由 capstone 发起 opt-in 联调，应由独立流程显式管理 Java 与 mini-kv 进程，并记录新鲜输出；不能把当前 GET 观察接口偷偷升级成编排器。讲解明确这条证据能力边界，避免把“契约对齐”夸大成“真实系统联调已完成”。

## 阻断与安全边界

response 顶层继续固定 `readOnly=true`，其余六个能力位 `executionAllowed`、`startsJavaService`、`startsMiniKvService`、`readsCredentialValue`、`resolvesRawEndpointUrl` 与 `managedAuditHttpAllowed` 全为 false。这里最重要的机理是证据与能力分离：证据可以证明某个批次通过，但不会因为 passed 自动获得部署、回滚、写路由、读取 secret 或访问受管审计网络的能力。状态计算只能收紧判断，不能扩权。

blocked 也不会触发自愈。假如上游 source status 不是 passed，某个 manifest 缺项，CI matrix 中出现非只读项，boundary lock 未锁，或 Markdown 少一段，Support 只会返回 blocked 和对应计数。它不会填造缺项，不会把 expected 降到 actual，不会忽略失败项，也不会重跑外部系统。对于 readiness 接口，透明失败比自动修复更安全，因为审阅者看到的是可审计事实，而不是为了绿色结果产生的新副作用。

结构边界同样被机械化。九个退休 Catalog、旧长名 Support 和三个旧长名测试文件都列入禁止复活清单；当前生产包最多五个 Java 文件，测试包最多六个；`PackageCatalog` 必须少于 300 行、恰好九次 `List.copyOf`；service 必须恰好装配一次；Catalog 源码不得引用 Support；Renderer 与 Support 必须接收 typed Evidence。全局 ops、Catalog、renderer 行数与长命名 baseline 只减不增。任何未来提交破坏这些条件，测试会在发布前失败。

公开边界则由完整 response SHA 保护。若有人调整 record 字段、列表顺序、状态文本、Markdown 内容、安全位或 checks，即使代码可以编译，摘要也会改变。若有人只删测试期望来迁就实现，released v1893 的冻结值和本版文档证据就会失去对应关系，因此规则明确禁止修改 fixture 字节或 oracle 来让重构通过。重构自由发生在实现内部，公开行为变化必须另立契约版本和迁移证据。

## 测试覆盖

`PackageCatalogTests` 按职责验证九组投影。第一组覆盖 source、manifest、audience 与 section；第二组覆盖 acceptance 和 CI 顺序；第三组覆盖 locks、checklist 与 scorecard；第四组验证九组集合的所有权隔离。测试不是逐行复刻实现，而是检查有领域意义的名称、数量、顺序、状态和不可变性。这样在内部方法继续优化时，测试不会因无关实现细节频繁破碎。

`PackageRegistryServiceTests` 由旧 source tests 改名而来，继续验证 endpoint、profile、计划、来源身份、最终 status 和全部安全位。`PackageChecksTests` 独立锁住二十八条 checks 的规模及关键禁止项。`ConsumerPackageMarkdownTests` 保留九段展示格式。`PackageResponseOracleTests` 冻结完整 response 向量与 SHA。六个测试 owner 各自回答“投影对不对、编排对不对、检查对不对、展示对不对、整体是否字节等价、结构是否会退化”，职责比原来的长名综合测试更容易定位。

`ConsumerPackageExtractionTests` 是维护性防线。它要求生产包精确存在 `PackageCatalog`、`PackageSupport`、公开 Response、公开 Service 和 `ReportRenderer`，要求测试包精确存在六个短职责 owner，并确认所有退休文件消失。它还验证 verification dossier 继续导入当前 consumer-package 边界，SpotBugs 对公开 response 的排除路径仍指向移动后的包。`DigestExtractionTests` 则确认 Catalog 和 Support 消费已提取的 digest response，service 消费已提取的 digest service，避免跨包引用悄悄回到根目录历史文件。

focused gate 会先运行上述行为、oracle、结构与全局 elegance 测试，便于快速定位。随后写入新的 exact-name baseline，并要求 Git 差异只有删除、没有新增长名条目。最终发布仍执行 `scripts/verify-release.ps1`：Spotless 检查改动 Java、完整 Maven verify、JaCoCo floor、SpotBugs、可执行 jar，以及 predecessor tag 的 peeled commit 校验。局部绿不等于发布完成，完整 gate 和 canonical CI 都必须成功。

## 实际工作量说明

生产侧删除九个同形 Catalog 和一个 346 行长名 Support，新增 262 行 `PackageCatalog` 与 203 行 `PackageSupport`，同时收短 service 参数展开与 renderer 签名。包内生产文件从十三个降为五个，减少八个；生产 Java 与 ops 也各净减八个。Catalog 总数净减八个。旧实现约五百行 Catalog 加三百余行 Support，现在职责落在两个尺寸受控的 owner 中，且 HTTP、公开 response 与展示仍保持独立，没有用一个新巨型文件掩盖删除数字。

测试侧新增完整响应 oracle、目录投影测试和 checks 测试，保留 Markdown 与 fixture owner，把旧 source tests 改成 service 职责名，删除两个重复综合测试，并把历史 v1846 结构门改成当前语义名。最终测试文件净增一个，说明行为保护比原先更厚而不是通过删断言换取整洁。命名 census 从生产 `1094/19898/2653` 收敛到 `1084/19785/2643`，测试从 `705/9816/3679` 收敛到 `701/9807/3672`；三个数字依次表示长文件 stem、长标识符出现次数和唯一长标识符。

工作并不是一次机械合并。先调查九个 Catalog 的输入和生命周期，再写十行内 family design；随后在旧实现上建立完整 response oracle，故意让待定 SHA 失败并取得 released v1893 基准；之后实现 Evidence、替换 service、renderer 与 Support，拆掉重复 count 方法；再按职责重组测试、更新跨包结构门和全局 shrink-only ratchet；最后才写中文讲解、归档 manifest、发布账本和完整验证。每个步骤都有可复现输入、明确输出和失败条件。

本版没有为了达到篇幅而加入无关抽象，也没有顺手改变业务协议。真正购买的优雅是：一个概念对应一个 owner，一份证据只装配一次，数据所有权由不可变快照保证，状态判定读取即将返回的真实列表，展示层不参与业务判断，安全能力始终关闭，历史重复文件无法静默复活。它把后期维护者需要同时理解的协调点从九个 Catalog 加一个长 Support，缩减到一条单向流水线。

## 一句话总结

v1894 在完整响应 `1/5/4/5/5/5/8/5/8/9/28` 与 SHA-256 不变的前提下，把九个分散 Catalog 收敛为一次 `PackageCatalog.Evidence` 装配，把 346 行长名 Support 收短为职责明确的 `PackageSupport`，让 Service、Catalog、Renderer 与状态判定形成可编译、可冻结、只读且不可回退的单向结构，同时把生产文件、Catalog、renderer 行数和长命名债全部写进只减不增的机械门。
