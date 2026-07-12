package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentServiceTests {

  @Test
  void snapshotsMaintenanceAlignmentToNodeV549SourcePlan() {
    OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentResponse alignment =
        new OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentService(
                new OpsShardReadinessRouteCleanupSourcePlanAlignmentService())
            .alignment();

    assertThat(alignment.version()).isEqualTo("Java v481");
    assertThat(alignment.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-maintenance-source-plan-alignment");
    assertThat(alignment.profile())
        .isEqualTo("java-shard-readiness-route-cleanup-maintenance-source-plan-alignment.v1");
    assertThat(alignment.sourcePlan()).isEqualTo("Node v549");
    assertThat(alignment.sourcePlanPath()).contains("docs/plans3");
    assertThat(alignment.segmentCount()).isEqualTo(6);
    assertThat(alignment.upstreamAlignmentCount()).isEqualTo(4);
    assertThat(alignment.checks())
        .contains(
            "java-and-mini-kv-not-started-by-maintenance-suite",
            "managed-audit-connection-remains-closed");
    assertThat(alignment.status()).isEqualTo("passed");
  }
}
