package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v936";
    static final String SOURCE_DRAFT_VERSION = "Java v633";
    static final String ENVELOPE_STATE = "disabled-design";
    static final String SUPPLIED_VALUE_STATE = "not-accepted";
    static final String REDACTION_STATE = "redact-before-storage";
    static final String PROVENANCE_STATE = "required-before-import";

    private OpsShardReadinessOperatorEvidenceValueSupplySupport() {
    }

    static OpsShardReadinessOperatorEvidenceValueSupplyResponse response(
            String version,
            String endpoint,
            String profile,
            List<OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot> slots,
            List<String> additionalChecks
    ) {
        List<OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot> slotCopy = List.copyOf(slots);
        int passedSlotCount = (int) slotCopy.stream()
                .filter(slot -> "passed".equals(slot.status()))
                .count();
        List<String> checks = new ArrayList<>();
        checks.add("value-supply-slot-count-" + slotCopy.size());
        checks.add("value-supply-passed-slot-count-" + passedSlotCount);
        checks.add("value-supply-source-plan-" + SOURCE_PLAN);
        checks.add("value-supply-source-draft-" + SOURCE_DRAFT_VERSION);
        checks.add("value-supply-envelope-disabled-design-ready");
        checks.add("value-supply-supplied-value-state-not-accepted");
        checks.add("value-supply-operator-submission-locked");
        checks.add("value-supply-evidence-import-locked");
        checks.add("value-supply-runtime-payload-locked");
        checks.add("value-supply-production-execution-locked");
        checks.addAll(additionalChecks);

        return new OpsShardReadinessOperatorEvidenceValueSupplyResponse(
                PROJECT,
                version,
                true,
                false,
                true,
                SOURCE_PLAN,
                SOURCE_DRAFT_VERSION,
                ENVELOPE_STATE,
                SUPPLIED_VALUE_STATE,
                REDACTION_STATE,
                PROVENANCE_STATE,
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
                slotCopy,
                List.copyOf(checks),
                passedSlotCount == slotCopy.size() ? "passed" : "blocked"
        );
    }

    static OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot slot(
            String code,
            String sourceDraftSlot,
            String evidenceSource,
            String envelopeRequirement,
            String valuePolicy,
            String provenanceRequirement
    ) {
        return new OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot(
                code,
                sourceDraftSlot,
                evidenceSource,
                envelopeRequirement,
                valuePolicy,
                provenanceRequirement,
                "passed"
        );
    }
}
