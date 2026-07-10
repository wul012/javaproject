# v1837 ReleaseApproval 验证组合拆分：让 ReceiptChain 成为唯一顺序权威

## 实际工作量说明

v1837 没有增加接口，却处理了本项目中一个典型的后期维护问题：对象已经被上游组装成一条完整的 `ReceiptChain`，下游代码仍把它拆成几十个参数逐项传递。旧 `ReleaseApprovalVerificationHintBuilder` 有 34 个 builder 字段，构造器重复接收并赋值 34 次；`build` 又接收七个基础提示、几十个 receipt、失败分类和执行边界。调用它的响应构建器必须连续写一百多行 getter。任何人增删一个阶段，都要同时修改链、构造器、字段、调用方、摘要器和证明方法，顺序稍有错位就可能改变 digest 或证明结果。

这一版先通过 CodeGraph 和源码核对确认：所有阶段 builder 与 receipt 已经同时存在于 `ReleaseApprovalRehearsalManagedAuditReceiptChainBuilder.ReceiptChain`，真正的问题不是缺少模型，而是下游没有信任这个模型。于是重构没有再造一个阶段清单，而是新增 15 行的 `ReleaseApprovalVerificationHintContext`，把七个基础提示、原 ReceiptChain、失败分类和执行边界具名聚合；提示构建、摘要构建和无账本写证明都从这个上下文出发。

实际改动覆盖六个生产类型和三类测试边界。提示构建器与 context 合并在 70 行文件中；摘要构建器从 675 行降到 421 行；响应构建器从 564 行降到 421 行；无账本写证明器与 contribution record 合并为 412 行 verification support；贡献目录 382 行。三个原本超过 500 行的文件同时退出热点清单，生产代码超过 500 行的数量从 38 降为 35，超过 750 行从 4 降为 3。

这里还做了一个看似朴素、实际很关键的取舍：摘要器和证明器在方法入口把上下文分量重新绑定为有语义的局部变量。表面看它增加了若干行，为什么不直接在三百多行表达式里反复写长访问链？因为局部绑定明确划出“输入适配层”和“原算法主体”的分界。评审者可以先检查每个旧变量是否准确对应一个 context 或 ReceiptChain accessor，再确认分界线之后的摘要行与布尔条件没有重排。若直接全文件替换成长访问链，真正的算法变化会淹没在数百处文本噪声里，反而更难证明等价。局部变量也不会复制对象或值，它只给同一引用一个短名字，运行时语义与原参数一致。

本版同样没有为了文件数而取消类型边界。第一次完整 verify 发现两个新增 root 文件违反历史 805 文件 ratchet；处理方式不是把上限改成 807，也不是把 context 与 evaluator 的逻辑重新混回主方法，而是让 context 与提示构建器同文件、让 evaluator 与 contribution 同属 verification support 文件。Java 顶层包内类型仍然独立，职责和反射边界仍在，同时 root 与 total ops 文件数零净增长。维护性的目标不是“每个类型都必须独占文件”，而是让每个修改原因有清晰所有者并服从仓库整体约束。

从运行成本看，这次拆分也没有引入昂贵复制。上下文记录保存的都是既有对象引用，ReceiptChain 本身仍只构建一次；局部绑定不会重新生成 receipt，贡献目录仍按原数量创建贡献对象，摘要器仍只生成一份行列表。新增对象只有一个很小的上下文记录和无状态辅助实例，相比响应中已有的大量证据记录可以忽略。换句话说，本版主要减少认知与变更成本，没有用显著的内存或计算开销交换可读性。

因此，性能审查的结论也有明确依据：对象数量基本不变，遍历次数不变，摘要次数不变，变化集中在编译期类型组织，而不是运行期业务路径。

这不是把长文件随意切开。摘要行主体、长布尔证明链、贡献 `List.of` 的次序都保留；改变的是输入从哪里来、由谁负责。讲解同样遵守“禁止硬凑”：以下内容会透明说明每一层输入、输出、顺序、阻断和验证机理，而不是用重复形容词填满篇幅。

## 入口路由

对外入口没有变化。ReleaseApproval rehearsal 仍由既有 ops 控制器和 `OpsEvidenceService` 提供，路由字符串、HTTP 方法、请求 header 和响应 JSON 都没有调整。`ReleaseApprovalRehearsalResponseBuilder` 仍是响应装配入口：它先规范化请求，构建基础 section，再调用 `ReleaseApprovalRehearsalManagedAuditReceiptChainBuilder` 生成完整 ReceiptChain，最后组装响应。

旧实现到验证提示这一步时，会从 ReceiptChain 调用 34 个 `...Builder()` accessor，逐项送进 `ReleaseApprovalVerificationHintBuilder` 构造器；随后又从同一链调用几十个 receipt accessor，逐项送进 `build`。这相当于仓库已经把一箱按顺序编号的证据交给下一站，下一站却先全部倒出、手工排一遍，再装进另一箱。路由没变，但内部入口非常脆弱。

新实现的调用只做一件事：创建 `ReleaseApprovalVerificationHintContext`。context 中前七项来自已经构建好的 `RehearsalSections`，第八项就是原 ReceiptChain，最后两项是 failureTaxonomy 与 executionBoundaries。随后 `new ReleaseApprovalVerificationHintBuilder().build(context)`。调用方不再知道 34 个阶段 builder 的名字，因此新增阶段时不会在响应构建器形成第二个修改点。

这个 context 是 package-private record，与提示构建器同处一个文件但不是嵌套私有实现。它没有 Spring 注解，不是控制器参数，不是 Jackson 响应，也不跨包公开。它只是在同一 package 内把一个复杂函数的输入变成具名对象。路由层仍只看原请求与原响应，内部的类型安全增强不会泄漏成 API 变化。

## 响应模型

最终响应仍是 `ReleaseApprovalRehearsalResponseRecords.RehearsalVerificationHint`。字段顺序保持 hintVersion、responseSchemaVersion、warningDigest、noLedgerWriteProof、noLedgerWriteProved、nodeMayTreatAsProductionAuthorization、schemaFields、warningDigestInputs、proofClaims、nodeVerificationActions。固定的 proof 名称仍是 `NO_LEDGER_WRITE_PROOF_BY_RESPONSE_FIELDS`，生产授权布尔值仍为 false。

主提示构建器现在只有四个步骤。第一步通过 ContributionCatalog 从 context 的 ReceiptChain 生成 34 个有序 contribution。第二步把同一 ReceiptChain 交给 warning digest builder，再用同一 context 生成摘要。第三步调用 no-ledger evaluator 计算无写证明。第四步沿用原 catalog 与 contribution 顺序组合三组列表。因为四步都共享同一个 context/chain，摘要、证明和解释列表不可能各自从不同的 builder 集合取值。

`schemaFields` 仍由 `ReleaseApprovalVerificationHintCatalog.schemaFields()` 提供。`warningDigestInputs` 仍先放基础 warning 输入，再按 contribution 顺序追加各阶段 warning 输入，然后放基础 boundary 输入、各阶段 boundary 输入和最终 boundary 名。`proofClaims` 与 `nodeVerificationActions` 也保持“基础、逐阶段、收尾”的三段结构。代码搬迁没有改变任何字符串内容。

精确列表测试对这些顺序逐项断言，不是只判断包含关系。特别是 854 行的验证提示测试会检查很长的 warningDigestInputs、proofClaims 和 nodeVerificationActions；如果本版误交换两个阶段，哪怕 JSON 字段都还在，测试也会在具体位置失败。因此“响应不变”有机械证据，不依赖肉眼看起来相似。

## 上游证据配置

本版最重要的上游对象是 ReceiptChain。它由 `ReleaseApprovalRehearsalManagedAuditReceiptChainBuilder` 按固定流程构建：从 approval handoff marker 开始，经过 managed audit adapter、生产前置条件、质量拆分、实现 guard、外部迁移 guard、沙箱连接，再进入 endpoint credential resolver 的多阶段只读 echo，最后到 abort/rollback semantics contract。record 同时保存每一阶段的 receipt 和生成它的 builder。

为什么同时保存 receipt 与 builder？receipt 是已经生成的只读证据值；builder 还提供该阶段对应的 warning 输入名、boundary 输入名、proof claim、Node 验证动作以及 no-write 证明方法。验证提示需要二者配对：摘要读 receipt 内容，贡献目录读 builder 元数据，无写证明用 builder 校验 receipt。ReceiptChain 把这对关系一次固定，正适合作为唯一来源。

基础提示来自 requestContext、operatorWindowHint、ciEvidenceHint、artifactRetentionHint、liveReadinessHint、auditPersistenceHandoffHint 与 approvalRecordHandoffHint。它们先于 ReceiptChain 构建，表达请求、窗口、CI、工件保留、实时就绪、审计交接和审批记录交接。failureTaxonomy 与 executionBoundaries 则提供失败分类和最终执行禁止项。context 只引用这些现有对象，不复制字段值，因此不会出现新的默认值或规范化逻辑。

本版没有读取环境变量、凭据值或原始端点，也没有修改 RabbitMQ、数据库或 Spring profile。它处理的是已经存在于内存中的只读证据对象。所谓“上游配置”在这里不是新配置文件，而是对象图的来源和所有权；明确所有权后，摘要和证明才能保持同源。

## 服务层核心流程

流程起点仍是 `ReleaseApprovalRehearsalResponseBuilder.build`。它从 `OpsEvidenceResponse` 和请求构造 sections，生成 ReceiptChain、failureTaxonomy 与 executionBoundaries。之后构建 context 并交给提示构建器。提示构建器不保存可变状态，因此同一个实例或不同实例都不会跨请求残留阶段数据。

ContributionCatalog 的 public surface 没有扩大，它仍是包内静态目录。旧 build 方法有 34 个参数，新方法只有一个 ReceiptChain。在方法入口，代码把 chain accessor 绑定到原来的局部变量名，后面的 `List.of` 主体完全保留。这一点很关键：贡献顺序由既有列表决定，chain 只是提供对象，不会因为 record 字段声明或反射顺序自动重排。首项仍是 sandbox adapter approval schema guard，末项仍是 abort/rollback semantics contract，总数仍为 34。

WarningDigestBuilder 构造器现在只接收 ReceiptChain。它用 chain 中的 34 个 builder 创建既有 line-catalog Builders；build 方法只接收 context，并在开头把基础提示、阶段 receipt、失败分类和执行边界绑定成原变量名。从创建 `Receipts` 到 `ReleaseApprovalDigestSupport.digest(lines)` 的主体顺序没有变。摘要仍把同样的键和值按同样顺序编码，算法没有替换。

审查摘要同源性可以分三步进行。第一步看 context 中的 ReceiptChain 是否就是响应构建器刚生成并用于响应其他字段的那一个，而不是重新构建的副本；第二步看 digest builder 的构造器和 build 方法是否都从这条链取 builder 与 receipt；第三步看 line catalog 和 lines 列表是否仍显式追加而没有进入无序容器。三步都成立时，摘要键、摘要值和解释列表来自同一阶段对象图。若以后有人误用另一条链，结构测试和精确列表可能首先报警；即使字段内容恰巧相同，代码审查也能从单一入口发现分叉。

NoLedgerWriteProofEvaluator 承接旧主构建器中的完整布尔链。它先检查 request/CI/artifact/live/audit/approval 基础提示没有真实写入，再检查 managed audit、SQL、deployment、rollback、restore、credential 和外部连接均未执行，然后逐阶段调用 builder 的 no-write 方法验证对应 receipt，最后确认 Node 不得创建审批决策或写审批账本。原先数百行混在“组装响应”的类里，现在类名直接表达其责任。

Evaluator 的主体约 388 行，并与小型 contribution record 共处 412 行 support 文件；它不是新的混合巨型类，内容仍是一条完整安全证明，拆成任意小片会掩盖全条件 AND 关系。support 文件低于 500 行并被单文件预算固定。提示构建器不再掌握具体 credential-resolver 阶段名，响应构建器也不再逐项访问 builder，这两个结构门防止职责回流。

## Java 证据检查

第一项证据是生产编译。context 的十个 record component、ReceiptChain 的每个 accessor、摘要 line catalog 的 Builders/Receipts 类型都由 Java 编译器校验。首次 testCompile 还准确发现了旧 ContributionCatalog 单测仍调用 34 参数方法；测试随后改为先通过真实 ReceiptChainBuilder 构建链，再调用新入口。这不是绕过测试，而是让测试本身也服从唯一顺序来源。

第二项是反射结构测试。它确认 context 确实是 record 且恰有十个 component；WarningDigestBuilder 构造器只有一个参数；HintBuilder.build、WarningDigestBuilder.build 和 Evaluator.evaluate 都只接收一个 context。相比字符串匹配，反射不会受格式化换行影响，能真正阻止长参数列表回归。

第三项是源码结构测试。它要求提示构建器显式调用 ContributionCatalog、WarningDigestBuilder 和 Evaluator，并禁止出现具体 `sandboxEndpointCredentialResolver` 阶段名；要求响应构建器创建 context，同时禁止重新调用 `sandboxAdapterApprovalSchemaGuardReceiptBuilder()`。单文件行数门同步限制 70、421、421、412 等实际数字。

第一次完整 verify 共运行 1692 个测试，出现 23 个失败，全部来自历史 root/total ops 文件数与 census 覆盖门：两个新增文件让 direct-root 从 805 变成 807。业务响应、精确列表和编译没有错误。本版随后执行同文件合并，`ops-root-census.ps1 -Json` 恢复 direct-root 805、remaining 700、unassigned 0；23 个受影响历史测试类与新结构门一起重跑全部通过。这个过程证明旧 ratchet 真正发挥了作用，也证明本版选择修结构而不是修期望。

文件数量门并不只是追求数字好看。根包中的每个新文件都会增加导航、命名、归类和后续迁移成本；若每次重构都以“职责更清晰”为理由增加两个文件，长期仍会回到难以浏览的状态。把两个小型类型与最相关的现有边界同文件放置，既保留独立类型和测试能力，又尊重仓库已经建立的总量纪律，这正是局部设计与全局治理之间的平衡。

第四项是正式普查。脚本报告 1485 个生产 Java 文件，最大 1530 行，超过 500/750/1000 行为 35/3/2。相对 v1836，两个聚合预算都净下降，没有把代码挪入另一个超过 500 行的类。Spotless 已通过，SpotBugs `check` 也已通过且没有新增排除项。

## mini-kv 证据检查

v1837 只修改 Java 仓库的 ReleaseApproval 只读证据组合代码，没有写入 `D:\C\mini-kv`，没有移动任何历史 fixture，也没有改变 mini-kv 摘要输入、路径或 schema。ReceiptChain 中可能包含对上游 mini-kv 证据的只读描述，但本版既不重新采集也不改写这些值。

精确列表与 Spring 集成测试证明 Java 输出仍保持既有跨项目字段和顺序。这能证明合同没有被当前重构改坏，但不能冒充真实三项目联合运行。mini-kv 二进制没有在本版启动，Node 也没有被 Java 自动启动；真实联合 capstone 仍是独立阶段。

这个边界恰好与无写证明一致：Java 只读取已经交付的上游证据，不能写 mini-kv、不能启动其服务、不能连接生产 managed audit，也不能触发部署或回滚。context 重构让这些布尔证明更集中，却没有扩大 Java 的能力。

## 阻断与安全边界

第一条边界是只读。Evaluator 的所有条件以 AND 连接，任何一个写账本、建审批、访问外部审计、执行 SQL、触发部署/回滚/恢复、读取凭据或启动服务的标记为真，最终 `noLedgerWriteProved` 都会变成 false。搬迁时没有删除或放宽条件。

第二条边界是阶段配对。builder 与 receipt 必须来自同一个 ReceiptChain；调用方不能从别处拼一个 builder，再配另一条链的 receipt。这样每个 no-write 方法验证的是生成该阶段证据的同源 builder/receipt 对。

第三条边界是顺序。ContributionCatalog 的 `List.of`、摘要的 lines、warning/boundary 输入、proof claims 和 Node actions 都保留显式顺序，不依赖 HashMap、反射或自动扫描。新增阶段必须显式修改权威链和对应测试，不能静默插入。

第四条边界是对外不可见。context、evaluator、catalog 和 builder 全部包内可见，没有新控制器、Bean 或 API。响应 schema、route、header、fixture 和 digest 算法均未修改。若任何精确列表、Spring JSON 或历史测试发生变化，本版就不能封存。

## 测试覆盖

聚焦内容回归包括默认 verification hint、header-backed hint、沙箱连接 overview 和 ContributionCatalog。默认测试逐项检查 schemaFields、warningDigestInputs、proofClaims、nodeVerificationActions 和无写布尔；header-backed 变体证明换一组请求 header 后顺序仍稳定；catalog 测试固定 34 个贡献的首尾与过滤规则。

结构回归包括反射参数门、context component 数、调用方禁止逐项 builder getter、提示构建器禁止具体阶段名，以及四个关键文件的行数上限。维护预算测试再从全仓库角度固定 35/3/2，避免局部数字漂亮但其他热点悄悄增长。

真实 Spring 集成测试启动 Flyway 12 个迁移、H2、JPA 与 Web 上下文，验证 ops overview 和 headered live aggregation 能生成完整响应。它覆盖了从服务入口、ReceiptChain 构建、context 组合到 Jackson 输出的实际路径，而不是只调用一个私有 helper。

最终完整 `mvnw verify` 必须在本讲解已经落盘且自动讲解门能扫描到 v1837 的前提下运行。通过标准仍是全部测试零失败、JaCoCo 全部门达标、SpotBugs 0、Spotless 清洁。随后提交、tag、push 与远端 CI 全绿才能称为完成。

## 一句话总结

v1837 让 ReleaseApproval 验证链从“同一 ReceiptChain 被反复拆散传递”回到“一个具名上下文贯穿摘要、证明和响应”，在不改变任何只读合同字节与顺序的前提下，一次消除三个 500 行以上热点并用编译、反射、精确列表、Spring 集成和收紧预算把收益固定下来。
