# version-1859：维护目录核心迁移、公开面回收与依赖链收敛

本文在 v1859 的 Java 实现修改之前写成，用于固定本项目这一刀的真实输入、处理、输出和失败条件。上一版把九组基础维护报告迁入 routecleanup 包，本版继续处理消费这些报告的 upkeep 核心。这里的 upkeep 可以理解为“维护索引和审阅视图”，不是后台维护任务，更不执行修复动作。所有服务仍只读取内存目录和历史证据路径，输出不可变响应；包移动不代表部署，不代表跨项目联调，也不代表对 Node 或 mini-kv 做了任何写操作。

## 实际工作量说明

本项目禁止硬凑。v1859 的范围来自 MaintenanceUpkeepController 的编译依赖闭包。控制器直接注入五个服务：UpkeepCatalogService、ConsumerHandoffMatrixService、CiExpectationManifestService、RouteTopologyIndexService 与 FailClosedPolicyService。每个服务有一个响应类型，共十个文件。五个服务共同读取 OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog；这个目录再读取私有 Seeds，所以再加入目录和 Seeds 两个文件，闭包总数正好十二。递归扫描证明它们不依赖其他尚留根包的 RouteCleanup 实现，只依赖 Java、Spring 和 v1857/v1858 已在 routecleanup 包中的证据分析器与九组维护服务。

五个直接行为测试随实现迁移。控制器继续留根，因为它是 HTTP 组合入口；其余十三个根包生产读者继续通过窄公开边界消费目录、服务或响应。十二个生产文件移走后，direct-root 从二百三十一降到二百一十九，可迁移 backlog 从一百二十七降到一百一十五，RouteCleanup bucket 从一百二十三降到一百一十一，递归 ops 生产文件总数仍是一千三百五十二。没有增加生产包装类型，也没有把两个文件合成难维护巨类。

本版还偿还 v1858 的临时公开面。上一版为了让根包 Seeds 读取九个基础服务端点，把九个 ENDPOINT 提升为 public static final。v1859 把 Seeds 移到同包后，生产源码中不再存在包外读者，因此九个字段应全部回收为 package-private。公开性不是一次放大后永不回头的债务；调用图改变时应重新测量并收窄。与此相对，本版 FailClosedPolicyService.ENDPOINT 仍被根包 ShardFieldMapService 读取，所以它必须保持公共不可变。这样的逐字段决策比把五个 service 的所有常量一律公开更可靠。

## 入口路由

外部输入仍是五个 GET 请求，基础路径保持 /api/v1/ops/shard-readiness。后缀依次为 /route-cleanup-maintenance-upkeep-catalog、/route-cleanup-maintenance-consumer-handoff-matrix、/route-cleanup-maintenance-ci-expectation-manifest、/route-cleanup-maintenance-route-topology-index 和 /route-cleanup-maintenance-fail-closed-policy。没有请求体、查询参数或凭据参数，也没有 POST、PUT、PATCH、DELETE。

过去五个后缀定义在全局 OpsShardReadinessRoutePaths；本版把它们移动到 RouteCleanupRoutes，字段名分别是 MAINTENANCE_UPKEEP_CATALOG、MAINTENANCE_CONSUMER_HANDOFF_MATRIX、MAINTENANCE_CI_EXPECTATION_MANIFEST、MAINTENANCE_ROUTE_TOPOLOGY_INDEX 和 MAINTENANCE_FAIL_CLOSED_POLICY。每个新标识符不超过四十字符。控制器直接引用家庭路由所有者，五个服务用相同 BASE_PATH 与后缀组成端点，全局表删除重复常量。

一次请求的机理仍是四步。客户端给 Spring 一个固定 GET 路径；根控制器选择方法并调用对应 service；service 从 UpkeepCatalog 或 v1857 EvidenceAnalyzer 读取不可变输入，生成响应；Jackson 返回原有 JSON。控制器不计算版本、不读取文件、不决定 passed 或 blocked。路由测试逐字节比对五个后缀，EndpointManifestService 继续合并全局表和 RouteCleanupRoutes，并保留原有标准化名称，因此代码所有权移动不会改变运维端点清单。

## 响应模型

UpkeepCatalogResponse 是九项基础维护报告的总目录。每项包含名称、service 版本、route 版本、完整 endpoint、版本化 evidence 路径、消费者、边界和状态。响应还给出首个 service 版本、最后一个 route 版本、检查项和总体状态。ConsumerHandoffMatrixResponse 把同一目录投影为消费者交接矩阵，并附上禁止操作数。CiExpectationManifestResponse 为每一项给出聚焦测试类、路由回归套件、完整回归命令与 CI job 名称，但它只是描述命令，不在请求期间执行命令。

RouteTopologyIndexResponse 按目录顺序生成九个 RouteNode，每个节点保留前驱端点、后继端点、版本和 evidence 路径。FailClosedPolicyResponse 则把 EvidenceAnalyzer 的七类禁止操作映射为 PolicyCheck，逐项统计违规数；全部为零才返回 passed。五种响应都继续包含项目、Java 版本、readOnly=true、executionAllowed=false、endpoint、profile、事实集合、checks 和 status。

迁包只改变 Java 全限定名，不改变响应组件或序列化值。SpotBugs 中五个响应各有两条 EI_EXPOSE 镜像，总数十条。本版把旧根包 FQN 精确改成 routecleanup FQN，旧计数归零、新计数精确为十，既不增加豁免也不删除既有保护。目录 Item 仍是不可变 record，列表由 List.of 返回；对外公开 record 和只读查询方法不等于公开可变存储。

## 上游证据配置

UpkeepCatalogSeeds 是本版最底层输入。它固定九个条目，对应 v471 到 v488 的 service/route 配对：segment-catalog、continuity、latest-sibling-report、handoff-pair-audit、boundary-drift、source-plan-alignment、test-budget-plan、archive-manifest 和 closeout。每项保存 e/<version>/evidence/*.json 路径以及消费者和边界名称。这些是历史证据索引，v1859 不移动 e 目录、不重写 JSON、不替换版本号、不重新生成 digest。

UpkeepCatalog 只提供 items、firstServiceVersion 和 latestRouteVersion 三个查询，以及不可变 Item record。六个根包服务直接读取目录：ArchiveDigestLedger、ArchiveRetentionCalendar、DependencyBoundaryMap、FreshnessWindow、OwnershipRegister 与 VersionLineage。另有 OperatorReviewPacket、ReleaseChecklist 和 UpkeepCloseout 通过 UpkeepCatalogService 读取响应。全量测量得到十三个根包生产读者、三十七条类型边，所以目录公开面必须足以让这些读者编译，但 Seeds 仍可私有，目录内部的组装方法也不需要成为公共 API。

举例来说，RouteTopologyIndexService 的输入是九项 Item。它按索引生成节点，第一个 previousEndpoint 为 none，最后一个 nextEndpoint 为 none，中间节点分别指向相邻端点；输出 latestRouteVersion 必须是四百八十八。整个过程不访问网络，只在内存中遍历 List。FailClosedPolicyService 的输入则是 EvidenceAnalyzer 提供的禁止操作和证据条目，它对 write-routing、active-shard-router、credential-value-read、raw-endpoint-parse、managed-audit-connection、deployment-or-rollback、node-start-or-stop-java-or-mini-kv 逐类统计，任何非零都会阻断。

## 服务层核心流程

UpkeepCatalogService 把内部 Item 映射为外部 UpkeepItem，并验证九项数量、service/route 相差一、evidence 路径以 json 结尾以及状态全为 passed。ConsumerHandoffMatrixService 把每项转换成 consumer、boundary、sourceEndpoint 和 reviewAction，同时从 EvidenceAnalyzer 读取禁止操作列表，确保托管审计连接仍在禁止集合。CiExpectationManifestService 根据条目名称生成聚焦测试类名，固定路由回归套件和 Maven 回归命令，并明确 startsJavaService=false、startsMiniKvService=false。

RouteTopologyIndexService 使用 IntStream 生成相邻关系，检查版本升序、邻居明确和端点基础路径。FailClosedPolicyService 对证据条目做零违规计数，并把每类操作映射为 fail-closed-before-* guard。五个服务都使用 Transactional(readOnly = true)，没有 repository save、消息发布、文件写入或进程启动。

这五个投影不会互相复制目录事实。目录项只在 Seeds 定义一次，Catalog 负责查询，service 负责从同一事实生成不同读者需要的视图。若某个历史端点或 evidence 路径需要纠正，评审者能沿 Seeds 到 Catalog 再到五个响应追踪唯一来源，而不必在五份服务常量中寻找不一致副本。相反，状态计算仍留在各自 service，因为目录完整、交接完整、拓扑连续和零违规是四种不同的行为判断，强塞进数据类会模糊职责。

根控制器的职责保持最薄：构造器注入五个 service，五个方法各返回一个 response。迁移后依赖方向是 root controller -> routecleanup implementation，routecleanup 不反向 import 根包 RouteCleanup 实现。十三个保留根读者也只消费实测需要的目录、service 或 response。编译器会指导哪些类型、构造器和方法需要 public；ENDPOINT 与 PROFILE 单独按常量读者判断，不能因为类 public 就把字段全部 public。

这里最容易被忽略的工作不是移动文件，而是偿还上一刀留下的临时可见性债务。v1858 迁移维护归档服务时，UpkeepCatalogSeeds 仍在根包，因此它必须跨包读取九个已迁服务的 ENDPOINT；那些字段当时临时公开是编译所需，并不代表它们已经成为稳定扩展点。v1859 把 Seeds、Catalog 与五个核心投影一起放回 routecleanup 家庭后，九个旧 ENDPOINT 的最后一批包外读者消失，守卫便要求它们恢复为包内可见。新的五个 ENDPOINT 也逐项测量：目录、交接、持续集成期望和拓扑端点都没有包外生产读者，所以保持包内可见；只有失败关闭策略仍被根包的分片字段映射服务读取，因而保留公开。这个判断以实际源码引用为依据，不以类名、习惯或“以后可能有用”为依据。

这种做法把迁包从目录整理提升为边界重建。若一律公开，编译虽然容易通过，却会把二十多个内部常量永久暴露给后续代码，任何改名都会被误判为兼容性破坏；若一律私有，现有根包读者又会被迫复制数据或绕过服务。现在的结果是：控制器只承担 HTTP 适配，routecleanup 包拥有维护事实和投影行为，根包读者只获得确实消费的最小表面。未来再迁移这些读者时，公开面还能继续收缩，而且每一次收缩都由读者清单和反射守卫证明，不依赖维护者记忆。

## Java 证据检查

第一组机械证据锁定十二个生产文件和五个测试文件。守卫断言它们存在于 routecleanup 包、旧根路径不存在，并扫描每个移动文件禁止 import 根包 RouteCleanup 实现。控制器必须导入新包且使用 RouteCleanupRoutes，五个旧全局字段必须消失。家庭路由字段通过反射检查 public static final、名称长度和字节值。

第二组证据锁定边界。守卫重算十二类型的包外生产读者，必须是十三个来源、三十七条类型边。UpkeepCatalog 的类、Item record 和三个查询方法只开放到编译所需范围；Seeds 保持 package-private。五个 PROFILE 都不得 public。FailClosedPolicyService.ENDPOINT 的唯一外部生产读者必须是 ShardFieldMapService；其余四个新 service ENDPOINT 不应有包外生产读者。v1858 九个 service ENDPOINT 也必须没有包外生产读者并恢复 package-private。

第三组证据锁定质量与规模。SpotBugs 新 FQN 十条、旧 FQN 零条；direct-root 精确二百一十九；递归 ops 不超过一千三百五十二；endgame census 写明当前二百一十九、可迁移一百一十五、RouteCleanup 一百一十一和 v1859 progress；未分类文件必须为空。共享 OpsExtractionTestSupport 继续提供读取、列举、计数和标题统计，v1859 测试不复制 helper。

验证顺序也被刻意固定：先让编译器暴露真实包外依赖，再运行聚焦守卫确认文件、路由和可见性，最后才执行全量构建。这样可以区分“迁移闭包漏项”和“历史回归”两类问题，避免在庞大的测试输出里猜测原因。若聚焦门失败，修复对象必须是遗漏的依赖、错误的归属或不足的解释；不得删除断言、降低数量下限，也不得改写历史证据来制造通过。全量构建只在这些局部事实已经稳定后运行，因此它承担的是系统回归证明，而不是替代边界分析。

## mini-kv 证据检查

本版没有启动 mini-kv，没有执行 minikv_cli，也没有读取实时 shard 或 slot 状态。CiExpectationManifest 中的 startsMiniKvService=false 是静态契约字段；FailClosedPolicy 将 node-start-or-stop-java-or-mini-kv 视为禁止操作。它们证明 Java 服务的设计会拒绝启动上游，不证明 C++ 进程在本轮接受过请求。

Seeds 中若出现 mini-kv、Node 或源计划字样，也只是历史证据条目的消费者和边界文本。v1859 不进入 D:\C\mini-kv，不改其 WAL、snapshot、e 归档或测试，不改 Node 对 Java/C++ 归档的绝对路径。真正的跨项目 capstone 仍需真实 Java jar、真实 minikv_cli 和统一 cross report；本版结论只能是 Java 单仓门通过与静态契约保持。

这种边界使项目可以并行维护。如果 upkeep 请求自动启动 mini-kv 或读取凭据，它就从只读审阅接口变成执行器，权限、失败恢复和审计模型都必须重做。本版明确不打开这条路径，所有启动、连接、写路由和部署行为继续由零违规守卫阻断。

## 阻断与安全边界

安全条件仍是 readOnly=true、executionAllowed=false、零凭据值读取、零原始端点解析、零托管审计连接、零写路由修改、零 Java/mini-kv 启停。五个控制器映射都是 GET，五个 service 都不接受执行参数。所谓 remediation、CI expectation、policy 和 topology 都是报告或计划，不会执行修复、测试、部署或回滚。

以下任一事实出现必须阻断：十二文件闭包不完整；路由字节变化；响应字段变化；目录条目、版本或 evidence 路径变化；新包反向依赖根实现；无读者的 ENDPOINT 继续 public；有读者的 FailClosed ENDPOINT 被隐藏；SpotBugs 镜像增减；根计数不等于二百一十九；总量超过一千三百五十二；为了让测试通过修改历史 fixture 或期望；中文讲解少于三千汉字。修复只能针对真实根因，不能放宽 ratchet。

公开 UpkeepCatalog 也不等于允许修改目录。类仍是 final，无公共构造器；items 返回不可变 List；Item 是只读 record；Seeds 私有。根服务只能读取和投影，不能添加、删除或替换条目。若未来需要可变维护配置，应单独设计配置来源、校验和审计，而不是在本次迁包里偷偷引入。

## 测试覆盖

聚焦验证先跑 test-compile，发现所有 package-private 和 import 边界。随后运行五个移动服务测试、UpkeepController 相关集成测试、十三个读者中直接构造候选类型的测试、MaintenanceRoutePaths、OpsShardReadinessRoutePaths、EndpointManifest、v1858/v1859 版本守卫和 EndgameCensus。这样既覆盖服务算法和 HTTP 入口，也覆盖后续服务的编译消费关系。

Spotless 检查格式和 import 顺序；SpotBugs 检查迁包后的响应镜像；JaCoCo 保持原有覆盖率门。完整 mvnw verify 在设计、讲解、代码、守卫、索引和账本全部完成后运行。测试不改 fixture 字节、不降低断言、不跳过失败。长验证期间只读准备下一闭包，不修改当前提交。

收口继续采用两提交模式。实现提交推送后由 GitHub Actions 运行 Docker 与 headless job；真实成功后只回填 J69 的 commit 和 run，再提交 closeout、创建注解 tag 并推送。下一版开头核对上一版 closeout CI。v1859 不到五版检查点，因此成功后继续 v1860；v1862 完成后再停下等待外部评审。

## 一句话总结

v1859 的输入是九项不可变维护目录、v1857 的证据分析器、v1858 的九组基础维护端点和五条固定 GET 路由，处理是把十二文件 Upkeep 闭包与五个行为测试迁入 routecleanup、把五条路由交给家庭所有者、按十三个读者三十七条类型边建立最小公开面，并回收 v1858 已失去包外读者的九个公共端点，输出是字节不变的五类只读响应、二百一十九文件根目录、一百一十五文件 backlog 和更窄的生产 API；它没有执行维护、没有启动兄弟项目、没有改历史证据，却让目录数据、服务行为、HTTP 入口和测试机械动作各归其位。
