# Java v123：Sandbox Connection Echo 集成测试拆分

本版执行 Node v287 plan 中的 Java v123：Integration Tests 第二刀连拆。目标是继续降低单个集成测试文件体积，同时把重复请求 fixture 抽成共享 helper。

## 改动

- 新增 `OpsReleaseApprovalSandboxConnectionEchoTestSupport`，统一 `/api/v1/ops/release-approval-rehearsal` 请求和沙箱连接 header fixture。
- `OpsReleaseApprovalSandboxConnectionEchoIntegrationTests` 保留前 5 个 echo receipt 场景，避免继续承载 marker 尾段断言。
- 新增 `OpsReleaseApprovalSandboxConnectionMarkerIntegrationTests`，承接 fake transport dry-run packet marker 和 sandbox endpoint handle preflight marker。

## 维护收益

- 主测试文件从 803 行降到 361 行，单类职责从“全量 sandbox connection echo”收窄为前段 receipt contract。
- 重复 header 链从多个测试方法中移出，后续同类 marker 或 receipt 测试不需要再复制 40 多行请求准备。
- 新增 marker 测试类仍沿用同一个 Spring Boot test 配置和同一套只读 rehearsal endpoint，行为边界不变。

## 行数

```text
OpsReleaseApprovalSandboxConnectionEchoIntegrationTests.java: 803 -> 361 行
OpsReleaseApprovalSandboxConnectionEchoTestSupport.java: 66 行
OpsReleaseApprovalSandboxConnectionMarkerIntegrationTests.java: 173 行
```

## 验证

```text
mvn -q -DskipTests test-compile
mvn -q "-Dtest=OpsReleaseApprovalSandboxConnectionEchoIntegrationTests,OpsReleaseApprovalSandboxConnectionMarkerIntegrationTests" test
```
