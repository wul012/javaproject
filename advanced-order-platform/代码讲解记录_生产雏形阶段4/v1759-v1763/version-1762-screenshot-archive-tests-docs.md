# Java v1762 代码讲解：截图说明归档测试与文档硬化

本版目标是补齐 Java 测试，创建 d_runtime_screenshot_archive_next，并更新旧 d README 和项目归档规则。

它不是小粒度补丁，也不会抓取新截图、移动历史归档、打开 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback、Java autostart 或 mini-kv autostart。

## 入口路由

测试入口是 OpsShardReadinessScreenshotExplanationArchive*Tests 和 OpsScreenshotExplanationArchiveSegmentationDocsTests；文档入口是 d_runtime_screenshot_archive_next/README.md 与 d_runtime_screenshot_archive_next/v1759-v1763/README.md。

## 响应模型

DocsTests 直接检查续写根和版本段 README 存在，并检查 no-root-dumping 文案。AGENTS 和 d/README.md 说明旧 d 是历史归档，新截图说明走分段续写目录。

## 上游证据配置

上游依据仍是 Node v367 之后的只读 gate 结论：Java 当前不要求新 runtime 版本，除非后续出现 invalid-read-contract。本批只消费 Java 本仓库的归档规则、README、测试和静态 registry，不启动 Java / mini-kv，不抓取浏览器截图，也不读取外部 credential 或 endpoint value。

## 服务层核心流程

本版把代码规则落到文件系统：旧 d 不搬动，只改 README 指向；新续写根只放 README 和分段 README；没有截图文件直接放在根目录。Java 测试保证以后目录结构不会悄悄漂移。

## Java 证据检查

Java 证据检查包括 docs tests、route/service/boundary/renderer/controller/immutability tests，以及 d README 和 AGENTS 规则更新。

## mini-kv 证据检查

本版不消费 mini-kv 运行证据，不读取 mini-kv credential，不解析 mini-kv raw endpoint，也不启动 mini-kv 进程。mini-kv 在本批中只作为必须保持关闭的 read-only boundary 被记录。

## 阻断与安全边界

本版继续阻断 screenshot capture、historical archive move、write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback、Java autostart、mini-kv autostart。Registry response 中 readOnly=true，executionAllowed=false，startsJavaService=false，startsMiniKvService=false，capturesScreenshot=false，movesHistoricalArchive=false，readsCredentialValue=false，resolvesRawEndpointUrl=false，managedAuditHttpAllowed=false。

## 测试覆盖

新增 RoutePathsTests、RegistryServiceTests、RegistryBoundaryTests、RegistryRendererTests、RegistryControllerTests、RegistryImmutabilityTests、OpsScreenshotExplanationArchiveSegmentationDocsTests。

## 一句话总结

Java v1762 把截图说明归档的分段规则落实到测试和实际目录，不再让新资料挤进旧 d。
