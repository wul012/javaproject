> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# Java v124：Failed Event Search 集成测试拆分

本版执行 Node v287 plan 中的 Java v124：Integration Tests 第三刀连拆。目标是把服务级 failed event search 回归按职责拆开，并保留共享 fixture/helper。

## 改动

- 新增 `FailedEventSearchIntegrationTestSupport`，统一 failed event 相关仓库清理、服务注入、基础 fixture 和 bad request 断言。
- `FailedEventSearchIntegrationTests` 保留 failed message search 与 replay attempt search 两个基础查询场景。
- 新增 `FailedEventReplayApprovalSearchIntegrationTests`，承接 replay approval 请求、复核、历史搜索、CSV export 和 replay gate。
- 新增 `FailedEventManagementSearchIntegrationTests`，承接 management status、management history 和 management CSV export。
- 新增 `FailedEventSearchValidationIntegrationTests`，承接无效 range、limit、sort、role 等 validation 边界。

## 维护收益

- 主测试文件从 812 行降到 205 行，不再把 failed message、replay approval、management、validation 四类回归堆在同一个类中。
- 清理顺序和基础 fixture 由 support 统一，后续新增 search 场景不需要复制仓库清理和 `failedEventMessage` 构造。
- 每个测试类仍使用相同 Spring Boot test properties，验证的是同一套 service contract，没有改变业务语义。

## 行数

```text
FailedEventSearchIntegrationTests.java: 812 -> 205 行
FailedEventSearchIntegrationTestSupport.java: 67 行
FailedEventReplayApprovalSearchIntegrationTests.java: 218 行
FailedEventManagementSearchIntegrationTests.java: 289 行
FailedEventSearchValidationIntegrationTests.java: 105 行
```

## 验证

```text
mvn -q -DskipTests test-compile
mvn -q "-Dtest=FailedEventSearchIntegrationTests,FailedEventReplayApprovalSearchIntegrationTests,FailedEventManagementSearchIntegrationTests,FailedEventSearchValidationIntegrationTests" test
```
