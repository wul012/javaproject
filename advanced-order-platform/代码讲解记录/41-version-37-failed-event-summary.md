> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 第三十七版：失败事件治理摘要

## 本版目标

v37 按 `D:\nodeproj\orderops-node\docs\plans\v54-post-infojson-roadmap.md` 推进，目标是让 Java 先只读暴露失败事件治理压力，给后续 Node v55 的统一风险观察做准备。

```text
GET /api/v1/failed-events/summary
 -> totalFailedEvents
 -> pendingReplayApprovals
 -> approvedReplayApprovals
 -> rejectedReplayApprovals
 -> latestFailedAt
 -> latestApprovalAt
 -> replayBacklog
```

本版只读聚合，不执行 replay，不审批 replay，不修改失败事件状态，也不接 mini-kv。

## 改动文件

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventSummaryController.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventSummaryService.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventSummaryResponse.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageRepository.java
src/test/java/com/codexdemo/orderplatform/notification/FailedEventSummaryServiceTests.java
src/test/java/com/codexdemo/orderplatform/FailedEventSummaryIntegrationTests.java
README.md
a/37/解释/说明.md
```

## 一、接口入口独立成 Summary Controller

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventSummaryController.java`

v37 没有把接口塞进已有的 `FailedEventMessageController`，而是单独放一个摘要 Controller：

```java
@RestController
@RequestMapping("/api/v1/failed-events")
public class FailedEventSummaryController {
```

实际入口很薄：

```java
@GetMapping("/summary")
public FailedEventSummaryResponse summary() {
    return failedEventSummaryService.summary();
}
```

这样做的边界很清楚：

```text
FailedEventMessageController
 -> 继续负责列表、导出、重放、审批和操作员上下文

FailedEventSummaryController
 -> 只负责失败事件治理摘要
```

## 二、响应对象直接面向控制面

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventSummaryResponse.java`

响应对象只放 Node 后续需要的摘要字段：

```java
public record FailedEventSummaryResponse(
        Instant sampledAt,
        long totalFailedEvents,
        long pendingReplayApprovals,
        long approvedReplayApprovals,
        long rejectedReplayApprovals,
        Instant latestFailedAt,
        Instant latestApprovalAt,
        long replayBacklog
) {
}
```

这些字段不暴露失败事件 payload，也不返回操作历史明细。它表达的是“压力和风险”，不是“明细列表”。

## 三、服务层只做 readOnly 聚合

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventSummaryService.java`

服务入口使用只读事务：

```java
@Transactional(readOnly = true)
public FailedEventSummaryResponse summary() {
```

聚合逻辑只调用 repository 查询：

```java
return new FailedEventSummaryResponse(
        Instant.now(),
        failedEventMessageRepository.count(),
        failedEventMessageRepository.countByReplayApprovalStatus(FailedEventReplayApprovalStatus.PENDING),
        failedEventMessageRepository.countByReplayApprovalStatus(FailedEventReplayApprovalStatus.APPROVED),
        failedEventMessageRepository.countByReplayApprovalStatus(FailedEventReplayApprovalStatus.REJECTED),
        latestFailedAt(),
        latestApprovalAt(),
        failedEventMessageRepository.countByStatusNot(FailedEventMessageStatus.REPLAYED)
);
```

这里的 `replayBacklog` 定义为：

```text
失败事件 status != REPLAYED 的数量
```

也就是还没有成功完成重放闭环的失败事件数量。

## 四、latestFailedAt 复用现有最新失败查询

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventSummaryService.java`

最近失败时间来自最新 `failedAt`：

```java
private Instant latestFailedAt() {
    return failedEventMessageRepository.findTopByOrderByFailedAtDescIdDesc()
            .map(FailedEventMessage::getFailedAt)
            .orElse(null);
}
```

对应 repository：

```java
Optional<FailedEventMessage> findTopByOrderByFailedAtDescIdDesc();
```

空库时返回 `null`，让 JSON 表达为：

```json
"latestFailedAt": null
```

## 五、latestApprovalAt 同时看申请和审核

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventSummaryService.java`

审批活动有两个时间来源：

```text
replayApprovalRequestedAt
replayApprovalReviewedAt
```

服务层分别取两边最新一条：

```java
Instant latestRequest = failedEventMessageRepository
        .findTopByReplayApprovalRequestedAtIsNotNullOrderByReplayApprovalRequestedAtDescIdDesc()
        .map(FailedEventMessage::getReplayApprovalRequestedAt)
        .orElse(null);
Instant latestReview = failedEventMessageRepository
        .findTopByReplayApprovalReviewedAtIsNotNullOrderByReplayApprovalReviewedAtDescIdDesc()
        .map(FailedEventMessage::getReplayApprovalReviewedAt)
        .orElse(null);
```

然后在 Java 内存里取较新的时间：

```java
return latest(latestRequest, latestReview).orElse(null);
```

这样避免写复杂 JPQL，也兼容 H2 和 PostgreSQL。

## 六、Repository 只补派生查询

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageRepository.java`

v37 补了 3 个只读查询：

```java
long countByStatusNot(FailedEventMessageStatus status);

Optional<FailedEventMessage> findTopByReplayApprovalRequestedAtIsNotNullOrderByReplayApprovalRequestedAtDescIdDesc();

Optional<FailedEventMessage> findTopByReplayApprovalReviewedAtIsNotNullOrderByReplayApprovalReviewedAtDescIdDesc();
```

这些都是 Spring Data JPA 派生查询，不需要新增 Flyway 迁移，也不改变任何表结构。

## 七、测试覆盖

文件：`src/test/java/com/codexdemo/orderplatform/notification/FailedEventSummaryServiceTests.java`

单测固定 repository 返回值，确认服务层聚合映射和 latestApprovalAt 取较新值：

```java
when(failedEventMessageRepository.count()).thenReturn(8L);
when(failedEventMessageRepository.countByReplayApprovalStatus(FailedEventReplayApprovalStatus.PENDING))
        .thenReturn(2L);
when(failedEventMessageRepository.countByStatusNot(FailedEventMessageStatus.REPLAYED)).thenReturn(5L);
```

断言：

```java
assertThat(summary.totalFailedEvents()).isEqualTo(8L);
assertThat(summary.pendingReplayApprovals()).isEqualTo(2L);
assertThat(summary.latestApprovalAt()).isEqualTo(Instant.parse("2026-05-11T08:12:00Z"));
assertThat(summary.replayBacklog()).isEqualTo(5L);
```

文件：`src/test/java/com/codexdemo/orderplatform/FailedEventSummaryIntegrationTests.java`

集成测试通过 MockMvc 请求真实接口：

```java
mockMvc.perform(get("/api/v1/failed-events/summary"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalFailedEvents").value(4))
        .andExpect(jsonPath("$.pendingReplayApprovals").value(1))
        .andExpect(jsonPath("$.approvedReplayApprovals").value(1))
        .andExpect(jsonPath("$.rejectedReplayApprovals").value(1))
        .andExpect(jsonPath("$.latestApprovalAt").value("2026-05-11T08:20:00Z"))
        .andExpect(jsonPath("$.replayBacklog").value(3));
```

## 八、本版边界

v37 不做：

```text
不执行 replay
不审批 replay
不改失败事件状态
不新增持久化模型
不接 mini-kv
不修改 Node
```

它只给后续 Node v55 准备一个稳定 Java 读接口：

```text
Java /api/v1/failed-events/summary
 -> Node OrderPlatformClient.failedEventsSummary()
 -> Node upstream overview risk summary
```

## 一句话总结

v37 把 Java 失败事件治理从“可查明细”推进到“可读摘要”，让外部控制面不用拉全量列表也能判断失败事件积压、审批压力和最近治理活动。
