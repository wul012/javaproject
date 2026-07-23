# v1895：verification dossier 证据目录与共享计数引擎讲解

本版继续提高本项目 coding brilliant and elegant 的实际质量，但没有增加功能路由，也没有把重构包装成新业务。处理对象是 operator CI verification dossier：它已经能稳定返回只读证据，却由十个同生命周期 Catalog、一个三百九十四行长名 Support 和一组展开参数共同装配。v1895 先在 released v1894 上冻结完整 JSON，再把内部结构收敛为一个不可变 Evidence；同时发现前三个已整理 family 都复制了同一计数算法，于是遵守第三次规则建立共享 owner。以下说明只讲真实代码、真实输入输出和可复现检查，禁止硬凑篇幅，也不把尚未运行的跨项目联调说成已经完成。

## 入口路由

外部入口仍是 GET `/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-registry`。路径由既有 ReleaseAcceptance RoutePaths 的基础路径和 dossier 后缀拼接，Controller 仍留在根 ops 包，继续把请求委托给公开 registry service。v1895 没有修改 HTTP 方法、路径字节、Controller 类型、Spring 注解、response 类型或序列化字段，因此现有调用方不需要知道内部 Catalog 被替换。

请求没有 body，也不接收地址、账号、密钥、命令、订单号或部署参数。入口的实际输入来自进程内已经存在的 consumer-package registry service。它返回一份强类型只读 response，包含来源 digest、manifest、受众、package section、准入条件、CI matrix、boundary lock、handoff checklist、scorecard 和九段 Markdown。dossier service 只读取这份对象，不重新访问磁盘归档，不调用 Node，不连接 mini-kv，也不根据字符串猜测上游结构。

公开 `registry()` 继续带有 `@Transactional(readOnly = true)`。这个约束说明即使未来上游内部增加数据库读取，此入口仍以观察为默认事务语义。调用链中没有 repository save、消息发送、进程启动或外部 HTTP。输入是一份已经生成的 Java record，输出是一份更适合 verification dossier 审阅的 Java record；中间只发生确定性映射、计数、Markdown 生成和状态判断。

可以把入口机理画成一条单向管线：Controller 接收无副作用 GET，service 读取一次 consumer package，Catalog 生成一次完整 Evidence，Renderer 从 Evidence 生成十段展示，Support 从同一 Evidence 计算三十四条 checks 和最终状态，Spring 最后序列化原公开 response。任何环节发现缺项只会输出 blocked，不会自动补齐证据或执行外部动作。

## 响应模型

公开 response 的形状保持原样。顶层首先给出 project、version、endpoint、profile、三个来源计划和七个能力位；随后给出上游 consumer-package 的版本、endpoint、状态以及 dossier 自身状态；再给出每组证据的总数和通过数；最后依次携带十组结构化列表、十段 Markdown、三十四条 checks 和最终 status。v1895 没有把 record 改成松散 Map，也没有删除类型信息来换取短代码。

十组证据依次是一条 source package snapshot、六条 provenance、九条 section digest、四条 audience route、五条 CI lane、五条 acceptance gate、八条 boundary audit、五条 release checklist、四条 handoff receipt 和十条 scorecard。展示层还有十个 Markdown section，因此完整数量向量是 `1/6/9/4/5/5/8/5/4/10/10/34`。最后一个数字是 checks 数量，不是 scorecard 数量。

新建的 `DossierCatalog.Evidence` 只是包内所有权模型，不是新的网络协议。它一次持有十组列表，并在紧凑构造器中逐组调用 `List.copyOf`。这样 Catalog 交付后，Renderer 和 Support 只能读取同一份不可变快照，不能各自保留一套可变集合，也不能在渲染后悄悄改变用于状态判断的数据。公开 response 继续接收这些相同列表，所以序列化结果保持兼容。

为了证明“不变”不是口头承诺，`DossierResponseOracleTests` 在删除旧实现以前先运行。测试将整个 response 按属性名稳定排序后序列化成 UTF-8 JSON，并计算 SHA-256，得到 `f9ee01616f66f941914558105fbf7fe2652deb82891058fde433a06dcaf92a92`。新实现完成后同一个数量向量和同一个摘要继续通过。只要字段值、字段顺序、列表顺序、状态文本、Markdown、checks 或安全位有一处漂移，这个 oracle 就会失败。

## 上游证据配置

唯一业务上游是 `minimalreadonlygateoperatorciconsumerpackage` 包中的公开 service。它在 v1894 已经收敛成自己的 `PackageCatalog.Evidence`，但 dossier 并不跨包读取那个内部类型，而是通过公开 consumer-package response 消费。这个设计保持 package boundary：上游可以继续优化内部实现，只要公开 response 不变，dossier 就不会被迫同步修改私有结构。

provenance 六项分别记录 consumer package 的 version、endpoint、profile、source digest version、source digest state 和 consumer package state。每一项都要求非空，空值会得到 blocked。section digest 不重新解析 Markdown 文本语义，只保留 heading、line count、required 和 status；heading 非空且行数大于零才通过。audience route 则把上游四个 consumer packet 映射到 operator review、CI non-docker regression、archive verification 或默认只读审阅 lane。

CI lane 保留上游顺序、batch、command family、readOnly、sourcePassed 和 status，并把 focused、grouped、build、smoke 映射为稳定 replay group。这些值描述验证批次如何分类，不是可以直接执行的命令。acceptance gate 为每条上游 criterion 加上 verification-dossier artifact 名；boundary audit 保留 locked behavior，并把上游 reason 包装为明确审计证据；checklist 和 receipt 则分别描述交接步骤与接收者。

所有投影方法只依赖一个 source 参数。没有方法读取全局可变状态，没有随机值、当前时间或环境分支。计划标识 `Node v367`、`Node v368`、`Node v369` 仍由 Support 作为既有响应元数据输出，但本次 Java 请求不会启动 Node。配置身份、证据投影和最终决策被分开：service 知道入口与上游，Catalog 知道如何映射数据，Support 知道如何判断完整性。

## 服务层核心流程

旧 service 的 `registry()` 需要依次知道 SourcePackageCatalog、ProvenanceCatalog、SectionDigestCatalog、AudienceRouteCatalog、CiLaneCatalog、AcceptanceGateCatalog、BoundaryAuditCatalog、ReleaseChecklistCatalog、HandoffReceiptCatalog 和 ScorecardCatalog。它先创建九组局部变量，再把这些变量全部传给 scorecard，之后又把十组变量展开传给 Support 和 Renderer。虽然每个文件不大，但编排层被迫知道 evidence 的全部内部组成。

新 service 只有两个有意义的局部值：`source` 与 `evidence`。它读取一次上游，然后恰好调用一次 `DossierCatalog.evidence(source)`。结构门会计算这段调用文本出现次数，少于一次说明没有使用统一 owner，多于一次说明生成了可能漂移的重复快照。随后 `ReportRenderer.render(evidence)` 和 `DossierSupport.response(..., evidence, markdown)` 都读取同一个对象，参数列表不再随着 evidence component 数量线性膨胀。

`DossierCatalog.evidence` 仍按业务依赖顺序执行。前九组列表先从 source 产生，scorecard 最后统计这些已经生成的列表。scorecard 不重新访问上游来构造另一份实际数，也不向 Support 询问期望值；十个 shape constant 由 Catalog 所有，表达它承诺生成的数据规模。Catalog 源码被机械门禁止引用 Support，从代码方向上固定“生产数据在前，判断状态在后”。

`DossierSupport` 首先建立一个小型 `Summary`，对 provenance、digest、audience、CI、gate、audit、checklist、receipt 和 scorecard 计算通过数。然后它用同一 evidence 生成三十四条 checks，并核对上游 status、安全位、每组 expected size、每组 passed size 与 Markdown 数量。只有全部条件成立才返回 passed。这个过程不修改 evidence，也不尝试把失败项目过滤掉后伪装成完整集合。

原十个 Catalog 共五百八十八行，长名 Support 三百九十四行，service 一百零四行。新 `DossierCatalog` 经 Spotless 格式化后是二百九十九行，`DossierSupport` 二百二十六行，service 四十行，Renderer 一百九十六行。合并的是同一生命周期的纯投影，HTTP、事务、公开模型、渲染和判定仍分层存在，因此这不是把碎片简单粘成一个新巨型类。

## Java 证据检查

Java 证据检查有三层。第一层是来源：上游 response 必须 passed、readOnly，并且不能允许执行、启动 Java、启动 mini-kv、读取 credential、解析 raw endpoint 或访问 managed audit HTTP。第二层是形状：十组列表的实际大小必须分别等于 Catalog 常量，source 自带的九段 Markdown 也必须对应九条 section digest。第三层是内容状态：所有 required、ready、readOnly、passed 或 locked 项的统计数必须等于各自列表总数。

计数算法过去分散在多个 family。ArchiveCatalog、DigestCatalog、PackageCatalog、PackageSupport 以及旧 Dossier Scorecard 和 Support 都以略不同名字复制 `stream().filter(...).count()`。若 v1895 再在新 Catalog 和新 Support 各写一份，代码看似局部方便，却会继续积累第五、第六份机制。新 `EvidenceCounts.matching` 因此放入现有 `evidencecore`，只接收列表与 `Predicate<? super T>`，使用 `Math.toIntExact` 返回精确 int。

共享工具只抽象算法，不抽象业务判断。Archive 仍写 status passed，Digest 仍写 packet ready 与 instruction readOnly，Package 仍写 audience ready、criterion passed、lock locked，Dossier 仍写 route ready、gate passed、audit locked。读代码的人能在调用点看到领域条件，而不会跳进一个巨型通用规则引擎。`EvidenceCountsTests` 验证只统计匹配项且不修改输入；结构测试要求六个调用者都引用共享 owner，并禁止重新导入 Predicate 创建本地 helper。

中央 ratchet 也把收益固定下来。生产 Java 从一千三百二十四降到一千三百一十六，ops 从一千一百九十二降到一千一百八十四，Catalog 从二百七十五降到二百六十六，Readiness 文件从九百九十六降到九百八十五。renderer 数量保持三十，总行数从三千二百一十九降到三千二百零九。未来提交只要让这些值回涨，机械门就会失败。

## mini-kv 证据检查

本接口中的 mini-kv 只出现在安全声明和跨项目语义中。顶层 `startsMiniKvService` 固定为 false，`executionAllowed` 固定为 false；入口没有 host、port、RESP payload、key、value、WAL 路径或 snapshot 参数。DossierCatalog 的唯一输入是 Java 内存中的 consumer-package response，EvidenceCounts 只对 Java List 计数，Renderer 只生成 Java 字符串，因此这条调用链没有触达 mini-kv 的技术路径。

CI lane 和 handoff receipt 可能描述 archive verification 或 operator CI，但这些记录是证据，不是命令。passed 只表示现有证据满足当前只读审阅条件，不能升级为写路由、admin 操作、WAL 追加、snapshot 生成或 shard 切换权限。若某项不满足，Java 只返回 blocked，不会通过启动 mini-kv 或执行 CLI 来自动补证。

所以本版对 mini-kv 的运行时输入与输出都为零。它能证明 Java 侧没有越过已声明边界，却不能证明真实 mini-kv 二进制已联调。真实跨项目测试必须由单独的 opt-in capstone 启动进程、记录新鲜输出并验证 no-write 行为；当前单仓重构不会冒充那份证据。

## 阻断与安全边界

安全边界的核心是证据与能力分离。response 可以包含十条全绿 scorecard，但顶层仍只有 readOnly 为 true，executionAllowed、startsJavaService、startsMiniKvService、readsCredentialValue、resolvesRawEndpointUrl 和 managedAuditHttpAllowed 全为 false。证据完整不会自动授予部署、回滚、写数据库、读取密钥或连接受管审计系统的权限。

blocked 路径也保持透明。来源状态失败、字符串为空、Markdown 无内容、consumer packet 未 ready、CI lane 非只读、acceptance gate 未通过、boundary 未锁、checklist 未就绪或 receipt 缺少来源证据，都会使某个计数低于总数，最终 status 变为 blocked。Support 不降低 expected count，不删除失败项，不修改上游 response，也不启动补偿流程。

结构安全同样有明确失败条件。当前 Dossier package 必须精确为五个生产文件，测试 package 必须精确为六个短名文件；十个退休 Catalog、旧长名 Support、六个旧长名测试和旧 v1847 结构类不得复活。Catalog 必须少于三百行、恰好十次 `List.copyOf`，service 必须恰好装配一次，Catalog 不能依赖 Support。任何绕开这些约束的提交都会在 Maven verify 前被结构测试拒绝。

完整 response oracle 则保护公开安全边界。有人即使只把一个 false 改成 true，或把 blocked 文本改成 passed，SHA 也会改变。规则禁止为了重构通过而修改 oracle 期望、fixture 字节或历史 archive。若将来确实要改变公开协议，必须另立契约版本、迁移说明和上下游证据，不能夹带在内部优雅化版本中。

## 测试覆盖

测试首先按时间顺序建立因果。`DossierResponseOracleTests` 在 released v1894 的旧实现上先通过，证明摘要来自已发布行为；之后才删除旧 Catalog 与 Support。重构完成后的第一轮相关选择覆盖 archive、digest、consumer-package 和 dossier，验证共享 count 改造没有让前三个 family 回归，同时验证 dossier 完整 SHA 不变。测试重组后，同一批行为和 oracle 再运行一次。

当前 dossier 测试只有六个 owner。`DossierRegistryServiceTests` 检查 endpoint、profile、来源计划、consumer 状态、最终 status 和全部安全位。`DossierCatalogTests` 检查九条 digest heading、四条 audience route、五条 CI lane 的顺序和 replay group、五条 gate、八条 audit、五条 checklist 和四条 receipt。`DossierChecksTests` 锁十二段数量向量、三十四条 checks、关键禁止项和集合不可变性。

`DossierMarkdownTests` 保留十段 Markdown 的精确 heading 与每一行文本；`DossierResponseOracleTests` 覆盖局部测试可能漏掉的任何公开字段；`DossierTestData` 继续通过真实 service 链构造 response，不手写一份迎合新实现的假对象。六个旧长名测试的断言被重新分配到这些 owner，没有为了文件变少而删掉来源、边界、顺序或不可变检查。

`DossierExtractionTests` 负责维护结构。它精确比较五个生产文件和六个测试文件，检查退休清单，确认 Controller 留在根包、下游 release acceptance 仍导入公开 dossier 边界，并检查 Catalog 行数、copy 次数、service 装配次数、typed renderer/support 和共享 count owner。`ConsumerPackageExtractionTests` 也同步更新为真实三个 dossier 上游消费者，避免历史结构门要求已退休类复活。

中央 `JavaChangeGateTests` 检查新增文件名不超过四十字符、生产源码净增长受控、family design 在实现前存在；`JavaEleganceGateTests` 检查长名三指标与 exact baseline 只能减少；`OpsEleganceCensusTests` 检查 ops、Catalog、renderer 和当前 family 文件数。最终还要执行完整 verify、JaCoCo、SpotBugs、jar、production-profile smoke 与 canonical CI，focused 通过不能替代发布闭环。

## 实际工作量说明

生产侧删除十个 Catalog 和一个三百九十四行旧 Support，新增一个二百九十九行 Catalog、一个二百二十六行 Support 和一个十二行共享计数工具；同时修改 service、renderer 以及四个相邻 family 的调用点。Dossier package 从十四个文件降到五个，生产 Java 和 ops 各净减八个，Catalog 总数净减九个。新文件没有超过三百行，且没有改变公开 response 或路由。

测试侧新增旧实现 oracle、共享工具测试和三个短职责测试，删除六个长名综合测试，并把二百余行历史结构门改为当前语义的 `DossierExtractionTests`。测试 Java 从九百零七降到九百零六，当前 package 从八个降到六个，但行为证据更完整。生产长名 stem、出现次数、唯一名从 `1084/19785/2643` 降为 `1073/19646/2632`；测试从 `701/9807/3672` 降为 `694/9780/3655`。exact baseline 删除四十六项，新增零项。

工作流程也属于交付内容。先执行 Step-0 对账和 CodeGraph 依赖调查，再写 family design；在旧实现上建立故意失败后固定的 oracle；实现 Evidence、Support 和共享 count；运行第一轮相关测试；重组测试与结构门；运行第二轮行为和维护性测试；测量 census 后收紧 ratchet；最后在完整 verify 以前写本讲解。没有把测试期望改到能过，也没有在构建以后补写一篇无法参与门禁的说明。

本版的价值不只是少九个文件。更重要的是让“一次来源读取产生一份不可变 dossier evidence”成为代码中的直接概念，让 service 不再知道十个投影细节，让 Renderer 与 Support 读取同一事实，让跨 family 的通用计数算法只有一个 owner，并把这些边界都变成会失败的机械检查。这些收益能降低后续修改的认知成本和遗漏概率。

## 一句话总结

v1895 在完整输出零漂移的前提下，把十个零散 Dossier Catalog 收敛为一份不可变 Evidence，把重复计数提升为跨 family 的小型纯函数，并用短职责测试、精确 inventory、只减 ratchet 和冻结 SHA 共同守住结果；本项目因此更容易阅读、修改和审计，同时仍严格保持只读、不启动、不写入、不读取秘密的边界。
