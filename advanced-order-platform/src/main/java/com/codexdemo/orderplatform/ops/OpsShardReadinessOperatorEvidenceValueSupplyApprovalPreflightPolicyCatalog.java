package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalog {

    static final int POLICY_COUNT = 20;

    private OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalPolicy> allPolicies() {
        return List.of(
                policy("APPROVAL_PREFLIGHT_POLICY_01_IDENTITY_ALIAS_ONLY", "identity",
                        "Operator identity is represented by alias only; credential values are not accepted.",
                        "fail-closed"),
                policy("APPROVAL_PREFLIGHT_POLICY_02_REVIEWER_ROLE_REQUIRED", "identity",
                        "Reviewer role metadata must exist before approval packet design can proceed.",
                        "required-before-capture"),
                policy("APPROVAL_PREFLIGHT_POLICY_03_SIGNED_HUMAN_APPROVAL_REQUIRED", "approval",
                        "Signed human approval is required later, but this preflight cannot capture it.",
                        "fail-closed"),
                policy("APPROVAL_PREFLIGHT_POLICY_04_NO_APPROVAL_CAPTURE", "approval",
                        "Approval capture, approval grant, and approval persistence remain locked.",
                        "fail-closed"),
                policy("APPROVAL_PREFLIGHT_POLICY_05_NO_OPERATOR_VALUE_BODY", "approval",
                        "Approval packet preflight cannot contain operator value body fields.",
                        "fail-closed"),
                policy("APPROVAL_PREFLIGHT_POLICY_06_ISSUED_AT_REQUIRED", "timestamp",
                        "Issued-at timestamp is required in the future packet shape.",
                        "required-before-capture"),
                policy("APPROVAL_PREFLIGHT_POLICY_07_EXPIRY_WINDOW_REQUIRED", "timestamp",
                        "Expiry window is required before a signed approval can be considered.",
                        "required-before-capture"),
                policy("APPROVAL_PREFLIGHT_POLICY_08_REPLAY_NONCE_REQUIRED", "timestamp",
                        "Replay nonce metadata is required without storing secret material.",
                        "required-before-capture"),
                policy("APPROVAL_PREFLIGHT_POLICY_09_REDACTION_DIGEST_REQUIRED", "redaction",
                        "Redaction digest must exist before future approval capture.",
                        "required-before-capture"),
                policy("APPROVAL_PREFLIGHT_POLICY_10_NO_CREDENTIAL_OR_RAW_ENDPOINT", "redaction",
                        "Credential values and raw endpoints remain blocked from approval preflight.",
                        "fail-closed"),
                policy("APPROVAL_PREFLIGHT_POLICY_11_PROVENANCE_SOURCE_REQUIRED", "provenance",
                        "Provenance source id is required before future import review.",
                        "required-before-import"),
                policy("APPROVAL_PREFLIGHT_POLICY_12_PROVENANCE_FILE_AND_SNIPPET_REQUIRED", "provenance",
                        "Evidence file and snippet ids are required before future import review.",
                        "required-before-import"),
                policy("APPROVAL_PREFLIGHT_POLICY_13_TYPED_VALUE_ENVELOPE_REFERENCE_ONLY", "value-envelope",
                        "Typed value envelope references cannot include supplied value bodies.",
                        "metadata-only"),
                policy("APPROVAL_PREFLIGHT_POLICY_14_MALFORMED_VALUES_REJECTED", "rejection",
                        "Malformed values are rejected before import preflight can be designed.",
                        "fail-closed"),
                policy("APPROVAL_PREFLIGHT_POLICY_15_MISSING_VALUES_REJECTED", "rejection",
                        "Missing values are rejected and cannot be synthesized.",
                        "fail-closed"),
                policy("APPROVAL_PREFLIGHT_POLICY_16_ZERO_VALUE_COUNTS_REQUIRED", "zero-count",
                        "Supplied, accepted, and imported value counts must remain zero.",
                        "fail-closed"),
                policy("APPROVAL_PREFLIGHT_POLICY_17_CLEANUP_RECEIPT_REQUIRED", "receipt",
                        "Cleanup receipt metadata is required before future import work.",
                        "required-before-import"),
                policy("APPROVAL_PREFLIGHT_POLICY_18_IMPORT_FIREWALL_LOCKED", "import",
                        "Import preview and evidence import remain locked.",
                        "fail-closed"),
                policy("APPROVAL_PREFLIGHT_POLICY_19_RUNTIME_EXECUTION_LOCKED", "runtime",
                        "Runtime payload, live execution, and production execution remain locked.",
                        "fail-closed"),
                policy("APPROVAL_PREFLIGHT_POLICY_20_CLOSEOUT_LOCK_SUMMARY_REQUIRED", "closeout",
                        "Closeout must restate approval, value, import, runtime, and production locks.",
                        "required-before-handoff")
        );
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalPolicy> policies(
            int fromInclusive,
            int toExclusive
    ) {
        return List.copyOf(allPolicies().subList(fromInclusive, toExclusive));
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalPolicy policy(
            String code,
            String category,
            String policy,
            String enforcement
    ) {
        return OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupport.policy(
                code,
                category,
                policy,
                enforcement
        );
    }
}
