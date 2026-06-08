package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSlotCatalog {

    static final int SLOT_COUNT = 25;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSlotCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
            .InstructionSlot> allSlots() {
        List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
                .InstructionSlot> slots = new ArrayList<>();
        slots.addAll(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightFoundationSlotCatalog
                .foundationSlots());
        slots.addAll(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightAssuranceSlotCatalog
                .assuranceSlots());
        return List.copyOf(slots);
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
            .InstructionSlot> slots(int fromInclusive, int toExclusive) {
        return List.copyOf(allSlots().subList(fromInclusive, toExclusive));
    }
}
