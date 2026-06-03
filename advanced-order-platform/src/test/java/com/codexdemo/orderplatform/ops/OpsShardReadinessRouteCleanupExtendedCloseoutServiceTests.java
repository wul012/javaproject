package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupExtendedCloseoutServiceTests {

    @Test
    void closesExtendedRouteCleanupHandoffRun() {
        OpsShardReadinessRouteCleanupExtendedCloseoutResponse closeout =
                new OpsShardReadinessRouteCleanupExtendedCloseoutService(
                        handoffBundleService(),
                        consumerChecklistService(),
                        new OpsShardReadinessRouteCleanupFinalDigestService(),
                        new OpsShardReadinessRouteCleanupContinuityReportService(
                                new OpsShardReadinessRouteCleanupEndpointManifestService(),
                                new OpsShardReadinessRouteCleanupPhaseSummaryService()
                        )
                ).closeout();

        int latestVersion = OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion();

        assertThat(latestVersion).isGreaterThanOrEqualTo(365);
        assertThat(closeout.project()).isEqualTo("advanced-order-platform");
        assertThat(closeout.version()).isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(closeout.readOnly()).isTrue();
        assertThat(closeout.executionAllowed()).isFalse();
        assertThat(closeout.closeoutEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-extended-closeout");
        assertThat(closeout.closeoutProfile()).isEqualTo("java-shard-readiness-route-cleanup-extended-closeout.v1");
        assertThat(closeout.firstExtendedVersion()).isEqualTo(346);
        assertThat(closeout.latestVersion()).isEqualTo(latestVersion);
        assertThat(closeout.extendedVersionCount()).isEqualTo(latestVersion - 345);
        assertThat(closeout.extendedVersionCount()).isEqualTo(20);
        assertThat(closeout.evidenceCount()).isEqualTo(4);
        assertThat(closeout.evidence()).allSatisfy(item -> assertThat(item).isNotBlank());
        assertThat(closeout.decision()).isEqualTo("extended-closeout-ready-for-final-route");
        assertThat(closeout.status()).isEqualTo("passed");
    }

    private OpsShardReadinessRouteCleanupConsumerChecklistService consumerChecklistService() {
        return new OpsShardReadinessRouteCleanupConsumerChecklistService(
                consumerPacketService(),
                new OpsShardReadinessRouteCleanupContinuityReportService(
                        new OpsShardReadinessRouteCleanupEndpointManifestService(),
                        new OpsShardReadinessRouteCleanupPhaseSummaryService()
                )
        );
    }

    private OpsShardReadinessRouteCleanupHandoffBundleService handoffBundleService() {
        return new OpsShardReadinessRouteCleanupHandoffBundleService(
                consumerPacketService(),
                new OpsShardReadinessRouteCleanupCiEvidenceService(),
                new OpsShardReadinessRouteCleanupRegressionGuardService(
                        new OpsShardReadinessRouteCleanupEndpointManifestService(),
                        new OpsShardReadinessRouteCleanupCiEvidenceService()
                )
        );
    }

    private OpsShardReadinessRouteCleanupConsumerPacketService consumerPacketService() {
        return new OpsShardReadinessRouteCleanupConsumerPacketService(
                new OpsShardReadinessRouteCleanupReadOnlyGateService(
                        releaseHandoffService(),
                        new OpsShardReadinessRouteCleanupOperatorRunbookService()
                ),
                new OpsShardReadinessRouteCleanupArchiveVerificationService(
                        new OpsShardReadinessRouteCleanupArchivePlanService(),
                        suiteCloseoutService()
                )
        );
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
