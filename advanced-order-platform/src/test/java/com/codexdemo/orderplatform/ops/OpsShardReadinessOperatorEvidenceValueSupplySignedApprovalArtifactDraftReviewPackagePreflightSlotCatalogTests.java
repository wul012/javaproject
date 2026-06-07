package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSlotCatalogTests {

    @Test
    void combinesFoundationAndAssuranceSlotsWithoutMaterializingPackage() {
        var slots = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSlotCatalog
                .allSlots();

        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightFoundationSlotCatalog
                .foundationSlots()).hasSize(13);
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightAssuranceSlotCatalog
                .assuranceSlots()).hasSize(12);
        assertThat(slots).hasSize(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSlotCatalog
                        .SLOT_COUNT);
        assertThat(slots.stream().map(slot -> slot.code()).collect(Collectors.toSet())).hasSize(25);
        assertThat(slots.stream().map(slot -> slot.guardCode()).collect(Collectors.toSet())).hasSize(25);
        assertThat(slots).allSatisfy(slot -> {
            assertThat(slot.status()).isEqualTo("passed");
            assertThat(slot.sourceLane()).isNotBlank();
            assertThat(slot.sourceField()).isNotBlank();
            assertThat(slot.materializationBlocker()).isNotBlank();
            assertThat(slot.sourceEndpoint()).startsWith(OpsShardReadinessRoutePaths.BASE_PATH);
        });
    }
}
