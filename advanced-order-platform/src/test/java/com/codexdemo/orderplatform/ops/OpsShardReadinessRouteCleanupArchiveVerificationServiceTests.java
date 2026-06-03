package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupArchiveVerificationServiceTests {

    @Test
    void verifiesArchivePlanAndSuiteCloseoutWithoutRuntimeExecution() {
        OpsShardReadinessRouteCleanupArchiveVerificationResponse verification =
                new OpsShardReadinessRouteCleanupArchiveVerificationService(
                        new OpsShardReadinessRouteCleanupArchivePlanService(),
                        suiteCloseoutService()
                ).verification();

        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion()).isGreaterThanOrEqualTo(346);
        assertThat(verification.project()).isEqualTo("advanced-order-platform");
        assertThat(verification.version())
                .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(verification.readOnly()).isTrue();
        assertThat(verification.executionAllowed()).isFalse();
        assertThat(verification.verificationEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-archive-verification");
        assertThat(verification.verificationProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-archive-verification.v1");
        assertThat(verification.archivePlanEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-archive-plan");
        assertThat(verification.suiteCloseoutEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-suite-closeout");
        assertThat(verification.checkCount()).isEqualTo(4);
        assertThat(verification.checks())
                .extracting(OpsShardReadinessRouteCleanupArchiveVerificationResponse.VerificationCheck::name)
                .containsExactly(
                        "archive-plan-passed",
                        "archive-artifacts-required",
                        "closeout-passed",
                        "digest-present"
                );
        assertThat(verification.checks())
                .allSatisfy(check -> {
                    assertThat(check.passed()).isTrue();
                    assertThat(check.status()).isEqualTo("passed");
                });
        assertThat(verification.status()).isEqualTo("passed");
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
