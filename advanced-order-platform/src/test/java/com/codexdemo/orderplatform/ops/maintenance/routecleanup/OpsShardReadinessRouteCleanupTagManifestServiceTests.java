package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupTagManifestServiceTests {

  @Test
  void buildsExpectedTagManifestFromLatestRouteCleanupEvidence() {
    OpsShardReadinessRouteCleanupTagManifestResponse manifest =
        OpsShardReadinessRouteCleanupPostCompletionServiceFixtures.tagManifestService().manifest();

    assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion())
        .isGreaterThanOrEqualTo(393);
    assertThat(manifest.project()).isEqualTo("advanced-order-platform");
    assertThat(manifest.version())
        .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
    assertThat(manifest.readOnly()).isTrue();
    assertThat(manifest.executionAllowed()).isFalse();
    assertThat(manifest.tagManifestEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-tag-manifest");
    assertThat(manifest.tagManifestProfile())
        .isEqualTo("java-shard-readiness-route-cleanup-tag-manifest.v1");
    assertThat(manifest.ciRunAttestationEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-ci-run-attestation");
    assertThat(manifest.tagCount()).isEqualTo(8);
    assertThat(manifest.tags())
        .allSatisfy(
            tag -> {
              assertThat(tag.tagName()).startsWith("v");
              assertThat(tag.tagName()).contains("order-platform-route-cleanup");
              assertThat(tag.status()).isEqualTo("expected");
            });
    assertThat(manifest.tags())
        .extracting(OpsShardReadinessRouteCleanupTagManifestResponse.TagEntry::javaVersion)
        .contains(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion());
    assertThat(manifest.policy()).contains("javaproject");
    assertThat(manifest.status()).isEqualTo("passed");
  }
}
