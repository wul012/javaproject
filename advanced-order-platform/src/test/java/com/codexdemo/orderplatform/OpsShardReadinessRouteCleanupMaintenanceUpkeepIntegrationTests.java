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
class OpsShardReadinessRouteCleanupMaintenanceUpkeepIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void routeCleanupMaintenanceUpkeepCatalogReturnsVersionedMaintenanceItems()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-upkeep-catalog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v489"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-upkeep-catalog"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-upkeep-catalog.v1"))
                .andExpect(jsonPath("$.itemCount").value(9))
                .andExpect(jsonPath("$.firstServiceVersion").value(471))
                .andExpect(jsonPath("$.latestRouteVersion").value(488))
                .andExpect(jsonPath("$.items[0].name").value("segment-catalog"))
                .andExpect(jsonPath("$.items[8].name").value("closeout"))
                .andExpect(jsonPath("$.items[8].routeVersion").value(488))
                .andExpect(jsonPath("$.checks[4]").value("upkeep-catalog-remains-read-only"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceConsumerHandoffMatrixReturnsConsumerBoundaries()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-consumer-handoff-matrix"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v491"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-consumer-handoff-matrix"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-consumer-handoff-matrix.v1"))
                .andExpect(jsonPath("$.matrixEntryCount").value(9))
                .andExpect(jsonPath("$.consumerCount").value(9))
                .andExpect(jsonPath("$.forbiddenOperationCount").value(7))
                .andExpect(jsonPath("$.matrix[4].consumer").value("runtime-boundary-reviewer"))
                .andExpect(jsonPath("$.matrix[4].boundary").value("read-only-boundary"))
                .andExpect(jsonPath("$.forbiddenOperations[4]").value("managed-audit-connection"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
