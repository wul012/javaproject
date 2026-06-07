package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v986";
    static final String SOURCE_SUPPLY_VERSION = "Java v658";
    static final String ADAPTER_STATE = "disabled-preflight";
    static final String ACCEPTED_VALUE_STATE = "not-accepted";
    static final String COMPATIBILITY_STATE = "metadata-only";
    static final String REDACTION_STATE = "required-before-adapter";
    static final String PROVENANCE_STATE = "required-before-adapter";
    static final String SUBMISSION_STATE = "locked";

    private OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport() {
    }

    static OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse response(
            String version,
            String endpoint,
            String profile,
            List<OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterSlot> slots,
            List<OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterRule> rules,
            List<String> additionalChecks
    ) {
        List<OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterSlot> slotCopy =
                List.copyOf(slots);
        List<OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterRule> ruleCopy =
                List.copyOf(rules);
        int passedSlotCount = (int) slotCopy.stream()
                .filter(slot -> "passed".equals(slot.status()))
                .count();
        List<String> checks = new ArrayList<>();
        checks.add("value-supply-adapter-preflight-slot-count-" + slotCopy.size());
        checks.add("value-supply-adapter-preflight-passed-slot-count-" + passedSlotCount);
        checks.add("value-supply-adapter-preflight-rule-count-" + ruleCopy.size());
        checks.add("value-supply-adapter-preflight-source-plan-" + SOURCE_PLAN);
        checks.add("value-supply-adapter-preflight-source-supply-" + SOURCE_SUPPLY_VERSION);
        checks.add("value-supply-adapter-preflight-disabled");
        checks.add("value-supply-adapter-preflight-values-not-accepted");
        checks.add("value-supply-adapter-preflight-implementation-locked");
        checks.add("value-supply-adapter-preflight-submission-locked");
        checks.add("value-supply-adapter-preflight-runtime-locked");
        checks.add("value-supply-adapter-preflight-production-locked");
        checks.addAll(additionalChecks);

        return new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse(
                PROJECT,
                version,
                true,
                false,
                true,
                SOURCE_PLAN,
                SOURCE_SUPPLY_VERSION,
                ADAPTER_STATE,
                ACCEPTED_VALUE_STATE,
                COMPATIBILITY_STATE,
                REDACTION_STATE,
                PROVENANCE_STATE,
                SUBMISSION_STATE,
                false,
                false,
                false,
                false,
                false,
                false,
                endpoint,
                profile,
                slotCopy.size(),
                passedSlotCount,
                ruleCopy.size(),
                slotCopy,
                ruleCopy,
                List.copyOf(checks),
                passedSlotCount == slotCopy.size() ? "passed" : "blocked"
        );
    }

    static OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterSlot slot(
            String code,
            String sourceSupplySlot,
            String adapterStage,
            String preflightRequirement,
            String blockedReason,
            String sourceEndpoint
    ) {
        return new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterSlot(
                code,
                sourceSupplySlot,
                adapterStage,
                preflightRequirement,
                blockedReason,
                sourceEndpoint,
                "passed"
        );
    }

    static OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterRule rule(
            String code,
            String category,
            String rule,
            String enforcement
    ) {
        return new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterRule(
                code,
                category,
                rule,
                enforcement
        );
    }
}
