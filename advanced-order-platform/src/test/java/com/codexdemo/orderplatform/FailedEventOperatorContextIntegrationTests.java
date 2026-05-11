package com.codexdemo.orderplatform;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codexdemo.orderplatform.notification.FailedEventManagementHistoryRepository;
import com.codexdemo.orderplatform.notification.FailedEventManagementStatus;
import com.codexdemo.orderplatform.notification.FailedEventMessage;
import com.codexdemo.orderplatform.notification.FailedEventMessageRepository;
import com.codexdemo.orderplatform.notification.FailedEventReplayApprovalHistoryAction;
import com.codexdemo.orderplatform.notification.FailedEventReplayApprovalHistoryRepository;
import com.codexdemo.orderplatform.notification.FailedEventReplayAttemptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
        "order.expiration.enabled=false",
        "outbox.publisher.enabled=false"
})
@AutoConfigureMockMvc
class FailedEventOperatorContextIntegrationTests {

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
        failedEventReplayApprovalHistoryRepository.deleteAll();
        failedEventManagementHistoryRepository.deleteAll();
        failedEventReplayAttemptRepository.deleteAll();
        failedEventMessageRepository.deleteAll();
    }

    @Test
    void resolvesOperatorContextFromHeadersForProbeAndManagementMutation() throws Exception {
        FailedEventMessage failedMessage = failedEventMessageRepository.save(failedEventMessage());

        mockMvc.perform(get("/api/v1/failed-events/operator-context")
                        .header("X-Operator-Id", " ops-user ")
                        .header("X-Operator-Role", " sre "))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operatorId").value("ops-user"))
                .andExpect(jsonPath("$.operatorRole").value("SRE"))
                .andExpect(jsonPath("$.allowedRoles").value(containsInAnyOrder("ORDER_SUPPORT", "SRE", "SYSTEM")));

        mockMvc.perform(post("/api/v1/failed-events/management-status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Operator-Id", " ops-user ")
                        .header("X-Operator-Role", " order_support ")
                        .content("""
                                {
                                  "ids": [%d],
                                  "status": "INVESTIGATING",
                                  "note": "operator context v29"
                                }
                                """.formatted(failedMessage.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(FailedEventManagementStatus.INVESTIGATING.name()))
                .andExpect(jsonPath("$.items[0].managedBy").value("ops-user"));

        assertThat(failedEventManagementHistoryRepository
                .findByFailedEventMessageIdOrderByChangedAtDescIdDesc(failedMessage.getId()))
                .singleElement()
                .satisfies(history -> {
                    assertThat(history.getOperatorId()).isEqualTo("ops-user");
                    assertThat(history.getOperatorRole()).isEqualTo("ORDER_SUPPORT");
                    assertThat(history.getNote()).isEqualTo("operator context v29");
                });
    }

    @Test
    void usesSameOperatorContextForReplayApprovalAndRejectsInvalidRoles() throws Exception {
        FailedEventMessage failedMessage = failedEventMessageRepository.save(failedEventMessage());

        mockMvc.perform(get("/api/v1/failed-events/operator-context")
                        .header("X-Operator-Id", "ops-user")
                        .header("X-Operator-Role", "viewer"))
                .andExpect(status().isForbidden());

        mockMvc.perform(post("/api/v1/failed-events/{id}/replay-approval", failedMessage.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Operator-Id", " ops-user ")
                        .header("X-Operator-Role", " sre ")
                        .content("""
                                {
                                  "reason": "operator context approval request"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.replayApprovalRequestedBy").value("ops-user"))
                .andExpect(jsonPath("$.replayApprovalStatus").value("PENDING"));

        assertThat(failedEventReplayApprovalHistoryRepository
                .findByFailedEventMessageIdOrderByChangedAtDescIdDesc(failedMessage.getId()))
                .singleElement()
                .satisfies(history -> {
                    assertThat(history.getAction()).isEqualTo(FailedEventReplayApprovalHistoryAction.REQUESTED);
                    assertThat(history.getOperatorId()).isEqualTo("ops-user");
                    assertThat(history.getOperatorRole()).isEqualTo("SRE");
                });
    }

    private FailedEventMessage failedEventMessage() {
        return FailedEventMessage.record(
                "v29-message-001",
                "29292929-2929-2929-2929-292929292901",
                "OrderCreated",
                "ORDER",
                "2901",
                "order-platform.outbox.events",
                "order-platform.outbox.events.dlq",
                "listener rejected event",
                "{\"aggregateId\":\"2901\"}"
        );
    }
}
