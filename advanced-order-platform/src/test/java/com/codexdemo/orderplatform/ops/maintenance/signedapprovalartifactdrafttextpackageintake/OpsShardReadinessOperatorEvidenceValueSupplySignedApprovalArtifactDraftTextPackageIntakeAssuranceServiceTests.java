package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackageintake;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeAssuranceServiceTests {

  @Test
  void sourceEvidenceExposesDigestPointersWithoutImport() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSourceEvidenceService()
            .sourceEvidence();

    assertThat(response.version()).isEqualTo("Java v925");
    assertThat(response.fieldCount()).isEqualTo(3);
    assertThat(response.readyForEvidenceImport()).isFalse();
  }

  @Test
  void operatorValueHandleKeepsValueImportLocked() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeOperatorValueHandleService()
            .operatorValueHandle();

    assertThat(response.version()).isEqualTo("Java v926");
    assertThat(response.fieldCount()).isEqualTo(2);
    assertThat(response.readyForOperatorValueSubmission()).isFalse();
    assertThat(response.valueImportState()).isEqualTo("locked");
  }

  @Test
  void policyReviewStateStopsBeforeApprovalGrant() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakePolicyReviewStateService()
            .policyReviewState();

    assertThat(response.version()).isEqualTo("Java v927");
    assertThat(response.fieldCount()).isEqualTo(3);
    assertThat(response.approvalGrantState()).isEqualTo("not-emitted");
    assertThat(response.readyForApprovalGrant()).isFalse();
  }

  @Test
  void executionLockKeepsRuntimeAndSiblingLocked() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeExecutionLockService()
            .executionLock();

    assertThat(response.version()).isEqualTo("Java v928");
    assertThat(response.fieldCount()).isEqualTo(5);
    assertThat(response.readyForRuntimePayload()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
  }

  @Test
  void archiveCloseoutSummarizesAllFieldsBeforePackageReview() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeArchiveCloseoutService()
            .archiveCloseout();

    assertThat(response.version()).isEqualTo("Java v929");
    assertThat(response.fieldCount()).isEqualTo(25);
    assertThat(response.guardCount()).isEqualTo(25);
    assertThat(response.readyForDraftTextPackageReview()).isFalse();
    assertThat(response.checks())
        .contains(
            "signed-approval-artifact-draft-text-package-intake-closeout-before-package-review");
  }
}
