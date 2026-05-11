package com.codexdemo.orderplatform;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codexdemo.orderplatform.notification.FailedEventManagementHistoryRepository;
import com.codexdemo.orderplatform.notification.FailedEventMessage;
import com.codexdemo.orderplatform.notification.FailedEventMessageRepository;
import com.codexdemo.orderplatform.notification.FailedEventReplayApprovalHistoryRepository;
import com.codexdemo.orderplatform.notification.FailedEventReplayAttemptRepository;
import java.time.Instant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
        "order.expiration.enabled=false",
        "outbox.publisher.enabled=false"
})
@AutoConfigureMockMvc
class FailedEventSummaryIntegrationTests {

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
    void failedEventSummaryReturnsReadOnlyGovernanceSignals() throws Exception {
        FailedEventMessage pending = failedEvent("summary-pending");
        pending.requestReplayApproval(
                "pending summary",
                "ops-user",
                Instant.parse("2026-05-11T08:00:00Z")
        );
        FailedEventMessage approved = failedEvent("summary-approved");
        approved.requestReplayApproval(
                "approved summary",
                "ops-user",
                Instant.parse("2026-05-11T08:05:00Z")
        );
        approved.approveReplay("sre-user", "approved", Instant.parse("2026-05-11T08:10:00Z"));
        FailedEventMessage rejected = failedEvent("summary-rejected");
        rejected.requestReplayApproval(
                "rejected summary",
                "ops-user",
                Instant.parse("2026-05-11T08:15:00Z")
        );
        rejected.rejectReplay("sre-user", "rejected", Instant.parse("2026-05-11T08:20:00Z"));
        FailedEventMessage replayed = failedEvent("summary-replayed");
        replayed.markReplayed("replayed-event", Instant.parse("2026-05-11T08:25:00Z"));
        failedEventMessageRepository.save(pending);
        failedEventMessageRepository.save(approved);
        failedEventMessageRepository.save(rejected);
        failedEventMessageRepository.save(replayed);

        mockMvc.perform(get("/api/v1/failed-events/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sampledAt").exists())
                .andExpect(jsonPath("$.totalFailedEvents").value(4))
                .andExpect(jsonPath("$.pendingReplayApprovals").value(1))
                .andExpect(jsonPath("$.approvedReplayApprovals").value(1))
                .andExpect(jsonPath("$.rejectedReplayApprovals").value(1))
                .andExpect(jsonPath("$.latestFailedAt").exists())
                .andExpect(jsonPath("$.latestApprovalAt").value("2026-05-11T08:20:00Z"))
                .andExpect(jsonPath("$.replayBacklog").value(3));
    }

    private FailedEventMessage failedEvent(String messageId) {
        return FailedEventMessage.record(
                messageId,
                "event-" + messageId,
                "OrderNotificationFailed",
                "ORDER",
                messageId,
                "order-platform.outbox.events",
                "order-platform.outbox.events.dlq",
                "summary integration test",
                "{\"messageId\":\"%s\"}".formatted(messageId)
        );
    }

    private void deleteFailedEventData() {
        failedEventReplayApprovalHistoryRepository.deleteAll();
        failedEventManagementHistoryRepository.deleteAll();
        failedEventReplayAttemptRepository.deleteAll();
        failedEventMessageRepository.deleteAll();
    }
}
