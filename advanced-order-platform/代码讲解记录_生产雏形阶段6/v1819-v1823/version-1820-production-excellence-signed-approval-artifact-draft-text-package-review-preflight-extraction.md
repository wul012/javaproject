# v1820 生产卓越拆分讲解：草稿文本包审查预检注册表迁出

## 入口路由

v1820 处理 `TextPackageReviewPreflight`，它位于 v1819 的文本包接收目录之后、
真正的提交预检之前。这里的“审查”不是读取真实草稿、打开附件或判断内容好坏，而是把
上一阶段列出的二十五项预期字段转成二十五条审查标准和二十五条拒绝控制。系统回答的是
“以后审查时要核对什么、哪些状态必须拒绝”，并不回答“这份文本是否批准”。

九个 GET 路由保持不变：catalog、identity criteria、digest recheck、
signature envelope、source evidence、operator value handle、policy review state、
execution lock controls 和 archive closeout。两个 Controller 继续位于根 `ops` 包，
因此 Spring 映射、调用方 URL 和响应类型的外部观感不变。内部 Service、Response、
Support、CriteriaCatalog 与控制目录迁入新包，入口稳定，所有权收紧。

新的
`OpsShardReadinessSignedApprovalArtifactDraftTextPackageReviewPreflightRoutePaths`
成为九个后缀的唯一所有者。根路由聚合器继续暴露历史常量，但值来自叶子所有者；迁出的
Service 直接使用叶子所有者。这样既没有复制 URL，也没有把巨型根聚合器提升为公共 API。

请求链路因此很透明：旧 URL 进入根 Controller，Controller 调用新包 Service，Service
选择 Criteria、RejectionControl 和 Gate 的切片，Support 组装只读 Response。没有文件
上传、数据库写入、消息发送或外部连接。

## 响应模型

`TextPackageReviewPreflightResponse` 描述项目、版本、只读标志、执行许可、来源计划、
审查状态、草稿状态、签名状态、批准状态、值导入状态、运行时状态，以及 Criteria、
RejectionControl、Gate 和 checks。它的作用是把审查前必须具备的证据与必须关闭的能力
放在一个可枚举对象里。

`ReviewCriterion` 回答审查员应核对什么。例如身份是否稳定、摘要是否匹配、签名信封是否
只有元数据、来源文件是否只以引用出现、值是否只有脱敏句柄。`RejectionControl` 回答不满足
时如何 fail-closed。`ReviewGate` 回答即使标准齐全，当前仍不能做什么。三层组合避免把
“标准已定义”误读成“文本已通过”。

Support 会复制列表、统计通过数量、加入固定检查项，并计算最终状态。各 Service 只选择不同
切片，不各自实现状态机。本版没有改变 record 组件、切片顺序、状态算法或 profile 字符串，
只改变 Java 包归属。

SpotBugs 中两处响应 FQN 同步迁移，覆盖的仍是原来含列表的同一个类型。这不是新增排除，
而是让既有静态分析边界跟随类型移动。

## 上游证据配置

本阶段只消费 v1819 `TextPackageIntake` 的公开 endpoint。FoundationCriteriaCatalog
读取 identity correlation、digest binding、signature envelope；AssuranceCriteriaCatalog
读取 source evidence、operator value handle、policy review state、execution lock 和
archive closeout。Criterion 保存的是来源地址，不是来源中的敏感值。

这种单向关系让职责清楚：Intake 说明未来包应有哪些字段；ReviewPreflight 把字段转为审查问题；
后续 SubmissionPreflight 再决定提交前还需哪些 slot。每一站只读取上一站公开、不可变的
endpoint，不读取上一站 package-private 目录，也不复制上一站 route 字符串。

v1819 先迁出 Intake 并公开 endpoint，所以 v1820 的出边已经满足。若跳过这个顺序，本版会
同时修改 Intake 内部可见性、ReviewPreflight 包结构和 SubmissionPreflight 入边，风险更大。
链式拆分把复杂依赖分解成每版一条相邻边。

输入仍是静态证据定义，不是数据库记录、文件流、密钥或审批意见。本项目没有借拆包之名增加
新的跨项目事实。

## 服务层核心流程

CatalogService 返回完整二十五条 Criteria、二十五条 RejectionControl 和二十条 Gate。
IdentityCriteriaService 聚焦身份，DigestRecheckService 聚焦摘要复核，
SignatureEnvelopeService 聚焦签名元数据，SourceEvidenceService 聚焦来源引用。
后四个保障服务分别描述脱敏值句柄、策略状态、执行锁和归档收尾。

每个 Service 保留 `@Transactional(readOnly = true)`。它们不注入 Repository，
不访问 RabbitMQ，不连接 mini-kv，不启动进程。Service 只是选择目录切片并交给 Support。
Controller 只做映射与委托，不承载目录构造逻辑。

新包把同一阶段的实现聚在一起。维护者想查某个审查 endpoint 时，可以在一个目录内沿
Service、CriteriaCatalog、RejectionControlCatalog、Support、Response 阅读，不再在
九百多个根文件里依靠超长前缀猜测归属。

两个 Controller 不合并，也不搬走。它们分别承接基础审查和保障审查，职责仍清楚。减少根包
压力不能靠制造一个更大的 Controller，而应迁出真正的内部实现。

## GateCatalog 为什么合并

新增 RoutePaths 会多一个 Java 文件。为保持总 `ops` Java 文件数 1,352，本版检查内部目录，
选择把 GateCatalog 并入 RejectionControlCatalog。二者都描述审查阶段的 fail-closed 边界，
都是 package-private，都由同一组 Service 和白盒测试消费，变化原因与生命周期一致。

合并后 `allControls()` 和 `controls(...)` 仍返回拒绝控制，`allGates()` 和 `gates(...)`
仍返回 Gate，类型、方法和测试保持分离。Gate 测试继续保留概念名称，只把调用对象改为合并后的
RejectionControlCatalog。概念没有消失，文件跳转减少。

这不是为了数字而硬凑。若两个目录职责无关，宁可总量增加，也不应塞进巨型文件。当前目录都是
静态列表，没有复杂流程；合并后规模仍能按方法边界阅读，且拒绝控制与关闭能力本来就是同一审查
防线的两个视角。

最终根包移除十六个非 Controller 文件，新包有十五个实现文件，加一个 RoutePaths，整体总量
不变。根包从 977 降为 961，治理收益可测量。

## 下游消费如何对齐

直接下游是 `TextPackageSubmissionPreflight`。它的 FoundationSlotCatalog 和
AssuranceSlotCatalog 都读取 ReviewPreflight CatalogService 的 endpoint，用来说明提交
slot 的来源。本版将九个 Service 的 `ENDPOINT` 设为 public static final，并只在下游导入
需要的 CatalogService，没有公开内部 Criteria 或控制目录。

`SignedApprovalDraftTextPackageProfileSectionRegistryService` 也消费 ReviewPreflight catalog，
SourceCatalog 持有它的 Response。本版同步更新两处 FQN，保证综合视图仍然能串联 Intake、
Review、Submission 等阶段。

测试侧也有相同读边：SubmissionPreflight Support 测试和 ProfileSection 测试辅助类会直接
构造 Service。只修生产代码而忽略测试构造器，会在 test-compile 才失败。因此本版先编译主代码，
再用测试编译器穷举真实消费者。

下一版若迁出 SubmissionPreflight，它已经只依赖 v1820 的公开 endpoint，这条线性链可以继续
向前推进。

## Java 证据检查

主编译验证 Controller 注入、SubmissionPreflight 入边、ProfileSection 响应组合和新路由所有者。
测试编译验证根 Controller 测试、RoutePaths 测试、迁入包的白盒测试和下游测试辅助类。编译器
给出的每一个缺失符号都对应真实边，不依赖人工猜测。

`ReadabilityUpkeepOpsConsolidationExtractionV1820Tests` 检查文档索引、代表性文件的新位置、
两个 Controller 的旧位置、GateCatalog 的消失、根文件上限 961 和总文件上限 1,352。
三组历史 count ratchet 同步从 977 降到 961，防止数字漂移。

RoutePaths 测试继续验证 Service endpoint 等于根 BASE_PATH 加后缀；根聚合器代码则明确委托
新叶子所有者。Spotless 负责长导入格式，JaCoCo 保护包级覆盖率，SpotBugs 验证 FQN 迁移没有
产生新告警。

讲解文件受自动合规测试约束，必须包含标准章节、中文主体、三千以上汉字、实际工作量说明、
“禁止硬凑”和“本项目”。解释本身也是可验证产物。

## mini-kv 证据检查

mini-kv 不参与 v1820。没有启动服务，没有读取 WAL、快照或分片状态，没有修改 C++ 文件，
也没有新增跨项目 schema。Java 只是整理自己的只读审查目录。

只有证据契约、字段 schema、归档路径或真实运行流程变化时，才需要按上游到下游协同。本版
URL、响应组件和 archive 路径都不变，因此不应制造 mini-kv 工作量。

历史 `e/<version>/` 目录保持原位。Node 可能按绝对路径和摘要引用这些证据，源码包移动不能
成为迁移归档的理由。文档继续明确 Do not rename or move archive roots。

不触碰无关仓库也是质量证据：变更范围准确，回滚简单，没有把 Java 内部维护扩散为系统级风险。

## 阻断与安全边界

本版不解析草稿文本、不读取分离签名、不授予批准、不导入操作员值、不打开写路由、不启动 Java
或 mini-kv、不连接托管审计、不部署、不回滚。公开 endpoint 只是不可变地址，不是能力开关。

RejectionControl 对身份变化、摘要不匹配、原始签名、原始来源、原始值、策略缺失和锁打开给出
拒绝码。Gate 则持续声明审查仅限标准、接受仍是未来步骤、运行时与兄弟仓库变更关闭。

包迁移没有改变这些内容。若未来有人在该包加入文件解析或 Repository 写入，应被视为新功能，
需要重新设计和评审，不能假装是现有预检的自然延伸。

本版可通过还原包声明、导入、路由委托、Gate 文件和 count ratchet 回滚，不涉及数据迁移或
消息补偿。

## 测试覆盖

迁入包的测试覆盖九个 Service、CriteriaCatalog、RejectionControl、Gate 和 Support。
它们需要访问 package-private 目录，因此跟随实现移动。Controller 与 RoutePaths 测试留根，
验证公共入口和兼容层。

下游 SubmissionPreflight 与 ProfileSection 测试证明证据链没有断。Focused 测试会覆盖本家族、
下游、readability 和 walkthrough compliance；之后执行完整 verify。

全量 verify 会运行全部单元与集成测试、构建 jar、执行 JaCoCo 和 SpotBugs。只有局部测试通过
不能作为版本完成依据。

Windows 超长类名也被真实验证：移动阶段使用长路径安全处理，Git longpaths 保持开启，最终
仍由 Maven、Git 和 CI 在同一文件布局下验证。

## 实际工作量说明

本版处理十五个实现文件和六个白盒测试迁移，新增路由所有者，合并 Gate 与 RejectionControl，
修复两个 Controller、两个 Submission slot 目录、ProfileSection、多个根测试和测试辅助类，
公开九个 endpoint，更新九个根路由委托、两处 SpotBugs FQN、三组 count ratchet，并新增
治理测试、ops 文档、索引、进度、changelog 和中文讲解。

真正困难的是可见性与依赖闭环：哪些类型公开，哪些留包内，哪些测试跟随，哪些入口留根，哪些
下游只读取 endpoint。公开过多会扩大 API，公开不足会断编译。编译器审计与分层测试用于找到
恰好的边界。

禁止硬凑意味着不能为了版本数量添加无关类，也不能为了维持总量把不相干职责塞进一个文件。
本项目选择合并 Gate 与 RejectionControl，是因为二者确实属于同一审查防线；同时保留独立方法、
类型和测试，避免语义混淆。

结果是根包 977 到 961、总量不增、路由不变、响应不变、下游可继续迁移。每项结果都能由代码、
测试和文档交叉验证。

还有一部分容易被忽略的工作是 Windows 长路径处理。ReviewPreflight 的类名前缀很长，目标包名
又必须表达完整领域，部分目标路径超过传统 Win32 长度。普通 `git mv` 只成功移动了短文件，
其余文件明确返回无效参数。本版没有因此临时缩写包名，也没有复制后删除造成内容风险，而是先核验
源目录和目标目录都位于仓库内部，再使用长路径前缀完成同一文件系统内移动，最后由 Git 按内容识别
重命名。这样包名仍能准确表达职责，历史文件内容未被重写，移动过程也留下了可复查的状态。

此外，编译审计不是一次命令装饰。第一次主编译列出了两个 Controller、两个 Submission slot
目录和 ProfileSection 的三十二个缺失符号；第一次测试编译又列出 Controller 测试、RoutePaths
测试、Submission Support、Profile 测试辅助类以及包内 Criteria 测试的二十一个缺失符号。
这些结果逐项转化为最小导入，不通过公开内部目录来绕过错误。由编译器给出的清单反推边界，比只用
文本搜索更可靠，也解释了为什么本版需要完整工作量而不能几分钟草率结束。

## 后续维护怎么读这一刀

先读新 RoutePaths，确认九个后缀；再读两个根 Controller；然后进入新包按 CatalogService、
CriteriaCatalog、RejectionControlCatalog、Support、Response 阅读。这个顺序能看清入口、
审查标准、拒绝逻辑和输出。

再读 SubmissionPreflight 两个 SlotCatalog 与 ProfileSection，确认它们只消费 public endpoint
或 public response。最后读 v1820 extraction test 和 count ratchet，确认目录治理没有回退。

下一自然目标是 TextPackageSubmissionPreflight，但它文件更多，需重新做边缘普查，不能因为链条
连续就跳过审计。若发现额外入边，应按编译器事实处理。

维护规则不变：找不到合理合并点时接受总量变化；涉及契约或归档时停止纯内部推进，转入跨项目评估。

## 一句话总结

v1820 在不改 URL、不改响应、不解析真实文本、不打开批准或运行能力、不触碰历史归档的前提下，
把草稿文本包审查预检实现迁入专属维护包，将根 `ops` 文件数由 977 降到 961，并为下一站
`TextPackageSubmissionPreflight` 建立了公开、只读、单向的 endpoint 边界。
