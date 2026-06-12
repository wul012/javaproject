# 第一千七百九十五版代码讲解：发布纪律与生产边界集中

## 入口路由

本版本没有新增业务入口，也没有改变任何控制器路由。入口变化发生在项目维护层：根目录新增 `CHANGELOG.md` 和 `PRODUCTION_READINESS.md`，README 增加发布纪律指针，测试目录新增文档守卫。J5 playbook 的目标不是让应用多一个业务能力，而是让后续维护者能快速知道本项目当前到底到了什么生产阶段、哪些能力已经有证据、哪些能力仍然关闭。过去这些信息散落在 README 的长段落、配置文件、历史 evidence 和多个 docs 文件中，查一次边界要跨很多位置。J5 把最重要的生产判断集中到根文档，入口更清楚。

`CHANGELOG.md` 的入口是版本视角。它声明当前版本策略：git tag 是权威版本证据，Maven artifact 暂时保持 `0.1.0-SNAPSHOT`。这个选择符合当前项目状态。Java 仓库在短时间内连续推进很多工程版本，如果每一版都修改 Maven 制品版本，反而会制造大量无意义的版本噪声，也容易把“工程证据版本”和“可发布制品版本”混为一谈。现在用 git tag 记录 v1795 这种工程版本，用 pom 保持快照，后续如果真的进入制品发布节奏，再单独改策略。

`PRODUCTION_READINESS.md` 的入口是生产边界视角。它集中说明运行 profile、可观测性、数据库迁移、支付、消息、失败事件重放、release approval rehearsal、credential、SQL、部署和回滚边界。这里最重要的不是写得好看，而是写得诚实。比如 playbook 里提到 outbox publisher off by default，但实际 `application.yml` 中 `outbox.publisher.enabled=true`，真正默认关闭的是 RabbitMQ transport，也就是 `outbox.rabbitmq.enabled=false`。J5 没有为了迎合一句模板话而伪造事实，而是把真实默认行为写清楚：scheduler 默认存在，RabbitMQ 发布默认关闭，CI smoke 会显式关闭 scheduler。

## 响应模型

本版本的响应模型主要是文档响应和测试响应。文档响应不是 HTTP JSON，而是维护者阅读项目时得到的结构化结论。`CHANGELOG.md` 先说明版本政策，再列出 v1795 到 v1786 的近十个版本。每个条目只保留高价值变化，不把历史 README 的所有细节搬过来。这样后续判断“这一段生产卓越工作走到哪里”时，不需要从上千个 tag 里猜，也不需要翻滚动很长的 README。

`PRODUCTION_READINESS.md` 的响应模型则是按风险域拆分。运行 profile 说明 prod profile 关闭 H2 console、关闭 SQL 展示、打开优雅停机；可观测性说明 actuator 只暴露 health、info、metrics，并使用 Micrometer tracing 记录 trace/span；支付边界说明 provider 是模拟值，没有真实支付网关；消息边界说明 database-only dispatcher 和 RabbitMQ transport 的默认关系；失败事件重放边界说明 ORDER_SUPPORT、SRE、SYSTEM 的角色矩阵；release rehearsal 边界说明只读 evidence 不授权部署、回滚、SQL、credential 或 managed audit connection。

测试响应由 `ProductionReadinessDocumentationTests` 提供。它不是为了测试 Markdown 渲染，而是防止关键文档失联。第一条测试要求 README 指向 `CHANGELOG.md` 和 `PRODUCTION_READINESS.md`，并提到 git tag 与 `0.1.0-SNAPSHOT`。第二条测试要求 changelog 保留 v1795 到 v1786 的近版本记录和版本策略。第三条测试要求生产就绪文档包含支付模拟、消息默认、H2 console、角色矩阵、credential value、managed audit、deployment、rollback、rollback SQL 和 actuator 暴露范围等关键字。以后如果有人删除这些结论，测试会提醒。

## 上游证据配置

上游依据仍然是 Java production excellence playbook 的 J5。J5 要求三件事：建立 changelog，选择版本一致性策略，集中生产就绪边界。本版本逐条完成，但根据本项目真实情况做了一个偏差处理。版本一致性方面，没有把 pom 改成 `0.1.0` 或 `1795`，因为当前仓库的 tag 频率非常高，且这些版本大多是工程证据版本，不是对外制品版本。将 Maven artifact 保持快照，并声明 git tag 为权威，是更稳妥的策略。

生产就绪边界方面，本版本没有新增生产能力。它把已有事实集中记录，包括 J0 的 CI bootstrap、J1 的静态分析、J2 的覆盖率 ratchet、J3 的 prod profile 和输入边界、J4 的 tracing 和 actuator。文档明确写出“单项目验证 + 跨项目契约对齐”，不夸大成已经具备真实生产部署能力。这一点很重要，因为 Java 项目确实有大量 release approval、rollback、credential、managed audit 相关 evidence，但它们大多是只读样本、审批交接、echo receipt 或 preflight，不是执行器。

本版本没有编辑 Node 计划，也没有改 mini-kv。J5 是 Java 仓库自己的维护文档工作，不需要改跨项目 schema。历史 archive 目录也没有移动。新增文件只在根目录、测试目录和当前代码讲解续写目录，符合之前确定的归档规则。Node 仍然可以读取 Java 历史 evidence 路径，不会因为 J5 文档集中而丢 digest 或路径。

## 服务层核心流程

虽然 J5 是文档版本，但它仍然有服务层意义。服务层不是只有 Java class 才算，后期维护中“人如何理解系统边界”本身也是服务系统的一部分。过去 README 里包含大量版本流水、接口示例和证据说明，信息很多，但不适合作为生产边界入口。J5 把发布纪律和生产就绪判断抽到根文档，等于给维护者提供了一条更短的阅读路径：先看 README 指针，再看 changelog 确认版本，再看生产就绪文档确认能做什么和不能做什么。

文档守卫测试是这个流程的自动化保护。没有测试的文档容易在几版后失真：有人重写 README，忘记保留生产就绪指针；有人追加版本，不知道 tag 策略；有人改配置，却没更新消息边界。`ProductionReadinessDocumentationTests` 把这些核心词固定住，虽然它不能替代人工 review，但它能防止最粗的漂移。尤其是 credential、managed audit、deployment、rollback、rollback SQL 这些危险词，必须持续出现在集中边界文档里。

J5 还处理了 outbox 默认值的诚实表达。这个点如果写错，会直接影响后续运维理解。当前 scheduler 默认开启，database-only dispatcher 会把 outbox 事件标记为已处理；RabbitMQ transport 默认关闭，只有显式开启时才对外发布。CI prod smoke 又会显式关闭 scheduler，避免短生命周期检查被后台任务干扰。文档把这三层写开，比一句“outbox off by default”更准确，也更符合顶级工程师对事实的尊重。

文档债在这个项目里会直接变成执行风险，因为仓库中存在大量只读证据、演练回执、审批交接和禁用状态说明。如果读者只看到“就绪”“审批”“回滚”“凭据”这些词，却没有看到集中边界，很容易把只读材料误解成执行许可。J5 的服务层意义就在这里：它没有改变运行时对象，却改变了维护者进入系统的路径。先读集中边界，再看具体接口和证据，人的操作顺序更安全；先在长篇历史记录中搜索关键词，再凭印象判断，风险就会放大。后期工程维护不只是清代码，也要清认知入口。

## Java 证据检查

Java 证据第一组是 `CHANGELOG.md`。它包含版本策略和 v1795 到 v1786 的版本摘要。v1795 是当前版本，v1794 是可观测性，v1793 是生产 profile 和输入校验，v1792 是覆盖率门，v1791 是静态分析门，v1790 是 CI bootstrap，v1789 是 ops consolidation roadmap，v1788 到 v1786 是 readability upkeep 收尾和 registry。这个范围刚好覆盖当前生产卓越阶段的上下文，不把更早几百个历史版本全搬进来。

第二组证据是 `PRODUCTION_READINESS.md`。它写明默认 H2 只适合本地和 CI，prod profile 是冒烟与生产配置入口，支付是模拟，RabbitMQ consumer 默认关闭，RabbitMQ outbox transport 默认关闭，failed-event replay 必须经过角色矩阵和审批规则，release approval rehearsal 不授权真实执行。它还列出截至 v1795 明确未授权的能力：真实支付、生产 secret、credential value、raw endpoint、managed audit connection、deployment、rollback、rollback SQL、自动启动 Node/Java/mini-kv、未经审批的重放。

第三组证据是 README 指针。README 不再只把读者带进长篇操作说明，而是在 Observability 之后提供 Release Discipline 段落，直接指向两个根文档，并说明 git tag 与 `0.1.0-SNAPSHOT` 的关系。这个位置靠前，开发者启动项目、看健康检查之后，就能看到版本和生产边界入口。

第四组证据是测试。`ProductionReadinessDocumentationTests` 读取文件系统中的真实 Markdown 文件，断言文件存在、README 指针存在、changelog 版本策略存在、生产边界关键词存在。这类测试不会检查每一句自然语言，但它能防止最重要的骨架被删掉。它也让 J5 不只是“写了文档”，而是把文档纳入了默认测试体系。

## mini-kv 证据检查

J5 不消费 mini-kv 证据。mini-kv 作为上游 C++ 项目，有自己的生产卓越路线，例如文件拆分、归档保留和 C++ 测试整理；这些不应该被 Java 文档版本代做。本版本只在 `PRODUCTION_READINESS.md` 的未授权清单里写明 Java 不会自动启动 mini-kv，也不会授权 Node 自动启动或停止 Java 与 mini-kv。这是边界说明，不是 mini-kv 工作。

这个处理符合四项目统筹规则。Java 可以在不改变跨项目契约的前提下推进内部质量；mini-kv 也可以独立推进自己的维护。只有当 Java 文档要声明一个会影响 mini-kv 的 contract 或执行能力时，才需要跨项目同步。本版本没有这种变化。

## 阻断与安全边界

J5 的最大安全价值是把“不允许”写清楚。很多项目后期问题不是因为没有功能，而是因为只读证据、预演接口、审批交接和真实执行被读者混在一起。`PRODUCTION_READINESS.md` 明确说明 release approval rehearsal 不是部署授权，rollback evidence 不是回滚执行，credential resolver echo 不是 credential value 读取，managed audit handoff 不是连接，failed-event simulation 不是真实 replay。这样后续接手的人不会因为看到很多 readiness 词就误以为可以上线执行。

版本策略也有安全边界。用 git tag 作为权威版本，可以保证每次工程版本都有可追溯提交；保留 Maven snapshot 可以避免误把高频内部工程版本发布成外部制品版本。真正要做 artifact release 时，应该另开版本，设计制品版本、发布仓库、签名、回滚和部署策略，而不是在 J5 顺手改 pom。

本版本没有打开 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment、rollback、Node 自动启停 Java 或 mini-kv 自动启停。文档新增不会改变运行时权限，也不会让任何禁用能力变成可执行能力。

## 测试覆盖

J5 的聚焦测试是 `ProductionReadinessDocumentationTests`。它覆盖三个方面：根文档存在且 README 可发现；changelog 保留版本策略和最近版本；production readiness 集中记录关键生产边界。后续还要运行讲解合规测试，因为 v1795 也新增了中文长篇讲解；再运行 Spotless，虽然文档本身不需要 Java 格式化，但新增测试需要；最后运行完整 verify，让文档守卫进入默认质量门。

测试设计有意保持轻量。它不要求 Markdown 每一行固定不变，否则后续文档维护会变得痛苦；它只固定不可丢失的关键词和版本入口。这样既能防止关键边界消失，又不会让正常文档优化变成测试重写。这是文档测试比较合适的粒度。

## 实际工作量说明

本版本的实际工作量包括：梳理 J5 playbook 要求，核对当前是否已有 changelog 和生产就绪文档，检查最近版本 tag，检查 application.yml 中 failed-event 角色矩阵、outbox、RabbitMQ、prod profile 和 tracing 状态，确认支付实现是模拟 provider，识别 outbox 默认值与 playbook 草稿之间的偏差，选择 git tag 权威版本策略，新增两个根文档，新增 README 指针，新增文档守卫测试，并撰写本篇中文讲解。

这里继续遵守“禁止硬凑”。本项目这版没有为了增加工作量去改业务代码，没有把 outbox 默认值强行改成文档想象的样子，也没有把 mini-kv 的维护任务塞进 Java。工作量来自真实维护需求：把散落边界集中，让版本策略可追踪，让危险执行面保持明确关闭，并用测试保护这些文档入口。文档版本如果做得扎实，后面功能版本才不会在 review 时反复解释同一堆边界。

## 一句话总结

v1795 把本项目的发布纪律和生产边界集中到根文档：git tag 是当前权威版本，pom 保持快照；生产就绪文档明确支付模拟、消息默认、重放审批、只读 rehearsal、credential、SQL、部署和回滚边界；测试守卫确保这些结论不会再次散落或丢失。
