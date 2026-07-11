# version-1852：只读证据目录链提取讲解

## 实际工作量说明

本版继续只做本项目 Java 仓库，目标是把只读证据目录、目录交接、交接验证和端点完整性四层从根
`ops` 包迁成一个可独立理解的模块。禁止硬凑篇幅，也不把“移动文件”冒充业务功能。本版实际处理
十一份生产类型、七个包内行为与快照测试、一个留根控制器、一个留根 ControllerSplit 测试、一个新
测试支持类、一个根测试工厂、六个历史兼容测试、四个路由后缀、三十组根核心证据常量、四个
SpotBugs Response 双镜像、七处近期活计数、endgame census、版本守卫、索引、进度账本和长篇讲解。

十一份生产类型构成真实闭包。目录层由 Service、Response、Snapshot 三份组成；交接层由 Service 和
Response 两份组成；交接验证层由 Service、Response、Snapshot 三份组成；端点完整性层同样由
Service、Response、Snapshot 三份组成。控制器是 HTTP 适配器，继续留在根包。四层在语义上按 v175、
v177、v179、v184 前进：先冻结基础目录，再声明如何交给下游，再验证冻结边界，最后核对当前端点和
fixture 是否一一对应。

这次迁移不只解决文件位置，还处理了两个实际维护问题。第一，三份 Snapshot 原来依赖同包可见性，
许多根历史测试直接调用它们；若迁移后简单把 Snapshot 改 public，会把生产内部实现永久暴露。本版
改用测试源集 TestSupport 转发六个不可变列表，生产 Snapshot 仍保持 package-private。第二，v184
完整性 Snapshot 过去借用根证据注册表内部的 EndpointPair 类型，这是一条只能在同包内存在的隐形
耦合。本版让 Snapshot 自己持有极小的 package-private record，Service 读取同样两个字段，输出不变。

新包最长主源码绝对路径一百八十三字符，最长测试路径一百八十八字符，远低于 Windows 传统边界。
根包从四百八十二降到四百七十一，可迁 backlog 从三百七十七降到三百六十六，ReadOnlyEvidence 桶
从十一清零；一百零五个最终保留文件、零未分类和一千三百五十二个 ops 主源码总上限都没有放松。

## 入口路由

对外仍是同一个根包 ReadOnlyEvidence 控制器。它通过构造注入持有四个 Service，分别提供目录、目录
交接、交接验证和端点完整性 GET。控制器没有请求体、没有动态地址参数、没有凭据字段和执行开关；
每个方法只调用对应 Service 并返回 Response。迁移只增加新包 import，HTTP 方法、完整路径、响应
字段、媒体类型和异常边界不变。

四个路径继续由根全局 RoutePaths 组合。BASE_PATH 固定为 `/api/v1/ops/shard-readiness`，四个历史
后缀分别表达只读证据目录、目录交接、交接验证与端点注册完整性。本版没有新增局部 route owner，
因为根 RoutePaths 是 endgame 明确保留的全局聚合器，也是这些早期核心端点的既有所有者。新增第二
份 owner 会复制常量并让主源码总数增长；本版只把四个不可变后缀改为 public，供新包 Service 读取。

这种选择不是偷懒，而是按所有权区分。近几百版新增的业务家族有独立 route owner，便于整族迁移；
v175 至 v184 目录端点属于根 readiness 核心注册表，历史快照也以全局顺序冻结它们。继续让全局聚合器
拥有后缀，可以避免改变历史注册顺序。版本守卫同时检查 Service 必须读取该 owner，路由测试检查完整
URL，任何字符变化都会失败。

通俗理解，四个入口像档案馆的四个窗口：第一个发目录，第二个发交接单，第三个验交接单，第四个数
目录中的实物和索引是否成对。窗口号码和办理规则不变，只是窗口后的档案人员从综合办公室搬进“只读
证据目录室”。外部调用者无需改地址，内部维护者却能一次看到完整四阶段流程。

## 响应模型

Catalog Response 记录项目、版本、只读和执行边界，同时保存来源 Echo、来源运行证据收尾、目录
profile、二十个 live endpoint、二十个 fixture endpoint、对应 evidence archive path、来源回执、
消费批次、失败关闭规则和禁止操作。它回答“v175 冻结目录里有什么”，不回答“现在是否可以执行”。

Handoff Response 在目录之上增加来源目录版本、回执、目录端点、fixture、evidence、数量、冻结状态、
下游消费提示、交接制品和消费规则。Verification Response 同时读取 Catalog 与 Handoff，并比较冻结
数量和 v179 当前注册数量，确认新增交接与验证端点没有反向写入 v175 快照。Integrity Response 则把
v184 的二十三组 live/fixture pair 做数量、唯一性、非空和自包含检查。

四个 Response 都是不可变 record。迁移前后字段名、顺序、类型、列表顺序、版本号、receipt id、
evidence path、状态词和否定安全字段全部不变。SpotBugs 原有四个集合暴露镜像仅更换完整包名，两组
规则仍同时存在，没有用通配符覆盖更多类。JSON 序列化因此保持稳定，Node 或文档消费者无需知道 Java
内部 FQN 变化。

用具体数字说明输入输出。v175 Catalog 从核心与 RuntimeExecution 读取二十组端点、fixture 和归档
路径，输出数量二十。v177 Handoff 消费这份目录，仍保留二十，不向旧快照补写自己的端点。v179
Verification 的当前列表包含 Catalog、Handoff、Verification 三层，数量二十二，但冻结目录仍是二十。
v184 Integrity 再加入自身，形成二十三组 pair。每一步都允许“当前列表追加”，同时禁止“历史快照
回填”，这正是 append-only 兼容机理。

## 上游证据配置

Catalog 的直接运行上游有两个。一个是根 Echo Service，它汇总基础 readiness、hardening、证据索引
和证据交接的只读状态；另一个是 v1851 新包的 PassEvidenceCloseout Service，它证明运行执行证据链
已经完成只读冒烟、清理与归档收尾。Catalog 不重新执行前序步骤，只读取两个 Response，并把来源回执
固定到目录中。

三份 Snapshot 还引用十个根 readiness 核心 Service 的 ENDPOINT、FIXTURE_ENDPOINT 和
EVIDENCE_PATH，以及九个 RuntimeExecution Service 的同类常量。过去 Snapshot 与核心类同包，访问
依靠默认可见性；迁移后这些不可变字符串需要明确跨包边界。本版只把三类静态常量公开，没有公开
私有算法、仓储、执行方法或可变集合。调用者能知道“证据在哪里”，不能因此获得“如何写入或执行”。

CatalogHandoff 唯一消费 Catalog；HandoffVerification 只消费 Catalog、Handoff 和自己的冻结快照；
Integrity 只消费自己的 pair Snapshot。下游主要是 V1Contract 历史兼容测试和根 EvidenceEndpoints
滚动注册表。生产下游通过公开 Service/Response 或不可变常量读取；测试下游通过 TestSupport 读取
历史列表，三份 Snapshot 本身没有被提升为生产 API。

历史 fixture 与 evidence 路径仍指向原目录。v175、v177、v179、v184 的资源名和归档版本不会因
package 迁移重命名，Node 中可能存在的摘要或绝对引用也不受影响。目录治理改变 Java 编译所有权，
不触碰跨项目证据字节。

## 服务层核心流程

Catalog Service 先调用 Echo 与 Runtime Pass Closeout，确认两份来源回执；再从 v175 Snapshot 读取
二十个 live endpoints、二十个 fixture endpoints 和对应 archive paths。它比较列表数量、固定来源
receipt、生成消费者批次和失败关闭规则，最后返回 Catalog Response。若来源未通过或列表不齐，状态
必须 blocked；它不会通过补造路径让数量对齐。

Handoff Service 调用 Catalog，复制来源版本、回执和三类路径，生成只读交接制品、消费规则、失败
关闭检查和禁止操作。它可以说明“Node 批处理可以读取”，但 nodeMayStartOrStopJavaOrMiniKv 继续为
false。Handoff 不启动任何服务，也不把目录写入远端系统。

Verification Service 同时得到 Catalog 与 Handoff，再从 v179 Snapshot 读取二十二个 live/fixture。
它核对来源均 passed、冻结目录仍是二十、当前列表包含自己的验证端点，并确认未来增长不修改 v175。
这一步的关键不是“当前越多越好”，而是“旧列表保持原序，新端点只追加到新快照”。

Integrity Service 从 v184 Snapshot 取得二十三个 package-private EndpointPair，再分别投影 live 和
fixture 列表。它检查三个数量一致、两类 endpoint 各自无重复、每个 pair 两端非空、当前列表包含
Integrity 自身端点和 fixture。最后生成九条 verification checks。新 EndpointPair record 只有两个
字符串且不可变，替代对根私有嵌套类型的借用，不改变任何业务判断。

测试装配也按同样顺序收口。ReadOnlyEvidence TestSupport 先构造 EvidenceIndex、Verification、
Handoff 和 Echo，再复用 v1851 RuntimeExecution TestSupport 取得 PassEvidenceCloseout，创建 Catalog；
其余工厂由 Catalog 顺序建立 Handoff 和 Verification。根 ServiceGraphTestFactory 改为委托它，避免
同一服务图存在两套构造代码。

## Java 证据检查

v1852 守卫用精确清单验证十一份生产文件只存在于新包，目标包不能混入控制器或无关类型；控制器必须
留根并导入新包。七个行为/快照测试必须移动，新 TestSupport 与它们同目录，总数恰好八；ControllerSplit
测试留根，从包外证明公开 Service/Response 足够支撑控制器拆分。

守卫逐一读取三份 Snapshot，要求类声明仍是 package-private，禁止出现 public。它检查 TestSupport
公开 v175、v179、v184 六个列表方法和服务工厂；根历史兼容测试必须使用 TestSupport，不得直接跨包
调用 Snapshot。根测试工厂也必须委托 Support，不能重新出现 Catalog 构造长链。

v184 解耦守卫要求 Snapshot 内存在自己的 EndpointPair record，并且源码不再出现根
EvidenceEndpoints。四个 Service 必须读取根 RoutePaths，并公开三类不可变路径常量；十个根核心
Service 也必须显式公开这三类常量。SpotBugs 四个 Response FQN 必须全部迁入新包，旧根 FQN 必须
为零。

数量守卫要求根包恰好四百七十一，总 ops 主源码不超过一千三百五十二。普查脚本必须报告保留一百零五、
可迁三百六十六、ReadOnlyEvidence 桶零、未分类零。v1847 到 v1851 的历史文档数字保持不变，只有
它们的“当前活计数”与三个全局棘轮继续向下收紧。

首次 test-compile 给出了一个精准反馈：生产一千四百八十三个源码全部通过，测试只有一处仍调用旧
根工厂的长方法名。修复只把该调用改成新 TestSupport 的 `handoffVerificationService()`，没有修改
断言。第二次编译包含八百五十个测试源码并成功。这说明编译器被当作依赖边审计器，而不是用扩大
可见性掩盖错误。

## mini-kv 证据检查

本版不编辑、不启动 mini-kv，也不执行 `minikv_cli`。Catalog 中的 mini-kv 或 Node 消费提示来自
历史只读证据，表示将来消费者可读取哪些固定端点，不代表本版进行了实时联合测试。C1-C4 capstone
仍由 Node 窗口负责；Java 本版只保证 jar 内的目录、交接和历史兼容行为不变。

四层 Response 都显式禁止 Node 启动或停止 Java/mini-kv，禁止写路由、启用活动分片、读取凭据值、
解析原始 endpoint、连接托管审计、部署或回滚。即使 Integrity 返回 passed，也只说明二十三组目录
引用完整且无重复，不说明对应服务已经被实时调用，更不说明可以写 mini-kv。

可以把它想成对跨项目通讯录做版本核对。Java 保存二十、二十二、二十三条历史地址清单，并证明旧版
清单未被后来地址污染；它没有拨号，也没有进入对方系统。真正联合验证必须在独立环境门下启动真实
jar 和真实 CLI，保存新鲜输出并再次证明无写边界，不能用本版的静态目录替代。

## 阻断与安全边界

第一，禁止写。所有 Service 使用只读事务和不可变列表，没有 repository save、文件写入、消息发布
或审批账本。第二，禁止执行。控制器无执行参数，服务不启动进程、不部署、不回滚、不执行 SQL。
第三，禁止秘密和任意网络。固定 endpoint 是目录值，不是动态连接目标；代码不读取 credential，
不解析调用方 URL，不创建托管审计连接。

第四，禁止历史回填。v175 必须始终二十，v179 必须始终二十二，v184 必须始终二十三；未来增长只能
新增快照版本，不能改旧列表。第五，禁止扩大生产可见性。Snapshot 和 EndpointPair 保持 package-private，
只有测试源集 Support 能向历史测试转发不可变列表。第六，禁止用测试适配掩盖行为变化。Support 只
转发真实 Snapshot 和真实构造器，不复制 fixture 字节，不制造假的 passed。

明确失败条件包括：目标文件不是十一、控制器离根、Snapshot 变 public、历史测试直接访问内部类、
v184 继续借根私有 pair、路由或归档路径变化、Response 字段变化、根包高于四百七十一、桶或未分类
非零、总文件数增长、讲解不足三千汉字，或 focused、Spotless、JaCoCo、SpotBugs、完整 verify 任一
失败。任何失败都回到实现修复，不能降低阈值或删除断言。

## 测试覆盖

Focused 集覆盖四个 Service、三份 Snapshot、留根控制器拆分、历史 Endpoint Snapshot 兼容、五组
V1Contract 历史兼容、v1851/v1852 结构守卫、v1828 census、三处全局活棘轮和讲解合规。它同时验证
业务输出、历史数量、跨包 TestSupport 和 FQN 迁移，而不是只检查新目录存在。

Spotless 会统一新包、二十个跨包消费者、根核心常量和测试 support 的格式。完整 `mvnw verify` 再
运行全部历史行为与 Spring 集成测试，构建 jar，执行 JaCoCo、SpotBugs 和所有架构棘轮。版本文档与
本讲解在完整门之前写入，最终测试数量、耗时和静态扫描结果只能在真实成功后补录。

输入证据包括 v1851 双提交/tag、唯一远端、两条进行中的 CI、十一文件清单、七测试清单、Snapshot
调用普查、路径测量和迁移前 482/377 普查。输出证据将包括编译、focused、Spotless、完整 verify、
471/366 普查、实现提交、关账提交、注解 tag、两条 v1852 CI 与五版检查点账本。检查点会统一等待
v1848 至 v1852 的所有远端门并再提交最终状态。

审查者可以按四步复现。先跑 census，核对 471、366、零桶和零未分类；再检查十一文件和三份私有
Snapshot；然后运行 focused，确认二十、二十二、二十三历史列表及所有安全否定字段；最后运行完整
verify 并读取 JaCoCo、SpotBugs 和 Maven 退出码。任何一步无法复现，都不能用进度表中的“passed”
代替机械证据。

## 一句话总结

v1852 把只读目录、交接、验证和完整性四层迁成独立模块，以测试支持保护历史快照私有性，以包内
EndpointPair 消除根私有类型耦合，保持路由、响应和归档字节不变，并把根包降到四百七十一，为后续
V1Contract 消费链提供清晰稳定的只读上游。
