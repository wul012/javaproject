package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeFoundationControllerTests {

    @Test
    void exposesCatalogThroughFoundationController() {
        var response = controller().catalog();

        assertThat(response.version()).isEqualTo("Java v921");
        assertThat(response.fieldCount()).isEqualTo(25);
    }

    @Test
    void exposesIdentityCorrelationThroughFoundationController() {
        var response = controller().identityCorrelation();

        assertThat(response.version()).isEqualTo("Java v922");
        assertThat(response.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeIdentityCorrelationService
                        .ENDPOINT);
    }

    @Test
    void exposesDigestBindingThroughFoundationController() {
        var response = controller().digestBinding();

        assertThat(response.version()).isEqualTo("Java v923");
        assertThat(response.readyForDraftTextPackageReview()).isFalse();
    }

    @Test
    void exposesSignatureEnvelopeThroughFoundationController() {
        var response = controller().signatureEnvelope();

        assertThat(response.version()).isEqualTo("Java v924");
        assertThat(response.readyForDetachedSignature()).isFalse();
    }

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeFoundationController
    controller() {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeFoundationController(
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeCatalogService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeIdentityCorrelationService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeDigestBindingService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSignatureEnvelopeService()
        );
    }
}
