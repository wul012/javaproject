# Java v1759 代码讲解：截图说明归档分段基础模型

本版目标是新增 screenshot explanation archive registry 的 route、response、当前归档评估和分段计划，明确旧 d 不再继续被挤满。

它不是小粒度补丁，也不会抓取新截图、移动历史归档、打开 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback、Java autostart 或 mini-kv autostart。

## 入口路由

入口路由是 GET /api/v1/ops/shard-readiness/screenshot-explanation-archive-registry。路径 owner 是 OpsShardReadinessScreenshotExplanationArchiveRoutePaths，统一 delegate 是 OpsShardReadinessRoutePaths.SCREENSHOT_EXPLANATION_ARCHIVE_REGISTRY。

## 响应模型

响应模型是 OpsShardReadinessScreenshotExplanationArchiveRegistryResponse，包含 legacyRoot=d、nextRoot=d_runtime_screenshot_archive_next、currentArchiveAssessments、segmentPlans、namingRules、boundaryRules、verificationSteps、markdownSections、checks 和 status。

## 上游证据配置

上游依据仍是 Node v367 之后的只读 gate 结论：Java 当前不要求新 runtime 版本，除非后续出现 invalid-read-contract。本批只消费 Java 本仓库的归档规则、README、测试和静态 registry，不启动 Java / mini-kv，不抓取浏览器截图，也不读取外部 credential 或 endpoint value。

## 服务层核心流程

本版先建立归档分流的基础数据：CurrentCatalog 记录旧 d 和新续写根；SegmentCatalog 记录历史 d、当前 v1759-v1763、预留 v1764-v1780 三个段；route 和 response 让这条归档规则能够被 Java 只读查询。

## Java 证据检查

Java 证据检查会验证 endpoint、profile、legacyRoot、nextRoot、currentArchiveAssessmentCount=2、segmentPlanCount=3，以及 v1759-v1763 active segment。

## mini-kv 证据检查

本版不消费 mini-kv 运行证据，不读取 mini-kv credential，不解析 mini-kv raw endpoint，也不启动 mini-kv 进程。mini-kv 在本批中只作为必须保持关闭的 read-only boundary 被记录。

## 阻断与安全边界

本版继续阻断 screenshot capture、historical archive move、write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback、Java autostart、mini-kv autostart。Registry response 中 readOnly=true，executionAllowed=false，startsJavaService=false，startsMiniKvService=false，capturesScreenshot=false，movesHistoricalArchive=false，readsCredentialValue=false，resolvesRawEndpointUrl=false，managedAuditHttpAllowed=false。

## 测试覆盖

RoutePathsTests 和 RegistryServiceTests 覆盖本版基础面；本批最终还会跑 archive docs tests、targeted tests 和 full Maven。

## 一句话总结

Java v1759 把“截图讲解不要挤入一个文件夹”落成可查询的归档分段模型。
