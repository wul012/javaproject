package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane;

import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneBlockerCatalog {

  static final int BLOCKER_COUNT = 25;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneBlockerCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
              .ControlBlocker>
      allBlockers() {
    return List.of(
        blocker(
            "DRAFT_READINESS_LANE_REQUEST_MANIFEST_BLOCKER",
            "request",
            "Request manifest readiness lane must be present.",
            "REJECT_DRAFT_READINESS_REQUEST_MANIFEST_MISSING",
            "fail-closed"),
        blocker(
            "DRAFT_READINESS_LANE_ARTIFACT_PREFLIGHT_DIGEST_BLOCKER",
            "digest",
            "Artifact preflight digest must stay pinned.",
            "REJECT_DRAFT_READINESS_ARTIFACT_PREFLIGHT_DIGEST_UNPINNED",
            "required"),
        blocker(
            "DRAFT_READINESS_LANE_TEMPLATE_DIGEST_BLOCKER",
            "digest",
            "Template digest must stay pinned.",
            "REJECT_DRAFT_READINESS_TEMPLATE_DIGEST_UNPINNED",
            "required"),
        blocker(
            "DRAFT_READINESS_LANE_REVIEW_DIGEST_BLOCKER",
            "digest",
            "Approval review digest must stay pinned.",
            "REJECT_DRAFT_READINESS_REVIEW_DIGEST_UNPINNED",
            "required"),
        blocker(
            "DRAFT_READINESS_LANE_OPERATOR_IDENTITY_BLOCKER",
            "operator",
            "Operator identity must be reviewed without credentials.",
            "REJECT_DRAFT_READINESS_OPERATOR_IDENTITY_UNREVIEWED",
            "fail-closed"),
        blocker(
            "DRAFT_READINESS_LANE_OPERATOR_ROLE_BLOCKER",
            "operator",
            "Operator role must be reviewed without approval authority.",
            "REJECT_DRAFT_READINESS_OPERATOR_ROLE_UNREVIEWED",
            "fail-closed"),
        blocker(
            "DRAFT_READINESS_LANE_WINDOW_ID_BLOCKER",
            "capture-policy",
            "Capture window id must be reviewed without runtime opening.",
            "REJECT_DRAFT_READINESS_WINDOW_ID_UNREVIEWED",
            "fail-closed"),
        blocker(
            "DRAFT_READINESS_LANE_CHANNEL_POLICY_BLOCKER",
            "capture-policy",
            "Capture channel policy must be reviewed without write route exposure.",
            "REJECT_DRAFT_READINESS_CHANNEL_POLICY_UNREVIEWED",
            "fail-closed"),
        blocker(
            "DRAFT_READINESS_LANE_SIGNATURE_ALGORITHM_BLOCKER",
            "signature",
            "Signature algorithm policy must be reviewed.",
            "REJECT_DRAFT_READINESS_SIGNATURE_ALGORITHM_UNREVIEWED",
            "fail-closed"),
        blocker(
            "DRAFT_READINESS_LANE_DETACHED_SIGNATURE_BLOCKER",
            "signature",
            "Detached signature placeholder must be reviewed.",
            "REJECT_DRAFT_READINESS_DETACHED_SIGNATURE_PLACEHOLDER_UNREVIEWED",
            "fail-closed"),
        blocker(
            "DRAFT_READINESS_LANE_SIGNATURE_REDACTION_BLOCKER",
            "signature",
            "Signature redaction policy must be reviewed.",
            "REJECT_DRAFT_READINESS_SIGNATURE_REDACTION_UNREVIEWED",
            "fail-closed"),
        blocker(
            "DRAFT_READINESS_LANE_APPROVAL_STATEMENT_DIGEST_BLOCKER",
            "statement",
            "Approval statement digest must stay pinned.",
            "REJECT_DRAFT_READINESS_APPROVAL_STATEMENT_DIGEST_UNPINNED",
            "required"),
        blocker(
            "DRAFT_READINESS_LANE_SOURCE_VERSION_BLOCKER",
            "evidence",
            "Source evidence version must be reviewed without import.",
            "REJECT_DRAFT_READINESS_SOURCE_VERSION_UNREVIEWED",
            "metadata-only"),
        blocker(
            "DRAFT_READINESS_LANE_SOURCE_FILE_BLOCKER",
            "evidence",
            "Source evidence file id must be reviewed without file load.",
            "REJECT_DRAFT_READINESS_SOURCE_FILE_UNREVIEWED",
            "metadata-only"),
        blocker(
            "DRAFT_READINESS_LANE_SOURCE_SNIPPET_BLOCKER",
            "evidence",
            "Source evidence snippet id must be reviewed without payload import.",
            "REJECT_DRAFT_READINESS_SOURCE_SNIPPET_UNREVIEWED",
            "metadata-only"),
        blocker(
            "DRAFT_READINESS_LANE_REDACTED_VALUE_DIGEST_BLOCKER",
            "value",
            "Redacted value digest must stay pinned.",
            "REJECT_DRAFT_READINESS_REDACTED_VALUE_DIGEST_UNPINNED",
            "fail-closed"),
        blocker(
            "DRAFT_READINESS_LANE_VALUE_SHAPE_BLOCKER",
            "value",
            "Value shape must be reviewed without value body.",
            "REJECT_DRAFT_READINESS_VALUE_SHAPE_UNREVIEWED",
            "fail-closed"),
        blocker(
            "DRAFT_READINESS_LANE_REDACTION_POLICY_BLOCKER",
            "policy",
            "Redaction policy must be reviewed without secret reveal.",
            "REJECT_DRAFT_READINESS_REDACTION_POLICY_UNREVIEWED",
            "fail-closed"),
        blocker(
            "DRAFT_READINESS_LANE_PROVENANCE_POLICY_BLOCKER",
            "policy",
            "Provenance policy must be reviewed without evidence import.",
            "REJECT_DRAFT_READINESS_PROVENANCE_POLICY_UNREVIEWED",
            "fail-closed"),
        blocker(
            "DRAFT_READINESS_LANE_RAW_SECRET_EMBARGO_BLOCKER",
            "embargo",
            "Raw secret embargo must hold.",
            "REJECT_DRAFT_READINESS_RAW_SECRET_PRESENT",
            "fail-closed"),
        blocker(
            "DRAFT_READINESS_LANE_APPROVAL_GRANT_EMBARGO_BLOCKER",
            "embargo",
            "Approval grant embargo must hold.",
            "REJECT_DRAFT_READINESS_APPROVAL_GRANT_EMITTED",
            "fail-closed"),
        blocker(
            "DRAFT_READINESS_LANE_ZERO_VALUE_IMPORT_EMBARGO_BLOCKER",
            "embargo",
            "Zero value import embargo must hold.",
            "REJECT_DRAFT_READINESS_VALUE_IMPORT_NONZERO",
            "fail-closed"),
        blocker(
            "DRAFT_READINESS_LANE_WRITE_ROUTE_EMBARGO_BLOCKER",
            "embargo",
            "Write route embargo must hold.",
            "REJECT_DRAFT_READINESS_WRITE_ROUTE_ENABLED",
            "fail-closed"),
        blocker(
            "DRAFT_READINESS_LANE_SIBLING_NON_MUTATION_BLOCKER",
            "embargo",
            "Sibling non-mutation evidence must hold.",
            "REJECT_DRAFT_READINESS_SIBLING_MUTATION_ENABLED",
            "fail-closed"),
        blocker(
            "DRAFT_READINESS_LANE_CLOSEOUT_BLOCKER",
            "closeout",
            "Next step must be a separate manual package plan.",
            "REJECT_DRAFT_READINESS_NEXT_STEP_NOT_MANUAL_PACKAGE",
            "required"));
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
              .ControlBlocker>
      blockers(int fromInclusive, int toExclusive) {
    return List.copyOf(allBlockers().subList(fromInclusive, toExclusive));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
          .ControlBlocker
      blocker(
          String code, String category, String blocker, String rejectionCode, String enforcement) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSupport
        .blocker(code, category, blocker, rejectionCode, enforcement);
  }
}

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneGateCatalog {

  static final int GATE_COUNT = 20;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneGateCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
              .ReadinessLaneGate>
      allGates() {
    return List.of(
        gate(
            "DRAFT_READINESS_LANE_GATE_01_REQUEST_MANIFEST_PRESENT",
            "request",
            "Request manifest lane must be present before review closeout.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_LANE_GATE_02_DIGEST_PINS_BOUND",
            "digest",
            "Artifact, template, and review digest pins must be bound.",
            "required"),
        gate(
            "DRAFT_READINESS_LANE_GATE_03_OPERATOR_REVIEW_REQUIRED",
            "operator",
            "Operator identity and role lanes require review.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_LANE_GATE_04_CAPTURE_POLICY_REVIEW_REQUIRED",
            "capture-policy",
            "Capture window and channel policy lanes require review.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_LANE_GATE_05_SIGNATURE_MATERIAL_ABSENT",
            "signature",
            "Signature review lanes cannot contain signature material.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_LANE_GATE_06_STATEMENT_DIGEST_PINNED",
            "statement",
            "Approval statement digest lane must stay pinned.",
            "required"),
        gate(
            "DRAFT_READINESS_LANE_GATE_07_EVIDENCE_REVIEW_NO_IMPORT",
            "evidence",
            "Evidence lanes cannot import files or snippets.",
            "metadata-only"),
        gate(
            "DRAFT_READINESS_LANE_GATE_08_VALUE_BODY_ABSENT",
            "value",
            "Value lanes cannot carry operator value body.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_LANE_GATE_09_REDACTION_PROVENANCE_REVIEW",
            "policy",
            "Redaction and provenance policy lanes require review.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_LANE_GATE_10_RAW_SECRET_EMBARGO",
            "embargo",
            "Raw secret embargo remains closed.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_LANE_GATE_11_APPROVAL_GRANT_EMBARGO",
            "embargo",
            "Approval grant remains not emitted.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_LANE_GATE_12_ZERO_VALUE_IMPORT_EMBARGO",
            "embargo",
            "Value import counts remain zero.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_LANE_GATE_13_WRITE_ROUTE_EMBARGO",
            "embargo",
            "Write route remains unavailable.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_LANE_GATE_14_SIBLING_NON_MUTATION",
            "embargo",
            "Sibling services remain untouched.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_LANE_GATE_15_NO_REAL_MANUAL_DRAFT",
            "manual-package",
            "Readiness lane closeout cannot create real manual draft.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_LANE_GATE_16_NO_DRAFT_MATERIALIZATION",
            "manual-package",
            "Draft materialization remains absent.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_LANE_GATE_17_NO_SIGNATURE_CAPTURE",
            "capture",
            "Signature capture remains absent.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_LANE_GATE_18_NO_RUNTIME_PAYLOAD",
            "runtime",
            "Runtime payload remains locked.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_LANE_GATE_19_NO_PRODUCTION_EXECUTION",
            "runtime",
            "Production execution remains locked.",
            "fail-closed"),
        gate(
            "DRAFT_READINESS_LANE_GATE_20_NEXT_STEP_MANUAL_PACKAGE_PLAN",
            "closeout",
            "Manual draft artifact package requires a separate explicit plan.",
            "required"));
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
              .ReadinessLaneGate>
      gates(int fromInclusive, int toExclusive) {
    return List.copyOf(allGates().subList(fromInclusive, toExclusive));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
          .ReadinessLaneGate
      gate(String code, String category, String gate, String enforcement) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSupport
        .gate(code, category, gate, enforcement);
  }
}
