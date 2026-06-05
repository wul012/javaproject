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

    @Test
    void buildsGateHandoffWithoutRunningTestsOrStartingUpstreams() {
        OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse handoff =
                new OpsShardReadinessRouteCleanupMaintenanceGateHandoffService().handoff();

        assertThat(handoff.version()).isEqualTo("Java v539");
        assertThat(handoff.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/route-cleanup-maintenance-gate-handoff");
        assertThat(handoff.itemCount()).isEqualTo(4);
        assertThat(handoff.passedItemCount()).isEqualTo(4);
        assertThat(handoff.items())
                .extracting(OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse
                        .ReviewItem::name)
                .containsExactly(
                        "focused-tests",
                        "grouped-route-tests",
                        "build-validation",
                        "smoke-read-only"
                );
        assertThat(handoff.checks()).contains(
                "gate-order-focused-grouped-build-smoke",
                "handoff-does-not-run-tests",
                "handoff-does-not-start-upstreams"
        );
        assertThat(handoff.status()).isEqualTo("passed");
    }

    @Test
    void buildsShardFieldMapWithoutEnablingRouting() {
        OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse fieldMap =
                new OpsShardReadinessRouteCleanupMaintenanceShardFieldMapService().fieldMap();

        assertThat(fieldMap.version()).isEqualTo("Java v541");
        assertThat(fieldMap.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/route-cleanup-maintenance-shard-field-map");
        assertThat(fieldMap.itemCount()).isEqualTo(4);
        assertThat(fieldMap.items())
                .extracting(OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse
                        .ReviewItem::name)
                .containsExactly(
                        "project-version",
                        "read-only-boundary",
                        "shard-shape",
                        "evidence-path"
                );
        assertThat(fieldMap.checks()).contains(
                "shard-readiness-v1-minimal-fields-mapped",
                "field-map-does-not-enable-active-routing"
        );
        assertThat(fieldMap.status()).isEqualTo("passed");
    }
}
