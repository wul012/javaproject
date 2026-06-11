> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 139-version-137-verification-hint-contribution-catalog-quality

## 本版目标

Java v137 是质量优化版，不新增业务 echo，不消费新的 Node 证据，也不改变 release approval rehearsal 输出语义。

Node 当前计划显示：Node v302 是 Node 侧 echo segment catalog 质量优化，Java v136 已完成。Java 本版选择同步做自己的 catalog 化小刀：拆 `ReleaseApprovalVerificationHintBuilder` 里不断膨胀的 verification contribution 注册表。

## 改动内容

- 新增 `ReleaseApprovalVerificationHintContributionCatalog`
  - 集中维护 25 个 verification contribution 的注册顺序。
  - 保留 adapter approval schema guard 的特殊 action 过滤逻辑。
  - 后续新增 echo receipt 时，优先改这个 catalog，而不是继续拉长 `ReleaseApprovalVerificationHintBuilder` 构造器。
- 精简 `ReleaseApprovalVerificationHintBuilder`
  - 构造器里不再内联 25 段 `contribution(...)` 注册。
  - 保留原有 warning digest inputs、proof claims、node verification actions 的聚合逻辑。
- 新增 `ReleaseApprovalVerificationHintContributionCatalogTests`
  - 断言 contribution 数量为 25。
  - 断言首个 contribution 仍是 sandbox adapter approval schema guard。
  - 断言末尾仍是 Java v136 runtime shell post-decision plan intake echo。
  - 断言特殊 action 过滤和 v136 proof/action 暴露保持不变。

## 拆分效果

- `ReleaseApprovalVerificationHintBuilder`：约 837 行降到 694 行。
- 新增 catalog：约 234 行。

总行数略增，但职责从“构造 hint + 维护注册表”拆成：

- `ReleaseApprovalVerificationHintBuilder`：负责构造 verification hint。
- `ReleaseApprovalVerificationHintContributionCatalog`：负责维护 echo contribution 注册表。

这符合当前规则：不让巨型代码文件继续膨胀，做必要拆分。

## 边界

本版没有改变任何生产行为或授权边界：

- 不写 ledger。
- 不执行 SQL/schema migration。
- 不读取 credential value。
- 不解析 raw endpoint URL。
- 不实例化 provider/client。
- 不调用外部 managed audit。
- 不启动 Java/Node/mini-kv 服务。

## 验证

- `mvn -q -DskipTests test-compile`
- `mvn -q "-Dtest=ReleaseApprovalVerificationHintContributionCatalogTests" test`
- `mvn -q "-Dtest=OpsEvidenceServiceReleaseApprovalRehearsalVerificationHintOverviewTests" test`

后续若再继续做 Java 质量优化，优先候选是：

- `ReleaseApprovalVerificationWarningDigestBuilder` 的同类 catalog 化。
- 仍偏大的 endpoint credential resolver builder/support 家族做共用 template/catalog。
