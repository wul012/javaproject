package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceSustainmentBatchServiceTests {

    @Test
    void buildsContractFreezeFromNodeV549Plan() {
        OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse freeze =
                new OpsShardReadinessRouteCleanupMaintenanceContractFreezeService().freeze();

        assertThat(freeze.version()).isEqualTo("Java v537");
        assertThat(freeze.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/route-cleanup-maintenance-contract-freeze");
        assertThat(freeze.profile()).isEqualTo(
                "java-shard-readiness-route-cleanup-maintenance-contract-freeze.v1");
        assertThat(freeze.itemCount()).isEqualTo(4);
        assertThat(freeze.passedItemCount()).isEqualTo(4);
        assertThat(freeze.sourcePlan()).isEqualTo("Node v549");
        assertThat(freeze.items())
                .extracting(OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse
                        .ReviewItem::name)
                .containsExactly(
                        "read-only-integration-v1",
                        "shard-readiness-v1",
                        "runtime-boundary",
                        "source-plan"
                );
        assertThat(freeze.checks()).contains(
                "read-only-integration-v1-fields-frozen",
                "shard-readiness-v1-fields-frozen",
                "sustainment-review-remains-read-only"
        );
        assertThat(freeze.status()).isEqualTo("passed");
    }
}
