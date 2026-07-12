package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupRegressionGuardServiceTests {

  @Test
  void buildsRegressionGuardFromManifestAndCiEvidence() {
    OpsShardReadinessRouteCleanupRegressionGuardResponse guard =
        new OpsShardReadinessRouteCleanupRegressionGuardService(
                new OpsShardReadinessRouteCleanupEndpointManifestService(),
                new OpsShardReadinessRouteCleanupCiEvidenceService())
            .guard();

    assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion())
        .isGreaterThanOrEqualTo(354);
    assertThat(guard.project()).isEqualTo("advanced-order-platform");
    assertThat(guard.version())
        .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
    assertThat(guard.readOnly()).isTrue();
    assertThat(guard.executionAllowed()).isFalse();
    assertThat(guard.guardEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-regression-guard");
    assertThat(guard.guardProfile())
        .isEqualTo("java-shard-readiness-route-cleanup-regression-guard.v1");
    assertThat(guard.guardCount()).isEqualTo(4);
    assertThat(guard.guards())
        .extracting(OpsShardReadinessRouteCleanupRegressionGuardResponse.GuardCheck::name)
        .containsExactly(
            "version-continuity",
            "read-only-boundary",
            "manifest-core-endpoints",
            "ci-requirements-present");
    assertThat(guard.guards())
        .allSatisfy(
            check -> {
              assertThat(check.passed()).isTrue();
              assertThat(check.status()).isEqualTo("passed");
            });
    assertThat(guard.status()).isEqualTo("passed");
  }
}
