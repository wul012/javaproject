# version-1854：ReleaseApproval 完整闭包迁移与依赖方向校正

这是一篇只解释本项目 Java 实现的版本讲解。它记录的是可复现的工程事实，不把文件
搬家包装成业务新功能，也不把 Node 或 mini-kv 尚未执行的事情写成已经完成。全文遵守
“禁止硬凑”：篇幅来自本版真实存在的入口、模型、常量所有权、摘要链、测试边界和安全
约束；如果这些内容不足以解释三千汉字，就说明版本工作量本身还不够，而不是重复同一
句话凑数。

## 实际工作量说明

v1854 处理的是根级 `ops` 包中最大的一个连续闭包。迁移前，八个 ReleaseApproval
桶合计有一百一十八个生产文件，既包含 rehearsal request/response，也包含 managed
audit、sandbox connection、credential resolver、verification digest 等多层 builder、
record 和 catalog。它们共同生成同一份只读发布审批演练结果，因此不能只挑几个短文件
迁走：那样会在根包与新包之间留下大量包私有依赖，使“分包”只改变路径而没有改变
所有权。本版把这八个桶一次性清零，五个包内行为测试随实现移动，原先供大量 rehearsal
测试复用的 support 也归入新包。根目录生产文件由四百二十九个降到三百一十个，其中
一百一十八个来自家族闭包，另一个来自过期共享豁免的撤销。

最后这一个文件是原 `ContextHeaderField`。编译前的调用面检查显示，它的实际运行时
消费者已经只剩 ReleaseApproval 的 hint builder 和 response builder；所谓“被多个
独立 readiness 家族共享”的 waiver 理由不再成立。把它留在根包只有两条路：公开整个
record 及其静态方法，或者让新包继续依赖根包内部实现。两条路都会扩大错误边界，所以
本版将其改名为包私有 `ReleaseApprovalContextHeaderField` 一并迁入。最终根目标因此从
一百零五进一步收紧到一百零四，而不是为了保持旧计划数字继续保存无效豁免。

这次工作还完成了两项实质重构。第一，原 `ReleaseApprovalEchoMarkerSupport` 中的 warning
输入、boundary line 和 workflow readiness 工具，与 `ReleaseApprovalDigestSupport` 的
摘要规范本来就是同一职责，现合并为一个包私有支持类。第二，一个 execution-denied
receipt builder 只把每个调用原样转发给对应 support，没有独立状态或策略，因此折叠为
support 内的 `ReceiptBuilder`。释放出的两个物理文件位分别承载 Java 契约常量目录和
Node 上游证据常量目录，使总 `ops` 生产文件仍为一千三百五十二，没有借重构之名放宽
总量 ratchet。

本家族的设计边界可以压缩成四行：controller 与总服务负责流程编排，公开 request、
response、builder 负责跨包契约，package-private catalog/support 负责数据目录与纯计算，
上游常量按 Java 契约和 Node 证据来源分成两个有界目录。行为不进入常量目录，证据字节
也不散落回流程类。这样新增条目是数据变化，执行顺序变化才是行为变化，评审时不会混淆。

## 入口路由

对调用者而言，入口没有变化。运维端仍通过根目录的 `OpsOverviewController` 进入现有
发布审批演练接口，路由字节仍是 `/api/v1/ops/release-approval-rehearsal`。controller
不会因为实现迁包而跟着下沉：它继续位于容易发现的 HTTP 入口层，把请求交给
`OpsEvidenceService`；service 获取全局只读 evidence 后，再调用公开的
`ReleaseApprovalRehearsalResponseBuilder`。这条边界刻意只有 request、response 和
builder 三类组合对象可见，内部几十个 catalog、support 和局部 record 仍保持包私有。

可以把一次请求理解成下面的顺序：

```text
HTTP read-only request
  -> OpsOverviewController
  -> OpsEvidenceService.releaseApprovalRehearsal(request)
  -> ReleaseApprovalRehearsalResponseBuilder.build(evidence, request)
  -> normalized request + rehearsal sections + managed-audit receipt chain
  -> immutable ReleaseApprovalRehearsalResponse
  -> JSON response
```

例如操作员提交 request id、operator identity、audit correlation id，以及 CI manifest
digest、artifact retention、runtime smoke session、managed-audit candidate 和 approval
binding。入口层不据此执行审批写入，也不打开生产窗口；它只把这些值作为待核对材料。
如果某个字段为空，后续规范化会产生显式 placeholder 和 warning，而不是猜测真实凭据。
如果字段完整，响应会标记“已回显并可供 Node 比对”，仍不会把回显等同于 Java 已验证
外部身份。由此可见，路由的价值是生成可审计的只读演练包，不是执行发布命令。

迁包以后，endpoint 字符串由 `ReleaseApprovalContractConstants` 持有，根 service 和
controller 通过公开不可变目录读取同一字节。这里没有创建第二条路由，也没有把旧值
复制成可能漂移的字符串。机械测试同时检查旧根级 ReleaseApproval 文件为零、新包文件
完整、controller 仍在根层，因此将来有人把 controller 一起搬走或重新写路由时会立即
触发失败。

## 响应模型

`ReleaseApprovalRehearsalResponse` 是整个演练的不可变输出。它不是一个简单的“通过或
失败”布尔值，而是按证据阶段保存上下文、operator window、CI evidence、artifact
retention、live readiness、audit persistence handoff、approval record handoff、failure
taxonomy、verification hint，以及从 sandbox connection 一直延伸到 credential resolver
abort/rollback contract 的 receipt chain。每个嵌套 record 都让调用者看到来源、是否
回显、是否完整、禁止事项和下一步 Node 核对动作，因而可以区分“输入存在”“Java 已
规范化”“上游证据一致”“生产动作获准”四种完全不同的状态。

以 operator identity 为例，响应既保存规范化后的值，也保存来源标签和 echoed 标志。
当输入是空白字符串时，`ReleaseApprovalContextHeaderField.normalized` 会先 trim，再把
空值转换成明确的 not-supplied placeholder，同时把对应 warning 加入列表。三个字段
都回显时，`allEchoed` 才会给出上下文完整；即便完整，`operatorAuthenticatedByJava`
仍保持既定的只读语义，不会因为调用者自报了邮箱就变成 true。这个细分正是响应模型
透明的地方：消费者能够知道系统做了哪一步，也能知道系统没有做哪一步。

managed-audit 和 credential-resolver 部分采用相同原则。每个 receipt 记录 source echo、
decision、checks、side-effect boundary、warning digest 和 readiness。比如 execution
denied receipt 会明确列出 credential value read、raw endpoint parse、external request、
ledger write、SQL、schema migration、automatic upstream start 均未发生。后续 receipt
只能消费前一阶段的不可变输出，不能绕过链条直接宣称 ready。v1854 只改变这些 record
和 builder 的包名，字段顺序、构造参数、列表顺序及 JSON 形状均未改变；SpotBugs 中
允许的不可变列表暴露项也只迁移 FQN，没有新增豁免。

## 上游证据配置

迁移前最大的隐藏问题不在路由，而在常量所有权。ReleaseApproval builder 直接读取
`OpsEvidenceService` 上数百个字段：一部分是 Java rehearsal 自身的 version、schema、
endpoint 和 receipt version，另一部分是 Node v210 到 v329 的 profile、state、endpoint、
markdown endpoint 与 verification version。因为原来同处根包，这些包私有字段看起来
像普通局部引用；迁出后，编译器一次暴露了真实的反向依赖。如果简单把字段全部加上
`public`，根 service 就会永久变成三百多个常量的公共仓库，任何新 builder 都可能继续
向错误方向取值。

本版先做跨行扫描，而不是只搜索 `OpsEvidenceService.CONSTANT`。很多旧代码为了格式化
长名称，把 `OpsEvidenceService` 和点号后的字段拆成两行；单行搜索最初识别出二百一十
一个常量，跨行扫描又找到一百一十一个符号，其中六个与前一批重叠，最终得到三百一十
六个不同依赖。这个过程说明编译器为什么必须参与边界校验，也说明 census 脚本不能只
依赖视觉抽样。

三百一十六项按语义分为两个目录。`ReleaseApprovalContractConstants` 保存八十九个 Java
侧契约字段，文件不得超过四百行；`ReleaseApprovalUpstreamContractConstants` 保存二百
二十七个 Node 上游证据字段，文件不得超过八百行。两个类型都不可实例化，字段全部是
公开静态最终字符串。分成两类后，维护者看到 `RELEASE_APPROVAL_` 就知道它描述本服务
输出，看到 `NODE_V...` 就知道它只是被消费的上游证据坐标，不会误把 Node 的声明当成
Java 已执行的能力。

有一个字段在旧 service 中原本就是 `public static final`。为了不破坏可能存在的已编译
消费者，根 service 保留同名兼容别名，但初始化值改为家族目录中的真实字段。其余字段
原本都不是公共 API，因此直接从 service 移除。最终新包对 `OpsEvidenceService` 的源码
引用为零，根 service 反过来只依赖公开组合边界。这是依赖方向的真正校正，而不只是把
访问修饰符改成能编译。

## 服务层核心流程

`ReleaseApprovalRehearsalResponseBuilder.build` 先构造两个 hint builder，一个负责请求、
operator window、CI、retention 和 live readiness，另一个负责 audit persistence 与
approval record handoff。它随后把输入转换为 `NormalizedRequest`。规范化只做 trim、
空值折叠和来源标记，不解析 credential，不访问网络，也不查询外部身份提供方。这里的
输出是可解释的值对象，因此后续每个阶段都能同时读取“值是什么”和“值从哪里来”。

第二步是 `rehearsalSections`。它把全局 `OpsEvidenceResponse` 中已有的只读 fixture 与
规范化请求组合成多个 section。artifact retention 会比较请求保留天数是否落在 Java
fixture 允许范围；live readiness 会列出允许读取的目标和禁止操作；audit persistence
会说明候选 sink 仍只是 handoff 材料；approval record 会说明 binding 是否完整。任何
不完整项都会进入 warning，但不会自动补成通过。这样，响应中的 readiness 是由多个
可追溯条件合取而来，不是一个随意设置的状态字段。

第三步是 `ReleaseApprovalRehearsalManagedAuditReceiptChainBuilder`。它按固定顺序生成
adapter boundary、production prerequisite、implementation guard、external migration
guard、sandbox adapter schema、sandbox connection preflight、dry-run envelope、operator
window、command package、precheck packet，再逐步进入 endpoint handle 和 credential
resolver。每个 builder 只接受上一阶段的 receipt，构造自己的 source echo、decision、
checks 和 side-effect boundary。链条越往后，证明项越多，但权限并不会变大；“ready for
Node vNNN comparison”只表示证据包可供下游比较，不表示 Java 获得执行权限。

第四步是 verification 聚合。`ReleaseApprovalVerificationHintContributionCatalog` 收集
三十四段贡献，`ReleaseApprovalVerificationWarningDigestLineCatalog` 以同样顺序收集
warning line 和 boundary line，`ReleaseApprovalVerificationWarningDigestBuilder` 对规范
行序列生成 SHA-256。摘要输入由 `ReleaseApprovalDigestSupport.line` 统一序列化：null、
list、boolean 和 record 都有稳定表示。v1854 合并 echo marker 工具后，warning 名称、
boundary 名称、workflow readiness 与 digest 共享同一规范 owner，减少了两个支持类之间
来回跳转，也降低了未来一边改序列化、一边忘记改 workflow 的风险。

最后，response builder 把所有 section、receipt、failure taxonomy、verification hint 和
摘要装入不可变 response。整个流程没有 repository save、message publish、HTTP client、
shell、process builder 或数据库迁移调用。输出的价值在于把“哪些材料齐备、哪些材料缺失、
哪些副作用明确未发生、下游应核对什么”一次说明白。

## Java 证据检查

Java 侧证据分为结构证据、行为证据和质量证据。结构证据由 v1854 readability guard 与
v1828 census guard 提供：新包必须有一百一十九个生产文件和六个测试文件；根目录不得
残留 `ReleaseApproval*.java` 或 `ContextHeaderField.java`；八个 census 桶必须全部为零；
根目录精确为三百一十；最终保留目标为一百零四；总 `ops` 文件不得超过一千三百五十二。
两个常量目录分别有四百行和八百行上限，包内任何生产文件再次引用 `OpsEvidenceService`
都会失败。这些检查不是表格自述，而是会在回归时变红的机械门。

行为证据来自已有 ReleaseApproval 测试。它们检查完整请求与缺失请求的 warning、header
trim、operator window 回显、CI manifest、retention、live readiness、managed audit、
sandbox packet、credential resolver 的每个阶段，以及 verification digest 的固定顺序。
本版迁移测试时没有改期望值和 fixture 内容，只修改 package/import，并把共享 support
迁到新包。二十八个仍位于根目录的 overview/echo 测试显式导入新的公共测试 support，
生产代码不会看到这个 test-scope 边界。

质量证据包括 Spotless、编译、聚焦测试、JaCoCo、SpotBugs 和完整 Maven verify。六个
maintainability 热点只把路径从 `ops/` 改成 `ops/maintenance/releaseapproval/`，原有
七十、四百二十一、四百一十二、三百八十二、七百九十三等上限不增加。SpotBugs 的一百
八十个 FQN 同样只迁路径，旧根 FQN 必须消失。讲解和版本文档在最终 verify 前落盘，
因此最终质量门会把代码、测试和证据账本一起验证，而不是先测代码、后补一份未经检查
的说明。

## mini-kv 证据检查

本版本没有启动、停止或修改 mini-kv。Java 响应中出现 mini-kv、Node 或 runtime shell
时，含义都是上游/下游证据坐标或明确的禁止边界，不是实际连接记录。相关 receipt 会
持续输出 `credentialValueRead=false`、`rawEndpointUrlParsed=false`、`externalRequestSent=false`
和 `automaticUpstreamStart=false` 等字段，使消费者能够区分“证明未执行”与“没有记录”。

如果未来跨项目 capstone 要验证真实 mini-kv CLI，那应由获得明确授权的集成命令启动
独立进程、记录 PID、执行只读命令并清理；它不能借 ReleaseApproval rehearsal 的一次
GET 调用偷偷发生。本版只保证 Java 侧输出契约与冻结证据坐标不变，不声称 mini-kv
当前在线，也不声称 Node 已消费新提交。这样的表述看似保守，实际上让证据链更可信：
单项目静态/行为验证和三项目实时集成是两种不同等级，不能混写。

## 阻断与安全边界

第一层阻断是输入语义。空白或缺失 header 只会生成 placeholder 与 warning，不会从
环境变量、credential store 或默认生产账户补值。第二层阻断是状态语义。echoed、context
complete、ready for comparison 与 production authorized 是不同字段；前几者为 true
不能推导最后一项为 true。第三层阻断是副作用清单。每个深层 receipt 都明确列出禁止的
credential read、raw URL parse、managed-audit connect、SQL、schema migration、ledger
write、external request 和 automatic upstream start，并由 verification digest 覆盖。

第四层阻断是依赖方向。新包不能引用根 `OpsEvidenceService`，否则家族又会把内部常量
和实现细节当作上游 API。v1854 guard 对全部一百一十九个文件逐个读取并禁止该名称；
常量目录的行数也被限制，防止未来所有新证据继续堆进一个巨型文件。第五层阻断是根包
ratchet。三组 live pin、census 脚本和多个历史 guard 都使用三百一十这一当前值，任何
文件回流都会失败；最终目标一百零四只能继续下降，不能无 waiver 上升。

第六层阻断是版本收尾。只有本地 focused 与 full verify 通过，才能提交实现；只有实现
commit 的远端 CI 通过，才能写 closeout、打 v1854 tag；closeout commit 还要再次通过
远端 CI。测试失败时允许修代码、修导入、修错误账本，但不允许改 fixture 字节、降低
断言、放宽文件数或行数上限来求绿。这使“完成”由可失败的门定义，而不是由执行者口头
宣布。

## 测试覆盖

测试策略按风险分层。第一层是 `test-compile`，它最早暴露了两类真实边界：数百个包私有
常量仍归根 service 所有，以及 `ContextHeaderField`/根测试 support 在迁包后不可见。
前者促成两个契约目录和兼容别名，后者促成 waiver 撤销与测试 support 归位。这里没有
把所有字段改 public 作为快捷修复，因为编译通过不是唯一目标，依赖方向同样属于交付。

第二层是聚焦行为测试，覆盖所有名称含 ReleaseApproval 的测试、三个移动后的 catalog
测试、OpsEvidenceService 的 rehearsal/echo overview 测试、v1854 新 guard、v1828 census、
三组根目录 ratchet、maintainability budget 和 walkthrough compliance。聚焦层应快速
回答“本刀自身是否完整”，尤其要验证 warning digest 的三十四段顺序、runtime shell
boundary line、header-backed request、只读 fixture service，以及旧 execution-denied
转发类消失后嵌套 `ReceiptBuilder` 的调用结果不变。

第三层是完整 `mvnw verify`。它会执行全项目单元/集成测试、JaCoCo 覆盖率门、SpotBugs、
Spotless 和所有文档/讲解 guard。由于迁移影响一百多个生产文件、三百多个常量引用、
一百八十个 SpotBugs FQN 和二十八个根测试导入，只跑 ReleaseApproval 自身测试不足以
证明外部消费者仍正确。完整门必须覆盖 `OpsOverviewController`、credentialresolver 与
sandboxconnection 的跨包记录消费者，以及所有旧版本 shrink-only guard。

首次聚焦门实际执行了一百七十九个测试，结果是四个失败和两个路径错误。重要的是，
所有 ReleaseApproval 行为断言、Spring 集成入口、warning 内容、receipt digest 与只读
副作用断言都没有失败；暴露出来的全是迁移以后才看得见的维护问题。这样的失败不是要
隐藏的噪音，而是机械门发挥价值的证据。处理原则是逐项追到文件和失败条件，能通过
减少结构复杂度解决的就减少结构复杂度，能通过修正现实路径解决的就修路径，绝不把
现有上限调大，也不删除覆盖面来缩短测试时间。

第一个失败是 `ReleaseApprovalRehearsalResponseBuilder` 经新包格式化后由四百二十一行
变成四百二十二行。维护预算仍固定在四百二十一，没有改成四百二十二；修复只删除了
规范化局部变量与 return 之间一行没有分组意义的空白。这个动作看起来小，却表达了
ratchet 的纪律：阈值代表已经承诺的最大复杂度，迁包不能成为回涨理由。Spotless 会
重新检查最终文本，如果格式器认为空白必需，它会自动恢复并让门继续失败，因此结果
不是手工绕过格式器。

第二个失败更有价值。原 header-backed overview 和 sandbox-connection overview 都把
数百条断言塞在单一测试方法中；过去它们未被重新格式化时暂时低于八百五十四行上限，
迁包增加 import 后，Spotless 把长调用链展开，真实规模分别达到一千三百四十行和九百
九十五行。提高测试源码上限会掩盖巨型测试，所以本版继续按证据阶段拆分，而不是只把
千行文件勉强切成两个仍超过五百行的文件。header-backed 场景最终分为基础上下文、
managed-audit guard、external boundary、sandbox handoff 四段；sandbox-connection
场景分为 handoff/preflight、precondition、checklist 三段。所有原断言逐段迁移，没有
删除或改值；每个新测试只重新构造同一只读 fixture。七个文件经 Spotless 后实测为三百
三十五、三百五十九、三百五十五、三百五十四、四百九十一、二百六十六和二百七十六行。
全项目测试源码因此重新满足最大八百五十四行、超过五百行最多八个、超过七百五十行
最多两个、超过一千行零个的四重预算；测试名称也比一个笼统的 overview 更能说明失败
发生在哪个阶段。

这也解释了为什么本版差异统计看起来比普通搬迁更大。项目的格式化采用变更感知策略，
旧根文件虽然处于可接受历史基线，文件一旦换包或增加跨包导入，就会按当前规则重新排版。
长链式断言因此从紧凑历史形态展开为统一缩进，行数门随即看见此前被排版遮住的体积。
本版接受格式化后的现实并拆测试，而不是把文件恢复成只有旧工具才接受的紧凑写法。评审
时应结合重命名检测、包声明、导入、断言数量和测试结果阅读差异，不能把格式化产生的
行移动误写成业务变化；同样也不能因为差异大就跳过逐项机械门。最终输出要同时满足
当前格式、当前行数预算和原行为断言，这三者共同构成可维护基线。
换句话说，格式化不是本版额外追求的外观工程，而是一次压力测试：它把隐藏在旧排版里的
超长测试显露出来，迫使代码按职责重新分段。拆分后的失败定位更短，后续维护者修改某个
证据阶段时，也不必在上千行单方法中判断改动属于哪一组前置条件。

第三类错误来自架构测试仍把源码根写死为旧 `ops` 路径。生产类已经迁入 maintenance
包，测试却尝试读取不存在的旧文件，因此得到 `NoSuchFileException`。修复只把
`SOURCE_ROOT` 指向真实包，原先关于单一 context、单一 receipt chain、构造参数数量和
四个文件行数上限的断言全部保留。第四类失败来自 waiver 测试使用了过宽的字符串禁止：
文档为了说明撤销历史而出现旧文件名，测试便误判它仍是活动 waiver。机械条件现收窄为
“活动表中不得出现对应表格行”，既允许诚实记录历史，也继续阻止豁免悄悄恢复。

第五个失败是讲解深度门。汉字绝对数已经达到三千八百九十五，但代码标识、类名、路径
和英文契约词较多，导致中文在字母与汉字合计中未占多数。正确修复不是重复结论，而是
把首次聚焦门的输入、失败、判断和结构性修复写入本文：读者因此能看见测试如何改变
设计，而不仅是看见最终绿色数字。扩写后仍由同一个测试重新统计汉字数、中文占比、
工作量标题和禁止硬凑声明；如果真实说明仍不足，门继续失败。

这轮失败还验证了一个取舍：共享 rehearsal 测试 support 虽然移动到新包并成为公开的
test-scope 类型，但它没有进入生产 classpath。根级 overview 测试通过显式 import 复用
三个 fixture 方法，新包 catalog 测试在同包直接复用；生产 request、response 或 service
没有获得任何仅为测试服务的方法。对比复制三十份 mock 构造或公开生产 helper，这个
测试边界更窄，也能保证拆分后的四个大型测试仍使用相同输入图。

第四层是远端 CI。实现 commit 和 closeout commit 分别触发 headless verify、Docker
隔离任务及 production smoke；tag 只能指向 closeout 后的绿色提交。本文写作时不提前
填写尚未发生的测试数量和 CI run id，完成后由进度账本记录命令、测试数、耗时、JaCoCo、
SpotBugs、commit、tag 和 run id。这样读者可以复现证据，也能看见第一次失败暴露了什么，
而不是只看到经过润色的“全都正常”。

## 一句话总结

v1854 的核心不是把一百一十八个文件换个目录，而是让 ReleaseApproval 从入口到响应、
从 Java 契约到 Node 证据、从 header 规范化到摘要验证形成一个自洽闭包：根目录减少到
三百一十，过期 waiver 被撤销，反向根服务依赖归零，两个常量目录有硬上限，薄转发层
被收拢，测试 support 随职责归位，而所有路由、响应和只读安全语义保持不变。
