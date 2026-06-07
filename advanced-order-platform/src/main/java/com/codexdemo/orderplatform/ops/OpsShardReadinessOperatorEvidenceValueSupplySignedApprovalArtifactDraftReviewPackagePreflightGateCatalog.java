package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGateCatalog {

    static final int GATE_COUNT = 20;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGateCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
            .ReviewPackageGate> allGates() {
        return List.of(
                gate("REVIEW_PACKAGE_PREFLIGHT_GATE_01_SLOT_MAP_ONLY", "package",
                        "Review package preflight remains a slot map only.", "fail-closed"),
                gate("REVIEW_PACKAGE_PREFLIGHT_GATE_02_DIGEST_PINS_BOUND", "digest",
                        "Artifact, template, and review digest pins remain bound.", "required"),
                gate("REVIEW_PACKAGE_PREFLIGHT_GATE_03_OPERATOR_REVIEW_VISIBLE", "operator",
                        "Operator identity and role stay review-only.", "fail-closed"),
                gate("REVIEW_PACKAGE_PREFLIGHT_GATE_04_CAPTURE_POLICY_NO_WINDOW_OPEN", "capture-policy",
                        "Capture window and channel policy cannot open adapters or runtime.", "fail-closed"),
                gate("REVIEW_PACKAGE_PREFLIGHT_GATE_05_SIGNATURE_MATERIAL_ABSENT", "signature",
                        "Signature material stays absent.", "fail-closed"),
                gate("REVIEW_PACKAGE_PREFLIGHT_GATE_06_STATEMENT_TEXT_ABSENT", "statement",
                        "Signed statement text stays absent.", "fail-closed"),
                gate("REVIEW_PACKAGE_PREFLIGHT_GATE_07_EVIDENCE_NO_IMPORT", "evidence",
                        "Evidence slots cannot import files or snippets.", "metadata-only"),
                gate("REVIEW_PACKAGE_PREFLIGHT_GATE_08_VALUE_BODY_ABSENT", "value",
                        "Value slots cannot carry operator value body.", "fail-closed"),
                gate("REVIEW_PACKAGE_PREFLIGHT_GATE_09_REDACTION_PROVENANCE_NO_IMPORT", "policy",
                        "Redaction and provenance slots cannot import evidence.", "fail-closed"),
                gate("REVIEW_PACKAGE_PREFLIGHT_GATE_10_RAW_SECRET_ABSENT", "embargo",
                        "Raw secret material remains absent.", "fail-closed"),
                gate("REVIEW_PACKAGE_PREFLIGHT_GATE_11_NO_APPROVAL_GRANT", "embargo",
                        "Approval grant remains not emitted.", "fail-closed"),
                gate("REVIEW_PACKAGE_PREFLIGHT_GATE_12_ZERO_VALUE_IMPORT", "embargo",
                        "Operator value submissions and imports remain zero.", "fail-closed"),
                gate("REVIEW_PACKAGE_PREFLIGHT_GATE_13_WRITE_ROUTE_DISABLED", "embargo",
                        "Write route remains disabled.", "fail-closed"),
                gate("REVIEW_PACKAGE_PREFLIGHT_GATE_14_SIBLING_NON_MUTATION", "embargo",
                        "Sibling state remains untouched.", "fail-closed"),
                gate("REVIEW_PACKAGE_PREFLIGHT_GATE_15_NO_SIGNED_DRAFT", "draft",
                        "Signed approval draft text remains unavailable.", "fail-closed"),
                gate("REVIEW_PACKAGE_PREFLIGHT_GATE_16_NO_PACKAGE_MATERIALIZATION", "package",
                        "Review package artifact materialization remains absent.", "fail-closed"),
                gate("REVIEW_PACKAGE_PREFLIGHT_GATE_17_NO_SIGNATURE_CAPTURE", "capture",
                        "Signature capture remains absent.", "fail-closed"),
                gate("REVIEW_PACKAGE_PREFLIGHT_GATE_18_NO_RUNTIME_PAYLOAD", "runtime",
                        "Runtime payload remains locked.", "fail-closed"),
                gate("REVIEW_PACKAGE_PREFLIGHT_GATE_19_NO_SERVICE_STARTUP", "runtime",
                        "Java and mini-kv service startup remains out of scope.", "fail-closed"),
                gate("REVIEW_PACKAGE_PREFLIGHT_GATE_20_NEXT_STEP_HUMAN_DRAFT_PLAN", "closeout",
                        "Human draft artifact authoring requires a separate explicit plan.", "required")
        );
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
            .ReviewPackageGate> gates(int fromInclusive, int toExclusive) {
        return List.copyOf(allGates().subList(fromInclusive, toExclusive));
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
            .ReviewPackageGate gate(String code, String category, String gate, String enforcement) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSupport
                .gate(code, category, gate, enforcement);
    }
}
