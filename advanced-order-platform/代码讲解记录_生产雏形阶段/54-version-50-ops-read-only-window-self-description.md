> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 第五十版代码讲解：ops read-only window self-description

本版目标是让 Java 启动后的 `/api/v1/ops/evidence` 不只是返回业务风险信号，还能直接说明“真实只读联调窗口里，Node 可以怎么读、不能怎么动”。

v49 已经提供静态样本，v50 再补动态 evidence 的自描述字段。这样 Node v156 做 live probe capture 时，不需要只依赖外部 runbook 猜 Java 侧边界，可以直接读取 Java 返回的 `healthProbe` 和 `readOnlyWindow`。

## 本版所处项目进度

最新计划来自：

```text
D:\nodeproj\orderops-node\docs\plans\v153-post-operator-runbook-roadmap.md
```

计划里当前推荐顺序是：

```text
Java v50 + mini-kv v59：只补真实只读联调窗口需要的“启动后自描述/健康证据”增强。
Java 补 ops health/evidence 字段稳定性。
```

因此 Java v50 的边界是：

```text
只增强只读 evidence 字段
不新增写接口
不执行 failed-event replay POST
不执行订单写操作
不修改 Node
不修改 mini-kv
```

## 响应模型新增字段

改动文件：

```text
src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceResponse.java
```

`OpsEvidenceResponse` 新增两个 record：

```java
public record HealthProbe(
        String endpoint,
        String method,
        String expectedStatus,
        String evidenceEndpoint,
        List<String> additionalProbeEndpoints,
        boolean liveProbeRequiredForPass,
        boolean staticSampleOnly
) {
}
```

`HealthProbe` 解决的是“Java 启动后先读哪里确认活着”的问题：

```text
endpoint=/actuator/health
method=GET
expectedStatus=UP
evidenceEndpoint=/api/v1/ops/evidence
```

再新增：

```java
public record ReadOnlyWindow(
        String windowVersion,
        boolean operatorStartRequired,
        boolean nodeAutoStartAllowed,
        boolean upstreamProbesRequired,
        boolean upstreamActionsAllowed,
        boolean readyForReadOnlyLiveProbe,
        boolean readyForProductionOperations,
        List<String> allowedProbeEndpoints,
        List<String> forbiddenOperations,
        List<String> requiredNodeEnvironment,
        String replayPostBoundary
) {
}
```

`ReadOnlyWindow` 解决的是“Node 真实只读窗口到底允许什么”的问题。

## 动态 evidence 组装

改动文件：

```text
src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java
```

构造响应时新增：

```java
healthProbe(false),
true,
false,
readOnlyWindow(true),
```

这里的语义分别是：

```text
healthProbe.staticSampleOnly=false
readOnly=true
executionAllowed=false
readOnlyWindow.readyForReadOnlyLiveProbe=true
```

动态接口只有在 Java 应用真实启动后才能返回，所以它可以声明 `readyForReadOnlyLiveProbe=true`。但它仍然保持：

```text
readyForProductionOperations=false
upstreamActionsAllowed=false
```

这说明 Java 允许 Node 做只读 probe，不代表已经达到生产操作状态。

## 只读窗口边界

`readOnlyWindow()` 固定输出：

```java
List.of(
        "GET /actuator/health",
        "GET /api/v1/ops/overview",
        "GET /api/v1/ops/evidence",
        "GET /contracts/ops-read-only-evidence.sample.json"
)
```

这就是 Node 真实只读窗口允许访问的 Java 入口。

禁止动作也固定输出：

```java
List.of(
        "POST /api/v1/orders",
        "POST /api/v1/failed-events/{id}/replay",
        "RabbitMQ replay publish",
        "Outbox mutation",
        "Any non-GET Node upstream action"
)
```

这让 Java evidence 自己带上 no-write 边界：即使 Node 侧配置或调用者误解，也能从 Java evidence 看到 replay POST 和订单写入不属于只读窗口。

## Node 环境开关

`requiredNodeEnvironment` 固定为：

```java
List.of(
        "UPSTREAM_PROBES_ENABLED=true",
        "UPSTREAM_ACTIONS_ENABLED=false"
)
```

这和 Node plan 中的真实只读窗口要求对齐：

```text
开启 probe
关闭 action
```

也就是说，Node 可以读 Java，但不能通过 Java 触发任何写动作。

## 静态样本同步增强

改动文件：

```text
src/main/resources/static/contracts/ops-read-only-evidence.sample.json
```

静态样本同样补充 `healthProbe` 与 `readOnlyWindow`，但和动态接口有一个关键差异：

```json
"staticSampleOnly": true,
"readyForReadOnlyLiveProbe": false
```

原因是静态样本只能证明字段结构和边界，不证明 Java 服务已经启动，也不能代表真实 live probe 通过。

## 测试覆盖

改动文件：

```text
src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java
src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java
```

服务层测试锁定 Java 对象语义：

```java
assertThat(evidence.healthProbe().endpoint()).isEqualTo("/actuator/health");
assertThat(evidence.healthProbe().staticSampleOnly()).isFalse();
assertThat(evidence.readOnlyWindow().nodeAutoStartAllowed()).isFalse();
assertThat(evidence.readOnlyWindow().upstreamActionsAllowed()).isFalse();
assertThat(evidence.readOnlyWindow().readyForReadOnlyLiveProbe()).isTrue();
```

集成测试锁定 JSON 输出语义：

```java
.andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints", hasItem("GET /actuator/health")))
.andExpect(jsonPath("$.readOnlyWindow.forbiddenOperations",
        hasItem("POST /api/v1/failed-events/{id}/replay")))
.andExpect(jsonPath("$.readOnlyWindow.requiredNodeEnvironment",
        hasItem("UPSTREAM_ACTIONS_ENABLED=false")))
```

静态样本测试则专门锁定：

```java
.andExpect(jsonPath("$.healthProbe.staticSampleOnly").value(true))
.andExpect(jsonPath("$.readOnlyWindow.readyForReadOnlyLiveProbe").value(false))
```

## 验证、归档和成熟度变化

运行调试归档写入：

```text
b/50/解释/说明.md
b/50/图片/
```

v50 之后，Java 侧 evidence 从“可读状态快照”进一步变成“只读窗口契约输入”。这提高的是跨项目联调成熟度：Node 可以在真实只读窗口里先读 Java 自描述，再决定 capture 结果是 pass、mixed 还是 skipped。

但它仍不是生产放行：

```text
readyForProductionOperations=false
UPSTREAM_ACTIONS_ENABLED=false
replay POST forbidden
订单写操作 forbidden
```

## 一句话总结

v50 给 Java ops evidence 补上 healthProbe 和 readOnlyWindow，让真实只读联调窗口里的 Java 侧健康检查、允许 GET、禁止写操作和 Node 环境开关都变成稳定字段。
