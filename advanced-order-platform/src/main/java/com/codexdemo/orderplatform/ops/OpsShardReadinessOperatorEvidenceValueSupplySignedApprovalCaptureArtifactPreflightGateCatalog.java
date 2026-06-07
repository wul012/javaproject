package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightGateCatalog {

    static final int GATE_COUNT = 20;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightGateCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse.ArtifactGate>
    allGates() {
        return List.of(
                gate("ARTIFACT_PREFLIGHT_GATE_01_REQUEST_METADATA_ONLY", "request",
                        "Artifact preflight request id is metadata only.", "fail-closed"),
                gate("ARTIFACT_PREFLIGHT_GATE_02_CAPTURE_DIGEST_REQUIRED", "digest",
                        "Source capture preflight digest must be present.", "required"),
                gate("ARTIFACT_PREFLIGHT_GATE_03_TEMPLATE_DIGEST_REQUIRED", "digest",
                        "Signed approval template digest must be present.", "required"),
                gate("ARTIFACT_PREFLIGHT_GATE_04_REVIEW_DIGEST_REQUIRED", "digest",
                        "Approval packet review digest must be present.", "required"),
                gate("ARTIFACT_PREFLIGHT_GATE_05_OPERATOR_ALIAS_ONLY", "operator",
                        "Operator identity and role are alias-only.", "fail-closed"),
                gate("ARTIFACT_PREFLIGHT_GATE_06_WINDOW_PLACEHOLDER_ONLY", "capture-window",
                        "Capture window id cannot open runtime.", "fail-closed"),
                gate("ARTIFACT_PREFLIGHT_GATE_07_CHANNEL_POLICY_NO_WRITE_ROUTE", "capture-policy",
                        "Capture channel policy cannot expose writes.", "fail-closed"),
                gate("ARTIFACT_PREFLIGHT_GATE_08_SIGNATURE_ALGORITHM_NO_MATERIAL", "signature",
                        "Signature algorithm policy cannot carry signature material.", "fail-closed"),
                gate("ARTIFACT_PREFLIGHT_GATE_09_DETACHED_SIGNATURE_PLACEHOLDER_ONLY", "signature",
                        "Detached signature placeholder is not signature material.", "fail-closed"),
                gate("ARTIFACT_PREFLIGHT_GATE_10_SIGNATURE_REDACTION_REQUIRED", "signature",
                        "Signature redaction policy is required before artifact draft.", "required-before-draft"),
                gate("ARTIFACT_PREFLIGHT_GATE_11_STATEMENT_DIGEST_PLACEHOLDER_ONLY", "statement",
                        "Approval statement digest placeholder is not signed statement.", "placeholder-only"),
                gate("ARTIFACT_PREFLIGHT_GATE_12_EVIDENCE_MIRROR_NO_IMPORT", "evidence",
                        "Source evidence fragments are mirrored without import.", "metadata-only"),
                gate("ARTIFACT_PREFLIGHT_GATE_13_REDACTED_DIGEST_REFERENCE_ONLY", "value",
                        "Redacted value digest reference cannot include raw value hash.", "fail-closed"),
                gate("ARTIFACT_PREFLIGHT_GATE_14_VALUE_SHAPE_NO_BODY", "value",
                        "Value shape cannot carry operator value body.", "fail-closed"),
                gate("ARTIFACT_PREFLIGHT_GATE_15_REDACTION_PROVENANCE_MIRRORED", "policy",
                        "Redaction and provenance policies are mirrored without import.", "required"),
                gate("ARTIFACT_PREFLIGHT_GATE_16_NO_RAW_SECRET", "lock",
                        "Raw secret and signature material remain absent.", "fail-closed"),
                gate("ARTIFACT_PREFLIGHT_GATE_17_NO_APPROVAL_GRANT", "lock",
                        "Approval grant remains not emitted.", "fail-closed"),
                gate("ARTIFACT_PREFLIGHT_GATE_18_ZERO_VALUE_IMPORT", "lock",
                        "Submitted, accepted, and imported value counts remain zero.", "fail-closed"),
                gate("ARTIFACT_PREFLIGHT_GATE_19_NO_WRITE_ROUTE", "lock",
                        "No write route is exposed.", "fail-closed"),
                gate("ARTIFACT_PREFLIGHT_GATE_20_NO_SIBLING_MUTATION", "lock",
                        "Sibling services remain unstarted and unmutated.", "fail-closed")
        );
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse.ArtifactGate>
    gates(int fromInclusive, int toExclusive) {
        return List.copyOf(allGates().subList(fromInclusive, toExclusive));
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse.ArtifactGate
    gate(String code, String category, String gate, String enforcement) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSupport.gate(
                code,
                category,
                gate,
                enforcement
        );
    }
}
