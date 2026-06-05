package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceContinuityServiceTests {

    @Test
    void verifiesRouteCleanupEvidenceContinuityAcrossSplitSegments() {
        OpsShardReadinessRouteCleanupMaintenanceContinuityResponse continuity =
                new OpsShardReadinessRouteCleanupMaintenanceContinuityService().continuity();

        assertThat(continuity.project()).isEqualTo("advanced-order-platform");
        assertThat(continuity.version()).isEqualTo("Java v473");
        assertThat(continuity.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/route-cleanup-maintenance-continuity");
        assertThat(continuity.profile()).isEqualTo(
                "java-shard-readiness-route-cleanup-maintenance-continuity.v1");
        assertThat(continuity.firstJavaVersion()).isEqualTo(306);
        assertThat(continuity.latestJavaVersion()).isEqualTo(408);
        assertThat(continuity.expectedEntryCount()).isEqualTo(103);
        assertThat(continuity.actualEntryCount()).isEqualTo(103);
        assertThat(continuity.segmentCount()).isEqualTo(6);
        assertThat(continuity.gapCount()).isZero();
        assertThat(continuity.checks())
                .contains(
                        "segment-boundaries-are-contiguous",
                        "all-entries-remain-read-only"
                );
        assertThat(continuity.status()).isEqualTo("passed");
    }
}
