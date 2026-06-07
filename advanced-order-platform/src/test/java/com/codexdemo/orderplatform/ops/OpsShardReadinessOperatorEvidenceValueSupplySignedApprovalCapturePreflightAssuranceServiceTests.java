package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAssuranceServiceTests {

    @Test
    void exposesChannelSignaturePolicyWithoutRawSignatureMaterial() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightChannelSignaturePolicyService()
                        .policy();

        assertThat(response.version()).isEqualTo("Java v724");
        assertThat(response.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightChannelSignaturePolicyService
                        .ENDPOINT);
        assertThat(response.readyForSignedApprovalCapture()).isFalse();
        assertThat(response.inputCount()).isEqualTo(3);
        assertThat(response.attestationCount()).isEqualTo(3);
        assertThat(response.policyCount()).isEqualTo(3);
        assertThat(response.checks()).contains(
                "signed-approval-capture-preflight-signature-material-redacted");
    }

    @Test
    void exposesStatementJustificationWithoutSignedApprovalText() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightStatementJustificationService()
                        .statement();

        assertThat(response.version()).isEqualTo("Java v726");
        assertThat(response.readyForSignedApprovalCapture()).isFalse();
        assertThat(response.readyForOperatorValueSubmission()).isFalse();
        assertThat(response.inputCount()).isEqualTo(2);
        assertThat(response.attestationCount()).isEqualTo(2);
        assertThat(response.policyCount()).isEqualTo(2);
    }

    @Test
    void mirrorsSourceEvidenceWithoutImportingIt() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSourceEvidenceMirrorService()
                        .mirror();

        assertThat(response.version()).isEqualTo("Java v728");
        assertThat(response.readyForEvidenceImport()).isFalse();
        assertThat(response.evidenceImportState()).isEqualTo("locked");
        assertThat(response.inputCount()).isEqualTo(3);
        assertThat(response.attestationCount()).isEqualTo(3);
        assertThat(response.policyCount()).isEqualTo(1);
    }

    @Test
    void bindsRedactionProvenanceWithoutValueBody() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightRedactionProvenanceBindingService()
                        .binding();

        assertThat(response.version()).isEqualTo("Java v730");
        assertThat(response.readyForOperatorValueSubmission()).isFalse();
        assertThat(response.readyForEvidenceImport()).isFalse();
        assertThat(response.inputCount()).isEqualTo(4);
        assertThat(response.attestationCount()).isEqualTo(4);
        assertThat(response.policyCount()).isEqualTo(3);
    }

    @Test
    void exposesFailClosedLocksWithoutWriteRoutesOrSiblingMutation() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightFailClosedLockService()
                        .locks();

        assertThat(response.version()).isEqualTo("Java v732");
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.readyForOperatorValueSubmission()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
        assertThat(response.inputCount()).isEqualTo(5);
        assertThat(response.attestationCount()).isEqualTo(5);
        assertThat(response.policyCount()).isEqualTo(5);
    }

    @Test
    void closesOutCapturePreflightWithAllExecutionGatesClosed() {
        var response = new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCloseoutService()
                .closeout();

        assertThat(response.version()).isEqualTo("Java v734");
        assertThat(response.readyForSignedApprovalCapture()).isFalse();
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.readyForOperatorValueSubmission()).isFalse();
        assertThat(response.readyForEvidenceImport()).isFalse();
        assertThat(response.readyForProductionExecution()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
        assertThat(response.inputCount()).isEqualTo(25);
        assertThat(response.attestationCount()).isEqualTo(25);
        assertThat(response.policyCount()).isEqualTo(20);
        assertThat(response.checks()).contains(
                "signed-approval-capture-preflight-closeout-versions-v710-v734",
                "signed-approval-capture-preflight-closeout-no-signed-approval-capture",
                "signed-approval-capture-preflight-closeout-next-step-requires-separate-artifact-plan"
        );
    }
}
