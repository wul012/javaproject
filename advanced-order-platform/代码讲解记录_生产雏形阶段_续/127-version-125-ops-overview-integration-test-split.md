# Java v125: Ops Overview 集成测试拆分

本版执行 Node v287 plan 中的 Java v125: Integration Tests 第四刀连拆。目标是把 `OpsOverviewIntegrationTests` 里三个职责不同的 MockMvc 回归拆开，保留共享 fixture/helper，让后续 overview、dynamic evidence、static sample 可以分别演进。

## 改动

- `OpsOverviewIntegrationTests` 只保留 `/api/v1/ops/overview` 基础只读业务信号断言。
- 新增 `OpsOverviewReadOnlyEvidenceIntegrationTests`，承接 `/api/v1/ops/evidence` 动态 evidence 场景。
- 新增 `OpsOverviewStaticReadOnlyEvidenceIntegrationTests`，承接 `/contracts/ops-read-only-evidence.sample.json` 静态样本场景。
- 新增 `OpsOverviewIntegrationTestSupport`，统一 `MockMvc`、failed event 仓库注入，以及 replay approval / management history / replay attempt / message 的清理顺序。

## 维护收益

- 主测试文件从 854 行降到 57 行，不再同时承载 overview、动态 evidence、静态 sample 三类回归。
- 动态 evidence 仍然较长，但已经独立成类；后续如果要继续拆，可以只针对 evidence 内部的 release、rollback、secret、SQL、production pass 边界继续拆，不影响 overview 基础测试。
- 静态 sample 测试变成独立类，后续契约样本字段变化不会牵动动态 evidence 测试。
- 本版没有改生产行为，也没有新增 fake harness runtime、credential value 读取、raw endpoint 解析、managed audit connection、approval ledger 或外部 SQL。

## 行数

```text
OpsOverviewIntegrationTests.java: 854 -> 57 行
OpsOverviewIntegrationTestSupport.java: 35 行
OpsOverviewReadOnlyEvidenceIntegrationTests.java: 621 行
OpsOverviewStaticReadOnlyEvidenceIntegrationTests.java: 163 行
```

## 验证

```text
mvn -q -DskipTests test-compile
mvn -q "-Dtest=OpsOverviewIntegrationTests,OpsOverviewReadOnlyEvidenceIntegrationTests,OpsOverviewStaticReadOnlyEvidenceIntegrationTests" test
```
