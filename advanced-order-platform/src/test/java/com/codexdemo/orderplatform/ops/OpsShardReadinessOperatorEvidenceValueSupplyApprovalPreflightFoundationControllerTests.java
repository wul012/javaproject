package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightFoundationControllerTests {

    @Test
    void exposesCatalogThroughFoundationControllerWithoutCapturingApproval() {
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightFoundationController controller = controller();

        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse catalog =
                controller.catalog();

        assertThat(catalog.version()).isEqualTo("Java v688");
        assertThat(catalog.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCatalogService.ENDPOINT);
        assertThat(catalog.readyForApprovalPreflight()).isTrue();
        assertThat(catalog.readyForSignedApprovalCapture()).isFalse();
        assertThat(catalog.readyForApprovalGrant()).isFalse();
        assertThat(catalog.itemCount()).isEqualTo(25);
        assertThat(catalog.policyCount()).isEqualTo(20);
        assertThat(catalog.status()).isEqualTo("passed");
    }

    @Test
    void exposesIdentitySignatureThroughFoundationControllerWithoutApprovalGrant() {
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightFoundationController controller = controller();

        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse signature =
                controller.identitySignature();

        assertThat(signature.version()).isEqualTo("Java v690");
        assertThat(signature.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightIdentitySignatureService.ENDPOINT);
        assertThat(signature.readyForSignedApprovalCapture()).isFalse();
        assertThat(signature.readyForApprovalGrant()).isFalse();
        assertThat(signature.readyForOperatorValueSubmission()).isFalse();
        assertThat(signature.itemCount()).isEqualTo(5);
        assertThat(signature.policyCount()).isEqualTo(5);
        assertThat(signature.status()).isEqualTo("passed");
    }

    private OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightFoundationController controller() {
        return new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightFoundationController(
                new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCatalogService(),
                new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightIdentitySignatureService()
        );
    }
}
