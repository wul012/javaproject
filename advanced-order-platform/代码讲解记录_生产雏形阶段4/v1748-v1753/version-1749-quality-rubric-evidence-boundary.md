# Java v1749 代码讲解：讲解 rubric、证据锚点与安全边界

本版目标是把“解释要出彩”拆成 rubric、证据锚点、评审清单和 runtime boundary，避免以后版本只写空泛收据。

它不是小粒度补丁，也不会打开 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback、Java autostart 或 mini-kv autostart。

## 入口路由

入口仍由 quality gate registry 暴露；本版新增的入口是 ExplanationRubricCatalog、EvidenceAnchorCatalog、ReviewChecklistCatalog、BoundaryRuleCatalog 四个静态 catalog。

## 响应模型

响应中的 ExplanationRubric 记录 section、mustExplain、standoutSignal、minimumEvidencePoints；EvidenceAnchor 记录 source 与 requiredProof；ReviewChecklist 记录 reviewerQuestion 与 releaseBlocker；BoundaryRule 记录 forbiddenAction 与 allowed=false。

## 上游证据配置

上游依据仍是 Node v367 之后的只读 gate 结论：Java 当前不要求新 runtime 版本，除非后续出现 invalid-read-contract。本批只消费 Java 本仓库的写作规范、讲解合规 registry、归档索引和测试结果，把用户提出的“版本不能过碎、解释必须足够出彩”固化成只读证据。

## 服务层核心流程

service 后续会依次读取四类 catalog：rubric 解释每个章节必须讲什么，anchor 指向 Node plan、写作规范、合规 registry、质量 registry、归档索引和测试，checklist 把审查问题变成 release blocker，boundary catalog 保持写路由、凭证、raw endpoint、审计连接和 autostart 关闭。

## Java 证据检查

Java 证据检查会关注 rubricCount=8、evidenceAnchorCount=6、reviewChecklistCount=6、boundaryRuleCount=8、deniedBoundaryRuleCount=8。

## mini-kv 证据检查

本版不消费 mini-kv 运行证据，不读取 mini-kv credential，不解析 mini-kv raw endpoint，也不启动 mini-kv 进程。mini-kv 在本批中只作为必须保持关闭的 read-only boundary 被记录。

## 阻断与安全边界

本版继续阻断 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment / rollback、Java autostart、mini-kv autostart。Registry response 中 readOnly=true，executionAllowed=false，startsJavaService=false，startsMiniKvService=false，readsCredentialValue=false，resolvesRawEndpointUrl=false，managedAuditHttpAllowed=false。

## 测试覆盖

RegistryServiceTests 检查计数，BoundaryTests 检查所有 runtime-free anchor 和 forbidden action，RendererTests 检查 rubric、anchor、checklist、boundary 的 markdown 行。

## 一句话总结

Java v1749 把优秀讲解的判断标准细化成可测试 catalog，同时不消费任何 mini-kv 或外部运行证据。
