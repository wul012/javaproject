> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 第四十一版：失败事件重放 approval-status digest

## 本版目标

v41 按 `D:\nodeproj\orderops-node\docs\plans\v65-post-upstream-evidence-roadmap.md` 推进，目标是在 Java v40 的 `approval-status` 只读响应中增加：

```text
evidenceVersion
approvalDigest
replayEligibilityDigest
```

这三个字段让 Node 后续做 digest-aware upstream evidence verification 时，可以判断 Java 上游审批证据有没有漂移。

本版仍然不执行 replay，不申请审批，不审核审批，不调用 Node，不依赖 mini-kv。

## 改动文件

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalEvidenceDigests.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatusResponse.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatusService.java
src/test/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatusServiceTests.java
src/test/java/com/codexdemo/orderplatform/FailedEventReplayApprovalStatusIntegrationTests.java
README.md
a/41/解释/说明.md
```

## 一、响应对象增加证据字段

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatusResponse.java`

v40 的响应开头是：

```java
Instant sampledAt,
Long failedEventId,
boolean exists,
```

v41 在它们后面追加三类证据字段：

```java
String evidenceVersion,
String approvalDigest,
String replayEligibilityDigest,
```

字段含义：

```text
evidenceVersion
 -> 当前证据格式版本

approvalDigest
 -> 审批证据摘要

replayEligibilityDigest
 -> 审批层面的可重放判断摘要
```

`sampledAt` 不参与 digest。这样同一条失败事件在状态不变时，每次读取的采样时间可以不同，但 digest 保持稳定。

## 二、not found 也有稳定 digest

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatusResponse.java`

不存在 ID 仍然返回稳定结构：

```java
public static FailedEventReplayApprovalStatusResponse notFound(Long failedEventId, Instant sampledAt) {
```

v41 给 not found 响应同样补上证据版本：

```java
FailedEventReplayApprovalEvidenceDigests.EVIDENCE_VERSION,
```

审批 digest 使用不存在状态计算：

```java
FailedEventReplayApprovalEvidenceDigests.approvalDigest(
        failedEventId,
        false,
        null,
        FailedEventReplayApprovalStatus.APPROVED,
```

可重放 digest 使用明确阻断原因：

```java
FailedEventReplayApprovalEvidenceDigests.replayEligibilityDigest(
        failedEventId,
        false,
        null,
        null,
        null,
        FailedEventReplayApprovalStatus.APPROVED,
        false,
        List.of("FAILED_EVENT_NOT_FOUND"),
        List.of()
)
```

这保证 Node 查询不存在 ID 时，也能把结果作为可复核 evidence，而不是只能记录一段错误。

## 三、digest helper 只做纯计算

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalEvidenceDigests.java`

证据版本集中定义：

```java
static final String EVIDENCE_VERSION = "failed-event-approval-status.v1";
```

类本身不可实例化：

```java
final class FailedEventReplayApprovalEvidenceDigests {

    private FailedEventReplayApprovalEvidenceDigests() {
    }
}
```

它不读数据库，不访问网络，不执行业务动作，只把 service 已经读出的字段转换成规范文本后计算 SHA-256。

## 四、approvalDigest 覆盖审批证据本身

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalEvidenceDigests.java`

`approvalDigest` 的输入字段包括：

```java
approvalStatus,
requiredApprovalStatus,
approvalRequested,
approvalPending,
approvedForReplay,
rejected,
requestReason,
requestedBy,
requestedAt,
reviewedBy,
reviewedAt,
reviewNote,
historyCount,
latestApproval
```

代码里通过带 key 的行构造 canonical 内容：

```java
line("digestKind", "approval"),
line("evidenceVersion", EVIDENCE_VERSION),
line("failedEventId", failedEventId),
line("exists", exists),
line("approvalStatus", approvalStatus),
```

最新审批动作也进入 digest：

```java
line("latestApproval.action", latestApproval == null ? null : latestApproval.action()),
line("latestApproval.status", latestApproval == null ? null : latestApproval.status()),
line("latestApproval.operatorId", latestApproval == null ? null : latestApproval.operatorId()),
line("latestApproval.operatorRole", latestApproval == null ? null : latestApproval.operatorRole()),
line("latestApproval.note", latestApproval == null ? null : latestApproval.note()),
line("latestApproval.changedAt", latestApproval == null ? null : latestApproval.changedAt())
```

所以只要审批请求、审核人、审核备注、最新流水或历史数量变化，`approvalDigest` 就会变化。

## 五、replayEligibilityDigest 覆盖审批层面的可重放判断

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalEvidenceDigests.java`

这个 digest 不试图替代 v38 readiness。它只覆盖 approval-status 响应中和“审批层面能否继续向 replay 前进”有关的字段：

```java
line("failedEventStatus", failedEventStatus),
line("managementStatus", managementStatus),
line("approvalStatus", approvalStatus),
line("requiredApprovalStatus", requiredApprovalStatus),
line("approvedForReplay", approvedForReplay),
line("approvalBlockedBy", approvalBlockedBy),
line("nextAllowedActions", nextAllowedActions)
```

边界很重要：

```text
replayEligibilityDigest
 -> 校验 approval-status 这份响应里的审批层可重放判断

replay-readiness
 -> 仍然负责 RabbitMQ、payload、aggregateId、是否已重放等完整重放资格判断
```

因此 `approvedForReplay=true` 加 digest 匹配，也不等于真实 replay 一定能执行。

## 六、SHA-256 输出带算法前缀

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalEvidenceDigests.java`

规范文本使用换行拼接，并补一个结尾换行：

```java
String canonical = String.join("\n", lines) + "\n";
```

然后计算 SHA-256：

```java
byte[] bytes = MessageDigest.getInstance("SHA-256")
        .digest(canonical.getBytes(StandardCharsets.UTF_8));
```

最终输出：

```java
return "sha256:" + HexFormat.of().formatHex(bytes);
```

`sha256:` 前缀让 Node 后续不需要猜算法。

## 七、Service 先读模型，再计算 digest

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatusService.java`

v41 先把 v40 的派生值提出来：

```java
boolean approvalRequested = status != FailedEventReplayApprovalStatus.NOT_REQUESTED;
boolean approvalPending = status == FailedEventReplayApprovalStatus.PENDING;
boolean approvedForReplay = status == FailedEventReplayApprovalStatus.APPROVED;
boolean rejected = status == FailedEventReplayApprovalStatus.REJECTED;
```

再读历史数量和最新审批：

```java
long historyCount = failedEventReplayApprovalHistoryRepository.countByFailedEventMessageId(failedMessage.getId());
FailedEventReplayApprovalStatusResponse.LatestApproval latestApproval = latestApproval(failedMessage);
```

阻断和下一步动作也先固定：

```java
List<String> approvalBlockedBy = approvalBlockedBy(status);
List<String> nextAllowedActions = nextAllowedActions(failedMessage, status);
```

最后把同一组值同时放进响应和 digest：

```java
FailedEventReplayApprovalEvidenceDigests.approvalDigest(...)
FailedEventReplayApprovalEvidenceDigests.replayEligibilityDigest(...)
```

这样响应字段和 digest 输入不会出现“两套计算”的漂移。

## 八、测试验证 digest 存在且稳定

文件：`src/test/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalStatusServiceTests.java`

not found 响应验证版本和 digest：

```java
assertThat(response.evidenceVersion()).isEqualTo("failed-event-approval-status.v1");
assertThat(response.approvalDigest()).startsWith("sha256:");
assertThat(response.replayEligibilityDigest()).startsWith("sha256:");
```

审批通过状态验证 digest 存在：

```java
assertThat(response.approvalDigest()).startsWith("sha256:");
assertThat(response.replayEligibilityDigest()).startsWith("sha256:");
```

同一状态重复读取，digest 不因 `sampledAt` 变化而变化：

```java
FailedEventReplayApprovalStatusResponse repeated = service.approvalStatus(10L);
assertThat(repeated.approvalDigest()).isEqualTo(response.approvalDigest());
assertThat(repeated.replayEligibilityDigest()).isEqualTo(response.replayEligibilityDigest());
```

文件：`src/test/java/com/codexdemo/orderplatform/FailedEventReplayApprovalStatusIntegrationTests.java`

HTTP JSON 层验证字段对外可见：

```java
.andExpect(jsonPath("$.evidenceVersion").value("failed-event-approval-status.v1"))
.andExpect(jsonPath("$.approvalDigest").exists())
.andExpect(jsonPath("$.replayEligibilityDigest").exists())
```

## 一句话总结

v41 把 Java 的 approval-status 从“可读审批快照”升级成“带版本和稳定摘要的上游证据”，为 Node 后续 digest-aware verification 做准备，同时保持完全只读。
