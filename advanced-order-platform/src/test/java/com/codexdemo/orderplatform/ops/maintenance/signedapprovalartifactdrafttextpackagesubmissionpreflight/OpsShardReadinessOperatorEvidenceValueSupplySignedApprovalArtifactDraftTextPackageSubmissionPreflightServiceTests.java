package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightServiceTests {

  @Test
  void catalogSummarizesFullSubmissionPreflightWithoutAcceptance() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCatalogService()
            .catalog();

    assertThat(response.version()).isEqualTo("Java v966");
    assertThat(response.slotCount()).isEqualTo(25);
    assertThat(response.comparisonControlCount()).isEqualTo(25);
    assertThat(response.readyForSubmittedPackageAcceptance()).isFalse();
  }

  @Test
  void identityAndDigestSignatureSegmentsStayCompareOnly() {
    var identity =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightIdentityService()
            .identity();
    var digestSignature =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightDigestSignatureService()
            .digestSignature();

    assertThat(identity.version()).isEqualTo("Java v967");
    assertThat(identity.slotCount()).isEqualTo(4);
    assertThat(digestSignature.slotCount()).isEqualTo(7);
    assertThat(digestSignature.readyForDetachedSignatureParsing()).isFalse();
  }

  @Test
  void assuranceSegmentsDoNotImportOrExecute() {
    var evidenceValue =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightEvidenceValueService()
            .evidenceValue();
    var closeout =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightPolicyExecutionCloseoutService()
            .policyExecutionCloseout();

    assertThat(evidenceValue.version()).isEqualTo("Java v968");
    assertThat(evidenceValue.readyForEvidenceImport()).isFalse();
    assertThat(closeout.slotCount()).isEqualTo(9);
    assertThat(closeout.readyForRuntimePayload()).isFalse();
    assertThat(closeout.siblingMutationAllowed()).isFalse();
  }
}
