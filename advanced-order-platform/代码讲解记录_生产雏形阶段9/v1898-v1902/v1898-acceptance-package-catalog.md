# v1898 Release Acceptance Package Catalog 代码讲解

## 入口路由

本版本处理的是 release acceptance route-path split sustainment 之后的一份“验收包”只读响应。可以把它理解成：上游已经把一组路由拆分结果持续维护好，Java 再把这些结果整理成一份供审阅、归档和后续变更使用的结构化报告。外部请求仍然从根 `ops` 包中的 Controller 进入，Controller 继续调用公开的
`OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageService`。
这个公开类的全限定名、构造参数、`registry()` 方法、Spring `@Service` 身份都没有变化，因此
Controller、下游 closeout receipt、closeout archive index 以及任何依赖注入配置都不需要知道
内部发生了重构。

路由字符串仍由 `OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH` 与
`RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_SUSTAINMENT_ACCEPTANCE_PACKAGE` 拼接。版本仍是
`Java v1634`，事务仍标注 `@Transactional(readOnly = true)`。输入不是用户提交的命令，也不是
可执行脚本，而是上游 sustainment Service 返回的只读 Response。一次请求的入口流程可以通俗地
写成：Controller 接收 GET 请求，公开 Service 调用上游 `registry()`，本包把上游对象投影成九组
证据，再生成 Markdown、checks 与 status，最后返回公开 Response。这里没有新增 route，没有
改变 HTTP 方法，没有增加写事务，也没有把内部 `PackageCatalog` 或 `PackageSupport` 暴露为
Spring bean。

本版本刻意区分“同一目录”和“同一生命周期”。目录里还有 closeout receipt 与 closeout archive
index 两条响应，它们会消费主验收包，但各自拥有独立 Response、Service、Support、Renderer 和
测试。它们没有因为文件放在一起就被塞进同一个 Catalog。这样的边界很重要：优雅不是把目录压成
一个大类，而是让一次请求的一组同生命周期数据拥有一个明确 owner。主响应收敛，两个下游响应
保持原样，外部调用方向仍是主包到 receipt、receipt 到 archive index。

## 响应模型

公开 Response 仍包含项目名、版本、只读和执行许可位、Node 来源计划、上游 sustainment 版本与
endpoint、当前 endpoint 和 profile、十个数量字段、九组领域列表、九段 Markdown、40 条 checks
以及最终 status。九组领域列表依次是 source snapshots、version lineage、acceptance decisions、
archive items、review items、CI evidence、runtime boundaries、next-change rules 和 scorecard。
它们不是九份互不相关的小报告，而是同一验收包的九个视角。

本版在旧实现上先建立完整响应 oracle。测试最初故意写入全零 SHA-256，旧实现运行后如预期失败，
从失败信息取得真实摘要
`2679ebdc83c27789a17d52d8d255f96ebda0cb081e9f37295b9953613ecca51a`，再把它固定下来。
同时冻结数量向量 `1/3/6/5/5/5/7/6/9/9/40`：一条来源快照、三条版本谱系、六条决策、五个
归档项、五个复核项、五条 CI 证据、七条运行边界、六条下一变更规则、九条 scorecard、九段
Markdown 和四十条 checks。摘要由按属性名排序的 UTF-8 JSON 计算，所以顶层字段、嵌套 record、
布尔值、字符串、列表顺序、Markdown 行和 checks 任一字节变化都会让测试失败。

新 `PackageCatalog.Evidence` 是包内不可变 record。它不替代公开 Response，而是一次请求内部的
中间快照。compact constructor 对九组列表逐一执行 `List.copyOf`，确保 Renderer 和 Support
看到的是同一批不可变数据。公开 Response 的字段顺序与构造顺序仍由原类型决定。旧实现、接入
新装配但尚未删旧文件的实现、删除旧 owner 后的最终实现，先后通过同一个 oracle；测试期望没有
为了迁就新代码而修改。

## 上游证据配置

主 Service 的唯一运行时输入是
`OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse`。该对象声明自身版本
`Java v1604`，同时携带更早的 closeout `Java v1579` 与 route split `Java v1570`。本包没有
重新读取磁盘、数据库或网络来猜测这些值，而是沿公开的上游边界消费。`PackageSupport` 仍要求
这三个版本精确匹配，若上游版本或 status 不符合预设，当前响应会落到 `blocked`。

九组投影的输入关系透明可追踪。source snapshot 复制上游版本、endpoint、status 和 profile；
lineage 把 split、closeout、sustainment 三个阶段串成有序谱系；decision 检查上游 ownership
rule、只读位和 execution 位；archive item 检查 boundary、CI gate 与 consumer handoff；
review item 检查 route、test、CI 与 archive owner；CI evidence 映射上游五个 CI gate；
runtime boundary 映射七条 boundary guard；next-change rule 根据上游通过状态给出六个落点；
scorecard 最后对前八组列表逐项汇总。也就是说，scorecard 不是独立抄一份常量，而是前八组证据
是否通过的派生结果。

来源计划常量仍是 `Node v1903`，并保留 `Node v1879-v1903` 的平行计划说明。这些字符串属于已
发布合同，本版不改写。scorecard 中“acceptance package is split into focused catalogs and
renderers”也保持原字节；这里的 focused catalogs 表示职责按领域分离，并不要求“一列表一 Java
类”。重构后的 `PackageCatalog` 仍有九个清晰私有方法，数据边界没有消失，只是不再把每个
方法包装成一个超长文件名的单次调用类。

## 服务层核心流程

旧 Service 先声明九个局部变量，逐个调用九个超长类名；scorecard Catalog 再接收前八个列表；
Support 接收九个列表和 Markdown；Renderer 也接收九个列表。相同的一次请求数据被三次展开，
Service 的视觉噪声高，参数顺序容易错位，新增第十组数据时至少需要修改三条长签名。虽然每个
小 Catalog 看似单一职责，但合起来形成了“文件多、调用窄、生命周期完全相同”的结构性重复。

新流程只有三个核心语句。第一步 `sourceService.registry()` 取得上游响应。第二步
`PackageCatalog.evidence(source)` 一次生成 typed snapshot。第三步
`PackageSupport.response(..., evidence, ReportRenderer.render(evidence))` 分别委托展示与
判定。Service 从 88 行降到 32 行，不再知道 scorecard 依赖哪八组列表，也不再承担列表顺序的
人工接线职责。

`PackageCatalog` 内部仍按业务顺序显式生成九组数据。它先得到前八组，再把这些变量交给私有
`scorecard(...)`，最后构造 `Evidence`。重复出现在 decision 与 review 中的 ownership 查询被
收为一个 `ownershipHeld`；archive 的三种 all-match 检查分别保留有语义的方法名；各种 record
创建使用 `decision`、`archiveItem`、`reviewItem`、`nextChangeRule` 等短 helper。Catalog 不读取
Support 常量，不调用 Renderer，不构造最终 Response，依赖方向保持单向。

`ReportRenderer` 仍有九个 section 方法，因此定位某段 Markdown 文案只需找对应方法。它的入口
从九个列表参数变成一个 `PackageCatalog.Evidence`，内部依次读取九个 accessor。`PackageSupport`
从 Evidence 读取数量和列表，继续生成原有四十条 checks、验证固定版本、执行 all-match 并构造
Response。数据生产、文本展示、状态判定三个职责因此既分开，又共享同一个不可变事实源。

## Java 证据检查

改动前主家族是 13 个生产文件、1,167 行：九个 Catalog 共 458 行，旧 Support 420 行，Service
88 行，Renderer 139 行，公开 Response 62 行。改动后是 5 个文件、773 行：
`PackageCatalog` 330 行、`PackageSupport` 219 行、Service 32 行、Renderer 130 行、公开
Response 62 行。净删八个文件和 394 行。这里不是把 458 行机械粘贴成更大的 458 行类：类型导入
消除了反复限定名，共享 helper 消除了重复 ownership 查询与 record 构造噪声，Evidence 取代了
三处九参数展开。

全局生产 Java 从 1,301 降到 1,293，ops 从 1,169 降到 1,161，Catalog 从 251 降到 243，
AcceptancePackage 整目录从 26 降到 18。Renderer 数量保持 30，行数从 3,185 降到 3,176，
长 Renderer 文件名继续为零。Readiness 文件从 966 降到 956。最大生产文件仍为 738 行，超过
500 行仍是 32，超过 750 与 1,000 行保持零，因此本版没有把复杂度转移成新的巨型文件。

生产长文件 stem、长标识符使用次数和唯一值从 `1054/19458/2613` 收紧到
`1044/19346/2603`；测试从 `685/9768/3646` 收紧到 `680/9763/3641`。exact name baseline
删除十五个退休文件条目与十五个退休类型名，共三十项，新增为零。新增文件
`PackageCatalog`、`PackageSupport`、`PackageResponseOracleTests` 等都在四十字符命名预算内。
这些数字已写入会失败的 Java elegance、ops census 与 readability ratchet，不只是讲解中的
自述。

## mini-kv 证据检查

本响应会在 checks 中声明不自动启动 Node 或 mini-kv，但它不会启动进程来“证明没有启动”。
机理是权限与调用图上都没有相应能力：Service 只调用 Java 上游 sustainment Service，Catalog
只读取 Java Response，Renderer 只拼接 Markdown，Support 只计算内存中的数量、谓词和状态。
本包没有 socket、进程启动器、文件写入器或 mini-kv 客户端依赖，因此一次 GET 不可能穿透到
mini-kv 写路径。

mini-kv 在四项目关系中提供更上游的只读证据语义，但本版本是 Java 内部结构优化，不改变跨项目
schema、endpoint、fixture 或摘要。Node 已固定的历史 archive 路径也没有移动。响应中的
`no-node-or-minikv-auto-start`、`no-write-routing` 等 checks 保持原字节；完整 JSON oracle
覆盖它们，所以若重构误删或改序会立即失败。换言之，本版对 mini-kv 的正确处理不是“顺手去改
C++”，而是保持既有消费合同、保持无启动权限、保持历史证据可复查。

本轮没有访问 mini-kv 凭据，没有读取原始 endpoint value，没有建立 managed audit connection，
也没有把 Java 的 status 反写给上游。后续若 C++ 或 Node 需要对齐新合同，必须另开合同版本并按
依赖顺序处理；当前没有新合同，因此关联项目不需要跟随。这样的克制也是工程价值：内部优雅改造
不制造系统级协调成本。

## 阻断与安全边界

公开 Response 继续固定 `readOnly=true` 与 `executionAllowed=false`。`PackageSupport` 的 status
只有在三类条件同时满足时才是 `passed`：十个数量全部匹配；上游 status 和三个 Java 版本匹配；
九组证据的全部 predicate 通过。任何 source snapshot 不是 passed、任一 decision 未接受、任一
archive/review/CI 项失败、任一 runtime boundary 未锁定、任一 next-change rule 未 ready、任一
scorecard 未 passed，都会返回 `blocked`。

四十条 checks 继续显式记录来源计划、上游版本、各类数量、通过数量和安全禁令。禁令覆盖 runtime
execution、write routing、credential value read、raw endpoint resolution、managed audit
connection、deployment/rollback 以及 Node/mini-kv 自动启动。重构没有把这些字符串移进
Catalog，因为它们属于最终响应判定和审计说明，仍由 Support 统一拥有。Catalog 只拥有领域数据，
Support 才拥有安全政策与通过条件。

结构门禁止 Catalog 反向依赖 `PackageSupport` 或 `ReportRenderer`，防止数据 owner 偷偷承担
展示或状态职责。行数门把 Catalog 限在 330、Support 限在 219、Renderer 限在 130、Service
限在 32；目录最多 18 个生产文件，主家族最多五个 owner。九个退休 Catalog 与旧长名 Support
被列入永久缺席清单。禁止硬凑的含义在这里很具体：不能靠放宽行数、复活小类、修改 oracle、
删除检查或改 fixture 来换取绿灯。

## 测试覆盖

测试按“先冻结、再替换、后删除”分三阶段执行。第一阶段只新增
`PackageResponseOracleTests`，零摘要在旧实现失败，真实摘要固定后旧实现通过。第二阶段接入
`PackageCatalog.Evidence`、`PackageSupport` 和新调用链，旧九个文件仍在，此时同一 oracle 与
既有 package 套件通过。第三阶段删除十个旧生产 owner，再跑同一选择仍通过。这证明成功不是因为
类路径上还残留旧实现。

原 Catalog 行为断言保留在 `PackageCatalogTests`：版本、Node 计划、十个数量、status、只读位、
执行位、lineage 顺序和所有 ready/passed/locked 谓词仍受检查。不可变性保留在
`PackageServiceTests`；九组领域列表、Markdown、Markdown 行与 checks 都不能修改。
`PackageRendererTests` 保留九个标题顺序、关键 checks 与 next-change 文本；Controller 测试仅
缩短 owner 名，不改 HTTP 行为。`PackageMarkdownTests` 与两个 closeout 子家族测试不受影响。

`AcceptancePackageHistoryTests` 同时保护历史与当前：v1842 文档和旧 walkthrough 必须仍在，
当前目录必须精确包含十八个文件，十个退休 owner 和十三个更早退休 Renderer 必须缺席，测试目录
最多十个文件，Service 只能装配一次 Evidence，Catalog 必须恰好九次 `List.copyOf`，Renderer
和 Support 必须使用 typed signature。联合门还运行 Java exact name baseline、全局 ops census、
sustainment 依赖方向和根 readability ratchet。

## 实际工作量说明

本版本不是把九个类删掉就结束。首先调查了全 ops 的 Catalog 密度，比较 releaseapproval、
TextPackageSubmissionPreflight 与 ReleaseAcceptancePackage 三个候选；再用 CodeGraph 检查
公开 Service 的影响半径，确认主响应与两个 closeout 子响应的生命周期边界。随后统计九 Catalog
458 行、Support 420 行、Service 88 行和 Renderer 139 行，确定有足够工作量支撑一篇透明讲解，
也确定 Catalog 可以在 330 行内完成而不制造巨型文件。

实现阶段新增完整 oracle 并真实走过一次失败；设计 Evidence 的九个字段与装配顺序；合并九个
Catalog 时保留每条 record 文本、顺序和谓词；提取复用 helper；把 Support 的十一个领域参数降为
一个 Evidence；把 Renderer 的九参数入口降为 typed snapshot；把 Service 收到三十二行；删除
十个旧 owner；重命名五个长测试类；升级 v1842 历史门；删除 exact baseline 三十项；收紧生产、
测试、ops、Catalog、Renderer、Readiness 和目录文件数阈值。

维护收益体现在以后新增证据组时的修改面。旧结构要创建长文件、改 Service、改 Renderer 长签名、
改 Support 长签名、改 scorecard 参数；新结构仍需在 Catalog 中明确生成数据，但跨层只扩展
Evidence，并由编译器提示 Renderer 与 Support 的消费点。领域方法仍独立可读，不以 Map 或
Object[] 隐藏类型。总行数真实下降三百九十四行，测试只增加一个完整 oracle，公开合同零变化，
这才是本项目向 coding brilliant and elegant 靠近的一次有效优化，而不是版本号推进。

## 一句话总结

v1898 在不改变任何公开路由、响应字节和安全边界的前提下，把同一验收包的九个单列表 Catalog
与三处参数展开收敛成一次不可变 typed Evidence 装配，使主家族从十三文件一千一百六十七行降为
五文件七百七十三行，并用完整 SHA oracle、精确结构门和只减不增 ratchet 证明这次优雅是真实、
可复现、可维护的。
