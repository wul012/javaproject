# version-1856：分片原型闭包迁移、路由数据收敛与依赖倒置

本文在实现之前记录 v1856 的边界和证据标准，只解释 Java 本项目实际拥有的代码。它不把
Prototype 的只读目录说成活动分片能力，不把 RouteCleanup 历史 closeout 说成实时执行，
也不代替 Node 或 mini-kv 声明跨项目通过。实现中若发现调用图与本文不符，必须在最终
verify 前修正范围与讲解，不能先提交再用模糊措辞掩盖偏差。

## 实际工作量说明

本项目明确禁止硬凑。v1856 的工作量来自十二个生产文件、三个行为测试、三个根 controller、
三十条路由后缀、十二处 SpotBugs 镜像 FQN、两个 RouteCleanup 入站读取者和一条需要倒置的
RouteCleanup closeout 依赖。每项都能由编译器、文本 guard、路由测试或 census 重现；若
事实不足以支撑讲解，就应扩大同一闭包内的有效重构，而不是重复字段或虚构联调成果。

家族设计说明先于实现，分成四行：controller 负责 HTTP 适配，Prototype service 负责编排；
catalog 与 response 只保存数据，`PrototypeRoutes` 集中保存路由数据；readinesscore 与
V1Contract 是上游只读契约，RouteCleanup 通过窄端口提供四字段快照；行为依赖接口，数据
依赖不可变值，任何一侧都不借迁包扩大执行权限。

直接根当前有十五个 `OpsShardReadinessPrototype*` 文件，其中三个是 Spring controller，
其余十二个组成三个对称但有先后关系的实现组。基础 Evidence 组包含 catalog response、
evidence catalog、evidence response 和 evidence service；ConsumerGate 与 Handoff 组各有
同样四类文件。三个 service test 随实现移动，三个 controller-split test 留在根层。预计
root 从二百九十降到二百七十八，可迁移 backlog 从一百八十六降到一百七十四，Prototype
两个 census bucket 从四加八归零，总 `ops` main 仍是一千三百五十二。

这十二个文件不是按名称相似度机械搬运，而是一个可以独立解释的闭包。基础证据组拥有原始
条目和事实拼装，消费门组只回答下游是否具备读取材料，交接组只回答材料是否整理完整；三者
依次调用，不存在反向调用。把它们放进同一维护包后，包内协作不再依赖根目录的偶然可见性，
包外只看公开服务、公开响应和确有消费者的两个端点。审阅者可以从控制器进入，沿三层服务
走到只读来源，再沿返回对象逐字段退回，而不必在二百多个根文件里猜测哪些类属于同一能力。

工作量还包含删除重复所有权而不只是迁包。三十个路由后缀、完整关闭响应的宽依赖、十二条
静态分析镜像和六组测试位置都必须同步改变；其中任一项遗漏，编译可能暂时通过，但后续维护者
仍会面对双重路由来源、根包反向耦合或检查器盲区。因此本版成功标准是旧结构真正消失、新
边界只有一个事实来源，并且历史 HTTP 输出逐字不变，而不是提交记录里出现十二次重命名。

## 入口路由

Prototype 有三组只读 GET 入口，每组十条。基础组从 `/prototype-catalog` 开始，依次覆盖
fixture echo、field alignment、read-only integration bridge、route-cleanup bridge、read
window handoff、consumer gate packet、operator CI handoff、audit digest 与 closeout。
Handoff 组从 `/prototype-handoff-catalog` 开始，ConsumerGate 组从
`/prototype-consumer-gate-catalog` 开始。所有入口继续挂在
`/api/v1/ops/shard-readiness` 下，不新增 method，也不改变任何 suffix 字节。

旧结构把三十个超长 `SHARD_READINESS_PROTOTYPE_*` 字段放在全局
`OpsShardReadinessRoutePaths`，三个 service 又各自重复十次 `BASE_PATH + suffix`。这既让
一千一百一十一行的根聚合器继续膨胀，也让一个 Prototype 变更必须触碰全局文件。v1856
计划把三十个 suffix 移入基础 evidence service 的嵌套 `PrototypeRoutes` 数据类，并用
`CATALOG`、`HANDOFF_CATALOG`、`CONSUMER_CATALOG` 等不超过四十字符的名字表达分组。

根 controller 显式导入 `PrototypeRoutes`，继续使用同一 base path 和 suffix；三个 service
也从同一个数据 owner 组合完整 endpoint。根 RoutePaths 删除旧 Prototype 字段，而不是
再保留三十个转发别名，因为这些字段原本就是 package-private，不是对外 Java API。路由
测试逐条比较 endpoint 与预期字符串，证明删的是重复所有权，不是兼容契约。

以目录请求为例，输入是控制器接收到的无正文读取请求；转换的第一步是用固定后缀匹配处理
方法，第二步是服务从目录取得十个阶段键，第三步才是组合完整地址；输出是原有目录响应，
副作用为零。若后缀拥有者迁移时少一条、重复一条或换了顺序，控制器测试与三十项路由表检查
会分别在适配层和数据层失败。这样能够区分地址没有变和碰巧某个请求还能返回两种完全不同
的结论，避免只跑一个冒烟请求就宣称路由收敛正确。

新的路由所有者只表达静态数据，不承担注册控制器、查找条目或计算状态。基础地址仍由同一
公开常量给出，三个控制器仍是唯一 HTTP 入口，嵌套类也不会成为第四个服务。选择嵌套数据类
而非新增文件，是因为它与基础证据服务共享清晰的家族所有权，同时不制造一个仅装常量的新
生产类型；选择短常量名，是为了让调用点直接呈现阶段含义，并守住后续新增标识符长度预算。

## 响应模型

基础组的 CatalogResponse 描述目录元数据，EvidenceResponse 承载每个阶段的项目、版本、
readOnly、executionAllowed、endpoint、profile、key、phase、Node 计划版本、V1 契约名、
shardEnabled、shardCount、slotCount、routingMode、来源版本、最小字段、证据引用、检查项、
禁止操作、digest、evidencePath 与 status。它不是“启用原型”的命令，而是一张说明当前
仍关闭活动路由的只读证据卡。

ConsumerGateEvidenceResponse 把基础证据转成消费门判断，CatalogResponse 再汇总十个门项。
它检查来源状态、最小字段、路由拓扑预览、digest 接受、CI batch plan、archive manifest、
operator signoff 与 closeout，但任何 passed 都只表示“冻结材料足以被消费”，不表示 Java
可以执行写路由。HandoffEvidenceResponse 和 HandoffCatalogResponse 同理，它们组织 endpoint
inventory、boundary matrix、consumer verification checklist、read-window checklist、
digest/CI/archive manifest 与 signoff packet，输出仍是不可变记录。

迁包不会修改 record component 的顺序和类型。SpotBugs 中六个 response 类型各有两处
EI_EXPOSE 镜像，共十二处 FQN 只改 package，不增加 exclude。专用 guard 会同时断言旧根
FQN 为零、新 FQN 恰为十二，并让完整 SpotBugs 验证实际字节码。

三个响应层次的输入输出也有严格区别。基础响应输入来自单个版本条目和三个只读事实源，输出
是一张完整证据卡；消费门输入是十张基础证据卡和冻结契约字段，输出是十个消费判断；交接层
输入是基础证据与消费判断，输出是面向审阅者的清单。任何层都不接收写入指令，也不返回令牌、
凭据或可执行句柄。这样的分层使材料存在、材料可消费、材料可交接不再被一个布尔值混为一谈，
调用方可以知道失败发生在哪一层，而不是只得到含义不明的未通过状态。

响应记录保持不可变的意义不仅是兼容序列化。它还保证摘要输入不会在返回后被调用者修改，
目录条目不会因一次请求影响下一次请求，控制器也无法在服务完成后偷偷打开执行标志。测试会
同时比较字段内容、集合顺序和最终状态；静态分析镜像只针对既有不可变集合的暴露告警，不能
把新增风险塞进排除文件。若迁包导致需要新增排除规则，应先修正对象边界，而不是扩大豁免。

## 上游证据配置

Prototype 的第一组上游是 v1855 `readinesscore`。EvidenceService 读取基础 readiness 和
echo，要求 readOnly 为真、executionAllowed 为假、shardEnabled 为假，并沿用原始版本、
evidence path、routing mode、shard/slot 计数。第二组上游是 v1853 `v1contract`，它提供
contract name、minimal fields 与 `alignsWithReadOnlyContract` 检查。两组都已在独立 package
形成公开不可变边界，v1856 只消费，不扩大它们的 API。

第三组上游是 RouteCleanup post-completion closeout。旧代码直接构造并依赖根级
`OpsShardReadinessRouteCleanupPostCompletionCloseoutService/Response`，使 Prototype package
一旦迁出就反向借用仍在巨型根 web 中的实现。Prototype 实际只读取 version、
executionAllowed、postCompletionCloseoutEndpoint 与 status 四个字段，因此没有理由看见
完整 closeout response 的十余个字段和四条服务依赖。

v1856 计划在 Prototype service 内定义 `CloseoutSource` 与 `CloseoutSnapshot`。现有 root
RouteCleanup closeout service 实现端口，把已有 `closeout()` 结果映射成四字段快照；Spring
仍注入同一个 bean。依赖方向因此变为 root RouteCleanup 适配器依赖 Prototype 定义的消费
端口，Prototype 不再 import RouteCleanup 类型。没有新增网络调用，没有重复计算 closeout，
也没有更改其 status 判断。

依赖倒置前，基础服务的构造输入包含一个具体关闭服务，因而它必须知道对方的包名、宽响应和
完整构造图；输出阶段只读取四项，却承担了对十余项模型的编译依赖。倒置后，基础服务的构造
输入是自己定义的只读来源接口，返回自己定义的四字段快照；根层关闭服务既保留原有关闭接口，
又负责把结果映射成快照。输入从具体实现对象缩成能提供四个事实的能力，输出从宽领域响应
缩成本消费者实际使用的数据，而运行时仍只有原来那个 Spring 实例参与。

四字段各有不可替代的作用：版本进入摘要材料，执行许可证明关闭边界，关闭端点进入证据引用，
状态参与通过判断。再少一项会丢失原行为，再多一项会把无关变化重新耦合进来。适配映射必须
显式逐字段完成，不用反射、不转成通用映射，也不吞掉空值；这样字段含义在代码审阅中可见，
编译器能在记录变更时迫使维护者重新判断，而不是让运行期字符串查找悄悄漂移。

## 服务层核心流程

一次基础 Evidence 请求先按 key 从 package-private catalog 取版本化 Entry，再调用 readiness
与 echo，最后从 `CloseoutSource` 取得四字段快照。它把三类来源整理为 evidenceRefs，依次
检查 V1 契约对齐、echo passed、closeout passed、两个 executionAllowed 均为 false，然后
计算 status。digest 的材料仍按 entry version、key、profile、readiness version、echo
version、closeout version 与 evidence path 的原顺序连接后做 SHA-256；端口化不能改变
任何一个字段或分隔顺序。

ConsumerGateService 消费基础 EvidenceService 的十个阶段输出，结合 V1Contract 最小字段
形成 catalog、source inventory、minimal field checklist、route topology preview、boundary
matrix、digest acceptance、CI plan、archive manifest、operator signoff 与 closeout。Handoff
service 再把基础和 consumer gate 证据组织成交接目录。三个 service 都保持
`@Transactional(readOnly = true)`，内部 catalog 与辅助计算保持 package-private/private。

路由收敛是数据重构，不是把三组 service 合成巨型类。`PrototypeRoutes` 只保存 base path
和三十个 suffix，不包含 Spring、状态计算或证据构造；service 只引用数据，不复制字面值。
这满足“第三个相似实现出现前先抽共享抽象”的规则，也避免一个新的独立 route-owner 文件
增加总量。嵌套数据类名与新增常量名都受四十字符预算约束。

完整请求机理可以按六步重放：控制器接收读取请求并调用对应方法；目录按固定键返回不可变
条目；基础服务分别读取准备度、回显和关闭快照；服务按原顺序生成引用、检查项和禁止项；
摘要函数对原材料求值；响应记录返回控制器并由框架序列化。消费门与交接层只是继续读取上一步
结果，不会绕过基础服务重新访问根层实现。任何一步失败都在当前请求内暴露，不写数据库、
不发消息、不启动外部进程，也不缓存一个可能过期的已通过结果。

服务图保持有向无环是本次结构收益的核心。准备度和冻结契约位于上游，原型位于中间，根层
关闭实现作为端口适配器只向中间层提供窄事实；消费门和交接位于原型包内部的下游。包移动后
若出现原型重新导入根层关闭响应，守卫会失败；若根层为了适配端口反过来调用消费门，编译图
与聚焦测试会暴露环。这个约束让下一批清理 RouteCleanup 时可以替换其内部结构，而不再次
撬动原型的摘要、响应和控制器。

## Java 证据检查

编译器首先验证十二个文件的新 package、三个 controller import、两个 RouteCleanup 入站
reader、readinesscore/V1Contract import 和 CloseoutSource 实现是否闭合。若 root closeout
service 少实现一个方法、快照字段顺序错误或 Spring 找不到端口实现，test-compile 会立即
失败。随后三个 service test 在新包直接检查原有 response、digest 与 status；三个根
controller-split test 验证 controller 未移动并仍映射三十条 GET route。

v1856 guard 应确认新包恰有十二个生产文件、根只剩三个 controller、package test 恰有
三个 service test、根保留三个 controller test。它还逐条检查 `PrototypeRoutes` 的三十
个 suffix 值、根 RoutePaths 不再含旧长字段、两个合法 RouteCleanup reader 只读取公开
catalog/field endpoint，以及 Prototype package 中没有 RouteCleanup service/response FQN。

census 必须输出 direct root 二百七十八、target 一百零四、retained 一百零四、remaining
一百七十四、unassigned 零；总 `ops` main 不超过一千三百五十二。maintainability census
必须显示 RoutePaths 低于 v1855 的一千一百一十一行，而不是提高 cap。最后完整 verify
覆盖一千八百余测试、JaCoCo、SpotBugs、Spring 注入与生产 profile。

需求到证据的对应关系必须一一成立：十二文件迁移由路径守卫和编译证明，三十路由收敛由常量
清单与控制器测试证明，窄端口由禁止根类型导入和快照映射断言证明，序列化兼容由原行为测试
证明，维护预算由 census 与行数门证明。某一项只有文档声称而没有会失败的机械检查，就仍是
未完成；某一项只有测试通过却无法解释输入输出，则必须补足设计说明后重新验证。

历史快照和当前门要分开读取。旧版本文档里的二百九十是迁移起点，不应被全局替换；当前实时
上限必须收紧到二百七十八，且后续不能回升。总文件数不变说明这不是删除能力，根文件数下降
说明所有权确实迁出，未分配数为零说明没有把文件藏到统计之外。三个数字共同出现，才足以
证明保持行为、改善结构两件事同时发生。

## mini-kv 证据检查

本版不运行也不修改 mini-kv。Prototype response 中的 shard、slot、routing mode 或 active
prototype 文本来自冻结 Java readiness 与计划说明，不是从 mini-kv 实时读取。任何目录
状态为 passed 都不能解释为 mini-kv 已启动、slot table 已加载或写路由已开放。

禁止操作列表继续包含 active-shard-router、write-routing、credential-value-read、raw
endpoint parse、managed-audit connection、deployment/rollback 与 Node 自动启停 Java 或
mini-kv。CloseoutSource 也不携带进程句柄、端口、credential 或可写 endpoint，只携带四个
只读事实。这样依赖倒置不会顺便形成新的执行通道。

真正的 mini-kv 实时联调属于跨项目 capstone 的环境授权与单独报告。v1856 能证明的是
Java 没有越权、冻结证据字节没变、阻断条件仍在；不能把静态文本冒充实时健康检查。

## 阻断与安全边界

第一条阻断是路由字节变化：三十个 suffix 只换 owner，任何字符、连字符或顺序变化都使
route test 失败。第二条是 digest 变化：CloseoutSnapshot 必须保留原 version，digest 材料
不能加入 endpoint 或 status。第三条是可见性扩张：只公开两个确有入站 reader 的 endpoint，
不能把全部 catalog、helper 或 RouteCleanup response 改 public 来省 import。

第四条是执行边界：所有 controller 保持 GET，service 保持 readOnly transaction，response
保持 executionAllowed false。第五条是所有权边界：controller 不移动，RouteCleanup 实现
不进入 Prototype package，Prototype 不 import root RouteCleanup。第六条是维护预算：不得
新增生产文件、不得保留旧三十常量形成双 owner、不得提高 root/file-size/SpotBugs ratchet。

如果为了让测试通过而修改 fixture、response component、状态期望或历史 route，版本失败；
如果新增 Java 标识符超过四十字符，版本失败；如果讲解少于三千汉字、不是中文多数、缺少
十个规定标题或在 final verify 后改正文而不重跑，版本同样失败。

## 测试覆盖

三个 service test 随实现迁移，分别覆盖基础 evidence、consumer gate 和 handoff。它们会
复用原测试图，验证十个 entry、来源版本、V1Contract 字段、route-cleanup closeout 状态、
禁止操作、digest 和最终 status。端口化后应增加针对快照映射的断言，确保原 response 的
version、executionAllowed、endpoint、status 原样进入 Prototype，而不是只验证“能编译”。

三个 controller-split test 留在根，验证 controller class、RequestMapping base path、十个
GetMapping suffix 和 service 调用。RoutePathsTests 改为读取 `PrototypeRoutes` 并继续比较
三十个完整 endpoint，旧长字段必须从根聚合器消失。两个 RouteCleanup reader test 或 v1856
guard 验证只有 catalog 与 field alignment endpoint 是公开入站面。

验证顺序保持 test-compile、聚焦 Prototype/port/route/census/预算、Spotless、full verify、
census、提交、实现 CI、closeout、tag、closeout CI。本文先于实现写入；实现若出现额外边，
必须在 final verify 前把真实修复补回，而不是改测试字节或增加 waiver。

聚焦验证会按风险而非文件名堆叠。第一组验证基础服务在关闭通过与关闭失败时的状态、摘要和
执行许可；第二组验证十项消费门在单项来源失败时不会误报全部通过；第三组验证交接目录保留
次序与证据引用；第四组验证三个控制器各自的十条映射；第五组验证端口适配恰好复制四字段；
第六组验证路径、可见性、标识符长度、静态分析镜像和 census。完整回归随后捕获这些类型对
仓库其他家族的真实入站影响，二者不能相互替代。

失败处置也预先固定：编译失败先修导入与可见性，路由失败对照原字节而不改期望，摘要失败
检查输入顺序而不更新冻结值，静态分析失败修对象所有权而不加宽排除，统计失败重新核对迁移
集合而不放松上限。若完整验证揭示计划外消费者，先把它记录进设计说明并建立窄边界，再重跑
聚焦与全量验证。只有同一提交在这些条件下全部通过，才允许进入远端实现检查。

## 一句话总结

v1856 的价值不是“打开分片原型”，而是把十二个只读 Prototype 实现放进明确 package，
用一个短名路由数据 owner 替代三十个全局长字段，用四字段消费端口切断对 RouteCleanup
大响应的反向依赖，并以编译、三十条 route、digest、census、SpotBugs 与全仓回归证明：
输入来源、输出字节、只读状态和禁止执行边界全部保持透明且可机械复核。
