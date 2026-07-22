# v1888：候选文档核心目录由十四个零散工厂收敛为三个领域聚合器

## 入口路由

本版覆盖 CandidateDocument 只读链上的三个既有入口：submission precheck、intake packet 和
profile section registry。第一个入口由提交预检 Controller 调用
`OpsShardReadinessCandidateDocumentSubmissionPrecheckService.precheck()`，输入不是上传文件，而是
request package 与 handoff 两份已经形成的只读响应。第二个入口由 intake packet Controller 调用
`OpsShardReadinessCandidateDocumentIntakePacketService.intakePacket()`，输入是上一层 submission
precheck 的完整响应。第三个入口由 profile section registry Controller 调用
`OpsShardReadinessCandidateDocumentProfileSectionRegistryService.registry()`，输入来自 request package、
submission precheck、intake packet、material request 与 material submission precheck 五条只读链。

外部路由、HTTP 方法、Controller 类型和公开 Response 在 v1888 中都没有改动。调用者仍然从原地址
读取同样的 JSON，也仍然不能提交真实候选文档、打开 runtime payload、触发 import、发放 approval
或修改兄弟项目。变化发生在 Service 拿到上游响应以后：旧代码把一次装配拆给十四个名字很长的
Catalog，Service 必须逐个知道“来源、模块、槽位、守卫、制品、字段、路由锁、gate”由谁创建；新代码
按三个公开响应的实际所有权，各用一个短 Catalog 生成一份完整 `Evidence`。入口没有变宽，内部依赖
却从十四个文件级细节收敛为三个领域概念，这正是本项目这轮优雅度治理要购买的可读性。

从操作过程看，调用仍是 Controller -> Service -> upstream Service -> Support -> Response。Catalog
只位于 upstream Response 与 Support 之间，既没有 Spring 注解，也没有数据库、网络或执行依赖。
因此这不是把业务流程“藏”进工具类，而是把原先分散在同一响应装配步骤里的静态数据投影重新放回
一个 owner。读者沿着入口进入 Service，只需要理解一次 `from(...)`，随后就能看到全部证据如何交给
原 Support；不再需要在五到六个同形文件之间来回跳转才能拼出一次响应。

## 响应模型

submission precheck 的公开模型仍是
`OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse`。它发布二十五个 checkpoint、二十五个
validator、八个 artifact、四十个 gate 和十九个 check，完整集合向量是
`25/25/8/40/19`。intake packet 仍发布五条 source lineage、五个 module、十个 intake slot、十个
intake guard、八个 artifact、三十五个 gate 和二十三个 check，向量为
`5/5/10/10/8/35/23`。profile registry 仍发布五个 module、五条 source、五个 section、二十五个
field entry、五个 rendered section、五个 route field lock、四十三个 gate 与二十一个 check，向量为
`5/5/5/25/5/5/43/21`。

三个向量只能发现数量或顺序维度的粗差异，所以本版还在删除旧实现以前，对三个完整 Response 使用
Jackson 的属性名排序与 map key 排序形成规范 JSON，再对 UTF-8 字节计算 SHA-256。冻结摘要依次是
`920742a06cdbe7f0502abeb4c4b38d2f772088677aabdc5a2eb594f2bc0ce0fa`、
`cb0b888fcc190b1272834cabf7c1bb414471d486da55212cc562cdd6af4c4e95` 与
`d3cbe7af21f604737121aa8a5e4d9e05f5dd9ed3e1c7013ec2757b8d60dbc660`。旧实现先通过三项硬断言，
替换后同一份测试不改预期再次通过。字段值、列表顺序、嵌套 record 内容、状态文本或 check 文本只要
有一处变化，完整摘要就会失败；因此“输出没变”是字节级可复现结论，不是人工浏览后的感觉。

新建的三个 `Evidence` 都是包内嵌套 record，不属于 HTTP 合同。`SubmissionCatalog.Evidence` 拥有四组
列表，`IntakeCatalog.Evidence` 与 `ProfileCatalog.Evidence` 各拥有六组列表。紧凑构造器对每组执行
一次 `List.copyOf`，把装配时的可变临时集合截断在 Catalog 内部；Service 和 Support 得到的是稳定快照。
Response 里的 count、ready、status 和 checks 仍由旧 Support 从这些列表推导，新 record 没有接管
状态机，也没有把不同响应压成字符串键 map。类型信息、领域词汇和编译期约束全部保留。

## 上游证据配置

`SubmissionCatalog.from(requestPackage, handoff)` 先把十五个 request item 与十条 consumer rule
映射为二十五个 checkpoint。前十五项保持 `request-` 前缀和 `request-item` 分类，后十项保持
`consumer-` 前缀和 `consumer-rule` 分类；owner、instruction 与 passed 状态均沿用旧实现。validator
仍与 checkpoint 一对一，rejection code、fail-closed enforcement 和文字说明不变。八条 artifact
继续指向 `e/1117` 下的计划、来源、checkpoint、validator、禁用边界、route 与 closeout 证据；四十个
gate 仍由 Support 的 `EXPECTED_GATE_COUNT` 决定。这里聚合的是一次提交预检的完整数据，不是把
request package 与 handoff 的公共模型改造成新模型。

`IntakeCatalog.from(sourcePrecheck)` 先固定五条来源链与五个维护模块，再按
`3/3/3/3/3/2/2/2/2/2` 的原分组把二十五个 checkpoint 装入十个 slot。每个 slot 仍只携带两个候选
字段和一个明确标为 placeholder 的 envelope；十个 guard 逐一引用 slot code，并保持
`reject-intake-packet-...` 拒绝码与 fail-closed 语义。八条 artifact 和三十五个 no-material gate 原样
保留。内部构造 slot 与 profile field 时会短暂使用 `ArrayList`，但这些列表只在 `from` 内部存在，进入
`Evidence` 时统一复制；本版还用结构门阻止有人在 helper 与 `Evidence` 两处重复复制，避免看似安全、
实则多余的内存与阅读成本。

`ProfileCatalog.from(...)` 把五条上游 Response 投影为五个 `SectionSource`，保持 Java v1081、v1117、
v1142、v1152、v1162 的版本顺序和原 endpoint/profile/status。随后生成五个 ProfileSection，每节仍有
五个字段：version、endpoint、profile、status 与 boundary；其中前四个 route-facing，boundary 固定为
`read-only-no-runtime`。每节仍生成一个锁住 endpoint/profile/version 的 RouteFieldLock，四十三个 gate
继续来自原 Support 上限。真正的 Markdown 行为没有进入 Catalog：`ProfileRenderer.render(sections,
fieldEntries)` 仍在 Service 侧单独调用。数据聚合与输出渲染因此保持清楚分界。

## 服务层核心流程

三个 Service 现在采用同一种可读流程，但没有被迫继承一个泛型基类。Submission Service 先读取
request package 与 handoff，然后调用一次 `SubmissionCatalog.from(...)`；Intake Service 先读取
submission precheck，再调用一次 `IntakeCatalog.from(...)`；Profile Service 读取五条既有来源，再调用
一次 `ProfileCatalog.from(...)`。每个 Service 最后仍把版本、endpoint、profile、source、evidence 与
本地 service check 交给原 Support。结构测试读取源文件并计算 `.from(` 出现次数，三个入口都必须精确
为一，旧十四个 Catalog 路径必须全部不存在。

这种设计没有为了“代码看起来一样”建立跨 Response 的万能 engine。三个领域的条目类型、来源数量、
编号、路径、gate 文本和投影规则差异很大；硬抽成 `Map<String, List<?>>` 会丢失编译期类型，也会让
读者追踪字符串键。真正可共享的是生命周期边界：一个 Catalog 对一次完整响应的数据负责，一次
`from` 返回不可变证据。具体数据规则仍留在各自短 owner 中。最终三个文件格式化后分别为 131、190、
197 行，都低于 300 行机械上限，也没有出现新的第四个同形 helper。

旧代码总计十四个生产 Catalog、约五百五十行；新代码用三个 owner 表达同样步骤，并删除 Service 中
逐项拼装的知识。ops 生产文件从 1,237 降到 1,226，Catalog 从 320 降到 309，整个生产源码文件从
1,369 降到 1,358。测试层也把七个与旧 Profile Catalog 一一对应的长壳合并为一个
`ProfileCatalogTests`，并把 Submission、Intake、Profile 的五个被触及长测试 owner 改成短职责名。
这不是简单“把文件塞大”：生产与测试文件数、长名集合、跳转次数和重复装配入口同时下降。

## Java 证据检查

Java 侧第一道证据是 `CoreCatalogResponseOracleTests`。测试直接构造真实 Service 链，不使用虚构 JSON
fixture，也不绕过 Support；三份响应在旧实现和新实现上使用相同向量与摘要断言。第二道证据是原有
Submission、Intake、Profile 语义测试：它们继续检查来源顺序、slot 分组、guard 拒绝语义、字段 schema、
route lock、gate、状态和所有禁用能力。测试文件虽然被改短名或合并，但断言没有删除，Profile 的九项
目录语义仍逐项存在。Controller 与 route evidence 测试也继续使用真实 `ProfileTestData` 服务链。

第三道证据是 `CandidateCoreCatalogStructureTests`。它要求三个短 owner 存在、十四个旧路径不存在；
逐个读取 Service，要求恰好一次 Catalog 组装；逐个读取 owner，要求不超过 300 行、存在类型化
`Evidence`，并精确拥有 4/6/6 次 `List.copyOf`；还要求 `ProfileCatalog` 不引用 `ProfileRenderer`，而
Profile Service 必须保留渲染调用。设计文档中的 `14 -> 3`、三组向量、三份完整摘要和失败条件也被
测试锁定，防止后续只改代码却让决策证据失真。

第四道证据是全局优雅门。官方 census 得到生产长文件 stem、长标识符出现次数、唯一长标识符分别从
`1140/20178/2699` 收紧到 `1126/20107/2685`；测试侧从 `737/9898/3741` 收紧到
`725/9866/3719`。`config/java-name-baseline.txt` 重建后只有五十八项删除、零项新增。
`JavaChangeGateTests` 同时检查新增文件名不超过四十字符、三文件 family 已先写设计说明，以及重构新增
源码没有突破增长门。聚焦行为、oracle、结构、命名和变更门已经合计 51/51 通过；最终 release verify
仍将在本讲解完成以后执行，讲解不会在 verify 后补写来绕过检查。

## mini-kv 证据检查

本版没有修改 `D:\C\mini-kv`，也没有让 Java 启动、停止或写入 mini-kv。三个目标响应的上游全部是
Java CandidateDocument 的只读 Response；其中出现的 Node 计划路径、历史 `e/<version>` 证据路径和
版本字符串只是已经冻结的协议来源，不会被重命名或移动。mini-kv 若在四项目体系中提供更上游的
shard readiness 事实，本次 Catalog 收敛也不重新解释那些事实，只保持 Java 当前响应中已有的文本与
摘要。完整 JSON oracle 会间接阻止任何来源字段在重构中漂移。

这里需要区分“跨项目证据对齐”和“运行时联动”。Java 的三条 route 能证明自身只读投影与输出合同，
不能据此声称 mini-kv 进程在线，更不能声称 Java 获得了存储写权限。v1888 没有执行 C++ 命令、没有
修改 fixture、没有触碰 mini-kv archive，也没有把 Java 内部优化包装成新的跨项目能力。将来关联项目
做对齐时，应消费本版已发布 tag 的稳定响应，而不是读取三个包内 Catalog；包内 owner 可以继续优化，
公开 Response 与 digest 才是可对齐边界。

这种克制本身也是优雅的一部分。内部重构最容易犯的错误，是为了证明“共享”而扩大依赖面，最终让
mini-kv、Node 与 Java 同时需要迁移。v1888 明确停在 Java package 内，Node 已固定的绝对归档路径不动，
mini-kv 的命令、WAL、snapshot 和只读门不动。跨项目没有新增输入，也没有新增输出；唯一新增的是 Java
仓库内可机械验证的短抽象。因此关联项目不需要同步版本，用户后续可以在稳定 tag 上独立评审。

## 阻断与安全边界

三个公开 Service 的 `@Transactional(readOnly = true)` 原样保留。Catalog 没有 Repository、HTTP client、
filesystem writer 或 Spring execution bean，不可能因为合并文件而获得写能力。Submission 响应继续把
real、synthetic、staged、imported、evaluated、accepted、rejected 与 payload 计数保持为零；import、
evaluation、approval grant、signed approval capture、runtime payload、write 和 sibling mutation 继续为
false。Intake 另外保持 materialAccepted=false，Profile 保持 materialSubmissionAccepted=false。

Support 仍是状态与 checks 的唯一 owner。若 slot 缺失，`IntakeSupportTests` 仍构造空 slot/guard 输入并
要求 blocked；Catalog 不会自行宣布 passed，也不会把空集合替换成默认成功。Profile Renderer 仍独立，
所以把字段投影收拢到 `ProfileCatalog` 不会顺便改变 Markdown。公共 Response record 未改，Bean 序列化
字段和顺序由完整摘要覆盖。route 常量、Controller 与版本/profile 字符串未改，外部消费者无法借本版
获得新入口或新执行参数。

失败条件被写进设计文档并由结构门读取：任一 JSON 字段、顺序、数量、gate、check 或摘要变化即失败；
修改 fixture、Response、route 或旧期望来迎合重构即失败；任一新 owner 超过 300 行、旧十四个 owner
复活、出现第四个同形工具或把 ProfileRenderer 塞入数据 Catalog 即失败。门槛只收紧，不用“测试也跟着
改”掩盖行为变化。禁止硬凑不仅约束讲解字数，也约束抽象：只有真实减少总复杂度的合并才算进展。

## 测试覆盖

测试按“旧实现冻结、迁移后复验、结构防回退、全局门收紧”四层组织。冻结阶段先用临时只读探针获得
集合向量和 SHA-256，随后立即删除打印、改成硬断言，并在旧实现上通过 3/3。生产替换完成后，同一
oracle 不改一字再次通过 3/3。第一轮聚焦回归执行四十四项测试，结构门主动发现 Intake 与 Profile
内部临时列表在进入 `Evidence` 前重复复制；修复选择删除冗余复制，保留 record 构造器这一处真正的
所有权边界，而不是把期望从六放宽到七。修复后 44/44 通过。

第二轮把 `JavaEleganceGateTests` 与 `JavaChangeGateTests` 加入同一选择，共 51/51 通过。它同时证明三个
完整响应仍冻结、三个 Catalog 结构正确、Submission/Intake/Profile 的领域断言保留、Controller 和
renderer 消费链不变、exact baseline 与聚合指标只缩小、设计说明满足三次规则。Spotless 使用前一
canonical tag `v1887-order-platform-candidate-handoff-catalogs` peeled commit 作为固定 ratchet 起点检查，
避免移动 remote 隐藏本版格式变化。

最终交付还必须执行 `scripts/verify-release.ps1`，由脚本自行解析前一 canonical tag，再运行全量 Maven
verify、JaCoCo、SpotBugs、Spotless、文档门、归档门和可执行 jar 打包。该结果不能在运行前写成“通过”；
若全量历史测试暴露旧文件存在性假设，只能把它升级为“当前短 owner 必须存在、退休 owner 必须缺失”的
真实结构检查，不能恢复旧壳或放宽 ratchet。只有本讲解、manifest 和所有上限先固定，最终 verify 才有
资格成为版本证据。

## 实际工作量说明

本版不是一次机械重命名。生产侧阅读并对照了十四个旧 Catalog、三个 Service、三个 Response、三个
Support 与真实上游 Service 图；先建立需求-证据矩阵，纠正只读规划把 Profile 基类重复计数而产生的
`15 -> 3` 误差，以实际文件 census 锁定 `14 -> 3`。随后建立三响应规范 oracle，分别验证旧实现与新
实现，再创建三个 300 行以内的包内 owner，迁移四组、六组、六组证据，删除十四个旧生产文件并改造
Service 为一次组装。

测试侧没有把旧测试一起扔掉。Submission、Intake、Profile 的核心 Service 测试改为短职责名；Profile
原先按六个 Catalog 拆开的来源、模块、section、field、route lock、gate 断言连同聚合计数，合并为一个
围绕当前 `ProfileCatalog` 边界的语义测试；公开 `ProfileTestData` 替代长 TestSupport，并同步两处根包
Controller/route 消费者以及两个 renderer 测试。新增结构门、设计文档和本讲解后，再运行格式化、两轮
聚焦测试、官方 census、baseline 重建与固定提交比较。

可量化结果是：生产文件净减十一，测试文件净减四；Catalog 净减十一；三个新生产 owner 合计五百余行，
替代旧十四文件与 Service 中的分散拼装，且每个 owner 单独可读。长名指标两侧都下降，exact baseline
删除五十八项而无新增。没有修改 route、Response、fixture、Support、事务或归档历史。本项目仍有
release approval 大文件与大量存量长名债务，v1888 不把一次局部高质量收敛包装成整体九分；它完成的
是 Catalog engine 阶段第二个可复现切片，并为下一刀提供更清楚的范式。

## 一句话总结

v1888 把 CandidateDocument 三个核心只读响应从十四个零散 Catalog 收敛为
`SubmissionCatalog`、`IntakeCatalog`、`ProfileCatalog` 三个短领域 owner：输入仍是原上游 Response，
输出仍由原 Support 和公开 Response 拥有，中间只增加一次类型化、不可变的证据快照。三份完整 JSON
在删除前后保持相同集合向量与 SHA-256，Profile 渲染、route、事务和所有禁用能力不变；结构门又把
一次组装、300 行上限、4/6/6 次防御复制、十四个退休路径和设计证据锁成会失败的规则。

这版的价值不在“少了几个文件”本身，而在于读者现在能从 Service 的一个 `from(...)` 进入完整领域
数据，不再把一次响应在十四个长名间人工拼图；同时 exact baseline、ops/Catalog census 和测试 owner
数量都真实下降。最终评价仍服从 full release gate 与外部复核，仓库不会自封 coding brilliant and
elegant 9；但这一刀在兼容性、透明机理、类型安全、失败边界和维护成本五个维度都向目标前进，并且每个
结论都有可重复命令与机械门支撑。
