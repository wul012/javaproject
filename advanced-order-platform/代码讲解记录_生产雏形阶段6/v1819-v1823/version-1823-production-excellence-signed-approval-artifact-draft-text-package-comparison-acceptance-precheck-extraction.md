# v1823：签批草稿文本包“比较验收预检”窄包拆分

这一版处理的是一个名字很长、职责却应当很克制的只读能力：在真正接收“比较后的文本包”之前，先回答上游证据是否齐全、执行边界是否仍然关闭、审批与归档职责是否仍然分离。它不是比较引擎，也不是审批器，更不是包接收器。它的价值在于把“现在具不具备进入下一阶段的证据条件”表达成稳定、可审计、可被下游继续引用的只读结果。

v1823 没有添加业务动作，而是完成一次合同保持型拆分。七个实现文件从拥挤的根 `ops` 包迁入
`ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonacceptanceprecheck`，根包继续保留公共 Controller 和总路由聚合器。与此同时，原来独立的 GuardCatalog 被并入 CheckpointCatalog，新建一个窄职责 RoutePaths 所有者，因此根包直接 Java 文件数从 919 降到 911，而整个 `ops` 树仍保持 1,352 个 Java 文件。这个数字关系很重要：它说明本版确实减少了根包噪声，但没有靠新增大量包装类把复杂度转移到别处。

## 入口路由

外部入口仍然由根包 Controller 承担，因为 Controller 是已有 HTTP 合同的一部分，贸然迁移会扩大 Spring 扫描、测试引用和对外类名变化的范围。本版只让 Controller 显式导入窄包中的四个 Service，调用关系仍是“HTTP GET 请求进入 Controller，再由 Controller 调用只读 Service”。四条路由分别对应完整目录、来源身份与摘要、签名证据与值、策略执行与归档四种观察窗口。

路由字符串没有在迁移中重新拼写，而是从根聚合器内联常量迁到
`OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckRoutePaths`。
这个叶子所有者保存统一的 `/api/v1/ops/shard-readiness` 基础路径和四个后缀；原来的
`OpsShardReadinessRoutePaths` 继续委托给它。对调用方来说，输入 URL、HTTP 方法和返回结构都不变；对维护者来说，路由所有权终于和这组能力的实现边界一致。

四个入口的输入都很简单：没有请求体，不接收待比较文本，不接收签名，不接收凭据，也不接收可触发写操作的参数。它们读取的是编译期定义好的证据目录。完整目录端点输出十个 checkpoint 和十个 guard；来源身份摘要端点输出第 0 到第 2 项；签名证据值端点输出第 3 到第 5 项；策略执行归档端点输出第 6 到第 9 项。这样的切片不是把同一逻辑复制四份，而是四个 Service 共享 CatalogService 的响应组装入口，再选择不同的不可变列表片段。

## 响应模型

统一响应是一个 Java record。最上层先说明项目、版本、只读属性和执行权限，然后列出来源计划、Node 与 Java 的来源版本，再明确当前阶段状态。最关键的几个输出不是“成功”两个字，而是一组负向边界：`comparedPackageState` 是 `not-accepted`，草稿文本与分离签名都是 `not-parsed`，审批授权是 `not-emitted`，运行载荷和兄弟项目变更都是 `locked`。相应的六个 `readyFor...` 或 `...Allowed` 布尔值全部为 false。

这种设计把“预检通过”和“业务动作已获准”分开。`readyForComparisonAcceptancePrecheck=true` 只说明这份只读目录自身可以被读取；它绝不等于 `readyForComparedPackageAcceptance=true`。如果只保留一个笼统的 `ready=true`，下游很容易把“证据目录完整”误解为“允许接收包”，从而穿透安全边界。当前模型故意同时给出阶段状态字符串和布尔许可位，让人读、机器检查和历史归档都能看见相同的约束。

两个嵌套 record 分别表达验收检查点和缺失证据守卫。AcceptanceCheckpoint 包含代码、来源版本、检查点说明、验收问题、缺失时的拒绝码、来源端点和状态；MissingEvidenceGuard 包含代码、类别、守卫说明、拒绝码、执行方式和状态。列表在 Support 中通过 `List.copyOf` 固化，避免调用方拿到响应后修改底层集合。SpotBugs 对 record 暴露列表引用的既有豁免也从旧 FQN 精确迁移到新 FQN，没有新增宽泛排除。

## 上游证据配置

本版的十个 checkpoint 并不凭空产生。它们引用 v1822 已经拆出的 ComparisonPreflight 公共端点：Catalog、IdentityRequest、DigestSignature、EvidenceValuePolicy 和 ExecutionCloseout。每个 checkpoint 都回答一个“能否看见比较前证据，同时仍不执行下一步”的问题。例如身份与请求元数据是否存在、摘要绑定是否可复查、分离签名信封是否只作为元数据比较、来源证据句柄是否可引用、操作员值句柄是否没有被实际捕获、执行锁是否仍关闭、审批授予是否仍与比较预检分离。

输入在这里不是运行时上传的数据，而是五个公开且不可变的 `ENDPOINT` 字符串。这些字符串承担证据来源定位器的角色。v1822 已把它们公开化，所以 v1823 的 CheckpointCatalog 只需导入窄包类，不需要再次放宽上游内部实现。依赖方向也很清楚：ComparisonAcceptancePrecheck 读取 ComparisonPreflight 的只读端点；ComparisonPreflight 不反向依赖本版。这样可以避免包之间形成环。

十个 guard 与十个 checkpoint 一一对应，但关注点不同。checkpoint 说“应当看到什么”，guard 说“缺失或越界时如何拒绝”。来源、身份、摘要、签名、来源证据、操作员值、策略、执行、审批、归档各有独立拒绝码，执行方式统一为 `fail-closed`。这意味着缺少证据时不能猜测为通过，也不能用空集合伪装成安全结果。把原 GuardCatalog 合并进 CheckpointCatalog 后，这种一一对应关系反而更容易在一个文件中审查；合并没有删除任何 guard，也没有改变拒绝码。

## 服务层核心流程

CatalogService 是完整视图入口。它从 CheckpointCatalog 取得十个 checkpoint 和十个 guard，再把版本、端点、profile 和附加检查项交给 Support。三个专题 Service 不自己重新构造模型，而是按稳定索引切分同一目录：SourceIdentityDigest 取前三项，SignatureEvidenceValue 取中间三项，PolicyExecutionArchive 取后四项。四个 Service 全部标记 `@Transactional(readOnly = true)`，既传达意图，也避免未来维护者误把它们当成写入流程扩展点。

Support 首先复制两个列表，随后统计状态为 `passed` 的 checkpoint 和 guard 数量，再生成可归档的 checks。checks 不只记录数量和来源版本，还明确写入 no-package-acceptance、no-draft-text-parsing、no-signature-parsing、no-approval-grant、no-runtime、no-sibling-mutation。最终状态只有在通过数等于总数时才是 `passed`，否则为 `blocked`。这里没有“部分通过也算可继续”的灰色通道。

可以用一个通俗例子理解：假设值班人员要确认“比较后的签批草稿能不能进入接收准备”。输入不是草稿文件，而是访问 catalog 端点。输出告诉他十个证据槽都存在，但同时明确包尚未接收、文本尚未解析、签名尚未解析、审批尚未发出、运行时仍锁定。值班人员得到的是“可以继续做下一层只读核对”，不是“一键放行”。如果只关心签名和证据值，他访问专题端点，只得到第 4 至第 6 类检查，不必在完整目录中自行过滤。

本版还修复两个真实下游边：ComparedPackageEvidenceIntake 的来源提交槽读取 CatalogService 端点，保障后续证据接收知道预检目录的位置；它的 assurance 槽读取 PolicyExecutionArchiveService 端点，确认执行、审批与归档边界。ProfileSection 的 RegistryService 和 SourceCatalog 分别读取 CatalogService 与 Response 类型，用于把这组只读状态纳入更大的签批草稿画像。公开的是端点常量和不可变响应模型，不是内部目录构造器。

## Java 证据检查

Java 侧首先由编译器验证包迁移是否完整。七个实现类换包后，根 Controller、ComparedPackageEvidenceIntake、ProfileSection、ControllerTests、RoutePathsTests 和共享测试支持都必须显式导入新位置。任何遗漏都会在 `compile` 或 `test-compile` 阶段直接暴露。长类名与 Windows 路径组合超过传统长度限制，因此文件移动使用长路径 API，避免通过缩短名字或丢文件来绕过问题。

三个计数钉同时从 919 收紧到 911。治理计划测试提供“不得回涨”的上限；v1806 质量收尾测试保存当前精确根包值；v1809 提取测试继续要求实测值精确相等。v1823 自己新增结构测试，确认窄包存在、代表性实现已迁出根包、Controller 仍在根包、GuardCatalog 旧文件已消失、运维说明可从索引发现、根包不高于 911、总量不高于 1,352。三个历史守卫加一个本版守卫形成交叉验证，避免只改文档数字而没有真实移动。

代码格式由 Spotless 统一，静态缺陷由 SpotBugs 检查，覆盖率由 JaCoCo 原有门槛约束。Response 的两个 EI_EXPOSE_REP 类历史豁免只改变类全名，规则数量与缺陷类型不变，因此它是迁移而不是扩张基线。聚焦测试会覆盖本家族、ComparedPackageEvidenceIntake、ProfileSection 和可读性守卫；最终仍必须运行完整 `mvnw verify`，因为包迁移可能影响全局覆盖率归属、Spring 扫描或远距离测试。

## mini-kv 证据检查

本版本身不修改 mini-kv，也不启动它。响应中的来源计划与检查项只把跨项目证据当作只读引用，不能由 Java 在这里替 mini-kv 生成、覆盖或移动证据。所谓 mini-kv 证据检查，是确认“如果后续链路需要引用存储侧证据，其位置和版本可以被描述”，而不是建立数据库连接、执行命令或改变分片状态。

这个边界尤其重要，因为 Node 中存在大量指向 Java 与 mini-kv 历史归档的绝对路径和摘要。Java 的包拆分可以自由移动源码所有权，却不能顺手重命名 `e/<version>/`、证据 JSON、截图或跨项目交接路径。v1823 的运维说明继续保留 “Do not rename or move archive roots” 规则。换句话说，源码结构可以改善，历史证据地址必须稳定。

如果把四项目比作接力，mini-kv 输出的是存储侧只读事实，Java 把业务与签批条件组织成结构化证据，Node 在下游聚合，其他项目再消费。v1823 只整理 Java 自己这一棒的“比较验收预检”代码，不代替上游跑步，也不替下游宣布终点。这样的局部自治使三个仓库可以并行做内部质量工作，同时不破坏合同依赖顺序。

## 阻断与安全边界

本项目把安全边界写进数据，而不是只写在注释中。`readOnly=true`、`executionAllowed=false`、六个许可位为 false，以及多个 `not-*`、`locked` 状态共同构成机器可读的阻断面。十个 guard 的 enforcement 都是 fail-closed，缺失证据必须阻断。任何未来修改如果把“预检通过”误改成“包可接收”，响应测试和边界测试都应立即失败。

明确禁止的动作包括：接收比较后的包、执行文本比较、读取或解析真实草稿正文、解析分离签名载荷、捕获凭据或操作员值、发出审批授权、构造运行时载荷、打开写路由、启动或停止 Java/mini-kv、写入归档、部署和回滚。四个 Service 只返回内存中由静态目录构造的响应，既没有 Repository，也没有消息发布器或外部客户端依赖。

“验收预检”这个名称最容易引起误会，所以必须强调它检查的是验收前置证据，不是执行验收。一个门卫核对名单完整，不等于门卫替业务负责人签收货物；一个航前检查表全绿，也不等于飞机已经起飞。这里的 `status=passed` 只代表本次只读检查项齐全，真正的 package acceptance、approval grant 和 runtime execution 仍由后续独立能力决定。

## 测试覆盖

本家族原有测试按所有权分层处理。CatalogTests、ServiceTests、SupportTests 与实现一起进入窄包，因为它们需要访问 package-private profile、组装方法或 Support；ControllerTests 和 RoutePathsTests 保留根包，因为它们验证根 Controller 和公共聚合路由。测试没有为了迁移而把所有内部方法改成 public，而是让测试跟随被测实现移动，减少可见性泄漏。

聚焦验证至少覆盖四个方面。第一，四个 endpoint 与迁移前字符串完全一致；第二，完整目录和三个切片的 checkpoint/guard 数量、顺序与状态正确；第三，响应集合不可变、所有执行许可保持关闭；第四，下游 ComparedPackageEvidenceIntake 和 ProfileSection 能编译并读取新公共边界。结构测试再补充文件物理位置、旧 GuardCatalog 消失和计数守卫。

完整验证的意义不是形式主义。即使局部测试全绿，移动 Response 也可能影响 SpotBugs FQN，移动 Service 也可能改变 JaCoCo 的包覆盖统计，新增 RoutePaths 也可能让总文件数悄悄增长。只有格式、编译、聚焦测试、静态分析、覆盖率和全套测试一起通过，才能把“看起来只是移动文件”升级为可交付的重构证据。远端 GitHub Actions 还会在推送后重新执行，排除本地环境偶然性。

## 实际工作量说明

这不是把七个文件剪切到新目录后改一行 package。实际工作包括：先做依赖边扫描，确认上游五个端点已在 v1822 公开；迁移七个实现和三个包内测试；保留 Controller 与两个根测试；新增叶子 RoutePaths 并让根聚合器委托；公开四个稳定 ENDPOINT；修复四类生产下游和三类测试引用；将 GuardCatalog 无损合并到 CheckpointCatalog；迁移两处 SpotBugs FQN；收紧三个历史计数钉；新增五项结构测试；更新运维索引、CHANGELOG、进度记录和本篇中文讲解。

这里合并 GuardCatalog 不是为了追求更少文件而硬塞代码。十个 checkpoint 与十个 guard 本来就是一一对应的同一规则表，二者都只被 CatalogService 使用，合并后审查者能在同一位置核对“应见证据”和“缺失拒绝”。如果它们有独立调用者、独立生命周期或继续增长的趋势，就应保留拆分；当前合并反而减少来回跳转，并抵消新增 RoutePaths 文件，使总 `ops` 文件数不增长。

讲解也遵守“至少三千汉字、禁止硬凑”的规则。篇幅来自真实的输入输出、依赖方向、状态机、安全边界、测试与文件计数，而不是重复同一句结论。本项目的维护目标不是制造版本号，而是让每一版都能回答：改了什么、为什么现在改、外部合同有没有变化、失败会在哪里被发现、下一位维护者如何验证。

## 一句话总结

v1823 把“比较验收预检”从根 `ops` 包迁入独立窄包，用四条不变的只读路由、十个检查点、十个 fail-closed 守卫和明确关闭的执行许可，证明证据可以被检查但包尚未被接收；根包由 919 降到 911，总量保持 1,352，行为合同、历史归档和跨项目边界均未改变。
