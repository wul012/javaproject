package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceReadinessGateServiceTests {

  @Test
  void buildsReadinessGateFromMaintenanceUpkeepReports() {
    OpsShardReadinessRouteCleanupMaintenanceReadinessGateResponse gate = service().gate();

    assertThat(gate.version()).isEqualTo("Java v505");
    assertThat(gate.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-maintenance-readiness-gate");
    assertThat(gate.profile())
        .isEqualTo("java-shard-readiness-route-cleanup-maintenance-readiness-gate.v1");
    assertThat(gate.gateCheckCount()).isEqualTo(5);
    assertThat(gate.acceptedCheckCount()).isEqualTo(5);
    assertThat(gate.blockedCheckCount()).isZero();
    assertThat(gate.firstServiceVersion()).isEqualTo(471);
    assertThat(gate.latestRouteVersion()).isEqualTo(488);
    assertThat(gate.gateChecks())
        .extracting(OpsShardReadinessRouteCleanupMaintenanceReadinessGateResponse.GateCheck::name)
        .containsExactly(
            "operator-review-packet",
            "version-lineage",
            "route-topology-index",
            "fail-closed-policy",
            "ci-expectation-manifest");
    assertThat(gate.gateChecks())
        .allSatisfy(
            check -> {
              assertThat(check.passed()).isTrue();
              assertThat(check.status()).isEqualTo("passed");
            });
    assertThat(gate.checks()).contains("readiness-gate-remains-read-only");
    assertThat(gate.status()).isEqualTo("passed");
  }

  private OpsShardReadinessRouteCleanupMaintenanceReadinessGateService service() {
    OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService ci =
        new OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService();
    OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService policy =
        new OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService();
    return new OpsShardReadinessRouteCleanupMaintenanceReadinessGateService(
        new OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService(
            new OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService(),
            new OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixService(),
            ci,
            policy,
            new OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService()),
        new OpsShardReadinessRouteCleanupMaintenanceVersionLineageService(),
        new OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexService(),
        policy,
        ci);
  }
}
