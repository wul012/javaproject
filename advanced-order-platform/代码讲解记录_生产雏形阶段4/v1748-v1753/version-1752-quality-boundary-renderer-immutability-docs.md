# Java v1752 代码讲解：质量门禁边界、renderer、不可变性与规范文档

本版目标是补齐边界、renderer、不可变性测试，并把“版本不能过碎”的新要求写入讲解规范和索引。

它不是小粒度补丁，也不会打开 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback、Java autostart 或 mini-kv autostart。

## 入口路由

测试入口是 BoundaryTests、RendererTests、ImmutabilityTests；文档入口是 代码讲解记录_写作规范.md、代码讲解记录_总索引.md、代码讲解记录_整改清单.md。

## 响应模型

BoundaryTests 验证 readOnly=true 和 executionAllowed=false 等字段；RendererTests 验证 markdownSections；ImmutabilityTests 验证 versionRules、rubrics、anchors、checklists、boundaries、sections、checks 均不可变。

## 上游证据配置

上游依据仍是 Node v367 之后的只读 gate 结论：Java 当前不要求新 runtime 版本，除非后续出现 invalid-read-contract。本批只消费 Java 本仓库的写作规范、讲解合规 registry、归档索引和测试结果，把用户提出的“版本不能过碎、解释必须足够出彩”固化成只读证据。

## 服务层核心流程

本版把质量门禁从“能返回”推进到“能被审查”：renderer 输出可读 section，boundary tests 防止 runtime 能力误开，immutability tests 防止调用方篡改 response，文档索引说明 v1748-v1753 是中等粒度质量门禁批次。

## Java 证据检查

Java 证据检查 includes no-write-routing、no-credential-value、no-raw-endpoint-url、no-upstream-autostart，并确保 anchor.runtimeFree 全部为 true。

## mini-kv 证据检查

本版不消费 mini-kv 运行证据，不读取 mini-kv credential，不解析 mini-kv raw endpoint，也不启动 mini-kv 进程。mini-kv 在本批中只作为必须保持关闭的 read-only boundary 被记录。

## 阻断与安全边界

本版继续阻断 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback、Java autostart、mini-kv autostart。Registry response 中 readOnly=true，executionAllowed=false，startsJavaService=false，startsMiniKvService=false，readsCredentialValue=false，resolvesRawEndpointUrl=false，managedAuditHttpAllowed=false。

## 测试覆盖

新增 RegistryBoundaryTests、RegistryRendererTests、RegistryImmutabilityTests；同时讲解合规测试会扫描本目录六篇文档的九个 required headings。

## 一句话总结

Java v1752 把质量门禁补到可审查、可复现、不可变，并把新粒度规则写进维护文档。
