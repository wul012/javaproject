package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceCloseoutServiceTests {

    @Test
    void closesRouteCleanupMaintenanceEvidenceRun() {
        OpsShardReadinessRouteCleanupMaintenanceCloseoutResponse closeout = service().closeout();

        assertThat(closeout.version()).isEqualTo("Java v487");
        assertThat(closeout.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/route-cleanup-maintenance-closeout");
        assertThat(closeout.profile()).isEqualTo(
                "java-shard-readiness-route-cleanup-maintenance-closeout.v1");
        assertThat(closeout.sourcePlan()).isEqualTo("Node v549");
        assertThat(closeout.checkedReportCount()).isEqualTo(8);
        assertThat(closeout.segmentCount()).isEqualTo(6);
        assertThat(closeout.archiveArtifactCount()).isEqualTo(7);
        assertThat(closeout.checks())
                .extracting(OpsShardReadinessRouteCleanupMaintenanceCloseoutResponse.CloseoutCheck::name)
                .containsExactly(
                        "segment-catalog",
                        "continuity",
                        "latest-sibling-report",
                        "handoff-pair-audit",
                        "boundary-drift",
                        "source-plan-alignment",
                        "test-budget-plan",
                        "archive-manifest"
                );
        assertThat(closeout.checks()).allSatisfy(check -> assertThat(check.status()).isEqualTo("passed"));
        assertThat(closeout.status()).isEqualTo("passed");
    }

    private OpsShardReadinessRouteCleanupMaintenanceCloseoutService service() {
        return new OpsShardReadinessRouteCleanupMaintenanceCloseoutService(
                new OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogService(),
                new OpsShardReadinessRouteCleanupMaintenanceContinuityService(),
                new OpsShardReadinessRouteCleanupMaintenanceLatestSiblingService(),
                new OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditService(),
                new OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftService(),
                new OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentService(
                        new OpsShardReadinessRouteCleanupSourcePlanAlignmentService()
                ),
                new OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanService(),
                new OpsShardReadinessRouteCleanupMaintenanceArchiveManifestService()
        );
    }
}
