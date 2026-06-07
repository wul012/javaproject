package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAttestationCatalog {

    static final int ATTESTATION_COUNT = 25;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAttestationCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse.CaptureAttestation>
    allAttestations() {
        return List.of(
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_01_REQUEST_ID", "identity",
                        "Capture preflight request id is metadata only.", "fail-closed"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_02_TEMPLATE_DIGEST", "template-binding",
                        "Template digest is bound before any capture artifact exists.", "required"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_03_REVIEW_DIGEST", "review-binding",
                        "Approval packet review digest is bound without approval grant.", "required"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_04_OPERATOR_IDENTITY", "operator",
                        "Operator identity remains alias-only.", "fail-closed"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_05_OPERATOR_ROLE", "operator",
                        "Operator role is mirrored and cannot authorize capture.", "fail-closed"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_06_TIMESTAMP_SOURCE", "time",
                        "Timestamp source is a placeholder and does not timestamp a signature.", "placeholder-only"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_07_MANUAL_WINDOW", "time",
                        "Manual capture window id is a placeholder and does not open runtime.", "placeholder-only"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_08_CHANNEL_POLICY", "channel",
                        "Capture channel policy is declared without raw endpoint routing.", "fail-closed"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_09_SIGNATURE_ALGORITHM", "signature",
                        "Signature algorithm policy excludes signature material.", "fail-closed"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_10_SIGNATURE_REDACTION", "signature",
                        "Signature material redaction policy keeps raw material absent.", "fail-closed"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_11_APPROVAL_STATEMENT", "statement",
                        "Approval statement placeholder is not signed approval text.", "placeholder-only"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_12_OPERATOR_JUSTIFICATION", "statement",
                        "Operator justification mirror cannot carry values.", "fail-closed"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_13_EVIDENCE_VERSION", "evidence",
                        "Source evidence version is mirrored without import.", "metadata-only"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_14_EVIDENCE_FILE", "evidence",
                        "Source evidence file id is mirrored without reading file content.", "metadata-only"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_15_EVIDENCE_SNIPPET", "evidence",
                        "Source evidence snippet id is mirrored without importing payload.", "metadata-only"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_16_REDACTED_DIGEST", "redaction",
                        "Redacted value digest reference is not raw value material.", "fail-closed"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_17_VALUE_SHAPE", "value-shape",
                        "Value shape binding excludes value body acceptance.", "fail-closed"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_18_REDACTION_POLICY", "redaction",
                        "Redaction policy mirror cannot reveal secret material.", "fail-closed"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_19_PROVENANCE_POLICY", "provenance",
                        "Provenance policy mirror cannot import evidence.", "fail-closed"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_20_RAW_SECRET_SIGNATURE_LOCK", "lock",
                        "Raw secret and signature material remain locked.", "fail-closed"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_21_APPROVAL_GRANT_LOCK", "lock",
                        "Approval grant emission remains locked.", "fail-closed"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_22_ZERO_VALUE_COUNTS", "lock",
                        "Submitted, accepted, and imported value counts remain zero.", "fail-closed"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_23_NO_WRITE_ROUTE", "lock",
                        "Write route exposure remains locked.", "fail-closed"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_24_SIBLING_NON_MUTATION", "lock",
                        "Sibling services are neither started nor mutated.", "fail-closed"),
                attestation("SIGNED_CAPTURE_PREFLIGHT_ATTEST_25_CLOSEOUT_BOUNDARY", "closeout",
                        "Capture preflight stops before a real approval artifact plan.", "required-before-next-step")
        );
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse.CaptureAttestation>
    attestations(int fromInclusive, int toExclusive) {
        return List.copyOf(allAttestations().subList(fromInclusive, toExclusive));
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse
            .CaptureAttestation attestation(String code, String category, String attestation, String enforcement) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSupport.attestation(
                code,
                category,
                attestation,
                enforcement
        );
    }
}
