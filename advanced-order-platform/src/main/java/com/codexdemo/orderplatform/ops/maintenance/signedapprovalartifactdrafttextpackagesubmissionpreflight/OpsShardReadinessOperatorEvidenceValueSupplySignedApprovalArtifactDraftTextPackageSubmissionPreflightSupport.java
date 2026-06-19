package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight;

import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1286";
  static final String SOURCE_NODE_REVIEW_PREFLIGHT_VERSION = "Node v1261";
  static final String SOURCE_JAVA_REVIEW_PREFLIGHT_VERSION = "Java v959";
  static final String SUBMISSION_PREFLIGHT_STATE = "slots-only";
  static final String SUBMITTED_PACKAGE_STATE = "not-accepted";
  static final String DRAFT_TEXT_PARSE_STATE = "not-parsed";
  static final String DETACHED_SIGNATURE_PARSE_STATE = "not-parsed";
  static final String APPROVAL_GRANT_STATE = "not-emitted";
  static final String VALUE_IMPORT_STATE = "locked";
  static final String RUNTIME_STATE = "locked";
  static final String SIBLING_MUTATION_STATE = "locked";

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSupport() {}

  static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
      response(
          String version,
          String endpoint,
          String profile,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
                      .SubmissionSlot>
              slots,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
                      .ComparisonControl>
              comparisonControls,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
                      .SubmissionGate>
              gates,
          List<String> additionalChecks) {
    var slotCopy = List.copyOf(slots);
    var controlCopy = List.copyOf(comparisonControls);
    var gateCopy = List.copyOf(gates);
    int passedSlotCount =
        (int) slotCopy.stream().filter(slot -> "passed".equals(slot.status())).count();
    int passedControlCount =
        (int) controlCopy.stream().filter(control -> "passed".equals(control.status())).count();
    List<String> checks = new ArrayList<>();
    checks.add(
        "signed-approval-artifact-draft-text-package-submission-preflight-slot-count-"
            + slotCopy.size());
    checks.add(
        "signed-approval-artifact-draft-text-package-submission-preflight-passed-slot-count-"
            + passedSlotCount);
    checks.add(
        "signed-approval-artifact-draft-text-package-submission-preflight-comparison-control-count-"
            + controlCopy.size());
    checks.add(
        "signed-approval-artifact-draft-text-package-submission-preflight-passed-comparison-control-count-"
            + passedControlCount);
    checks.add(
        "signed-approval-artifact-draft-text-package-submission-preflight-gate-count-"
            + gateCopy.size());
    checks.add(
        "signed-approval-artifact-draft-text-package-submission-preflight-source-plan-"
            + SOURCE_PLAN);
    checks.add(
        "signed-approval-artifact-draft-text-package-submission-preflight-source-node-review-"
            + SOURCE_NODE_REVIEW_PREFLIGHT_VERSION);
    checks.add(
        "signed-approval-artifact-draft-text-package-submission-preflight-source-java-review-"
            + SOURCE_JAVA_REVIEW_PREFLIGHT_VERSION);
    checks.add(
        "signed-approval-artifact-draft-text-package-submission-preflight-no-package-acceptance");
    checks.add(
        "signed-approval-artifact-draft-text-package-submission-preflight-no-draft-text-parsing");
    checks.add(
        "signed-approval-artifact-draft-text-package-submission-preflight-no-detached-signature-parsing");
    checks.add(
        "signed-approval-artifact-draft-text-package-submission-preflight-no-approval-grant");
    checks.add(
        "signed-approval-artifact-draft-text-package-submission-preflight-no-runtime-or-sibling-mutation");
    checks.addAll(additionalChecks);

    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse(
        PROJECT,
        version,
        true,
        false,
        true,
        SOURCE_PLAN,
        SOURCE_NODE_REVIEW_PREFLIGHT_VERSION,
        SOURCE_JAVA_REVIEW_PREFLIGHT_VERSION,
        SUBMISSION_PREFLIGHT_STATE,
        SUBMITTED_PACKAGE_STATE,
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
        slotCopy.size(),
        passedSlotCount,
        controlCopy.size(),
        passedControlCount,
        gateCopy.size(),
        slotCopy,
        controlCopy,
        gateCopy,
        List.copyOf(checks),
        passedSlotCount == slotCopy.size() && passedControlCount == controlCopy.size()
            ? "passed"
            : "blocked");
  }

  static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
          .SubmissionSlot
      slot(
          String code,
          String versionRange,
          String submissionSlot,
          String comparisonQuestion,
          String materialComparisonControl,
          String sourceEndpoint) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
        .SubmissionSlot(
        code,
        versionRange,
        submissionSlot,
        comparisonQuestion,
        materialComparisonControl,
        sourceEndpoint,
        "passed");
  }

  static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
          .ComparisonControl
      control(String code, String category, String control, String rejectionCode) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
        .ComparisonControl(code, category, control, rejectionCode, "fail-closed", "passed");
  }

  static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
          .SubmissionGate
      gate(String code, String category, String gate) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
        .SubmissionGate(code, category, gate, "fail-closed");
  }
}
