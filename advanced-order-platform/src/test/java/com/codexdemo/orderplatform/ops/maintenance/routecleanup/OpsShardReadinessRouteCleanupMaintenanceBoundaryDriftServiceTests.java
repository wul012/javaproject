package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftServiceTests {

  @Test
  void auditsRouteCleanupBoundaryDriftAcrossAllEntries() {
    OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftResponse audit =
        new OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftService().audit();

    assertThat(audit.version()).isEqualTo("Java v479");
    assertThat(audit.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-maintenance-boundary-drift");
    assertThat(audit.profile())
        .isEqualTo("java-shard-readiness-route-cleanup-maintenance-boundary-drift.v1");
    assertThat(audit.scannedEntryCount()).isEqualTo(103);
    assertThat(audit.readOnlyViolationCount()).isZero();
    assertThat(audit.executionAllowedViolationCount()).isZero();
    assertThat(audit.upstreamStartupViolationCount()).isZero();
    assertThat(audit.credentialValueViolationCount()).isZero();
    assertThat(audit.rawEndpointViolationCount()).isZero();
    assertThat(audit.managedAuditViolationCount()).isZero();
    assertThat(audit.writeRoutingViolationCount()).isZero();
    assertThat(audit.forbiddenOperations()).contains("raw-endpoint-parse");
    assertThat(audit.checks()).contains("managed-audit-violation-count-0");
    assertThat(audit.status()).isEqualTo("passed");
  }
}
