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
class FailedEventReplayExecutionContractIntegrationTests {

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
    void replayExecutionContractReturnsStableNotFoundBody() throws Exception {
        mockMvc.perform(get("/api/v1/failed-events/{id}/replay-execution-contract", 999_999L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sampledAt").exists())
                .andExpect(jsonPath("$.failedEventId").value(999_999))
                .andExpect(jsonPath("$.exists").value(false))
                .andExpect(jsonPath("$.contractVersion").value("failed-event-replay-execution-contract.v1"))
                .andExpect(jsonPath("$.contractDigest").exists())
                .andExpect(jsonPath("$.approvalEvidenceVersion").value("failed-event-approval-status.v1"))
                .andExpect(jsonPath("$.approvalDigest").exists())
                .andExpect(jsonPath("$.replayEligibilityDigest").exists())
                .andExpect(jsonPath("$.replayPreconditionsSatisfied").value(false))
                .andExpect(jsonPath("$.realReplayEndpointEnforcesApprovalDigest").value(false))
                .andExpect(jsonPath("$.realReplayEndpointEnforcesReplayEligibilityDigest").value(false))
                .andExpect(jsonPath("$.digestVerificationMode").value("CLIENT_PRECHECK_ONLY"))
                .andExpect(jsonPath("$.executionChecks[0].checkId").value("FAILED_EVENT_EXISTS"))
                .andExpect(jsonPath("$.executionChecks[0].status").value("BLOCKED"))
                .andExpect(jsonPath("$.blockedBy[0]").value("FAILED_EVENT_NOT_FOUND"))
                .andExpect(jsonPath("$.expectedSideEffects").isEmpty());
    }

    @Test
    void replayExecutionContractReturnsApprovedReplayContract() throws Exception {
        FailedEventMessage target = failedEvent("execution-contract-target", Instant.parse("2026-05-12T09:00:00Z"));
        target.requestReplayApproval("need replay", "ops-user", Instant.parse("2026-05-12T09:05:00Z"));
        target.approveReplay("sre-user", "approved", Instant.parse("2026-05-12T09:10:00Z"));
        FailedEventMessage savedTarget = failedEventMessageRepository.save(target);
        failedEventReplayApprovalHistoryRepository.save(FailedEventReplayApprovalHistory.record(
                savedTarget,
                FailedEventReplayApprovalHistoryAction.REQUESTED,
                "ops-user",
                "ORDER_SUPPORT",
                "need replay",
                Instant.parse("2026-05-12T09:05:00Z")
        ));
        failedEventReplayApprovalHistoryRepository.save(FailedEventReplayApprovalHistory.record(
                savedTarget,
                FailedEventReplayApprovalHistoryAction.APPROVED,
                "sre-user",
                "SRE",
                "approved",
                Instant.parse("2026-05-12T09:10:00Z")
        ));

        mockMvc.perform(get("/api/v1/failed-events/{id}/replay-execution-contract", savedTarget.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sampledAt").exists())
                .andExpect(jsonPath("$.failedEventId").value(savedTarget.getId()))
                .andExpect(jsonPath("$.exists").value(true))
                .andExpect(jsonPath("$.contractVersion").value("failed-event-replay-execution-contract.v1"))
                .andExpect(jsonPath("$.contractDigest").exists())
                .andExpect(jsonPath("$.approvalEvidenceVersion").value("failed-event-approval-status.v1"))
                .andExpect(jsonPath("$.approvalStatus").value("APPROVED"))
                .andExpect(jsonPath("$.requiredApprovalStatus").value("APPROVED"))
                .andExpect(jsonPath("$.replayPreconditionsSatisfied").value(true))
                .andExpect(jsonPath("$.realExecutionMethod").value("POST"))
                .andExpect(jsonPath("$.realExecutionPath").value("/api/v1/failed-events/{id}/replay"))
                .andExpect(jsonPath("$.requiredOperatorAction").value("REPLAY_FAILED_EVENT"))
                .andExpect(jsonPath("$.idempotencyKeyHint")
                        .value("failed-event-replay:%s:execution-contract-target".formatted(savedTarget.getId())))
                .andExpect(jsonPath("$.executionChecks[0].status").value("PASSED"))
                .andExpect(jsonPath("$.executionChecks[1].checkId").value("REPLAY_APPROVAL_APPROVED"))
                .andExpect(jsonPath("$.executionChecks[1].evidenceDigest").exists())
                .andExpect(jsonPath("$.requestRequirements[0].field").value("reason"))
                .andExpect(jsonPath("$.requestRequirements[0].requiredForPost").value(true))
                .andExpect(jsonPath("$.blockedBy").isEmpty())
                .andExpect(jsonPath("$.expectedSideEffects[0]").value("PUBLISH_RABBITMQ_REPLAY_MESSAGE"))
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
                "execution contract integration test",
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
