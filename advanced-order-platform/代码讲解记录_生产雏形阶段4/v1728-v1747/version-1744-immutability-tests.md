# Java v1744 代码讲解：immutability tests

本版目标是补强不可变性测试，避免调用方篡改 response 内部 list。

它仍然不会打开 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback，且不会自动启动 Java 或 mini-kv。

## 入口路由

测试入口是 OpsShardReadinessCodeWalkthroughComplianceRegistryImmutabilityTests。

## 响应模型

versions、requiredHeadings、archiveRanges、documentationRules、boundaryRules、testCoverages、markdownSections、checks 都必须不可变。

## 上游证据配置

上游证据来自 Node v367 minimal read-only gate execution 与 Node v368 archive verification 的只读结论：Java / mini-kv 当前不需要继续消费真实运行入口，除非后续出现 invalid-read-contract。Java 本版只把讲解合规、归档范围和安全边界固化为本仓库证据。

## 服务层核心流程

support 对输入列表执行 List.copyOf，renderer section lines 也执行 List.copyOf。

## Java 证据检查

Java 证据检查 clear() 抛 UnsupportedOperationException。

## mini-kv 证据检查

本版不消费 mini-kv 运行证据，不读取 mini-kv credential，不解析 mini-kv raw endpoint，也不启动 mini-kv 进程。mini-kv 只作为 read-only boundary 出现在文档和 registry 边界说明里。

## 阻断与安全边界

本版继续阻断 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback、Java autostart、mini-kv autostart。Registry response 中 readOnly=true，executionAllowed=false，startsJavaService=false，startsMiniKvService=false，readsCredentialValue=false，resolvesRawEndpointUrl=false，managedAuditHttpAllowed=false。

## 测试覆盖

OpsShardReadinessCodeWalkthroughComplianceRegistryImmutabilityTests 覆盖。 本批收尾还会运行 OpsCodeWalkthroughArchiveComplianceTests，确认 v1728-v1747 的新讲解全部包含九个 required headings，且没有使用 legacy marker。

## 一句话总结

Java v1744 把补强不可变性测试，避免调用方篡改 response 内部 list。同时保持只读证据边界关闭。

