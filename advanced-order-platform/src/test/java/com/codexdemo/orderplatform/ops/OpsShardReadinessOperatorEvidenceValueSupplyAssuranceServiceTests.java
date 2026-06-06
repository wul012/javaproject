package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyAssuranceServiceTests {

    @Test
    void buildsValidationMatrixWithImportAndExecutionLocksHeld() {
        OpsShardReadinessOperatorEvidenceValueSupplyResponse matrix =
                new OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService().matrix();

        assertThat(matrix.version()).isEqualTo("Java v648");
        assertThat(matrix.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-validation-matrix");
        assertThat(matrix.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-validation-matrix.v1");
        assertThat(matrix.readyForOperatorValueSubmission()).isFalse();
        assertThat(matrix.readyForEvidenceImport()).isFalse();
        assertThat(matrix.readyForLiveExecution()).isFalse();
        assertThat(matrix.readyForProductionExecution()).isFalse();
        assertThat(matrix.slotCount()).isEqualTo(5);
        assertThat(matrix.slots())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot::code)
                .containsExactly(
                        "VALUE_SUPPLY_21_IMPORT_PREVIEW_BLOCK",
                        "VALUE_SUPPLY_22_WRITE_SIDE_EFFECT_BLOCK",
                        "VALUE_SUPPLY_23_LIVE_EXECUTION_BLOCK",
                        "VALUE_SUPPLY_24_PRODUCTION_EXECUTION_BLOCK",
                        "VALUE_SUPPLY_25_CLOSEOUT_LOCKS_HELD"
                );
        assertThat(matrix.checks()).contains(
                "value-supply-validation-matrix-slice-21-25",
                "value-supply-validation-operator-submission-locked",
                "value-supply-validation-import-preview-locked",
                "value-supply-validation-execution-locks-held"
        );
        assertThat(matrix.status()).isEqualTo("passed");
    }
}
