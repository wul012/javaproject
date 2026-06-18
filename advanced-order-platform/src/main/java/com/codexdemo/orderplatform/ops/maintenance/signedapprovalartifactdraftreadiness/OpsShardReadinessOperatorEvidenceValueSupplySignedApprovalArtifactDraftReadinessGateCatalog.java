package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness;

import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessGateCatalog {

  static final int GATE_COUNT = 20;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessGateCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
              .DraftGate>
      allGates() {
    return List.of(
        gate(
            "DRAFT_READINESS_GATE_01_REQUEST_METADATA_ONLY",
            "request",
            "Request id readiness is metadata only.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_GATE_02_DIGEST_CHAIN_REQUIRED",
            "digest",
            "Capture, template, and review digests are required before draft planning.",
            "required"),
        gate(
            "DRAFT_READINESS_GATE_03_OPERATOR_ALIAS_ONLY",
            "operator",
            "Operator identity and role remain aliases.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_GATE_04_CAPTURE_POLICY_NO_WRITE",
            "capture-policy",
            "Capture window and channel policy cannot expose write route.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_GATE_05_SIGNATURE_NO_MATERIAL",
            "signature",
            "Signature policy cannot contain raw signature material.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_GATE_06_STATEMENT_DIGEST_PLACEHOLDER",
            "statement",
            "Statement digest placeholder is not signed text.",
            "placeholder-only"),
        gate(
            "DRAFT_READINESS_GATE_07_EVIDENCE_MIRROR_NO_IMPORT",
            "evidence",
            "Evidence mirrors cannot import file or snippet payload.",
            "metadata-only"),
        gate(
            "DRAFT_READINESS_GATE_08_VALUE_DIGEST_NO_BODY",
            "value",
            "Redacted digest and value shape cannot carry value body.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_GATE_09_REDACTION_PROVENANCE_NO_IMPORT",
            "policy",
            "Redaction and provenance mirrors cannot import evidence.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_GATE_10_NO_RAW_SECRET",
            "lock",
            "Raw secret and signature material remain absent.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_GATE_11_NO_APPROVAL_GRANT",
            "lock",
            "Approval grant remains not emitted.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_GATE_12_ZERO_VALUE_IMPORT",
            "lock",
            "Submitted, accepted, and imported value counts remain zero.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_GATE_13_NO_WRITE_ROUTE",
            "lock",
            "No write route is exposed.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_GATE_14_NO_SIBLING_MUTATION",
            "lock",
            "Sibling services remain unstarted and unmutated.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_GATE_15_NO_MANUAL_ARTIFACT_DRAFT",
            "draft",
            "Manual artifact draft remains unavailable.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_GATE_16_NO_MATERIALIZATION",
            "draft",
            "Artifact materialization remains absent.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_GATE_17_NO_SIGNED_APPROVAL_CAPTURE",
            "capture",
            "Signed approval capture remains absent.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_GATE_18_NO_RUNTIME_PAYLOAD",
            "runtime",
            "Runtime payload remains locked.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_GATE_19_NO_PRODUCTION_EXECUTION",
            "runtime",
            "Production execution remains locked.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_GATE_20_CLOSEOUT_REQUIRES_SEPARATE_PLAN",
            "closeout",
            "Any real manual artifact draft requires a separate explicit plan.",
            "required"));
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
              .DraftGate>
      gates(int fromInclusive, int toExclusive) {
    return List.copyOf(allGates().subList(fromInclusive, toExclusive));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse.DraftGate
      gate(String code, String category, String gate, String enforcement) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSupport
        .gate(code, category, gate, enforcement);
  }
}
