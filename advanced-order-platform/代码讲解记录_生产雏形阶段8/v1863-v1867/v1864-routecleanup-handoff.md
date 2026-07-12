# v1864 RouteCleanup Handoff 闭包迁移代码讲解

## 实际工作量说明

这一版不是增加一个新的业务接口，而是把已经存在、已经被大量测试覆盖的 RouteCleanup 交接链从根包搬回它真正所属的实现包。迁移对象共有二十二个生产类型，正好是十一个服务与十一个响应记录；同时迁移十一份只验证这些服务的单元测试。三个对外控制器继续留在 `ops` 根包，因为它们承担的是 HTTP 适配职责，而不是证据计算职责。这样处理后，浏览根包时看到的是入口、全局路由聚合器和明确批准保留的共享对象，不再把内部编排服务误认为公共入口。

本版还同步处理四类容易在“只改 package”时漏掉的问题。第一类是十一条路由后缀的所有权，它们从全局 `OpsShardReadinessRoutePaths` 转移到 `RouteCleanupRoutes`，字符串逐字节保持不变。第二类是根包十个调用方的显式导入，迁移后编译器必须能准确说明谁依赖谁。第三类是十个带集合字段的响应在 SpotBugs 排除表中的全限定名，两个镜像块都必须跟随新包名。第四类是根包数量、剩余可迁移数量和 RouteCleanup 数量的只减不增门：预期分别从一百七十四降到一百五十二、从七十降到四十八、从六十六降到四十四。这里没有通过删除能力换取数字，也没有新增转发壳；总生产类型数保持不变。

讲解只围绕本项目这次真实发生的依赖、数据和验证变化，禁止硬凑与实现无关的背景段落；如果这些机制不足以支撑篇幅，正确动作应是扩大工程工作量，而不是重复结论。

## 入口路由

调用方输入仍然是无请求体、无凭据值、无执行参数的 HTTP GET。交接控制器继续暴露 suite closeout、archive verification、consumer packet、handoff bundle、consumer checklist 和 extended closeout；治理控制器继续暴露 CI evidence 与 regression guard；摘要控制器继续暴露 endpoint manifest、continuity report 和 final digest。控制器的类、构造器、方法和 Spring 注解不迁移，只把字段类型与返回类型显式导入新的实现包。因此请求进入系统的第一跳完全不变，Spring 扫描位置也不变。

路由输入可理解成两部分：共同前缀 `/api/v1/ops/shard-readiness`，以及十一条固定后缀。v1864 把后缀的代码所有权交给 `RouteCleanupRoutes`，控制器注解和服务的 `ENDPOINT` 都读取同一个族内数据源。以前全局表同时承担“全局入口索引”和“族内实现常量”两项职责；迁移后全局表只保留仍有根包调用者的内容，族内表负责 RouteCleanup 自己的数据。输出 URL 没有变化，例如 `/route-cleanup-ci-evidence`、`/route-cleanup-endpoint-manifest` 和 `/route-cleanup-extended-closeout` 仍与旧版本完全一致。路由测试会按常量名和字节值逐项比对，并确认全局表不再重复保存这十一项。

## 响应模型

每个服务返回一个不可变 record。record 的包名变化只影响 Java 源码导入，不影响 Jackson 生成的 JSON 字段名、字段顺序和嵌套结构。比如 ArchiveVerification 响应仍包含项目、版本、只读标志、执行许可、两个上游端点、检查数量、检查列表和状态；HandoffBundle 仍用组件列表表达 consumer packet、CI evidence 与 regression guard 三项结果；ExtendedCloseout 仍汇总四类最终证据并给出 decision 与 status。没有字段增删，也没有把列表换成可变集合。

十个响应含有列表或嵌套组件，SpotBugs 对其不可变边界已有经过审查的 `EI_EXPOSE_REP` 排除。迁移时只改这十个排除项的类全名，而且配置中前后两个镜像块都必须恰好出现一次新全名、零次旧全名。ContinuityReport 不携带需要该排除的集合，因此不凭惯性新增 waiver。结构门会逐个检查这件事，避免“编译通过但静态分析实际跳过了错误类”的假绿。

## 上游证据配置

这十一项能力并不是各自读取一套外部配置，而是组合 v1857 以来已经进入 `ops.maintenance.routecleanup` 的只读证据内核。SuiteCloseout 读取 release handoff、read-only gate 和 digest；ArchiveVerification 在 archive plan 与 suite closeout 之上形成检查项；ConsumerPacket 再把 read-only gate 与 archive verification 包成消费者入口。另一支从 CI evidence 和 endpoint manifest 出发，RegressionGuard 比较清单与 CI 声明，HandoffBundle 把它与 ConsumerPacket 合并。ContinuityReport 把 endpoint manifest 与 phase summary 对齐，ConsumerChecklist 组合 packet 与 continuity，FinalDigest 对版本、条目数、边界状态和五个固定端点做 SHA-256，ExtendedCloseout 最后汇总 bundle、checklist、digest 与 continuity。

这里的“配置”主要是不可变路由、profile 标识、证据目录和版本连续性规则，不是生产密钥或可执行地址。`OpsShardReadinessRouteCleanupEvidenceAnalyzer` 只读取仓库内的证据目录，检查 Java 版本连续、所有条目保持只读、没有启动 Java 或 mini-kv、没有读取 credential value、没有解析 raw endpoint、没有打开 managed audit 连接，也没有改变 write routing。任何一项为假，统一边界状态就从 `passed` 变为 `blocked`。迁移不改变这些判断，只让调用图在包结构上与实际职责一致。

## 服务层核心流程

从最底层看，CiEvidenceService 生成四个必需验证步骤：聚焦测试、完整 Java 套件、主分支 GitHub Actions 和清理门。它输出的是“必须检查什么”的只读清单，不会替调用方执行命令。EndpointManifestService 通过反射读取两个路由所有者：全局表中仍以 `ROUTE_CLEANUP_` 开头的字段，以及族内 `RouteCleanupRoutes` 除 `BASE_PATH` 外的字段。族内字段在输出时重新补上 `ROUTE_CLEANUP_` 名称前缀，所以迁移前后的 manifest 名称、排序和完整 URL 相同。这也是为什么路由常量不能简单删除，必须先转移所有权并让双表扫描继续成立。

RegressionGuardService 获取 manifest 和 CI evidence 后，逐项验证路由数量、唯一性、只读标志、执行禁止标志以及 CI 步骤是否齐全。HandoffBundleService 再把 ConsumerPacket、CiEvidence 和 RegressionGuard 三个结果规整成组件列表，只有全部组件状态为 `passed` 才给出可交接决策。ContinuityReportService 对照 endpoint manifest 与 phase summary，确认路线总量和历史阶段连续；ConsumerChecklistService 把 packet 与 continuity 拆成面向消费者的检查项。FinalDigestService 不读取网络，也不遍历用户输入，而是把当前 Java 版本标签、证据条目数量、统一边界状态以及五个端点按固定顺序连接，再计算 SHA-256。固定顺序意味着任何字符串或顺序变化都会改变摘要，因此完整测试天然承担了响应兼容性哨兵。

最后，ExtendedCloseoutService 依次调用 handoff bundle、consumer checklist、final digest 和 continuity report。输入是上述四个服务的不可变返回值，输出是包含版本范围、证据数量、证据列表、决策和状态的最终记录。整个流程只有读事务注解，没有 repository 写入、消息发布、HTTP 客户端或进程启动。迁移后的包内调用不需要扩大公共表面；只有仍被根包后续实现读取的三个 `ENDPOINT` 暂时公开，分别是 CiEvidence、EndpointManifest 和 ExtendedCloseout。其余八个 ENDPOINT 与全部 PROFILE 保持包内可见，后续 RouteCleanup 全闭包迁移完成时，这三个临时公开项也可以偿还。

## Java 证据检查

Java 侧首先用编译器验证包边界：二十二个类型必须只存在于新目录，十一个测试必须与实现同包，根控制器必须通过显式 import 使用它们，迁移包不得反向 import 根包中的 RouteCleanup 实现。随后运行服务行为测试，逐项比对项目名、版本、只读与执行标志、端点、profile、检查项顺序、状态、摘要格式和决策文本。路由测试验证十一条后缀的字节值和 ENDPOINT 拼接结果；结构测试验证十个根调用源、三十八条类型边、二十二个目标类型以及三个公开 ENDPOINT 的精确边界。

局部门通过后还要运行 v1857 至 v1863 的历史边界测试，因为把调用者搬进同一实现包会让旧版本记录的“外部读者数量”下降。正确做法是收紧这些实时上界，而不是改宽断言。Spotless 检查格式，SpotBugs 检查新全限定名，JaCoCo 检查既有覆盖率下限。最终 `mvnw verify` 必须在本篇讲解已经固定后执行；本地成功只说明候选提交成立，推送后的 headless、prod smoke 和 Docker-tagged 两个远端作业都成功，才允许写入版本完成状态。

## mini-kv 证据检查

本版不会启动、停止或修改 mini-kv。RouteCleanup 历史条目中出现 mini-kv，只表示 Java 保存并核对跨项目只读证据边界，不表示 Java 获得了控制 C++ 进程的能力。EvidenceAnalyzer 对每条记录都要求 `startsMiniKvService=false`，并把“Node 启停 Java 或 mini-kv”列为明确禁止操作。输入是仓库中已经冻结的证据描述，输出只是连续性与边界状态；不存在 socket 连接、CLI 调用、WAL 写入、快照生成或 shard 路由更新。

因此 mini-kv 侧的正确证据不是“本版跑了一次 C++”，而是 Java 的迁移没有改变任何跨项目契约字节，也没有把历史只读描述误写成实时执行能力。相关字段、禁止操作列表和最终 `passed/blocked` 算法由原有测试继续覆盖。真正的四项目实时联测仍由独立 capstone 命令负责，不能在一个 Java 包迁移版本里偷换成自报完成。

## 阻断与安全边界

最重要的阻断条件是依赖方向。若任一迁移服务仍引用根包中的 RouteCleanup 实现，v1857 建立的 no-reverse-edge 门会失败；这时必须扩大到真实闭包或缩小本版范围，禁止通过公开一个根包实现、复制一个适配壳或删除测试来绕过。v1864 的二十二个类型已按调用图验证为闭包，它们只依赖同批类型、既有 routecleanup 包类型、全局路由基础前缀和 JDK/Spring 只读设施。根包可以向实现包依赖，实现包不能向根实现倒流。

运行安全边界同样保持原样。所有服务继续使用 `@Transactional(readOnly = true)`；响应中的 `readOnly` 为真、`executionAllowed` 为假。代码不接受 credential value，不解析 raw endpoint，不实例化 secret provider，不创建 managed audit HTTP/TCP 连接，不写 ledger，不发布 RabbitMQ 消息，不切 active shard router，不执行 deployment、rollback 或 rollback SQL。EndpointManifest 的反射范围只读取静态字符串字段，不调用字段所描述的端点。FinalDigest 只在内存中计算摘要。任何测试若发现写行为、路由字节变化、清单数量变化或权限扩大，都必须阻断版本，不能用 fixture 改写来迁就实现。

## 测试覆盖

十一份服务测试随实现迁入包内，原因不是整理目录好看，而是让测试直接覆盖包内可见的 ENDPOINT、PROFILE 和构造协作关系，同时避免为了测试把内部字段公开。保留在根包的控制器结构测试继续通过反射读取 `@GetMapping`，证明入口仍分布在 Handoff、Governance 和 Summary 控制器，且旧 URL 全部存在。根 `OpsShardReadinessRoutePathsTests` 删除已经不再由全局表拥有的十一项，族内 route test 增加同样的键值对；一删一加共同证明“所有权移动但字节不变”。

新增的 v1864 结构测试是本版的需求证据矩阵执行器。它检查文件存在位置、测试存在位置、路由字段修饰符、旧字段缺失、根适配器 import、反向依赖为空、SpotBugs 双镜像、外部类型边界、ENDPOINT 可见性、根包精确数量、总 ops 类型上限、census 文档以及讲解门。失败信息带文件名或服务名，后续维护者可以直接定位，而不是只看到一个模糊布尔值。聚焦测试通过后仍运行完整 Maven 生命周期，覆盖 Spring 上下文、数据库、失败事件、Outbox、所有历史 ops 契约、静态分析和格式。最终远端 CI 再在干净 checkout 中重复，排除本地缓存或未跟踪文件造成的假象。

## 一句话总结

v1864 的输入是根包中相互闭合的十一组只读交接服务、既有族内证据内核和固定路由字节，输出是职责清楚的 `ops.maintenance.routecleanup` 实现边界、保持原样的 HTTP/JSON/摘要契约、缩小到一百五十二个文件的根包，以及能够在依赖、可见性、路由、静态分析、覆盖率和远端 CI 任一回退时立即失败的机械证据；它只搬清责任，不扩大能力。
