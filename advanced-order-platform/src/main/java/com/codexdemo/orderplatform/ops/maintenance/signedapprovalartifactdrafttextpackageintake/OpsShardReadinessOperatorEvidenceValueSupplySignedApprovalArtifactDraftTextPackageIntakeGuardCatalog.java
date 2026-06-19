package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackageintake;

import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeGuardCatalog {

  static final int GUARD_COUNT = 25;
  static final int GATE_COUNT = 20;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeGuardCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
              .IntakeGuard>
      allGuards() {
    return List.of(
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_REQUEST_MANIFEST_ID_GUARD",
            "identity",
            "Reject intake when request manifest id is absent.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_REQUEST_MANIFEST_ID_MISSING"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_REQUEST_CORRELATION_ID_GUARD",
            "identity",
            "Reject intake when request correlation id is absent.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_CORRELATION_ID_MISSING"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_OPERATOR_ID_GUARD",
            "identity",
            "Reject intake when operator id is absent.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_OPERATOR_ID_MISSING"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_PACKAGE_IDENTITY_GUARD",
            "identity",
            "Reject intake when package identity is absent.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_PACKAGE_IDENTITY_MISSING"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_INSTRUCTION_PREFLIGHT_DIGEST_GUARD",
            "digest",
            "Reject intake when instruction preflight digest is absent.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_INSTRUCTION_DIGEST_MISSING"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_AUTHORING_READINESS_DIGEST_GUARD",
            "digest",
            "Reject intake when authoring readiness digest is absent.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_AUTHORING_DIGEST_MISSING"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_ARTIFACT_PREFLIGHT_DIGEST_GUARD",
            "digest",
            "Reject intake when artifact preflight digest is absent.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_ARTIFACT_DIGEST_MISSING"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_FIELD_MAP_DIGEST_GUARD",
            "digest",
            "Reject intake when field map digest is absent.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_FIELD_MAP_DIGEST_MISSING"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_SIGNATURE_ENVELOPE_ID_GUARD",
            "signature",
            "Reject intake when signature envelope id is absent.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_SIGNATURE_ENVELOPE_ID_MISSING"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_SIGNATURE_ALGORITHM_POLICY_GUARD",
            "signature",
            "Reject intake when signature algorithm policy is absent.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_SIGNATURE_ALGORITHM_POLICY_MISSING"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_SIGNATURE_REDACTION_POLICY_GUARD",
            "signature",
            "Reject intake when signature redaction policy is absent.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_SIGNATURE_REDACTION_POLICY_MISSING"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_SOURCE_PLAN_VERSION_GUARD",
            "evidence",
            "Reject intake when source plan version is absent.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_SOURCE_PLAN_VERSION_MISSING"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_SOURCE_FILE_REFERENCES_GUARD",
            "evidence",
            "Reject intake when source file references are absent.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_SOURCE_FILE_REFERENCES_MISSING"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_SOURCE_SNIPPET_DIGEST_GUARD",
            "evidence",
            "Reject intake when source snippet digest is absent.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_SOURCE_SNIPPET_DIGEST_MISSING"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_OPERATOR_VALUE_HANDLE_GUARD",
            "value",
            "Reject intake when operator value handle is absent.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_OPERATOR_VALUE_HANDLE_MISSING"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_REDACTED_VALUE_DIGEST_GUARD",
            "value",
            "Reject intake when redacted value digest is absent.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_REDACTED_VALUE_DIGEST_MISSING"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_REDACTION_POLICY_GUARD",
            "policy",
            "Reject intake when redaction policy is absent.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_REDACTION_POLICY_MISSING"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_PROVENANCE_POLICY_GUARD",
            "policy",
            "Reject intake when provenance policy is absent.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_PROVENANCE_POLICY_MISSING"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_REVIEW_STATE_GUARD",
            "policy",
            "Reject intake when review state is absent or already approved.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_REVIEW_STATE_INVALID"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_WRITE_ROUTE_LOCK_GUARD",
            "lock",
            "Reject intake if write route lock is not closed.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_WRITE_ROUTE_OPEN"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_RUNTIME_PAYLOAD_LOCK_GUARD",
            "lock",
            "Reject intake if runtime payload lock is not closed.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_RUNTIME_PAYLOAD_OPEN"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_JAVA_STARTUP_LOCK_GUARD",
            "lock",
            "Reject intake if Java startup lock is not closed.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_JAVA_STARTUP_OPEN"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_MINI_KV_STARTUP_LOCK_GUARD",
            "lock",
            "Reject intake if mini-kv startup lock is not closed.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_MINI_KV_STARTUP_OPEN"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_SIBLING_MUTATION_LOCK_GUARD",
            "lock",
            "Reject intake if sibling mutation lock is not closed.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_SIBLING_MUTATION_OPEN"),
        guard(
            "DRAFT_TEXT_PACKAGE_INTAKE_ARCHIVE_CLOSEOUT_MANIFEST_GUARD",
            "closeout",
            "Reject intake when archive closeout manifest is absent.",
            "REJECT_DRAFT_TEXT_PACKAGE_INTAKE_ARCHIVE_CLOSEOUT_MISSING"));
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
              .IntakeGuard>
      guards(int fromInclusive, int toExclusive) {
    return List.copyOf(allGuards().subList(fromInclusive, toExclusive));
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
              .IntakeGate>
      allGates() {
    return List.of(
        gate(
            "DRAFT_TEXT_PACKAGE_INTAKE_GATE_01",
            "intake",
            "Draft text package intake remains expected-fields-only."),
        gate(
            "DRAFT_TEXT_PACKAGE_INTAKE_GATE_02",
            "draft-text",
            "Draft text artifact is not accepted by this contract."),
        gate(
            "DRAFT_TEXT_PACKAGE_INTAKE_GATE_03",
            "signature",
            "Detached signature payload is not accepted by this contract."),
        gate(
            "DRAFT_TEXT_PACKAGE_INTAKE_GATE_04",
            "approval",
            "Approval capture and grants remain disabled."),
        gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_05", "value", "Operator value import remains locked."),
        gate(
            "DRAFT_TEXT_PACKAGE_INTAKE_GATE_06",
            "runtime",
            "Runtime payload creation remains locked."),
        gate(
            "DRAFT_TEXT_PACKAGE_INTAKE_GATE_07",
            "runtime",
            "Java service startup remains out of scope."),
        gate(
            "DRAFT_TEXT_PACKAGE_INTAKE_GATE_08",
            "runtime",
            "mini-kv service startup remains out of scope."),
        gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_09", "sibling", "Sibling mutation remains blocked."),
        gate(
            "DRAFT_TEXT_PACKAGE_INTAKE_GATE_10",
            "catalog",
            "Twenty-five intake fields must be present."),
        gate(
            "DRAFT_TEXT_PACKAGE_INTAKE_GATE_11",
            "catalog",
            "Twenty-five intake guards must be present."),
        gate(
            "DRAFT_TEXT_PACKAGE_INTAKE_GATE_12",
            "source",
            "Node v1236 intake roadmap remains pinned."),
        gate(
            "DRAFT_TEXT_PACKAGE_INTAKE_GATE_13",
            "source",
            "Node v1211 instruction preflight remains pinned."),
        gate(
            "DRAFT_TEXT_PACKAGE_INTAKE_GATE_14",
            "source",
            "Java v909 instruction preflight remains pinned."),
        gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_15", "secret", "Raw secret values remain absent."),
        gate(
            "DRAFT_TEXT_PACKAGE_INTAKE_GATE_16",
            "field-map",
            "Expected field map is typed but not ingested."),
        gate(
            "DRAFT_TEXT_PACKAGE_INTAKE_GATE_17",
            "digest",
            "Digest pins are required before later review."),
        gate(
            "DRAFT_TEXT_PACKAGE_INTAKE_GATE_18",
            "review",
            "Later package review must be a separate step."),
        gate(
            "DRAFT_TEXT_PACKAGE_INTAKE_GATE_19",
            "archive",
            "Archive closeout manifest must be present."),
        gate(
            "DRAFT_TEXT_PACKAGE_INTAKE_GATE_20",
            "closeout",
            "Closeout stops before signed approval consideration."));
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
              .IntakeGate>
      gates(int fromInclusive, int toExclusive) {
    return List.copyOf(allGates().subList(fromInclusive, toExclusive));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
          .IntakeGuard
      guard(String code, String category, String guard, String rejectionCode) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSupport
        .guard(code, category, guard, rejectionCode, "fail-closed");
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
          .IntakeGate
      gate(String code, String category, String gate) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSupport
        .gate(code, category, gate, "fail-closed");
  }
}
