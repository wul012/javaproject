# v1828 Java ops extraction endgame census 代码讲解

## 入口路由

这一版没有新增入口路由，也没有修改任何已经对外暴露的 HTTP 路径。它的价值不在于多开一个接口，而在于把本项目 Java 侧长期推进的 ops root package 抽取工作从经验推进变成可复核的工程合同。上一轮 v1827 已经把 v1826 的提交、标签、推送、远端 CI 和漂浮的项目说明文件全部收口，因此 v1828 可以进入计划书 Phase 1 要求的第一步：先做 remaining-families census，再继续抽取。这里的“入口”可以理解为维护人员进入下一阶段工作的入口，不是用户请求进入业务系统的入口。维护者以后看到 `docs/ops/extraction-endgame-census-v1828.md`，就能知道当前直接留在 `src/main/java/com/codexdemo/orderplatform/ops` 根包下的 Java 文件一共有八百七十四个，终态目标不是模糊地“继续减少”，而是固定到一百零五个。这个目标由一百个 controller、一个全局 route 聚合器和四个共享核心 waiver 组成。也就是说，后续每一刀都必须能解释自己从七百六十九个待迁移文件里拿走了哪一块，不能再用“这一版顺手移动一点”来替代结构设计。入口路由层面最重要的安全结论是：v1828 不碰 Spring controller，不碰 `OpsShardReadinessRoutePaths.java` 的路径值，不碰 release acceptance 现有 route owner 的字节内容，因此外部调用者看到的 URL 没有变化。

## 响应模型

这一版同样没有修改响应模型。`OpsEvidenceResponse`、各类 `OpsShardReadiness...Response`、release approval 记录对象、runtime execution 响应对象都保持原样。真正新增的是文档响应模型，也就是 census 如何回答维护者的三个问题：当前 root 包有多少文件、哪些文件应该继续迁移、哪些文件允许最终留下。为了让这个回答可以被机器守住，我把 census 分成三层。第一层是 root retained contract：controller 保留一百个，global route aggregator 保留一个，shared core waiver 保留四个。第二层是 remaining family buckets：把剩余七百六十九个非 controller root 文件按命名前缀和迁移语义拆成三十一个迁移桶，例如 `RouteCleanup web` 一百七十个、`MinimalReadOnlyGateOperatorCiHandoff` 一百四十个、`ReleaseAcceptanceRoutePathSplit` 七十八个、`ReleaseApprovalSandboxEndpointCredentialResolver records` 五十九个。第三层是 revision rule：终态目标只能向下收紧，不能向上放宽；如果以后有人认为某个非 controller 文件必须继续留在 root，就要先写入 `docs/ops/extraction-waivers.md` 并给出 reviewer check。这样做的意义是让文档像响应模型一样稳定：字段少、含义清楚、校验点明确。它不是随笔，也不是流水账，而是后续二十版、三十版抽取工作的基线。

## 上游证据配置

v1828 的上游证据来自三处。第一处是 Node 侧 final-push brief，它明确要求 Java 在下一版本产出 remaining-families census，写入 `docs/ops/extraction-endgame-census-v<version>.md`，并把“direct-root ops 包最终只留下 controller、共享 route aggregator、真正跨家族共享根文件和 waiver 文件”作为结束条件。第二处是 Java 本仓库已经存在的生产卓越进度账，它记录了 J7 到 J36 的抽取节奏、每一版的 root count 变化、SpotBugs/JaCoCo/Spotless/full verify 证据，以及 v1827 对 v1826 状态的收口。第三处是当前文件系统本身：我直接枚举 `src/main/java/com/codexdemo/orderplatform/ops/*.java`，确认直接 root 文件数是八百七十四，而不是引用旧文档中的数。这里没有读取 Node 或 mini-kv 的运行时，也没有启动跨项目流程；Node 计划只是只读上游规范，mini-kv 在这一版没有证据变更。上游证据配置的关键是“可复现”：census 文档不是凭记忆列出大概方向，而是把每个 bucket 的计数写清楚，并且由 `ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests` 重新扫描当前 Java 源码来验证。如果某个文件名未来新增、移动或留在 root，测试会迫使维护者同步解释它属于哪个桶。

## 服务层核心流程

这一版没有改服务层执行流程，但它反过来为服务层后续拆分定义路线。过去的抽取版本大多围绕一个 family 做机械迁移：创建窄 subpackage、公开 route owner、搬 service/response/support/catalog/test support、保留 controller、更新 SpotBugs FQN、降低 root ratchet。v1828 先把还没有迁移的服务层形态分清楚。最大的一类是 `RouteCleanup web`，有一百七十个文件，明显不是适合一刀搬完的简单家族，因为它横跨 maintenance upkeep、evidence、handoff、completion、archive、consumer、post-completion 等语义。第二大类是 `MinimalReadOnlyGateOperatorCiHandoff`，有一百四十个文件，里面大量 renderer 和 archive digest 片段更像需要拆成多轮的证据包生成链。第三类是 release acceptance 和 release approval，它们有 route-path split、archive verification handoff、credential resolver records、managed audit builders 等多层结构，后续应按 route owner 和记录构造边界拆，而不是按名字长短随意移动。中型家族则包括 compared evidence、compared package review、operator evidence value supply、signed approval draft profile section、v1 contract snapshots、runtime execution approval residuals 等。服务层核心流程的透明点在于：从 v1829 开始，每一版都应该能回到这个 census，说明自己消耗了哪个 bucket、是否降低了七百六十九的剩余量、是否保持一百零五的终态目标不变。

一百零五这个终态数不是拍脑袋。它来自一个很朴素的工程判断：root 包应该承担“找到入口”的职责，而不应该继续承担“容纳所有实现细节”的职责。controller 留在 root，是因为它们让维护者能快速定位 HTTP 入口；全局 route 聚合器留在 root，是因为它是各个入口路径的集中索引；四个共享核心 waiver 暂时留在 root，是因为它们当前确实横跨多个证据家族。除此之外，service、response、catalog、renderer、support、snapshot、builder、record、endpoint refs 都应该归入更窄的包名上下文。这样一来，后续阅读代码时，维护者看到 `ops.maintenance.releaseacceptance...` 就知道自己在 release acceptance 证据区，看到 `ops.maintenance.minimalreadonlygate...` 就知道自己在只读门禁证据区，而不是在一个八百多个文件的根目录里猜测类名差异。这个判断也为后续重构留出空间：如果某个包迁出后仍然过大，可以继续在子包内拆 renderer、catalog、support；但根包不会再成为所有复杂度的停放场。

## Java 证据检查

Java 证据检查分为静态和动态两层。静态层面，`extraction-endgame-census-v1828.md` 记录了当前总数、终态目标、迁移剩余量、每个 bucket 的数量、推荐批次顺序和修改规则；`extraction-waivers.md` 则记录四个真正允许作为 root waiver 的共享核心文件：`ContextHeaderField.java`、`OpsEvidenceResponse.java`、`OpsEvidenceService.java`、`OpsShardReadinessEvidenceEndpoints.java`。我特别没有把 `OpsEvidenceStaticReleaseArtifact.java` 和 `OpsEvidenceStaticReleaseDispatchTable.java` 放进 waiver，因为它们虽然重要，但更像 `OpsEvidenceService` 的内部静态支撑，后续应该进入 shared evidence support 包，而不是长期占据 root。动态层面，新增的 v1828 测试会扫描真实源码目录，先按 controller 规则收走一百个 controller，再按精确保留列表收走五个 root 保留对象，随后按三十一个迁移 bucket 的正则顺序分类所有剩余文件。测试断言总数必须是八百七十四，unassigned 必须为空，保留总数必须是一百零五，待迁移数量必须是七百六十九。这个检查的用处很实际：如果以后有人新增一个 root 文件却没有把它放进明确 family，测试会失败；如果有人把终态目标悄悄改大，文档和测试也会对不上。

## mini-kv 证据检查

mini-kv 在这一版只是边界参照，不参与修改。原因很简单：v1828 的任务是 Java final-push Phase 1 的 Java 仓库本地 census，输入是 Java 源码树和 Node 侧只读计划，输出也是 Java 文档、Java 测试、Java 进度账和 Java 中文讲解。mini-kv 的 WAL、RESP、OSFS、receipt consolidation 都不是这一刀的对象。如果把 mini-kv 拉进来，反而会破坏计划书要求的“每个 session 只写自己的 repo”。不过 mini-kv 给 Java 的启发仍然重要：它的问题是证据文件和治理档案膨胀，Java 的问题是 root package 膨胀，二者都不能靠口头承诺治理，必须靠计数、规则和自动检查。v1828 的做法就是把这种治理方式落到 Java：不是说“以后尽量少放 root”，而是说“最终 root 只能是一百零五，七百六十九个文件必须有迁移去处，放宽必须写 waiver”。这和 mini-kv 后续需要 receipt parity、archive policy 的思路一致，都是先把边界写死，再逐步消耗债务。

## 阻断与安全边界

这一版最大的阻断不是编译错误，而是“如果 census 写得太宽，后续工作会继续失焦”。所以我把边界设得比直觉更紧。controller 保留，因为它们是 HTTP 入口定位点；`OpsShardReadinessRoutePaths.java` 保留，因为它是全局 route 聚合器；四个共享核心文件暂时保留，因为当前引用面横跨多个家族。除此之外，即使某些文件名字看起来像“核心支撑”，也不直接给 root waiver。例如 release acceptance 的 route owner 要随 release acceptance route-path split track 迁移，OpsEvidence 静态 release support 要随 shared evidence support 迁移，release approval 的 digest 和 marker support 要随 release approval shared support 迁移。安全边界还包括不提前打开执行类能力：没有 write routing，没有 active shard router，没有 credential value，没有 raw endpoint，没有 managed audit connection，没有 deployment/rollback，也没有 Node 自动启动或停止 Java/mini-kv。这个版本只产生文档和测试，不改变运行时行为。禁止硬凑的含义在这里也很具体：讲解字数够不够不是目标，真正目标是让每个数字、每个 bucket、每个 waiver 都能被后续维护者拿来做决策。

换句话说，这一版的安全感来自克制。能不动运行时代码就不动，能不扩大例外就不扩大，能用现有测试规则守住就不另造一套松散口径。后续真正开始迁移时，任何一版如果想跳过某个难块，都必须在这张账上留下理由；任何一版如果想把新文件放回根包，都必须面对计数失败。这样做会让短期推进略慢一点，但长期会让维护者更敢动代码，因为边界清楚、责任清楚、回滚点也清楚。

这也是后期工程保养最需要的状态：少一点口头承诺，多一点可检查约束；少一点临时例外，多一点长期秩序。只要这个边界不松，后面的抽取就会越做越轻，而不是越做越乱。

## 测试覆盖

新增测试 `ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests` 覆盖三件事。第一，census 文档必须被 ops README 索引发现，且必须包含八百七十四、一百零五、七百六十九这三个核心数，以及 `MinimalReadOnlyGateOperatorCiHandoff`、`RouteCleanup web`、`ReleaseAcceptanceRoutePathSplit` 等关键大桶。第二，bucket 分类必须覆盖当前所有直接 root Java 文件。测试使用和文档一致的顺序规则：controller 优先，精确保留列表第二，然后才是按 family 前缀划分迁移桶。这样可以避免一个 controller 因为名字属于大 family 而被误算成需要迁移的 implementation。第三，waiver 清单必须存在，且必须包含四个共享核心 waiver、reviewer check 和 explicit non-waivers。这个测试不是为了证明业务正确性，而是为了证明治理规则没有漂移。它会和原有 `ProductionReadinessDocumentationTests`、`OpsCodeWalkthroughArchiveComplianceTests` 一起跑，保证项目说明、代码讲解、文档索引和 census 守卫在同一个版本里互相咬合。最终仍需要 full `mvnw verify`，因为文档测试通过并不代表 JaCoCo、SpotBugs、Spotless 和所有历史集成测试都安全。

## 实际工作量说明

v1828 看起来是文档版本，但它不是小粒度补文档。实际工作量在于把八百七十四个 root 文件拆成可以执行的结构账。先确认计划书要求，确认 v1827 已经远端绿，再通过当前源码树重新计算 root count；然后按命名前缀、控制器优先级、共享核心保留规则和迁移风险，把所有文件分桶，直到 unassigned 为零。这个过程中我没有把大块直接归成“其他”，因为“其他”就是后续维护失败的入口。随后把终态保留数从一百零七收紧到一百零五，原因是 `OpsEvidenceStaticReleaseArtifact` 和 `OpsEvidenceStaticReleaseDispatchTable` 不应被轻易批准为 root waiver。再之后新增 waiver 文档，让“留在 root”不再是默认选择，而是需要 reviewer check 的例外选择。最后新增测试，把这些计数和规则变成自动门禁。这样的版本不会让用户看到一个新页面或新 API，但它会显著提高后续二十版、三十版抽取的质量：每一版都能被问责，每一刀都能对照 census，无法用含糊解释掩盖没有减少结构债。

还有一个细节也很重要：v1828 没有选择“先抽一版再顺手写 census”。那样看似更有产出，实际会让计划书的 end state 继续滞后。先写 census 的好处是把选择权前置：哪些家族先动、哪些家族晚动、哪些文件允许留、哪些文件必须离开，全部在继续编码前摆到桌面上。尤其是 `RouteCleanup web` 这种一百七十文件的大块，如果没有 census，很容易因为它难而一直绕开；有了 census，它可以被标记为高耦合晚期 track，但不能从债务账里消失。`MinimalReadOnlyGateOperatorCiHandoff` 也是同理，它很大，却不应该靠短平快迁移冒险破坏证据包结构，而应该拆成多轮、每轮保持 route、response、archive 和测试不变。这个版本把这些判断写入文档和测试，就是为了让后续推进既快又不乱：快在每版不需要重新争论方向，不乱在每版都有明确边界和失败条件。

## 一句话总结

v1828 把 Java ops root package 的后半程从“继续凭经验抽”变成“以八百七十四当前数、一百零五终态数、七百六十九待迁移数为合同推进”，本项目后续所有抽取都必须围绕这个合同收敛。
