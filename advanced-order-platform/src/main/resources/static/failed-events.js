const apiBase = "/api/v1/failed-events";

const state = {
    page: 0,
    totalPages: 0,
    selectedIds: new Set(),
    activeEventId: null,
    activeEvent: null,
    itemsById: new Map(),
    pendingReplay: null
};

const elements = {};

document.addEventListener("DOMContentLoaded", () => {
    [
        "statusFilter",
        "managementStatusFilter",
        "replayApprovalStatusFilter",
        "eventTypeFilter",
        "aggregateTypeFilter",
        "aggregateIdFilter",
        "sortFilter",
        "sizeFilter",
        "exportLimitInput",
        "operatorIdInput",
        "operatorRoleInput",
        "targetManagementStatusInput",
        "managementNoteInput",
        "operatorContextStatus",
        "replayOperatorIdInput",
        "replayOperatorRoleInput",
        "replayOperatorContextStatus",
        "replayReasonInput",
        "replayApprovalReasonInput",
        "replayApprovalReviewNoteInput",
        "replayEventIdInput",
        "replayEventTypeInput",
        "replayAggregateTypeInput",
        "replayAggregateIdInput",
        "replayPayloadInput",
        "failedEventsBody",
        "historyList",
        "historyMeta",
        "replayMeta",
        "approvalHistoryList",
        "attemptList",
        "replayConfirmOverlay",
        "replayConfirmSummary",
        "replayRiskList",
        "replayConfirmCheckbox",
        "replayConfirmSubmitButton",
        "pageMeta",
        "selectedCount",
        "selectAllCheckbox",
        "toast"
    ].forEach((id) => {
        elements[id] = document.getElementById(id);
    });

    document.getElementById("searchButton").addEventListener("click", () => {
        state.page = 0;
        loadFailedEvents();
    });
    document.getElementById("refreshButton").addEventListener("click", loadFailedEvents);
    document.getElementById("resetButton").addEventListener("click", resetFilters);
    document.getElementById("previousPageButton").addEventListener("click", previousPage);
    document.getElementById("nextPageButton").addEventListener("click", nextPage);
    document.getElementById("markButton").addEventListener("click", markSelectedEvents);
    document.getElementById("verifyOperatorButton").addEventListener("click", () => verifyOperatorContext("management"));
    document.getElementById("verifyReplayOperatorButton").addEventListener("click", () => verifyOperatorContext("replay"));
    document.getElementById("requestReplayApprovalButton").addEventListener("click", requestReplayApproval);
    document.getElementById("approveReplayButton").addEventListener("click", () => reviewReplayApproval("APPROVED"));
    document.getElementById("rejectReplayButton").addEventListener("click", () => reviewReplayApproval("REJECTED"));
    document.getElementById("replayButton").addEventListener("click", replayActiveEvent);
    document.getElementById("clearReplayOverrideButton").addEventListener("click", clearReplayOverrides);
    document.getElementById("refreshApprovalHistoryButton").addEventListener("click", refreshActiveReplayApprovalHistory);
    document.getElementById("refreshAttemptsButton").addEventListener("click", refreshActiveReplayAttempts);
    document.getElementById("replayConfirmCancelButton").addEventListener("click", closeReplayConfirm);
    document.getElementById("replayConfirmBackButton").addEventListener("click", closeReplayConfirm);
    document.getElementById("replayConfirmSubmitButton").addEventListener("click", confirmReplaySubmission);
    elements.replayConfirmCheckbox.addEventListener("change", () => {
        elements.replayConfirmSubmitButton.disabled = !elements.replayConfirmCheckbox.checked;
    });
    document.getElementById("exportFailedButton").addEventListener("click", exportFailedEvents);
    document.getElementById("exportHistoryButton").addEventListener("click", exportActiveHistory);
    elements.selectAllCheckbox.addEventListener("change", toggleCurrentPageSelection);

    loadFailedEvents();
});

async function loadFailedEvents() {
    try {
        elements.failedEventsBody.innerHTML = '<tr><td colspan="11" class="empty-cell">加载中</td></tr>';
        const page = await fetchJson(`${apiBase}?${failedEventQueryParams(true)}`);
        state.totalPages = page.totalPages;
        state.selectedIds.clear();
        elements.selectAllCheckbox.checked = false;
        renderFailedEvents(page);
        updateSelectedCount();
    } catch (error) {
        renderTableMessage("加载失败");
        showToast(error.message, true);
    }
}

function failedEventQueryParams(includePage) {
    const params = new URLSearchParams();
    addParam(params, "status", elements.statusFilter.value);
    addParam(params, "managementStatus", elements.managementStatusFilter.value);
    addParam(params, "replayApprovalStatus", elements.replayApprovalStatusFilter.value);
    addParam(params, "eventType", elements.eventTypeFilter.value);
    addParam(params, "aggregateType", elements.aggregateTypeFilter.value);
    addParam(params, "aggregateId", elements.aggregateIdFilter.value);
    addParam(params, "sort", elements.sortFilter.value);
    if (includePage) {
        params.set("page", state.page);
        params.set("size", elements.sizeFilter.value);
    } else {
        params.set("limit", exportLimit());
    }
    return params;
}

function renderFailedEvents(page) {
    state.itemsById = new Map((page.content || []).map((item) => [item.id, item]));
    elements.pageMeta.textContent = `${page.page + 1} / ${Math.max(page.totalPages, 1)}，共 ${page.totalElements}`;
    if (!page.content || page.content.length === 0) {
        renderTableMessage("暂无数据");
        return;
    }
    elements.failedEventsBody.innerHTML = page.content.map((item) => `
        <tr>
            <td class="select-col">
                <input type="checkbox" data-select-id="${item.id}" aria-label="选择 ${item.id}">
            </td>
            <td>
                <div class="row-title">#${escapeHtml(item.id)}</div>
                <div class="muted">${escapeHtml(item.messageId || "")}</div>
            </td>
            <td>${approvalSummary(item)}</td>
            <td>
                <div>${escapeHtml(item.eventType || "")}</div>
                <div class="muted">${escapeHtml(item.sourceQueue || "")}</div>
            </td>
            <td>
                <div>${escapeHtml(item.aggregateType || "")}</div>
                <div class="muted">${escapeHtml(item.aggregateId || "")}</div>
            </td>
            <td>${statusPill(item.status, messageStatusClass(item.status))}</td>
            <td>${statusPill(item.managementStatus, managementStatusClass(item.managementStatus))}</td>
            <td>
                <div>${escapeHtml(item.replayCount)}</div>
                <div class="muted">${formatDate(item.lastReplayedAt)}</div>
                <div class="muted">${escapeHtml(item.lastReplayError || "")}</div>
            </td>
            <td>
                <div>${escapeHtml(item.managedBy || "")}</div>
                <div class="muted">${formatDate(item.managedAt)}</div>
            </td>
            <td>${formatDate(item.failedAt)}</td>
            <td>
                <div class="action-stack">
                    <button class="ghost-button" type="button" data-history-id="${item.id}">流水</button>
                    <button class="secondary-button" type="button" data-replay-id="${item.id}">重放</button>
                </div>
            </td>
        </tr>
    `).join("");

    elements.failedEventsBody.querySelectorAll("[data-select-id]").forEach((checkbox) => {
        checkbox.addEventListener("change", () => {
            const id = Number(checkbox.dataset.selectId);
            if (checkbox.checked) {
                state.selectedIds.add(id);
            } else {
                state.selectedIds.delete(id);
                elements.selectAllCheckbox.checked = false;
            }
            updateSelectedCount();
        });
    });
    elements.failedEventsBody.querySelectorAll("[data-history-id]").forEach((button) => {
        button.addEventListener("click", () => loadHistory(Number(button.dataset.historyId)));
    });
    elements.failedEventsBody.querySelectorAll("[data-replay-id]").forEach((button) => {
        button.addEventListener("click", () => prepareReplay(Number(button.dataset.replayId)));
    });
    if (state.activeEventId && state.itemsById.has(state.activeEventId)) {
        setActiveEvent(state.activeEventId);
    }
}

async function prepareReplay(id) {
    setActiveEvent(id);
    await Promise.all([
        loadHistory(id),
        loadReplayApprovalHistory(id),
        loadReplayAttempts(id)
    ]);
}

function setActiveEvent(id) {
    state.activeEventId = id;
    state.activeEvent = state.itemsById.get(id) || state.activeEvent;
    if (state.activeEvent && state.activeEvent.id === id) {
        renderReplayMeta(state.activeEvent);
        updateReplayPlaceholders(state.activeEvent);
        return;
    }
    elements.replayMeta.textContent = `#${id}`;
}

function renderReplayMeta(item) {
    elements.replayMeta.innerHTML = `
        <div class="row-title">#${escapeHtml(item.id)} ${escapeHtml(item.messageId || "")}</div>
        <div>${escapeHtml(item.eventType || "")} / ${escapeHtml(item.aggregateType || "")}:${escapeHtml(item.aggregateId || "")}</div>
        <div class="muted">${escapeHtml(item.status || "")}，重放 ${escapeHtml(item.replayCount)} 次</div>
        <div>${approvalSummary(item)}</div>
    `;
}

function updateReplayPlaceholders(item) {
    elements.replayEventIdInput.placeholder = item.eventId || "UUID";
    elements.replayEventTypeInput.placeholder = item.eventType || "OrderCreated";
    elements.replayAggregateTypeInput.placeholder = item.aggregateType || "ORDER";
    elements.replayAggregateIdInput.placeholder = item.aggregateId || "404";
    elements.replayPayloadInput.placeholder = item.payload || "";
}

async function loadHistory(id) {
    try {
        setActiveEvent(id);
        elements.historyMeta.textContent = `#${id}`;
        elements.historyList.innerHTML = '<div class="history-item">加载中</div>';
        const history = await fetchJson(`${apiBase}/${id}/management-history`);
        if (!history || history.length === 0) {
            elements.historyList.innerHTML = '<div class="history-item">暂无流水</div>';
            return;
        }
        elements.historyList.innerHTML = history.map((item) => `
            <article class="history-item">
                <div class="history-line">${escapeHtml(item.previousStatus)} -> ${escapeHtml(item.newStatus)}</div>
                <div class="muted">${escapeHtml(item.operatorId)} / ${escapeHtml(item.operatorRole)}</div>
                <div class="history-note">${escapeHtml(item.note || "")}</div>
                <div class="muted">${formatDate(item.changedAt)}</div>
            </article>
        `).join("");
    } catch (error) {
        elements.historyList.innerHTML = '<div class="history-item">加载失败</div>';
        showToast(error.message, true);
    }
}

async function loadReplayAttempts(id) {
    try {
        setActiveEvent(id);
        elements.attemptList.innerHTML = '<div class="attempt-item">加载中</div>';
        const attempts = await fetchJson(`${apiBase}/${id}/replay-attempts`);
        renderReplayAttempts(attempts);
    } catch (error) {
        elements.attemptList.innerHTML = '<div class="attempt-item">加载失败</div>';
        showToast(error.message, true);
    }
}

async function loadReplayApprovalHistory(id) {
    try {
        setActiveEvent(id);
        elements.approvalHistoryList.innerHTML = '<div class="approval-history-item">Loading...</div>';
        const history = await fetchJson(`${apiBase}/${id}/replay-approval-history`);
        renderReplayApprovalHistory(history);
    } catch (error) {
        elements.approvalHistoryList.innerHTML = '<div class="approval-history-item">Load failed</div>';
        showToast(error.message, true);
    }
}

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

function renderReplayAttempts(attempts) {
    if (!attempts || attempts.length === 0) {
        elements.attemptList.innerHTML = '<div class="attempt-item">暂无审计</div>';
        return;
    }
    elements.attemptList.innerHTML = attempts.map((item) => `
        <article class="attempt-item">
            <div class="attempt-line">
                ${statusPill(item.status, replayAttemptStatusClass(item.status))}
                <span>${escapeHtml(item.effectiveEventType || "")}</span>
            </div>
            <div class="muted">${escapeHtml(item.operatorId || "")} / ${escapeHtml(item.operatorRole || "")}</div>
            <div class="history-note">${escapeHtml(item.reason || "")}</div>
            <div class="muted">${escapeHtml(item.effectiveEventId || "")}</div>
            ${item.errorMessage ? `<div class="attempt-error">${escapeHtml(item.errorMessage)}</div>` : ""}
            <div class="muted">${formatDate(item.attemptedAt)}</div>
        </article>
    `).join("");
}

async function markSelectedEvents() {
    if (state.selectedIds.size === 0) {
        showToast("请选择失败事件", true);
        return;
    }
    const body = {
        ids: Array.from(state.selectedIds),
        status: elements.targetManagementStatusInput.value,
        note: elements.managementNoteInput.value
    };
    try {
        const response = await fetch(apiBase + "/management-status", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                ...managementOperatorHeaders()
            },
            body: JSON.stringify(body)
        });
        if (!response.ok) {
            throw new Error(await errorMessage(response));
        }
        const result = await response.json();
        showToast(`已更新 ${result.updatedCount} 条`);
        await loadFailedEvents();
        if (state.activeEventId) {
            await loadHistory(state.activeEventId);
        }
    } catch (error) {
        showToast(error.message, true);
    }
}

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

function managementOperatorHeaders() {
    return {
        "X-Operator-Id": elements.operatorIdInput.value,
        "X-Operator-Role": elements.operatorRoleInput.value
    };
}

async function requestReplayApproval() {
    const id = replayTargetId();
    if (!id) {
        showToast("请选择要申请审批的失败事件", true);
        return;
    }
    const reason = elements.replayApprovalReasonInput.value.trim() || elements.replayReasonInput.value.trim();
    if (!reason) {
        showToast("请填写审批申请原因", true);
        return;
    }
    try {
        const response = await fetch(`${apiBase}/${id}/replay-approval`, {
            method: "POST",
            headers: replayOperatorHeaders(),
            body: JSON.stringify({ reason })
        });
        if (!response.ok) {
            throw new Error(await errorMessage(response));
        }
        await handleReplayApprovalResult(await response.json(), "审批申请已提交");
    } catch (error) {
        showToast(error.message, true);
    }
}

async function reviewReplayApproval(status) {
    const id = replayTargetId();
    if (!id) {
        showToast("请选择要审批的失败事件", true);
        return;
    }
    const note = elements.replayApprovalReviewNoteInput.value.trim();
    if (status === "REJECTED" && !note) {
        showToast("拒绝审批需要填写原因", true);
        return;
    }
    if (isSelfReviewAttempt()) {
        showToast("申请人不能审批自己的重放申请，请切换审批人", true);
        return;
    }
    try {
        const response = await fetch(`${apiBase}/${id}/replay-approval/review`, {
            method: "POST",
            headers: replayOperatorHeaders(),
            body: JSON.stringify({ status, note })
        });
        if (!response.ok) {
            throw new Error(await errorMessage(response));
        }
        await handleReplayApprovalResult(await response.json(), status === "APPROVED" ? "审批已通过" : "审批已拒绝");
    } catch (error) {
        showToast(error.message, true);
    }
}

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

function isSelfReviewAttempt() {
    const event = state.activeEvent || {};
    return event.replayApprovalStatus === "PENDING"
            && event.replayApprovalRequestedBy
            && event.replayApprovalRequestedBy === elements.replayOperatorIdInput.value.trim();
}

async function replayActiveEvent() {
    const id = replayTargetId();
    if (!id) {
        showToast("请选择要重放的失败事件", true);
        return;
    }
    if ((state.activeEvent?.replayApprovalStatus || "NOT_REQUESTED") !== "APPROVED") {
        showToast("重放前必须先审批通过", true);
        return;
    }
    const replayRequest = buildReplayRequest(id);
    if (!replayRequest) {
        return;
    }
    openReplayConfirm(replayRequest);
}

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

function openReplayConfirm(replayRequest) {
    state.pendingReplay = replayRequest;
    elements.replayConfirmCheckbox.checked = false;
    elements.replayConfirmSubmitButton.disabled = true;
    elements.replayConfirmSummary.innerHTML = replayConfirmSummary(replayRequest);
    elements.replayRiskList.innerHTML = replayRiskList(replayRequest);
    elements.replayConfirmOverlay.hidden = false;
    elements.replayConfirmCheckbox.focus();
}

function closeReplayConfirm() {
    state.pendingReplay = null;
    elements.replayConfirmOverlay.hidden = true;
}

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

async function submitReplayRequest(replayRequest) {
    const { id, body, operatorId, operatorRole } = replayRequest;
    try {
        const response = await fetch(`${apiBase}/${id}/replay`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "X-Operator-Id": operatorId,
                "X-Operator-Role": operatorRole
            },
            body: JSON.stringify(body)
        });
        if (!response.ok) {
            throw new Error(await errorMessage(response));
        }
        const result = await response.json();
        state.activeEvent = result;
        showToast(`重放已提交，当前状态 ${result.status}`);
        await loadFailedEvents();
        await loadReplayAttempts(id);
    } catch (error) {
        showToast(error.message, true);
        await loadReplayAttempts(id);
    }
}

function replayConfirmSummary(replayRequest) {
    const event = replayRequest.event || {};
    return `
        <div class="summary-grid">
            <div class="summary-item">
                <span>失败事件</span>
                <strong>#${escapeHtml(replayRequest.id)} ${escapeHtml(event.messageId || "")}</strong>
            </div>
            <div class="summary-item">
                <span>当前状态</span>
                <strong>${escapeHtml(event.status || "")}，重放 ${escapeHtml(event.replayCount ?? "")} 次</strong>
            </div>
            <div class="summary-item">
                <span>事件</span>
                <strong>${escapeHtml(event.eventType || replayRequest.body.eventType || "")}</strong>
            </div>
            <div class="summary-item">
                <span>聚合</span>
                <strong>${escapeHtml(event.aggregateType || replayRequest.body.aggregateType || "")}:${escapeHtml(event.aggregateId || replayRequest.body.aggregateId || "")}</strong>
            </div>
            <div class="summary-item">
                <span>操作者</span>
                <strong>${escapeHtml(replayRequest.operatorId || "")} / ${escapeHtml(replayRequest.operatorRole || "")}</strong>
            </div>
            <div class="summary-item">
                <span>重放原因</span>
                <strong>${escapeHtml(replayRequest.body.reason)}</strong>
            </div>
            <div class="summary-item">
                <span>Approval</span>
                <strong>${escapeHtml(event.replayApprovalStatus || "NOT_REQUESTED")}</strong>
            </div>
        </div>
    `;
}

function replayRiskList(replayRequest) {
    return replayRisks(replayRequest).map((risk) => `
        <article class="risk-item ${risk.level}">
            <strong>${escapeHtml(risk.title)}</strong>
            <div class="muted">${escapeHtml(risk.description)}</div>
        </article>
    `).join("");
}

function replayRisks(replayRequest) {
    const risks = [];
    const event = replayRequest.event || {};
    if ((event.replayApprovalStatus || "NOT_REQUESTED") !== "APPROVED") {
        risks.push({
            level: "risk-high",
            title: "Replay approval is not approved",
            description: "后台会拒绝未审批通过的重放请求，请先完成申请和审批。"
        });
    }
    const overrideFields = ["eventId", "eventType", "aggregateType", "aggregateId", "payload"]
            .filter((field) => Object.prototype.hasOwnProperty.call(replayRequest.body, field));
    if (overrideFields.length === 0) {
        risks.push({
            level: "",
            title: "未覆盖消息字段",
            description: "本次将使用失败事件原始字段重放，后端会在缺失 eventId 时生成新的 UUID。"
        });
    } else {
        risks.push({
            level: "risk-medium",
            title: `覆盖字段：${overrideFields.join(", ")}`,
            description: "覆盖字段会写入重放审计，请确认它们来自已核对的修复方案。"
        });
    }
    if (Object.prototype.hasOwnProperty.call(replayRequest.body, "payload")) {
        risks.push({
            level: "risk-high",
            title: "Payload 已被覆盖",
            description: "Payload 变更会改变下游消费者收到的消息内容，请确认 JSON 和业务语义都正确。"
        });
    }
    if (event.status === "REPLAYED") {
        risks.push({
            level: "risk-medium",
            title: "当前事件已经重放成功",
            description: "后端会保护性跳过再次投递，并记录 SKIPPED_ALREADY_REPLAYED 审计。"
        });
    }
    if (!replayRequest.operatorId || !replayRequest.operatorRole) {
        risks.push({
            level: "risk-high",
            title: "操作者信息不完整",
            description: "后端会拒绝缺少 X-Operator-Id 或 X-Operator-Role 的重放请求。"
        });
    }
    return risks;
}

function replayTargetId() {
    if (state.activeEventId) {
        return state.activeEventId;
    }
    if (state.selectedIds.size === 1) {
        const id = Array.from(state.selectedIds)[0];
        setActiveEvent(id);
        return id;
    }
    return null;
}

function refreshActiveReplayAttempts() {
    const id = replayTargetId();
    if (!id) {
        showToast("请选择失败事件", true);
        return;
    }
    loadReplayAttempts(id);
}

function refreshActiveReplayApprovalHistory() {
    const id = replayTargetId();
    if (!id) {
        showToast("请选择失败事件", true);
        return;
    }
    loadReplayApprovalHistory(id);
}

function clearReplayOverrides() {
    elements.replayEventIdInput.value = "";
    elements.replayEventTypeInput.value = "";
    elements.replayAggregateTypeInput.value = "";
    elements.replayAggregateIdInput.value = "";
    elements.replayPayloadInput.value = "";
}

function exportFailedEvents() {
    downloadCsv(`${apiBase}/export?${failedEventQueryParams(false)}`, "failed-events.csv");
}

function exportActiveHistory() {
    if (!state.activeEventId) {
        showToast("请选择状态流水", true);
        return;
    }
    const params = new URLSearchParams();
    params.set("failedEventMessageId", state.activeEventId);
    params.set("sort", "changedAt,desc");
    params.set("limit", exportLimit());
    downloadCsv(`${apiBase}/management-history/export?${params}`, "failed-event-management-history.csv");
}

async function downloadCsv(url, filename) {
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(await errorMessage(response));
        }
        const blob = await response.blob();
        const objectUrl = URL.createObjectURL(blob);
        const anchor = document.createElement("a");
        anchor.href = objectUrl;
        anchor.download = filename;
        document.body.appendChild(anchor);
        anchor.click();
        anchor.remove();
        URL.revokeObjectURL(objectUrl);
        showToast("导出完成");
    } catch (error) {
        showToast(error.message, true);
    }
}

function toggleCurrentPageSelection() {
    elements.failedEventsBody.querySelectorAll("[data-select-id]").forEach((checkbox) => {
        checkbox.checked = elements.selectAllCheckbox.checked;
        const id = Number(checkbox.dataset.selectId);
        if (checkbox.checked) {
            state.selectedIds.add(id);
        } else {
            state.selectedIds.delete(id);
        }
    });
    updateSelectedCount();
}

function previousPage() {
    if (state.page === 0) {
        return;
    }
    state.page -= 1;
    loadFailedEvents();
}

function nextPage() {
    if (state.page + 1 >= state.totalPages) {
        return;
    }
    state.page += 1;
    loadFailedEvents();
}

function resetFilters() {
    elements.statusFilter.value = "";
    elements.managementStatusFilter.value = "";
    elements.replayApprovalStatusFilter.value = "";
    elements.eventTypeFilter.value = "";
    elements.aggregateTypeFilter.value = "";
    elements.aggregateIdFilter.value = "";
    elements.sortFilter.value = "failedAt,desc";
    elements.sizeFilter.value = "20";
    elements.exportLimitInput.value = "1000";
    state.page = 0;
    loadFailedEvents();
}

function updateSelectedCount() {
    elements.selectedCount.textContent = `${state.selectedIds.size} 已选`;
}

async function fetchJson(url, options = {}) {
    const response = await fetch(url, options);
    if (!response.ok) {
        throw new Error(await errorMessage(response));
    }
    return response.json();
}

async function errorMessage(response) {
    try {
        const payload = await response.json();
        return payload.detail || payload.message || `HTTP ${response.status}`;
    } catch {
        return `HTTP ${response.status}`;
    }
}

function addParam(params, name, value) {
    if (value && value.trim() !== "") {
        params.set(name, value.trim());
    }
}

function addBodyField(body, name, value) {
    if (value && value.trim() !== "") {
        body[name] = value.trim();
    }
}

function exportLimit() {
    const value = elements.exportLimitInput.value || "1000";
    return value.trim();
}

function statusPill(value, extraClass) {
    return `<span class="status-pill ${extraClass}">${escapeHtml(value || "")}</span>`;
}

function approvalSummary(item) {
    const status = item.replayApprovalStatus || "NOT_REQUESTED";
    const requested = item.replayApprovalRequestedBy
            ? `req ${item.replayApprovalRequestedBy} ${formatDate(item.replayApprovalRequestedAt)}`
            : "";
    const reviewed = item.replayApprovalReviewedBy
            ? `rev ${item.replayApprovalReviewedBy} ${formatDate(item.replayApprovalReviewedAt)}`
            : "";
    return `
        <div>${statusPill(status, approvalStatusClass(status))}</div>
        <div class="muted">${escapeHtml(requested)}</div>
        <div class="muted">${escapeHtml(reviewed)}</div>
    `;
}

function messageStatusClass(value) {
    switch (value) {
        case "REPLAYED":
            return "status-resolved";
        case "REPLAY_FAILED":
            return "status-failed";
        default:
            return "";
    }
}

function managementStatusClass(value) {
    switch (value) {
        case "OPEN":
            return "status-open";
        case "INVESTIGATING":
            return "status-investigating";
        case "RESOLVED":
            return "status-resolved";
        case "IGNORED":
            return "status-ignored";
        default:
            return "";
    }
}

function approvalStatusClass(value) {
    switch (value) {
        case "APPROVED":
            return "status-resolved";
        case "PENDING":
            return "status-investigating";
        case "REJECTED":
            return "status-failed";
        default:
            return "status-open";
    }
}

function replayAttemptStatusClass(value) {
    switch (value) {
        case "SUCCEEDED":
            return "status-resolved";
        case "FAILED":
            return "status-failed";
        case "SKIPPED_ALREADY_REPLAYED":
            return "status-skipped";
        default:
            return "";
    }
}

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

function formatDate(value) {
    if (!value) {
        return "";
    }
    const date = new Date(value);
    if (Number.isNaN(date.getTime())) {
        return value;
    }
    return date.toLocaleString();
}

function renderTableMessage(message) {
    elements.failedEventsBody.innerHTML = `<tr><td colspan="11" class="empty-cell">${escapeHtml(message)}</td></tr>`;
    elements.pageMeta.textContent = "0 / 0";
}

function showToast(message, isError = false) {
    elements.toast.textContent = message;
    elements.toast.classList.toggle("error", isError);
    elements.toast.classList.add("visible");
    window.clearTimeout(showToast.timer);
    showToast.timer = window.setTimeout(() => {
        elements.toast.classList.remove("visible");
    }, 3200);
}

function escapeHtml(value) {
    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll("\"", "&quot;")
        .replaceAll("'", "&#39;");
}
