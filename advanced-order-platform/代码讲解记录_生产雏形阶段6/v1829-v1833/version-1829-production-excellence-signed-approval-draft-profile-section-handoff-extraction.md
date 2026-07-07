# version-1829-production-excellence-signed-approval-draft-profile-section-handoff-extraction

## 实际工作量说明

本版本是 Java final-push 计划里 v1828 endgame census 之后的第一刀，也是三段 ProfileSection 拆分的收口版本。前两刀已经把基础 ProfileSection 注册表和 TextPackage ProfileSection 注册表搬进各自的 maintenance 子包，但最后的 Handoff 层还留在根 `ops` 包里。这个状态不危险，却会让 root 包继续背负与实际职责不匹配的实现类：控制器只是 HTTP 入口，真正的证据组装、模块目录、来源目录、边界目录、渲染器、响应模型和支持函数都属于一个很窄的只读 handoff 主题。v1829 的实际工作不是简单挪文件，而是把这条只读证据链的归属、路由所有权、SpotBugs 例外、测试包边界、census 口径和讲解归档一起闭环。禁止硬凑的核心标准是：如果讲解写不出真实改动的输入、输出、边界和验证方式，就说明版本粒度太小；本项目这一版能说明清楚，是因为它同时完成了实现拆分、路由归属收敛、机械 census 脚本、历史测试语义刷新和中文解释材料。

具体文件层面，十个非控制器实现文件从 `com.codexdemo.orderplatform.ops` 根包移动到 `com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff`。这十个文件包括 handoff 的边界目录、门禁目录、模块目录、渲染器、响应模型、路由合同目录、章节目录、服务、来源目录和支持类。两个控制器没有移动：`OpsShardReadinessSignedApprovalDraftProfileSectionHandoffController` 和 `OpsShardReadinessSignedApprovalDraftProfileSectionRegistryController` 仍然留在 root，因为当前 Java 项目的约定是让公开 Spring 控制器集中保留在根 `ops` 包，便于维护者从 HTTP 入口向下追踪。换句话说，root 只继续承担入口索引功能，业务证据组装从 root 中剥离出去。这让 direct-root Java 文件数从 874 降到 864，最终 root 目标仍然是 105，剩余需要移动或合并的非控制器 root 文件从 769 降到 759。

这版还补了一个容易被忽略但很重要的维护动作：把 census 变成可运行脚本。v1828 的表格已经把剩余 bucket 讲清楚，但表格如果只存在文档里，下一位执行者仍然可能用另一套 PowerShell、另一套过滤规则或者肉眼统计来判断“还剩多少”。v1829 增加 `scripts/ops-root-census.ps1`，让 direct-root 文件数、保留 root 文件数、剩余非控制器文件数、未归类文件和 bucket 表都从同一条命令出来。这个脚本没有替代 JUnit guard，因为 JUnit 才是 CI 上会失败的机械门；它的价值是降低评审和下一版启动成本。维护者不用重新发明统计口径，先跑脚本，就知道当前事实是不是 864、105、759，以及 `SignedApprovalDraftProfileSection` bucket 是否已经归零。

因此，v1829 不是单纯“搬十个文件”。如果只搬文件而不补路由归属，handoff endpoint 的所有权仍然混在 root 和 candidate-document 之间；如果只补路由而不补 SpotBugs FQN，静态分析会把旧的响应例外误报成新暴露；如果只补测试而不补 census 脚本，后续版本又要重新人工数文件；如果只补脚本而不补中文讲解，维护者看不到为什么这刀选择 ProfileSection handoff 而不是更大的 RouteCleanup 或 ReleaseAcceptance。把这些点合在一版里，才符合用户要求的中大粒度：每个版本要有可说明、可验证、可延续的工程价值。

再从维护心理上看，这一刀的价值在于减少“看见根包就不想进”的阻力。一个后期工程如果每个只读证据主题都把目录、渲染、响应、服务和支持函数放在根包，维护者会在查一个小入口时被大量无关类名淹没，久而久之就只能依赖记忆和搜索。记忆会过期，搜索会给出太多结果，最后每一版都要重新判断哪些文件真的属于本主题。把尾部交接层搬到单独子包后，维护者进入目录时看到的都是同一条证据链：它从基础注册表取只读资料，生成交接视图，解释边界和门禁，再返回给入口控制器。这个目录没有生产写入，没有跨仓库副作用，没有部署含义，也没有隐藏的运行时开关。它的职责收窄以后，下一位执行者可以更大胆地继续拆别的族，因为已经完成的族不会继续在根包里制造噪声。

从评审角度看，v1829 还把“做完了”拆成几个能被反驳的事实。第一，根包数量必须真的下降到八百六十四；如果多一个实现类留在根包，计数测试会失败。第二，交接族 bucket 必须归零；如果某个同名前缀文件没有移动，脚本和测试都会暴露。第三，路由值必须仍然是原来的只读路径；如果所有权迁移时写错 suffix，route 断言会失败。第四，响应模型的静态分析例外必须跟着新全限定名走；如果漏改其中一个镜像块，后续静态检查会重新报警。第五，讲解必须在最终验证之前存在，而且中文比例足够高；如果只是几行英文式流水账，合规测试会拦住。这样的设计让评审者不用相信执行者的口头总结，而是能从代码、文档、脚本和测试中分别复现结论。

最后，这一版也刻意没有扩大到更大的清理面。剩下的路线清理网、发布验收路线拆分、只读门操作员交接、凭据解析记录和运行时执行残留都还有价值，但它们的依赖更复杂，应该各自成为独立版本。v1829 只处理已经被前两版铺好边界的尾部层，收益清楚，风险可控，测试能准确定义。这种节奏比一次性搬很多族更适合生产后期保养：每版都让结构更清楚一点，每版都给下一版留下可复现起点，每版都避免触碰旧归档和跨项目契约。维护不是追求一次清空，而是让系统每天更容易被人理解、验证和继续改。

还有一个更朴素的判断标准：一名新维护者打开这一版以后，应该能不用询问前任就回答四个问题。第一，这个入口收什么输入，答案是只读请求和已经存在的注册表资料。第二，它产出什么，答案是交接证据、边界说明、门禁说明和稳定的展示文本。第三，它不会做什么，答案是不写业务状态、不读秘密、不启动外部服务、不改变历史归档。第四，下一步从哪里继续，答案是看脚本输出的剩余分组，再选一个依赖清楚的族。v1829 把这些答案分别放进包结构、路线归属、测试、脚本和中文讲解里，而不是藏在个人经验里。后期工程真正怕的不是代码多，而是知识只存在于某次会话的记忆中；这版的目标就是把记忆变成仓库里的证据。

这种做法也能保护后来版本的质量。如果下一位执行者想移动更大的族，他可以先看本版怎样保留入口、怎样迁出实现、怎样让旧路线委托到新所有者、怎样把静态分析例外跟随响应模型、怎样让旧测试从“暂时保留”转成“已经完成”。这些动作形成了可复用的范式，但不是僵硬模板。每个族的依赖不同，不能机械复制；可以复制的是证据顺序和边界意识：先确认输入输出，再移动实现，再锁住路线，再收紧计数，再写说明，再验证。这样连续推进很多版本时，速度来自稳定流程，不来自冒险省略检查。

所以这版的解释必须比“文件搬走了”更长。维护者需要知道为什么入口不动、为什么实现要走、为什么路线归属要收口、为什么旧断言要改成新完成态、为什么脚本和测试同时存在。把这些问题讲清楚，后续版本才不会把拆分当成机械搬家，而会把它当成一次一次削薄复杂度的工程保养。

这种说明也是交接材料，能让后来的人少走弯路。

每个判断都要能回到证据。

证据闭环优先。

稳妥。

## 入口路由

这一版最容易出错的地方是路由所有权。handoff endpoint 的完整路径仍然是 `/api/v1/ops/shard-readiness/signed-approval-draft-profile-section-handoff`，不能因为移动服务类而改变字节。旧结构里，这个 suffix 有一部分历史上挂在 candidate-document route catalog 上，另一部分通过 root aggregator 暴露。v1829 做的不是发明新路径，而是把 `SIGNED_APPROVAL_DRAFT_PROFILE_SECTION_HANDOFF` suffix 放到 `OpsShardReadinessSignedApprovalDraftProfileSectionRoutePaths`。这个类已经在 v1825 作为 ProfileSection 的 signedapproval 叶子 route owner 存在，所以本版本不需要新增 Java route owner 文件，也就不会为了拆包而增加总 main 文件数量。

移动后的 handoff service 不再从 package-private 的 root aggregator 读取路径，而是用 `OpsShardReadinessSignedApprovalDraftProfileSectionRoutePaths.BASE_PATH` 加上同一个 handoff suffix 生成 endpoint。root aggregator 仍然保留 `SIGNED_APPROVAL_DRAFT_PROFILE_SECTION_HANDOFF` 常量，但它现在委托给 signedapproval 叶子 owner。candidate-document route catalog 也继续暴露这个历史常量，同样委托给同一个 signedapproval owner。这样一来，外部看到的常量名和路径值保持兼容，内部所有权却更准确：ProfileSection handoff 路由属于 ProfileSection 族，而不是候选文档族或 root 包。这个做法符合本项目这轮 ops extraction 的基本原则：入口控制器可以留在 root，route 字节必须保持不变，但 suffix 所有权要逐步迁到真正的业务叶子包。

## 响应模型

响应模型 `OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse` 也随实现层移动到新的 handoff 子包。它是只读证据 response，不是写请求 DTO，也不参与订单、库存、支付、outbox 或运行时写流程。移动 response 的风险主要有两个：第一，root 控制器必须能继续导入公开 response 类型；第二，SpotBugs 既有的 EI_EXPOSE 例外必须跟着全限定名移动，否则静态检查会把同一个已接受的只读暴露模型当成新问题。v1829 对这两点都做了闭环：root controller 显式 import 新包里的 response 和 service；`config/spotbugs-exclude.xml` 两个镜像块里的 response FQN 都换成 `ops.maintenance.signedapprovaldraftprofilesectionhandoff` 下的新路径。

响应本身的语义没有变化。service 仍然从 v1825 已经抽出的 `OpsShardReadinessSignedApprovalDraftProfileSectionRegistryService` 读取 registry，然后把章节 handoff、模块目录、来源目录、route contract、边界决策、Markdown 渲染结果和 gate 列表组装成同一个只读返回体。输入是已经存在的 ProfileSection registry 输出，输出是 handoff endpoint 的证据说明。这个方向很重要：handoff 层只消费基础 registry，不反向写 registry，也不绕过 v1825 的服务边界去读 root 里的旧类。对于维护者来说，现在看到 response 所在包，就能知道它是 ProfileSection handoff 视图的一部分，而不是整个 ops 根包的通用模型。

## 上游证据配置

Handoff 层的上游证据很窄，主要来自 v1825 完成的基础 ProfileSection registry。移动后的 service 构造器依赖 `OpsShardReadinessSignedApprovalDraftProfileSectionRegistryService`，该服务已经位于 `ops.maintenance.signedapprovaldraftprofilesection`。因此 v1829 的调用链是：root handoff controller 接收只读 GET 请求，委托给新 handoff 子包里的 service；handoff service 调用基础 registry service；各类 catalog 根据 registry.sections、registry.routeFieldLocks 和 registry 里的源数据派生 handoff 响应。这个链条没有触碰 TextPackage ProfileSection，也没有重新打开 artifact draft text-package 的长链路。

证据配置类移动后仍然保持分层：SourceCatalog 负责来源，SectionCatalog 负责章节 handoff，ModuleCatalog 负责模块，RouteContractCatalog 负责路由字段锁，BoundaryCatalog 负责边界决策，GateCatalog 负责门禁描述，Renderer 负责 Markdown 稳定渲染，Support 负责 response 拼装。它们被放进同一个 narrow package 后，包名本身就给了维护者上下文，长类名虽然暂时保留，但不再全部堆在 root 包里互相遮蔽。后续如果要继续缩短类名，前提也更充分，因为 package 已经包含 `signedapprovaldraftprofilesectionhandoff` 语义。v1829 不做类名缩短，是为了保持 diff 纯净，把本版的可验证主题限定在 package extraction 和 route owner 收口。

## 服务层核心流程

服务层流程可以用四步理解。第一步，controller 通过 Spring 注入 handoff service，HTTP route 仍然映射到原路径。第二步，handoff service 调用基础 registry service，拿到已经稳定的 ProfileSection registry。第三步，各目录类从 registry 派生展示型证据，比如每个 section 的 handoff 条目、来源清单、路由字段锁、边界决策和 gate 列表。第四步，support 类生成 response，renderer 生成面向人工阅读的 Markdown 片段，最终 controller 返回 response。整个流程是读模型到读模型的转换，不引入新的外部系统，不启动 Java 之外的服务，也不访问 Node 或 mini-kv 工作区。

从输入输出看，输入是已经抽取完成的基础 registry 只读对象和固定 catalog 数据；输出是 handoff endpoint 的响应对象。没有数据库写入，没有消息发布，没有 outbox 追加，没有审批状态改变，也没有 runtime artifact 生成。事务注解仍是 `@Transactional(readOnly = true)`，这对本项目很关键：ops readiness endpoint 的目的不是执行生产动作，而是给维护者展示某条只读证据链是否完整。v1829 把服务类搬走后，读事务语义、response version、profile 字符串和 gate 文案都没有改变。移动后编译能过，说明 retained-root controller 与新包 public 边界连接正常；测试再锁住 root count 和 route delegation，说明这不是一次只靠 IDE 移动的浅层改名。

## Java 证据检查

Java 侧证据分为直接结构证据、测试证据和计划证据。直接结构证据是文件系统上 root `ops` 目录只剩两个 ProfileSection 相关控制器，不再有 handoff implementation；新包里存在十个 moved implementation。测试证据由 `ReadabilityUpkeepOpsConsolidationExtractionV1829Tests` 承担，它检查 extraction note 是否可从 ops README 发现，检查十个文件是否在新包且 root 中不存在，检查两个 controller 和 root aggregator 是否仍在 root，检查 signedapproval route owner、candidate-document catalog 与 root aggregator 是否都委托到同一个 handoff suffix，检查 SpotBugs response FQN 是否跟随 moved package，检查 direct-root count 是否等于 864，并检查 census 脚本与本讲解是否存在。

计划证据是 `docs/ops/extraction-endgame-census-v1828.md` 里的 v1829 progress 小节，以及 `scripts/ops-root-census.ps1`。v1828 的 census 把 874、105、769 固定成起点与目标，但如果之后每一版只在散文里说“减少了”，评审者还要重新写命令数文件。v1829 补了一条项目内命令，让维护者可以在 `advanced-order-platform` 根目录直接跑 `.\scripts\ops-root-census.ps1`，看到 DirectRootJavaFiles、TargetFinalDirectRootJavaFiles、RetainedDirectRootFiles、RemainingDirectRootNonControllers、UnassignedFiles 和 bucket table。这个脚本不是替代测试，而是让评审复现口径一致。测试继续负责 fail fast，脚本负责 reviewer 操作简化。

## mini-kv 证据检查

本版本没有修改 mini-kv，也不读取 mini-kv 工作区的 fixture，更不会启动 C++ 程序。这里仍然保留 mini-kv 证据检查标题，是因为代码讲解合规模板要求每版说明跨项目边界。v1829 的结论是：mini-kv 对本版没有运行时输入，Java handoff endpoint 也没有消费 mini-kv CLI 输出或 C++ 存储响应。它只消费 Java 内部的 ProfileSection registry，因此 mini-kv 的 WAL、snapshot、RESP server、receipt parity 和 OSFS 目录都不在本版本变更范围内。

这不是忽略 mini-kv，而是遵守四项目协作边界。跨项目规则说，contract 和 evidence schema 变化要按 mini-kv 到 Java 到 Node 的顺序流动；非 contract 的内部维护可以各自推进。v1829 没改 Java 对外 schema，没改 route 字节，没改 evidence JSON，也没改任何 frozen fixture 路径，所以没有理由触碰 mini-kv。真正需要 mini-kv 的检查会出现在系统集成 capstone 或 Java endpoint 直接消费新鲜 mini-kv 输出的时候。当前这一刀只是 Java root 包维护，最安全的做法就是把 mini-kv 明确列为未参与输入，避免维护者误以为拆包需要同步 C++ 仓库。

## 阻断与安全边界

v1829 的安全边界可以概括为六个“不”。不打开 write routing，不读取 credential value，不接触 raw endpoint，不创建 managed audit connection，不部署或回滚，不自动启动或停止 Node、Java、mini-kv 运行进程。代码层面，controller 仍是只读 readiness endpoint，service 仍是 read-only transaction，response 仍是证据视图。文档层面，extraction note 明确要求不要重命名或移动 archive roots、`e/<version>/` 文件夹、evidence JSON 或跨项目 historical fixtures。这个提醒不是形式主义，因为 Node 仓库里有大量硬编码历史 archive 路径，Java 自己的证据链也依赖稳定归档。

另一个边界是测试语义。v1825 和 v1826 的旧测试原先断言 handoff service 仍留在 root，那个断言在当时用于证明“前两刀没有偷动第三刀”。v1829 正式移动第三刀之后，继续保留旧断言就会变成错误历史负担。这里没有放松测试，而是把语义改成更严格的当前事实：handoff service 必须存在于新 handoff 子包，root 中不得再有同名实现文件。root ratchet 也继续收紧到 864，而不是保持 874。也就是说，测试没有被改成适应失败，而是被改成表达新的完成态。这个区别是本项目后期维护最重要的纪律之一。

## 测试覆盖

本版本的测试覆盖分三层。第一层是 moved package 自己原有的测试随实现类一起移动，包名同步更新，测试 support 被设为 public，retained-root controller test 能继续导入它。这样原本验证 aggregate、boundary decision、boundary flags、gate catalog、Markdown stability、module catalog、renderer、route contract、route evidence、section catalog、service、source catalog 的测试仍然围绕同一组实现类运行。第二层是 root controller test 继续验证 root 入口可以构造新包 service，不因为 package extraction 断掉。第三层是新增 v1829 readability test，从文档、文件位置、route ownership、SpotBugs FQN、root count、census script、walkthrough 存在性几个维度给这刀建立机械门。

最终验证顺序也有讲究。先用 compile 或 focused tests 捕捉 import、package、SpotBugs FQN 和文档 guard 的小错误，再跑完整 `mvnw verify`。讲解必须写在最终 verify 之前，这样 walkthrough 合规测试才会把它当成本版交付的一部分，而不是事后补材料。census 脚本可以用 PowerShell 在本地运行来复现 864、105、759；CI 上的核心 fail gate 仍是 Java 测试、Spotless、SpotBugs、JaCoCo 和 prod smoke。这个组合让版本不是“我觉得搬好了”，而是结构、文档、说明和机械检查互相印证。

## 一句话总结

v1829 把 ProfileSection 集群最后留在 root 的 handoff 实现层完整搬进自己的 maintenance 子包，控制器继续留在 root 作为 HTTP 入口，handoff route suffix 归 signedapproval ProfileSection route owner，SpotBugs 和测试边界同步更新，root 文件数从 874 收紧到 864，剩余 movable backlog 从 769 收紧到 759，并补上 `scripts/ops-root-census.ps1` 让评审者可以用同一口径复现下一轮拆分的起点。对维护者来说，这一版的价值不是多一个 endpoint，而是让“入口在哪里、实现在哪里、路由归谁、证据怎么数、下一刀从哪里开始”都变得透明。
