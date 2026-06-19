# v1818 生产卓越拆分讲解：签批草稿指令预检注册表迁出

## 入口路由

v1818 处理的是签批草稿链路中的 `InstructionPreflight`。它位于 `AuthoringReadiness` 之后、`TextPackageIntake` 之前，作用不是创建草稿文本，也不是接收签名、审批授权或写入业务数据，而是把“下一步如果要人工准备草稿文本包，必须先有哪些只读指令字段、来源端点、阻断条件和门禁说明”列成稳定证据。通俗说，上一站 `AuthoringReadiness` 已经证明草稿撰写还没有真正发生，但具备了把需求翻译成指令预检清单的前置条件；这一站则把这些前置条件整理成更贴近“文本包准备”的字段和 guard。它的输入是上一站公开的 endpoint 常量，输出是只读 response，不产生任何草稿、不保存任何审批、不打开任何写路径。

本项目现在处在后期保养阶段，最大的维护负担不是某一个接口逻辑复杂，而是 root `ops` 包堆积了过多历史注册表文件。很多类名都以 `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraft...` 开头，放在同一个 root 包里时，维护者需要靠超长文件名猜测归属。v1818 的目标就是把这一组“签批草稿指令预检”实现从 root 包剥离出来，放进 `ops.maintenance.signedapprovalartifactdraftinstructionpreflight`。这样以后查这段链路时，不必在近千个 root ops 文件里翻找，而是直接进入一个表达业务阶段的窄包。

## 响应模型

这一版的输入可以分三层看。第一层是路由输入：外部仍然访问原来的只读 GET endpoint，例如 catalog、digest instructions、operator instructions、signature instructions、evidence instructions、value policy instructions、embargo instructions、draft text lock 和 closeout。URL 字符串没有变化，controller 仍留在 root `ops` 包，Spring 暴露面保持原样。第二层是证据输入：迁入新包的 slot catalog 继续读取 v1817 已经公开的 `ArtifactDraftAuthoringReadiness` endpoint 常量，用它们说明每个未来字段或指令来自哪一类上游 readiness 证据。第三层是工程输入：root route aggregator 仍作为兼容入口，但真正的 route suffix 所有权交给新的 `OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths`。

输出也有三层。业务输出是 `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse`，里面包含 project、version、readOnly、executionAllowed、source plan、各类状态位、slot 数量、guard 数量、gate 数量、slots、guards、gates、checks 和 status。工程输出是 root `ops` 直接 Java 文件数从 1,009 降到 993，总 `ops` Java 文件数仍保持 1,352。维护输出是新增文档、回归测试、SpotBugs FQN 迁移、进度表和 changelog，确保这次拆分不是只靠人脑记住，而是被测试和文档固定下来。

这里最容易误解的是“指令预检”这个名字。它听起来像系统开始下发指令，实际上正相反：它反复证明指令还不能执行。response 里的 `executionAllowed` 仍是关闭语义，draft text lock 表示草稿文本没有被系统持有，embargo instructions 表示审批授权、写路由、原始密钥和值导入仍被禁止。也就是说，v1818 的输出是给后续人工文本包准备使用的证据清单，不是给运行时执行器消费的命令。

## 上游证据配置

这次保留两个 controller 在 root 包，是为了稳定公共入口。controller 是请求映射层，很多既有测试和 Spring 组件扫描都围绕它们组织；把 controller 一起搬走虽然也能编译，但会扩大行为感知面，和本轮“只做维护性拆分”的目标不一致。相反，service、response、support、slot catalog、guard catalog、foundation slot catalog、assurance slot catalog 等实现类被迁入新包，因为它们表达的是内部只读注册表组装逻辑，不需要继续占用 root。

这类切法有一个好处：公共 API 看起来没有变化，但维护者看到的 ownership 已经变化。root 包继续承接历史入口，新包承接实现细节。以后如果要查“指令预检的字段从哪里来”，直接看新包里的 foundation 和 assurance slot catalog；如果要查“为什么禁止写入或禁止审批”，看 guard catalog；如果要查“每个 endpoint 返回什么”，看对应 service。这个边界让读代码的人不用先理解整个 ops 巨包，也能定位一段链路的机理。

本项目这几版一直沿用同一种路径依赖策略：先迁出被下游读取的一站，并把它的 `ENDPOINT` 做成 public immutable string；下一站再迁出时，出边依赖已经满足，只需要处理自己的入边。v1818 的上游就是 v1817，`AuthoringReadiness` 在上一版已经公开 endpoint，所以本版的 slot catalog 可以安全地继续引用它们。v1818 的下游则是 `TextPackageIntake` 和 `SignedApprovalDraftProfileSection`，这两个 reader 现在改为从新包导入本组 service endpoint。这样链条没有断，依赖方向也更清楚。

## 服务层核心流程

原来这一组 service 通过 root `OpsShardReadinessRoutePaths` 读取 route suffix。这个 root aggregator 是 package-private，迁包后 service 不能再直接访问它。最保守的做法不是把 root aggregator 改成 public，也不是在新包复制字符串，而是新增一个真正属于本家族的 route owner：`OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths`。它公开 `BASE_PATH` 和九个 suffix 常量，root aggregator 再委托这些常量。这样既保留原有 URL 字符串，又让迁出的 service 拥有合法、清晰的 route 依赖。

这种设计的关键是“字符串单源”。如果迁包时直接在每个 service 里硬编码 URL，看似少改几个文件，实际会让路由事实分裂。后续有人改 root aggregator 时，service 里的字符串可能不同步；有人看新包时，也不知道哪个字符串才是权威。现在 route owner 成为本家族的唯一 suffix 所有者，root 只是历史兼容聚合层，service 只读这个 owner，测试再验证 root 常量等于 split owner 常量。这样路线透明，出错面小。

本版还扩展了 route-path 测试。原来的测试只验证 service endpoint 等于 `BASE_PATH + root route`，现在又增加了一层：root route 必须等于新 route owner 的 suffix。这是一个很小但很值钱的测试，因为它防止未来维护时把字符串重新散落回 root。只要有人误改 root 或 split owner，测试会立刻指出两层常量不一致。

## GateCatalog 为什么合并

新增 route owner 会增加一个 Java 文件。如果只是搬 15 个实现文件，再加一个 route owner，总 `ops` Java 文件数会多 1。为了避免治理包继续膨胀，这一版把原来的独立 `GateCatalog` 合并进 `GuardCatalog`。这不是为了让数字好看而硬凑，也不是把无关概念塞在一起；这里的 guard 和 gate 都是同一组指令预检的只读约束目录，生命周期一致，调用点一致，包可见性一致，测试目标也一致。guard 说明每个未来字段缺失或违规时如何拒绝，gate 说明哪些能力在预检阶段仍然关闭。放在一个内部 catalog 里，比散在两个文件里更容易维护。

合并后的 `GuardCatalog` 同时提供 `allGuards()`、`guards(...)`、`allGates()` 和 `gates(...)`。原来的 gate 测试保留为“GateCatalogTests”这个概念测试，但它调用的是合并后的 guard catalog。这说明代码文件所有权变了，测试意图没有丢。服务层也从调用旧 `GateCatalog.allGates()` 改为调用 `GuardCatalog.allGates()`。最终结果是 root 文件数下降 16，总 ops 文件数不增加，内部目录关系更紧。

这里也体现了“禁止硬凑”的规则：如果一个文件只是为了抵消 route owner 而被删除，但职责并没有合理归宿，那就是危险重构。v1818 没有这么做。gate 被合并，是因为它和 guard 在这个家族里本来就共同描述“指令预检不能越界”的防线。合并后，概念仍然清晰，测试仍然覆盖，调用点更少，维护成本下降，这才是有价值的拆分优化。

## 下游消费如何对齐

`TextPackageIntake` 是下一站，它的 foundation field catalog 和 assurance field catalog 会读取本组 instruction preflight service 的 endpoint，用来说明文本包字段来自哪一类指令预检证据。例如 package identity、instruction preflight digest、signature envelope、redacted value digest、raw secret embargo 等字段，都需要指向本组不同 endpoint。v1818 把这些 service 的 `ENDPOINT` 改成 public static final，并在下游 reader 中改为从新包 import。这样下一站不需要知道 service 曾经在 root，也不需要访问 root 的 package-private 常量。

`SignedApprovalDraftProfileSection` 也消费本组输出。它不是业务写入口，而是一个跨阶段 profile section 汇总器，会把 readiness、preflight、authoring readiness、instruction preflight 等阶段的 catalog response 组织成 profile 视图。v1818 更新它的 service import 和 source catalog response import，保证 profile 视图还能看到 instruction preflight 的只读 catalog。这个动作很重要，因为很多治理信息最后不是直接被 controller 调用，而是被汇总视图引用。如果只修 controller，不修 profile section，编译可能会失败，或者更糟的是证据视图缺了一段链路。

通过这两个下游对齐，本版形成了清楚的输入输出管道：v1817 `AuthoringReadiness` 输出 endpoint，v1818 `InstructionPreflight` 读取这些 endpoint 并输出自己的 endpoint，下一版候选 `TextPackageIntake` 再读取 v1818 endpoint。每一站都只消费上一站公开的只读证据，不越级拿内部 catalog，也不复制旧 route 字符串。链式拆分能持续推进，靠的就是这种单向、公开、不可变的 endpoint 边界。

## 阻断与安全边界

v1818 没有打开 write routing，没有 active shard router，没有读取 credential value，没有 raw endpoint，没有 managed audit connection，没有 deployment，也没有 rollback。它不启动 Java 服务，不启动 Node，不启动 mini-kv，不修改跨项目证据归档。所有变化都限定在 Java 源码包结构、只读 route 常量、测试、文档和静态分析配置中。对运行时用户来说，URL、HTTP 方法、返回形状和只读语义保持一致。

这一点要反复说明，是因为这些类名里出现了 signed approval、artifact draft、instruction、text package 等词，很容易让人误以为系统正在接近真实签批执行。实际上本项目目前的策略是先把只读证据链梳理清楚，把每一步的“不能做什么”讲透明，再考虑真正的功能边界。指令预检只告诉维护者和后续 reader：如果未来要人工提交文本包，哪些字段和证据应该被列出，哪些动作仍必须被拒绝。它不是任务调度器，也不是审批引擎。

因此，本版输入输出没有任何敏感值。输入是公开 endpoint 字符串和静态 catalog；输出是只读 response。即便 response 中出现 source endpoint，也只是说明证据来源，不携带密钥、签名原文或审批内容。SpotBugs FQN 迁移也只是让既有 response list 暴露评估继续指向新包名，不是新增安全例外。

## Java 证据检查

测试分为四类。第一类是编译证据：迁包后最容易出问题的是 root controller、下游 reader 和测试包找不到新 FQN，所以先跑 `test-compile`。它通过后，说明包声明、import、public service、public response、route owner 可见性已经闭环。第二类是 route 证据：route-path 测试验证 service endpoint 等于 root route，同时 root route 又等于 split owner route，确保 URL 字符串单源且未变。第三类是目录证据：新增 `ReadabilityUpkeepOpsConsolidationExtractionV1818Tests` 验证文档可发现、代表性实现文件已迁入窄包、两个 controller 仍留 root、旧 GateCatalog 不再直接留 root、root 文件数不超过 993、总 ops 文件数不超过 1,352。

第四类是质量门证据。三个历史 ratchet 被降到 993：`MAX_ROOT_OPS_MAIN_JAVA_FILES`、`EXPECTED_ROOT_OPS_MAIN_JAVA_FILES` 和 exact root count。SpotBugs exclude 里的 `ArtifactDraftInstructionPreflightResponse` FQN 被迁到新包，防止全量 verify 在静态分析阶段指向旧类名。后续还需要跑 focused tests、Spotless 和 full verify，确保这不是只通过编译的半成品。

文档证据也同步了。`docs/ops/signed-approval-artifact-draft-instruction-preflight-extraction-v1818.md` 说明这次拆分的所有权、route owner、边界和下一站；`docs/ops/README.md` 把它加入 ops 维护索引；`docs/production-excellence-progress.md` 记录 J28；`CHANGELOG.md` 记录版本事实；本中文讲解负责把机制讲通俗。这样一版代码不仅能运行，还能被后来的维护者读懂。

## mini-kv 证据检查

mini-kv 在本版没有被修改，也不应该被修改。v1818 是 Java 仓库内部的包所有权治理，输入来自 Java 内部上一站已经公开的只读端点，输出也是 Java 内部下一站要读取的只读端点。它没有改变跨项目契约，没有改变 evidence JSON，没有改变历史归档路径，也没有要求 mini-kv 提供新的键值、命令、快照或分片 readiness 输出。换句话说，mini-kv 对本版的正确参与方式就是保持不动，让 Java 自己消化自己的 root 包压力。

这种“不动”本身也是一种证据边界。四项目协作里，mini-kv 常常是更底层的只读存储和证据背景，但不是每一次 Java 维护性拆包都要牵动它。如果本版因为 Java 类名移动就去改 mini-kv 文档、改归档路径或启动服务，反而会把单仓库维护问题扩散成跨仓库风险。v1818 明确没有启动 mini-kv，没有写入数据，没有调整它的 `e/` 归档，也没有把 Java 新包名当作 mini-kv 新契约。这让本项目的拆分保持轻量、可验证、可回滚。

后续如果要在 mini-kv 复制同类治理思路，应该处理它自己的问题，例如大文件拆分、归档膨胀、只读命令证据索引和文档集中度，而不是因为 Java v1818 修改了包结构就联动 mini-kv。并行项目之间可以共享方法论，但不能共享不必要的改动面。本版的结论很简单：mini-kv 的输入为零，输出为零，验证重点是确认 Java 没有越界消费或要求 mini-kv 产生新事实。

换成更直白的话说，这一版的边界检查不是去证明底层存储已经准备好，而是证明 Java 自己没有把内部整理误传成跨项目任务。好的后期保养应该像整理工具箱：哪一层乱了，就在哪一层收纳；哪一层没有参与，就不要把它拉进变更单。这样做看起来克制，却能避免很多后来难以追踪的问题。维护者读到这里时，只需要记住三点：不启动，不写入，不迁移历史归档。只要这三点成立，本版和底层项目之间就是干净的只读关系。

这也是本项目后期保养最需要坚持的节奏：先把自己仓库里的边界讲清楚，再决定是否需要外部协作。没有新的契约输入，就不制造新的协作负担；没有新的运行输出，就不要求别的仓库背书。这样每一版的责任都能落在自己的提交、自己的测试和自己的文档上，后续追查问题时不会出现“只是改了一个包名，却牵出三套系统”的混乱。

## 测试覆盖

测试覆盖从入口到文档形成闭环。入口层有 controller 测试和 route-path 测试，确认 root controller 仍能调用迁入新包的 service，并且 endpoint 仍由原路由字符串组成。实现层有 service、slot catalog、guard catalog、gate 概念和 support builder 测试，确认迁包后内部只读目录仍能返回同样的 readiness 信息。下游层有 TextPackageIntake 和 SignedApprovalDraftProfileSection 相关测试，确认下一站继续读取本组公开 endpoint，而不是断在旧 root 包。

治理层有 `ReadabilityUpkeepOpsConsolidationExtractionV1818Tests`，它不检查某个业务字段，而是检查维护目标本身：文档必须进索引，代表性实现文件必须在窄包，旧 GateCatalog 不应留 root，两个 controller 必须留 root，root 文件数不超过 993，总 ops 文件数不超过 1,352。讲解层有 `OpsCodeWalkthroughArchiveComplianceTests`，它要求本篇使用标准章节、中文长篇说明、真实工作量解释，并包含“禁止硬凑”和“本项目”这些约束语义。这样测试不是只验证程序能跑，还验证这次维护是否真的可读、可追踪、可继续。

## 实际工作量说明

这一版不是很小粒度。它处理了长路径文件移动、包声明归一化、route owner 新增、root route 委托、service endpoint public 化、GateCatalog 合并、root controller import、下游 TextPackageIntake import、ProfileSection import、response FQN 迁移、root-count ratchet、v1818 治理测试、ops 文档、进度表、changelog 和中文讲解。它没有为了凑版本数添加无关业务，也没有碰 Node 或 mini-kv。所有工作都围绕本项目自己的 Java 维护问题展开。

从工程范式看，这一版的价值在于降低 root `ops` 包压力，同时保持行为不变。root 直接 Java 文件数从 1,009 降到 993，第一次压到千以下；总 `ops` Java 文件数没有增加，说明新增 route owner 的成本被合理吸收；下游链路继续可编译，说明 endpoint 依赖没有断；文档和测试同步，说明这不是“搬完就忘”的手工重排。后续如果继续推进，最自然的下一刀就是 `TextPackageIntake`，因为它现在已经只依赖 v1818 公开 endpoint。

## 后续维护怎么读这一刀

后来的维护者如果要复查 v1818，不需要从所有 diff 开始。最好的阅读顺序是先打开 route owner，看九个 suffix 是否仍和 root 聚合器一致；再打开两个 root controller，看它们是否只负责请求映射和 service 调用；然后进入新包，按 `CatalogService`、`FoundationSlotCatalog`、`AssuranceSlotCatalog`、`GuardCatalog`、`Support`、`Response` 的顺序读。这样能从入口、证据来源、阻断规则、响应构造一路看到输出，不会被超长类名带偏。

如果要判断这一版以后还能不能继续拆，重点看两个信号。第一个信号是下游是否只读 public `ENDPOINT`，不能重新依赖 package-private 内部 catalog；第二个信号是总文件数是否因为新 route owner 持续膨胀。如果下一版 `TextPackageIntake` 也能找到一个合理的内部 catalog 合并点，且 route 字符串仍由 split owner 单源维护，那么链式拆分就可以继续；如果找不到合并点，也不应该为了数字勉强删除职责清晰的文件，而应接受总量小幅变化并在文档里说明原因。这个判断比机械追求每版固定减少多少文件更重要。

## 一句话总结

v1818 把签批草稿指令预检注册表从 root `ops` 包迁入专属维护包，在不改 URL、不改 response、不打开写能力、不触碰跨项目归档的前提下，把 root 文件数从 1,009 压到 993，并为下一站 `TextPackageIntake` 的维护性拆分铺好了只读 endpoint 边界。
