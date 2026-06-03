package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupSuiteCloseoutServiceTests {

    @Test
    void closesOutTwentyVersionRouteCleanupHandoffSuite() {
        OpsShardReadinessRouteCleanupSuiteCloseoutResponse closeout =
                new OpsShardReadinessRouteCleanupSuiteCloseoutService(
                        releaseHandoffService(),
                        new OpsShardReadinessRouteCleanupReadOnlyGateService(
                                releaseHandoffService(),
                                new OpsShardReadinessRouteCleanupOperatorRunbookService()
                        ),
                        new OpsShardReadinessRouteCleanupDigestService()
                ).closeout();

        assertThat(closeout.project()).isEqualTo("advanced-order-platform");
        int latestVersion = OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion();

        assertThat(closeout.version())
                .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(closeout.readOnly()).isTrue();
        assertThat(closeout.executionAllowed()).isFalse();
        assertThat(closeout.closeoutEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-suite-closeout");
        assertThat(closeout.closeoutProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-suite-closeout.v1");
        assertThat(closeout.firstSuiteVersion()).isEqualTo(326);
        assertThat(closeout.lastSuiteVersion()).isEqualTo(latestVersion);
        assertThat(closeout.suiteVersionCount()).isEqualTo(latestVersion - 325);
        assertThat(closeout.publishedEndpointCount()).isEqualTo(11);
        assertThat(closeout.publishedEndpoints())
                .contains(
                        "/api/v1/ops/shard-readiness/route-cleanup-evidence-catalog",
                        "/api/v1/ops/shard-readiness/route-cleanup-release-handoff",
                        "/api/v1/ops/shard-readiness/route-cleanup-read-only-gate",
                        "/api/v1/ops/shard-readiness/route-cleanup-suite-closeout"
                );
        assertThat(closeout.digestValue()).matches("[0-9a-f]{64}");
        assertThat(closeout.decision()).isEqualTo("closeout-ready-for-read-only-consumer-handoff");
        assertThat(closeout.status()).isEqualTo("passed");
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
