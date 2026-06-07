package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGuardCatalog {

    static final int GUARD_COUNT = 25;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGuardCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
            .PackageGuard> allGuards() {
        return List.of(
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_REQUEST_MANIFEST_GUARD", "request",
                        "Guard request manifest slot presence.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_REQUEST_MANIFEST_MISSING", "fail-closed"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_ARTIFACT_PREFLIGHT_DIGEST_GUARD", "digest",
                        "Guard artifact preflight digest pin.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_ARTIFACT_PREFLIGHT_DIGEST_UNPINNED", "required"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_TEMPLATE_DIGEST_GUARD", "digest",
                        "Guard template digest pin.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_TEMPLATE_DIGEST_UNPINNED", "required"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_REVIEW_DIGEST_GUARD", "digest",
                        "Guard approval review digest pin.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_REVIEW_DIGEST_UNPINNED", "required"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_OPERATOR_IDENTITY_GUARD", "operator",
                        "Guard operator identity without credential capture.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_OPERATOR_IDENTITY_CAPTURED", "fail-closed"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_OPERATOR_ROLE_GUARD", "operator",
                        "Guard operator role without grant authority.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_OPERATOR_ROLE_GRANTED", "fail-closed"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_WINDOW_ID_GUARD", "capture-policy",
                        "Guard capture window id without opening runtime.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_WINDOW_OPENED", "fail-closed"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_CHANNEL_POLICY_GUARD", "capture-policy",
                        "Guard capture channel policy while adapters stay disabled.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_CHANNEL_ADAPTER_ENABLED", "fail-closed"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_SIGNATURE_ALGORITHM_GUARD", "signature",
                        "Guard signature algorithm policy without signature material.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_SIGNATURE_MATERIAL_PRESENT", "fail-closed"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_DETACHED_SIGNATURE_GUARD", "signature",
                        "Guard detached signature slot as out-of-band placeholder.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_DETACHED_SIGNATURE_BODY_PRESENT", "fail-closed"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_SIGNATURE_REDACTION_GUARD", "signature",
                        "Guard signature redaction policy without raw signature.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_RAW_SIGNATURE_PRESENT", "fail-closed"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_APPROVAL_STATEMENT_DIGEST_GUARD", "statement",
                        "Guard approval statement digest without signed statement text.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_SIGNED_STATEMENT_TEXT_PRESENT", "fail-closed"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_SOURCE_VERSION_GUARD", "evidence",
                        "Guard source evidence version without evidence import.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_SOURCE_VERSION_IMPORT", "metadata-only"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_SOURCE_FILE_GUARD", "evidence",
                        "Guard source evidence file id without file load.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_SOURCE_FILE_LOAD", "metadata-only"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_SOURCE_SNIPPET_GUARD", "evidence",
                        "Guard source evidence snippet id without payload assembly.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_SOURCE_SNIPPET_PAYLOAD", "metadata-only"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_REDACTED_VALUE_DIGEST_GUARD", "value",
                        "Guard redacted value digest without raw operator value.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_RAW_VALUE_PRESENT", "fail-closed"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_VALUE_SHAPE_GUARD", "value",
                        "Guard value shape metadata without value body.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_VALUE_BODY_PRESENT", "fail-closed"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_REDACTION_POLICY_GUARD", "policy",
                        "Guard redaction policy without secret reveal.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_SECRET_REVEAL", "fail-closed"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_PROVENANCE_POLICY_GUARD", "policy",
                        "Guard provenance policy without evidence import.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_PROVENANCE_IMPORT", "fail-closed"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_RAW_SECRET_EMBARGO_GUARD", "embargo",
                        "Guard raw secret embargo evidence.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_RAW_SECRET_PRESENT", "fail-closed"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_APPROVAL_GRANT_EMBARGO_GUARD", "embargo",
                        "Guard approval grant embargo evidence.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_APPROVAL_GRANT_EMITTED", "fail-closed"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_ZERO_VALUE_IMPORT_EMBARGO_GUARD", "embargo",
                        "Guard zero operator value and evidence import counts.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_VALUE_IMPORT_NONZERO", "fail-closed"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_WRITE_ROUTE_EMBARGO_GUARD", "embargo",
                        "Guard write route embargo evidence.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_WRITE_ROUTE_ENABLED", "fail-closed"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_SIBLING_NON_MUTATION_GUARD", "embargo",
                        "Guard sibling non-mutation evidence.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_SIBLING_MUTATION_ENABLED", "fail-closed"),
                guard("ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_CLOSEOUT_GUARD", "closeout",
                        "Guard closeout before separate human draft artifact authoring.",
                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_NEXT_STEP_NOT_HUMAN_DRAFT_ARTIFACT", "required")
        );
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
            .PackageGuard> guards(int fromInclusive, int toExclusive) {
        return List.copyOf(allGuards().subList(fromInclusive, toExclusive));
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
            .PackageGuard guard(
                    String code,
                    String category,
                    String guard,
                    String rejectionCode,
                    String enforcement
    ) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSupport
                .guard(code, category, guard, rejectionCode, enforcement);
    }
}
