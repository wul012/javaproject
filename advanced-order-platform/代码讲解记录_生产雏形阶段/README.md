# advanced-order-platform 代码讲解记录_生产雏形阶段

本目录从 v48 之后作为新的代码讲解入口使用，和旧目录同级。

目录名里的“生产雏形阶段”表示当前项目进度：系统已经不只是订单 CRUD，而是进入带消息、失败事件治理、审批、证据接口和生产 readiness 雏形的阶段。

```text
D:\javaproj\advanced-order-platform\代码讲解记录
D:\javaproj\advanced-order-platform\代码讲解记录_生产雏形阶段
```

旧目录保留 v1-v47 的历史讲解，不再继续堆新文件。

## 写入规则

后续每次推进 Java 版本时，新的代码讲解文件写入本目录。

以后如果项目进入新的阶段，再新建同级目录，不继续塞进旧阶段目录。目录命名格式为：

```text
代码讲解记录_阶段名称
```

示例：

```text
代码讲解记录_生产雏形阶段
代码讲解记录_生产强化阶段
代码讲解记录_多项目融合阶段
```

命名模式继续沿用旧目录：

```text
52-version-48-主题.md
53-version-49-主题.md
54-version-50-主题.md
```

说明文档结构也继续沿用旧模式：

```text
先说明文件或类的角色
再说明本版所处项目进度
再给核心流程
然后多代码引用解释关键实现
再说明验证、归档和成熟度变化
最后做一句话总结
```

也就是说，本目录不是只写“代码做了什么”，还要明确说明“本版让生产雏形阶段推进到了什么程度”。

## 当前项目进度基线

截至 v47，项目已经从基础订单系统推进到带失败事件治理、审批、重放证据和控制面证据接口的高级 Java 后端练手项目。

当前主线能力：

```text
订单核心
 -> 幂等下单
 -> 商品校验
 -> 库存预占、扣减、释放、回补
 -> 支付、退款、取消、过期、发货、完成
 -> 订单状态历史

数据一致性
 -> Outbox 事件表
 -> Outbox 发布标记
 -> Flyway 迁移
 -> Hibernate validate
 -> H2 默认本地运行
 -> PostgreSQL profile 与 Testcontainers 验证

消息与失败治理
 -> RabbitMQ Outbox 投递
 -> RabbitMQ 通知消费
 -> 幂等通知落库
 -> 消费失败重试
 -> DLQ 失败事件沉淀
 -> 失败事件查询、分页、筛选、导出

失败事件重放
 -> replay readiness
 -> replay simulation
 -> replay approval status
 -> approval digest
 -> execution contract
 -> approved / blocked sample
 -> replay audit evidence sample
 -> replay evidence index

运维与控制面证据
 -> ops overview
 -> failed-event summary
 -> ops evidence
 -> replay evidence index
 -> 页面端权限预检和写操作守卫
```

成熟度判断：

```text
业务链路：中高成熟
失败事件治理：中高成熟
重放审批与证据：中高成熟
真实生产安全：仍需继续补强
跨项目融合：Java 侧适合作为订单交易核心和证据上游
```

还没有完成的方向：

```text
真实登录态和操作员身份接入
更完整的生产 readiness 聚合
更多异常路径和回归矩阵
PostgreSQL / RabbitMQ 的定期完整验证
观测指标、告警和追踪链路
控制面接入后的契约稳定性维护
```

## 后续讲解索引

新版本讲解从这里继续追加：

```text
52-version-48-replay-evidence-operator-auth-boundary.md
 -> 第四十八版 replay evidence index 增强 operator/auth boundary，说明 Header 身份、动作角色策略和生产认证缺口

53-version-49-ops-read-only-evidence-sample.md
 -> 第四十九版 ops read-only evidence 静态样本，给 Node production pass evidence verification 提供 Java 只读证据引用位

54-version-50-ops-read-only-window-self-description.md
 -> 第五十版 ops evidence 启动后自描述增强，固定 healthProbe、readOnlyWindow、Node 只读窗口环境开关和禁止写操作边界

55-version-51-ops-evidence-field-guide.md
 -> 第五十一版 ops evidence 字段说明样本，解释 service、healthProbe、readOnlyWindow 和 executionBoundaries 的字段稳定性

56-version-52-order-idempotency-boundary.md
 -> 第五十二版订单创建幂等边界，增加请求指纹、同 key 不同请求 409、ops evidence 和静态样本

57-version-53-idempotency-store-abstraction.md
 -> 第五十三版订单幂等存储抽象，新增 IdempotencyStore、JPA 默认实现和 mini-kv disabled candidate evidence

58-version-54-release-verification-manifest.md
 -> 第五十四版发布验证 manifest，固化 Maven 测试、打包、HTTP smoke、静态 contracts 清单和 Node 只读 release gate 边界

59-version-55-deployment-rollback-evidence-sample.md
 -> 第五十五版部署回退证据样本，说明 Java 包、运行配置、数据库迁移和静态契约的回退边界

60-version-56-release-bundle-manifest.md
 -> 第五十六版发布包 bundle manifest，把 jar、contracts、发布验证和回退证据收成 Node 可读但不可执行的只读清单

61-version-57-rollback-approval-handoff-sample.md
 -> 第五十七版 rollback approval handoff 样本，固化 Java 回退窗口前必须人工确认的包版本、运行配置、密钥来源和数据库迁移方向

62-version-58-rollback-sql-review-gate-sample.md
 -> 第五十八版 rollback SQL review gate 样本，固化 SQL review owner、迁移方向和 operator approval placeholder 的只读检查边界

63-version-59-production-secret-source-contract.md
 -> 第五十九版 production secret source contract，固化 secret source、rotation owner、review cadence 和 secret value 访问边界

64-version-60-production-deployment-runbook-contract.md
 -> 第六十版 production deployment runbook contract，固化 deployment window owner、rollback approver、migration direction 和 no-execution 边界

65-version-61-rollback-approval-record-fixture.md
 -> 第六十一版 rollback approval record fixture，固化 reviewer、approval timestamp placeholder、rollback target 和 no-secret-value 边界

66-version-62-release-handoff-checklist-fixture.md
 -> 第六十二版 release handoff checklist fixture，固化 release operator、rollback approver、artifact target、migration direction 和 secret source confirmation，并收口静态 contract endpoint helper

67-version-63-release-audit-retention-fixture.md
 -> 第六十三版 release audit retention fixture，固化 release evidence retention id、operator placeholder、artifact target、retention days、audit export 字段和 no-secret-value 边界

68-version-64-release-operator-signoff-fixture.md
 -> 第六十四版 release operator signoff fixture，固化 release operator、rollback approver、release window、artifact target 和 operator signoff placeholder 的审批决定前置证据边界

69-version-65-rollback-approver-evidence-fixture.md
 -> 第六十五版 rollback approver evidence fixture，固化 rollback approver、migration direction、rollback SQL artifact reference 和 production database boundary 的只读证据边界

70-version-66-release-approval-rehearsal.md
 -> 第六十六版 release approval rehearsal 只读聚合入口，汇总审批演练输入、live replay/outbox 信号和禁止审批/ledger/deploy/rollback/SQL 的执行边界

71-version-67-release-approval-request-context.md
 -> 第六十七版 release approval rehearsal 只读请求上下文，回显 request id、operator identity 和 audit correlation 来源，但不认证、不持久化、不写 ledger

72-version-68-release-approval-failure-taxonomy.md
 -> 第六十八版 release approval rehearsal 只读失败分类，区分 upstream readiness、auth context warning 和 audit correlation warning，继续保持 no-write/no-execution 边界

73-version-69-release-approval-verification-hint.md
 -> 第六十九版 release approval rehearsal 只读验证提示，提供 response schema version、warning digest 和 no-ledger-write proof，供 Node 导入窗口结果前校验
```

后续推进 v70 时，从 `74-version-70-主题.md` 继续追加。

## 一句话总览

旧目录记录“项目如何一步步长到 v47”，本目录从 v48 开始继续记录“每版代码怎么实现、生产雏形阶段推进到哪里、成熟度发生了什么变化”。

## 新增版本入口

```text
93-version-90-release-approval-context-normalization-helper.md
 -> 第九十版 release approval context normalization helper，收敛 ContextHeaderField 的 normalize / missing warning 入口，保持只读边界不变

94-version-91-release-approval-sandbox-connection-precondition-receipt.md
 -> 第九十一版 release approval sandbox connection precondition receipt，列出真实 sandbox connection 前置条件，但不打开连接、不读取 credential value、不执行 SQL
95-version-92-release-approval-sandbox-connection-dry-run-envelope-echo-receipt.md
 -> 第九十二版 release approval sandbox connection dry-run envelope echo receipt，只读回显 Node v236 envelope 六个字段名，证明不含 credential value、不连接、不执行 SQL、不写 ledger

96-version-93-release-approval-sandbox-connection-operator-window-checklist-echo-receipt.md
 -> 第九十三版 release approval sandbox connection operator window checklist echo receipt，只读回显 Node v238 checklist 字段、数量、approval item id 和 pause code，继续阻断连接、credential value、SQL、ledger 和 auto-start

97-version-94-ops-evidence-service-dispatch-table-refactor.md
 -> 第九十四版 OpsEvidenceService dispatch table 重构，把 release/static evidence 构建迁移到分发表，外部契约不变，主文件降到 1032 行

98-version-95-ops-evidence-static-release-artifact-enum-refactor.md
 -> 第九十五版 OpsEvidence 静态 release 字符串常量收敛为 enum，继续压缩 OpsEvidenceService 的常量噪音，外部契约不变

99-version-96-release-approval-rehearsal-request-record-refactor.md
 -> 第九十六版 release approval rehearsal request record 重构，消除多层 null overload，主文件降到 606 行，外部 HTTP 契约不变

100-version-97-release-approval-rehearsal-builder-chain-refactor.md
 -> 第九十七版 release approval rehearsal builder chain 重构，拆出 managed-audit receipt chain 和 normalized request/sections 上下文，外部契约不变

101-version-98-release-approval-sandbox-dry-run-command-package-echo-receipt.md
 -> 第九十八版 release approval sandbox dry-run command package echo receipt，只读回显 Node v241 六条 disabled command，供 Node v244 核对字段和 no-write / no-start 边界

102-version-99-release-approval-sandbox-precheck-packet-echo-receipt.md
 -> 第九十九版 release approval sandbox precheck packet echo receipt，只读回显 Node v245 七个 precheck item，供 Node v246 核对字段和 no-write / no-start 边界

103-version-100-ci-bootstrap-large-file-guard.md
 -> 第一百版 CI bootstrap + large-file guard，新增 GitHub Actions Maven CI 基线，并记录大文件后续拆分目标，业务契约不变

104-version-101-dependabot-security-maintenance.md
 -> 第一百零一版 Dependabot/security maintenance，覆盖 Maven + GitHub Actions，不升级依赖、不改业务契约、不打开 managed-audit 边界

105-version-102-disabled-adapter-client-precheck-echo-receipt.md
 -> 第一百零二版 disabled adapter client precheck echo receipt，只读回显 Node v252 adapter client 前置检查形状，供 Node v254 校验，并继续阻断 credential value、真实 client、外部请求、连接、SQL、ledger、auto-start 和 mini-kv 写权限

106-version-103-fake-transport-dry-run-packet-echo-marker.md
 -> 第一百零三版 fake transport dry-run packet echo marker，只读回显 Node v255/v256 fake transport packet 的 request、response、timeout、cleanup 和 side-effect 边界，供 Node v257 校验，并继续阻断 credential value、真实连接、SQL、ledger、临时文件和 auto-start
```

## v107 update

```text
110-version-107-sandbox-endpoint-credential-resolver-test-only-shell-echo-marker.md
 -> 第一百零七版 sandbox endpoint credential resolver test-only shell echo marker：只读回显 Node v264 fake-only request、response、failure mapping、guard、probe 和 no-side-effect 边界；继续阻断真实 resolver 实现、secret provider、credential value、raw endpoint、外部请求、managed-audit connection、SQL、ledger、auto-start 和 mini-kv 托管存储
```

## v108 update

```text
111-version-108-echo-marker-support-optimization.md
 -> 第一百零八版 echo marker support 优化：把 v104-v107 的 warning 输入、warning 行和条件 warning 收集收口到小型 support；不改 contract、schema 或 managed-audit 边界
```

## v106 update

```text
109-version-106-sandbox-endpoint-credential-resolver-disabled-precheck-echo-marker.md
 -> 第一百零六版 sandbox endpoint credential resolver disabled precheck echo marker：只读回显 Node v262 env handles、opt-in gates、failure classes、dry-run response shape、inherited no-go conditions 和 side-effect 边界；继续阻断 resolver 实现、secret provider、credential value、raw endpoint、外部请求、managed-audit connection、SQL、ledger、auto-start 和 mini-kv 托管存储
```

## v105 update

```text
108-version-105-sandbox-endpoint-credential-resolver-decision-echo-marker.md
 -> 绗竴鐧鹃浂浜斾簲 sandbox endpoint credential resolver decision echo marker锛屽彧璇诲洖鏄?Node v260 decision record、Node v259 upstream echo verification、8 个决策字段、9 个 no-go 条件和 side-effect 边界；继续阻断 resolver 执行、credential value、raw endpoint、外部请求、managed-audit connection、SQL、ledger、auto-start 和 mini-kv 托管存储
```

## v104 update

```text
107-version-104-sandbox-endpoint-handle-preflight-echo-marker.md
 -> 第一百零四版 sandbox endpoint handle preflight echo marker，只读回显 Node v258 endpoint/credential handle、network/TLS/redaction/operator-window review，供 Node v259 校验，并继续阻断 credential value、raw endpoint、真实连接、SQL、ledger 和 auto-start
```
