> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 第三十四版：失败事件操作员动作权限决策明细

## 本版目标

v31 到 v33 已经完成了三层能力：

```text
v31: operator-context 返回 allowedActions / deniedActions
v32: 页面按 allowedActions 禁用未授权按钮
v33: 写操作函数入口再次检查本地权限快照
```

v34 补的是“可解释性”：

```text
每个动作
 -> 是否允许
 -> 允许角色有哪些
 -> 页面直接显示允许/禁止决策
```

这样操作员不用只看 `可3 禁1` 或鼠标悬停 title，就能直接看到“审批为什么被禁用”。

## 改动文件

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventOperatorActionDecision.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventOperatorContextResolver.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventOperatorContextResponse.java
src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageController.java
src/main/resources/static/failed-events.html
src/main/resources/static/failed-events.css
src/main/resources/static/failed-events.js
src/test/java/com/codexdemo/orderplatform/FailedEventOperatorContextIntegrationTests.java
src/test/java/com/codexdemo/orderplatform/FailedEventManagementPageTests.java
```

## 一、动作决策响应对象

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventOperatorActionDecision.java`

v34 新增一个小 record：

```java
public record FailedEventOperatorActionDecision(
        FailedEventOperatorAction action,
        boolean allowed,
        List<String> allowedRoles
) {
}
```

它表达的是单个动作的判断结果：

```text
action
 -> 哪个动作，例如 REVIEW_REPLAY_APPROVAL

allowed
 -> 当前操作员角色是否允许执行

allowedRoles
 -> 这个动作配置允许的角色集合
```

和 `allowedActions` / `deniedActions` 相比，它多了一层“为什么”：

```text
deniedActions 只能告诉你审批被拒
actionDecisions 可以告诉你审批只允许 SRE/SYSTEM
```

## 二、Resolver 生成完整决策表

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventOperatorContextResolver.java`

v34 在 resolver 里增加 `actionDecisionsFor`：

```java
public Map<FailedEventOperatorAction, FailedEventOperatorActionDecision> actionDecisionsFor(String operatorRole) {
    String normalizedRole = failedEventReplayProperties.normalize(operatorRole);
    Map<FailedEventOperatorAction, FailedEventOperatorActionDecision> decisions =
            new EnumMap<>(FailedEventOperatorAction.class);
    for (FailedEventOperatorAction action : FailedEventOperatorAction.values()) {
        decisions.put(action, new FailedEventOperatorActionDecision(
                action,
                failedEventReplayProperties.isAllowedFor(action, normalizedRole),
                normalizeRoles(failedEventReplayProperties.rolesFor(action))
        ));
    }
    return decisions;
}
```

这里继续使用 `EnumMap`，原因很简单：

```text
key 是 enum
动作集合固定
遍历顺序稳定
内存结构也更贴合 enum key
```

判断是否允许仍复用已有策略：

```java
failedEventReplayProperties.isAllowedFor(action, normalizedRole)
```

允许角色也复用已有配置入口：

```java
normalizeRoles(failedEventReplayProperties.rolesFor(action))
```

所以 v34 没有引入第二套权限规则，只是把现有规则解释得更清楚。

## 三、operator-context 响应增加 actionDecisions

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventOperatorContextResponse.java`

响应 record 增加字段：

```java
Map<FailedEventOperatorAction, FailedEventOperatorActionDecision> actionDecisions,
```

完整响应结构变成：

```java
public record FailedEventOperatorContextResponse(
        String operatorId,
        String operatorRole,
        List<String> allowedRoles,
        Map<FailedEventOperatorAction, List<String>> allowedRolesByAction,
        Map<FailedEventOperatorAction, FailedEventOperatorActionDecision> actionDecisions,
        List<FailedEventOperatorAction> allowedActions,
        List<FailedEventOperatorAction> deniedActions
) {
}
```

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageController.java`

Controller 在探针接口中填入新字段：

```java
return FailedEventOperatorContextResponse.from(
        operatorContext,
        operatorContextResolver.allowedRoles(),
        operatorContextResolver.allowedRolesByAction(),
        operatorContextResolver.actionDecisionsFor(operatorContext.operatorRole()),
        operatorContextResolver.allowedActionsFor(operatorContext.operatorRole()),
        operatorContextResolver.deniedActionsFor(operatorContext.operatorRole())
);
```

对老客户端来说，`allowedActions` / `deniedActions` 仍然保留；新客户端可以优先读 `actionDecisions`。

## 四、页面增加动作决策展示区域

文件：`src/main/resources/static/failed-events.html`

批量标记区增加：

```html
<div id="operatorActionDecisionList" class="action-decision-list" aria-live="polite">
    <span class="action-decision-empty">未校验</span>
</div>
```

重放工作台增加：

```html
<div id="replayActionDecisionList" class="action-decision-list replay-action-decisions" aria-live="polite">
    <span class="action-decision-empty">未校验</span>
</div>
```

两块区域分开，是因为它们关心的动作不同：

```text
批量标记
 -> 管理

重放工作台
 -> 申请 / 审批 / 重放
```

## 五、页面渲染决策标签

文件：`src/main/resources/static/failed-events.js`

元素缓存加入两个新节点：

```javascript
"operatorActionDecisionList",
"replayActionDecisionList",
```

身份校验时，页面会同步更新状态文本、按钮权限和决策标签：

```javascript
rememberOperatorPermissions(scope, result.allowedActions || []);
applyOperatorPermissions(scope, result.allowedActions || []);
renderOperatorActionDecisions(scope, result);
```

渲染函数优先读后端的新字段：

```javascript
const decisions = result.actionDecisions || {};
```

如果后端没有这个字段，则退回到旧字段：

```javascript
const allowedActionSet = new Set(result.allowedActions || []);
const rolesByAction = result.allowedRolesByAction || {};
const decision = decisions[action] || {};
const allowed = typeof decision.allowed === "boolean" ? decision.allowed : allowedActionSet.has(action);
const allowedRoles = decision.allowedRoles || rolesByAction[action] || [];
```

这让页面保持兼容：

```text
v34 后端
 -> 使用 actionDecisions

旧响应或缓存响应
 -> 使用 allowedActions + allowedRolesByAction 推导
```

最终生成的标签包含动作名、允许/禁止和允许角色：

```javascript
return `
    <span class="action-decision ${stateClass}" title="${escapeHtml(title)}">
        <strong>${escapeHtml(actionLabel(action))}</strong>
        <span>${statusText}</span>
        <small>${escapeHtml(roleText)}</small>
    </span>
`;
```

`ORDER_SUPPORT` 在重放工作台会显示：

```text
申请 允许 ORDER_SUPPORT/SRE/SYSTEM
审批 禁止 SRE/SYSTEM
重放 允许 ORDER_SUPPORT/SRE/SYSTEM
```

## 六、样式保持后台页面克制

文件：`src/main/resources/static/failed-events.css`

决策列表允许换行：

```css
.action-decision-list {
    grid-column: 1 / -1;
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
    min-height: 32px;
}
```

允许动作使用浅绿色：

```css
.action-decision-allowed {
    border-color: #a9d8b4;
    background: #edf8f0;
}
```

禁止动作使用浅红色和虚线：

```css
.action-decision-denied {
    border-color: #e5b4b4;
    border-style: dashed;
    background: #fff1f1;
}
```

这里没有做很重的视觉提示，因为页面已经有按钮禁用和写操作守卫。决策标签主要负责让权限边界更容易扫描。

## 七、测试和浏览器验证

文件：`src/test/java/com/codexdemo/orderplatform/FailedEventOperatorContextIntegrationTests.java`

`SRE` 探针会看到审批允许：

```java
.andExpect(jsonPath("$.actionDecisions.REVIEW_REPLAY_APPROVAL.allowed").value(true))
.andExpect(jsonPath("$.actionDecisions.REVIEW_REPLAY_APPROVAL.allowedRoles")
        .value(containsInAnyOrder("SRE", "SYSTEM")))
```

`ORDER_SUPPORT` 探针会看到审批禁止：

```java
.andExpect(jsonPath("$.actionDecisions.REVIEW_REPLAY_APPROVAL.action")
        .value("REVIEW_REPLAY_APPROVAL"))
.andExpect(jsonPath("$.actionDecisions.REVIEW_REPLAY_APPROVAL.allowed").value(false))
.andExpect(jsonPath("$.actionDecisions.REVIEW_REPLAY_APPROVAL.allowedRoles")
        .value(containsInAnyOrder("SRE", "SYSTEM")))
```

文件：`src/test/java/com/codexdemo/orderplatform/FailedEventManagementPageTests.java`

页面静态测试确认新节点、函数和样式存在：

```java
"operatorActionDecisionList",
"replayActionDecisionList",
"actionDecisions",
"renderOperatorActionDecisions",
"actionDecisionListElement",
```

CSS 检查：

```java
".action-decision-list",
".action-decision-allowed",
".action-decision-denied",
```

Playwright smoke 验证结果：

```json
{
  "status": "local-admin / ORDER_SUPPORT | 可3 禁1",
  "deniedCount": 1,
  "approveDisabled": true
}
```

页面决策标签包含：

```text
申请 允许 ORDER_SUPPORT/SRE/SYSTEM
审批 禁止 SRE/SYSTEM
重放 允许 ORDER_SUPPORT/SRE/SYSTEM
```

## 一句话总结

v34 把操作员权限探针从“动作列表”升级为“动作决策明细”，并把这些明细直接展示在失败事件管理页面上，让按钮为什么可用或禁用变得可扫描、可解释、可继续扩展。
