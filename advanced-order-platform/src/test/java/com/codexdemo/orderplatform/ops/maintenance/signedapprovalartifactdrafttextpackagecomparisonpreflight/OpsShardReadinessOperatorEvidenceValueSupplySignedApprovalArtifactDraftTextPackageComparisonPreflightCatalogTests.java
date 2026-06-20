package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightCatalogTests {

  @Test
  void exposesTwentyFiveComparisonLanesWithAcceptanceControls() {
    var lanes =
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightLaneCatalog
            .allLanes();
    var controls =
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightAcceptanceControlCatalog
            .allControls();
    var gates =
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightAcceptanceControlCatalog
            .allGates();

    assertThat(lanes).hasSize(25);
    assertThat(controls).hasSize(25);
    assertThat(gates).hasSize(10);
    assertThat(lanes)
        .extracting(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
                    .ComparisonLane
                ::status)
        .containsOnly("passed");
    assertThat(controls)
        .allSatisfy(
            control -> {
              assertThat(control.enforcement()).isEqualTo("fail-closed");
              assertThat(control.rejectionCode()).startsWith("reject-package-");
            });
  }
}
