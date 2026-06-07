package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGuardCatalog {

    static final int GUARD_COUNT = 25;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGuardCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse.DraftGuard>
    allGuards() {
        return List.of(
                guard("ARTIFACT_DRAFT_PREFLIGHT_REQUEST_ID_GUARD", "request",
                        "Guard request id as metadata only.", "reject-manual-draft-materialization", "fail-closed"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_ARTIFACT_PREFLIGHT_DIGEST_GUARD", "digest",
                        "Guard source artifact preflight digest.", "reject-digest-bypass", "required"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_TEMPLATE_DIGEST_GUARD", "digest",
                        "Guard source template digest.", "reject-template-substitution", "required"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_REVIEW_DIGEST_GUARD", "digest",
                        "Guard source review digest.", "reject-review-substitution", "required"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_OPERATOR_IDENTITY_GUARD", "operator",
                        "Guard operator identity as alias-only.", "reject-credential-material", "fail-closed"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_OPERATOR_ROLE_GUARD", "operator",
                        "Guard operator role without grant authority.", "reject-approval-grant", "fail-closed"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_WINDOW_ID_GUARD", "capture-policy",
                        "Guard capture window id as placeholder.", "reject-runtime-open", "fail-closed"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_CHANNEL_POLICY_GUARD", "capture-policy",
                        "Guard channel policy without write route.", "reject-write-route", "fail-closed"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_SIGNATURE_ALGORITHM_GUARD", "signature",
                        "Guard signature algorithm without material.", "reject-signature-material", "fail-closed"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_DETACHED_SIGNATURE_PLACEHOLDER_GUARD", "signature",
                        "Guard detached signature placeholder.", "reject-detached-signature-body", "fail-closed"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_SIGNATURE_REDACTION_GUARD", "signature",
                        "Guard signature redaction.", "reject-raw-signature", "fail-closed"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_APPROVAL_STATEMENT_DIGEST_GUARD", "statement",
                        "Guard statement digest placeholder.", "reject-signed-statement", "placeholder-only"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_SOURCE_VERSION_GUARD", "evidence",
                        "Guard source version without import.", "reject-evidence-import", "metadata-only"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_SOURCE_FILE_GUARD", "evidence",
                        "Guard source file id without file load.", "reject-file-load", "metadata-only"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_SOURCE_SNIPPET_GUARD", "evidence",
                        "Guard source snippet id without payload import.", "reject-payload-import", "metadata-only"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_REDACTED_VALUE_DIGEST_GUARD", "value",
                        "Guard redacted value digest reference.", "reject-raw-value-hash", "fail-closed"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_VALUE_SHAPE_GUARD", "value",
                        "Guard value shape without value body.", "reject-value-body", "fail-closed"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_REDACTION_POLICY_GUARD", "policy",
                        "Guard redaction policy mirror.", "reject-secret-reveal", "fail-closed"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_PROVENANCE_POLICY_GUARD", "policy",
                        "Guard provenance policy mirror.", "reject-evidence-import", "fail-closed"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_NO_RAW_SECRET_GUARD", "lock",
                        "Guard raw secret absence.", "reject-raw-secret", "fail-closed"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_NO_GRANT_GUARD", "lock",
                        "Guard approval grant absence.", "reject-approval-grant", "fail-closed"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_ZERO_VALUE_IMPORT_GUARD", "lock",
                        "Guard zero value import.", "reject-value-count-increase", "fail-closed"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_NO_WRITE_ROUTE_GUARD", "lock",
                        "Guard write route absence.", "reject-write-route", "fail-closed"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_SIBLING_NON_MUTATION_GUARD", "lock",
                        "Guard sibling non-mutation.", "reject-sibling-mutation", "fail-closed"),
                guard("ARTIFACT_DRAFT_PREFLIGHT_CLOSEOUT_GUARD", "closeout",
                        "Guard closeout boundary before manual draft.", "reject-auto-manual-draft", "required")
        );
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse.DraftGuard>
    guards(int fromInclusive, int toExclusive) {
        return List.copyOf(allGuards().subList(fromInclusive, toExclusive));
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse.DraftGuard
    guard(String code, String category, String guardRequirement, String rejectionCode, String enforcement) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSupport.guard(
                code,
                category,
                guardRequirement,
                rejectionCode,
                enforcement
        );
    }
}
