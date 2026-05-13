# 第四十九版代码讲解：ops read-only evidence sample

本版目标是给 Java 的只读运维证据补一份稳定静态样本。

v45 已经提供 `/api/v1/ops/evidence` 动态接口，v49 不新增写能力，也不改变 replay 执行逻辑，而是把这个接口的核心响应形状固化为随包 JSON 样本，供 Node v152 / v153 做 production pass evidence archive verification 和 operator runbook 引用。

## 本版所处项目进度

最新计划来自：

```text
D:\nodeproj\orderops-node\docs\plans\v149-post-production-pass-evidence-roadmap.md
```

计划里当前推荐顺序是：

```text
Java v49 + mini-kv v58：可以一起推进，只补只读 evidence 供给能力，不做写操作。
Java 补 ops/read-only evidence sample。
mini-kv 补 CHECKJSON/INFOJSON/STATSJSON 只读样本与说明。
```

因此 Java v49 的边界很明确：

```text
只补 Java 只读证据供给
不启动真实联调窗口
不执行订单写操作
不执行 failed-event replay POST
不修改 Node
不修改 mini-kv
```

## 静态样本文件

新增文件：

```text
src/main/resources/static/contracts/ops-read-only-evidence.sample.json
```

Spring Boot 会把 `src/main/resources/static` 下的文件直接作为静态资源暴露，所以启动应用后可以读取：

```text
GET /contracts/ops-read-only-evidence.sample.json
```

样本顶部固定了版本和场景：

```json
{
  "evidenceVersion": "java-ops-evidence.v1",
  "scenario": "OPS_READ_ONLY_EVIDENCE_SAMPLE",
  "readOnly": true,
  "executionAllowed": false
}
```

这里最重要的是：

```text
readOnly=true
executionAllowed=false
```

这说明它只是 evidence 形状样本，不代表 Java 允许生产执行。

## replay 与 outbox 边界

样本里的失败事件重放部分固定表达：

```json
"failedEventReplay": {
  "realReplayEndpoint": "/api/v1/failed-events/{id}/replay",
  "realReplayAllowedByEvidence": false
}
```

这和动态接口的语义一致：Java 可以说明真实 replay endpoint 在哪里，但这个只读 evidence 自身不允许执行 replay。

Outbox 部分也固定表达默认阻断：

```json
"outbox": {
  "publisherEnabled": false,
  "rabbitMqEnabled": false,
  "blockers": [
    "OUTBOX_PUBLISHER_DISABLED",
    "RABBITMQ_OUTBOX_DISABLED"
  ]
}
```

这给 Node 后续 verification 一个稳定负向边界：没有真实 live pass 时，不能把样本伪装成 production pass。

## productionPassBoundary

v49 样本额外增加了静态样本专用的边界说明：

```json
"productionPassBoundary": {
  "readyForProductionPassEvidence": false,
  "reason": "This sample proves Java read-only evidence shape only; it does not prove a live upstream pass.",
  "allowedProbeEndpoints": [
    "GET /actuator/health",
    "GET /api/v1/ops/overview",
    "GET /api/v1/ops/evidence"
  ],
  "forbiddenOperations": [
    "POST /api/v1/orders",
    "POST /api/v1/failed-events/{id}/replay",
    "RabbitMQ replay publish",
    "Any mutation through this sample"
  ]
}
```

这段不是动态接口字段，而是静态样本的说明边界。

它解决的问题是：Node 可以引用 Java v49 样本来确认字段结构和 no-write 约束，但不能把它当成真实上游 pass evidence。

## 动态接口加入样本引用

改动文件：

```text
src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java
```

`evidenceEndpoints()` 增加了：

```java
"/api/v1/ops/evidence",
"/contracts/ops-read-only-evidence.sample.json",
"/api/v1/failed-events/replay-evidence-index",
```

这样动态 `/api/v1/ops/evidence` 会主动告诉控制面：

```text
自己在哪里
稳定静态样本在哪里
replay evidence index 在哪里
```

这比只在 README 里写路径更可靠，Node 可以直接从 JSON 字段读取证据入口。

## 测试覆盖

改动文件：

```text
src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java
src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java
```

集成测试锁定静态样本：

```java
mockMvc.perform(get("/contracts/ops-read-only-evidence.sample.json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.evidenceVersion").value("java-ops-evidence.v1"))
        .andExpect(jsonPath("$.scenario").value("OPS_READ_ONLY_EVIDENCE_SAMPLE"))
        .andExpect(jsonPath("$.readOnly").value(true))
        .andExpect(jsonPath("$.executionAllowed").value(false));
```

同时锁定 production pass 边界：

```java
.andExpect(jsonPath("$.productionPassBoundary.readyForProductionPassEvidence").value(false))
.andExpect(jsonPath("$.productionPassBoundary.allowedProbeEndpoints",
        hasItem("GET /api/v1/ops/evidence")))
.andExpect(jsonPath("$.productionPassBoundary.forbiddenOperations",
        hasItem("POST /api/v1/failed-events/{id}/replay")));
```

服务层测试则确认动态 evidence endpoint 列表包含新样本：

```java
assertThat(evidence.evidenceEndpoints())
        .contains(
                "/api/v1/ops/evidence",
                "/contracts/ops-read-only-evidence.sample.json",
                "/api/v1/failed-events/replay-evidence-index"
        );
```

## 验证、归档和成熟度变化

本版验证分成三层：

```text
聚焦测试：OpsOverviewIntegrationTests + OpsEvidenceServiceTests
非 Docker 测试：排除 Testcontainers 后的 64 个测试
Docker 集成测试：PostgresMigrationIntegrationTests + 3 个 RabbitMQ 测试
```

第一次直接跑完整 `mvn test` 时，Surefire 已报告业务测试通过到：

```text
Tests run: 64, Failures: 0, Errors: 0, Skipped: 0
```

随后 JVM fork 因本机 native memory allocation 失败退出，所以本版按拆分验证收口：

```text
非 Docker 64 个测试通过
Docker / Testcontainers 4 个测试通过
mvn "-DskipTests" package 通过
HTTP smoke 三个只读 GET 通过
```

运行调试归档写入：

```text
b/49/解释/说明.md
b/49/图片/
```

成熟度变化是：Java 侧不再只提供 live 动态 evidence，还提供了一个可随版本引用的只读静态样本。后续 Node v152/v153 可以稳定引用 Java v49 的 evidence 形状、no-write 边界和 production pass 限制，但仍不能把它当成真实生产通过证据。

## 一句话总结

v49 把 Java ops evidence 的只读结构固化为可引用静态样本，并明确 production pass 边界，让后续 Node v152/v153 可以消费 Java 上游证据，同时不会把样本误判成真实生产通过证据。
