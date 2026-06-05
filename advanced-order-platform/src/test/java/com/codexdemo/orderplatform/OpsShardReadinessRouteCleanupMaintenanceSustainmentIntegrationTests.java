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

    @Test
    void routeCleanupMaintenanceOwnershipRegisterReturnsOwners()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-ownership-register"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v518"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-ownership-register"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-ownership-register.v1"))
                .andExpect(jsonPath("$.ownerEntryCount").value(9))
                .andExpect(jsonPath("$.distinctOwnerCount").value(9))
                .andExpect(jsonPath("$.owners[4].owner").value("runtime-boundary-reviewer"))
                .andExpect(jsonPath("$.owners[4].boundary").value("read-only-boundary"))
                .andExpect(jsonPath("$.checks[4]").value("ownership-register-remains-read-only"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceRiskLedgerReturnsMitigatedRisks()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-risk-ledger"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v520"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-risk-ledger"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-risk-ledger.v1"))
                .andExpect(jsonPath("$.riskCount").value(5))
                .andExpect(jsonPath("$.highRiskCount").value(0))
                .andExpect(jsonPath("$.mitigatedRiskCount").value(5))
                .andExpect(jsonPath("$.risks[2].name").value("boundary-drift"))
                .andExpect(jsonPath("$.risks[2].status").value("mitigated"))
                .andExpect(jsonPath("$.checks[4]").value("risk-ledger-remains-read-only"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

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
