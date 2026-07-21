# v1881：Minimal Read-only Gate Execution 双报告渲染收敛讲解

## 入口路由

外部入口有两个，均为既有 GET 路由。execution 注册表地址是 `/api/v1/ops/shard-readiness/minimal-read-only-gate-execution-registry`，archive verification 地址是 `/api/v1/ops/shard-readiness/minimal-read-only-gate-execution-archive-verification-registry`。共同前缀来自 `OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH`，后缀也仍由同一个公开 route owner 提供。v1881 没有修改字符串、HTTP 方法、Controller 的 `@RequestMapping` 或 `@GetMapping`，所以调用方不需要改 URL，也不会看到重定向或兼容转发层。

一次 execution 请求进入根 `ops` 包的 Controller 后，Controller 只调用 `OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService.registry()` 并返回 response。一次 archive verification 请求同理进入另一个根 Controller，再调用 archive Service。两个 Controller 保留在根包，是因为它们承担 Spring Web 适配；二十三个实现文件仍位于 maintenance 子包，是因为它们承担证据编排。历史 v1843 结构门继续逐文件检查这个方向：实现不能重新泄漏回根包，Controller 也不能为了缩短名字被藏进内部包。

这两个 GET 没有通过 query、header 或 body 接收执行指令。输入来自编译期固定的 Catalog 和上一层结构化 response，而不是用户提供的命令、凭据或 endpoint。换句话说，HTTP 调用者只能要求“给我当前只读计划和证明”，不能要求“替我执行某个探针”。路由返回 JSON，其中 Markdown 只是供人阅读的一个字段；本版改变的仅是服务器内部如何从同一批强类型条目构造这个字段，入口、鉴权上下文和返回类型完全没有变化。

## 响应模型

execution response 先用布尔字段声明权限边界：`readOnly=true`、`executionAllowed=false`、`startsJavaService=false`、`startsMiniKvService=false`、`readsCredentialValue=false`、`resolvesRawEndpointUrl=false`、`managedAuditHttpAllowed=false`。随后提供版本、endpoint、profile、source plan、前序 smoke lane、registry state，以及 source plan、read target、gate check、boundary rule、CI batch、archive requirement、operator handoff 的计数和完整条目。最后才是 `markdownSections`、checks 与 status。结构化字段是机器契约，Markdown 是同一事实的可读投影，renderer 无权创造新事实。

`ExecutionRenderer` 接收六组已经构造好的强类型列表。Read Targets 逐条输出目标名、所有者、协议、地址 handle、命令或 route 与状态；Gate Checks 先按 group 保持首次出现顺序，再输出 `code=evidence`；Boundary Rules 输出禁止动作及 `allowed=false`；CI Batches 保留序号、名称、命令族和范围；Archive Requirements 输出 artifact、producer、evidence 与 required 标记；Operator Handoff 输出步骤、所有者和说明。它返回恰好六个不可变 `MarkdownSection`，不读取数据库、不调用网络，也不改变条目。

archive verification response 具有同样的权限布尔字段，但数据模型是验证结果：source registry snapshot、artifact verification、read target verification、gate check verification、boundary verification、CI batch verification、operator handoff verification 和 scorecard。`ArchiveRenderer` 把它们投影成 Source Registry、Archive Artifacts、Read Target Verification、Gate Check Verification、Boundary Verification、CI Handoff Scorecard 六节。最后一节同时有 CI batch、operator handoff、scorecard 三条计数头，因此它不是普通单 count 章节。本版有意让它保留在 `ArchiveRenderer` 内部，没有给共享引擎增加模式开关，也没有把不同 record 降级为 `Object`。

## 上游证据配置

execution Service 的输入全部来自七组 Catalog。SourcePlanCatalog 说明 Node 侧前序计划与通过数量；ReadTargetCatalog 定义 Java health、Java ops overview，以及 mini-kv `HEALTH`、`INFOJSON`、`STATSJSON` 五个只读目标；GateCheckCatalog 把二十项检查按 read-target、runtime-boundary、archive、lineage 四组组织；BoundaryPolicyCatalog 固定十条禁止动作；CiBatchCatalog 固定四个验证批次；ArchiveVerificationCatalog 固定六项应归档证据；OperatorHandoffCatalog 固定五个操作员交接步骤。Service 只负责按顺序取得这些列表并交给 response support 与 renderer。

archive verification Service 的第一项输入不是另一份复制的 fixture，而是调用 execution Service 得到的真实 source registry。ArchiveSourceRegistrySnapshotCatalog 从它提取版本、endpoint 和 source plan；ArtifactVerificationCatalog 验证六项归档要求；ReadTargetVerificationCatalog 验证五个目标；GateCheckVerificationCatalog 验证二十项门；BoundaryVerificationCatalog 验证十个禁止动作都被拒绝；CI 与 handoff verification Catalog 分别验证四批和五步；ArchiveScorecardCatalog 汇总七个 expected/actual 指标。这种依赖方向意味着第二份报告证明第一份报告，而不是两份报告各自维护一套容易漂移的数据。

v1881 没有修改任何 Catalog 常量，也没有重排列表。精确 oracle 中出现的 `Node v367`、`Java v1312`、五个只读目标、二十项检查、十条边界、四个 CI batch、六项 artifact 和五个 handoff 都来自旧实现真实输出。重构不把这些历史版本文字当作当前发布号更新，因为它们描述的是证据来源，不是 Java git tag。若未来证据版本需要升级，应由独立契约版本修改 Catalog 并同步下游，而不是在 renderer 优化中顺手替换。

## 服务层核心流程

execution 的运行顺序可以概括为“取事实、算响应、做投影”。Service 先读取七组 Catalog；RegistrySupport 根据列表计算通过数、拒绝数和状态；`ExecutionRenderer.render` 对相同列表生成六节 Markdown；最后 RegistrySupport 把标量、列表、Markdown 与 checks 一起装入公开 response。renderer 不参与是否通过的判断，因此把 renderer 删除或替换不会改变 `passedGateCheckCount` 等业务字段。Service 继续标注 `@Transactional(readOnly = true)`，重构没有新增 repository、client、process 或 shell 依赖。

普通章节通过 `MarkdownSections.counted` 建造。该方法把 `countName=size` 放在第一行，再按输入顺序应用 line mapper，最后调用 response 自己的 `MarkdownSection::new`。Gate Checks 的输入有二十条，但输出按四组压缩；新 `groupedCounted` 使用 `LinkedHashMap` 保存 group 首次出现顺序，每个 group 用 ArrayList 保存条目顺序，最后生成 `group: item; item`。它返回 `List.copyOf(lines)`，所以调用方不能在 response 创建后修改文本。这个原语只接收 group mapper、line mapper 和 section factory，没有引用 execution 包，因而共享引擎仍是领域中立的 Markdown 组装器。

archive Service 先调用 source Service，再依次创建八组验证数据与 scorecard，然后把这些数据同时交给 ArchiveRegistrySupport 和 `ArchiveRenderer`。`ArchiveRenderer` 对五个规则相同的章节调用共享引擎；scorecard 因为有三种计数来源，使用一个短小私有方法明确加入三行，再遍历七个 scorecard entry。这里没有为了少写几行而把 `ciBatches`、`handoffs` 和 `scorecard` 包进弱类型 map。类型系统仍能检查每个字段的 `actual`、`expected` 和 status，读者也能直接看出三条计数的来源。

## Java 证据检查

Java 侧可见的五个 read target 中，`java-health` 对应 `GET /actuator/health`，`java-ops-overview` 对应 `GET /api/v1/ops/overview`。这里的 response 只是保存“应读取什么”和“既有证据状态是什么”，本 Service 本身不会发出 HTTP 请求。Gate Checks 的 runtime-boundary 组明确包含 `UPSTREAM_ACTIONS_ENABLED=false`、不读取 credential value、不解析 raw URL、不调用 managed audit HTTP、不调用 runtime shell。Boundary Rules 又从权限角度声明 write routing、active shard router、deployment/rollback、Java autostart、ledger/schema/SQL write 都不允许。

精确 oracle 固定了 Java 行中的地址是 `ORDER_PLATFORM_URL handle`，而不是原始 URL。handle 允许运维层知道应使用哪个配置句柄，却不让展示代码解析或泄漏实际 endpoint。archive verification 的 Source Registry 行固定 source endpoint、Node 计划版本和 passed 状态；Boundary Verification 则把相同禁止项表示为 `denied=true | status=passed`。因此第一份 response 说明“规则要求不允许”，第二份 response 说明“归档验证证明已经拒绝”，两层语义没有被新 renderer 混成一个布尔值。

Java 机械检查不只看输出。v1843 门固定目标包二十三个文件、十个旧文件永久缺席、十四个目标包测试文件和两个旧 TestSupport 永久缺席；v1842、v1844、v1847 到 v1850、v1866 把全局 ops 上限收紧到一千二百六十六。`OpsEleganceCensusTests` 要求本包恰好只有两个短 renderer，全局 renderer 不超过四十五、总行数不超过三千六百一十六、长 renderer 不超过三十。`JavaEleganceGateTests` 与精确 baseline 又阻止删除的长名重新出现。

## mini-kv 证据检查

mini-kv 在本流程中是外部已经启动的只读目标，不是 Java 管理的子进程。五个 read target 中三项属于 mini-kv：`HEALTH` 用于确认服务状态，`INFOJSON` 与 `STATSJSON` 用于读取结构化信息和统计。地址只记录 `MINIKV_HOST/MINIKV_PORT handle`，没有解析主机和端口值。Operator Handoff 第一条明确要求 Java 和 mini-kv 必须由 Node 之外的操作者先行启动，Boundary Rules 又明确禁止 mini-kv autostart 与 mini-kv write/admin command。

这解释了为何本版本可以独立在 Java 仓库完成。renderer 的输入条目、命令拼写、计数、状态、JSON schema 和 route 都没有变化，mini-kv 不需要发布新版本，Node 也不需要更新消费代码。若 oracle 中 `HEALTH` 被改成其他命令，或新增可写命令，那将是跨项目契约变化，必须先核对 mini-kv 的 canonical surface，再按依赖顺序更新 Java 和 Node。v1881 没有做这种事情，它只证明相同三条只读证据在新结构中逐字符保留。

Archive Artifacts 中的 JSON、Markdown、summary、screenshot、walkthrough 与 gate manifest 仍是既有归档要求。Java 只展示 artifact 名、producer、evidence 和 status，不访问 mini-kv 文件系统，也不重写 Node fixture。这样既能让操作员看到跨项目证据链，又不会因为报告提到 mini-kv 就获得越权执行能力。

## 阻断与安全边界

阻断逻辑由 response 与 Catalog 保持，而不是由 renderer 重新解释。最关键的总开关仍是 `executionAllowed=false`。服务不会启动 Java、不会启动 mini-kv、不会读 credential value、不会解析 raw endpoint、不会连接 managed audit。十条 Boundary Rule 的 `allowed` 全为 false；archive verification 对应十条 `denied` 全为 true 且 status 为 passed。任何 invalid-read-contract 都要求停止并请求上游只读修复，不能由本服务自动修复、部署或回滚。

新 `MarkdownSections.groupedCounted` 是纯函数式组装边界：它只消费内存列表和 mapper，不持有 Spring Bean，不访问环境变量，不执行反射，不使用并行 stream，不改变原列表。`ExecutionRenderer` 与 `ArchiveRenderer` 也保持 package-private，所以下游 operator-CI 只能依赖公开 Service 与 Response，无法把展示层当作新的运行 API。两个 TestData 因根 Controller 测试需要而 public，但它们只存在于 `src/test`，不会进入生产 jar。

本版失败条件被写入技术文档和机械门：81 行中任一字符变化、分组顺序变化、旧 renderer/support 回流、目标包超过二十三个生产文件、出现第三个 renderer、长名 baseline 新增、全局 cap 放宽、下游消费失败，都会直接红灯。不能通过修改 oracle、fixture、SpotBugs 豁免或权限标志来换取绿色。这些约束让“内部重构”真正等价，而不是靠维护者主观判断“看起来差不多”。

## 测试覆盖

验证顺序刻意分层。第一层是旧实现真值：临时 probe 调用真实 Service graph，完整输出两份报告；据此建立 `ExecutionMarkdownTests`，并在八个旧 renderer 尚未删除时通过 2/2。第二层是替换后同尺验证：不改一行期望，再次通过 execution 六节四十行与 archive 六节四十一行。测试先把不同 response 的 Markdown 映射为本地 `Section(heading, lines)`，因此只比较产品输出，不依赖新旧实现类名。

第三层验证共享算法。`MarkdownSectionsTests.groupsEntriesInEncounterOrder` 输入 beta、alpha、beta 三项，要求输出严格是 `entry-count=3`、`beta: B1; B2`、`alpha: A1`；随后修改源列表并尝试修改结果列表，证明 section 是创建时快照且不可变。原有 counted 空列表、counted 快照和 mapped 快照测试继续通过。第四层运行目标包现有行为测试：source plan、read target、gate boundary、execution service、archive artifact、archive gate、scorecard、Markdown boundary 全部使用真实 Catalog 与 Service。两个根 Controller 测试又证明 Spring 入口仍调用相同公开服务。

第五层是结构和全局门。v1843 测试逐个固定二十三个生产文件、十四个测试文件、十个删除文件、两个删除 TestSupport、两个根 Controller、SpotBugs response FQN 和 operator-CI 下游依赖。Ops census、Java name census、exact baseline、change gate、Spotless 与历史 ops cap 共同防止回退。最终还要运行完整 `mvnw -B verify`，覆盖全部单元与集成测试、JaCoCo floor、SpotBugs、生产 profile smoke 所需编译面和 jar packaging；实现提交与 closeout 提交分别等待 canonical GitHub Actions 的 headless 与 Docker job，再创建 annotated tag。任一层没闭环，都不能把 v1881 写成完成。

讲解本身也在 final verify 前进入归档 manifest，并由 `CurrentWalkthroughTests` 检查中文字符数不少于三千、十个标准标题一个不少、没有额外标题。这样维护者得到的不是一张“改了哪些文件”的清单，而是一份能从 HTTP 输入一路追踪到 Catalog、Service、Response、Markdown 和远端发布边界的可复现说明。

## 实际工作量说明

本项目在 v1881 没有增加新接口，也没有把原来只读的运维能力偷偷变成执行能力。它处理的是代码组织已经明显妨碍理解的问题：`minimalreadonlygateexecution` 包共有三十一个生产 Java 文件，其中八个文件名很长的 renderer 和两个 renderer support 共同完成两份 Markdown。第一份是最小只读门执行注册表，第二份是对第一份注册表的归档验证。旧代码按章节拆类，看上去每个文件都不大，但读者要理解一份六节报告，必须从 Service 跳到聚合 renderer，再跳到三至四个章节 renderer，最后跳到 support 才能看见最终字符串。两个 Gate 章节还分别复制了 `LinkedHashMap` 分组、组内追加、总数前缀和不可变复制的控制流。文件数量增长了，真正的抽象却没有增长。

这次工作不是把十个文件简单拼接成一个大文件，也不靠重复背景或罗列类名硬凑篇幅。第一步先用真实 Service graph 运行旧实现，打印全部标题和行；第二步把结果写入与 renderer 类型无关的精确 oracle，并先在旧实现上通过。execution 输出冻结为六节四十行，archive verification 输出冻结为六节四十一行。第三步识别两份报告中真正重复的算法：普通计数章节已有 `MarkdownSections.counted`，有序分组计数章节缺少共享原语，于是新增 `groupedCounted`，并单独证明组首次出现顺序、组内输入顺序和结果不可变。第四步才以 `ExecutionRenderer` 与 `ArchiveRenderer` 替换八个旧 renderer 和两个 support。第五步删除两条重复的测试构造链，建立短名 TestData 工厂，并让 archive 工厂复用 execution service。这里的“禁止硬凑”同时约束代码和讲解：抽象必须对应真实重复，文字必须对应真实输入、输出或机械证据。

最终目标包从三十一个生产文件降为二十三个，全局 ops 从一千二百七十四降为一千二百六十六；renderer 从五十一个降为四十五，总行数从三千八百一十六降为三千六百一十六，超长 renderer 文件名从三十八降为三十。新增生产源码是两个短 renderer 和共享引擎的一个小原语，删除量大于新增量，没有形成新的巨型文件。所有变化都围绕一个明确问题：让一次产品输出由一个产品输出所有者解释，让可重复算法由一个可测试引擎解释。

## 一句话总结

v1881 把最小只读门 execution 与 archive verification 的八个章节 renderer、两个 support 收敛为两个按完整输出负责的短类，并把两份重复的有序分组算法提升为一个经过顺序与不可变性验证的共享原语；它先在旧实现上冻结六节四十行和六节四十一行，再让新实现通过同一 oracle，同时保持路由、结构化 response、Catalog、事务、Java/mini-kv 只读权限和下游 operator-CI 依赖完全不变，用 family cap、全局 census、长名 baseline、完整 Maven、双 CI 与 annotated tag 把优雅收益固定成只能继续缩小、不能悄悄反弹的工程事实。
