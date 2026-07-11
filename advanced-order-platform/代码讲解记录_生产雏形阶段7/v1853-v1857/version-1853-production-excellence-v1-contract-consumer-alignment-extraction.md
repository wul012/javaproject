# version-1853：V1Contract 消费者与对齐证据闭包拆分讲解

这篇讲解只讨论本项目 Java 端 v1853 的真实改动。它不是把文件换一个目录后再复述文件名，而是说明一条已经运行多年的只读契约链为什么能整体迁移、每一步接收什么输入、产出什么输出、哪些历史字节必须冻结，以及编译器、测试和 census 如何共同证明迁移没有改变业务行为。全文禁止硬凑；如果一个结论不能对应到类、方法、路由或机械检查，就不把它写成完成事实。

## 实际工作量说明

v1853 处理的是当前根包中完整的 V1Contract 家族。迁移前，根目录里共有四十三个 `OpsShardReadinessV1Contract*` 生产文件，其中一个是 Spring Controller，另外四十二个是契约工具、十一组 Service/Response、十一类历史 Snapshot、endpoint pair 注册表以及交接后的收据分段帮助类。按照本项目既定规则，Controller 是 Web 适配器，留在根包；其余四十二个实现文件整体进入 `ops.maintenance.v1contract`。测试侧原有一百零一个同名前缀文件，其中九十八个验证服务、快照、历史兼容、收据、归档和只读边界，随实现迁移；ControllerSplit、ControllerMapping 和 RouteInventory 三个结构测试继续留根。

这不是小粒度搬运。生产文件与测试文件合计一百四十个发生物理归属变化，同时还要修正根 Controller、全局 evidence registry、Prototype 消费者、历史兼容测试、RoutePaths 测试以及 SpotBugs 排除项的导入。真正困难的地方不在 `package` 这一行，而在原来同包可见的关系被拆开后，哪些关系应成为正式公开边界，哪些关系只能通过 test-source support 访问。v1853 明确选择：十一组 Web 返回模型和服务可以公开；历史 Snapshot、收据分段、证据路径目录仍然包私有；跨时代测试只能通过窄转发器读取它实际需要的值。

迁移后的直接根文件数从四百七十一降到四百二十九，可移动 backlog 从三百六十六降到三百二十四，V1Contract census bucket 从四十二清零。保留根数量仍是固定的一百零五，总 `ops` 生产 Java 文件仍是 一千三百五十二，未归类文件仍为零。这四组数字同时约束“确实搬走”“没有新增膨胀”“没有把文件藏出 census”“最终目标没有放松”。

## 入口路由

V1Contract 对外有十一条 GET 路由，分别覆盖对齐结果、对齐交接、证据包、操作员检查表、交接清单、消费者探测计划、端点目录、消费者交接包、消费者验证清单、消费者证据摘要和消费者就绪交接。请求先命中根包中的 `OpsShardReadinessV1ContractController`。Controller 自身不计算状态，只持有十一项 Service 依赖，并把每个 `@GetMapping` 方法直接委托给对应服务。输入是一个无请求体的 HTTP GET；输出是对应的不可变 Response record。没有 POST、PUT、DELETE，也没有从请求中读取凭据、原始端点或执行参数。

路由由 `OpsShardReadinessRoutePaths.BASE_PATH` 与十一条 `V1_CONTRACT_*` 后缀拼接。迁移前这些后缀就在全局 RoutePaths 中，迁移后文字完全相同，只把 Java 可见性改成 `public static final`，让子包服务能够合法引用。这里“公开”只意味着编译期可见，不意味着新增 HTTP 面。比如 alignment 的实际地址仍由 `"/api/v1/ops/shard-readiness" + "/v1-contract-alignment"` 得到；字符、斜杠和大小写都没有变化。Controller 的三个根侧结构测试分别检查路由集合、构造器依赖顺序和 endpoint pair 对齐，所以导入调整无法掩盖少路由、重路由或错序。

为什么不新建一个额外 RoutePaths 类？因为当前总文件数有不增反降的 ratchet，而且这十一条后缀已经由全局聚合器稳定拥有。另建一类会引入没有行为价值的新文件，还要人为合并别的类型才能抵消计数。v1853 沿用 v1852 已验证的模式：保留原所有者，只公开不可变后缀。这比为了形式上的“每族一个路由类”制造额外结构更诚实。

## 响应模型

十一种 Response 都是 Java record。它们承载的不是可变实体，而是某个历史版本冻结后的只读证据视图。例如 AlignmentResponse 描述 Java 输出是否满足 `shard-readiness.v1` 的最小字段；EvidencePacketResponse 把上游对齐、证据链和可供 Node 消费的只读端点编成一个包；ConsumerReadinessHandoffResponse 则汇总证据摘要、验证清单、阻断操作、归档收据和最终状态。record 的构造参数顺序就是序列化字段顺序的重要来源，因此 v1853 没有改 record 声明、字段名称、字段类型或构造调用顺序。

输入到响应构造的值来自三类位置。第一类是稳定常量，例如 `ENDPOINT`、`FIXTURE_ENDPOINT`、`EVIDENCE_PATH` 和历史证据路径；第二类是前一阶段 Response，例如验证清单读取交接包，证据摘要读取验证清单，就绪交接读取证据摘要；第三类是 Snapshot 返回的不可变 `List<String>`，其中记录 required evidence、blocked operations、verification checks 和 receipt ids。输出状态通常由这些输入的版本、端点、数量和只读标志共同决定，任何必要条件不满足都产生 `blocked`，而不是补默认值后继续声明 `passed`。

SpotBugs 对包含 List 的 record 会报告 EI_EXPOSE_REP 或 EI_EXPOSE_REP2。项目已有二十二条精确 FQN 排除，分别覆盖十一种 Response 的两类告警。v1853 只把 FQN 从根包改为 `ops.maintenance.v1contract`，既没有增加模式级豁免，也没有删除 SpotBugs 检查。这样历史基线随类型移动，排除范围仍精确到具体 record。

## 上游证据配置

整条链最早读取 Java v153 的 `OpsShardReadinessResponse`，验证 `project`、`version`、`readOnly`、`executionAllowed`、`shardEnabled`、`shardCount`、`slotCount`、`routingMode`、`evidencePath` 和 `status` 十个最小字段。`OpsShardReadinessV1Contract` 保存契约名 `shard-readiness.v1` 与这十个字段，并用 `alignsWithReadOnlyContract` 明确检查：必须只读、禁止执行、分片未启用、计数为零、路由模式是 fixture、状态为 passed。Prototype 家族仍需要这项判断，因此该工具类成为公开 final 类，但内部字段列表仍是 private immutable list。

第二类上游是全局 `OpsShardReadinessEvidenceEndpoints`。它维护当前可读 live endpoint、fixture endpoint 及 GET probe 的有序注册表。迁移前 V1Contract endpoint pair 帮助类借用了根 registry 的内部 `EndpointPair`；跨包后继续这样做会迫使根私有实现向外公开。v1853 改为 V1Contract 自己定义不可变 `EndpointPair` record，根 registry 读取后映射成自己的私有 pair。输入仍是十一组 live/fixture 字符串，输出顺序也不变，但依赖方向从“子包借根内部类型”变成“根组合公开族边界”。

第三类上游是历史 archive 和 fixture 路径。它们对应 v187、v190、v193、v196、v199、v202、v208、v211、v215、v220、v225 以及随后 post-handoff 版本。路径是证据身份的一部分，不能因为包名改变而重写。服务中五个需要被根历史兼容测试读取的附加证据路径被改为公开不可变字符串；其值没有改变。其余只在族内使用的路径继续留在 package-private helper 中。

## 服务层核心流程

服务链可以按“读取、组装、收紧”理解。AlignmentService 先取得冻结的 v187 源 readiness，把十字段契约与当前端点一起输出。AlignmentHandoffService 读取 alignment，附加历史快照保护和交接证据。EvidencePacketService 再把 alignment handoff 转成 Node 可消费的 endpoint 与 fixture 集合。OperatorChecklistService 对证据包给出人工检查项目和 Java/Node 职责。HandoffManifestService 把检查表变成可交付目录。ConsumerProbePlanService 从目录生成只读 GET 顺序与停止条件。

链的后半段面向消费闭环。EndpointCatalogService 把已有端点登记为有序目录；ConsumerHandoffBundleService 把目录、历史冻结证据和必须保留的操作边界打包；ConsumerVerificationChecklistService 明确消费者必须核对的条目；ConsumerEvidenceDigestService 把清单压成摘要；ConsumerReadinessHandoffService 最后汇总摘要、收据和 post-handoff catalog，只有所有来源都一致才返回 passed。每一步输入都是上一阶段不可变 Response 或 Snapshot，输出也是不可变 Response，不访问数据库，不写消息队列，不创建部署任务。

post-handoff 的多个 Receipts 类看上去文件多，但职责清楚：Seed、Growth、Archive、Completion 分别持有不同版本段的冻结收据，ReceiptSegments 只负责组合分段，EvidenceCatalog 只负责形成统一目录。它们不是公共 API，迁移后仍是 final package-private class。保留这种私有性可以阻止其他包绕过 Service 直接依赖某一历史分段，后续若收据继续增长，也只影响同一个维护边界。

## Java 证据检查

Java 侧首先用 `test-compile` 检查包边界。首轮编译发现五个收据文件仍静态导入旧 FQN，统一改为新包后，主源码通过。测试编译随后发现两处真实的隐式同包依赖：迁移测试直接访问根私有 evidence registry，根历史测试直接访问六个私有 Snapshot。v1853 没有为了省事把这些生产类型全部 public，而是新增两个 test-source support。`OpsShardReadinessEvidenceEndpointsTestSupport` 只转发 live、fixture、live probe、fixture probe 四个不可变列表；`OpsShardReadinessV1ContractTestSupport` 只转发历史测试实际使用的十七个 Snapshot 方法。

这种处理让编译器成为设计工具。若测试需要整个类，就可能说明边界过宽；若只需要少数稳定读值，就应通过测试支持暴露最小接口。最终 `test-compile` 同时编译全部主源码和测试源码，证明 Controller 能注入新包服务、Prototype 能读取公开契约、历史测试不再穿透 Snapshot、迁移测试也不再要求根 registry 公开。

版本机械门还检查四十二个生产文件全部位于目标包，根同前缀只剩 Controller；目标测试包有九十九个文件，其中九十八个为迁移测试，一个为 V1Contract TestSupport；根同前缀只剩三个结构测试。它逐一验证十一组 Service/Response 公开、所有 Snapshot 非 public、十一条路由后缀公开、二十二个 SpotBugs FQN 已迁移，以及根计数恰好为四百二十九。

## mini-kv 证据检查

V1Contract 并不在运行时启动或连接 mini-kv。这里的 mini-kv 证据检查是消费契约检查，不是伪装成集成测试的网络调用。ProbePlan、OperatorChecklist 和 ReadinessHandoff 中保存的 mini-kv 相关条目，只描述允许读取哪些冻结 fixture、需要核对哪些版本与 receipt、遇到何种不一致必须停止。输入是已归档路径、端点字符串和只读标志，输出是检查项目与停止条件。

这种设计有两个价值。第一，Java 独立构建不依赖本机是否恰好运行 mini-kv，不会把环境偶然性写进单仓验证。第二，真正的跨项目运行由 Node capstone 在 clean Java HEAD 上负责；上一检查点已独立验证 health、ops evidence read 和未认证写请求拒绝。v1853 不修改这些外部路径与边界，因此不会暗中把“契约对齐”夸大为“本版重新启动了所有系统”。单仓证据与联合证据的责任分开，结论才可复现。

## 阻断与安全边界

这十一条路由全部是 GET，服务全部生成只读证据。响应中的 `executionAllowed`、`writeOperationsAllowed`、`activeShardPrototypeEnabled`、`credentialValueRead`、`rawEndpointUrlParsed`、`connectsManagedAudit` 以及启动停止 Java/mini-kv 的标志都按历史契约保持 false。blocked operations 明确包含 write routing、active shard router、credential value read、raw endpoint parse、managed audit connection、deployment/rollback 和进程启停。

失败关闭不是文档口号。各 Snapshot 和 Service 在构造状态时会比较来源状态、版本、端点、列表数量、顺序、只读标志和证据存在性；条件不齐返回 blocked。测试覆盖端点相邻关系、GET-only probe、fixture parity、旧快照不回填、receipt id 唯一、scope 唯一、归档字节下限、JSON 元数据、README/讲解索引以及禁止写路由。v1853 只改变 Java 包归属，不改这些判断分支和期望值。

最强的评审质疑是：一次迁移一百四十个代码/测试文件，是否可能靠大范围 public 化掩盖依赖问题。回应是可机械复核的：十一种 Snapshot 仍不是 public；五类 post-handoff helper 仍不是 public；根 EvidenceEndpoints 仍不是 public；只有 Web 调用边界、不可变契约工具、不可变 endpoint pair 和冻结字符串公开；历史访问全部收口到两个测试支持类。若有人以后把 Snapshot 改为 public，v1853 机械门会立即失败。

## 测试覆盖

验证顺序遵守“讲解先于最终 verify”。先运行 test-compile 让编译器暴露访问边界，再运行 V1Contract 全族测试、v1853 结构门、endgame census、RoutePaths、历史 endpoint compatibility 等 focused tests；之后运行 Spotless 检查，最后才运行完整 `mvnw verify`。全量门会串联单元测试、Spring 上下文测试、JaCoCo floor、SpotBugs 零新增和 Spotless。任何一步失败都必须修实现或边界，不能提高计数阈值、删除测试、改 fixture 字节或扩大排除模式。

census 通过 `scripts/ops-root-census.ps1 -Json` 复算，不依赖进度表自述。它列出根文件四百二十九、保留一百零五、待迁移三百二十四、V1Contract 为零、未归类为零。历史 guard 中的 live root pin 同步收紧到四百二十九，但每个历史版本文档中的“当时从多少降到多少”保持不变。这一区分很重要：历史叙述冻结，当前防回退阈值跟随最新树继续下降。

首轮 full verify 实际执行一千七百八十五个测试，其中一千七百八十四个通过。唯一失败不是业务断言，而是既有的命名热点预算：十一条后缀加上 public 后，格式化器把 RoutePaths 从一千一百一十一行推到一千一百一十二行。这里没有把预算调大，而是删除 V1Contract 与 Prototype 路由分组之间一个无语义空行，所有路由文字保持不变；随后先单独复验热点预算和路由，再重跑完整门。这说明文件行数预算在本项目里确实能阻止“每次只多一点”的缓慢回退。

提交闭环仍分为实现提交和收尾提交。实现提交必须先通过本地完整 verify，推送后产生远端 CI；收尾提交写入真实 commit、tag、CI run id，打 `v1853` 描述性标签并再次推送。只有实现与收尾两次远端 Actions 都绿，版本才完成，下一批才能开始。这样输入是确定的 clean HEAD，输出是可由 Claude 重新 checkout、重跑 census 与 verify 的审查基线。

## 一句话总结

v1853 把 V1Contract 从根包里的四十二个只读实现文件收束成一个边界清晰的维护包：HTTP 输入与十一种响应输出不变，历史 Snapshot 和收据仍私有，根 registry 与族内快照通过窄测试支持被验证，路由、fixture、证据和安全阻断字节保持原样，同时把直接根从四百七十一实打实降到四百二十九。
