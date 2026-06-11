> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 第三十六版：订单平台只读运行概览

## 本版目标

v36 是三项目路线图里的第一步：Java 先补一个稳定、只读、面向控制面的业务概览接口，让 Node 后续可以真实接入 Java 业务信号，而不是只看 `/actuator/health`。

```text
GET /api/v1/ops/overview
 -> application
 -> orders
 -> inventory
 -> outbox
 -> failedEvents
```

这个接口只读聚合，不执行重放，不修改订单、库存、Outbox 或失败事件状态。

## 改动文件

```text
src/main/java/com/codexdemo/orderplatform/ops/OpsOverviewController.java
src/main/java/com/codexdemo/orderplatform/ops/OpsOverviewService.java
src/main/java/com/codexdemo/orderplatform/ops/OpsOverviewResponse.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageRepository.java
src/test/java/com/codexdemo/orderplatform/ops/OpsOverviewServiceTests.java
src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java
README.md
a/36/解释/说明.md
```

## 一、接口入口保持很薄

文件：`src/main/java/com/codexdemo/orderplatform/ops/OpsOverviewController.java`

Controller 只负责暴露路径：

```java
@RestController
@RequestMapping("/api/v1/ops")
public class OpsOverviewController {
```

真正的入口是：

```java
@GetMapping("/overview")
public OpsOverviewResponse overview() {
    return opsOverviewService.overview();
}
```

这里没有请求体、没有操作员头、没有写动作，刻意保持成纯 GET。Node 后续只需要探测这个接口，就能拿到 Java 业务概览。

## 二、响应对象按控制面视角分组

文件：`src/main/java/com/codexdemo/orderplatform/ops/OpsOverviewResponse.java`

顶层响应分成五块：

```java
public record OpsOverviewResponse(
        Instant sampledAt,
        Application application,
        Orders orders,
        Inventory inventory,
        Outbox outbox,
        FailedEvents failedEvents
) {
```

应用块记录运行身份：

```java
public record Application(
        String name,
        List<String> profiles,
        Instant startedAt,
        long uptimeSeconds
) {
}
```

业务块只暴露聚合数字：

```java
public record Orders(long total) {
}

public record Inventory(long items) {
}

public record Outbox(long pending) {
}
```

失败事件块暴露 Node 最关心的风险信号：

```java
public record FailedEvents(
        long total,
        long pendingReplayApprovals,
        Instant latestFailedAt
) {
}
```

这样做的好处是 Node 不需要理解 Java 内部实体，也不需要拉取全量失败事件列表，先拿摘要就能判断 Java 当前是否有运维风险。

## 三、服务层只做只读聚合

文件：`src/main/java/com/codexdemo/orderplatform/ops/OpsOverviewService.java`

服务层用 `@Transactional(readOnly = true)` 明确这是读事务：

```java
@Transactional(readOnly = true)
public OpsOverviewResponse overview() {
```

聚合时只调用 repository 的查询能力：

```java
return new OpsOverviewResponse(
        sampledAt,
        application(sampledAt),
        new OpsOverviewResponse.Orders(orderRepository.count()),
        new OpsOverviewResponse.Inventory(inventoryRepository.count()),
        new OpsOverviewResponse.Outbox(outboxRepository.countByPublishedAtIsNull()),
        failedEvents()
);
```

这段代码对应到业务含义：

```text
orderRepository.count()
 -> 当前订单总数

inventoryRepository.count()
 -> 当前库存条目数

outboxRepository.countByPublishedAtIsNull()
 -> 尚未发布的 Outbox 事件数

failedEvents()
 -> 失败事件总数、待审批重放数、最近失败时间
```

应用信息来自 Spring 环境：

```java
environment.getProperty("spring.application.name", "advanced-order-platform")
```

profile 处理也偏控制面友好：

```java
String[] activeProfiles = environment.getActiveProfiles();
if (activeProfiles.length > 0) {
    return List.copyOf(Arrays.asList(activeProfiles));
}
return List.copyOf(Arrays.asList(environment.getDefaultProfiles()));
```

如果没有显式 profile，就返回 Spring 默认的 `default`，避免 Node 看到空数组后误判“没有运行配置”。

## 四、失败事件仓储只增加查询方法

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageRepository.java`

v36 只补两个派生查询：

```java
long countByReplayApprovalStatus(FailedEventReplayApprovalStatus replayApprovalStatus);

Optional<FailedEventMessage> findTopByOrderByFailedAtDescIdDesc();
```

第一个用于统计待审批重放：

```java
failedEventMessageRepository.countByReplayApprovalStatus(FailedEventReplayApprovalStatus.PENDING)
```

第二个用于找到最近失败时间：

```java
failedEventMessageRepository.findTopByOrderByFailedAtDescIdDesc()
        .map(FailedEventMessage::getFailedAt)
        .orElse(null)
```

这里没有新增表结构，也没有新增状态枚举，更没有调用 `requestReplayApproval`、`reviewReplayApproval`、`replay` 等写动作。

## 五、单元测试覆盖聚合映射

文件：`src/test/java/com/codexdemo/orderplatform/ops/OpsOverviewServiceTests.java`

单测用 mock repository 固定计数：

```java
when(orderRepository.count()).thenReturn(12L);
when(inventoryRepository.count()).thenReturn(4L);
when(outboxRepository.countByPublishedAtIsNull()).thenReturn(3L);
when(failedEventMessageRepository.count()).thenReturn(5L);
when(failedEventMessageRepository.countByReplayApprovalStatus(FailedEventReplayApprovalStatus.PENDING))
        .thenReturn(2L);
```

然后断言响应字段：

```java
assertThat(overview.orders().total()).isEqualTo(12L);
assertThat(overview.inventory().items()).isEqualTo(4L);
assertThat(overview.outbox().pending()).isEqualTo(3L);
assertThat(overview.failedEvents().total()).isEqualTo(5L);
assertThat(overview.failedEvents().pendingReplayApprovals()).isEqualTo(2L);
```

这层测试关注的是“Java 聚合逻辑是不是把各个 repository 的信号放进了正确 JSON 分组”。

## 六、HTTP 集成测试覆盖真实接口形状

文件：`src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java`

集成测试启动 Spring Boot 和 MockMvc：

```java
@SpringBootTest(properties = {
        "order.expiration.enabled=false",
        "outbox.publisher.enabled=false"
})
@AutoConfigureMockMvc
class OpsOverviewIntegrationTests {
```

测试里准备两条失败事件，其中一条是待审批：

```java
pendingApproval.requestReplayApproval("needs operator review", "ops-user", Instant.now());
failedEventMessageRepository.save(pendingApproval);
failedEventMessageRepository.save(FailedEventMessage.record(...));
```

然后直接请求新接口：

```java
mockMvc.perform(get("/api/v1/ops/overview"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.application.name").value("advanced-order-platform"))
        .andExpect(jsonPath("$.failedEvents.total").value(2))
        .andExpect(jsonPath("$.failedEvents.pendingReplayApprovals").value(1))
        .andExpect(jsonPath("$.failedEvents.latestFailedAt").exists());
```

这层测试确认 HTTP 路径、JSON 字段和失败事件摘要都能真实工作。

## 七、本版边界

v36 刻意不做这些事：

```text
不触发失败事件重放
不修改订单、库存、Outbox 或失败事件状态
不接入 mini-kv
不新增登录系统
不让 Node 参与 Java 验证
```

它的价值是给后续 Node v53 一个稳定入口：

```text
Java /api/v1/ops/overview
 -> Node OrderPlatformClient.opsOverview()
 -> Node upstream overview 展示 Java business signals
```

## 一句话总结

v36 把 Java 从“只有技术健康检查”推进到“能输出业务运行概览”，但仍然保持只读和松耦合，为三项目融合里的 Node 统一观察台打下第一块稳定接口。
