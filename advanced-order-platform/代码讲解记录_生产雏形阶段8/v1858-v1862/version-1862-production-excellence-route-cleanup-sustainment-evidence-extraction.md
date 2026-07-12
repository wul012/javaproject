# v1862 RouteCleanup 持续维护证据闭包拆分讲解

## 实际工作量说明

本项目这一版处理的是 RouteCleanup 维护链中最后一组“证据汇总层”，不是增加一个看起来热闹却缺少边界的新功能。迁移对象一共十二个生产文件，由六组 Service 和 Response 组成，分别负责交接验收摘要、依赖边界图、归档保留日历、测试证据汇总、运维评分卡和持续维护收尾。与它们直接对应的六个行为测试也随实现迁移；OperationsScorecard 与 SustainmentCloseout 测试共同使用的包内 fixture 一并迁移，因此测试闭包实际是七个文件。HTTP 控制器不下沉，仍留在根 `ops` 包中作为可见入口，只通过导入调用新的包边界。

这次工作的价值不在“目录变了”，而在依赖方向终于和概念方向一致。迁移前，六个服务与一百多个其他 RouteCleanup 类平铺在根包里，Java 的默认包可见性会把大量偶然可访问关系伪装成合理设计。迁移后，底层的所有者、风险和新鲜度证据先进入交接摘要；目录项目与禁止操作进入依赖边界图；归档路径与版本进入保留日历；固定测试契约进入测试证据汇总；四个维度再汇成百分制评分；最后由 closeout 给出是否可交接的单一结论。这是一条从事实到判断的单向流水线，阅读者可以顺着构造器依赖逐层定位失败来源。

机械工作同时包括六条路由常量转移、十二处 SpotBugs 响应类型镜像迁移、保留控制器与五类外部 endpoint 读者的显式导入、三代历史边界门收紧、根包 census 收紧，以及 `UpkeepCatalog` 临时 public 面的偿还。直接根文件从 199 降到 187，仍需迁移的非控制器文件从 95 降到 83，RouteCleanup 桶从 91 降到 79，总 `ops` Java 文件保持 1352，未分类文件保持零。禁止硬凑工作量，因此每一项都有会失败的测试或可复现 census 对应，而不是只在文档中宣称完成。

## 入口路由

六个 HTTP 入口仍共享 `/api/v1/ops/shard-readiness` 基础路径，后缀分别是 `route-cleanup-maintenance-handoff-acceptance-digest`、`dependency-boundary-map`、`archive-retention-calendar`、`test-evidence-rollup`、`operations-scorecard` 和 `sustainment-closeout`。客户端看到的完整 URL、HTTP 方法和 JSON 字节语义不变。变化只发生在常量的所有权：后缀从根部巨型 `OpsShardReadinessRoutePaths` 移到短小的 `RouteCleanupRoutes`，控制器也改为从这个 family owner 读取基础路径和后缀。

根控制器保留有两个原因。第一，项目约定公开 Spring 控制器是根包的导航层，运维人员从 `ops` 目录即可找到外部入口。第二，控制器是适配器，不拥有业务判断。它只把六个 GET 请求分别转发给六个服务，不应因为内部包整理而改变公开 FQN 或 Spring 扫描行为。v1862 的 guard 会直接读取控制器源码，要求它包含新包导入和 `RouteCleanupRoutes.BASE_PATH`，同时拒绝继续使用旧的 `OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_*` 字段。

路由守卫不是只比较“能不能访问”。测试通过反射读取新 owner 的六个 public static final 字段，并逐字比较旧后缀；再扫描根路由文件，确认旧字段完全消失；还把该文件行数从 1001 继续压到 989 左右。这样既能防止 URI 漂移，也能防止同一字符串在两个 owner 中长期重复。若有人只复制常量而不删除旧 owner，测试会因全局字段仍存在而失败；若有人顺手改了连字符、复数或路径层级，字节断言会失败。

## 响应模型

六个 Response 都是不可变 record，它们没有在本版增删组件。`HandoffAcceptanceDigestResponse` 输出五个验收区段，每个区段携带名称、来源 endpoint、责任人、证据摘要和状态，并汇总接受数与阻断数。输入来自所有者登记、风险台账和新鲜度窗口；输出回答“谁负责、风险是否收口、证据是否过期、运行边界是否仍关闭、交接条目是否齐全”。只要任一区段不是 passed，最终摘要就是 blocked。

`DependencyBoundaryMapResponse` 把九个 Upkeep 条目映射成边界记录，字段包括服务名、owner、边界名称、来源 endpoint、允许范围和状态；同时附带明确禁止的操作集合。它不是运行时拓扑发现器，而是对已版本化事实做只读投影。`ArchiveRetentionCalendarResponse` 为同九条证据给出路径、路由版本、365 天保留期和每 20 个 Java 版本复核一次的节奏，并计算下一复核版本。它只生成日历，不创建、移动或删除任何归档文件。

`TestEvidenceRollupResponse` 输出五条测试证据，覆盖三项叶服务、共享路由契约和 MockMvc 集成契约。它记录测试类名与预期证据文本，但不会在 HTTP 请求中动态执行 Maven 测试。`OperationsScorecardResponse` 把交接、边界、归档和测试四个维度各赋 25 分，只有四项全部通过才得到 100 分。`SustainmentCloseoutResponse` 再把评分、控制器拆分、归档复核、测试覆盖和禁止执行五个条件组合成最终收尾结果。响应中的 `executionAllowed` 始终为 false，这不是缺功能，而是本阶段刻意保留的安全契约。

这些 record 在 SpotBugs 配置中各有两处已有镜像，用于允许不可变响应携带列表组件。v1862 只把十二条 FQN 从根包改到 `ops.maintenance.routecleanup`，不新增过滤规则，也不放宽 bug pattern。机械门要求每个新 FQN 恰好出现两次、旧 FQN 恰好为零，因此复制漏项、只改一半镜像或增加第三个豁免都会失败。

## 上游证据配置

这条流水线的第一类输入是 v1861 已经迁入同包的三项服务。OwnershipRegister 把九个维护条目映射到 owner 和边界；RiskLedger 提供五项低风险或已缓解风险，并明确执行仍不允许；FreshnessWindow 用最新路由版本 488 与各条目版本比较，最大容忍滞后为 20，当前没有过期条目。HandoffAcceptanceDigest 不重新实现这些判断，而是调用三项服务并引用它们的状态，从而保证“交接通过”能够追溯到具体来源。

第二类输入是 `UpkeepCatalog` 和 `RouteCleanupEvidenceAnalyzer`。目录包含从 segment-catalog 到 closeout 的九条版本化事实，每条都有 serviceVersion、routeVersion、endpoint、`e/...` 证据路径、consumer、boundary 和 passed 状态。依赖边界图只把这些字段转换为 reviewer 能读懂的边界条目；归档日历只计算统一保留期和下一复核版本。EvidenceAnalyzer 提供禁止操作集合，其中必须包含 write-routing 与 managed-audit-connection。换言之，配置的作用是描述允许读取什么、禁止执行什么，而不是提供连接凭据或真实写路由。

第三类输入是测试契约的静态清单。TestEvidenceRollup 明确列出行为测试和集成测试应证明的事实，例如验收区段数量为五、边界条目数量为九、归档条目数量为九、路由常量与服务 endpoint 一致、MockMvc JSON 契约存在。这种清单是交接证据索引，不是测试执行器。真实执行仍由 Maven 和 CI 完成，服务只把已经约定的测试面展示给 reviewer，避免线上 GET 请求产生构建副作用。

最后两层是纯组合。OperationsScorecard 只消费前四个结果并按固定权重计算分数；SustainmentCloseout 只消费评分、归档和测试结果，附上既有 `Node v549` 来源计划标识后给出最终状态。本版不会修改这个历史标识，也不会把它解释成 Node 运行时已经参与验证。它只是现有响应契约中的来源标签，真实跨项目联测由独立 capstone 负责。

## 服务层核心流程

第一步是交接摘要。服务调用 ownership、risk、freshness，构造 owner-coverage、risk-closure、evidence-freshness、runtime-boundary、handoff-readiness 五个区段。比如输入是 distinctOwnerCount 为若干、mitigatedRiskCount 为五、staleEvidenceCount 为零、executionAllowed 为 false，输出证据字符串会分别包含这些精确数字。服务再统计 passed 区段，只有 blocked 为零才返回 passed。它没有隐藏的数据库查询，事务注解明确为 readOnly。

第二步是边界图与归档日历并行形成两种视图。边界图遍历九条目录项，要求 owner 与 sourceEndpoint 非空、allowedScope 必须是 `read-only-evidence-preview`、禁止操作必须含写路由和托管审计连接。归档日历也遍历同一目录，要求每条路径以 `e/` 开头、保留期固定 365 天，并把 latestRouteVersion 488 加 20 得到下一复核版本 508。两者共享事实来源但承担不同判断，避免一个巨型方法同时处理权限、路径和时间策略。

第三步是测试证据汇总。五条静态证据都标记 covered，covered 数等于条目数时通过。这里刻意不读取 surefire XML，也不启动子进程，因为运行态 endpoint 不应拥有构建工具权限。第四步是评分卡。四个维度固定各 25 分，只有上游状态 passed 的维度计分；因此任一叶节点 blocked 都会把总分降到 100 以下，并使评分卡 blocked。这个设计让失败传播透明，reviewer 可以从低分维度直接跳回来源 endpoint。

第五步是最终 closeout。它检查 score 是否 100、归档下一复核版本是否已经形成、测试覆盖数是否完整、控制器拆分事实是否存在，以及 executionAllowed 是否仍为 false。第五项的逻辑容易误读：若 executionAllowed 为 true，closeout 项必须 blocked；只有不允许执行才 passed。最终条件是五项全 passed 且总分 100。整个调用图从叶证据到摘要再到 closeout 单向流动，没有服务反向调用上层，也没有循环依赖。

## Java 证据检查

Java 侧首先检查编译边界。十二个类型迁入后，保留根包里恰好六个生产 source reader，形成二十条 source-to-type edge，并覆盖十二个目标类型。六个 source 是 SustainmentEvidenceController、RuntimeBoundaryChecklist、ShardFieldMap、ContractFreeze、CiBudgetLedger 和 GateHandoff。guard 不是只数 import 行，而是扫描所有 Java 源中的完整类型词边界；源数、边数和目标集合任一改变都必须由新版本明确解释。

可见性检查更严格。HandoffAcceptanceDigest 的 ENDPOINT 没有外部常量读者，所以保持包内；DependencyBoundaryMap、ArchiveRetentionCalendar、TestEvidenceRollup、OperationsScorecard、SustainmentCloseout 的 ENDPOINT 只为已测量读者公开。所有 PROFILE 都保持包内。测试会逐个反射字段修饰符，并用外部 reader 扫描核对精确文件名。这样 public 不是“为了编译先全开”，而是一份最小、可追责的只读 API 清单。

本版还偿还 v1859 的临时设计债。`UpkeepCatalog` 当时必须 public，是因为后续 sustainment 服务仍在根包读取它；v1862 把最后两个外部消费者 DependencyBoundaryMap 和 ArchiveRetentionCalendar 移到同包后，生产源码中已没有外部读者。于是 catalog 类、三个静态查询方法和嵌套 Item record 都可以恢复包可见。v1859 守卫改用 `Class.forName` 反射验证，不再因为测试方便而制造生产 public 面。这个变化是优雅门的核心收益之一。

最后检查回归与静态分析。六个原行为测试及 fixture 随包迁移，根控制器的 MockMvc 集成测试保留；v1859、v1860、v1861、v1862 四代 guard 同时运行；根 census 必须是 187、movable 83、RouteCleanup 79、总量不超过 1352、unassigned 为零。Spotless 必须无差异，JaCoCo 阈值不得降低，SpotBugs 必须零新发现。测试失败时禁止改 fixture 字节、路由期望或 ratchet 来迁就实现。

## mini-kv 证据检查

mini-kv 在本版中是跨项目上下文的一部分，但不是被 Java endpoint 启动或控制的进程。Sustainment 证据链只读取 Java 仓库内已经版本化的目录、来源计划和归档路径，不连接 mini-kv TCP 服务，不执行 `minikv_cli`，也不读取凭据。这样做符合当前阶段的只读边界：Java 负责说明自己的证据如何被维护和审阅，真正读取 mini-kv 新鲜输出的工作属于跨项目 capstone，而不是塞进某个 GET 请求。

这一区分很重要。若文档把 `latest-sibling-report` 或来源计划标签写成“mini-kv 已现场通过”，就会把合同对齐误报成联机验证。正确解释是：目录中某些条目为 sibling consumer 提供来源和边界信息，Java 当前只证明这些条目的 owner、endpoint、evidencePath 和状态结构完整；它不证明外部二进制此刻可启动。讲解与响应必须维持这种诚实边界。

测试也会间接守住这一点。归档日历只验证 `e/` 路径和数值，不访问磁盘归档内容；依赖边界图要求 allowedScope 为只读预览，并要求 forbiddenOperations 包含写路由与托管审计连接；closeout 要求 executionAllowed 为 false。没有任何测试需要启动 Node 或 mini-kv，没有后台进程需要在任务结束时遗留。后续跨项目验收若需要真实 mini-kv 输出，应由独立命令产生新鲜报告，并与本版的内部维护证据分层展示。

## 阻断与安全边界

六个服务全部使用 `@Transactional(readOnly = true)`，返回模型把 `readOnly` 设为 true、`executionAllowed` 设为 false。这里的只读不只是一条注解，还由数据和状态传播共同约束：边界图允许范围固定为 evidence preview；归档日历声明不触碰归档文件；测试汇总声明不执行测试；评分卡只聚合上游结果；closeout 把任何允许执行的状态直接判为 blocked。即使未来有人误改其中一层，多个独立断言也会交叉发现。

禁止范围继续包含 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment、rollback，以及自动启动或停止 sibling 服务。本版不修改数据库实体、消息队列、订单写路径和 Spring profile。RouteCleanup 维护 endpoint 的作用是给 operator 和 reviewer 提供证据视图，不是变相的控制平面。任何需要副作用的能力都必须进入单独设计、审批和测试轨道，不能借“维护”名义混入。

包边界本身也是安全边界。迁移完成后，五个 public ENDPOINT 是不可变字符串，公开它们只为静态证据引用，不等于公开服务内部状态；Response 与 Service 类因 Spring 和控制器调用仍为 public，但 PROFILE、无外部读者的 endpoint、catalog 数据 owner 都尽量包内。三次规则同样生效：边界扫描和 walkthrough 统计继续复用既有短 helper，不创建第三套复制实现。新增 v1862 guard 控制在 400 行以内，避免治理代码自己成为维护负担。

## 测试覆盖

第一层是六个叶与组合服务的既有行为测试。Handoff 测试验证五个区段和全部通过；DependencyBoundaryMap 测试验证九个边界及禁止操作；ArchiveRetentionCalendar 测试验证九个条目、365 天和下一版本；TestEvidenceRollup 测试验证五条 covered 证据；OperationsScorecard 测试验证四个 25 分维度合计 100；SustainmentCloseout 测试验证五项收尾及 executionAllowed 为 false。共享 fixture 只为后两个组合测试构造同一依赖图，保持包内且不被生产代码引用。

第二层是控制器集成测试。它通过 Spring Boot 与 MockMvc 请求六条完整 URL，确认路由仍可达、JSON 字段和状态不变。控制器留根、服务迁包、路由 owner 迁移三件事必须同时正确才能通过，因此这层能捕获仅靠 service unit test 看不到的 Spring 注入或映射问题。根 `MaintenanceRoutePathsTests` 删除已由 family guard 接管的六项，避免两个测试继续争夺同一常量所有权。

第三层是 v1862 专属结构 guard。它检查十二个生产文件与七个测试文件的新位置和旧位置缺失，检查控制器只做适配，检查六条路由字节和全局 owner 删除，检查 6/20/12 生产边界，检查五个公开 endpoint 的精确 reader，检查 Handoff endpoint 与全部 PROFILE 为包内，检查十二条 SpotBugs FQN，检查 UpkeepCatalog 归还包可见，检查根 census 和中文讲解。历史 guard 还会验证读者迁移后的真实收缩：DependencyBoundaryMap 进入 EvidenceAnalyzer 同包后，v1857 从 38/76/21/34 降到 37/75/21/33；v1859 降到 2/11/10，v1860 保持 3/12/10，v1861 降到 4/13/10。

最终验证顺序保持可归因：先 main compile 用编译器找生产 import，再 test-compile 找保留测试 import；然后运行专属与历史 focused suite、执行 Spotless、运行完整 `mvnw verify`，最后推送并等待 GitHub Actions 的 headless 与 Docker 两个 job。讲解在最终 verify 前完成，汉字数量至少 3000 且恰好十个规定标题。若任一门失败，修实现或边界，不改测试期望和 fixture 字节来假装通过。

## 一句话总结

v1862 把交接摘要、依赖边界、归档日历、测试证据、运维评分和最终收尾整理成一个单向、只读、可机械验证的包内证据闭包，同时保留根控制器的清晰入口、收回不再需要的 public catalog、把根包压到 187 个文件，并用精确 reader、路由字节、响应镜像、历史边界和完整回归证明这次拆分既不改变外部合同，也真正提升了本项目后续维护时的可读性与安全性。
