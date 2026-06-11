> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 第五十一版代码讲解：ops evidence field guide

本版目标是补一份 Java ops evidence 字段说明样本，让后续 Node release evidence review 能引用 Java 对字段语义、稳定性和只读边界的解释。

v50 已经让 `/api/v1/ops/evidence` 返回 `healthProbe` 和 `readOnlyWindow`。v51 不继续加运行态业务字段，而是把这些字段的解释固化成一个随包静态 JSON：

```text
/contracts/ops-evidence-field-guide.sample.json
```

## 本版所处项目进度

最新计划来自：

```text
D:\nodeproj\orderops-node\docs\plans\v156-post-read-only-window-capture-roadmap.md
```

计划里当前推荐顺序是：

```text
Java v51 + mini-kv v60：只补只读 capture 后的证据解释增强。
Java 补 ops/evidence 字段说明。
补健康/自描述字段的稳定性说明。
```

因此 Java v51 的边界很明确：

```text
只补字段说明
不新增写接口
不执行 failed-event replay POST
不执行订单写操作
不修改 Node
不修改 mini-kv
```

## 字段说明样本

新增文件：

```text
src/main/resources/static/contracts/ops-evidence-field-guide.sample.json
```

Spring Boot 会把 `src/main/resources/static` 下的文件暴露为静态资源，所以启动应用后可以读取：

```text
GET /contracts/ops-evidence-field-guide.sample.json
```

文件顶部固定说明：

```json
{
  "guideVersion": "java-ops-evidence-field-guide.v1",
  "evidenceVersion": "java-ops-evidence.v1",
  "scenario": "OPS_EVIDENCE_FIELD_GUIDE_SAMPLE",
  "readOnly": true,
  "executionAllowed": false
}
```

这里继续保持核心边界：

```text
readOnly=true
executionAllowed=false
```

它是字段说明，不是 live probe 结果，也不是生产通过证据。

## releaseReviewUse

字段说明样本里有一段：

```json
"releaseReviewUse": {
  "intendedConsumer": "Node read-only capture release evidence review",
  "mayBeUsedForProductionPass": false,
  "requiredLiveEvidence": [
    "GET /actuator/health returns UP",
    "GET /api/v1/ops/evidence returns readOnlyWindow.readyForReadOnlyLiveProbe=true"
  ]
}
```

这段解释了 v51 样本的用途：

```text
可以帮助 Node 解释字段
不能被 Node 当成 production pass
真实只读 capture 仍必须读 live /actuator/health 和 live /api/v1/ops/evidence
```

## fieldGroups

字段说明样本把说明拆成四组：

```text
service
healthProbe
readOnlyWindow
executionBoundaries
```

`service` 说明哪些字段是稳定结构、哪些是运行态值：

```json
{
  "path": "service.uptimeSeconds",
  "meaning": "Seconds from process start to sampling time; should be non-negative.",
  "stability": "runtime"
}
```

`healthProbe` 锁定健康检查字段：

```json
{
  "path": "healthProbe.expectedStatus",
  "meaning": "Expected actuator health status value.",
  "expectedValue": "UP"
}
```

`readOnlyWindow` 锁定真实只读窗口字段：

```json
{
  "path": "readOnlyWindow.upstreamActionsAllowed",
  "meaning": "Node write actions are outside the read-only window.",
  "expectedValue": false
}
```

`executionBoundaries` 把读写边界再次摊平：

```json
{
  "path": "failedEventReplay.realReplayAllowedByEvidence",
  "meaning": "The evidence endpoint does not authorize replay POST.",
  "expectedValue": false
}
```

## 动态 evidence 发现入口

改动文件：

```text
src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java
```

`evidenceEndpoints()` 新增：

```java
"/contracts/ops-evidence-field-guide.sample.json",
```

这样 Node 不需要硬编码新样本路径，可以从动态 `/api/v1/ops/evidence` 的 `evidenceEndpoints` 里发现它。

## 静态样本同步引用

改动文件：

```text
src/main/resources/static/contracts/ops-read-only-evidence.sample.json
```

静态样本的 `evidenceEndpoints` 也加入：

```json
"/contracts/ops-evidence-field-guide.sample.json"
```

这保证静态样本和动态 evidence 都能指向字段说明。

## 测试覆盖

改动文件：

```text
src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java
src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java
```

服务层测试确认动态 endpoint 列表包含 field guide：

```java
assertThat(evidence.evidenceEndpoints())
        .contains("/contracts/ops-evidence-field-guide.sample.json");
```

集成测试直接读取静态说明样本：

```java
mockMvc.perform(get("/contracts/ops-evidence-field-guide.sample.json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.guideVersion").value("java-ops-evidence-field-guide.v1"))
        .andExpect(jsonPath("$.releaseReviewUse.mayBeUsedForProductionPass").value(false));
```

并锁定几个关键说明组：

```java
.andExpect(jsonPath("$.fieldGroups[*].name", hasItem("healthProbe")))
.andExpect(jsonPath("$.fieldGroups[*].name", hasItem("readOnlyWindow")))
.andExpect(jsonPath("$.fieldGroups[*].name", hasItem("executionBoundaries")))
```

## 验证、归档和成熟度变化

运行调试归档写入：

```text
b/51/解释/说明.md
b/51/图片/
```

成熟度变化是：Java 侧不只提供运行态 evidence 和静态样本，还提供字段解释。后续 Node v159 做 release evidence review 时，可以引用 Java v51 说明来解释哪些字段是 stable，哪些字段是 runtime，以及为什么 skipped/mixed 不能变成 production pass。

## 一句话总结

v51 把 Java ops evidence 的字段语义、稳定性和只读执行边界固化为静态 field guide，让跨项目 release evidence review 有稳定的 Java 侧说明来源。
