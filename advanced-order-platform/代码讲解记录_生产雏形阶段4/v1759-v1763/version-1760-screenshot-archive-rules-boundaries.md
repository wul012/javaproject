# Java v1760 代码讲解：截图说明归档命名规则、边界与验证矩阵

本版目标是补齐命名规则、边界规则和验证矩阵，明确后续截图与说明必须先按版本段分流。

它不是小粒度补丁，也不会抓取新截图、移动历史归档、打开 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback、Java autostart 或 mini-kv autostart。

## 入口路由

入口仍是 screenshot explanation archive registry；本版新增 NamingRuleCatalog、BoundaryCatalog、VerificationCatalog 三个静态入口。

## 响应模型

NamingRule 记录 range-before-version、separate-images-and-explanations、no-root-dumping、old-d-root-read-only、code-walkthrough-separate、readme-per-segment；BoundaryRule 记录 no-screenshot-capture、no-historical-move、no-write-routing 等禁止事项；VerificationStep 记录 route/service/boundary/doc/full Maven 检查。

## 上游证据配置

上游依据仍是 Node v367 之后的只读 gate 结论：Java 当前不要求新 runtime 版本，除非后续出现 invalid-read-contract。本批只消费 Java 本仓库的归档规则、README、测试和静态 registry，不启动 Java / mini-kv，不抓取浏览器截图，也不读取外部 credential 或 endpoint value。

## 服务层核心流程

service 后续会读取 naming rules、boundary rules、verification steps。naming rules 防止直接往 d 或续写根下丢文件，boundary rules 保证本批不抓图、不搬旧档、不启动运行时，verification catalog 把文档存在性和 Java 测试纳入发布检查。

## Java 证据检查

Java 证据检查会锁定 namingRuleCount=6、boundaryRuleCount=8、deniedBoundaryRuleCount=8、verificationStepCount=5，并检查 no-root-dumping、no-screenshot-capture、no-historical-move checks。

## mini-kv 证据检查

本版不消费 mini-kv 运行证据，不读取 mini-kv credential，不解析 mini-kv raw endpoint，也不启动 mini-kv 进程。mini-kv 在本批中只作为必须保持关闭的 read-only boundary 被记录。

## 阻断与安全边界

本版继续阻断 screenshot capture、historical archive move、write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback、Java autostart、mini-kv autostart。Registry response 中 readOnly=true，executionAllowed=false，startsJavaService=false，startsMiniKvService=false，capturesScreenshot=false，movesHistoricalArchive=false，readsCredentialValue=false，resolvesRawEndpointUrl=false，managedAuditHttpAllowed=false。

## 测试覆盖

BoundaryTests、RendererTests、DocsTests 会覆盖这些规则和输出。

## 一句话总结

Java v1760 把截图说明归档的分段命名、禁区和验证方式一次讲清。
