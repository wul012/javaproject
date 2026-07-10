package com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview;

public final class OpsShardReadinessComparedPackageReviewRoutePaths {

  public static final String BASE_PATH = "/api/v1/ops/shard-readiness";

  public static final String COMPARED_PACKAGE_REVIEW_CATALOG =
      "/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-package-review-catalog";
  public static final String COMPARED_PACKAGE_REVIEW_SOURCE_EVIDENCE =
      "/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-package-review-source-evidence";
  public static final String COMPARED_PACKAGE_REVIEW_COMPARISON_OUTCOME =
      "/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-package-review-comparison-outcome";
  public static final String COMPARED_PACKAGE_REVIEW_IDENTITY_DIGEST =
      "/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-package-review-identity-digest";
  public static final String COMPARED_PACKAGE_REVIEW_POLICY_ARCHIVE =
      "/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-package-review-policy-archive";
  public static final String COMPARED_PACKAGE_REVIEW_HANDOFF_CLOSEOUT =
      "/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-package-review-handoff-closeout";

  public static final String CATALOG = BASE_PATH + COMPARED_PACKAGE_REVIEW_CATALOG;
  public static final String SOURCE_EVIDENCE = BASE_PATH + COMPARED_PACKAGE_REVIEW_SOURCE_EVIDENCE;
  public static final String COMPARISON_OUTCOME =
      BASE_PATH + COMPARED_PACKAGE_REVIEW_COMPARISON_OUTCOME;
  public static final String IDENTITY_DIGEST = BASE_PATH + COMPARED_PACKAGE_REVIEW_IDENTITY_DIGEST;
  public static final String POLICY_ARCHIVE = BASE_PATH + COMPARED_PACKAGE_REVIEW_POLICY_ARCHIVE;
  public static final String HANDOFF_CLOSEOUT =
      BASE_PATH + COMPARED_PACKAGE_REVIEW_HANDOFF_CLOSEOUT;

  private OpsShardReadinessComparedPackageReviewRoutePaths() {}
}
