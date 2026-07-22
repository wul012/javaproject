# v1891：operator-CI handoff 五组证据如何从散列文件收敛为一个不可变概念

## 入口路由

外部入口仍是 `/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-registry`。Controller 继续位于根 `ops` 包，对外类型仍是 `OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse`，Controller 到 service 的注入关系没有变化。route 后缀继续由 `OpsShardReadinessReleaseAcceptanceRoutePaths` 持有，service 中的 `ENDPOINT` 仍由统一 `BASE_PATH` 与原后缀拼接。v1891 没有新建路由、没有兼容别名、没有改路径大小写，也没有把内部 `HandoffCatalog` 暴露成 Spring bean。

这条入口的意义不是“执行 CI”，而是展示一份供 operator 阅读的只读 handoff 计划。HTTP 请求到达后，只会读取 Java 内已存在的静态证据模型，组合 source archive、建议 lane、CI batch、禁止边界和 scorecard，再返回 JSON。它不会启动 Java 子进程，不会启动 mini-kv，不会调用 shell，不会连接 managed audit，也不会把计划文本变成执行命令。因而入口稳定性不仅是字符串兼容问题，还包括权限语义兼容：调用者从旧版本获得的是 advisory evidence，新版本仍只能获得 advisory evidence。

内部重构对入口透明的关键，在于 Spring 可见边界没有动。`HandoffCatalog` 是 package-private 且构造器私有，只能由同包 service 调用；公开 service 的构造器仍只接收上游 execution archive service；`registry()` 仍带 `@Transactional(readOnly = true)`。因此容器装配图、Controller 接口、序列化类型和事务属性都没有新的节点。若未来有人把 catalog 标成 `@Service`、给它增加公开 route 或让 Controller 直接调用它，结构门应视为职责泄漏，而不是“更方便”。

## 响应模型

公开 Response 仍包含项目、版本、七个禁止执行布尔值、endpoint、profile、三个 Node 计划引用、上游 archive 版本与 endpoint、handoff state、九个计数、五组业务列表、Markdown sections、checks 和最终 status。v1891 没有重新声明这些 record，也没有把五组列表包装成新的公开字段。`HandoffCatalog.Evidence` 只是内部装配类型：它把已有公开嵌套 record 的五个列表放在一个 package-private record 中，最终 Support 仍按原构造器顺序把列表放回公开 Response。

五组数据分别回答五个不同问题。`sourceArchiveSnapshots` 说明本次 handoff 依据哪一版 execution archive；`operatorLanes` 给出 focused、grouped、build、smoke 的人工顺序；`ciBatches` 描述每一批验证范围以及是否阻断下一批；`boundaryLocks` 明确哪些行为必须保持关闭；`scorecard` 把上游状态、lane 数、batch 数、边界数和上游 scorecard 通过数转为五项可检查结果。它们不是可以随意混装的字符串集合，所以 `Evidence` 没有使用 `Map<String, List<?>>`。每个 accessor 都保留编译期元素类型，删错一组或交换类型会在编译时暴露。

record 紧凑构造器对五个输入逐一执行 `List.copyOf`。这个动作同时建立两层约束：调用者之后清空原列表，evidence 仍保留创建时快照；消费者试图向 evidence 列表写入，会得到 `UnsupportedOperationException`。不能只依赖当前各方法返回 `List.of`，因为那是生成方式的偶然属性；明确复制把所有权写进聚合类型自身。未来即使某个投影改用可变构建器，Evidence 的不可变承诺仍成立。

## 上游证据配置

本项目的 handoff 数据不是凭空生成。service 的唯一外部依赖仍是 `OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService`。它先返回 execution archive 响应，其中包含 Java v1337 archive 状态、artifact/read-target/gate-check/boundary 的验证计数、上游 scorecard 和只读边界。`HandoffCatalog` 从这份响应复制必要 lineage，四条 lane 的 `sourceEvidence` 也继续指向同一 archive version，最后一项 scorecard 继续比较上游 scorecard 总数与 passed 数量。

依赖方向必须保持单向：execution registry 生成基础执行证据，execution archive 验证其归档，operator-CI handoff 把归档结果翻译为人工检查顺序，handoff archive 再验证 handoff 是否完整归档，archive digest 最后消费 handoff archive。v1891 只整理链中 handoff 这一环，没有让 execution 包反向依赖 handoff，也没有让 digest 跳过 archive 读取内部 catalog。`ExecutionExtractionTests` 与 `HandoffExtractionTests` 分别从上下游两侧读取源码，确认 service 仍依赖上游公开 service，catalog/Support 只读取上游公开 Response。

三个 Node 计划字符串仍是 `Node v367`、`Node v368`、`Node v369`。它们是来源、所需 archive verification 和推荐 operator plan 的可读标识，不是运行 Node 的指令。v1891 不访问 Node 工作区、不修改 Node fixture、不重新解释计划内容。跨项目对齐仍由公开响应和 frozen digest 提供；如果 Node 后续要采用新的计划版本，应通过独立契约版本推进，而不是在这次 Java 内部重构中静默替换字符串。

## 服务层核心流程

旧 `registry()` 的阅读路径是：取 source archive，调用 SourceArchiveCatalog，调用 LaneCatalog，调用 BatchCatalog，调用 BoundaryLockCatalog，再调用 service 私有 scorecard；随后把五个列表传给 Support，同时又把五个列表传给 renderer。读者需要在 service 与五处实现之间来回跳转，才能知道一份响应是否完整。service 还拥有 scorecard 生成细节，这让“协调流程”和“投影数据”混在同一类中。

新流程只有四步。第一步仍调用 `sourceArchiveService.registry()`；第二步调用一次 `HandoffCatalog.evidence(sourceArchive)`；第三步调用 `HandoffRenderer.render(evidence)`；第四步把 metadata、source archive、evidence 和 Markdown 交给 Support。service 不再知道 lane 有几条、batch 文案是什么、边界锁有哪些、scorecard 如何比较。它只表达请求级时序和公开响应编排，代码长度与认知负担同时下降。

catalog 内部先构造 lanes、batches、locks，再用这三组数据和 source archive 生成 scorecard，最后连同 snapshot 一次交给 Evidence。这里没有为了追求“单表达式”把所有内容塞进一个巨大构造器；局部变量说明 scorecard 的依赖，也保证每组投影只计算一次。五个私有方法仍按领域名称分段，使 181 行 owner 可以顺序阅读。文件收敛不等于方法收敛，真正目标是让一个概念有一个 owner，同时保留概念内部清晰的小步骤。

renderer 现在只接收 `HandoffCatalog.Evidence`，但它仍有 source、lanes、batches、boundaries、scorecard 五个私有渲染函数。Support 也只接收 evidence，却继续独立计算 ready/passed/locked 计数、checks 和 status。这种设计避免两个极端：既不保留五参数扇出，也不把投影、Markdown、状态全部塞进一个“万能 Catalog”。新增第六组证据时，编译器会要求 Evidence、renderer 和 Support 明确响应，而不是让一个无类型 map 静默吞掉变化。

## Java 证据检查

Java 侧最强证明是完整响应 oracle。测试使用 Jackson 按属性名和 map key 排序，将公开 Response 序列化为 UTF-8 JSON，再计算 SHA-256。这个摘要覆盖所有公开字段和嵌套列表，比逐字段抽查更容易发现遗漏。旧实现先以最终断言形式通过，重构后同一测试再次通过，因此不是“看起来一样”，而是完整序列化结果逐字节一致。七段长度向量提供更易读的故障定位：如果摘要失败，先看是哪一组数量变化。

第二层是 Catalog 语义测试。它检查 source snapshot 仍指向 Java v1337 且状态 passed；lane 顺序严格是 focused、grouped、build、smoke；CI command-family 顺序严格是 focused、focused、grouped、build、smoke；八条 boundary 全部 locked，并包含 no-java-autostart、no-write-routing、no-credential-value；五项 scorecard 全部 passed。这些断言说明摘要中的数据为何正确，而不是只给出一个无法解释的哈希值。

第三层是结构与优雅门。`HandoffExtractionTests` 精确枚举 15 个生产文件和 10 个包内测试，禁止四个旧 Catalog 复活，限制 `HandoffCatalog` 小于 200 行，要求 `List.copyOf` 恰好五次、service 的 `HandoffCatalog.evidence` 恰好一次，并确认 renderer 与 Support 的参数类型是 Evidence。名称 census 证明生产与测试三项指标全部下降，baseline diff 只有 12 个删除项。扩大选择覆盖当前包、根 Controller、下游 archive/digest 和上游 execution 结构，共 77 个测试全绿。

## mini-kv 证据检查

这条 Java route 会提到 mini-kv，但不会直接读取或启动 mini-kv。四条 operator lane 与五个 CI batch 是 Java 端的检查编排；mini-kv 的真实进程、CLI、WAL、snapshot 和网络端口不属于 `HandoffCatalog` 的输入。边界列表中的 `no-mini-kv-autostart` 与 `no-mini-kv-write-admin` 表示 Java/Node 协作时必须保持的禁令，不表示本 service 已连接 mini-kv 验证运行状态。

为什么仍要在讲解中说明 mini-kv？因为 handoff 响应面向跨项目 operator，容易把“计划中提到 mini-kv”误解成“Java 拥有 mini-kv 执行权”。v1891 的证据链刻意只从 Java execution archive 读取静态、只读结果；任何 mini-kv 实机检查必须由 env-gated capstone 或人工 operator 在明确授权的环境中执行。该结果可以作为外部证据被消费，但不能由本 Catalog 偷偷发起。

因此本版本对 mini-kv 的验证是边界验证：完整响应摘要必须继续包含 startsMiniKvService=false；boundary locks 必须继续包含 no-mini-kv-autostart 和 no-mini-kv-write-admin；checks 与最终状态不能因为缺少运行中的 mini-kv 而改成执行探测。若重构后需要启动 mini-kv 才能通过测试，那不是测试环境问题，而是架构越权，版本应立即失败。

公开只来自既有静态安全边界：Node 不得自动启动 mini-kv，read-only smoke 不得执行写路由，mini-kv write/admin 命令保持禁止。Java 不解析 mini-kv WAL，不连接 RESP server，也不读取 mini-kv 的 archive 路径。`startsMiniKvService=false` 和对应 boundary lock 同时存在，是为了让结构化字段与人工可读边界互相印证。

为什么 Java registry 仍要提 mini-kv？因为这个 handoff 描述的是跨项目 operator 应如何逐步验证证据，而不是 Java 单体内部的调用图。operator 可以在外部按计划读取 mini-kv health、infojson 或 statsjson，但 Java 只声明“这些动作必须由外部显式完成且保持只读”。它不持有 raw endpoint，不注入 credential value，也不会根据 evidence 自动发网络请求。这个区分防止“可读性更好的计划”悄悄演变成“拥有执行权的编排器”。

本轮重构把边界列表放入 Evidence 并不会扩大权限。`BoundaryLock` 仍是响应 record，`locked=true` 的含义是动作被禁止；Catalog 只生成这些事实，Support 只统计 locked 数，最终 status 只有在八项全部锁定时才可能 passed。若有人把某条 lock 改为 false、删除 no-mini-kv-write-admin 或改变原因文字，完整响应 SHA-256、字段测试和 scorecard 都会同时失败。Java 与 mini-kv 的关系因此仍是契约说明和只读证据消费，而不是进程控制。

## 阻断与安全边界

八条 boundary lock 继续覆盖：禁止 Node 自动启动 Java、禁止 Node 自动启动 mini-kv、禁止写路由、禁止读取 credential value、禁止解析 raw endpoint URL、禁止 managed-audit HTTP/TCP、禁止 runtime shell、禁止 mini-kv write/admin。它们不是“当前实现碰巧没做”，而是 handoff 能通过的必要条件。Support 会比较 locked boundary 数和总数；只要一项未锁定，最终 status 就会 blocked。

此外，Spring service 仍是 read-only transaction。没有新增 Repository、HTTP client、ProcessBuilder、Socket、shell command 或 credential resolver 依赖；新 `HandoffCatalog` 只有纯 Java 列表投影。Catalog 不接受可执行函数，不返回 endpoint handle，也没有根据状态触发下一批次的逻辑。CI batch 中的 scope 是供 operator 阅读的文本，`blocksNextBatch` 是描述顺序的布尔字段，不是 Java 运行 Maven 或启动服务的开关。

本版本明确不碰 deployment、rollback、rollback SQL、支付、库存、消息发布和 failed-event replay。即便响应最终为 passed，也只表示“只读 handoff 证据内部一致”，不表示生产发布获批，更不表示跨项目自动执行获批。外部 maturity label 仍是 single-project validation 加 env-gated 的只读跨项目集成；最终轨道状态仍需外部评审，代码不能自授“final”或“九分”。

失败条件也写入版本文档：公开字段、顺序、Markdown、check、向量或 SHA 变化即失败；为了通过而修改 fixture 或摘要即失败；四个旧 Catalog 复活、scorecard 回到 service、owner 达到 200 行、复制次数不是五次、service 装配不再恰好一次即失败；任一名称指标上升或 exact baseline 新增也失败。这些条件使安全和优雅都成为会阻断提交的约束。

## 测试覆盖

测试分成四层。第一层是完整 response oracle，它在旧实现上先固定向量和 SHA，再用于新实现。它防止“所有局部测试都过了，但某个未被抽查字段变了”。第二层是 Catalog 行为与所有权测试，检查五组投影的内容、顺序、通过状态和不可变快照。第三层是既有 service、Markdown、Controller、archive 和 digest 消费者测试，证明公开链路仍能从 execution archive 一直走到下游。第四层是结构、命名、归档、讲解和发布纪律门，防止代码正确但维护性倒退。

测试文件也按职责调整。原 `OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryServiceTests` 改为 `HandoffRegistryServiceTests`，断言内容不缩水；历史 v1844 owner 改为 `HandoffExtractionTests`，不仅保留旧提取事实，还加入当前 15 文件上限、四个 retired catalog、181 行 owner、五次 copy、一次 assembly 和 typed boundary。`HandoffCatalogTests` 不复制 service 的 HTTP 元数据断言，而是专注纯投影和所有权；`HandoffResponseOracleTests` 专注全量字节语义。每个测试 owner 都有可说清的职责。

讲解和版本说明在最终 verify 前完成，因为文档也是被测交付物。`CurrentWalkthroughTests` 会把本文件设为最新讲解，要求恰好十个标准章节、中文内容达到下限、包含“禁止硬凑”和“本项目”等策略词。Archive retention 会把本文件作为 v1891 唯一新增归档，按 canonical LF 字节计算 SHA、文件数和 raw byte 上限。最终还必须运行 `scripts/verify-release.ps1`，由它固定前一 annotated tag、检查 Spotless、执行全部非 Docker 测试、JaCoCo floors、SpotBugs 和 jar 打包；随后实现与 closeout 两轮远端 CI 都必须真实转绿。

## 实际工作量说明

本版本不是把四个文件机械粘贴到一起，而是重新确认一次 operator-CI handoff 请求究竟拥有几组数据、谁负责生成、谁负责展示、谁负责作最终判断。旧代码中，source archive snapshot、operator lane、CI batch、boundary lock 分别由四个超长类名的 Catalog 生成，scorecard 则藏在 service 的私有方法里。五组列表都在同一次请求中产生，都只供同一条响应链使用，却没有一个类型表示“这就是一份完整的 handoff 证据”。结果是 service 必须逐个知道五种构造细节，renderer 接收五个参数，Support 又接收同样五个参数。文件看似细，概念反而散。

实际改动先从已发布 v1890 取样：在任何生产代码变化前运行完整响应 oracle，固定七段集合向量 `1/4/5/8/5/5/15`，再对属性排序后的 UTF-8 JSON 计算 SHA-256 `4fc6dc6069cff5bc40ee0934bc1ed9133ff50bcfe7c3c5940429e83cf4287ab0`。随后新增 181 行 `HandoffCatalog`，删除四个长名 Catalog，把 service 内 scorecard 投影移入 catalog，建立五列表 `Evidence`，改造 service、renderer、Support 三个调用边界，新增 Catalog 所有权测试和完整响应测试，收短两个被触及的长名测试 owner，并更新两个跨版本结构门。整个过程不改 Response、Controller、route、fixture、状态文字或任何安全布尔值。

量化结果也必须解释得通。生产 Java 从 1345 降到 1342，ops 从 1213 降到 1210，Catalog 从 296 降到 293，当前包从 18 个生产文件降到 15 个。测试文件从 904 增到 906，是因为增加了此前不存在的完整响应摘要和五列表不可变所有权证明，不是把旧测试换名字重复一遍。生产长文件名、长标识符使用次数、唯一长标识符收紧为 `1107/20002/2666`，测试收紧为 `714/9844/3695`，精确 baseline 删除 12 项且新增 0 项。这里强调禁止硬凑：如果工作量不足以解释完整机制，就扩大真正的行为和结构证明，而不是用空泛段落凑字数。

## 一句话总结

v1891 把“一次 operator-CI handoff 的五组投影”从四个长名 Catalog 加一个 service 私有方法，收敛成一个 181 行、强类型、不可变且可机械约束的 `HandoffCatalog.Evidence`；service 因而只编排一次，renderer 只展示，Support 只汇总，公开 route、Response、顺序、安全边界和完整 JSON 摘要保持不变。它减少三个生产文件、三个 Catalog、十二项精确长名债，并在测试初稿出现局部反弹时主动修正，而不是用“总体下降”掩盖。禁止硬凑的标准在这里不是文字长度，而是每一段都能对应具体代码、机械门和失败条件；本项目因此向 coding brilliant and elegant 九分目标又走了一步，但仍以完整 verify、双 CI、canonical tag 和最终外部复核为准。
