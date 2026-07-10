package com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedPackageReviewRoutePathsTests {

  @Test
  void comparedPackageReviewRoutesRemainReadOnlyCatalogStyleRoutes() {
    assertThat(OpsShardReadinessComparedPackageReviewRoutePaths.COMPARED_PACKAGE_REVIEW_CATALOG)
        .isEqualTo(
            "/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-package-review-catalog");
    assertThat(OpsShardReadinessComparedPackageReviewRoutePaths.CATALOG)
        .startsWith(OpsShardReadinessComparedPackageReviewRoutePaths.BASE_PATH);
    assertThat(OpsShardReadinessComparedPackageReviewRoutePaths.SOURCE_EVIDENCE)
        .endsWith("compared-package-review-source-evidence");
    assertThat(OpsShardReadinessComparedPackageReviewRoutePaths.COMPARISON_OUTCOME)
        .endsWith("compared-package-review-comparison-outcome");
    assertThat(OpsShardReadinessComparedPackageReviewRoutePaths.IDENTITY_DIGEST)
        .endsWith("compared-package-review-identity-digest");
    assertThat(OpsShardReadinessComparedPackageReviewRoutePaths.POLICY_ARCHIVE)
        .endsWith("compared-package-review-policy-archive");
    assertThat(OpsShardReadinessComparedPackageReviewRoutePaths.HANDOFF_CLOSEOUT)
        .endsWith("compared-package-review-handoff-closeout");
  }
}
