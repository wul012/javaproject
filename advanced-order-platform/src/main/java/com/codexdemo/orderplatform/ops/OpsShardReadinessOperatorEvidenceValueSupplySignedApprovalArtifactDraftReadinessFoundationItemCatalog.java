package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFoundationItemCatalog {

    static final int FOUNDATION_ITEM_COUNT = 13;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFoundationItemCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse.ReadinessItem>
    foundationItems() {
        return List.of(
                item("ARTIFACT_DRAFT_READINESS_01_REQUEST_ID", "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_REQUEST_ID",
                        "request", "Confirm request id fragment is owned before any draft planning.",
                        "request id cannot create manual artifact draft", "OWNERSHIP_REQUEST_METADATA",
                        "java-v759-artifact-preflight", "request-id-readiness",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCatalogService
                                .ENDPOINT),
                item("ARTIFACT_DRAFT_READINESS_02_CAPTURE_DIGEST",
                        "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CAPTURE_PREFLIGHT_DIGEST",
                        "digest", "Confirm source capture preflight digest chain is owned.",
                        "capture digest cannot bypass artifact preflight", "OWNERSHIP_DIGEST_CHAIN",
                        "java-v759-artifact-preflight", "capture-digest-readiness",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCaptureDigestBindingService
                                .ENDPOINT),
                item("ARTIFACT_DRAFT_READINESS_03_TEMPLATE_DIGEST",
                        "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_TEMPLATE_DIGEST",
                        "digest", "Confirm signed approval template digest ownership.",
                        "template digest cannot materialize artifact", "OWNERSHIP_DIGEST_CHAIN",
                        "java-v759-artifact-preflight", "template-digest-readiness",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightTemplateReviewDigestService
                                .ENDPOINT),
                item("ARTIFACT_DRAFT_READINESS_04_REVIEW_DIGEST",
                        "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_REVIEW_DIGEST",
                        "digest", "Confirm approval packet review digest ownership.",
                        "review digest cannot emit grant", "OWNERSHIP_DIGEST_CHAIN",
                        "java-v759-artifact-preflight", "review-digest-readiness",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightTemplateReviewDigestService
                                .ENDPOINT),
                item("ARTIFACT_DRAFT_READINESS_05_OPERATOR_IDENTITY",
                        "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_OPERATOR_IDENTITY",
                        "operator", "Confirm operator identity alias ownership.",
                        "operator identity remains alias-only", "OWNERSHIP_OPERATOR_ALIAS",
                        "java-v759-artifact-preflight", "operator-identity-readiness",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightOperatorFragmentService
                                .ENDPOINT),
                item("ARTIFACT_DRAFT_READINESS_06_OPERATOR_ROLE",
                        "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_OPERATOR_ROLE",
                        "operator", "Confirm operator role ownership.",
                        "operator role cannot grant approval", "OWNERSHIP_OPERATOR_ALIAS",
                        "java-v759-artifact-preflight", "operator-role-readiness",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightOperatorFragmentService
                                .ENDPOINT),
                item("ARTIFACT_DRAFT_READINESS_07_WINDOW_ID",
                        "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_WINDOW_ID",
                        "capture-policy", "Confirm capture window placeholder ownership.",
                        "window id cannot open runtime", "OWNERSHIP_CAPTURE_POLICY",
                        "java-v759-artifact-preflight", "window-id-readiness",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCapturePolicyFragmentService
                                .ENDPOINT),
                item("ARTIFACT_DRAFT_READINESS_08_CHANNEL_POLICY",
                        "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CHANNEL_POLICY",
                        "capture-policy", "Confirm channel policy ownership.",
                        "channel policy cannot expose write route", "OWNERSHIP_CAPTURE_POLICY",
                        "java-v759-artifact-preflight", "channel-policy-readiness",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCapturePolicyFragmentService
                                .ENDPOINT),
                item("ARTIFACT_DRAFT_READINESS_09_SIGNATURE_ALGORITHM",
                        "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_SIGNATURE_ALGORITHM",
                        "signature", "Confirm signature algorithm policy ownership.",
                        "algorithm cannot contain signature material", "OWNERSHIP_SIGNATURE_POLICY",
                        "java-v759-artifact-preflight", "signature-algorithm-readiness",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSignatureSealService
                                .ENDPOINT),
                item("ARTIFACT_DRAFT_READINESS_10_DETACHED_SIGNATURE_PLACEHOLDER",
                        "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_DETACHED_SIGNATURE_PLACEHOLDER",
                        "signature", "Confirm detached signature placeholder ownership.",
                        "placeholder is not signature material", "OWNERSHIP_SIGNATURE_POLICY",
                        "java-v759-artifact-preflight", "detached-signature-readiness",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSignatureSealService
                                .ENDPOINT),
                item("ARTIFACT_DRAFT_READINESS_11_SIGNATURE_REDACTION",
                        "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_SIGNATURE_REDACTION",
                        "signature", "Confirm signature redaction policy ownership.",
                        "raw signature material remains absent", "OWNERSHIP_SIGNATURE_POLICY",
                        "java-v759-artifact-preflight", "signature-redaction-readiness",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSignatureSealService
                                .ENDPOINT),
                item("ARTIFACT_DRAFT_READINESS_12_APPROVAL_STATEMENT_DIGEST",
                        "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_APPROVAL_STATEMENT_DIGEST",
                        "statement", "Confirm statement digest placeholder ownership.",
                        "statement digest is not signed text", "OWNERSHIP_STATEMENT_EVIDENCE",
                        "java-v759-artifact-preflight", "statement-digest-readiness",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightStatementEvidenceService
                                .ENDPOINT),
                item("ARTIFACT_DRAFT_READINESS_13_SOURCE_VERSION",
                        "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_SOURCE_VERSION",
                        "evidence", "Confirm source evidence version ownership.",
                        "source version cannot import evidence", "OWNERSHIP_STATEMENT_EVIDENCE",
                        "java-v759-artifact-preflight", "source-version-readiness",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightStatementEvidenceService
                                .ENDPOINT)
        );
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse.ReadinessItem
    item(
            String code,
            String sourceArtifactFragment,
            String readinessStage,
            String readinessRequirement,
            String blockedReason,
            String ownershipCode,
            String evidenceFileId,
            String evidenceSnippetId,
            String sourceEndpoint
    ) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSupport.item(
                code,
                sourceArtifactFragment,
                readinessStage,
                readinessRequirement,
                blockedReason,
                ownershipCode,
                evidenceFileId,
                evidenceSnippetId,
                sourceEndpoint
        );
    }
}
