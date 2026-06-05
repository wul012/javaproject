package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardServiceTests {

    @Test
    void buildsOperationsScorecardFromSustainmentServices() {
        OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardResponse scorecard =
                service().scorecard();

        assertThat(scorecard.version()).isEqualTo("Java v530");
        assertThat(scorecard.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/route-cleanup-maintenance-operations-scorecard");
        assertThat(scorecard.profile()).isEqualTo(
                "java-shard-readiness-route-cleanup-maintenance-operations-scorecard.v1");
        assertThat(scorecard.score()).isEqualTo(100);
        assertThat(scorecard.dimensionCount()).isEqualTo(4);
        assertThat(scorecard.passedDimensionCount()).isEqualTo(4);
        assertThat(scorecard.dimensions())
                .extracting(OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardResponse
                        .ScorecardDimension::name)
                .containsExactly(
                        "handoff-acceptance",
                        "dependency-boundary",
                        "archive-retention",
                        "test-evidence"
                );
        assertThat(scorecard.dimensions()).allSatisfy(dimension -> {
            assertThat(dimension.weight()).isEqualTo(25);
            assertThat(dimension.status()).isEqualTo("passed");
        });
        assertThat(scorecard.checks()).contains("operations-scorecard-remains-read-only");
        assertThat(scorecard.status()).isEqualTo("passed");
    }

    private OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService service() {
        OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService handoff =
                new OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService(
                        new OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterService(),
                        new OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService(),
                        new OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowService()
                );
        return new OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService(
                handoff,
                new OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapService(),
                new OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService(),
                new OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService()
        );
    }
}
