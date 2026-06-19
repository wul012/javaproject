package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight;

import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightGuardCatalog {

  static final int GUARD_COUNT = 25;
  static final int GATE_COUNT = 20;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightGuardCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
              .InstructionGuard>
      allGuards() {
    return List.of(
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_REQUEST_MANIFEST_SLOT_GUARD",
            "request",
            "Reject instruction preflight when request manifest slot is absent.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_REQUEST_MANIFEST_MISSING",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_ARTIFACT_PREFLIGHT_DIGEST_SLOT_GUARD",
            "digest",
            "Reject instruction preflight when artifact preflight digest is absent.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_ARTIFACT_PREFLIGHT_DIGEST_MISSING",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_TEMPLATE_DIGEST_SLOT_GUARD",
            "digest",
            "Reject instruction preflight when template digest is absent.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_TEMPLATE_DIGEST_MISSING",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_REVIEW_DIGEST_SLOT_GUARD",
            "digest",
            "Reject instruction preflight when review digest is absent.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_REVIEW_DIGEST_MISSING",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_OPERATOR_IDENTITY_SLOT_GUARD",
            "operator",
            "Reject instruction preflight when operator identity is absent.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_OPERATOR_IDENTITY_MISSING",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_OPERATOR_ROLE_SLOT_GUARD",
            "operator",
            "Reject instruction preflight when operator role is absent.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_OPERATOR_ROLE_MISSING",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_WINDOW_ID_SLOT_GUARD",
            "capture-policy",
            "Reject instruction preflight when capture window is absent.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_WINDOW_ID_MISSING",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_CHANNEL_POLICY_SLOT_GUARD",
            "capture-policy",
            "Reject instruction preflight when channel policy is absent.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_CHANNEL_POLICY_MISSING",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_SIGNATURE_ALGORITHM_SLOT_GUARD",
            "signature",
            "Reject instruction preflight when signature algorithm policy is absent.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_SIGNATURE_ALGORITHM_MISSING",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_DETACHED_SIGNATURE_SLOT_GUARD",
            "signature",
            "Reject instruction preflight if detached signature material is present.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_DETACHED_SIGNATURE_PRESENT",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_SIGNATURE_REDACTION_SLOT_GUARD",
            "signature",
            "Reject instruction preflight when signature redaction policy is absent.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_SIGNATURE_REDACTION_MISSING",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_APPROVAL_STATEMENT_DIGEST_SLOT_GUARD",
            "statement",
            "Reject instruction preflight when approval statement digest is absent.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_APPROVAL_STATEMENT_DIGEST_MISSING",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_SOURCE_VERSION_SLOT_GUARD",
            "evidence",
            "Reject instruction preflight when source version is absent.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_SOURCE_VERSION_MISSING",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_SOURCE_FILE_SLOT_GUARD",
            "evidence",
            "Reject instruction preflight when source file is absent.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_SOURCE_FILE_MISSING",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_SOURCE_SNIPPET_SLOT_GUARD",
            "evidence",
            "Reject instruction preflight when source snippet is absent.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_SOURCE_SNIPPET_MISSING",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_REDACTED_VALUE_DIGEST_SLOT_GUARD",
            "value",
            "Reject instruction preflight when redacted value digest is absent.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_REDACTED_VALUE_DIGEST_MISSING",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_VALUE_SHAPE_SLOT_GUARD",
            "value",
            "Reject instruction preflight when value shape is absent.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_VALUE_SHAPE_MISSING",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_REDACTION_POLICY_SLOT_GUARD",
            "policy",
            "Reject instruction preflight when redaction policy is absent.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_REDACTION_POLICY_MISSING",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_PROVENANCE_POLICY_SLOT_GUARD",
            "policy",
            "Reject instruction preflight when provenance policy is absent.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_PROVENANCE_POLICY_MISSING",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_RAW_SECRET_EMBARGO_SLOT_GUARD",
            "embargo",
            "Reject instruction preflight if raw secrets are present.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_RAW_SECRET_PRESENT",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_APPROVAL_GRANT_EMBARGO_SLOT_GUARD",
            "embargo",
            "Reject instruction preflight if approval grant exists.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_APPROVAL_GRANT_PRESENT",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_ZERO_VALUE_IMPORT_EMBARGO_SLOT_GUARD",
            "embargo",
            "Reject instruction preflight if value import count is nonzero.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_VALUE_IMPORT_NONZERO",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_WRITE_ROUTE_EMBARGO_SLOT_GUARD",
            "embargo",
            "Reject instruction preflight if write routing is enabled.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_WRITE_ROUTE_ENABLED",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_SIBLING_NON_MUTATION_SLOT_GUARD",
            "embargo",
            "Reject instruction preflight if sibling mutation is allowed.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_SIBLING_MUTATION_ALLOWED",
            "fail-closed"),
        guard(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_CLOSEOUT_SLOT_GUARD",
            "closeout",
            "Reject instruction preflight if real draft text package is not separate.",
            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_CLOSEOUT_INCOMPLETE",
            "fail-closed"));
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
              .InstructionGuard>
      guards(int fromInclusive, int toExclusive) {
    return List.copyOf(allGuards().subList(fromInclusive, toExclusive));
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
              .InstructionGate>
      allGates() {
    return List.of(
        gate(
            "DRAFT_INSTRUCTION_PREFLIGHT_GATE_01",
            "preflight",
            "Instruction preflight remains slot-map-only.",
            "fail-closed"),
        gate(
            "DRAFT_INSTRUCTION_PREFLIGHT_GATE_02",
            "instruction",
            "Instruction artifact remains uncreated.",
            "fail-closed"),
        gate(
            "DRAFT_INSTRUCTION_PREFLIGHT_GATE_03",
            "draft-text",
            "Signed draft text remains absent.",
            "fail-closed"),
        gate(
            "DRAFT_INSTRUCTION_PREFLIGHT_GATE_04",
            "signature",
            "Detached signature payload remains absent.",
            "fail-closed"),
        gate(
            "DRAFT_INSTRUCTION_PREFLIGHT_GATE_05",
            "approval",
            "Approval capture and grants remain disabled.",
            "fail-closed"),
        gate(
            "DRAFT_INSTRUCTION_PREFLIGHT_GATE_06",
            "value",
            "Operator value import remains locked.",
            "fail-closed"),
        gate(
            "DRAFT_INSTRUCTION_PREFLIGHT_GATE_07",
            "runtime",
            "Runtime payload creation remains locked.",
            "fail-closed"),
        gate(
            "DRAFT_INSTRUCTION_PREFLIGHT_GATE_08",
            "runtime",
            "Java service startup remains out of scope.",
            "fail-closed"),
        gate(
            "DRAFT_INSTRUCTION_PREFLIGHT_GATE_09",
            "runtime",
            "mini-kv service startup remains out of scope.",
            "fail-closed"),
        gate(
            "DRAFT_INSTRUCTION_PREFLIGHT_GATE_10",
            "sibling",
            "Sibling state mutation remains blocked.",
            "fail-closed"),
        gate(
            "DRAFT_INSTRUCTION_PREFLIGHT_GATE_11",
            "catalog",
            "Twenty-five instruction slots must be present.",
            "fail-closed"),
        gate(
            "DRAFT_INSTRUCTION_PREFLIGHT_GATE_12",
            "catalog",
            "Twenty-five instruction guards must be present.",
            "fail-closed"),
        gate(
            "DRAFT_INSTRUCTION_PREFLIGHT_GATE_13",
            "source",
            "Node v1186 authoring readiness remains pinned.",
            "fail-closed"),
        gate(
            "DRAFT_INSTRUCTION_PREFLIGHT_GATE_14",
            "source",
            "Java v884 authoring readiness remains pinned.",
            "fail-closed"),
        gate(
            "DRAFT_INSTRUCTION_PREFLIGHT_GATE_15",
            "secret",
            "Raw secret values remain absent.",
            "fail-closed"),
        gate(
            "DRAFT_INSTRUCTION_PREFLIGHT_GATE_16",
            "materialization",
            "Future instructions are reviewable but not materialized.",
            "fail-closed"),
        gate(
            "DRAFT_INSTRUCTION_PREFLIGHT_GATE_17",
            "handoff",
            "Future draft text package must be separate.",
            "fail-closed"),
        gate(
            "DRAFT_INSTRUCTION_PREFLIGHT_GATE_18",
            "handoff",
            "Future text package must name owner and evidence requirements.",
            "fail-closed"),
        gate(
            "DRAFT_INSTRUCTION_PREFLIGHT_GATE_19",
            "handoff",
            "Future text package must name startup and cleanup commands.",
            "fail-closed"),
        gate(
            "DRAFT_INSTRUCTION_PREFLIGHT_GATE_20",
            "closeout",
            "Closeout stops before real draft text.",
            "fail-closed"));
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
              .InstructionGate>
      gates(int fromInclusive, int toExclusive) {
    return List.copyOf(allGates().subList(fromInclusive, toExclusive));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
          .InstructionGuard
      guard(String code, String category, String guard, String rejectionCode, String enforcement) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSupport
        .guard(code, category, guard, rejectionCode, enforcement);
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
          .InstructionGate
      gate(String code, String category, String gate, String enforcement) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSupport
        .gate(code, category, gate, enforcement);
  }
}
