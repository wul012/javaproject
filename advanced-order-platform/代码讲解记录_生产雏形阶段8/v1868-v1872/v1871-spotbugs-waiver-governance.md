# Java v1871：SpotBugs 豁免从“数数量”升级为“验身份”

## 入口路由

这一版没有新增 HTTP 接口，也没有改变订单、库存、支付或失败事件重放的运行路径。它治理的是
构建链里一个很容易被忽略的入口：`mvnw verify` 执行测试阶段时，
`SpotBugsWaiverTests` 会读取 `config/spotbugs-exclude.xml`，把每个 `Match` 解析成可比较的
豁免身份。所谓身份不是“这是第几条”，而是 SpotBugs pattern 与 Java class FQN 的组合，
例如 `EI_EXPOSE_REP + com.codexdemo.orderplatform.common.PagedResponse`。只有这两个维度同时
相同，才算同一笔历史债务。

旧入口位于 `JavaEleganceGateTests.spotbugsBaselineOnlyShrinks`。它把 XML 当普通字符串，计算
`<Match>` 出现次数，只要结果不大于 686 就放行。这个检查确实能挡住直接增加第 687 条，
却看不见“删除一条旧记录，再加入一条新记录”的等量替换。数量仍是 686，债务身份已经换了，
构建却会误判为没有增长。v1871 因而没有简单地把 686 改成 682，而是更换判断模型：入口仍是
Maven 测试，约束对象从一个整数升级为完整集合。

开发者不需要记一条额外命令。聚焦排查可以运行
`mvnw -B -Dtest=SpotBugsWaiverTests test`，正常交付仍运行完整 `mvnw -B verify`。这意味着
规则既便于本地快速定位，也必然进入 canonical CI，不依赖某个人记得手工执行脚本。本项目
没有为治理门引入运行时 Bean、Controller 或生产配置，质量入口与业务入口保持物理分离。

## 响应模型

测试内部的 `Waiver` record 是这一版最小但关键的模型。它只有 `pattern` 与 `className` 两个
字段，没有状态、版本号或人为解释。选择 record 是因为身份对象需要稳定的值相等语义：
两个字段相同，集合就认为它们是同一条；任一字段变化，便是一个新身份。由编译器生成的
`equals` 与 `hashCode` 比拼接长字符串更清楚，也避免分隔符转义等额外问题。

这里没有新建第二份 682 行手工基线。若把 XML 复制成另一个 baseline 文件，维护者完全可以
在新增豁免时同时修改 XML 和 baseline；两份文件彼此吻合，却无法证明债务没有扩大。v1871
直接调用 `GitChangeSet.priorFile(FILTER)` 读取 Git 中的上一版 XML。当前集合是“响应”，上一版
集合是“准入边界”，二者来自同一个规范文件的不同时点，无法在一次提交里同步篡改历史。

集合比较也特意计算 `added = after - before`，而不是让 AssertJ 打印两个完整大集合。第一次
负向验证证明等量偷换会失败，但原始 `isSubsetOf` 诊断展开了数百条长 FQN，真正新增的一条反而
淹没在日志中。现在失败消息只展示新增身份。约束强度没有降低，CI 可读性却显著提高：审阅者
直接看到哪一种 pattern 被加到了哪个 class，不必在上万字符输出中寻找差异。

## 上游证据配置

`spotbugs-exclude.xml` 来自 J1 的首次生产卓越扫描。当时 2,602 个 BugInstance 被折叠为 686 个
class-pattern 匹配项。v1871 实施前重新用 XML DOM 盘点，得到 686 个 Match、686 个唯一身份，
说明当前没有重复项。pattern 分布是：`EI_EXPOSE_REP2` 340 条、`EI_EXPOSE_REP` 331 条、
`CT_CONSTRUCTOR_THROW` 8 条、`DM_CONVERT_CASE` 5 条，另外两个单项分别是
`VA_FORMAT_STRING_USES_NEWLINE` 与 `URF_UNREAD_FIELD`。这些数字用于解释现状，不作为可放宽的
新目标。

上游还有 Git 工作区状态。`GitChangeSet.priorFile` 已在 v1869 的精确长名基线中验证过两种
场景：开发中若文件有未提交改动，它读取 `HEAD` 的版本；提交后工作区干净时，它读取
`HEAD^`。因此同一测试既能在编码阶段比较“当前文件与当前提交”，也能在 CI 比较“本次提交与
父提交”。它避免了只在脏树有效、提交后失明的常见门禁缺陷。

重新对照 `target/classes` 后还发现四条豁免已经没有对应字节码，均指向旧的
`OpsShardReadinessCodeWalkthroughComplianceRegistryResponse` 及其 `MarkdownSection`，pattern
分别为 `EI_EXPOSE_REP` 和 `EI_EXPOSE_REP2`。它们不是待修缺陷，而是类早已删除、XML 没同步
清理的陈旧债务。v1871 精确删除这四个 Match，使集合从 686 收紧到 682；没有把别的条目改名
来伪造减少，也没有重写任何历史 fixture。

## 服务层核心流程

虽然它是测试代码，内部仍按清晰流水线组织。第一步由 `secureFactory` 创建 JAXP
`DocumentBuilderFactory`。第二步 `readWaivers` 用 DOM 解析 XML，并检查根节点必须是
`FindBugsFilter`。第三步 `directElements` 只收集直接子元素，避免深层搜索把嵌套或错位节点
误当合法结构。第四步要求根下每个元素都是 `Match`，每个 Match 的直接元素严格按
`Bug`、`Class` 顺序出现。第五步 `requiredAttr` 验证两个元素都恰好只有一个属性，且
`pattern`、`name` 值非空。最后构造 `Waiver` 并加入 `LinkedHashSet`，重复身份会在加入时立即
失败。

这条流程故意不使用正则解析 XML。正则可以数标签，却不能可靠表达注释、空白、属性、节点
层级与实体展开规则；字符串包含检查还可能把注释里的示例误算成真实豁免。DOM 在这里不是
为了做复杂抽象，而是让输入语法由成熟解析器负责，让测试只声明本项目接受的结构。文件只有
一种合法形状，审阅和错误定位都更直接。

身份比较流程随后读取两个时点。`before` 来自 Git，`after` 来自工作区。代码复制当前集合，
移除所有历史身份，剩下的就是新增差集。差集必须为空，因此允许删除任意旧项，也允许排序和
空白调整，却拒绝新增 pattern、把同一 class 换成另一 pattern、或把同一 pattern 转给另一
class。它约束的是语义身份，不把无意义的 XML 格式变化当成债务变化。

## Java 证据检查

第一层证据是结构完整性。`filterIsStrictAndUnique` 调用同一个解析器，要求集合非空且不超过
当前收紧后的 682。真正的唯一性由 `LinkedHashSet.add` 的返回值保证；若复制了一个 Match，
即使数量上限尚未越界，也会以 `duplicate waiver` 失败。严格的 `Bug + Class` 子节点检查还会
拒绝缺失 Bug、缺失 Class、额外节点、空属性与多余属性，避免测试读到的集合与 SpotBugs 实际
解释的配置发生偏差。

第二层证据是历史单调性。`waiversOnlyShrinkByIdentity` 不比较总数，而比较新增差集。实施过程
做过真实负向试验：把 `PagedResponse` 的一个 `EI_EXPOSE_REP` Match 临时等量替换为
`BusinessException`。XML 仍有 682 条，新 class 也真实存在，旧数量门会放行；新门则构建失败，
只报告 `BusinessException` 是新增身份。试验后用补丁恢复原 XML，没有修改测试期望来迎合
输入，证明失败条件确实可触发。

第三层证据是 class 可达性。`waiversReferenceClasses` 遍历全部身份，通过上下文 ClassLoader
调用 `Class.forName(name, false, loader)`。第二个参数为 false，表示只加载类型元数据而不执行
静态初始化；这样能验证 FQN 与编译产物一致，又不会启动 Spring、连接数据库或触发业务副作用。
外部类和使用 `$` 表示的嵌套类都按 JVM 的真实命名检查。四条陈旧记录正是在这个机械边界下
被确认并删除，之后 682 条全部可加载。

## mini-kv 证据检查

v1871 不读取、不生成也不改写 mini-kv 仓库内容。Java 与 mini-kv 的联动仍停留在已经验证的
只读跨项目契约，SpotBugs 豁免属于 Java 构建内部治理，不是 evidence schema、路由或 fixture
格式。因此这项工作可独立推进，不要求 C++ 项目同步发版，也不会改变 Node 对 Java/mini-kv
冻结证据路径的引用。

讲解保留本节，是为了把“没有改 mini-kv”说成可审阅边界，而不是含糊地宣称跨项目都通过。
本版的机械证据只覆盖 Java XML、Git 历史和 Java 编译 class。它没有启动 `minikv_cli`，没有
重跑跨项目 capstone，也没有获得写路由或执行权限。已有联合测试结论继续有效，但不能拿来替代
本版自己的测试，也不能把 Java 静态治理包装成新的系统能力。

这也说明输入输出的层次：mini-kv 没有为本版提供新输入，Java 没有向 mini-kv 产生新输出；
唯一跨边界承诺是“不破坏既有冻结路径和只读契约”。若未来 SpotBugs 修复需要改共享响应 schema，
那将不再是内部优化，必须回到四项目依赖顺序重新评估。本版删除的四条只指向 Java 已不存在的
类型，不触及这一条件。

## 阻断与安全边界

XML 解析器显式禁止 DTD，并关闭外部通用实体、外部参数实体、外部 DTD 与外部 schema 访问；
同时关闭 XInclude 和实体展开。原因不是当前仓库有人提交恶意 XML，而是测试门会在开发机和 CI
自动处理工作区文件。若解析器保留外部实体能力，一段被误提交的 DOCTYPE 可能尝试读取本机文件
或访问网络，质量检查反而变成不必要的输入通道。`rejectsDocumentTypes` 使用带外部实体声明的
最小文档，确认解析在接触实体内容前失败。

解析器还安装了 `DefaultHandler`。它不吞掉异常，`parse` 仍把 `SAXException` 交给测试断言；
它只阻止底层解析器把预期的 fatal error 额外打印到标准错误。没有这个处理器时，测试虽然绿色，
日志末尾却出现一行醒目的“Fatal Error”，容易让人误判构建状态。安全边界和可观察性在这里同时
成立：危险输入被拒绝，正常 CI 输出保持安静，真正未被断言的异常仍会令测试失败。

本版禁止用三种方式绕过：不能提高 682 上限，不能创建一份可与 XML 同步修改的影子基线，也
不能给陈旧或新发现加 `SuppressFBWarnings` 来把债务从 XML 搬进源码。SpotBugs 的 effort、
threshold 和扫描范围保持原样。生产源码为零改动，运行时权限边界、HTTP 行为、数据库事务和
消息投递均不受影响。

## 测试覆盖

正向聚焦运行包含 `SpotBugsWaiverTests`、`JavaEleganceGateTests` 与
`JavaChangeGateTests`。它同时覆盖新身份门、原有精确长名门、路由别名读者门、变更文件短名门、
生产源码增长门和 family design 门。首轮 11 项测试全部通过：682 条豁免结构合法、唯一、属于
Git 前版集合并能加载 class；新增测试文件及标识符也满足 40 字符预算。

负向测试不是伪造一段脱离仓库的字符串，而是临时改动真实 XML 中一个合法 Match。替换前后
条数相同，被替换的新 class 也存在，所以失败只能来自“身份不是历史子集”这一核心条件。Maven
按预期返回非零，Surefire 指向 `waiversOnlyShrinkByIdentity`，差集包含唯一新增项。恢复后还要
再次运行正向门，确保试验没有残留。

最终验证还包括 Spotless、全部 1,900 余项回归、JaCoCo 阈值、SpotBugs 零未豁免发现、可执行
jar 和远端双 job CI。中文讲解在完整 verify 之前写入，并由当前讲解门验证至少 3,000 汉字与
恰好十个标准二级标题。归档清单只允许本版这一篇新增文件，文件数、原始字节数和 SHA-256 都要
机械对齐；禁止硬凑段落或通过放宽归档上限隐藏额外文件。

## 实际工作量说明

这不是把常量 686 改成 682 的小修。前置盘点先结构化读取 686 个 Match，确认唯一身份数量、
六类 pattern 分布、353 个唯一 class 名以及外部类/嵌套类构成；随后用 Windows 长路径安全方式
对照编译产物，排除了“源码路径过长导致 Test-Path 假阴性”，最终只认定四条真实陈旧记录。
这个区分很重要：若把长路径读取失败误当 class 删除，会错误移除仍需保留的豁免，让后续
SpotBugs 结果突然爆发。

设计阶段比较了两条路线。复制一份精确 baseline 文件实现直观，却产生 682 行重复数据和同步
篡改入口；复用 Git 前版 XML 则用已有 `GitChangeSet` 获得不可同提交改写的历史状态，代码更少、
证据更强。实现又从简单字符串计数升级为安全 DOM、严格直接子节点、属性数量检查、重复检测、
新增差集和 class 加载五层边界。负向试验暴露日志过长后，没有接受噪音，而是调整为只输出差集；
DTD 用例产生假红日志后，也增加静默 ErrorHandler 保持信号质量。

最终改动集中在一个短名测试类、一份 XML、现有优雅测试中的旧门删除、任务证据页和本篇讲解。
没有新增生产代码，没有第三个相似类，没有巨型辅助框架，也没有为了凑足说明篇幅重复结论。
篇幅来自对输入、转换、输出、失败路径、Git 时态、安全解析和跨项目边界的逐层解释，这才符合
“有内容才写、字数不够就加大真实工作量”的规则。

## 一句话总结

Java v1871 把 SpotBugs 历史债务从“总数看起来没增加”升级为“每个 pattern 与 class 身份都必须
来自 Git 上一版且仍指向真实 class”，并用安全 XML 解析、四条陈旧豁免清理、等量偷换负向试验
和简洁差集诊断证明这条门真的会在回归时失败；运行时行为不变，但以后任何新增、替换、重复、
失效或结构异常的豁免都无法安静混入主干。
