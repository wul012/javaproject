package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceVersionLineageServiceTests {

  @Test
  void buildsVersionLineageSummaryForServiceRoutePairs() {
    OpsShardReadinessRouteCleanupMaintenanceVersionLineageResponse lineage =
        new OpsShardReadinessRouteCleanupMaintenanceVersionLineageService().lineage();

    assertThat(lineage.version()).isEqualTo("Java v503");
    assertThat(lineage.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-maintenance-version-lineage");
    assertThat(lineage.profile())
        .isEqualTo("java-shard-readiness-route-cleanup-maintenance-version-lineage.v1");
    assertThat(lineage.pairCount()).isEqualTo(9);
    assertThat(lineage.firstServiceVersion()).isEqualTo(471);
    assertThat(lineage.latestServiceVersion()).isEqualTo(487);
    assertThat(lineage.firstRouteVersion()).isEqualTo(472);
    assertThat(lineage.latestRouteVersion()).isEqualTo(488);
    assertThat(lineage.serviceVersionStep()).isEqualTo(2);
    assertThat(lineage.routeVersionStep()).isEqualTo(2);
    assertThat(lineage.gapCount()).isZero();
    assertThat(lineage.pairs().getFirst().routeFollowsService()).isTrue();
    assertThat(lineage.pairs().getFirst().nextServiceVersion()).isEqualTo(473);
    assertThat(lineage.pairs().getLast().nextServiceVersion()).isEqualTo(-1);
    assertThat(lineage.pairs()).allSatisfy(pair -> assertThat(pair.status()).isEqualTo("passed"));
    assertThat(lineage.checks()).contains("version-lineage-remains-read-only");
    assertThat(lineage.status()).isEqualTo("passed");
  }
}
