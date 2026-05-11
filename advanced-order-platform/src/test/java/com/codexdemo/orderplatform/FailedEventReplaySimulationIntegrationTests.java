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
class FailedEventReplaySimulationIntegrationTests {

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
    void replaySimulationReturnsStableNotFoundBody() throws Exception {
        mockMvc.perform(get("/api/v1/failed-events/{id}/replay-simulation", 999_999L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sampledAt").exists())
                .andExpect(jsonPath("$.failedEventId").value(999_999))
                .andExpect(jsonPath("$.exists").value(false))
                .andExpect(jsonPath("$.eligibleForReplay").value(false))
                .andExpect(jsonPath("$.wouldReplay").value(false))
                .andExpect(jsonPath("$.wouldPublishOutbox").value(false))
                .andExpect(jsonPath("$.wouldChangeManagementStatus").value(false))
                .andExpect(jsonPath("$.requiredApprovalStatus").value("APPROVED"))
                .andExpect(jsonPath("$.expectedSideEffects").isEmpty())
                .andExpect(jsonPath("$.blockedBy[0]").value("FAILED_EVENT_NOT_FOUND"));
    }

    @Test
    void replaySimulationReturnsExpectedSideEffectsForApprovedEvent() throws Exception {
        FailedEventMessage older = failedEvent("simulation-older", Instant.parse("2026-05-11T13:00:00Z"));
        FailedEventMessage target = failedEvent("simulation-target", Instant.parse("2026-05-11T13:05:00Z"));
        target.requestReplayApproval("need replay", "ops-user", Instant.parse("2026-05-11T13:10:00Z"));
        target.approveReplay("sre-user", "approved", Instant.parse("2026-05-11T13:15:00Z"));
        failedEventMessageRepository.save(older);
        FailedEventMessage savedTarget = failedEventMessageRepository.save(target);
        failedEventReplayApprovalHistoryRepository.save(FailedEventReplayApprovalHistory.record(
                savedTarget,
                FailedEventReplayApprovalHistoryAction.APPROVED,
                "sre-user",
                "SRE",
                "approved",
                Instant.parse("2026-05-11T13:15:00Z")
        ));

        mockMvc.perform(get("/api/v1/failed-events/{id}/replay-simulation", savedTarget.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sampledAt").exists())
                .andExpect(jsonPath("$.failedEventId").value(savedTarget.getId()))
                .andExpect(jsonPath("$.exists").value(true))
                .andExpect(jsonPath("$.eligibleForReplay").value(true))
                .andExpect(jsonPath("$.wouldReplay").value(true))
                .andExpect(jsonPath("$.wouldPublishOutbox").value(true))
                .andExpect(jsonPath("$.wouldChangeManagementStatus").value(false))
                .andExpect(jsonPath("$.requiredApprovalStatus").value("APPROVED"))
                .andExpect(jsonPath("$.idempotencyKeyHint")
                        .value("failed-event-replay:%s:simulation-target".formatted(savedTarget.getId())))
                .andExpect(jsonPath("$.expectedAggregateId").value("simulation-target"))
                .andExpect(jsonPath("$.expectedSideEffects[0]").value("PUBLISH_RABBITMQ_REPLAY_MESSAGE"))
                .andExpect(jsonPath("$.expectedSideEffects[1]").value("SAVE_REPLAY_ATTEMPT_AUDIT"))
                .andExpect(jsonPath("$.expectedSideEffects[2]").value("MARK_FAILED_EVENT_REPLAYED_ON_SUCCESS"))
                .andExpect(jsonPath("$.expectedSideEffects[3]").value("MARK_FAILED_EVENT_REPLAY_FAILED_ON_BROKER_ERROR"))
                .andExpect(jsonPath("$.blockedBy").isEmpty())
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
                "simulation integration test",
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
