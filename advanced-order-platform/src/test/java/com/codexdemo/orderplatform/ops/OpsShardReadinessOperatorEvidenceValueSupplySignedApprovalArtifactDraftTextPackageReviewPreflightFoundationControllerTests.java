package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightFoundationControllerTests {

    @Test
    void exposesFoundationReviewPreflightRoutes() {
        var controller = controller();

        assertThat(controller.catalog().version()).isEqualTo("Java v946");
        assertThat(controller.identityCriteria().version()).isEqualTo("Java v947");
        assertThat(controller.digestRecheck().version()).isEqualTo("Java v948");
        assertThat(controller.signatureEnvelope().version()).isEqualTo("Java v949");
        assertThat(controller.signatureEnvelope().readyForDetachedSignatureParsing()).isFalse();
    }

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightFoundationController
    controller() {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightFoundationController(
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightCatalogService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightIdentityCriteriaService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightDigestRecheckService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightSignatureEnvelopeService()
        );
    }
}
