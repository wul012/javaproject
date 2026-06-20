# v1824：签批草稿文本包“已比较包证据接收”窄包拆分

这一版处理的是签批草稿文本包链路里一个容易被误解的中间层：ComparedPackageEvidenceIntake。它的名字里有 evidence intake，但它不是接收真实文件、不是导入值、不是解析签名，也不是批准包进入运行时。它做的事情更克制：把“未来如果要接受一个已经离线比较过的文本包，必须先看到哪些证据槽、哪些缺失证据守卫、这些证据应该来自哪些上游只读端点”整理成可查询的 Java 响应。换成通俗说法，它像一张交接清单，清单上写着十个必须被引用的证据位置和十个缺失时必须 fail-closed 的拒绝规则；它不替人收货，只说明收货前要检查什么。

v1824 的主要价值不是新增业务能力，而是把这张清单从拥挤的 root `ops` 包里迁到专门的 `ops.maintenance.signedapprovalartifactdrafttextpackagecomparedpackageevidenceintake` 包。迁移后，根包只保留已经对外稳定的 Spring Controller 和总路由聚合器；实现文件、响应模型、slot catalog、support、五个只读 service 和 endpoint refs 都进入窄包。这样维护者打开 root `ops` 时不会被这组内部证据拼装类淹没；真正要检查 ComparedPackageEvidenceIntake 时，又能在一个包里看到完整的输入来源、输出结构和边界说明。本项目后期最大的维护痛点就是 root 包长类名和 readiness 类过度堆积，所以这种迁移的价值在于降低认知成本，而不是制造更多版本号。

从机理上看，这一刀是在做三层分离。第一层是入口层，负责承接旧路由，不改变外部请求方式；第二层是证据目录层，负责说明未来证据应当来自哪里、缺失时怎样拒绝；第三层是治理层，负责让文件数量、文档索引、静态分析和讲解记录都能反向证明迁移没有越界。三层之间的关系很朴素：入口层只调服务，不知道清单如何拆分；服务层只读静态目录，不拥有写权限；治理层只检查事实，不替业务做判断。这样一来，维护者不用在一个巨大根包里同时思考路由、证据、文档和历史归档，而是可以沿着职责一层层定位问题。

这一版也把“证据接收”四个字重新钉牢：它接收的是证据目录的描述，不是接收证据本身。真正的文件、签名、摘要、审批和运行时都没有进入这个接口。假如后续有人想把它扩成上传口，最先冲突的会是响应里的关闭状态，其次是只读事务，再其次是测试中对缺失证据守卫的断言，最后是文档里反复强调的归档不移动规则。也就是说，代码、测试和说明共同形成了一圈防线，而不是只靠口头约定。

## 入口路由

外部入口仍然是 root 包里的 `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeController`。保留它是有意为之，因为 Controller 承载已有 HTTP 合同，贸然移动会扩大 Spring 扫描、测试构造和对外类名变化的影响面。Controller 的输入非常简单：五个 GET 路由都没有请求体，没有路径变量，没有查询参数，也没有任何能触发写操作的参数。调用方访问 catalog、source acceptance、submission comparison、identity digest signature、assurance closeout 五个只读窗口，得到的是编译期静态目录组装出来的响应。

v1824 新增了叶子路由所有者 `OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeRoutePaths`。这个类只放在 `ops.maintenance.signedapproval` 下，公开 `BASE_PATH` 和五个后缀常量。原来的 `OpsShardReadinessRoutePaths` 没有删除这些常量名，而是委托给新的叶子类；因此旧测试、旧 Controller、旧调用方看到的字符串完全不变。这个设计像把门牌号从一张巨大总表搬到对应楼层的门牌册里，但楼外牌匾仍然指向同一个门。

五条路由的语义也保持原样。Catalog 输出十个证据槽的全量目录；SourceAcceptance 只输出源验收预检相关的第一项；SubmissionComparison 输出人工提交引用和离线比较结果两类证据；IdentityDigestSignature 输出身份绑定、摘要匹配和分离签名观察；AssuranceCloseout 输出来源值句柄、策略执行锁、审批授予分离和归档收口。输入是读请求，输出是结构化只读响应，中间没有数据库写入、消息发布、外部进程启动或归档文件移动。

## 响应模型

统一响应 record 叫 `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse`。它最先给出项目名、版本号、只读标记和执行许可，然后列出来源计划、Node 与 Java 的来源版本，再列出当前阶段状态。这里最重要的不是 `readyForComparedPackageEvidenceIntake=true`，而是一组明确关闭的能力：`executionAllowed=false`，`comparedEvidenceState=not-accepted`，`signedDraftTextParseState=not-parsed`，`detachedSignatureParseState=not-parsed`，`approvalGrantState=not-emitted`，`runtimePayloadState=locked`，`siblingMutationState=locked`。这些字段共同说明：清单可以读，但包没有被接受，文本没有被解析，签名没有被解析，审批没有发出，运行时仍锁住。

响应里有两个嵌套 record。`EvidenceSlot` 描述一个未来证据槽：它包括代码、来源版本、槽位说明、要回答的问题、缺失时的守卫码、来源 endpoint 和状态。`IntakeGuard` 描述一个缺失证据守卫：它包括代码、分类、守卫描述、拒绝码、执行方式和状态。slot 回答“应该看到什么证据”，guard 回答“看不到时如何拒绝”。两者成对出现，使维护者不会只看见清单而忘记缺失处理。

Support 在组装响应时会先 `List.copyOf` 两类列表，避免调用方拿到响应后篡改底层集合。它还统计通过的 slot 和 guard 数量，再写入 checks：slot 数、guard 数、来源计划、来源 Node 版本、来源 Java 版本、no-evidence-fabrication、no-evidence-acceptance、no-draft-text-parsing、no-signature-parsing、no-approval-grant、no-runtime、no-sibling-mutation。最终状态只有在所有 slot 与 guard 都 passed 时才是 passed，否则就是 blocked。这个设计让“清单本身完整”和“真实业务动作允许”分开表达，避免一个模糊的 ready 字段被下游误解为放行。

## 上游证据配置

ComparedPackageEvidenceIntake 读的上游已经在前几版完成拆分并公开了只读 endpoint。第一组来自 v1823 的 ComparisonAcceptancePrecheck，它提供 source acceptance precheck 和 policy execution archive 的来源；第二组来自 v1821 的 SubmissionPreflight，它提供 manual submission reference；第三组来自 v1822 的 ComparisonPreflight，它提供 offline comparison result、identity request、digest signature、evidence value policy 和 execution closeout。v1824 没有再要求这些上游打开新能力，只是显式导入它们已经公开的 `ENDPOINT` 常量。

证据配置分成四个 slot catalog。SourceSubmissionSlotCatalog 管源验收和人工提交；ComparisonIdentitySlotCatalog 管离线比较结果和身份绑定；DigestSignatureSlotCatalog 管摘要匹配和分离签名观察；AssuranceSlotCatalog 管来源值句柄、策略执行锁、审批分离与归档收口。顶层 SlotCatalog 把四组列表拼成十项全量目录，并在本版合并保存十个 guard。这个合并不是为了硬凑文件数，而是因为 slot 与 guard 本来就是同一份清单的两面，且没有独立调用者。把它们放在同一 catalog 里，反而让审查者能在一个位置核对“应见证据”和“缺失拒绝”是否一一对应。

本版还把 `OpsShardReadinessComparedPackageEvidenceIntakeEndpointRefs` 一起迁入窄包并改成 public final class。这个类不是新业务入口，而是给后续 `ComparedPackageReview` 家族读取五个 evidence intake endpoint 的轻量引用表。原来它在 root 包里只是同包可见，现在后续 root 读者通过显式 import 读取它。这样做的好处是把“证据接收入口属于谁”说清楚：endpoint refs 属于 evidence intake，而不是属于更晚的 review。

## 服务层核心流程

CatalogService 是全量入口。它拿到 SlotCatalog 的十个 evidence slots，拿到同一 catalog 的十个 guards，再调用 Support 组装响应。它的 profile 明确指向 compared-package-evidence-intake-catalog，版本是 Java v1020。这个 service 没有 repository，没有外部 client，没有消息队列，也没有运行时配置读取；它只负责把静态目录转换成稳定响应。

SourceAcceptanceService、SubmissionComparisonService、IdentityDigestSignatureService 和 AssuranceCloseoutService 是四个切片入口。它们不是复制四套模型，而是复用 CatalogService 的 `response` 方法，只传入不同片段。SourceAcceptance 取第一项；SubmissionComparison 从 source/submission 中取一项、从 comparison/identity 中取一项；IdentityDigestSignature 取身份绑定之后的比较身份项和两个摘要签名项；AssuranceCloseout 取最后四项。切片结果都保留同样的 guard 集合，因为任何局部观察也必须继承全局 fail-closed 规则。

可以用一个日常例子理解：仓库管理员要接收一个供应商提交的“已比较文本包”。本接口不让管理员上传文本，也不让系统替管理员比较文本。它只是给管理员一张清单：第一项要有来源验收预检证明，第二项要有人工提交引用，第三项要有离线比较结果，后面还要有身份、摘要、签名、来源值句柄、策略锁、审批分离和归档收口。管理员可以看全量清单，也可以只看签名与摘要切片。无论看哪一页，系统都不断提醒：缺失任何证据都必须关闭，不能猜测通过。

## Java 证据检查

Java 侧最直接的证据是编译边界。十三个实现和引用文件搬入窄包后，root Controller、ProfileSection、ComparedPackageReview、ControllerTests、RoutePathsTests 和 test support 都必须显式 import 新包。如果有任何遗漏，`compile` 或 `test-compile` 会立刻失败。本版已经先跑 `mvnw -DskipTests compile test-compile`，用编译器确认主代码和测试代码都能从新包解析类型、常量和 response。

第二层证据是文件计数。v1823 后 root `ops` 直接 Java 文件数是 911；v1824 移出十三个实现/引用文件，并删除被合并的 GuardCatalog，新增一个非 root 的 route owner，所以 root 直接文件数下降到 897，总 `ops` Java 文件数仍是 1,352。三个历史 ratchet 同步收紧：治理计划的 root 上限、v1806 当前精确值、v1809 实测精确值。新增 v1824 结构测试再检查代表性文件已经不在 root、窄包存在、旧 GuardCatalog 不存在、root Controller 和总路由聚合器仍在 root、总量没有增长。

第三层证据是静态分析边界。Response record 里暴露列表字段，历史上通过 SpotBugs exclude 接受这个模式；本版只把两个旧 FQN 改到新窄包 FQN，没有增加新的宽泛排除，也没有修改规则类型。后续 SpotBugs 如果发现新的暴露问题，仍然会失败。Spotless 也会统一所有迁移文件的换行、缩进和 import 排序，防止长类名迁移后出现人工格式漂移。

人工复查时，可以按一条很短的路径走：先看根包是否只剩控制器，再看窄包是否包含响应、支撑、目录和服务，再看总路由是否委托给叶子路由所有者，最后看下游读取者是否只通过公开常量和响应类型连接。只要这四步都成立，就能说明代码所有权已经移动，而行为合同仍留在原位。这个复查路径比逐个打开长类名文件更省力，也更不容易漏掉真正的边界变化。

## mini-kv 证据检查

本版不修改 mini-kv，也不启动 mini-kv。代码讲解里保留 mini-kv 这一节，是为了说明跨项目证据边界，而不是暗示 Java 可以替 C++ 项目做事情。Java 这里只消费已经被上游版本描述过的证据来源，不读取 mini-kv 工作区，不移动它的 `e/<version>/`，不重写历史 fixture，也不生成新的存储侧证据。

四项目协作里，mini-kv 更靠近底层事实，Java 负责把业务可读证据组织成结构化只读接口，Node 或其他下游再消费这些接口。ComparedPackageEvidenceIntake 的十个 slot 有些最终可能会间接追溯到存储侧只读事实，但 v1824 不跨仓库修改这些事实。这个边界很重要，因为 Node 和 Java 里有历史归档路径、摘要和版本引用；如果为了代码整理顺手移动历史档案，就会让下游证据链接失效。

所以本版的 mini-kv 检查结论可以概括为：没有启动、没有写入、没有删除、没有迁移、没有变更远端，也没有改变任何跨项目历史路径。Java 只完成本项目内部的包所有权治理，让自己暴露给下游的证据入口更清晰。这样的局部自治，才允许 Java、C++、Node 在非合约工作上并行推进，而不会互相踩碎历史证据。

## 阻断与安全边界

安全边界在本版里不是注释，而是输出数据的一部分。每个响应都说明 readOnly 为 true、executionAllowed 为 false；每个 guard 的 enforcement 都是 fail-closed；每个缺失证据都有拒绝码；每个“可能被误会为下一步动作”的布尔位都明确关闭。这样下游即使只消费 JSON，也能看见这不是业务执行授权。

明确禁止的动作包括：接收已比较包、解析签批草稿文本、解析分离签名、生成或导入凭据值、发出审批授予、打开写路由、构造运行时 payload、写入归档、部署、回滚、启动或停止 Java/mini-kv。五个 service 只返回内存中由静态 catalog 构造的响应，没有 repository、没有事务写入、没有外部 HTTP 调用，也没有文件系统写入。`@Transactional(readOnly = true)` 进一步表达了这个服务族的运行意图。

这里尤其要防止一个误读：EvidenceIntake 的 passed 不等于包可接受。passed 只是说明这份清单的十个证据槽和十个守卫本身都被正确声明。真正的 compared evidence acceptance、approval grant 和 runtime execution 仍然属于后续独立能力。换句话说，检查表填写完整，不等于负责主管已经签字；门禁读卡成功，不等于仓库已经发货。

## 测试覆盖

测试按所有权分层迁移。SupportTests、ServiceTests、CatalogTests 跟随实现进入窄包，因为它们需要访问包内 support、slot catalog 和 package-private 组装方法。ControllerTests 和 RoutePathsTests 留在 root，因为它们验证的是 root Controller 和 root route aggregation 这个公开入口。这样的测试布局减少了为了测试而把内部方法 public 化的冲动，也让测试位置反映真实代码所有权。

聚焦测试需要覆盖五类内容。第一，五个 endpoint 和 root route constants 拼出的完整路径保持不变；第二，catalog 有十个 evidence slots 和十个 intake guards，状态全是 passed，guard 拒绝码都以 reject-missing 开头；第三，五个 service 的切片数量分别是十、一、二、三、四，并且运行时、审批、解析、兄弟项目变更都保持 false；第四，ProfileSection 和 ComparedPackageReview 能导入新 public endpoint/response 边界；第五，v1824 结构测试确认物理文件、文档索引、root ratchet 和总量 ratchet 都对齐。

完整验证不能只跑本家族测试。移动 response 可能影响 SpotBugs FQN，移动 service 可能影响 Spring 扫描和 JaCoCo 包覆盖归属，移动 endpoint refs 可能影响后续 ComparedPackageReview 的编译，新增中文讲解可能影响讲解合规门禁。因此本版后续需要跑 focused tests、Spotless、完整 `mvnw verify`，并在提交推送后等 GitHub Actions 远端再跑一遍。只有本地和远端都过，才算这刀收尾。

## 实际工作量说明

这版不是把文件拖进目录后改一行 package。实际工作包括：用 CodeGraph 和 grep 确认 ComparedPackageEvidenceIntake 的真实消费者；识别五个服务、四个 slot catalog、response、support、endpoint refs 和 root controller 的边界；用长路径方式迁移长类名文件；新建 route owner 并让 root route aggregator 委托；把服务 endpoint 改成 public immutable 常量；把 endpoint refs 改成 public final class；把 GuardCatalog 合并进 SlotCatalog 并删除旧文件，抵消新增 route owner；修复 ProfileSection、ComparedPackageReview、controller tests、route tests、test support 的 imports；迁移 SpotBugs FQN；收紧三个历史 ratchet；新增 v1824 结构测试；补 ops note、README、CHANGELOG、progress 和本篇中文讲解。

其中有一个值得记录的小教训：最初用普通 PowerShell `Move-Item` 移动长类名文件时遇到 Windows 长路径限制，只有短的 endpoint refs 先移动成功。后续改用 .NET 长路径前缀逐文件移动，才避免因为路径长度导致半迁移状态。另一个教训是脚本修改 ratchet 时不能用会被 PowerShell 扁平化的嵌套数组，否则可能把字符级替换误用到历史测试文件。发现后已经从 HEAD 精确恢复被误伤文件，再用补丁只改目标数字。这种过程说明工程范式不是永不犯错，而是错误要被快速定位、可恢复、可验证。

讲解也遵守“中文书写、至少三千汉字、禁止硬凑”的规则。篇幅来自真实工作量：路由、响应、上游、服务流程、Java 证据、mini-kv 边界、安全阻断、测试覆盖和实际迁移风险，而不是反复换句话重复结论。本项目后期维护最需要的不是漂亮口号，而是下一位维护者能沿着这篇说明找到入口、知道输入输出、理解为什么这只是只读证据清单、并能复现验证。

## 一句话总结

v1824 把签批草稿文本包链路中的 ComparedPackageEvidenceIntake 从 root `ops` 包迁入专属窄包，用五条不变的只读路由、十个证据槽、十个 fail-closed 守卫和公开的 endpoint refs 继续服务 ComparedPackageReview 与 ProfileSection；root 文件数从 911 降到 897，总 `ops` 文件数保持 1,352，行为合同、历史归档、跨项目边界和运行时安全状态均未改变。
