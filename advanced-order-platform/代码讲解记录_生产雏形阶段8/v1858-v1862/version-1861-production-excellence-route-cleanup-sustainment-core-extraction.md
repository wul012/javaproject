# version-1861：维护持续性核心迁移、预览式修复与风险边界收敛

本篇解释本项目 v1861 的输入、输出、组合流程和失败条件。这里处理的不是五个随意凑在一起的接口，而是维护保障结果进入持续审阅后的五种视图：发布检查、修复预览、证据新鲜度、责任归属和风险台账。全文禁止硬凑；每一项结论都能落回源码、路由常量、响应字段或机械守卫。

## 实际工作量说明

v1861 将 ReleaseChecklist、RemediationQueue、FreshnessWindow、OwnershipRegister 和 RiskLedger 五组 service/response，共十个生产文件，迁入 `ops.maintenance.routecleanup`。五个直接行为测试跟随实现。`OpsShardReadinessRouteCleanupMaintenanceSustainmentController` 保留在根包，继续承担 Spring HTTP 适配，不拥有清单、队列或风险事实。迁移后根包直接 Java 文件从二百零九降到一百九十九，可迁移积压从一百零五降到九十五，RouteCleanup 分类从一百零一降到九十一，总体 `ops` 生产源码仍为一千三百五十二，未分类文件必须为零。

五个服务总计约五百二十八行，最大单文件约一百零二行，不存在借迁移制造新巨型文件的问题。它们的共同主题不是“版本号接近”，而是同一 sustainment 控制器直接暴露、且消费已经迁入家庭包的 v1859/v1860 事实。把整个控制器闭包一次迁入后，ReleaseChecklist 与 RemediationQueue 不再跨包读取 ReadinessGate、UpkeepCloseout 和 FailClosedPolicy，FreshnessWindow 与 OwnershipRegister 不再跨包读取 UpkeepCatalog；包内依赖方向变得连续。

本版不再向共享测试 helper 塞入新职责。v1860 已把文件/讲解操作与边界扫描拆成六十行 `OpsExtractionTestSupport` 和七十一行 `OpsBoundaryTestSupport`。v1861 只提供十文件清单、五条路由、边界期望和公开面策略。这体现三次规则的目的：算法统一一次，后续版本用数据表达差异，版本守卫不复制扫描循环。

## 入口路由

控制器继续提供五个 GET 地址：`/route-cleanup-maintenance-release-checklist`、`/route-cleanup-maintenance-remediation-queue`、`/route-cleanup-maintenance-freshness-window`、`/route-cleanup-maintenance-ownership-register` 和 `/route-cleanup-maintenance-risk-ledger`。基础路径保持 `/api/v1/ops/shard-readiness`，请求方法、完整 URL、响应 JSON 和状态规则都不变化。

五个后缀从全局 `OpsShardReadinessRoutePaths` 移入 `RouteCleanupRoutes`，字段名缩短为 `MAINTENANCE_RELEASE_CHECKLIST`、`MAINTENANCE_REMEDIATION_QUEUE`、`MAINTENANCE_FRESHNESS_WINDOW`、`MAINTENANCE_OWNERSHIP_REGISTER` 与 `MAINTENANCE_RISK_LEDGER`。包名和 owner 已表达 RouteCleanup 上下文，所以字段不再重复长前缀；五个新名字均不超过四十字符。守卫用反射读取家庭字段并比较原始字节，同时确认全局 owner 已删除对应长字段。

根控制器改用 `RouteCleanupRoutes.BASE_PATH` 和五个家庭后缀，构造器注入五个迁入 service，每个方法只调用一个查询方法并返回 response。它不计算版本差、不修改队列、不登记 owner，也不处置风险。控制器留在根包只是 HTTP 边界选择，不是业务逻辑残留。

## 响应模型

ReleaseChecklistResponse 输出五个 ChecklistItem。每项包含名称、来源端点、责任人、证据摘要和状态；顶层记录项目、版本、只读标记、执行许可、端点、配置标识、总项数、接受项数、检查列表和总状态。它把目录基线、就绪门、Upkeep 收尾、来源计划与只读边界压成一份发布审阅清单，却不复制上游完整响应。

RemediationQueueResponse 输出四个 QueueItem，分别对应失败关闭策略漂移、就绪门阻断、执行边界漂移和上游启动漂移。每项保存触发条件、来源端点、建议动作与状态。这里的 queue 是预览，不是任务执行队列；当来源通过时条目为 standby，只有来源失败才为 blocked。顶层同时给出 standby 数和 blocked 数。

FreshnessWindowResponse 保存九个 FreshnessEntry，包括证据名、路径、路由版本、相对最新版本的差值、是否位于窗口及状态。OwnershipRegisterResponse 保存九个 OwnerEntry，包括条目名、消费者、边界、来源端点和状态，并统计去重 owner 数。RiskLedgerResponse 保存五个 RiskEntry，记录风险名、缓解项、责任人、严重度和状态。五种 response 继续是不可变 record，本版只改包归属，不增删组件或改变序列化字段。

这些 response 都含 List 组件，SpotBugs 配置有两组既有镜像。五个旧 FQN 必须各迁两次，结果是十个新 FQN、零个旧 FQN。条目数量保持不变，说明这是路径跟随而不是新增豁免。

## 上游证据配置

ReleaseChecklist 的三个直接输入是 v1859 的 UpkeepCatalogService、v1860 的 ReadinessGateService 和 UpkeepCloseoutService。目录提供九项基线；就绪门提供接受与阻断数量；收尾提供来源计划、检查报告数量和最终状态。该服务只读取类型化响应，不从文件系统或网络重新构造证据。

RemediationQueue 读取 v1859 的 FailClosedPolicy 与 v1860 的 ReadinessGate。它把策略状态、阻断检查数、executionAllowed 和禁止启动上游的要求转换成四个预览项。FreshnessWindow 与 OwnershipRegister 直接读取不可变 UpkeepCatalog：前者使用路由版本和证据路径，后者使用 consumer、boundary 与 endpoint。RiskLedger 的五项风险在服务内显式声明，不读取外部配置，也不执行缓解项。

迁移后当前包外生产边界精确为五个来源、十九条类型边、十个目标类型。来源是 SustainmentController、HandoffAcceptanceDigest、ContractFreeze、ReadWindowEvidence 和 RuntimeBoundaryChecklist。HandoffAcceptanceDigest 将在 v1862 与本组服务同包，届时这条边会正确消失；另外三个未来读者只需要 RiskLedger.ENDPOINT。共享边界 census 会区分这种预期收缩与未知依赖增长。

## 服务层核心流程

ReleaseChecklistService 依次获取 catalog、gate 与 closeout，生成五项。catalog-baseline 记录九项基线，readiness-gate 记录已接受检查数，closeout 记录已检查报告数，source-plan 保留 `Node v549`，read-only-boundary 记录 `executionAllowed=false`。五项来源状态全部 passed 才接受。输入是三份只读响应，输出是发布审阅清单，不会触发发布。

RemediationQueueService 先取得失败关闭策略和就绪门。四个 QueueItem 的 action 是人工审阅提示，例如先查看策略报告、查看 gate 原因、保持执行禁用、不要启动 Java 或 mini-kv。`item` 方法把来源 passed 映射为 standby，把来源失败映射为 blocked；只有 blocked 为零时总状态 passed。它不会调用 action 字符串，不会写数据库，也不会创建后台任务。

FreshnessWindowService 取目录最新路由版本四百八十八，对每项计算 `latestRouteVersion - item.routeVersion`。最大允许差固定二十；差值不超过二十为 passed，否则为 stale。九项版本从四百七十二到四百八十八，当前最大差十六，因此 stale 数为零。算法基于版本化目录，不读取文件时间戳，避免机器时钟和复制时间改变结果。

OwnershipRegisterService 把每个目录 Item 映射为 OwnerEntry，复用 consumer 作为 owner、boundary 作为职责边界、endpoint 作为来源。它统计去重 owner 数，并要求九项齐全、owner 与 boundary 非空、所有状态 passed。该投影让维护责任可查，但不创建账号、角色或权限。

RiskLedgerService 明确列出 route-drift、evidence-staleness、boundary-drift、handoff-owner-gap 和 ci-regression-gap 五类风险。每项有缓解证据名、owner、low 严重度和 mitigated 状态。只有高风险为零且五项全部 mitigated 才通过。字符串中的 mitigation 是证据指针，不是可执行命令；服务不会自动修复路由或 CI。

从依赖拓扑看，这五个服务恰好处于 v1859/v1860 与 v1862 之间。ReleaseChecklist 和 RemediationQueue 向下读取已迁的目录、策略、门禁与收尾；FreshnessWindow 和 OwnershipRegister 读取同一目录事实；RiskLedger 是无外部输入的叶节点。向上只有下一阶段的 HandoffAcceptanceDigest 组合 owner、risk 和 freshness，另外三个保留服务读取 RiskLedger 端点。把五组一起迁移后，所有向下边都变成包内边，向上边则通过明确的公共类与唯一端点常量穿越边界。若只迁其中两三组，下一版仍要维护双向 import 和临时公开字段，既不减少认知成本，也无法给出稳定边界数。

举一个完整输入输出例子：当前目录最新路由版本为四百八十八，最早条目路由版本为四百七十二，FreshnessWindow 算出最大差十六，小于窗口二十，所以九项均 passed。OwnershipRegister 同时把该条目的 consumer 与 boundary 投影为 owner 记录；RiskLedger 把 evidence-staleness 指向 freshness-window 缓解项。HandoffAcceptanceDigest 下一版将读取这三份结果：新鲜度零过期、责任记录完整、风险已缓解时才接受。这个链条说明三种响应不是重复报表，而是分别回答“证据是否还新”“谁负责”“已知风险是否闭合”。

## Java 证据检查

第一组守卫验证十个生产文件与五个测试的新旧路径，根控制器继续存在，迁入实现不反向 import 根包 RouteCleanup 类型。编译器先揭示保留生产读者，再揭示保留测试和共享 fixture 的 import；任何漏项都必须在真实读者处修复，不允许增加转发类。

第二组守卫验证五条路由字节、全局 owner 删除和家庭 owner 字段长度。v1861 当前边界必须是五来源、十九边、十目标。RiskLedger.ENDPOINT 的包外读者必须精确为 ContractFreeze、ReadWindowEvidence 和 RuntimeBoundaryChecklist；其余四个 ENDPOINT 不得有包外生产读者；五个 PROFILE 全部包内可见。

公开性按符号而不是按类一刀切。五个 service 与 response 必须 public，因为根控制器和后续服务需要构造或调用它们；这不意味着其常量全部公开。ReleaseChecklist、RemediationQueue、FreshnessWindow 和 OwnershipRegister 的端点只被同包控制器及未来同包组合服务使用，迁移后没有包外常量读者，因此 ENDPOINT 保持默认可见。RiskLedger 的响应类型供 HandoffAcceptanceDigest 调用，而它的 ENDPOINT 还被三个暂留根包的证据服务直接写入来源字段，所以只有这一项设为 public static final。PROFILE 没有任何生产常量读者，全部收回包内。守卫同时检查修饰符和读者文件名，避免出现“字段公开但没人使用”或“真实读者被遗漏”两种相反错误。

边界数字也不是只看 import 行数。共享引擎先从十个文件名推导十个类型名，再遍历家庭包外所有生产 Java 文件；同一来源引用同一目标无论出现多少次只形成一条来源到类型的边。这样十九条边表达的是依赖形状，而不是格式化后换行数量。来源减少必须能对应文件迁入，目标减少必须能解释公开面回收；无法解释的变化会直接让历史守卫失败。

第三组守卫验证上游边界继续收紧。v1859 因 ReleaseChecklist、RemediationQueue、FreshnessWindow 和 OwnershipRegister 归位，从 `8/19/11` 收缩到 `4/13/11`；v1860 因两个 sustainment 读者归位，从 `5/18/10` 收缩到 `3/12/10`。目标类型没有异常丢失，减少的来源和边都有明确文件解释。守卫还锁定十个 SpotBugs 新 FQN、根包一百九十九、总量不超过一千三百五十二、RouteCleanup 九十一和未分类为零。

## mini-kv 证据检查

v1861 不启动 mini-kv，不执行 `minikv_cli`，不读取实时 shard、slot、WAL 或 snapshot。RemediationQueue 中 `do-not-start-java-or-mini-kv` 是禁止动作文本；ReleaseChecklist 的 source plan 是来源说明；RiskLedger 的 CI 风险是静态维护条目。它们都不是 C++ 运行回执。

FreshnessWindow 使用 Java 路由版本而不是 mini-kv 文件时间，OwnershipRegister 使用 Java 目录消费者，RiskLedger 不连接任何上游。Node 与 mini-kv 的历史归档路径继续冻结，本版不改其他仓库。可以声明 Java 单仓持续性视图通过验证，不能把它写成新的跨项目联调完成。

保持这一措辞能区分两种输入：一类是已经归档并由 Java 投影的静态证据，另一类是真实进程在本轮产生的运行证据。v1861 只处理前者，系统级 capstone 才负责后者。

## 阻断与安全边界

五个服务全部 `Transactional(readOnly = true)`，控制器全部 GET，响应保持 `readOnly=true`、`executionAllowed=false`。没有 repository save、消息发布、文件写入、凭据读取、原始端点解析、托管审计连接、写路由变更、服务启停、部署或回滚。修复队列是只读预览，发布清单是审阅材料，风险缓解是文本指针。

以下任一事实出现必须失败：文件闭包不完整；路由字节变化；响应组件变化；最新版本、窗口二十、五类风险或五项发布清单变化；新包反向依赖根实现；除 RiskLedger 外的 ENDPOINT 公开；RiskLedger 三个读者不精确；PROFILE 公开；历史边界未按依赖归位收紧；SpotBugs 镜像不等于十；根计数不等于一百九十九；未分类不为零；讲解少于三千汉字；为了通过而改 fixture 或旧期望。

失败修复必须指向真实根因。编译错误补 import，边界错误重新核对读者，路由错误恢复原字节，体量错误修正闭包。不得删除断言、上调 ratchet、把预览改成执行器，或为了“统一”而把五种不同判断塞进一个巨型服务。

## 测试覆盖

五个直接服务测试分别锁定：发布清单由目录、就绪门和收尾组成；修复队列在来源通过时全部 standby；证据版本差都在二十以内；九项 owner 与 boundary 完整；五类风险全部低风险且 mitigated。测试随实现迁入同包，可以读取包内字段而无需扩大生产 API。

Sustainment 的 MockMvc 集成测试继续验证五个 GET 地址和 JSON 契约。HandoffAcceptanceDigest 的服务与 fixture 暂留根包，显式 import v1861 类型，证明保留读者仍能通过最小公共类/方法边界组合。根 MaintenanceRoutePaths 测试删除已由家庭守卫接管的五项，其余端点继续受保护。

验证顺序保持编译器导向、聚焦守卫、服务测试、路由与集成、Spotless、最终 `mvnw verify`。讲解必须先于最终门。全量测试数从本轮 Surefire XML 时间窗重建，JaCoCo 与 SpotBugs 从报告读取；提交、annotated tag、push 与远端双作业 CI 全绿后才能关闭 v1861。

## 一句话总结

v1861 把发布检查、修复预览、版本新鲜度、责任归属和风险台账五种持续性视图归入 RouteCleanup 家庭，用精确边界收缩和唯一公开端点证明：维护信息更集中，但预览没有越权成为执行，风险文本也没有伪装成自动修复。
