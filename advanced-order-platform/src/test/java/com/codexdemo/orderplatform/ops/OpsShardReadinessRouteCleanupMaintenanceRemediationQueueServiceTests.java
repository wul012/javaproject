package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceRemediationQueueServiceTests {

  @Test
  void buildsReadOnlyRemediationQueuePreview() {
    OpsShardReadinessRouteCleanupMaintenanceRemediationQueueResponse queue = service().queue();

    assertThat(queue.version()).isEqualTo("Java v514");
    assertThat(queue.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-maintenance-remediation-queue");
    assertThat(queue.profile())
        .isEqualTo("java-shard-readiness-route-cleanup-maintenance-remediation-queue.v1");
    assertThat(queue.queueItemCount()).isEqualTo(4);
    assertThat(queue.standbyItemCount()).isEqualTo(4);
    assertThat(queue.blockedItemCount()).isZero();
    assertThat(queue.items())
        .extracting(
            OpsShardReadinessRouteCleanupMaintenanceRemediationQueueResponse.QueueItem::name)
        .containsExactly(
            "fail-closed-policy-drift",
            "readiness-gate-blocked",
            "execution-boundary-drift",
            "upstream-startup-drift");
    assertThat(queue.items()).allSatisfy(item -> assertThat(item.status()).isEqualTo("standby"));
    assertThat(queue.checks()).contains("remediation-does-not-execute-actions");
    assertThat(queue.status()).isEqualTo("passed");
  }

  private OpsShardReadinessRouteCleanupMaintenanceRemediationQueueService service() {
    OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService ci =
        new OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService();
    OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService policy =
        new OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService();
    OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService review =
        new OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService(
            new OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService(),
            new OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixService(),
            ci,
            policy,
            new OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService());
    return new OpsShardReadinessRouteCleanupMaintenanceRemediationQueueService(
        policy,
        new OpsShardReadinessRouteCleanupMaintenanceReadinessGateService(
            review,
            new OpsShardReadinessRouteCleanupMaintenanceVersionLineageService(),
            new OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexService(),
            policy,
            ci));
  }
}
