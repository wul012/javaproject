package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEmbargoLockService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEvidenceReviewService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneValueRedactionService;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightAssuranceSlotCatalog {

  static final int ASSURANCE_SLOT_COUNT = 12;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightAssuranceSlotCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
              .PackageSlot>
      assuranceSlots() {
    return List.of(
        slot(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_SOURCE_FILE_SLOT",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_SOURCE_FILE_REVIEW",
            "sourceEvidenceFileId",
            "Expose source evidence file id without importing evidence.",
            "Source file slot cannot load file contents.",
            "ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_SOURCE_FILE_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEvidenceReviewService
                .ENDPOINT),
        slot(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_SOURCE_SNIPPET_SLOT",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_SOURCE_SNIPPET_REVIEW",
            "sourceEvidenceSnippetId",
            "Expose source snippet id without assembling runtime payload.",
            "Source snippet slot cannot import payload.",
            "ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_SOURCE_SNIPPET_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEvidenceReviewService
                .ENDPOINT),
        slot(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_REDACTED_VALUE_DIGEST_SLOT",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_REDACTED_VALUE_DIGEST_PIN",
            "redactedValueDigest",
            "Pin redacted value digest while raw values remain absent.",
            "Redacted value digest slot cannot become raw value material.",
            "ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_REDACTED_VALUE_DIGEST_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneValueRedactionService
                .ENDPOINT),
        slot(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_VALUE_SHAPE_SLOT",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_VALUE_SHAPE_REVIEW",
            "valueShape",
            "Expose value shape metadata without accepting operator values.",
            "Value shape slot cannot carry operator value body.",
            "ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_VALUE_SHAPE_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneValueRedactionService
                .ENDPOINT),
        slot(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_REDACTION_POLICY_SLOT",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_REDACTION_POLICY_REVIEW",
            "redactionPolicy",
            "Carry redaction policy into review package preflight.",
            "Redaction policy slot cannot reveal secrets.",
            "ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_REDACTION_POLICY_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneValueRedactionService
                .ENDPOINT),
        slot(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_PROVENANCE_POLICY_SLOT",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_PROVENANCE_POLICY_REVIEW",
            "provenancePolicy",
            "Carry provenance policy for later immutable evidence citation.",
            "Provenance policy slot cannot import evidence.",
            "ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_PROVENANCE_POLICY_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneValueRedactionService
                .ENDPOINT),
        slot(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_RAW_SECRET_EMBARGO_SLOT",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_RAW_SECRET_EMBARGO",
            "noRawSecretLock",
            "Carry raw secret embargo evidence into package preflight.",
            "Raw secret embargo slot cannot carry secret material.",
            "ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_RAW_SECRET_EMBARGO_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEmbargoLockService
                .ENDPOINT),
        slot(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_APPROVAL_GRANT_EMBARGO_SLOT",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_APPROVAL_GRANT_EMBARGO",
            "noApprovalGrantEmittedLock",
            "Prove no approval grant exists before human draft authoring.",
            "Approval grant embargo slot cannot emit grants.",
            "ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_APPROVAL_GRANT_EMBARGO_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEmbargoLockService
                .ENDPOINT),
        slot(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_ZERO_VALUE_IMPORT_EMBARGO_SLOT",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_ZERO_VALUE_IMPORT_EMBARGO",
            "zeroValueImportLock",
            "Prove operator value submissions and imports remain zero.",
            "Zero value import slot cannot increase value counts.",
            "ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_ZERO_VALUE_IMPORT_EMBARGO_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEmbargoLockService
                .ENDPOINT),
        slot(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_WRITE_ROUTE_EMBARGO_SLOT",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_WRITE_ROUTE_EMBARGO",
            "noWriteRouteLock",
            "Keep write routing disabled until a real approval grant exists.",
            "Write route embargo slot cannot enable write routing.",
            "ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_WRITE_ROUTE_EMBARGO_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEmbargoLockService
                .ENDPOINT),
        slot(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_SIBLING_NON_MUTATION_SLOT",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_SIBLING_NON_MUTATION_EVIDENCE",
            "siblingNonMutationLock",
            "Carry sibling non-mutation evidence into the review package.",
            "Sibling non-mutation slot cannot mutate sibling state.",
            "ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_SIBLING_NON_MUTATION_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEmbargoLockService
                .ENDPOINT),
        slot(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_CLOSEOUT_SLOT",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_CLOSEOUT",
            "signedApprovalCaptureArtifactDraftPreflightCloseout",
            "Close review package preflight before separate human draft artifact authoring.",
            "Closeout slot requires a separate human draft artifact plan.",
            "ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_CLOSEOUT_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCloseoutService
                .ENDPOINT));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
          .PackageSlot
      slot(
          String code,
          String sourceLane,
          String sourceField,
          String packagePurpose,
          String materializationBlocker,
          String guardCode,
          String sourceEndpoint) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSupport
        .slot(
            code,
            sourceLane,
            sourceField,
            packagePurpose,
            materializationBlocker,
            guardCode,
            sourceEndpoint);
  }
}
