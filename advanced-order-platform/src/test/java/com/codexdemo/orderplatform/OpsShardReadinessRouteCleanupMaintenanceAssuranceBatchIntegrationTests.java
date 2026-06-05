package com.codexdemo.orderplatform;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "order.expiration.enabled=false",
        "outbox.publisher.enabled=false"
})
@AutoConfigureMockMvc
class OpsShardReadinessRouteCleanupMaintenanceAssuranceBatchIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void routeCleanupMaintenanceConsumerGatePacketReturnsPacketSources()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-consumer-gate-packet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v547"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-consumer-gate-packet"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-consumer-gate-packet.v1"))
                .andExpect(jsonPath("$.itemCount").value(4))
                .andExpect(jsonPath("$.items[0].name").value("contract-freeze"))
                .andExpect(jsonPath("$.items[3].name").value("runtime-boundary"))
                .andExpect(jsonPath("$.checks[3]").value("consumer-gate-packet-source-count-4"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
