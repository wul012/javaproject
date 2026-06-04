package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupPostCompletionCloseoutServiceTests {

    @Test
    void closesPostCompletionRouteCleanupRunFromV389() {
        OpsShardReadinessRouteCleanupPostCompletionCloseoutResponse closeout =
                OpsShardReadinessRouteCleanupPostCompletionServiceFixtures.postCompletionCloseoutService().closeout();

        int latest = OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion();

        assertThat(latest).isGreaterThanOrEqualTo(407);
        assertThat(closeout.project()).isEqualTo("advanced-order-platform");
        assertThat(closeout.version()).isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(closeout.readOnly()).isTrue();
        assertThat(closeout.executionAllowed()).isFalse();
        assertThat(closeout.postCompletionCloseoutEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-post-completion-closeout");
        assertThat(closeout.postCompletionCloseoutProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-post-completion-closeout.v1");
        assertThat(closeout.firstVersion()).isEqualTo(389);
        assertThat(closeout.latestVersion()).isEqualTo(latest);
        assertThat(closeout.versionCount()).isEqualTo(latest - 388);
        assertThat(closeout.versionCount()).isGreaterThanOrEqualTo(19);
        assertThat(closeout.completionAuditDigestEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-completion-audit-digest");
        assertThat(closeout.maintenanceBoundaryReportEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-maintenance-boundary-report");
        assertThat(closeout.closeoutEvidenceCount()).isEqualTo(6);
        assertThat(closeout.closeoutEvidence()).anySatisfy(item -> assertThat(item).contains("completion-audit-digest"));
        assertThat(closeout.decision()).isEqualTo("post-completion-closeout-ready-for-route");
        assertThat(closeout.status()).isEqualTo("passed");
    }
}
