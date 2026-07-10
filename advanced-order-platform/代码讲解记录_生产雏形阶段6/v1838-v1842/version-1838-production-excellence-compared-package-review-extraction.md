# version-1838：ComparedPackageReview 依赖倒序抽取与路由所有权收口

本版本恢复 Java final-push 的结构抽取主线，主题只有一个：把“对比包审阅”这一组实现从巨大的 `ops` 根包迁到 `ops.maintenance.comparedpackagereview`，同时把仍然面向维护者可见的 HTTP controller 留在根包。这里的“迁移”不是改一个 `package` 声明便结束，而是同时处理路由所有权、上游证据引用、下游规则目录、SpotBugs 类型名、包内测试、根目录计数和文档证据。全文坚持禁止硬凑：每一节都对应本项目中可以复现的代码事实或失败条件。

## 实际工作量说明

这一刀移动十六个生产 Java 文件和四个包内测试文件，保留一个 controller 与一个 controller 测试在根包。十六个生产文件覆盖六条只读服务链：总目录、来源证据、比较结果、身份摘要、策略归档和交接收口；还包括响应 record、共享 support、审阅槽位、保护规则、审阅组以及四个分域槽位目录。旧的 `EndpointRefs` 没有复制出一个新文件，而是原位升级为 `OpsShardReadinessComparedPackageReviewRoutePaths`，所以总 `ops` 文件数没有因为新增 route owner 而增长。

真正费力的部分是依赖顺序。表面上 endgame census 把 `ComparedEvidenceEvaluationPreflight` 排在 `ComparedPackageReview` 前面，但静态引用证明前者的四组规则目录会读取后者的六个 endpoint。若先迁 EvaluationPreflight，就必须让迁出的包反向依赖仍在根包的内部 helper，或者临时制造第二份常量，两种方案都会增加后续清理成本。因此本版本选择“被依赖者先走”：先公开并迁走 review 路由边界，下一版再迁 EvaluationPreflight。根目录生产文件从 805 降为 789，可移动积压从 700 降为 684，目标 105 没有上调，未分类文件仍为零。

## 入口路由

六条入口仍然挂在 `/api/v1/ops/shard-readiness` 下，后缀逐字节保持不变。根 controller 继续使用 `OpsShardReadinessRoutePaths.BASE_PATH` 和六个聚合常量，因此 Spring 的 `@RequestMapping` 与 `@GetMapping` 解析结果没有变化。变化发生在常量的所有权：根聚合器不再直接保存六段很长的字符串，而是委托给新的 family route owner。这样，HTTP 入口仍集中可见，具体 family 又拥有自己的路由事实。

新的 route owner 同时公开“后缀常量”和“完整 endpoint 常量”。后缀常量服务于根 controller 与全局聚合器，完整常量服务于其他证据 family 的目录引用。例如 EvaluationPreflight 只需要指出其规则来源是 review 的 `POLICY_ARCHIVE` 或 `HANDOFF_CLOSEOUT`，它不应该了解根聚合器中数百个无关字段。通过公开窄边界，依赖从“整个根聚合器”收缩为“一个明确的 review 路由契约”。route-path 单元测试既校验完整路径以前缀开头，也校验每个语义后缀，防止重排代码时无意改字节。

## 响应模型

`OpsShardReadinessComparedPackageReviewResponse` 的字段、嵌套 record、集合复制策略和最终状态计算全部原样迁移。六条服务返回同一种响应模型，但通过版本、endpoint、profile 和选取的槽位集合表达不同视角。目录服务返回完整的十二个审阅槽位；来源服务强调输入证据；比较结果服务强调对比结果但仍不生成审阅决定；身份摘要服务只呈现可核验摘要；策略归档服务给出归档规则；交接收口服务汇总可交给下游消费的只读材料。

响应中的否定边界比“passed”更重要：`readyForReviewDecision`、`readyForRuntimePayload`、`readyForApprovalGrant` 以及 sibling mutation 等能力不会因为包迁移变成真。支持类继续使用 `List.copyOf` 或等价的不可变复制，外部调用者不能通过持有原始 list 修改已经生成的证据。SpotBugs 配置中该 response 的两个镜像排除块都迁到新 FQN；只改一处会让另一分析阶段出现误报或漏报，所以本版本的可读性测试同时断言新 FQN 存在、旧 FQN 完全消失。

## 上游证据配置

ComparedPackageReview 的直接上游是已经在 v1824 抽取完成的 ComparedPackageEvidenceIntake。四个槽位目录继续引用它公开的 endpoint refs：来源目录读取 catalog 与 source acceptance，比较结果目录读取 submission comparison，身份摘要目录读取 identity digest signature，策略归档目录读取 assurance closeout。这里没有把上游响应复制到 review 包，也没有重新解释上游状态；review 只保存“证据从哪里来”和“审阅哪个槽位”的声明。

这种配置方式让维护者能沿着常量追踪输入：先从 review 服务看到槽位，再进入对应 slot catalog，最后落到已抽取 family 的公开 endpoint。它也限制了影响范围：上游 family 内部如何构造响应可以独立重构，只要公开 endpoint 和响应契约不变，review 不需要跟随修改。相反，如果 endpoint 字节真的发生变化，route-path 测试、目录测试和跨 family 的源码守卫会一起失败，不允许文档声称“兼容”而代码静默漂移。

## 服务层核心流程

一次 `catalog()` 调用从根 controller 进入迁出的 catalog service。service 只读事务中读取总槽位目录、保护规则和审阅组，把这些值交给 support 构造响应。support 先复制集合，再统计通过数量、追加固定检查项，最后依据“所有槽位和规则是否通过”计算状态。其他五个 service 复用同一条构造通道，但选择各自的 slot 子集和 profile，因此六条 endpoint 的结构一致而语义区分清楚。

迁移后 controller 构造器显式导入六个新包 service，返回类型也显式导入新包 response。Spring 仍按公开类型注入 bean；类名和 `@Service` 注解未变，因此 bean identity 没有发生名称冲突。包内 helper、slot catalog 和 support 保持 package-private，只有 controller 或下一 family 真正需要的服务、响应、route owner 是 public。这个可见性划分避免为了“编译方便”把整个实现面公开，也让后续 CodeGraph 或 IDE 依赖图更接近真实业务边界。

## Java 证据检查

Java 侧证据分四层。第一层是编译：主代码编译证明根 controller、迁移 service、上游 intake 边界和下游 EvaluationPreflight 导入关系完整；测试编译证明包内测试与保留根测试的可见性正确。第二层是 family 测试：目录测试校验十二个槽位与唯一 code，服务测试校验各视角的版本和安全字段，support 测试校验不可变复制与状态计算，controller 测试校验六条调用仍能到达对应服务。

第三层是结构 ratchet：`scripts/ops-root-census.ps1 -Json` 必须返回 direct root 789、retained root 105、remaining 684、unassigned 0，并把 ComparedPackageReview bucket 归零；全局 root 上限、当前精确计数和 endgame census 测试同步收紧。第四层是全量 `mvnw verify`，它覆盖所有 JUnit、JaCoCo floor、SpotBugs 零新增和 Spotless。只有中文讲解、抽取说明和守卫测试都写完之后才运行最终 verify，避免重演“测试跑绿后又补文档，导致最终树没有被验证”的旧问题。

## mini-kv 证据检查

本版本没有启动、写入或重排 mini-kv。ComparedPackageReview 中出现的来源 endpoint 是 Java 内部只读证据路径，不是 mini-kv 网络地址，也不是允许执行 KV 命令的连接信息。响应中任何关于 sibling 或 runtime 的字段仍保持禁止或未就绪状态，因此 Node 不能把本响应解释成“可以启动 mini-kv”“可以写分片映射”或“可以代替真实联合测试”的授权。

需要区分“合同对齐”和“实时集成”。Java family 测试能证明路由与响应在本仓库内没有变化，冻结 fixture 能证明历史消费格式没有被移动，但它们不能证明一个正在运行的 Node 进程真的调用了 Java jar，也不能证明真实 `minikv_cli` 返回了新鲜结果。按照四项目统筹规则，在 integration capstone 完成之前，本版本只支持 `single-project validation + cross-project contract alignment`。这条诚实边界写进讲解，是为了防止结构优化被包装成尚未发生的运行时能力。

## 阻断与安全边界

这次迁移必须维持六个明确的“没有发生”：没有 write routing，没有 active shard router，没有 credential value，没有 raw endpoint，没有 managed-audit connection，也没有 deployment 或 rollback 执行。所有 Spring 方法仍为 GET 风格只读入口，服务事务仍标记 `readOnly = true`。review 响应只描述候选证据、审阅槽位和安全状态，不创建审批决定，不写审批账本，不持久化审阅记录，不产生运行时 payload，也不修改 sibling 项目。

失败条件同样具体。若任何 route 字符串变化，本刀回退；若 response 字段或 JSON 顺序因重写 record 而变化，本刀回退；若为了通过 census 把 789 上调，本刀回退；若通过修改 fixture 字节或降低测试期望掩盖差异，本刀回退；若旧 response FQN 仍残留在 SpotBugs 镜像块，本刀不完整；若 controller 被一起迁走导致根包 HTTP 入口不可见，也不符合 endgame retained-root 合同。安全不是一句“行为不变”，而是一组会失败的机械门。

## 测试覆盖

本版本保留 controller 测试在根包，让它从真实的跨包可见性角度构造六个 service；其余 catalog、service、support 和 route-path 测试跟随实现移动，使 package-private helper 不必被扩大为 public。新增 `ReadabilityUpkeepOpsConsolidationExtractionV1838Tests` 逐项检查十六个文件的新位置和根位置缺失、controller 留存、route owner 公开字段、根聚合委托、四个 EvaluationPreflight 读者的 import、SpotBugs 双镜像 FQN、789 与 1352 两级 ratchet、抽取说明和中文讲解标题。

测试设计刻意同时包含正断言与负断言。只断言“新文件存在”无法发现旧文件复制残留，只断言“新 import 存在”无法发现旧 EndpointRefs 仍被使用，只断言 root 少于上限又可能让错误删除混过去。因此守卫同时检查新位置存在、旧位置不存在、旧类名消失、总文件数不增加、census 无未分类项。最终全量 verify 再把局部结构证据放回整个 Spring Boot 工程，检查它没有破坏其他 1600 余个测试覆盖的历史行为。

第一次全量 verify 的 1697 项测试中，业务与结构行为全部通过，但维护预算准确拦住了根路由聚合器：新增委托 import 令文件从上限 1111 行增至 1113 行。处理方式不是把预算调到 1113，而是使用窄 family 的静态路由导入，并删除聚合器中两个只用于视觉分组的空行，使文件恢复到精确 1111 行。这个小插曲说明 ratchet 的意义：即使功能完全正确，结构债也不能借“只是两行”悄悄回流；修复完成后必须重跑最终全量 verify，而不能沿用第一次运行中其余测试的绿色结果。

### 维护者复核示例

假设维护者要确认“身份摘要”入口没有在迁移中接错来源，可以按输入到输出的顺序走一遍。先看根 controller 的 `identityDigest()`，它只转交给迁出后的 identity-digest service；再看 service 使用的 profile、版本和 route owner 完整 endpoint；随后进入 identity-digest slot catalog，确认三个槽位都指向上游 intake 的摘要签名，而不是来源接收或提交比较 endpoint；最后查看 support 如何把槽位、保护规则和检查项复制进 response。这个路径短而且每一跳有明确所有者，比在 805 个根文件中按长前缀猜关系可靠得多。

再假设有人误把 `POLICY_ARCHIVE` 当成允许写入真实归档。源码复核应从 response 的能力布尔值开始，而不是只看名称：策略服务返回的是只读审阅材料，审批授予、运行时载荷和 sibling 修改仍为 false；事务注解是只读；目录只保存来源 endpoint；controller 只有 GET 映射。测试则从另一侧验证这些事实：controller 测试断言审批与运行时能力未打开，service 测试断言 profile 和检查项，结构守卫断言没有引入新的连接或执行类。名称表达“审阅什么”，布尔边界表达“允许做什么”，两者不能混为一谈。

### 为什么不顺手改短类名

新包名已经提供 `comparedpackagereview` 上下文，理论上可以把类名缩短为 `CatalogService` 或 `ReviewResponse`，但本版本没有这样做。大量 Spring 测试、SpotBugs FQN、文档索引和下游源码都以现有公开类名为定位点；同时改包与改类名会把“所有权迁移”和“API 重命名”混成一个难以归因的版本。先完成无行为变化的包抽取，可以用编译器证明所有调用者都已迁到新 FQN；以后若确有维护收益，再用独立版本做命名收敛，并让兼容策略和调用点变更单独接受评审。这正是一次一个主题的工程纪律。

### 对抗性自审

评审者最强的质疑是：这只是移动文件，为什么值得一个版本？回答不应是“目录更整齐”，而是三个可量化结果。第一，根包减少十六个非入口实现，census 和 ratchet 会阻止它们回流；第二，六条路由从全局字符串堆中获得唯一 family owner，下一 family 不再依赖整个根聚合器；第三，依赖顺序被纠正为 review 先于 evaluation，消除了临时反向依赖。若这三项中任一项没有机械测试，版本就只剩美化；现在它们分别由文件位置、route delegation、跨包 import 和精确计数守卫覆盖，因此这一刀是结构边界的实质收紧。

## 一句话总结

v1838 以被依赖者先迁的顺序，把 ComparedPackageReview 的实现、测试和路由事实收进单一维护包，在六条 endpoint 与所有只读边界不变的前提下把 root 从 805 收紧到 789，并为下一刀 EvaluationPreflight 建好唯一、公开、可机械验证的依赖入口。
