# v1896 Sandbox blocked-execution dossier Catalog 收敛讲解

## 入口路由

本版处理的入口仍是一条纯读运维证据路由：
`/api/v1/ops/shard-readiness/sandbox-connection-blocked-execution-context-normalization-dossier`。
请求到达后，根包中的 Controller 只负责把 GET 请求交给 sandboxconnection 窄包中的 Service，然后原样返回响应对象。
Controller、`OpsShardReadinessSandboxConnectionRoutePaths`、`BASE_PATH` 和子路由常量都没有改动，因此调用方不需要替换 URL，Node 也不需要调整消费位置。
路由本身不接收凭据值、原始端点、SQL、部署命令或回滚指令，也不会启动外部系统。它只把 Java 内存证据图中已经存在的演练结果，组织成一份可审查、可归档、不授予执行权的 dossier。
本版保留 Service 上的 `@Transactional(readOnly = true)`，也保留公开方法名 `dossier()`，所以从 HTTP 边界到事务边界的外部契约完全不变。

## 响应模型

返回类仍是原有的 `OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse`，它没有被改名、拆字段或重排顺序。
响应先给出项目名、版本、只读与执行权标志，再给出 source plan、Node owner plan、Java context 证据版本、上游 receipt 版本和 schema 版本，然后是 endpoint、profile、每组数量、十组证据列表、九组 Markdown、checks 和最终 status。
十组基础列表依次为 source receipt、context field、normalization rule、precondition evidence、boundary snapshot、execution guard、warning echo、downstream intake gate、verification gate 和 handoff note。

重构后新增的 `DossierCatalog.Evidence` 只是包内的组装边界，不是新的 HTTP 模型，也没有暴露给 Controller。
它在 compact constructor 中对十个列表各执行一次 `List.copyOf`，一方面让 Service、Renderer 和 Support 共享同一个快照，另一方面保证后续代码不能在不知情的情况下改写证据。
这个内部记录解决的是 Java 方法参数爆炸，而不是发明新 schema。字节级保真由完整响应 oracle 负责：数量向量严格为 `1/3/5/6/5/12/4/5/10/4/9/21`，sorted-property UTF-8 JSON SHA-256 严格为 `f4ff835d241fd99fd1113f926f542c6954ab22f409ff43ef78b6e34f4413fad2`。
以前十组列表之间的属于关系只存在于开发者的记忆里；方法签名看到的是一排相互独立的 `List`。现在 `Evidence` 把它们定义为同一次 rehearsal 的不可分割快照，编译器可以保证 Renderer 和 Support 获得的是同一种类型。每个形状常量也从旧 Support 移到 Catalog，因为“应该生成几条证据”属于证据目录的定义，而“这些证据是否足以得出 passed”才属于 Support 的判定。这个所有权调整消除了旧 VerificationCatalog 反向引用 Support 常量的循环概念。

## 上游证据配置

本功能的唯一运行时输入是 `OpsEvidenceService.releaseApprovalRehearsal()` 返回的 `ReleaseApprovalRehearsalResponse`。
这个输入已经是一份只读演练结果，其中包含 request context 和 `managedAuditSandboxConnectionPreconditionReceipt`。request context 提供 requestId、operatorIdentity、auditCorrelationId，以及它们来自哪个 header 或 placeholder 的 source label；receipt 提供已经冻结的 Node v234 消费信息、Node v235 下一步 intake 前置条件、owner approval、credential handle、schema rehearsal、rollback path 和 Java execution boundary。

`DossierCatalog` 只从这两个来源投影证据。它不读凭据真值，只读 credential handle 是否需要人工审查、Java 是否保持“未读取”与“未存储”。
它不执行 schema migration，只把 rehearsal id、evidence required 与 `schemaMigrationSqlExecutedByJava=false` 这类已有事实变成 boundary/guard。它不连接 managed audit，只读取 `externalManagedAuditConnectionOpenedByJava=false` 和 `actualConnectionAttemptedByJava=false`。
因此输入是“已经发生的演练证据”，输出是“对这份证据的有序快照”，中间没有隐藏的执行器。`SOURCE_PLAN=Node v1982`、`NODE_OWNER_PLAN=Node v1968-v1982`、`JAVA_CONTEXT_VERSION=Java v90` 和 profile 都集中在 Catalog，让证据生成与响应判定不再循环依赖。
十组证据的推导顺序也是有因果的。source receipt 先锁定上游版本与下游许可位；context field 和 normalization rule 解释请求上下文如何被保存；precondition evidence 给出进入人工沙箱步骤前必须存在的条目；boundary 和 guard 证明这些条目没有打开执行权；warning 保留不完整上下文；downstream intake 只说明下一个手工环节可以读取；verification 对前面各组进行二次校验；handoff note 最后向 Node、Java、mini-kv 和操作员分别说明允许做什么。这个顺序同时决定响应字段、Markdown 章节和 checks 的阅读路径，不能随意调换。

## 服务层核心流程

旧 Service 有 88 行：它先取 rehearsal 和 receipt，然后逐一调用九个长名 Catalog，把十组列表保存为局部变量，再把其中九组展开给 Renderer，最后把十组列表加 Markdown 再展开给 328 行 Support。
问题不是某个 Catalog 本身不正确，而是“一个 dossier 快照”没有在代码中成为显式概念。每新增一组证据，Service、Renderer、Support 和 status 的参数列表都要一起变长，顺序一旦错位也很难从编译错误中看出。

新流程只有四步。第一，Service 调用一次 `releaseApprovalRehearsal()`。第二，`DossierCatalog.evidence(rehearsal)` 按固定顺序生成十组列表，并在 `Evidence` 边界一次性冻结。
第三，`DossierRenderer.render(evidence)` 只读这份快照，生成九个 Markdown section。第四，`DossierSupport.response(version, endpoint, rehearsal, evidence, markdown)` 把元数据、同一快照、checks 和 status 放入公开 Response。
Service 因此从 88 行降到 30 行，它所展示的正是业务管线，而不再是十一个容器的搬运清单。这是本项目本版最关键的优雅度改善。

## Java 证据检查

Catalog 对 Java 证据的检查不是一个模糊的 `ready=true`，而是可定位到具体输入的多层投影。三个 context field 各自保留 name、value、source、supplied 和 normalized；五条 normalization rule 说明空白如何变成 placeholder、source label 如何保留、warning 为何不能隐藏。
六条 precondition evidence 从 receipt 的 `requiredPreconditionEvidence` 生成，id 由冒号前的稳定文本规范化而来，required 和 present 都必须为真。

五个 boundary snapshot 分别检查 owner approval、credential handle、schema rehearsal、rollback path 和 Java execution；十二个 execution guard 把“不写 approval ledger”、“不写 managed audit store”、“不打开外部连接”、“不执行 SQL”、“不触发部署”、“不触发回滚”、“不启动服务”和“不尝试真实连接”逐一显式化。
四条 warning echo 保留缺少 requestId、operator identity、audit correlation id 和 Node v235 marker 未就绪的事实。十条 verification gate 再从前述列表校验版本、数量、边界关闭、warning 归档和 downstream readiness。
Support 最后生成 21 条可读 checks，并在所有形状与语义都通过时才返回 `passed`。
这里需要区分 verification gate 和 checks。verification gate 是带 name、evidence 和 passed 的结构化判定，供程序和人同时消费；checks 是把 profile、plan、版本、每组数量、安全布尔值和归档状态编码成稳定文本标记，便于历史 archive 和跨项目 fixture 做对比。status 不盲信任一层：它既检查十一个数量上限，也检查 source 中的 Node v234/v235 与两个禁止位，还要求上下文已规范化、前置证据真实存在、边界关闭、警告归档以及两类 gate 全部通过。所以一个数量恰好、但内容错误的列表不可能仅靠凑数量获得 `passed`。

## mini-kv 证据检查

mini-kv 在这条 Java 路由中不是运行时依赖，而是一份被标明来源的 sibling-only 冻结证据。downstream intake 中的 `mini-kv-v99-wal-regression-evidence` 明确写着 `frozen sibling evidence only; Java does not start mini-kv`。
它的 `ready=true` 只表示这份既有 WAL 回归证据可以被 dossier 引用，不表示 Java 已经调用 `minikv_cli`、打开 RESP 端口、读写 WAL、加载 snapshot 或启动 mini-kv 进程。

这个边界之所以要在代码和讲解中说清，是因为“消费证据”很容易被误解为“拥有执行权”。本版只收敛 Java 内部的证据生成结构，没有修改 mini-kv 仓库，没有移动其 archive，没有重写 fixture，也没有把任何终端命令嵌入 Java。
整个 `DossierCatalog` 只生成一条文本证据，并且该条目会进入完整 JSON oracle。如果以后有人把这条 evidence 改成调用命令的结果、增加进程启动或更改文本内容，SHA、安全测试与跨项目边界都会立即报错，不会悄悄升级为执行能力。

## 阻断与安全边界

响应顶层仍然是 `readOnly=true` 与 `executionAllowed=false`。source receipt 仍然要求 `readyForManagedAuditSandboxAdapterConnection=false` 和 `nodeMayTreatAsProductionAuditRecord=false`；这表示下游可以消费前置证据，但不能把它当成生产 audit record，更不能因此连接 managed audit adapter。
所有 boundary 都要同时满足 `required && closed`，所有 guard 都要 passed，所有 warning 都要 archived，所有 verification gate 和 handoff note 也都要通过。

本版没有通过“减少检查条数”换取短代码。相反，21 条 checks、十条 verification gate、十二条 execution guard、五个 boundary 和四条 warning 都原样保留，只是把它们从多个长名容器里收回到一个有明确边界的 Catalog。
`DossierSupport.status` 仍然逐项判定列表形状、Node v234/v235 版本、context normalization、precondition present、boundary closed、guard passed、warning archived 和 downstream ready。如果任一项不满足，返回状态就是 `blocked`。所以这次重构改变的是代码组织方式，不是安全门的通过条件。

## 测试覆盖

测试分为行为、字节、结构和全局 ratchet 四层。在任何生产代码修改之前，新增的 `DossierResponseOracleTests` 先对 released v1895 旧实现运行，数量向量一次命中，并取得完整 response SHA。
固定摘要后，旧实现先跑绿；新 service/renderer/support/catalog 路径接通后，oracle 在旧类尚未删除时再跑绿；九个 Catalog 和旧 Support 物理删除后，oracle 与行为组合继续通过。这个时间顺序证明 SHA 是迁移前的真实基线，不是为新实现量身修改的期望。

原有 source、boundary、immutability 测试的断言全部保留，但 owner 改为短语义名 `DossierCatalogTests`、`DossierSafetyTests` 和 `DossierServiceTests`；`DossierMarkdownTests` 仍然检查九个 section 的顺序与行内容，Controller 测试仍然走公开 HTTP 边界。
`SandboxExtractionTests` 则精确要求 family 只剩五个生产 owner、Catalog 低于 400 行、`List.copyOf` 恰好十次、Service 恰好一次 assembly、Renderer/Support 只接收 typed Evidence，且所有退休名不得复活。
首轮结构门曾真实捕捉到 warning 在局部和 Evidence 边界重复执行 `List.copyOf`；修复是删除重复复制，而不是把期望从 10 改成 11。扩展后的行为、oracle、历史结构与中央 census 联合门通过 52/52。
三阶段 oracle 特别重要。第一阶段在旧生产代码上取得摘要并跑绿，证明期望来自发布基线；第二阶段让新 Service 使用新 Catalog/Support/Renderer，但暂时保留旧类，此时同一摘要通过，证明新管线等价；第三阶段物理删除十个旧 owner 后再跑绿，证明新路径没有偷偷回退到旧实现。结构门还精确统计 service assembly 次数、`List.copyOf` 次数、包内文件集和退休名，因此日后不能在保持行为测试通过的同时，把重复结构悄悄加回来。
完整发布门随后发现旧 `PreconditionEvidenceCatalog` 的 SpotBugs waiver 已失去 class owner。删除 waiver 后，真实 SpotBugs 扫描进一步暴露 `evidenceId` 依赖默认 locale；实现改用 `Locale.ROOT`，并在土耳其 locale 下以 `INPUT ID` 必须稳定生成 `input-id` 的回归测试锁住根因。修复没有重新添加 waiver，精确 waiver 上限从 675 收紧到 674。

## 实际工作量说明

本版不是把一个类换个名字。旧 family 有 13 个生产文件，其中九个 Catalog 合计 454 行、Support 328 行、Service 88 行、Renderer 92 行、Response 77 行，总计 1,039 行。
新 family 只有五个文件：`DossierCatalog` 374 行、`DossierSupport` 173 行、Service 30 行、Renderer 86 行、Response 仍为 77 行，总计 740 行，在增加 typed boundary 的同时净减 299 行。
生产 Java 文件从 1,316 降到 1,308，ops 从 1,184 降到 1,176，Catalog 从 266 降到 258，Readiness 文件从 985 降到 975，renderer 总行数从 3,209 降到 3,203。

命名债务也是真实净减：生产的超长文件名/标识符出现次数/唯一标识符从 `1073/19646/2632` 收紧到 `1063/19545/2622`，测试从 `694/9780/3655` 收紧到 `690/9773/3651`，exact name baseline 删除 28 项、新增 0 项。
测试文件数从 906 变为 907，新增的唯一文件是完整 response oracle，而三个旧长名测试和 v1803 长名结构 owner 都被短名责任主体替代。当前最大生产文件仍为 738 行，500 行以上文件仍为 32，没有用新巨型文件换取表面文件数下降。
所有这些数字都来自脚本和会失败的 ratchet，不是讲解中的主观评价。禁止硬凑的要求在本版是通过增加完整 oracle、收紧结构门和物理删除重复 owner 来满足的。
从维护者的视角看，以后新增一类 dossier 证据时，需要思考的是“它属于哪个证据概念、如何进入 Evidence、如何被 Renderer 呈现、如何被 Support 判定”，而不是先复制一个又长又孤立的 Catalog 文件。从审查者视角看，输入生成都在 Catalog，文本呈现都在 Renderer，决策和 Response 装配都在 Support，Service 只表达调用流程；四个责任不再穿插在十三个文件中。这种改善不会在某一次请求中让响应更快，但会显著降低下一次修改的认知范围、顺序错误概率和代码审查成本，这正是工程后期优雅度的实际价值。
方案初稿曾考虑把 boundary、guard、intake 和 verification 再拆成 `SafetyCatalog`，但精确排版后，所有证据生成方法放在一个 Catalog 中仍只有 374 行，低于事先写入结构门的 400 行上限。此时强行拆出第二个类，会让两个 Catalog 共享形状常量、互相传递中间列表，甚至重新产生双向依赖。因此最终选择的不是“文件越小越好”，而是“一个概念有一个责任完整、且有机械上限的 owner”。这既避免了巨型文件，也避免了只为看起来拆分而增加的伪抽象。
这一取舍也让后续审查者能在一个连续语义范围内追踪全部证据生成规则。

## 一句话总结

v1896 把一份原本需要九个长名 Catalog、多次列表展开和 328 行长参数 Support 才能组装的 sandbox blocked-execution dossier，收敛成“一次取源、一次生成 typed Evidence、一次渲染、一次判定”的透明管线。
同时，旧实现先通过的完整 JSON SHA、21 条 checks、十二条执行禁止、五个关闭边界与只减不增的全局 ratchet 一起证明：代码更短、概念更清晰，但对 Java、Node 和 mini-kv 的只读安全承诺一个字节都没有放松。
