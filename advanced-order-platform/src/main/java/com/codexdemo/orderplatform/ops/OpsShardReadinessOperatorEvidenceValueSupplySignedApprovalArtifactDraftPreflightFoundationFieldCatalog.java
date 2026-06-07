package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFoundationFieldCatalog {

    static final int FOUNDATION_FIELD_COUNT = 13;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFoundationFieldCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse.DraftField>
    foundationFields() {
        return List.of(
                field("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_REQUEST_ID",
                        "ARTIFACT_DRAFT_READINESS_01_REQUEST_ID", "request",
                        "Map artifact draft preflight request id.", "request id cannot create real draft",
                        "ARTIFACT_DRAFT_PREFLIGHT_REQUEST_ID_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCatalogService
                                .ENDPOINT),
                field("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_ARTIFACT_PREFLIGHT_DIGEST",
                        "ARTIFACT_DRAFT_READINESS_02_CAPTURE_DIGEST", "digest",
                        "Bind source artifact preflight digest.", "artifact preflight digest cannot materialize draft",
                        "ARTIFACT_DRAFT_PREFLIGHT_ARTIFACT_PREFLIGHT_DIGEST_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessDigestChainService
                                .ENDPOINT),
                field("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_TEMPLATE_DIGEST",
                        "ARTIFACT_DRAFT_READINESS_03_TEMPLATE_DIGEST", "digest",
                        "Bind template digest draft field.", "template digest cannot create signed draft body",
                        "ARTIFACT_DRAFT_PREFLIGHT_TEMPLATE_DIGEST_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessDigestChainService
                                .ENDPOINT),
                field("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_REVIEW_DIGEST",
                        "ARTIFACT_DRAFT_READINESS_04_REVIEW_DIGEST", "digest",
                        "Bind approval packet review digest draft field.", "review digest cannot emit approval grant",
                        "ARTIFACT_DRAFT_PREFLIGHT_REVIEW_DIGEST_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessDigestChainService
                                .ENDPOINT),
                field("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_OPERATOR_IDENTITY",
                        "ARTIFACT_DRAFT_READINESS_05_OPERATOR_IDENTITY", "operator",
                        "Map operator identity draft field.", "operator identity remains alias-only",
                        "ARTIFACT_DRAFT_PREFLIGHT_OPERATOR_IDENTITY_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessOperatorWindowService
                                .ENDPOINT),
                field("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_OPERATOR_ROLE",
                        "ARTIFACT_DRAFT_READINESS_06_OPERATOR_ROLE", "operator",
                        "Map operator role draft field.", "operator role cannot grant approval",
                        "ARTIFACT_DRAFT_PREFLIGHT_OPERATOR_ROLE_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessOperatorWindowService
                                .ENDPOINT),
                field("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_WINDOW_ID",
                        "ARTIFACT_DRAFT_READINESS_07_WINDOW_ID", "capture-policy",
                        "Map capture window id draft field.", "window id cannot open runtime",
                        "ARTIFACT_DRAFT_PREFLIGHT_WINDOW_ID_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessOperatorWindowService
                                .ENDPOINT),
                field("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_CHANNEL_POLICY",
                        "ARTIFACT_DRAFT_READINESS_08_CHANNEL_POLICY", "capture-policy",
                        "Map capture channel policy draft field.", "channel policy cannot expose write route",
                        "ARTIFACT_DRAFT_PREFLIGHT_CHANNEL_POLICY_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessOperatorWindowService
                                .ENDPOINT),
                field("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_SIGNATURE_ALGORITHM",
                        "ARTIFACT_DRAFT_READINESS_09_SIGNATURE_ALGORITHM", "signature",
                        "Map signature algorithm draft field.", "algorithm cannot contain signature material",
                        "ARTIFACT_DRAFT_PREFLIGHT_SIGNATURE_ALGORITHM_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSignatureStatementService
                                .ENDPOINT),
                field("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_DETACHED_SIGNATURE_PLACEHOLDER",
                        "ARTIFACT_DRAFT_READINESS_10_DETACHED_SIGNATURE_PLACEHOLDER", "signature",
                        "Map detached signature placeholder.", "placeholder is not detached signature material",
                        "ARTIFACT_DRAFT_PREFLIGHT_DETACHED_SIGNATURE_PLACEHOLDER_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSignatureStatementService
                                .ENDPOINT),
                field("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_SIGNATURE_REDACTION",
                        "ARTIFACT_DRAFT_READINESS_11_SIGNATURE_REDACTION", "signature",
                        "Map signature redaction draft field.", "raw signature material remains absent",
                        "ARTIFACT_DRAFT_PREFLIGHT_SIGNATURE_REDACTION_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSignatureStatementService
                                .ENDPOINT),
                field("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_APPROVAL_STATEMENT_DIGEST",
                        "ARTIFACT_DRAFT_READINESS_12_APPROVAL_STATEMENT_DIGEST", "statement",
                        "Map approval statement digest placeholder.", "statement digest is not signed statement text",
                        "ARTIFACT_DRAFT_PREFLIGHT_APPROVAL_STATEMENT_DIGEST_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSignatureStatementService
                                .ENDPOINT),
                field("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_PREFLIGHT_SOURCE_VERSION",
                        "ARTIFACT_DRAFT_READINESS_13_SOURCE_VERSION", "evidence",
                        "Map source evidence version draft field.", "source version cannot import evidence",
                        "ARTIFACT_DRAFT_PREFLIGHT_SOURCE_VERSION_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessEvidenceSourceService
                                .ENDPOINT)
        );
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse.DraftField
    field(
            String code,
            String sourceReadinessItem,
            String draftStage,
            String fieldRequirement,
            String materializationBlocker,
            String guardCode,
            String sourceEndpoint
    ) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSupport.field(
                code,
                sourceReadinessItem,
                draftStage,
                fieldRequirement,
                materializationBlocker,
                guardCode,
                sourceEndpoint
        );
    }
}
