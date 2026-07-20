# v1874：Consumer Package 声明式渲染复用与结构收敛

这一版处理的不是新接口，而是一个已经稳定运行、却被重复样板包围的只读证据接口。它把
Consumer Package 的九段 Markdown 从十个超长 Renderer 和一个 Support 中收回来，交给
一个短名家族组合器与 v1873 已建立的共享算法。目标不是追求“文件越少越好”，而是让读者
能够沿着数据来源、领域映射、不可变输出和机械验证四条线，在较短时间内确认行为确实未变。

## 入口路由

本版对应的外部入口仍是 consumer package registry 的只读 GET 路由。客户端不提交请求体，
也不携带任何可以触发执行的命令；Controller 只调用 Registry Service，并把响应 record 交给
Spring 序列化。路由字符串继续由 `OpsShardReadinessReleaseAcceptanceRoutePaths` 持有，
Service 中的 `ENDPOINT` 仍由同一个 `BASE_PATH` 与同一个 route field 拼接。换言之，本版没有
重新声明路径，没有建立兼容别名，也没有让短名重构穿透到 HTTP 合同。

一次请求进入后的第一步输入是“无业务参数的读取意图”，输出是完整的 consumer package
registry。Controller 不负责组织 Markdown，不知道 section 的数量，更不会逐段调用 Renderer。
这种分工很重要：HTTP 层只表达协议边界，Service 才表达聚合流程，家族内 `ReportRenderer`
只表达文本投影。旧代码虽然最终也能得到相同结果，但十个 Renderer 文件让读者误以为存在
十个独立子系统；新结构明确告诉读者，它们只是同一份报告的九个展示切面。

入口链路可通俗地写成：`GET 路由 -> Controller -> Registry Service -> Catalog 数据 ->
ReportRenderer -> Response`。输入端没有 credential value、raw endpoint、shell command 或
写入开关；输出端仍包含原版本号、原 endpoint、原 profile、原 checks、原 Markdown sections
与原 status。只要其中任一字符串改变，旧实现先通过的逐行 oracle 就会失败，因此“路由没改”
不是口头承诺，而是 Controller 测试、RoutePaths 所有权和完整响应断言共同约束的事实。

保留 Controller 在 root ops 包中也有明确理由：它是对外可见入口，属于全局 HTTP 导航面；
实现类则留在 `minimalreadonlygateoperatorciconsumerpackage` 家族内。v1846 的历史抽取原则没有
被推翻，本版只是继续收紧家族内部形状。读者从 Controller import 可以直接定位家族，从家族
Service 又能看到唯一上游 digest 服务，入口和实现之间没有增加转发层，也没有隐藏式反射。

## 响应模型

响应模型没有增加、删除或重排任何 record component。最外层响应仍携带 source digest、
manifest、consumer audiences、package sections、acceptance criteria、CI matrix、boundary
locks、handoff checklist、scorecard、Markdown sections 与 checks。`MarkdownSection` 仍然只由
heading 和 lines 两部分组成；`lines` 仍是不可变快照。新引擎只帮助构造这两个既有字段，
没有发明通用 Map，也没有把强类型 record 压扁成无类型键值表。

九段输出各有明确输入和输出。Source Digest 输入 `SourceDigestSnapshot` 列表，输出版本、端点、
digest state 与状态；Manifest 输入五个 `ManifestEntry`，输出 name、value、required 与状态；
Consumer Audiences 输入四个 audience，输出受众、owner、packet 与状态；Package Sections 输入
五个 section，输出 section、owner、source evidence 与状态。每段第一行仍是原有计数，例如
`source-digest-count=1` 和 `package-section-count=5`。

Acceptance Criteria 把 code、evidence 与 passed 投影为文本；CI Matrix 把序号、batch、command
family、read-only 与状态连成原格式；Boundary Locks 保留 code、locked 和 reason；Handoff
Checklist 保留序号、item、owner、ready 与状态；Scorecard 保留 name、actual/expected 与状态。
这些映射之所以留在家族 `ReportRenderer`，是因为字段意义属于 consumer package，而不是
所有 Markdown 报告共享的知识。共享层只知道“计数行在前、条目随后、结果不可变”。

这种数据与行为分界避免了两个极端。若把每一段继续放在独立类里，结构噪声会压过真实逻辑；
若把所有字段名、分隔符和状态规则都塞进一个万能模板，编译器就无法保护 record accessor，
领域变更还会退化为字符串配置。现在的组合器使用具体 record 类型和方法引用，字段拼写错误会
在编译期暴露；共享算法保持很小，只承担跨家族真正相同的部分。输出仍是 `List.of` 包围的九个
section，顺序从 Source Digest 到 Scorecard 完全不变。

不可变性也没有被“少写代码”牺牲。`Stream.toList()` 生成不可修改列表，section record 接收的
就是这份快照；最外层 `List.of` 同样不可修改。测试不只检查 heading 数量，还比较完整 record，
因此某一行被遗漏、换位、增加空格或改变布尔文本都会被识别。这里的输出模型透明到可以逐行
解释，也坚固到不能靠偶然的 section count 通过。

## 上游证据配置

Registry Service 的唯一跨家族上游仍是 archive-digest Registry Service。它先读取 digest
registry，再把这份强类型响应分别交给九个 Catalog。Source Digest Catalog 提取源版本、源端点
和 digest state；Manifest Catalog 把必须交接的五项元数据列成清单；Audience Catalog 定义
operator、CI、operator-CI 与 release-review 四类消费者；其余 Catalog 分别建立 package section、
验收条件、CI 顺序、边界锁、交接清单和总分卡。

这里的输入是已经通过上游 archive-digest 证据校验的响应，输出是 consumer package 自己的
领域 record。v1874 没有修改任何 Catalog 常量，也没有改 expected count。这样做刻意维持了
依赖方向：上游提供事实，当前家族解释如何消费事实，Renderer 只展示当前家族已经决定好的
结果。若 Renderer 自己重新判断 source status 或自行计算 expected count，就会把业务规则偷偷
搬进展示层；本版没有这么做。

Scorecard 是最容易混淆的部分。它依赖 source digest 以及前面七组 consumer 数据，计算八项
expected/actual。这个计算仍在 Scorecard Catalog 中；`ReportRenderer.scorecard` 只读取已经
算好的 name、actual、expected 和 status。因此报告组合器虽集中九段文本，却没有成为新的业务
中心。读者可以分别审查“数据为何通过”和“通过结果如何显示”，两类问题不再散落在十一个
形状相似的类之间。

下游 verification dossier 继续通过 consumer package Registry Service 获取数据。测试夹具从
超长名缩短为 `ConsumerPackageTestData` 后，下游只改 import 和构造入口，服务类型与返回类型
均未改变。这个改名不会进入生产 jar 的公开 API，也不会影响 Node、mini-kv 或 aiproj。它只让
测试依赖图更清楚：名称直接表达“提供测试数据”，而不是把整条上游链复制进类名。

上游链还保持严格的只读性质。执行时不会启动 Java 外部进程，不会自动启动 mini-kv，不会向
消息队列发送命令，也不会创建 managed audit 连接。Catalog 中保存的是版本、路径、状态与边界
说明，而非可执行句柄。此次重构只消费现有对象，不增加网络解析、文件读取或环境变量分支，
所以数据来源与运行环境的关系比以前更简单，而不是更隐蔽。

## 服务层核心流程

旧流程的最后一步先进入 `RegistryRenderer.render`，再由它依次调用九个 section Renderer；每个
section Renderer 都创建 `ArrayList`、添加计数行、遍历条目、拼接字段，最后调用 Renderer Support
复制列表并构造 record。十一份文件合计表达的算法其实只有一个：`count + map + immutable
section`。差异只在标题、计数键和条目映射。重复不仅增加文件数，还把一次报告的顺序拆散到
多个跳转点中。

新流程仍先生成同样的九组 Catalog 数据，然后只调用家族内 `ReportRenderer.render`。这个入口
以具体泛型列表接收九组数据，并用 `List.of` 清楚列出最终 section 顺序。读者在一个屏幕内就能
看见报告骨架；继续向下阅读时，每个私有方法只回答一种 record 怎样变成一行。方法名如
`sources`、`manifest`、`audiences` 和 `ciMatrix` 都低于命名预算，不再把完整路由历史塞进标识符。

每个私有方法调用共享 `MarkdownSections.counted`。该算法先对 heading、count name、entries、
mapper 和 factory 做非空检查，再用 `Stream.concat` 把计数首行与映射后的条目连接，最后通过
`toList` 固化不可变快照并交给 record factory。算法不认识 consumer package 的任何 record，
因此没有反向依赖；家族组合器通过 `MarkdownSection::new` 注入构造方式。第二个真实家族复用
证明 v1873 的抽象不是只为一个 case 包装旧代码。

为什么不把九个私有方法再压成一个巨大的数据表？因为 Java 的 lambda 可以保留字段类型和
编译期检查，而统一配置表通常需要弱类型参数或复杂泛型元组，读起来反而更难。当前结构用
约 176 行的组合器替代 380 行 Renderer 与 18 行 Support，减少总行数同时保留九个领域段落。
它没有制造超过 300 行的新巨型文件，也没有让一个方法接管所有字符串拼接。

服务事务注解仍是 `@Transactional(readOnly = true)`。组合器没有 Spring 注解、没有可变字段、
没有缓存，也不持有 Service；它是确定性纯函数集合。同一输入总会得到同一顺序与同一文本，
这使它适合被逐行 oracle 约束。Service 负责依赖注入和流程编排，Catalog 负责事实转换，Renderer
负责显示，三层责任在本版后比旧结构更容易从文件名与调用图直接看出。

## Java 证据检查

本版先在 v1873 已闭环 tag 上建立基线：生产 Java 1478、ops 1346、Renderer 115、Renderer
总行数 5236、长 Renderer 文件名 112。实现完成后，生产 Java 为 1468，ops 为 1336，目标家族
从 23 文件降到 13；Renderer 为 106，总行数 5032，长 Renderer 文件名为 102。Catalog 仍为
332，Service 仍为 375，说明下降来自删除重复展示壳，而不是把业务类改名规避统计。

长名治理也同步收紧。生产长文件 stem 从 1289 降到 1278，长标识符出现从 21124 降到 21063，
唯一长标识符从 2848 降到 2837。测试虽然因新增行为 oracle 从 892 增到 893 个 Java 文件，
长文件 stem 却从 794 降到 793，长标识符出现从 10216 降到 10206。原因是一个有价值的短名
测试替代了超长 TestSupport，而不是用删除测试换取数字好看。

`scripts/ops-elegance-census.ps1` 现在除全局 Renderer 指标外，还直接输出 consumer package
文件数。`OpsEleganceCensusTests` 把全局上限收紧为 106、5032 和 102，并要求 archive-digest 与
consumer package 两个目录都恰好只有 `ReportRenderer.java` 这一种 Renderer。v1846 历史门禁
要求当前十三个生产文件存在、十一份旧壳在家族和 root 都不存在、五份当前测试证据存在。

精确长名 baseline 由同一 census 脚本重新生成，只允许删除已有身份。`JavaEleganceGateTests`
同时锁住 aggregate：生产 1278/21063/2837，测试 793/10206/3831。这样无法通过“删一个旧长名、
换进一个新长名”来维持总数；身份集合和总量必须同时不增长。`OpsExtractionV1866Tests` 的
全树上限也从 1346 收紧到 1336，后续版本不能把这十份债务重新放回来。

文件大小方面，超过 500 行的生产文件仍为 32，最大仍为 738；本版 `ReportRenderer` 低于
300 行。这里没有声称整个项目已经完成三分提升，只记录可复现的局部下降。最终分数仍需外部
评审；仓库能做的是把每次改进变成会失败的门，而不是用主观形容词替代证据。

## mini-kv 证据检查

本项目的 consumer package 会在文本中陈述 mini-kv 自动启动被锁定，但 Java 代码不会连接、
启动或修改 `D:\C\mini-kv`。Boundary Locks 段仍逐行输出 `no-mini-kv-autostart` 与
`no-mini-kv-write-admin`，两个 locked 值都保持 true，reason 仍是原来的 archived boundary
说明。旧实现 oracle 对这两行做完整字符串比较，因此重构无法把它们遗漏或改成宽松表达。

Java 从上游 digest registry 取得的是只读证据对象，不是 mini-kv endpoint 或 credential。
本版没有新增 socket、HTTP client、shell、进程启动器或文件系统跨仓读取。即便 mini-kv 不在
本机，Registry Service 的 Catalog 和 Renderer 逻辑也能在普通单元测试中确定性运行。这个机制
说明跨项目对齐依靠合同和证据，而不是让一个项目偷偷控制另一个项目的生命周期。

为什么代码讲解仍保留 mini-kv 检查？因为该接口面向四项目协作，边界本身就是产品语义的一部分。
当维护者看到 `no-mini-kv-autostart`，应能追到 BoundaryLock Catalog、响应 record、Markdown
oracle 和 checks，而不必相信注释。本版对这些链路只做读取与展示重构，没有移动任何 mini-kv
历史 archive，也没有更改 Node 已固定引用的 Java 归档路径。

未来若关联项目需要对齐，可以消费相同的 consumer package 输出；但本轮不会提前改变 schema
来迁就尚未实施的下游设计。先保持 Java 自己的强类型模型、稳定路径和逐行文本，再由下游在
独立版本中明确适配，是风险更低的顺序。任何真实跨项目执行或写入能力都不属于 Renderer 优化，
若有需求必须另立合同、授权和集成测试。

## 阻断与安全边界

第一层阻断是事务语义。Registry Service 继续声明只读事务，流程中没有 repository save、消息
发布、HTTP 写请求或 shell 调用。第二层阻断是数据语义：CI Matrix 的每一项 `read-only=true`，
Boundary Locks 的八项全部 locked，checks 仍包含 no-upstream-autostart、no-write-routing 与
no-secret-value。第三层阻断是展示行为：Renderer 只接收已构造的 record，不拥有任何执行依赖。

共享引擎只处理内存中的字符串和列表。它对输入做 `requireNonNull`，不会在 null 时静默产出
不完整证据；同时它不解析 HTML、不执行 Markdown、不读取模板路径，因此没有模板注入或路径
遍历面。条目 mapper 由编译期绑定的家族代码提供，输出只是响应内容。此次复用没有引入反射、
动态类加载、脚本表达式或通用序列化 Map。

Credential 与 endpoint 的边界同样没变。Manifest 中的 source endpoint 是公开只读 API 路径，
不是原始外部服务地址；没有 credential value 字段。Boundary Locks 继续明确 no-raw-endpoint-url、
no-credential-value 和 no-managed-audit-http。重构没有把这些文本当作配置执行，也没有从环境变量
补齐隐藏值。测试用固定对象链构造响应，不需要真实凭据。

结构门也属于阻断机制。若开发者重新添加一个长名 section Renderer，家族文件上限、Renderer
数量、长名身份和 removed-file 断言会同时失败；若把所有逻辑塞进一个超大组合器，低于 300 行
的版本失败条件会阻止合并；若修改 Catalog 或 fixture 让 oracle 通过，代码审计能看到行为输入
被篡改，而且变更范围不再符合本版矩阵。

最后，版本边界要求 walkthrough 先于最终 verify，tag、push 与 canonical CI 完成前不写下一版。
这防止“本地看起来没问题”被当成发布完成，也防止在等待 CI 时把两个版本混进同一工作树。v1873
正是按实现 CI、closeout CI、annotated tag 的顺序闭环后，本版才开始写入。

## 测试覆盖

最关键的测试顺序是先证旧、再换新。`ConsumerPackageMarkdownTests` 的 expected sections 来自
当前旧实现的真实运行结果，不是根据新代码反推。它在删除任何 Renderer 前单独通过 1/1，冻结
九个 heading、九个计数首行和所有条目。生产替换后，同一测试不改期望再次通过。这样可以区分
“重构等价”与“测试跟着实现一起移动”的假安全。

oracle 覆盖容易被粗粒度测试漏掉的细节：Source Digest 的完整 endpoint，Manifest 的 required
布尔值，Audience 的 packet，Package Section 的 source evidence，Acceptance 的 passed，CI
Matrix 的序号与 read-only，Boundary 的 reason，Checklist 的 ready，以及 Scorecard 的
actual/expected 顺序。一个空格、一处分隔符或一行换位都会导致 record 不相等。

既有三个家族测试继续检查 source、catalog、boundary 与 checklist；root Controller aggregate
继续检查 route field、版本、执行禁止、section heading 顺序和 checks；共享引擎测试继续检查
空列表计数、映射顺序、不可变结果和 null fail-fast；v1846、v1866 与 elegance tests 检查文件
结构和基线。本版第一次重构后，这组聚焦测试合计 25/25 通过。

最终验收还必须执行完整 `mvnw -B verify`。它会编译全部生产与测试源，运行普通回归，检查
JaCoCo floor、Spotless 和 SpotBugs，并打包 jar。本讲解在该命令之前写完，禁止在最终 verify
之后再扩充篇幅来满足字数。canonical GitHub Actions 仍要分别通过 Docker-tagged 与 headless
job；只有本地和远端两侧都绿，tag 才能发布。

测试没有通过降低覆盖率、放宽 floor、改 fixture 字节或删除历史断言来迁就重构。相反，历史
v1846 测试从要求旧形状存在，升级为要求新形状存在且旧壳消失；全树上限和 Renderer 上限都
下降。这种“行为 oracle + 结构 ratchet”组合，既防输出回归，也防代码形状复发。

## 实际工作量说明

生产侧删除十个 section/registry Renderer 和一个 Support，新建一个家族 `ReportRenderer`，
替换 Service 最后一步调用。测试侧新增一篇完整 Markdown oracle，把超长 TestSupport 改成
`ConsumerPackageTestData`，同步三个家族测试、root Controller 测试和下游 dossier test data，
并重写 v1846 的当前结构集合。共享 `MarkdownSections` 无需改动，说明抽象接口足够稳定。

治理侧扩展 ops census，新增 consumer package 指标，收紧 Renderer 数量、行数、长名、生产
与测试 aggregate、精确 identity baseline 和全树 Java 上限。文档侧补充技术矩阵、CHANGELOG、
progress、final evidence、归档政策、manifest 与本篇中文讲解。每一项都对应可复现命令或会失败
的测试，不把“整理了一下”当作工程证据。

净结果不是简单的十一减一。生产文件净减十，Renderer 净减九，Renderer 代码净减 204 行，
同时新增的短名组合器让九段顺序集中可见；测试文件净增一但长名债务下降；Catalog 和 Service
数量不变，证明业务边界未被藏起来。目标家族从 23 个生产文件变为 13 个，读者需要打开的展示
文件从十一个变成一个，且仍可通过私有方法逐段定位。

本项目要求讲解至少三千汉字，但规则同样强调禁止硬凑。因此篇幅来自输入输出、调用链、record
字段、不可变机制、上游关系、安全阻断、测试顺序、census 与失败条件的逐层解释，而不是重复
同一句优点。若实际工作不足以支撑透明讲解，就应扩大有价值的工作或缩小版本承诺，不能用空泛
形容词伪造“中大版本”。

本版仍不是三分计划的终点。后续可沿相同方法处理 operator dossier、CI acceptance 与 CI archive
家族，但每刀都必须先建立自己的旧行为 oracle，并证明共享引擎确实比保留重复文件更易读。
等 Renderer 面收敛后，才进入 Catalog engine、Service pipeline 和 500 行热点拆分，避免同时
改变过多维度而失去可验证性。

## 一句话总结

v1874 在完全保持只读路由、强类型响应、九段 Markdown 字节、Catalog 事实、下游消费与安全锁
不变的前提下，用旧实现先通过的逐行 oracle 护住行为，再以第二个真实消费者证明共享
`MarkdownSections` 的抽象价值，用一个短名 `ReportRenderer` 替代十个一次性 Renderer 和一个
Support，并把文件数、Renderer 数量与行数、生产及测试长名、历史结构门和精确身份基线全部向下
收紧，使维护者既能更快看懂一次请求从上游 digest 到最终报告的每一步输入输出，也无法在未来
悄悄恢复旧债；这就是本版对“优雅”的具体定义：不是把复杂度藏起来，而是让必要差异留在领域、
让真正重复进入小而稳定的算法、让每个结论都有机械证据、让任何回退都能被仓库自己拒绝。
