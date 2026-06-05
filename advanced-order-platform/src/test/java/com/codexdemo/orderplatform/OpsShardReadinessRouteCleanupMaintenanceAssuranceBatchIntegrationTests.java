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

    @Test
    void routeCleanupMaintenanceArchiveVerifierSummaryReturnsArchiveItems()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-archive-verifier-summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v549"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-archive-verifier-summary"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-archive-verifier-summary.v1"))
                .andExpect(jsonPath("$.itemCount").value(4))
                .andExpect(jsonPath("$.items[0].name").value("json-output"))
                .andExpect(jsonPath("$.items[3].name").value("route-boundary"))
                .andExpect(jsonPath("$.checks[4]").value("archive-verifier-summary-does-not-touch-files"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceCiBudgetLedgerReturnsBudgetItems()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-ci-budget-ledger"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v551"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-ci-budget-ledger"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-ci-budget-ledger.v1"))
                .andExpect(jsonPath("$.itemCount").value(4))
                .andExpect(jsonPath("$.items[0].name").value("focused-service-tests"))
                .andExpect(jsonPath("$.items[3].name").value("full-suite-final-gate"))
                .andExpect(jsonPath("$.checks[4]").value("ci-budget-ledger-does-not-run-ci"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceRouteInventoryDigestReturnsInventoryItems()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-route-inventory-digest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v553"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-route-inventory-digest"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-route-inventory-digest.v1"))
                .andExpect(jsonPath("$.itemCount").value(4))
                .andExpect(jsonPath("$.items[0].name").value("sustainment-batch-routes"))
                .andExpect(jsonPath("$.items[3].name").value("read-only-inventory"))
                .andExpect(jsonPath("$.checks[4]").value("route-inventory-digest-does-not-scan-runtime"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
