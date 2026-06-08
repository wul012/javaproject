package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightFoundationServiceTests {

    @Test
    void catalogExposesFullReviewPreflightWithoutAcceptance() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightCatalogService()
                        .catalog();

        assertThat(response.version()).isEqualTo("Java v946");
        assertThat(response.criterionCount()).isEqualTo(25);
        assertThat(response.rejectionControlCount()).isEqualTo(25);
        assertThat(response.gateCount()).isEqualTo(20);
        assertThat(response.readyForDraftTextPackageAcceptance()).isFalse();
    }

    @Test
    void identityCriteriaExposeRequestReviewOnly() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightIdentityCriteriaService()
                        .identityCriteria();

        assertThat(response.version()).isEqualTo("Java v947");
        assertThat(response.criterionCount()).isEqualTo(4);
        assertThat(response.readyForApprovalGrant()).isFalse();
    }

    @Test
    void digestRecheckExposesDigestControlsOnly() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightDigestRecheckService()
                        .digestRecheck();

        assertThat(response.version()).isEqualTo("Java v948");
        assertThat(response.criterionCount()).isEqualTo(4);
        assertThat(response.readyForDraftTextPackageAcceptance()).isFalse();
    }

    @Test
    void signatureEnvelopeDoesNotParseDetachedSignature() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightSignatureEnvelopeService()
                        .signatureEnvelope();

        assertThat(response.version()).isEqualTo("Java v949");
        assertThat(response.criterionCount()).isEqualTo(3);
        assertThat(response.readyForDetachedSignatureParsing()).isFalse();
    }
}
