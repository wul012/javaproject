package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFragmentCatalog {

    static final int FRAGMENT_COUNT = 25;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFragmentCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
            .ArtifactFragment> allFragments() {
        return List.of(
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_REQUEST_ID", "capture.requestId",
                        "request", "Map artifact preflight request id fragment.",
                        "request id cannot materialize artifact", "ARTIFACT_PREFLIGHT_REQUEST_ID_SEAL",
                        "node-v1086-artifact-preflight", "artifact-request-id",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCloseoutService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CAPTURE_PREFLIGHT_DIGEST",
                        "capture.preflightDigest", "digest", "Bind source capture preflight digest fragment.",
                        "capture digest cannot create signed approval", "ARTIFACT_PREFLIGHT_CAPTURE_PREFLIGHT_DIGEST_SEAL",
                        "node-v1061-capture-preflight", "capture-preflight-digest",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCloseoutService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_TEMPLATE_DIGEST",
                        "template.digest", "digest", "Bind signed approval template digest fragment.",
                        "template digest cannot create artifact body", "ARTIFACT_PREFLIGHT_TEMPLATE_DIGEST_SEAL",
                        "node-v1036-template", "template-digest",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightTemplateDigestBindingService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_REVIEW_DIGEST",
                        "review.digest", "digest", "Bind approval packet review digest fragment.",
                        "review digest cannot emit approval grant", "ARTIFACT_PREFLIGHT_REVIEW_DIGEST_SEAL",
                        "node-v1011-review", "review-digest",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightReviewDigestBindingService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_OPERATOR_IDENTITY",
                        "operator.identityAlias", "operator", "Map operator identity artifact fragment.",
                        "operator identity remains alias-only", "ARTIFACT_PREFLIGHT_OPERATOR_IDENTITY_SEAL",
                        "node-v1086-artifact-preflight", "operator-identity-fragment",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightOperatorInputMirrorService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_OPERATOR_ROLE",
                        "operator.role", "operator", "Map operator role artifact fragment.",
                        "operator role cannot grant approval", "ARTIFACT_PREFLIGHT_OPERATOR_ROLE_SEAL",
                        "node-v1086-artifact-preflight", "operator-role-fragment",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightOperatorInputMirrorService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_WINDOW_ID",
                        "capture.manualWindowId", "capture-window", "Map capture window id artifact fragment.",
                        "window id cannot open runtime", "ARTIFACT_PREFLIGHT_WINDOW_ID_SEAL",
                        "node-v1086-artifact-preflight", "window-id-fragment",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightTimingWindowService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CHANNEL_POLICY",
                        "capture.channelPolicy", "capture-policy", "Map capture channel policy artifact fragment.",
                        "channel policy cannot expose write route", "ARTIFACT_PREFLIGHT_CHANNEL_POLICY_SEAL",
                        "node-v1086-artifact-preflight", "channel-policy-fragment",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightChannelSignaturePolicyService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_SIGNATURE_ALGORITHM",
                        "signature.algorithmPolicy", "signature", "Map signature algorithm artifact fragment.",
                        "algorithm fragment cannot contain signature material",
                        "ARTIFACT_PREFLIGHT_SIGNATURE_ALGORITHM_SEAL",
                        "node-v1086-artifact-preflight", "signature-algorithm-fragment",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightChannelSignaturePolicyService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_DETACHED_SIGNATURE_PLACEHOLDER",
                        "signature.detachedPlaceholder", "signature",
                        "Map detached signature placeholder artifact fragment.",
                        "placeholder cannot become signature material",
                        "ARTIFACT_PREFLIGHT_DETACHED_SIGNATURE_PLACEHOLDER_SEAL",
                        "node-v1086-artifact-preflight", "detached-signature-placeholder",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightChannelSignaturePolicyService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_SIGNATURE_REDACTION",
                        "signature.materialRedaction", "signature", "Map signature material redaction fragment.",
                        "redaction fragment keeps raw signature absent", "ARTIFACT_PREFLIGHT_SIGNATURE_REDACTION_SEAL",
                        "node-v1086-artifact-preflight", "signature-redaction-fragment",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightChannelSignaturePolicyService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_APPROVAL_STATEMENT_DIGEST",
                        "approval.statementDigestPlaceholder", "statement",
                        "Map approval statement digest placeholder fragment.",
                        "statement digest placeholder is not signed text",
                        "ARTIFACT_PREFLIGHT_APPROVAL_STATEMENT_DIGEST_SEAL",
                        "node-v1086-artifact-preflight", "approval-statement-digest",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightStatementJustificationService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_SOURCE_VERSION",
                        "evidence.version", "evidence", "Map source evidence version fragment.",
                        "version fragment cannot import evidence", "ARTIFACT_PREFLIGHT_SOURCE_VERSION_SEAL",
                        "node-v1086-artifact-preflight", "source-version-fragment",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSourceEvidenceMirrorService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_SOURCE_FILE",
                        "evidence.fileId", "evidence", "Map source evidence file fragment.",
                        "file fragment cannot read file contents", "ARTIFACT_PREFLIGHT_SOURCE_FILE_SEAL",
                        "node-v1086-artifact-preflight", "source-file-fragment",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSourceEvidenceMirrorService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_SOURCE_SNIPPET",
                        "evidence.snippetId", "evidence", "Map source evidence snippet fragment.",
                        "snippet fragment cannot import payload", "ARTIFACT_PREFLIGHT_SOURCE_SNIPPET_SEAL",
                        "node-v1086-artifact-preflight", "source-snippet-fragment",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSourceEvidenceMirrorService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_REDACTED_VALUE_DIGEST",
                        "value.redactedDigest", "value", "Map redacted value digest artifact fragment.",
                        "redacted digest cannot be raw value hash",
                        "ARTIFACT_PREFLIGHT_REDACTED_VALUE_DIGEST_SEAL",
                        "node-v1086-artifact-preflight", "redacted-value-digest-fragment",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightRedactionProvenanceBindingService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_VALUE_SHAPE",
                        "value.shapeBinding", "value", "Map value shape artifact fragment.",
                        "value shape cannot accept operator value body", "ARTIFACT_PREFLIGHT_VALUE_SHAPE_SEAL",
                        "node-v1086-artifact-preflight", "value-shape-fragment",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightRedactionProvenanceBindingService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_REDACTION_POLICY",
                        "policy.redaction", "policy", "Map redaction policy artifact fragment.",
                        "redaction policy cannot reveal secrets", "ARTIFACT_PREFLIGHT_REDACTION_POLICY_SEAL",
                        "node-v1086-artifact-preflight", "redaction-policy-fragment",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightRedactionProvenanceBindingService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_PROVENANCE_POLICY",
                        "policy.provenance", "policy", "Map provenance policy artifact fragment.",
                        "provenance policy cannot import evidence", "ARTIFACT_PREFLIGHT_PROVENANCE_POLICY_SEAL",
                        "node-v1086-artifact-preflight", "provenance-policy-fragment",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightRedactionProvenanceBindingService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_NO_RAW_SECRET_LOCK",
                        "locks.rawSecretSignature", "lock", "Map no raw secret artifact lock.",
                        "raw secret material remains absent", "ARTIFACT_PREFLIGHT_NO_RAW_SECRET_SEAL",
                        "node-v1086-artifact-preflight", "no-raw-secret-lock",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightFailClosedLockService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_NO_GRANT_LOCK",
                        "locks.approvalGrant", "lock", "Map no approval grant artifact lock.",
                        "approval grant remains not emitted", "ARTIFACT_PREFLIGHT_NO_GRANT_SEAL",
                        "node-v1086-artifact-preflight", "no-grant-lock",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightFailClosedLockService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_ZERO_VALUE_IMPORT_LOCK",
                        "locks.zeroValueImport", "lock", "Map zero value import artifact lock.",
                        "value import remains zero", "ARTIFACT_PREFLIGHT_ZERO_VALUE_IMPORT_SEAL",
                        "node-v1086-artifact-preflight", "zero-value-import-lock",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightFailClosedLockService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_NO_WRITE_ROUTE_LOCK",
                        "locks.noWriteRoute", "lock", "Map no write route artifact lock.",
                        "write route remains unavailable", "ARTIFACT_PREFLIGHT_NO_WRITE_ROUTE_SEAL",
                        "node-v1086-artifact-preflight", "no-write-route-lock",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightFailClosedLockService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_SIBLING_NON_MUTATION_LOCK",
                        "locks.siblingMutation", "lock", "Map sibling non-mutation artifact lock.",
                        "sibling services remain untouched", "ARTIFACT_PREFLIGHT_SIBLING_NON_MUTATION_SEAL",
                        "node-v1086-artifact-preflight", "sibling-non-mutation-lock",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightFailClosedLockService
                                .ENDPOINT),
                fragment("SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CLOSEOUT",
                        "closeout.boundary", "closeout", "Map artifact preflight closeout boundary.",
                        "next step requires separate artifact draft plan", "ARTIFACT_PREFLIGHT_CLOSEOUT_SEAL",
                        "node-v1086-artifact-preflight", "artifact-preflight-closeout",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCloseoutService
                                .ENDPOINT)
        );
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
            .ArtifactFragment> fragments(int fromInclusive, int toExclusive) {
        return List.copyOf(allFragments().subList(fromInclusive, toExclusive));
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
            .ArtifactFragment fragment(
            String code,
            String sourceCaptureInput,
            String artifactStage,
            String fragmentRequirement,
            String materializationBlocker,
            String sealCode,
            String evidenceFileId,
            String evidenceSnippetId,
            String sourceEndpoint
    ) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSupport.fragment(
                code,
                sourceCaptureInput,
                artifactStage,
                fragmentRequirement,
                materializationBlocker,
                sealCode,
                evidenceFileId,
                evidenceSnippetId,
                sourceEndpoint
        );
    }
}
