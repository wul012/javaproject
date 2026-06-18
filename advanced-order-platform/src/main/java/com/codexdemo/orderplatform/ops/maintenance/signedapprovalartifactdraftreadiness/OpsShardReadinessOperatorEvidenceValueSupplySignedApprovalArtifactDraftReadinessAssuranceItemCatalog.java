package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFailClosedLockService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightRedactionValueService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightStatementEvidenceService;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessAssuranceItemCatalog {

  static final int ASSURANCE_ITEM_COUNT = 12;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessAssuranceItemCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
              .ReadinessItem>
      assuranceItems() {
    return List.of(
        item(
            "ARTIFACT_DRAFT_READINESS_14_SOURCE_FILE",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_SOURCE_FILE",
            "evidence",
            "Confirm source evidence file id ownership.",
            "file id cannot load file content",
            "OWNERSHIP_STATEMENT_EVIDENCE",
            "java-v759-artifact-preflight",
            "source-file-readiness",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightStatementEvidenceService
                .ENDPOINT),
        item(
            "ARTIFACT_DRAFT_READINESS_15_SOURCE_SNIPPET",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_SOURCE_SNIPPET",
            "evidence",
            "Confirm source evidence snippet id ownership.",
            "snippet id cannot import payload",
            "OWNERSHIP_STATEMENT_EVIDENCE",
            "java-v759-artifact-preflight",
            "source-snippet-readiness",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightStatementEvidenceService
                .ENDPOINT),
        item(
            "ARTIFACT_DRAFT_READINESS_16_REDACTED_VALUE_DIGEST",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_REDACTED_VALUE_DIGEST",
            "redaction",
            "Confirm redacted value digest ownership.",
            "redacted digest cannot be raw value hash",
            "OWNERSHIP_REDACTION_VALUE",
            "java-v759-artifact-preflight",
            "redacted-value-digest-readiness",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightRedactionValueService
                .ENDPOINT),
        item(
            "ARTIFACT_DRAFT_READINESS_17_VALUE_SHAPE",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_VALUE_SHAPE",
            "value-shape",
            "Confirm value shape ownership.",
            "value shape cannot carry operator value",
            "OWNERSHIP_REDACTION_VALUE",
            "java-v759-artifact-preflight",
            "value-shape-readiness",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightRedactionValueService
                .ENDPOINT),
        item(
            "ARTIFACT_DRAFT_READINESS_18_REDACTION_POLICY",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_REDACTION_POLICY",
            "redaction",
            "Confirm redaction policy ownership.",
            "redaction policy cannot reveal secrets",
            "OWNERSHIP_REDACTION_VALUE",
            "java-v759-artifact-preflight",
            "redaction-policy-readiness",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightRedactionValueService
                .ENDPOINT),
        item(
            "ARTIFACT_DRAFT_READINESS_19_PROVENANCE_POLICY",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_PROVENANCE_POLICY",
            "provenance",
            "Confirm provenance policy ownership.",
            "provenance policy cannot import evidence",
            "OWNERSHIP_REDACTION_VALUE",
            "java-v759-artifact-preflight",
            "provenance-policy-readiness",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightRedactionValueService
                .ENDPOINT),
        item(
            "ARTIFACT_DRAFT_READINESS_20_NO_RAW_SECRET_LOCK",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_NO_RAW_SECRET_LOCK",
            "lock",
            "Confirm no raw secret ownership lock.",
            "raw secret remains absent",
            "OWNERSHIP_FAIL_CLOSED_LOCK",
            "java-v759-artifact-preflight",
            "no-raw-secret-readiness",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFailClosedLockService
                .ENDPOINT),
        item(
            "ARTIFACT_DRAFT_READINESS_21_NO_GRANT_LOCK",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_NO_GRANT_LOCK",
            "lock",
            "Confirm no grant ownership lock.",
            "approval grant remains not emitted",
            "OWNERSHIP_FAIL_CLOSED_LOCK",
            "java-v759-artifact-preflight",
            "no-grant-readiness",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFailClosedLockService
                .ENDPOINT),
        item(
            "ARTIFACT_DRAFT_READINESS_22_ZERO_VALUE_IMPORT_LOCK",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_ZERO_VALUE_IMPORT_LOCK",
            "lock",
            "Confirm zero value import ownership lock.",
            "value import remains zero",
            "OWNERSHIP_FAIL_CLOSED_LOCK",
            "java-v759-artifact-preflight",
            "zero-value-import-readiness",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFailClosedLockService
                .ENDPOINT),
        item(
            "ARTIFACT_DRAFT_READINESS_23_NO_WRITE_ROUTE_LOCK",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_NO_WRITE_ROUTE_LOCK",
            "lock",
            "Confirm no write route ownership lock.",
            "write route remains unavailable",
            "OWNERSHIP_FAIL_CLOSED_LOCK",
            "java-v759-artifact-preflight",
            "no-write-route-readiness",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFailClosedLockService
                .ENDPOINT),
        item(
            "ARTIFACT_DRAFT_READINESS_24_SIBLING_NON_MUTATION_LOCK",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_SIBLING_NON_MUTATION_LOCK",
            "lock",
            "Confirm sibling non-mutation ownership lock.",
            "sibling services remain untouched",
            "OWNERSHIP_FAIL_CLOSED_LOCK",
            "java-v759-artifact-preflight",
            "sibling-non-mutation-readiness",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFailClosedLockService
                .ENDPOINT),
        item(
            "ARTIFACT_DRAFT_READINESS_25_CLOSEOUT_BOUNDARY",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CLOSEOUT",
            "closeout",
            "Confirm draft readiness closeout boundary.",
            "next step requires explicit separate manual draft plan",
            "OWNERSHIP_CLOSEOUT_BOUNDARY",
            "java-v759-artifact-preflight",
            "draft-readiness-closeout",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCloseoutService
                .ENDPOINT));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
          .ReadinessItem
      item(
          String code,
          String sourceArtifactFragment,
          String readinessStage,
          String readinessRequirement,
          String blockedReason,
          String ownershipCode,
          String evidenceFileId,
          String evidenceSnippetId,
          String sourceEndpoint) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSupport
        .item(
            code,
            sourceArtifactFragment,
            readinessStage,
            readinessRequirement,
            blockedReason,
            ownershipCode,
            evidenceFileId,
            evidenceSnippetId,
            sourceEndpoint);
  }
}
