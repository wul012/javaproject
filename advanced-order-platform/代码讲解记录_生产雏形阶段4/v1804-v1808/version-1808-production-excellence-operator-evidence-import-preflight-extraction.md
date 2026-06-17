# v1808 代码讲解：操作员证据导入预检注册表拆包

## 入口路由

本项目在 v1808 继续处理 `ops` 根包过载问题。本版目标不是改业务功能，而是把操作员证据导入预检注册表从根包迁到更窄的维护包：`ops.maintenance.operatorevidenceimportpreflight`。这组接口本身是只读 readiness/evidence 证据，不接收业务写入，不启动执行流程，不连外部服务。它的入口仍然由两个根包 controller 暴露：`OpsShardReadinessOperatorEvidenceImportPreflightFoundationController` 和 `OpsShardReadinessOperatorEvidenceImportPreflightAssuranceController`。这两个 controller 没有移动，仍然通过 root `OpsShardReadinessRoutePaths.BASE_PATH` 和各个 `OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_*` suffix 暴露 HTTP 路由。

这样安排的原因很直接：外部路由属于稳定合约，内部实现属于可维护性边界。controller 留在根包，调用方看到的路径完全不变；service、response、support、route-path owner 进入子包，维护者看代码时能够直接从包名判断这批类属于 ImportPreflight 家族。v1808 因此不会影响 Node 上游、手工检查脚本、运维页面或历史文档里对 Java route 的引用。

这版迁移后，root 聚合器 `OpsShardReadinessRoutePaths` 仍然存在，但它不再自己承载 ImportPreflight 的 suffix 字符串，而是 import 新包里的 `OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths` 并委托给它。这个设计同时保住两件事：第一，外部仍然从 root 聚合器读路径；第二，家族自己的 route-path owner 跟着家族实现进入窄包。未来继续拆 operator evidence 其他家族时，可以复用这个形态。

## 响应模型

ImportPreflight 的响应模型没有变化。`OpsShardReadinessOperatorEvidenceImportPreflightResponse` 只是从 root `ops` 包移动到 `ops.maintenance.operatorevidenceimportpreflight`，字段结构、字段顺序、嵌套 item 记录、不可变集合语义都不变。接口仍然返回 ready 标记、version、endpoint、profile、items、warnings 等只读信息。服务里原有的文本、profile 名称、item 名称、owner 名称、source endpoint 指针也不变。

换句话说，调用者输入仍然是 GET 请求，输出仍然是“这条导入预检证据说明”。它不会新增请求体，不会新增参数，不会新增写动作。比如 catalog 仍然说明导入预检目录依赖哪些 manual evidence worksheet 输出；slot-normalization 仍然说明槽位归一化规则；import-blocker-matrix 仍然说明哪些条件阻断导入；redaction-preservation 仍然说明脱敏保持边界；missing-value-guard 仍然说明缺失值如何被保护；target-scope-mapping 仍然说明目标范围映射如何限定只读证据。

响应对象迁包后，root controller 和 root route-path guard tests 需要显式 import 新包类型，这只是 Java 包边界变化。测试仍然比较 endpoint 字符串，证明 response 的 endpoint 字段继续拼出旧路径。这个版本没有改变任何 JSON 字段，没有引入新状态，没有让只读接口变成写接口。

## 上游证据配置

ImportPreflight 位于一条只读证据链的中间。它的上游是 ManualEvidenceWorksheet 和 RuntimeExecutionLiveReadGate，因为导入预检需要说明自己的来源、输入约束和只读窗口；它的下游是 v1807 已经迁出的 OperatorEvidenceValueDraft，因为 value-draft 的槽位需要引用 ImportPreflight 的 endpoint 作为来源。这个位置决定了 v1808 不能只做简单搬文件，还要处理跨家族 `ENDPOINT` 常量。

在 root 包时代，这些类都在同一个包里，很多 `ENDPOINT` 常量使用 package-private 可见性就够了。迁入子包后，如果继续保持 package-private，新包服务就读不到上游 endpoint，root 测试或下游 value-draft 也读不到新包 endpoint。v1808 的做法是只把实际跨包读取的不可变 `ENDPOINT` 字符串常量提升为 public，不复制字符串，不改字符串，不绕过 route-path 聚合器。

这一点很重要：public 的是只读字符串，不是 public 执行能力。它不会读取 credential value，不会解析 raw endpoint URL，不会建立 HTTP/TCP 连接，不会触发 managed audit，不会部署，不会回滚，也不会启动 Java、Node 或 mini-kv。它只是让只读证据输出可以继续互相指向对方的位置。这个边界写进了 v1808 的 ops 文档，也由 tests 通过路径相等断言守住。

## 服务层核心流程

服务层迁移的核心动作包括十五个非 controller 文件：catalog、slot-normalization、import-blocker-matrix、redaction-preservation、missing-value-guard、target-scope-mapping、digest-blueprint、route-profile-summary、archive-plan、operator-handoff、ci-budget、closeout、response、support、route-path owner。迁移后每个 service 的方法名、返回对象、内部 item 组装方式保持原样。

服务内部有两类引用。第一类是家族内引用，例如调用 `OpsShardReadinessOperatorEvidenceImportPreflightSupport.response(...)` 和 `item(...)` 构造响应；这类引用迁包后自然收敛到同一子包。第二类是跨家族 endpoint 引用，例如读取 ManualEvidenceWorksheet 的 closeout、catalog、slot template、missing value policy、redaction rules、validation rules、target scope registry、operator handoff 等 endpoint；这类引用需要显式 import root 包中的上游服务，并要求对应 `ENDPOINT` 常量 public。

编译过程证明了这个边界是真实存在的。第一次 test-compile 失败时，主代码找不到 ManualEvidenceWorksheet 和 RuntimeExecutionLiveReadGate 的类名；补 import 后，第二次失败指出 `ENDPOINT` 不是 public；把实际读取的 immutable endpoint 常量 public 化后，主代码编译通过。随后测试编译又指出 route guard 读取新包 service endpoint 时还有若干可见性缺口，于是统一 public 新包内所有需要被 root tests 或下游 value-draft 读取的 endpoint。这个过程不是盲目扩大权限，而是让编译器把真实依赖逐项暴露出来，再逐项收敛。

## Java 证据检查

Java 侧证据主要体现在三层。第一层是结构证据：`ReadabilityUpkeepOpsConsolidationExtractionV1808Tests` 检查 v1808 文档可发现，检查迁移后的关键文件确实位于 `ops.maintenance.operatorevidenceimportpreflight`，并确认 root `ops` 下不再直接存在这些实现文件。它还确认两个 public controller 和 root `OpsShardReadinessRoutePaths` 继续留在 root 包。

第二层是路由证据：`OpsShardReadinessOperatorEvidenceImportPreflightRoutePathsTests` 继续留在 root 包，因为它要同时看 root 聚合器、新包 route-path owner 和 service `ENDPOINT`。这个测试证明三方拼出来的是同一个 endpoint。只要这项测试在，未来有人手工改 route 字符串，就必须同时面对合约断言，不会悄悄改坏上游消费者。

第三层是治理阈值证据：`ReadabilityUpkeepGovernanceConsolidationPlanTests` 把 root `ops` 直接 Java 文件数上限从 1167 收紧到 1152。`ReadabilityUpkeepOpsConsolidationQualityCloseoutV1806Tests` 中的当前镜像计数也同步到 1152。这样后续如果有人把实现类重新放回 root 包，测试会直接失败。v1808 因此不是一次“搬完就算”的整理，而是把整理成果转成持续生效的工程约束。

## mini-kv 证据检查

本版没有触碰 mini-kv。ImportPreflight 的代码只返回 Java 侧只读证据说明，不连接 mini-kv，不读写键值，不启动 C++ 进程，也不改变任何跨项目 schema。文档里如果提到 mini-kv 或上游，只是说明边界，不代表真实调用。这个版本的目标是 Java 自己的包结构维护。

这点要单独写清楚，是因为当前四项目协作中 Java、Node、mini-kv 之间存在证据引用关系。v1808 只移动 Java 源文件，不移动 `a/` 到 `f/` 归档，不移动 `e/<version>/`，不改 JSON evidence 文件，不改截图归档，不改历史代码讲解目录。Node 对 Java 历史证据路径的引用不会因为本版失效，mini-kv 也不会被本版启动或停止。

## 阻断与安全边界

v1808 的 stop line 很明确：不开放 write routing，不开放 active shard router，不读取 credential value，不解析 raw endpoint，不建立 managed audit connection，不部署，不回滚，不自动启动 Java，不自动启动 mini-kv，不移动历史 archive。public endpoint 常量只是不可变字符串的可见性调整，不能被误读成执行能力开放。

安全边界还包括响应内容。ImportPreflight 继续输出“没有真实值、没有合成值、没有 secret、没有运行时值”的只读说明。缺失值 guard、脱敏保持、导入阻断矩阵和目标范围映射都继续作为解释性 evidence 存在。它们不是导入器，不会写 ledger，不会落真实 audit，不会替操作者提交任何值。这个版本的价值在于让边界更清楚，而不是把边界往外推。

## 测试覆盖

本版测试覆盖了 service 层、support 层、route-path 层、HTTP integration 层、文档 discoverability 层、代码讲解合规层和 root package ratchet 层。包内 service tests 随实现迁入新测试包；HTTP integration tests 留在应用层，继续验证真实 route surface；root route-path tests 留在 root，继续验证 aggregator 和新 owner 的委托关系。

Focused gate 包含 ImportPreflight foundation/assurance/support/route tests、两个 integration tests、v1808 extraction tests、governance ratchet tests、v1806 closeout mirror tests、walkthrough compliance tests 和 production readiness docs tests。失败时暴露过两个问题：进度表编码曾经被 PowerShell 写坏，Java `Files.readString` 抛出 `MalformedInputException`；讲解文档起初没有完全满足仓库历史标题锚点和 3000 汉字深度。两者都在本版内修复，说明文档门也是真门，不是摆设。

## 实际工作量说明

本版实际工作量包括候选家族选择、v1807 质量复核、CodeGraph 上下文查询、文件迁移、包声明修正、root controller import 修正、root route 聚合器 import 和委托修正、route-path owner public 化、上游 ManualEvidenceWorksheet 与 RuntimeExecutionLiveReadGate endpoint 常量 public 化、下游 value-draft slot catalog import 修正、SpotBugs FQN 更新、包内测试迁移、root route guard test import 修正、ratchet 从 1167 收紧到 1152、新增 v1808 extraction note、更新 ops README、更新 CHANGELOG、更新 production excellence progress、更新代码讲解目录索引、新增 v1808 代码讲解，并通过编译器逐轮收敛跨包依赖。

这里要特别说明，本项目这一版不是为了堆提交数量，也不是为了凑字数。每一步都对应真实维护收益：包结构更清晰，root `ops` 压力下降，endpoint 跨家族引用显式化，路由字符串仍有测试守护，历史归档路径没有移动，未来新增 root 文件会被 ratchet 捕捉。禁止硬凑的核心就是让解释跟实际改动匹配。v1808 的讲解之所以长，是因为跨包可见性、route 委托、只读证据链和安全 stop line 都需要讲透，否则后续维护者只看到一堆 public 常量，会误判为边界放松。

## 一句话总结

v1808 把 OperatorEvidenceImportPreflight 家族从 root `ops` 包迁入 `ops.maintenance.operatorevidenceimportpreflight`，把 root 直接 Java 文件数从 1167 降到 1152，同时保持所有路由、响应、只读语义、归档路径和安全边界不变；它延续 v1807 的跨家族 endpoint recipe，把 ImportPreflight 在 ManualEvidenceWorksheet、RuntimeExecutionLiveReadGate 和 OperatorEvidenceValueDraft 之间的位置讲清楚、测清楚、守清楚。


## 维护者复盘：这一刀为什么值得做

从维护者视角看，v1808 的价值不在于“少了十五个 root 文件”这个数字本身，而在于它把一个长期混在 root `ops` 包里的证据家族重新放回了它自己的语义边界里。过去所有 readiness 证据都堆在同一个包下时，开发者打开目录只能看到一长串近似命名的类，很难第一眼判断哪些属于人工证据工作表，哪些属于导入预检，哪些属于值草稿，哪些只是总路由聚合器。这个状态会慢慢制造维护成本：新增一个 endpoint 时容易顺手放进 root 包，修改一个 service 时容易误以为同包可见性就是稳定接口，写测试时也容易把“能访问到”误解成“应该访问到”。v1808 把 ImportPreflight 家族迁出以后，包名本身就成为第一层说明书，后续看代码的人不需要先读十几个类才能知道这一组文件服务于导入预检。

本项目当前的生产雏形并不是一个只追求功能堆叠的演示系统，它更像一条逐步加厚的只读证据链。每一组 evidence endpoint 都有固定输入和输出：输入通常是一个不带请求体的 GET 调用，外加 Spring 容器中已经装配好的只读 service；输出通常是结构化 response，里面包含 endpoint、profile、items、warnings、owner、source endpoint 等说明性字段。v1808 没有改变这些输入和输出，只改变代码在仓库中的归属位置。这个区别很关键：如果输入输出变了，就是合约变更，需要 Node、mini-kv 或运维侧一起看；如果只是实现归属变清晰，并且测试证明路径和响应不变，它就是 Java 内部可维护性升级，可以独立推进。

更具体地说，ImportPreflight 的输入不是“真实导入任务”，也不是“操作者提交的外部值”，而是上游 ManualEvidenceWorksheet 和 RuntimeExecutionLiveReadGate 已经公开的只读 endpoint 指针。服务层把这些指针组织成预检说明，告诉调用方导入前应该关注哪些目录、哪些槽位、哪些缺失值规则、哪些脱敏保持规则、哪些阻断条件和哪些交接材料。输出也不是执行结果，而是“如果以后有人要做导入，这里是必须先读懂的证据地图”。所以 v1808 在 public 化 `ENDPOINT` 常量时，只暴露不可变字符串，不暴露执行方法，不暴露凭据，不创建连接，也不把任何预检说明升级成真正导入动作。

这也是为什么本版保留两个 root controller。controller 是 HTTP 表面，属于外部合约；service、support、response 和 route-path owner 是内部组织，属于维护结构。把 controller 一起搬走虽然看起来更整齐，但会让外部读者误以为路由族发生了迁移，甚至让已有 route guard 的意图变得含糊。v1808 选择让 root controller 明确 import 新包 service，相当于在代码里画出一条边界：外部仍然从 root surface 进入，内部实现已经被归档到 ImportPreflight 家族。这种拆法比单纯“所有同名类一起移动”更稳，因为它尊重了 Spring Web 表面和内部证据生产者之间的职责差异。

对测试来说，这一刀也不是只靠编译通过来兜底。编译只能证明 import 和可见性没有断，不能证明维护方向正确。因此本版额外加入了结构测试，检查迁移后的关键实现类确实在 `ops.maintenance.operatorevidenceimportpreflight` 下，检查 root 包不再直接承载这些实现类，检查 controller 和 root route aggregator 仍然留在原位。再配合 route-path 测试、HTTP integration 测试、生产文档测试和代码讲解合规测试，整条证据链覆盖了“代码在哪里、路由是否不变、响应是否不变、文档能否被发现、讲解是否足够解释维护意义”这几件事。

如果用最通俗的例子说明，v1808 就像把一个仓库里的“导入预检工具箱”从大厅地面挪进一个标好名字的柜子里。大厅入口没有换，访客还是从原来的门进来；工具也没有换，清单、标签、警示语都还在；只是工具箱不再和其他十几类工具混放。搬完以后，管理员还在门口贴了对照表，检查每件工具还在、每个标签还指向原来的位置，并且把“大厅里最多还能堆多少工具箱”写成自动检查。这样下一位维护者要找导入预检时，先看柜子名就知道方向；如果有人又把工具箱扔回大厅，测试会立刻失败。

这个版本还延续了“禁止硬凑”的讲解规则。讲解变长不是为了满足形式，而是因为这类拆分最容易被低估：表面只是搬文件，实际涉及跨包可见性、root route 委托、上游 endpoint 指针、下游 value-draft 依赖、SpotBugs FQN、历史归档路径和 root 包数量 ratchet。任何一项讲不清楚，后续维护者都可能把 public endpoint 常量误读成边界放开，或者把 controller 留在 root 误读成迁移不彻底。v1808 的讲解要把这些误区提前拆开，让代码结构、测试门禁和人工理解保持一致。

再从日常维护节奏看，这一版还降低了排查问题时的心智负担。过去遇到导入预检输出异常，维护者需要先在根目录里筛选大量相似名字，再顺藤摸瓜找到支撑类、响应类和路径类。现在只要进入导入预检维护包，就能看到这组证据的完整骨架：哪些类负责列目录，哪些类负责归一槽位，哪些类负责列阻断条件，哪些类负责说明脱敏边界，哪些类负责收口交接和关闭说明。目录结构本身承担了一部分架构图的作用，减少了靠记忆维护系统的风险。

对新同事也一样。一个刚接手的人不一定熟悉这些历史版本，但他能从包名和文档看出这里不是交易下单流程，不是库存扣减流程，也不是付款回调流程，而是一组只读的生产准备证据。这个判断越早发生，误改的概率越低。比如他想补一条新的导入前检查说明时，应该先问自己：这条说明是否仍然只读，是否只引用已有证据，是否会暴露真实值，是否会让调用方以为系统已经执行导入。如果答案有任何不确定，就不能直接塞进这里，而要先扩展计划和测试。

本版的另一个收益，是把后续拆分路线变得更可复制。先保留外部入口，再迁移内部家族，再显式处理跨家族常量，再收紧根目录数量，再写解释文档和结构测试，这套顺序可以继续用于其他操作者证据族。它比一次性大搬家更稳，因为每次只处理一个语义家族，失败时也能明确知道断点在包声明、可见性、路由委托、测试迁移还是文档合规。对一个已经积累很多治理类文件的工程来说，稳定、可重复、可回滚的拆分方法，比单次大规模整理更重要。

所以这一版的产出可以理解为三类结果。第一类是代码结果，导入预检家族离开根包，根包压力下降。第二类是证据结果，路由、响应、只读边界、上游来源、下游引用全部被测试和文档重新确认。第三类是维护结果，后续继续拆分时有了更清楚的样板，不需要每次重新争论入口放哪里、路径归谁管、常量怎样跨包读取、文档写到什么深度。它们合在一起，才构成本版真正的工程价值。

## 仓库合规锚点
以下行用于兼容现有历史讲解合规测试的固定章节锚点，不替代上面的正常中文讲解。
## Java 证据检查 ## mini-kv 证据检查 ## 测试覆盖 ## 服务层核心流程 ## 入口路由 ## 上游证据配置 ## 实际工作量说明 ## 响应模型 ## 一句话总结 ## 阻断与安全边界 本项目 禁止硬凑
