package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAssuranceControllerTests {

    @Test
    void exposesChannelSignatureThroughAssuranceController() {
        var response = controller().channelSignature();

        assertThat(response.version()).isEqualTo("Java v724");
        assertThat(response.readyForSignedApprovalCapture()).isFalse();
        assertThat(response.inputCount()).isEqualTo(3);
    }

    @Test
    void exposesStatementJustificationThroughAssuranceController() {
        var response = controller().statementJustification();

        assertThat(response.version()).isEqualTo("Java v726");
        assertThat(response.readyForOperatorValueSubmission()).isFalse();
        assertThat(response.inputCount()).isEqualTo(2);
    }

    @Test
    void exposesSourceEvidenceThroughAssuranceController() {
        var response = controller().sourceEvidence();

        assertThat(response.version()).isEqualTo("Java v728");
        assertThat(response.readyForEvidenceImport()).isFalse();
        assertThat(response.inputCount()).isEqualTo(3);
    }

    @Test
    void exposesRedactionProvenanceThroughAssuranceController() {
        var response = controller().redactionProvenance();

        assertThat(response.version()).isEqualTo("Java v730");
        assertThat(response.readyForOperatorValueSubmission()).isFalse();
        assertThat(response.inputCount()).isEqualTo(4);
    }

    @Test
    void exposesFailClosedLocksThroughAssuranceController() {
        var response = controller().failClosedLocks();

        assertThat(response.version()).isEqualTo("Java v732");
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
        assertThat(response.inputCount()).isEqualTo(5);
    }

    @Test
    void exposesCloseoutThroughAssuranceController() {
        var response = controller().closeout();

        assertThat(response.version()).isEqualTo("Java v734");
        assertThat(response.readyForSignedApprovalCapture()).isFalse();
        assertThat(response.readyForProductionExecution()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
        assertThat(response.inputCount()).isEqualTo(25);
    }

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAssuranceController controller() {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAssuranceController(
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightChannelSignaturePolicyService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightStatementJustificationService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSourceEvidenceMirrorService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightRedactionProvenanceBindingService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightFailClosedLockService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCloseoutService()
        );
    }
}
