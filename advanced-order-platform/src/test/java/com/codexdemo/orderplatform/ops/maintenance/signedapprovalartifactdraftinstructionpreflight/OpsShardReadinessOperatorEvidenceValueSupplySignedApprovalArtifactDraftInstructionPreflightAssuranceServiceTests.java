package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightAssuranceServiceTests {

  @Test
  void exposesEvidenceInstructionsWithoutEvidenceImport() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEvidenceInstructionService()
            .evidenceInstructions();

    assertThat(response.version()).isEqualTo("Java v900");
    assertThat(response.readyForEvidenceImport()).isFalse();
    assertThat(response.readyForRuntimePayload()).isFalse();
    assertThat(response.slotCount()).isEqualTo(3);
    assertThat(response.guardCount()).isEqualTo(3);
  }

  @Test
  void exposesValuePolicyInstructionsWithoutRawValues() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightValuePolicyInstructionService()
            .valuePolicyInstructions();

    assertThat(response.version()).isEqualTo("Java v901");
    assertThat(response.readyForOperatorValueSubmission()).isFalse();
    assertThat(response.valueImportState()).isEqualTo("locked");
    assertThat(response.slotCount()).isEqualTo(4);
    assertThat(response.guardCount()).isEqualTo(4);
  }

  @Test
  void exposesEmbargoInstructionsWithoutSiblingMutation() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEmbargoInstructionService()
            .embargoInstructions();

    assertThat(response.version()).isEqualTo("Java v902");
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
    assertThat(response.slotCount()).isEqualTo(5);
    assertThat(response.guardCount()).isEqualTo(5);
  }

  @Test
  void exposesDraftTextLockWithoutMaterializingSignedDraft() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDraftTextLockService()
            .draftTextLock();

    assertThat(response.version()).isEqualTo("Java v903");
    assertThat(response.signedDraftState()).isEqualTo("not-created");
    assertThat(response.readyForSignedDraftText()).isFalse();
    assertThat(response.gateCount()).isEqualTo(20);
  }

  @Test
  void exposesCloseoutWithAllInstructionLocksHeld() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCloseoutService()
            .closeout();

    assertThat(response.version()).isEqualTo("Java v904");
    assertThat(response.readyForDraftTextPackage()).isFalse();
    assertThat(response.readyForRuntimePayload()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
    assertThat(response.slotCount()).isEqualTo(25);
    assertThat(response.guardCount()).isEqualTo(25);
    assertThat(response.status()).isEqualTo("passed");
  }
}
