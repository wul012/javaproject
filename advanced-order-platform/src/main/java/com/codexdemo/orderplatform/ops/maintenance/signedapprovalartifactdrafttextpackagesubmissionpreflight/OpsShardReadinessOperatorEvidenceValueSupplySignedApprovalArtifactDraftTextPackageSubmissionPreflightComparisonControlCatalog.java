package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight;

import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightComparisonControlCatalog {

  static final int CONTROL_COUNT = 25;
  static final int GATE_COUNT = 10;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightComparisonControlCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
              .ComparisonControl>
      allControls() {
    return List.of(
        control(
            "SUBMISSION_REQUEST_MANIFEST_CONTROL", "identity", "Reject missing requestManifestId."),
        control(
            "SUBMISSION_CORRELATION_CONTROL", "identity", "Reject missing requestCorrelationId."),
        control("SUBMISSION_OPERATOR_ID_CONTROL", "identity", "Reject missing operatorId."),
        control("SUBMISSION_PACKAGE_ID_CONTROL", "identity", "Reject missing draftTextPackageId."),
        control(
            "SUBMISSION_INSTRUCTION_DIGEST_CONTROL",
            "digest",
            "Reject instruction digest mismatch."),
        control(
            "SUBMISSION_AUTHORING_DIGEST_CONTROL", "digest", "Reject authoring digest mismatch."),
        control("SUBMISSION_ARTIFACT_DIGEST_CONTROL", "digest", "Reject artifact digest mismatch."),
        control(
            "SUBMISSION_FIELD_MAP_DIGEST_CONTROL", "digest", "Reject field map digest mismatch."),
        control(
            "SUBMISSION_SIGNATURE_ENVELOPE_CONTROL",
            "signature",
            "Reject signature envelope payload."),
        control(
            "SUBMISSION_SIGNATURE_ALGORITHM_CONTROL",
            "signature",
            "Reject signature algorithm mismatch."),
        control(
            "SUBMISSION_SIGNATURE_REDACTION_CONTROL",
            "signature",
            "Reject raw detached signature text."),
        control(
            "SUBMISSION_SOURCE_PLAN_CONTROL", "evidence", "Reject missing source plan version."),
        control("SUBMISSION_SOURCE_FILES_CONTROL", "evidence", "Reject raw source file payload."),
        control("SUBMISSION_SOURCE_SNIPPET_CONTROL", "evidence", "Reject raw source snippet text."),
        control("SUBMISSION_VALUE_HANDLE_CONTROL", "value", "Reject raw operator value."),
        control(
            "SUBMISSION_VALUE_DIGEST_CONTROL", "value", "Reject redacted value digest mismatch."),
        control(
            "SUBMISSION_REDACTION_POLICY_CONTROL", "policy", "Reject missing redaction policy."),
        control(
            "SUBMISSION_PROVENANCE_POLICY_CONTROL", "policy", "Reject missing provenance policy."),
        control(
            "SUBMISSION_REVIEW_STATE_CONTROL",
            "policy",
            "Reject accepted or approval-ready state."),
        control("SUBMISSION_WRITE_ROUTE_LOCK_CONTROL", "lock", "Reject open write route."),
        control(
            "SUBMISSION_RUNTIME_PAYLOAD_LOCK_CONTROL", "lock", "Reject runtime payload material."),
        control("SUBMISSION_JAVA_STARTUP_LOCK_CONTROL", "lock", "Reject Java startup request."),
        control(
            "SUBMISSION_MINI_KV_STARTUP_LOCK_CONTROL", "lock", "Reject mini-kv startup request."),
        control(
            "SUBMISSION_SIBLING_MUTATION_LOCK_CONTROL", "lock", "Reject sibling mutation request."),
        control(
            "SUBMISSION_ARCHIVE_CLOSEOUT_CONTROL",
            "closeout",
            "Reject missing archive closeout manifest."));
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
              .ComparisonControl>
      controls(int fromInclusive, int toExclusive) {
    return List.copyOf(allControls().subList(fromInclusive, toExclusive));
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
              .SubmissionGate>
      allGates() {
    return List.of(
        gate(
            "SUBMISSION_PREFLIGHT_GATE_01",
            "submission",
            "Submission preflight remains slots-only."),
        gate("SUBMISSION_PREFLIGHT_GATE_02", "acceptance", "Submitted package is not accepted."),
        gate("SUBMISSION_PREFLIGHT_GATE_03", "draft-text", "Signed draft text is not parsed."),
        gate(
            "SUBMISSION_PREFLIGHT_GATE_04",
            "signature",
            "Detached signature payload is not parsed."),
        gate("SUBMISSION_PREFLIGHT_GATE_05", "approval", "Approval grants remain disabled."),
        gate("SUBMISSION_PREFLIGHT_GATE_06", "value", "Operator value import remains locked."),
        gate("SUBMISSION_PREFLIGHT_GATE_07", "runtime", "Runtime and startup remain locked."),
        gate("SUBMISSION_PREFLIGHT_GATE_08", "sibling", "Sibling mutation remains blocked."),
        gate(
            "SUBMISSION_PREFLIGHT_GATE_09",
            "catalog",
            "Twenty-five slots and controls are present."),
        gate(
            "SUBMISSION_PREFLIGHT_GATE_10",
            "closeout",
            "Closeout stops before manual package acceptance."));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
          .ComparisonControl
      control(String code, String category, String control) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSupport
        .control(code, category, control, "REJECT_DRAFT_TEXT_PACKAGE_" + code);
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
          .SubmissionGate
      gate(String code, String category, String gate) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSupport
        .gate(code, category, gate);
  }
}
