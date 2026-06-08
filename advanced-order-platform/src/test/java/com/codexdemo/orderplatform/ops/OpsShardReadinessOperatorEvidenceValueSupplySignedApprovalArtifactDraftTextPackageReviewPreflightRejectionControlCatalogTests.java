package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightRejectionControlCatalogTests {

    @Test
    void controlsCoverAllCriteriaAndFailClosed() {
        var controls =
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightRejectionControlCatalog
                        .allControls();

        assertThat(controls)
                .hasSize(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightRejectionControlCatalog
                        .CONTROL_COUNT);
        assertThat(controls).allSatisfy(control -> {
            assertThat(control.status()).isEqualTo("passed");
            assertThat(control.enforcement()).isEqualTo("fail-closed");
            assertThat(control.rejectionCode()).startsWith("REJECT_DRAFT_TEXT_PACKAGE_REVIEW_");
        });
        assertThat(controls).extracting(
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
                                .RejectionControl::category)
                .contains("identity", "digest", "signature", "evidence", "value", "policy", "lock",
                        "closeout");
    }

    @Test
    void controlSlicesPreserveLockSegment() {
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightRejectionControlCatalog
                .controls(19, 24))
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
                        .RejectionControl::category)
                .containsOnly("lock");
    }
}
