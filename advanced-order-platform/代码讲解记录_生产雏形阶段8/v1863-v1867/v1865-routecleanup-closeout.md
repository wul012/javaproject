# v1865 RouteCleanup 后完成图收口代码讲解

## 实际工作量说明

这一版不是把若干文件换个目录，而是关闭一个长期存在的依赖图。迁移对象包括二十二个服务、二十二个响应模型、二十二个归属单元测试以及两个共享测试夹具，共六十八个物理文件；同时保留 Assurance、Completion、Governance、PostCompletion 四个 Spring 控制器作为根包入口。迁移前，根包还剩一百五十二个 Java 文件，其中四十八个不属于最终保留面，RouteCleanup 后完成图占四十四个。迁移后，根包精确降到一百零八个，可移动项只剩 OpsOverview 两个类型与静态发布证据两个类型，RouteCleanup 根残留归零。选择一次搬完四十四个类型，是因为它们已经形成闭合后继链：AuditTrail 被 AcceptanceReceipt 消费，随后进入 EvidenceRegister、OperationalSnapshot、PolicyGuard、ReviewerPacket、TransitionBrief、FinalVerification、FinalArchivePlan，再经过完成索引、证书、推送收口、CI 证明、标签清单、发布证据包、消费者签收、归档交接、维护边界、夹具覆盖、完成摘要，最后汇入 PostCompletionCloseout。若在链中间切开，就必须临时公开更多字段并制造包到根实现的反向边；整体迁移反而更小、更透明，也能一次偿还前几版为过渡而放宽的可见性。本文只解释本项目真实发生的结构变化，禁止硬凑无关背景或重复句子来满足篇幅。

## 入口路由

HTTP 入口仍然由根包四个控制器负责，外部路径、请求方法和返回状态都不变。变化发生在路由所有权：原先最后二十二个 `ROUTE_CLEANUP_*` 常量仍放在全局 `OpsShardReadinessRoutePaths`，而已经迁出的六十二条后缀位于 `RouteCleanupRoutes`。这种双 owner 状态在迁移期有价值，却不应成为最终结构。因此本版把 AuditTrail 到 PostCompletionCloseout 的二十二条后缀逐字节移入 `RouteCleanupRoutes`，控制器改为导入该 owner，基础路径仍是 `/api/v1/ops/shard-readiness`。全局聚合器不再含任何 `ROUTE_CLEANUP_` 字段，RouteCleanup 自己拥有八十四条完整后缀。这里的“移入”不等于重新拼写：每个字符串都由精确映射测试校验，完整端点仍由同一个基础路径与同一个后缀拼成。EndpointManifest 也随所有权收敛而简化。旧实现反射全局 owner 和家族 owner，再按声明类决定是否补 `ROUTE_CLEANUP_` 前缀；新实现只反射家族 owner，排除 `BASE_PATH` 后统一生成兼容名称。清单条目仍精确为八十四，名称、相对路径、完整路径、只读标记和执行禁止标记都保持不变。根控制器只知道公开路由常量与服务边界，包内服务不再反向依赖根路由聚合器，方向由“入口依赖实现”单向成立。

## 响应模型

二十二个响应类型继续使用 Java record 表达不可变传输数据。包名变化不会改变 record component 的名称、顺序、类型或 Jackson 序列化结果，因此 HTTP JSON 的字段集合和字节语义保持稳定。迁移脚本只修改 package 声明，不改构造参数；随后生产编译和原有服务测试验证每个服务仍按原顺序创建响应。SpotBugs 基线中，二十二个响应各有两条成对的 `EI_EXPOSE_REP` 或 `EI_EXPOSE_REP2` 历史镜像。本版没有增加排除项，也没有删除检查，而是把四十四个旧 FQN 精确替换成新包 FQN，并由结构测试要求新位置恰好出现两次、旧位置恰好为零。这样既保留“存量基线只减不增”的规则，又避免类移动后基线失效。此前 ContinuityReport 等已迁响应的不可变性做法继续生效；本批响应中的列表构造和访问行为由原测试保持。更重要的是，控制器所需的服务与响应类型仍为 public，因为它们构成真实的包边界；证据 catalog、分析器、端点字段和 profile 字段并不是 HTTP 类型，不应跟着公开。类型可见性按调用者而不是按迁移便利性决定，避免把整个包误当成公共 SDK。

## 上游证据配置

RouteCleanup 的内容来自一条只读证据目录，而不是运行时执行器。`OpsShardReadinessRouteCleanupEvidenceAnalyzer` 汇总 latest-sibling、readiness-seed、handoff-core、handoff-assurance、handoff-governance 与 post-completion 六段目录，计算最新 Java 版本、版本连续性、只读边界和禁止操作集合。迁移前，仍在根包的后完成服务直接读取分析器与 latest-sibling catalog，因此两个类型曾被临时设为 public。本版所有读者都进入同一包后，独立扫描确认包外生产读者为零，于是分析器类、九个静态查询方法、内部 segment 构造方法以及最后一个公开 catalog 都回到包可见性。内部 Segment record 仍防御性复制 `sourceNodePlans`，不会因为可见性缩小而改变数据。上游 Node 与 mini-kv 信息只以版本标签、文件路径、摘要和状态字段进入目录；Java 不启动兄弟进程，不读取凭据值，不解析原始生产端点，也不建立 managed-audit 连接。`OpsShardReadinessRouteCleanupPostCompletionServiceFixtures` 随测试移动到包内，但保留一个 public 的 `postCompletionCloseoutService()` 工厂，这是三个 Prototype 测试的实测包外读者。其余工厂仍包可见，既复用完整只读图，又不把测试构造细节扩大成无约束接口。

## 服务层核心流程

后完成图可以按四层理解。第一层是审核与快照：AuditTrail 产生审计条目，AcceptanceReceipt 将审计结果与上一版 ExtendedCloseout 合并，EvidenceRegister 汇总端点清单和最终摘要，OperationalSnapshot 形成连续性、清单和接受状态快照。第二层是人工复核准备：PolicyGuard 根据快照和证据登记给出阻断判断，ReviewerPacket 组织审阅者所需材料，TransitionBrief 把复核包转换成移交说明，FinalVerification 汇总说明、审阅包和最终摘要。第三层是归档与完成：FinalArchivePlan 结合最终验证和 EndpointManifest 形成归档步骤，ThirdRunCloseout 加入接受回执，CompletionIndex 建立可检索索引，CompletionCertificate 将索引、第三轮收口和归档计划封装成完成证书。第四层是推送后的证据链：PostPushCloseout 关联完成证书与 CI 证据，CiRunAttestation 记录运行证明，TagManifest 固定版本标签，ReleaseEvidenceBundle 组合证书、CI 与标签，ConsumerSignoffPacket 叠加策略和接受回执，ArchiveHandoffReceipt 连接归档计划与消费者签收，MaintenanceBoundaryReport、FixtureCoverageIndex、CompletionAuditDigest 依次验证边界、夹具和摘要，最终由 PostCompletionCloseout 给出只读完成结论。迁移没有改变构造注入顺序或任何判断式；变化只是让这条完整流程在一个包内表达，使依赖从目录结构上可见，并消除跨包访问静态端点常量的需要。

## Java 证据检查

Java 侧采用多层机械证据。第一层是可复现普查：`scripts/ops-root-census.ps1 -Json` 必须输出 direct-root 一百零八、retained 一百零四、remaining 四、RouteCleanup 零、unassigned 零，总 `ops` 源码仍为一千三百五十二。第二层是文件集合门：新结构测试按二十二个家族名生成四十四个生产文件和二十二个测试文件，要求新路径全部存在、旧路径全部消失；两个夹具也做同样检查。第三层是边界门：通用 `OpsBoundaryTestSupport` 扫描包外源码，结果必须是四个来源、四十四条类型边、四十四个目标，恰好对应四个根控制器导入每个服务与响应。第四层是路由门：二十二条本批后缀逐值校验，家族 owner 的静态字符串字段扣除基础路径后必须正好八十四，EndpointManifest 返回数也必须正好八十四，全局 owner 中禁止再出现 `ROUTE_CLEANUP_`。第五层是可见性门：反射包内所有服务的 `ENDPOINT` 与 `PROFILE`，只要字段存在就必须非 public，同时文本扫描确认没有包外生产读者。第六层是方向门：包内任何文件都不得导入根 RouteCleanup 实现或全局路由 owner。最后由生产编译、测试编译、Spotless、聚焦测试、完整 Maven verify、JaCoCo 与 SpotBugs 共同验收；任何一层失败都不允许靠提高上限或改 fixture 字节通过。

## mini-kv 证据检查

本版没有修改 mini-kv 仓库，也没有把 Java 变成 mini-kv 的执行控制面。RouteCleanup 目录中涉及 mini-kv 的字段只是只读证据说明：它可以记录兄弟版本、固定路径、摘要、检查状态和“不允许执行”的结论，却不能读取真实 credential value、解析 raw endpoint、启动或停止 mini-kv 服务、改变 active shard router，更不能开放写路由。为什么在 Java 重构里仍要检查这部分？因为服务搬包最容易发生的风险不是算法算错，而是为了修编译临时改成另一条数据来源，或者把原来只读的目录替换成运行时连接。本版通过原有服务测试、完整集成测试和分析器的 `allEntriesKeepReadOnlyBoundary()` 继续证明：每个条目都保持 `readOnly=true`、`executionAllowed=false`、`startsMiniKvService=false`、`credentialValueRead=false`、`rawEndpointParsed=false`。测试夹具只在内存中构造已有对象，不访问 `D:\C\mini-kv`，不修改任何冻结档案，也不要求 Node 同步跟版。因此这是结构性收口，不是跨项目契约变更；它能与兄弟项目并行，又不会破坏 Node 对 Java 与 mini-kv 历史证据路径和摘要的固定引用。

## 阻断与安全边界

四十四个服务虽然名字包含 closeout、release、certificate、signoff 等词，但它们输出的是操作证据，不执行操作。每个 endpoint 都是 GET 只读入口，响应持续声明 `readOnly=true` 与 `executionAllowed=false`。本版不接触订单、库存、支付、Outbox、失败事件重放、审批写入或数据库迁移；不新增 RabbitMQ 发布，不打开生产数据库，不创建审计连接，也不处理 secret value。部署、回滚和 rollback SQL 继续只是被描述和阻断的对象，Node 不能把这些响应解释成生产授权。可见性收紧本身也是安全改进：当 `ENDPOINT`、`PROFILE`、Analyzer 与 catalog 只在包内可见时，后续代码若想绕过公开服务边界直接耦合内部证据源，会在编译期失败。四个根控制器是唯一有意保留的入口适配层，其依赖方向可被结构测试重放。若某次后续改动重新在全局 owner 中加入 RouteCleanup 常量、把包内字段改为 public、增加包外读者、改变八十四条清单数，或者让根文件数高于一百零八，机械门都会失败。这样的阻断比文档承诺更可靠，因为它把“不能做什么”编码成每次 CI 必经的断言。

## 测试覆盖

测试迁移遵循“行为跟实现走，HTTP 跟入口走”。二十二个服务测试与两个夹具进入 `ops.maintenance.routecleanup`，因此可以继续访问包可见的端点、profile、分析器和 catalog，不需要为了测试重新扩大生产 API。四个控制器及其 Spring 集成回归保留在根测试面，证明依赖注入和 JSON 输出不受包移动影响。包内 `OpsShardReadinessMaintenanceRoutePathsTests` 接管全部 RouteCleanup 路由比对，根 `OpsShardReadinessRoutePathsTests` 退出这一家族，避免跨包读取内部字段。v1857 的历史边界门从二十六来源、四十四边、二十一目标、二十二个分析器读者收紧为四来源、二十边、二十目标、零分析器读者；v1864 的手递图边界从十来源、三十八边收紧为三来源、二十二边，证明后完成服务已经与其前驱同包。v1865 新门再固定完整四十四类型边界、所有端点包可见、两组 SpotBugs 镜像、根计数和讲解质量。最终 verify 必须在本文定稿之后运行；如果它发现旧快照，修正机械事实后必须整套重跑。版本还要经过实现提交 CI、账本收口、annotated tag 和 closeout CI，任何远端作业未绿都不能开始下一版。

## 一句话总结

v1865 用一次闭合迁移把 RouteCleanup 从“根包与子包共同拥有、靠临时 public 字段维持”的过渡结构，变成“四个根 HTTP 适配器依赖一个自包含只读证据包”的稳定结构：四十四个类型和二十二个测试归位，八十四条路由只有一个 owner，清单字节不变，全部内部端点与分析器可见性得到偿还，根包从一百五十二降到一百零八，并由编译、精确边界、路由映射、不可变响应、SpotBugs、覆盖率、完整测试和双阶段远端 CI 共同证明这次重构只改变维护结构、不改变业务与安全语义。
