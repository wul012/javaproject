# 第二十七版：失败事件重放审批历史流水

## 本版目标

v25 已经把失败事件重放从“按钮直接提交”升级成“先申请、再审批、批准后才能重放”：

```text
REQUEST approval
 -> PENDING
 -> APPROVED / REJECTED
 -> APPROVED 后才允许 replay
```

但 v25 的审批信息主要保存在 `failed_event_messages` 当前行上，适合展示“当前状态”，不适合复盘“每一次申请、拒绝、再次申请、批准”的完整过程。

v27 的目标就是补齐这条审计链：

```text
当前状态字段
 -> 仍然保留在 failed_event_messages 上，方便列表筛选和重放门禁判断

历史流水表
 -> 新增 failed_event_replay_approval_history
 -> 每次 REQUESTED / REJECTED / APPROVED 都单独落一条
 -> 支持单条查询、全局筛选、CSV 导出和页面查看
```

## 改动文件

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalHistory.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalHistoryAction.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalHistoryRepository.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalHistoryResponse.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalHistorySearchCriteria.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageService.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageController.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventCsvExporter.java
src/main/resources/db/migration/h2/V11__failed_event_replay_approval_history.sql
src/main/resources/db/migration/postgresql/V11__failed_event_replay_approval_history.sql
src/main/resources/static/failed-events.html
src/main/resources/static/failed-events.js
src/main/resources/static/failed-events.css
src/test/java/com/codexdemo/orderplatform/FailedEventSearchIntegrationTests.java
src/test/java/com/codexdemo/orderplatform/FailedEventManagementPageTests.java
src/test/java/com/codexdemo/orderplatform/PostgresMigrationIntegrationTests.java
README.md
a/27/解释/说明.md
```

## 一、审批历史动作枚举

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalHistoryAction.java`

```java
public enum FailedEventReplayApprovalHistoryAction {
    REQUESTED,
    APPROVED,
    REJECTED
}
```

这里没有把 `NOT_REQUESTED` 和 `PENDING` 放进去，因为历史表记录的是“发生过的动作”，不是当前状态。

对应关系是：

```text
申请审批
 -> 写 REQUESTED
 -> 当前状态变 PENDING

审批通过
 -> 写 APPROVED
 -> 当前状态变 APPROVED

审批拒绝
 -> 写 REJECTED
 -> 当前状态变 REJECTED
```

## 二、审批历史实体

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayApprovalHistory.java`

核心表名：

```java
@Entity
@Table(
        name = "failed_event_replay_approval_history",
        indexes = {
                @Index(
                        name = "idx_failed_event_replay_approval_history_message",
                        columnList = "failed_event_message_id, changed_at"
                ),
                @Index(
                        name = "idx_failed_event_replay_approval_history_action",
                        columnList = "action, changed_at"
                ),
                @Index(
                        name = "idx_failed_event_replay_approval_history_operator_role",
                        columnList = "operator_role, changed_at"
                ),
                @Index(
                        name = "idx_failed_event_replay_approval_history_operator_id",
                        columnList = "operator_id, changed_at"
                )
        }
)
public class FailedEventReplayApprovalHistory {
```

这四个索引对应四类排查问题：

```text
按失败事件看审批过程
 -> failed_event_message_id, changed_at

看所有拒绝或批准记录
 -> action, changed_at

看某个角色做过哪些审批动作
 -> operator_role, changed_at

看某个操作人做过哪些审批动作
 -> operator_id, changed_at
```

实体字段：

```java
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "failed_event_message_id", nullable = false)
private FailedEventMessage failedEventMessage;

@Enumerated(EnumType.STRING)
@Column(nullable = false, length = 32)
private FailedEventReplayApprovalHistoryAction action;

@Column(name = "operator_id", nullable = false, length = 80)
private String operatorId;

@Column(name = "operator_role", nullable = false, length = 80)
private String operatorRole;

@Column(length = 500)
private String note;

@Column(name = "changed_at", nullable = false)
private Instant changedAt;
```

这里的 `note` 允许为空，是为了支持“批准但没有额外备注”的场景；申请原因和拒绝原因仍然在服务层强制校验。

统一创建入口：

```java
public static FailedEventReplayApprovalHistory record(
        FailedEventMessage failedEventMessage,
        FailedEventReplayApprovalHistoryAction action,
        String operatorId,
        String operatorRole,
        String note,
        Instant changedAt
) {
    return new FailedEventReplayApprovalHistory(
            failedEventMessage,
            action,
            operatorId,
            operatorRole,
            note,
            changedAt
    );
}
```

这个 `record(...)` 和项目里已有的 `FailedEventManagementHistory.record(...)`、`FailedEventReplayAttempt.record(...)` 保持同一种写法。

## 三、Flyway V11 迁移

文件：

```text
src/main/resources/db/migration/h2/V11__failed_event_replay_approval_history.sql
src/main/resources/db/migration/postgresql/V11__failed_event_replay_approval_history.sql
```

建表语句：

```sql
create table failed_event_replay_approval_history (
    id bigint generated by default as identity primary key,
    failed_event_message_id bigint not null,
    action varchar(32) not null,
    operator_id varchar(80) not null,
    operator_role varchar(80) not null,
    note varchar(500),
    changed_at timestamp(6) with time zone not null,
    constraint fk_failed_event_replay_approval_history_message
        foreign key (failed_event_message_id) references failed_event_messages (id)
);
```

索引语句：

```sql
create index idx_failed_event_replay_approval_history_message
    on failed_event_replay_approval_history (failed_event_message_id, changed_at);

create index idx_failed_event_replay_approval_history_action
    on failed_event_replay_approval_history (action, changed_at);

create index idx_failed_event_replay_approval_history_operator_role
    on failed_event_replay_approval_history (operator_role, changed_at);

create index idx_failed_event_replay_approval_history_operator_id
    on failed_event_replay_approval_history (operator_id, changed_at);
```

这次 H2 和 PostgreSQL 脚本保持一致，配合 Hibernate validate 检查实体和真实表结构是否对齐。

## 四、审批申请时写历史

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageService.java`

v26 之前，申请审批只改当前失败事件行：

```java
failedMessage.requestReplayApproval(reason, normalizedOperatorId, Instant.now());
```

v27 改成同一个事务里同时写当前状态和历史流水：

```java
String normalizedOperatorId = normalizeOperatorId(operatorId);
String normalizedOperatorRole = requireAllowedOperatorRole(operatorRole);
String reason = resolveReplayApprovalReason(request);
Instant requestedAt = Instant.now();

failedMessage.requestReplayApproval(reason, normalizedOperatorId, requestedAt);
failedEventReplayApprovalHistoryRepository.save(FailedEventReplayApprovalHistory.record(
        failedMessage,
        FailedEventReplayApprovalHistoryAction.REQUESTED,
        normalizedOperatorId,
        normalizedOperatorRole,
        reason,
        requestedAt
));
```

关键点有两个：

```text
1. normalizedOperatorRole 不再只做校验，还会落库
2. requestedAt 同时用于当前状态字段和历史流水 changedAt
```

这样排查时不会出现“当前行显示最新申请人，但不知道前一次谁拒绝过”的信息缺口。

## 五、审批通过/拒绝时写历史

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageService.java`

审批动作先校验当前状态必须是 `PENDING`：

```java
if (failedMessage.getReplayApprovalStatus() != FailedEventReplayApprovalStatus.PENDING) {
    throw new ResponseStatusException(HttpStatus.CONFLICT, "replay approval is not pending");
}
```

然后根据审批结果更新当前状态：

```java
Instant reviewedAt = Instant.now();
if (reviewStatus == FailedEventReplayApprovalStatus.APPROVED) {
    failedMessage.approveReplay(normalizedOperatorId, note, reviewedAt);
} else {
    failedMessage.rejectReplay(normalizedOperatorId, note, reviewedAt);
}
```

最后写历史流水：

```java
failedEventReplayApprovalHistoryRepository.save(FailedEventReplayApprovalHistory.record(
        failedMessage,
        FailedEventReplayApprovalHistoryAction.valueOf(reviewStatus.name()),
        normalizedOperatorId,
        normalizedOperatorRole,
        note,
        reviewedAt
));
```

这里 `reviewStatus` 只允许是 `APPROVED` 或 `REJECTED`，刚好能映射到历史动作枚举。

## 六、查询和导出服务

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageService.java`

排序白名单：

```java
private static final Map<String, String> REPLAY_APPROVAL_HISTORY_SORT_FIELDS = Map.of(
        "id", "id",
        "changedAt", "changedAt",
        "action", "action",
        "operatorId", "operatorId",
        "operatorRole", "operatorRole"
);
```

单条失败事件审批历史：

```java
@Transactional(readOnly = true)
public List<FailedEventReplayApprovalHistoryResponse> listReplayApprovalHistory(Long failedEventMessageId) {
    validateSearchId(failedEventMessageId, "failedEventMessageId");
    if (!failedEventMessageRepository.existsById(failedEventMessageId)) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "failed event message not found");
    }
    return failedEventReplayApprovalHistoryRepository
            .findByFailedEventMessageIdOrderByChangedAtDescIdDesc(failedEventMessageId)
            .stream()
            .map(FailedEventReplayApprovalHistoryResponse::from)
            .toList();
}
```

全局筛选：

```java
public PagedResponse<FailedEventReplayApprovalHistoryResponse> searchReplayApprovalHistory(
        FailedEventReplayApprovalHistorySearchCriteria criteria
) {
    validateSearchId(normalizedCriteria.failedEventMessageId(), "failedEventMessageId");
    validateTimeRange(
            normalizedCriteria.changedFrom(),
            normalizedCriteria.changedTo(),
            "changedFrom",
            "changedTo"
    );
    NormalizedPageRequest pageRequest = normalizePageRequest(
            normalizedCriteria.page(),
            normalizedCriteria.size(),
            normalizedCriteria.limit(),
            normalizedCriteria.sort(),
            REPLAY_APPROVAL_HISTORY_SORT_FIELDS,
            "changedAt,desc"
    );
```

动态查询条件：

```java
if (criteria.failedEventMessageId() != null) {
    predicates.add(criteriaBuilder.equal(
            root.get("failedEventMessage").get("id"),
            criteria.failedEventMessageId()
    ));
}
if (criteria.action() != null) {
    predicates.add(criteriaBuilder.equal(root.get("action"), criteria.action()));
}
addTextEquals(predicates, criteriaBuilder, root.get("operatorId"), criteria.operatorId());
addTextEquals(
        predicates,
        criteriaBuilder,
        root.get("operatorRole"),
        failedEventReplayProperties.normalize(criteria.operatorRole())
);
```

这和失败事件查询、重放审计查询、管理状态流水查询保持同一个模式：先把条件对象标准化，再由 `Specification` 拼查询。

## 七、Controller 新接口

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageController.java`

单条查询：

```java
@GetMapping("/{id}/replay-approval-history")
public List<FailedEventReplayApprovalHistoryResponse> listReplayApprovalHistory(@PathVariable Long id) {
    return failedEventMessageService.listReplayApprovalHistory(id);
}
```

全局分页筛选：

```java
@GetMapping("/replay-approval-history")
public PagedResponse<FailedEventReplayApprovalHistoryResponse> searchReplayApprovalHistory(
        @RequestParam(required = false) Long failedEventMessageId,
        @RequestParam(required = false) FailedEventReplayApprovalHistoryAction action,
        @RequestParam(required = false) String operatorId,
        @RequestParam(required = false) String operatorRole,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant changedFrom,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant changedTo,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size,
        @RequestParam(required = false) String sort,
        @RequestParam(required = false) Integer limit
) {
```

CSV 导出：

```java
@GetMapping(value = "/replay-approval-history/export", produces = "text/csv")
public ResponseEntity<String> exportReplayApprovalHistory(...) {
    String csv = failedEventMessageService.exportReplayApprovalHistoryCsv(...);
    return csvResponse("failed-event-replay-approval-history.csv", csv);
}
```

接口结构和已有接口保持一致：

```text
GET /api/v1/failed-events/{id}/management-history
GET /api/v1/failed-events/management-history
GET /api/v1/failed-events/management-history/export

GET /api/v1/failed-events/{id}/replay-approval-history
GET /api/v1/failed-events/replay-approval-history
GET /api/v1/failed-events/replay-approval-history/export
```

## 八、CSV 导出

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventCsvExporter.java`

新增方法：

```java
static String replayApprovalHistory(List<FailedEventReplayApprovalHistoryResponse> history) {
    StringBuilder csv = new StringBuilder();
    appendRow(
            csv,
            List.of(
                    "id",
                    "failedEventMessageId",
                    "action",
                    "operatorId",
                    "operatorRole",
                    "note",
                    "changedAt"
            )
    );
```

逐行写出：

```java
history.forEach(item -> appendRow(
        csv,
        List.of(
                value(item.id()),
                value(item.failedEventMessageId()),
                value(item.action()),
                value(item.operatorId()),
                value(item.operatorRole()),
                value(item.note()),
                value(item.changedAt())
        )
));
```

CSV 转义逻辑继续复用已有的 `appendRow(...)` / `escape(...)`，所以备注里有逗号、双引号、换行时仍然能正确导出。

## 九、页面审批历史面板

文件：`src/main/resources/static/failed-events.html`

重放工作台里新增审批历史区域：

```html
<div class="approval-history-block">
    <div class="replay-section-title">
        <h3>Approval history</h3>
        <button id="refreshApprovalHistoryButton" class="ghost-button" type="button">Refresh</button>
    </div>
    <div id="approvalHistoryList" class="approval-history-list">
        <div class="approval-history-item">Not selected</div>
    </div>
</div>
```

文件：`src/main/resources/static/failed-events.js`

选择一条失败事件进入重放工作台时，现在会同时加载三类信息：

```javascript
async function prepareReplay(id) {
    setActiveEvent(id);
    await Promise.all([
        loadHistory(id),
        loadReplayApprovalHistory(id),
        loadReplayAttempts(id)
    ]);
}
```

审批申请/审批通过/审批拒绝后，也会刷新审批历史：

```javascript
async function handleReplayApprovalResult(result, message) {
    state.activeEvent = result;
    state.activeEventId = result.id;
    state.itemsById.set(result.id, result);
    renderReplayMeta(result);
    updateReplayPlaceholders(result);
    showToast(`${message}: ${result.replayApprovalStatus}`);
    await loadFailedEvents();
    await loadReplayApprovalHistory(result.id);
}
```

渲染审批历史：

```javascript
function renderReplayApprovalHistory(history) {
    if (!history || history.length === 0) {
        elements.approvalHistoryList.innerHTML = '<div class="approval-history-item">No approval history</div>';
        return;
    }
    elements.approvalHistoryList.innerHTML = history.map((item) => `
        <article class="approval-history-item">
            <div class="approval-history-line">
                ${statusPill(item.action, replayApprovalActionClass(item.action))}
                <span>${formatDate(item.changedAt)}</span>
            </div>
            <div class="muted">${escapeHtml(item.operatorId || "")} / ${escapeHtml(item.operatorRole || "")}</div>
            <div class="history-note">${escapeHtml(item.note || "")}</div>
        </article>
    `).join("");
}
```

动作颜色映射：

```javascript
function replayApprovalActionClass(value) {
    switch (value) {
        case "APPROVED":
            return "status-resolved";
        case "REJECTED":
            return "status-failed";
        case "REQUESTED":
            return "status-investigating";
        default:
            return "";
    }
}
```

文件：`src/main/resources/static/failed-events.css`

审批历史面板用独立样式，避免和重放审计列表混在一起：

```css
.approval-history-block {
    display: grid;
    gap: 10px;
    padding: 14px;
    border-top: 1px solid var(--line);
}

.approval-history-list {
    display: grid;
    gap: 10px;
    max-height: 240px;
    overflow: auto;
}

.approval-history-item {
    border: 1px solid var(--line);
    border-left: 4px solid #7462e0;
    border-radius: 8px;
    padding: 10px;
    background: #fcfbff;
}
```

## 十、集成测试

文件：`src/test/java/com/codexdemo/orderplatform/FailedEventSearchIntegrationTests.java`

每次测试前先清理新表：

```java
@BeforeEach
void cleanFailedEventData() {
    failedEventReplayApprovalHistoryRepository.deleteAll();
    failedEventManagementHistoryRepository.deleteAll();
    failedEventReplayAttemptRepository.deleteAll();
    failedEventMessageRepository.deleteAll();
}
```

申请后断言历史表有 `REQUESTED`：

```java
assertThat(failedEventMessageService.listReplayApprovalHistory(failedMessage.getId()))
        .singleElement()
        .satisfies(history -> {
            assertThat(history.failedEventMessageId()).isEqualTo(failedMessage.getId());
            assertThat(history.action()).isEqualTo(FailedEventReplayApprovalHistoryAction.REQUESTED);
            assertThat(history.operatorId()).isEqualTo("ops-user");
            assertThat(history.operatorRole()).isEqualTo("SRE");
            assertThat(history.note()).isEqualTo("operator verified the fixed event headers");
            assertThat(history.changedAt()).isNotNull();
        });
```

完整流程后断言四条历史：

```java
assertThat(approvalHistory).extracting(FailedEventReplayApprovalHistoryResponse::action)
        .containsExactly(
                FailedEventReplayApprovalHistoryAction.APPROVED,
                FailedEventReplayApprovalHistoryAction.REQUESTED,
                FailedEventReplayApprovalHistoryAction.REJECTED,
                FailedEventReplayApprovalHistoryAction.REQUESTED
        );
```

也就是说这条失败事件经历了：

```text
第一次申请
 -> 拒绝
 -> 第二次申请
 -> 批准
```

筛选拒绝历史：

```java
PagedResponse<FailedEventReplayApprovalHistoryResponse> rejectedHistory =
        failedEventMessageService.searchReplayApprovalHistory(new FailedEventReplayApprovalHistorySearchCriteria(
                failedMessage.getId(),
                FailedEventReplayApprovalHistoryAction.REJECTED,
                "sre-lead",
                "sre",
                pending.replayApprovalRequestedAt().minusSeconds(1),
                Instant.now().plusSeconds(5),
                0,
                10,
                "changedAt,desc",
                null
        ));
```

导出 CSV：

```java
String approvalHistoryCsv = failedEventMessageService.exportReplayApprovalHistoryCsv(
        new FailedEventReplayApprovalHistorySearchCriteria(
                failedMessage.getId(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                "changedAt,desc",
                10
        )
);
```

非法参数也补了回归：

```java
assertBadRequest(() -> failedEventMessageService.searchReplayApprovalHistory(
        new FailedEventReplayApprovalHistorySearchCriteria(null, null, null, null, now, now.minusSeconds(1), 10)
));

assertBadRequest(() -> failedEventMessageService.exportReplayApprovalHistoryCsv(
        new FailedEventReplayApprovalHistorySearchCriteria(null, null, null, null, null, null, null, null, "messageId,desc", 10)
));
```

## 十一、PostgreSQL 迁移测试

文件：`src/test/java/com/codexdemo/orderplatform/PostgresMigrationIntegrationTests.java`

迁移数从 10 变成 11：

```java
assertThat(appliedMigrations).isEqualTo(11);
```

核心业务表从 12 张变成 13 张：

```java
assertThat(tableCount).isEqualTo(13);
```

新增表纳入真实 PostgreSQL 验证：

```sql
'failed_event_replay_approval_history'
```

这个测试只有 Docker 可用时才会跑：

```java
@Testcontainers(disabledWithoutDocker = true)
```

所以本地不开 Docker 时不会阻塞普通开发；开启 Docker 后可以验证 PostgreSQL 方言脚本。

## 十二、本版后的链路

现在失败事件重放从页面到数据库的链路是：

```text
失败事件进入 DLQ
 -> failed_event_messages 记录失败事件
 -> 管理页选择事件
 -> Request approval
 -> failed_event_messages 当前状态改成 PENDING
 -> failed_event_replay_approval_history 写 REQUESTED
 -> Approve / Reject
 -> failed_event_messages 当前状态改成 APPROVED / REJECTED
 -> failed_event_replay_approval_history 写 APPROVED / REJECTED
 -> APPROVED 后才能提交 replay
 -> failed_event_replay_attempts 记录重放尝试
```

一条失败事件现在同时有三类“可复盘数据”：

```text
failed_event_messages
 -> 当前状态、当前审批状态、当前管理状态

failed_event_replay_approval_history
 -> 申请、拒绝、批准的审批流水

failed_event_replay_attempts
 -> 真正发起重放后的投递尝试流水
```

## 一句话总结

v27 把“重放审批”从只看当前状态升级成可复盘的审批流水：谁申请、谁拒绝、谁再次申请、谁批准，都能查询、导出，并能在失败事件管理页直接查看。
