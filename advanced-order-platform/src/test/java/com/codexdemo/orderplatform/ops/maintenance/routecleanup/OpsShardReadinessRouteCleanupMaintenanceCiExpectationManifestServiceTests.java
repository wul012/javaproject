package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestServiceTests {

  @Test
  void buildsCiExpectationManifestForMaintenanceUpkeepItems() {
    OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestResponse manifest =
        new OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService().manifest();

    assertThat(manifest.version()).isEqualTo("Java v493");
    assertThat(manifest.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-maintenance-ci-expectation-manifest");
    assertThat(manifest.profile())
        .isEqualTo("java-shard-readiness-route-cleanup-maintenance-ci-expectation-manifest.v1");
    assertThat(manifest.expectationCount()).isEqualTo(9);
    assertThat(manifest.laneCount()).isEqualTo(4);
    assertThat(manifest.startsJavaService()).isFalse();
    assertThat(manifest.startsMiniKvService()).isFalse();
    assertThat(manifest.expectations().getFirst().focusedTestClass())
        .isEqualTo("OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogServiceTests");
    assertThat(manifest.expectations().get(2).focusedTestClass())
        .isEqualTo("OpsShardReadinessRouteCleanupMaintenanceLatestSiblingServiceTests");
    assertThat(manifest.expectations())
        .allSatisfy(
            expectation -> {
              assertThat(expectation.routeRegressionClass())
                  .isEqualTo("OpsShardReadinessRouteCleanupMaintenanceIntegrationTests");
              assertThat(expectation.fullRegressionCommand()).isEqualTo("mvn -q test");
              assertThat(expectation.githubActionsJob()).contains("non-Docker regression");
            });
    assertThat(manifest.checks()).contains("ci-manifest-does-not-start-upstreams");
    assertThat(manifest.status()).isEqualTo("passed");
  }
}
