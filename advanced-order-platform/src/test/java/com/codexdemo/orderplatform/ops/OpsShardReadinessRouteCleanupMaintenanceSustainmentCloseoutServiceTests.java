package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutServiceTests {

    @Test
    void buildsCloseoutFromScorecardArchiveAndTestEvidence() {
        OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutResponse closeout =
                service().closeout();

        assertThat(closeout.version()).isEqualTo("Java v532");
        assertThat(closeout.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/route-cleanup-maintenance-sustainment-closeout");
        assertThat(closeout.profile()).isEqualTo(
                "java-shard-readiness-route-cleanup-maintenance-sustainment-closeout.v1");
        assertThat(closeout.closeoutItemCount()).isEqualTo(5);
        assertThat(closeout.passedItemCount()).isEqualTo(5);
        assertThat(closeout.finalScore()).isEqualTo(100);
        assertThat(closeout.sourcePlan()).isEqualTo("Node v549");
        assertThat(closeout.items())
                .extracting(OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutResponse
                        .CloseoutItem::name)
                .containsExactly(
                        "operations-scorecard",
                        "sustainment-route-split",
                        "archive-retention",
                        "test-evidence-rollup",
                        "read-only-boundary"
                );
        assertThat(closeout.items()).allSatisfy(item -> assertThat(item.status()).isEqualTo("passed"));
        assertThat(closeout.checks()).contains("sustainment-closeout-remains-read-only");
        assertThat(closeout.status()).isEqualTo("passed");
    }

    private OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutService service() {
        OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService handoff =
                new OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService(
                        new OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterService(),
                        new OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService(),
                        new OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowService()
                );
        OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService archive =
                new OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService();
        OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService tests =
                new OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService();
        OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService scorecard =
                new OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService(
                        handoff,
                        new OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapService(),
                        archive,
                        tests
                );
        return new OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutService(
                scorecard,
                archive,
                tests
        );
    }
}
