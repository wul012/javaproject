package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexServiceTests {

  @Test
  void buildsRouteTopologyIndexForMaintenanceUpkeepRoutes() {
    OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexResponse index =
        new OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexService().index();

    assertThat(index.version()).isEqualTo("Java v495");
    assertThat(index.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-maintenance-route-topology-index");
    assertThat(index.profile())
        .isEqualTo("java-shard-readiness-route-cleanup-maintenance-route-topology-index.v1");
    assertThat(index.routeCount()).isEqualTo(9);
    assertThat(index.firstRouteVersion()).isEqualTo(472);
    assertThat(index.latestRouteVersion()).isEqualTo(488);
    assertThat(index.routes().getFirst().itemName()).isEqualTo("segment-catalog");
    assertThat(index.routes().getFirst().previousEndpoint()).isEqualTo("none");
    assertThat(index.routes().getFirst().nextEndpoint())
        .isEqualTo(OpsShardReadinessRouteCleanupMaintenanceContinuityService.ENDPOINT);
    assertThat(index.routes().getLast().itemName()).isEqualTo("closeout");
    assertThat(index.routes().getLast().nextEndpoint()).isEqualTo("none");
    assertThat(index.routes())
        .allSatisfy(
            route -> {
              assertThat(route.endpoint()).startsWith("/api/v1/ops/shard-readiness");
              assertThat(route.sourceEvidencePath()).endsWith(".json");
            });
    assertThat(index.checks()).contains("topology-index-remains-read-only");
    assertThat(index.status()).isEqualTo("passed");
  }
}
