# v1899 Marker Evidence Builders 代码讲解

## 入口路由

v1899 没有增加业务路由，也没有改变控制器。外部仍通过 release approval rehearsal 的只读
GET 入口取得一份演练报告，根 `OpsOverviewController` 把请求头整理成
`ReleaseApprovalRehearsalRequest`，然后调用公开 `OpsEvidenceService`。服务先读取订单平台自身
已经存在的只读证据，再交给 `ReleaseApprovalRehearsalResponseBuilder` 生成最终响应。这个入口
可以通俗地理解为“把当前系统状态装进一份不可执行的验收报告”，而不是“执行发布审批”。

本版处理的是这条长链中三个连续的内部步骤。第一步根据上一阶段的 fake transport dry-run
结果生成 endpoint handle preflight marker；第二步根据 preflight marker 生成 credential
resolver decision marker；第三步根据 decision marker 生成 disabled precheck marker。它们原先
分别藏在三个 590、738、726 行的包内 builder 中，类名也把整条业务句子编码进标识符。公开入口
虽然能工作，但维护者要在三份巨型文件和三个超长名字之间来回跳转，真正的“编排、静态数据、
规则判定、验证元数据”没有清楚分层。

新入口链不改变调用顺序，只把内部名字收短为 `EndpointPreflightBuilder`、
`DecisionMarkerBuilder` 和 `DisabledPrecheckBuilder`。`ReceiptChain` 仍按原顺序构造三个
marker，后一步仍显式接收前一步的强类型 record。换句话说，输入输出的方向完全没有改变：

```text
fake transport marker
  -> endpoint preflight marker
  -> resolver decision marker
  -> disabled precheck marker
  -> 后续 test-only shell 与全部 receipt
  -> ReleaseApprovalRehearsalResponse
```

三个新短名类仍是 package-private，不是 Spring bean，不对外形成新的 API。Controller、
`OpsEvidenceService`、公开 Request/Response、route path、事务注解和 HTTP 字节均保持原样。
内部重构因此不会要求 Node、mini-kv 或任何调用者同步改代码。

## 响应模型

最终 `ReleaseApprovalRehearsalResponse` 是一个很大的公开 record。它包含采样时间、演练版本、
只读与 executionAllowed 标志、请求上下文、CI 证据、运行边界、审批交接、sandbox connection
链、credential resolver 链、失败分类、verification hint、blockers 和后续动作。v1899 没有
修改这个 record 的组件、顺序、嵌套类型全限定名或 JSON 属性名。三种被重构 marker 仍使用原来
的公开嵌套 record，所以调用方反序列化模型完全不变。

`EndpointPreflightBuilder` 的输出说明 endpoint 和 credential 都只能以 handle 形式出现，
网络 allowlist、TLS、脱敏策略和人工窗口均已被审阅，但真实 URL、凭据值、连接、迁移和自动启动
仍禁止。`DecisionMarkerBuilder` 的输出把下一阶段决策固化为 policy-record-only，列出八个必需
决策字段和九个 no-go 条件。`DisabledPrecheckBuilder` 的输出进一步证明 resolver client 与
secret provider 仍未实现，只允许检查环境变量名称、opt-in gate、失败分类和 dry-run 响应形状。
这些响应事实一个都没有被删减。

本版新增 `MarkerEvidence`，它不是公开响应，也不代替上述领域 record。它只持有四组验证元数据：
warning 输入名、boundary 输入名、proof claims 和 Node verification actions。构造时对三个列表
执行 `List.copyOf`，因此后续调用者不能通过修改原始列表改变同一次构建所见的验证事实。
`warningInputName` 也必须非空。这个短 record 解决的是“验证元数据如何传递”，不尝试把三个
marker 的业务字段塞进一个通用 map。

为什么边界行没有也被泛化？因为三种 marker 的 boundary 值来自不同类型：preflight 读取
review 与 side-effect boundary，decision 读取 decision record，disabled precheck 读取
precheck record 与 side-effect boundary。把它们强行变成 `Map<String, Object>` 会丢失编译期
类型，并把错误推迟到运行时。因此共享对象只负责真正相同的元数据算法，领域字段仍由各 builder
显式映射。

为了冻结整个公开模型，`RehearsalResponseOracleTests` 在旧 v1898 实现上先故意使用全零摘要。
失败输出给出默认请求 SHA-256
`48dc64dd2385de0ad0b98f114be157c98b19012abcfde8384ff6e237248b8550`，完整 header 请求
SHA-256 为 `c64e2fac8194ab2f70ef5bbd603a9a92dd0ea1a9ae75459f386c7fa6373258cc`。
测试只排除每次调用必然变化的顶层 `sampledAt` 和嵌套 `serverTimestamp`，其余完整 JSON 均
按属性名排序后参与摘要。这样既避免时钟造成假失败，又能锁住所有业务字段和列表顺序。

## 上游证据配置

三步 marker 的输入均来自内存中的上一阶段强类型响应，而不是环境变量值、数据库或外部网络。
endpoint preflight 接收 fake transport dry-run packet marker，确认 request shape、response
shape、timeout、failure mapping 和 side-effect boundary 已经回显。decision marker 接收
endpoint preflight marker，确认 handle-only 审阅和所有禁用位。disabled precheck 再接收
decision marker，确认八个决策字段、九个 no-go 条件、审批关联、脱敏和 fallback rotation
计划均已回显。

这些 builder 会引用 `ReleaseApprovalContractConstants` 与
`ReleaseApprovalUpstreamContractConstants`。前者固定 Java 自己公开的 marker/schema
版本，后者固定已对齐的 Node v257 到 v263 profile、endpoint、state 和版本。v1899 没有修改
任何常量值，也没有把 Node 的 archive 路径移动或重命名。规则 owner 只读取这些常量并装配
原 record。

拆分后，每组静态领域数据和规则位于对应的 `*Rules` owner。比如
`EndpointPreflightRules` 拥有 required review items、forbidden operations、来源检查和
side-effect 判定；`DecisionMarkerRules` 拥有 required decision fields、no-go codes、
source echo、decision record 与各字段验证；`DisabledPrecheckRules` 拥有 env handle、
opt-in gate、failure taxonomy、dry-run shape 以及 readiness 计算。builder 只保留本 marker
的验证元数据和最终构造顺序。

这里的“配置”不是可热更新配置，而是发布证据合同。改一个字符串会改变 marker digest，也会
改变最终 rehearsal JSON SHA，因此必须通过合同版本演进，而不能在重构中顺手调整。完整 response
oracle 正是为了让这种边界机械化：只要上游版本、endpoint、warning、proof claim、action 或
顺序发生漂移，测试立即失败。

## 服务层核心流程

一次默认 rehearsal 调用先由 `OpsEvidenceService.evidence()` 采样只读事实，再进入
`ReleaseApprovalRehearsalResponseBuilder`。builder 创建 request context、CI hint、handoff
hint 和 execution boundaries，然后调用
`ReleaseApprovalRehearsalManagedAuditReceiptChainBuilder`。receipt chain 按时间与依赖顺序
生成 sandbox adapter、connection、endpoint、resolver 以及后续 approval receipt。

在本版范围内，三个短 builder 都遵守相同但不隐藏领域含义的流程。第一步检查来源 marker 是否
被接受；第二步由 rules 构造当前阶段的 source echo、领域 record 和 side-effect boundary；
第三步计算每个回显布尔位；第四步把这些布尔位合成下一 Node 版本是否 ready；第五步使用稳定
键名和值生成 marker digest；第六步按原构造参数顺序返回公开嵌套 record。

过去，四组验证元数据分别通过
`warningDigestWarningInputNames()`、`warningDigestBoundaryInputNames()`、
`proofClaims()` 和 `nodeVerificationActions()` 暴露。38 份 releaseapproval 文件都出现了
这组形状，三个当前 builder 还各自重复 warning line 的算法。v1899 没有一次性触碰全部 38
份存量，而是先在三个连续热点建立可验证范式：每个 builder 只返回一个 `MarkerEvidence`，
共同对象生成 warning input 和 warning line，中央 contribution catalog 直接消费该对象。

`ReleaseApprovalVerificationHintContributionCatalog` 因而不再为这三份 builder 传四个 method
reference，而是各传一个 immutable evidence。现存未迁移 builder 仍走原 overload，所以改动
边界有限，后续可以逐批迁移而不进行大爆炸式改写。`ReleaseApprovalVerificationWarningDigestLineCatalog`
继续按原顺序追加 warning 与 boundary lines，`ReleaseApprovalNoLedgerWriteProofEvaluator`
继续调用各领域安全谓词。共享的是稳定数据传递，不是授权逻辑。

拆分后的行数也说明职责边界确实落地：三个 builder 分别为 276、291、237 行，三个 rules
分别为 460、299、489 行，`MarkerEvidence` 仅 26 行。所有 owner 均低于 500 行，且文件名和
新增标识符不超过 40 字符。原 738、590、726 行单体永久删除。

## Java 证据检查

Java 侧首先检查来源对象，而不是相信某个 ready 布尔值。endpoint preflight 会确认 fake
transport marker 版本、schema、来源 profile、warning 为空以及 request/response/timeout/
failure mapping 等回显事实。decision rules 会再次检查 endpoint preflight 中的 handle-only、
allowlist、TLS、脱敏、operator window 和所有 side-effect 位。disabled precheck rules 会
确认 decision marker 的八个字段和九个 no-go 条件都存在，并验证任何真实 resolver 行为仍是
false。

每个 marker 的 digest 都由有序 `key=value` 行计算 SHA-256。输入包括 marker 版本、来源版本、
Node profile、模式、source echo、领域 record、side-effect boundary 和下一阶段 readiness。
这使“响应显示 ready”与“摘要实际覆盖了哪些事实”能够对应。v1899 没有改 digest 算法，也没有
改键名或顺序；完整响应 oracle 对最终 digest 一并冻结。

`MarkerEvidenceTests` 用可变 `ArrayList` 构造共享对象，随后修改和清空来源列表，断言对象仍
保留原 boundary、proof 与 action，证明防御性复制有效。测试还断言 warning input 与 warning
line 的 canonical 格式。`MarkerBuilderArchitectureTests` 为七个短 owner 设置逐文件行数帽，
要求三个 builder 必须声明 `MarkerEvidence EVIDENCE`、必须用它生成 warning lines，并禁止
四个旧 getter 重新出现。它还锁定三个旧单体与四个末端空壳 wrapper 永久缺席，防止把已经
删除的无行为层以长名字重新带回。

全局 `JavaMaintainabilityBudgetTests` 同时收紧。生产最大文件由 738 降到 658，超过 500 行
的文件由 32 降到 29。当前三个 monolith 路径被六个短 owner 和一个共享 record 的精确帽替代。
`JavaEleganceGateTests` 把生产名称指标从 `1044/19346/2603` 收紧到
`1037/19155/2589`，测试指标收紧到 `680/9755/3633`。exact baseline 删除二十二项且没有
新增长名，未来提交不能把旧名字或同等新债务加回来。生产 Java 和 ops 文件总数都没有上升，
因此这次拆分既买到了职责边界，也守住了总量预算。

## mini-kv 证据检查

mini-kv 在这条 rehearsal 中仍是“不参与 managed audit 写入”的上游边界。Java 响应保留
mini-kv non-participation receipt、版本提示和禁止把 mini-kv 提升为 managed-audit backend 的
证明。v1899 没有启动 mini-kv，没有执行 `minikv_cli`，没有读取或改写它的 WAL、snapshot、
slot table 或 archive。

endpoint preflight 的 forbidden operations 明确包含不得启动 Java 或 mini-kv，以及不得把
mini-kv 变为 managed audit storage backend。decision marker 的 no-go 条件继续包含
`AUTO_START_REQUIRED`、`MINI_KV_BACKEND_REQUIRED` 和 `PRODUCTION_WINDOW_REQUIRED`。
disabled precheck 继承同一组 no-go 条件。这些值不仅出现在响应列表，也参与 readiness 和
完整 JSON oracle，因此不能被重构静默丢失。

Java 对 mini-kv 的正确输入是已冻结的非参与合同，不是一个可连接地址；正确输出是
`javaOrMiniKvStartAllowed=false`、所有 auto-start 与 backend promotion 位为 false，以及
下一证据动作仍要求独立回显。`MarkerEvidence` 只复制这些动作的文本列表，不会执行动作。
所以本版改善 Java 内部可维护性，不要求 C++ 仓库对齐，也不改变四项目依赖顺序。

如果未来真的开放 mini-kv 参与能力，必须先形成新的上游合同、版本、fixture 和联合测试，再
修改 Java 响应与摘要。不能把本次 package-private 重构解释为运行权限放开。当前机械证据仍是
单项目完整响应一致性与跨项目合同对齐，而不是声称执行了真实跨进程写入。

## 阻断与安全边界

三个 builder 的核心不是“如何连接”，而是“为什么还不能连接”。endpoint preflight 要求真实
URL 未解析、未包含，凭据值未读，外部请求未发，schema migration 未执行，approval ledger
未写，Java 和 mini-kv 未启动。decision marker 进一步要求 credential value 不可读、不可载入、
不可存储，managed audit connection 不可打开。disabled precheck 还要求 resolver client 和
secret provider 都不可实例化。

`noCredentialConnectionWriteOrAutoStartProved` 仍保留在各 builder，因为它们的证明前提不同。
它不是被一个“万能 predicate”取代。每个方法先验证本领域 record 的结构与回显，再验证统一
side-effect boundary，最后确认 readyForProductionAudit、readyForProductionWindow 和
nodeMayTreatAsProductionAuditRecord 等权限位为 false。中央 no-ledger-write evaluator 仍把
这三个结果与整条 receipt chain 的其他结果做逻辑与。

本版没有读取 credential value，没有解析 raw endpoint URL，没有建立 socket，没有发送 HTTP、
SQL 或 RabbitMQ 请求，没有执行 schema migration，没有写 approval ledger，没有触发 deployment、
rollback 或 restore，也没有自动启动 Node、Java 或 mini-kv。所有改动发生在纯 Java 对象装配、
验证元数据和维护门中。

安全上的另一个边界是 fail-closed。来源 marker 任一必需事实不满足时，warning 会出现，
readiness 会保持 false，最终 rehearsal 仍阻断执行。共享 `MarkerEvidence` 不缓存 ready，
不推导权限，不吞掉 warning，只把已经由领域规则生成的验证元数据做不可变传递。这样抽象不会
成为绕过领域检查的捷径。

## 测试覆盖

测试按四层组织。第一层是完整响应 oracle。默认请求覆盖空 header 的归一化路径，完整 header
请求覆盖 request context、operator window、CI、retention、runtime、managed audit 和 approval
binding 的真实测试 fixture。除两个时钟字段外，整个 JSON 都进入 SHA-256。重构前后摘要完全
一致，证明公开响应、嵌套 record、marker digest、warning、proof、action、blocker 和列表顺序
没有变化。

第二层是共享值对象测试。`MarkerEvidenceTests` 验证输入别名隔离、列表不可变语义、warning
输入和 warning 行格式。第三层是结构测试。`MarkerBuilderArchitectureTests` 锁定所有短 owner
存在、行数不增长、旧 monolith 和四个末端 pass-through wrapper 永久缺席、三个 builder 必须
通过共享 evidence 暴露验证元数据。
`OpsExtractionV1854Tests` 不再坚持“包内所有文件名必须以 ReleaseApproval 开头”的过时假设，
改为精确列出七个短 production owner 和三个短 test owner，并把当前 releaseapproval 文件
总量锁回 119，同时继续验证公开 composition boundary 与根包抽取结果。

第四层是全局门。maintainability、elegance、exact name baseline、change gate、archive、
walkthrough、JaCoCo、SpotBugs、Spotless 和打包仍由 `scripts/verify-release.ps1` 串联。
局部选择已经联合运行完整 oracle、shared evidence、architecture、v1854 history、
warning-digest catalog、maintainability 和 elegance 测试。任何通过放宽 cap、改 fixture、
改 oracle 摘要或恢复旧类来换取绿色的做法都会被视为失败。

最终完整 verify 必须在这份讲解完成之后运行。这样文档描述的是实际最终代码，而不是先通过后
补写的猜测。完整门还会固定前一 annotated tag 的 peeled SHA，统计全部测试、JaCoCo 类数、
SpotBugs finding 和 jar 大小；这些结果会在 closeout 提交中回填，不在本讲解中预先虚构。

## 实际工作量说明

本版不是三个文件改名。前置调查先对 releaseapproval 包 119 个 Java 文件、38879 行进行
census，并统计七个共同方法：warning input 51 处、boundary input 51 处、proof claims 62 处、
Node actions 86 处、warning lines 48 处、boundary lines 48 处、安全证明 39 处。七个方法同时
出现的文件有 38 份。随后逐段比较三个代表性 builder，确认四组元数据 getter 与 warning line
算法可共享，而 boundary projection 和安全 predicate 必须保留领域类型。

兼容工作先于重构。新增 oracle 在旧实现上故意失败，修正 Java Time 模块后取得两份真实摘要，
再连续运行两次确认排除时钟后的快照稳定。只有旧代码通过后，才移动生产类。三个超长 package-
private 类型改为短名，receipt chain、verification hint、warning digest、no-ledger evaluator
和测试调用点全部同步。随后按编排与规则切开方法，建立 `MarkerEvidence`，再由编译器检查所有
可见性和依赖。

第一次定向门暴露出一个不能被忽略的预算问题：三个单体拆成七个 owner 后，ops 文件总数会
从 1161 增到 1165。规则禁止放宽 ratchet，因此没有把阈值改大。沿同一 receipt chain 检查后，
找到末端四个各 58 行的 builder；它们没有状态、分支、校验或转换，七个方法全部只是转调已有
Support。构建链、贡献目录、warning digest 与 no-write evaluator 改为直接调用这些强类型
Support，四个空壳文件被删除，完整响应 oracle 再次通过。这个处理不是把职责塞回巨型文件，
也没有引入反射或通用 Object adapter，而是移除没有语义价值的一层间接。

最终生产 Java 保持 1293，ops 保持 1161，releaseapproval 保持 119 个文件且总行数从 38879
降到 38570。三个巨型文件被七个受精确 cap 约束的短 owner 取代，超过 500 行热点净减三，
最大文件降低 80 行，所有新增 owner 均小于 500 行。生产长文件名减少七，长标识符使用减少
191，唯一长名减少十四；exact baseline 共删除二十二项、新增零项。测试增加三份职责明确的短
owner，分别覆盖完整合同、共享值对象和结构 ratchet。

本版还更新 maintainability cap、v1854 当前架构断言、命名聚合值和 exact baseline，编写 family
design、中文讲解与 archive policy 增量。所有格式化由 Spotless 统一处理。完整 release gate、
implementation CI、closeout CI、annotated tag 与 post-tag receipt CI 属于同一版本的后续闭环，
只有每一层都通过才能写成完成。

这份讲解只解释本项目真实发生的输入、输出、代码和机械证据，禁止硬凑与本次重构无关的功能、
概念或跨项目结论；篇幅来自实际边界和失败条件，而不是为了达到字数而重复同一句话。

## 一句话总结

v1899 用完整双场景响应摘要守住外部合同，把三个 `590/738/726` 行的内部 marker 单体改造成
六个短而有边界的 builder/rules owner，并以 26 行不可变 `MarkerEvidence` 消除真正重复的
验证元数据通道；结果是公开输出、digest、顺序和 fail-closed 权限完全不变，生产最大文件与
超 500 行热点、长文件名、长标识符和 exact baseline 同时收紧，为余下 releaseapproval 热点
提供了一条可以逐批复用、可以机械失败、也不会牺牲领域类型的重构路径。
