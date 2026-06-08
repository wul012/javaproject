package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedPackageReviewRoutePathsTests {

    @Test
    void comparedPackageReviewRoutesRemainReadOnlyCatalogStyleRoutes() {
        assertThat(OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_REVIEW_CATALOG)
                .isEqualTo("/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-package-review-catalog");
        assertThat(OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_REVIEW_SOURCE_EVIDENCE)
                .endsWith("compared-package-review-source-evidence");
        assertThat(OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_REVIEW_COMPARISON_OUTCOME)
                .endsWith("compared-package-review-comparison-outcome");
        assertThat(OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_REVIEW_IDENTITY_DIGEST)
                .endsWith("compared-package-review-identity-digest");
        assertThat(OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_REVIEW_POLICY_ARCHIVE)
                .endsWith("compared-package-review-policy-archive");
        assertThat(OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_REVIEW_HANDOFF_CLOSEOUT)
                .endsWith("compared-package-review-handoff-closeout");
    }
}
