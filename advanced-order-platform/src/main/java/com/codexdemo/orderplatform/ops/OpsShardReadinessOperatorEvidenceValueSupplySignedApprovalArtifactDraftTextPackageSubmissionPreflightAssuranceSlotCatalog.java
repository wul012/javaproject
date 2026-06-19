package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagereviewpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightCatalogService;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightAssuranceSlotCatalog {

  static final int ASSURANCE_SLOT_COUNT = 14;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightAssuranceSlotCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
              .SubmissionSlot>
      assuranceSlots() {
    return List.of(
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_SOURCE_PLAN_SLOT",
            "Node v1273-v1275",
            "sourcePlanVersion",
            "Can source plan version be compared?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_SOURCE_FILES_SLOT",
            "Node v1273-v1275",
            "sourceFileReferences",
            "Can source files be compared by reference?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_SOURCE_SNIPPET_SLOT",
            "Node v1273-v1275",
            "sourceSnippetDigest",
            "Can source snippet digest be compared?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_VALUE_HANDLE_SLOT",
            "Node v1276-v1277",
            "operatorValueHandle",
            "Can redacted value handle be compared?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_REDACTED_VALUE_DIGEST_SLOT",
            "Node v1276-v1277",
            "redactedValueDigest",
            "Can redacted value digest be compared?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_REDACTION_POLICY_SLOT",
            "Node v1278-v1280",
            "redactionPolicy",
            "Can redaction policy be compared?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_PROVENANCE_POLICY_SLOT",
            "Node v1278-v1280",
            "provenancePolicy",
            "Can provenance policy be compared?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_REVIEW_STATE_SLOT",
            "Node v1278-v1280",
            "reviewState",
            "Can review state remain pre-acceptance?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_WRITE_ROUTE_LOCK_SLOT",
            "Node v1281-v1285",
            "writeRouteLock",
            "Can write route lock be compared?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_RUNTIME_PAYLOAD_LOCK_SLOT",
            "Node v1281-v1285",
            "runtimePayloadLock",
            "Can runtime payload lock be compared?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_JAVA_STARTUP_LOCK_SLOT",
            "Node v1281-v1285",
            "javaStartupLock",
            "Can Java startup lock be compared?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_MINI_KV_STARTUP_LOCK_SLOT",
            "Node v1281-v1285",
            "miniKvStartupLock",
            "Can mini-kv startup lock be compared?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_SIBLING_MUTATION_LOCK_SLOT",
            "Node v1281-v1285",
            "siblingMutationLock",
            "Can sibling mutation lock be compared?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_ARCHIVE_CLOSEOUT_SLOT",
            "Node v1286",
            "archiveCloseoutManifest",
            "Can archive closeout manifest be compared?"));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
          .SubmissionSlot
      slot(String code, String versionRange, String submissionSlot, String question) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSupport
        .slot(
            code,
            versionRange,
            submissionSlot,
            question,
            "compare only; do not parse or import submitted material",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightCatalogService
                .ENDPOINT);
  }
}
