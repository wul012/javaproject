# 第二十九版：失败事件操作员上下文解析

## 本版目标

v16 到 v28 已经逐步给失败事件重放补上了角色校验、原因记录、审计、审批、审批历史和职责分离。但这些写操作仍然都直接依赖请求头：

```text
X-Operator-Id
X-Operator-Role
```

v29 的目标不是一下子接入完整登录系统，而是先把“从请求头解析操作员”收拢成一个明确入口。这样后续接 Spring Security、JWT 或 Session 登录态时，只需要替换解析器，而不是到处找散落的头部读取和角色规范化逻辑。

## 改动文件

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventOperatorContext.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventOperatorContextResolver.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventOperatorContextResponse.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageController.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageService.java
src/main/resources/static/failed-events.html
src/main/resources/static/failed-events.js
src/main/resources/static/failed-events.css
src/test/java/com/codexdemo/orderplatform/FailedEventOperatorContextIntegrationTests.java
src/test/java/com/codexdemo/orderplatform/FailedEventManagementPageTests.java
README.md
a/29/解释/说明.md
```

## 一、操作员上下文对象

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventOperatorContext.java`

这个 record 很小，但它把过去的两个松散字符串变成一个业务概念：

```java
public record FailedEventOperatorContext(String operatorId, String operatorRole) {
}
```

它表达的是：

```text
一次失败事件写操作是谁发起的
这个人以什么角色发起
```

后续如果接入真实登录态，这个对象可以继续保留，只是来源从 HTTP Header 换成认证上下文。

## 二、统一解析和校验

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventOperatorContextResolver.java`

解析器集中定义两个请求头名称：

```java
public static final String OPERATOR_ID_HEADER = "X-Operator-Id";

public static final String OPERATOR_ROLE_HEADER = "X-Operator-Role";
```

Controller 可以直接把 Spring 的 `HttpHeaders` 交给它：

```java
public FailedEventOperatorContext resolve(HttpHeaders headers) {
    return resolve(headers.getFirst(OPERATOR_ID_HEADER), headers.getFirst(OPERATOR_ROLE_HEADER));
}
```

服务层测试或内部调用仍然可以传字符串：

```java
public FailedEventOperatorContext resolve(String operatorId, String operatorRole) {
    return new FailedEventOperatorContext(normalizeOperatorId(operatorId), requireAllowedOperatorRole(operatorRole));
}
```

操作员 ID 负责去空格和限长：

```java
private String normalizeOperatorId(String operatorId) {
    if (!StringUtils.hasText(operatorId)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, OPERATOR_ID_HEADER + " header is required");
    }
    return truncate(operatorId.strip(), 80);
}
```

角色负责规范化成大写，并复用原来的允许角色配置：

```java
private String requireAllowedOperatorRole(String operatorRole) {
    if (!StringUtils.hasText(operatorRole)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, OPERATOR_ROLE_HEADER + " header is required");
    }
    String normalizedRole = failedEventReplayProperties.normalize(operatorRole);
    if (!failedEventReplayProperties.isAllowedRole(normalizedRole)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "operator role is not allowed to replay failed events");
    }
    return truncate(normalizedRole, 80);
}
```

这里保留了原有行为：

```text
缺少 X-Operator-Id -> 400 Bad Request
缺少 X-Operator-Role -> 403 Forbidden
角色不在允许列表 -> 403 Forbidden
role=sre -> SRE
role=order_support -> ORDER_SUPPORT
```

## 三、Controller 不再散落解析逻辑

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageController.java`

Controller 注入解析器：

```java
private final FailedEventOperatorContextResolver operatorContextResolver;
```

新增一个轻量探针端点：

```java
@GetMapping("/operator-context")
public FailedEventOperatorContextResponse resolveOperatorContext(@RequestHeader HttpHeaders headers) {
    return FailedEventOperatorContextResponse.from(
            operatorContextResolver.resolve(headers),
            operatorContextResolver.allowedRoles()
    );
}
```

这个接口的作用是让页面或调试命令提前确认：

```text
当前 X-Operator-* 会被后端解析成谁
角色会不会被接受
允许角色列表是什么
```

管理状态写接口也改成先解析上下文：

```java
@PostMapping("/management-status")
public FailedEventManagementBatchResponse markManagementStatus(
        @RequestHeader HttpHeaders headers,
        @RequestBody MarkFailedEventManagementRequest request
) {
    return failedEventMessageService.markManagementStatus(request, operatorContextResolver.resolve(headers));
}
```

重放申请、审批和重放提交同样走这个入口：

```java
return failedEventMessageService.requestReplayApproval(id, request, operatorContextResolver.resolve(headers));
return failedEventMessageService.reviewReplayApproval(id, request, operatorContextResolver.resolve(headers));
return failedEventMessageService.replay(id, request, operatorContextResolver.resolve(headers));
```

## 四、Service 保留兼容入口，核心逻辑使用上下文

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageService.java`

为了不破坏已有服务层测试，原来的字符串方法保留，但只做一件事：委托解析器。

```java
public FailedEventManagementBatchResponse markManagementStatus(
        MarkFailedEventManagementRequest request,
        String operatorId,
        String operatorRole
) {
    return markManagementStatus(request, operatorContextResolver.resolve(operatorId, operatorRole));
}
```

真正的业务方法接收 `FailedEventOperatorContext`：

```java
public FailedEventManagementBatchResponse markManagementStatus(
        MarkFailedEventManagementRequest request,
        FailedEventOperatorContext operatorContext
) {
```

再统一取出规范化后的身份：

```java
FailedEventOperatorContext operator = requireOperatorContext(operatorContext);
String normalizedOperatorId = operator.operatorId();
String normalizedOperatorRole = operator.operatorRole();
```

审批申请也是同样模式：

```java
public FailedEventMessageResponse requestReplayApproval(
        Long id,
        RequestFailedEventReplayApprovalRequest request,
        FailedEventOperatorContext operatorContext
) {
    FailedEventOperatorContext operator = requireOperatorContext(operatorContext);
    String normalizedOperatorId = operator.operatorId();
    String normalizedOperatorRole = operator.operatorRole();
```

审批审核继续沿用 v28 的职责分离规则，只是 reviewer 已经来自统一上下文：

```java
ensureReplayApprovalReviewerIsDifferent(failedMessage, normalizedOperatorId);
```

重放接口也同样改造：

```java
public FailedEventMessageResponse replay(
        Long id,
        ReplayFailedEventRequest request,
        FailedEventOperatorContext operatorContext
) {
    FailedEventOperatorContext operator = requireOperatorContext(operatorContext);
    String normalizedOperatorId = operator.operatorId();
    String normalizedOperatorRole = operator.operatorRole();
```

这样后续替换身份来源时，审计、管理流水、审批流水和重放流水都不用改字段语义。

## 五、页面增加身份校验入口

文件：`src/main/resources/static/failed-events.html`

批量标记区域增加校验按钮和结果占位：

```html
<button id="verifyOperatorButton" class="ghost-button" type="button">校验身份</button>
<span id="operatorContextStatus" class="operator-context-status">未校验</span>
```

重放工作台也增加同样入口：

```html
<button id="verifyReplayOperatorButton" class="ghost-button" type="button">校验身份</button>
<span id="replayOperatorContextStatus" class="operator-context-status">未校验</span>
```

文件：`src/main/resources/static/failed-events.js`

页面初始化时绑定按钮：

```javascript
document.getElementById("verifyOperatorButton").addEventListener("click", () => verifyOperatorContext("management"));
document.getElementById("verifyReplayOperatorButton").addEventListener("click", () => verifyOperatorContext("replay"));
```

校验函数请求新端点：

```javascript
async function verifyOperatorContext(scope) {
    const statusElement = scope === "replay"
            ? elements.replayOperatorContextStatus
            : elements.operatorContextStatus;
    const headers = scope === "replay" ? replayContextHeaders() : managementOperatorHeaders();
    try {
        statusElement.textContent = "校验中";
        const result = await fetchJson(`${apiBase}/operator-context`, { headers });
        const summary = `${result.operatorId} / ${result.operatorRole}`;
        statusElement.textContent = summary;
        showToast(`身份已通过: ${summary}`);
    } catch (error) {
        statusElement.textContent = "校验失败";
        showToast(error.message, true);
    }
}
```

管理状态提交现在复用同一组 Header 构造：

```javascript
function managementOperatorHeaders() {
    return {
        "X-Operator-Id": elements.operatorIdInput.value,
        "X-Operator-Role": elements.operatorRoleInput.value
    };
}
```

重放相关写操作也拆出纯身份 Header：

```javascript
function replayOperatorHeaders() {
    return {
        "Content-Type": "application/json",
        ...replayContextHeaders()
    };
}

function replayContextHeaders() {
    return {
        "X-Operator-Id": elements.replayOperatorIdInput.value,
        "X-Operator-Role": elements.replayOperatorRoleInput.value
    };
}
```

## 六、测试覆盖

文件：`src/test/java/com/codexdemo/orderplatform/FailedEventOperatorContextIntegrationTests.java`

第一个测试确认探针端点会去空格、规范化角色，并返回允许角色：

```java
mockMvc.perform(get("/api/v1/failed-events/operator-context")
                .header("X-Operator-Id", " ops-user ")
                .header("X-Operator-Role", " sre "))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.operatorId").value("ops-user"))
        .andExpect(jsonPath("$.operatorRole").value("SRE"))
        .andExpect(jsonPath("$.allowedRoles").value(containsInAnyOrder("ORDER_SUPPORT", "SRE", "SYSTEM")));
```

同一个测试再确认管理状态写操作会使用规范化后的上下文：

```java
mockMvc.perform(post("/api/v1/failed-events/management-status")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Operator-Id", " ops-user ")
                .header("X-Operator-Role", " order_support ")
                .content("""
                        {
                          "ids": [%d],
                          "status": "INVESTIGATING",
                          "note": "operator context v29"
                        }
                        """.formatted(failedMessage.getId())))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.items[0].managedBy").value("ops-user"));
```

最后查管理历史，证明落库角色已经统一成 `ORDER_SUPPORT`：

```java
assertThat(failedEventManagementHistoryRepository
        .findByFailedEventMessageIdOrderByChangedAtDescIdDesc(failedMessage.getId()))
        .singleElement()
        .satisfies(history -> {
            assertThat(history.getOperatorId()).isEqualTo("ops-user");
            assertThat(history.getOperatorRole()).isEqualTo("ORDER_SUPPORT");
            assertThat(history.getNote()).isEqualTo("operator context v29");
        });
```

第二个测试先确认非法角色会被拒绝：

```java
mockMvc.perform(get("/api/v1/failed-events/operator-context")
                .header("X-Operator-Id", "ops-user")
                .header("X-Operator-Role", "viewer"))
        .andExpect(status().isForbidden());
```

再确认重放审批申请也走同一套上下文：

```java
mockMvc.perform(post("/api/v1/failed-events/{id}/replay-approval", failedMessage.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Operator-Id", " ops-user ")
                .header("X-Operator-Role", " sre ")
                .content("""
                        {
                          "reason": "operator context approval request"
                        }
                        """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.replayApprovalRequestedBy").value("ops-user"))
        .andExpect(jsonPath("$.replayApprovalStatus").value("PENDING"));
```

文件：`src/test/java/com/codexdemo/orderplatform/FailedEventManagementPageTests.java`

静态页面测试补上关键页面元素和脚本入口：

```java
"verifyOperatorButton",
"operatorContextStatus",
"verifyReplayOperatorButton",
"replayOperatorContextStatus",
```

脚本侧确认新接口和函数仍然存在：

```java
"/operator-context",
"verifyOperatorContext",
```

## 七、本版后的身份链路

v29 后，失败事件写操作变成：

```text
HTTP Header
 -> FailedEventOperatorContextResolver
 -> FailedEventOperatorContext
 -> FailedEventMessageService
 -> 管理状态流水 / 审批流水 / 重放审计
```

这比过去直接把字符串一路传到底更清楚：

```text
请求头解析在哪里做
角色允许列表在哪里校验
大小写和空格在哪里规范化
未来登录态替换点在哪里
```

## 一句话总结

v29 没有急着做完整登录系统，而是先把失败事件写操作的操作者身份收拢成统一上下文，为后续真实认证鉴权打下干净接口。
