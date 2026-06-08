package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCatalogTests {

    @Test
    void catalogsExposeTwentyFiveSubmissionSlotsAndControls() {
        var slots = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSlotCatalog
                .allSlots();
        var controls =
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightComparisonControlCatalog
                        .allControls();
        var gates = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightGateCatalog
                .allGates();

        assertThat(slots)
                .hasSize(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSlotCatalog
                        .SLOT_COUNT);
        assertThat(controls)
                .hasSize(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightComparisonControlCatalog
                        .CONTROL_COUNT);
        assertThat(gates)
                .hasSize(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightGateCatalog
                        .GATE_COUNT);
        assertThat(slots).allSatisfy(slot -> {
            assertThat(slot.status()).isEqualTo("passed");
            assertThat(slot.sourceEndpoint()).startsWith(OpsShardReadinessRoutePaths.BASE_PATH);
        });
        assertThat(controls).allSatisfy(control -> {
            assertThat(control.enforcement()).isEqualTo("fail-closed");
            assertThat(control.rejectionCode()).startsWith("REJECT_DRAFT_TEXT_PACKAGE_SUBMISSION_");
        });
    }

    @Test
    void slotSlicesPreservePlanSegments() {
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSlotCatalog
                .slots(4, 11))
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
                        .SubmissionSlot::versionRange)
                .contains("Node v1266-v1269", "Node v1270-v1272");
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSlotCatalog
                .slots(16, 25))
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
                        .SubmissionSlot::versionRange)
                .contains("Node v1278-v1280", "Node v1281-v1285", "Node v1286");
    }
}
