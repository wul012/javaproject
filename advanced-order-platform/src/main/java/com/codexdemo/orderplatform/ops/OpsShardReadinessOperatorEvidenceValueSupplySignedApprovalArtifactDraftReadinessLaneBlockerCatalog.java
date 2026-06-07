package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneBlockerCatalog {

    static final int BLOCKER_COUNT = 25;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneBlockerCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
            .ControlBlocker> allBlockers() {
        return List.of(
                blocker("DRAFT_READINESS_LANE_REQUEST_MANIFEST_BLOCKER", "request",
                        "Request manifest readiness lane must be present.",
                        "REJECT_DRAFT_READINESS_REQUEST_MANIFEST_MISSING", "fail-closed"),
                blocker("DRAFT_READINESS_LANE_ARTIFACT_PREFLIGHT_DIGEST_BLOCKER", "digest",
                        "Artifact preflight digest must stay pinned.",
                        "REJECT_DRAFT_READINESS_ARTIFACT_PREFLIGHT_DIGEST_UNPINNED", "required"),
                blocker("DRAFT_READINESS_LANE_TEMPLATE_DIGEST_BLOCKER", "digest",
                        "Template digest must stay pinned.",
                        "REJECT_DRAFT_READINESS_TEMPLATE_DIGEST_UNPINNED", "required"),
                blocker("DRAFT_READINESS_LANE_REVIEW_DIGEST_BLOCKER", "digest",
                        "Approval review digest must stay pinned.",
                        "REJECT_DRAFT_READINESS_REVIEW_DIGEST_UNPINNED", "required"),
                blocker("DRAFT_READINESS_LANE_OPERATOR_IDENTITY_BLOCKER", "operator",
                        "Operator identity must be reviewed without credentials.",
                        "REJECT_DRAFT_READINESS_OPERATOR_IDENTITY_UNREVIEWED", "fail-closed"),
                blocker("DRAFT_READINESS_LANE_OPERATOR_ROLE_BLOCKER", "operator",
                        "Operator role must be reviewed without approval authority.",
                        "REJECT_DRAFT_READINESS_OPERATOR_ROLE_UNREVIEWED", "fail-closed"),
                blocker("DRAFT_READINESS_LANE_WINDOW_ID_BLOCKER", "capture-policy",
                        "Capture window id must be reviewed without runtime opening.",
                        "REJECT_DRAFT_READINESS_WINDOW_ID_UNREVIEWED", "fail-closed"),
                blocker("DRAFT_READINESS_LANE_CHANNEL_POLICY_BLOCKER", "capture-policy",
                        "Capture channel policy must be reviewed without write route exposure.",
                        "REJECT_DRAFT_READINESS_CHANNEL_POLICY_UNREVIEWED", "fail-closed"),
                blocker("DRAFT_READINESS_LANE_SIGNATURE_ALGORITHM_BLOCKER", "signature",
                        "Signature algorithm policy must be reviewed.",
                        "REJECT_DRAFT_READINESS_SIGNATURE_ALGORITHM_UNREVIEWED", "fail-closed"),
                blocker("DRAFT_READINESS_LANE_DETACHED_SIGNATURE_BLOCKER", "signature",
                        "Detached signature placeholder must be reviewed.",
                        "REJECT_DRAFT_READINESS_DETACHED_SIGNATURE_PLACEHOLDER_UNREVIEWED", "fail-closed"),
                blocker("DRAFT_READINESS_LANE_SIGNATURE_REDACTION_BLOCKER", "signature",
                        "Signature redaction policy must be reviewed.",
                        "REJECT_DRAFT_READINESS_SIGNATURE_REDACTION_UNREVIEWED", "fail-closed"),
                blocker("DRAFT_READINESS_LANE_APPROVAL_STATEMENT_DIGEST_BLOCKER", "statement",
                        "Approval statement digest must stay pinned.",
                        "REJECT_DRAFT_READINESS_APPROVAL_STATEMENT_DIGEST_UNPINNED", "required"),
                blocker("DRAFT_READINESS_LANE_SOURCE_VERSION_BLOCKER", "evidence",
                        "Source evidence version must be reviewed without import.",
                        "REJECT_DRAFT_READINESS_SOURCE_VERSION_UNREVIEWED", "metadata-only"),
                blocker("DRAFT_READINESS_LANE_SOURCE_FILE_BLOCKER", "evidence",
                        "Source evidence file id must be reviewed without file load.",
                        "REJECT_DRAFT_READINESS_SOURCE_FILE_UNREVIEWED", "metadata-only"),
                blocker("DRAFT_READINESS_LANE_SOURCE_SNIPPET_BLOCKER", "evidence",
                        "Source evidence snippet id must be reviewed without payload import.",
                        "REJECT_DRAFT_READINESS_SOURCE_SNIPPET_UNREVIEWED", "metadata-only"),
                blocker("DRAFT_READINESS_LANE_REDACTED_VALUE_DIGEST_BLOCKER", "value",
                        "Redacted value digest must stay pinned.",
                        "REJECT_DRAFT_READINESS_REDACTED_VALUE_DIGEST_UNPINNED", "fail-closed"),
                blocker("DRAFT_READINESS_LANE_VALUE_SHAPE_BLOCKER", "value",
                        "Value shape must be reviewed without value body.",
                        "REJECT_DRAFT_READINESS_VALUE_SHAPE_UNREVIEWED", "fail-closed"),
                blocker("DRAFT_READINESS_LANE_REDACTION_POLICY_BLOCKER", "policy",
                        "Redaction policy must be reviewed without secret reveal.",
                        "REJECT_DRAFT_READINESS_REDACTION_POLICY_UNREVIEWED", "fail-closed"),
                blocker("DRAFT_READINESS_LANE_PROVENANCE_POLICY_BLOCKER", "policy",
                        "Provenance policy must be reviewed without evidence import.",
                        "REJECT_DRAFT_READINESS_PROVENANCE_POLICY_UNREVIEWED", "fail-closed"),
                blocker("DRAFT_READINESS_LANE_RAW_SECRET_EMBARGO_BLOCKER", "embargo",
                        "Raw secret embargo must hold.",
                        "REJECT_DRAFT_READINESS_RAW_SECRET_PRESENT", "fail-closed"),
                blocker("DRAFT_READINESS_LANE_APPROVAL_GRANT_EMBARGO_BLOCKER", "embargo",
                        "Approval grant embargo must hold.",
                        "REJECT_DRAFT_READINESS_APPROVAL_GRANT_EMITTED", "fail-closed"),
                blocker("DRAFT_READINESS_LANE_ZERO_VALUE_IMPORT_EMBARGO_BLOCKER", "embargo",
                        "Zero value import embargo must hold.",
                        "REJECT_DRAFT_READINESS_VALUE_IMPORT_NONZERO", "fail-closed"),
                blocker("DRAFT_READINESS_LANE_WRITE_ROUTE_EMBARGO_BLOCKER", "embargo",
                        "Write route embargo must hold.",
                        "REJECT_DRAFT_READINESS_WRITE_ROUTE_ENABLED", "fail-closed"),
                blocker("DRAFT_READINESS_LANE_SIBLING_NON_MUTATION_BLOCKER", "embargo",
                        "Sibling non-mutation evidence must hold.",
                        "REJECT_DRAFT_READINESS_SIBLING_MUTATION_ENABLED", "fail-closed"),
                blocker("DRAFT_READINESS_LANE_CLOSEOUT_BLOCKER", "closeout",
                        "Next step must be a separate manual package plan.",
                        "REJECT_DRAFT_READINESS_NEXT_STEP_NOT_MANUAL_PACKAGE", "required")
        );
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
            .ControlBlocker> blockers(int fromInclusive, int toExclusive) {
        return List.copyOf(allBlockers().subList(fromInclusive, toExclusive));
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
            .ControlBlocker blocker(
                    String code,
                    String category,
                    String blocker,
                    String rejectionCode,
                    String enforcement
    ) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSupport.blocker(
                code,
                category,
                blocker,
                rejectionCode,
                enforcement
        );
    }
}
