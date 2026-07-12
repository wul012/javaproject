package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapServiceTests {

  @Test
  void buildsDependencyBoundaryMapFromUpkeepCatalog() {
    OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapResponse map =
        new OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapService().map();

    assertThat(map.version()).isEqualTo("Java v524");
    assertThat(map.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-maintenance-dependency-boundary-map");
    assertThat(map.profile())
        .isEqualTo("java-shard-readiness-route-cleanup-maintenance-dependency-boundary-map.v1");
    assertThat(map.boundaryEntryCount()).isEqualTo(9);
    assertThat(map.forbiddenOperationCount()).isEqualTo(7);
    assertThat(map.boundaries())
        .extracting(
            OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapResponse.BoundaryEntry
                ::boundary)
        .contains("read-only-boundary", "node-v549-alignment", "maintenance-closeout");
    assertThat(map.boundaries())
        .allSatisfy(
            boundary -> {
              assertThat(boundary.allowedScope()).isEqualTo("read-only-evidence-preview");
              assertThat(boundary.status()).isEqualTo("passed");
            });
    assertThat(map.forbiddenOperations()).contains("write-routing", "managed-audit-connection");
    assertThat(map.checks()).contains("dependency-boundary-map-remains-read-only");
    assertThat(map.status()).isEqualTo("passed");
  }
}
