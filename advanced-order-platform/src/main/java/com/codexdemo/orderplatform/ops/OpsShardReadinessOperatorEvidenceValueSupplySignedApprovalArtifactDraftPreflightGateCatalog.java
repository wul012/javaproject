package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGateCatalog {

    static final int GATE_COUNT = 20;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGateCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
            .DraftPreflightGate> allGates() {
        return List.of(
                gate("DRAFT_PREFLIGHT_GATE_01_REQUEST_METADATA_ONLY", "request",
                        "Draft request id is metadata only.", "fail-closed"),
                gate("DRAFT_PREFLIGHT_GATE_02_DIGEST_CHAIN_REQUIRED", "digest",
                        "Artifact preflight, template, and review digests are required.", "required"),
                gate("DRAFT_PREFLIGHT_GATE_03_OPERATOR_ALIAS_ONLY", "operator",
                        "Operator identity and role remain alias-only.", "fail-closed"),
                gate("DRAFT_PREFLIGHT_GATE_04_CAPTURE_POLICY_NO_WRITE", "capture-policy",
                        "Capture policy cannot expose writes or runtime.", "fail-closed"),
                gate("DRAFT_PREFLIGHT_GATE_05_SIGNATURE_NO_MATERIAL", "signature",
                        "Signature fields cannot contain signature material.", "fail-closed"),
                gate("DRAFT_PREFLIGHT_GATE_06_STATEMENT_PLACEHOLDER_ONLY", "statement",
                        "Approval statement digest is placeholder only.", "placeholder-only"),
                gate("DRAFT_PREFLIGHT_GATE_07_EVIDENCE_NO_IMPORT", "evidence",
                        "Evidence fields cannot import files or snippets.", "metadata-only"),
                gate("DRAFT_PREFLIGHT_GATE_08_VALUE_NO_BODY", "value",
                        "Value fields cannot contain operator value body.", "fail-closed"),
                gate("DRAFT_PREFLIGHT_GATE_09_REDACTION_PROVENANCE_NO_IMPORT", "policy",
                        "Redaction and provenance mirrors cannot import evidence.", "fail-closed"),
                gate("DRAFT_PREFLIGHT_GATE_10_NO_RAW_SECRET", "lock",
                        "Raw secret and signature material remain absent.", "fail-closed"),
                gate("DRAFT_PREFLIGHT_GATE_11_NO_APPROVAL_GRANT", "lock",
                        "Approval grant remains not emitted.", "fail-closed"),
                gate("DRAFT_PREFLIGHT_GATE_12_ZERO_VALUE_IMPORT", "lock",
                        "Submitted, accepted, and imported value counts remain zero.", "fail-closed"),
                gate("DRAFT_PREFLIGHT_GATE_13_NO_WRITE_ROUTE", "lock",
                        "No write route is exposed.", "fail-closed"),
                gate("DRAFT_PREFLIGHT_GATE_14_NO_SIBLING_MUTATION", "lock",
                        "Sibling services remain untouched.", "fail-closed"),
                gate("DRAFT_PREFLIGHT_GATE_15_NO_REAL_MANUAL_DRAFT", "draft",
                        "Real manual draft remains unavailable.", "fail-closed"),
                gate("DRAFT_PREFLIGHT_GATE_16_NO_DRAFT_MATERIALIZATION", "draft",
                        "Draft materialization remains absent.", "fail-closed"),
                gate("DRAFT_PREFLIGHT_GATE_17_NO_SIGNATURE_CAPTURE", "capture",
                        "Signature capture remains absent.", "fail-closed"),
                gate("DRAFT_PREFLIGHT_GATE_18_NO_RUNTIME_PAYLOAD", "runtime",
                        "Runtime payload remains locked.", "fail-closed"),
                gate("DRAFT_PREFLIGHT_GATE_19_NO_PRODUCTION_EXECUTION", "runtime",
                        "Production execution remains locked.", "fail-closed"),
                gate("DRAFT_PREFLIGHT_GATE_20_NEXT_STEP_EXPLICIT_PLAN", "closeout",
                        "Real manual draft requires a separate explicit plan.", "required")
        );
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
            .DraftPreflightGate> gates(int fromInclusive, int toExclusive) {
        return List.copyOf(allGates().subList(fromInclusive, toExclusive));
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
            .DraftPreflightGate gate(String code, String category, String gate, String enforcement) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSupport.gate(
                code,
                category,
                gate,
                enforcement
        );
    }
}
