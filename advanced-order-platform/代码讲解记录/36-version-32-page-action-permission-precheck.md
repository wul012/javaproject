# 第三十二版：失败事件管理页面动作权限预检

## 本版目标

v31 已经让 `/operator-context` 返回当前操作员的动作能力快照：

```text
allowedActions
deniedActions
```

v32 不继续扩大后端模型，而是把这个能力快照真正用到页面交互里：

```text
校验身份成功
 -> 读取 allowedActions
 -> 当前角色没有权限的写按钮直接禁用
 -> 切换操作人或角色后恢复“未校验”，要求重新校验
```

这版的边界很明确：只做页面动作权限预检，不改变后端授权规则。真正的权限拦截仍然保留在后端。

## 改动文件

```text
src/main/resources/static/failed-events.js
src/main/resources/static/failed-events.css
src/test/java/com/codexdemo/orderplatform/FailedEventManagementPageTests.java
README.md
a/32/解释/说明.md
```

## 一、把写按钮纳入 elements 缓存

文件：`src/main/resources/static/failed-events.js`

过去这些按钮只在绑定事件时临时 `getElementById`：

```javascript
document.getElementById("markButton").addEventListener("click", markSelectedEvents);
document.getElementById("requestReplayApprovalButton").addEventListener("click", requestReplayApproval);
document.getElementById("approveReplayButton").addEventListener("click", () => reviewReplayApproval("APPROVED"));
document.getElementById("rejectReplayButton").addEventListener("click", () => reviewReplayApproval("REJECTED"));
document.getElementById("replayButton").addEventListener("click", replayActiveEvent);
```

v32 把它们加入 `elements`：

```javascript
"markButton",
"requestReplayApprovalButton",
"approveReplayButton",
"rejectReplayButton",
"replayButton",
```

这样后续权限预检函数可以统一操作按钮状态：

```text
禁用
恢复
增加未授权样式
设置 title 提示
```

事件绑定也改成使用缓存对象：

```javascript
elements.markButton.addEventListener("click", markSelectedEvents);
elements.requestReplayApprovalButton.addEventListener("click", requestReplayApproval);
elements.approveReplayButton.addEventListener("click", () => reviewReplayApproval("APPROVED"));
elements.rejectReplayButton.addEventListener("click", () => reviewReplayApproval("REJECTED"));
elements.replayButton.addEventListener("click", replayActiveEvent);
```

## 二、校验成功后应用动作权限

文件：`src/main/resources/static/failed-events.js`

v31 的校验成功逻辑只展示身份和能力摘要：

```javascript
const ability = operatorAbilitySummary(result);
statusElement.textContent = `${summary} | ${ability.short}`;
statusElement.title = ability.long;
showToast(`身份已通过: ${summary}，${ability.short}`);
```

v32 在这里接入按钮预检：

```javascript
applyOperatorPermissions(scope, result.allowedActions || []);
```

完整流程变成：

```javascript
const result = await fetchJson(`${apiBase}/operator-context`, { headers });
const summary = `${result.operatorId} / ${result.operatorRole}`;
const ability = operatorAbilitySummary(result);
statusElement.textContent = `${summary} | ${ability.short}`;
statusElement.title = ability.long;
applyOperatorPermissions(scope, result.allowedActions || []);
showToast(`身份已通过: ${summary}，${ability.short}`);
```

如果校验失败，则所有受控写按钮都进入禁用状态：

```javascript
statusElement.textContent = "校验失败";
statusElement.removeAttribute("title");
applyOperatorPermissions(scope, []);
showToast(error.message, true);
```

这可以避免身份已经明确失败后，页面还鼓励用户继续点击写操作。

## 三、动作到按钮的映射

文件：`src/main/resources/static/failed-events.js`

核心映射函数是 `actionControls`。

管理区域只有一个写动作：

```javascript
if (scope === "management") {
    return [
        {
            action: "MANAGE_FAILED_EVENT",
            buttons: [elements.markButton]
        }
    ];
}
```

重放工作台有三类写动作：

```javascript
return [
    {
        action: "REQUEST_REPLAY_APPROVAL",
        buttons: [elements.requestReplayApprovalButton]
    },
    {
        action: "REVIEW_REPLAY_APPROVAL",
        buttons: [elements.approveReplayButton, elements.rejectReplayButton]
    },
    {
        action: "REPLAY_FAILED_EVENT",
        buttons: [elements.replayButton]
    }
];
```

这层映射让页面结构和后端动作枚举对齐：

```text
MANAGE_FAILED_EVENT
 -> 提交标记

REQUEST_REPLAY_APPROVAL
 -> Request approval

REVIEW_REPLAY_APPROVAL
 -> Approve / Reject

REPLAY_FAILED_EVENT
 -> 提交重放
```

后续如果增加“导出敏感字段”“强制忽略失败事件”等动作，也可以继续在这里补映射。

## 四、统一禁用和提示

文件：`src/main/resources/static/failed-events.js`

`applyOperatorPermissions` 把后端返回的 `allowedActions` 转成 `Set`：

```javascript
const allowedActionSet = new Set(allowedActions);
```

然后逐个按钮应用状态：

```javascript
actionControls(scope).forEach(({ action, buttons }) => {
    const enabled = allowedActionSet.has(action);
    buttons.forEach((button) => {
        button.disabled = !enabled;
        button.classList.toggle("permission-denied", !enabled);
        if (enabled) {
            button.removeAttribute("title");
        } else {
            button.title = `${actionLabel(action)} 未授权，请切换角色后重新校验身份`;
        }
    });
});
```

这段代码表达的是：

```text
当前动作在 allowedActions 里
 -> 按钮可点
 -> 去掉未授权样式和 title

当前动作不在 allowedActions 里
 -> 按钮 disabled
 -> 添加 permission-denied 样式
 -> title 说明哪个动作未授权
```

例如 `ORDER_SUPPORT` 校验重放工作台后：

```text
Request approval -> enabled
Approve -> disabled
Reject -> disabled
提交重放 -> enabled
```

## 五、切换身份后恢复未校验状态

文件：`src/main/resources/static/failed-events.js`

如果用户切换操作人或角色，旧的权限预检结果就不能再信任。所以 v32 给四个身份输入绑定重置：

```javascript
elements.operatorIdInput.addEventListener("input", () => resetOperatorPermissions("management"));
elements.operatorRoleInput.addEventListener("change", () => resetOperatorPermissions("management"));
elements.replayOperatorIdInput.addEventListener("input", () => resetOperatorPermissions("replay"));
elements.replayOperatorRoleInput.addEventListener("change", () => resetOperatorPermissions("replay"));
```

重置函数会把状态改回“未校验”，并恢复按钮：

```javascript
function resetOperatorPermissions(scope) {
    const statusElement = scope === "replay"
            ? elements.replayOperatorContextStatus
            : elements.operatorContextStatus;
    statusElement.textContent = "未校验";
    statusElement.removeAttribute("title");
    actionControls(scope).forEach(({ buttons }) => {
        buttons.forEach((button) => {
            button.disabled = false;
            button.classList.remove("permission-denied");
            button.removeAttribute("title");
        });
    });
}
```

这里的取舍是：

```text
未校验
 -> 不强制禁用按钮，保持旧页面使用习惯

校验成功
 -> 按当前角色权限预检

校验失败
 -> 禁用当前区域写按钮
```

这样既不会破坏未校验时的老流程，又能在用户主动校验后给出更强的前端保护。

## 六、未授权样式

文件：`src/main/resources/static/failed-events.css`

过去只有 primary 按钮禁用态：

```css
.primary-button:disabled {
    cursor: not-allowed;
    opacity: 0.55;
}
```

v32 扩展到三类按钮：

```css
.primary-button:disabled,
.secondary-button:disabled,
.ghost-button:disabled {
    cursor: not-allowed;
    opacity: 0.55;
}
```

未授权按钮再加一点可视区别：

```css
.permission-denied {
    border-style: dashed;
    filter: grayscale(0.4);
}
```

这里保持克制，不做夸张视觉效果。后台页面的重点是减少误操作，而不是制造很重的提示噪音。

## 七、测试和浏览器验证

文件：`src/test/java/com/codexdemo/orderplatform/FailedEventManagementPageTests.java`

静态测试确认新函数和样式已经打进页面资源：

```java
"applyOperatorPermissions",
"resetOperatorPermissions",
"actionControls",
"permission-denied",
```

CSS 也检查：

```java
".permission-denied",
```

本版还用了 Playwright 做真实页面 smoke：

```text
打开 /failed-events.html
重放工作台角色切到 ORDER_SUPPORT
点击“校验身份”
读取按钮状态
```

结果：

```json
{
  "status": "local-admin / ORDER_SUPPORT | 可3 禁1",
  "requestDisabled": false,
  "approveDisabled": true,
  "rejectDisabled": true,
  "replayDisabled": false
}
```

再切回 `SRE` 后：

```json
{
  "status": "local-admin / SRE | 可4 禁0",
  "requestDisabled": false,
  "approveDisabled": false,
  "rejectDisabled": false,
  "replayDisabled": false
}
```

## 一句话总结

v32 把 v31 返回的操作员动作权限快照真正接到了页面按钮上，让操作员校验身份后能在点击前看到权限边界，减少无意义的 403 和高风险误操作。
