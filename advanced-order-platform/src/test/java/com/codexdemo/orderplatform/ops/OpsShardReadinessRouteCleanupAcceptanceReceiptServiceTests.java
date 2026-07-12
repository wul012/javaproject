package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupAcceptanceReceiptServiceTests {

  @Test
  void buildsAcceptanceReceiptFromAuditTrailAndCloseout() {
    OpsShardReadinessRouteCleanupAcceptanceReceiptResponse receipt =
        new OpsShardReadinessRouteCleanupAcceptanceReceiptService(
                new OpsShardReadinessRouteCleanupAuditTrailService(),
                OpsShardReadinessRouteCleanupServiceFixtures.extendedCloseoutService())
            .receipt();

    assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion())
        .isGreaterThanOrEqualTo(368);
    assertThat(receipt.project()).isEqualTo("advanced-order-platform");
    assertThat(receipt.version())
        .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
    assertThat(receipt.readOnly()).isTrue();
    assertThat(receipt.executionAllowed()).isFalse();
    assertThat(receipt.receiptEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-acceptance-receipt");
    assertThat(receipt.receiptProfile())
        .isEqualTo("java-shard-readiness-route-cleanup-acceptance-receipt.v1");
    assertThat(receipt.auditTrailEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-audit-trail");
    assertThat(receipt.closeoutEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-extended-closeout");
    assertThat(receipt.acceptedCriteriaCount()).isEqualTo(5);
    assertThat(receipt.acceptedCriteria())
        .extracting(OpsShardReadinessRouteCleanupAcceptanceReceiptResponse.AcceptedCriterion::name)
        .containsExactly(
            "audit-trail-passed",
            "extended-closeout-passed",
            "read-only-boundary-held",
            "source-plan-anchored",
            "execution-remains-disabled");
    assertThat(receipt.acceptedCriteria())
        .allSatisfy(
            criterion -> {
              assertThat(criterion.required()).isTrue();
              assertThat(criterion.status()).isEqualTo("accepted");
            });
    assertThat(receipt.receipt()).startsWith("accepted-read-only-route-cleanup-handoff-v");
    assertThat(receipt.status()).isEqualTo("passed");
  }
}
