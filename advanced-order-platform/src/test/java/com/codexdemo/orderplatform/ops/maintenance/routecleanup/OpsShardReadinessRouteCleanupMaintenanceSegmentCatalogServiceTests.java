package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogServiceTests {

  @Test
  void buildsReadOnlySegmentCatalogFromSplitRouteCleanupEvidence() {
    OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogResponse catalog =
        new OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogService().catalog();

    assertThat(catalog.project()).isEqualTo("advanced-order-platform");
    assertThat(catalog.version()).isEqualTo("Java v471");
    assertThat(catalog.readOnly()).isTrue();
    assertThat(catalog.executionAllowed()).isFalse();
    assertThat(catalog.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-maintenance-segment-catalog");
    assertThat(catalog.profile())
        .isEqualTo("java-shard-readiness-route-cleanup-maintenance-segment-catalog.v1");
    assertThat(catalog.segmentCount()).isEqualTo(6);
    assertThat(catalog.entryCount()).isEqualTo(103);
    assertThat(catalog.segments())
        .extracting(
            OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogResponse.SegmentSummary::name)
        .containsExactly(
            "latest-sibling",
            "readiness-seed",
            "handoff-core",
            "handoff-assurance",
            "handoff-governance",
            "post-completion");
    assertThat(catalog.segments().getFirst().firstJavaVersion()).isEqualTo(306);
    assertThat(catalog.segments().getLast().lastJavaVersion()).isEqualTo(408);
    assertThat(catalog.forbiddenOperations()).contains("managed-audit-connection");
    assertThat(catalog.status()).isEqualTo("passed");
  }
}
