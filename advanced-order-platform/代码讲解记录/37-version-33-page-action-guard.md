> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 第三十三版：失败事件管理页面写操作本地权限守卫

## 本版目标

v32 已经把 `allowedActions` / `deniedActions` 接到按钮禁用上：

```text
校验身份
 -> 当前角色没有权限的按钮 disabled
 -> 页面减少误点击和无意义 403
```

v33 继续补一个小但关键的前端防线：

```text
写操作处理函数入口
 -> 再读一次本地权限快照
 -> 当前已校验且动作未授权时直接提示并返回
 -> 不再发起未授权写请求
```

注意它不是替代后端鉴权。后端仍然是最终防线；页面守卫负责挡住脚本改按钮、旧 DOM 状态、误触发函数这类前端绕过。

## 改动文件

```text
src/main/resources/static/failed-events.js
src/test/java/com/codexdemo/orderplatform/FailedEventManagementPageTests.java
README.md
a/33/解释/说明.md
```

## 一、本地记录校验后的动作权限

文件：`src/main/resources/static/failed-events.js`

v33 在页面状态里增加 `operatorPermissions`：

```javascript
const state = {
    page: 0,
    totalPages: 0,
    selectedIds: new Set(),
    activeEventId: null,
    activeEvent: null,
    itemsById: new Map(),
    pendingReplay: null,
    operatorPermissions: {
        management: { checked: false, allowedActions: [] },
        replay: { checked: false, allowedActions: [] }
    }
};
```

这里按页面区域分成两份：

```text
management
 -> 批量标记管理状态

replay
 -> 申请审批、审批通过/拒绝、提交重放
```

`checked` 表示当前区域是否已经完成过身份校验。它很重要，因为 v33 保留了旧页面的兼容行为：

```text
未校验
 -> 不在前端强拦截，仍交给后端处理

已校验且动作未授权
 -> 页面立即拦截
```

## 二、校验身份时写入权限快照

文件：`src/main/resources/static/failed-events.js`

身份校验成功后，页面把后端返回的 `allowedActions` 同时用于两件事：

```javascript
const result = await fetchJson(`${apiBase}/operator-context`, { headers });
const summary = `${result.operatorId} / ${result.operatorRole}`;
const ability = operatorAbilitySummary(result);
statusElement.textContent = `${summary} | ${ability.short}`;
statusElement.title = ability.long;
rememberOperatorPermissions(scope, result.allowedActions || []);
applyOperatorPermissions(scope, result.allowedActions || []);
showToast(`身份已通过: ${summary}，${ability.short}`);
```

第一件事是 v33 新增的本地快照：

```javascript
function rememberOperatorPermissions(scope, allowedActions) {
    state.operatorPermissions[scope] = {
        checked: true,
        allowedActions: allowedActions || []
    };
}
```

第二件事是 v32 已有的按钮禁用：

```javascript
applyOperatorPermissions(scope, result.allowedActions || []);
```

校验失败时同样写入一个“已校验但无动作权限”的快照：

```javascript
statusElement.textContent = "校验失败";
statusElement.removeAttribute("title");
rememberOperatorPermissions(scope, []);
applyOperatorPermissions(scope, []);
showToast(error.message, true);
```

这样失败后的点击也会被入口守卫拦下，而不是只依赖按钮禁用状态。

## 三、统一入口守卫函数

文件：`src/main/resources/static/failed-events.js`

核心函数是 `ensureOperatorActionAllowed`：

```javascript
function ensureOperatorActionAllowed(scope, action) {
    const permission = state.operatorPermissions[scope];
    if (!permission || !permission.checked) {
        return true;
    }
    if ((permission.allowedActions || []).includes(action)) {
        return true;
    }
    showToast(`${actionLabel(action)} 未授权，请切换角色后重新校验身份`, true);
    return false;
}
```

这段逻辑拆开看：

```text
没有快照，或者还没有校验
 -> 放行，保持旧流程

已经校验，且 allowedActions 包含当前动作
 -> 放行

已经校验，但不包含当前动作
 -> toast 提示未授权
 -> 返回 false
```

这里复用 `actionLabel(action)`，所以提示能显示中文动作名：

```text
审批 未授权，请切换角色后重新校验身份
```

它比直接显示枚举更适合后台操作者快速理解。

## 四、把守卫放进所有写操作入口

文件：`src/main/resources/static/failed-events.js`

批量管理状态入口：

```javascript
async function markSelectedEvents() {
    if (!ensureOperatorActionAllowed("management", "MANAGE_FAILED_EVENT")) {
        return;
    }
    if (state.selectedIds.size === 0) {
        showToast("请选择失败事件", true);
        return;
    }
    ...
}
```

申请重放审批入口：

```javascript
async function requestReplayApproval() {
    if (!ensureOperatorActionAllowed("replay", "REQUEST_REPLAY_APPROVAL")) {
        return;
    }
    const id = replayTargetId();
    ...
}
```

审批通过/拒绝入口：

```javascript
async function reviewReplayApproval(status) {
    if (!ensureOperatorActionAllowed("replay", "REVIEW_REPLAY_APPROVAL")) {
        return;
    }
    const id = replayTargetId();
    ...
}
```

提交重放入口：

```javascript
async function replayActiveEvent() {
    if (!ensureOperatorActionAllowed("replay", "REPLAY_FAILED_EVENT")) {
        return;
    }
    const id = replayTargetId();
    ...
}
```

这些 guard 都放在函数最前面。原因很直接：

```text
权限不满足
 -> 先返回
 -> 不读表单
 -> 不检查业务前置条件
 -> 不发 HTTP 写请求
```

这样才能保证脚本直接调用函数时也走同一条本地权限判断。

## 五、切换身份时清空快照

文件：`src/main/resources/static/failed-events.js`

v32 已经在切换操作人或角色时恢复按钮。v33 在同一个函数里把本地权限快照也重置：

```javascript
function resetOperatorPermissions(scope) {
    const statusElement = scope === "replay"
            ? elements.replayOperatorContextStatus
            : elements.operatorContextStatus;
    state.operatorPermissions[scope] = { checked: false, allowedActions: [] };
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

对应事件仍然是四个身份输入：

```javascript
elements.operatorIdInput.addEventListener("input", () => resetOperatorPermissions("management"));
elements.operatorRoleInput.addEventListener("change", () => resetOperatorPermissions("management"));
elements.replayOperatorIdInput.addEventListener("input", () => resetOperatorPermissions("replay"));
elements.replayOperatorRoleInput.addEventListener("change", () => resetOperatorPermissions("replay"));
```

这个设计避免旧快照污染新身份：

```text
SRE 校验通过
 -> 切到 ORDER_SUPPORT
 -> 旧的 SRE allowedActions 立即失效
 -> 必须重新校验
```

## 六、测试和浏览器验证

文件：`src/test/java/com/codexdemo/orderplatform/FailedEventManagementPageTests.java`

页面静态测试补了三个关键字符串：

```java
"operatorPermissions",
"rememberOperatorPermissions",
"ensureOperatorActionAllowed",
```

它确认打包进页面的 JavaScript 至少包含：

```text
本地权限状态
校验结果记忆函数
写操作入口守卫函数
```

本版还做了真实浏览器 smoke。先把重放工作台角色切成 `ORDER_SUPPORT`，再校验身份：

```json
{
  "status": "local-admin / ORDER_SUPPORT | 可3 禁1",
  "approveDisabled": true,
  "rejectDisabled": true,
  "requestDisabled": false,
  "replayDisabled": false
}
```

然后用脚本强行打开审批按钮，并直接调用处理函数：

```javascript
document.querySelector("#approveReplayButton").disabled = false;
await reviewReplayApproval("APPROVED");
```

页面返回的 toast 是：

```text
审批 未授权，请切换角色后重新校验身份
```

这说明 v33 不是只靠 `disabled` 视觉状态，而是把权限判断放进了写操作函数入口。

## 一句话总结

v33 把 v32 的按钮级预检升级为“按钮状态 + 函数入口守卫”双层前端保护：操作员一旦校验过身份，页面就会在发起写请求前再次确认本地动作权限，防止未授权动作被脚本绕过禁用按钮触发。
