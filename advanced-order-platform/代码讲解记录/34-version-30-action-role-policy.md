# 第三十版：失败事件动作级角色策略

## 本版目标

v29 已经把失败事件写操作的身份入口收拢成 `FailedEventOperatorContextResolver`。但它仍然只有一份通用允许角色：

```text
allowed-roles
 -> ORDER_SUPPORT / SRE / SYSTEM
```

这对真实运维不够细。管理失败事件、申请重放、审批重放、执行重放是不同风险等级的动作。v30 的目标是继续在统一操作员上下文之上拆出动作级角色策略：

```text
MANAGE_FAILED_EVENT
REQUEST_REPLAY_APPROVAL
REVIEW_REPLAY_APPROVAL
REPLAY_FAILED_EVENT
```

默认策略中，`ORDER_SUPPORT` 可以管理、申请和重放，但不能审批重放申请；审批动作只允许 `SRE` 和 `SYSTEM`。

## 改动文件

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventOperatorAction.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayProperties.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventOperatorContextResolver.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventOperatorContextResponse.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageController.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageService.java
src/main/resources/application.yml
src/main/resources/static/failed-events.js
src/test/java/com/codexdemo/orderplatform/FailedEventOperatorContextIntegrationTests.java
src/test/java/com/codexdemo/orderplatform/FailedEventManagementPageTests.java
README.md
a/30/解释/说明.md
```

## 一、用枚举定义动作边界

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventOperatorAction.java`

这版新增一个很小的 enum，但它是权限策略能拆开的前提：

```java
public enum FailedEventOperatorAction {
    MANAGE_FAILED_EVENT,
    REQUEST_REPLAY_APPROVAL,
    REVIEW_REPLAY_APPROVAL,
    REPLAY_FAILED_EVENT
}
```

过去的逻辑只有“这个角色能不能操作失败事件”：

```text
role -> allowedRoles -> true / false
```

现在变成：

```text
role + action -> rolesFor(action) -> true / false
```

这样以后新增“导出敏感 Payload”“强制关闭失败事件”“跳过审批重放”等动作时，可以继续扩展枚举，而不用再复制一套散乱的字符串判断。

## 二、配置层拆出四组角色

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayProperties.java`

v30 保留原来的基础允许角色：

```java
private List<String> allowedRoles = List.of("ORDER_SUPPORT", "SRE", "SYSTEM");
```

同时新增四组动作角色：

```java
private List<String> managementRoles = List.of("ORDER_SUPPORT", "SRE", "SYSTEM");

private List<String> replayApprovalRequestRoles = List.of("ORDER_SUPPORT", "SRE", "SYSTEM");

private List<String> replayApprovalReviewRoles = List.of("SRE", "SYSTEM");

private List<String> replayRoles = List.of("ORDER_SUPPORT", "SRE", "SYSTEM");
```

核心判断方法是 `isAllowedFor`：

```java
public boolean isAllowedFor(FailedEventOperatorAction action, String role) {
    return isRoleIn(rolesFor(action), role);
}
```

动作到角色集合的映射集中在 `rolesFor`：

```java
public List<String> rolesFor(FailedEventOperatorAction action) {
    return switch (action) {
        case MANAGE_FAILED_EVENT -> managementRoles;
        case REQUEST_REPLAY_APPROVAL -> replayApprovalRequestRoles;
        case REVIEW_REPLAY_APPROVAL -> replayApprovalReviewRoles;
        case REPLAY_FAILED_EVENT -> replayRoles;
    };
}
```

这里故意没有把判断写在 Controller 或 Service 里，因为配置类负责表达“策略是什么”，解析器负责表达“当前请求是否符合策略”。

文件：`src/main/resources/application.yml`

默认配置直接展示每个动作允许哪些角色：

```yaml
failed-event:
  replay:
    allowed-roles:
      - ORDER_SUPPORT
      - SRE
      - SYSTEM
    management-roles:
      - ORDER_SUPPORT
      - SRE
      - SYSTEM
    replay-approval-request-roles:
      - ORDER_SUPPORT
      - SRE
      - SYSTEM
    replay-approval-review-roles:
      - SRE
      - SYSTEM
    replay-roles:
      - ORDER_SUPPORT
      - SRE
      - SYSTEM
```

这段配置的业务含义是：

```text
ORDER_SUPPORT
 -> 可以做失败事件管理
 -> 可以提交重放申请
 -> 可以执行已批准的重放
 -> 不能审批重放申请

SRE / SYSTEM
 -> 可以覆盖全部失败事件写动作
```

## 三、解析器按动作校验

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventOperatorContextResolver.java`

v29 的入口仍然保留：

```java
public FailedEventOperatorContext resolve(HttpHeaders headers) {
    return resolve(headers.getFirst(OPERATOR_ID_HEADER), headers.getFirst(OPERATOR_ROLE_HEADER));
}
```

v30 新增带动作的重载：

```java
public FailedEventOperatorContext resolve(HttpHeaders headers, FailedEventOperatorAction action) {
    return resolve(headers.getFirst(OPERATOR_ID_HEADER), headers.getFirst(OPERATOR_ROLE_HEADER), action);
}

public FailedEventOperatorContext resolve(String operatorId, String operatorRole, FailedEventOperatorAction action) {
    String normalizedRole = requireAllowedForAction(operatorRole, action);
    return new FailedEventOperatorContext(normalizeOperatorId(operatorId), normalizedRole);
}
```

真正的动作校验在 `requireAllowedForAction`：

```java
private String requireAllowedForAction(String operatorRole, FailedEventOperatorAction action) {
    if (!StringUtils.hasText(operatorRole)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, OPERATOR_ROLE_HEADER + " header is required");
    }
    String normalizedRole = failedEventReplayProperties.normalize(operatorRole);
    if (!failedEventReplayProperties.isAllowedFor(action, normalizedRole)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "operator role is not allowed for action: " + action.name());
    }
    return truncate(normalizedRole, 80);
}
```

注意这里做了两件事：

```text
role=sre
 -> normalize -> SRE
 -> 按 action 查允许角色

role=ORDER_SUPPORT + REVIEW_REPLAY_APPROVAL
 -> 不在 SRE/SYSTEM 中
 -> 403 Forbidden
```

解析器还提供 `allowedRolesByAction`，让页面探针能看到完整策略：

```java
public Map<FailedEventOperatorAction, List<String>> allowedRolesByAction() {
    Map<FailedEventOperatorAction, List<String>> rolesByAction = new EnumMap<>(FailedEventOperatorAction.class);
    for (FailedEventOperatorAction action : FailedEventOperatorAction.values()) {
        rolesByAction.put(action, normalizeRoles(failedEventReplayProperties.rolesFor(action)));
    }
    return rolesByAction;
}
```

## 四、探针响应返回策略快照

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventOperatorContextResponse.java`

响应对象从三段信息扩展为四段信息：

```java
public record FailedEventOperatorContextResponse(
        String operatorId,
        String operatorRole,
        List<String> allowedRoles,
        Map<FailedEventOperatorAction, List<String>> allowedRolesByAction
) {
```

静态工厂方法同时接收通用角色和动作角色：

```java
public static FailedEventOperatorContextResponse from(
        FailedEventOperatorContext context,
        List<String> allowedRoles,
        Map<FailedEventOperatorAction, List<String>> allowedRolesByAction
) {
```

这样 `GET /api/v1/failed-events/operator-context` 不只是告诉页面“你是谁”，还告诉页面：

```text
当前系统配置里，每个失败事件动作分别允许哪些角色
```

## 五、Controller 在入口标记动作

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageController.java`

身份探针仍然校验基础上下文，并返回动作策略：

```java
return FailedEventOperatorContextResponse.from(
        operatorContextResolver.resolve(headers),
        operatorContextResolver.allowedRoles(),
        operatorContextResolver.allowedRolesByAction()
);
```

管理状态入口使用管理动作：

```java
return failedEventMessageService.markManagementStatus(
        request,
        operatorContextResolver.resolve(headers, FailedEventOperatorAction.MANAGE_FAILED_EVENT)
);
```

申请审批入口使用申请动作：

```java
return failedEventMessageService.requestReplayApproval(
        id,
        request,
        operatorContextResolver.resolve(headers, FailedEventOperatorAction.REQUEST_REPLAY_APPROVAL)
);
```

审批入口使用审批动作：

```java
return failedEventMessageService.reviewReplayApproval(
        id,
        request,
        operatorContextResolver.resolve(headers, FailedEventOperatorAction.REVIEW_REPLAY_APPROVAL)
);
```

重放入口使用重放动作：

```java
return failedEventMessageService.replay(
        id,
        request,
        operatorContextResolver.resolve(headers, FailedEventOperatorAction.REPLAY_FAILED_EVENT)
);
```

Controller 这一层只负责把“当前 HTTP 接口是什么动作”说清楚，不直接判断角色。这个边界比较舒服：接口知道动作，解析器知道权限，服务层拿到的已经是可信上下文。

## 六、Service 兼容旧入口但使用动作解析

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageService.java`

服务层保留字符串重载，避免大量测试或内部调用立刻破坏；但每个重载都改为动作级解析。

管理状态：

```java
return markManagementStatus(
        request,
        operatorContextResolver.resolve(operatorId, operatorRole, FailedEventOperatorAction.MANAGE_FAILED_EVENT)
);
```

申请审批：

```java
return requestReplayApproval(
        id,
        request,
        operatorContextResolver.resolve(operatorId, operatorRole, FailedEventOperatorAction.REQUEST_REPLAY_APPROVAL)
);
```

审批审核：

```java
return reviewReplayApproval(
        id,
        request,
        operatorContextResolver.resolve(operatorId, operatorRole, FailedEventOperatorAction.REVIEW_REPLAY_APPROVAL)
);
```

重放执行：

```java
return replay(
        id,
        request,
        operatorContextResolver.resolve(operatorId, operatorRole, FailedEventOperatorAction.REPLAY_FAILED_EVENT)
);
```

这保证了两种调用方式行为一致：

```text
HTTP Controller -> resolver.resolve(headers, action)
Service 字符串重载 -> resolver.resolve(operatorId, operatorRole, action)
```

后续如果接入真实认证上下文，服务层的核心业务方法仍然只需要接收 `FailedEventOperatorContext`。

## 七、页面校验提示动作角色

文件：`src/main/resources/static/failed-events.js`

v29 的页面校验只展示：

```text
operatorId / operatorRole
```

v30 在状态元素的 `title` 上补充动作角色摘要：

```javascript
statusElement.title = actionRoleSummary(result.allowedRolesByAction);
```

摘要函数把后端返回的动作角色 map 转成人能扫一眼的提示：

```javascript
function actionRoleSummary(rolesByAction) {
    if (!rolesByAction) {
        return "";
    }
    return Object.entries(rolesByAction)
            .map(([action, roles]) => `${action}: ${(roles || []).join("/")}`)
            .join(" | ");
}
```

校验失败时清掉旧提示：

```javascript
statusElement.title = "";
```

这不是完整前端权限控制，只是把后端策略暴露给操作员确认。真正的拦截仍然在后端。

## 八、测试覆盖动作差异

文件：`src/test/java/com/codexdemo/orderplatform/FailedEventOperatorContextIntegrationTests.java`

探针测试除了校验规范化，还校验动作角色返回：

```java
.andExpect(jsonPath("$.allowedRolesByAction.MANAGE_FAILED_EVENT")
        .value(containsInAnyOrder("ORDER_SUPPORT", "SRE", "SYSTEM")))
.andExpect(jsonPath("$.allowedRolesByAction.REVIEW_REPLAY_APPROVAL")
        .value(containsInAnyOrder("SRE", "SYSTEM")));
```

审批链路测试先让 `ORDER_SUPPORT` 发起申请：

```java
.header("X-Operator-Id", " ops-user ")
.header("X-Operator-Role", " order_support ")
```

并确认审批申请流水里角色被规范化：

```java
assertThat(history.getOperatorRole()).isEqualTo("ORDER_SUPPORT");
```

然后用同样的 `ORDER_SUPPORT` 去审批，期望被拒绝：

```java
mockMvc.perform(post("/api/v1/failed-events/{id}/replay-approval/review", failedMessage.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Operator-Id", "support-reviewer")
                .header("X-Operator-Role", "ORDER_SUPPORT")
                .content("""
                        {
                          "status": "APPROVED",
                          "note": "support role should not review"
                        }
                        """))
        .andExpect(status().isForbidden())
        .andExpect(status().reason("operator role is not allowed for action: REVIEW_REPLAY_APPROVAL"));
```

最后换成 `SRE` 审批，链路恢复成功：

```java
.header("X-Operator-Id", "sre-lead")
.header("X-Operator-Role", "SRE")
```

文件：`src/test/java/com/codexdemo/orderplatform/FailedEventManagementPageTests.java`

静态页面测试确认脚本里存在动作角色摘要函数：

```java
"actionRoleSummary",
```

## 九、本版后的权限链路

v30 后，失败事件写操作链路变成：

```text
HTTP Header
 -> FailedEventOperatorAction
 -> FailedEventOperatorContextResolver.resolve(..., action)
 -> FailedEventReplayProperties.rolesFor(action)
 -> FailedEventOperatorContext
 -> FailedEventMessageService
 -> 管理状态流水 / 审批流水 / 重放审计
```

相比 v29：

```text
v29: 是否是允许角色
v30: 是否是这个动作允许的角色
```

这一步让项目更像真实后台系统：同一个人也许可以提交申请，但不能审批；同一个角色也许可以管理失败事件，但不能碰更高风险的审批动作。

## 一句话总结

v30 把失败事件权限从“通用角色白名单”推进到“动作级角色策略”，让管理、申请、审批和重放都能独立授权，同时保持 Controller、Service、页面和测试的身份链路一致。
