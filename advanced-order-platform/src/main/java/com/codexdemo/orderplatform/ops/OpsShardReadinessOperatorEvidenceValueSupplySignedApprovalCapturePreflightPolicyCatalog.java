package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightPolicyCatalog {

    static final int POLICY_COUNT = 20;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightPolicyCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse.CapturePolicy>
    allPolicies() {
        return List.of(
                policy("SIGNED_CAPTURE_PREFLIGHT_POLICY_01_REQUEST_ID_METADATA_ONLY", "identity",
                        "Capture preflight request id is metadata and cannot create approval capture.", "fail-closed"),
                policy("SIGNED_CAPTURE_PREFLIGHT_POLICY_02_TEMPLATE_DIGEST_REQUIRED", "template-binding",
                        "Source signed approval template digest must be present before capture artifact planning.",
                        "required"),
                policy("SIGNED_CAPTURE_PREFLIGHT_POLICY_03_REVIEW_DIGEST_REQUIRED", "review-binding",
                        "Source approval packet review digest must be present without granting approval.", "required"),
                policy("SIGNED_CAPTURE_PREFLIGHT_POLICY_04_OPERATOR_ALIAS_ONLY", "operator",
                        "Operator identity and role are mirrored as aliases only.", "fail-closed"),
                policy("SIGNED_CAPTURE_PREFLIGHT_POLICY_05_NO_OPERATOR_AUTHORIZATION", "operator",
                        "Operator mirror fields cannot authorize capture, grant, or value submission.", "fail-closed"),
                policy("SIGNED_CAPTURE_PREFLIGHT_POLICY_06_TIMESTAMP_PLACEHOLDER_ONLY", "time",
                        "Timestamp source and manual window ids are placeholders only.", "placeholder-only"),
                policy("SIGNED_CAPTURE_PREFLIGHT_POLICY_07_CHANNEL_POLICY_NO_RAW_ENDPOINT", "channel",
                        "Capture channel policy cannot expose raw endpoint values.", "fail-closed"),
                policy("SIGNED_CAPTURE_PREFLIGHT_POLICY_08_SIGNATURE_ALGORITHM_NO_MATERIAL", "signature",
                        "Signature algorithm policy excludes raw signature material.", "fail-closed"),
                policy("SIGNED_CAPTURE_PREFLIGHT_POLICY_09_SIGNATURE_REDACTION_REQUIRED", "signature",
                        "Signature material redaction policy must be declared before any artifact planning.",
                        "required-before-artifact"),
                policy("SIGNED_CAPTURE_PREFLIGHT_POLICY_10_STATEMENT_PLACEHOLDER_ONLY", "statement",
                        "Approval statement placeholder is not signed approval text.", "placeholder-only"),
                policy("SIGNED_CAPTURE_PREFLIGHT_POLICY_11_JUSTIFICATION_NO_VALUE_BODY", "statement",
                        "Operator justification mirror cannot carry value bodies.", "fail-closed"),
                policy("SIGNED_CAPTURE_PREFLIGHT_POLICY_12_EVIDENCE_MIRROR_NO_IMPORT", "evidence",
                        "Evidence version, file id, and snippet id are mirrored without import.", "metadata-only"),
                policy("SIGNED_CAPTURE_PREFLIGHT_POLICY_13_REDACTED_DIGEST_REFERENCE_ONLY", "redaction",
                        "Redacted value digest reference cannot include raw value hash material.", "fail-closed"),
                policy("SIGNED_CAPTURE_PREFLIGHT_POLICY_14_VALUE_SHAPE_NO_BODY", "value-shape",
                        "Value shape binding cannot normalize or accept operator values.", "fail-closed"),
                policy("SIGNED_CAPTURE_PREFLIGHT_POLICY_15_REDACTION_AND_PROVENANCE_MIRRORS", "policy-mirror",
                        "Redaction and provenance policies are mirrored before import preflight.", "required"),
                policy("SIGNED_CAPTURE_PREFLIGHT_POLICY_16_RAW_SECRET_SIGNATURE_LOCK", "lock",
                        "Raw secret and raw signature material are locked.", "fail-closed"),
                policy("SIGNED_CAPTURE_PREFLIGHT_POLICY_17_NO_APPROVAL_GRANT", "lock",
                        "Approval grant emission remains locked.", "fail-closed"),
                policy("SIGNED_CAPTURE_PREFLIGHT_POLICY_18_ZERO_VALUE_COUNTS", "lock",
                        "Submitted, accepted, and imported value counts remain zero.", "fail-closed"),
                policy("SIGNED_CAPTURE_PREFLIGHT_POLICY_19_NO_WRITE_ROUTE", "lock",
                        "No write route is exposed by capture preflight.", "fail-closed"),
                policy("SIGNED_CAPTURE_PREFLIGHT_POLICY_20_NO_SIBLING_MUTATION", "lock",
                        "Sibling services remain unstarted and unmutated.", "fail-closed")
        );
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse.CapturePolicy>
    policies(int fromInclusive, int toExclusive) {
        return List.copyOf(allPolicies().subList(fromInclusive, toExclusive));
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse.CapturePolicy
    policy(String code, String category, String policy, String enforcement) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSupport.policy(
                code,
                category,
                policy,
                enforcement
        );
    }
}
