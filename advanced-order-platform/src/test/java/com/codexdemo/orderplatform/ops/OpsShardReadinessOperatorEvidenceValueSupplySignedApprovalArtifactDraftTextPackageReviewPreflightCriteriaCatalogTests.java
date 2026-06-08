package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightCriteriaCatalogTests {

    @Test
    void combinesFoundationAndAssuranceCriteriaIntoTwentyFiveReviewChecks() {
        var foundation =
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightFoundationCriteriaCatalog
                        .foundationCriteria();
        var assurance =
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightAssuranceCriteriaCatalog
                        .assuranceCriteria();
        var criteria =
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightCriteriaCatalog
                        .allCriteria();

        assertThat(foundation)
                .hasSize(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightFoundationCriteriaCatalog
                        .FOUNDATION_CRITERION_COUNT);
        assertThat(assurance)
                .hasSize(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightAssuranceCriteriaCatalog
                        .ASSURANCE_CRITERION_COUNT);
        assertThat(criteria)
                .hasSize(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightCriteriaCatalog
                        .CRITERION_COUNT);
        assertThat(criteria).allSatisfy(criterion -> {
            assertThat(criterion.status()).isEqualTo("passed");
            assertThat(criterion.sourceEndpoint()).startsWith(OpsShardReadinessRoutePaths.BASE_PATH);
        });
        assertThat(criteria).extracting(
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
                                .ReviewCriterion::versionRange)
                .contains("Node v1237-v1240", "Node v1241-v1244", "Node v1256-v1260", "Node v1261");
    }

    @Test
    void criteriaSlicesPreserveDigestAndExecutionSegments() {
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightCriteriaCatalog
                .criteria(4, 8))
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
                        .ReviewCriterion::versionRange)
                .containsOnly("Node v1241-v1244");
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightCriteriaCatalog
                .criteria(19, 24))
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
                        .ReviewCriterion::versionRange)
                .containsOnly("Node v1256-v1260");
    }
}
