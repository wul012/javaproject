package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupCompletionAuditDigestServiceTests {

  @Test
  void buildsCompletionAuditDigestFromPostCompletionEvidence() {
    OpsShardReadinessRouteCleanupCompletionAuditDigestResponse digest =
        OpsShardReadinessRouteCleanupPostCompletionServiceFixtures.completionAuditDigestService()
            .digest();

    assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion())
        .isGreaterThanOrEqualTo(405);
    assertThat(digest.project()).isEqualTo("advanced-order-platform");
    assertThat(digest.version())
        .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
    assertThat(digest.readOnly()).isTrue();
    assertThat(digest.executionAllowed()).isFalse();
    assertThat(digest.completionAuditDigestEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-completion-audit-digest");
    assertThat(digest.completionAuditDigestProfile())
        .isEqualTo("java-shard-readiness-route-cleanup-completion-audit-digest.v1");
    assertThat(digest.digestAlgorithm()).isEqualTo("SHA-256");
    assertThat(digest.digestValue()).matches("[0-9a-f]{64}");
    assertThat(digest.sourceCount()).isEqualTo(3);
    assertThat(digest.sources())
        .contains(
            "/api/v1/ops/shard-readiness/route-cleanup-fixture-coverage-index",
            "/api/v1/ops/shard-readiness/route-cleanup-tag-manifest",
            "/api/v1/ops/shard-readiness/route-cleanup-archive-handoff-receipt");
    assertThat(digest.status()).isEqualTo("passed");
  }
}
