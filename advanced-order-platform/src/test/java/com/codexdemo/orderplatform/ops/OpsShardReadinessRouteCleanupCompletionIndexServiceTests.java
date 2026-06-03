package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupCompletionIndexServiceTests {

    @Test
    void buildsCompletionIndexForRouteCleanupCloseoutEndpoints() {
        OpsShardReadinessRouteCleanupCompletionIndexResponse index =
                OpsShardReadinessRouteCleanupServiceFixtures.completionIndexService().index();

        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion()).isGreaterThanOrEqualTo(386);
        assertThat(index.project()).isEqualTo("advanced-order-platform");
        assertThat(index.version()).isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(index.readOnly()).isTrue();
        assertThat(index.executionAllowed()).isFalse();
        assertThat(index.completionIndexEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-completion-index");
        assertThat(index.completionIndexProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-completion-index.v1");
        assertThat(index.completionEndpointCount()).isEqualTo(6);
        assertThat(index.completionEndpoints())
                .extracting(OpsShardReadinessRouteCleanupCompletionIndexResponse.CompletionEndpoint::name)
                .containsExactly(
                        "reviewer-packet",
                        "transition-brief",
                        "final-verification",
                        "final-archive-plan",
                        "third-run-closeout",
                        "completion-index"
                );
        assertThat(index.completionEndpoints())
                .allSatisfy(endpoint -> {
                    assertThat(endpoint.readOnly()).isTrue();
                    assertThat(endpoint.executionAllowed()).isFalse();
                    assertThat(endpoint.status()).isEqualTo("passed");
                });
        assertThat(index.statusSignalCount()).isEqualTo(6);
        assertThat(index.statusSignals())
                .extracting(OpsShardReadinessRouteCleanupCompletionIndexResponse.StatusSignal::name)
                .containsExactly(
                        "reviewer-packet",
                        "transition-brief",
                        "final-verification",
                        "final-archive-plan",
                        "third-run-closeout",
                        "version-continuity"
                );
        assertThat(index.decision()).isEqualTo("completion-index-ready-for-route");
        assertThat(index.status()).isEqualTo("passed");
    }
}
