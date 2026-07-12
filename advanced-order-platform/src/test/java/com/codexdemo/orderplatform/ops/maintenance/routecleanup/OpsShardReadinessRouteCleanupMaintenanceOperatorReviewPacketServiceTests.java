package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketServiceTests {

  @Test
  void buildsOperatorReviewPacketFromTypedMaintenanceServices() {
    OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketResponse packet =
        service().packet();

    assertThat(packet.version()).isEqualTo("Java v501");
    assertThat(packet.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-maintenance-operator-review-packet");
    assertThat(packet.profile())
        .isEqualTo("java-shard-readiness-route-cleanup-maintenance-operator-review-packet.v1");
    assertThat(packet.sectionCount()).isEqualTo(5);
    assertThat(packet.evidenceItemCount()).isEqualTo(9);
    assertThat(packet.matrixEntryCount()).isEqualTo(9);
    assertThat(packet.ciExpectationCount()).isEqualTo(9);
    assertThat(packet.policyCount()).isEqualTo(7);
    assertThat(packet.digestLedgerEntryCount()).isEqualTo(9);
    assertThat(packet.sections())
        .extracting(
            OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketResponse.ReviewSection
                ::name)
        .containsExactly(
            "upkeep-catalog",
            "consumer-handoff-matrix",
            "ci-expectation-manifest",
            "fail-closed-policy",
            "archive-digest-ledger");
    assertThat(packet.sections())
        .allSatisfy(
            section -> {
              assertThat(section.sourceEndpoint()).startsWith("/api/v1/ops/shard-readiness");
              assertThat(section.status()).isEqualTo("passed");
            });
    assertThat(packet.checks()).contains("operator-review-packet-remains-read-only");
    assertThat(packet.status()).isEqualTo("passed");
  }

  private OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService service() {
    return new OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService(
        new OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService(),
        new OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixService(),
        new OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService(),
        new OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService(),
        new OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService());
  }
}
