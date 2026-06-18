# v1814 签批工件草稿预检注册表迁出代码讲解（J24）

本版本处理的是 `OperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflight` 这一组只读注册表。它位于签批链路里一个很容易被误读的位置：它不是生成签批工件的功能，也不是创建人工草稿的功能，而是在人工草稿真正出现以前，用只读响应列出“草稿工件预检必须满足哪些字段、守卫、门禁”。如果用通俗说法，它像一张进门前的空白检查表：请求编号必须只是元数据，签批模板摘要只能作为摘要引用，操作员身份只能是别名，签名字段不能含有签名材料，证据文件和片段不能被导入，运行时和生产执行都不能被打开。v1814 做的事情，是把这张检查表的实现从巨大 root `ops` 包里迁到 `ops.maintenance.signedapprovalartifactdraftpreflight`，让维护者一眼知道它属于签批工件草稿预检阶段，同时保持 HTTP 路由、响应字段、响应内容和安全边界都不变。

这一版接在 v1813 后面是有顺序原因的。v1813 已经把 `ArtifactDraftReadiness` 迁出，并把它的 endpoint 常量公开成只读字符串；而本版的 `ArtifactDraftPreflight` 正好读取 v1813 的这些 endpoint 作为上游证据来源。也就是说，先迁 readiness，再迁 preflight，可以让这次迁移的出边很干净：迁出的 field catalog 继续 import readiness 包里的 public immutable endpoint，不需要再把另一个大包顺手挪走，也不需要扩大业务方法可见性。这个顺序比随机挑一个 16 文件家族更稳，因为它顺着实际依赖链向前收缩 root 包。

## 入口路由

入口仍然是普通 GET 请求，请求体为空，不接收会改变业务状态的参数。公共前缀仍然是 `/api/v1/ops/shard-readiness`，后缀包括 `signed-approval-artifact-draft-preflight-catalog`、`digest-chain`、`operator-window`、`signature-statement`、`evidence-source`、`redaction-provenance`、`fail-closed-locks`、`archive-plan` 和 `closeout`。这些 URL 对调用方来说没有任何变化。

本版新增 `OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths`，放在已有的 `ops.maintenance.signedapproval` route owner 区域里。它不是新入口，只是这些 suffix 的明确所有者。root `OpsShardReadinessRoutePaths` 仍然保留原来的常量名，但每一个 artifact-draft-preflight 常量都委托给新 owner。这样 root controller 和旧调用方依旧看 root 聚合器，新包内 service 也不再依赖 root 包的 package-private route 类。保留 root 入口、迁走实现，是这批拆分的稳定策略。

## 响应模型

输出仍然是 `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse`。它描述项目、版本、只读状态、是否允许执行、来源计划、草稿状态、签名捕获状态、批准授权状态、导入状态、运行时状态、字段列表、守卫列表、门禁列表、检查项和最终状态。迁包改变的是 Java 包归属，不改变 JSON 字段和语义。

响应里的关键点是“能证明什么”和“不能做什么”被放在同一个返回模型里。字段目录说明预检需要哪些元数据，例如请求 manifest、源工件预检摘要、签批模板摘要、操作员身份、操作员角色、捕获窗口、签名算法、脱敏策略、来源版本等；守卫目录说明每个字段为什么必须被拦住，例如拒绝手工草稿自动物化、拒绝摘要绕过、拒绝凭据材料、拒绝签名正文、拒绝证据导入、拒绝原始值体、拒绝写路由和拒绝兄弟状态变更；门禁目录则把这些守卫整理成更高层的 fail-closed 规则。这样维护者读一个响应就能看清：这不是执行通道，而是只读预检说明。

## 上游证据配置

本版最重要的上游证据是 v1813 已经迁出的 `ArtifactDraftReadiness`。`FoundationFieldCatalog` 读取 readiness 的 catalog、digest-chain、operator-window、signature-statement、evidence-source 等 endpoint，`AssuranceFieldCatalog` 读取 readiness 的 evidence-source、redaction-provenance、fail-closed-locks、closeout 等 endpoint。它们读取的只是 endpoint 字符串，用来说明“本字段的证据来源地址在哪里”，不是去调用上游服务，更不是把上游响应混进本响应。

这条上游边界很干净，因为 v1813 已经把 readiness endpoint 常量 public 化。v1814 迁包后不需要回头修改 readiness 业务逻辑，只需要让新包继续 import `ops.maintenance.signedapprovalartifactdraftreadiness`。这也是按依赖链推进的价值：每一版只处理自己范围内最小但完整的一段，既能降低 root 包压力，又不会让多个家族同时大范围翻动。

## 服务层核心流程

服务层结构仍然是 catalog service、digest chain service、operator window service、signature statement service、evidence source service、redaction provenance service、fail closed lock service、archive plan service 和 closeout service。每个 service 先用新的 route owner 组装自己的 `ENDPOINT`，再从 field、guard、gate 三类目录里取对应切片，最后交给 `Support.response(...)` 组装不可变响应。比如 catalog 返回完整字段、完整守卫、完整门禁；digest-chain 返回前几项摘要相关字段和对应守卫；operator-window 聚焦操作员身份、角色和捕获窗口；closeout 汇总完整目录并追加关闭检查项。

本版没有把 controller 迁走。两个 public controller 仍然留在 root `ops` 包，因为它们代表外部入口和既有 Spring 映射边界。迁出的是实现、响应、支持类、字段目录、守卫目录、门禁目录和内部测试。这样做的好处是调用入口稳定，内部维护负担降低。后续维护者搜索 `signedapprovalartifactdraftpreflight` 包时，可以看到该家族的主要实现；搜索 root 包时，则只看到公共 controller 和全局 route aggregation 这种真正需要留在 root 的入口层。

本版还做了一个小的结构优化：`GateCatalog` 不再单独占一个文件，而是作为同包 package-private class 与 `GuardCatalog` 同文件存放。它们本来就是同一套 fail-closed 规则的两层表达，一个负责逐条守卫，一个负责门禁摘要。合并以后文件仍然很小，没有形成难维护的大文件，却抵消了新增 route owner 带来的总文件数增长。结果是 root 文件数下降，整棵 `ops` Java 文件数仍保持 1,352，没有靠放宽总量护栏完成版本。

## Java 证据检查

Java 侧证据分成四类。第一类是编译证据：`test-compile` 已经证明迁出的 main/test 包名、root controller import、route owner、上游 readiness endpoint import、下游 root reader import 都能被 javac 接受。第二类是结构证据：新增 `ReadabilityUpkeepOpsConsolidationExtractionV1814Tests`，检查短说明是否从 ops README 可发现，检查代表性实现文件是否真的在 `ops.maintenance.signedapprovalartifactdraftpreflight`，检查 root 包里不再出现这些实现文件，检查两个 controller 和 root route 聚合器仍留在 root。

第三类是计数证据：root `ops` 直接 Java 文件数从 1,073 降到 1,057；全局治理 ratchet、质量 closeout 的精确计数和 v1809 的精确测量护栏都同步更新到 1,057。第四类是总量证据：新增 route owner 会增加一个文件，所以本版把 gate catalog 与 guard catalog 同文件放置，让整棵 `ops` Java 文件数仍然保持 1,352。可读性测试会同时守 root 下降和总量不增长，避免以后有人为了拆分随意增加文件膨胀。

route-path 测试也被增强。它继续比较 root 聚合器常量和 service `ENDPOINT`，并新增 root 聚合器委托到 split owner 的断言。这样如果未来有人把 root 常量改回硬编码，或者新 owner 的 suffix 发生漂移，测试会直接失败。这类测试不是为了覆盖业务分支，而是保护“迁包不改路由”的工程契约。

## mini-kv 证据检查

本版本没有触碰 mini-kv。它不读取 C++ 键值数据，不建立 RESP 连接，不修改 WAL、快照、命令解析或 `e/` 归档目录。文档里提到 mini-kv，只是为了明确四项目协作边界：Java 这次是在自己的只读注册表内部做包归属治理，不要求 C++ 项目同步改动，也不会影响 Node 对历史证据路径的引用。

同样，本版本没有启动 Node，也没有让 Node 自动启动或停止 Java / mini-kv。Node 侧可能引用 Java 历史证据路径和归档路径，所以本项目继续遵守“不移动历史归档”的规则。代码包迁移和归档整理是两个风险面，这里只做前者，不碰归档目录，不改截图目录，不改 historical fixture。

## 阻断与安全边界

v1814 明确不打开 write routing，不启用 active shard router，不读取 credential value，不解析 raw endpoint，不建立 managed audit connection，不部署，不回滚，不启动 Java、Node 或 mini-kv 进程。所有 endpoint 常量都是静态字符串，所有 service 方法都是只读事务，所有响应都描述“仍然锁住”“仍然没有物化草稿”“仍然没有捕获签名”“仍然没有发出批准授权”“仍然不能导入值”“仍然不能运行时执行”。

签批工件草稿预检很容易被误解为“草稿已经能生成”。本版通过响应模型、服务切片、守卫目录和文档反复强调相反事实：它只列出草稿产生之前需要满足的只读前置条件；它不生成草稿、不写审批、不存签名、不导入证据、不打开运行时。这样的 fail-closed 解释对后期工程维护很重要，因为越接近生产雏形，越要把“可以看见证据”和“可以执行动作”分开。

## 测试覆盖

测试覆盖也跟着边界拆成两类。迁入新包的测试包括 assurance service、foundation service、field catalog、guard catalog、gate catalog、support，它们验证的是家族内部响应内容、目录切片和支持方法，所以应该跟实现同包移动。留在 root 的测试包括两个 controller 测试和 route-path 测试，因为它们验证 root 入口是否还能构造、root 路由聚合是否还能等价委托、controller 是否仍然暴露相同响应。

额外的 root reader 测试也被编译保护。`ArtifactDraftReadinessLaneSupportTests` 读取 preflight catalog endpoint，`SignedApprovalDraftProfileSectionRegistryTestSupport` 构造 preflight catalog service，迁包后都改成显式 import 新包里的 public 类型。这个处理说明本版没有无视入边，而是把 retained-root 的读者逐一闭合。后续如果继续沿依赖链迁 `ReadinessLane`，这些 import 会成为下一版的清晰线索。

## 实际工作量说明

本项目这版的真实工作量包括：用 CodeGraph 先定位 artifact-draft-preflight、artifact-draft-readiness 和 readiness-lane 的相关入口；复核 Node playbook 和当前仓库状态；新建 `ops.maintenance.signedapprovalartifactdraftpreflight` 包；迁出十五个生产实现物理文件和六个家族内部测试；把 package-private 的 `GateCatalog` 合并到同包 `GuardCatalog` 文件尾部；新增 public route owner；把 root route 聚合器改成委托；让九个 service 的 `ENDPOINT` 变成 public immutable 字符串；给两个 root controller、两个 root controller test、route-path test、readiness-lane 两个目录、draft profile section registry 和 test support 补 imports；把 moved field catalog 继续指向 v1813 readiness 包；更新 SpotBugs baseline FQN；新增 v1814 短说明、README 索引、CHANGELOG、进度表和 JUnit 可读性护栏；把 root 计数护栏从 1,073 调整到 1,057，同时保持整棵 `ops` Java 文件数仍为 1,352；并用 `test-compile` 验证所有 import、visibility、package 声明和测试源码都能通过。

这里明确遵守本项目的讲解规则，禁止硬凑字数。篇幅来自真实工程判断：为什么先做 preflight，为什么不跳到 release acceptance，为什么 controller 保留在 root，为什么 route owner 放在 signedapproval leaf，为什么 service endpoint 可以 public 而业务方法不能扩大，为什么 gate 和 guard 可以同文件，为什么要同时守 root count 和 total count，为什么入边不仅有 readiness lane 还有 draft profile section。每个点都对应一个维护风险，而不是为了凑段落堆字。

再具体说，这一版的耗时不是“移动文件”本身，而是迁包后的边界闭合。出边如果漏了 readiness endpoint import，field catalog 会断；入边如果漏了 readiness lane，下一段链路会断；如果漏了 draft profile section，聚合视图会断；如果 route owner 没有测试，URL 漂移不会立刻暴露；如果为了新增 route owner 放宽总文件数，后续每一版都会越来越松；如果讲解只写一句“迁包完成”，后来的人就不知道这条签批工件草稿链路为什么按这个顺序拆。把这些关系写清楚，才是后期维护阶段真正有价值的部分。

还有一个细节值得单独说明：本版没有把 `ReadinessLane` 一起迁走。它确实是下一段最自然的候选，但如果在同一版里同时迁 preflight 和 lane，就会把“当前家族的入边闭合”和“下一家族的自身迁移”混在一起，验证范围会变大，失败时也更难判断是哪一层出问题。现在先让 lane 作为 retained-root 读者显式 import preflight endpoint，等下一版再迁 lane，就能清楚看到依赖方向从“root 读新包”变成“新包读新包”。这比一次性大搬家更适合长期维护，也更符合本项目当前按证据链逐段下降 root 压力的节奏。

从维护者视角看，这种节奏还有一个好处：每一版都留下可复核的输入、输出和边界。输入是当前 root 包里的一个完整只读家族，以及它对上下游 endpoint 的静态引用；输出是一个窄包、一个公开路由常量所有者、一组保留在根包的控制器和一组新的护栏测试。中间没有隐藏状态，没有脚本生成未知代码，也没有把多个主题揉在一起提交。以后如果有人排查签批工件草稿链路，他可以从短说明找到包名，从中文讲解理解顺序，从测试确认计数和路由，再从提交记录看到实际迁移范围。这些证据组合起来，才让拆分不是一次文件整理，而是可追溯、可继续、可回滚的工程演进。

因此，本版的价值不是表面上的目录变化，而是把责任边界、证据来源、只读承诺和后续迁移路线都固定下来。
这让后续排查更直接。
维护者也可以沿着这条线继续拆分，不必重新猜测依赖方向。

## 一句话总结

v1814 把签批工件草稿预检注册表从 root `ops` 包迁入 `ops.maintenance.signedapprovalartifactdraftpreflight`，root 直接 Java 文件数从 1,073 降到 1,057；controller 和全局入口保持不动，新 route owner 接管 suffix，上下游关系只通过 public immutable `ENDPOINT` 字符串表达，所有 HTTP 路由、响应模型、只读安全边界和历史归档路径保持不变。
