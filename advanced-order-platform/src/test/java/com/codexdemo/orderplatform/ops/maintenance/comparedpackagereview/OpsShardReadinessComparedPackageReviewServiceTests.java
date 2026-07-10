package com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedPackageReviewServiceTests {

  @Test
  void exposesScopedReviewSlicesWithoutWideningLocks() {
    assertSlice(
        new OpsShardReadinessComparedPackageReviewSourceEvidenceService().sourceEvidence(),
        "Java v1035",
        3,
        3,
        1);
    assertSlice(
        new OpsShardReadinessComparedPackageReviewComparisonOutcomeService().comparisonOutcome(),
        "Java v1036",
        3,
        3,
        1);
    assertSlice(
        new OpsShardReadinessComparedPackageReviewIdentityDigestService().identityDigest(),
        "Java v1037",
        3,
        3,
        1);
    assertSlice(
        new OpsShardReadinessComparedPackageReviewPolicyArchiveService().policyArchive(),
        "Java v1038",
        3,
        3,
        2);
  }

  @Test
  void handoffCloseoutKeepsFullCatalogReadOnly() {
    var response =
        new OpsShardReadinessComparedPackageReviewHandoffCloseoutService().handoffCloseout();

    assertThat(response.version()).isEqualTo("Java v1039");
    assertThat(response.reviewSlotCount()).isEqualTo(12);
    assertThat(response.guardCount()).isEqualTo(12);
    assertThat(response.reviewerGroupCount()).isEqualTo(5);
    assertThat(response.readyForRuntimePayload()).isFalse();
  }

  private void assertSlice(
      OpsShardReadinessComparedPackageReviewResponse response,
      String version,
      int slots,
      int guards,
      int reviewerGroups) {
    assertThat(response.version()).isEqualTo(version);
    assertThat(response.reviewSlotCount()).isEqualTo(slots);
    assertThat(response.guardCount()).isEqualTo(guards);
    assertThat(response.reviewerGroupCount()).isEqualTo(reviewerGroups);
    assertThat(response.readyForApprovalGrant()).isFalse();
  }
}
