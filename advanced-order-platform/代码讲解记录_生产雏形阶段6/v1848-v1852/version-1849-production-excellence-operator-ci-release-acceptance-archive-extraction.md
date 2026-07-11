# version-1849：Operator-CI 发布验收归档层提取讲解

## 实际工作量说明

本版继续治理本项目 Java 代码，只处理 v1848 下游的 ReleaseAcceptanceArchive。禁止硬凑说明，也不
通过修改测试期望、fixture 字节或安全标志来换取成功。实际工作包含二十三个生产实现迁移、六个包内
测试迁移、一个控制器留根、一个控制器 Markdown 测试留根、十余个下游 Handoff import 收口、四个
SpotBugs 完整类名迁移、五类活计数收紧、endgame census 历史追加、新机械守卫、技术说明和中文长篇。

迁移前，Archive 与 v1848 已迁出的 ReleaseAcceptance 基座虽然在业务上是上下游，却因为 Archive
仍在根包而保留了大量默认可见关系。开发者只看目录，很难知道 ArtifactManifestCatalog、
RoutePackageCatalog、CiAttestationCatalog、BoundarySealCatalog 和 CloseoutLedgerCatalog 共同属于
同一个归档登记功能。迁移后，这些 catalog、renderer、service、response、support 全部归入
`ops.maintenance.ciarc`，根包只留下 Web 控制器。编译器开始替项目回答“谁可以依赖谁”。

路径选择也属于真实工程工作。完整业务前缀若直接成为包名，最长主源码和测试路径分别是三百五十四
与三百六十一字符，明显不适合当前 Windows 工作区。`ciarchive` 虽可读，测试路径仍超过传统边界。
最终使用 `ciarc`，主源码与测试最长降至二百五十二和二百五十九字符。这个缩写在包上下文中表示
CI acceptance archive，类名本身仍完整，因此维护者搜索业务术语不会丢失结果。

二十三个实现不是按文件名随意凑出的批次，而是一个能够独立回答归档问题的完整闭包。其中十六个
文件组成八组 catalog/renderer 配对，分别处理制品、路由、操作员、持续集成、边界、保留、收尾和
来源；RegistryService 固定调用次序，RegistrySupport 组装响应，RegistryRenderer 组织总文档，
RendererSupport 生成公共段落，ScorecardCatalog 与 ScorecardRenderer 给出汇总，Response 定义唯一
输出模型。控制器不属于这个闭包的内部实现，它是根包 Web 适配器，所以明确留根。六个包内测试则
覆盖上述八组职责的组合，而控制器测试继续留根，从外部视角验证公开工厂。这种划分既没有把一个
功能拆成无法独立理解的碎片，也没有把下一层 Handoff 混入同一版本。

本版还主动处理了迁移引起的下游格式变化。Handoff 中凡是读取 Archive Response 的文件都需要显式
import，新 import 会使 Spotless 重新格式化这些曾被缓存跳过的旧文件。较大的 diff 并不代表响应
逻辑被重写，机械守卫与原行为测试会验证构造内容不变。把这种格式成本留在当前边界版本，比让下游
继续依赖根包默认可见性更诚实，也让 v1850 可以在一个已经编译通过的公开接口上继续工作。

## 入口路由

本版入口仍是无请求体、无查询参数的只读 GET。调用者访问 ReleaseAcceptanceArchive registry 的既有
地址，控制器从 Spring 容器取得 ArchiveRegistryService，调用 `registry()` 后返回 ArchiveRegistryResponse。
控制器不解析凭据，不接收原始 endpoint，不接受执行开关，也不把请求转换成部署、回滚或消息发布。
从 HTTP 输入看，本版前后没有任何可观察差异。

路由后缀仍由 v1840 的 `OpsShardReadinessReleaseAcceptanceRoutePaths` 唯一持有。迁移前 Service 通过
根 `OpsShardReadinessRoutePaths` 间接读取，迁移后直接导入专属 owner；根控制器继续读取根聚合器，
根聚合器仍委托同一个后缀。这样做的输入是同一个 BASE_PATH 和同一个 suffix，输出自然仍是同一 URL。
没有复制字符串，也没有为了腾行数删除其他路由或提高 1111 行上限。

可以把它理解为档案馆的收件窗口。外部人员仍到同一个窗口提交“我要查看发布验收归档”的请求，窗口
编号完全不变。内部工作人员过去先问总服务台窗口在哪，现在直接查看档案馆自己的正式窗口表。内部
指引更短，外部访问不变。历史路由测试、v1849 route-owner 守卫和完整 Web 测试共同检查这一点。

## 响应模型

ArchiveRegistryResponse 是归档层的机器输出。头部字段说明项目、版本、只读状态、执行禁用状态、入口
和 profile，并记录来源 ReleaseAcceptance 的版本、端点和状态。主体包含九组结构化证据：来源归档
快照、制品清单、路由包、操作员包、CI 证明、边界封条、保留窗口、收尾账本和总评分。末尾仍有
Markdown sections、检查字符串和总状态，方便机器与人工使用同一份事实。

SourceArchiveSnapshot 保存本次归档消费的 ReleaseAcceptance 版本与关键计数；ArtifactManifestEntry
说明哪些制品必须存在及其状态；RoutePackageEntry 说明接收方、owner 和 packet 是否就绪；
OperatorPackEntry 按顺序给出操作员材料；CiAttestationEntry 固定 CI 证明；BoundarySealEntry 声明
哪些执行与秘密边界必须锁定；RetentionWindowEntry 描述保留窗口；CloseoutLedgerEntry 固定收尾账目；
ScorecardEntry 比较每组 expected 与 actual。每个嵌套 record 都是不可变值，而不是执行命令。

本版只移动 Response 源文件并更新 FQN，没有改变字段顺序、字段类型、嵌套 record 名称、列表顺序、
状态词或 JSON 形态。为什么 Archive 尤其不能随手改顺序？因为归档证据可能被摘要、快照、下游 Handoff
或审查脚本按稳定顺序消费。结构迁移与契约演进必须分开，否则无法证明变化究竟来自包地址还是业务
语义。SpotBugs 的双镜像规则也只跟随新 FQN，不删除现有集合暴露风险决策。

## 上游证据配置

Archive 的唯一业务上游是 v1848 `ciaccept` 包中的 ReleaseAcceptanceRegistryService。服务调用上游
`registry()` 得到 Response，然后所有目录函数都以该不可变响应为输入。v1848 已经把 Service、
Response 和测试工厂作为窄边界公开，因此 v1849 不需要访问基座内部的 readiness catalog、renderer
或 support。这个顺序体现“先迁被依赖层，再迁依赖层”的工程纪律。

上游 Response 表示发布验收是否齐备，但仍明确 executionAllowed 为 false。Archive 不把 passed 解释
成可以部署，而是把验收事实整理成可保存、可交接、可复核的包。例如，上游 readiness gate 会影响
artifact manifest 和 scorecard；signoff lane 会进入 operator pack；CI replay lane 会被转成 CI
attestation；boundary control 会成为 boundary seal；retention policy 会成为 retention window；
closeout checkpoint 会映射到 closeout ledger。映射是确定性的，没有网络请求和时间依赖。

下游是 v1850 待迁的 ArchiveVerificationHandoff。它需要 ArchiveRegistryService 取得完整归档响应，
并在 source、requirement、artifact、route、operator、CI、boundary、retention、closeout 与 scorecard
目录中读取嵌套记录。v1849 把这些下游文件改成显式导入 `ciarc` 的 Service/Response。没有一个 Handoff
文件导入 Archive 的 catalog、renderer 或 support，因而下一版可以把整个 Handoff 家族独立迁出。

## 服务层核心流程

`registry()` 在只读事务中运行。第一步调用 v1848 Service 得到 source。第二步生成 source archive
snapshots，用来固定上游版本和验收计数。第三步生成 artifact manifest，列出要求归档的证据制品。
第四步生成 route packages 与 operator packs，说明这些证据应交给谁、由谁负责。第五步生成 CI
attestations 和 boundary seals，分别证明自动化检查与安全边界。第六步生成 retention windows 与
closeout ledger，固定保留与收尾。第七步计算 scorecard。最后 renderer 生成九段 Markdown，support
把结构化集合、数量、检查项和总状态组装成 Response。

每一步的输入都是同一 source 或之前生成的列表，输出是新不可变列表。Catalog 负责业务投影，Renderer
负责文字投影，Support 负责聚合，Service 负责调用顺序。职责分开后，维护者若要理解 CI 证明，只需
看 CiAttestationCatalog 和对应 renderer；若要理解归档总状态，则看 ScorecardCatalog 与 Support；
无需在一个巨型 Service 中滚动寻找所有逻辑。

举例说明完整输入输出。假设 v1848 返回 passed，包含十个 readiness gate、六个 signoff lane、五个
只读 CI replay lane、八个 locked boundary control 和若干 retention/closeout 条目。Archive 先保存
来源版本和计数，再生成必须存在的 artifact manifest；随后形成面向不同接收者的 route packages 和
operator packs；五个 replay lane 成为五个 CI attestations；八个边界控制成为八个 boundary seals；
保留策略与收尾检查点进入窗口和账本。所有 expected 与 actual 相等时 scorecard passed，最终 status
passed，但 startsJavaService、startsMiniKvService、readsCredentialValue、resolvesRawEndpointUrl、
managedAuditHttpAllowed 和 executionAllowed 继续为 false。

## Java 证据检查

结构证据由 v1849 守卫给出。它用精确清单要求二十三个生产文件只存在于 `maintenance/ciarc`，目标包
不能多也不能少；六个测试必须随包移动；控制器与控制器 Markdown 测试必须留根。服务源码必须同时
包含 `ops.maintenance.ciaccept` 和公共 ReleaseAcceptance route owner，并且不得再通过根聚合器构造
自己的 ENDPOINT。Handoff 文件只要使用 Archive Response，就必须出现 `ciarc` import。

行为证据沿用原有测试。SourceManifest 测试验证来源与制品；RouteOperator 测试验证路由包和操作员包；
CiBoundary 测试验证 CI 证明与安全封条；RetentionCloseout 测试验证保留窗口和账本；Immutability
测试验证集合不可修改；控制器 Markdown 测试从根包通过公开 TestSupport 组装真实 Service。下游
Handoff 测试参与 focused 集，证明跨包注入和嵌套 record 读取能编译并产生相同结果。

治理证据把根包从五百四十八收紧到五百二十五，可迁文件从四百四十三收紧到四百二十，Operator-CI
bucket 从二十三清零。保留根文件仍是一百零五，Handoff 独立 bucket 仍为二十五，未分类为零，总
ops 主源码仍不超过一千三百五十二。三处 live ratchet、v1847/v1848 活计数、v1828 endgame census
同时更新，而文档中的历史 `573→548`、`468→443` 仍保留。

## mini-kv 证据检查

本版不写 mini-kv 仓库，也不启动 C++ 进程。Archive 中出现的 mini-kv 或跨项目描述，来自上游只读
证据，并非当前版本实时访问 mini-kv 得到的结果。本版能证明 Java 对冻结证据的归档投影仍稳定，能
证明不会启动 mini-kv、不会解析原始 endpoint、不会读取 credential value，却不能声称完成系统级
联调。系统 capstone 仍由 Node 窗口负责，Java 只在收到明确 C1 配合请求时优先响应。

负面能力在 Archive 层非常重要。归档通常容易被误解为“把证据写到外部存储”，但当前实现只是生成
只读归档登记响应，没有文件系统写入、对象存储上传或数据库保存。startsMiniKvService=false 说明
它不会为了补证据拉起上游；managedAuditHttpAllowed=false 说明它不会把审查包发送到外部；
executionAllowed=false 说明归档完成也不构成执行授权。这些都由结构字段与测试共同固定。

## 阻断与安全边界

第一道边界是无写入。服务只有只读事务和纯函数目录，没有 repository save、消息发送、文件写入、
进程启动、部署或回滚。第二道边界是无秘密。入口没有可携带密钥的参数，服务不读取凭据，不解析
原始 URL。第三道边界是无外部网络。没有 HTTP client、RabbitMQ producer 或 managed audit
connection。第四道边界是无契约漂移。路由从唯一 owner 获取，Response 原样移动，历史 fixture、
证据 JSON 和 archive 目录均未改名。

可见性控制同样是边界。大多数 catalog、renderer 和 support 仍是 package-private，生产代码只公开
既有 Service 与 Response。TestSupport 因留根控制器测试和下游 Handoff 测试需要而公开，但位于测试
源集，不会进入生产 jar。若未来出现编译失败，不能把所有类批量改 public，而应判断下游是否绕过了
Service/Response。v1849 机械守卫会阻止这种边界逐渐扩散。

失败条件也明确：目标包不是精确二十三个实现失败；根包残留同名实现失败；控制器移动失败；Handoff
读取包私有类失败；路由变化失败；Response 变化失败；根包高于五百二十五失败；Operator-CI bucket
非零失败；未分类非零失败；讲解不足三千汉字失败；focused、Spotless、JaCoCo、SpotBugs 或完整
verify 任一失败都不得提交版本完成结论。

## 测试覆盖

Focused 集覆盖 Archive 全家族、Handoff 下游、v1848 历史守卫、v1849 新守卫、v1828 endgame
census、三处根包棘轮和讲解合规门。先跑它可以快速发现 import、包私有访问、路径、计数和文档结构
问题。随后 Spotless 归一化机械迁移导致的 import 顺序和行尾。最终 `mvnw verify` 运行约一千七百五十
项测试，并执行 JaCoCo、SpotBugs、Spotless 和所有架构守卫。

历史守卫不是冻结当前计数。v1848 守卫仍要证明 ReleaseAcceptance 只依赖 v1847 dossier，但它读取
Archive 的位置必须从根包改到 `ciarc`；其“当前根包数”也要继续下降到五百二十五。历史文档里的
`573→548` 则不能改，因为那是 v1848 发生过的事实。区分活约束和历史快照，是本项目多版本治理不会
自相矛盾的关键。

最终证据会记录 focused 测试数与耗时、完整测试总数与耗时、JaCoCo class count、SpotBugs findings、
最终 census、实现提交、关账提交、tag 和 GitHub Actions run。中间版 CI 启动后不原地等待，下一版
Step-0 读取结果；到五版检查点才统一等待所有 run 结束并更新账本。这样既保持证据完整，也不浪费
本地长验证期间的可用时间。

## 一句话总结

v1849 把发布验收归档从根包隐式共享区变成独立只读模块：输入仅是 v1848 ReleaseAcceptance
Response，输出是稳定的归档清单与评分，只向 v1850 Handoff 暴露 Service/Response，路由和响应不变，
根包再减二十三个实现并清空 Operator-CI bucket，所有结论由精确清单、编译器、收缩棘轮和全量门证明。
