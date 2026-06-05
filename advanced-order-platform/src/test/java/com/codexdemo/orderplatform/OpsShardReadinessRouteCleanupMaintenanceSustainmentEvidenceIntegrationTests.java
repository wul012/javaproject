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
class OpsShardReadinessRouteCleanupMaintenanceSustainmentEvidenceIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void routeCleanupMaintenanceHandoffAcceptanceDigestReturnsAcceptedSections()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-handoff-acceptance-digest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v522"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-handoff-acceptance-digest"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-handoff-acceptance-digest.v1"))
                .andExpect(jsonPath("$.sectionCount").value(5))
                .andExpect(jsonPath("$.acceptedSectionCount").value(5))
                .andExpect(jsonPath("$.blockedSectionCount").value(0))
                .andExpect(jsonPath("$.sections[1].name").value("risk-closure"))
                .andExpect(jsonPath("$.sections[1].status").value("passed"))
                .andExpect(jsonPath("$.checks[4]").value("handoff-acceptance-digest-remains-read-only"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceDependencyBoundaryMapReturnsBoundaryEntries()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-dependency-boundary-map"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v524"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-dependency-boundary-map"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-dependency-boundary-map.v1"))
                .andExpect(jsonPath("$.boundaryEntryCount").value(9))
                .andExpect(jsonPath("$.forbiddenOperationCount").value(7))
                .andExpect(jsonPath("$.boundaries[4].boundary").value("read-only-boundary"))
                .andExpect(jsonPath("$.boundaries[4].allowedScope").value("read-only-evidence-preview"))
                .andExpect(jsonPath("$.forbiddenOperations[0]").value("write-routing"))
                .andExpect(jsonPath("$.checks[4]").value("dependency-boundary-map-remains-read-only"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceArchiveRetentionCalendarReturnsReviewWindow()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-archive-retention-calendar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v526"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-archive-retention-calendar"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-archive-retention-calendar.v1"))
                .andExpect(jsonPath("$.archiveEntryCount").value(9))
                .andExpect(jsonPath("$.retentionDays").value(365))
                .andExpect(jsonPath("$.nextReviewVersion").value(508))
                .andExpect(jsonPath("$.entries[0].reviewCadence").value("every-20-java-versions"))
                .andExpect(jsonPath("$.checks[4]").value("archive-retention-calendar-remains-read-only"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceTestEvidenceRollupReturnsCoverageEntries()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-test-evidence-rollup"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v528"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-test-evidence-rollup"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-test-evidence-rollup.v1"))
                .andExpect(jsonPath("$.evidenceEntryCount").value(5))
                .andExpect(jsonPath("$.coveredEntryCount").value(5))
                .andExpect(jsonPath("$.entries[3].coverageType").value("contract"))
                .andExpect(jsonPath("$.entries[4].coverageType").value("integration"))
                .andExpect(jsonPath("$.checks[4]").value("test-evidence-rollup-remains-read-only"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceOperationsScorecardReturnsPassedScore()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-operations-scorecard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v530"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-operations-scorecard"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-operations-scorecard.v1"))
                .andExpect(jsonPath("$.score").value(100))
                .andExpect(jsonPath("$.dimensionCount").value(4))
                .andExpect(jsonPath("$.passedDimensionCount").value(4))
                .andExpect(jsonPath("$.dimensions[0].name").value("handoff-acceptance"))
                .andExpect(jsonPath("$.dimensions[3].name").value("test-evidence"))
                .andExpect(jsonPath("$.checks[4]").value("operations-scorecard-remains-read-only"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
