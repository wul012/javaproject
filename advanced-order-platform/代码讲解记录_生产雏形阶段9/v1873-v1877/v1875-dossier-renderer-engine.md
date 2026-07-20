# v1875：验证卷宗 Renderer 收敛与无计数映射引擎

## 入口路由

本版本处理的是 `operatorcidossier`，也就是“最小只读门 operator CI handoff archive digest consumer package verification dossier”这一条很长业务语义在 Java 内部的验证卷宗层。名字虽然长，外部入口并没有变复杂：客户端仍向既有只读 GET 路由发起请求，根 `ops` 包中的 Spring Controller 接收请求，再调用已经公开的 dossier Registry Service。Controller 不解析凭据，不接收写入载荷，也不决定业务状态；它只完成 HTTP 到 Java 方法的适配。Service 的 `@Transactional(readOnly = true)` 仍然保留，因此这次重构没有把展示代码变成新的执行入口。

一次请求进入后，最先发生的不是拼 Markdown，而是读取上一层 consumer-package Registry Service 的结构化响应。可以把它想成审计人员拿到一只已经封装好的资料袋：资料袋里有来源摘要、manifest、受众、章节、验收项、CI matrix、边界锁、handoff checklist 和 scorecard。dossier 层不会重新抓取 Node 或 mini-kv，也不会重新解释原始文件；它只把这只资料袋转换成适合复核的“卷宗”。输入是一个只读 Java response，输出是另一个只读 Java response，中间没有网络下载、数据库写入、shell 执行或部署动作。

路由不属于 `ReportRenderer`。这是本次拆分最重要的边界之一：Renderer 不知道 URL，也不应该知道 Controller。原来的十二个 renderer/support 文件虽然都处于展示层，但超长类名把“路由来源、家族名、数据种类、输出动作”全部塞进了文件名，阅读者需要反复横向滚动才能确认它们只是字符串映射。v1875 把家族内唯一组合者命名为 `ReportRenderer`，让目录本身承担上下文。这样从入口追踪代码时，路径变成 Controller -> Registry Service -> Catalog/Scorecard -> ReportRenderer，而不是在十一组近乎同形的类之间来回跳转。

输入示例可以概括为：调用 dossier GET 路由，不携带执行参数。输出示例是一份状态为 `passed` 的卷宗响应，其中 `markdownSections` 有十段，第一段标题为 `Source Consumer Package`，最后一段为 `Scorecard`。这次改动前后，路由字节、HTTP 方法、响应字段、段落标题和内容行完全相同。改变的是内部如何表达“把结构化条目映射成不可变文本段”，而不是对外提供了新能力。

## 响应模型

dossier response 是一组嵌套 record 的聚合。十类核心数据分别是 `SourcePackageSnapshot`、`ProvenanceEntry`、`SectionDigest`、`AudienceRoute`、`CiLane`、`AcceptanceGate`、`BoundaryAudit`、`ReleaseChecklistItem`、`HandoffReceipt` 和 `ScorecardEntry`。最终还有 `MarkdownSection`，它只含标题和文本行列表。结构化字段供机器和测试做精确判断，Markdown 供人阅读；两者同时存在，避免把字符串重新解析成业务对象。

以 `AudienceRoute` 为例，输入字段包括 audience、reviewerLane、owner、packet 和 status。Renderer 输出一行 `audience -> reviewerLane | owner=... | packet=... | status=...`。这一映射没有判断谁可以执行，也没有根据 owner 打开权限；它只是忠实展示 Catalog 已经给出的事实。`CiLane` 同理，把 order、batch、commandFamily、replayGroup、readOnly 和 status 按固定顺序排成一行。`BoundaryAudit` 则展示 code、lockedBehavior、auditEvidence 和 status，明确说明哪些能力继续被锁住。

旧实现把十类映射分别放在十个 Renderer 中，再由一个 RegistryRenderer 按顺序聚合，最后借 RendererSupport 构造不可变 section。单看一个文件并没有复杂算法，但整体有十二个形状文件、429 行 Renderer 和 22 行 support；每新增一类数据就很容易照抄一个新类。问题不在每行代码，而在系统把“变化的数据映射”误表达成“变化的算法类型”。当结构相似文件已经远超三个时，继续复制会使审查者把时间花在比较模板差异，而不是检查真正的业务字段。

新 `ReportRenderer` 保留十个私有 typed 方法，每个方法只负责一种 record 的行格式；公开于包内的 `render` 方法只负责十段顺序。这个设计没有把所有内容压成难懂的通用 map 配置，也没有把业务字段名塞入反射或字符串表达式。编译器仍会检查 `entry.reviewerLane()`、`entry.releaseEvidence()` 等访问是否存在。共享的是稳定的“映射并构造不可变 section”行为，差异仍以普通 Java lambda 明确写出。

响应所有权没有变化。各 Catalog 继续返回不可变列表，RegistrySupport 继续组装最终 response，`MarkdownSections.mapped` 通过 Stream 的 `toList()` 产生不可修改快照，`MarkdownSection` 仍接收同样的标题和行列表。因此调用者既不能向最终行列表追加内容，也不会因原始 entries 后续变化而看到漂移。输入所有权、输出所有权和序列化形状均有现有测试覆盖。

## 上游证据配置

dossier 的唯一业务上游是 v1874 已经收敛过的 consumer-package Registry Service。上游响应版本为 `Java v1432`，状态是 `minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-ready`。dossier 读取它的 manifest 数、package section 数、CI matrix 数以及九段 Markdown，再形成来源快照和 section digest。这里的“digest”是只读证据摘要，不是重新访问归档目录，也不是修改 SHA manifest。

Service 先调用 source service 得到 consumer package，然后十个 Catalog 分别产出自己的类型化列表。Provenance Catalog 固定记录来源版本、endpoint、profile、digest version、digest state 和 consumer-package state；SectionDigest Catalog 遍历上游九段 Markdown，记录标题、行数、required 和 status；AudienceRoute Catalog 将四个受众包映射到审核 lane；CI Lane Catalog 保留 focused、grouped、build、smoke 的顺序；其余 Catalog 分别计算验收门、边界审计、发布清单和 handoff receipt。

这些 Catalog 没有在 v1875 被合并。原因很具体：它们拥有不同的业务数据和检查规则，变化轴不是相同的。Renderer 的重复是“同一个算法套不同 record mapper”，适合收敛；Catalog 的差异则是“不同证据如何产生”，贸然把它们做成一张万能表会隐藏类型关系和规则来源。优雅不是文件越少越好，而是让相同原因变化的代码放在一起，让不同原因变化的代码保持边界。

上游 v1874 的 Markdown 段落带有计数首行，例如 `manifest-entry-count=5`。dossier 根据这些行数生成 `SectionDigest`，所以字节级兼容尤其重要。如果 v1875 错误地给 dossier 自己的十段也添加计数首行，上游下游虽然都能编译，但 section 行数、后续 digest 和冻结输出都会变化。为防止这种“看起来更统一、实际上改契约”的错误，本版在生产改动前先运行旧实现并冻结全部十段、共 51 条内容行。

配置输入不包括 credential value、raw endpoint URL 解析结果、managed audit connection 或 runtime shell。响应里出现 endpoint 字符串，是作为来源证据展示的固定只读路径，不是让 Java 发起连接。出现 CI command family，也只是归档说明，不会由 Renderer 或 Service 执行命令。上游状态若不是 passed，各 Catalog 会按既有规则形成 blocked 状态；Renderer 仍只展示，不替业务规则做决定。

## 服务层核心流程

核心流程可以分成五步。第一步，Registry Service 调用 consumer-package service，取得一个完整、不可变的来源响应。第二步，依次调用十个 Catalog 生成 sourcePackages、provenance、sectionDigests、audienceRoutes、ciLanes、acceptanceGates、boundaryAudits、releaseChecklist 和 handoffReceipts。第三步，Scorecard Catalog 同时读取来源和前九类列表，计算十项 expected/actual/status。第四步，`ReportRenderer.render` 按固定顺序把十类列表转换成十个 MarkdownSection。第五步，RegistrySupport 把结构化列表、Markdown 列表、计数和总状态封装进最终 response。

v1875 只替换第四步的内部实现。前三步的数据生成顺序没有改，第五步的 response 构造参数没有改。Service 原来调用超长 RegistryRenderer，现在调用同包短名 `ReportRenderer`；传入的十个列表仍是同一批局部变量，顺序也完全相同。这个最小调用差异使影响范围容易审计：若 runtime 输出发生变化，原因只能位于新 renderer 或共享 engine，而不会混入 Catalog 重算、route 迁移或 response 改型。

共享引擎已有 `counted`：它验证 heading、countName、entries、lineMapper 和 sectionFactory 非空，然后用一条计数首行加映射结果构造 section。dossier 的格式没有计数首行，所以本版增加 `mapped`。它接收 heading、entries、typed line mapper 和 section factory，完成同样的非空检查，再把 `entries.stream().map(lineMapper).toList()` 交给 factory。输入是结构化列表和映射函数，输出是调用方定义的 section 类型；引擎不知道任何 dossier record，也没有跨家族依赖。

为什么不让 dossier 继续使用自己的 RendererSupport？因为 Support 只做 `new MarkdownSection(heading, List.copyOf(lines))`，而十个 Renderer 都先 stream/map/toList，再调用 Support。这样的抽象只包住最后一行，重复算法仍散在十处。`mapped` 把真正重复的行为放到共享位置，同时保留 typed mapper。为什么不强行调用 `counted`？因为那会新增输出行。为什么不把 `counted` 改成一个带布尔参数的方法？`includeCount=true/false` 会把两种语义藏在开关里，调用端不如两个明确动词可读。

新 `ReportRenderer` 为 206 行，低于本阶段 300 行家族上限。虽然它比单个旧 Renderer 长，但审查者现在只需打开一个文件，就能同时确认十段标题、顺序和字段格式；不存在十二个文件间的导航成本。其内部方法都短小、同级、无状态，未来某个字段格式改变时只触碰对应方法。若出现第三种稳定 section 语义，应先扩展共享 engine 的明确操作，而不是重新复制 support。

## Java 证据检查

Java 侧的第一份证据是先验 oracle。`DossierMarkdownTests` 在任何生产文件删除前，针对旧十二文件实现运行并通过 1/1。断言不是只看标题或 section 数，而是为十段逐一构造 MarkdownSection，冻结全部 51 条内容行。随后生产代码切换到 `ReportRenderer`，同一测试不改期望再次通过。这样可以排除“新实现与新测试一起自洽，却悄悄改了旧输出”的循环证明。

第二份证据是现有家族行为测试。SourceProvenance、SectionDigest、AudienceCi、AcceptanceBoundary、ChecklistReceipt 和 Immutability 六组测试继续检查来源版本、必填 provenance、九段 digest、lane 顺序、五个验收门、八个边界锁、release checklist、handoff receivers 和不可修改列表。根 ControllerMarkdownAggregate 测试继续从 Controller 入口调用同一公开 service，验证版本、endpoint、profile、十段标题和 scorecard。它们与 oracle、engine 测试合计先通过 17/17。

第三份证据是历史与全局 ratchet。v1847 当年证明 25 个实现文件被正确迁入 `operatorcidossier`，本版没有删除这份历史测试，而是把它收紧成：14 个当前文件必须存在，12 个旧 renderer/support 文件在新包和根包都不得存在，8 个当前测试文件必须存在，整个家族和测试目录只能继续缩小。v1866 全局 ops 上限从 1336 降到 1325。`OpsEleganceCensusTests` 同时锁定 96 个 Renderer、4809 行和 91 个长 Renderer 文件名。

第四份证据是 exact name baseline。生产 Java 文件从 1468 降到 1457；长文件 stem 从 1278 降到 1266，长标识符出现次数从 21063 降到 20996，唯一长名从 2837 降到 2825。测试新增 oracle，但把旧 TestSupport 改为 `DossierTestData`，因此测试文件 893 增至 894 的同时，长 stem 793 降到 792，出现次数 10206 降到 10189，唯一长名 3831 降到 3830。重建 baseline 后，结构、行为、优雅、变更门合计通过 43/43。

最终本地 `mvnw -B verify` 会在本篇讲解完成后执行，包含全部当前测试、JaCoCo floor、SpotBugs、Spotless 和 jar 打包。随后实现 commit 必须推到唯一 canonical remote `javaproject`，GitHub Actions 的 Docker 与 headless 作业都通过；再写 closeout 收据、推送第二次 CI，最后创建 annotated tag。任一环节缺失都不能把版本描述为完成。

## mini-kv 证据检查

本版本没有读取、编译、启动或修改 mini-kv。dossier 中出现的 mini-kv 边界，来自上游 consumer-package 已归档的 boundary lock，例如 `no-mini-kv-autostart` 和 `no-mini-kv-write-admin`。这些是“禁止发生什么”的证据，不是 Java 要执行的命令。Renderer 只把 lockedBehavior 与 auditEvidence 排成文本行，不会把字符串转换为进程调用。

四项目协作规则要求跨项目契约变化按 mini-kv -> Java -> Node 顺序传播，但本次是 Java 内部展示算法重构：route、JSON、response record、状态文本、归档路径和证据 schema 都不变。因此无需等待 mini-kv 新版本，也无需让 Node 同步消费新字段。Node 若读取同一 Java endpoint，看到的字节和段落顺序应与重构前一致。oracle 正是对此边界的本地机械证明。

验证时不会伪造 mini-kv fresh runtime 输出，也不会把 frozen fixture 描述成真实联调。本项目当前被允许的表述仍是单项目验证加已验证的只读跨项目集成；v1875 不提升成熟度标签。若以后真正修改跨项目字段，必须回到共同计划和 capstone，不能借 renderer 收敛顺带改变合同。

mini-kv 相关输入只有既有结构化字符串，输出仍是 dossier 中的锁定说明。例如输入边界项 code=`no-mini-kv-autostart`、lockedBehavior=`Node must not start mini-kv`、status=`passed`，输出是一条同字段顺序的 Markdown 行。没有 PID、端口、二进制路径或连接信息进入新 engine。`MarkdownSections.mapped` 甚至不知道 mini-kv 这个词，它只处理泛型条目和调用方提供的 mapper。

因此 mini-kv 的证据检查结论不是“mini-kv 已被本轮重新测试”，而是“Java 内部重构没有扩大 mini-kv 交互面，且原有禁止边界逐行保持”。这种诚实区分很重要：它防止代码讲解把静态兼容性说成实时运行成功，也让后续真正联调的负责人知道还需要哪些环境证据。

## 阻断与安全边界

本版继续锁住八项核心边界：不自动启动 Java、不自动启动 mini-kv、不开放 write routing、不读取 credential value、不解析 raw endpoint URL、不连接 managed audit HTTP/TCP、不开放 runtime shell、不允许 mini-kv write/admin。它们由结构化 BoundaryAudit 输入携带，Renderer 只展示 code、lockedBehavior、evidence 和 status。删除旧类不会删除这些业务条目，因为条目来自 Catalog；新 engine 也无权把 locked 改成 unlocked。

安全上最容易误解的是响应里出现 `command=focused`、`command=build` 或 endpoint 路径。这些都属于证据文本。Service 没有 `ProcessBuilder`，Renderer 没有网络客户端，`mapped` 没有 Spring、文件系统或反射依赖。它接受内存列表，输出内存 section。事务仍是 readOnly，Controller 仍是 GET，权限与 replay approval 逻辑都不在本次改动范围。

失败条件被写成机械门，而不是口头提醒。若任何旧 Renderer 文件重新出现，v1847 或 elegance gate 失败；若 Renderer 数、总行数或长文件名上升，全局门失败；若新文件名或标识符超过 40 字符，change gate 失败；若 output 只改一个空格、竖线、大小写或顺序，oracle 失败；若有人为让迁移通过而修改 oracle、fixture 或 Catalog 数据，应整版回退。

归档边界同样严格。v1875 只授权新增本篇中文讲解，不移动、不重命名、不压缩、不删除过去的 `e/`、截图或讲解文件，也不改 Node 已固定的绝对路径。归档脚本会重算 SHA-256 manifest、文件数和原始字节数；预算只按这一篇真实文件上调，没有给后续版本预留空间。

“禁止硬凑”在这里既约束讲解，也约束代码。不能为了达到行数把十个 mapper 拆回十个类，不能为了显得抽象而引入反射 DSL，也不能为了统一格式给 dossier 添加计数行。童子军规则要求触碰的 engine、service、测试夹具和历史门离开时都比进入时更清楚、更紧，而不是只完成一次重命名。

## 测试覆盖

测试分四层。第一层是 engine 单元测试：原有 `counted` 测试继续证明计数首行、空列表和不可变快照；新增 `mapped` 测试证明它只输出映射行、不生成 count，并且输入列表后续追加不会影响输出，输出列表也不可修改。这层不依赖任何 ops 业务类型，定位共享算法错误最快。

第二层是 dossier 精确 oracle。它直接调用 `DossierTestData.registry()`，比较十个完整 MarkdownSection。每个 section 都显式列出标题和所有行：来源一行、provenance 六行、digest 九行、audience 四行、CI 五行、acceptance 五行、boundary 八行、checklist 五行、receipt 四行、scorecard 十行。测试先在旧实现通过，再在新实现通过，形成迁移前后同一把尺子。

第三层是领域行为与边界测试。它们不重复所有 Markdown，而是检查结构化 response 的业务含义，例如来源状态是否 passed、每个 provenance 是否 required、CI lane 是否 readOnly、边界行为是否仍被锁住、handoff receiver 是否完整、所有列表是否不可变。根 controller test 额外验证 Spring 适配层仍调用同一个 service。下游 `ciaccept` 的测试夹具改为导入 `DossierTestData`，所以编译也证明公开测试边界仍能被后续家族消费。

第四层是仓库治理门。v1847 检查当前文件集合与已删除壳，v1866 检查全局 ops 上限，elegance census 检查 renderer 与家族收敛，JavaElegance/JavaChange 检查 exact name baseline 和新增命名，CurrentWalkthrough 检查讲解至少三千汉字、十个标准章节、含“禁止硬凑”和“本项目”，ArchiveRetention 检查唯一新增归档与哈希。最终 verify 再把所有业务、集成、覆盖率、静态分析和打包放到同一进程中。

测试不会通过放宽 floor、提高 cap 或删除历史测试来迁就重构。相反，本版把 ops 上限 1336 收到 1325，把 Renderer 上限 106 收到 96，把行数 5032 收到 4809，把长 Renderer 文件名 102 收到 91。未来再次回到旧形状，会立即被当前低水位拦截。

## 实际工作量说明

本版不是把十二个文件机械拼接后就结束。第一步先用 CodeGraph 和源文件 census 确认 service 的上游、调用者和下游影响；第二步现场运行旧 service，取得十段真实输出；第三步写 51 行精确 oracle，并在旧实现上单独通过；第四步才增加 `mapped`、编写 206 行 typed ReportRenderer、切换 service 和删除旧壳；第五步缩短测试夹具并修改所有直接与下游引用。

随后对历史门做语义升级：v1847 从“25 个迁移文件必须永远存在”变为“14 个当前文件必须存在、12 个旧壳必须永远消失”，同时把测试集合更新为 8 个当前文件。全局 v1866 上限、ops census、renderer census、长名 aggregate 和 exact baseline 全部按真实扫描收紧。技术说明记录需求证据矩阵、明确不做与失败条件，本篇讲解则把输入输出和机理展开。

量化结果是生产 Java 净减 11 个文件，家族 25 降到 14，Renderer 11 降到 1，旧 Renderer 429 行与 support 22 行被 206 行家族 renderer 和 12 行共享 engine 增量取代。全局 Renderer 行数净减 223，长 Renderer 文件名净减 11。测试虽然新增一份高价值 oracle，长测试文件和长标识符总量仍下降。这说明工作量买到的是可读性、回归确定性和真实债务减少，而不是只追求负行数。

本项目没有把等待 CI 的时间算成代码产出，也没有把文档字数当成功能规模。真正的工程工作量体现在：先验/后验同尺验证、跨层引用修复、历史门迁移、精确 census、完整 verify、两轮 canonical CI、closeout 和 tag。任何一项未完成时，进度表都只能写 pending。

后续家族可以复用本版配方，但不能盲目批量替换。若某个 renderer 有计数首行，用 `counted`；若只有映射行，用 `mapped`；若存在分组、排序或多行展开，应先识别新的稳定语义并写测试。抽象服务于真实共同点，不能把不同输出强压成一个开关迷宫。

## 一句话总结

v1875 在不改变任何路由、结构化响应、Markdown 字节或安全边界的前提下，把 dossier 的十一套展示壳和一个 support 收敛为一个短 `ReportRenderer`，并以共享 `MarkdownSections.mapped` 正确表达“无计数首行的类型化映射”。

这版的透明机理是：输入仍是 consumer-package 的不可变 response；十个 Catalog 仍生成十类结构化证据；Service 仍按原顺序聚合；唯一变化是十类列表经过一个 typed renderer 和通用 mapped engine 形成十段不可变 Markdown；输出仍由先验 oracle 逐行证明与旧实现一致。

它的价值不只是少 11 个生产文件，而是让维护者在一个文件中看到完整报告格式，让编译器继续检查 record 字段，让共享算法只承担真正重复的行为，并让历史门、全局门和 exact baseline 把收益永久锁住。

最终判断标准仍很朴素：代码更短只是现象，契约不变、证据更强、边界不扩、退化会失败，才是这次优雅化真正完成。
