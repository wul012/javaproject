package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupFinalDigestServiceTests {

  @Test
  void buildsFinalDigestForConsumerHandoffSources() {
    OpsShardReadinessRouteCleanupFinalDigestResponse digest =
        new OpsShardReadinessRouteCleanupFinalDigestService().digest();

    assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion())
        .isGreaterThanOrEqualTo(362);
    assertThat(digest.project()).isEqualTo("advanced-order-platform");
    assertThat(digest.version())
        .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
    assertThat(digest.readOnly()).isTrue();
    assertThat(digest.executionAllowed()).isFalse();
    assertThat(digest.digestEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-final-digest");
    assertThat(digest.digestProfile())
        .isEqualTo("java-shard-readiness-route-cleanup-final-digest.v1");
    assertThat(digest.digestAlgorithm()).isEqualTo("SHA-256");
    assertThat(digest.digestInput()).contains("Java v", "passed", "/route-cleanup-handoff-bundle");
    assertThat(digest.digestValue()).matches("[0-9a-f]{64}");
    assertThat(digest.sourceCount()).isEqualTo(5);
    assertThat(digest.sources())
        .contains(
            "/api/v1/ops/shard-readiness/route-cleanup-handoff-bundle",
            "/api/v1/ops/shard-readiness/route-cleanup-consumer-checklist",
            "/api/v1/ops/shard-readiness/route-cleanup-continuity-report",
            "/api/v1/ops/shard-readiness/route-cleanup-endpoint-manifest",
            "/api/v1/ops/shard-readiness/route-cleanup-regression-guard");
    assertThat(digest.status()).isEqualTo("passed");
  }
}
