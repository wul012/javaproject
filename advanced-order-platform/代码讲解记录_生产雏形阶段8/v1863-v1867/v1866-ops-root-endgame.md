# v1866 Java ops 根包终局与静态发布目录拆分讲解

## 实际工作量说明

本版本不是把四个文件从一个目录拖到另一个目录，也不是为了让统计数字看起来更小。它完成的是持续数十个版本的 Java ops 根包治理终局：先把运行概览的服务与响应模型放回自己的业务语境，再把静态发布证据从一个混合了目录、调度、数据构造和常量解释的文件中拆开，最后用机械检查证明根包只剩真正承担 HTTP 入口或跨族组合职责的 104 个文件。实际改动覆盖生产源码、单元测试、SpotBugs 基线镜像、根包 census、豁免表、历史结构守卫和版本讲解。迁移前的直接根包有 108 个 Java 文件，其中 100 个是控制器，4 个是经审查保留的共享核心，另外 4 个就是本轮处理对象。迁移后的输入数字为 104 个保留文件，输出数字也是 104；可迁移项从 4 变成 0，未归类项保持 0，总 ops 生产文件数保持 1352，没有通过新增转发壳或复制类型来换取目录整洁。

工作量的重点在静态发布支持。原来的调度表有 645 行，它既保存 12 个证据段的选择顺序，又构造发布核验、部署回滚、交接清单、保留策略、操作员签字、回滚审批、SQL 审查和生产配置边界等数据。简单改包名会让根包数字达标，却把难维护的问题原封不动带到新目录，这不符合本项目的优雅门。因此本轮把它变成 225 行的 `StaticReleaseCatalog` 与 476 行的 `StaticReleaseSections`。这两个数字使用仓库规定的 `StreamReader.ReadLine()` 口径，包含空行，不再使用会漏算空行的 PowerShell 统计。前者回答“有哪些段、按什么顺序装配、对外暴露什么只读目录”，后者回答“每一段的不可变数据怎样构造”。原有 artifact 枚举收进 catalog 成为嵌套公共类型，所以生产文件总数没有增加。这里的拆分是按职责切开，不是按行数随意截断，也没有制造第三套相似构建逻辑。

## 入口路由

根包治理的输入是一份可复现的分类：扫描 `src/main/java/com/codexdemo/orderplatform/ops/*.java`，先识别以 `Controller.java` 结尾的 100 个 Spring HTTP 入口，再识别四个明确保留文件。四个保留文件分别是 `OpsEvidenceService`、`OpsEvidenceResponse`、`OpsShardReadinessEvidenceEndpoints` 与 `OpsShardReadinessRoutePaths`。前三个承担跨多个已提取子包的证据组合或共享响应边界，最后一个是全局路由聚合器。它们不是因为“移动很麻烦”而留下，而是豁免表为每个共享核心给出了可由审查者执行的使用面检查。输入集合之外的文件必须落入某个待迁移 family，不能进入含糊的其他分类。

本轮执行后，`scripts/ops-root-census.ps1` 输出 `DirectRootJavaFiles: 104`、`TargetFinalDirectRootJavaFiles: 104`、`RetainedDirectRootFiles: 104`、`RemainingDirectRootNonControllers: 0` 与 `UnassignedFiles: 0`。这五个数字共同描述终局，缺一不可。仅有 104 不能证明没有误删控制器，只有 Remaining 为 0 也不能证明没有把文件藏进未归类项。因此 v1866 守卫进一步读取真实文件名：控制器必须正好 100 个，非控制器必须与四个保留文件完全相等。任何人以后在根包增加一个方便类、兼容壳或临时目录，都会同时撞上精确数量和精确集合检查。目标只能继续下降，不能因为新增需求把 ratchet 放宽。

输出不是“所有东西都离开根包”。根包仍是外部读者最容易找到 HTTP 入口和跨族组合的位置。治理的机理是让目录层级表达职责：根包展示适配器，子包拥有实现；控制器依赖服务，服务不反向依赖控制器。这样维护者从路由进入时仍能快速定位入口，从业务类型进入时又不会在一千多个同层文件中搜索。v1866 只关闭 Phase 1 的结构合同，不宣称整个工程已经由自己授予最终完成；后续 Phase 2 还要收紧名字、热点、覆盖率、文档和归档保留门，最终结论由外部评审给出。

## 响应模型

运行概览链路的输入来自五类只读来源：订单仓库总数、库存仓库总数、未发布 Outbox 数量、失败事件统计，以及 Spring `Environment` 中的应用名和 profile。`OpsOverviewService` 在一次只读事务中采样当前时间，读取这些仓库，再构造 `OpsOverviewResponse`。响应包含采样时间、应用启动时间与运行秒数、订单数量、库存数量、待发布事件数量、失败事件总量、待审批数量和最近失败时间。它没有写数据库、启动消息发布、执行重放或修改环境。控制器收到 `GET /api/v1/ops/overview` 后只调用 `overview()` 并返回记录，输入和输出的行为都很窄。

迁移前，服务与响应因为控制器在根包而与控制器同层。迁移后，两者进入 `ops.maintenance.overview`，`OpsOverviewController` 仍留在根包并显式导入它们。Spring Boot 的组件扫描覆盖应用根包以下的子包，所以 `@Service` 的发现方式不变；`@Transactional(readOnly = true)` 不变；构造器注入参数不变；JSON 响应记录的组件名、顺序和类型不变；路由字符串也没有移动。单元测试跟随实现进入相同测试包，使包内语境保持一致。集成测试仍从 HTTP 侧验证真实响应，因此如果组件扫描、序列化或路由发生偏差，不会被单纯的文件存在检查掩盖。

`OpsOverviewResponse.Application` 含有 `List<String> profiles`，SpotBugs 对不可变记录边界已有两处经过接受的镜像规则。本轮没有新增排除项，只把两处 FQN 从根包精确改到 `ops.maintenance.overview`。机械门要求新 FQN 恰好出现两次、旧 FQN 为零。这里的输出不是让 SpotBugs 安静，而是让既有策略跟随真实所有权；完整 SpotBugs 分析仍必须保持零新发现。

## 上游证据配置

静态发布目录接收的核心输入是 12 个 artifact 定义。每个定义只有 `version` 与 `endpoint` 两个不可变字段，例如发布核验清单对应 `java-release-verification-manifest.v1` 和 `/contracts/release-verification-manifest.sample.json`，部署回滚证据对应自己的 v1 版本和 sample 路径。其他定义覆盖发布包、交接清单 fixture、审计保留 fixture、操作员签字 fixture、回滚审批人证据、回滚审批交接、审批记录 fixture、SQL 审查门、生产 secret 来源合同和生产部署 runbook。它们是 Node 或操作员可读取的契约定位信息，不是执行部署或读取 secret 的能力。

`StaticReleaseCatalog` 现在拥有这些 artifact，并用一个固定 `DISPATCH_TABLE` 指明 12 个响应段的装配顺序。`build()` 创建以内部 `Section` 枚举为键的 `EnumMap`，逐项调用对应 supplier，然后按明确的响应类型取回每一段，最终返回 `StaticReleaseEvidence`。这个中间记录的输出正好对应 `OpsEvidenceResponse` 所需的 12 个静态发布组件。使用 `EnumMap` 的原因不是炫技，而是让“段标识”和“构造结果”的关系由类型约束；若漏掉某段、放错类型或改变顺序，编译、现有完整 evidence 测试或 v1866 artifact 守卫会失败。

目录还输出 `staticContractEndpoints` 与 `staticContractProbeEndpoints`。前者从两个基础 ops 合同开始，根据参数决定是否包含字段指南，再追加订单幂等边界和 12 个 artifact endpoint，最后用 `List.copyOf` 返回不可变快照；后者只在前者结果前加 `GET `。`OpsEvidenceService` 仍在健康探针、只读窗口和静态合同清单三个位置消费它们。方法从包内旧类型迁移为公共短名 facade，但布尔参数含义、列表顺序与字符串字节均保持不变。发布审批构建器只需导入嵌套 `Artifact` 中三个常量并读取 endpoint，不能访问 sections 的构造细节。

## 服务层核心流程

`StaticReleaseSections` 是包内 final 类，没有公共构造器，也不提供给根包直接选择某一段。它的输入包括 catalog 中的 artifact、固定的发布核验检查列表、数据库迁移方向选项，以及发布审批 rehearsal 的只读 endpoint 常量。它的输出是 12 种 `OpsEvidenceResponse` 嵌套记录。每个静态方法只构造一种响应段，例如 `releaseVerification()` 组合核验模式、必要检查与合同 endpoint；`deploymentRollback()` 描述回滚对象、确认字段和禁止自动执行的布尔边界；`releaseAuditRetentionFixture()` 描述保留天数、证据 endpoint、导出字段和只读限制。

拆分后的关键点是数据与调度分离，而不是把共享常量复制两份。所有构造方法都从同一个 `Artifact` 读取版本和 endpoint；数据库迁移方向只在一个不可变列表中定义；交接、保留、签字与回滚审批所需 artifact 列表由本类的私有辅助方法集中生成。catalog 不知道每个记录构造器有多少参数，sections 也不知道最终装配顺序。前者可以在审查目录完整性时单独阅读，后者可以在核对安全布尔值和 fixture 内容时单独阅读。两边通过 12 个包内静态构造入口连接，接口面比原来一个 645 行全能类更窄。

输入输出示例可以这样理解：目录选择 `ROLLBACK_SQL_REVIEW_GATE` 段，sections 读取该 artifact 的版本和 endpoint，加入审查 owner、必填字段、迁移方向选项和“任何 SQL 执行前必须人工批准”的占位文本，并把 `sqlExecutionAllowed`、`requiresProductionDatabase` 与“改变订单事务语义”等能力保持为 false。最后 `OpsEvidenceService` 把这个只读记录放进整体 evidence 响应。整个链条只生产描述数据，不执行 SQL。类似地，生产 secret 来源合同只列出允许的来源类型、责任人和确认字段，明确 `nodeMayReadSecretValues=false`，不会因为类移动而获得读取 secret 的依赖。

## Java 证据检查

本轮没有修改 `OpsOverviewController` 上的 `/api/v1/ops` 基路径，也没有修改 `/overview`、`/evidence` 或 `/release-approval-rehearsal`。控制器对概览服务的 Java 导入发生变化，但 Spring MVC 映射注解和方法签名保持不变。`OpsOverviewResponse` 的 record 组件及嵌套 record 名称保持不变，因此 Jackson 仍输出同样的字段名和结构。运行时间是每次请求动态计算的，本来就不是固定字节；这里所说的兼容是同一输入状态下字段语义、类型和计算方式不变，而不是要求两个不同时刻的时间戳相等。

静态发布响应的兼容性由更强的多层证据保护。第一层是 v1866 守卫对 12 个 artifact 名、版本和 endpoint 做精确 Map 比较，任何字符变化都会直接失败。第二层是现有 `OpsEvidenceServiceTests` 对整体 evidence 中每一个发布、回滚、保留和安全边界字段做细粒度断言。第三层是 release approval rehearsal 的大量测试继续读取三个关键 fixture endpoint，并核对验证提示与 digest 输入。第四层是完整 Spring 集成测试从真实 HTTP 入口读取响应。第五层是 Node 侧已有冻结契约和 live capstone，最终评审还会针对最终 Java tag 再运行一次，而不是只相信 Java 自述。

迁移没有修改静态资源文件，没有重写 fixture JSON，没有移动 `a/` 到 `f/` 的历史归档，也没有改变 Node 硬编码的路径或摘要。输出路径仍以 `/contracts/` 开头，发布包仍指向同一个 Maven jar 名称，禁止部署、禁止回滚、禁止 SQL、禁止读取 secret 的布尔值保持原样。任何为让测试通过而改期望值或 fixture 字节的做法都被规则禁止；本轮采用的是编译器引导导入迁移和结构守卫收紧。

## mini-kv 证据检查

mini-kv 在本轮不是运行时依赖，也不是可以由 Java 自动启动的外部组件。本项目只继续维护既有的只读契约边界：Java evidence 可以描述 mini-kv 相关候选、冻结样本和未连接状态，但不会建立网络连接、写入键值、读取凭据或把候选适配器切成活动幂等存储。Node 若要联合消费 Java 与 mini-kv，必须分别读取各自的真实输出，并由最终 capstone 对齐；Java 的目录迁移不能替 mini-kv 生成证据，也不能改动其归档路径。v1866 没有触碰任何 C++ 文件、fixture 字节或跨项目 digest，所以 mini-kv 的输入与输出保持原状。

可见性按真实读者设计。`StaticReleaseCatalog` 是 public final，因为根包 `OpsEvidenceService` 需要调用 `build()` 和两个 endpoint 查询，发布审批子包需要读取嵌套 `Artifact` 的三个常量。`StaticReleaseEvidence` 是公共不可变记录，因为根包组合器需要访问 12 个组件。`Artifact` 的 `version()` 与 `endpoint()` 公开，但字段仍私有且 final。除此之外，调度条目、段枚举和构造器都保持私有。`StaticReleaseSections` 整个类为包可见，其 12 个构造入口也只对同包 catalog 可见，辅助列表与版本解析继续私有。

依赖方向的输入是根包适配器对实现的需求，输出应当是单向的“根包组合层指向子包实现层”。`OpsOverviewController` 导入 overview 服务与响应；`OpsEvidenceService` 导入 evidencecore catalog；release approval builder 导入 catalog 的 artifact。反方向不允许：overview 服务不能导入控制器，sections 不能依赖根路由聚合器，catalog 不能调用部署或回滚执行器。v1866 源码守卫会检查关键导入与禁止字符串，Java 编译器会检查类型可见性，完整测试会检查 Spring 装配。

这种可见性偿还了两类债。第一类是为了跨包迁移临时公开内部字段，最终应该在读者迁入后收回；RouteCleanup 在 v1865 已完成这一步。第二类是为了让一个全能类在根包内被方便调用而默认共享全部实现细节。v1866 通过 public facade 与 package-private sections 切开，不再让根包看到 12 个记录如何逐参数构造。以后新增静态发布段时，维护者必须同时在 artifact、dispatch 和精确测试中登记，不能悄悄塞进一个巨型方法。

## 阻断与安全边界

验证从快到慢分层执行。主源码编译首先证明包移动、嵌套 enum、公共 facade 与方法引用在 Java 21 下成立；本轮主编译一次通过。测试编译再证明移动后的概览单测、历史 v1854 结构守卫和所有根包读者都已指向新位置，也一次通过。随后运行 `scripts/ops-root-census.ps1`，真实输出为根包 104、目标 104、保留 104、剩余 0、未归类 0。这个脚本是审查者与执行者共用的数字来源，不从进度表反推。

新增 `OpsExtractionV1866Tests` 有八个独立检查。它验证旧四个根文件不存在、新包文件集合精确；验证 100 个控制器和四个共享文件组成唯一保留集合；验证 12 个 artifact 契约精确；验证 catalog 不超过 250 行、sections 不超过 500 行、短名文件不超过 40 字符；验证根组合层的导入方向；验证 SpotBugs 两个镜像；验证 census 与豁免表；验证本讲解恰好十个标题、至少三千个汉字、中文占主导且包含“禁止硬凑”和“本项目”。它不是把实现文本复制到测试里，而是把最容易回归的边界变成会失败的门。

第一次修复标题后的完整验证运行了 1900 个测试，所有测试都通过，但 JaCoCo 仍然正确阻断了构建。原因不是新包没被测到：`evidencecore` 的 215 行可执行行和 `overview` 的 35 行可执行行都是全覆盖。真正的机理是 JaCoCo 对 `com.codexdemo.orderplatform.ops` 设置的 floor 只计算根包；当 35 行已覆盖的概览实现离开根包后，七个保留的 RouteCleanup 控制器中 43 条旧委托线的缺口被暴露，根包实际覆盖率成为 96.37%，低于既有 97% 门槛。这是 ratchet 在发挥作用，禁止为了通过而降低 floor。

覆盖修复新增短名 `RouteCleanupControllerContractTests`，它不为七个控制器复制七份样板。测试把控制器类作为数据列表，用一个反射引擎读取构造器参数、为每个服务生成 Mockito mock，调用所有标注 `GetMapping` 的零参数方法，再检查每个服务恰好收到一次零参数调用。因为每个 endpoint 与每个构造器依赖一一对应，少委托、重复委托或把路由方法改成带参数都会失败。这个共享引擎同时买下覆盖率和可维护性，不是为指标硬凑无意义断言。

聚焦测试还会覆盖 `OpsOverviewServiceTests`、整体 `OpsEvidenceServiceTests`、release approval rehearsal 相关测试、v1854 到 v1866 的结构守卫、根包治理和文档规则。Spotless 保证移动和新文件保持统一格式；SpotBugs 对全部生产 class 重新分析，不能靠新增宽泛排除项；JaCoCo 继续执行全局和分包 floor；最终 `mvnw -B verify` 会运行完整 headless 测试、打包、覆盖率与静态分析。只有本地全部通过、实现提交的两个 Actions job 通过、closeout 提交与 tag 推送且 closeout CI 再次通过，本版本才闭环。

## 测试覆盖

覆盖不是只看新增八项结构测试。概览单元测试验证仓库计数、profile 与时间模型，六组概览集成测试从 Spring HTTP 上下文验证静态发布、回滚和只读 evidence，`OpsEvidenceServiceTests` 精确检查 12 个发布段及所有禁止能力，七组 release approval rehearsal 测试继续校验 fixture endpoint、提示和摘要输入。v1854 到 v1866 的历史守卫验证每次可见性偿还与 live census，新的控制器契约引擎则专门填补根包委托边界。完整套件还覆盖订单、库存、Outbox、失败事件、生产 profile、JaCoCo、SpotBugs 与 Spotless。任何一层失败都不能用修改 fixture 或放宽 ratchet 绕过。

直接收益是根包从开放式待办变成封闭合同。维护者以后看到新增根文件时，不需要靠经验判断是否合理，机械门会要求它是控制器或进入明确豁免；否则构建失败。目录浏览从曾经 1352 个同层 ops 类，收敛为 100 个入口和 4 个共享边界，具体实现分散到有语义的子包。这个变化不会减少系统功能，却显著降低搜索噪声、误改邻近 family 和为迁移临时公开字段的概率。

第二个收益是静态发布证据的阅读路径变短。想知道系统公开哪些静态合同，只读 225 行 catalog；想审计某个安全边界的字段和值，只进 476 行 sections；想知道 HTTP 如何交付这些数据，从 `OpsEvidenceService` 进入即可。原来一个文件同时承担三种阅读任务，任何修改都容易在长文件中越界。现在 catalog 与 sections 都低于 500 行，命名短而直接，且两者之间的边界由方法引用清晰列出。总文件数不增加，说明拆分不是用大量薄壳换取行数指标。

第三个收益是最终治理阶段有可信起点。Phase 2 不再被“还有哪个 family 没迁”干扰，可以集中处理全局路由聚合器的未使用别名、两个 800 行级测试热点、名字 baseline、覆盖率 floor、生产就绪文档和历史归档保留清单。v1866 把结构地基钉死，但不会夸大成生产授权。真实支付、生产 secret、部署、回滚、SQL 和 managed audit 连接仍按既有边界关闭，最终系统级联合测试仍由 Node capstone 和外部评审执行。

## 一句话总结

v1866 用可复现的 104 文件精确集合关闭 Java ops 根包迁移，把概览实现放回 `overview`，把静态发布支持拆成短名 catalog 与包内 sections，并在不改变任何路由、响应、安全布尔值、fixture、归档或生产文件总数的前提下，为每个关键输入和输出建立了会失败的机械证据。
