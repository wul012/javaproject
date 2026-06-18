package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightEvidenceSourceService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFailClosedLockService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightRedactionProvenanceService;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneAssuranceLaneCatalog {

  static final int ASSURANCE_LANE_COUNT = 12;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneAssuranceLaneCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
              .ReadinessLane>
      assuranceLanes() {
    return List.of(
        lane(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_SOURCE_FILE_REVIEW",
            "sourceEvidenceFileId",
            "Review source evidence file id without reading file contents.",
            "Source file id must be reviewed without importing evidence.",
            "DRAFT_READINESS_LANE_SOURCE_FILE_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightEvidenceSourceService
                .ENDPOINT),
        lane(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_SOURCE_SNIPPET_REVIEW",
            "sourceEvidenceSnippetId",
            "Review source evidence snippet id without payload import.",
            "Source snippet id must remain metadata-only.",
            "DRAFT_READINESS_LANE_SOURCE_SNIPPET_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightEvidenceSourceService
                .ENDPOINT),
        lane(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_REDACTED_VALUE_DIGEST_PIN",
            "redactedValueDigest",
            "Pin redacted value digest without raw value material.",
            "Redacted value digest must stay pinned and non-secret.",
            "DRAFT_READINESS_LANE_REDACTED_VALUE_DIGEST_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightRedactionProvenanceService
                .ENDPOINT),
        lane(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_VALUE_SHAPE_REVIEW",
            "valueShape",
            "Review value shape without operator value body.",
            "Value shape must be reviewed without submitted value material.",
            "DRAFT_READINESS_LANE_VALUE_SHAPE_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightRedactionProvenanceService
                .ENDPOINT),
        lane(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_REDACTION_POLICY_REVIEW",
            "redactionPolicy",
            "Review redaction policy without revealing secrets.",
            "Redaction policy must remain review-only.",
            "DRAFT_READINESS_LANE_REDACTION_POLICY_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightRedactionProvenanceService
                .ENDPOINT),
        lane(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_PROVENANCE_POLICY_REVIEW",
            "provenancePolicy",
            "Review provenance policy without evidence import.",
            "Provenance policy must remain metadata-only.",
            "DRAFT_READINESS_LANE_PROVENANCE_POLICY_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightRedactionProvenanceService
                .ENDPOINT),
        lane(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_RAW_SECRET_EMBARGO",
            "noRawSecretLock",
            "Review raw secret embargo before any manual package.",
            "Raw secret material must remain absent.",
            "DRAFT_READINESS_LANE_RAW_SECRET_EMBARGO_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFailClosedLockService
                .ENDPOINT),
        lane(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_APPROVAL_GRANT_EMBARGO",
            "noApprovalGrantEmittedLock",
            "Review approval grant embargo before any grant emission.",
            "Approval grant must remain not emitted.",
            "DRAFT_READINESS_LANE_APPROVAL_GRANT_EMBARGO_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFailClosedLockService
                .ENDPOINT),
        lane(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_ZERO_VALUE_IMPORT_EMBARGO",
            "zeroValueImportLock",
            "Review zero value import embargo.",
            "Submitted, accepted, and imported value counts must remain zero.",
            "DRAFT_READINESS_LANE_ZERO_VALUE_IMPORT_EMBARGO_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFailClosedLockService
                .ENDPOINT),
        lane(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_WRITE_ROUTE_EMBARGO",
            "noWriteRouteLock",
            "Review write route embargo before manual package authoring.",
            "Write route must remain unavailable.",
            "DRAFT_READINESS_LANE_WRITE_ROUTE_EMBARGO_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFailClosedLockService
                .ENDPOINT),
        lane(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_SIBLING_NON_MUTATION_EVIDENCE",
            "siblingNonMutationLock",
            "Review sibling non-mutation evidence.",
            "Sibling state must remain untouched.",
            "DRAFT_READINESS_LANE_SIBLING_NON_MUTATION_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFailClosedLockService
                .ENDPOINT),
        lane(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_CLOSEOUT",
            "signedApprovalCaptureArtifactDraftPreflightCloseout",
            "Close out readiness lane package before a separate manual draft package.",
            "Next step must be a separate explicit manual package plan.",
            "DRAFT_READINESS_LANE_CLOSEOUT_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCloseoutService
                .ENDPOINT));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
          .ReadinessLane
      lane(
          String code,
          String sourceField,
          String reviewPurpose,
          String manualReviewBlocker,
          String blockerCode,
          String sourceEndpoint) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSupport
        .lane(code, sourceField, reviewPurpose, manualReviewBlocker, blockerCode, sourceEndpoint);
  }
}
