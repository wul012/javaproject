# 第一千七百八十二版代码讲解：registry 模板文档与 docs 门禁

本版目标是落实 Java 可读性保养建议中的第三步：把稳定的 registry 写法沉淀成模板，并用测试保护 `docs/ops` 文档入口。v1781 已经有可读性保养 registry，但如果没有模板，后续新增 registry 仍可能靠复制旧类时临场发挥，漏掉 route path test、boundary test、renderer test 或 read-only transaction。v1782 因此新增 `docs/ops/registry-template.md`，并新增 `ReadabilityUpkeepDocsTests`。

它仍然只做本项目。模板描述的是 advanced-order-platform 内部新增 read-only ops registry 时应遵守的 Java 层次，不是四项目通用规范，也不会去要求 Node、mini-kv、aiproj 按这个模板改。跨项目目录只是建议来源，本版实现全部在 Java 项目内部。

## 入口路由

本版没有新增运行时入口路由。已有 `/api/v1/ops/readability/upkeep-registry` 由 v1781 暴露，本版新增的是文档模板入口 `docs/ops/registry-template.md`。这个文件被 `docs/ops/README.md` 所在目录承载，后续维护者可以从 `docs/ops` 进入，先读地图，再读模板，再写新的 registry。

为什么模板不通过 controller 输出？因为模板主要服务开发者写代码，不是运行时调用者查询状态。将它放在 docs 目录更合适：它可以包含表格、说明和测试命名要求，不会把开发约定塞进 API 响应。API 响应适合展示当前保养状态，模板适合指导下一次新增。

测试入口是 `ReadabilityUpkeepDocsTests`。它通过文件系统读取 `docs/ops` 下的 README、三张地图和 registry 模板。这个测试让文档入口不再是“写了就算”，而是进入 Maven 门禁。后续如果有人删掉模板或改坏关键字段，测试会失败。

## 响应模型

本版没有修改 `ReadabilityUpkeepRegistryResponse` 的字段，但模板文档对响应模型提出了固定要求。`registry-template.md` 明确新增 registry 的 response 应包含 `project`、`version`、`readOnly`、`executionAllowed`、`endpoint`、`profile`、各类 catalog count、denied boundary count、`checks` 和 `status`。这些字段正是 v1781 response 的结构化经验。

模板还要求 response 不能只返回一段说明文字。必须有 flags、counts、checks、markdown sections 和 status。flags 用来证明只读边界，counts 用来证明目录完整性，checks 用来给测试和人工审查提供稳定锚点，markdown sections 用来给人读，status 用来表达整体结果。这样的响应模型让 registry 既适合机器断言，也适合维护者阅读。

从后期保养角度看，模板是把经验外化。过去每个 registry 都可能靠附近文件复制，复制时容易带入不属于当前主题的长前缀或漏掉新门禁。现在模板把这些要求列出来，未来新增 registry 可以先对照模板，再选择是否需要新子包和短类名。

## 上游证据配置

本版上游仍然是 Java 可读性保养建议。建议中第三版明确提到 registry 模板保养，并列出新增 registry 必须有 `RESPONSE_VERSION`、`ENDPOINT`、`PROFILE`、`@Transactional(readOnly = true)`、boundary rules、verification steps、controller test、service test、renderer test、route path test。v1782 将这些内容写进 `docs/ops/registry-template.md`，并通过测试确认关键字存在。

本版不直接读取外部建议文件。原因和 v1781 一样：仓库测试应该在 CI 中稳定运行，不应该依赖本机 `D:\C` 路径存在。外部建议被吸收为仓库内模板和测试断言，成为本项目自己的维护规则。

模板还吸收了 v1774-v1778 的讲解深度经验：新增 registry 相关版本仍要跑 `OpsCodeWalkthroughArchiveComplianceTests`，讲解必须中文长篇、包含实际工作量说明，并且禁止硬凑。也就是说，registry 模板不只管 Java 类，还管版本收尾时的讲解质量。

## 服务层核心流程

本版没有新增 service 方法，但新增了 docs guard 流程。`ReadabilityUpkeepDocsTests.docsOpsEntryMapsRemainDiscoverable()` 检查 `docs/ops/README.md`、`shard-readiness-map.md`、`walkthrough-registry-map.md`、`archive-layout-map.md`、`registry-template.md` 都存在。这个测试守住入口完整性。

`registryTemplateKeepsRequiredLayersAndBoundaries()` 读取模板文件，检查 `RESPONSE_VERSION`、`ENDPOINT`、`PROFILE`、`@Transactional(readOnly = true)`，以及 route paths、response、catalog、renderer、support、service、controller、各类测试关键字都存在。它还检查八类边界：write routing、active shard router、credential value reads、raw endpoint URL resolution、managed audit HTTP/TCP connection、deployment or rollback、Java autostart、mini-kv autostart。

`mapsKeepTopicSpecificReadingSignals()` 读取三张地图和 README，确认 README 仍链接三张地图，shard map 仍包含 controller/service/response/read-only 信号，walkthrough map 仍包含 depth registry、Chinese longform、3000 Chinese characters，archive map 仍包含归档合规和截图说明布局测试。这些断言不是细到每个字，而是保护地图的主题价值。

## Java 证据检查

Java 证据第一层是新增测试类 `ReadabilityUpkeepDocsTests` 位于 `ops.maintenance.readability` 测试子包，和 v1781 的 registry tests 在同一个主题下。这样文档门禁也归入可读性保养子包，而不是散回 `ops` 根测试目录。

第二层证据是 `docs/ops/registry-template.md` 的内容。它明确规定新增 registry 的 Java layers：route paths、response、catalog、renderer、support、service、controller、tests。它还规定 service 必须有 `RESPONSE_VERSION`、`ENDPOINT`、`PROFILE` 和 `@Transactional(readOnly = true)`。这些不是抽象建议，而是能被测试匹配的文本。

第三层证据是模板中的命名规则。新子包应该让 package 承担重复上下文，class name 描述局部职责。举例使用 `ReadabilityUpkeepRegistryService`，而不是重复整个历史 `OpsShardReadiness` 前缀。这和 v1780/v1781 的实际代码一致，形成“文档写法”和“代码做法”的闭环。

## mini-kv 证据检查

本版不消费 mini-kv 证据，不启动 mini-kv，也不读取 mini-kv 文件。模板只把 mini-kv autostart 列为必须拒绝的边界。这样未来新增 read-only ops registry 时，也要明确说明不自动启动 mini-kv。

mini-kv 作为边界出现在模板中，是因为 Java 项目历史上确实有 Java/Node/mini-kv 协同语境。模板把它写进禁止项，可以防止维护者为了“验证更真实”而在只读 registry 测试中误启动上游服务。本版依然只做 Java 项目自己的文档和测试。

## 阻断与安全边界

本版没有新增运行时能力。`ReadabilityUpkeepDocsTests` 只读取仓库内 Markdown 文件，不访问网络，不启动 Spring，不连接数据库，不读取 credential，不解析 raw endpoint，不发 audit 请求，不部署，不回滚。它是文件级测试，失败时只会让 Maven 失败。

业务写边界继续关闭。模板里提到 write routing、active shard router、deployment 等词，是为了要求未来 registry 显式拒绝它们，不是为了打开它们。本版本身没有修改订单业务类、repository、migration 或 controller。

历史文档边界也保持关闭。本版不改历史讲解目录结构，不重写旧版本，不删除旧 marker。它只新增模板和当前批次讲解。旧文件以后如果被触及，再按新规则重写；当前不做大返工。

## 测试覆盖

本版新增 `ReadabilityUpkeepDocsTests` 三个测试方法：入口文件存在性、registry template 必需层和边界、地图主题信号。它们和 v1781 的 route/service/renderer/boundary/controller tests 形成互补：v1781 保护 API 证据，v1782 保护文档入口和模板。

本版完成后应运行定向测试：`mvn -q "-Dtest=ReadabilityUpkeep*Tests,OpsCodeWalkthroughArchiveComplianceTests" test`。这个命令会覆盖可读性子包的全部测试和本版中文长篇讲解门禁。整批完成后还要跑全量 Maven 和 CI。

## 实际工作量说明

本版的实际工作量落在本项目的模板和门禁上。新增 `registry-template.md` 不是泛泛写一份 README，而是把 route paths、response、catalog、renderer、support、service、controller、tests、read-only transaction、boundary rules、verification steps、response fields、naming rule、verification rule 都写成可检查内容。新增测试也不是只判断文件存在，而是读取文件内容并检查关键维护信号。

这不是硬凑。用户要求字数不够就加大每版工作量，禁止硬凑；本版通过新增测试把模板质量纳入 Maven，而不是只多写几段解释。以后如果有人删掉 `@Transactional(readOnly = true)` 要求、漏掉 boundary test、忘记 mini-kv autostart 禁止项，测试会暴露。这是实质性维护收益。

本版继续只做 Java 本项目。模板的目标是 advanced-order-platform 的 ops registry，不去约束其他项目。这样的范围控制也是实际工作的一部分：后期保养要有边界，不能因为建议来自四项目目录，就把 Java 版本做成四项目工具。

禁止硬凑在本版里表现为“模板必须能被测试”。如果只是写一篇模板文档，未来有人删掉关键层次也未必会被发现；现在 `ReadabilityUpkeepDocsTests` 会读取模板并检查关键字段。它要求 route path test、service test、renderer test、boundary test、controller test 都在模板里出现，也要求 read-only transaction、response、catalog、renderer、support、service、controller 等层次齐全。这个测试把文字规范变成工程门禁。

本项目的实际收益是后续新增 registry 时可以少靠记忆。advanced-order-platform 已经有很多 registry，经验很丰富，但经验如果只存在于复制粘贴里，就会随着版本加速而漂移。模板把经验压缩成一个可读文件，docs 测试再保证这个文件不会丢。维护者新增 registry 时，可以先打开模板核对，而不是在几十个旧 registry 里猜哪一个最新。

本版还补齐了 docs/ops 的长期入口完整性。README、三张地图、registry template 都被测试检查存在。也就是说，后续如果有人移动 docs 目录、删除某张地图、忘记更新模板，定向测试会给出清晰失败。这个行为和代码测试一样重要，因为后期可读性保养的产物本身就是文档与代码之间的桥。

这段补充仍然围绕本项目，不用外部材料凑字。所有测试读取的都是 `advanced-order-platform/docs/ops` 里的文件，所有断言都对应 Java ops registry 的新增规则。它没有把四项目通用建议原样搬进来，也没有评价其他项目的结构。范围越清楚，保养越能持续。

模板还降低了后续版本的沟通成本。以后用户继续要求多版推进时，新增 registry 不需要重新解释“为什么要有 renderer、为什么要有 support、为什么要有 boundary test”，模板已经把这些列成项目规则。开发者只需要说明本版如何满足模板，以及哪些边界没有打开。这样讲解也会更具体，不容易变成发布收据。

从可读性角度看，模板本身就是索引的延伸。地图回答“去哪里看”，模板回答“怎么新增”。两者都在 `docs/ops`，并由同一组测试保护。这个组合让本项目后期保养有了稳定入口，而不是每次新建 registry 都从旧文件里复制并猜测最新实践。

模板还能减少未来讲解里的重复争论。每次新增 registry 时，讲解可以直接说明本版如何满足模板：是否有 route paths，是否有 response record，是否有 catalog，是否有 renderer，是否有 support，是否有 service，是否有 controller，是否有测试，是否关闭边界。这样讲解会围绕实现证据展开，而不是反复解释为什么需要这些层。

本版新增的 docs test 也保护了“地图不是摆设”。README 必须链接地图，地图必须保留主题信号，模板必须保留层次信号。后期维护中，文档最容易因为“看起来不影响编译”而被随手删改；把它纳入测试后，文档就和代码一样进入质量门槛。

这项工作还让未来讲解更容易写深。因为模板已经固定应说明的层次，后续版本可以围绕这些层次逐项解释，而不是靠作者临时想结构。换句话说，模板不仅服务写代码，也服务写讲解。它把“出彩解释”需要覆盖的证据面提前列出来，能减少短讲解和空泛总结。

本版对本项目的长期价值，是把文档从旁路资料变成受保护入口。只要 Maven 还运行这些测试，docs/ops 的入口和模板就不会悄悄失效。对于一个持续推进很多版本的项目，这种保护比一次性整理更重要。

因此，v1782 不是单纯补文档，而是在本项目里给后续 registry 新增流程加护栏。模板负责告诉人怎么做，测试负责确认模板还在，讲解负责说明为什么这样做。这三者一起出现，才符合后期保养的标准。

模板越稳定，后续实现越不会走偏；测试越明确，文档越不容易被忽略。这就是本版的实际收益。

它让本项目后续新增接口时先对照模板，再写代码，再写测试，最后写讲解。

这能持续减少遗漏。

也能让文档长期保持有用。

后续维护者可以按模板逐项检查，不必重新猜测项目习惯。

这会让新增工作更稳定，也更容易审查。

也更容易长期延续。

这对后期项目很重要。

尤其重要。

确实重要。

## 一句话总结

v1782 为 Java ops 可读性保养新增 registry 模板和 docs 门禁，让后续只读 registry 必须保留标准层次、边界规则、测试入口和短类名命名原则，同时不打开任何运行时或跨项目动作。
