package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeCatalogTests {

    @Test
    void exposesTenEvidenceSlotsAndTenIntakeGuards() {
        var slots = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSlotCatalog
                .allSlots();
        var guards = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeGuardCatalog
                .allGuards();

        assertThat(slots).hasSize(10);
        assertThat(guards).hasSize(10);
        assertThat(slots).extracting(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
                        .EvidenceSlot::status
        ).containsOnly("passed");
        assertThat(guards).allSatisfy(guard -> {
            assertThat(guard.enforcement()).isEqualTo("fail-closed");
            assertThat(guard.rejectionCode()).startsWith("reject-missing-");
        });
    }
}

