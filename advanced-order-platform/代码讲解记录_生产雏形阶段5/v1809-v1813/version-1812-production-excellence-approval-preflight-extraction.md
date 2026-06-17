# v1812 操作员证据取值供给审批预检注册表迁出代码讲解（J22）

本版本处理的是 `OperatorEvidenceValueSupplyApprovalPreflight` 这一组只读注册表。它在业务链路里的位置，比 v1811 的签批捕获预检更靠上游：先确认“一个操作员取值供给包是否具备进入审批准备阶段的前置条件”，再让下游的签批捕获预检引用这些前置条件。通俗地说，它不是审批系统本身，也不是签名系统；它更像进入审批室之前贴在门口的一张检查清单：身份别名是否只是引用、签批记录是否还没有生成、值是否还没有被接受、导入和运行时是否还锁着、脱敏摘要和来源证据是否必须存在。v1812 做的事情，是把这整张检查清单的实现从巨大 root `ops` 包里迁出，放入 `ops.maintenance.approvalpreflight`，同时保持对外 URL、响应字段、响应内容和安全边界完全不变。

这次不是为了凑一个小提交。它的价值在于把上游审批预检从 root 包剥离出来，使 v1810、v1811、v1812 形成一条更清楚的只读证据链：`ApprovalPreflight` 说明能不能进入审批预检，`SignedApprovalCapturePreflight` 说明能不能描述一次签批捕获，`CaptureArtifactPreflight` 说明能不能把签批捕获整理成后续工件片段。三个阶段都不写业务状态、不读密钥、不启动流程；它们只输出可审计、可测试、可版本化的证据说明。现在三段链路的实现都不再挤在 root `ops` 包中，后续维护者检索某一段时，不必在上千个 root 文件里猜它的归属。

## 入口路由

入口仍然保留在 root 包的两个 controller 中：`OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightFoundationController` 和 `OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightAssuranceController`。这样做是有意的。controller 是外部 HTTP 入口，继续留在 root 包，可以保持 Spring 扫描、全局路由聚合和历史测试外观稳定；真正迁出的，是 controller 后面的服务、目录、响应和 support 实现。

输入是普通 GET 请求，请求体为空，不需要用户传递凭据，也不接收会改变状态的参数。路由前缀仍然是 `/api/v1/ops/shard-readiness`，后缀包括 `approval-preflight-catalog`、`identity-signature`、`timestamp-window`、`redaction-digest`、`provenance-binding`、`value-rejection`、`zero-value-ledger`、`cleanup-receipt`、`import-firewall`、`digest-blueprint`、`archive-plan` 和 `closeout`。输出是 `OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse`。这些 URL 对调用方来说没有任何变化。

本版新增了 `OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePaths`。它不是新功能入口，而是 route suffix 的新所有者。root `OpsShardReadinessRoutePaths` 仍然保留原常量名，但每个 approval-preflight 常量都委托给新 owner。这样可以同时满足两件事：对外仍看见老的 root 聚合器；对内迁出的服务不再依赖 package-private 的 root route 类。服务用新 owner 组装 `ENDPOINT`，controller 和旧测试仍可从 root 聚合器验证路由一致性。

## 响应模型

响应模型仍然是一个不可变 record，核心字段可以按三层理解。第一层是全局元数据：项目名、版本号、是否只读、是否允许执行、endpoint、profile、status。第二层是审批预检状态：`readyForApprovalPreflight` 为 true，说明这是一张可读的预检表；但 `readyForSignedApprovalCapture`、`readyForApprovalGrant`、`readyForOperatorValueSubmission`、`readyForEvidenceImport`、`readyForRuntimePayload`、`readyForLiveExecution`、`readyForProductionExecution` 都保持 false，明确说明这不是执行入口。第三层是条目和策略：25 个 `ApprovalItem` 描述每个审批包输入位的来源、阶段、要求、阻断原因、证据文件标识和来源端点；20 个 `ApprovalPolicy` 描述身份、审批、时间窗、脱敏、来源、值封装、拒绝、零值、回执、导入和收尾规则。

举个最简单的例子：如果维护者访问目录端点，输入只是 GET URL；服务返回一个 response，其中 `itemCount=25`、`policyCount=20`，并列出“审批包 ID 只能是元数据”“操作员身份只能是别名”“签批人角色必须存在但不能在这里捕获签批”“值仍然不能被接受”“导入和运行时必须锁住”等条目。调用者得到的不是一份可执行审批，而是一份证明：当前代码把所有危险动作都挡在只读边界外。

v1812 迁包后，响应字段没有增加、删除或改名。record 的包名变了，但 controller 返回的 JSON 形状不变；测试仍然通过 service、controller 和 route-path 三类断言确保端点和值没有漂移。SpotBugs 中已经接受的 `EI_EXPOSE_REP` / `EI_EXPOSE_REP2` 响应模型例外，也只是从旧 FQN 改到新 FQN，并没有扩大基线池。

## 上游证据配置

这版最需要透明说明的是上游端点引用。`ApprovalPreflightItemCatalog` 的每个条目都要说明“我这条审批预检证据来自哪一个只读源端点”。它读取了七类上游服务的 `ENDPOINT` 字符串：取值供给 closeout、操作员复核清单、信封模板、脱敏策略、来源要求、验证矩阵，以及 adapter-preflight closeout。这些服务仍然留在 root 包，因为它们不属于本次审批预检家族；v1812 只把它们的 immutable `ENDPOINT` 字符串公开成 `public static final`，让新包能引用地址。

这里公开的是地址，不是行为。新包没有调用上游服务方法，没有触发上游流程，没有读取数据库，也没有改写任何上游响应。更直白地说，`ItemCatalog` 只是把“来源证据来自哪个只读端点”写进响应，像在清单里标注证据出处；它没有拿着这个地址去请求，也没有把 URL 当成 raw endpoint 解析，更没有打开 credential 或 managed audit connection。

下游边也被收紧了。v1811 已经迁出的 `SignedApprovalCapturePreflightInputCatalog` 过去要从 root 包导入 approval-preflight 的多个 endpoint 常量。v1812 后，这些导入改到 `ops.maintenance.approvalpreflight`。这让三段签批链路更自然：下游签批捕获预检读取上游审批预检端点时，读取的是上游家族自己的包，而不是 root 包里的残留实现。边界变清楚，字符串仍然一样。

## 服务层核心流程

服务层流程很简单，但正因为简单才应该被保持透明。每个服务先用 `BASE_PATH + suffix` 形成自己的 `ENDPOINT`，再从 `ItemCatalog` 和 `PolicyCatalog` 取一段只读目录，最后交给 `Support.response(...)` 组装成不可变 response。比如 identity-signature 服务取前 5 个身份相关 item 和前 5 个 policy；redaction-digest 服务取 8 到 12 这一段脱敏相关 item，以及 8 到 10 这一段策略；closeout 服务取完整 items 和 policies，并追加所有锁定条件已汇总的 checks。

迁包前后，这条流程没有变化。变化只在于代码所有权：service、item catalog、policy catalog、response、support 进入 `ops.maintenance.approvalpreflight`，controller 保留在 root。这样的拆分让维护者能看清“入口”和“实现”的分工：入口负责映射 URL，服务负责组装只读证据，目录负责列数据，support 负责统一不可变响应。每一步输入和输出都可解释，不会混成一个难维护的大文件。

这版没有试图把所有长类名一口气缩短。原因也很务实：先建立包边界，再谈命名缩短。没有包边界时，类名必须携带大量上下文；有了 `approvalpreflight` 包，未来如果继续做重构，就可以在包内引入更短的局部名称或更小的目录类，而不会损失语义。这就是“先拆所有权，再优化局部形状”的工程顺序。

## Java 证据检查

Java 侧证据主要分三类。第一类是编译证据：`test-compile` 已经证明迁出的 main/test 包名、controller import、route owner、上游 endpoint 可见性都能被 javac 接受。第二类是结构证据：新增 `ReadabilityUpkeepOpsConsolidationExtractionV1812Tests`，检查短说明是否从 ops README 可发现，检查代表性实现文件是否真的在 `ops.maintenance.approvalpreflight`，检查 root 包里不再出现这些实现文件，检查两个 controller 和 root route 聚合器仍留在 root。第三类是计数证据：root `ops` 直接 Java 文件数从 1,105 降到 1,089，全局 ratchet、质量 closeout 的精确计数和 v1809 的精确测量护栏都同步更新到 1,089；同时 package-private 的 `PolicyCatalog` 与 `ItemCatalog` 同文件保留，使新增 route owner 不会让整棵 `ops` Java 文件数从 1,352 往上增长。

route-path 测试也被增强。它不仅继续比较 root 聚合器常量和 service `ENDPOINT`，还新增了 root 聚合器委托到 split owner 的断言。这样未来如果有人把 root 常量偷偷改回硬编码，或者新 owner 的 suffix 发生漂移，测试会直接失败。这个测试不是为了覆盖业务分支，而是为了保护“迁包不改路由”的契约。

## mini-kv 证据检查

本版本没有触碰 mini-kv。它不读 C++ 键值数据，不建立 RESP 连接，不改 WAL、快照、命令解析或 `e/` 归档目录。文档里提到 mini-kv，只是为了明确四项目协作边界：Java 这次是在自己的只读注册表内部做包归属治理，不会影响 mini-kv 的运行状态，也不会要求 Node 或 C++ 同步改动。

同样，本版本没有启动 Node，也没有让 Node 自动启动或停止 Java / mini-kv。Node 侧可能引用 Java 历史证据路径和归档路径，所以本项目继续遵守“不移动历史归档”的规则。代码包迁移和归档整理是两个风险面，这里只做前者。

## 阻断与安全边界

v1812 明确不打开 write routing，不启用 active shard router，不读取 credential value，不解析 raw endpoint，不建立 managed audit connection，不部署，不回滚，不启动 Java、Node 或 mini-kv 进程。所有 endpoint 常量都是静态字符串，所有服务方法都是只读事务，所有响应都描述“仍然锁住”“仍然未捕获”“仍然未接受”“仍然不允许执行”。

审批预检容易让人误解成“审批已经可以发生”。本版通过响应字段和文档反复强调相反事实：它只证明审批前置条件以只读方式列出；它不产生签名、不接受值、不导入证据、不打开运行时载荷。这样的 fail-closed 描述，对后期工程维护很重要，因为越接近生产雏形，越要把“能看见证据”和“能执行动作”分开。

## 测试覆盖

测试覆盖分成迁入新包和留在 root 两部分。迁入新包的测试包括 assurance service、foundation service、item catalog、policy catalog、support，它们跟随实现一起移动，因为它们验证的是家族内部响应内容和目录切片。留在 root 的测试包括两个 controller 测试和 route-path 测试，因为它们验证 root 入口是否还能构造、root 路由聚合是否还能等价委托、controller 是否仍然暴露相同响应。

新增 readability 测试则负责维护工程纪律。它不关心某个业务字段的细枝末节，而是关心这版迁移有没有留下可发现文档、有没有真的降低 root 包压力、有没有保留 public controller、有没有把代表性实现从 root 剥离。它是后期保养的护栏：只要未来有人把同类实现塞回 root，或者忘记更新文档，测试会及时提示。

## 实际工作量说明

本项目这版的真实工作量包括：新建 `ops.maintenance.approvalpreflight` 包；迁出十五个生产实现物理文件和五个家族内部测试；把 package-private 的 `PolicyCatalog` 合并到同包 `ItemCatalog` 文件尾部，既不把它暴露成 public API，也用它抵消新增 route owner 带来的总文件数增长；新增一个 public route owner；改 root route 聚合器为委托；给两个 root controller 和三个 root 测试补新包导入；公开七个上游只读 endpoint 字符串；把 v1811 下游 `SignedApprovalCapturePreflightInputCatalog` 的 approval-preflight 导入改到新包；更新 SpotBugs baseline FQN；新增 v1812 短说明、README 索引、CHANGELOG、进度表和 JUnit 可读性护栏；把 root 计数护栏从 1,105 调整到 1,089，同时保持整棵 `ops` Java 文件数仍为 1,352；并编译验证所有 import、visibility、package 声明和测试源码都能通过。

这里明确遵守本项目的讲解规则，禁止硬凑字数。它围绕输入、输出、路由、响应、上游证据、服务流程、Java 证据、mini-kv 边界、安全阻断和测试覆盖，把本版本为什么值得做说明白。它也只讲本项目实际发生的工作，不虚构 Node、mini-kv 或外部部署动作。

再具体一点说，这一版的工作量不是“移动几个文件”这么轻。真正耗时的部分，是把移动后的所有边界逐一闭合：哪些类跟随实现迁走，哪些测试因为验证 root controller 必须留下，哪些上游端点只是证据地址而不是服务调用，哪些 `ENDPOINT` 可以公开而哪些业务方法不能公开，哪些计数护栏需要降到最新实测值，哪些历史文档只能补索引而不能整理归档路径。每一个选择都对应一个维护风险。如果把 controller 也顺手迁走，外部入口的稳定性会变差；如果不新增 route owner，新包就会继续依赖 root 包的 package-private 路由；如果把上游服务整体迁走，改动面会失控；如果只写短说明不写中文长讲解，后续维护者很难理解 approval-preflight 在签批链路中的位置。因此本版的解释篇幅来自真实工程判断，而不是为了通过测试凑段落。

## 一句话总结

v1812 把审批预检注册表从 root `ops` 包迁入 `ops.maintenance.approvalpreflight`，root 直接 Java 文件数从 1,105 降到 1,089；controller 和全局路由入口保持不动，新 route owner 接管 suffix，上下游关系只通过 public immutable `ENDPOINT` 字符串表达，所有 HTTP 路由、响应模型、只读安全边界和历史归档路径保持不变。
