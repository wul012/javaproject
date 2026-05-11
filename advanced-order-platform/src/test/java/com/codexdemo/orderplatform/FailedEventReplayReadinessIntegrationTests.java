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
import com.codexdemo.orderplatform.notification.FailedEventReplayAttempt;
import com.codexdemo.orderplatform.notification.FailedEventReplayAttemptRepository;
import com.codexdemo.orderplatform.notification.FailedEventReplayAttemptStatus;
import com.codexdemo.orderplatform.notification.ReplayFailedEventRequest;
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
class FailedEventReplayReadinessIntegrationTests {

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
    void replayReadinessReturnsStableNotFoundBody() throws Exception {
        mockMvc.perform(get("/api/v1/failed-events/{id}/replay-readiness", 999_999L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sampledAt").exists())
                .andExpect(jsonPath("$.failedEventId").value(999_999))
                .andExpect(jsonPath("$.exists").value(false))
                .andExpect(jsonPath("$.eligibleForReplay").value(false))
                .andExpect(jsonPath("$.requiresApproval").value(false))
                .andExpect(jsonPath("$.blockedBy[0]").value("FAILED_EVENT_NOT_FOUND"));
    }

    @Test
    void replayReadinessReturnsApprovedEventPlan() throws Exception {
        FailedEventMessage older = failedEvent("readiness-older", Instant.parse("2026-05-11T09:00:00Z"));
        FailedEventMessage target = failedEvent("readiness-target", Instant.parse("2026-05-11T09:05:00Z"));
        target.requestReplayApproval("need replay", "ops-user", Instant.parse("2026-05-11T09:10:00Z"));
        target.approveReplay("sre-user", "approved", Instant.parse("2026-05-11T09:15:00Z"));
        FailedEventMessage replayed = failedEvent("readiness-replayed", Instant.parse("2026-05-11T09:02:00Z"));
        replayed.markReplayed("event-readiness-replayed", Instant.parse("2026-05-11T09:03:00Z"));
        failedEventMessageRepository.save(older);
        FailedEventMessage savedTarget = failedEventMessageRepository.save(target);
        failedEventMessageRepository.save(replayed);
        failedEventReplayApprovalHistoryRepository.save(FailedEventReplayApprovalHistory.record(
                savedTarget,
                FailedEventReplayApprovalHistoryAction.APPROVED,
                "sre-user",
                "SRE",
                "approved",
                Instant.parse("2026-05-11T09:15:00Z")
        ));
        failedEventReplayAttemptRepository.save(FailedEventReplayAttempt.record(
                savedTarget,
                "sre-user",
                "SRE",
                "readiness replay attempt",
                new ReplayFailedEventRequest(null, null, null, null, null, "readiness replay attempt"),
                "event-readiness-target",
                "OrderNotificationFailed",
                "ORDER",
                "readiness-target",
                "{\"messageId\":\"readiness-target\"}",
                FailedEventReplayAttemptStatus.FAILED,
                "broker unavailable",
                Instant.parse("2026-05-11T09:20:00Z")
        ));

        mockMvc.perform(get("/api/v1/failed-events/{id}/replay-readiness", savedTarget.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sampledAt").exists())
                .andExpect(jsonPath("$.failedEventId").value(savedTarget.getId()))
                .andExpect(jsonPath("$.exists").value(true))
                .andExpect(jsonPath("$.eventType").value("OrderNotificationFailed"))
                .andExpect(jsonPath("$.aggregateType").value("ORDER"))
                .andExpect(jsonPath("$.aggregateId").value("readiness-target"))
                .andExpect(jsonPath("$.managementStatus").value("OPEN"))
                .andExpect(jsonPath("$.replayApprovalStatus").value("APPROVED"))
                .andExpect(jsonPath("$.replayBacklogPosition").value(2))
                .andExpect(jsonPath("$.eligibleForReplay").value(true))
                .andExpect(jsonPath("$.requiresApproval").value(false))
                .andExpect(jsonPath("$.nextAllowedActions[0]").value("REPLAY_FAILED_EVENT"))
                .andExpect(jsonPath("$.latestReplayAttempt.status").value("FAILED"))
                .andExpect(jsonPath("$.latestReplayAttempt.errorMessage").value("broker unavailable"))
                .andExpect(jsonPath("$.latestApproval.status").value("APPROVED"));
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
                "readiness integration test",
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
