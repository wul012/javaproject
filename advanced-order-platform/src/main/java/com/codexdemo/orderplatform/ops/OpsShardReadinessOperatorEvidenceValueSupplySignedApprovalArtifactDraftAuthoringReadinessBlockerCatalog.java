package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessBlockerCatalog {

    static final int BLOCKER_COUNT = 25;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessBlockerCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
            .AuthoringBlocker> allBlockers() {
        return List.of(
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_REQUEST_MANIFEST_BLOCKER",
                        "request", "Reject authoring when the reviewed request manifest is absent.",
                        "REJECT_DRAFT_AUTHORING_READINESS_REQUEST_MANIFEST_MISSING", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_ARTIFACT_PREFLIGHT_DIGEST_BLOCKER",
                        "digest", "Reject authoring when artifact preflight digest is not pinned.",
                        "REJECT_DRAFT_AUTHORING_READINESS_ARTIFACT_PREFLIGHT_DIGEST_MISSING", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_TEMPLATE_DIGEST_BLOCKER",
                        "digest", "Reject authoring when template digest is not pinned.",
                        "REJECT_DRAFT_AUTHORING_READINESS_TEMPLATE_DIGEST_MISSING", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_REVIEW_DIGEST_BLOCKER",
                        "digest", "Reject authoring when approval review digest is not pinned.",
                        "REJECT_DRAFT_AUTHORING_READINESS_REVIEW_DIGEST_MISSING", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_OPERATOR_IDENTITY_BLOCKER",
                        "operator", "Reject authoring when reviewed operator identity is absent.",
                        "REJECT_DRAFT_AUTHORING_READINESS_OPERATOR_IDENTITY_MISSING", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_OPERATOR_ROLE_BLOCKER",
                        "operator", "Reject authoring when reviewed operator role is absent.",
                        "REJECT_DRAFT_AUTHORING_READINESS_OPERATOR_ROLE_MISSING", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_WINDOW_ID_BLOCKER",
                        "capture-policy", "Reject authoring when the capture window id is absent.",
                        "REJECT_DRAFT_AUTHORING_READINESS_WINDOW_ID_MISSING", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_CHANNEL_POLICY_BLOCKER",
                        "capture-policy", "Reject authoring when channel policy is absent.",
                        "REJECT_DRAFT_AUTHORING_READINESS_CHANNEL_POLICY_MISSING", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_SIGNATURE_ALGORITHM_BLOCKER",
                        "signature", "Reject authoring when signature algorithm policy is absent.",
                        "REJECT_DRAFT_AUTHORING_READINESS_SIGNATURE_ALGORITHM_MISSING", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_DETACHED_SIGNATURE_BLOCKER",
                        "signature", "Reject authoring if detached signature material is present in readiness.",
                        "REJECT_DRAFT_AUTHORING_READINESS_DETACHED_SIGNATURE_PRESENT", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_SIGNATURE_REDACTION_BLOCKER",
                        "signature", "Reject authoring when signature redaction policy is absent.",
                        "REJECT_DRAFT_AUTHORING_READINESS_SIGNATURE_REDACTION_MISSING", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_APPROVAL_STATEMENT_DIGEST_BLOCKER",
                        "statement", "Reject authoring when approval statement digest is absent.",
                        "REJECT_DRAFT_AUTHORING_READINESS_APPROVAL_STATEMENT_DIGEST_MISSING", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_SOURCE_VERSION_BLOCKER",
                        "evidence", "Reject authoring when source evidence version is absent.",
                        "REJECT_DRAFT_AUTHORING_READINESS_SOURCE_VERSION_MISSING", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_SOURCE_FILE_BLOCKER",
                        "evidence", "Reject authoring when source evidence file is absent.",
                        "REJECT_DRAFT_AUTHORING_READINESS_SOURCE_FILE_MISSING", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_SOURCE_SNIPPET_BLOCKER",
                        "evidence", "Reject authoring when source evidence snippet is absent.",
                        "REJECT_DRAFT_AUTHORING_READINESS_SOURCE_SNIPPET_MISSING", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_REDACTED_VALUE_DIGEST_BLOCKER",
                        "value", "Reject authoring when redacted value digest is absent.",
                        "REJECT_DRAFT_AUTHORING_READINESS_REDACTED_VALUE_DIGEST_MISSING", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_VALUE_SHAPE_BLOCKER",
                        "value", "Reject authoring when value shape metadata is absent.",
                        "REJECT_DRAFT_AUTHORING_READINESS_VALUE_SHAPE_MISSING", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_REDACTION_POLICY_BLOCKER",
                        "policy", "Reject authoring when redaction policy is absent.",
                        "REJECT_DRAFT_AUTHORING_READINESS_REDACTION_POLICY_MISSING", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_PROVENANCE_POLICY_BLOCKER",
                        "policy", "Reject authoring when provenance policy is absent.",
                        "REJECT_DRAFT_AUTHORING_READINESS_PROVENANCE_POLICY_MISSING", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_RAW_SECRET_EMBARGO_BLOCKER",
                        "embargo", "Reject authoring if raw secret material is present.",
                        "REJECT_DRAFT_AUTHORING_READINESS_RAW_SECRET_PRESENT", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_APPROVAL_GRANT_EMBARGO_BLOCKER",
                        "embargo", "Reject authoring if an approval grant already exists.",
                        "REJECT_DRAFT_AUTHORING_READINESS_APPROVAL_GRANT_PRESENT", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_ZERO_VALUE_IMPORT_EMBARGO_BLOCKER",
                        "embargo", "Reject authoring if value submission or import counts are nonzero.",
                        "REJECT_DRAFT_AUTHORING_READINESS_VALUE_IMPORT_NONZERO", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_WRITE_ROUTE_EMBARGO_BLOCKER",
                        "embargo", "Reject authoring if write routing is enabled.",
                        "REJECT_DRAFT_AUTHORING_READINESS_WRITE_ROUTE_ENABLED", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_SIBLING_NON_MUTATION_BLOCKER",
                        "embargo", "Reject authoring if sibling state mutation is allowed.",
                        "REJECT_DRAFT_AUTHORING_READINESS_SIBLING_MUTATION_ALLOWED", "fail-closed"),
                blocker("SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_CLOSEOUT_BLOCKER",
                        "closeout", "Reject authoring if a separate signed draft package is not declared.",
                        "REJECT_DRAFT_AUTHORING_READINESS_CLOSEOUT_INCOMPLETE", "fail-closed")
        );
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
            .AuthoringBlocker> blockers(int fromInclusive, int toExclusive) {
        return List.copyOf(allBlockers().subList(fromInclusive, toExclusive));
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
            .AuthoringBlocker blocker(
                    String code,
                    String category,
                    String blocker,
                    String rejectionCode,
                    String enforcement
    ) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSupport
                .blocker(code, category, blocker, rejectionCode, enforcement);
    }
}
