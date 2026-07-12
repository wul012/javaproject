package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupAuditTrailServiceTests {

  @Test
  void buildsReadOnlyAuditTrailForRouteCleanupHandoff() {
    OpsShardReadinessRouteCleanupAuditTrailResponse auditTrail =
        new OpsShardReadinessRouteCleanupAuditTrailService().auditTrail();

    assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion())
        .isGreaterThanOrEqualTo(366);
    assertThat(auditTrail.project()).isEqualTo("advanced-order-platform");
    assertThat(auditTrail.version())
        .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
    assertThat(auditTrail.readOnly()).isTrue();
    assertThat(auditTrail.executionAllowed()).isFalse();
    assertThat(auditTrail.auditTrailEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-audit-trail");
    assertThat(auditTrail.auditTrailProfile())
        .isEqualTo("java-shard-readiness-route-cleanup-audit-trail.v1");
    assertThat(auditTrail.checkpointCount()).isEqualTo(5);
    assertThat(auditTrail.checkpoints())
        .extracting(OpsShardReadinessRouteCleanupAuditTrailResponse.AuditCheckpoint::name)
        .containsExactly(
            "node-plan-source",
            "catalog-continuity",
            "read-only-boundary",
            "controller-split",
            "handoff-evidence");
    assertThat(auditTrail.checkpoints())
        .allSatisfy(
            checkpoint -> {
              assertThat(checkpoint.readOnly()).isTrue();
              assertThat(checkpoint.executionAllowed()).isFalse();
              assertThat(checkpoint.status()).isEqualTo("passed");
            });
    assertThat(auditTrail.sourcePlan()).isEqualTo("Node v549");
    assertThat(auditTrail.status()).isEqualTo("passed");
  }
}
