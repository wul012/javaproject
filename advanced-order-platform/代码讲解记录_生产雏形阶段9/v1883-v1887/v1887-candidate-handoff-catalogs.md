# v1887：候选文档交接目录从十四个碎片收敛为两个领域组合器

## 入口路由

这一版处理的不是新接口，而是两条已经存在的只读交接链。第一条链从候选文档 request package
开始，Controller 调用 `OpsShardReadinessCandidateDocumentHandoffService.handoff()`，服务再读取
`OpsShardReadinessCandidateDocumentRequestPackageService.packageCatalog()`。它的输入是一份已经整理好的
请求包：十五个请求条目、十五项验收锁、上游版本和 route/profile 元数据；输出仍是 Java v1107 的
候选文档交接响应。第二条链从 material submission precheck 开始，Controller 调用
`OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffService.handoff()`，服务先获得 Java
v1162 的预检响应，再输出 Java v1187 的预检归档交接响应。两条 route 常量、Spring Controller、HTTP
方法、profile 字符串和返回 Response 类型在本版都没有变化，因此现有消费者不需要知道内部重构。

可以把入口理解成“读一张已经批准格式的清单，再为下游生成只读交接索引”。调用者不给服务传入
credential value、raw endpoint、真实文档或写入指令；服务也不接受“启动执行”的参数。第一条链的
实际输入来自 `sourcePackageService`，第二条链的实际输入来自 `sourcePrecheckService`，依赖方向始终是
Controller -> handoff Service -> source Service。v1887 只改变 Service 在拿到上游响应后如何组织七组
证据，不改变入口之前的来源，也不改变入口之后的 HTTP 输出。换句话说，外界看到的是同一扇门、
同一张表、同一顺序；仓库内部只是把门后十四个分散抽屉改造成两个标注明确的资料柜。

## 响应模型

第一份公开响应仍由 `OpsShardReadinessCandidateDocumentHandoffResponse` 定义，包含来源链、模块、制品
句柄、策略锁、归档条目、消费者规则、gate 与 checks。冻结向量
`6/5/15/15/8/10/25/20` 依次记录这些核心集合及最终检查集合的数量。第二份公开响应仍由
`OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse` 定义，其冻结向量为
`6/5/10/10/8/10/42/26`。这两个向量不是随手统计，而是响应结构的紧凑指纹：任何映射漏项、排序漂移、
gate 上下界错误或 Support 检查变化，至少会改变一个位置，随后完整 JSON 摘要还会定位到内容层差异。

新加入的 `Evidence` 并不是公共 API。它分别嵌套在 `HandoffCatalog` 和
`PrecheckHandoffCatalog` 中，只在同一个 package 内承担“七组已计算证据”的类型化载体。它没有状态
判断、HTTP 语义或执行能力，也没有为了复用而抹掉两种 Response 的具体条目类型。紧凑构造器对每个
列表执行 `List.copyOf`：调用者不能在组合完成后增删元素，Catalog 内部也不能把可变集合泄漏给
Support。这一步把一个重要不变量写进类型边界：从 `from(source)` 返回后，集合的成员和顺序就是该次
响应装配的冻结输入。最终 Response 仍由原 Support 创建，所以 blocked/passed 判断、count 字段和 checks
列表继续由既有规则拥有，而不是被新组合器暗中接管。

## 上游证据配置

`HandoffCatalog.from(sourcePackage)` 处理 request-package 领域。`sourceLineage` 固定 Node 计划、Node
候选摄入、Java 候选摄入、Java request package、profile 和未来真实文档摄入六级来源；`modules` 保留
190 到 194 的五个责任条目。十五个 request item 逐一映射为 artifact handle，证据、digest 和 archive
路径由同一个 slug 生成规则得到；十五项 acceptance check 逐一映射为 fail-closed policy lock。八个
archive entry 和十条 consumer rule 是该交接协议自己的固定政策，二十五个 gate 继续由
`EXPECTED_GATE_COUNT` 决定。这里的数据来源和固定政策同处一个领域 owner，阅读者从一个文件即可看到
“哪些来自上游、哪些由交接协议固定”，不用在七个超长类名之间来回跳转。

`PrecheckHandoffCatalog.from(sourcePrecheck)` 处理 material-precheck 领域。它冻结六级来源、214 到 218
的五个模块，把十个 checkpoint 一对一映射为 archive handle，把十个 validator 一对一映射为 policy
lock，把八个 artifact 一对一映射为 archive reference，再按十个 checkpoint 产生消费者规则，最后生成
四十二个 no-material gate。这个领域使用自己的 order、路径前缀、拒绝码和文字政策，不能安全地塞进
request-package 的泛型函数。v1887 因此共享“一个领域一个 bundle、一次返回完整 Evidence”的结构，
但没有共享两种不同 Response 的映射细节。这样的边界比跨模型万能映射器更诚实：相似的生命周期被
统一，真正不同的数据规则仍保持显式。

## 服务层核心流程

旧服务在一次 handoff 中分别调用七个 Catalog 静态方法。单个方法很短，却造成两个问题：服务必须知道
七个内部 owner，任何新增集合都要同时修改依赖列表；阅读者也无法从类型上确认七次调用是否基于同一个
source 快照。新流程只有三步。第一，调用 source Service 得到 `sourcePackage` 或 `sourcePrecheck`；
第二，执行一次 `HandoffCatalog.from(sourcePackage)` 或
`PrecheckHandoffCatalog.from(sourcePrecheck)`；第三，把 `evidence` 的七个访问器连同版本、endpoint、
profile 和 source 交给原 Support。结构门会计算 Service 源码中 `.from(` 的次数，必须精确等于一。

这一改法没有把 Support 合并进 Catalog。Catalog 的职责是投影和固定证据，Support 的职责是校验数量、
推导 status、形成 checks 并创建公开 Response。二者之间以 `Evidence` 的七个类型化列表连接，数据与行为
边界比旧实现更清楚。服务上的 `@Transactional(readOnly = true)` 原样保留，source Service 的真实调用链
也原样保留，没有为了测试方便换成静态 fixture。最终代码仍能从入口一路读到来源：先获取上游响应，
再组合证据，再由 Support 作失败关闭判断。删除的是文件级重复，不是领域步骤；抽象后的流程更短，但每个
关键决定仍有明确 owner。

## Java 证据检查

重构前先运行 `HandoffResponseOracleTests`，它用 Jackson 的 sorted-property 规范 JSON 和 UTF-8
SHA-256 冻结两份完整响应。request handoff 摘要是
`3c988b527fcf1b53946d9cab7ea91866609b2424ce981c87ad3fef8b849e13c2`，precheck
handoff 摘要是 `91473893363f7062af79e05237e1b43407f73bd14176efcfe844fc0331f21cf5`。
删除十四个旧 owner、接入两个新 bundle 后，同一测试文件、同一期望值再次 2/2 通过。这里禁止修改摘要
来迎合实现；只要新输出有一个字符、字段值或列表位置不同，测试就必须失败并要求修正生产映射。

结构证据由 `CandidateHandoffCatalogStructureTests` 提供：目标目录只能存在两个 `*HandoffCatalog`，
七种旧分片在两条 family 下共十四个文件都必须不存在；两个 Service 各组装一次；每个 owner 必须包含
嵌套 `Evidence`、精确七次 `List.copyOf` 且不超过 300 行；设计文档还必须保留两组向量、两个完整摘要
和失败条件。全局 census 则把 ops Java 上限从 1249 收紧到 1237，把 Catalog 上限从 332 收紧到
320。名称门同步收紧为生产 `1140/20178/2699`、测试 `737/9898/3741`，exact baseline 新增 0、
删除 46。相关行为、oracle、结构、优雅和 staged-change 测试合计 56/56 通过。

## mini-kv 证据检查

v1887 是 Java 仓库内部的非契约重构，不调用 mini-kv，也不读取或改写 mini-kv 的 WAL、snapshot、RESP
命令或历史归档。这里仍然要明确 mini-kv 边界，是因为整个系统的证据链允许 Java 引用上游只读事实，
却不允许一次代码整理顺手启动、停止或修改兄弟项目。两条 CandidateDocument handoff 的输入都来自 Java
本地 source Service；响应里的 archive path 和 Node plan reference 是说明性证据，不是文件系统写操作，
更不是跨仓库执行命令。

因此本版对 mini-kv 的机械结论是“零运行时接触、零 schema 改动、零 fixture 字节改动”。完整系统联调
仍由 Node 拥有的 env-gated C1-C4 capstone 在发布检查点执行，而不是由每个内部重构版本重复启动三个
工程。这样做既不夸大证据，也不把跨项目稳定性当成借口阻止 Java 内部维护。若后续某版改变 Node/Java/
mini-kv 共享 evidence schema、route contract 或 digest 规则，就必须回到依赖顺序并执行真实联调；
v1887 没有触碰这些边界，所以同仓库行为 oracle 和全量 Java release gate 是本版的直接证据。

## 阻断与安全边界

两条 handoff 的业务含义本来就是“交接只读证据，同时保持真实操作关闭”。request handoff 的消费者规则
继续拒绝缺失文档、合成文档和未经审核的文档，禁止导入 payload、评估候选、打开 runtime、写 routing
或修改兄弟工作区。material-precheck handoff 的规则继续只允许读取 archive handle 与 policy lock，明确
阻断 submit、import、evaluate、approve、sign、execute、write 和 mutate。四十二个 no-material gate 与
二十五个 read-only gate 的名称、数量和顺序全部由冻结响应覆盖。

安全边界还包括“没有通过重构绕过失败关闭”。新 Catalog 不返回 status，不决定通过与否；原 Support 仍
检查预期集合数量和必需 check，再形成最终 Response。没有新增 credential value 字段，没有接受 raw
endpoint，没有打开 managed audit connection，也没有部署、回滚、支付或库存写入。Controller 与 route
未修改，事务注解仍为 readOnly。若任何人以后把 `Evidence` 改成可变列表、恢复旧 Catalog、增加第二次
source 组装或让 bundle 超过 300 行，结构门会先失败；若输出变化，摘要 oracle 会失败。这使安全承诺从
文字说明变成可重复触发的阻断条件。

## 测试覆盖

测试分成四层。第一层是完整响应 oracle，共两项，覆盖所有公开字段和值，而不是只抽查数量。第二层是
原有语义测试：request handoff 的十项服务行为、blocked Support 分支、material-precheck 的服务与 gate
聚合，以及 source lineage、module order、archive reference、artifact reference、policy rejection、
consumer blocked action 等细粒度断言。十四个生产 Catalog 删除后，这些测试没有被删除，也没有降低
期望；它们改为从新 `Evidence` 取得同一列表。九个被触碰的测试 owner 同时改成短职责名，解决了测试侧
“类名复述整条业务链”的维护债务。

第三层是五项结构测试，负责防止架构回退；第四层是 `OpsEleganceCensusTests`、
`JavaEleganceGateTests` 与 `JavaChangeGateTests`，负责全局文件数、Catalog 数、长名 identity、变化集合和
新增代码预算。Spotless 在 prior canonical tag `b5c8df42...` 上 ratchet，只格式化本版变更。最终发布前
还要执行 `scripts/verify-release.ps1`，让全部 Maven tests、JaCoCo floors、SpotBugs、Spotless、jar 包装、
文档和归档门共同给出结果。测试期望、fixture 字节、公共 route 或 Response 绝不能为了让重构绿灯而
修改；失败只能通过修正实现或撤回不成立的抽象解决。

## 实际工作量说明

本版删除十四个生产 Catalog，新建两个 package-local bundle，改写两个 Service，新增完整响应 oracle 与
五项结构门，并迁移、收短九个直接语义测试 owner。生产文件净减十二个，ops 从 1249 降到 1237，
Catalog 从 332 降到 320；新 owner 不是把旧文本机械拼起来，而是按来源投影、固定政策和不可变结果重新
划定边界。`HandoffCatalog` 235 行，`PrecheckHandoffCatalog` 182 行，都低于 300 行上限；全仓最大文件
仍为 738 行，500 行以上文件仍为 32 个，说明本次没有用新的巨型文件换取表面上的文件数下降。

工作顺序也属于交付证据：先写不超过十行的 family design，再在旧实现运行 2/2 oracle；随后实现、迁移
测试、运行新实现 2/2 oracle，最后才收紧 census、exact baseline 和文档。第一次新实现编译暴露二十处
旧 Catalog 直接引用，这些引用全部被迁到新领域入口，原断言保留；没有用临时兼容壳掩盖删除。代码讲解
在最终 verify 前完成，并记录输入、输出、机制、失败条件与跨项目边界。这里的“至少三千字”服务于机制
透明，禁止硬凑；若内容不足，正确做法是扩大真实工程工作和证据，而不是重复口号。本项目以可复现命令
和会失败的门证明工作量，不以版本号或文件数量自证质量。

维护者今后定位交接证据时，只需先看 Service 的单次组装，再进入对应 bundle 查投影或固定政策；
无需先猜七个相似类名分别掌管哪一段列表，这正是本次导航复杂度下降的直接收益。

## 一句话总结

v1887 把两条 CandidateDocument 只读交接从十四个超长单列表 Catalog 收敛成两个短、类型化、不可变的
领域组合器：输入仍是原 request package 与 material precheck，输出仍是原两份公开 Response，Support、
route、Controller 和安全阻断均不变；完整 JSON 摘要证明每个字节级语义保持一致，结构门与全局 ratchet
则保证旧碎片不会回来。它没有为了“共享”制造跨 Response 万能类，也没有把行为塞进数据 Catalog，而是
在恰当边界停止抽象，让一次响应组装从七个隐式依赖变成一个明确的 `Evidence`。这是一刀真实降低文件、
命名和导航成本，同时保留领域可读性与失败关闭机制的重构；它推进了 coding brilliant and elegant 的
目标，但仍服从后续全量验证、CI、tag 和外部评审，不把单版改进冒充整个项目已经达到九分。
