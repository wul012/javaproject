# Java v1746 代码讲解：archive index

本版目标是更新总索引和整改清单，标出 phase4/v1728-v1747 是新标准目录。

它仍然不会打开 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback，且不会自动启动 Java 或 mini-kv。

## 入口路由

文档入口是 代码讲解记录_总索引.md 和 代码讲解记录_整改清单.md。

## 响应模型

索引不作为 JSON response，但作为人类导航证据和归档范围说明。

## 上游证据配置

上游证据来自 Node v367 minimal read-only gate execution 与 Node v368 archive verification 的只读结论：Java / mini-kv 当前不需要继续消费真实运行入口，除非后续出现 invalid-read-contract。Java 本版只把讲解合规、归档范围和安全边界固化为本仓库证据。

## 服务层核心流程

索引指向 phase4 目录，registry archive range 同步包含该目录。

## Java 证据检查

Java 证据检查 archiveRange catalog 和合规测试共同防止索引/目录漂移。

## mini-kv 证据检查

本版不消费 mini-kv 运行证据，不读取 mini-kv credential，不解析 mini-kv raw endpoint，也不启动 mini-kv 进程。mini-kv 只作为 read-only boundary 出现在文档和 registry 边界说明里。

## 阻断与安全边界

本版继续阻断 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback、Java autostart、mini-kv autostart。Registry response 中 readOnly=true，executionAllowed=false，startsJavaService=false，startsMiniKvService=false，readsCredentialValue=false，resolvesRawEndpointUrl=false，managedAuditHttpAllowed=false。

## 测试覆盖

OpsShardReadinessCodeWalkthroughComplianceRegistryServiceTests 和 OpsCodeWalkthroughArchiveComplianceTests 覆盖。 本批收尾还会运行 OpsCodeWalkthroughArchiveComplianceTests，确认 v1728-v1747 的新讲解全部包含九个 required headings，且没有使用 legacy marker。

## 一句话总结

Java v1746 把更新总索引和整改清单，标出 phase4/v1728-v1747 是新标准目录。同时保持只读证据边界关闭。

