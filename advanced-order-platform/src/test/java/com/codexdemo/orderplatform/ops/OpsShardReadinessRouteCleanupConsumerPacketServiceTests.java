package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupConsumerPacketServiceTests {

    @Test
    void buildsReadOnlyConsumerPacketFromGateAndArchiveVerification() {
        OpsShardReadinessRouteCleanupConsumerPacketResponse packet =
                new OpsShardReadinessRouteCleanupConsumerPacketService(
                        new OpsShardReadinessRouteCleanupReadOnlyGateService(
                                releaseHandoffService(),
                                new OpsShardReadinessRouteCleanupOperatorRunbookService()
                        ),
                        new OpsShardReadinessRouteCleanupArchiveVerificationService(
                                new OpsShardReadinessRouteCleanupArchivePlanService(),
                                suiteCloseoutService()
                        )
                ).packet();

        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion()).isGreaterThanOrEqualTo(348);
        assertThat(packet.project()).isEqualTo("advanced-order-platform");
        assertThat(packet.version())
                .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(packet.readOnly()).isTrue();
        assertThat(packet.executionAllowed()).isFalse();
        assertThat(packet.packetEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-consumer-packet");
        assertThat(packet.packetProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-consumer-packet.v1");
        assertThat(packet.endpointCount()).isEqualTo(4);
        assertThat(packet.endpoints())
                .contains(
                        "/api/v1/ops/shard-readiness/route-cleanup-release-handoff",
                        "/api/v1/ops/shard-readiness/route-cleanup-read-only-gate",
                        "/api/v1/ops/shard-readiness/route-cleanup-archive-verification",
                        "/api/v1/ops/shard-readiness/route-cleanup-suite-closeout"
                );
        assertThat(packet.blockedOperations())
                .contains("write-routing", "credential-value-read", "raw-endpoint-parse");
        assertThat(packet.decision()).isEqualTo("consumer-may-read-handoff-evidence");
        assertThat(packet.status()).isEqualTo("passed");
    }

    private OpsShardReadinessRouteCleanupSuiteCloseoutService suiteCloseoutService() {
        return new OpsShardReadinessRouteCleanupSuiteCloseoutService(
                releaseHandoffService(),
                new OpsShardReadinessRouteCleanupReadOnlyGateService(
                        releaseHandoffService(),
                        new OpsShardReadinessRouteCleanupOperatorRunbookService()
                ),
                new OpsShardReadinessRouteCleanupDigestService()
        );
    }

    private OpsShardReadinessRouteCleanupReleaseHandoffService releaseHandoffService() {
        return new OpsShardReadinessRouteCleanupReleaseHandoffService(
                new OpsShardReadinessRouteCleanupHandoffChecklistService(
                        new OpsShardReadinessRouteCleanupPhaseSummaryService(),
                        new OpsShardReadinessRouteCleanupBoundaryMatrixService()
                ),
                new OpsShardReadinessRouteCleanupArchivePlanService(),
                new OpsShardReadinessRouteCleanupDigestService(),
                new OpsShardReadinessRouteCleanupSourcePlanAlignmentService()
        );
    }
}
