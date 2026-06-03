package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupDigestServiceTests {

    @Test
    void buildsStableDigestForRouteCleanupSuiteSources() {
        OpsShardReadinessRouteCleanupDigestResponse digest =
                new OpsShardReadinessRouteCleanupDigestService().digest();

        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion()).isGreaterThanOrEqualTo(335);
        assertThat(digest.project()).isEqualTo("advanced-order-platform");
        assertThat(digest.version())
                .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(digest.readOnly()).isTrue();
        assertThat(digest.executionAllowed()).isFalse();
        assertThat(digest.digestEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-digest");
        assertThat(digest.digestProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-digest.v1");
        assertThat(digest.digestAlgorithm()).isEqualTo("SHA-256");
        assertThat(digest.digestInput()).contains("Java v", "passed", "/route-cleanup-archive-plan");
        assertThat(digest.digestValue()).matches("[0-9a-f]{64}");
        assertThat(digest.sourceCount()).isEqualTo(5);
        assertThat(digest.sourceEndpoints())
                .contains(
                        "/api/v1/ops/shard-readiness/route-cleanup-evidence-catalog",
                        "/api/v1/ops/shard-readiness/route-cleanup-phase-summary",
                        "/api/v1/ops/shard-readiness/route-cleanup-boundary-matrix",
                        "/api/v1/ops/shard-readiness/route-cleanup-handoff-checklist",
                        "/api/v1/ops/shard-readiness/route-cleanup-archive-plan"
                );
        assertThat(digest.status()).isEqualTo("passed");
    }
}
