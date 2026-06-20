package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightServiceTests {

  @Test
  void comparisonPreflightServicesExposeReadOnlyLaneSlices() {
    var catalog =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightCatalogService()
            .catalog();
    var identity =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightIdentityRequestService()
            .identityRequest();
    var digest =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightDigestSignatureService()
            .digestSignature();
    var evidencePolicy =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightEvidenceValuePolicyService()
            .evidenceValuePolicy();
    var execution =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightExecutionCloseoutService()
            .executionCloseout();

    assertThat(catalog.version()).isEqualTo("Java v1001");
    assertThat(catalog.comparisonLaneCount()).isEqualTo(25);
    assertThat(identity.comparisonLaneCount()).isEqualTo(4);
    assertThat(digest.comparisonLaneCount()).isEqualTo(7);
    assertThat(evidencePolicy.comparisonLaneCount()).isEqualTo(8);
    assertThat(execution.comparisonLaneCount()).isEqualTo(6);
    assertThat(execution.readyForRuntimePayload()).isFalse();
    assertThat(execution.readyForSubmittedPackageAcceptance()).isFalse();
  }
}
