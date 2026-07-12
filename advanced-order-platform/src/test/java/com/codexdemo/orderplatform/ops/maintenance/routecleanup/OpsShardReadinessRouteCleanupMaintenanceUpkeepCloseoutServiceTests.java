package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutServiceTests {

  @Test
  void closesMaintenanceUpkeepEvidenceRun() {
    OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutResponse closeout = service().closeout();

    assertThat(closeout.version()).isEqualTo("Java v507");
    assertThat(closeout.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-maintenance-upkeep-closeout");
    assertThat(closeout.profile())
        .isEqualTo("java-shard-readiness-route-cleanup-maintenance-upkeep-closeout.v1");
    assertThat(closeout.sourcePlan()).isEqualTo("Node v549");
    assertThat(closeout.checkedReportCount()).isEqualTo(5);
    assertThat(closeout.upkeepItemCount()).isEqualTo(9);
    assertThat(closeout.gateCheckCount()).isEqualTo(5);
    assertThat(closeout.archiveDigestCount()).isEqualTo(9);
    assertThat(closeout.latestRouteVersion()).isEqualTo(488);
    assertThat(closeout.checks())
        .extracting(
            OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutResponse.CloseoutCheck::name)
        .containsExactly(
            "upkeep-catalog",
            "operator-review-packet",
            "readiness-gate",
            "version-lineage",
            "archive-digest-ledger");
    assertThat(closeout.checks())
        .allSatisfy(check -> assertThat(check.status()).isEqualTo("passed"));
    assertThat(closeout.status()).isEqualTo("passed");
  }

  private OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutService service() {
    OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService ci =
        new OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService();
    OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService policy =
        new OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService();
    OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService ledger =
        new OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService();
    OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService review =
        new OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService(
            new OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService(),
            new OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixService(),
            ci,
            policy,
            ledger);
    return new OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutService(
        new OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService(),
        review,
        new OpsShardReadinessRouteCleanupMaintenanceReadinessGateService(
            review,
            new OpsShardReadinessRouteCleanupMaintenanceVersionLineageService(),
            new OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexService(),
            policy,
            ci),
        new OpsShardReadinessRouteCleanupMaintenanceVersionLineageService(),
        ledger);
  }
}
