package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagereviewpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightGateCatalogTests {

  @Test
  void gatesPinReviewPreflightBoundaries() {
    var gates =
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightRejectionControlCatalog
            .allGates();

    assertThat(gates)
        .hasSize(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightRejectionControlCatalog
                .GATE_COUNT);
    assertThat(gates).allSatisfy(gate -> assertThat(gate.enforcement()).isEqualTo("fail-closed"));
    assertThat(gates)
        .extracting(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
                    .ReviewGate
                ::category)
        .contains(
            "review",
            "draft-text",
            "signature",
            "approval",
            "value",
            "runtime",
            "sibling",
            "catalog",
            "source",
            "secret",
            "digest",
            "reviewer",
            "acceptance",
            "archive",
            "closeout");
  }
}
