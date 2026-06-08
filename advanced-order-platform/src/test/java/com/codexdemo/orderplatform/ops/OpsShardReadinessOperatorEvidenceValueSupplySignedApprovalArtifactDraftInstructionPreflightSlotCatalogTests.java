package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSlotCatalogTests {

    @Test
    void combinesFoundationAndAssuranceSlotsWithoutInstructionMaterialization() {
        var slots = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSlotCatalog
                .allSlots();

        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightFoundationSlotCatalog
                .foundationSlots()).hasSize(13);
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightAssuranceSlotCatalog
                .assuranceSlots()).hasSize(12);
        assertThat(slots).hasSize(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSlotCatalog
                        .SLOT_COUNT);
        assertThat(slots.stream().map(slot -> slot.code()).collect(Collectors.toSet())).hasSize(25);
        assertThat(slots.stream().map(slot -> slot.guardCode()).collect(Collectors.toSet())).hasSize(25);
        assertThat(slots).allSatisfy(slot -> {
            assertThat(slot.status()).isEqualTo("passed");
            assertThat(slot.sourceAuthoringRequirement()).contains("AUTHORING_READINESS");
            assertThat(slot.materializationBlocker()).isNotBlank();
            assertThat(slot.sourceEndpoint()).startsWith(OpsShardReadinessRoutePaths.BASE_PATH);
        });
    }
}
