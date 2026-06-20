package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonacceptanceprecheck;

import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1321";
  static final String SOURCE_NODE_COMPARISON_PREFLIGHT_VERSION = "Node v1311";
  static final String SOURCE_JAVA_COMPARISON_PREFLIGHT_VERSION = "Java v1004";
  static final String ACCEPTANCE_PRECHECK_STATE = "checkpoints-only";
  static final String COMPARED_PACKAGE_STATE = "not-accepted";
  static final String SIGNED_DRAFT_TEXT_PARSE_STATE = "not-parsed";
  static final String DETACHED_SIGNATURE_PARSE_STATE = "not-parsed";
  static final String APPROVAL_GRANT_STATE = "not-emitted";
  static final String RUNTIME_PAYLOAD_STATE = "locked";
  static final String SIBLING_MUTATION_STATE = "locked";

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckSupport() {}

  static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckResponse
      response(
          String version,
          String endpoint,
          String profile,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckResponse
                      .AcceptanceCheckpoint>
              checkpoints,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckResponse
                      .MissingEvidenceGuard>
              guards,
          List<String> additionalChecks) {
    var checkpointCopy = List.copyOf(checkpoints);
    var guardCopy = List.copyOf(guards);
    int passedCheckpointCount =
        (int)
            checkpointCopy.stream()
                .filter(checkpoint -> "passed".equals(checkpoint.status()))
                .count();
    int passedGuardCount =
        (int) guardCopy.stream().filter(guard -> "passed".equals(guard.status())).count();
    List<String> checks = new ArrayList<>();
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-acceptance-precheck-checkpoint-count-"
            + checkpointCopy.size());
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-acceptance-precheck-guard-count-"
            + guardCopy.size());
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-acceptance-precheck-source-plan-"
            + SOURCE_PLAN);
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-acceptance-precheck-source-node-"
            + SOURCE_NODE_COMPARISON_PREFLIGHT_VERSION);
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-acceptance-precheck-source-java-"
            + SOURCE_JAVA_COMPARISON_PREFLIGHT_VERSION);
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-acceptance-precheck-no-package-acceptance");
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-acceptance-precheck-no-draft-text-parsing");
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-acceptance-precheck-no-signature-parsing");
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-acceptance-precheck-no-approval-grant");
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-acceptance-precheck-no-runtime");
    checks.add(
        "signed-approval-artifact-draft-text-package-comparison-acceptance-precheck-no-sibling-mutation");
    checks.addAll(additionalChecks);

    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckResponse(
        PROJECT,
        version,
        true,
        false,
        true,
        SOURCE_PLAN,
        SOURCE_NODE_COMPARISON_PREFLIGHT_VERSION,
        SOURCE_JAVA_COMPARISON_PREFLIGHT_VERSION,
        ACCEPTANCE_PRECHECK_STATE,
        COMPARED_PACKAGE_STATE,
        SIGNED_DRAFT_TEXT_PARSE_STATE,
        DETACHED_SIGNATURE_PARSE_STATE,
        APPROVAL_GRANT_STATE,
        RUNTIME_PAYLOAD_STATE,
        SIBLING_MUTATION_STATE,
        false,
        false,
        false,
        false,
        false,
        false,
        endpoint,
        profile,
        checkpointCopy.size(),
        passedCheckpointCount,
        guardCopy.size(),
        passedGuardCount,
        checkpointCopy,
        guardCopy,
        List.copyOf(checks),
        passedCheckpointCount == checkpointCopy.size() && passedGuardCount == guardCopy.size()
            ? "passed"
            : "blocked");
  }

  static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckResponse
          .AcceptanceCheckpoint
      checkpoint(
          String code,
          String sourceVersion,
          String checkpoint,
          String acceptanceQuestion,
          String missingEvidenceGuard,
          String sourceEndpoint) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckResponse
        .AcceptanceCheckpoint(
        code,
        sourceVersion,
        checkpoint,
        acceptanceQuestion,
        missingEvidenceGuard,
        sourceEndpoint,
        "passed");
  }

  static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckResponse
          .MissingEvidenceGuard
      guard(String code, String category, String guard, String rejectionCode) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckResponse
        .MissingEvidenceGuard(code, category, guard, rejectionCode, "fail-closed", "passed");
  }
}
