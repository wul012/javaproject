# Java v1737 代码讲解：registry support

本版目标是新增 support 层，集中处理不可变复制、计数、checks 和 status。

它仍然不会打开 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback，且不会自动启动 Java 或 mini-kv。

## 入口路由

入口是 OpsShardReadinessCodeWalkthroughComplianceRegistrySupport.response(...)。

## 响应模型

support 输出完整 RegistryResponse，并生成 version-count、heading-count、boundary-count、future-cutoff、legacy-marked-count 等 checks。

## 上游证据配置

上游证据来自 Node v367 minimal read-only gate execution 与 Node v368 archive verification 的只读结论：Java / mini-kv 当前不需要继续消费真实运行入口，除非后续出现 invalid-read-contract。Java 本版只把讲解合规、归档范围和安全边界固化为本仓库证据。

## 服务层核心流程

service 只负责收集 catalog，support 负责聚合；这样测试和后续扩展都更清晰。

## Java 证据检查

Java 证据检查 List.copyOf 后 response lists 不能被 clear。

## mini-kv 证据检查

本版不消费 mini-kv 运行证据，不读取 mini-kv credential，不解析 mini-kv raw endpoint，也不启动 mini-kv 进程。mini-kv 只作为 read-only boundary 出现在文档和 registry 边界说明里。

## 阻断与安全边界

本版继续阻断 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback、Java autostart、mini-kv autostart。Registry response 中 readOnly=true，executionAllowed=false，startsJavaService=false，startsMiniKvService=false，readsCredentialValue=false，resolvesRawEndpointUrl=false，managedAuditHttpAllowed=false。

## 测试覆盖

OpsShardReadinessCodeWalkthroughComplianceRegistryImmutabilityTests 覆盖不可变性。 本批收尾还会运行 OpsCodeWalkthroughArchiveComplianceTests，确认 v1728-v1747 的新讲解全部包含九个 required headings，且没有使用 legacy marker。

## 一句话总结

Java v1737 把新增 support 层，集中处理不可变复制、计数、checks 和 status。同时保持只读证据边界关闭。

