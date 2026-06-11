> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 第三十一版：失败事件操作员动作权限快照

## 本版目标

v30 已经把失败事件写操作从一份通用角色白名单升级成动作级角色策略：

```text
MANAGE_FAILED_EVENT
REQUEST_REPLAY_APPROVAL
REVIEW_REPLAY_APPROVAL
REPLAY_FAILED_EVENT
```

但 v30 的 `/operator-context` 主要返回的是“系统策略”：

```text
allowedRoles
allowedRolesByAction
```

页面和调试者还需要自己根据当前 `operatorRole` 推断“这个人到底能做哪些动作”。v31 的目标是让身份探针直接返回当前操作员的实际动作能力：

```text
allowedActions
deniedActions
```

这样 `ORDER_SUPPORT` 调用探针时能直接看到：

```text
可执行：管理、申请、重放
不可执行：审批
```

## 改动文件

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventOperatorContextResolver.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventOperatorContextResponse.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageController.java
src/main/resources/static/failed-events.js
src/test/java/com/codexdemo/orderplatform/FailedEventOperatorContextIntegrationTests.java
src/test/java/com/codexdemo/orderplatform/FailedEventManagementPageTests.java
README.md
a/31/解释/说明.md
```

## 一、Resolver 负责计算当前角色的动作能力

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventOperatorContextResolver.java`

v30 已经有按动作校验的方法：

```java
private void requireAllowedForAction(String normalizedRole, FailedEventOperatorAction action) {
    if (!failedEventReplayProperties.isAllowedFor(action, normalizedRole)) {
        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "operator role is not allowed for action: " + action.name()
        );
    }
}
```

v31 没有重复写一份判断，而是复用 `FailedEventReplayProperties.isAllowedFor(action, role)` 来计算列表。

允许动作：

```java
public List<FailedEventOperatorAction> allowedActionsFor(String operatorRole) {
    return actionsFor(operatorRole, true);
}
```

拒绝动作：

```java
public List<FailedEventOperatorAction> deniedActionsFor(String operatorRole) {
    return actionsFor(operatorRole, false);
}
```

核心筛选逻辑：

```java
private List<FailedEventOperatorAction> actionsFor(String operatorRole, boolean allowed) {
    String normalizedRole = failedEventReplayProperties.normalize(operatorRole);
    return List.of(FailedEventOperatorAction.values())
            .stream()
            .filter(action -> failedEventReplayProperties.isAllowedFor(action, normalizedRole) == allowed)
            .toList();
}
```

这段代码的含义是：

```text
拿到当前角色
 -> 规范化成大写
 -> 遍历所有 FailedEventOperatorAction
 -> 根据 isAllowedFor(action, role) 分成 allowed / denied
```

如果角色是 `ORDER_SUPPORT`：

```text
MANAGE_FAILED_EVENT -> true
REQUEST_REPLAY_APPROVAL -> true
REVIEW_REPLAY_APPROVAL -> false
REPLAY_FAILED_EVENT -> true
```

如果角色是 `SRE`：

```text
四个动作全部 true
```

## 二、响应对象增加 allowedActions / deniedActions

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventOperatorContextResponse.java`

响应 record 从 v30 的策略信息：

```java
String operatorId,
String operatorRole,
List<String> allowedRoles,
Map<FailedEventOperatorAction, List<String>> allowedRolesByAction
```

扩展为同时包含当前角色能力：

```java
String operatorId,
String operatorRole,
List<String> allowedRoles,
Map<FailedEventOperatorAction, List<String>> allowedRolesByAction,
List<FailedEventOperatorAction> allowedActions,
List<FailedEventOperatorAction> deniedActions
```

静态工厂方法也同步扩展：

```java
public static FailedEventOperatorContextResponse from(
        FailedEventOperatorContext operatorContext,
        List<String> allowedRoles,
        Map<FailedEventOperatorAction, List<String>> allowedRolesByAction,
        List<FailedEventOperatorAction> allowedActions,
        List<FailedEventOperatorAction> deniedActions
) {
```

最终返回：

```java
return new FailedEventOperatorContextResponse(
        operatorContext.operatorId(),
        operatorContext.operatorRole(),
        allowedRoles,
        allowedRolesByAction,
        allowedActions,
        deniedActions
);
```

这里的设计有两个好处：

```text
allowedRolesByAction
 -> 解释系统策略

allowedActions / deniedActions
 -> 解释当前操作者实际能力
```

一个面向配置，一个面向当前人。

## 三、Controller 在探针里组装能力快照

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageController.java`

v30 的探针入口是直接解析并返回策略：

```java
return FailedEventOperatorContextResponse.from(
        operatorContextResolver.resolve(headers),
        operatorContextResolver.allowedRoles(),
        operatorContextResolver.allowedRolesByAction()
);
```

v31 先把解析后的上下文保存成局部变量：

```java
FailedEventOperatorContext operatorContext = operatorContextResolver.resolve(headers);
```

再用这个规范化后的 `operatorRole` 计算能力：

```java
return FailedEventOperatorContextResponse.from(
        operatorContext,
        operatorContextResolver.allowedRoles(),
        operatorContextResolver.allowedRolesByAction(),
        operatorContextResolver.allowedActionsFor(operatorContext.operatorRole()),
        operatorContextResolver.deniedActionsFor(operatorContext.operatorRole())
);
```

注意这里用的是 `operatorContext.operatorRole()`，不是原始请求头：

```text
请求头：order_support
 -> Resolver 规范化
 -> operatorRole = ORDER_SUPPORT
 -> 再计算动作能力
```

这样可以避免大小写或空格导致页面看到的能力快照和后端真正写操作不一致。

## 四、页面身份校验展示可/禁摘要

文件：`src/main/resources/static/failed-events.js`

v30 页面校验成功后只展示：

```javascript
const summary = `${result.operatorId} / ${result.operatorRole}`;
statusElement.textContent = summary;
statusElement.title = actionRoleSummary(result.allowedRolesByAction);
```

v31 改成展示身份和动作能力数量：

```javascript
const summary = `${result.operatorId} / ${result.operatorRole}`;
const ability = operatorAbilitySummary(result);
statusElement.textContent = `${summary} | ${ability.short}`;
statusElement.title = ability.long;
showToast(`身份已通过: ${summary}，${ability.short}`);
```

`operatorAbilitySummary` 负责把后端返回结果变成短文本和长提示：

```javascript
function operatorAbilitySummary(result) {
    const allowedActions = result.allowedActions || [];
    const deniedActions = result.deniedActions || [];
    const policySummary = actionRoleSummary(result.allowedRolesByAction);
    return {
        short: `可${allowedActions.length} 禁${deniedActions.length}`,
        long: `可执行: ${actionLabelSummary(allowedActions)} | 不可执行: ${actionLabelSummary(deniedActions)} | ${policySummary}`
    };
}
```

`actionLabelSummary` 处理空数组：

```javascript
function actionLabelSummary(actions) {
    if (!actions || actions.length === 0) {
        return "无";
    }
    return actions.map(actionLabel).join("/");
}
```

`actionLabel` 把后端枚举转成中文操作名：

```javascript
function actionLabel(action) {
    switch (action) {
        case "MANAGE_FAILED_EVENT":
            return "管理";
        case "REQUEST_REPLAY_APPROVAL":
            return "申请";
        case "REVIEW_REPLAY_APPROVAL":
            return "审批";
        case "REPLAY_FAILED_EVENT":
            return "重放";
        default:
            return action;
    }
}
```

所以页面校验 `ORDER_SUPPORT` 时会看到：

```text
support-user / ORDER_SUPPORT | 可3 禁1
```

鼠标悬停提示会看到：

```text
可执行: 管理/申请/重放 | 不可执行: 审批 | MANAGE_FAILED_EVENT: ...
```

## 五、测试覆盖 SRE 和 ORDER_SUPPORT 两类角色

文件：`src/test/java/com/codexdemo/orderplatform/FailedEventOperatorContextIntegrationTests.java`

第一个测试继续校验 `sre` 会被规范化成 `SRE`，并确认 `SRE` 拥有全部动作：

```java
.andExpect(jsonPath("$.allowedActions").value(containsInAnyOrder(
        "MANAGE_FAILED_EVENT",
        "REQUEST_REPLAY_APPROVAL",
        "REVIEW_REPLAY_APPROVAL",
        "REPLAY_FAILED_EVENT"
)))
.andExpect(jsonPath("$.deniedActions").isEmpty());
```

第二个测试补了 `ORDER_SUPPORT` 探针：

```java
mockMvc.perform(get("/api/v1/failed-events/operator-context")
                .header("X-Operator-Id", "support-user")
                .header("X-Operator-Role", "order_support"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.allowedActions").value(containsInAnyOrder(
                "MANAGE_FAILED_EVENT",
                "REQUEST_REPLAY_APPROVAL",
                "REPLAY_FAILED_EVENT"
        )))
        .andExpect(jsonPath("$.deniedActions").value(containsInAnyOrder("REVIEW_REPLAY_APPROVAL")));
```

这和 v30 的真实写操作校验互相补位：

```text
探针告诉你 ORDER_SUPPORT 不能审批
真正审批接口也会返回 403
```

文件：`src/test/java/com/codexdemo/orderplatform/FailedEventManagementPageTests.java`

静态页面测试确认脚本引用了新响应字段和新函数：

```java
"allowedActions",
"deniedActions",
"operatorAbilitySummary",
"actionLabelSummary",
```

## 六、本版后的身份探针响应

`SRE` 调用：

```json
{
  "operatorId": "sre-user",
  "operatorRole": "SRE",
  "allowedActions": [
    "MANAGE_FAILED_EVENT",
    "REQUEST_REPLAY_APPROVAL",
    "REVIEW_REPLAY_APPROVAL",
    "REPLAY_FAILED_EVENT"
  ],
  "deniedActions": []
}
```

`ORDER_SUPPORT` 调用：

```json
{
  "operatorId": "support-user",
  "operatorRole": "ORDER_SUPPORT",
  "allowedActions": [
    "MANAGE_FAILED_EVENT",
    "REQUEST_REPLAY_APPROVAL",
    "REPLAY_FAILED_EVENT"
  ],
  "deniedActions": [
    "REVIEW_REPLAY_APPROVAL"
  ]
}
```

## 一句话总结

v31 把身份探针从“告诉你系统配置了哪些动作角色”升级为“直接告诉你当前操作员能做什么、不能做什么”，让后续页面按钮禁用、真实登录态权限展示和操作前自检都有了更明确的数据来源。
