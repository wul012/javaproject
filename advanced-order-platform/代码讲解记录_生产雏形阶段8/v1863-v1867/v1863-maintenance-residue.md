# Java v1863：RouteCleanup maintenance 残余闭包归位

## 实际工作量说明

这一版不是增加一个新的运维接口，而是把已经形成稳定业务边界、却仍散落在根包里的最后一组纯 maintenance review 实现归回它真正所属的包。迁移对象一共十三个生产类型：十一个只读证据服务、一个共享响应模型和一个共享 support；同时迁移四个只服务于这组实现的测试类。根包中的两个批次 Spring Controller 继续保留，因为它们承担 HTTP 适配职责，而不是证据计算职责。这样的边界很明确：Controller 负责接收请求并调用服务，`ops.maintenance.routecleanup` 负责生成只读证据。迁移前直接根包有一百八十七个 Java 文件，其中八十三个不是最终保留项；迁移后应分别收紧到一百七十四和七十，RouteCleanup 待迁移项从七十九降到六十六。这里的“收紧”不是改一个文档数字，而是由 census 脚本、历史 ratchet 和本版结构测试共同约束，任何文件漏移、重复保留或落到错误目录都会让验证失败。

工作量的重点也不只是移动路径。Java 的包可见性会随目录变化而发生真实变化，原来同在根包中可以直接访问的类型，迁移后必须重新审视哪些能力确实需要公开。共享的 `SustainmentReviewSupport` 只服务于这一组 maintenance 证据组合，因此应随闭包一起移动并继续保持包私有；Controller 只接触公开 Service 和 Response，不应看到 support 内部的条目构造细节。四个测试也必须一起迁移，否则为了让旧测试跨包访问内部实现而扩大可见性，就会把一次结构优化变成新的 API 泄漏。本版坚持“先设计、后实现、再验证”：先写需求证据矩阵和这篇讲解，再执行机械迁移，最后才运行完整 verify。禁止硬凑版本号、禁止用放宽测试换取绿色，也不把单纯的文件数量下降冒充行为改进。

## 入口路由

这组实现被两个批次 Controller 和后续 closeout Controller 使用。HTTP 入口仍位于根包，是因为 Spring 的适配层需要保持集中可发现，也因为最终 census 明确把 Controller 列为一百个合法保留项。迁移不会改变 `@RequestMapping`、`@GetMapping` 或任何路径字节；Controller 只需要把服务类型的 import 指向 `com.codexdemo.orderplatform.ops.maintenance.routecleanup`。客户端看到的仍然是原有 `/api/v1/ops/shard-readiness/...` 路径，既不会多出新路由，也不会失去旧路由。路径所有权继续由已经提取的 `RouteCleanupRoutes` 承担，根级 `OpsShardReadinessRoutePaths` 不重新吸收 maintenance 常量，避免全局聚合器再次膨胀。

入口层的透明性体现在调用方式没有隐式分支。Controller 构造器接收具体服务，GET 方法直接返回服务生成的不可变响应；不存在根据环境变量切换实现、从请求中读取凭证、触发后台任务或绕过审批的路径。比如 sustainment batch 的入口依次暴露 contract freeze、gate handoff、field map、read window 和 runtime boundary checklist，这些方法只读取编译进代码的证据目录与状态描述。assurance batch 则组合 consumer gate、archive summary、CI budget、route inventory、operator signoff 和 extended closeout。包迁移后，Controller 仍是唯一外部入口，服务之间的组合仍在实现包内完成，这使“HTTP 适配”和“证据生成”两种变化可以被不同测试独立约束。

## 响应模型

这组 maintenance 服务共享 `OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse`。这个响应不是随意拼接的 Map，而是不可变 record：它携带项目名、版本、端点、profile、只读标志、执行许可、证据条目、检查项、来源计划和最终状态。条目本身也有稳定结构，用名称、责任方、证据说明、来源和状态表达一个可审计判断。共享模型的价值在于让十一个服务使用同一种语义，而不是每个接口发明自己的“通过”字段。迁移响应类型时，record 组件的顺序、名称和类型都保持不变，因此 JSON 序列化形状也保持不变；SpotBugs 排除项若引用其完整类名，只允许做旧 FQN 到新 FQN 的等量迁移，不能新增宽泛豁免。

`MaintenanceBoundaryReportResponse` 是另一种更靠近最终 closeout 的响应，它把归档交接、策略防线和维护边界整理为可读报告，但这一版明确不迁移它。编译器和 v1857 结构守卫证明，其 Service 仍直接组合根包里的 ArchiveHandoffReceipt 与 PolicyGuard；若只因为名字含有 Maintenance 就提前移动，会形成“实现包反向依赖根实现”的坏边。它因此与通用 RouteCleanup 闭包一起留到下一批，等其依赖同批归位后再迁移。这个取舍说明 family 不能只靠前缀划分，真实调用方向优先于命名相似度。包内 support 仍负责把重复的 review 条目构造集中起来，但不成为公共 API；外部调用者只依赖公开响应和服务方法，内部装配可以在不影响 Controller 的情况下继续优化。

## 上游证据配置

这些服务会提到 Node 计划、mini-kv 证据、归档路径和 CI 顺序，但“提到”不等于“运行”。本项目在这一阶段提供的是单项目验证加跨项目契约对齐：Java 根据冻结的字段、版本标签和证据路径生成只读说明，不能把静态目录误写成实时联调结果。`ContractFreezeService` 说明哪些最小字段已经冻结，`ShardFieldMapService` 说明 Java 与上游 shard readiness 形状如何对应，`ReadWindowEvidenceService` 列出允许读取的健康、概览和 readiness 面，而 `RuntimeBoundaryChecklistService` 明确禁止写路由、凭证值、原始端点解析、受管审计连接与自动启动上游。

配置来源必须可审计且单向。服务可以引用已经提交的 route 常量、profile 字符串、来源计划名称和证据文件名，但不能在请求期间扫描 Node 仓库、改写 mini-kv fixture、下载远端配置或自行决定一个部署可执行。版本迁移只改变 Java 类所在的 package，不改变任何来源计划文本和证据字节。若某个服务需要另一个 maintenance 服务的 endpoint 作为证据来源，它在同一实现包内直接引用该稳定常量；若外部包确实读取 endpoint，则由边界 census 精确记录读者，而不是把所有字段一律 public。v1863 完成后，maintenance 内部关系回到同包，理论上应减少跨包 public 暴露，而不是增加新的可见性债务。

## 服务层核心流程

核心流程可以看成两条逐步收敛的只读流水线。第一条从 contract freeze 开始，确认契约字段、来源计划与不执行边界；field map 把冻结字段投影到 Java 能解释的 shard 结构；read window 说明哪些接口只读可取；runtime checklist 把禁止行为写成明确检查。consumer gate packet 把这四类结果组合成面向消费者的门禁包，archive verifier summary 再描述证据归档所需的格式与摘要，CI budget ledger 规定 focused、route、path、full verify 的验证顺序。这里没有一个步骤可以批准执行，它们只是在不断增加“为什么当前只读结论可复核”的证据密度。

第二条把前述证据收束成 operator 与 closeout 视角。route inventory digest 证明路由清单来自已知 Controller 和共享路径所有者，而不是运行时扫描；operator signoff 证明相关证据已经被纳入检查，却明确不等于执行授权；extended closeout 汇总批次完成情况，仍维持 read-only。`SustainmentReviewSupport` 是这两条流水线的共同引擎，它统一项目名、来源计划、状态计算和条目构造，避免十一个服务各自复制容易漂移的规则。它被保持为 package-private，说明“共享”只发生在 family 内部。更外层的 `MaintenanceBoundaryReportService` 暂留根包，继续连接归档交接与 policy guard；下一批会在通用依赖一起迁移时消除这层历史位置，而不是在本版制造反向边。

迁移顺序必须遵守编译器看到的依赖。十三个生产文件同批移动，消除 support 与 response 被一半代码留在根包的过渡状态；四个测试随后移动到同包，继续验证包私有协作。Controller 通过 import 访问公开服务，其他已经迁移的 routecleanup 服务若依赖这些类型，也通过同一实现包自然访问。随着最后的读者进入同包，前几版为了跨包编译而临时公开的九个 ENDPOINT 可以全部恢复包可见性。最终结构不是把长类名换一个目录而已，而是让依赖方向变成“根 Controller 指向 maintenance 实现，maintenance 内部相互组合”，并让机械守卫继续禁止实现包反向依赖根实现。

## Java 证据检查

Java 侧的第一层证据是结构检查。本版守卫会枚举十三个生产文件和四个测试文件，逐一确认新目录存在、旧根目录不存在；同时读取两个 Controller，确认它们导入实现包而不是重新复制服务。第二层是可见性检查：共享 support 必须是 final 且非 public，其辅助方法和内部条目构造不能因为迁移而开放；九个不再有外部读者的 ENDPOINT 也必须退回包可见性。第三层是边界 census，精确记录两个外部来源、十三条类型边和十二个被引用目标；如果某个读者丢失或凭空增加，测试会给出具体文件名，而不是只报一个模糊总数。结构守卫还明确确认 BoundaryReport 留在根包，防止以后有人只看前缀又重复引入反向依赖。

第四层证据是行为回归。四个包内测试覆盖 sustainment 和 assurance 的主要输出：版本标签、endpoint、profile、条目数量、条目顺序、checks、readOnly、executionAllowed 与 status 都必须保持。第五层是历史 ratchet：此前 v1857 到 v1862 的结构测试可能因为目标类型进入同包而出现可预期的边界收缩，此时只允许根据真实 census 把数字向下调整，绝不能把上限提高或删除断言。最后运行 Spotless 和完整 `mvnw verify`，由单元测试、集成测试、JaCoCo 与 SpotBugs 一起证明迁移没有改变运行行为。只有本地完整门通过、实现提交推送且 GitHub Actions 绿色，本版才进入 closeout；账本、tag 和 closeout CI 也必须在下一版前闭合。

## mini-kv 证据检查

mini-kv 在这组 maintenance 响应中是只读证据对象，不是 Java 进程控制的从属服务。Java 可以说明 mini-kv 的 shard 字段、证据路径、命令输出形状和禁止写边界，却不能启动或停止它，不能修改 WAL、snapshot 或 fixture，也不能把本地静态文件当成刚刚执行的真实命令结果。`ShardFieldMapService` 对字段的描述必须保持原字节，`ReadWindowEvidenceService` 只声明允许的读取窗口，`RuntimeBoundaryChecklistService` 明确运行时不会建立写路由。包迁移不会访问 `D:\C\mini-kv`，不会移动其历史归档，也不会改动 Node 对上游 digest 的硬编码引用。

这种边界看起来保守，却是跨项目证据可信的基础。若 Java 在生成 readiness 响应时偷偷探测 mini-kv，那么同一个 GET 请求会因为本机进程、端口或文件权限不同而产生不稳定结果；更严重的是，它可能把“探测成功”误解为“允许执行”。当前机制把实时联调留给已经授权的 capstone 套件，把日常 ops 接口限制为可重复的契约说明。v1863 的验证因此只检查 Java 代码和冻结内容，不伪造 mini-kv 运行截图，也不为了讲解篇幅硬加无关命令。等最终 program-close 触发 capstone 重跑时，真实客户端输出由跨项目套件另行采集，并与这里的只读声明相互印证。

## 阻断与安全边界

本版最重要的安全属性是“证据不能升级成权限”。所有 maintenance 服务都通过 GET 暴露只读结果，响应中的 `executionAllowed` 保持 false；operator signoff 只是说明检查项已被覆盖，不是部署、回滚、SQL、重放失败事件或读取 secret 的批准。服务方法保持只读事务语义，不增加 Repository 写调用、消息发布、HTTP 客户端或进程启动。任何试图在迁移时顺便接入实时 endpoint、解析凭证值、创建 managed audit connection 或自动启动 Node/mini-kv 的改动，都超出结构版本边界并应立即回退。

另一个阻断点是可见性扩张。为了让跨包代码编译，最省事的坏做法是把 support、内部 record、所有常量和构造方法全部改成 public；本版反过来用真实读者决定边界。共同 support 与其内部 helper 留在包内，服务只公开 Controller 或既有消费者确实使用的方法，PROFILE 等内部常量不因为移动而暴露。SpotBugs 排除只能等量迁移完整类名，禁止加入通配符掩盖新问题。历史 fixture、route 字符串、响应期望和版本标签一字不改；若完整 verify 暴露边界变化，先解释真实原因并收紧 ratchet，不能编辑 fixture 让测试“看起来通过”。这些失败条件使结构优化具备可回滚、可复查的机械边界。

## 测试覆盖

focused 阶段首先运行 `OpsExtractionV1863Tests`，验证精确文件清单、根目录缺席、新包归属、Controller import、support 可见性、census 数字、讲解标题和中文字符数。随后运行四个迁移后的 maintenance 测试，它们直接构造服务并检查输出内容，能够捕获 package 调整中误删 import、构造器不可见或共享 support 行为漂移。再把受影响的历史 extraction 守卫加入 focused 集合，特别关注它们记录的 source、edge、target 数是否因同包化自然下降。下降是好事，但必须由当前文件扫描证明；不下降或上升都需要解释。

格式门在完整验证前执行，确保机械迁移没有留下错误缩进和 import 顺序。首轮完整 `mvnw verify` 实际运行一千八百七十三个测试，只发现 v1806 quality closeout 的 live 根包值仍是旧的 187，而真实 census 已是 174；这不是行为回归，也不是历史快照，正确处理是把当前状态常量单向收紧到 174，保留历史叙述，再跑 focused 修复和完整重验。最终 verify 必须在这篇补充完成后运行，避免出现“代码通过了，但最终文档从未被门检查”的旧问题。完整验证还要覆盖 JaCoCo floor、SpotBugs 零问题与 Spotless。远端采用两提交闭环：实现提交先推送并等待 Actions 绿色，账本再记录准确 commit 与 run id，随后创建 v1863 描述性 tag 并推送 closeout；下一版开始时复核 tag run。清理阶段删除本任务产生的临时输出，不留下后台 Java、Docker 或浏览器进程。

## 一句话总结

Java v1863 用一次可复现、可回滚且不改变任何路由与响应字节的闭包迁移，把十三个 RouteCleanup maintenance review 实现和四个真正属于它们的测试从拥挤根包归回既有实现包，让根 Controller 只承担 HTTP 适配、共享 support 继续保持包私有、九个临时 endpoint 完成可见性还债、BoundaryReport 因真实反向依赖而诚实留待下一批、跨项目证据继续只读、根包 ratchet 从一百八十七收紧到一百七十四，并以精确 inventory、行为回归、边界 census、完整 Maven 门和远端 CI 共同证明这次变化是结构治理而不是权限或功能漂移。
