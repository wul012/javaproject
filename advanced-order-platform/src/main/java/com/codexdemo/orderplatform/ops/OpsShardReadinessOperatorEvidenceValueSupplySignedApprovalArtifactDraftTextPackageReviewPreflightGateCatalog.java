package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightGateCatalog {

    static final int GATE_COUNT = 20;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightGateCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
            .ReviewGate> allGates() {
        return List.of(
                gate("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_01", "review",
                        "Review preflight remains criteria-only."),
                gate("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_02", "draft-text",
                        "Signed draft text is not parsed."),
                gate("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_03", "signature",
                        "Detached signature payload is not parsed."),
                gate("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_04", "approval",
                        "Approval grants remain disabled."),
                gate("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_05", "value",
                        "Operator value import remains locked."),
                gate("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_06", "runtime",
                        "Runtime payload creation remains locked."),
                gate("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_07", "runtime",
                        "Java startup remains out of scope."),
                gate("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_08", "runtime",
                        "mini-kv startup remains out of scope."),
                gate("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_09", "sibling",
                        "Sibling mutation remains blocked."),
                gate("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_10", "catalog",
                        "Twenty-five review criteria must be present."),
                gate("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_11", "catalog",
                        "Twenty-five rejection controls must be present."),
                gate("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_12", "source",
                        "Node v1261 review preflight remains pinned."),
                gate("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_13", "source",
                        "Node v1236 intake remains pinned."),
                gate("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_14", "source",
                        "Java v934 intake remains pinned."),
                gate("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_15", "secret",
                        "Raw secret values remain absent."),
                gate("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_16", "digest",
                        "Digest recheck controls are required before acceptance."),
                gate("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_17", "reviewer",
                        "Separate-reviewer requirement is documented."),
                gate("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_18", "acceptance",
                        "Package acceptance remains a future step."),
                gate("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_19", "archive",
                        "Archive closeout review criterion must be present."),
                gate("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_20", "closeout",
                        "Closeout stops before signed approval consideration.")
        );
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
            .ReviewGate> gates(int fromInclusive, int toExclusive) {
        return List.copyOf(allGates().subList(fromInclusive, toExclusive));
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
            .ReviewGate gate(String code, String category, String gate) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightSupport
                .gate(code, category, gate, "fail-closed");
    }
}
