package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeGateCatalogTests {

    @Test
    void gatesPinReadOnlyPackageIntakeBoundaries() {
        var gates = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeGateCatalog
                .allGates();

        assertThat(gates)
                .hasSize(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeGateCatalog
                        .GATE_COUNT);
        assertThat(gates).allSatisfy(gate -> assertThat(gate.enforcement()).isEqualTo("fail-closed"));
        assertThat(gates).extracting(
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
                                .IntakeGate::category)
                .contains("intake", "draft-text", "signature", "approval", "value", "runtime", "sibling",
                        "catalog", "source", "secret", "field-map", "digest", "review", "archive", "closeout");
    }

    @Test
    void gateSlicesExposeReviewAndCloseoutBoundaries() {
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeGateCatalog
                .gates(17, 20))
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
                        .IntakeGate::category)
                .containsExactly("review", "archive", "closeout");
    }
}
