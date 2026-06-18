package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEmbargoLockService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEvidenceReviewService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneManualPackageGateService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneValueRedactionService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneAssuranceControllerTests {

  @Test
  void exposesAssuranceRoutesWithoutOpeningExecution() {
    var controller = controller();

    assertThat(controller.evidenceReview().version()).isEqualTo("Java v825");
    assertThat(controller.valueRedaction().version()).isEqualTo("Java v826");
    assertThat(controller.embargoLocks().version()).isEqualTo("Java v827");
    assertThat(controller.manualPackageGate().version()).isEqualTo("Java v828");
    assertThat(controller.closeout().version()).isEqualTo("Java v829");
    assertThat(controller.closeout().readyForManualDraft()).isFalse();
    assertThat(controller.closeout().readyForRuntimePayload()).isFalse();
  }

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneAssuranceController
      controller() {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneAssuranceController(
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEvidenceReviewService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneValueRedactionService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEmbargoLockService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneManualPackageGateService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCloseoutService());
  }
}
