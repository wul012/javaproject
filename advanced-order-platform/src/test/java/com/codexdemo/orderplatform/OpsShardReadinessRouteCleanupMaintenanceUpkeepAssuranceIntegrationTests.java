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
class OpsShardReadinessRouteCleanupMaintenanceUpkeepAssuranceIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void routeCleanupMaintenanceArchiveDigestLedgerReturnsStableDigestEntries()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-archive-digest-ledger"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v499"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-archive-digest-ledger"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-archive-digest-ledger.v1"))
                .andExpect(jsonPath("$.ledgerEntryCount").value(9))
                .andExpect(jsonPath("$.algorithm").value("SHA-256"))
                .andExpect(jsonPath("$.digestLength").value(16))
                .andExpect(jsonPath("$.entries[0].itemName").value("segment-catalog"))
                .andExpect(jsonPath("$.entries[0].digest").value("3fbb01c5c2147916"))
                .andExpect(jsonPath("$.checks[3]").value("ledger-does-not-read-archive-files"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceOperatorReviewPacketReturnsTypedReviewSections()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-operator-review-packet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v501"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-operator-review-packet"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-operator-review-packet.v1"))
                .andExpect(jsonPath("$.sectionCount").value(5))
                .andExpect(jsonPath("$.evidenceItemCount").value(9))
                .andExpect(jsonPath("$.policyCount").value(7))
                .andExpect(jsonPath("$.digestLedgerEntryCount").value(9))
                .andExpect(jsonPath("$.sections[0].name").value("upkeep-catalog"))
                .andExpect(jsonPath("$.sections[4].name").value("archive-digest-ledger"))
                .andExpect(jsonPath("$.checks[4]").value("operator-review-packet-remains-read-only"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceVersionLineageReturnsServiceRoutePairs()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-version-lineage"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v503"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-version-lineage"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-version-lineage.v1"))
                .andExpect(jsonPath("$.pairCount").value(9))
                .andExpect(jsonPath("$.firstServiceVersion").value(471))
                .andExpect(jsonPath("$.latestRouteVersion").value(488))
                .andExpect(jsonPath("$.gapCount").value(0))
                .andExpect(jsonPath("$.pairs[0].routeFollowsService").value(true))
                .andExpect(jsonPath("$.pairs[8].nextServiceVersion").value(-1))
                .andExpect(jsonPath("$.checks[4]").value("version-lineage-remains-read-only"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceReadinessGateReturnsAcceptedChecks()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-readiness-gate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v505"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-readiness-gate"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-readiness-gate.v1"))
                .andExpect(jsonPath("$.gateCheckCount").value(5))
                .andExpect(jsonPath("$.acceptedCheckCount").value(5))
                .andExpect(jsonPath("$.blockedCheckCount").value(0))
                .andExpect(jsonPath("$.firstServiceVersion").value(471))
                .andExpect(jsonPath("$.latestRouteVersion").value(488))
                .andExpect(jsonPath("$.gateChecks[3].name").value("fail-closed-policy"))
                .andExpect(jsonPath("$.gateChecks[3].passed").value(true))
                .andExpect(jsonPath("$.checks[4]").value("readiness-gate-remains-read-only"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceUpkeepCloseoutReturnsFinalEvidenceCloseout()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-upkeep-closeout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v507"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-upkeep-closeout"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-upkeep-closeout.v1"))
                .andExpect(jsonPath("$.sourcePlan").value("Node v549"))
                .andExpect(jsonPath("$.checkedReportCount").value(5))
                .andExpect(jsonPath("$.upkeepItemCount").value(9))
                .andExpect(jsonPath("$.gateCheckCount").value(5))
                .andExpect(jsonPath("$.archiveDigestCount").value(9))
                .andExpect(jsonPath("$.latestRouteVersion").value(488))
                .andExpect(jsonPath("$.checks[0].name").value("upkeep-catalog"))
                .andExpect(jsonPath("$.checks[4].name").value("archive-digest-ledger"))
                .andExpect(jsonPath("$.checks[4].status").value("passed"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
