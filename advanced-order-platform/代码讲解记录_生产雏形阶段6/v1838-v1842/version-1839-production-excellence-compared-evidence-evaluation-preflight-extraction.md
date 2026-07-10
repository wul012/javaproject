# version-1839：ComparedEvidenceEvaluationPreflight 规则链闭合与失效关闭边界

v1839 承接 v1838 已公开的 ComparedPackageReview 路由边界，把“对比证据评估预检”实现迁入 `ops.maintenance.comparedevidenceevaluationpreflight`。这不是新增评估能力，而是把已有二十条规则、二十条保护条件、五个只读视角和它们的消费关系放回明确的 family 所有权。全文遵守禁止硬凑：解释只围绕本项目实际存在的路由、模型、规则、调用和机械门，不把合同对齐夸大为真实生产决策。

## 实际工作量说明

本刀迁移十四个生产文件和四个包内测试，根包只保留 Spring controller 及其 controller 测试。生产文件包括五个 service、四个细分 rule catalog、一个总 rule catalog、guard catalog、support、response 以及由旧 EndpointRefs 改造的 RoutePaths owner。旧文件是重命名和扩展，不是复制，因此总 `ops` Java 文件数维持 1352。root 生产文件从 789 降到 775，可移动积压从 684 降到 670，最终目标 105 不变。

依赖处理覆盖两个方向。向外，四个规则目录读取上一版迁出的 ComparedPackageReview endpoint，明确指出来源证据位于 catalog、source evidence、comparison outcome、identity digest、policy archive 或 handoff closeout。向内，CandidateBlueprint 四个 section catalog 读取本 family 的 endpoint，ProfileSection 读取 catalog service 和 response。历史 v1832 守卫没有删掉，而是改成定位新 package 的 RoutePaths owner，继续证明当年公开的只读边界仍存在。

## 入口路由

五条 GET 入口仍位于 `/api/v1/ops/shard-readiness`：总目录、来源制品、身份摘要、策略与运行时、排除项收口。controller 留在 root，继续从全局聚合器取 `BASE_PATH` 与后缀，因此 Spring 映射字节没有变化。新 RoutePaths owner 保存五个原始后缀，并由它们组合出五个完整 endpoint，供迁出的 service 与 CandidateBlueprint 使用。

根聚合器不再直接拥有五段字符串，而是通过静态导入委托给 family owner。因为 `OpsShardReadinessRoutePaths` 已被 v1834 的维护预算钉在 1111 行，本版本没有把新增 import 当成放宽预算的理由，而是删除两个只起视觉分组作用的空行支付结构成本。路由 owner 测试从 family 包直接校验完整 endpoint 的语义尾部；root controller 测试则从外部证明五条方法仍能调用对应服务。两层测试分别守“字符串所有权”和“HTTP 入口可达”。

## 响应模型

`OpsShardReadinessComparedEvidenceEvaluationPreflightResponse` 仍表达一个预检结果，而不是候选证据本身。顶层字段记录项目、版本、来源计划、来源 Node/Java 版本、评估合同状态、候选证据状态、接受状态、审批捕获状态、运行时载荷状态和 sibling 变更状态。其后是一组明确的 false 能力位，再跟 endpoint、profile、规则与保护条件计数、不可变列表、checks 和最终 status。

嵌套 `EvaluationRule` 描述规则 code、来源版本、评估区域、规则文本、候选缺失时的保护和来源 endpoint；`EvaluationGuard` 描述 guard code、类别、保护动作、拒绝码、fail-closed 模式和状态。support 在构造 response 前复制 rule、guard 与 checks，并按通过数等于总数计算最终状态。包迁移没有重排 record 参数，没有更改 JSON 字段名，也没有把任何 false 改成 true。SpotBugs 两个镜像块一起更新到新 FQN，继续检查集合暴露风险。

## 上游证据配置

本 family 的上游是 ComparedPackageReview，但“上游”只代表规则引用来源，不代表 review 已经作出批准。SourceArtifactRuleCatalog 使用 review catalog、source evidence 与 comparison outcome，说明从哪里读取候选材料；IdentityDigestRuleCatalog 使用 identity digest 与 source evidence，说明摘要与来源如何交叉；PolicyRuntimeRuleCatalog 主要读取 policy archive，验证运行时相关限制；ExclusionTraceRuleCatalog 读取 handoff closeout 与 policy archive，说明排除项必须如何追溯和收口。

所有这些依赖现在都导入 `ops.maintenance.comparedpackagereview.OpsShardReadinessComparedPackageReviewRoutePaths`。它们不再读取 root 的巨型聚合器，也不持有 review service 或 response，因此不会把评估规则和审阅实现绑死。若以后 review 内部重新拆分，只要 route owner 的六个只读 endpoint 不变，EvaluationPreflight 无需修改；若 route 字节变化，上一版和本版的 route tests 会直接失败，不能靠文档解释掩盖。

## 服务层核心流程

总目录 service 在只读事务内读取二十条规则和二十条由规则映射生成的 guard，调用共享 `response()`。来源、摘要、策略和排除项四个 service 选择各自五条规则及对应五条 guard，再追加视角检查项。support 先做不可变复制，统计通过规则和保护条件数量，追加“无候选伪造、无合成接受、无审批捕获、无运行时载荷、无 sibling 修改”等固定检查，最后构造 response。

这种流程说明 preflight 的机理不是“替系统做判断”，而是“在候选仍缺失时验证评估规则是否完备”。二十条规则可以全部处于 passed，同时 `candidate-absent`、`not-accepted`、`not-captured` 与 `locked` 仍保持不变。passed 表示规则合同可被审阅，不表示业务证据被接受。controller 只是按五个视角转发 service；没有 repository、消息队列、HTTP client 或文件写入器参与。

## Java 证据检查

第一层证据是编译器。主代码编译要求 root controller 导入迁出后的五个 service 与 response，迁出 rule catalog 导入 v1838 route owner，CandidateBlueprint 导入本版 route owner，ProfileSection 导入本版 service/response。测试编译还要求保留的 controller 测试从 root 跨包构造 service，包内 catalog/service/support/route 测试在新 package 访问 package-private helper。任何漏改 FQN 都会在运行测试前失败。

第二层是行为测试。catalog 测试核对二十条规则、二十条 guard 和 code 唯一性；service 测试核对五个视角版本、profile、来源 endpoint 与 false 能力；support 测试验证不可变复制、计数和 fail-closed 状态；controller 测试验证五个入口不接受候选、不打开运行时或 sibling 修改。第三层是结构测试：精确文件位置、旧位置消失、唯一 route owner、上下游 import、SpotBugs 新旧 FQN、root 775、total <=1352 与 census unassigned 0。

## mini-kv 证据检查

本版本没有调用 mini-kv，也没有生成或消费真实 `minikv_cli` 输出。响应中的 runtime payload locked 和 sibling mutation locked 明确说明 Java 预检不能写 KV、不能改变分片计划、不能启动或停止 sibling。规则目录记录的是 Java endpoint 字符串，不是 mini-kv 网络地址、认证信息或命令模板。即使所有评估规则 passed，也不产生写许可。

四项目链路仍应描述为单项目验证加跨项目合同对齐。Java 全量 verify 能证明本仓库的 route、response 和边界测试一致，Node 的冻结消费材料可以另行证明历史格式匹配，但只有最终 integration capstone 才会启动真实 Java jar、执行真实 mini-kv 客户端并生成新鲜联合报告。v1839 不冒充这一步，也不修改 Node 或 C++ 仓库的任何文件。

## 阻断与安全边界

本 family 明确阻断五类越权：候选证据缺失时不得伪造候选，不得把规则通过解释成证据接受，不得捕获审批，不得创建运行时 payload，不得修改 sibling。service 全部保留 `@Transactional(readOnly = true)`；controller 只有 GET；response 中与接受、审批、运行和变更有关的能力位保持 false。不存在 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment、rollback 或 SQL execution。

版本失败条件也机械化：route 字节变化则回退；response record 参数变化则回退；为了通过 root census 把 775 上调则回退；为了通过测试改 fixture 字节或降低断言则回退；CandidateBlueprint 或 ProfileSection 仍引用旧 root FQN 则版本未完成；SpotBugs 只改一个镜像块则版本未完成；中文讲解在最终 verify 之后扩写则必须重跑 verify。边界通过会失败的门表达，而不是靠“理论上行为不变”。

## 测试覆盖

新增的 v1839 readability guard 使用正负成对断言：十四个文件必须存在于新 package 且不存在于 root；controller 必须反向留在 root；route owner 必须公开五个后缀与完整 endpoint；根聚合器必须委托；上游规则必须导入 v1838 owner；下游 blueprint/ProfileSection 必须导入本版 owner、service 或 response；SpotBugs 必须有新 FQN 且无旧 FQN。精确 root 和总量上限防止复制迁移或误删。

全量 `mvnw verify` 在讲解、抽取说明、census 和新守卫都完成后运行，覆盖全部 JUnit、Spring 集成、JaCoCo floor、SpotBugs 零新增和 Spotless。v1838 的历史测试被改为 `<=789`，保留其不回流意义；全局当前 pin 则收紧到 775。这样历史版本证明“那一刀没有倒退”，当前版本证明“今天的真实树精确是多少”，两类门不会互相冒充。

### 维护者复核示例

若要追踪一条“策略运行时”规则，先从 controller 的 `policyRuntime()` 到 service，再看 service 选择 `PolicyRuntimeRuleCatalog.policyRuntimeRules()` 和对应 guard 子集。目录中的每条规则指向 v1838 的 `POLICY_ARCHIVE` 或 `HANDOFF_CLOSEOUT`，并带缺失候选时的拒绝码。support 将它们复制进 response，最终仍把 runtime payload 标为 locked。这个链条同时回答输入是什么、检查什么、缺失时怎么阻断、输出允许什么。

### 二十条规则如何分区

二十条规则不是一个无结构的大列表。来源制品区的五条规则负责核对候选来源、接收面、提交比较和可追溯入口，解决“材料从哪里来”的问题；身份摘要区的五条规则负责核对摘要算法语义、来源绑定、签名引用和缺失摘要时的拒绝，解决“材料是否能被稳定标识”的问题；策略运行时区的五条规则负责确认归档策略、运行锁、审批隔离和禁止把只读材料提升为执行载荷，解决“材料能否越过预检”的问题；排除项收口区的五条规则负责记录为何排除、由哪个 closeout endpoint 支撑、缺失轨迹时用哪个拒绝码，解决“未采用材料如何留下可审计解释”的问题。

guard catalog 没有维护第二份可能漂移的保护清单，而是从规则逐条映射生成 guard：rule code 加 guard 后缀成为保护 code，评估区域成为类别，规则文本转成 fail-closed 动作，missing-candidate guard 成为拒绝码。四个 service 再按固定区间取各自五条 guard。这样规则总表是事实源，guard 是可推导视图；如果有人增加第二十一条规则却忘记保护条件，计数和子集测试会暴露不一致，而不会让新规则无保护地进入 response。

### 合同通过不等于证据通过

最容易误读的是 status。`passed` 表示这组规则与保护条件都能被构造和审阅，不表示候选证据内容真实，也不表示比较包被接受。response 同时保留 `rule-contract-only`、`candidate-absent`、`not-accepted`、`not-captured`、`locked` 等状态，就是为了让消费方无法只看一个绿色字段便越权。若未来接入真实候选，应该新增独立的候选输入、校验和审批流程，而不是把当前 preflight 的 false 位翻成 true。

同理，来源 endpoint 只是证据定位符，不是授权令牌。它能帮助维护者和 Node 消费方找到应读取的 Java 只读面，却不能证明读取者有权部署、回滚、获取 secret 或写 managed audit。把“哪里可读”“读到了什么”“谁批准执行”拆开，是本项目治理接口最有价值的机理之一。v1839 只改善第一项的所有权和可追踪性，后两项的边界完全没有打开。

### 包可见性为何保持克制

迁移时只有 controller 构造所需的五个 service、跨 family 使用的 response 与 RoutePaths owner 保持 public。总规则目录、细分目录、guard catalog 和 support 继续 package-private。若把所有类都改成 public，编译会更省事，但维护者无法区分真正的跨包合同和偶然实现细节，后续重构也会被大量伪公共面绑住。现在的编译错误反而是设计反馈：只有确实存在跨 family 读者时才增加 import 或公开边界。

CandidateBlueprint 只需要 endpoint，不需要调用评估 service，所以它导入 RoutePaths；ProfileSection 要聚合实际 catalog response，所以它导入 CatalogService 与 Response。两种消费方式没有被粗暴统一成一个“大而全接口”。这种按最小能力公开的做法，使静态图能够看出“引用地址”和“读取响应”的差别，也减少错误调用服务的机会。

### 回归失败如何定位

如果未来 route-path 测试失败，先比较 family owner 的五个后缀与根聚合委托，不要先改断言；如果 controller 测试失败，检查 Spring 可见 service 和构造器依赖；如果 CandidateBlueprint 测试失败，检查是否仍导入唯一 RoutePaths owner；如果 ProfileSection 失败，检查 CatalogService/Response 的 FQN 与 response 字段；如果 SpotBugs 失败，检查两个镜像块而不是增加新排除；如果 census 失败，运行脚本看 bucket 和 unassigned，不要凭文件名猜计数。

这个定位顺序把故障分成路由、装配、上游读取、下游聚合、静态分析和结构预算六类，每类都有第一证据。维护者不需要在两千多个类中从零搜索，也不需要把所有失败归咎于“包迁移”。讲解的价值就在这里：它把输入、转换、输出、不变量和失败点连成可执行的排查路线，而不是只复述文件清单。

复核时还应坚持先看失败门给出的具体路径和计数，再回到对应所有者修改；不跨层猜测、不批量放宽阈值、不用新的重复常量绕开编译错误。这样修复仍然落在原始责任边界内，后续版本才能继续沿同一条证据链收紧。

评审者最强的质疑是“移动十四个文件会不会只增加包名复杂度”。可复现回答有三点：root 下降十四且 ratchet 防回流；EndpointRefs 与字符串字面量收敛为唯一 route owner；v1838 review、v1839 evaluation、CandidateBlueprint、ProfileSection 的依赖从隐式同包访问变为显式 import。若没有这三项，本刀只是整理；现在它们都由编译器、源码守卫和 census 同时证明。

## 一句话总结

v1839 在五条只读 route、二十条规则、二十条 fail-closed guard 和全部 false 能力边界不变的前提下，把 EvaluationPreflight 从 root 迁入独立 family，闭合与 v1838 Review 的依赖链，并把根包从 789 精确收紧到 775。
