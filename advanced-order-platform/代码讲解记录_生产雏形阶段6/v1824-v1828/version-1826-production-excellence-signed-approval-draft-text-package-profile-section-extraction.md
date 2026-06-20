# v1826 生产卓越：签署审批文本包 ProfileSection 注册表拆分讲解

## 入口路由

这一版的入口仍然是一个只读 HTTP 路由：`/api/v1/ops/shard-readiness/signed-approval-draft-text-package-profile-section-registry`。外部调用者看到的地址没有变化，控制器类仍然留在根 `ops` 包里，因为 Spring Web 的公开入口现在还集中在根包，贸然搬走控制器会让路由扫描、历史测试和文档索引同时发生更大波动。本项目这一刀的目标不是改变访问方式，而是把入口后面的注册表实现从根包里拆出去，让根包只保留真正需要暴露的门面。

输入可以理解成一次普通的 GET 请求。请求本身不带业务参数，不提交文本包，不上传证据，不携带审批人，也不传递任何运行时 payload。系统接到请求后，只调用 `OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryController.registry()`，控制器再把工作交给新的 `ops.maintenance.signedapprovaldrafttextpackageprofilesection` 包里的 service。输出是一个结构化响应，说明文本包 ProfileSection 注册表当前引用了哪些只读上游、分成哪些章节、每个章节由哪个 renderer 渲染、哪些字段被锁定、哪些禁用门仍然关闭。

这一点很重要：v1826 没有打开新的业务能力。它只是把一个已经存在的只读说明端点搬到更窄的维护包里。原来 route suffix 临时挂在 CandidateDocument route paths 下，语义不够清楚；现在新增 `OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRoutePaths`，让 text-package profile section 的路径归到 signedapproval 主题下。根 `OpsShardReadinessRoutePaths` 继续委托它，CandidateDocument 里的历史常量也继续委托它，因此旧测试、旧调用和旧说明都能拿到字节一致的路径字符串。

## 响应模型

响应模型的核心记录是 `OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse`。这一版把它从根包迁到 `ops.maintenance.signedapprovaldrafttextpackageprofilesection`，但字段没有改名、没有增删、没有改变含义。响应里最外层仍然保留 project、version、readOnly、executionAllowed、sourcePlan、registryState、endpoint、profile、各类 count、禁用布尔值、模块列表、来源列表、章节列表、字段列表、渲染结果、路由字段锁、gate 列表、checks 和 status。

从维护角度看，这个响应模型有两个价值。第一，它把只读边界说得非常直白。`readOnly` 是 true，`executionAllowed` 是 false，package acceptance、signed approval capture、approval grant、value import、runtime payload、secret value、write、sibling mutation 全部是 false。这些字段让人一眼看出，端点不是执行入口，只是生产就绪证据目录。第二，它把渲染层拆成可检查的数据：source 说明上游证据来自哪里，section 说明页面章节如何组织，field entry 说明每个章节固定展示哪些字段，rendered section 说明 markdown 片段如何生成，route field lock 说明哪些路由字段被锁住。

这一刀没有把响应字段简化成字符串拼接，也没有把 renderer 的结果写死在控制器里。相反，响应记录、source catalog、section catalog、field catalog、renderer、route lock catalog 和 registry support 仍然各自承担单一职责。迁包以后，这些职责聚在一个窄包中，读代码的人不需要在一千多个根包类里搜索同一前缀。输出的对象还是同一个契约，代码的位置变得更容易维护。

## 上游证据配置

这个注册表的上游证据一共有九路。前六路已经在前面几个版本抽出到独立包：TextPackageIntake、TextPackageReviewPreflight、TextPackageSubmissionPreflight、ComparisonPreflight、ComparisonAcceptancePrecheck、ComparedPackageEvidenceIntake。它们对应文本包从入口、复核、提交预检、比较预检、比较接受预检到已比较包证据摄取的连续只读链条。后三路还留在根包：ComparedEvidenceEvaluationPreflight、ComparedEvidenceCandidateBlueprint、ComparedEvidenceCandidateIntakePreflight。它们是比较证据候选侧的读模型，仍然没有被本轮打开迁移。

服务层输入不是原始 HTTP 请求参数，而是这些上游服务返回的只读响应。`SourceCatalog.sources()` 从每个上游响应取 version、endpoint、profile、status，再补上 Node 版本标记和 rendererGroup。前五个来源属于 submission 组，第六到第九个来源属于 compared-evidence 组。这样一来，后续 renderer 不需要重新理解九个上游服务的类型，只看统一的 `TextPackageSectionSource` 就能分组渲染。

这一版的一个细节是“混合边界显性化”。六个上游服务已经有 maintenance 子包，三个上游服务还在根包。迁包以后，如果不加显式 import，原来根包内的可见性会掩盖这种事实。现在 service、source catalog 和 test support 都显式导入根包里的三个 compared-evidence 服务或响应，也显式导入已抽出的六个上游服务。维护者打开文件就能看见：哪些依赖已经完成拆分，哪些还会成为未来候选，而不会误以为整条链已经全部迁出。

## 服务层核心流程

服务层的核心流程可以分成六步。第一步，registry service 调用九个上游只读服务拿到响应。第二步，source catalog 把九种响应规整成统一来源列表。第三步，section catalog 根据来源 code 生成章节，补上标题、renderer owner、profile 和 endpoint。第四步，field catalog 为每个章节抽取固定字段，保证每个章节有七个展示字段。第五步，renderer 按 rendererGroup 分派到 submission renderer 或 compared-evidence renderer，再由 renderer support 生成 markdown body。第六步，route lock catalog 固定 endpoint、profile、javaVersion、nodeVersionMarker 和 rendererGroup，registry support 统一计算计数、checks、gates 和最终 status。

输入和输出在这个流程里非常透明。输入是九个只读响应，输出是一个注册表响应。中间没有数据库写入，没有消息发送，没有状态机推进，没有审批对象生成，也没有向 Node 或 mini-kv 发请求。所有内容都在 Java 进程内从已有响应对象组合而来。这样设计的价值是，端点可以作为生产后期的解释面板：它告诉维护者文本包 ProfileSection 会如何引用上游证据，却不会因为被访问而改变业务状态。

这一版还把 `GateCatalog` 折入 `RegistrySupport.gates()`。这不是为了压缩文件而压缩文件，而是因为 gate 生成只依赖 support 里的 `EXPECTED_GATE_COUNT`，并且它只服务于 registry support 组装最终响应。保留一个单独的 GateCatalog 会让新增 route owner 使总文件数上升；把它合并到 support 后，总文件数仍保持一千三百五十二，根包文件数实实在在下降到八百七十四。这个取舍符合本项目后期保养的目标：拆分要让边界更清楚，同时不要制造新的零碎膨胀。

## Java 证据检查

Java 侧第一类证据是结构证据。根 `ops` 包的直接 Java 文件数从八百八十七降到八百七十四，说明十三个根包文件不再挤在主目录里。总 `ops` Java 文件数保持一千三百五十二，说明新增 route owner 被 GateCatalog 合并抵消，没有把问题转移成总量膨胀。`ReadabilityUpkeepOpsConsolidationExtractionV1826Tests` 会检查新说明文档可被 README 找到、代表性实现文件在窄包内、根包里不存在这些实现文件、独立 GateCatalog 已不存在、根控制器和根 route aggregation 仍然存在、新 route owner 存在、ProfileSectionHandoff 仍留给下一版。

第二类证据是契约证据。控制器路由仍引用根 `OpsShardReadinessRoutePaths.SIGNED_APPROVAL_DRAFT_TEXT_PACKAGE_PROFILE_SECTION_REGISTRY`，根 route paths 再委托到新的 signedapproval route owner。CandidateDocument 历史 route paths 也委托同一个 owner。这样可以证明路径所有权从语义上归位，但 HTTP 字符串没有变化。SpotBugs exclude 中响应类 FQN 也迁到新包，避免工具仍盯着旧根包类名，从而把已接受的不可变响应暴露模式错判成新增问题。

第三类证据是编译证据。迁包以后，原来根包默认可见的类都必须通过 import 和 public 边界重新暴露。根控制器导入新的 service 和 response，根控制器测试导入新的 test support，test support 改为 public，并且 service 与 source catalog 明确导入三个仍在根包的 compared-evidence 类型。这些变化让 Java 编译器成为边界审计器。只要哪个类还偷偷依赖旧包位置，test-compile 就会报错。

## mini-kv 证据检查

本版本没有修改 mini-kv，也没有读取、移动或清理 mini-kv 的归档目录。这里提到 mini-kv，是因为四项目统筹要求 Java、mini-kv、Node 的证据链保持边界透明。当前 v1826 属于 Java 内部只读注册表拆分，不改变跨项目 schema，不改 evidence JSON，不移动 archive root，不要求 mini-kv 同步版本。它对 mini-kv 的影响应当是零。

从依赖方向看，mini-kv 更像上游基础设施和证据来源之一，而这个 Java 端点只是展示 Java 如何组织签署审批文本包的 ProfileSection。端点不会调用 mini-kv，不会启动 mini-kv，不会改 mini-kv 的 WAL、snapshot、RESP server、shard readiness 或归档文件。若未来有 Java 端注册表引用 mini-kv 版本证据，也应继续通过只读响应或文档记录表达，而不是在 ProfileSection 注册表里打开运行连接。

因此本版的 mini-kv 证据检查结论是：无需同步修改，禁止借本次拆包移动 mini-kv 旧证据，禁止把 Java 的 route owner 调整扩散成跨项目归档重排。这正是“只做本项目”的边界。Java 可以继续消化自己根包的可维护性债务，mini-kv 继续保持现状，Node 也不需要为了这一刀消费一次全局协调。

## 阻断与安全边界

这版最关键的安全边界是“只读解释，不做执行”。响应中 packageAcceptedCount、signedApprovalCount、runtimePayloadCount、secretValueCount、writeOperationCount 都是零，对应的允许开关全部为 false。服务层没有 repository 写入，没有 RabbitMQ outbox 发布，没有 HTTP client 调用外部系统，也没有任何凭证读取。它只消费已有 Java 服务返回的只读对象，然后组织成注册表。

第二个边界是“不打开文本包能力”。虽然类名里有 TextPackageProfileSection，但它不是上传文本包、比较文本内容或提交审批的入口。它展示的是文本包相关读模型如何组成 ProfileSection。真正的 text package submission、comparison、acceptance precheck、compared package evidence intake 已经在前序版本中以只读注册表形式存在，本版只是汇总这些只读注册表的 endpoint、profile、version 和 status。

第三个边界是“不破坏历史路径”。过去这个 route suffix 放在 CandidateDocument route paths 里，这很别扭，但很多测试和历史说明已经引用它。如果直接删除 CandidateDocument 常量，可能造成历史守卫失败。v1826 的做法是新增真正的 owner，同时让旧常量委托新 owner。这样路径语义纠偏，历史入口仍可用，调用方不需要迁移。这个做法比一次性大扫除稳得多。

## 测试覆盖

测试覆盖分三层。第一层是原有业务形态测试搬迁。十三个包内测试随实现一起移到新 test package，继续验证 aggregate、boundary flags、catalog、field catalog、gate count、markdown stability、module catalog、registry service、renderer、route evidence、route lock、source catalog 等细节。根控制器测试保留在根包，因为控制器本身还在根包，它通过 public test support 创建 service，验证路由、endpoint、profile、version、readOnly 和 executionAllowed。

第二层是新增结构守卫。`ReadabilityUpkeepOpsConsolidationExtractionV1826Tests` 不是测业务逻辑，而是防止未来维护时把文件又塞回根包，或不小心删除 route owner，或让总文件数随着每次拆分继续上涨。它还检查文档索引，确保维护者能从 README 找到这一版说明。对于这种后期保养工程，结构测试和业务测试一样重要，因为问题往往不是某个算法错，而是文件组织在长期迭代中重新失控。

第三层是全局质量门。计数 ratchet 从八百八十七降到八百七十四，旧的精确根包计数测试也随之更新。SpotBugs FQN 更新保证静态分析基线不会误指旧类。讲解归档进入阶段 6 的 v1824-v1828 目录，并包含标准章节、中文长文、实际工作量说明、“禁止硬凑”和“本项目”这些门槛。后续 focused tests、Spotless、full verify 和远端 CI 会共同证明这一刀不是只在本地编过，而是符合整个生产卓越流水线。

## 实际工作量说明

这版的实际工作量不是“改一个 package 名”这么简单。第一步要先确认 ProfileSection cluster 的真实规模，避免把三段总量误判成一个小文件夹。v1825 已经完成 base ProfileSection，v1826 处理 text-package ProfileSection，剩余 handoff 留给 v1827。第二步要找出 controller、service、response、support、source catalog、renderer、route lock、test support 和各测试之间的可见性关系。控制器留根，服务和响应迁出，test support 变 public，这些决策都要和 Spring 注入、JUnit 包可见性、SpotBugs FQN、route constants 一起对齐。

第三步是路线所有权调整。不是简单把字符串从一个类复制到另一个类，而是新增 signedapproval route owner，让 root aggregator 和 CandidateDocument historical constant 都委托到同一处。这保证路径只有一个真正 owner，也避免旧入口突然失效。第四步是 GateCatalog 折叠。它依赖 support 里的计数常量，单独存在没有独立抽象价值；合并后减少一个类，抵消新增 route owner，保持总文件数不变。这个动作体现的是工程后期保养的判断：拆分要刀刀到肉，不能一边拆根包一边堆更多散文件。

第五步是测试和文档同步。迁出的十三个测试要跟着包走，根控制器测试要改 import，结构测试要新增，旧 v1825 的“下一层还在根包”断言也要更新，否则它会和现在的事实冲突。说明文档、README、CHANGELOG、进度表、讲解归档都要记录同一组事实：根包八百八十七到八百七十四，总数一千三百五十二不变，第二段 ProfileSection 已完成，handoff 仍是下一刀。禁止硬凑的意思就是，如果这些事实讲不清，就不应该把版本说成完成；必须把输入、输出、边界和维护收益解释到足够透明。

补充说明这一刀为什么值得花时间。根包长期堆积会带来三个隐形成本：第一，定位成本上升，维护者想看一个注册表，却会在同一目录遇到大量无关控制器、目录、响应和测试支撑类；第二，边界成本上升，包内可见性会让依赖关系显得很自然，但搬迁时才发现哪些类其实应该是公开边界，哪些类只是内部帮手；第三，演进成本上升，每次新增只读证据都容易顺手塞回根包，短期省事，长期把目录变成难以判断归属的杂货堆。v1826 的实际价值就在于把第二段章节注册表从这个杂货堆里拿出来，放进一个按职责命名的维护包，并且用测试证明它不会再悄悄回流。

这次没有选择更激进地连同控制器一起搬走，是因为控制器属于公开入口层，当前项目仍然把大量只读控制器集中放在根包，单独迁走一个控制器会让扫描边界、路由聚合、历史测试和维护者习惯同时变化。好的重构不是把所有东西一次搬空，而是先把最厚、最重复、最容易失控的实现层切出去，让根包留下清晰门面。等后续控制器策略统一时，再集中处理入口层会更稳。也正因为如此，本版的输入输出没有任何业务变化，只有归属变化、可见性变化和守卫变化。

还要强调，讲解不是为了满足字数门槛而堆叠形容词。本版讲解必须说明真实工作：文件搬迁、包名修正、显式导入、路由所有权归位、门控生成合并、静态分析排除迁移、旧守卫改写、新守卫建立、索引文档补齐、中文归档补齐、局部测试和全量验证准备。这些都是本项目后期保养里能降低维护风险的动作。只有这些动作齐备，版本说明才有资格说“完成”。如果只是移动文件而不解释为什么移动、什么不该变、谁来证明没有变，那就不是工程范式，只是目录整理。

## 一句话总结

v1826 把签署审批文本包 ProfileSection 的实现从根 `ops` 包移入专属维护包，保留原路由和响应契约不变，把根包压力从八百八十七降到八百七十四，并为下一版 Handoff 拆分留下清楚、可验证、只读且不扩散到其他项目的边界。
