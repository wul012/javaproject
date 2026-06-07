package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneFoundationLaneCatalog {

    static final int FOUNDATION_LANE_COUNT = 13;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneFoundationLaneCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
            .ReadinessLane> foundationLanes() {
        return List.of(
                lane("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_REQUEST_MANIFEST",
                        "draftArtifactRequestId", "Review request manifest identity before manual package authoring.",
                        "Request manifest must be present before any manual draft package.",
                        "DRAFT_READINESS_LANE_REQUEST_MANIFEST_BLOCKER",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCatalogService
                                .ENDPOINT),
                lane("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_ARTIFACT_PREFLIGHT_DIGEST_PIN",
                        "sourceSignedApprovalCaptureArtifactPreflightDigest",
                        "Pin source artifact preflight digest for review.",
                        "Artifact preflight digest must stay pinned.",
                        "DRAFT_READINESS_LANE_ARTIFACT_PREFLIGHT_DIGEST_BLOCKER",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightDigestChainService
                                .ENDPOINT),
                lane("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_TEMPLATE_DIGEST_PIN",
                        "sourceSignedApprovalTemplateDigest", "Pin signed approval template digest.",
                        "Template digest must stay pinned.",
                        "DRAFT_READINESS_LANE_TEMPLATE_DIGEST_BLOCKER",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightDigestChainService
                                .ENDPOINT),
                lane("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_REVIEW_DIGEST_PIN",
                        "sourceApprovalPacketReviewDigest", "Pin approval packet review digest.",
                        "Approval packet review digest must stay pinned.",
                        "DRAFT_READINESS_LANE_REVIEW_DIGEST_BLOCKER",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightDigestChainService
                                .ENDPOINT),
                lane("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_OPERATOR_IDENTITY_REVIEW",
                        "operatorIdentity", "Review operator identity as alias-only evidence.",
                        "Operator identity must be reviewed without credentials.",
                        "DRAFT_READINESS_LANE_OPERATOR_IDENTITY_BLOCKER",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightOperatorWindowService
                                .ENDPOINT),
                lane("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_OPERATOR_ROLE_REVIEW",
                        "operatorRole", "Review operator role without granting authority.",
                        "Operator role must be reviewed before any grant package.",
                        "DRAFT_READINESS_LANE_OPERATOR_ROLE_BLOCKER",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightOperatorWindowService
                                .ENDPOINT),
                lane("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_WINDOW_ID_REVIEW",
                        "captureWindowId", "Review capture window id without opening runtime.",
                        "Capture window id must remain review-only.",
                        "DRAFT_READINESS_LANE_WINDOW_ID_BLOCKER",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightOperatorWindowService
                                .ENDPOINT),
                lane("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_CHANNEL_POLICY_REVIEW",
                        "captureChannelPolicy", "Review capture channel policy without write route exposure.",
                        "Capture channel policy must remain read-only.",
                        "DRAFT_READINESS_LANE_CHANNEL_POLICY_BLOCKER",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightOperatorWindowService
                                .ENDPOINT),
                lane("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_SIGNATURE_ALGORITHM_REVIEW",
                        "signatureAlgorithmPolicy", "Review signature algorithm policy without signature material.",
                        "Signature algorithm must be reviewed before any signature capture.",
                        "DRAFT_READINESS_LANE_SIGNATURE_ALGORITHM_BLOCKER",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSignatureStatementService
                                .ENDPOINT),
                lane("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_DETACHED_SIGNATURE_REVIEW",
                        "detachedSignaturePlaceholder", "Review detached signature placeholder as empty material.",
                        "Detached signature placeholder must not contain signature text.",
                        "DRAFT_READINESS_LANE_DETACHED_SIGNATURE_BLOCKER",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSignatureStatementService
                                .ENDPOINT),
                lane("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_SIGNATURE_REDACTION_REVIEW",
                        "signatureMaterialRedactionPolicy", "Review signature material redaction policy.",
                        "Signature redaction must be reviewed before manual package authoring.",
                        "DRAFT_READINESS_LANE_SIGNATURE_REDACTION_BLOCKER",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSignatureStatementService
                                .ENDPOINT),
                lane("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_APPROVAL_STATEMENT_DIGEST_PIN",
                        "approvalStatementDigest", "Pin approval statement digest without statement text.",
                        "Approval statement digest must stay pinned.",
                        "DRAFT_READINESS_LANE_APPROVAL_STATEMENT_DIGEST_BLOCKER",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSignatureStatementService
                                .ENDPOINT),
                lane("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_SOURCE_VERSION_REVIEW",
                        "sourceEvidenceVersion", "Review source evidence version without importing evidence.",
                        "Source evidence version must be reviewed before any import package.",
                        "DRAFT_READINESS_LANE_SOURCE_VERSION_BLOCKER",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightEvidenceSourceService
                                .ENDPOINT)
        );
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
            .ReadinessLane lane(
                    String code,
                    String sourceField,
                    String reviewPurpose,
                    String manualReviewBlocker,
                    String blockerCode,
                    String sourceEndpoint
    ) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSupport.lane(
                code,
                sourceField,
                reviewPurpose,
                manualReviewBlocker,
                blockerCode,
                sourceEndpoint
        );
    }
}
