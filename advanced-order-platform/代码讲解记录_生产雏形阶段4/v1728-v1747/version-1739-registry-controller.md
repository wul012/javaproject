# Java v1739 代码讲解：registry controller

本版目标是新增 REST controller，暴露只读讲解合规 registry。

它仍然不会打开 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback，且不会自动启动 Java 或 mini-kv。

## 入口路由

GET /api/v1/ops/shard-readiness/code-walkthrough-compliance-registry，由 OpsShardReadinessCodeWalkthroughComplianceRegistryController.registry() 提供。

## 响应模型

controller 返回 RegistryResponse，不接受请求体、不读取凭证、不解析 raw endpoint。

## 上游证据配置

上游证据来自 Node v367 minimal read-only gate execution 与 Node v368 archive verification 的只读结论：Java / mini-kv 当前不需要继续消费真实运行入口，除非后续出现 invalid-read-contract。Java 本版只把讲解合规、归档范围和安全边界固化为本仓库证据。

## 服务层核心流程

controller 只委托 service.registry()，没有分支和副作用。

## Java 证据检查

Java 证据检查 route 常量和 response endpoint/profile/version。

## mini-kv 证据检查

本版不消费 mini-kv 运行证据，不读取 mini-kv credential，不解析 mini-kv raw endpoint，也不启动 mini-kv 进程。mini-kv 只作为 read-only boundary 出现在文档和 registry 边界说明里。

## 阻断与安全边界

本版继续阻断 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback、Java autostart、mini-kv autostart。Registry response 中 readOnly=true，executionAllowed=false，startsJavaService=false，startsMiniKvService=false，readsCredentialValue=false，resolvesRawEndpointUrl=false，managedAuditHttpAllowed=false。

## 测试覆盖

OpsShardReadinessCodeWalkthroughComplianceRegistryControllerTests 覆盖。 本批收尾还会运行 OpsCodeWalkthroughArchiveComplianceTests，确认 v1728-v1747 的新讲解全部包含九个 required headings，且没有使用 legacy marker。

## 一句话总结

Java v1739 把新增 REST controller，暴露只读讲解合规 registry。同时保持只读证据边界关闭。

