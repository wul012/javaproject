package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneGateCatalog {

    static final int GATE_COUNT = 20;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneGateCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
            .ReadinessLaneGate> allGates() {
        return List.of(
                gate("DRAFT_READINESS_LANE_GATE_01_REQUEST_MANIFEST_PRESENT", "request",
                        "Request manifest lane must be present before review closeout.", "fail-closed"),
                gate("DRAFT_READINESS_LANE_GATE_02_DIGEST_PINS_BOUND", "digest",
                        "Artifact, template, and review digest pins must be bound.", "required"),
                gate("DRAFT_READINESS_LANE_GATE_03_OPERATOR_REVIEW_REQUIRED", "operator",
                        "Operator identity and role lanes require review.", "fail-closed"),
                gate("DRAFT_READINESS_LANE_GATE_04_CAPTURE_POLICY_REVIEW_REQUIRED", "capture-policy",
                        "Capture window and channel policy lanes require review.", "fail-closed"),
                gate("DRAFT_READINESS_LANE_GATE_05_SIGNATURE_MATERIAL_ABSENT", "signature",
                        "Signature review lanes cannot contain signature material.", "fail-closed"),
                gate("DRAFT_READINESS_LANE_GATE_06_STATEMENT_DIGEST_PINNED", "statement",
                        "Approval statement digest lane must stay pinned.", "required"),
                gate("DRAFT_READINESS_LANE_GATE_07_EVIDENCE_REVIEW_NO_IMPORT", "evidence",
                        "Evidence lanes cannot import files or snippets.", "metadata-only"),
                gate("DRAFT_READINESS_LANE_GATE_08_VALUE_BODY_ABSENT", "value",
                        "Value lanes cannot carry operator value body.", "fail-closed"),
                gate("DRAFT_READINESS_LANE_GATE_09_REDACTION_PROVENANCE_REVIEW", "policy",
                        "Redaction and provenance policy lanes require review.", "fail-closed"),
                gate("DRAFT_READINESS_LANE_GATE_10_RAW_SECRET_EMBARGO", "embargo",
                        "Raw secret embargo remains closed.", "fail-closed"),
                gate("DRAFT_READINESS_LANE_GATE_11_APPROVAL_GRANT_EMBARGO", "embargo",
                        "Approval grant remains not emitted.", "fail-closed"),
                gate("DRAFT_READINESS_LANE_GATE_12_ZERO_VALUE_IMPORT_EMBARGO", "embargo",
                        "Value import counts remain zero.", "fail-closed"),
                gate("DRAFT_READINESS_LANE_GATE_13_WRITE_ROUTE_EMBARGO", "embargo",
                        "Write route remains unavailable.", "fail-closed"),
                gate("DRAFT_READINESS_LANE_GATE_14_SIBLING_NON_MUTATION", "embargo",
                        "Sibling services remain untouched.", "fail-closed"),
                gate("DRAFT_READINESS_LANE_GATE_15_NO_REAL_MANUAL_DRAFT", "manual-package",
                        "Readiness lane closeout cannot create real manual draft.", "fail-closed"),
                gate("DRAFT_READINESS_LANE_GATE_16_NO_DRAFT_MATERIALIZATION", "manual-package",
                        "Draft materialization remains absent.", "fail-closed"),
                gate("DRAFT_READINESS_LANE_GATE_17_NO_SIGNATURE_CAPTURE", "capture",
                        "Signature capture remains absent.", "fail-closed"),
                gate("DRAFT_READINESS_LANE_GATE_18_NO_RUNTIME_PAYLOAD", "runtime",
                        "Runtime payload remains locked.", "fail-closed"),
                gate("DRAFT_READINESS_LANE_GATE_19_NO_PRODUCTION_EXECUTION", "runtime",
                        "Production execution remains locked.", "fail-closed"),
                gate("DRAFT_READINESS_LANE_GATE_20_NEXT_STEP_MANUAL_PACKAGE_PLAN", "closeout",
                        "Manual draft artifact package requires a separate explicit plan.", "required")
        );
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
            .ReadinessLaneGate> gates(int fromInclusive, int toExclusive) {
        return List.copyOf(allGates().subList(fromInclusive, toExclusive));
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
            .ReadinessLaneGate gate(String code, String category, String gate, String enforcement) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSupport.gate(
                code,
                category,
                gate,
                enforcement
        );
    }
}
