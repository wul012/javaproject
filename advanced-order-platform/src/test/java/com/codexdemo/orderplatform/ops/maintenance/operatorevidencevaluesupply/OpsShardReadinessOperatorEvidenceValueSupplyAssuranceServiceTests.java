package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyAssuranceServiceTests {

  @Test
  void buildsValidationMatrixWithImportAndExecutionLocksHeld() {
    OpsShardReadinessOperatorEvidenceValueSupplyResponse matrix =
        new OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService().matrix();

    assertThat(matrix.version()).isEqualTo("Java v648");
    assertThat(matrix.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/operator-evidence-value-supply-validation-matrix");
    assertThat(matrix.profile())
        .isEqualTo("java-shard-readiness-operator-evidence-value-supply-validation-matrix.v1");
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
            "VALUE_SUPPLY_25_CLOSEOUT_LOCKS_HELD");
    assertThat(matrix.checks())
        .contains(
            "value-supply-validation-matrix-slice-21-25",
            "value-supply-validation-operator-submission-locked",
            "value-supply-validation-import-preview-locked",
            "value-supply-validation-execution-locks-held");
    assertThat(matrix.status()).isEqualTo("passed");
  }

  @Test
  void buildsSideEffectGateWithoutStartingServicesOrWritingState() {
    OpsShardReadinessOperatorEvidenceValueSupplyResponse gate =
        new OpsShardReadinessOperatorEvidenceValueSupplySideEffectGateService().gate();

    assertThat(gate.version()).isEqualTo("Java v650");
    assertThat(gate.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/operator-evidence-value-supply-side-effect-gate");
    assertThat(gate.profile())
        .isEqualTo("java-shard-readiness-operator-evidence-value-supply-side-effect-gate.v1");
    assertThat(gate.readOnly()).isTrue();
    assertThat(gate.executionAllowed()).isFalse();
    assertThat(gate.readyForRuntimePayload()).isFalse();
    assertThat(gate.readyForProductionExecution()).isFalse();
    assertThat(gate.slotCount()).isEqualTo(25);
    assertThat(gate.checks())
        .contains(
            "value-supply-side-effect-gate-no-sibling-service-start",
            "value-supply-side-effect-gate-no-state-write",
            "value-supply-side-effect-gate-no-runtime-payload",
            "value-supply-side-effect-gate-no-production-path");
    assertThat(gate.status()).isEqualTo("passed");
  }

  @Test
  void buildsOperatorReviewChecklistWithoutGrantingApproval() {
    OpsShardReadinessOperatorEvidenceValueSupplyResponse checklist =
        new OpsShardReadinessOperatorEvidenceValueSupplyOperatorReviewChecklistService()
            .checklist();

    assertThat(checklist.version()).isEqualTo("Java v652");
    assertThat(checklist.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/operator-evidence-value-supply-operator-review-checklist");
    assertThat(checklist.profile())
        .isEqualTo(
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
            "VALUE_SUPPLY_04_VALUE_KIND");
    assertThat(checklist.checks())
        .contains(
            "value-supply-operator-review-checklist-envelope-id",
            "value-supply-operator-review-checklist-source-draft-slot",
            "value-supply-operator-review-checklist-redaction-before-value",
            "value-supply-operator-review-checklist-no-approval-grant");
    assertThat(checklist.status()).isEqualTo("passed");
  }

  @Test
  void buildsDigestBlueprintWithoutValueHashOrImportReadiness() {
    OpsShardReadinessOperatorEvidenceValueSupplyResponse blueprint =
        new OpsShardReadinessOperatorEvidenceValueSupplyDigestBlueprintService().blueprint();

    assertThat(blueprint.version()).isEqualTo("Java v654");
    assertThat(blueprint.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/operator-evidence-value-supply-digest-blueprint");
    assertThat(blueprint.profile())
        .isEqualTo("java-shard-readiness-operator-evidence-value-supply-digest-blueprint.v1");
    assertThat(blueprint.readyForEvidenceImport()).isFalse();
    assertThat(blueprint.slotCount()).isEqualTo(25);
    assertThat(blueprint.slots().get(0).code()).isEqualTo("VALUE_SUPPLY_01_ENVELOPE_ID");
    assertThat(blueprint.slots().get(24).code()).isEqualTo("VALUE_SUPPLY_25_CLOSEOUT_LOCKS_HELD");
    assertThat(blueprint.checks())
        .contains(
            "value-supply-digest-blueprint-slot-count-25",
            "value-supply-digest-blueprint-no-value-hash",
            "value-supply-digest-blueprint-provenance-before-import",
            "value-supply-digest-blueprint-lock-flags-covered");
    assertThat(blueprint.status()).isEqualTo("passed");
  }

  @Test
  void buildsArchivePlanWithoutWritingFilesOrStartingProcesses() {
    OpsShardReadinessOperatorEvidenceValueSupplyResponse plan =
        new OpsShardReadinessOperatorEvidenceValueSupplyArchivePlanService().plan();

    assertThat(plan.version()).isEqualTo("Java v656");
    assertThat(plan.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/operator-evidence-value-supply-archive-plan");
    assertThat(plan.profile())
        .isEqualTo("java-shard-readiness-operator-evidence-value-supply-archive-plan.v1");
    assertThat(plan.readOnly()).isTrue();
    assertThat(plan.executionAllowed()).isFalse();
    assertThat(plan.slotCount()).isEqualTo(5);
    assertThat(plan.slots().get(0).code()).isEqualTo("VALUE_SUPPLY_21_IMPORT_PREVIEW_BLOCK");
    assertThat(plan.slots().get(4).code()).isEqualTo("VALUE_SUPPLY_25_CLOSEOUT_LOCKS_HELD");
    assertThat(plan.checks())
        .contains(
            "value-supply-archive-plan-external-capture",
            "value-supply-archive-plan-no-file-write",
            "value-supply-archive-plan-no-runtime-process",
            "value-supply-archive-plan-lock-summary-required");
    assertThat(plan.status()).isEqualTo("passed");
  }

  @Test
  void buildsCloseoutWithAllDisabledValueSupplyLocksHeld() {
    OpsShardReadinessOperatorEvidenceValueSupplyResponse closeout =
        new OpsShardReadinessOperatorEvidenceValueSupplyCloseoutService().closeout();

    assertThat(closeout.version()).isEqualTo("Java v658");
    assertThat(closeout.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/operator-evidence-value-supply-closeout");
    assertThat(closeout.profile())
        .isEqualTo("java-shard-readiness-operator-evidence-value-supply-closeout.v1");
    assertThat(closeout.sourcePlan()).isEqualTo("Node v936");
    assertThat(closeout.sourceDraftVersion()).isEqualTo("Java v633");
    assertThat(closeout.envelopeState()).isEqualTo("disabled-design");
    assertThat(closeout.suppliedValueState()).isEqualTo("not-accepted");
    assertThat(closeout.readyForOperatorValueSubmission()).isFalse();
    assertThat(closeout.readyForEvidenceImport()).isFalse();
    assertThat(closeout.readyForRuntimePayload()).isFalse();
    assertThat(closeout.readyForProductionExecution()).isFalse();
    assertThat(closeout.slotCount()).isEqualTo(25);
    assertThat(closeout.passedSlotCount()).isEqualTo(25);
    assertThat(closeout.slots().get(0).code()).isEqualTo("VALUE_SUPPLY_01_ENVELOPE_ID");
    assertThat(closeout.slots().get(24).code()).isEqualTo("VALUE_SUPPLY_25_CLOSEOUT_LOCKS_HELD");
    assertThat(closeout.checks())
        .contains(
            "value-supply-closeout-versions-v634-v658",
            "value-supply-closeout-slot-count-25",
            "value-supply-closeout-foundation-and-assurance-split",
            "value-supply-closeout-values-not-accepted",
            "value-supply-closeout-all-execution-locks-held");
    assertThat(closeout.status()).isEqualTo("passed");
  }
}
