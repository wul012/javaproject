# v1893：handoff archive digest 证据目录收敛讲解

本版处理的是一条已经对外稳定、但内部仍有明显重复的只读证据链。它不增加新的业务路由，也不改变任何公开字段，而是把同一个上游响应被六个 Catalog 反复拆解的实现，收敛成一个有明确所有权的 `DigestCatalog.Evidence`。下面从真实请求进入系统开始，逐步说明输入怎样变成输出、哪些代码可以做判断、哪些代码只能展示，以及本版用什么机械证据证明“更短、更清楚、行为不变”。全文只讨论本项目实际代码，禁止硬凑抽象口号。

## 入口路由

外部读者看到的入口仍是一个 GET 路由：`/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-digest-registry`。基础路径来自共享的 `OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH`，后半段来自同一个路由所有者中的常量，Controller 只负责把 HTTP 请求转交给 registry service。请求不带订单号、不带凭据、不带执行参数，也没有请求体；因此这里的“输入”不是用户提交的一组可变数据，而是系统内部已经存在的 handoff archive verification 响应。这样的入口设计很重要：它明确告诉调用者，这个接口是观察窗口，不是操作按钮。

Controller 没有拼接证据、没有决定 passed 或 blocked，也不会自己启动 Java、mini-kv 或任何外部进程。它只调用 service，再把公开 Response 返回给 Spring。真正的入口语义由 service 上的 `@Transactional(readOnly = true)` 固定：即使未来有人为接口增加仓储访问，事务默认仍拒绝把这条观察链误写成状态修改链。公开 route、Controller 类型和 Response 类型在 v1893 中都没有改名，所以下游 Node 或人工审计脚本无需因为内部重构调整地址或反序列化规则。

从输入输出角度看，这一步可以概括为：输入是一条无副作用的 HTTP GET；中间调用一次 Java 内部的上游只读 service；输出是一份结构化 digest registry。若上游证据不完整，系统输出 blocked 及对应计数，而不是尝试修复、补跑或绕过。也就是说，入口负责“发起一次读取”，绝不负责“让结果变绿”。这条界线让公开接口可重复调用，也让测试可以用同一 fixture 精确冻结响应。

## 响应模型

公开响应的核心不是一个含糊的状态字符串，而是六组有序证据加一组 Markdown 展示。六组证据依次是：一条 `SourceArchiveSnapshot`、六条 `DigestSection`、四条 `ConsumerPacket`、五条 `ReplayInstruction`、八条 `BoundaryLock` 和六条 `ScorecardEntry`。在它们之后，`ReportRenderer` 生成六个 `MarkdownSection`；`DigestSupport` 再生成二十二条 checks 和最终 status。因此完整数量向量是 `1/6/4/5/8/6/6/22`。每一个数字都对应公开列表，缺一项、增一项或顺序变化都会被完整响应 oracle 发现。

`SourceArchiveSnapshot` 说明证据来自哪个 Java 版本、哪个 endpoint、哪个 profile，以及上游 artifact、operator lane、CI batch 和 boundary 的数量。`DigestSection` 把这些来源事实压缩成“总数、通过数、证据文本、状态”四个观察维度。`ConsumerPacket` 不发送真实包，只声明面向 operator、CI、operator-CI 和 release review 的四种只读消费视图是否已包含 digest 与 boundary locks。`ReplayInstruction` 描述怎样复用已归档批次证据，并把 `readOnly` 固定为 true。`BoundaryLock` 逐条说明哪些行为继续被锁住。`ScorecardEntry` 则把 expected 与 actual 并列呈现，避免只有结论没有分母。

本版没有修改这些 public record，也没有把它们改成字符串键值 Map。强类型 record 让编译器检查字段和元素类型，让 Jackson 继续生成稳定 JSON，让调用者能明确区分“批次回放说明”和“边界锁”。新引入的 `DigestCatalog.Evidence` 只是包内聚合值，它不出现在 HTTP 契约中。它的紧凑构造器对六组列表逐一执行 `List.copyOf`：调用者即使清空原列表，也不能改变 evidence；调用者尝试向 evidence 列表添加元素，则会得到 `UnsupportedOperationException`。这就是数据所有权的透明机制，而不是依赖“大家约定不要改”。

## 上游证据配置

service 的唯一上游是 `OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService`。它返回的是已经完成 archive verification 的 Java 响应，不是磁盘路径字符串，也不是对 Node 或 mini-kv 的实时网络调用。v1893 的 service 先执行 `sourceArchiveService.registry()` 得到输入，再且只再执行一次 `DigestCatalog.evidence(sourceArchive)`。这两行构成完整的数据入口：前一行取得权威来源，后一行把来源投影为当前 digest 家族需要的六组事实。

上游响应中的 `version`、`endpoint`、`profile`、`sourceHandoffVersion`、`archiveState` 和四类验证数量被原样放入 source snapshot。artifact、operator lane、CI batch、boundary 及上游 scorecard 的通过数被映射为六个 digest section。五个 CI batch verification 按上游次序变成五条 replay instruction；八个 boundary verification 按上游次序变成八条 boundary lock。这里没有排序、猜测或从 Markdown 反向解析数据，因而上游顺序就是下游顺序，结构化事实始终早于展示文本。

配置值同样保持公开可追溯：本接口自己的 response version、endpoint 和 profile 仍由 service 持有；`SOURCE_PLAN`、required archive verification plan、operator handoff plan 和 digest state 仍由 Support 放入最终响应。Catalog 不读取环境变量，不解析凭据，不选择远端地址，也不改变这些发布标识。这样的分界让配置、数据投影和状态政策各有一个所有者：service 知道“从哪里取、以哪个公开身份返回”，Catalog 知道“怎样从来源生成证据”，Support 知道“怎样判定完整性”。

## 服务层核心流程

重构前，service 需要分别调用 source archive、digest section、consumer packet、replay instruction、boundary lock 和 scorecard 六个 Catalog，再把六个散列列表同时传给 renderer 与 Support。六个 Catalog 总计 332 行，它们拥有相同输入、相同生命周期和相同状态词汇，却没有一个能表达“这六组结果共同组成一次 digest evidence”。这种设计表面上文件都小，实际把一次概念拆成六次协调：调用方必须知道全部部件，新增或删除一组时多个签名一起变化，列表所有权也分散在不同位置。

v1893 用 220 行的 `DigestCatalog` 建立唯一投影边界。`evidence(source)` 先生成 digests、packets、instructions 和 locks，再用这些已经生成的真实列表计算 scorecard，最后连同 source snapshot 一次构造 `Evidence`。顺序是刻意的：scorecard 不重新推导另一份影子数据，而是统计即将返回的同一组列表。通用的 `passedCount` 接收 Predicate，分别用于 passed digest、ready packet、read-only instruction 和 locked boundary，避免四段相同 stream/count 模板继续复制。

依赖方向也被整理成单向。证据形状所需的 `SOURCE_COUNT`、`DIGEST_COUNT`、`PACKET_COUNT`、`REPLAY_COUNT`、`LOCK_COUNT` 和 `SCORECARD_COUNT` 归 `DigestCatalog` 所有；`DigestSupport` 可以读取这些常量来判断完整性，但 Catalog 不再反向读取 Support。结构测试明确要求 Catalog 源码不含 `RegistrySupport`，从机械上阻止双向依赖回归。原先 88 字符的 package-private Support 同时改名为 `DigestSupport`，旧文件名被列入退休清单。service 最终只做四件事：读上游、建 evidence、渲染 Markdown、交给 Support 组装公开响应。

## Java 证据检查

Java 侧检查分为事实检查和完整性检查。事实检查来自 `DigestCatalog`：source snapshot 必须保留上游版本与状态；六个 digest section 分别覆盖 source、artifact、operator lane、CI batch、boundary 和 source scorecard；四个 consumer packet 必须同时声明包含 digest 与 boundary locks；五条 replay instruction 必须保持上游 order、batch 和 command family；八条 boundary lock 必须保留 code、locked behavior 与 archived 状态。scorecard 再用 expected/actual 对这些列表执行第二层一致性核对。

完整性检查由 `DigestSupport` 完成。它统计 passed digest、ready packet、read-only replay、locked boundary 和 passed scorecard，并把实际数量与 `DigestCatalog` 的形状常量比较。所有来源状态为 passed、所有数量精确相等、所有局部状态通过且六个 Markdown section 齐全时，最终 status 才是 passed；任何一项缺失都会得到 blocked。Support 还生成二十二条人可读 checks，把来源计划、来源版本、各列表计数和禁止边界都写进响应，便于运维者不用阅读代码也能定位阻断原因。

兼容性不是靠肉眼判断。`DigestResponseOracleTests` 在删除旧实现之前先对 released v1892 行为取样，冻结 sorted-property UTF-8 JSON 的 SHA-256：`2c0d238ec99c234a1c679eb4b7de2d37174c0a088f31b61d6d516949a5581ba4`。旧实现先通过这个 oracle，重构后仍用同一个期望值通过；因此公开字段、布尔值、列表元素、顺序、Markdown 与 checks 任一字节级语义漂移都会失败。局部测试再解释“为什么这些值正确”，完整 oracle 则证明“整个响应没有悄悄变化”，两类证据互补而不重复。

## mini-kv 证据检查

这条 Java 路径提到 mini-kv，是为了证明跨项目只读边界，不是为了替 mini-kv 执行命令。最终响应中的 `startsMiniKvService` 固定为 false；入口没有 host、port、socket、CLI 参数或凭据；Catalog 只读取 Java 上游 record；renderer 只把内存中的 record 映射为文本。也就是说，请求这个接口不会启动 mini-kv，不会停止它，不会写 key，不会触发 WAL、snapshot 或 shard routing，也不会把 Java 的 passed 当成 mini-kv 执行授权。

五条 replay instruction 中的“reuse archived ... evidence before any rerun”只是一份归档复核说明。`readOnly=true` 表示审阅者应优先复用既有证据，它不是可执行 shell，也没有命令解释器。八条 boundary lock 同样描述锁定行为，它们不会打开 socket。若未来需要真实跨项目联调，应由独立、显式 opt-in 的 capstone 或集成命令承担，并分别验证 Java 与 mini-kv 的进程生命周期；不能把观察接口偷偷升级为编排器。

因此本版对 mini-kv 的输入是零，对 mini-kv 的运行时输出也是零。Java 输出的只是“关于证据复用与边界锁的声明”。测试通过 Response 中的安全布尔值、checks 文本和上游 fixture 证明这一点，而不是通过启动一个外部服务后再声称没有写入。这样的证明范围更诚实：它能保证当前 Java 代码没有执行通道，不能替代未来真实环境对 mini-kv 自身实现的验证。

## 阻断与安全边界

公开响应顶层连续给出九类安全信息：`readOnly=true`；`executionAllowed=false`；`startsJavaService=false`；`startsMiniKvService=false`；`readsCredentialValue=false`；`resolvesRawEndpointUrl=false`；`managedAuditHttpAllowed=false`，再配合 endpoint、profile 和 status 说明本次观察的身份。这里最关键的机理是“证据不授予能力”。即使 source archive、全部 digest、packet、replay、lock、scorecard 和 Markdown 都是 passed，接口也仍然不能部署、回滚、执行 SQL、读取 secret、解析原始 endpoint 或打开 managed audit HTTP。

blocked 也不会触发自愈。若上游状态失败、列表数量不对或 boundary 未锁定，Support 只把 status 设为 blocked，并在 checks 中保留可诊断计数。它不会补造缺失元素，不会把 expected 改成 actual，不会忽略一条失败，也不会调用外部系统重跑。这样的失败模式适合治理接口：调用者得到稳定、可审计的事实，而系统没有因为“想让报告通过”扩大权限。

本版特意保留公开 Response、Controller、route、profile、事务注解、checks 算法和所有 fixture 字节。六个 Catalog 的删除只发生在包内实现层；`DigestSupport` 的改名也只涉及 package-private owner。若完整响应 SHA、数量向量或任一安全布尔值变化，oracle 会失败；若退役 Catalog 或旧 Support 文件复活，结构测试会失败；若 ops、Catalog 或长名基线反弹，优雅门会失败。这些失败条件把安全承诺从文字变成会阻止发布的代码。

## 测试覆盖

测试职责在本版一起收敛。`DigestCatalogTests` 用四个场景覆盖六组投影：第一组检查 source 与 digest 名称和 passed 状态；第二组检查 packet 内容与 replay 的顺序、command family、只读位；第三组检查八个 lock 和六项 scorecard；第四组把六个列表各自复制到可变 `ArrayList`，构造 Evidence 后清空来源并尝试修改结果，证明六次所有权快照真实有效。它关注领域投影，不负责 HTTP 或全文摘要。

`DigestRegistryServiceTests` 验证 route、profile、计划、来源版本、最终状态和所有安全位，确保 service 编排保持只读。`DigestChecksTests` 锁定二十二条 checks 的规模与关键边界文本。`ArchiveDigestMarkdownTests` 保留六段展示格式。`DigestResponseOracleTests` 锁完整公开响应。原四个超长测试 owner 被这四类短职责替代，测试文件总数仍为六，不是通过减少断言换取短名称。

`DigestExtractionTests` 是结构防线：生产包必须精确含五个当前 owner，测试包必须精确含六个当前 owner；六个旧 Catalog、旧长 Support 和四个旧测试 owner 必须不存在；`DigestCatalog` 必须少于 260 行、恰好六次 `List.copyOf`，service 必须恰好一次 evidence assembly，renderer 与 Support 必须接收 typed Evidence，Catalog 不得依赖 Support。`HandoffExtractionTests` 则更新上游跨包边界，要求新的 DigestCatalog 直接读取已提取的 archive response。行为、结构、命名和跨包四层测试共同防止“能运行但又变乱”。

## 实际工作量说明

生产代码从 1,337 个 Java 文件降到 1,332，ops 从 1,205 降到 1,200，Catalog 从 288 降到 283，当前 archive-digest 包从 10 降到 5。六个旧 Catalog 共 332 行，被一个 220 行的 `DigestCatalog` 替代；旧 Support 从 240 行的超长 owner 收短为 211 行 `DigestSupport`；service 从 72 行降到格式化后的短编排，renderer 也去掉六列表参数展开。全局 renderer 数仍为 30，但总行数从 3,234 收紧到 3,228。测试 Java 文件保持 906，当前包测试保持六个，说明代码量下降没有靠删除行为保护换取。

名称债也有可复现结果。生产长文件 stem 从 1,101 降到 1,094，长标识符出现次数从 19,956 降到 19,898，唯一长标识符从 2,660 降到 2,653；测试侧从 `710/9829/3687` 收紧为 `705/9816/3679`。精确 baseline 删除二十七项、新增零项。这个结果既包含六个 Catalog、四个测试 owner 和历史结构测试 owner 的退出，也包含 package-private Support 的短名替换；没有放宽阈值，也没有新增第二份可随意改写的基线。

工作量的价值不只在净删文件。先在旧实现上建立完整响应 oracle，再替换生产实现；随后把分散测试归并为按职责命名的 owner；发现 Catalog 与 Support 的双向依赖后，又把形状常量移动到证据 owner 并增加禁止反向依赖的结构断言；最后才更新 shrink-only census、中文讲解、archive manifest 和发布账本。每一步都有输入、输出和失败条件，因而最终减少的是协调复杂度，而不是把相同复杂度塞进一个更大的文件。

## 一句话总结

v1893 把“一个上游响应经过六个 Catalog、六组参数和一个超长 Support 才得到 digest 报告”的内部流水线，收敛为“service 读取一次上游、`DigestCatalog` 生成一次不可变 Evidence、renderer 展示、`DigestSupport` 判定”的单向结构；公开响应的 `1/6/4/5/8/6/6/22` 向量与 SHA-256 完全不变，Java 和 mini-kv 的执行权限始终关闭，而文件数、Catalog 数、行数和长命名债都由机械门永久收紧。
