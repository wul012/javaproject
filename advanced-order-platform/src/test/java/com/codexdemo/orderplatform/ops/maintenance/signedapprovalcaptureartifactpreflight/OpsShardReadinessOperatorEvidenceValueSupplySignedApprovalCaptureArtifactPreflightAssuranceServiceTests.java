package com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightAssuranceServiceTests {

  @Test
  void exposesSignatureSealWithoutSignatureMaterial() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSignatureSealService()
            .seal();

    assertThat(response.version()).isEqualTo("Java v749");
    assertThat(response.readyForSignedApprovalCapture()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(3);
    assertThat(response.sealCount()).isEqualTo(3);
    assertThat(response.gateCount()).isEqualTo(3);
    assertThat(response.checks())
        .contains("signed-approval-capture-artifact-preflight-no-signature-material");
  }

  @Test
  void exposesStatementEvidenceWithoutImportingEvidence() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightStatementEvidenceService()
            .evidence();

    assertThat(response.version()).isEqualTo("Java v750");
    assertThat(response.readyForEvidenceImport()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(4);
    assertThat(response.sealCount()).isEqualTo(4);
    assertThat(response.gateCount()).isEqualTo(2);
  }

  @Test
  void exposesRedactionValueWithoutValueBody() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightRedactionValueService()
            .binding();

    assertThat(response.version()).isEqualTo("Java v751");
    assertThat(response.readyForOperatorValueSubmission()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(4);
    assertThat(response.sealCount()).isEqualTo(4);
    assertThat(response.gateCount()).isEqualTo(3);
  }

  @Test
  void exposesFailClosedLocksWithoutWritesOrSiblingMutation() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFailClosedLockService()
            .locks();

    assertThat(response.version()).isEqualTo("Java v752");
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.readyForProductionExecution()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(5);
    assertThat(response.sealCount()).isEqualTo(5);
    assertThat(response.gateCount()).isEqualTo(5);
  }

  @Test
  void exposesArchivePlanWithoutFileWrites() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightArchivePlanService()
            .plan();

    assertThat(response.version()).isEqualTo("Java v753");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.readyForArtifactDraft()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(5);
    assertThat(response.sealCount()).isEqualTo(5);
    assertThat(response.gateCount()).isEqualTo(20);
  }

  @Test
  void closesOutArtifactPreflightWithAllLocksHeld() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCloseoutService()
            .closeout();

    assertThat(response.version()).isEqualTo("Java v754");
    assertThat(response.readyForArtifactDraft()).isFalse();
    assertThat(response.readyForSignedApprovalCapture()).isFalse();
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.readyForOperatorValueSubmission()).isFalse();
    assertThat(response.readyForEvidenceImport()).isFalse();
    assertThat(response.readyForProductionExecution()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(25);
    assertThat(response.sealCount()).isEqualTo(25);
    assertThat(response.gateCount()).isEqualTo(20);
    assertThat(response.checks())
        .contains(
            "signed-approval-capture-artifact-preflight-closeout-versions-v735-v759",
            "signed-approval-capture-artifact-preflight-closeout-no-artifact-materialization",
            "signed-approval-capture-artifact-preflight-closeout-next-step-requires-separate-draft-plan");
  }
}
