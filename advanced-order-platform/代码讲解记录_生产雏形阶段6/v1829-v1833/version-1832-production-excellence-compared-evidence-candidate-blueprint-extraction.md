# version-1832 production excellence compared evidence candidate blueprint extraction

## 实际工作量说明

本版本做的是 Java 项目里 `ComparedEvidenceCandidateBlueprint` 这一组只读证据蓝图的根包减压。它不是新增业务功能，也不是改变订单、库存、支付或发布流程，而是把原来挤在 `com.codexdemo.orderplatform.ops` 根包里的实现细节搬到更窄的维护包：`com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint`。这样做的价值很直接：根包继续保留 HTTP 控制器和全局路由聚合器，维护包承接蓝图目录、来源、比较、策略、收尾这些实现细节。以后维护人员查入口时仍然能在根包看到 controller，查实现时则能进入一个以候选蓝图为边界的包，不用在几百个相似前缀类里反复筛选。

这一刀的真实工作量主要有六块。第一，搬走候选蓝图的服务、响应模型、分段目录、阻断目录和支持类，让根包只留下 `OpsShardReadinessComparedEvidenceCandidateBlueprintController`。第二，新建 `OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths`，让候选蓝图五个只读路径后缀有独立 owner，根包的 `OpsShardReadinessRoutePaths` 改为委托它。第三，没有把旧的 `OpsShardReadinessComparedEvidenceCandidateBlueprintEndpointRefs` 也一起搬过去，而是把那五个完整 endpoint 常量折叠进新的 route owner，这一点很关键，因为本项目当前有总 `ops` Java 文件数 1,352 的 ratchet，新增 route owner 如果不抵消一个旧文件，就会让总文件数膨胀。第四，修复所有跨包读者：CandidateIntakePreflight 的 slot catalog 读候选蓝图 endpoint，ProfileSection 读候选蓝图 catalog service 和 response，它们现在都显式导入新包。第五，移动 response 后同步 SpotBugs 排除清单中的 FQN，避免镜像块还指向旧根包。第六，补文档、census、progress、changelog、readability test 和本篇中文讲解，把“完成”落成可复核证据。

这版必须强调“禁止硬凑”。字数不是目标，目标是讲清楚为什么这一刀是正确切面：候选蓝图是一个读模型族，它自身包含目录型入口和四个细分只读服务，依赖外部的 EvaluationPreflight endpoint，但不需要打开写路由、不需要启动 Node、不需要碰 mini-kv、不需要移动归档目录。它适合单版完成，也适合为下一刀 CandidateIntakePreflight 铺路。完成后的数字是根包 833 降到 819，剩余可搬 root 非 controller 从 728 降到 714，`ComparedEvidenceCandidateBlueprint` bucket 归零，总 `ops` Java 文件仍保持 1,352。

从维护者视角看，这一刀的价值还在于降低“同名前缀疲劳”。过去候选蓝图、候选接收预检、评估预检、包审阅等几组类都以相近长前缀开头，而且大多堆在同一个根包里。读代码的人想回答一个很简单的问题，例如“候选蓝图到底由哪些只读片段组成”，却要在根包里跨越控制器、服务、响应、目录、路径常量和其它相邻业务族反复跳转。v1832 把候选蓝图自己的实现放进一个小包后，这个问题变成了包内阅读：先看目录服务，再看四个分段服务，再看阻断目录和响应模型，最后看路径拥有者。这个阅读路径更短，也更符合人的理解顺序。

另一个实际收益是后续变更的影响面更容易判断。假设未来要调整候选蓝图里的收尾说明，维护者先进入候选蓝图维护包，看收尾服务和收尾分段目录即可；根包控制器是否需要改、候选接收预检是否需要改、评估预检是否需要改，都可以通过导入关系和测试边界逐层确认。过去所有类都在根包时，影响面容易被长类名遮住，很多变更看起来都像“改了根包里的某个东西”。现在包名本身就提供上下文，类名虽然仍长，但责任边界已经不再完全依赖类名前缀表达。

本版也刻意没有追求一次性清空整个 compared evidence 链。候选接收预检、评估预检和包审阅当然还可以继续搬，但它们分别有自己的入边、出边和路径拥有者，需要按版本逐个证明。若把这些内容塞进同一版，短期看 root 数字下降更快，长期会让审查者很难判断哪一处公开常量是为哪一组迁移服务，哪一个测试失败对应哪一个包边界。当前项目已经进入后期保养阶段，最需要的是可复核、可回退、可持续推进的节奏，而不是一次改很多却只能靠人工记忆解释。v1832 的切法就是把一组能闭环的候选蓝图先拿下，让下一版站在更清楚的依赖线上继续推进。

## 入口路由

入口路由的处理原则是“入口可见，owner 明确，字节不变”。用户或测试访问的 HTTP 入口仍然由根包 controller 承担，因为 controller 是运维人员寻找外部接口的第一层线索。`OpsShardReadinessComparedEvidenceCandidateBlueprintController` 继续在根包，它的 `@GetMapping` 仍然引用根包聚合器里的公共路由常量。这样做避免了控制器迁移带来的入口分散，也符合 endgame census 里“公共 Spring controllers 可以留 root”的 retained-root contract。

真正变化的是路由后缀的所有权。过去候选蓝图的五个后缀直接躺在 `OpsShardReadinessRoutePaths` 这个全局聚合器里，聚合器越来越像一个巨大常量仓库。v1832 把五个后缀交给 `OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths`：catalog、source、comparison、policy、closeout 都由这个 leaf owner 命名和保存。根聚合器不再硬编码这些长字符串，而是委托 leaf owner 的 suffix 常量。结果是外部路径字节完全不变，内部归属却变清楚了。

这一点对后续维护尤其重要。CandidateIntakePreflight 不是想知道“全局聚合器里有一个很长的常量”，它真正依赖的是“候选蓝图已经给出 source、comparison、policy、closeout 四种只读证据端点”。把这些 endpoint 放在候选蓝图自己的 route owner 上，读者导入时就能表达业务依赖，而不是导入一个模糊的全局仓库。更进一步，旧 EndpointRefs 的完整 endpoint 也被折叠到这个 route owner 上：`CATALOG`、`SOURCE`、`COMPARISON`、`POLICY`、`CLOSEOUT` 都是 `BASE_PATH + suffix`。因此路由 owner 同时能服务根聚合器、服务实现类、服务下游 slot catalog，而不会多出一个只做转发的文件。

## 响应模型

响应模型 `OpsShardReadinessComparedEvidenceCandidateBlueprintResponse` 随实现搬进了新包。它的意义不是承载可写命令，而是把候选蓝图这一组只读证据整理成稳定结构。catalog response 会聚合候选 section，source/comparison/policy/closeout response 则分别表达候选来源、比较规则、策略约束和收尾阻断点。移动 response 的风险在于下游 Java 类型引用，而不是 JSON 结构本身。因此本版只改 import，不改字段，不改 record 组件，不改序列化形态。

ProfileSection 是本轮最明显的 response 读者。它把多个已经存在的只读证据面拼成 profile section，其中包含候选蓝图的 catalog 结果。移动 response 后，`OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionSourceCatalog` 必须显式导入新 FQN。这种导入看似小，但它让依赖方向可见：ProfileSection 是候选蓝图的消费者，候选蓝图不反向知道 ProfileSection。维护人员以后读包依赖时，能够从 import 看出证据汇编层在消费候选蓝图，而不是把两组概念混在根包里。

SpotBugs 也需要同步。这个 response 里存在集合型组件，历史上已经有 EI_EXPOSE 类排除块。类迁移后，如果 `config/spotbugs-exclude.xml` 还写旧的根包 FQN，排除块就失真，未来要么漏报、要么让维护人员误判“这个 response 还在 root”。v1832 把两个镜像块都指向 `ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintResponse`，并在 readability test 里断言新 FQN 存在、旧 FQN 不存在。这不是为通过工具而改配置，而是让静态分析账本和代码现实一致。

## 上游证据配置

候选蓝图本身不是孤立目录，它读取下一层 `ComparedEvidenceEvaluationPreflight` 的 endpoint 作为证据来源。这里有一个边界取舍：EvaluationPreflight 还没有在本版搬走，如果为了候选蓝图强行提前搬 EvaluationPreflight，会把单版主题扩大，破坏“一次一刀”的节奏；如果完全不处理，又会因为 moved package 不能访问根包 package-private EndpointRefs 而编译失败。v1832 选择最小公开面：仍然让 `OpsShardReadinessComparedEvidenceEvaluationPreflightEndpointRefs` 留在 root，但把类和五个 immutable String 常量设为 public，让 moved section catalog 能读取这些只读 endpoint。

这个公开不是功能扩展。公开的不是 credential，不是原始 URL，不是动态连接，也不是可执行客户端，而是已经存在的只读路径字符串。它们继续由根包路由聚合器组合，语义仍然是“EvaluationPreflight 提供的只读证据端点”。这样候选蓝图可以从新包引用上游证据，EvaluationPreflight 的迁移仍然留给下一版或后续版本按完整包边界处理。

下游证据配置也同步变清晰。CandidateIntakePreflight 的 source、comparison、policy、closeout slot catalog 以前可以从根包或旧 EndpointRefs 间接找到候选蓝图 endpoint。现在它们导入候选蓝图 route owner，并读取 `SOURCE`、`COMPARISON`、`POLICY`、`CLOSEOUT` 这些完整 endpoint 常量。ProfileSection 的 registry service 和 source catalog 则导入 moved catalog service 与 response。结果是上游和下游各归其位：候选蓝图向上只读 EvaluationPreflight endpoint，向下暴露自己的候选蓝图 endpoint 和 response，所有关系都能在 import 层被看到。

## 服务层核心流程

候选蓝图服务层可以理解成一个只读蓝图工厂。CatalogService 是总览入口，它调用 section catalog，把 source、comparison、policy、closeout 四类 section 汇总成 catalog response。SourceService 输出候选来源的要求，说明证据从哪里来、需要哪些上下文；ComparisonService 输出比较维度，说明两个文本包或证据片段如何形成可审查的候选关系；PolicyService 输出策略约束，说明哪些策略必须在候选阶段被看见；CloseoutService 输出收尾和阻断要求，说明哪些缺口会让候选蓝图不能进入下一步。

这些服务本身没有数据库写入，也没有队列发布，也没有调用外部系统。它们主要从 section catalog 和 blocker catalog 组合静态、确定性的 evidence payload。搬包不会改变对象创建顺序，不会改变 section code，不会改变 blocker code，也不会改变 endpoint 值。真正变化的是包边界：以前所有类都在 root，维护人员很难分辨“候选蓝图实现”和“其它 compared evidence 实现”；现在同一族文件集中在 `comparedevidencecandidateblueprint`，测试也随实现进入对应 test package，根包只留下 controller 和 route 汇总视图。

支持类 `OpsShardReadinessComparedEvidenceCandidateBlueprintSupport` 保留下来并随包移动，因为它承接 response 创建的共享逻辑。这里没有像 v1830 或 v1831 那样把 support 折叠进另一个 catalog；本版选择折叠的是旧 EndpointRefs。原因是 support 仍然表达 response 组装语义，而 EndpointRefs 的完整 endpoint 常量天然属于 route owner。这样的拆分更利于维护：业务组装逻辑留在 support，路径所有权留在 route paths，避免为了文件数而把不相干职责硬塞进一个类。

## Java 证据检查

Java 侧证据分为编译证据、census 证据、结构证据和文档证据。编译证据用于确认跨包 import、public 边界和 controller wiring 没有断；本轮在补文档前已经跑过 `.\mvnw.cmd -q -DskipTests compile`，说明移动后的 main code 可以通过编译。后续还要跑 focused test 和 full verify，不能因为 compile 通过就自封完成。

census 证据由 `.\scripts\ops-root-census.ps1 -Json` 给出。v1832 的关键数字是 direct root 819、retained root 105、remaining root non-controller 714、unassigned files 为空、`ComparedEvidenceCandidateBlueprint` bucket 为 0。这个命令比人工数文件可靠，因为它复用了 v1828 endgame census 的分类规则。文档里的 top scope、bucket 表、progress section 和测试里的 expected count 都绑定这些数字，任何回退都会在测试中暴露。

结构证据由 `ReadabilityUpkeepOpsConsolidationExtractionV1832Tests` 固化。它检查 extraction note 是否能从 README 和 census 找到，检查十三个 moved implementation files 是否在新包且根包不存在，检查 root controller 是否保留，检查 route owner 是否包含 suffix 与完整 endpoint，检查旧 EndpointRefs 文件和引用是否消失，检查 CandidateIntakePreflight 与 ProfileSection 是否导入 moved boundary，检查 EvaluationPreflight endpoint refs 是否以只读 public 常量方式公开，检查 SpotBugs FQN 是否迁移，检查 root count 与 total ops count 是否守住。这样的测试不是装饰，它是后续版本的护栏。

## mini-kv 证据检查

本版本没有修改 mini-kv，也没有启动 mini-kv 进程。mini-kv 在四项目协作里是更上游的 C++ 只读键值存储，本轮 Java 工作只发生在本项目的 ops 证据整理层，且没有改变跨项目契约、fixture 字节、endpoint schema 或归档路径。因此 mini-kv 的证据检查结论不是“我测了 mini-kv”，而是“这一刀不需要 mini-kv 参与，并且没有触碰需要 mini-kv 复核的契约面”。

这个边界要写清楚，是因为四项目协作最容易出错的地方不是某个包移动，而是把非契约内部重构误当成跨项目改造。v1832 不新增 Node 自动启动，不停止 Java 服务，不读 mini-kv WAL，不改 mini-kv CLI，不复制历史 fixture，也不改 Node 那些指向 Java 或 mini-kv archive 的硬路径。它只让 Java 内部的候选蓝图证据包更可维护。后续如果要做真正跨项目 capstone，那会走单独的 `readiness:cross` 或类似 env-gated suite，而不是藏在这类 extraction 版本里。

## 阻断与安全边界

安全边界在本版保持保守。没有 write routing，没有 active shard router，没有 credential value，没有 raw endpoint，没有 managed audit connection，没有 deployment，没有 rollback。`EndpointRefs` 被折叠时也只搬运字符串常量，不引入 HTTP client，不创建外部连接，不读取环境变量。`ComparedEvidenceEvaluationPreflightEndpointRefs` 变 public 时同样只公开不可变 endpoint 字符串，并且类仍在 root，等待未来 EvaluationPreflight 专版迁移。

归档边界也不动。Java 的 `a/` 到 `f/`、历史 `e/<version>/`、证据 JSON 和截图讲解目录都不因为本版迁移而改名或移动。Node 侧曾经存在大量硬编码路径引用，虽然本版只做 Java，但仍然沿用“不要移动历史归档根”的规则。新增讲解文件放在已有的 `代码讲解记录_生产雏形阶段6/v1829-v1833` 目录下，是按当前续写目录组织版本讲解，而不是整理历史归档。

还要说明一个容易误读的点：把 endpoint 字符串设为 public 不等于开放接口权限。接口权限由 controller、Spring mapping、应用安全配置和调用方上下文决定；这里公开的是 Java 常量的可见性，用于让已存在的只读证据 catalog 跨 package 编译。它不改变是否有 HTTP 路由，也不改变路由字节。readability test 会检查这些 public 面集中在必要的类上，避免未来有人把可见性扩大到服务内部状态或可写入口。

## 测试覆盖

本版测试覆盖分三层。第一层是原功能测试随包移动：CatalogTests、ServiceTests、SupportTests 进入 `ops.maintenance.comparedevidencecandidateblueprint` 对应 test package，继续检查 catalog 组合、各分服务响应和 support 组装。ControllerTests 留在 root test package，因为 controller 留在 root，它只需要通过 import 使用 moved services。RoutePathsTests 也留在 root，因为它验证根聚合器对 leaf owner 的委托关系。

第二层是 readability guard。新增的 `ReadabilityUpkeepOpsConsolidationExtractionV1832Tests` 专门验证这次迁移的工程约束。它不只是看文件存在，还看旧文件不存在、旧引用不存在、总文件数没有放松、SpotBugs FQN 跟现实一致、讲解文件存在且包含标准中文标题。这样后续如果有人把 EndpointRefs 加回来、把 response FQN 写回 root、或者忘记更新 census，这个 guard 会直接失败。

第三层是全局质量门。focused gate 会组合候选蓝图自身测试、ProfileSection 读者测试、CandidateIntakePreflight 读者测试、v1832 readability test、v1831 historical guard、v1828 census guard、v1809/v1806 全局 ratchet guard 和 walkthrough compliance，再跑 Spotless。full verify 会跑完整 Maven 验证，包括测试、JaCoCo、SpotBugs 和格式检查。最终还要提交、打 tag、push，并看 GitHub Actions 远端结果。只有这些证据都落地，v1832 才能算可交付；在那之前，本篇讲解只是“final verify 前的工程说明”，不是自封验收。

评审时最应该追问的点有三个。第一，路径是否真的没变。回答不能只说“我没有改字符串”，而要看根聚合器委托、叶子路径拥有者、路由测试和服务端点常量是否指向同一组后缀。第二，总文件数是否被新路径类悄悄放松。回答不能只看根包减少了十四个文件，还要看旧端点引用类是否消失，以及全树运维包文件数是否仍然被限制在一千三百五十二。第三，跨包公开是否过度。回答不能只说编译通过，而要看公开的是不可变只读端点字符串，还是服务内部状态、凭据、连接、写入口。v1832 的答案分别是：路径由测试守住，旧端点引用类被折叠，总文件数没有上升；公开面只包含候选蓝图需要读取的评估预检端点字符串，没有扩大到执行能力。这样的回答才是工程后期保养需要的透明度。

如果未来要回退这一版，正确回退方式也很明确：把候选蓝图实现类和测试类移回根包，恢复根聚合器里的直接常量，恢复旧端点引用类，恢复读者导入和静态分析配置，然后把 census 数字恢复到上一版。不能只把目录挪回去，也不能只改测试期望。这个回退说明不是希望回退，而是证明这版的变更边界足够清楚。边界清楚的版本才适合连续推进，因为下一版可以基于确定事实继续切，而不用先猜上一版到底改了哪些隐含关系。

本版没有把下一族一起搬入，是为了让证据链保持单向清楚。候选蓝图先稳定，候选接收预检再读取它，评估预检再作为上游只读来源逐步外移。这样的顺序让每一版都有独立起点、独立输出和独立失败条件。维护者看到失败时，可以立刻判断是候选蓝图边界错了，还是下一族迁移错了，不会把多个问题缠在一起。这样评审路径也更短，后续维护判断更稳。

## 一句话总结

v1832 把本项目的 `ComparedEvidenceCandidateBlueprint` 从拥挤 root 包中切到清晰维护包，保留入口、固定路径、折叠旧 EndpointRefs、显式上下游只读依赖，并用 census、readability guard、中文讲解和后续 verify 把这次减压变成可复核的工程事实。
