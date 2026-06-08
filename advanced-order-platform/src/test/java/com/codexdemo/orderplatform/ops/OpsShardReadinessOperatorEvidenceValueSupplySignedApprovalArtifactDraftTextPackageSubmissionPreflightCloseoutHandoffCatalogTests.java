package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutHandoffCatalogTests {

    @Test
    void exposesTwentyFiveHandoffItemsFromFoundationAndAssuranceCatalogs() {
        var foundation = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutHandoffCatalog
                .foundationItems();
        var assurance = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutHandoffCatalog
                .assuranceItems();
        var all = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutHandoffCatalog
                .allItems();

        assertThat(foundation).hasSize(12);
        assertThat(assurance).hasSize(13);
        assertThat(all).hasSize(25);
        assertThat(all).extracting(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse
                        .HandoffItem::status
        ).containsOnly("passed");
        assertThat(all).anySatisfy(item -> {
            assertThat(item.code()).isEqualTo("submission-closeout-runtime-payload-absence");
            assertThat(item.evidence()).contains("cannot materialize runtime input");
        });
    }
}

