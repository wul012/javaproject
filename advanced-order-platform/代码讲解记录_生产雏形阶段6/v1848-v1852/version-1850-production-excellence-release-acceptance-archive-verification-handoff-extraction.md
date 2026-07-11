# version-1850：发布验收归档验证交接层提取讲解

## 实际工作量说明

本版继续只做本项目 Java 仓库，目标是把 ReleaseAcceptanceArchiveVerificationHandoff 从根 `ops` 包
迁成一个边界完整、可独立理解的模块。禁止硬凑字数，也不允许调整业务断言、fixture、路由字符串或
安全布尔值来让迁移通过。实际工作覆盖二十五个生产实现、六个包内测试、一个留根控制器、一个留根
控制器测试、三个已提取 RoutePathSplit 下游文件、一个下游测试工厂、四条 SpotBugs FQN、多个活
计数、endgame census、版本守卫、技术文档和长篇说明。

二十五个实现构成一个真实闭包，而不是按前缀机械抓取。九组 catalog/renderer 分别处理来源、验证
要求、制品核对、路由交接、操作员说明、CI 证明、边界守卫、保留守卫和收尾交接；ScorecardCatalog
与 ScorecardRenderer 汇总这些结果；总 Renderer 和 RendererSupport 组织 Markdown；Service 固定
调用顺序；Support 组装 Response；Response 定义唯一输出。控制器是 Web 适配器，因此留根。六个
行为测试跟随内部实现移动，控制器 Markdown 测试继续从包外证明公开边界可用。

本版没有迫于路径限制使用难懂缩写。完整包名的最长主源码和测试路径为二百二十二和二百三十四，
已经可用；最终采用稍短但仍直观的 `releasearchivehandoff`，最长降到二百和二百一十二字符。这样既
保留“发布归档交接”的语义，也给编译输出、测试报告和未来子包留下空间。类名、文件名、Bean 名称和
协议字段全部保持原样，包地址变化不会变成外部契约变化。

迁移时还需要区分三种看似相近、实际职责不同的层。v1849 Archive 回答“哪些发布验收材料已经按
归档形式整理”；本版 Handoff 回答“这份归档交给下一位消费者前，还需要验证哪些来源、制品、路由、
操作员、持续集成、边界、保留和收尾条件”；v1840 RoutePathSplit 则回答“这些交接事实如何用于路由
所有权拆分和关闭”。如果把三层放在同一包，调用者可以跳过交接验证直接读取归档内部，或者让路由
拆分反向依赖某个 catalog。分成三个包后，每层只能通过前一层 Response 获得信息，职责和时间顺序
都由 Java import 表达。

二十五个生产文件的迁移也涉及真实下游修复。根控制器需要导入新 Service/Response；RoutePathSplit
的 Service 需要导入新 Service，SourceCatalog 与 Support 需要导入新 Response；RoutePathSplit 的
测试工厂需要导入新 TestSupport。SpotBugs 中 Response 与 MarkdownSection 各有两份镜像，四处都要
同步。v1849 守卫原先从根目录读取 Handoff 文件，本版必须把读取位置移到新包，同时保留“所有读取
Archive Response 的文件必须显式导入 ciarc”这一原始要求。少改任何一处都会在编译、focused 或
全量门中暴露，而不是留给未来维护者猜测。

六个测试文件为什么不全部留根？其中五个直接验证 package-private catalog、renderer 或 support，
它们属于实现内部白盒证据，跟随包移动才能继续限制可见性；TestSupport 负责构造完整服务，也应与
实现同包。只有 ControllerMarkdownTests 是从 Web 适配层观察公开行为，留根后反而成为更强的黑盒
探针。它必须通过 public TestSupport 组装服务，若生产边界或测试边界不完整，编译器会立即拒绝。
这种“内部测试随实现、外部测试随适配器”的安排，比把所有测试机械放在一个目录更能说明所有权。

## 入口路由

入口仍是一个无请求体、无参数的只读 GET。根包 Controller 通过构造注入取得 Handoff Service，调用
`registry()` 后返回 Response。请求不能携带 credential、原始 endpoint、部署目标或执行开关，控制器
也不做数据变换。对调用者而言，版本前后的 HTTP 方法、完整路径、响应媒体类型和字段完全相同。

Service 继续使用 v1840 公共 `OpsShardReadinessReleaseAcceptanceRoutePaths`。该 owner 同时提供
BASE_PATH 和 `RELEASE_ACCEPTANCE_ARCHIVE_VERIFICATION_HANDOFF_REGISTRY` 后缀；根控制器仍通过根
聚合器读取，而根聚合器委托同一常量。迁移只把 Service 的依赖位置变清楚，没有复制 URL，也没有
修改根聚合器的 1111 行上限。历史 route test 与 v1850 source guard 会检查 owner 仍是唯一来源。

通俗理解，这个入口像归档材料送交下一位审查者的窗口。外部人员仍按原窗口编号领取交接包；内部
实现只是从综合办公区搬进“发布归档交接室”。窗口牌没有换，材料格式没有换，负责整理材料的同一组
人员被放在一个明确房间。以后审查某个路由问题时，维护者能直接定位 route catalog，而不必在五百个
根包文件中搜索相似前缀。

## 响应模型

Handoff Response 首先说明项目、版本、只读状态、执行禁用状态、endpoint 和 profile，再记录来源
Archive 的版本、端点、profile 和状态。主体包含 source archive snapshots、verification requirements、
artifact cross checks、route handoffs、operator instructions、CI proofs、boundary guards、retention
guards、closeout handoffs 和 scorecard。末尾保留 Markdown sections、checks 和总 status。

这些结构回答的是“归档是否足以交给下一层验证”，不是“是否立即执行生产操作”。Source snapshot
固定被验证的归档；Requirement 说明必须满足的条件；Artifact cross check 比对制品与证据；Route
handoff 说明接收方和路径所有者；Operator instruction 给出人工步骤；CI proof 固定自动化结果；
Boundary guard 说明秘密与执行边界；Retention guard 固定保留要求；Closeout handoff 固定收尾材料；
Scorecard 比较每组 expected 与 actual。

Response record 及嵌套 record 只移动 FQN，字段名、顺序、类型、列表顺序、构造参数和状态词全部不
变。这样既保持 JSON 序列化稳定，也保持下游 RoutePathSplit 的读取逻辑稳定。SpotBugs 中 Response
和 MarkdownSection 的两份镜像只更换完整包名，不删除规则、不扩大排除模式。若集合暴露策略未来要
改变，必须作为单独契约版本处理，不能夹带在结构迁移里。

## 上游证据配置

唯一业务上游是 v1849 `ciarc` 中的 ArchiveRegistryService。Handoff Service 调用其 `registry()`，
得到只读 Archive Response。各 catalog 只读取这个 Response 或前序产生的不可变列表，没有访问 v1848
ReleaseAcceptance 的内部类，更没有绕过 v1849 重新计算归档。这保证依赖链严格为 dossier 到
acceptance，到 archive，再到 handoff。

上游 Archive 提供来源快照、制品清单、路由包、操作员包、CI attestation、边界封条、保留窗口、
closeout ledger 和 scorecard。Handoff 将这些归档事实转换为交接视图。例如 artifact manifest 进入
artifact cross checks；route packages 转为 route handoffs；operator packs 转为 instructions；CI
attestations 转为 proofs；boundary seals 转为 guards；retention windows 与 ledger 转为保留和收尾
交接。映射只读、确定，相同输入得到相同输出。

下游是早在 v1840 已提取的 ReleaseAcceptanceRoutePathSplit。它的 Service 需要 Handoff Service，
SourceCatalog 与 Support 读取 Handoff Response，测试工厂需要 Handoff TestSupport。本版显式给这四
处加新包 import。下游没有导入 Handoff 的 catalog、renderer 或 support，证明公开 Service/Response
足以支撑现有行为。这个方向也说明 v1850 不是孤立搬家，而是完成一段真实跨包依赖闭环。

## 服务层核心流程

`registry()` 在只读事务内运行。第一步调用 v1849 Archive Service 得到 source。第二步由 SourceCatalog
生成来源归档快照。第三步 RequirementCatalog 生成验证条件。第四步 ArtifactCatalog 做制品交叉核对。
第五步 RouteCatalog 与 OperatorCatalog 生成路由交接和操作说明。第六步 CiCatalog 与 BoundaryCatalog
生成 CI 证明和安全守卫。第七步 RetentionCatalog 与 CloseoutCatalog 生成保留和收尾交接。第八步
ScorecardCatalog 对九组结果评分。最后 Renderer 生成 Markdown，Support 组装完整 Response。

Catalog 负责从结构化输入提取业务事实，Renderer 只负责人可读投影，Support 只负责数量、checks、
sections 和状态聚合。Service 不夹带具体列表构造，因而调用顺序一眼可见。迁到独立包后 package-private
进一步保护这些职责：只有同一 Handoff 内部能直接调用 catalog，外部必须通过 Service 取得一致结果。

举例说明输入输出。假设 Archive 返回 passed，含七个 artifact、四个 route package、四个 operator
pack、五个 CI attestation、八个 locked boundary seal、五个 retention window 和六个 closeout
ledger entry。Handoff 先固定来源版本，再生成对应的 requirement 与 cross check；路由包变成四条
交接；操作员包变成有序说明；五项 CI 证据与八项边界封条成为 proof/guard；保留和收尾条目保持数量。
scorecard 全部 expected 等于 actual 时，输出 passed，但 executionAllowed 与所有启动、秘密、网络
能力仍为 false。passed 只表示交接材料齐全。

## Java 证据检查

v1850 守卫用精确清单验证二十五个生产文件只存在于 `maintenance/releasearchivehandoff`，目标包不能
混入无关文件；六个测试必须同包；控制器和 controller Markdown test 必须留根。Service 源码必须导入
`ops.maintenance.ciarc` 和公共 route owner，禁止退回根聚合器。RoutePathSplit 的三个生产文件必须
分别导入 Handoff Service/Response，形成可复核下游边界。

行为测试覆盖 source/requirement、artifact/route/operator、CI/boundary、retention/closeout/scorecard、
immutability 和控制器 Markdown。RoutePathSplit 测试也加入 focused 集，验证 Spring 构造与响应读取
不受 FQN 迁移影响。v1849 历史守卫改从新包读取 Handoff 文件，但仍保留“必须消费 ciarc”的原断言；
它的当前根数继续收紧，而 v1849 文档中的历史 548 到 525 不改。

治理计数从五百二十五降至五百，可迁 backlog 从四百二十降至三百九十五，Handoff 独立 bucket 从
二十五清零。保留根文件仍是一百零五，未分类仍为零，总 ops 主源码仍不超过一千三百五十二。三处
live ratchet、v1847-v1849 活守卫和 v1828 census 一起更新，任何漏项都会由 focused 或全量门指出。

## mini-kv 证据检查

本版没有编辑 mini-kv，也没有启动其服务。Handoff 中出现的跨项目版本、路由或归档说明来自上游
Archive 已固定的证据，不是实时调用 C++ 二进制的结果。因此本版只证明 Java 内部交接投影和负面
边界，不声称系统级联合运行。Node 窗口负责最终 C1-C4 capstone，若它明确要求 Java 提供启动、端点
或报告配合，Java 任务将优先响应；当前结构迁移不抢跑该契约工作。

Response 继续显式声明 startsMiniKvService=false、readsCredentialValue=false、resolvesRawEndpointUrl=false、
managedAuditHttpAllowed=false。它不会为了验证归档而启动 mini-kv，不会读取凭据，不会解析用户提供的
原始地址，也不会发送托管审计请求。这些否定字段不是装饰，CI/boundary 测试会逐一检查；任一意外
变为 true，版本就不能完成。

## 阻断与安全边界

第一，禁止写入。服务只有只读事务和纯映射，没有 repository save、文件写入、消息发布或外部上传。
第二，禁止执行。没有 Java/mini-kv 进程启动、部署、回滚或 shell。第三，禁止秘密与任意网络。入口
无参数，服务不读 credential，不解析 raw URL，不创建 managed audit connection。第四，禁止契约
漂移。路由 owner、Response、历史 fixture、归档目录与证据摘要均不改。

第五，可见性最小化。生产包只公开既有 Service 与 Response；catalog、renderer、support 保持包私有。
TestSupport 虽公开，但只存在测试源集，用于留根控制器和已提取下游测试。不能因为编译器报错就批量
把所有类改 public；若下游需要内部类型，应首先检查它是否绕过了 Service/Response。机械守卫把这一
原则变成持续失败条件。

明确失败条件包括：目标文件数不是二十五、控制器离根、下游导入内部类、路由或字段变化、根包高于
五百、Handoff bucket 非零、unassigned 非零、总文件数超过上限、讲解不足三千汉字、focused、
Spotless、JaCoCo、SpotBugs 或完整 verify 任一失败。只有全部机械证据一致才能提交和打 tag。

## 测试覆盖

Focused 集覆盖 Handoff 全家族、RoutePathSplit 下游、v1849/v1850 守卫、v1828 census、三处活棘轮
和讲解合规。它先发现包地址、import、路径、计数和文档问题。Spotless 随后统一新 import 与旧文件的
格式。完整 `mvnw verify` 再运行约一千七百六十项测试，并执行 JaCoCo、SpotBugs、Spotless、Spring
集成和全部历史架构门，focused 不能替代它。

输入证据包括开始提交、tag、远端同步、上一版 CI、文件精确清单、依赖扫描和路径预算；输出证据包括
focused 数量、完整测试数量、覆盖率、静态扫描、最终 census、实现提交、关账提交、annotated tag
和 GitHub Actions run。中间 CI 在下一版开头读取，不原地空等；五版检查点再统一等待并把所有 pending
行改为 passed。这个节奏既完整又可持续。

审查者复核本版时，不必相信执行者写下的结论。先运行根包普查，确认保留文件、可迁文件和未分类
文件三者能闭合；再查看新包精确清单，确认控制器之外的交接实现没有残留；随后检查上游归档类型与
下游路由拆分类型的导入方向，确认没有反向依赖；最后比较迁移前后的接口常量、响应字段、检查项和
安全布尔值。任何一步不一致，都应把版本退回实现阶段，而不是在说明里增加豁免。这样的复核顺序把
目录整洁、依赖边界、协议稳定和运行安全串成一条可重复证据链，也使后来维护者能在不了解全部历史
版本的情况下判断当前改动是否诚实。

## 一句话总结

v1850 把发布验收归档验证交接从根包隐式共享区迁成独立只读模块：它只消费 v1849 Archive，只向
既有 RoutePathSplit 暴露 Service/Response，路由和响应不变，根包降到五百并清空 Handoff bucket，
整个 dossier、acceptance、archive、handoff 依赖链终于由编译器和机械守卫清楚表达。
