package com.codexdemo.orderplatform;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codexdemo.orderplatform.notification.FailedEventMessage;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "order.expiration.enabled=false",
        "outbox.publisher.enabled=false"
})
@AutoConfigureMockMvc
class OpsOverviewIntegrationTests extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsOverviewReturnsReadOnlyBusinessSignals() throws Exception {
        FailedEventMessage pendingApproval = FailedEventMessage.record(
                "ops-overview-pending",
                "event-ops-1",
                "OrderNotificationFailed",
                "ORDER",
                "1001",
                "order-platform.outbox.events",
                "order-platform.outbox.events.dlq",
                "v36 pending approval",
                "{\"orderId\":1001}"
        );
        pendingApproval.requestReplayApproval("needs operator review", "ops-user", Instant.now());
        failedEventMessageRepository.save(pendingApproval);
        failedEventMessageRepository.save(FailedEventMessage.record(
                "ops-overview-recorded",
                "event-ops-2",
                "OrderNotificationFailed",
                "ORDER",
                "1002",
                "order-platform.outbox.events",
                "order-platform.outbox.events.dlq",
                "v36 recorded",
                "{\"orderId\":1002}"
        ));

        mockMvc.perform(get("/api/v1/ops/overview"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sampledAt").exists())
                .andExpect(jsonPath("$.application.name").value("advanced-order-platform"))
                .andExpect(jsonPath("$.application.profiles").isArray())
                .andExpect(jsonPath("$.application.startedAt").exists())
                .andExpect(jsonPath("$.application.uptimeSeconds").value(greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.orders.total").value(greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.inventory.items").value(greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.outbox.pending").value(greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.failedEvents.total").value(2))
                .andExpect(jsonPath("$.failedEvents.pendingReplayApprovals").value(1))
                .andExpect(jsonPath("$.failedEvents.latestFailedAt").exists());
    }
}
