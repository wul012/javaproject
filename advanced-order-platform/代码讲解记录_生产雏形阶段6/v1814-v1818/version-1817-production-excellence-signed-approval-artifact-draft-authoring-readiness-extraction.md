# v1817 生产卓越拆分讲解：签批草稿撰写就绪注册表迁出

## 入口路由

本版本处理的是签批草稿链路中的 `AuthoringReadiness` 一段。它不是生成正式签批文本的入口，也不是审批执行入口，而是位于审查包预检之后、指令预检之前的一组只读证明路由。外部看见的入口仍然是原来的两个控制器：基础控制器负责目录、摘要钉住、操作员要求、签名要求等四类路由；保障控制器负责证据要求、值策略要求、禁运要求、草稿文本缺席和收尾等五类路由。控制器仍留在 root `ops` 包，是为了保持 Spring Web 暴露面的稳定，避免在一次维护性拆分中改变请求映射、Bean 名称预期或者已有控制器测试的组织方式。

真正变化发生在控制器背后的只读实现层。v1817 新增 `ops.maintenance.signedapprovalartifactdraftauthoringreadiness` 包，把十五个实现文件移入这个窄包：服务类、响应 record、支撑构造器、基础要求目录、保障要求目录、总要求目录，以及合并后的 blocker/gate 目录。用户请求进来时仍走原来的 URL，控制器方法仍调用同名 service，service 仍返回同一种响应模型。差别只是维护者打开代码时，不再需要在一千多个 root ops 文件里寻找这组类，而可以直接进入一个表达业务阶段的包。

这次还新增了 `OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths`。它是路由后缀的新归属者，root 的 `OpsShardReadinessRoutePaths` 只做代理委托。这样做的价值在于，已经迁出的 service 不再需要访问 root 包内可见的聚合器，也不再因为 package-private route owner 被迫留在 root。换句话说，入口路由不变，路由字符串不变，代码所有权却从“巨型 root 文件堆”变成了“阶段化维护包”。

## 响应模型

响应模型仍然是 `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse`。它表达的核心不是“可以自动写草稿”，而是“可以进入人工草稿撰写准备状态，但执行、写入、签名材料、审批授权仍然没有打开”。这个 record 里保存来源版本、endpoint、profile、要求列表、阻断列表、门禁列表和证据说明。v1817 将它迁入新包后，同时更新了 SpotBugs 排除配置中两处 `EI_EXPOSE_REP` 与 `EI_EXPOSE_REP2` 的类名，避免质量门继续指向旧 FQN。

这个响应模型很重要，因为它把输入和输出说清楚了。输入并不是表单值、密钥、签名原文或者审批正文，而是前一阶段 `ArtifactDraftReviewPackagePreflight` 已经整理好的只读 endpoint 证据。输出也不是“写好的草稿”，而是一组带来源、带 blocker、带 gate 的 readiness 证据。目录服务输出全部要求，摘要服务输出摘要钉住情况，操作员服务输出身份与角色准备情况，签名服务输出签名策略但不携带签名正文，证据服务和值策略服务输出证据引用和脱敏策略，禁运服务输出禁止写路由、禁止值导入、禁止审批授权等约束，收尾服务说明下一步必须进入单独的人工作业包。

因此，响应模型承担的是“把上一站审查包预检结果翻译成下一站指令预检可以消费的只读证明”。它让系统知道哪些字段、摘要、来源、阻断和门禁都已经被列明，但不会让系统获得任何可执行能力。这个边界对于后期工程维护很关键，因为很多注册表看起来像业务功能，实际上只是治理证据；如果不把响应模型讲清楚，后续很容易误以为这里可以提前打开写路径。

## 上游证据配置

v1817 的上游是 v1816 已经迁出的 `ArtifactDraftReviewPackagePreflight`。在上一版中，审查包预检的 service endpoint 已经公开为不可变字符串，`AuthoringReadiness` 的要求目录正是读取这些 endpoint 来构造自己的来源证据。基础要求目录读取目录、摘要、操作员包、签名包、证据包等来源；保障要求目录读取证据包、值策略包、禁运包和收尾 endpoint。v1817 没有重新定义这些来源，也没有复制路由字符串，而是让迁出的目录继续引用已公开的上游 endpoint。

这种配置方式的好处是，链式拆分可以一站一站向前推进。先把被依赖的一站迁出，并公开稳定 endpoint，再迁出依赖它的下一站。这样每次切割的出边都是已经满足的，编译器只需要帮我们找入边。v1817 就是这个路径依赖的收益：上游已经准备好，所以这版的主要工作不是重新设计业务，而是把 ownership 从 root 移走，并让下游 `InstructionPreflight` 与 `SignedApprovalDraftProfileSection` 改为从新包导入 endpoint 和 service。

上游证据配置还有一个隐含约束：不要动历史归档路径。项目里很多文档、证据 JSON、截图和跨项目 handoff 都可能以精确路径或摘要引用旧材料。v1817 只改 Java 源码包结构和 docs 索引，不移动 `a/`、`d/`、`e/<version>/` 等历史归档，也不改 Node 或 mini-kv 的资料位置。这样做是为了让本项目继续获得可维护性收益，同时不破坏跨项目证据链。

## 服务层核心流程

服务层的流程可以按三步理解。第一步，控制器接收 GET 请求，仍使用 root `OpsShardReadinessRoutePaths` 中的常量进行映射。第二步，控制器注入已经迁入新包的 service，调用 `catalog()`、`digestPins()`、`operatorRequirements()`、`signatureRequirements()`、`evidenceRequirements()`、`valuePolicyRequirements()`、`embargoRequirements()`、`draftTextAbsence()` 和 `closeout()` 等只读方法。第三步，service 从 requirement catalog、blocker catalog 和 gate catalog 组合响应，通过 support builder 生成 record。

这次重构的细节在于，原来的 `GateCatalog` 没有作为独立文件继续存在，而是合并进 `BlockerCatalog`。这不是为了减少一行统计数字而硬凑，而是因为两者在这个家族内部都属于“只读阻断条件说明”：blocker 说明哪些条件会拒绝进入撰写准备，gate 说明哪些能力仍被关闭。它们的生命周期、包可见性、测试目标和响应使用点完全一致，放在一个内部 catalog 中更容易维护。新增 route owner 会让总文件数增加一；合并内部 catalog 刚好抵消这一增量，使总 `ops` Java 文件数保持 1,352。

服务层没有新增数据库写入，没有新增事务写模式。所有 service 仍然是 `@Transactional(readOnly = true)`，返回的是固定配置和来源 endpoint 组合。它们的工作更像“解释器”而不是“执行器”：把前一阶段审查包预检结果解释成下一阶段可以引用的撰写就绪证据。这个机制透明后，维护者就能判断后续拆分是否安全：只要输入仍是上游只读 endpoint，输出仍是只读响应，控制器路由仍是原字符串，那么拆包不会改变产品行为。

## Java 证据检查

Java 侧证据分三层。第一层是编译证据。迁包后最容易出错的是 root controller、下游 reader 和测试包还找不到新 FQN，所以我用 `test-compile` 做 compiler-guided 检查。第一轮暴露 root controller 与下游 `InstructionPreflight`、`SignedApprovalDraftProfileSection` 缺少 import；第二轮暴露测试包仍在 root；第三轮暴露 gate 测试类名被机械替换重名，以及新测试包还引用 root package-private route path。逐轮修完后，`test-compile` 通过，说明包边界已经闭环。

第二层是计数证据。v1816 结束时 root `ops` 直接 Java 文件数是 1,025。v1817 保留两个 controller，迁出十五个实现文件，删除独立 gate catalog，新增一个 route owner，最终 root 直接 Java 文件数降到 1,009。总 `ops` Java 文件数仍是 1,352。这个结果被三个历史 ratchet 和新测试共同约束：`MAX_ROOT_OPS_MAIN_JAVA_FILES`、`EXPECTED_ROOT_OPS_MAIN_JAVA_FILES`、以及 exact root count 都从 1,025 调到 1,009，新建的 `ReadabilityUpkeepOpsConsolidationExtractionV1817Tests` 还专门验证窄包存在、代表性文件迁出、controller 保留和总数不增长。

第三层是静态质量证据。因为 response record 换了包，SpotBugs 排除文件里对应的 FQN 必须同步，否则全量 verify 可能在 spotbugs 阶段报旧类名无效或新类名暴露内部 list。这个动作不是绕过质量，而是迁移既有接受项的位置：响应模型仍是同一个只读 DTO 形态，暴露列表的风险评估没有变化，变化的是类的命名空间。把配置同步到新 FQN，才能让质量门表达真实状态。

## mini-kv 证据检查

mini-kv 在这版没有代码输入，也没有运行输入。原因很简单：v1817 属于 Java 内部包结构维护，不改变跨项目 schema，不改变 evidence JSON，不改变 Node 聚合合同，也不改变 mini-kv 的只读 shard readiness 输出。mini-kv 对这条链路的价值仍然是上游只读存储和证据归档背景，而不是本版的直接编辑对象。

虽然 mini-kv 没被修改，v1817 仍然遵守它相关的边界。第一，不启动 mini-kv 服务，也不写入任何键值数据。第二，不移动 mini-kv 的历史归档、截图、解释文件或 `e/` 目录。第三，不把 Java 的包名变化同步成 mini-kv 的新合同，因为这次只是 Java 代码所有权变化，不是跨项目协议变化。这样可以防止本项目的维护性拆分变成多仓库连锁改动，保持“谁的内部问题谁自己消化”的协作方式。

如果后续要把同样思路复制到 mini-kv，应处理的是它自己的大文件拆分、归档膨胀和只读证据索引，而不是因为 Java v1817 去改 mini-kv。这个判断很重要：并行项目的可维护性治理可以相互借鉴，但不能把一个仓库的内部包结构当作另一个仓库的合同输入。v1817 的输出只给 Java 后续版本消费，尤其是 `InstructionPreflight` 迁移。

## 阻断与安全边界

本版最核心的安全边界是“撰写就绪不等于撰写执行”。名字里有 authoring，很容易让人误解成系统开始生成签批草稿，但代码实际做的是相反的事：它证明草稿文本仍然缺席，签名材料仍然缺席，审批授权仍然缺席，写路由仍然关闭，值导入仍然为零。服务返回的是 readiness response，不是 draft artifact。这个边界在 blocker、gate、requirement 三层都被重复表达。

阻断列表说明缺少哪些条件时不能进入下一步，门禁列表说明哪些能力即使进入准备态也仍然关闭。合并后的 `BlockerCatalog` 同时提供 blocker 和 gate，反而让维护者更容易看到这两类约束是一组内部规则，而不是散落在两个文件中的重复概念。这里必须强调，禁止硬凑。合并是因为两个 catalog 的职责同源、调用点一致、包可见性一致，并且能抵消新增 route owner 的文件数，而不是为了让报表数字好看。

安全边界还包括“不碰运行时”。v1817 没有打开 active shard router，没有创建 managed audit connection，没有读取 credential value，没有解析 raw endpoint，没有 deployment 或 rollback 行为。所有新增和修改都在 Java 源码、测试、文档和静态配置层完成。本项目处在后期保养阶段，很多代码名带有生产、审批、签批、证据这些严肃词，但这并不意味着每个版本都应该开新能力。当前更重要的是把已有只读证据链从巨型 root 包里剥离出来，让未来真正的功能版本有清楚边界。

## 测试覆盖

测试覆盖分为迁移测试、路由测试、服务测试和治理测试。迁移测试是新建的 `ReadabilityUpkeepOpsConsolidationExtractionV1817Tests`，它不关心业务字段细节，而是关心包结构是否符合维护目标：代表性 service、response、support、catalog、closeout service 必须在新包，root 不应再有这些实现文件，两个 controller 必须仍在 root，route aggregator 必须仍存在，root 文件数不得超过 1,009，总文件数不得超过 1,352。

原有 service 和 catalog 测试也随包移动。比如 foundation service、assurance service、requirement catalog、blocker catalog、support builder 测试进入新测试包，这让它们能继续访问 package-private 内部构造器和目录方法。controller 测试、route path 测试仍留在 root 测试包，因为它们测试的是公开入口和 root route aggregator 的行为。这个测试布局和生产代码布局一致：内部逻辑归新包，公开入口归 root。

下游测试也被纳入编译检查。`InstructionPreflight` 的 foundation 和 assurance slot catalog 继续读取 authoring readiness 的 endpoint；`SignedApprovalDraftProfileSection` 的 registry service 和 source catalog 继续消费 authoring readiness 的 catalog response。v1817 把这些依赖改成新包 import，并把服务 `ENDPOINT` 改成 public immutable string。测试的意义不只是确认能编译，而是确认下一站依赖的输入仍然存在，链条不会因为拆包断裂。

## 实际工作量说明

这版的工作量主要在“安全拆分”而不是“新增功能”。实际做了包迁移、长路径搬文件、route owner 新增、root route delegation、service endpoint public 化、controller import 修复、下游 reader import 修复、测试包迁移、gate catalog 合并、SpotBugs FQN 迁移、三个 root-count ratchet 更新、新增 readability test、ops 文档、进度表、changelog 和本篇中文讲解。每一步都对应本项目的真实维护问题：root 包过大、route owner package-private、下游 endpoint 读取跨包、响应 record 迁移后的静态分析配置、以及后续版本需要知道下一刀切哪里。

这里没有把无关功能塞进同一版，也没有为了凑字数虚构 mini-kv 或 Node 改动。禁止硬凑的意思是，如果讲解写不长，就应该加大代码和验证的真实工作量，而不是堆形容词。本版之所以能写成较长说明，是因为它确实处理了多层输入输出：上游输入来自 v1816 review package preflight endpoint，下游输出给 v1818 候选的 instruction preflight，内部输出是只读 readiness response，工程输出是 root 文件数下降和总文件数不增长，维护输出是文档、测试和质量门同步。

从工程范式看，这是一版中等偏大的维护性版本。它没有改变业务合同，但改变了代码的所有权结构；它没有让系统更“能做事”，但让系统更清楚哪些事仍然不能做；它没有扩大运行面，却减少了维护者在 root 包里搜索的成本。后续如果继续沿着链条推进，`InstructionPreflight` 将成为自然下一站，因为它现在已经只依赖公开的 authoring readiness endpoint，可以按同样方式迁入窄包。

## 一句话总结

v1817 把签批草稿撰写就绪注册表从 root `ops` 包迁入专属维护包，在不改变路由、响应、写边界和跨项目归档的前提下，把 root 文件数从 1,025 压到 1,009，并为下一步拆 `InstructionPreflight` 铺好公开 endpoint 和证据链。
