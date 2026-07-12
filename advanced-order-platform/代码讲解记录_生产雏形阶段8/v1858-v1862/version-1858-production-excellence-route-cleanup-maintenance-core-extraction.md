# version-1858：路由清理维护核心迁移、共享测试引擎与只读契约收敛

本文在 v1858 的 Java 实现改动之前写成。它先把输入、处理、输出、边界和失败条件钉死，再让代码按这些事实移动。本项目中的 RouteCleanup 不是在线删除路由，也不是自动修改流量，而是把已经归档的版本证据转换成可审阅的只读报告。名称里虽然出现 cleanup、maintenance、mini-kv、handoff 等词，实际执行仍局限于 Java 内存中的不可变目录与历史路径字符串。本文不把静态引用说成真实联调，不把测试通过说成生产部署，也不把包迁移包装成新业务功能。

## 实际工作量说明

本项目禁止硬凑。v1858 的工作量来自编译依赖闭包，而不是为了凑一版随意挑若干同前缀文件。入口是根包里的 OpsShardReadinessRouteCleanupMaintenanceController。它注入九个服务，分别生成分段目录、版本连续性、最新兄弟项目报告、交接配对审计、边界漂移、源计划对齐、测试预算计划、归档清单和最终收口。每个服务有一个配套响应类型，所以生产实现闭包正好是十八个文件。沿这些类型继续追踪出边，依赖只落到 Java 标准库、Spring 只读事务注解，以及 v1857 已经迁入 ops.maintenance.routecleanup 的证据分析器、证据响应、最新兄弟目录和源计划对齐服务。它们不反向读取尚未迁移的根包维护服务，因此可以整体搬迁而不制造循环依赖。

这十八个文件迁入既有 routecleanup 包，九个直接行为测试随实现移动。控制器不移动，因为它是 HTTP 组合入口；MaintenanceUpkeepCatalogSeeds 也暂留根包，因为它把九个端点装配成更高层的维护目录。这样处理后，根目录从二百四十九个 Java 文件降到二百三十一个，可迁移 backlog 从一百四十五降到一百二十七，RouteCleanup bucket 从一百四十一降到一百二十三。仓库内 ops 生产源码总数保持一千三百五十二，没有用增加包装类来换取表面上的目录变小。

本版还有一项维护性工作。v1854、v1855、v1856、v1857 的版本守卫都重复实现读取 UTF-8 文件、列举 Java 文件、统计字符串、统计汉字以及抽取十个标题的辅助方法。第三次出现相似结构时就应该建立共享 engine，继续复制会违背优雅门。因此 v1858 先引入短名 OpsExtractionTestSupport，把稳定机械动作收进去；每个版本测试只保留属于该版的数据清单、路由期望、边界读者和数字阈值。它不是为了减少测试，而是让每版新增测试更聚焦、更容易审阅，也让以后修改统一编码或目录遍历逻辑时只改一处。

## 入口路由

对使用者而言，本版前后的输入完全一致。HTTP 基础路径仍是 /api/v1/ops/shard-readiness，九个后缀仍分别是 /route-cleanup-maintenance-segment-catalog、/route-cleanup-maintenance-continuity、/route-cleanup-maintenance-latest-sibling-report、/route-cleanup-maintenance-handoff-pair-audit、/route-cleanup-maintenance-boundary-drift、/route-cleanup-maintenance-source-plan-alignment、/route-cleanup-maintenance-test-budget-plan、/route-cleanup-maintenance-archive-manifest 和 /route-cleanup-maintenance-closeout。请求仍是 GET，没有请求体，没有用户凭据输入，也没有触发上游进程的参数。

变化只发生在所有权。过去控制器和服务通过巨型 OpsShardReadinessRoutePaths 读取这些后缀；v1858 把九个后缀放进短小的 RouteCleanupRoutes。控制器直接引用该家庭路由所有者，服务用同一 BASE_PATH 与后缀组成 ENDPOINT。全局路由表删除重复字段，EndpointManifestService 仍会同时读取全局表和 RouteCleanupRoutes，并把字段名标准化回既有的 ROUTE_CLEANUP_* 名称。因此运维侧看到的端点清单、排序和字符串都不变，改变的是源码中谁有资格定义它们。

可以把一次请求理解为四步输入输出。第一步，客户端输入固定 GET 路径；Spring 控制器选择对应方法。第二步，控制器把调用转交给一个服务，不参与业务判断。第三步，服务从 v1857 的只读证据边界或自己的静态维护配置读取输入，计算计数、状态和检查项。第四步，控制器把不可变响应交给 JSON 序列化。包名改变不会进入这条线上可见的 JSON。机械守卫会反射 RouteCleanupRoutes 的字段，逐个比对九个旧字节，并确认全局路由表不再保存同名常量。

## 响应模型

九个响应各自表达一种维护视角，但共同遵循相同安全骨架：项目名、Java 版本标签、只读标志、执行许可标志、当前端点、配置 profile、事实列表或计数、检查项以及最终状态。SegmentCatalog 把维护区段列成目录；Continuity 判断历史版本是否连续；LatestSibling 记录被允许读取的兄弟项目计划信息；HandoffPairAudit 检查服务版本和路由版本是否配对；BoundaryDrift 汇总只读、启动、凭据、原始端点、托管连接和写路由违规数；SourcePlanAlignment 比较 Java 证据与声明的源计划；TestBudgetPlan 描述非 Docker 回归预算；ArchiveManifest 把服务端点映射到 e/<version>/evidence/*.json；Closeout 汇总前八项是否满足收口条件。

这些响应类型迁包后，Java 全限定名发生变化，但 HTTP 字段、字段顺序、集合内容和状态算法不变。SpotBugs 配置中原本针对九个响应的二十个 EI_EXPOSE 镜像规则必须随全限定名移动，其中 SegmentCatalogResponse 有四处，其余响应各两处。这里不能简单新增新规则并留下旧规则，因为那会掩盖过期豁免；正确输出是旧 FQN 计数归零、新 FQN 精确等于既有数量、豁免总意图不增加。

集合边界仍由现有响应构造和分析器约束。v1857 已经让证据 Entry 对列表做防御性复制，本版不改构造器，不把可变列表暴露给调用者，也不改变序列化注解。服务的 PROFILE 只是响应内部的固定标识，没有根包外部读者，所以继续保持包可见；ENDPOINT 有 MaintenanceUpkeepCatalogSeeds 等实测读者，才提升为 public static final。这个差别体现最小公开面：编译需要什么就公开什么，不因为迁包方便而把所有常量一并变成 API。

## 上游证据配置

本版服务的上游输入分为两类。第一类是 v1857 已抽取的 RouteCleanup 证据核心。BoundaryDrift、Continuity 和 HandoffPairAudit 读取 EvidenceAnalyzer 或 EvidenceResponse；LatestSibling 读取 LatestSiblingEvidenceCatalog；SegmentCatalog 和 TestBudgetPlan 使用 EvidenceAnalyzer；SourcePlanAlignment 复用已经迁移的源计划对齐服务与响应。由于这些依赖已经在同一包拥有稳定可见性，本版不需要跨回根包拿实现，也不需要新增桥接器。

第二类是服务自身保存的历史配置。例如 ArchiveManifest 把 segment-catalog 对应到 e/471/evidence/java-route-cleanup-maintenance-segment-catalog-v471.json，把 continuity 对应到 e/473/...，依次形成版本化归档清单。MaintenanceUpkeepCatalogSeeds 则记录服务版本、路由版本、消费者和边界名称。这些字符串是既有证据契约的一部分，v1858 只更新类型 import 和端点常量所有权，不移动 e 目录，不重写 JSON，不重新计算旧 digest，也不把历史版本号改成当前版本。

举一个通俗例子：BoundaryDrift 像一张只读体检表。输入不是生产数据库，而是 EvidenceAnalyzer 给出的若干已声明事实。每条事实说明是否只读、是否允许执行、是否启动 Java 或 mini-kv、是否读取凭据值、是否解析原始端点、是否打开托管审计连接、是否修改写路由。服务逐项计数，任何违规数非零就输出 blocked；全部为零才输出 passed。迁包前后，这张体检表的题目、答案和判分方式都不变，只是负责保管体检表的文件柜从根目录移动到 routecleanup 专区。

## 服务层核心流程

SegmentCatalog 先把证据条目按 segment 分组，输出每段覆盖的版本与边界；Continuity 从分析器取得版本序列，检查是否有缺口；LatestSibling 只呈现目录中允许公开的最近 Java、Node 或 mini-kv 计划标识，不联网刷新；HandoffPairAudit 将每个维护服务版本与其下一版路由交接配成对，找出未配对项；BoundaryDrift 对所有禁止行为做零违规统计；SourcePlanAlignment 将历史源计划声明与当前只读边界逐项对照；TestBudgetPlan 根据目录规模给出测试分层与预算文字；ArchiveManifest 检查归档项使用版本化 JSON 路径；Closeout 聚合这些结果并输出是否可以完成维护交接。

控制器在这套流程里故意很薄。它不读取文件、不解释状态、不修改列表，只选择 service 方法并返回响应。根包因此保留用户可见的入口，却不再承载实现细节。MaintenanceUpkeepCatalogSeeds 也只做组合：给每项一个名字、两个版本号、公共 ENDPOINT、证据路径、消费者和边界，再构造不可变 Item。它不需要访问 PROFILE，也不需要知道服务内部算法。迁移后包间依赖方向是 root adapter -> routecleanup implementation，而 routecleanup 不反向导入 root RouteCleanup 实现，结构上避免环。

共享测试 engine 的流程同样分层。OpsExtractionTestSupport 只处理通用事实：给定 Path 读取 UTF-8，列举目录里的 Java 文件，递归列举所有 Java 文件，判断后缀，统计子串，抽取二级标题，统计汉字和字母。v1858 测试提供具体输入，例如十八个文件名、九个测试名、九个路由键值、二十个 SpotBugs 镜像以及二百三十一个根文件阈值；engine 返回可断言的数据。这样 engine 不知道 v1858 的业务，v1858 也不复制底层机械动作，数据与行为边界清楚。

## Java 证据检查

Java 侧第一组证据是精确清单。守卫列出十八个生产文件和九个行为测试，断言它们存在于 routecleanup 包，同时断言旧根路径不存在。它还扫描新包文件，禁止出现对根包 RouteCleanup 实现的 import。控制器与 MaintenanceUpkeepCatalogSeeds 必须显式导入新包类型，证明依赖方向已经翻转。任何漏搬、误搬或同名副本都会让测试失败。

第二组证据是路由和可见性。测试反射 RouteCleanupRoutes，确认新增九个字段都是 public static final、标识符长度不超过四十、值与迁移前逐字节一致；同时确认全局路由表不再出现对应 ROUTE_CLEANUP_MAINTENANCE_* 定义。对九个服务，测试只要求 ENDPOINT 为公共不可变字段，并确认 PROFILE 不是 public。生产源码读者扫描必须能解释为什么公开这些 ENDPOINT，而不是靠主观说明。

第三组证据是规模 ratchet。direct-root Java 文件必须精确等于二百三十一个，递归 ops 生产 Java 文件不得超过一千三百五十二。endgame census 必须同时写出当前二百三十一、可迁移一百二十七、RouteCleanup 一百二十三、v1857 的历史数字以及 v1858 的新进度。只改一个测试期望而不改可复现账本会失败；放宽总量上限也会失败。第四组证据是完整 Maven verify，它会覆盖编译、单元与集成测试、JaCoCo、Spotless、SpotBugs 和文档守卫。

## mini-kv 证据检查

v1858 不启动 mini-kv，不调用 minikv_cli，也不读取实时槽位表。维护响应中出现 mini-kv，只因为历史证据目录记录了跨项目只读边界。例如某条 Entry 可以声明 startsMiniKvService=false，BoundaryDrift 会把 true 视为启动违规；这证明 Java 分析器会拒绝越界声明，不证明 C++ 进程在本轮运行过。任何文档若把这次 Maven 通过写成 mini-kv 联调通过，都是不真实的扩大结论。

本版也不移动 D:\C\mini-kv 的 e 归档，不更改 Node 对兄弟项目绝对路径的引用，不生成新的跨项目 fixture。输入仍是 Java 仓库内已有的不可变目录和路径文本，输出仍是 Java 只读响应。真正的跨项目 C1-C4 capstone 由总简报另行安排，需要真实 jar、真实 minikv_cli 和统一报告；在那之前，本版只能声称单项目验证与跨项目契约对齐。

这个限制很重要。若维护服务为了“更真实”而自动启动 mini-kv、读取凭据或连接审计数据库，它就从证据阅读器变成执行器，风险和权限模型会完全改变。v1858 的机械守卫继续要求执行许可为 false、启动违规为零、凭据值读取为零、写路由修改为零。保持这种克制，才使包迁移可以独立于 C++ 仓库并行完成。

## 阻断与安全边界

本版的安全边界可以概括为只读、无启动、无凭据值、无原始端点、无托管连接、无写路由。所有九个服务继续使用 Transactional(readOnly = true) 或纯内存计算，不加入保存、删除、发布消息或网络调用。控制器仍只有 GET 映射。归档清单只返回路径字符串，不打开文件，更不会按路径执行内容。SourcePlanAlignment 只比较声明，TestBudgetPlan 只生成计划，Closeout 只汇总状态。

遇到以下情况必须阻断而不是“先过测试再说”：路由字节变化；响应组件变化；旧 fixture 或 evidence JSON 被改；PROFILE 无读者却变 public；新包导入根包实现形成反向依赖；SpotBugs 旧镜像残留或新镜像数量增加；根目录不是二百三十一个；总 ops 文件数超过一千三百五十二；walkthrough 少于三千汉字；为了让守卫通过而修改历史期望。编译器暴露的真实 import 问题可以修，机械门暴露的账本遗漏可以补，但不允许降低门槛。

共享测试支撑也有边界。它只进入 test source，不进入生产 jar，不提供 Spring bean，不拥有版本数字，不替每版决定文件族。若以后某版需要新的业务断言，应放在该版测试中；只有第三次重复且与版本无关的机械动作才进入 support。这样既消除重复，又避免制造一个知道所有版本细节的测试巨类。被 v1857 测试触碰的重复 helper 会在离开时达标，这正是童子军规则的实际应用。

## 测试覆盖

聚焦阶段先运行 test-compile，让迁包造成的 package-private、import 与构造器问题尽快暴露。然后运行九个移动后的服务测试、MaintenanceController 相关集成测试、MaintenanceUpkeepCatalog 和 RouteTopology 测试、OpsShardReadinessRoutePathsTests、EndpointManifest 测试、v1857 与 v1858 版本守卫。该集合覆盖服务算法、控制器装配、根组合目录、路由反射兼容、旧版本保护和新版本精确清单，而不是只跑一个“能编译”的快乐路径。

Spotless 检查包声明、import 顺序和格式；SpotBugs 检查九个响应迁包后没有新增暴露问题；JaCoCo 继续使用既有覆盖率门，不因迁移放宽。完整 mvnw verify 必须在讲解、设计、代码、账本和测试全部完成后运行。长验证期间只能只读准备下一批候选，不能继续修改当前提交，也不能用并行写操作让结果失去可归因性。

版本关闭分两步。实现提交包含迁移、测试、文档和 live ledger，推送后等待真实 GitHub Actions 通过；随后只把 J68 行补成可复现的测试数、耗时、JaCoCo 类数、SpotBugs 结果、实现 commit 和 CI run，再做 closeout 提交、注解 tag 与推送，并确认 closeout CI。只有本地与远端都绿、工作树清洁、tag 指向 closeout，才能说 v1858 收口。评审检查点到来时停止，未到检查点则按同一简报继续下一闭包。

## 一句话总结

v1858 的输入是九组既有 RouteCleanup 维护服务、v1857 已稳定的只读证据边界与九条固定 GET 路由，处理是把十八个实现和九个行为测试迁入明确包、把路由所有权收回家庭路由表、只公开实测需要的 ENDPOINT，并把重复版本守卫机械动作提炼成共享测试 engine，输出是字节不变的九类只读响应、更小的二百三十一个文件根目录、可复现的一百二十七文件 backlog 和更容易长期维护的测试结构；它不执行清理、不启动兄弟项目、不改历史证据，却用编译、精确清单、可见性、路由、SpotBugs、规模 ratchet、中文讲解和完整 Maven 验证把“只是搬文件”变成可审计的工程收敛。
