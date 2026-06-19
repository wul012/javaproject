package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagereviewpreflight;

import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightRejectionControlCatalog {

  static final int CONTROL_COUNT = 25;
  static final int GATE_COUNT = 20;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightRejectionControlCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
              .RejectionControl>
      allControls() {
    return List.of(
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_REQUEST_MANIFEST_CONTROL", "identity",
            "Reject missing or changed request manifest id.",
                "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_REQUEST_MANIFEST"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_CORRELATION_CONTROL", "identity",
            "Reject missing or changed correlation id.",
                "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_CORRELATION"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_OPERATOR_ID_CONTROL", "identity",
            "Reject missing operator identity.", "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_OPERATOR_ID"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_PACKAGE_ID_CONTROL", "identity",
            "Reject missing package identity.", "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_PACKAGE_ID"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_INSTRUCTION_DIGEST_CONTROL", "digest",
            "Reject instruction preflight digest mismatch.",
                "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_INSTRUCTION_DIGEST"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_AUTHORING_DIGEST_CONTROL", "digest",
            "Reject authoring readiness digest mismatch.",
                "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_AUTHORING_DIGEST"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_ARTIFACT_DIGEST_CONTROL", "digest",
            "Reject artifact digest mismatch.", "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_ARTIFACT_DIGEST"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_FIELD_MAP_DIGEST_CONTROL", "digest",
            "Reject field map digest mismatch.",
                "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_FIELD_MAP_DIGEST"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_SIGNATURE_ENVELOPE_CONTROL", "signature",
            "Reject detached signature envelope payload material.",
                "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_SIGNATURE_ENVELOPE_PAYLOAD"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_SIGNATURE_ALGORITHM_CONTROL", "signature",
            "Reject signature algorithm policy mismatch.",
                "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_SIGNATURE_ALGORITHM"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_SIGNATURE_REDACTION_CONTROL", "signature",
            "Reject raw detached signature text.",
                "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_SIGNATURE_RAW_TEXT"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_SOURCE_PLAN_CONTROL", "evidence",
            "Reject missing source plan version.", "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_SOURCE_PLAN"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_SOURCE_FILES_CONTROL", "evidence",
            "Reject raw source file payload.", "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_SOURCE_FILES_RAW"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_SOURCE_SNIPPET_CONTROL", "evidence",
            "Reject raw source snippet text.",
                "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_SOURCE_SNIPPET_RAW"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_VALUE_HANDLE_CONTROL", "value",
            "Reject raw operator value.", "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_RAW_OPERATOR_VALUE"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_REDACTED_VALUE_DIGEST_CONTROL", "value",
            "Reject redacted value digest mismatch.",
                "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_VALUE_DIGEST"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_REDACTION_POLICY_CONTROL", "policy",
            "Reject missing redaction policy.",
                "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_REDACTION_POLICY"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_PROVENANCE_POLICY_CONTROL", "policy",
            "Reject missing provenance policy.",
                "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_PROVENANCE_POLICY"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_REVIEW_STATE_CONTROL", "policy",
            "Reject approved, accepted, or grant-ready state.",
                "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_STATE_APPROVED"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_WRITE_ROUTE_CONTROL", "lock",
            "Reject open write route.", "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_WRITE_ROUTE_OPEN"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_RUNTIME_PAYLOAD_CONTROL", "lock",
            "Reject runtime payload material.", "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_RUNTIME_PAYLOAD"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_JAVA_STARTUP_CONTROL", "lock",
            "Reject Java startup request.", "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_JAVA_STARTUP"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_MINI_KV_STARTUP_CONTROL", "lock",
            "Reject mini-kv startup request.", "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_MINI_KV_STARTUP"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_SIBLING_MUTATION_CONTROL", "lock",
            "Reject sibling mutation request.",
                "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_SIBLING_MUTATION"),
        control(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_ARCHIVE_CLOSEOUT_CONTROL", "closeout",
            "Reject missing archive closeout manifest.",
                "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_ARCHIVE_CLOSEOUT"));
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
              .RejectionControl>
      controls(int fromInclusive, int toExclusive) {
    return List.copyOf(allControls().subList(fromInclusive, toExclusive));
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
              .ReviewGate>
      allGates() {
    return List.of(
        gate(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_01",
            "review",
            "Review preflight remains criteria-only."),
        gate(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_02",
            "draft-text",
            "Signed draft text is not parsed."),
        gate(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_03",
            "signature",
            "Detached signature payload is not parsed."),
        gate(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_04",
            "approval",
            "Approval grants remain disabled."),
        gate(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_05",
            "value",
            "Operator value import remains locked."),
        gate(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_06",
            "runtime",
            "Runtime payload creation remains locked."),
        gate(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_07",
            "runtime",
            "Java startup remains out of scope."),
        gate(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_08",
            "runtime",
            "mini-kv startup remains out of scope."),
        gate(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_09",
            "sibling",
            "Sibling mutation remains blocked."),
        gate(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_10",
            "catalog",
            "Twenty-five review criteria must be present."),
        gate(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_11",
            "catalog",
            "Twenty-five rejection controls must be present."),
        gate(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_12",
            "source",
            "Node v1261 review preflight remains pinned."),
        gate(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_13",
            "source",
            "Node v1236 intake remains pinned."),
        gate(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_14",
            "source",
            "Java v934 intake remains pinned."),
        gate(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_15",
            "secret",
            "Raw secret values remain absent."),
        gate(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_16",
            "digest",
            "Digest recheck controls are required before acceptance."),
        gate(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_17",
            "reviewer",
            "Separate-reviewer requirement is documented."),
        gate(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_18",
            "acceptance",
            "Package acceptance remains a future step."),
        gate(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_19",
            "archive",
            "Archive closeout review criterion must be present."),
        gate(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_20",
            "closeout",
            "Closeout stops before signed approval consideration."));
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
              .ReviewGate>
      gates(int fromInclusive, int toExclusive) {
    return List.copyOf(allGates().subList(fromInclusive, toExclusive));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
          .RejectionControl
      control(String code, String category, String control, String rejectionCode) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightSupport
        .control(code, category, control, rejectionCode, "fail-closed");
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
          .ReviewGate
      gate(String code, String category, String gate) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightSupport
        .gate(code, category, gate, "fail-closed");
  }
}
