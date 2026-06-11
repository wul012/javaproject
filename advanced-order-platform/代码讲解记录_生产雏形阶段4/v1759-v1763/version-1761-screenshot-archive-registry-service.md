# Java v1761 代码讲解：截图说明归档 registry 聚合服务

本版目标是把归档评估、分段计划、命名规则、边界和验证矩阵聚合成只读 endpoint。

它不是小粒度补丁，也不会抓取新截图、移动历史归档、打开 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback、Java autostart 或 mini-kv autostart。

## 入口路由

核心入口是 OpsShardReadinessScreenshotExplanationArchiveRegistryService.registry() 和 OpsShardReadinessScreenshotExplanationArchiveRegistryController.registry()。

## 响应模型

service 设置 RESPONSE_VERSION=Java v1763、PROFILE=java-shard-readiness-screenshot-explanation-archive-registry.v1；support 输出 registryState=screenshot-explanation-archives-segmented-away-from-the-crowded-root。

## 上游证据配置

上游依据仍是 Node v367 之后的只读 gate 结论：Java 当前不要求新 runtime 版本，除非后续出现 invalid-read-contract。本批只消费 Java 本仓库的归档规则、README、测试和静态 registry，不启动 Java / mini-kv，不抓取浏览器截图，也不读取外部 credential 或 endpoint value。

## 服务层核心流程

service 依次读取 currentArchives、segmentPlans、namingRules、boundaryRules、verificationSteps；renderer 输出 Current Archive Assessments、Archive Segment Plans、Naming Rules、Boundary Rules、Verification Steps；support 做不可变复制、计数、checks 和 status 计算。

## Java 证据检查

Java 证据检查包括 List.copyOf 快照、statusPassed 条件、hasNextRoot、hasCurrentSegment，以及 no-root-dumping/no-screenshot-capture/no-historical-move checks。

## mini-kv 证据检查

本版不消费 mini-kv 运行证据，不读取 mini-kv credential，不解析 mini-kv raw endpoint，也不启动 mini-kv 进程。mini-kv 在本批中只作为必须保持关闭的 read-only boundary 被记录。

## 阻断与安全边界

本版继续阻断 screenshot capture、historical archive move、write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback、Java autostart、mini-kv autostart。Registry response 中 readOnly=true，executionAllowed=false，startsJavaService=false，startsMiniKvService=false，capturesScreenshot=false，movesHistoricalArchive=false，readsCredentialValue=false，resolvesRawEndpointUrl=false，managedAuditHttpAllowed=false。

## 测试覆盖

RendererTests 验证五个 markdown section；ControllerTests 验证 route 和 response identity；ImmutabilityTests 验证 response lists 不可变。

## 一句话总结

Java v1761 把分段归档规则从文档约定升级成可消费的只读 Java registry。
