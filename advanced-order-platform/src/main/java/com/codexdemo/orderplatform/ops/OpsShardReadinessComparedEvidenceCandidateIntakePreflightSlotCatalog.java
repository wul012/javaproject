package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessComparedEvidenceCandidateIntakePreflightSlotCatalog {

    private OpsShardReadinessComparedEvidenceCandidateIntakePreflightSlotCatalog() {
    }

    static List<OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeSlot> allSlots() {
        List<OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeSlot> slots =
                new ArrayList<>();
        slots.addAll(OpsShardReadinessComparedEvidenceCandidateIntakePreflightSourceSlotCatalog.sourceSlots());
        slots.addAll(OpsShardReadinessComparedEvidenceCandidateIntakePreflightComparisonSlotCatalog.comparisonSlots());
        slots.addAll(OpsShardReadinessComparedEvidenceCandidateIntakePreflightPolicySlotCatalog.policySlots());
        slots.addAll(OpsShardReadinessComparedEvidenceCandidateIntakePreflightCloseoutSlotCatalog.closeoutSlots());
        return List.copyOf(slots);
    }
}
