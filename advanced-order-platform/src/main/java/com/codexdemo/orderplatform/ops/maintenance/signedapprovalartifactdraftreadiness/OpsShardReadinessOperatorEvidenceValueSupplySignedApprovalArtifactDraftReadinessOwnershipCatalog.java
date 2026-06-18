package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness;

import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessOwnershipCatalog {

  static final int OWNERSHIP_COUNT = 20;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessOwnershipCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
              .OwnershipRule>
      allOwnershipRules() {
    return List.of(
        ownership(
            "OWNERSHIP_REQUEST_METADATA",
            "request",
            "operator-review",
            "Owns artifact draft readiness request id metadata.",
            "metadata-only"),
        ownership(
            "OWNERSHIP_DIGEST_CHAIN",
            "digest",
            "readiness-handoff",
            "Owns capture/template/review digest chain readiness.",
            "required-before-draft-plan"),
        ownership(
            "OWNERSHIP_OPERATOR_ALIAS",
            "operator",
            "operator-review",
            "Owns operator identity and role alias readiness.",
            "alias-only"),
        ownership(
            "OWNERSHIP_CAPTURE_POLICY",
            "capture-policy",
            "readiness-handoff",
            "Owns capture window and channel policy readiness.",
            "fail-closed"),
        ownership(
            "OWNERSHIP_SIGNATURE_POLICY",
            "signature",
            "approval-security",
            "Owns signature algorithm, placeholder, and redaction readiness.",
            "fail-closed"),
        ownership(
            "OWNERSHIP_STATEMENT_EVIDENCE",
            "statement-evidence",
            "evidence-review",
            "Owns statement digest and source evidence mirror readiness.",
            "metadata-only"),
        ownership(
            "OWNERSHIP_REDACTION_VALUE",
            "redaction-value",
            "evidence-review",
            "Owns redacted digest, value shape, redaction, and provenance readiness.",
            "fail-closed"),
        ownership(
            "OWNERSHIP_FAIL_CLOSED_LOCK",
            "lock",
            "readiness-handoff",
            "Owns raw secret, grant, value import, write route, and sibling locks.",
            "fail-closed"),
        ownership(
            "OWNERSHIP_CLOSEOUT_BOUNDARY",
            "closeout",
            "readiness-handoff",
            "Owns stop condition before any manual artifact draft plan.",
            "required"),
        ownership(
            "OWNERSHIP_NO_ARTIFACT_DRAFT",
            "draft",
            "approval-security",
            "Confirms no manual artifact draft is created by readiness.",
            "fail-closed"),
        ownership(
            "OWNERSHIP_NO_MATERIALIZATION",
            "draft",
            "approval-security",
            "Confirms artifact materialization remains absent.",
            "fail-closed"),
        ownership(
            "OWNERSHIP_NO_SIGNED_CAPTURE",
            "capture",
            "approval-security",
            "Confirms signed approval capture remains absent.",
            "fail-closed"),
        ownership(
            "OWNERSHIP_NO_APPROVAL_GRANT",
            "grant",
            "approval-security",
            "Confirms approval grant remains not emitted.",
            "fail-closed"),
        ownership(
            "OWNERSHIP_NO_OPERATOR_VALUE",
            "value",
            "evidence-review",
            "Confirms operator value submission remains locked.",
            "fail-closed"),
        ownership(
            "OWNERSHIP_NO_EVIDENCE_IMPORT",
            "import",
            "evidence-review",
            "Confirms evidence import remains locked.",
            "fail-closed"),
        ownership(
            "OWNERSHIP_NO_RUNTIME_PAYLOAD",
            "runtime",
            "readiness-handoff",
            "Confirms runtime payload remains locked.",
            "fail-closed"),
        ownership(
            "OWNERSHIP_NO_PRODUCTION_EXECUTION",
            "runtime",
            "readiness-handoff",
            "Confirms production execution remains locked.",
            "fail-closed"),
        ownership(
            "OWNERSHIP_NO_SIBLING_MUTATION",
            "sibling",
            "readiness-handoff",
            "Confirms sibling services remain untouched.",
            "fail-closed"),
        ownership(
            "OWNERSHIP_READ_ONLY_ROUTE",
            "route",
            "readiness-handoff",
            "Confirms routes are read-only evidence routes.",
            "read-only"),
        ownership(
            "OWNERSHIP_ARCHIVE_PLAN_ONLY",
            "archive",
            "readiness-handoff",
            "Confirms archive plan is metadata only and writes no files.",
            "metadata-only"));
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
              .OwnershipRule>
      ownershipRules(int fromInclusive, int toExclusive) {
    return List.copyOf(allOwnershipRules().subList(fromInclusive, toExclusive));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
          .OwnershipRule
      ownership(
          String code, String category, String owner, String responsibility, String enforcement) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSupport
        .ownership(code, category, owner, responsibility, enforcement);
  }
}
