# v1890：归档验证注册表的单一 Catalog 与不可变证据流

## 入口路由

本项目这一版没有增加新接口，也没有改变既有接口的地址。外部调用仍然从 `OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryController` 进入，控制器接受一个普通的 GET 请求，然后把工作交给 `OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService.registry()`。完整路径仍是 `/api/v1/ops/shard-readiness/minimal-read-only-gate-execution-archive-verification-registry`。这个接口不是执行按钮，而是一张可读取的归档核验报告：它回答“上一阶段声明的只读执行证据是否完整转成了可归档、可审查、可交接的记录”。

路由字符串继续由 `OpsShardReadinessReleaseAcceptanceRoutePaths` 提供。服务中的 `ENDPOINT` 只是基础路径与既有后缀的组合，不拥有第二份字面量。这样做有两个现实意义。第一，Node 或其他只读消费者看到的地址不会因为 Java 内部重构而变化；第二，若某人误改后缀，路由契约测试会在编译或测试阶段发现，而不是等集成环境出现四零四。v1890 只调整包内数据生成方式，因此控制器、路由 owner、响应版本 `Java v1337` 和 profile 字符串都保持原样。

一次请求的最外层流程可以通俗理解为三步：先向基础执行注册表取一张“原始清单”，再把清单中的每类事实转成“归档验证项”，最后计算计数、总状态和适合人阅读的 Markdown。v1889 已经把第一步的七组静态事实收敛到 `RegistryCatalog`；v1890 处理的是第二步。这里强调入口不变，是因为重构的价值不应来自转移风险：外部仍调用同一地址、拿到同一 JSON，变化只发生在维护者如何理解和修改内部代码。

## 响应模型

公开响应仍是 `OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse`。它包含项目名、版本、端点、profile、上游计划、下一计划、基础注册表版本、只读安全标志、九类计数、八组明细、六段 Markdown、二十条 checks 和最终状态。嵌套 record 分别表达 `SourceRegistrySnapshot`、`ArtifactVerification`、`ReadTargetVerification`、`GateCheckVerification`、`BoundaryVerification`、`CiBatchVerification`、`OperatorHandoffVerification` 与 `ScorecardEntry`。这些类型没有被替换成字符串 map，因为字段名和 Java 类型本身就是编译期契约。

新增加的 `ArchiveCatalog.Evidence` 不是第二个公开响应，也不是兼容壳。它是包内中间值，只负责把八组列表作为一个完整概念传递。旧代码让 service 分别创建八个局部变量，再把同一组变量按顺序传给 renderer 和 Support。只要两个调用中的参数顺序看错，就可能把概念相近的列表接错，而且阅读者必须在长参数清单中来回核对。现在 `Evidence` 的 accessor 名称就是语义，例如 `boundaryVerifications()` 与 `operatorHandoffVerifications()`，编译器会同时检查类型和存在性。

`Evidence` 的紧凑构造器对八个输入全部执行 `List.copyOf`。这不是为了“多复制一次显得安全”，而是建立所有权：Catalog 返回后，任何调用者都不能通过原集合或返回集合改变本次证据快照。测试会故意用八个可修改的 `ArrayList` 构造 evidence，随后清空来源并尝试写入结果；若某一组漏掉复制，这个测试就会失败。因此输入是基础注册表响应，输出是有类型且不可变的八组归档证据，中间没有共享的可变容器。

## 上游证据配置

上游输入来自 `OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService.registry()`。该基础响应记录五个只读目标、二十个 gate check、十条禁止边界、四个 CI 批次、六个归档要求和五个操作交接步骤。v1890 不重新定义这些事实，只做确定性的投影。例如一个 `ArchiveRequirement` 的 `artifact`、`producer`、`evidence` 和 `required` 被投影为 `ArtifactVerification`；如果 `required` 为真，归档状态就是 passed，否则就是 blocked。read target 的 `status` 同时决定 archived 与最终状态，gate check 的 `passed` 同时决定 sourcePassed、archived 和状态。

这种投影关系解释了为什么八个旧 Catalog 不是真实的八个模块。它们都在同一包、接收同一个 source registry、只被同一个 service 调用，每个文件大多只有一条 stream 映射。文件名虽然很长，却没有独立生命周期、策略或消费者。维护者要回答“归档报告如何产生”，必须依次打开八个文件。v1890 以 `ArchiveCatalog` 作为唯一 owner，将相关转换并排放置；不同明细仍由不同私有方法和不同 record 类型隔开，所以合并文件并没有把数据揉成无类型大方法。

scorecard 也继续从同一上游事实计算。它比较 source registry 的实际通过数与 Support 中固定的预期数，生成 source-registry、archive-artifacts、read-targets、gate-checks、boundary-denials、ci-batches 和 operator-handoffs 七项分数。预期和实际相等才是 passed。这里没有读取磁盘、网络、数据库或时钟，因此 `ArchiveCatalog.evidence(sourceRegistry)` 是纯函数：给定相同输入就得到相同顺序、相同字段和相同状态，特别适合用完整响应摘要约束。

## 服务层核心流程

重构后的 service 核心只有一条清晰的数据线。第一行取得 `sourceRegistry`；第二行执行一次 `ArchiveCatalog.evidence(sourceRegistry)`；第三步让 `ArchiveRenderer.render(evidence)` 产生展示结构；最后调用 Support 组装公开响应。service 不知道八组列表怎样映射，也不计算 passed 数，更不拼 Markdown。它的职责就是协调上游、纯投影、展示与汇总四个明确角色。

旧实现有三次“展开”：八个静态 Catalog 分别调用一次，renderer 接八个列表，Support 又接八个列表。新的 evidence 聚合值消除了后两次展开。`ArchiveRenderer` 仍然独立存在，只是签名从八个列表改为一个 `ArchiveCatalog.Evidence`；内部仍调用 `MarkdownSections.counted` 和 `groupedCounted`，生成 Source Registry、Archive Artifacts、Read Target Verification、Gate Check Verification、Boundary Verification、CI Handoff Scorecard 六段。Support 同样仍负责复制 Markdown、统计 passed 和 denied、生成二十条 checks、计算最终 passed 或 blocked。

这里没有把 renderer 或 Support 合进 Catalog，因为三者的变化原因不同。上游 record 增减字段时，投影可能变化；展示格式变化时，只应碰 renderer；通过条件和 checks 变化时，只应碰 Support。所谓优雅不是文件越少越好，而是一个修改理由对应一个 owner。八个同形投影文件收成一个 owner，是去掉虚假的边界；保留 Catalog、Renderer、Support 三层，是守住真实的边界。`ExecutionExtractionTests` 还会检查 Catalog 源码不含 `ArchiveRenderer`，防止未来为了少一个文件再次混合职责。

## Java 证据检查

Java 侧最强的兼容证据是 `ArchiveResponseOracleTests`。它在删除旧实现前先运行，确认十段长度向量为 `1/6/5/20/10/4/5/7/6/20`，随后使用属性名排序、map key 排序和 UTF-8 编码把完整响应序列化，得到 SHA-256 `d5e75e352cee97a6f2c30111e0af57bb39af770b31cd420a018994b003e05859`。新实现完成后，同一个测试继续通过。摘要覆盖公开字段、列表元素、元素顺序、Markdown 行、checks 和安全标志，所以它比“状态还是 passed”这种单点断言强得多。

字段级测试仍有价值。`ArchiveCatalogTests` 明确检查六个归档 artifact 的名称和顺序、五个读目标不含原始 URL、二十个 gate check 全部保留 sourcePassed 与 archived、十条 boundary 全部 denied、四个 CI batch、五个 handoff 和七项 scorecard 全部 passed。`ArchiveRegistryServiceTests` 检查 source plan、版本、endpoint 和只读标志；`ArchiveRenderingTests` 检查六个 Markdown 标题和关键计数；`ArchiveMarkdownBoundaryTests` 检查展示文本没有泄漏禁用能力。

结构证据由 `ExecutionExtractionTests` 提供。它精确列出包内现存十个生产文件与十三个测试文件，确认八个旧 Catalog 全部缺席，限制 `ArchiveCatalog` 不超过二百行，要求源码恰有八次 `List.copyOf`，并确认 service 只调用一次 evidence。全局 `JavaEleganceGateTests` 和 `OpsEleganceCensusTests` 再把这次收益写成不可放宽的上限：生产 Java 一千三百五十二降到一千三百四十五，ops 一千二百二十降到一千二百一十三，Catalog 三百零三个降到二百九十六个，execution 包十七降到十个。

## mini-kv 证据检查

这个 Java 接口提到 mini-kv，但 v1890 不连接、启动或修改 mini-kv。基础注册表只保存三个 mini-kv 读目标：`HEALTH`、`INFOJSON` 和 `STATSJSON`。归档投影仅复制 target 名称、commandOrRoute 与上游状态，不解析 `MINIKV_HOST`、不读取端口后的真实地址，也不执行 TCP 命令。换言之，本版输入是 Java 内存中已经形成的 source registry 响应，而不是一个可以对 mini-kv 发请求的客户端。

这是跨项目边界必须透明说明的地方。归档报告中的 passed 表示“上游证据声明该只读目标已通过，并且当前归档形态完整”，不等于 v1890 在本机重新启动 mini-kv 做了联合测试。真正跨项目实时读取应由单独的 env-gated capstone 负责，不能借一个内部重构版本冒充。本版本保持 source plan `Node v367` 和 recommended next plan `Node v368`，也不改 Node 或 C++ 仓库中的 fixture、摘要或 archive 目录。

read target 测试还要求 `commandOrRoute` 不含 `://`。Java 健康与 overview 项只保存相对 GET 路径，mini-kv 项只保存命令名；这能防止原始 endpoint 被悄悄写入响应。即使未来上游地址变化，archive registry 仍只描述稳定的读取意图和结果，不暴露运行环境。这个限制与“不读取凭据值”共同保证证据可以被归档、展示和评审，而不成为秘密或基础设施信息的副本。

## 阻断与安全边界

service 上的 `@Transactional(readOnly = true)` 保持不变。响应里的 `readOnly=true`，而 `executionAllowed`、`startsJavaService`、`startsMiniKvService`、`readsCredentialValue`、`resolvesRawEndpointUrl` 和 `managedAuditHttpAllowed` 全部为 false。Catalog 是没有 Spring 注解的包内纯类，既不能注入 repository，也没有 HTTP client 或 shell；它唯一能接触的是调用者传入的 immutable-style response。这样的代码形态让安全边界不仅写在文档里，也体现在依赖能力上。

十条 boundary verification 包括禁止 write routing、active shard router、credential value、raw endpoint URL、managed audit connection、deployment/rollback、Java 自启动、mini-kv 自启动、mini-kv write/admin 命令以及 Java ledger/schema/SQL 写入。映射规则是 `allowed=false` 才得到 `denied=true` 和 passed；如果上游任何规则被改成允许，归档项会 blocked，最终响应也会 blocked。它不是把危险配置“美化”为通过，而是忠实放大上游异常。

操作层同样 fail closed。完整响应摘要若变化，oracle 失败；八个旧类若复活，结构门失败；新 owner 超过二百行，尺寸门失败；长名基线增加，优雅门失败；测试期待或 fixture 被修改来迁就迁移，评审应直接判定版本失败。禁止硬凑的含义在这里很具体：不能为了满足三千字讲解而发明能力，也不能为了得到绿色构建而改写既有事实。机械门必须对真实回归敏感，文档只能解释证据，不能代替证据。

## 测试覆盖

测试分为四层。第一层是纯 Catalog 行为：各组映射、顺序、状态与不可变所有权。第二层是完整响应 oracle：把 service、Catalog、renderer 和 Support 的组合输出锁成一个 canonical 摘要。第三层是结构与优雅：精确库存、退休文件、单次调用、行数、复制次数、全局文件数、Catalog 数、renderer 行数和长命名集合。第四层是原有 controller、Markdown、安全边界与下游消费测试，确保内部签名收敛没有破坏外部调用。

这种分层避免两个极端。只写摘要测试时，失败后不容易知道是哪一组数据变了；只写字段抽查时，又可能漏掉未抽查字段或顺序漂移。字段测试提供定位，摘要提供完整性，结构测试保护设计，集成测试保护公开边界。v1890 的目标不是让测试数字好看，而是让每类风险都有对应的失败信号。最终还必须运行 `scripts/verify-release.ps1`，覆盖所有非 Docker 测试、JaCoCo 阈值、SpotBugs、Spotless、归档策略和 jar 打包。

测试文件本身也接受童子军规则。两份按旧文件划分的长测试合成 `ArchiveCatalogTests`，因为新 owner 需要按语义而不是旧类名测试；另外三份被本次 service/renderer 改动触及的测试收短为 `ArchiveRegistryServiceTests`、`ArchiveRenderingTests` 和 `ArchiveMarkdownBoundaryTests`。新增测试名都在四十字符预算内，没有为了减少文件数把不同职责塞进一个巨大测试类。

## 实际工作量说明

生产侧删除八个 Catalog，新增一个一百八十三行 owner，并修改 service、renderer 与 Support 的参数边界。净结果是生产 Java 减七、ops Java 减七、Catalog 减七、execution 包减七。renderer 数量不变，但聚合参数使总行数从三千二百四十六减至三千二百四十一。测试 Java 总数保持九百零四：新增 Catalog 测试和完整响应 oracle，同时淘汰两份旧 catalog 测试；三个相关长名测试做等价改名。

命名 census 也得到实际收益。生产长文件 stem、长标识符使用次数、唯一长标识符从 `1119/20072/2678` 收紧为 `1111/20032/2670`；测试侧从 `721/9856/3710` 收紧为 `716/9846/3697`。精确 baseline 删除二十九项而新增零项。这里的数字不是手工估计，而是 `scripts/java-maintainability-census.ps1` 和测试使用同一套词法规则计算，并通过 Git 前一版本文件证明只减不增。

文档工作包括版本设计与证据矩阵、这一篇中文机理讲解、progress ledger、CHANGELOG、final evidence、优雅路线图和授权归档 manifest。讲解写在最终 verify 之前，避免先看到绿色结果再倒推理由。完整版本还要经过实现提交、远端两条 CI 作业、closeout 事实提交、第二轮 CI、annotated tag 与本地/远端 peel 一致性检查；任何一个阶段没有真实输出，都不能把 pending 写成 completed。

## 一句话总结

v1890 把八个只有文件名不同、输入输出同源的归档投影类收敛成一个有类型的 `ArchiveCatalog.Evidence`，让 service 只装配一次，让 renderer 与 Support 继续各守职责，并用旧实现先捕获的完整响应哈希、八组不可变性、精确文件库存和只减不增的优雅 ratchet 证明：代码更短、更容易理解，但 HTTP、JSON、顺序、状态和所有只读安全边界一个字节都没有被偷换。
