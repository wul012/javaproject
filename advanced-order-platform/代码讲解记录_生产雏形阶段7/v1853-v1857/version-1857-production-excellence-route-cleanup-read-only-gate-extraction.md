# version-1857：路由清理只读门依赖闭包、证据事实边界与路由所有权收敛

本文在任何 v1857 Java 实现改动之前写成，用来固定本项目这一刀的真实输入、转换、输出和
失败条件。RouteCleanup 不是删除 HTTP 路由，更不是执行路由清理；它是一组把历史证据整理成
只读审阅材料的服务。本文只说明 Java 仓库拥有的静态目录、分析器、服务和控制器，不把 Node
计划文本解释成联调结果，也不把 mini-kv 的名字解释成实时进程。后续实现若与调用闭包不符，
必须先修正文档和边界再验证，不能用模糊总结覆盖偏差。

## 实际工作量说明

本项目禁止硬凑。v1857 的范围来自实际依赖图，而不是从一百七十个同前缀文件中随意取数。
以 `OpsShardReadinessRouteCleanupReadOnlyGateService` 为上界递归读取 direct-root RouteCleanup
类型依赖，得到二十九个生产文件。它包含十组服务和响应：Evidence、PhaseSummary、
BoundaryMatrix、HandoffChecklist、ArchivePlan、Digest、SourcePlanAlignment、ReleaseHandoff、
OperatorRunbook 与 ReadOnlyGate；还包含 EvidenceAnalyzer、聚合 EvidenceCatalog、一个
EntryFactory，以及六个分段 catalog。闭包内部有完整的证据来源、分析、汇总和最终门判断，
对其余 direct-root RouteCleanup 实现没有出边。

二十九个旧文件并非简单原样迁包。`EvidenceEntryFactory` 只有一个创建不可变 Entry 的静态
方法，六个 catalog 都通过它填入相同的只读与禁止执行标志；保留独立文件只会为了文件名而
延续间接层。本版把该方法折入 EvidenceCatalog，同时新增短名 `RouteCleanupRoutes` 保存十条
路由后缀。新包仍是二十九个生产文件，所以 direct root 从二百七十八降到二百四十九、可迁移
backlog 从一百七十四降到一百四十五、RouteCleanup bucket 从一百七十降到一百四十一，而
`ops` main 总数仍是一千三百五十二。四个控制器留根，十一组服务或分析器测试随实现移动。

工作量的另一半是边界修复。闭包外有四十七个生产源文件通过九十二条类型引用读取二十二个
闭包类型，其中四十二个源文件读取 EvidenceAnalyzer。这说明分析器不是可以藏起来的局部
细节，而是后续 RouteCleanup 层共同使用的只读事实边界；但也不意味着整个新包都应公开。
实现必须由编译器逐项指出合法读者，只提升确实跨包使用的类、静态方法、响应和不可变记录，
catalog 内部拼装与摘要辅助仍保持最小可见性。

## 入口路由

本闭包对应十条 GET 后缀，全部挂在同一个 `/api/v1/ops/shard-readiness` 基础地址下。十条
后缀依次是 `/route-cleanup-evidence-catalog`、`/route-cleanup-phase-summary`、
`/route-cleanup-boundary-matrix`、`/route-cleanup-handoff-checklist`、
`/route-cleanup-archive-plan`、`/route-cleanup-digest`、
`/route-cleanup-source-plan-alignment`、`/route-cleanup-release-handoff`、
`/route-cleanup-operator-runbook` 与 `/route-cleanup-read-only-gate`。本版不增加 method，
不改连字符，不改大小写，也不改变这些路由在控制器中的返回类型。

旧结构把这些后缀与其余上百条 RouteCleanup 常量都放在全局 `OpsShardReadinessRoutePaths`。
这会让一个局部证据家族的修改触碰全局聚合器，也让服务依赖根包才能组合自己的 endpoint。
新 `RouteCleanupRoutes` 是纯数据 owner：保存基础地址和十个 suffix，不包含 Spring 注解、
状态判断、目录查找或摘要逻辑。迁出的十个 service 从它组合完整 endpoint，四个保留根控制器
也显式导入它作为 `GetMapping` 数据源；全局 RoutePaths 删除原十个字段，不留转发别名。

路由正确不能只靠“应用能启动”证明。专用测试要逐条比较十个 suffix 的原始字符串、十个服务
endpoint 的完整字符串和四个控制器方法的映射；历史 endpoint snapshot 继续比较冻结输出。
如果某个旧字段仍留在全局表，新旧 owner 就会形成双重事实来源；如果只删旧字段却漏改一个
控制器，编译会失败；如果字符串发生一字节变化，路由与集成测试会失败。三类证据合起来才能
证明所有权移动而兼容契约不动。

## 响应模型

最底层 `EvidenceResponse` 同时承载目录元数据与嵌套 Entry。每个 Entry 记录 Java 版本、来源
Node 计划、阶段、证据类型、版本化路径，以及一组始终关闭的执行标志和状态。折叠工厂只改变
创建代码所在位置，不改变 record component 顺序、布尔值排列、路径拼接或 `passed` 字节。
六个分段 catalog 仍按 LatestSibling、ReadinessSeed、HandoffCore、HandoffAssurance、
HandoffGovernance、PostCompletion 的既有条目生成列表，聚合 catalog 仍按原顺序连接并
`List.copyOf`，所以调用方看到的条目顺序与不可变性保持不变。

其余九个 response 各自表达一个阶段的投影，而不是重复同一大对象。PhaseSummary 按阶段汇总
数量，BoundaryMatrix 解释只读与执行禁止，HandoffChecklist 列出交接核对项，ArchivePlan
描述版本化证据路径，Digest 给出确定性摘要，SourcePlanAlignment 比较来源计划，
ReleaseHandoff 组织发布交接，OperatorRunbook 给出人工只读步骤，ReadOnlyGate 汇总最终门。
这些响应的 project、version、readOnly、executionAllowed、endpoint、profile、evidence、
checks、forbiddenOperations、decision 或 status 等既有 component 全部原样保留。

静态分析配置中十个 response 共有二十二处既存 FQN 镜像，其中 PhaseSummary 有四处，其余
各两处。本版只把 class name 的 package 前缀改到 `ops.maintenance.routecleanup`，不得新增
exclude，也不得扩大 bug pattern。专用 guard 逐类型核对新 FQN 数量和旧 FQN 为零，最终
SpotBugs 读取真实字节码再次验证。这样可以区分“已接受的不可变集合返回模型随包移动”与
“新增可变对象泄漏”两件事。

## 上游证据配置

这个闭包的直接输入不是数据库、消息队列或远端请求，而是仓库内冻结的六段历史证据目录。
LatestSibling 段描述最近的相邻版本，ReadinessSeed 段描述早期准备度种子，三个 Handoff 段
分别覆盖核心、保证和治理交接，PostCompletion 段覆盖完成后的收口事实。Entry 的证据路径
继续指向 `e/<version>/evidence/*.json`；v1857 不移动、重写或重新生成任何历史归档，因而
Node 仓库对这些路径和摘要的固定引用不会被破坏。

EvidenceCatalog 是六段数据的聚合点，EvidenceAnalyzer 是事实解释点。前者回答“有哪些条目，
顺序是什么”，后者回答“最新版本是什么、版本是否连续、所有条目是否保持只读边界、某条
Entry 属于哪个 segment、禁止操作有哪些、总体边界状态是什么”。后续四十二个生产消费者
读取的是这些计算后的只读事实，不应看到 catalog 如何创建 Entry 的细节。迁包后分析器类、
确有跨包读者的方法和 Segment 不可变记录成为公开边界，工厂方法和聚合过程仍留在包内。

实现前的图分析确认闭包唯一的非 RouteCleanup 类型依赖是全局 RoutePaths。把十条路由迁到
家族 owner 后，新包对 direct root 的实现依赖应归零。它不需要 readinesscore、Prototype、
V1Contract、订单、库存、支付或 Outbox 服务；若编译时出现这些新依赖，说明迁移范围或导入
发生偏差，必须停下调查，而不能把额外类型顺手公开。

## 服务层核心流程

一次 evidence catalog 请求的输入为空请求体。控制器调用 EvidenceService，service 读取六段
catalog 的聚合列表，Analyzer 提供最新版本与边界结论，随后构造只读响应。输入是确定的历史
Entry 列表，转换是排序既定的聚合和事实判断，输出是 EvidenceResponse，副作用为零。没有
Repository、事务写入、消息发布、文件写入或网络连接。EntryFactory 折叠后，Entry 的每个布尔
参数仍按旧位置传入，不能改成含义不透明的布尔数组，也不能借机改状态算法。

PhaseSummary、BoundaryMatrix、HandoffChecklist 和 ArchivePlan 分别从同一 EvidenceService 或
Analyzer 取事实并形成面向不同审阅者的视图。Digest 对既定字段按既定顺序求 SHA-256；
SourcePlanAlignment 使用目录里的来源计划标签；ReleaseHandoff 同时引用摘要、归档和核对结果；
OperatorRunbook 把这些只读结果组织成人工步骤；ReadOnlyGate 最后检查上游状态、readOnly 为真、
executionAllowed 为假以及禁止项齐全，再返回 passed 或 blocked。门通过只表示材料一致，绝不
表示允许路由写入。

服务图是单向的：catalog 和 analyzer 在底部，Evidence 在其上，阶段视图继续向上，最终
ReadOnlyGate 位于这批闭包上界。剩余 RouteCleanup 服务可以读取这些公开边界，但新包不能
反向 import 剩余根实现。专用 guard 会扫描新包中的 root RouteCleanup FQN；编译器会迫使
四十七个外部消费者增加显式 package import。这样未来继续迁移上层时，import 会自然消失，
而不是留下难以追踪的双向包依赖。

## Java 证据检查

第一项证据是可重现的闭包。脚本从一百七十个 direct-root RouteCleanup 文件提取类型引用，
从 ReadOnlyGateService 深度遍历，结果必须恰好是二十九个旧文件；新包精确清单则是折叠工厂
后的二十八个原类型加一个 RouteCleanupRoutes。根层只保留相关控制器，不应残留任何候选实现。
十一组 service/analyzer test 迁入同包，控制器结构测试、全局 RoutePaths 测试和历史 snapshot
测试留根。

第二项证据是编译器驱动的 API 最小化。闭包外有四十七个生产 source、九十二条类型边、
二十二个目标类型；实现不会先把所有类和字段设成 public，而是迁包后运行 test-compile，按
错误位置逐项开放既有只读服务、响应、endpoint、Analyzer 方法或 Segment。最终 guard 从源码
重算消费者集合，要求跨包目标恰好来自允许清单，并确认新包没有 import 根 RouteCleanup 类型。

第三项证据是 ratchet 联动。`ops-root-census.ps1 -Json` 必须给出 direct root 二百四十九、
retained 一百零四、remaining 一百四十五、unassigned 零；RouteCleanup bucket 必须是一百
四十一，总 main 文件不超过一千三百五十二。全局 RoutePaths 行数只能从当前一千零五十八继续
下降。历史文档中的二百七十八和一百七十四保留为 v1856 快照，只有 live pin 更新，不能全局
替换历史数字。

## mini-kv 证据检查

v1857 不运行、不修改也不模拟 mini-kv。RouteCleanup 条目里若出现 mini-kv、slot、shard 或
routing 等文字，它们只是冻结计划和禁止项的一部分，不是实时探测结果。本版没有 TCP client、
RESP parser、进程控制、凭据读取或端点发现代码，测试也不启动外部服务。任何 `passed` 都只
能解释为 Java 历史证据内部一致且执行边界关闭。

禁止操作继续覆盖 write-routing、active-shard-router、credential-value-read、raw endpoint
parse、managed audit connection、deployment/rollback，以及 Node 自动启动或停止 Java 和
mini-kv。路由 owner 只保存字符串，Analyzer 只读不可变 Entry，service 只构造 response；
三者都不携带进程句柄、连接信息或写命令。若迁包为了方便而新增 mini-kv 访问层，就超出闭包
范围并使版本失败。

真正的跨项目实时检查属于得到授权后的 capstone：由 Node 启动真实 Java jar、调用真实 Java
端点，并执行真实 `minikv_cli`。那份报告可以证明同一时刻的联合状态；本版只能证明 Java
自身的静态历史材料、HTTP 只读输出和禁止执行边界没有回归。把两种证据分开写，才能让审阅者
知道每个结论由谁产生、何时产生、失败时应查哪个项目。

## 阻断与安全边界

第一层阻断是行为字节：十条 route、十种 response component、六段 catalog 顺序、Entry 字段
与布尔排列、digest 输入和状态规则任一变化都阻断提交。第二层是所有权：旧 RoutePaths 常量
必须删除，EvidenceEntryFactory 文件必须消失，RouteCleanupRoutes 必须是唯一新 owner；同时
总文件数不得增长。第三层是依赖方向：新包不得引用未迁根实现，剩余消费者必须显式导入新包。

第四层是可见性：只有四十七个实际消费者所需的二十二个类型边界可以跨包，内部 catalog 拼装、
entry 创建和私有摘要辅助不能为了少写 import 全部公开。第五层是执行安全：controller 仍是
GET，service 保持只读行为，所有 executionAllowed 仍为 false，不新增写事务、消息发送、外部
连接、部署或回滚。第六层是维护预算：新标识符和 Java 文件名不超过四十字符，不新增巨型类，
root、总文件、SpotBugs 和 source-size ratchet 只收紧。

如果编译失败，先修 package、import 与必要可见性；如果 route 测试失败，对照原常量而不是改
期望；如果 digest 失败，检查字段顺序而不是更新冻结值；如果 SpotBugs 失败，修对象所有权而
不是新增豁免；如果 census 失败，核对精确闭包而不是提高上限。任何为了通过测试而改 fixture、
历史 endpoint、response 字段或证据字节的做法都使本版失败。

## 测试覆盖

十一组随包测试分别覆盖 EvidenceService、EvidenceAnalyzer、PhaseSummary、BoundaryMatrix、
HandoffChecklist、ArchivePlan、Digest、SourcePlanAlignment、ReleaseHandoff、OperatorRunbook 和
ReadOnlyGate。它们验证 catalog 条目、segment、连续版本、边界状态、摘要格式、来源计划、
归档路径、交接项目、人工步骤、禁止操作和最终门状态。工厂折叠要由 Entry 完整字段断言证明，
不能只验证条目数量。

根层测试覆盖四个 controller 的 Spring 注解与映射、十个 RouteCleanupRoutes suffix 的原字节、
十个 service endpoint 的完整地址以及历史 endpoint snapshot。v1857 guard 覆盖二十九文件精确
清单、十一测试位置、四十七消费者与二十二边界目标、Analyzer 公开面、二十二 SpotBugs FQN、
249/145/141/0 census、总文件不增长、RoutePaths 收缩、中文讲解字数和新名称长度。

验证顺序固定为 test-compile、聚焦服务/控制器/路由/历史/预算测试、Spotless、full verify、
census、实现提交与 CI、closeout 提交与 tag、closeout CI。长验证期间只允许准备下一批的只读
调用图，不改文件。v1857 是 v1853 至 v1857 五批检查点的最后一批；收口 CI 通过后必须停下，
由 Claude 重新计算根目录、消费者边界和远端证据，再决定下一批 RouteCleanup 上界。

## 一句话总结

v1857 的价值是把从六段历史证据到 ReadOnlyGate 的完整二十九文件闭包迁入明确包，把十条路由
从全局表交还家族 owner，把一次性 EntryFactory 折入真正的数据所有者，并用编译器、十一组
行为测试、四个根控制器、二十二个跨包边界、摘要与历史快照、SpotBugs、census 和全仓回归
证明：输入仍是同一批冻结 Entry，转换仍是同一套连续性与只读判断，输出仍是同一组 HTTP
响应，所有写入和执行能力仍然关闭。
