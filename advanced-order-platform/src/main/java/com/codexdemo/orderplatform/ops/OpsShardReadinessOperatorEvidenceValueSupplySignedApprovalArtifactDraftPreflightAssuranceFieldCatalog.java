package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessEvidenceSourceService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFailClosedLockService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessRedactionProvenanceService;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightAssuranceFieldCatalog {

  static final int ASSURANCE_FIELD_COUNT = 12;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightAssuranceFieldCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
              .DraftField>
      assuranceFields() {
    return List.of(
        field(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_SOURCE_FILE",
            "ARTIFACT_DRAFT_READINESS_14_SOURCE_FILE",
            "evidence",
            "Map source evidence file draft field.",
            "file field cannot read file contents",
            "ARTIFACT_DRAFT_PREFLIGHT_SOURCE_FILE_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessEvidenceSourceService
                .ENDPOINT),
        field(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_SOURCE_SNIPPET",
            "ARTIFACT_DRAFT_READINESS_15_SOURCE_SNIPPET",
            "evidence",
            "Map source evidence snippet draft field.",
            "snippet field cannot import payload",
            "ARTIFACT_DRAFT_PREFLIGHT_SOURCE_SNIPPET_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessEvidenceSourceService
                .ENDPOINT),
        field(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_REDACTED_VALUE_DIGEST",
            "ARTIFACT_DRAFT_READINESS_16_REDACTED_VALUE_DIGEST",
            "redaction",
            "Map redacted value digest draft field.",
            "redacted digest cannot be raw value hash",
            "ARTIFACT_DRAFT_PREFLIGHT_REDACTED_VALUE_DIGEST_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessRedactionProvenanceService
                .ENDPOINT),
        field(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_VALUE_SHAPE",
            "ARTIFACT_DRAFT_READINESS_17_VALUE_SHAPE",
            "value-shape",
            "Map value shape draft field.",
            "value shape cannot carry operator value body",
            "ARTIFACT_DRAFT_PREFLIGHT_VALUE_SHAPE_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessRedactionProvenanceService
                .ENDPOINT),
        field(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_REDACTION_POLICY",
            "ARTIFACT_DRAFT_READINESS_18_REDACTION_POLICY",
            "redaction",
            "Map redaction policy draft field.",
            "redaction policy cannot reveal secrets",
            "ARTIFACT_DRAFT_PREFLIGHT_REDACTION_POLICY_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessRedactionProvenanceService
                .ENDPOINT),
        field(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_PROVENANCE_POLICY",
            "ARTIFACT_DRAFT_READINESS_19_PROVENANCE_POLICY",
            "provenance",
            "Map provenance policy draft field.",
            "provenance policy cannot import evidence",
            "ARTIFACT_DRAFT_PREFLIGHT_PROVENANCE_POLICY_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessRedactionProvenanceService
                .ENDPOINT),
        field(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_NO_RAW_SECRET_LOCK",
            "ARTIFACT_DRAFT_READINESS_20_NO_RAW_SECRET_LOCK",
            "lock",
            "Map no raw secret draft lock.",
            "raw secret material remains absent",
            "ARTIFACT_DRAFT_PREFLIGHT_NO_RAW_SECRET_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFailClosedLockService
                .ENDPOINT),
        field(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_NO_GRANT_LOCK",
            "ARTIFACT_DRAFT_READINESS_21_NO_GRANT_LOCK",
            "lock",
            "Map no approval grant draft lock.",
            "approval grant remains not emitted",
            "ARTIFACT_DRAFT_PREFLIGHT_NO_GRANT_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFailClosedLockService
                .ENDPOINT),
        field(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_ZERO_VALUE_IMPORT_LOCK",
            "ARTIFACT_DRAFT_READINESS_22_ZERO_VALUE_IMPORT_LOCK",
            "lock",
            "Map zero value import draft lock.",
            "value import remains zero",
            "ARTIFACT_DRAFT_PREFLIGHT_ZERO_VALUE_IMPORT_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFailClosedLockService
                .ENDPOINT),
        field(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_NO_WRITE_ROUTE_LOCK",
            "ARTIFACT_DRAFT_READINESS_23_NO_WRITE_ROUTE_LOCK",
            "lock",
            "Map no write route draft lock.",
            "write route remains unavailable",
            "ARTIFACT_DRAFT_PREFLIGHT_NO_WRITE_ROUTE_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFailClosedLockService
                .ENDPOINT),
        field(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_SIBLING_NON_MUTATION_LOCK",
            "ARTIFACT_DRAFT_READINESS_24_SIBLING_NON_MUTATION_LOCK",
            "lock",
            "Map sibling non-mutation draft lock.",
            "sibling services remain untouched",
            "ARTIFACT_DRAFT_PREFLIGHT_SIBLING_NON_MUTATION_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFailClosedLockService
                .ENDPOINT),
        field(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_CLOSEOUT",
            "ARTIFACT_DRAFT_READINESS_25_CLOSEOUT_BOUNDARY",
            "closeout",
            "Map artifact draft preflight closeout.",
            "next step requires explicit manual draft plan",
            "ARTIFACT_DRAFT_PREFLIGHT_CLOSEOUT_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCloseoutService
                .ENDPOINT));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
          .DraftField
      field(
          String code,
          String sourceReadinessItem,
          String draftStage,
          String fieldRequirement,
          String materializationBlocker,
          String guardCode,
          String sourceEndpoint) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSupport
        .field(
            code,
            sourceReadinessItem,
            draftStage,
            fieldRequirement,
            materializationBlocker,
            guardCode,
            sourceEndpoint);
  }
}
