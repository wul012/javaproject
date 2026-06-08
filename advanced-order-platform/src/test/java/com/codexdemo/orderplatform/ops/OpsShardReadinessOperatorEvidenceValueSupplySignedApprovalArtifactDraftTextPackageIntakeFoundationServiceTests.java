package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeFoundationServiceTests {

    @Test
    void catalogExposesFullExpectedFieldContractWithoutPackageAcceptance() {
        var response = new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeCatalogService()
                .catalog();

        assertThat(response.version()).isEqualTo("Java v921");
        assertThat(response.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeCatalogService
                        .ENDPOINT);
        assertThat(response.fieldCount()).isEqualTo(25);
        assertThat(response.guardCount()).isEqualTo(25);
        assertThat(response.gateCount()).isEqualTo(20);
        assertThat(response.readyForDraftTextPackageReview()).isFalse();
        assertThat(response.status()).isEqualTo("passed");
    }

    @Test
    void identityCorrelationExposesOnlyRequestIdentityFields() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeIdentityCorrelationService()
                        .identityCorrelation();

        assertThat(response.version()).isEqualTo("Java v922");
        assertThat(response.fieldCount()).isEqualTo(4);
        assertThat(response.readyForApprovalGrant()).isFalse();
    }

    @Test
    void digestBindingExposesOnlyDigestFields() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeDigestBindingService()
                        .digestBinding();

        assertThat(response.version()).isEqualTo("Java v923");
        assertThat(response.fieldCount()).isEqualTo(4);
        assertThat(response.readyForDraftTextPackageReview()).isFalse();
    }

    @Test
    void signatureEnvelopeExposesMetadataWithoutDetachedSignature() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSignatureEnvelopeService()
                        .signatureEnvelope();

        assertThat(response.version()).isEqualTo("Java v924");
        assertThat(response.fieldCount()).isEqualTo(3);
        assertThat(response.readyForDetachedSignature()).isFalse();
        assertThat(response.signatureEnvelopeState()).isEqualTo("not-accepted");
    }
}
