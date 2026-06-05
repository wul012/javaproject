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
class OpsShardReadinessRouteCleanupMaintenanceSustainmentIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void routeCleanupMaintenanceReleaseChecklistReturnsReleaseReviewItems()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-release-checklist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v512"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-release-checklist"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-release-checklist.v1"))
                .andExpect(jsonPath("$.checklistItemCount").value(5))
                .andExpect(jsonPath("$.acceptedItemCount").value(5))
                .andExpect(jsonPath("$.items[3].evidence").value("Node v549"))
                .andExpect(jsonPath("$.checks[4]").value("release-checklist-remains-read-only"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceRemediationQueueReturnsStandbyPreview()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-remediation-queue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v514"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-remediation-queue"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-remediation-queue.v1"))
                .andExpect(jsonPath("$.queueItemCount").value(4))
                .andExpect(jsonPath("$.standbyItemCount").value(4))
                .andExpect(jsonPath("$.blockedItemCount").value(0))
                .andExpect(jsonPath("$.items[0].status").value("standby"))
                .andExpect(jsonPath("$.checks[3]").value("remediation-does-not-execute-actions"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceFreshnessWindowReturnsNoStaleEvidence()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-freshness-window"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v516"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-freshness-window"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-freshness-window.v1"))
                .andExpect(jsonPath("$.evidenceCount").value(9))
                .andExpect(jsonPath("$.maxVersionLag").value(20))
                .andExpect(jsonPath("$.staleEvidenceCount").value(0))
                .andExpect(jsonPath("$.entries[0].versionLag").value(16))
                .andExpect(jsonPath("$.entries[8].versionLag").value(0))
                .andExpect(jsonPath("$.checks[4]").value("freshness-window-remains-read-only"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
