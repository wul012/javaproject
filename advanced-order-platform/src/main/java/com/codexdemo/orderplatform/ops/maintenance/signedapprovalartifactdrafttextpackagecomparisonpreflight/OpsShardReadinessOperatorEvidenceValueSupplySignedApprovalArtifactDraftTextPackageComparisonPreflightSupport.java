package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight;

import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1311";
  static final String SOURCE_NODE_SUBMISSION_PREFLIGHT_VERSION = "Node v1286";
  static final String SOURCE_JAVA_SUBMISSION_PREFLIGHT_VERSION = "Java v969";
  static final String SOURCE_JAVA_SUBMISSION_CLOSEOUT_VERSION = "Java v994";
  static final String COMPARISON_PREFLIGHT_STATE = "lanes-only";
  static final String SUBMITTED_PACKAGE_STATE = "not-accepted";
  static final String COMPARISON_STATE = "not-performed";
  static final String DRAFT_TEXT_PARSE_STATE = "not-parsed";
  static final String DETACHED_SIGNATURE_PARSE_STATE = "not-parsed";
  static final String APPROVAL_GRANT_STATE = "not-emitted";
  static final String VALUE_IMPORT_STATE = "locked";
  static final String RUNTIME_STATE = "locked";
  static final String SIBLING_MUTATION_STATE = "locked";

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightSupport() {}

  static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
      response(
          String version,
          String endpoint,
          String profile,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
                      .ComparisonLane>
              lanes,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
                      .AcceptanceControl>
              controls,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
                      .ComparisonGate>
              gates,
          List<String> additionalChecks) {
    var laneCopy = List.copyOf(lanes);
    var controlCopy = List.copyOf(controls);
    var gateCopy = List.copyOf(gates);
    int passedLaneCount =
        (int) laneCopy.stream().filter(lane -> "passed".equals(lane.status())).count();
    int passedControlCount =
        (int) controlCopy.stream().filter(control -> "passed".equals(control.status())).count();
    List<String> checks = new ArrayList<>();
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-preflight-lane-count-"
            + laneCopy.size());
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-preflight-passed-lane-count-"
            + passedLaneCount);
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-preflight-acceptance-control-count-"
            + controlCopy.size());
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-preflight-passed-acceptance-control-count-"
            + passedControlCount);
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-preflight-source-plan-"
            + SOURCE_PLAN);
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-preflight-source-node-submission-"
            + SOURCE_NODE_SUBMISSION_PREFLIGHT_VERSION);
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-preflight-source-java-submission-"
            + SOURCE_JAVA_SUBMISSION_PREFLIGHT_VERSION);
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-preflight-source-java-closeout-"
            + SOURCE_JAVA_SUBMISSION_CLOSEOUT_VERSION);
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-preflight-no-package-acceptance");
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-preflight-no-draft-text-parsing");
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-preflight-no-detached-signature-parsing");
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-preflight-no-approval-grant");
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-preflight-no-runtime-or-sibling-mutation");
    checks.addAll(additionalChecks);

    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse(
        PROJECT,
        version,
        true,
        false,
        true,
        SOURCE_PLAN,
        SOURCE_NODE_SUBMISSION_PREFLIGHT_VERSION,
        SOURCE_JAVA_SUBMISSION_PREFLIGHT_VERSION,
        SOURCE_JAVA_SUBMISSION_CLOSEOUT_VERSION,
        COMPARISON_PREFLIGHT_STATE,
        SUBMITTED_PACKAGE_STATE,
        COMPARISON_STATE,
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
        endpoint,
        profile,
        laneCopy.size(),
        passedLaneCount,
        controlCopy.size(),
        passedControlCount,
        gateCopy.size(),
        laneCopy,
        controlCopy,
        gateCopy,
        List.copyOf(checks),
        passedLaneCount == laneCopy.size() && passedControlCount == controlCopy.size()
            ? "passed"
            : "blocked");
  }

  static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
          .ComparisonLane
      lane(
          String code,
          String versionRange,
          String comparisonLane,
          String comparisonQuestion,
          String acceptanceControl,
          String sourceEndpoint) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
        .ComparisonLane(
        code,
        versionRange,
        comparisonLane,
        comparisonQuestion,
        acceptanceControl,
        sourceEndpoint,
        "passed");
  }

  static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
          .AcceptanceControl
      control(String code, String category, String control, String rejectionCode) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
        .AcceptanceControl(code, category, control, rejectionCode, "fail-closed", "passed");
  }

  static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
          .ComparisonGate
      gate(String code, String category, String gate) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
        .ComparisonGate(code, category, gate, "fail-closed");
  }
}
