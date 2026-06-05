package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceAssuranceBatchServiceTests {

    @Test
    void buildsConsumerGatePacketFromFirstBatchEvidence() {
        OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse packet =
                new OpsShardReadinessRouteCleanupMaintenanceConsumerGatePacketService().packet();

        assertThat(packet.version()).isEqualTo("Java v547");
        assertThat(packet.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/route-cleanup-maintenance-consumer-gate-packet");
        assertThat(packet.profile()).isEqualTo(
                "java-shard-readiness-route-cleanup-maintenance-consumer-gate-packet.v1");
        assertThat(packet.itemCount()).isEqualTo(4);
        assertThat(packet.items())
                .extracting(OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse
                        .ReviewItem::name)
                .containsExactly(
                        "contract-freeze",
                        "field-map",
                        "read-window",
                        "runtime-boundary"
                );
        assertThat(packet.checks()).contains(
                "consumer-gate-packet-source-count-4",
                "consumer-gate-packet-does-not-contact-node"
        );
        assertThat(packet.status()).isEqualTo("passed");
    }

    @Test
    void buildsArchiveVerifierSummaryWithoutTouchingFiles() {
        OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse summary =
                new OpsShardReadinessRouteCleanupMaintenanceArchiveVerifierSummaryService().summary();

        assertThat(summary.version()).isEqualTo("Java v549");
        assertThat(summary.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/route-cleanup-maintenance-archive-verifier-summary");
        assertThat(summary.itemCount()).isEqualTo(4);
        assertThat(summary.items())
                .extracting(OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse
                        .ReviewItem::name)
                .containsExactly(
                        "json-output",
                        "markdown-output",
                        "digest-summary",
                        "route-boundary"
                );
        assertThat(summary.checks()).contains(
                "archive-verifier-summary-items-4",
                "archive-verifier-summary-does-not-touch-files"
        );
        assertThat(summary.status()).isEqualTo("passed");
    }
}
