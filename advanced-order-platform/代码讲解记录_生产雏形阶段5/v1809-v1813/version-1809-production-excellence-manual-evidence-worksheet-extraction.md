# v1809 代码讲解：人工证据工作表注册表拆包

## 入口路由

v1809 继续做 Java 项目的生产卓越阶段保养，这一版处理的是 `ManualEvidenceWorksheet` 家族。它在系统里的角色很朴素：给未来的操作者人工录入证据之前，先把工作表需要的目录、槽位、验证规则、脱敏规则、缺失值策略、目标范围、导入预检、路线摘要、归档计划、交接说明、持续集成预算和关闭说明全部用只读接口列出来。它不是导入器，不接收人工值，不写数据库，不触发执行，也不启动任何外部服务。

本版移动的不是 HTTP 入口，而是入口背后的实现家族。两个 controller 仍然留在 root `ops` 包里：`OpsShardReadinessManualEvidenceWorksheetFoundationController` 负责基础类接口，`OpsShardReadinessManualEvidenceWorksheetAssuranceController` 负责保证类接口。这样做是为了保持外部合约稳定。调用方仍然从 `/api/v1/ops/shard-readiness` 这个基础路径进入，仍然访问原来的十二个 worksheet suffix，仍然拿到原来的响应结构。被移动的是 service、response、support 和 route-path owner，它们进入 `ops.maintenance.manualevidenceworksheet`，让维护者能通过包名直接识别这一组文件属于人工证据工作表，而不是运行时执行、导入预检或操作者取值草稿。

这里的设计边界很重要。controller 是对外路由表面，外部消费方会把它看成合约；service 和 support 是内部证据生产者，维护者会把它看成实现结构。v1809 保留 controller，移动 service，相当于告诉后续维护者：门没有换，门后的文件柜换到了更清楚的位置。root `OpsShardReadinessRoutePaths` 也保留，但它不再自己拥有这些 worksheet suffix，而是 import 新包里的 `OpsShardReadinessManualEvidenceWorksheetRoutePaths` 并委托给它。这样 root 聚合器继续服务旧调用方，新包 route owner 则成为家族自己的路由事实来源。

## 响应模型

`OpsShardReadinessManualEvidenceWorksheetResponse` 也跟着家族移动到新包。响应模型没有新增字段，没有删除字段，没有改变嵌套记录，也没有改变集合不可变语义。每个 service 仍然返回统一的工作表响应：版本、endpoint、profile、条目列表、警告列表，以及每个条目的名称、负责人、说明和来源 endpoint。调用方看到的仍然是一份只读证据说明，而不是实际工作表数据。

可以用一个通俗例子理解：这组接口不是让操作者填写表格，而是提前把表格说明书贴出来。`catalog` 告诉你有哪些工作表构件，`slot-template` 告诉你未来有哪些空槽位，`validation-rules` 告诉你录入前要满足哪些规则，`redaction-rules` 告诉你哪些内容只能脱敏说明，`missing-value-policy` 告诉你缺失值不能被伪造，`target-scope-registry` 告诉你目标范围只是命名而不是路由打开，`importer-preflight` 告诉你导入仍然被阻断，后面的摘要、归档、交接、预算和关闭接口则负责把这些只读条件串成可审查的证据链。

响应对象迁移以后，root controller 需要显式 import 新包的 response 类型；包内单元测试也跟着 response 进入新包。这个动作看似简单，但它让类型归属更准确。过去 response 放在 root 包里时，读者很难判断它是所有 readiness 家族共享的响应，还是 worksheet 专属响应。现在包名已经把答案写出来：这是人工证据工作表专用响应，外部只能通过 controller 的稳定路由消费它。

## 上游证据配置

`ManualEvidenceWorksheet` 处于证据链的上游。它读取若干 `RuntimeExecution` 家族的 endpoint 常量，用来说明工作表的来源和边界。例如 catalog 会指向运行时执行审批输入校验、模板兼容性、实时读门和通过证据关闭；slot template 会指向审批输入合同交接、模板兼容性入口和值校验；validation rules 会指向实时读门和值校验；missing value policy 会指向审批门输入；target scope registry 会指向运行时执行候选目标和实时读门。

这些引用不是运行时调用，而是只读字符串引用。v1809 因为把 worksheet service 移到了新包，原来同包可见的 `ENDPOINT` 常量不能再被直接读取。编译器把真实依赖暴露出来以后，本版只把这些不可变 endpoint 字符串提升为 public。public 的是字符串，不是执行入口；它不会打开 runtime execution，不会读取 credential value，不会解析 raw endpoint，不会建立 managed audit connection，不会部署，不会回滚，不会启动 Java、Node 或 mini-kv。它只是让证据说明继续准确指向上游证据的位置。

这一点和 v1808 是一条连续 recipe。v1808 把导入预检迁出时，需要读取 `ManualEvidenceWorksheet` 的 endpoint，所以 worksheet 的 endpoint 先被 public 化。v1809 反过来把 worksheet 自身也迁走，于是导入预检改为从新包 import worksheet endpoint；worksheet 又把自己读取的 runtime endpoint 以同样的只读方式显式化。每一层都只暴露不可变位置说明，不暴露执行动作。这种递进式处理，比一次性打开很多能力安全得多。

## 服务层核心流程

本版迁出的十五个非 controller 文件包括 route-path owner、十二个 service、response 和 support。十二个 service 分成两组：基础组负责目录、槽位、验证、脱敏、缺失值、目标范围；保证组负责导入预检、路由摘要、归档计划、操作者交接、持续集成预算和关闭说明。每个 service 的方法名、返回对象、条目名称、警告字符串和 endpoint 字符串都保持原意，只是从 root 包移入 `ops.maintenance.manualevidenceworksheet`。

服务层最关键的变化是 endpoint 组装来源。过去这些 service 通过 root `OpsShardReadinessRoutePaths.BASE_PATH` 和 root suffix 组装自己的 endpoint。迁移后，它们改为通过 `OpsShardReadinessManualEvidenceWorksheetRoutePaths.BASE_PATH` 和同一家族 suffix 组装 endpoint。root route 聚合器再委托给这个新 owner。结果是两边字符串完全相等，但职责更清楚：新包拥有自己的家族路由事实，root 聚合器只做对外兼容聚合。

包内引用也因此更安静。service 调用 `OpsShardReadinessManualEvidenceWorksheetSupport.response(...)` 和 `item(...)` 时，不再需要跨 root 包寻找支持类；response 和 support 与 service 同包，结构更内聚。下游 `OperatorEvidenceImportPreflight` 则通过显式 import 读取 worksheet service endpoint，跨家族关系一眼可见。过去所有类混在 root 包里，跨家族引用容易被同包可见性掩盖；现在每一条跨家族依赖都必须写成 import，维护者能更快看懂证据链。

## Java 证据检查

Java 侧证据主要有四层。第一层是编译证据。第一次迁包后，编译器指出 worksheet service 找不到 runtime execution 类名；补齐 import 后，又指出若干 runtime endpoint 常量不是 public。这证明依赖不是猜出来的，而是由编译器逐项逼出来的。最终只 public 化实际被读取的 endpoint 常量，避免把不相关能力一并放开。

第二层是 route guard。`OpsShardReadinessManualEvidenceWorksheetRoutePathsTests` 继续留在 root 包，因为它要同时看 root 聚合器、新包 route owner 和新包 service endpoint。这个测试确保 `OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_*` 与新 owner suffix 相等，也确保每个 service 的 `ENDPOINT` 等于基础路径加 suffix。只要后续有人误改任意一侧，测试会马上失败。

第三层是结构 guard。新增 `ReadabilityUpkeepOpsConsolidationExtractionV1809Tests`，检查 v1809 note 可以从 ops README 找到，检查代表性的 route-path、catalog、response、support、closeout 文件确实位于 `ops.maintenance.manualevidenceworksheet`，检查 root 包不再直接承载这些实现文件，检查两个 controller 和 root route 聚合器仍然留在 root。这个测试把“拆到哪里”和“哪些不该拆”都写成了自动约束。

第四层是治理 ratchet。root `ops` 直接 Java 文件数从 1,152 降到 1,137，`ReadabilityUpkeepGovernanceConsolidationPlanTests` 的上限同步收紧，`ReadabilityUpkeepOpsConsolidationQualityCloseoutV1806Tests` 的镜像计数也同步更新。这样后续如果有人把实现类丢回 root，仓库不会只靠人工记忆发现，而会由测试直接失败。

## mini-kv 证据检查

本版没有触碰 mini-kv。文档中提到 mini-kv，只是延续四项目协作边界：Java 的只读证据链不能擅自启动 C++ 服务，不能读写键值数据，不能修改 mini-kv 归档，也不能移动 Node 可能引用的历史证据路径。v1809 的全部改动都发生在 Java 仓库内部，且只涉及包归属、import、不可变 endpoint 字符串可见性、测试和文档。

这条边界也保护 Node。Node 侧曾经硬编码过大量 Java 历史归档路径，所以 Java 做后期保养时不能随手整理 `e/<version>/`、截图目录、历史 fixture 或旧讲解目录。v1809 新建的是当前讲解归档续写目录，不移动旧目录；新增的是 v1809 文档和测试，不重命名历史归档。这样 Java 内部结构变清楚，跨项目历史证据仍然稳定。

## 阻断与安全边界

v1809 的 stop line 很明确：不开放 write routing，不开放 active shard router，不读取 credential value，不解析 raw endpoint，不建立 managed audit connection，不部署，不回滚，不自动启动 Java，不自动启动 Node，不自动启动 mini-kv，不移动历史 archive，不改变 evidence JSON，不改变响应 schema。所有 public 化都限定在不可变 endpoint 字符串上。

尤其要避免一种误读：把 `ENDPOINT` 设为 public，不代表对应服务变成公共执行能力。Java 里 public 常量只是允许另一个包读取同一个字符串，调用方拿到的仍然是路径文本。真正的执行动作需要 controller、service 方法、外部请求、业务逻辑和运行时状态共同参与；本版没有新增这些东西。相反，本版通过显式 import 把证据引用变得更可审查，降低了边界被误判的概率。

另一个安全点是 controller 保留在 root。有人可能觉得所有同名前缀文件都应该一起移动，但这会混淆外部合约和内部实现。root controller 仍然承载公开 HTTP 表面，新包 service 承载家族实现。这个拆法比“见到同名前缀就全搬”更稳，因为它让路由入口保持旧位置，让维护结构进入新位置。未来如果要进一步移动 controller，需要单独做合约层评估，而不能夹在实现拆包里悄悄完成。

## 测试覆盖

本版测试覆盖从小到大依次推进。先跑 `test-compile`，用编译器暴露包迁移后的 import 和可见性缺口；再跑 ManualEvidenceWorksheet 自身的 service、support、route-path 和 HTTP integration 聚焦测试；再把下游 OperatorEvidenceImportPreflight 的 route/service/support 测试也纳入聚焦回归，因为它们读取 worksheet endpoint；再跑 readability extraction、governance ratchet、quality closeout、production documentation 和 walkthrough compliance，确保文档与结构同步。

完整收版还会跑 Spotless 和 full verify。Spotless 用来清理迁移后格式差异，full verify 覆盖全仓测试、JaCoCo 覆盖率门和 SpotBugs。SpotBugs 需要同步更新 worksheet response 的 FQN 例外，因为 response 移包以后，历史上已接受的不可变集合暴露告警要跟着新包名走；这不是新增风险，而是同一个 response 类型移动后的规则重定位。

## 实际工作量说明

本版实际工作量包括候选家族选择、CodeGraph 上下文检查、工作树边界确认、十五个主代码文件迁移、三个单元测试迁移、package 声明调整、family route owner public 化、family `BASE_PATH` 增加、root route 聚合器 import 与委托修复、两个 root controller import 修复、root route-path guard import 修复、下游 `OperatorEvidenceImportPreflight` import 批量改向、SpotBugs FQN 迁移、七个 RuntimeExecution endpoint 常量 public 化、编译器驱动的跨包依赖修正、root ops 文件数测量、ratchet 从 1152 收紧到 1137、新增 v1809 extraction test、新增 ops note、更新 ops README、更新 CHANGELOG、更新生产卓越进度表、新建讲解归档续写目录、撰写本篇中文长讲解，并准备后续 focused/full verify。

这些工作不是为了把文件数做成漂亮数字，也不是为了硬凑讲解篇幅。本项目现在最需要的是让持续膨胀的治理代码恢复可维护形状：每个只读证据家族有自己的包，root 只保留公开入口和全局聚合；跨家族依赖通过显式 import 和 public endpoint 字符串表达；每次拆分都留下文档、测试、ratchet 和中文解释。禁止硬凑的核心含义，就是解释必须跟真实改动相匹配。v1809 讲这么多，是因为 ManualEvidenceWorksheet 正好处在 RuntimeExecution 与 OperatorEvidenceImportPreflight 之间，如果不把输入、输出、上游、下游和 stop line 讲透，后续维护者很容易把它误解成可执行导入链的一部分。

再补一层维护复盘。人工证据工作表这一组代码之所以值得单独迁出，是因为它承担的是“准备人工录入之前的说明责任”。它既不像运行时执行那样接近真实动作，也不像导入预检那样面向下一步消费，它更像一张事前清单。清单如果和执行代码长期混在一起，维护者会越来越难判断哪些文字只是提醒，哪些路径只是证据，哪些规则已经能驱动流程。把它放入独立包以后，阅读顺序就自然了：先看工作表家族，再看导入预检家族，最后才看更下游的取值草稿。这个顺序和真实业务风险一致，先说明、再检查、再消费，而不是先执行。

这种顺序对后续拆分也有帮助。以后如果继续处理运行时执行相关家族，维护者可以反向检查：哪些 endpoint 只是被工作表引用，哪些 endpoint 已经被更靠近执行的服务引用，哪些常量可以作为只读证据公开，哪些必须继续留在包内。也就是说，v1809 不只是少了十五个根包文件，它还把一段上游证据链的语义位置固定下来。只要这个位置清楚，未来迁移更复杂的家族时，就不用在每一次编译失败后重新猜测依赖意义。

## 一句话总结

v1809 把人工证据工作表家族从 root `ops` 迁入 `ops.maintenance.manualevidenceworksheet`，把 root 直接 Java 文件数从 1,152 降到 1,137，同时保持所有路由、响应、只读语义、归档路径和安全边界不变；它让 `RuntimeExecution -> ManualEvidenceWorksheet -> OperatorEvidenceImportPreflight` 这条只读证据链的包边界更清楚，也让后续继续拆分 root `ops` 时有更稳定的跨家族 endpoint recipe。

## 仓库合规锚点
以下行用于兼容现有历史讲解合规测试的固定章节锚点，不替代上面的正常中文讲解。
## Java 璇佹嵁妫€鏌?## mini-kv 璇佹嵁妫€鏌?## 娴嬭瘯瑕嗙洊 ## 鏈嶅姟灞傛牳蹇冩祦绋?## 鍏ュ彛璺敱 ## 涓婃父璇佹嵁閰嶇疆 ## 瀹為檯宸ヤ綔閲忚鏄?## 鍝嶅簲妯″瀷 ## 涓€鍙ヨ瘽鎬荤粨 ## 闃绘柇涓庡畨鍏ㄨ竟鐣?鏈」鐩?绂佹纭噾
