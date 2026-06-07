package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuleCatalog {

    static final int RULE_COUNT = 18;

    private OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuleCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterRule> allRules() {
        return List.of(
                rule(
                        "ADAPTER_RULE_01_DISABLED_IMPLEMENTATION",
                        "implementation",
                        "The value-supply adapter preflight cannot expose an implementation target.",
                        "fail-closed"
                ),
                rule(
                        "ADAPTER_RULE_02_METADATA_ONLY_COMPATIBILITY",
                        "compatibility",
                        "Compatibility checks can compare envelope metadata but cannot read value bodies.",
                        "metadata-only"
                ),
                rule(
                        "ADAPTER_RULE_03_NO_OPERATOR_VALUE_BODY",
                        "submission",
                        "Operator value body fields remain absent from adapter preflight responses.",
                        "fail-closed"
                ),
                rule(
                        "ADAPTER_RULE_04_NO_APPROVAL_CAPTURE",
                        "approval",
                        "The preflight can cite Node v986 approval packet draft readiness but cannot capture approval.",
                        "fail-closed"
                ),
                rule(
                        "ADAPTER_RULE_05_NO_CREDENTIAL_VALUE",
                        "redaction",
                        "Credential values are rejected before any adapter boundary is designed.",
                        "fail-closed"
                ),
                rule(
                        "ADAPTER_RULE_06_NO_RAW_ENDPOINT",
                        "redaction",
                        "Raw endpoint values stay out of adapter preflight and must remain aliases only.",
                        "fail-closed"
                ),
                rule(
                        "ADAPTER_RULE_07_NO_SECRET_MATERIAL",
                        "redaction",
                        "Secret material cannot be supplied, echoed, hashed, or persisted by preflight.",
                        "fail-closed"
                ),
                rule(
                        "ADAPTER_RULE_08_PROVENANCE_SOURCE_REQUIRED",
                        "provenance",
                        "Every future adapter input must bind to a source id before value import can be considered.",
                        "required-before-adapter"
                ),
                rule(
                        "ADAPTER_RULE_09_PROVENANCE_EVIDENCE_FILE_REQUIRED",
                        "provenance",
                        "Evidence file ids are required before adapter input review.",
                        "required-before-adapter"
                ),
                rule(
                        "ADAPTER_RULE_10_PROVENANCE_SNIPPET_REQUIRED",
                        "provenance",
                        "Evidence snippet ids are required before adapter input review.",
                        "required-before-adapter"
                ),
                rule(
                        "ADAPTER_RULE_11_MISSING_VALUES_REJECTED",
                        "missing-policy",
                        "Missing values cannot be defaulted into adapter-ready evidence.",
                        "fail-closed"
                ),
                rule(
                        "ADAPTER_RULE_12_BLANK_VALUES_REJECTED",
                        "missing-policy",
                        "Blank values are malformed for adapter preflight and cannot become evidence.",
                        "fail-closed"
                ),
                rule(
                        "ADAPTER_RULE_13_NO_AUTOMATIC_SIBLING_IMPORT",
                        "source-evidence",
                        "Fresh sibling references remain read-only metadata and cannot be auto-imported.",
                        "fail-closed"
                ),
                rule(
                        "ADAPTER_RULE_14_SYNTHETIC_EVIDENCE_BLOCKED",
                        "source-evidence",
                        "Synthetic evidence cannot satisfy adapter preflight provenance.",
                        "fail-closed"
                ),
                rule(
                        "ADAPTER_RULE_15_RUNTIME_PAYLOAD_BLOCKED",
                        "payload",
                        "Runtime payload fields remain blocked until a separate live-read gate is proven.",
                        "fail-closed"
                ),
                rule(
                        "ADAPTER_RULE_16_NO_STATE_WRITE",
                        "side-effect",
                        "Adapter preflight cannot write state, mutate schema, or start services.",
                        "fail-closed"
                ),
                rule(
                        "ADAPTER_RULE_17_NO_IMPORT_OR_LIVE_EXECUTION",
                        "runtime",
                        "Evidence import, live execution, and production execution remain locked.",
                        "fail-closed"
                ),
                rule(
                        "ADAPTER_RULE_18_CLOSEOUT_LOCK_SUMMARY_REQUIRED",
                        "closeout",
                        "Closeout must restate all adapter, submission, import, runtime, and production locks.",
                        "required-before-handoff"
                )
        );
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterRule> rules(
            int fromInclusive,
            int toExclusive
    ) {
        return List.copyOf(allRules().subList(fromInclusive, toExclusive));
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterRule rule(
            String code,
            String category,
            String rule,
            String enforcement
    ) {
        return OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport.rule(
                code,
                category,
                rule,
                enforcement
        );
    }
}
