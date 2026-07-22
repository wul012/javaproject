# v1889 MinimalReadOnlyGateExecution Registry Catalog 收敛代码讲解

## 入口路由

本版本处理的入口仍是
`GET /api/v1/ops/shard-readiness/minimal-read-only-gate-execution-registry`。客户端进入根包中的
Controller 后，Controller 仍只调用原有 RegistryService，既没有新增参数，也没有引入新的运行时
分支。Service 的 `registry()` 仍带有 `@Transactional(readOnly = true)`，所以这次重构没有借整理
Catalog 的机会改变事务语义。路由后缀仍由 ReleaseAcceptance 的公共 RoutePaths owner 提供，
`RESPONSE_VERSION` 仍是 `Java v1312`，profile 仍是
`java-shard-readiness-minimal-read-only-gate-execution-registry.v1`。外部调用者看到的入口、HTTP
方法、响应类型和序列化字段完全不变。

本版本的 family 设计在实现前先固定为七条边界：`RegistryCatalog` 是七组只读证据的唯一数据
owner；数据继续使用 Response 中的类型化记录；Service 只调用一次 `evidence()`；Support 继续
计算状态和 checks；`ExecutionRenderer` 继续负责 Markdown；新 owner 不超过三百行；公共 route、
Response、Controller、顺序和只读事务不变。这七条不是事后总结，而是开工约束。它们防止一个
看似简单的文件合并偷偷演变成 Response 改造、状态算法迁移或通用 map 抽象。本刀只让数据归属
清楚，不扩张外部能力。

通俗地说，入口可以看成一个只读展柜。以前展柜管理员要依次去七个小柜子取七摞材料，再把材料
交给统计员和排版员；现在七摞材料由一个明确命名的资料柜一次交出。展柜地址没有搬，管理员没有
获得执行按钮，统计员和排版员也没有换职责。变化发生在内部取件方式，而不是用户能做什么。

## 响应模型

响应不是一个含义模糊的字符串字典，而是一个公开 record。它先给出 project、version、
readOnly、executionAllowed、是否启动 Java、是否启动 mini-kv、是否读取凭据值、是否解析原始
endpoint、是否允许 managed audit HTTP 等标量边界；随后给出 endpoint、profile、来源计划、
状态和各类计数；最后承载七组类型化领域列表、六段 Markdown、二十条 checks 与最终 status。
七组领域列表分别是 `SourcePlanEntry`、`ReadTarget`、`GateCheck`、`BoundaryRule`、`CiBatch`、
`ArchiveRequirement` 和 `OperatorHandoff`。调用者不必猜 map 中某个键对应什么数据，编译器可以
直接检查每个字段的类型和访问方式。

旧实现先给出不可争辩的基线：九段集合向量为 `5/5/20/10/4/6/5/6/20`，依次表示五条来源
计划、五个读取目标、二十项门检查、十条禁止边界、四个 CI 批次、六项归档要求、五步人工交接、
六段 Markdown 和二十条聚合 checks。完整 sorted-property UTF-8 JSON 的 SHA-256 是
`8f33da2c1ed32695ef245c69cbf4a90d4b5b62324bb98e13c115ebec26df0b36`。这份摘要覆盖响应
全部标量、所有嵌套字段、列表顺序和文本内容，因此替换后不能只挑几个计数自证兼容，更不能修改
摘要期望来迁就新实现。

`RegistryCatalog.Evidence` 是新的内部所有权边界。compact constructor 对七组列表逐一执行
`List.copyOf`。这意味着调用方即使保留原可变列表并在构造后清空，也不能改变 Evidence；同时从
Evidence 取得的列表也不能被追加。这里的不可变性不是因为当前数据碰巧来自 `List.of`，而是由
领域边界自己保证。未来某组数据改为计算生成时，调用者依旧得到同样稳定的快照。公开 Response
及其字段没有被触碰，Support 原有的二次边界保护仍在，因此兼容性与内部所有权同时成立。

## 上游证据配置

第一组来源计划记录 Node v349、v364、v365、v366 和 v367 的证据血缘。v349 表示最早的五个
只读目标 smoke lane；v364 固定常规门；v365 固定 CI 与 operator 检查；v366 明确外部读窗口
决定；v367 是当前五个读取目标和二十项检查全部通过的归档结论。这些条目是历史证据描述，不是
Java 在运行时去启动 Node，也不是 Java 从磁盘重新解释 Node 工件。数据仍按旧顺序构造，完整
响应 oracle 会在任何标题、角色、结果或计数变化时失败。

第二组读取目标把地址与动作拆开。Java 侧只有 health 和 ops overview 两个 HTTP GET；mini-kv
侧只有 HEALTH、INFOJSON、STATSJSON 三个 TCP 只读命令。`addressHandle` 保存的是
`ORDER_PLATFORM_URL handle` 或 `MINIKV_HOST/MINIKV_PORT handle`，不是带协议和凭据的原始
URL。每个目标都明确 `readOnly=true`、`externallyStarted=true`、`status=passed`。因此目录表达
的是“如果外部操作员已按窗口启动服务，可以读什么”，不是“本服务有权替你启动和操作什么”。

其余五组配置承担不同问题。GateCheck 说明二十项证据为何通过；BoundaryRule 说明十类动作为何
全部禁止；CiBatch 固定 focused、grouped、build、smoke 的顺序与阻断关系；ArchiveRequirement
说明 JSON、Markdown、summary、截图、讲解和 Java manifest 应由谁产生；OperatorHandoff
说明外部操作员要确认读窗口、保持 actions disabled、按序运行检查、归档结果并在契约无效时停止。
把这些数据放进同一 Registry Catalog，并不抹平类型边界，反而让“一个响应所需的完整静态证据”
成为清晰责任。

## 服务层核心流程

旧 Service 在一个方法里依次调用七个长名 Catalog：先取 sourcePlans，再取 readTargets、
gateChecks、boundaryRules、ciBatches、archiveRequirements 和 operatorHandoffs。每个 Catalog
只有一个静态列表和一个很薄的构造 helper，七个文件没有独立生命周期、依赖或策略。文件边界看似
细，实际让读者为了理解一次响应在七个标签页之间跳转，并让类名重复整条业务上下文。第三个同形
文件早已超过“三次规则”，继续保留并不等于单一职责，而是把同一个职责切成维护噪声。

新流程先执行一次 `RegistryCatalog.evidence()`。Catalog 内部仍按原顺序构造七种类型，最后由
`Evidence` 接管列表。Service 接下来把 Evidence 的七个 accessor 交给原 Support；渲染所需的六组
数据则交给原 `ExecutionRenderer.render(...)`。Service 中只有一个目录装配入口，结构测试会统计
`RegistryCatalog.evidence()` 恰好出现一次。以后新增或审查一组基础注册表静态证据时，维护者先看
一个 owner，而不是搜索七个高度相似的类名。

职责边界仍是三层。Catalog 只回答“固定领域数据是什么”；Renderer 只回答“这些数据如何排成
Markdown”；Support 只回答“计数、通过数、拒绝数、checks 和 status 如何计算”。Support 会统计
五个 passed read target、二十个 passed gate check 和十个 denied boundary rule，仅当三个数都与
集合总数相等时返回 `passed`，否则返回 `blocked`。本版本没有把这段行为搬进 Catalog，因为那会
把静态资料与判定策略混在一起。新抽象之所以更短，不是删掉业务语义，而是让数据、呈现和判定各自
只有一个可指出的 owner。

## Java 证据检查

Java 证据只包含两个读取目标。`java-health` 对应 `GET /actuator/health`，用于确认服务进程能够回答
健康请求；`java-ops-overview` 对应 `GET /api/v1/ops/overview`，用于读取运维概览。两者都由
`java-operator` 负责，地址只以 `ORDER_PLATFORM_URL handle` 出现。目录没有保存原始主机名、
token 或账号，也没有 POST、PUT、PATCH、DELETE。Registry 自己的七个布尔边界继续表明
`executionAllowed=false`、`startsJavaService=false`、`readsCredentialValue=false` 和
`managedAuditHttpAllowed=false`。

测试不只断言数量。RegistryServiceTests 固定 project、version、endpoint、profile、来源版本、
状态和通过计数；Controller 测试保护公开 HTTP 适配；完整 JSON oracle 保护所有字段；新 Catalog
测试保护 Node 版本序列、两个 Java 目标和全部边界状态。Java 侧所谓“通过”，只说明冻结的只读
证据形状与门规则一致，不意味着执行了真实订单写入、失败事件 replay、部署或回滚。

## mini-kv 证据检查

mini-kv 的三个目标分别是 HEALTH、INFOJSON 和 STATSJSON。HEALTH 读取健康状态，INFOJSON
读取结构化信息，STATSJSON 读取结构化统计；它们都通过 `MINIKV_HOST/MINIKV_PORT handle`
寻址，且要求 mini-kv 由外部操作员预先启动。目录不会解析真实 endpoint，不会从环境中读取凭据
值，更不会创建连接后偷偷执行写命令。`externallyStarted=true` 在这里尤其重要，它把“被观察的
服务”与“负责启动服务的主体”区分开。

BoundaryRule 同时明确排除 LOAD、COMPACT、RESTORE、SET、DEL 一类写入或管理动作。即使未来
有人误把一个危险命令加入读取目标，完整 JSON oracle、Catalog 语义测试和十条边界检查也会暴露
变化；只要某条 BoundaryRule 变为 allowed，Support 统计出的 denied 数就小于总数，最终 status
会转为 blocked。Java 当前只是保存 mini-kv 只读窗口的治理证据，不是 mini-kv 客户端，更不是
跨项目执行编排器。

## 阻断与安全边界

十条 BoundaryRule 是本响应最重要的否定能力清单：不允许 write routing，不允许 active shard
router，不读取 credential value，不解析 raw endpoint URL，不连接 managed audit，不执行部署或
回滚，不自动启动 Java，不自动启动 mini-kv，不执行 mini-kv 写入/管理命令，也不执行 Java ledger、
schema 或 SQL 写入。每条规则的 `allowed` 都必须是 false，并附有 owner、forbiddenAction 和
rationale。安全边界不是藏在注释中的默认假设，而是响应中可被机器读取和计数的事实。

二十项 GateCheck 从四个角度闭环：五项 read-target 检查、五项 runtime-boundary 检查、五项
archive 检查和五项 lineage 检查。Support 不信任一个笼统的“passed”字符串，而是重新统计每个
GateCheck 的布尔值以及每个 ReadTarget 的状态。只要有一个读取目标不再 passed、有一个检查为
false，或者有一条禁止规则被改成 allowed，最终状态就不能保持 passed。这是 fail-closed：缺证据
时停止，而不是推断可以继续。

重构时最危险的诱惑是顺便“优化”这些边界，例如把 false 改成默认值、把十条规则合成一条文本、
把 handle 换成真实 URL，或让 Service 自动补齐失败项。本版本明确不做这些事。完整响应摘要在旧
实现上冻结，任何字段、顺序或文本的变化都会使 SHA-256 不同；结构门又要求 Renderer 与 Support
仍在原职责位置。这样既防止功能漂移，也防止重构以更短代码为名削弱可审计性。

## 测试覆盖

验证顺序刻意分为“先冻结、再替换、再扩圈”。第一步在七个旧 Catalog 仍存在时新增
`RegistryResponseOracleTests`，测得向量 `5/5/20/10/4/6/5/6/20` 和完整摘要
`8f33da2c...0b36`；随后把打印式测量器改成硬断言，并在旧实现上再次跑绿。只有这份合同成立后，
才创建 `RegistryCatalog`、改 Service 并删除旧文件。替换完成后同一测试原封不动通过，证明输出
兼容不是靠修改期望获得。

三个按旧文件划分的长名测试壳被合并为 `RegistryCatalogTests`。四个测试分别保护 Node v349 到
v367 的血缘、五个读取目标及无原始 URL、二十项检查和十条禁止规则、七个列表的真实所有权。
所有权测试先从真实 Evidence 复制七个可变 ArrayList，构造新 Evidence 后清空源列表，再确认快照
仍非空；随后逐一尝试追加元素并要求抛出 `UnsupportedOperationException`。它验证的是行为，而
不是简单搜索七次 `List.copyOf`。

历史 v1843 结构门也没有被删除来躲避失败，而是改名为短职责 `ExecutionExtractionTests` 并更新到
当前事实。它精确要求 execution 包只有十七个生产文件、十三个包内测试；七个旧 Catalog 永久
缺席；新 owner 不超过三百行、包含一个 Evidence、恰好七次所有权复制；Service 恰好一次调用
`RegistryCatalog.evidence()`；Catalog 不得引用 Renderer；版本文档必须保存向量、摘要和失败条件。
加上 Service、Markdown、Renderer、名称 baseline、staged change 与全局 ops census，当前核心和
优雅选择共三十九项检查通过。最终 release gate 与远端 CI 仍必须在讲解和归档完成后执行，未执行
前不把本版称为完成。

## 实际工作量说明

生产侧删除七个长名 Catalog，新增一个 284 行 `RegistryCatalog`，因此生产文件净减少六个。
Service 从七次分散静态调用变成一次类型化 Evidence 装配。测试侧删除三个长名 Catalog 测试，
新增一个合并后的语义测试和一个完整响应 oracle，并把被触碰的 v1843 长名结构测试改为
`ExecutionExtractionTests`，没有保留转发类。生产 Java 文件从 1,358 降到 1,352，测试文件从
905 降到 904；ops 从 1,226 降到 1,220，Catalog 从 309 降到 303，execution 子包从 23 降到
17。这些数字都由仓库脚本或精确文件清单复现。

长名债也同步收紧。生产长文件 stem、长标识符使用次数、长标识符唯一值从
`1126/20107/2685` 降到 `1119/20072/2678`；测试从 `725/9866/3719` 降到
`721/9856/3710`。精确 baseline 由同一 census 脚本重建，只删除已经不存在的文件与标识符，
没有加入新债。`OpsEleganceCensusTests` 进一步把 ops、Catalog 和 execution 包上限收紧到
`1220/303/17`，`JavaEleganceGateTests` 把六个名称指标钉到本次实测值，下一版不能反向放宽。

284 行接近但没有越过三百行，这里没有为了追求更小数字硬拆第二个同形 owner。七组数据共同服务
一个 Response，全部是静态配置，且每组由独立类型和私有构造方法隔开；把其中三组再拆成另一份
Catalog 会重新制造本次正在消除的跳转。反过来，Renderer、Support 和 archive-verification
registry 都有独立行为或消费者，所以没有塞进这一个文件。本节记录真实选择与代价，禁止硬凑
篇幅，也不以文件数下降掩盖任何契约变化。

## 一句话总结

本项目先用完整响应摘要冻结“什么都不能变”，再让一个短、类型化、不可变的领域 owner 取代七个
长名静态壳；Service 更容易读，数据、渲染和判定边界更清楚，而 Java、mini-kv、写入、凭据、
部署与回滚权限仍全部关闭，最终结论只以完整 release gate、双 CI 和 canonical tag 为准。
