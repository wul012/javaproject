package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistServiceTests {

  @Test
  void buildsReleaseChecklistFromUpkeepReadinessEvidence() {
    OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistResponse checklist =
        service().checklist();

    assertThat(checklist.version()).isEqualTo("Java v512");
    assertThat(checklist.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-maintenance-release-checklist");
    assertThat(checklist.profile())
        .isEqualTo("java-shard-readiness-route-cleanup-maintenance-release-checklist.v1");
    assertThat(checklist.checklistItemCount()).isEqualTo(5);
    assertThat(checklist.acceptedItemCount()).isEqualTo(5);
    assertThat(checklist.items())
        .extracting(
            OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistResponse.ChecklistItem::name)
        .containsExactly(
            "catalog-baseline", "readiness-gate", "closeout", "source-plan", "read-only-boundary");
    assertThat(checklist.items()).allSatisfy(item -> assertThat(item.status()).isEqualTo("passed"));
    assertThat(checklist.checks()).contains("release-checklist-remains-read-only");
    assertThat(checklist.status()).isEqualTo("passed");
  }

  private OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistService service() {
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
    OpsShardReadinessRouteCleanupMaintenanceVersionLineageService lineage =
        new OpsShardReadinessRouteCleanupMaintenanceVersionLineageService();
    OpsShardReadinessRouteCleanupMaintenanceReadinessGateService gate =
        new OpsShardReadinessRouteCleanupMaintenanceReadinessGateService(
            review,
            lineage,
            new OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexService(),
            policy,
            ci);
    return new OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistService(
        new OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService(),
        gate,
        new OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutService(
            new OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService(),
            review,
            gate,
            lineage,
            ledger));
  }
}
