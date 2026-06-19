package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftauthoringreadiness.*;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessAssuranceControllerTests {

  @Test
  void exposesEvidenceRequirementsThroughAssuranceController() {
    var response = controller().evidenceRequirements();

    assertThat(response.version()).isEqualTo("Java v875");
    assertThat(response.endpoint())
        .isEqualTo(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessEvidenceRequirementService
                .ENDPOINT);
    assertThat(response.readyForEvidenceImport()).isFalse();
  }

  @Test
  void exposesValuePolicyRequirementsThroughAssuranceController() {
    var response = controller().valuePolicyRequirements();

    assertThat(response.version()).isEqualTo("Java v876");
    assertThat(response.endpoint())
        .isEqualTo(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessValuePolicyRequirementService
                .ENDPOINT);
    assertThat(response.readyForOperatorValueSubmission()).isFalse();
  }

  @Test
  void exposesEmbargoRequirementsThroughAssuranceController() {
    var response = controller().embargoRequirements();

    assertThat(response.version()).isEqualTo("Java v877");
    assertThat(response.endpoint())
        .isEqualTo(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessEmbargoRequirementService
                .ENDPOINT);
    assertThat(response.siblingMutationAllowed()).isFalse();
  }

  @Test
  void exposesDraftTextAbsenceThroughAssuranceController() {
    var response = controller().draftTextAbsence();

    assertThat(response.version()).isEqualTo("Java v878");
    assertThat(response.endpoint())
        .isEqualTo(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessDraftTextAbsenceService
                .ENDPOINT);
    assertThat(response.readyForSignedDraftText()).isFalse();
  }

  @Test
  void exposesCloseoutThroughAssuranceController() {
    var response = controller().closeout();

    assertThat(response.version()).isEqualTo("Java v879");
    assertThat(response.endpoint())
        .isEqualTo(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessCloseoutService
                .ENDPOINT);
    assertThat(response.requirementCount()).isEqualTo(25);
  }

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessAssuranceController
      controller() {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessAssuranceController(
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessEvidenceRequirementService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessValuePolicyRequirementService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessEmbargoRequirementService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessDraftTextAbsenceService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessCloseoutService());
  }
}
