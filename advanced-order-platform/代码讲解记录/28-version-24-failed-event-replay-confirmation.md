> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 第二十四版：失败事件重放二次确认

## 本版目标

v23 已经把后端失败事件重放接口接到了管理页面上。v24 继续补一个真实运维台非常需要的安全动作：重放前二次确认。

这版的核心变化是：

```text
点击“提交重放”
 -> 不直接请求后端
 -> 先生成重放请求预览
 -> 弹出安全确认窗口
 -> 显示目标事件、操作者、原因和覆盖字段风险
 -> 用户勾选确认
 -> 再真正 POST /api/v1/failed-events/{id}/replay
```

## 改动文件

```text
src/main/resources/static/failed-events.html
src/main/resources/static/failed-events.css
src/main/resources/static/failed-events.js
src/test/java/com/codexdemo/orderplatform/FailedEventManagementPageTests.java
README.md
代码讲解记录/README.md
a/24/解释/说明.md
```

## HTML：新增重放确认弹窗

文件：`src/main/resources/static/failed-events.html`

v24 在页面底部新增确认弹窗：

```html
<div id="replayConfirmOverlay" class="modal-overlay" hidden>
    <section class="modal" role="dialog" aria-modal="true" aria-labelledby="replayConfirmTitle">
        <div class="modal-heading">
            <div>
                <h2 id="replayConfirmTitle">重放安全确认</h2>
                <p>提交前核对目标事件、操作者和覆盖字段。</p>
            </div>
            <button id="replayConfirmCancelButton" class="icon-button" type="button" title="关闭">关闭</button>
        </div>
        <div id="replayConfirmSummary" class="confirm-summary"></div>
        <div id="replayRiskList" class="risk-list"></div>
        ...
    </section>
</div>
```

这里有几个关键 ID：

```text
replayConfirmOverlay
 -> 控制弹窗显示/隐藏

replayConfirmSummary
 -> 渲染重放请求摘要

replayRiskList
 -> 渲染覆盖字段和 Payload 风险

replayConfirmCheckbox
 -> 用户显式确认

replayConfirmSubmitButton
 -> 真正提交重放
```

确认按钮默认禁用：

```html
<button id="replayConfirmSubmitButton" class="primary-button" type="button" disabled>确认重放</button>
```

这意味着第一次点击“提交重放”不会真正投递消息，必须先勾选确认项。

## JS：把重放拆成四步

文件：`src/main/resources/static/failed-events.js`

v23 的 `replayActiveEvent` 是直接发请求。v24 改成先构造请求，再打开确认窗口：

```javascript
async function replayActiveEvent() {
    const id = replayTargetId();
    if (!id) {
        showToast("请选择要重放的失败事件", true);
        return;
    }
    const replayRequest = buildReplayRequest(id);
    if (!replayRequest) {
        return;
    }
    openReplayConfirm(replayRequest);
}
```

这一版的重放链路变成：

```text
replayActiveEvent()
 -> buildReplayRequest()
 -> openReplayConfirm()
 -> confirmReplaySubmission()
 -> submitReplayRequest()
```

### 第一步：构造请求

文件：`src/main/resources/static/failed-events.js`

`buildReplayRequest` 只负责收集页面字段：

```javascript
function buildReplayRequest(id) {
    const reason = elements.replayReasonInput.value.trim();
    if (!reason) {
        showToast("请填写重放原因", true);
        return null;
    }
    const body = { reason };
    addBodyField(body, "eventId", elements.replayEventIdInput.value);
    addBodyField(body, "eventType", elements.replayEventTypeInput.value);
    addBodyField(body, "aggregateType", elements.replayAggregateTypeInput.value);
    addBodyField(body, "aggregateId", elements.replayAggregateIdInput.value);
    addBodyField(body, "payload", elements.replayPayloadInput.value);
    return {
        id,
        body,
        operatorId: elements.replayOperatorIdInput.value.trim(),
        operatorRole: elements.replayOperatorRoleInput.value.trim(),
        event: state.activeEvent
    };
}
```

注意这里还没有请求后端。

它只是把重放动作整理成一个结构化对象：

```text
id
 -> 失败事件 ID

body
 -> ReplayFailedEventRequest 请求体

operatorId/operatorRole
 -> 请求头需要的操作者信息

event
 -> 当前页面里的失败事件快照，用来渲染确认摘要
```

### 第二步：打开确认窗口

文件：`src/main/resources/static/failed-events.js`

```javascript
function openReplayConfirm(replayRequest) {
    state.pendingReplay = replayRequest;
    elements.replayConfirmCheckbox.checked = false;
    elements.replayConfirmSubmitButton.disabled = true;
    elements.replayConfirmSummary.innerHTML = replayConfirmSummary(replayRequest);
    elements.replayRiskList.innerHTML = replayRiskList(replayRequest);
    elements.replayConfirmOverlay.hidden = false;
    elements.replayConfirmCheckbox.focus();
}
```

这里有两个安全点：

```text
1. 每次打开弹窗都会重置 checkbox。
2. 确认按钮默认 disabled。
```

也就是说，不能靠上一次勾选残留直接提交。

### 第三步：确认后才提交

文件：`src/main/resources/static/failed-events.js`

确认按钮的启用逻辑：

```javascript
elements.replayConfirmCheckbox.addEventListener("change", () => {
    elements.replayConfirmSubmitButton.disabled = !elements.replayConfirmCheckbox.checked;
});
```

最终提交前还会再检查一次：

```javascript
async function confirmReplaySubmission() {
    if (!state.pendingReplay) {
        closeReplayConfirm();
        return;
    }
    if (!elements.replayConfirmCheckbox.checked) {
        showToast("请先勾选确认项", true);
        return;
    }
    const replayRequest = state.pendingReplay;
    closeReplayConfirm();
    await submitReplayRequest(replayRequest);
}
```

这一层是为了防止按钮状态异常或脚本误触发。

### 第四步：真正调用后端

文件：`src/main/resources/static/failed-events.js`

真正的请求被挪到了 `submitReplayRequest`：

```javascript
async function submitReplayRequest(replayRequest) {
    const { id, body, operatorId, operatorRole } = replayRequest;
    const response = await fetch(`${apiBase}/${id}/replay`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-Operator-Id": operatorId,
            "X-Operator-Role": operatorRole
        },
        body: JSON.stringify(body)
    });
    ...
}
```

它对应后端 Controller：

```java
@PostMapping("/{id}/replay")
public FailedEventMessageResponse replayFailedMessage(
        @PathVariable Long id,
        @RequestHeader(value = "X-Operator-Id", required = false) String operatorId,
        @RequestHeader(value = "X-Operator-Role", required = false) String operatorRole,
        @RequestBody(required = false) ReplayFailedEventRequest request
) {
    return failedEventMessageService.replay(id, request, operatorId, operatorRole);
}
```

也就是说，v24 没有改后端重放语义，只是在浏览器端增加了提交前保护。

## 风险摘要：让覆盖字段可见

文件：`src/main/resources/static/failed-events.js`

确认摘要由 `replayConfirmSummary` 生成：

```javascript
function replayConfirmSummary(replayRequest) {
    const event = replayRequest.event || {};
    return `
        <div class="summary-grid">
            <div class="summary-item">
                <span>失败事件</span>
                <strong>#${escapeHtml(replayRequest.id)} ${escapeHtml(event.messageId || "")}</strong>
            </div>
            ...
        </div>
    `;
}
```

它会展示：

```text
失败事件 ID / messageId
当前状态 / replayCount
eventType
aggregateType / aggregateId
operatorId / operatorRole
reason
```

风险项由 `replayRisks` 生成：

```javascript
function replayRisks(replayRequest) {
    const risks = [];
    const event = replayRequest.event || {};
    const overrideFields = ["eventId", "eventType", "aggregateType", "aggregateId", "payload"]
            .filter((field) => Object.prototype.hasOwnProperty.call(replayRequest.body, field));
    ...
    return risks;
}
```

如果没有覆盖字段：

```javascript
risks.push({
    level: "",
    title: "未覆盖消息字段",
    description: "本次将使用失败事件原始字段重放，后端会在缺失 eventId 时生成新的 UUID。"
});
```

如果覆盖了字段：

```javascript
risks.push({
    level: "risk-medium",
    title: `覆盖字段：${overrideFields.join(", ")}`,
    description: "覆盖字段会写入重放审计，请确认它们来自已核对的修复方案。"
});
```

如果覆盖了 Payload：

```javascript
risks.push({
    level: "risk-high",
    title: "Payload 已被覆盖",
    description: "Payload 变更会改变下游消费者收到的消息内容，请确认 JSON 和业务语义都正确。"
});
```

这就是 v24 最重要的风险提醒。

## CSS：弹窗和风险状态

文件：`src/main/resources/static/failed-events.css`

弹窗覆盖层：

```css
.modal-overlay {
    position: fixed;
    inset: 0;
    z-index: 20;
    display: grid;
    place-items: center;
    padding: 18px;
    background: rgba(16, 35, 51, 0.55);
}
```

隐藏状态：

```css
.modal-overlay[hidden] {
    display: none;
}
```

风险项：

```css
.risk-item {
    border: 1px solid var(--line);
    border-left: 4px solid var(--brand);
    border-radius: 8px;
    padding: 10px;
    background: #fbfdff;
}

.risk-medium {
    border-left-color: var(--accent);
    background: #fffaf2;
}

.risk-high {
    border-left-color: var(--danger);
    background: #fff5f5;
}
```

确认项：

```css
.confirm-check {
    display: flex;
    align-items: flex-start;
    gap: 10px;
    margin: 0 16px 14px;
    padding: 12px;
    border: 1px solid var(--line);
    border-radius: 8px;
    background: #f7fafc;
}
```

禁用按钮样式：

```css
.primary-button:disabled {
    cursor: not-allowed;
    opacity: 0.55;
}
```

## 测试：静态资源继续兜底

文件：`src/test/java/com/codexdemo/orderplatform/FailedEventManagementPageTests.java`

HTML 断言新增：

```java
"replayConfirmOverlay",
"replayConfirmCheckbox",
"replayConfirmSubmitButton"
```

JS 断言新增：

```java
"openReplayConfirm",
"confirmReplaySubmission",
"replayRisks"
```

CSS 断言新增：

```java
".modal-overlay",
".risk-high",
".confirm-check"
```

这些断言能防止后续误删确认弹窗、风险函数或关键样式。

## 真实运行验证

执行过的检查：

```text
node --check src/main/resources/static/failed-events.js
mvn -Dtest=FailedEventManagementPageTests test
mvn test
mvn -DskipTests package
```

结果：

```text
Tests run: 29, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

运行环境：

```text
RabbitMQ: advanced-order-rabbitmq
应用端口: 18104
profile: rabbitmq
```

HTTP 冒烟：

```text
health               : UP
htmlStatusCode       : 200
publishedRouted      : True
failedEventId        : 1
failedStatus         : RECORDED
replayCount          : 0
```

浏览器冒烟：

```text
点击“提交重放”
 -> 弹出“重放安全确认”
 -> 确认按钮默认禁用
 -> 后端状态仍为 RECORDED / replayCount=0

覆盖 Payload 后重新提交
 -> 风险项显示“覆盖字段：payload”
 -> 风险项显示“Payload 已被覆盖”
 -> 勾选确认
 -> 点击“确认重放”
 -> 状态变为 REPLAYED / replayCount=1
 -> 重放审计显示 SUCCEEDED
```

最终 API 验证：

```text
finalStatus              : REPLAYED
replayCount              : 1
latestAttemptStatus      : SUCCEEDED
latestAttemptOperator    : local-admin / SRE
latestAttemptReason      : replay from management page
notificationCount        : 1
```

## 本版总结

v24 没有扩后端数据库，也没有新增 API，但它把一个高风险操作从“点一下就执行”改成了“预览、风险提示、确认、提交”。

这一步让失败事件管理页面更像真实生产运维工具：危险动作可以做，但必须先看清楚自己要做什么。
