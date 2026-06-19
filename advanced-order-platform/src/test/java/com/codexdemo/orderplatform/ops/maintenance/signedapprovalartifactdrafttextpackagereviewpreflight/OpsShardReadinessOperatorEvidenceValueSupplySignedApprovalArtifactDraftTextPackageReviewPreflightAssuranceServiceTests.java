package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagereviewpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightAssuranceServiceTests {

  @Test
  void sourceEvidenceReviewDoesNotImportEvidence() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightSourceEvidenceService()
            .sourceEvidence();

    assertThat(response.version()).isEqualTo("Java v950");
    assertThat(response.criterionCount()).isEqualTo(3);
    assertThat(response.readyForEvidenceImport()).isFalse();
  }

  @Test
  void operatorValueHandleReviewKeepsValueImportLocked() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightOperatorValueHandleService()
            .operatorValueHandle();

    assertThat(response.version()).isEqualTo("Java v951");
    assertThat(response.criterionCount()).isEqualTo(2);
    assertThat(response.readyForOperatorValueSubmission()).isFalse();
  }

  @Test
  void policyReviewStateDoesNotGrantApproval() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightPolicyReviewStateService()
            .policyReviewState();

    assertThat(response.version()).isEqualTo("Java v952");
    assertThat(response.criterionCount()).isEqualTo(3);
    assertThat(response.readyForApprovalGrant()).isFalse();
  }

  @Test
  void executionLockControlsKeepRuntimeClosed() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightExecutionLockControlsService()
            .executionLockControls();

    assertThat(response.version()).isEqualTo("Java v953");
    assertThat(response.criterionCount()).isEqualTo(5);
    assertThat(response.readyForRuntimePayload()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
  }

  @Test
  void archiveCloseoutSummarizesAllCriteriaBeforeAcceptance() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightArchiveCloseoutService()
            .archiveCloseout();

    assertThat(response.version()).isEqualTo("Java v954");
    assertThat(response.criterionCount()).isEqualTo(25);
    assertThat(response.rejectionControlCount()).isEqualTo(25);
    assertThat(response.readyForDraftTextPackageAcceptance()).isFalse();
  }
}
