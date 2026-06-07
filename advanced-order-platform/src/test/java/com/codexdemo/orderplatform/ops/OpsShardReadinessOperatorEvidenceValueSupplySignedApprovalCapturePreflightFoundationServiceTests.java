package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightFoundationServiceTests {

    @Test
    void exposesCatalogWithoutOpeningSignedApprovalCapture() {
        var response = new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCatalogService()
                .catalog();

        assertThat(response.version()).isEqualTo("Java v714");
        assertThat(response.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCatalogService.ENDPOINT);
        assertThat(response.sourcePlan()).isEqualTo("Node v1061");
        assertThat(response.readyForCapturePreflight()).isTrue();
        assertThat(response.readyForSignedApprovalCapture()).isFalse();
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.inputCount()).isEqualTo(25);
        assertThat(response.attestationCount()).isEqualTo(25);
        assertThat(response.policyCount()).isEqualTo(20);
        assertThat(response.status()).isEqualTo("passed");
    }

    @Test
    void bindsTemplateDigestWithoutSignatureMaterial() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightTemplateDigestBindingService()
                        .binding();

        assertThat(response.version()).isEqualTo("Java v716");
        assertThat(response.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightTemplateDigestBindingService
                        .ENDPOINT);
        assertThat(response.sourceTemplateVersion()).isEqualTo("Node v1036");
        assertThat(response.readyForSignedApprovalCapture()).isFalse();
        assertThat(response.inputCount()).isEqualTo(2);
        assertThat(response.attestationCount()).isEqualTo(2);
        assertThat(response.policyCount()).isEqualTo(2);
        assertThat(response.checks()).contains(
                "signed-approval-capture-preflight-template-digest-no-signature-material");
    }

    @Test
    void bindsReviewDigestWithoutApprovalGrant() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightReviewDigestBindingService()
                        .binding();

        assertThat(response.version()).isEqualTo("Java v718");
        assertThat(response.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightReviewDigestBindingService
                        .ENDPOINT);
        assertThat(response.sourceApprovalPacketReviewVersion()).isEqualTo("Node v1011");
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.approvalGrantState()).isEqualTo("not-emitted");
        assertThat(response.inputCount()).isEqualTo(1);
        assertThat(response.attestationCount()).isEqualTo(1);
        assertThat(response.policyCount()).isEqualTo(1);
    }

    @Test
    void mirrorsOperatorInputWithoutAuthorizingCapture() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightOperatorInputMirrorService()
                        .mirror();

        assertThat(response.version()).isEqualTo("Java v720");
        assertThat(response.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightOperatorInputMirrorService
                        .ENDPOINT);
        assertThat(response.readyForSignedApprovalCapture()).isFalse();
        assertThat(response.readyForOperatorValueSubmission()).isFalse();
        assertThat(response.inputCount()).isEqualTo(2);
        assertThat(response.attestationCount()).isEqualTo(2);
        assertThat(response.policyCount()).isEqualTo(2);
    }

    @Test
    void exposesTimingWindowWithoutOpeningRuntime() {
        var response = new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightTimingWindowService()
                .window();

        assertThat(response.version()).isEqualTo("Java v722");
        assertThat(response.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightTimingWindowService.ENDPOINT);
        assertThat(response.readyForRuntimePayload()).isFalse();
        assertThat(response.readyForLiveExecution()).isFalse();
        assertThat(response.inputCount()).isEqualTo(2);
        assertThat(response.attestationCount()).isEqualTo(2);
        assertThat(response.policyCount()).isEqualTo(1);
    }
}
