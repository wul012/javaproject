package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupReleaseHandoffServiceTests {

  @Test
  void buildsReleaseHandoffFromChecklistArchiveDigestAndPlanAlignment() {
    OpsShardReadinessRouteCleanupReleaseHandoffResponse handoff =
        new OpsShardReadinessRouteCleanupReleaseHandoffService(
                new OpsShardReadinessRouteCleanupHandoffChecklistService(
                    new OpsShardReadinessRouteCleanupPhaseSummaryService(),
                    new OpsShardReadinessRouteCleanupBoundaryMatrixService()),
                new OpsShardReadinessRouteCleanupArchivePlanService(),
                new OpsShardReadinessRouteCleanupDigestService(),
                new OpsShardReadinessRouteCleanupSourcePlanAlignmentService())
            .handoff();

    assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion())
        .isGreaterThanOrEqualTo(339);
    assertThat(handoff.project()).isEqualTo("advanced-order-platform");
    assertThat(handoff.version())
        .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
    assertThat(handoff.readOnly()).isTrue();
    assertThat(handoff.executionAllowed()).isFalse();
    assertThat(handoff.releaseHandoffEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-release-handoff");
    assertThat(handoff.handoffProfile())
        .isEqualTo("java-shard-readiness-route-cleanup-release-handoff.v1");
    assertThat(handoff.checklistEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-handoff-checklist");
    assertThat(handoff.archivePlanEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-archive-plan");
    assertThat(handoff.digestEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-digest");
    assertThat(handoff.sourcePlanAlignmentEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-source-plan-alignment");
    assertThat(handoff.handoffItemCount()).isEqualTo(4);
    assertThat(handoff.handoffItems())
        .extracting(OpsShardReadinessRouteCleanupReleaseHandoffResponse.HandoffItem::name)
        .containsExactly("checklist", "archive-plan", "digest", "source-plan-alignment");
    assertThat(handoff.handoffItems())
        .allSatisfy(item -> assertThat(item.status()).isEqualTo("passed"));
    assertThat(handoff.status()).isEqualTo("passed");
  }
}
