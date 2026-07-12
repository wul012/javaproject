package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupEndpointManifestServiceTests {

  @Test
  void buildsEndpointManifestFromRouteCleanupConstants() {
    OpsShardReadinessRouteCleanupEndpointManifestResponse manifest =
        new OpsShardReadinessRouteCleanupEndpointManifestService().manifest();

    assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion())
        .isGreaterThanOrEqualTo(352);
    assertThat(manifest.project()).isEqualTo("advanced-order-platform");
    assertThat(manifest.version())
        .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
    assertThat(manifest.readOnly()).isTrue();
    assertThat(manifest.executionAllowed()).isFalse();
    assertThat(manifest.manifestEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-endpoint-manifest");
    assertThat(manifest.manifestProfile())
        .isEqualTo("java-shard-readiness-route-cleanup-endpoint-manifest.v1");
    assertThat(manifest.endpointCount()).isEqualTo(manifest.endpoints().size());
    assertThat(manifest.endpointCount()).isGreaterThanOrEqualTo(14);
    assertThat(manifest.endpoints())
        .extracting(OpsShardReadinessRouteCleanupEndpointManifestResponse.EndpointEntry::endpoint)
        .contains(
            "/api/v1/ops/shard-readiness/route-cleanup-evidence-catalog",
            "/api/v1/ops/shard-readiness/route-cleanup-consumer-packet",
            "/api/v1/ops/shard-readiness/route-cleanup-ci-evidence");
    assertThat(manifest.endpoints())
        .allSatisfy(
            endpoint -> {
              assertThat(endpoint.readOnly()).isTrue();
              assertThat(endpoint.executionAllowed()).isFalse();
              assertThat(endpoint.status()).isEqualTo("passed");
            });
    assertThat(manifest.status()).isEqualTo("passed");
  }
}
