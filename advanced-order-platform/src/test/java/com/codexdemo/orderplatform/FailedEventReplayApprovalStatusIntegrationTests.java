package com.codexdemo.orderplatform;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codexdemo.orderplatform.notification.FailedEventManagementHistoryRepository;
import com.codexdemo.orderplatform.notification.FailedEventMessage;
import com.codexdemo.orderplatform.notification.FailedEventMessageRepository;
import com.codexdemo.orderplatform.notification.FailedEventReplayApprovalHistory;
import com.codexdemo.orderplatform.notification.FailedEventReplayApprovalHistoryAction;
import com.codexdemo.orderplatform.notification.FailedEventReplayApprovalHistoryRepository;
import com.codexdemo.orderplatform.notification.FailedEventReplayAttemptRepository;
import java.time.Instant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
        "order.expiration.enabled=false",
        "outbox.publisher.enabled=false",
        "outbox.rabbitmq.enabled=true"
})
@AutoConfigureMockMvc
class FailedEventReplayApprovalStatusIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FailedEventMessageRepository failedEventMessageRepository;

    @Autowired
    private FailedEventReplayAttemptRepository failedEventReplayAttemptRepository;

    @Autowired
    private FailedEventManagementHistoryRepository failedEventManagementHistoryRepository;

    @Autowired
    private FailedEventReplayApprovalHistoryRepository failedEventReplayApprovalHistoryRepository;

    @BeforeEach
    void cleanFailedEventData() {
        deleteFailedEventData();
    }

    @AfterEach
    void removeFailedEventData() {
        deleteFailedEventData();
    }

    @Test
    void approvalStatusReturnsStableNotFoundBody() throws Exception {
        mockMvc.perform(get("/api/v1/failed-events/{id}/approval-status", 999_999L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sampledAt").exists())
                .andExpect(jsonPath("$.failedEventId").value(999_999))
                .andExpect(jsonPath("$.exists").value(false))
                .andExpect(jsonPath("$.requiredApprovalStatus").value("APPROVED"))
                .andExpect(jsonPath("$.approvedForReplay").value(false))
                .andExpect(jsonPath("$.historyCount").value(0))
                .andExpect(jsonPath("$.approvalBlockedBy[0]").value("FAILED_EVENT_NOT_FOUND"));
    }

    @Test
    void approvalStatusReturnsApprovedReadModel() throws Exception {
        FailedEventMessage target = failedEvent("approval-status-target", Instant.parse("2026-05-12T08:00:00Z"));
        target.requestReplayApproval("need replay", "ops-user", Instant.parse("2026-05-12T08:05:00Z"));
        target.approveReplay("sre-user", "approved", Instant.parse("2026-05-12T08:10:00Z"));
        FailedEventMessage savedTarget = failedEventMessageRepository.save(target);
        failedEventReplayApprovalHistoryRepository.save(FailedEventReplayApprovalHistory.record(
                savedTarget,
                FailedEventReplayApprovalHistoryAction.REQUESTED,
                "ops-user",
                "OPS",
                "need replay",
                Instant.parse("2026-05-12T08:05:00Z")
        ));
        failedEventReplayApprovalHistoryRepository.save(FailedEventReplayApprovalHistory.record(
                savedTarget,
                FailedEventReplayApprovalHistoryAction.APPROVED,
                "sre-user",
                "SRE",
                "approved",
                Instant.parse("2026-05-12T08:10:00Z")
        ));

        mockMvc.perform(get("/api/v1/failed-events/{id}/approval-status", savedTarget.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sampledAt").exists())
                .andExpect(jsonPath("$.failedEventId").value(savedTarget.getId()))
                .andExpect(jsonPath("$.exists").value(true))
                .andExpect(jsonPath("$.failedEventStatus").value("RECORDED"))
                .andExpect(jsonPath("$.managementStatus").value("OPEN"))
                .andExpect(jsonPath("$.approvalStatus").value("APPROVED"))
                .andExpect(jsonPath("$.requiredApprovalStatus").value("APPROVED"))
                .andExpect(jsonPath("$.approvalRequested").value(true))
                .andExpect(jsonPath("$.approvalPending").value(false))
                .andExpect(jsonPath("$.approvedForReplay").value(true))
                .andExpect(jsonPath("$.rejected").value(false))
                .andExpect(jsonPath("$.requestReason").value("need replay"))
                .andExpect(jsonPath("$.requestedBy").value("ops-user"))
                .andExpect(jsonPath("$.reviewedBy").value("sre-user"))
                .andExpect(jsonPath("$.reviewNote").value("approved"))
                .andExpect(jsonPath("$.historyCount").value(2))
                .andExpect(jsonPath("$.latestApproval.action").value("APPROVED"))
                .andExpect(jsonPath("$.latestApproval.operatorRole").value("SRE"))
                .andExpect(jsonPath("$.approvalBlockedBy").isEmpty())
                .andExpect(jsonPath("$.nextAllowedActions[0]").value("REPLAY_FAILED_EVENT"));
    }

    private FailedEventMessage failedEvent(String messageId, Instant failedAt) {
        FailedEventMessage failedEvent = FailedEventMessage.record(
                messageId,
                "event-" + messageId,
                "OrderNotificationFailed",
                "ORDER",
                messageId,
                "order-platform.outbox.events",
                "order-platform.outbox.events.dlq",
                "approval status integration test",
                "{\"messageId\":\"%s\"}".formatted(messageId)
        );
        ReflectionTestUtils.setField(failedEvent, "failedAt", failedAt);
        return failedEvent;
    }

    private void deleteFailedEventData() {
        failedEventReplayApprovalHistoryRepository.deleteAll();
        failedEventManagementHistoryRepository.deleteAll();
        failedEventReplayAttemptRepository.deleteAll();
        failedEventMessageRepository.deleteAll();
    }
}
