# version-1855：分片就绪核心闭包迁移与路由所有权校正

本文只讲 Java 项目中既有的分片就绪核心，不把目录迁移说成新增业务能力，也不把 Node
或 mini-kv 的外部状态写成 Java 已经验证的事实。讲解在实现之前落盘，目的是先把边界、
输入、输出、失败条件和机械证据说清楚，再让代码迁移接受这些约束。实现若与本文预先
描述的事实发生偏差，必须在最终验证之前修正文档或实现，不能让讲解替错误设计背书。

## 实际工作量说明

本项目只用真实变更支撑篇幅，明确禁止硬凑。二十个生产类、十个行为测试、十六个主要
生产消费者、十八处 SpotBugs 镜像路径、三个路由后缀所有权以及多代 census pin 都是可
逐项复现的工作量。若这些事实不足以形成完整讲解，应扩大同一闭包内的有效治理，而不是
重复安全口号、复制字段清单或把未发生的跨项目联调写成成果。

v1855 处理的是直接根 `ops` 包里最早形成、也最容易被后续功能反复借用的一组分片就绪
对象。它不是一个单独接口，而是由基础 readiness、hardening、echo、evidence index、
evidence verification、evidence handoff、active shard plan handoff、live read gate plan、
operator service lifecycle、declared operator lifecycle 十个服务及十个不可变响应组成。
其中 ActiveShardPlanHandoff 在旧 census 中单独占两个文件，其余十八个被归为 readiness
core simple endpoints；从调用图看，它们实际上是一条连续证据链，拆成两个版本只会让
中间包之间多一条暂时依赖，因此本版把二十个生产文件作为一个闭包处理。

设计说明先于实现，边界压缩成四行：根 controller 只做 HTTP 适配，核心 package 负责
只读证据组合；service 是行为入口，response 是不可变输出；endpoint、fixture、evidence
常量属于产生它们的核心服务，根 RoutePaths 只做兼容聚合；数据字节不重写，执行权限不
扩张。这个划分让“目录位置变化”和“契约语义变化”可以被分别审查。

本版预计把直接根文件从三百一十个降到二百九十个，把可迁移文件从二百零六个降到
一百八十六个，同时保持 `ops` 生产文件总数一千三百五十二不变。十个服务测试跟随实现
进入同包，跨家族的历史 endpoint 快照、根 RoutePaths 对照测试以及 controller 测试继续
留在根测试层。它们留下不是遗漏，而是因为这些测试验证的是组合根的兼容责任。新增的
机械 guard 使用短名 `OpsExtractionV1855Tests`，不会延续历史上五六十字符的测试类名。

## 入口路由

外部调用者看到的 HTTP 入口保持不变。基础入口仍是 `GET /api/v1/ops/shard-readiness`，
加固和回显入口仍分别是 `/hardening` 与 `/echo`；证据三段仍是 `/evidence-index`、
`/evidence-verification`、`/evidence-handoff`；生命周期一侧仍包括
`/active-shard-plan-handoff`、`/live-read-gate-plan`、`/operator-service-lifecycle` 与
`/declared-operator-lifecycle`。本版不新增 route，不改变 HTTP method，也不让 controller
跟随实现下沉。三个根 controller 继续承担清晰的适配职责：一个负责基础、加固和回显，
一个负责证据索引、核验和交接，另一个负责活动分片计划与操作员生命周期计划。

一次典型只读请求的路径可以写成：

```text
GET request
  -> root Spring controller
  -> readiness-core service
  -> prerequisite core services
  -> immutable response
  -> JSON serialization
```

迁包前，evidence index、verification 和 handoff 三个服务通过根级
`OpsShardReadinessRoutePaths` 取得后缀。这样在同包时可以编译，但迁出后会让底层核心包
反向依赖组合根。v1855 计划让基础 `OpsShardReadinessService` 持有 base path 与三个短
后缀常量，三个服务在新包内部直接组合它们，根 RoutePaths 再把原字段委托给核心所有者。
因此旧 controller、旧测试和任何同包调用仍读到完全相同的字符串，而依赖方向变为
“根聚合器依赖核心”，不再是“核心依赖根聚合器”。

## 响应模型

十个 response 都是只读快照，不是命令执行回执。最基础的 `OpsShardReadinessResponse`
输出项目名、Java 版本、readOnly、executionAllowed、shardEnabled、shardCount、slotCount、
routingMode、evidencePath 与 status。它刻意把 `readOnly=true`、`executionAllowed=false`、
`shardEnabled=false` 放在同一个模型里，让消费者不能因为“存在分片字段”就推断活动分片
路由已经打开。HardeningResponse 在此基础上补充冻结 fixture、归档证据、禁止事项和
消费建议；EchoResponse 再把多个既有快照串联起来，证明接口可读，而不是证明可执行。

EvidenceIndexResponse 的输入来自一组版本化 endpoint 与 fixture 清单，输出是可供审计的
索引；EvidenceVerificationResponse 消费索引，检查 frozen contract、证据路径和禁止边界；
EvidenceHandoffResponse 消费核验结果，给出 Node 可以读取哪些冻结材料以及何时必须停止。
这里的“handoff”只是交接事实，不是把 Java 运行时控制权交给 Node。它不会传出凭据值、
原始 endpoint、数据库连接或可写路由句柄。

生命周期四个 response 进一步解释谁负责启动、探活和清理服务。ActiveShardPlanHandoff
明确活动分片原型权仍在上游计划边界；LiveReadGatePlan 要求操作员先声明 Java owner、
base URL 或端口、启动命令责任和停止责任；OperatorServiceLifecycle 把这些声明组织成
可检查步骤；DeclaredOperatorLifecycle 则回显已经声明的责任字段。即便所有字段完整，
输出仍是计划与证据，不会自动启动 Java、mini-kv 或 Node，也不会打开写路由。

## 上游证据配置

这些服务的输入大多不是用户任意 JSON，而是编译期常量、冻结 fixture 路径和前一阶段
response。每个 service 都保留 `ENDPOINT`、`FIXTURE_ENDPOINT` 与 `EVIDENCE_PATH`，例如
基础 readiness 对应冻结 v153 事实，active shard handoff 对应 v158，live read gate 对应
v159，后续 lifecycle 与 echo 则继续沿用各自历史版本。版本号的价值是定位证据来源，
不是暗示这些历史版本今天重新执行过。迁包只改变 Java FQN，不能改任何路径字节，也不
重写 `contracts/` 或 `e/` 目录，因为下游项目可能按绝对历史路径和 digest 消费它们。

ReadOnlyEvidence 与 V1Contract 两个已抽取 package 是主要入站消费者。前者用十个核心
service 的 endpoint、fixture 与 response 生成只读目录和完整性快照；后者把基础 readiness
契约作为消费方对齐基准。RuntimeExecution 只读取核心证据以形成候选材料，Prototype
则读取 readiness 与 echo 作为“仍然只读”的先决条件。v1855 对这些文件只做 import
重定向，不改变它们收集的字段、列表顺序和状态计算。编译器负责暴露漏掉的 FQN，专用
guard 则防止旧根 import 悄悄残留。

mini-kv 与 Node 在这些 response 中只以证据来源或消费说明出现。Java 不读取 mini-kv
进程状态，不调用 Node route，也不把外部文档中的“passed”转换成自己的运行许可。
如果 frozen fixture 缺失、版本链断裂或上游状态不满足，服务只能输出 blocked 或停止
条件，不能通过默认值制造 ready。这样上游配置是可追溯输入，而不是隐式控制平面。

## 服务层核心流程

基础 readiness service 是整条链的最小事实源：它构造固定的只读响应，明确 shard count
与 slot count 尚未进入活动执行。Hardening service 在其上声明 fixture 与归档规则；
EvidenceIndex service 汇总 endpoint 对；EvidenceVerification service 检查索引是否覆盖
live、fixture 和 evidence 三种视角；EvidenceHandoff service 只在前置检查满足时形成
消费交接。Echo service 同时读取 readiness、hardening、index 与 handoff，再用布尔条件
计算 status。任何一个前置 status 不是 passed、readOnly 为 false、executionAllowed 为
true 或 shardEnabled 为 true，echo 都必须返回 blocked。

ActiveShardPlanHandoff 紧接 evidence handoff。它检查来源 status、readOnly 与执行禁止，
并附上 frozen Java evidence、Node consumption references、Java boundary rules 和 stop
conditions。LiveReadGatePlan 读取该 handoff，要求 owner、端口与清理计划先存在；两个
lifecycle service 再把计划转换成操作员可核对的责任清单。整个过程是有向的：后一步
只能消费前一步的不可变 response，不允许跳过核验直接写出 passed。

迁包后的 Spring 注入语义不变。类仍由 `@Service` 注册，构造器仍显式接收依赖，方法仍以
`@Transactional(readOnly = true)` 执行。公开面只保留跨包确实需要的 service、response
与不可变常量；没有必要跨包调用的私有列表构造、status 计算和 fail-closed 规则继续是
private。也就是说，包移动不会成为扩大内部 API 的借口。

## Java 证据检查

Java 端的第一层证据是编译器。二十个文件改 package 后，所有根 controller、根 registry、
readonlyevidence、v1contract、runtimeexecution 和 prototype 消费者必须显式导入新 FQN；
遗漏一处就无法 test-compile。第二层是 route 对照测试，它把根 RoutePaths 的三个证据
后缀与新包 service endpoint 逐项比较，保证委托前后字节相等。第三层是历史 snapshot
测试，它证明新位置没有把 v187 之后的 endpoint 倒灌进 v179、v184 等旧列表。

第四层是 v1855 专用结构 guard。它应断言新包恰有二十个生产文件、根目录不再出现这十
对 service/response、十个服务测试进入对应测试 package、跨家族测试仍留在根层，并检查
十六个主要生产消费者都指向新包。guard 还应检查三个 evidence service 不再出现根
RoutePaths 依赖，基础 service 持有短常量，根聚合器执行兼容委托。

第五层是静态分析与预算。SpotBugs 文件中九个 response FQN 有两组镜像条目，共十八处
必须同步迁移，不能新增 exclude。root census 必须给出二百九十、目标一百零四、保留
一百零四、剩余一百八十六、unassigned 零；总 `ops` main 仍是一千三百五十二。完整
`mvnw verify` 还会覆盖 JaCoCo、SpotBugs、Spring 上下文、数据库迁移与所有既有业务测试，
因此“文件搬到了新目录”只是开始，不是通过标准。

## mini-kv 证据检查

本版不改 mini-kv，也不启动它。Java response 中出现的 mini-kv 文本只描述职责边界：
如果未来 live-read 窗口把 mini-kv 纳入范围，必须另有明确 owner、端口、启动与停止责任；
Java 不能从这组 endpoint 自动启动或停止 mini-kv。Active shard prototype 的执行权也不
因为 handoff 字样转移给 Java，它仍是冻结计划中的外部边界。

因此对 mini-kv 的检查是“确认没有越界”，而不是伪造联调。专用测试会继续验证 stop
conditions 包含禁止自动启动外部进程、禁止启用 active shard router、禁止读取 credential
value 与 raw endpoint。完整回归若读取 frozen mini-kv 文本，只验证字节与历史契约一致，
不会把它包装成实时健康检查。真正的跨项目实时结果只能由已授权的 cross-project suite
给出，单个 Java extraction 版本无权替它下结论。

这种表述也保护维护者：看到 `mini-kv-service-owner-if-mini-kv-live-read-is-in-scope` 时，
应理解为缺失即阻断的输入字段，而不是待 Java 自动推断的配置。看到 Node 版本引用时，
应理解为冻结消费记录，而不是 Java 可以调用 Node 的许可。

## 阻断与安全边界

安全边界首先是 HTTP 只读：所有入口保持 GET 语义，不增加 POST、PUT、PATCH 或 DELETE。
第二是事务只读：核心 service 的公开方法继续使用 read-only transaction，不写订单、库存、
支付、outbox、审计表或任何 schema。第三是运行时禁止：executionAllowed、shardEnabled、
activeShardPrototypeEnabled 等执行标志必须保持 false，缺失 owner、端口、清理责任或来源
status 时只能 blocked。

第四是敏感信息边界。response 可以回显“需要 credential owner”或“raw endpoint parse
被禁止”，但不能读取 credential value，也不能解析未经批准的原始 endpoint。第五是进程
责任边界：Node 不能借这份计划启动或停止 Java/mini-kv，Java 也不能代替操作员开启生产
窗口。第六是归档边界：`contracts/`、`e/<version>/` 和已有 digest 不移动、不重写。

结构迁移本身也有阻断条件。若为了编译把内部状态计算全部改成 public，停止；若根文件
数减少但总文件数增加，停止；若 route 字符串改变，停止；若 SpotBugs waiver 增加，停止；
若新 Java 名称超过四十字符，停止；若测试期望或 fixture 字节被改来迎合迁移，停止。
这些条件让“编译成功”不能掩盖边界退化。

## 测试覆盖

十个服务测试随实现移动，原因是它们直接构造 service、检查 response 字段、status 计算和
fail-closed 列表，属于 package 行为。它们覆盖基础 readiness 固定字段、hardening 规则、
echo 聚合、evidence 三段链、active shard handoff、live read gate 与两个 lifecycle 输出。
迁包后测试与实现同包，可以继续访问必要的 package-local 测试边界，而不需要把生产内部
类型公开。

留在根层的测试承担另一种责任。RoutePathsTests 验证组合根仍能提供原有 route 后缀；
HistoricalEndpointSnapshotCompatibilityTests 跨 readonlyevidence 与 v1contract 检查历史
层叠；prototype 三组测试仍属于下一批家族，只导入新的 readiness core；controller mapping
测试继续证明 Spring adapter 没有被一起下沉。测试位置本身因此表达了所有权。

第一次完整聚焦选择实际执行八十一个测试，出现三个维护性失败，没有 service 行为失败。
其中一个是新 guard 把同文件里无关的 Prototype 根 import 与合法的新 core import 拼在一起，
形成假阳性；另外两个是 v1828 census 测试仍把实时值钉在三百一十和二百零六。修复只把
guard 收窄为逐个匹配已迁类型的完整 import，并把实时 pin 收紧为二百九十和一百八十六，
历史 `310/206` 仍保留在版本链。随后十个结构测试又发现 README 尚未索引 v1855 文档；
补上可发现性入口后，完整八十一个聚焦测试全部通过。这个过程没有修改 fixture、route、
response 或业务断言，说明门禁拦下的是维护遗漏，而不是靠放宽期望制造绿色。

第一次完整 verify 共执行一千八百零六个测试，十二个 failure 与一个 error 仍全部属于
维护门。v1847 至 v1853 的七个抽取 guard、v1806 closeout guard 仍使用上版实时根数；
v1852 guard 仍从旧根路径读取基础 readiness service；归档规范则指出本版两个证据标题
缺少规定空格、缺少“禁止硬凑/本项目”字面承诺，且英文标识较多使中文占比略低于一半。
修复把八个实时 pin 一致收紧到二百九十、把旧路径指向 readinesscore，并用本段真实失败
分析提高中文信息密度；没有添加 legacy 豁免，也没有降低三千汉字或中文多数阈值。

执行顺序是 test-compile、聚焦 service/结构/历史/预算测试、Spotless、完整 verify、census，
然后提交、push、实现 CI、closeout、tag、closeout CI。讲解和矩阵已经先于实现写入，后续
若编译器暴露未预见的边，必须在最终 verify 前把实际修复和原因补回本文，不能在验证后
悄悄改讲解。最终证据以可复现命令、测试数量、CI run id 和 tag 为准。

## 一句话总结

v1855 要做的不是让分片功能“更能执行”，而是把二十个只读 readiness 核心对象放回同一
所有权边界，让根 controller 与 route aggregator 只做适配和兼容，同时用编译、路由字节、
历史快照、SpotBugs、census 与完整回归证明：输入来源、输出模型、失败条件和禁止执行的
安全语义一个字节也没有被目录迁移偷换。
