# Java v1751 代码讲解：质量门禁 controller 与核心测试

本版目标是暴露 GET controller，并补齐 route、service、controller 三类基础测试，确保 endpoint 可稳定消费。

它不是小粒度补丁，也不会打开 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback、Java autostart 或 mini-kv autostart。

## 入口路由

HTTP 入口是 OpsShardReadinessCodeWalkthroughQualityGateRegistryController.registry()，@RequestMapping 使用 BASE_PATH，@GetMapping 使用 CODE_WALKTHROUGH_QUALITY_GATE_REGISTRY。

## 响应模型

controller 返回完整 RegistryResponse，不接收请求体，不读取凭证，不解析 endpoint，不启动服务。TestSupport 直接 new service，避免 Spring 容器和外部进程。

## 上游证据配置

上游依据仍是 Node v367 之后的只读 gate 结论：Java 当前不要求新 runtime 版本，除非后续出现 invalid-read-contract。本批只消费 Java 本仓库的写作规范、讲解合规 registry、归档索引和测试结果，把用户提出的“版本不能过碎、解释必须足够出彩”固化成只读证据。

## 服务层核心流程

controller 只委托 service.registry()；RoutePathsTests 锁定 owner/delegate/service ENDPOINT，ServiceTests 锁定 version、profile、sourcePlan、priorComplianceRegistry、registryState 和核心计数，ControllerTests 锁定 route response。

## Java 证据检查

Java 证据检查 route 常量、service endpoint、response version=Java v1753、readOnly=true、executionAllowed=false。

## mini-kv 证据检查

本版不消费 mini-kv 运行证据，不读取 mini-kv credential，不解析 mini-kv raw endpoint，也不启动 mini-kv 进程。mini-kv 在本批中只作为必须保持关闭的 read-only boundary 被记录。

## 阻断与安全边界

本版继续阻断 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback、Java autostart、mini-kv autostart。Registry response 中 readOnly=true，executionAllowed=false，startsJavaService=false，startsMiniKvService=false，readsCredentialValue=false，resolvesRawEndpointUrl=false，managedAuditHttpAllowed=false。

## 测试覆盖

新增 OpsShardReadinessCodeWalkthroughQualityGateRoutePathsTests、RegistryServiceTests、RegistryControllerTests，以及 RegistryTestSupport。最终会与 boundary/renderer/immutability tests 一起运行。

## 一句话总结

Java v1751 让质量门禁从静态 catalog 变成稳定 HTTP 只读入口，并用核心测试锁住路径与响应身份。
