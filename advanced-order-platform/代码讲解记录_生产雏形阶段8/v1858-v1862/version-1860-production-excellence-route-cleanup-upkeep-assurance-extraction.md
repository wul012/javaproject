# version-1860：维护保障组合链迁移、公开面收束与测试引擎提炼

本篇解释本项目 v1860 的真实改动、输入输出、组合机理和机械证据。它不是把十个文件换一个目录后罗列文件名，而是说明为什么这十个文件构成一个闭包、为什么五个服务必须按依赖顺序理解、哪些符号确实需要公开，以及迁移后怎样证明路由、响应和只读边界没有发生变化。全文禁止硬凑；每一段都对应代码中的可复查事实或本版新增的失败门。

## 实际工作量说明

v1860 处理的是 RouteCleanup 维护链中的保障层。它由 ArchiveDigestLedger、OperatorReviewPacket、VersionLineage、ReadinessGate 和 UpkeepCloseout 五组服务与响应组成，共十个生产文件；五个直接行为测试随实现迁入 `ops.maintenance.routecleanup`。`OpsShardReadinessRouteCleanupMaintenanceUpkeepAssuranceController` 继续留在根包，因为它承担 Spring 扫描下的 HTTP 适配职责，不拥有维护事实。迁移前根包直接文件为二百一十九，计划迁移后为二百零九；可迁移积压从一百一十五降到一百零五，RouteCleanup 分类从一百一十一降到一百零一，总体 `ops` 生产源码仍是固定的一千三百五十二个，未分类文件必须为零。

这不是五组互不相关的端点。摘要账本从 v1859 的九项维护目录生成稳定摘要；操作员审阅包消费目录、交接矩阵、持续集成期望、失败关闭策略和摘要账本；版本链检查服务版本与路由版本的连续关系；就绪门把审阅包、版本链、拓扑、失败关闭策略和持续集成期望汇成五项判定；最终收尾再读取目录、审阅包、就绪门、版本链和摘要账本。因此，把其中一半迁走会留下大量根包到新包再返回根包的交叉边，完整迁移五级组合链才是最小的行为闭包。

本版还包含一项维护性重构。v1859 守卫已经使用 `OpsExtractionTestSupport` 统一文件读取、Java 文件枚举、标题提取和汉字统计，但“根据文件名构造类型集合、扫描包外源码、统计来源与类型边”和“寻找包含某个常量引用的外部读者”仍写在版本测试内部。v1860 最初把两种机械操作继续塞进同一 helper，聚焦门立即因文件超过八十行而失败；修复没有抬高上限，而是新增短名 `OpsBoundaryTestSupport` 专管边界 census 和读者定位，原 helper 继续只管文件与讲解。v1859 与 v1860 只提供文件清单、期望值和各自的公开面策略，两个 support 都保持小而单责。

## 入口路由

控制器对外仍提供五个 GET 入口：`/route-cleanup-maintenance-archive-digest-ledger`、`/route-cleanup-maintenance-operator-review-packet`、`/route-cleanup-maintenance-version-lineage`、`/route-cleanup-maintenance-readiness-gate` 和 `/route-cleanup-maintenance-upkeep-closeout`。基础路径保持 `/api/v1/ops/shard-readiness`。客户端看到的完整地址、请求方法、返回类型和状态判断都不变化，变化的只是后缀常量的代码归属。

迁移前，这五个后缀位于超过一千行的全局 `OpsShardReadinessRoutePaths`。迁移后，它们由家庭内的 `RouteCleanupRoutes` 持有，字段名分别缩短为 `MAINTENANCE_ARCHIVE_DIGEST_LEDGER`、`MAINTENANCE_OPERATOR_REVIEW_PACKET`、`MAINTENANCE_VERSION_LINEAGE`、`MAINTENANCE_READINESS_GATE` 和 `MAINTENANCE_UPKEEP_CLOSEOUT`。这些新名字都没有重复包名已经表达的 `ROUTE_CLEANUP` 上下文，且长度不超过四十字符。全局路由表删除原字段，家庭路由表增加同字节值，守卫同时反射检查字段修饰符和值，并扫描旧 owner 确认没有双重事实来源。

控制器本身只做三件事：通过构造器接收五个 service；为五个方法声明对应的 `GetMapping`；返回 service 已生成的 response。它不会计算摘要、判断版本连续性或拼装收尾项。`RequestMapping` 也改用 `RouteCleanupRoutes.BASE_PATH`，使根控制器不再依赖全局路由聚合器中的家庭细节。控制器留根不等于逻辑留根，它只是面向 Spring 和 HTTP 的薄适配器。

## 响应模型

ArchiveDigestLedgerResponse 输出项目、版本、只读标记、执行许可、端点、配置标识、条目数、摘要算法、摘要长度、九个 LedgerEntry、检查项和状态。每个条目保留名称、来源端点、证据路径、摘要和原状态。摘要长度固定为十六个十六进制字符，响应不会携带归档文件内容，也不会暴露凭据或原始连接地址。

OperatorReviewPacketResponse 是面向操作员的组合视图。它记录五个 ReviewSection，以及目录条目数、交接矩阵条目数、持续集成期望数、策略数和摘要条目数。每个 section 保存来源名称、来源配置、来源端点、条目数量与状态。它不复制来源服务的完整 payload，而是保留足够的追踪信息，让审阅者能从组合结果返回具体证据端点。

VersionLineageResponse 保存九个 LineagePair，明确首尾服务版本、首尾路由版本、两种版本步长和缺口数量。ReadinessGateResponse 保存五个 GateCheck，每项都带来源端点、布尔结果、原因和状态，并汇总接受数与阻断数。UpkeepCloseoutResponse 保存五个 CloseoutCheck、目录数量、就绪检查数量、摘要数量、最新路由版本和来源计划。五种 response 都继续使用不可变 record 与 List 组件，本版只迁移包名，不增删组件、不调整顺序、不改变序列化字段。

SpotBugs 对这些包含 List 的响应存在两组镜像排除项。迁移必须把五个旧 FQN 各自改到新包，因此配置中应出现十个新 FQN、零个旧 FQN。这里的目标不是新增豁免，而是让既有、可审计的边界跟随类型移动；条目数量增加或减少都视为失败。

## 上游证据配置

五个服务的首要输入来自 v1859 已迁入同包的 UpkeepCatalog。目录有九个不可变 Item，记录名称、服务版本、路由版本、端点、证据路径、消费者、边界与状态。ArchiveDigestLedger 直接读取 Item；VersionLineage 读取同一列表判断版本；OperatorReviewPacket 通过 UpkeepCatalogService 读取目录响应。由于这些上游类型已经在 `routecleanup` 包中，v1860 迁入后可以删除跨包 import，让目录事实和保障行为处在同一个所有权边界。

OperatorReviewPacket 还消费 ConsumerHandoffMatrix、CiExpectationManifest 与 FailClosedPolicy。ReadinessGate 再消费 OperatorReviewPacket、VersionLineage、RouteTopologyIndex、FailClosedPolicy 和 CiExpectationManifest。所有这些输入都是已计算的只读响应，不接受用户传入的执行命令。UpkeepCloseout 最后消费目录、审阅包、就绪门、版本链和摘要账本，并只汇总状态，不触发任何修复动作。

迁移后的包外生产边界不是凭感觉决定。当前源码测得五个来源文件、十八条来源到类型的边、十个被引用类型。五个来源是根控制器、ArchiveVerifierSummary、ReleaseChecklist、RemediationQueue 与 ShardFieldMap。后两者将在下一版迁入同一家庭，届时 v1860 守卫的实时边界应继续收缩；历史守卫必须允许这种由依赖归位造成的正确收缩，但不能允许新增未知读者。

## 服务层核心流程

ArchiveDigestLedgerService 先遍历九个目录项。每项把 `name|endpoint|evidencePath` 按 UTF-8 编码后送入 SHA-256，再截取十六个十六进制字符作为稳定摘要。它检查条目恰为九个、每个摘要长度正确、摘要互不重复且来源状态全为 passed。这里所谓 archive digest 是由目录字段生成的审阅指纹，不会打开 `e/` 目录，不会读取归档字节，更不会修改历史证据。因此输入是内存中的目录项，输出是九项摘要账本，副作用为零。

OperatorReviewPacketService 依次调用目录、交接矩阵、持续集成期望、失败关闭策略和摘要账本，转换为五个 ReviewSection。它要求 section 数量为五、每个来源条目数大于零、每个来源状态为 passed。输入中的详细数据不被复制到大而混杂的新响应；输出只保留来源配置、端点、数量和状态。这样操作员得到统一入口，同时仍能沿 sourceEndpoint 回到原始证据，组合层不会取代事实所有者。

VersionLineageService 对九个 Item 按索引生成 LineagePair。每项要求 `routeVersion = serviceVersion + 1`，相邻服务版本要求步长为二，最后一项以负一表示没有下一版本。它汇总首尾版本、固定步长和 gapCount；只有缺口为零才返回 passed。该算法把版本连续性从文档约定变成可执行检查，同时保持输入只读，不会为了填补缺口重写目录。

ReadinessGateService 建立五项明确判定。第一项要求操作员包通过；第二项要求版本缺口为零；第三项要求拓扑条目为九且最新路由版本为四百八十八；第四项要求失败关闭策略的零违规数等于策略数；第五项要求持续集成计划既不启动 Java，也不启动 mini-kv。每项产生名称、来源端点、原因、布尔值和状态，最终只有五项全部接受才通过。它是判定器，不是执行器。

UpkeepCloseoutService 再读取目录、操作员包、就绪门、版本链和摘要账本，形成五个 CloseoutCheck。它输出目录条目数、就绪门检查数、摘要条目数、最新路由版本和 `Node v549` 来源计划。所有 check 必须为 passed 才能收尾。这个服务不会因为名称含 closeout 就提交代码、触发部署或关闭进程；它只是把本轮维护证据压缩成可供下游读取的最终报告。

## Java 证据检查

第一组守卫锁定十个生产文件与五个直接测试。文件必须存在于 `ops.maintenance.routecleanup`，旧根路径必须不存在；根控制器必须仍在原位置并导入新包；任何迁入文件都不得反向 import 根包 RouteCleanup 实现。编译器负责暴露遗漏的构造器、方法和响应类型引用，不能靠桥接类掩盖未闭合依赖。

第二组守卫锁定路由和公开面。五个后缀在家庭 owner 中必须保持原字节值，在全局 owner 中必须完全消失。十个类型的包外生产边界初始值必须为五个来源、十八条类型边和十个目标。ArchiveDigestLedgerService.ENDPOINT 因 ArchiveVerifierSummary 读取而公开；VersionLineageService.ENDPOINT 因 ShardFieldMap 读取而公开。OperatorReviewPacket、ReadinessGate 与 UpkeepCloseout 的 ENDPOINT 没有包外生产常量读者，应保持包内可见；五个 PROFILE 全部保持包内可见。

第三组守卫锁定维护性。`OpsBoundaryTestSupport` 提供通用边界 census 和外部读者查找，`OpsExtractionTestSupport` 保留文件与讲解操作；v1859 删除本地复制的扫描循环并调用前者，v1860 也只提供数据。两个 helper 都被机械限制在八十行以内。守卫同时检查新测试类和 `RouteCleanupRoutes` 的短名预算、全局路由表行数只减不增、十个 SpotBugs FQN 精确迁移、根包二百零九、总量不超过一千三百五十二、未分类为零。任何为了通过迁移而上调 ratchet 的做法都被视为失败。

## mini-kv 证据检查

v1860 不启动 mini-kv，不执行 `minikv_cli`，也不读取实时 shard、slot、WAL 或 snapshot。ReadinessGate 中 `startsMiniKvService=false` 来自静态持续集成期望，它证明 Java 保障层明确禁止自动启动上游，不证明 C++ 服务在本轮实际接受了请求。FailClosedPolicy 中禁止 `node-start-or-stop-java-or-mini-kv` 的条目同样是边界证据，不是运行记录。

ArchiveDigestLedger 使用 Java 维护目录中的证据路径生成摘要，不打开 mini-kv 的 `e/` 归档；UpkeepCloseout 记录 Node 来源计划，也不修改 Node 仓库。Node 对 Java 与 C++ 历史归档的绝对路径依赖继续冻结。本版可以声明 Java 单仓保障组合链经过静态与测试验证，不能宣称完成新的跨项目实时联调。真正的系统级结论仍由独立 capstone 使用最终 Java tag、真实 Java jar 和真实 `minikv_cli` 给出。

这种措辞边界很重要。把静态字段误写成“mini-kv 已验证”会混淆输入证据与运行证据；把不启动上游写成能力不足也不准确。本版的设计目标正是让维护审阅接口在没有上游进程、凭据和网络连接时仍能重现已有证据，并在任何执行企图出现前失败关闭。

## 阻断与安全边界

五个服务都使用 `Transactional(readOnly = true)`，五个控制器方法都是 GET，响应统一保持 `readOnly=true` 与 `executionAllowed=false`。代码不调用 repository save，不发布消息，不写文件，不解析原始端点，不读取凭据值，不建立托管审计连接，不修改写路由，不启动或停止 Java、Node、mini-kv，也不执行部署或回滚。摘要算法只处理公开目录字段，版本算法只处理整数，组合服务只处理不可变响应。

以下事实任一出现都必须阻断：十文件闭包不完整；控制器或保留读者编译失败；路由字节变化；响应组件变化；摘要材料或长度变化；版本步长、拓扑数量、策略数量变化；新包反向依赖根实现；没有读者的 ENDPOINT 被公开；有读者的两个 ENDPOINT 被隐藏；PROFILE 公开；SpotBugs 镜像不等于十；根计数不等于二百零九；总量增长；未分类不为零；中文讲解不足三千汉字；为了让测试通过而修改 fixture 或历史期望。

失败后的修复顺序也固定。先判断是否漏迁类型或漏加 import，再检查路由 owner 和修饰符，随后检查历史守卫是否需要因正确的依赖归位而收紧，最后才看行为断言。不得删除守卫、放宽计数、复制目录事实或增加桥接类来绕过根因。这样 fail-closed 不只存在于业务响应，也存在于本版工程流程。

## 测试覆盖

五个直接服务测试分别验证：摘要账本由目录字段稳定生成；操作员包确实消费类型化维护服务并形成五段；版本链的九组服务/路由关系连续；就绪门由五类报告组成且全部接受；收尾报告聚合五项来源并保持只读。这五个测试随实现迁入同包，因此可以验证包内 ENDPOINT 和 PROFILE，而无需扩大生产公开面。

根包保留的 ReleaseChecklist、RemediationQueue、ArchiveVerifierSummary、ShardFieldMap 及其测试会通过显式 import 消费新包类型。UpkeepAssurance 的 MockMvc 集成测试继续从真实 Spring 容器检查五个 GET 路由和响应。`OpsShardReadinessMaintenanceRoutePathsTests` 删除已经由 v1860 家庭守卫接管的五项，避免同一端点在两个 owner 测试里重复；其余尚未迁移的维护端点继续由该根测试保护。

执行顺序是编译器导向的 `test-compile`、v1859/v1860 聚焦守卫、五个服务测试、路由与集成测试、Spotless，最后完整 `mvnw verify`。讲解必须在最终 verify 之前完成。全量结果需要从本轮时间窗内的 Surefire XML 重建测试数，并记录 JaCoCo 类计数与 SpotBugs 类计数；仅凭 Maven 退出码或进度表自述不算完整证据。提交、annotated tag、push 和两作业远端 CI 都通过后，v1860 才能关闭。

## 一句话总结

v1860 把“摘要账本、操作员审阅、版本连续性、就绪判定、维护收尾”这条五级只读保障链完整归入 RouteCleanup 家庭，用字节不变的短路由 owner、实测最小公开面、共享边界扫描引擎和可失败的全量门证明：目录变清晰了，契约与安全边界没有被偷偷改写。
