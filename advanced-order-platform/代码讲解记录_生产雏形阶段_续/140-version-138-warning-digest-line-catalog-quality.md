# 140-version-138-warning-digest-line-catalog-quality

## 本版目标

Java v138 是质量优化版，不新增业务 echo，不改变 release approval rehearsal 的输出语义。

本版延续 v137 的 catalog 化方向：v137 拆了 verification hint contribution 注册表；v138 拆 `ReleaseApprovalVerificationWarningDigestBuilder` 里重复膨胀的 warning/boundary line 收集清单。

## 改动内容

- 新增 `ReleaseApprovalVerificationWarningDigestLineCatalog`
  - 集中维护 25 个 echo receipt 的 warning lines 收集顺序。
  - 集中维护 25 个 echo receipt 的 boundary lines 收集顺序。
  - 用 `Builders` record 承载相关 builder，用 `Receipts` record 承载对应 receipt，避免在主 digest builder 里继续展开长参数清单。
- 精简 `ReleaseApprovalVerificationWarningDigestBuilder`
  - 构造器生成 `lineBuilders`。
  - `build(...)` 里生成 `lineReceipts`。
  - 原来两段长 `lines.addAll(...)` 清单改为：
    - `ReleaseApprovalVerificationWarningDigestLineCatalog.warningLines(...)`
    - `ReleaseApprovalVerificationWarningDigestLineCatalog.boundaryLines(...)`
- 新增 `ReleaseApprovalVerificationWarningDigestLineCatalogTests`
  - 守住 warning line 数量为 25。
  - 守住首个 entry 仍是 sandbox adapter approval schema guard。
  - 守住末尾 entry 仍是 Java v136 runtime shell post-decision plan intake echo。
  - 守住 v136 ready/state/write boundary 关键 line。

## 拆分效果

- `ReleaseApprovalVerificationWarningDigestBuilder`：约 679 行降到 567 行。
- 新增 catalog：约 344 行。

新增 catalog 偏长，但它是纯注册/转发层；主 warning digest builder 不再同时承担 digest 骨架和 50 段 echo line 注册。后续新增 echo 时，warning/boundary line 接入点更集中。

## 边界

本版只改代码组织：

- 不新增 runtime shell implementation。
- 不读取 credential value。
- 不解析 raw endpoint URL。
- 不调用外部 managed audit。
- 不写 approval ledger。
- 不执行 SQL/schema migration。
- 不启动 Java、Node、mini-kv 或外部服务。

## 验证

- `mvn -q -DskipTests test-compile`
- `mvn -q "-Dtest=ReleaseApprovalVerificationWarningDigestLineCatalogTests,OpsEvidenceServiceReleaseApprovalRehearsalVerificationHintOverviewTests,OpsEvidenceServiceReleaseApprovalRehearsalHeaderBackedOverviewTests" test`

## 后续建议

继续质量优化时，优先考虑 endpoint credential resolver 的 builder/support 家族，尤其是相似 echo support 中的 option、side-effect boundary、proof/action 常量 catalog 化。这个收益更大，但风险也更高，适合小步推进。
