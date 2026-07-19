# Java v1872：公开 DTO 的不可变集合边界

## 入口路由

v1872 没有新增 Controller 路由，也没有改变任何 URL、HTTP 方法或授权角色。它修复的是数据
穿过既有入口后的一处对象边界：Spring/Jackson 把请求 JSON 绑定为 `CreateOrderRequest`，服务层
把订单实体映射为 `OrderResponse`，查询服务把分页结果包装为 `PagedResponse`。这三个公开 record
都含有 `List` 组件。record 本身的字段是 final，却不等于字段指向的列表不可修改；若调用方把
可变 `ArrayList` 传给构造器，之后仍可通过原引用改变 DTO 看到的内容。

以创建订单为例，输入是客户编号和订单行列表。旧实现直接保存 `items` 引用。理论上，控制器把
请求交给服务后，其他持有同一列表的代码仍可清空或替换元素，幂等指纹与实际处理内容就可能读取
不同快照。当前 Web 流程没有主动这样做，但对象模型把这种能力暴露出来，SpotBugs 因而为
`CreateOrderRequest` 同时报告过 `EI_EXPOSE_REP2`（保存外部可变引用）和 `EI_EXPOSE_REP`
（访问器暴露内部引用）。另外两个 DTO 具有相同问题。

本版把入口定义为 record 的公开构造器，把出口定义为 record 的列表访问器。构造时建立快照，
阻断“外部原列表后来被修改”；读取时只给不可变列表，阻断“调用者通过访问器修改 DTO”。业务
路由仍按原顺序调用相同组件，JSON 字段名、组件顺序和响应结构都不变。变化只发生在 Java 对象
所有权上：DTO 从借用调用者的容器，变成拥有自己的只读快照。

## 响应模型

`PagedResponse<T>` 的 `content` 是泛型结果列表，`CreateOrderRequest.items` 是带元素级
`@NotNull @Valid` 的订单行列表，`OrderResponse.lines` 是对外订单行响应列表。三者继续保持
原来的 record 组件声明，因此编译器生成的构造签名、`equals`、`hashCode`、`toString` 与
Jackson record 绑定规则不需要重写。新增的是紧凑构造器和显式访问器，它们仍使用组件原名，
不会产生第二套字段。

共享抽象 `ImmutableLists` 只有一个公开行为 `copy`。输入为 null 时返回 null；输入存在时，先用
`new ArrayList<>(source)` 建立与调用方脱离的容器，再用 `Collections.unmodifiableList` 包装。
返回类型仍是 `List<T>`，上层不依赖某个实现类。类本身是 final、构造器私有，没有状态或 Spring
注解，所以它只是所有权转换函数，不会成为可注入服务或运行时配置点。

这里称为“不可变集合边界”，并不夸大为深度不可变。helper 固化的是列表结构：不能增加、删除、
替换或重排元素。它不会复制每个元素。当前三处元素要么是 String，要么是 record
`CreateOrderLineRequest`、`OrderLineResponse`，自身没有可变集合字段，浅快照已经覆盖真实风险。
若未来元素类型含有可变对象，必须单独设计元素复制策略，不能假设本 helper 自动解决深拷贝。

## 上游证据配置

上游静态证据来自 v1871 已建立的精确 SpotBugs 身份门。实施前，XML 中能精确找到六个组合：
三个 class 各有 `EI_EXPOSE_REP` 与 `EI_EXPOSE_REP2`。这些不是按总数猜测的条目，而是
`pattern + class FQN` 身份。v1871 规定当前集合只能是 Git 前一版集合的子集，因此 v1872 删除
六条可以通过，换入任何新豁免则会失败。

代码调用面也做了只读盘点。生产与测试源码没有对 `items()`、`lines()`、`content()` 的结果调用
add、remove、set 或 clear，说明把返回值收紧为不可修改不会破坏已存在的合法用法。大量调用方
只迭代、映射、断言大小或序列化列表。`CreateOrderRequest` 的空值行为则必须更谨慎：列表本身由
`@NotEmpty` 校验，元素由 `@NotNull` 校验，构造层不能抢在 Bean Validation 前改变错误类型。

最初候选实现是 `List.copyOf`，代码更短，但它会同时拒绝 null 列表和 null 元素。若请求 JSON 的
items 中出现 null，旧路径应由元素级 Bean Validation 形成统一 400 ProblemDetail；构造器提前抛
NullPointerException 会绕开该语义。最终选择 `ArrayList` 快照加不可修改包装，既保存 null，
又隔离结构。这个取舍直接来自现有校验注解，不是为了追求某个 API 的表面简洁。

## 服务层核心流程

构造流程有三步。第一步，Jackson、服务映射或测试代码调用 record 构造器并交入列表。第二步，
紧凑构造器执行 `component = ImmutableLists.copy(component)`；赋值发生在编译器写入 final 字段
之前，所以字段从一开始就只持有快照。第三步，后续调用原列表的 clear、add 或 set，只修改外部
容器，不影响 DTO 字段。

访问流程同样有三步。调用者执行 `content()`、`items()` 或 `lines()`；显式访问器把字段再交给
`ImmutableLists.copy`；返回的是不可修改快照。构造器内已经保存不可修改列表，为什么访问时还要
再经过 helper？因为 SpotBugs 的边界判断关注方法是否直接返回内部字段，而且未来实现可能改变
字段来源。显式复制让所有权规则在方法体里可见，也让访问器始终不泄露字段引用，六条 REP/REP2
告警才能在不使用 suppression 的情况下真实消失。

分页工厂 `PagedResponse.from`、订单映射 `OrderResponse.from` 和订单幂等指纹逻辑均不需要知道
这些细节。它们继续构造相同 record，访问相同组件。helper 的泛型参数使用
`List<? extends T>` 输入，使子类型列表也能安全复制；输出是确定的 `List<T>`。没有反射、序列化
定制、代理或线程锁，运行成本是一份线性容器复制，换来明确的对象所有权。

## Java 证据检查

`ImmutableListBoundaryTests.copyPreservesNulls` 首先锁定兼容性：null 列表仍是 null，包含 null
元素的列表仍保留原位置。这条测试保护 Bean Validation 继续负责请求约束，而不是让 helper
偷偷改变领域错误。它同时证明共享抽象不是 `List.copyOf` 的别名，未来重构若误换 API 会立即
暴露。

`dtoListsAreSnapshots` 通过一个泛型 `assertSnapshot` 驱动三类 DTO。每个场景都创建可变输入，
先构造 DTO 并取得访问结果，再清空原列表；断言 DTO 结果仍等于构造时内容。随后直接对访问结果
执行 clear，必须抛 `UnsupportedOperationException`。同一个测试算法覆盖 String、请求行 record
和响应行 record，避免第三份相似测试出现后继续复制粘贴。

聚焦 Maven 还运行 `OrderIdempotencyBoundaryIntegrationTests`，覆盖空请求、非法行、幂等冲突和
正常创建路径；运行 `SpotBugsWaiverTests`，确认剩余 676 个身份仍是 Git 前版子集且 class 全可
加载；运行 `JavaChangeGateTests`，确认新增/触碰名称不超过 40 字符且生产源码增长低于 400 行。
12 项测试全绿后，真实 `spotbugs:check` 给出 BugInstance 0、Error 0，证明六条删除有代码修复支撑。

## mini-kv 证据检查

这一版没有读取或修改 mini-kv 的 WAL、快照、RESP 协议、只读证据或归档目录。DTO 不可变边界
只发生在 Java 进程内，既不改变 Java 对 mini-kv 的冻结证据读取，也不改变 Node 对两个上游项目
的绝对路径引用。因而不需要 C++ 项目同步版本，也没有新的跨项目输入输出。

保留本节是为了明确证据范围。本版的 Java 集成测试能证明请求校验和订单幂等路径，SpotBugs 能
证明 Java 引用边界，但它们不能代替 mini-kv 自身测试，也不构成新的联合运行授权。已有 C1-C4
只读 capstone 结论继续成立；本版没有重跑真实 `minikv_cli`，所以不会把“未触碰”描述成“重新
验证通过”。

如果未来某个跨项目响应也使用可变集合，应该在拥有该 schema 的项目中独立治理，并先确认字节
契约。当前三个 record 都是 Java 本地 API 模型，没有改组件或 JSON 名称，因此不触发依赖顺序。
边界原则仍是：内部所有权优化可并行，共享契约变化必须按 mini-kv、Java、Node 的顺序评估。

## 阻断与安全边界

不可修改列表防的是别名写入，不是权限系统。它阻止同一 JVM 内的调用者通过原容器或访问器改变
DTO 结构，减少竞态、幂等输入漂移和响应缓存被意外改写的可能；它不授权支付、重放、部署、回滚
或凭据访问，也不引入持久化写入。Controller、Service 与 Repository 的事务注解均未改变。

null 保持是安全兼容边界。helper 不把 null 变成空列表，因为“字段缺失”和“提交空数组”可能由
校验层产生不同细节；也不拒绝 null 元素，因为元素级注解应生成稳定字段错误。另一方面，访问器
返回 null 时仍是 null，这与旧 record 语义一致。调用方若需要空集合，必须在自己的业务语境中
明确转换，不能由通用基础设施猜测。

本版禁止以 `@SuppressFBWarnings` 代替修复，禁止先删 XML 再忽略 SpotBugs 失败，也禁止为了少
复制一次直接返回内部字段。六条豁免只有在真实扫描 0/0 后才算消债。列表是浅快照这一限制也写
入讲解，避免“不可变”一词被误读成所有嵌套对象都深度冻结。

## 测试覆盖

第一轮格式门由 Spotless 统一处理五个新增或触碰 Java 文件，避免大面积手工空白差异。随后聚焦
命令执行 12 项测试：2 项新不可变边界测试、4 项 SpotBugs 身份测试、3 项变更优雅门和 3 项
订单幂等/请求校验集成测试。它们分别覆盖 helper、三个 DTO、Git ratchet、类可达性、名称预算、
源码增长、Web 校验和业务幂等。

同一 Maven 调用继续执行 `spotbugs:check`。如果构造器仍保存外部引用，REP2 会重新出现；如果
访问器仍返回字段，REP 会重新出现。当前 XML 已删除六条身份，所以这不是“有豁免的绿色”，而是
分析器直接报告 BugInstance size 0、Error size 0。v1871 门还确保没有把问题迁移到新 class 的
豁免中。

最终交付仍要在本篇讲解写完后运行完整 `mvnw -B verify`，覆盖全部 1,900 余项测试、JaCoCo
十组阈值、Spotless、SpotBugs 与 jar 打包；提交后的 canonical CI 还要独立运行 headless 与
Docker-tagged job。归档测试会锁定本篇唯一新增文件的路径、SHA-256、文件数与原始字节数，任何
额外文件或正文漂移都必须显式解释。

## 实际工作量说明

工作并非在三个 record 中各加一行 `List.copyOf`。前置阶段先从 676 余条 SpotBugs XML 中定位
六个完整身份，再扫描所有生产和测试调用者是否依赖可变访问器；随后分析 Jackson record 构造、
Bean Validation 对 null 列表与 null 元素的职责，以及 SpotBugs 对构造输入和返回引用的两种
检测。正是这些证据否决了最短但会改变校验语义的方案。

三次规则也实际影响了设计。第三个相似 DTO 出现前提取 `ImmutableLists`，不让 null 判断、复制
方式和不可变包装分散在三个包内。测试没有写三个几乎相同的方法，而是用一个泛型场景承载“记录
期望、构造边界、修改来源、验证快照、攻击返回值”的共同算法。新增生产 helper 只有十余行，
整体生产增量远低于 400 行门，命名均在预算内。

验证不是只跑新单测：包含真实 Spring 上下文和十二次数据库迁移的订单集成测试证明 Web/幂等
行为没有回归，SpotBugs fork 证明六条债务真实消失，精确身份门证明没有等量偷换。讲解篇幅来自
所有权、校验顺序、浅拷贝限制、静态分析与跨项目边界的真实机制，禁止硬凑重复段落。本项目因此
同时获得更强代码语义和更小维护基线，而不是只获得一张“测试已过”的截图。

## 一句话总结

Java v1872 用一个短小共享 helper 为分页内容、订单请求行和订单响应行建立“构造时隔离、读取时
只读、null 语义不变”的统一所有权边界，并以泛型回归、现有 Spring 校验和 SpotBugs 0/0 删除
六条精确历史豁免；JSON 与业务能力不变，DTO 从形式上的 record 变成结构上真正不可被外部列表
别名篡改的值对象。
