package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightArchivePlanService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFailClosedLockService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightRedactionValueService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSignatureSealService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightStatementEvidenceService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightAssuranceControllerTests {

  @Test
  void exposesSignatureSealThroughAssuranceController() {
    var response = controller().signatureSeal();

    assertThat(response.version()).isEqualTo("Java v749");
    assertThat(response.readyForSignedApprovalCapture()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(3);
  }

  @Test
  void exposesStatementEvidenceThroughAssuranceController() {
    var response = controller().statementEvidence();

    assertThat(response.version()).isEqualTo("Java v750");
    assertThat(response.readyForEvidenceImport()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(4);
  }

  @Test
  void exposesRedactionValueThroughAssuranceController() {
    var response = controller().redactionValue();

    assertThat(response.version()).isEqualTo("Java v751");
    assertThat(response.readyForOperatorValueSubmission()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(4);
  }

  @Test
  void exposesFailClosedLocksThroughAssuranceController() {
    var response = controller().failClosedLocks();

    assertThat(response.version()).isEqualTo("Java v752");
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(5);
  }

  @Test
  void exposesArchivePlanThroughAssuranceController() {
    var response = controller().archivePlan();

    assertThat(response.version()).isEqualTo("Java v753");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.readyForArtifactDraft()).isFalse();
  }

  @Test
  void exposesCloseoutThroughAssuranceController() {
    var response = controller().closeout();

    assertThat(response.version()).isEqualTo("Java v754");
    assertThat(response.readyForArtifactDraft()).isFalse();
    assertThat(response.readyForProductionExecution()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(25);
  }

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightAssuranceController
      controller() {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightAssuranceController(
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSignatureSealService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightStatementEvidenceService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightRedactionValueService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFailClosedLockService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightArchivePlanService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCloseoutService());
  }
}
