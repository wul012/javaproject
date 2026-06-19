package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagereviewpreflight;

import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1261";
  static final String SOURCE_NODE_TEXT_PACKAGE_INTAKE_VERSION = "Node v1236";
  static final String SOURCE_JAVA_TEXT_PACKAGE_INTAKE_VERSION = "Java v934";
  static final String REVIEW_PREFLIGHT_STATE = "criteria-only";
  static final String DRAFT_TEXT_PARSE_STATE = "not-parsed";
  static final String DETACHED_SIGNATURE_PARSE_STATE = "not-parsed";
  static final String APPROVAL_GRANT_STATE = "not-emitted";
  static final String VALUE_IMPORT_STATE = "locked";
  static final String RUNTIME_STATE = "locked";
  static final String SIBLING_MUTATION_STATE = "locked";

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightSupport() {}

  static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
      response(
          String version,
          String endpoint,
          String profile,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
                      .ReviewCriterion>
              criteria,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
                      .RejectionControl>
              rejectionControls,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
                      .ReviewGate>
              gates,
          List<String> additionalChecks) {
    var criterionCopy = List.copyOf(criteria);
    var rejectionControlCopy = List.copyOf(rejectionControls);
    var gateCopy = List.copyOf(gates);
    int passedCriterionCount =
        (int)
            criterionCopy.stream().filter(criterion -> "passed".equals(criterion.status())).count();
    int passedRejectionControlCount =
        (int)
            rejectionControlCopy.stream()
                .filter(control -> "passed".equals(control.status()))
                .count();
    List<String> checks = new ArrayList<>();
    checks.add(
        "signed-approval-artifact-draft-text-package-review-preflight-criterion-count-"
            + criterionCopy.size());
    checks.add(
        "signed-approval-artifact-draft-text-package-review-preflight-passed-criterion-count-"
            + passedCriterionCount);
    checks.add(
        "signed-approval-artifact-draft-text-package-review-preflight-rejection-control-count-"
            + rejectionControlCopy.size());
    checks.add(
        "signed-approval-artifact-draft-text-package-review-preflight-passed-rejection-control-count-"
            + passedRejectionControlCount);
    checks.add(
        "signed-approval-artifact-draft-text-package-review-preflight-gate-count-"
            + gateCopy.size());
    checks.add(
        "signed-approval-artifact-draft-text-package-review-preflight-source-plan-" + SOURCE_PLAN);
    checks.add(
        "signed-approval-artifact-draft-text-package-review-preflight-source-node-intake-"
            + SOURCE_NODE_TEXT_PACKAGE_INTAKE_VERSION);
    checks.add(
        "signed-approval-artifact-draft-text-package-review-preflight-source-java-intake-"
            + SOURCE_JAVA_TEXT_PACKAGE_INTAKE_VERSION);
    checks.add(
        "signed-approval-artifact-draft-text-package-review-preflight-no-draft-text-parsing");
    checks.add(
        "signed-approval-artifact-draft-text-package-review-preflight-no-detached-signature-parsing");
    checks.add("signed-approval-artifact-draft-text-package-review-preflight-no-approval-grant");
    checks.add("signed-approval-artifact-draft-text-package-review-preflight-no-value-import");
    checks.add(
        "signed-approval-artifact-draft-text-package-review-preflight-no-runtime-or-sibling-mutation");
    checks.addAll(additionalChecks);

    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse(
        PROJECT,
        version,
        true,
        false,
        true,
        SOURCE_PLAN,
        SOURCE_NODE_TEXT_PACKAGE_INTAKE_VERSION,
        SOURCE_JAVA_TEXT_PACKAGE_INTAKE_VERSION,
        REVIEW_PREFLIGHT_STATE,
        DRAFT_TEXT_PARSE_STATE,
        DETACHED_SIGNATURE_PARSE_STATE,
        APPROVAL_GRANT_STATE,
        VALUE_IMPORT_STATE,
        RUNTIME_STATE,
        SIBLING_MUTATION_STATE,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        endpoint,
        profile,
        criterionCopy.size(),
        passedCriterionCount,
        rejectionControlCopy.size(),
        passedRejectionControlCount,
        gateCopy.size(),
        criterionCopy,
        rejectionControlCopy,
        gateCopy,
        List.copyOf(checks),
        passedCriterionCount == criterionCopy.size()
                && passedRejectionControlCount == rejectionControlCopy.size()
            ? "passed"
            : "blocked");
  }

  static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
          .ReviewCriterion
      criterion(
          String code,
          String versionRange,
          String reviewCriterion,
          String reviewQuestion,
          String materialRejectionControl,
          String sourceEndpoint) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
        .ReviewCriterion(
        code,
        versionRange,
        reviewCriterion,
        reviewQuestion,
        materialRejectionControl,
        sourceEndpoint,
        "passed");
  }

  static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
          .RejectionControl
      control(
          String code, String category, String control, String rejectionCode, String enforcement) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
        .RejectionControl(code, category, control, rejectionCode, enforcement, "passed");
  }

  static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
          .ReviewGate
      gate(String code, String category, String gate, String enforcement) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
        .ReviewGate(code, category, gate, enforcement);
  }
}
