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
class OpsShardReadinessRouteCleanupMaintenanceIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void routeCleanupMaintenanceSegmentCatalogReturnsSplitCatalogSummary()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-segment-catalog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v471"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-segment-catalog"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-segment-catalog.v1"))
                .andExpect(jsonPath("$.segmentCount").value(6))
                .andExpect(jsonPath("$.entryCount").value(103))
                .andExpect(jsonPath("$.segments[0].name").value("latest-sibling"))
                .andExpect(jsonPath("$.segments[0].firstJavaVersion").value(306))
                .andExpect(jsonPath("$.segments[5].name").value("post-completion"))
                .andExpect(jsonPath("$.segments[5].lastJavaVersion").value(408))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceContinuityReturnsNoGapEvidence()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-continuity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v473"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-continuity"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-continuity.v1"))
                .andExpect(jsonPath("$.firstJavaVersion").value(306))
                .andExpect(jsonPath("$.latestJavaVersion").value(408))
                .andExpect(jsonPath("$.expectedEntryCount").value(103))
                .andExpect(jsonPath("$.actualEntryCount").value(103))
                .andExpect(jsonPath("$.gapCount").value(0))
                .andExpect(jsonPath("$.checks[3]").value("segment-boundaries-are-contiguous"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceLatestSiblingReportReturnsSiblingEvidence()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-latest-sibling-report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v475"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-latest-sibling-report"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-latest-sibling-report.v1"))
                .andExpect(jsonPath("$.firstJavaVersion").value(306))
                .andExpect(jsonPath("$.latestJavaVersion").value(317))
                .andExpect(jsonPath("$.entryCount").value(12))
                .andExpect(jsonPath("$.liveSmokeEntryCount").value(6))
                .andExpect(jsonPath("$.sourceNodePlans[0]").value("Node v549"))
                .andExpect(jsonPath("$.checks[2]").value("latest-sibling-source-node-v549-present"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceHandoffPairAuditReturnsPairEvidence()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-handoff-pair-audit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v477"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-handoff-pair-audit"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-handoff-pair-audit.v1"))
                .andExpect(jsonPath("$.handoffEntryCount").value(83))
                .andExpect(jsonPath("$.documentedRouteOnlyEntries[0]").value("handoff-suite-closeout-route"))
                .andExpect(jsonPath("$.documentedRouteOnlyEntries[1]")
                        .value("handoff-suite-completion-certificate-route"))
                .andExpect(jsonPath("$.unpairedServiceEntries.length()").value(0))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceBoundaryDriftReturnsZeroViolationEvidence()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-boundary-drift"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v479"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-boundary-drift"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-boundary-drift.v1"))
                .andExpect(jsonPath("$.scannedEntryCount").value(103))
                .andExpect(jsonPath("$.executionAllowedViolationCount").value(0))
                .andExpect(jsonPath("$.managedAuditViolationCount").value(0))
                .andExpect(jsonPath("$.writeRoutingViolationCount").value(0))
                .andExpect(jsonPath("$.forbiddenOperations[4]").value("managed-audit-connection"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceSourcePlanAlignmentReturnsNodeV549Snapshot()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-source-plan-alignment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v481"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-source-plan-alignment"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-source-plan-alignment.v1"))
                .andExpect(jsonPath("$.sourcePlan").value("Node v549"))
                .andExpect(jsonPath("$.segmentCount").value(6))
                .andExpect(jsonPath("$.upstreamAlignmentCount").value(4))
                .andExpect(jsonPath("$.checks[2]").value("java-and-mini-kv-not-started-by-maintenance-suite"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void routeCleanupMaintenanceTestBudgetPlanReturnsValidationOrder()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/route-cleanup-maintenance-test-budget-plan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v483"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/route-cleanup-maintenance-test-budget-plan"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-route-cleanup-maintenance-test-budget-plan.v1"))
                .andExpect(jsonPath("$.stepCount").value(5))
                .andExpect(jsonPath("$.steps[0].name").value("focused-maintenance-services"))
                .andExpect(jsonPath("$.steps[4].name").value("github-actions-ci"))
                .andExpect(jsonPath("$.steps[0].startsJavaService").value(false))
                .andExpect(jsonPath("$.steps[0].startsMiniKvService").value(false))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
