# v1892：Operator-CI Handoff Archive Catalog 收敛代码讲解

## 入口路由

本版本处理的入口是 `/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-verification-registry`。它不是下单、扣库存、支付或失败事件重放接口，而是一条严格只读的运维证据路由。调用者的目标也不是让 Java 代替操作者执行部署，而是询问一个更窄的问题：上一层 operator-CI handoff 给出的来源、执行批次、边界锁和计分结果，是否已经被完整投影成可归档、可核验的证据。Controller 仍然只是 HTTP 适配器，路由常量仍由既有 `OpsShardReadinessReleaseAcceptanceRoutePaths` 提供；v1892 没有改 URL、请求方式或公开可见性，因此 Node 或人工检查脚本不需要跟着调整。

请求进入后，Spring 调用 archive verification registry service 的 `registry()`。这个方法保留 `@Transactional(readOnly = true)`，先调用真实的 handoff service 获取当前响应，再把响应交给新的 `ArchiveCatalog.evidence(sourceHandoff)`。这里的“真实”是指沿 Java 内部既有服务链读取当次生成的只读对象，而不是复制一份测试 fixture，也不是从磁盘拼接一个看起来相似的 JSON。输入只有一个完整的 source handoff，输出则是 archive registry response；中间不启动 Java 子进程、不启动 mini-kv、不解析凭据值、不探测原始 endpoint，也不开放写路由。

重构前，service 需要知道六个 Catalog 的名字、调用顺序和参数依赖：先 source，再 artifact、lane、batch、boundary，最后把前四组传给 scorecard。这样的代码虽然能工作，但协调细节泄漏到了应用服务。v1892 把入口的职责恢复为“取来源、做一次投影、交给渲染和汇总”。因此读者从 `registry()` 的几行代码就能看清完整控制流，不必在六个超长文件名之间来回跳转。入口没有变得更聪明，反而因为只保留流程编排而更容易验证。

## 响应模型

公开响应模型保持原样，版本仍为 `Java v1377`，profile、三个 Node 计划引用、source handoff 版本与 endpoint、archive state、所有布尔安全位、计数字段、列表字段和最终 status 都没有改名或换序。完整列表数量向量固定为 `1/6/4/5/8/6/6/21`：一条 source handoff snapshot、六条 artifact verification、四条 operator lane verification、五条 CI batch verification、八条 boundary verification、六条 scorecard、六段 Markdown，以及二十一条 checks。这个向量不仅表达“总数没变”，还表达每种事实仍待在原来的语义容器里。

为了证明对象序列化后没有隐蔽漂移，`ArchiveResponseOracleTests` 在删除旧 Catalog 前，使用属性名稳定排序的 Jackson mapper 把完整 response 转成 canonical JSON，再计算 SHA-256。发布态旧实现得到 `1b9fd78f3ac4d3905d027f2c5b3d04c15a768b0b17b45497d583606ead7a5321`。新实现必须同时满足数量向量和这个摘要；只要列表顺序、字符串、布尔值、计数、字段值或嵌套 record 内容有一个字符变化，测试都会失败。这比逐字段挑几个样本更严格，也避免“重构只改内部，所以大概兼容”的主观判断。

内部新增的 `ArchiveCatalog.Evidence` 不是新的 HTTP DTO，也不会扩大 API。它只是 package-private 的 typed record，持有 source、artifact、lane、batch、boundary 和 scorecard 六组列表。每个 accessor 都保留具体 record 类型，没有退化成 `Map<String, Object>` 或无类型数组。紧凑构造器对六组列表逐一执行 `List.copyOf`，所以 catalog 返回的是一次请求内的不可变快照；调用方不能清空、追加或借由原始可变列表反向修改它。数据所有权因此从“六个静态类各返回一段临时列表”变成“一个 evidence 明确拥有一套完整投影”。

## 上游证据配置

archive registry 的唯一上游是 `OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService`。上游 handoff 本身又读取 execution archive，但 v1892 没有越级访问 execution 层，也没有绕过 handoff 的公开 response。依赖方向仍是 execution archive 生成基础归档证据，handoff 把证据整理成 operator lane、CI batch 与 boundary lock，本版本的 archive registry 再验证这些 handoff 事实能否归档。下游 archive-digest 继续消费本 archive service 和公开 response，因此链条始终单向，不存在 archive catalog 反向调用 digest 的循环。

`ArchiveCatalog` 对 source handoff 做六种有类型的投影。source snapshot 记录版本、endpoint、profile、计划、状态和三个关键计数；artifact verification 检查 source JSON、Markdown、operator lane、CI batch、boundary lock 与 source scorecard 六类产物；lane 和 batch 保持上游顺序；boundary 把上游 locked 状态映射为 archived 与 passed/blocked；scorecard 最后用实际投影结果和 Support 中的 expected 常量比较。它并不自行发明新事实，而是把上游已经公开的事实换成 archive 语境下的验证记录。

配置常量仍由原 Support 持有，包括项目名、Node v367/v368/v369、预期数量和 archive state。这样做有意把“数据怎样投影”和“整份响应何时算 passed”分开：Catalog 负责形成事实，Support 负责政策判断。若以后上游新增一个 operator lane，Catalog 会如实映射，而 Support 的预期数量会让状态变为 blocked，提醒维护者显式审查契约；不会因为两个职责挤在一个方法里而默默放行。这种失败方式比自动适配更适合生产就绪证据。

## 服务层核心流程

新的服务流程可以透明地写成五步。第一步，调用 source handoff service 得到只读响应；第二步，调用且只调用一次 `ArchiveCatalog.evidence(sourceHandoff)`；第三步，把同一个 evidence 交给 `ArchiveRenderer.render(evidence)` 生成六段 Markdown；第四步，把 source、evidence 和 Markdown 交给 Support；第五步，由 Support 计算 passed 数量、锁定数量、二十一条 checks 和最终状态，构造原 response。所有对象都在一次方法调用内产生，没有缓存、全局可变集合或异步副作用。

重构前的六个 Catalog 共三百余行，每个文件只有一组列表，却重复包声明、超长 response 类型、私有构造器和几乎相同的 stream/map 框架。文件数量让“职责分离”看起来很强，实际语义边界却是同一份 source 到同一份 archive evidence 的一次投影。v1892 删除这六个文件，新增一个格式化后 200 行的 `ArchiveCatalog`。这不是把无关模块硬塞成巨型类：六组数据共享来源、生命周期、状态词汇和 scorecard，且 evidence 正是它们天然的聚合边界。结构门把上限设为 260 行，既允许清晰排版，也阻止未来无限膨胀。

Renderer 不再接收六个平铺参数，而是接收一个 typed evidence，然后分别读取六个 accessor 调用原来的 section 方法。Support 同样接收 evidence，但仍单独接收 Markdown，因为 Markdown 属于展示产物，不是 Catalog 的领域事实。这个细节很重要：若把 Markdown 也塞进 evidence，Catalog 就需要依赖渲染；若把 checks 塞进去，Catalog 又会承担最终政策。当前分界让数据投影、展示和汇总各有一个 owner，service 只连接三者，任何一层变化都有明确落点。

## Java 证据检查

Java 侧的 artifact verification 有六条。第一条要求 source response status 为 passed，并记录 source endpoint；第二条核对上游 Markdown 段数等于 archive 预期段数减一；第三至第五条分别要求 ready operator lane、passed CI batch、locked boundary 与总数一致；第六条要求上游 scorecard 全部通过。每条记录同时包含 artifact 名称、producer、evidence 字符串、archived 布尔值和 passed/blocked 状态，既能给机器计数，也能让人看到失败发生在哪一层。

lane verification 保持 focused、grouped、build、smoke 的顺序，并继承 owner 与 ready 状态；batch verification 保持五批 focused、focused、grouped、build、smoke 的 command family 顺序。v1892 没有为了代码短而排序、去重或改用集合，因为顺序本身就是操作协议。boundary verification 同样逐条映射八个锁，包括 no-java-autostart、no-write-routing、no-credential-value、no-managed-audit-http 等。scorecard 用六项 expected/actual 比较把 source 状态和五类投影结果汇总起来，只有每项实际值都达到预期才标记 passed。

Java 的机械证据分三层。行为层由完整 response oracle、service 契约、checks 和现有 controller/Markdown/下游 digest 测试守住；结构层由 `HandoffExtractionTests` 精确列出十个生产文件和十个测试文件，检查六个旧 Catalog 与四个旧测试 owner 不存在，并检查一次 evidence 调用、六次 `List.copyOf`、typed renderer/support 参数和 260 行上限；全局层由 ops census、Java elegance gate 和 exact-name baseline 守住文件数、Catalog 数、长文件名及长标识符。任一层失败都不能靠修改 fixture 或放宽阈值绕过。

## mini-kv 证据检查

本接口提到 mini-kv，不代表 Java 会连接或控制 mini-kv。响应中的 `startsMiniKvService` 保持 false，boundary 列表继续包含 `no-mini-kv-autostart` 与 `no-mini-kv-write-admin`，checks 继续声明无上游自动启动和无写路由。Catalog 只读取 Java 内存里的 source handoff response；它没有 socket、进程启动器、CLI 调用、主机名、端口、凭据或命令字符串。因此即使本机完全没有运行 mini-kv，这条 registry 也能稳定生成“边界被锁住”的证据。

这种设计与跨项目协作规则一致：Java 可以验证自己是否保留只读消费边界，却不能把 mini-kv 的真实运行状态伪装成 Java 已验证。mini-kv 自己的 WAL、snapshot、RESP 或 shard readiness 应由 C++ 项目的测试和证据负责；真正的联合运行应留给系统 capstone。v1892 所能诚实证明的是：本次重构没有新增任何 mini-kv 运行依赖，没有自动启动或停止 C++ 进程，没有开放写/admin 命令，也没有改动 Node 已固定的历史 archive 路径。

如果未来需要接入实时 mini-kv 读证据，正确输入应是一个显式、受环境开关控制且带超时的跨项目适配器结果，并由新的契约版本承载；不能偷偷塞进 `ArchiveCatalog`。Catalog 的纯函数性质是这里的安全资产：同一个 source handoff 必须得到同样的 evidence，构造过程不受网络波动影响。保持这个边界，既让单仓库回归可重复，也避免“为了证明 readiness，验证过程反而启动生产依赖”的悖论。

## 阻断与安全边界

最终 status 的判定仍在 Support。它要求 source handoff 为 passed；source、artifact、lane、batch、boundary、scorecard 和 Markdown 数量分别达到固定值；所有 artifact、lane、batch、boundary 与 scorecard 状态通过；八条 boundary 全部 locked。只要其中一个条件不满足，响应会返回 blocked，而不是抛弃失败项后仍显示 passed。二十一条 checks 仍包含三个计划、上游版本与状态、各类计数、passed/locked 计数及五项关键禁止能力，字符串内容和顺序均被完整摘要锁定。

公开安全位也没有因内部 evidence 聚合而弱化：readOnly 为 true，executionAllowed、startsJavaService、startsMiniKvService、readsCredentialValue、resolvesRawEndpointUrl 和 managedAuditHttpAllowed 全为 false。`@Transactional(readOnly = true)` 保持在 service 公共方法上。Catalog、Renderer 与 Support 都是 package-private 静态协作者，没有 Spring bean、没有可注入副作用，也没有把内部 record 暴露给 controller。调用者只能得到既有公开 response，无法借由 evidence 修改内部列表或调用新执行入口。

失败条件已经写成机械约束：完整 SHA 改变、列表向量改变、旧 Catalog 复活、package 超过十个生产文件、ArchiveCatalog 达到 260 行、`List.copyOf` 不是六次、service 出现第二次 evidence 装配、renderer/support 不再使用 typed evidence、全局 ops 或 Catalog 数回升、exact baseline 新增长命名，都会让测试失败。禁止硬凑通过的含义是，不允许调低摘要严格度、不允许把 expected 改成实际值、不允许放宽 ratchet，也不允许改历史 fixture 字节来迎合实现。

## 测试覆盖

`ArchiveResponseOracleTests` 是兼容性总闸，锁住八组数量与 canonical JSON SHA-256。`ArchiveRegistryServiceTests` 负责公开语义：项目、版本、route、profile、三项 Node 计划、source handoff 信息、archive state、最终状态和七个只读安全位。`ArchiveChecksTests` 专门验证二十一条 checks 的规模与关键文本。这样每个测试 owner 的名字和职责一致，失败时不必先读一个上百字符的类名再猜它覆盖什么。

`ArchiveCatalogTests` 直接测试新抽象，而不是只通过最终 response 间接碰到它。第一组检查 source snapshot 和六个 artifacts；第二组检查 lane/batch 顺序；第三组检查八条 locked boundary 和六项 passed scorecard；第四组从可修改列表构造 `Evidence`，随后清空六个来源列表，确认 evidence 仍保留数据，再尝试向每个输出列表追加元素并期待 `UnsupportedOperationException`。这证明 `List.copyOf` 不只是源码里出现了六次，而是真正建立了不可变所有权。

原来的四个 archive 测试 owner 被三个短 owner 与一个 oracle 取代，测试 Java 总数保持 906，没有用文件膨胀换取表面覆盖。`HandoffExtractionTests` 同时保存当前文件精确清单与退役文件清单，防止未来维护者因找不到旧类而重新复制。更广的回归还会覆盖上游 handoff、下游 archive digest、根 controller、Markdown、Spotless、JaCoCo、SpotBugs 和完整 Maven release gate。局部测试证明设计，完整门证明它没有破坏项目其他部分，两者不能互相替代。

## 实际工作量说明

本版本先在 v1891 已发布代码上冻结旧响应，故意让 oracle 用占位摘要失败一次，从测试输出取得真实 SHA，再写死摘要并让旧实现独立通过；这保证 oracle 不是重构完成后照着新结果补出来的。随后预写八行 family design，明确数据、行为、调用、渲染、汇总、兼容和尺寸边界。生产实现删除六个共三百余行的单列表 Catalog，新增一个 200 行 `ArchiveCatalog`，压缩 service 的六次协调与 scorecard 参数链，并把 renderer/support 改成 typed evidence 输入。

结构数据也不是估算：生产 Java 从 1342 降到 1337，ops 从 1210 降到 1205，Catalog 从 293 降到 288，当前 package 从 15 降到 10；测试文件总数保持 906。生产长文件 stem、长标识符使用次数、唯一长名从 `1107/20002/2666` 收紧到 `1101/19956/2660`，测试从 `714/9844/3695` 收紧到 `710/9829/3687`。exact-name baseline 新增零项、删除二十四项；renderer 总行数也因参数收束从 3241 降到 3234。所有下降值都已写入会失败的测试上限。

测试重组保留原有 service、catalog、boundary、scorecard 与 checks 断言，又补上此前缺失的完整响应摘要和六组列表所有权。工作量不只体现在删文件，还包括调用图复核、上游/下游契约检查、格式化、聚焦行为门、结构门、全局 census、讲解、archive manifest、完整 release gate、GitHub 双 job CI、closeout 与注解 tag。任何步骤失败都先修实现或文档事实，不通过削弱测试收尾。本项目这一刀的价值，是用更少的 owner 表达同样完整的生产证据，并让后续读者能从类型边界直接理解机理。

## 一句话总结

v1892 把同一份 operator-CI handoff 派生出的六组 archive verification 事实收进一个不可变、强类型、单次装配的 `ArchiveCatalog.Evidence`，在完整响应字节、公开路由、只读事务、二十一条 checks、Java 与 mini-kv 禁止边界全部不变的前提下，删除五个净生产文件、五个净 Catalog、二十四项长命名债和一层 service 协调噪声，并用响应 oracle、所有权测试、精确文件清单、尺寸门与全局 shrink-only ratchet 把这种优雅固化成可重复失败的工程规则。
