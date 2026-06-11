# Java v1734 代码讲解：runtime boundary catalog

本版目标是新增运行边界 catalog，确认本批讲解合规 registry 不打开写路由、凭证、raw endpoint 或审计连接。

它仍然不会打开 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback，且不会自动启动 Java 或 mini-kv。

## 入口路由

入口是 OpsShardReadinessCodeWalkthroughComplianceBoundaryRuleCatalog.boundaryRules()。

## 响应模型

BoundaryRule 记录 code、owner、forbiddenAction、allowed=false、rationale。

## 上游证据配置

上游证据来自 Node v367 minimal read-only gate execution 与 Node v368 archive verification 的只读结论：Java / mini-kv 当前不需要继续消费真实运行入口，除非后续出现 invalid-read-contract。Java 本版只把讲解合规、归档范围和安全边界固化为本仓库证据。

## 服务层核心流程

support 统计 deniedBoundaryRuleCount，只有全部 forbidden action 都 denied 才 status=passed。

## Java 证据检查

Java 证据检查 readOnly=true、executionAllowed=false、startsJavaService=false、startsMiniKvService=false。

## mini-kv 证据检查

本版不消费 mini-kv 运行证据，不读取 mini-kv credential，不解析 mini-kv raw endpoint，也不启动 mini-kv 进程。mini-kv 只作为 read-only boundary 出现在文档和 registry 边界说明里。

## 阻断与安全边界

本版继续阻断 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback、Java autostart、mini-kv autostart。Registry response 中 readOnly=true，executionAllowed=false，startsJavaService=false，startsMiniKvService=false，readsCredentialValue=false，resolvesRawEndpointUrl=false，managedAuditHttpAllowed=false。

## 测试覆盖

OpsShardReadinessCodeWalkthroughComplianceRegistryBoundaryTests 覆盖所有关键边界。 本批收尾还会运行 OpsCodeWalkthroughArchiveComplianceTests，确认 v1728-v1747 的新讲解全部包含九个 required headings，且没有使用 legacy marker。

## 一句话总结

Java v1734 把新增运行边界 catalog，确认本批讲解合规 registry 不打开写路由、凭证、raw endpoint 或审计连接。同时保持只读证据边界关闭。

