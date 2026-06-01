# 233. Java v231 v1 contract consumer readiness handoff ops evidence alignment

v231 是 OpsEvidence alignment 版本。它关注的是 readiness handoff 从 v1 contract 清单向全局 evidence/probe 清单扩散时是否一致。

## 这一版为什么放在 v229 之后

v229 已经检查了 route path、endpoint pair、GET probe 和 static fixture。但那一版更多是围绕 v1 contract 本身。

OpsEvidence 是更外层的只读证据入口。下游消费者通常不会只看某个 service 常量，而会通过 OpsEvidence 的 endpoint/probe
清单知道可以探测哪些 URL。因此 v231 要确认 readiness handoff 没有停留在 v1 registry 里，而是也进入了共享 evidence surface。

## 测试覆盖

新增 `OpsShardReadinessV1ContractConsumerReadinessHandoffOpsEvidenceAlignmentTests`：

- `liveEndpoints()` 包含 readiness handoff live endpoint；
- `fixtureEndpoints()` 包含 readiness handoff fixture endpoint；
- `liveProbeEndpoints()` 包含 `GET` live handoff；
- `fixtureProbeEndpoints()` 包含 `GET` fixture handoff；
- endpoint 顺序保持 checklist -> digest -> readiness handoff -> read-only catalog。

同时 focused test 带上 `OpsEvidenceServiceTests`，确保更外层 service 的 read-only window、health probe additional endpoints 等断言仍然通过。

## 边界说明

本版没有启动服务，也没有新增执行通路。所有验证都在清单和测试层完成，仍然只读。

## 测试证据

验证命令：

```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffOpsEvidenceAlignmentTests,OpsShardReadinessEvidenceEndpointsTests,OpsEvidenceServiceTests" test
```

结果：通过。
