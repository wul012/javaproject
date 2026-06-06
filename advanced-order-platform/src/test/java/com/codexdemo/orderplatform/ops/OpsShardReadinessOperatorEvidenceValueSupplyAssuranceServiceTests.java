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

    @Test
    void buildsSideEffectGateWithoutStartingServicesOrWritingState() {
        OpsShardReadinessOperatorEvidenceValueSupplyResponse gate =
                new OpsShardReadinessOperatorEvidenceValueSupplySideEffectGateService().gate();

        assertThat(gate.version()).isEqualTo("Java v650");
        assertThat(gate.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-side-effect-gate");
        assertThat(gate.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-side-effect-gate.v1");
        assertThat(gate.readOnly()).isTrue();
        assertThat(gate.executionAllowed()).isFalse();
        assertThat(gate.readyForRuntimePayload()).isFalse();
        assertThat(gate.readyForProductionExecution()).isFalse();
        assertThat(gate.slotCount()).isEqualTo(25);
        assertThat(gate.checks()).contains(
                "value-supply-side-effect-gate-no-sibling-service-start",
                "value-supply-side-effect-gate-no-state-write",
                "value-supply-side-effect-gate-no-runtime-payload",
                "value-supply-side-effect-gate-no-production-path"
        );
        assertThat(gate.status()).isEqualTo("passed");
    }

    @Test
    void buildsOperatorReviewChecklistWithoutGrantingApproval() {
        OpsShardReadinessOperatorEvidenceValueSupplyResponse checklist =
                new OpsShardReadinessOperatorEvidenceValueSupplyOperatorReviewChecklistService().checklist();

        assertThat(checklist.version()).isEqualTo("Java v652");
        assertThat(checklist.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-operator-review-checklist");
        assertThat(checklist.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-operator-review-checklist.v1");
        assertThat(checklist.readyForOperatorValueSubmission()).isFalse();
        assertThat(checklist.readyForEvidenceImport()).isFalse();
        assertThat(checklist.slotCount()).isEqualTo(4);
        assertThat(checklist.slots())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot::code)
                .containsExactly(
                        "VALUE_SUPPLY_01_ENVELOPE_ID",
                        "VALUE_SUPPLY_02_OPERATOR_REFERENCE",
                        "VALUE_SUPPLY_03_SOURCE_DRAFT_SLOT",
                        "VALUE_SUPPLY_04_VALUE_KIND"
                );
        assertThat(checklist.checks()).contains(
                "value-supply-operator-review-checklist-envelope-id",
                "value-supply-operator-review-checklist-source-draft-slot",
                "value-supply-operator-review-checklist-redaction-before-value",
                "value-supply-operator-review-checklist-no-approval-grant"
        );
        assertThat(checklist.status()).isEqualTo("passed");
    }
}
