package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEmbargoPackageService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEvidencePackageService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightValuePolicyPackageService;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessAssuranceRequirementCatalog {

  static final int ASSURANCE_REQUIREMENT_COUNT = 12;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessAssuranceRequirementCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
              .AuthoringRequirement>
      assuranceRequirements() {
    return List.of(
        requirement(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_SOURCE_FILE",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_SOURCE_FILE_SLOT",
            "sourceEvidenceFileId",
            "Expose source file id without importing evidence.",
            "Source file readiness cannot assemble a signed draft artifact.",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_SOURCE_FILE_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEvidencePackageService
                .ENDPOINT),
        requirement(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_SOURCE_SNIPPET",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_SOURCE_SNIPPET_SLOT",
            "sourceEvidenceSnippetId",
            "Expose source snippet id for manual review.",
            "Source snippet readiness cannot create runtime payloads.",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_SOURCE_SNIPPET_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEvidencePackageService
                .ENDPOINT),
        requirement(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_REDACTED_VALUE_DIGEST",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_REDACTED_VALUE_DIGEST_SLOT",
            "redactedValueDigest",
            "Pin redacted value digest while raw values remain absent.",
            "Redacted value readiness cannot accept operator supplied values.",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_REDACTED_VALUE_DIGEST_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightValuePolicyPackageService
                .ENDPOINT),
        requirement(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_VALUE_SHAPE",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_VALUE_SHAPE_SLOT",
            "valueShapeMetadata",
            "Expose value shape metadata without accepting values.",
            "Value shape readiness cannot import operator values.",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_VALUE_SHAPE_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightValuePolicyPackageService
                .ENDPOINT),
        requirement(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_REDACTION_POLICY",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_REDACTION_POLICY_SLOT",
            "redactionPolicy",
            "Keep redaction policy bound to authoring readiness.",
            "Redaction policy readiness cannot leak raw secret material.",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_REDACTION_POLICY_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightValuePolicyPackageService
                .ENDPOINT),
        requirement(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_PROVENANCE_POLICY",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_PROVENANCE_POLICY_SLOT",
            "provenancePolicy",
            "Keep provenance policy visible for immutable evidence citation.",
            "Provenance readiness cannot cite mutable or runtime state.",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_PROVENANCE_POLICY_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightValuePolicyPackageService
                .ENDPOINT),
        requirement(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_RAW_SECRET_EMBARGO",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_RAW_SECRET_EMBARGO_SLOT",
            "rawSecretEmbargo",
            "Prove authoring readiness carries no raw secret value.",
            "Raw secret embargo readiness cannot expose secret payloads.",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_RAW_SECRET_EMBARGO_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEmbargoPackageService
                .ENDPOINT),
        requirement(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_APPROVAL_GRANT_EMBARGO",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_APPROVAL_GRANT_EMBARGO_SLOT",
            "approvalGrantEmbargo",
            "Prove no approval grant exists before authoring.",
            "Approval grant embargo readiness cannot approve execution.",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_APPROVAL_GRANT_EMBARGO_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEmbargoPackageService
                .ENDPOINT),
        requirement(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_ZERO_VALUE_IMPORT_EMBARGO",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_ZERO_VALUE_IMPORT_EMBARGO_SLOT",
            "zeroValueImportEmbargo",
            "Keep supplied, accepted, and imported value counts at zero.",
            "Zero value import readiness cannot import evidence or values.",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_ZERO_VALUE_IMPORT_EMBARGO_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEmbargoPackageService
                .ENDPOINT),
        requirement(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_WRITE_ROUTE_EMBARGO",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_WRITE_ROUTE_EMBARGO_SLOT",
            "writeRouteEmbargo",
            "Keep write routing blocked until a separate approval path exists.",
            "Write route embargo readiness cannot enable write routes.",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_WRITE_ROUTE_EMBARGO_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEmbargoPackageService
                .ENDPOINT),
        requirement(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_SIBLING_NON_MUTATION",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_SIBLING_NON_MUTATION_SLOT",
            "siblingNonMutationEvidence",
            "Prove Java and mini-kv state remain untouched.",
            "Sibling non-mutation readiness cannot mutate sibling state.",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_SIBLING_NON_MUTATION_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEmbargoPackageService
                .ENDPOINT),
        requirement(
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_CLOSEOUT",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_CLOSEOUT_SLOT",
            "authoringReadinessCloseout",
            "Close readiness and require separate real draft authoring.",
            "Closeout readiness cannot produce signed draft text.",
            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_CLOSEOUT_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightCloseoutService
                .ENDPOINT));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
          .AuthoringRequirement
      requirement(
          String code,
          String sourceReviewPackageSlot,
          String sourceField,
          String authoringPurpose,
          String authoringBlocker,
          String blockerCode,
          String sourceEndpoint) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSupport
        .requirement(
            code,
            sourceReviewPackageSlot,
            sourceField,
            authoringPurpose,
            authoringBlocker,
            blockerCode,
            sourceEndpoint);
  }
}
