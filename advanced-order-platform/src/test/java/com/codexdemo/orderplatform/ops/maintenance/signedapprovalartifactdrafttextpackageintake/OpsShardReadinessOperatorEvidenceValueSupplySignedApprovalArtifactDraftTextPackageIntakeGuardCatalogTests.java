package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackageintake;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeGuardCatalogTests {

  @Test
  void guardsCoverEveryExpectedFieldAndFailClosed() {
    var guards =
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeGuardCatalog
            .allGuards();

    assertThat(guards)
        .hasSize(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeGuardCatalog
                .GUARD_COUNT);
    assertThat(guards)
        .allSatisfy(
            guard -> {
              assertThat(guard.status()).isEqualTo("passed");
              assertThat(guard.enforcement()).isEqualTo("fail-closed");
              assertThat(guard.rejectionCode()).startsWith("REJECT_DRAFT_TEXT_PACKAGE_INTAKE_");
            });
    assertThat(guards)
        .extracting(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
                    .IntakeGuard
                ::category)
        .contains(
            "identity", "digest", "signature", "evidence", "value", "policy", "lock", "closeout");
  }

  @Test
  void guardSlicesPreserveDigestAndLockSegments() {
    assertThat(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeGuardCatalog
                .guards(4, 8))
        .extracting(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
                    .IntakeGuard
                ::category)
        .containsOnly("digest");
    assertThat(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeGuardCatalog
                .guards(19, 24))
        .extracting(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
                    .IntakeGuard
                ::category)
        .containsOnly("lock");
  }
}
