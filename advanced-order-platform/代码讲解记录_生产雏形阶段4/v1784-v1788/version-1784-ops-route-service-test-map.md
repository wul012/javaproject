# 第一千七百八十四版代码讲解：路线服务测试地图与根包压力地图

本版开始新的 Java 后期保养五版。上一批 v1779 到 v1783 已经把 `docs/ops` 索引、`ops.maintenance.readability` 子包、registry 模板、长类名试点和收口状态立起来；但站在维护者角度，仍有两个问题没有被清楚回答。第一，看到一个只读运维路由时，应该从哪一个 Controller、Service、Response、测试类一路追下去。第二，旧的 `ops` 根包已经有很多历史证据类，后续新增保养功能到底应该继续放根包，还是进入新子包。v1784 的工作就是先把这两个问题写成地图，再让后续四版按地图落地。

这版只做 Java 本项目。没有读取 Node 计划，没有修改 mini-kv，没有启动 Java 服务，也没有连接数据库。它的产物是 `docs/ops/route-service-test-map.md`、`docs/ops/root-package-pressure-map.md`、`docs/ops/README.md` 的导航补齐，以及当前批次讲解目录。它看上去是文档版，但不是泛泛写说明，而是在后续要新增只读审计 registry 之前，先把“路线、服务、测试、包边界”固定下来。这样后续实现时就不会一边写代码一边猜目录和测试归属。

## 入口路由

v1784 没有新增 HTTP 入口。当前运行时入口仍然只有 `/api/v1/ops/readability/upkeep-registry`，由 `ReadabilityUpkeepRegistryController` 暴露，由 `ReadabilityUpkeepRegistryService` 组装响应，由 `ReadabilityUpkeepRegistryResponse` 表达结构。这个事实被写进 `route-service-test-map.md`，让维护者不必先在整个 `ops` 目录里搜索。后续新增 `/api/v1/ops/readability/upkeep-audit` 时，也会先遵守这个地图约束：路由常量必须在 `ReadabilityUpkeepRoutePaths`，控制器和服务必须在 `ops.maintenance.readability` 子包，测试必须覆盖路径、服务、渲染、边界、控制器和文档。

把入口路由写入地图的原因很直接。这个项目已经有大量 `OpsShardReadiness*` 历史入口，它们多数仍然稳定且有价值，但名字长、数量多、分布广。维护者如果只靠 IDE 搜索，很容易先读到旧根包里大量无关类。v1784 用一张小表把当前可读性保养路线钉住：先看 route，再看 controller，再看 service，再看 response，最后看测试。这不是替代源码，而是给源码建立读法。

本版还补齐 `docs/ops/README.md` 的入口表。上一批 README 已经列出 shard readiness map、walkthrough registry map、archive layout map 和 class name trial，但它没有把 `registry-template.md` 写进表里，也没有路线服务测试地图和根包压力地图。v1784 补上后，维护者从一个入口就能看到主题地图、模板、类名试点、路线映射和包压力。这样的索引更像后期保养的目录，而不是几篇零散说明。

## 响应模型

本版不改 `ReadabilityUpkeepRegistryResponse`。这是一个有意保持稳定的决定。v1784 的问题不在响应字段，而在“响应从哪里来、怎么被测试、后续新增响应应放哪里”。如果为了文档地图而给现有 response 增加字段，就会把低风险保养变成运行时 schema 变化，反而增加后续验证成本。

地图文档里已经提前说明后续 audit route 需要自己的 response record。也就是说，v1785 可以新增 `ReadabilityUpkeepAuditResponse`，而不是把 audit 字段塞进已有 registry response。这个分离很重要：registry response 负责说明当前 readability upkeep 的规则和模板，audit response 则负责说明路线、根包压力、边界、验证和维护周期。两个响应都只读，但职责不同。

保持响应模型不动，也说明本版不是靠“改字段”制造版本感。文档地图承担的是结构导航责任。它告诉后续实现：新增模型要小而清楚，字段要从 catalog 和 support 计算，renderer 只做 markdown section 展示，service 只组装，不读外部项目，不启动任何进程。响应模型的稳定反而让这版的工程意图更清晰。

## 上游证据配置

本版的上游证据来自本项目上一批已经沉淀的文件，而不是外部计划。`docs/ops/registry-template.md` 规定新 registry 需要 route path、response、catalog、renderer、support、service、controller 和测试层。`docs/ops/class-name-trial.md` 规定新增 readability 子包可以去掉重复长前缀，但不能对历史根包做大规模重命名。`ReadabilityUpkeepDocsTests` 和 `OpsCodeWalkthroughArchiveComplianceTests` 则把文档存在性、标准标题、中文长篇和实际工作量写成门禁。

`route-service-test-map.md` 把这些证据串起来。它列出当前 registry route 的 controller、service、response 和主要测试，并为未来 audit route 写出同样的模板。这个做法避免后续版本凭感觉新增接口。每一个新 route 都要有 route constant、response、catalog、renderer、support、service、controller，以及对应测试。若哪一层缺失，后续 docs guard 就可以明确失败。

`root-package-pressure-map.md` 则处理另一个上游信号：旧 `ops` 根包已经承载很多历史类。它没有要求马上迁移，因为历史类涉及已发布路由、旧测试和讲解证据；它只要求新增 maintenance readability 工作优先进入新子包。这个原则来自本项目现状，不是抽象洁癖。历史稳定和未来可读性需要同时保留，不能为了目录漂亮破坏已验证路径。

## 服务层核心流程

v1784 没有新增 service 方法，但它为下一版 service 流程预先定了形。后续 audit service 应该与 registry service 类似：先从 catalog 拿静态事实，再交给 renderer 生成 markdown section，再由 support 计算 count、checks 和 status，最后返回 response。Service 本身不应该做文件扫描、端口访问、外部 HTTP 调用或进程启动。

这条流程写进地图后，后续代码就可以更干净。比如路线服务测试地图会要求 `ReadabilityUpkeepRoutePaths` 增加 `UPKEEP_AUDIT`，而不是在 controller 注解里手写字符串；根包压力地图会要求新增类留在 `ops.maintenance.readability`，而不是回到 `com.codexdemo.orderplatform.ops` 根包；测试地图会要求 controller test 和 service test 同时存在，避免只测其中一边。

从维护角度看，本版真正做的是“把隐含流程显式化”。上一批代码已经证明 registry 模板可行，但如果没有地图，后续开发者仍可能绕开模板。v1784 把模板和当前 route 关系写到文档中，等 v1786 新增 audit controller 时，读者能看到它不是孤立新增，而是按同一流程落地。

## Java 证据检查

Java 证据第一层是文档文件本身。`docs/ops/route-service-test-map.md` 包含当前 route、controller、service、response、primary tests，以及计划中的 audit route 层次。它没有把所有历史 ops endpoint 都搬进表格，因为那会让地图再次臃肿；它只服务 readability upkeep 主题。`docs/ops/root-package-pressure-map.md` 则把 root package、code walkthrough depth、readability upkeep、archive layout 四类压力拆开，说明哪些保留、哪些新增要进子包。

第二层证据是 README 导航。新增地图如果不从 README 链出，就会变成又一个孤立文件。v1784 把 `registry-template.md`、`route-service-test-map.md` 和 `root-package-pressure-map.md` 都写入地图表，让入口完整。后续维护者不需要知道文件名，也可以从 README 看到它们。

第三层证据是当前批次目录。`代码讲解记录_生产雏形阶段4/v1784-v1788/README.md` 记录这五版的边界：只做本项目 Java，禁止打开高风险执行面，不触碰 Node、mini-kv 或其他项目。这个 README 不替代每版讲解，但它让归档目录不会像散装文件夹一样膨胀。用户之前明确要求截图和讲解不要再挤入一个文件夹，可以自己建文件夹来放图片和解释；这里延续同样思路，用批次目录承载讲解。

## mini-kv 证据检查

mini-kv 在本版中仍是明确不触碰的边界。路线服务测试地图里写明每条路线都是 read-only，并排除 mini-kv autostart。根包压力地图也不把 mini-kv 的 C++ 结构、slot table、read-only shard map 或 fixture 纳入 Java 保养范围。这样做可以避免 Java 后期保养因为跨项目牵连而变得不可收束。

保留 mini-kv 说明不是形式主义。过去用户已经多次强调 Java 和 C++ 可以较自由推进，但不要让 Node 卡住它们，也不要提前打开 active shard router、write routing、credential value、raw endpoint、managed audit connection、deployment 或 rollback。v1784 的地图把这个边界写在文档里，后续 audit response 也会把这些禁用项结构化。文档先行，是为了让代码实现时不会忘记边界。

本版没有读取 mini-kv 工作区，没有创建进程，没有运行端口，没有拿 fixture，也没有把 mini-kv 的计划当成 Java 的测试输入。所有新增内容都能在 Java 仓库内独立审查。这对 CI 很重要，因为 CI 只拿 Java 仓库，并不会有用户本机的四项目目录。

## 阻断与安全边界

v1784 明确不做写路径。没有 write routing，没有 active shard router，没有 credential value read，没有 raw endpoint URL resolution，没有 managed audit HTTP/TCP connection，没有 deployment，没有 rollback，没有 Java autostart，也没有 mini-kv autostart。新增的两张地图都是 Markdown 文件，README 只是链接它们，讲解只是解释本版工作量。

另一个边界是“不做 bulk rename”。根包压力地图承认旧 `ops` 根包压力很大，但它没有要求马上迁移历史 `OpsShardReadiness*` 类。原因是这些类仍然有已发布路由和测试，它们不是无用代码。后期保养应该先让新增代码进入更合适的子包，再逐步为旧类建立迁移证据。没有证据的重命名会让版本历史和讲解都变难读。

第三个边界是“不把文档当执行脚本”。地图里出现的 route、controller、service、test 名称只是读法和约束，不会被运行。后续如果新增 audit route，也必须通过 Java 测试证明，而不是依赖文档说法。文档提供方向，测试提供门禁，代码提供行为，这三层不能互相替代。

## 测试覆盖

v1784 本身不新增测试类，因为它先补文档地图和批次归档入口。更合理的节奏是让 v1785 到 v1786 新增 audit 模型和服务，v1787 再加入 docs guard，把 `route-service-test-map.md`、`root-package-pressure-map.md` 和维护周期文档纳入测试。这样测试会验证实际落地后的完整结构，而不是在路线还没出现时提前写死。

尽管本版暂不加测试类，它仍受 `OpsCodeWalkthroughArchiveComplianceTests` 约束。由于版本号大于 v1773，本讲解必须使用标准标题、中文为主、至少三千个汉字、包含 `## 实际工作量说明`，并说明本项目真实工作量和禁止硬凑。后续本地验证会运行 `mvn -q "-Dtest=ReadabilityUpkeep*Tests,OpsCodeWalkthroughArchiveComplianceTests" test`，再运行全量 `mvn -q test`。

本版也为后续测试留了明确断点。`ReadabilityUpkeepDocsTests` 将在 v1787 检查 README 包含新增地图，检查 route-service-test-map 里有 registry route 和 audit route，检查 root-package-pressure-map 里有 subpackage 和 bulk rename 边界。这样文档不会只靠人工记忆维护。

## 实际工作量说明

本版的实际工作量集中在可维护阅读路径，而不是运行时功能。它新增了路线服务测试地图，让 `/api/v1/ops/readability/upkeep-registry` 的 controller、service、response、测试关系被一眼看见；它新增根包压力地图，让历史 root ops 类和新增 readability 子包的边界被写清；它补齐 docs/ops README，避免 `registry-template.md` 和新增地图变成孤立文件；它新增当前五版批次 README，避免讲解目录继续无结构膨胀。

这不是硬凑。用户要求从今往后中文代码讲解至少三千字，字数不够就加大每版工作量，禁止硬凑。v1784 的加厚不是把一句话重复很多遍，而是补了后续四版会依赖的真实文档层。没有这两张地图，v1785 新增 catalog 时不知道该表达哪些 route-service-test 事实，v1786 新增 controller 时也没有文档说明为什么必须在子包里，v1787 的 docs guard 更缺少可守护对象。

本项目现在的问题不是缺少某个花哨功能，而是版本很多、ops 证据很多、讲解很多，如果继续只按功能点堆类，维护者会迷路。v1784 选择先处理“读法”，这正是工程后期保养应该做的事情。读法稳定后，新增 audit response 才能成为可理解的结构，而不是又一个孤立 endpoint。

这版也有意识控制了改动范围。它没有碰业务订单模型，没有碰数据库迁移，没有碰消息队列，没有碰审批、outbox、失败事件重放，也没有改已有 registry response。所有变化都在 docs、讲解目录和 README 导航内。这个范围让版本可以独立提交、独立回滚，也让后续代码版可以基于它继续扩展。

根包压力地图尤其重要。旧根包里有许多 `OpsShardReadiness*` 类，它们不是坏代码，只是历史积累很重。如果一上来就重命名和迁移，会影响测试、讲解、tag 和用户对证据链的理解。v1784 没有这么做，而是写下迁移纪律：未来移动旧类必须先有 route map、test map、response compatibility check、docs link 和中文讲解。这个纪律比一次激进重构更符合长期维护。

路线服务测试地图也不是简单表格。它把当前 route 的四层关系明确为 controller、service、response、primary tests。后续 audit route 会按相同形状补齐。这样每个新 endpoint 都自然带着测试约束，不会出现只有 controller 没有 service test，或者只有 service 没有 boundary test 的松散状态。对于已经有大量版本的项目，这种小表格可以显著降低审查成本。

本版还把 “read-only” 边界写入每个地图。很多 ops endpoint 名字听起来像治理操作，维护者容易误以为可以触发连接、审计或执行。v1784 反复写明这些地图只是文档，不启动 Java、mini-kv、Node、Docker、浏览器或 managed audit connection，不读取 credential value，不解析 raw endpoint URL，不部署也不回滚。这个边界会被后续 response 字段继续结构化。

从五版节奏看，v1784 是打地桩。v1785 可以新增 audit response 和 catalog foundation，v1786 可以暴露 read-only audit registry，v1787 可以用 docs guard 守住新增地图，v1788 可以收口版本和批次验证。每一步都有可交付对象，不需要用很小粒度凑版本。这个批次如果完成，Java 项目的后期可读性会从“有索引”推进到“有可审计索引”。

本项目的讲解归档也得到改善。过去大量版本讲解集中在同一类目录中，用户已经指出不要再挤在一个文件夹里。v1784 延续 `代码讲解记录_生产雏形阶段4/v1784-v1788` 的分段方式，把五版放在一个清晰批次下，并用 README 说明边界。以后如果继续推进，可以继续按版本段分目录，而不是把所有讲解堆在根目录。

禁止硬凑也体现在测试规划上。v1784 没有为了显得“功能更多”而立即写一个空测试。更好的工程方式是先有文档对象，再在 v1787 让测试守住它们。测试应该验证真实稳定的对象，而不是提前锁死尚未实现的 audit route。这个节奏让每版都有实际任务，也让版本之间形成自然依赖。

最终，v1784 让维护者在读 Java ops readability 时有三条路。第一条，从 README 到主题地图，理解当前证据领域。第二条，从 route-service-test map 到具体 controller/service/response/test，理解运行时只读入口。第三条，从 root-package-pressure map 到子包纪律，理解为什么新代码不再堆进旧根包。这三条路合起来，就是本版的实际价值。

## 一句话总结

v1784 没有新增运行时入口，而是用路线服务测试地图、根包压力地图、README 导航和批次归档把 Java 本项目后续可读性审计的读法先固定下来，为 v1785 到 v1788 的只读审计 registry、文档门禁和收口验证提供真实地基。
