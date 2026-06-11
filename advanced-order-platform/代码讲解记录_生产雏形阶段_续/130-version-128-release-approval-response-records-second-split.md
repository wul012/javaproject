> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 130. Java v128 - release approval ResponseRecords second split

## 本版目标

- 对齐 Node 当前计划 `v289-post-disabled-fake-harness-echo-roadmap.md` 中的 Java v128：继续拆 `ReleaseApprovalRehearsalResponseRecords`。
- 只做结构优化，不改变 JSON 字段名、record 组件名、endpoint 行为或 disabled fake harness 边界。
- 不读取 credential value，不解析 raw endpoint URL，不写 ledger，不执行 SQL，不启动 Java / mini-kv / Docker 后台运行时。

## 拆分范围

本版把 sandbox connection adapter preflight 末段从主 records 容器中移出，新增：

- `ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords`

迁出的 record 组包括：

- disabled adapter client precheck echo receipt
- fake transport dry-run packet echo marker
- sandbox endpoint handle preflight echo marker
- 上述三组依赖的 shape / boundary / review 子 record

这组是连续链路，语义边界清晰：它们都描述 adapter preflight 后半段的只读 echo、fake transport packet、endpoint-handle review 和 side-effect boundary。

## 行数变化

- `ReleaseApprovalRehearsalResponseRecords.java`：1680 行降至 1299 行。
- 新增 `ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords.java`：386 行。

主文件减少 376 行，下一步仍可继续按相同 Strangler Fig 模式拆更早的 sandbox connection packet / dry-run command / operator-window records。

## 引用调整

以下调用方从 `ReleaseApprovalRehearsalResponseRecords.*` 改为 `ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords.*`：

- `ReleaseApprovalRehearsalResponse`
- `ReleaseApprovalRehearsalManagedAuditReceiptChainBuilder`
- disabled adapter client precheck builder
- fake transport dry-run packet builder
- sandbox endpoint handle preflight builder
- credential resolver decision builder
- verification hint / warning digest builders
- 对应 sandbox connection / endpoint preflight 测试

## 验证

已执行：

```powershell
mvn -q -DskipTests test-compile
mvn -q "-Dtest=OpsEvidenceServiceSandboxConnectionPacketEchoTests,OpsEvidenceServiceSandboxEndpointPreflightEchoTests,OpsEvidenceServiceReleaseApprovalRehearsalVerificationHintOverviewTests,OpsEvidenceServiceReleaseApprovalRehearsalOverviewTests" test
```

结果：通过。测试输出仅包含 Mockito 动态 agent 的 JDK 未来兼容提示。

## 后续建议

Java v129 可继续做 plan 中的 OverviewTests 第二次拆分；若仍优先处理 ResponseRecords，也可以继续拆 sandbox connection dry-run command / precheck packet / operator-window checklist 三组。
