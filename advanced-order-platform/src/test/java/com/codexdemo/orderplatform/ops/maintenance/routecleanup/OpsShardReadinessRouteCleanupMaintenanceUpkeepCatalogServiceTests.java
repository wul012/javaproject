package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogServiceTests {

  @Test
  void buildsVersionedUpkeepCatalogFromMaintenanceReports() {
    OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogResponse catalog =
        new OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService().catalog();

    assertThat(catalog.version()).isEqualTo("Java v489");
    assertThat(catalog.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-maintenance-upkeep-catalog");
    assertThat(catalog.profile())
        .isEqualTo("java-shard-readiness-route-cleanup-maintenance-upkeep-catalog.v1");
    assertThat(catalog.itemCount()).isEqualTo(9);
    assertThat(catalog.firstServiceVersion()).isEqualTo(471);
    assertThat(catalog.latestRouteVersion()).isEqualTo(488);
    assertThat(catalog.items())
        .extracting(OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogResponse.UpkeepItem::name)
        .containsExactly(
            "segment-catalog",
            "continuity",
            "latest-sibling-report",
            "handoff-pair-audit",
            "boundary-drift",
            "source-plan-alignment",
            "test-budget-plan",
            "archive-manifest",
            "closeout");
    assertThat(catalog.items().getFirst().endpoint())
        .isEqualTo(OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogService.ENDPOINT);
    assertThat(catalog.items())
        .allSatisfy(
            item -> {
              assertThat(item.routeVersion()).isEqualTo(item.serviceVersion() + 1);
              assertThat(item.evidencePath()).endsWith(".json");
              assertThat(item.consumer()).isNotBlank();
              assertThat(item.boundary()).isNotBlank();
            });
    assertThat(catalog.checks()).contains("upkeep-catalog-remains-read-only");
    assertThat(catalog.status()).isEqualTo("passed");
  }
}
