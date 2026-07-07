package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceServiceTests {

  @Test
  void buildsPayloadFirewallWithoutRuntimePayloadOrImportPreview() {
    OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse firewall =
        new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightPayloadFirewallService()
            .firewall();

    assertThat(firewall.version()).isEqualTo("Java v674");
    assertThat(firewall.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/operator-evidence-value-supply-adapter-preflight-payload-firewall");
    assertThat(firewall.profile())
        .isEqualTo(
            "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-payload-firewall.v1");
    assertThat(firewall.readyForRuntimePayload()).isFalse();
    assertThat(firewall.readyForEvidenceImport()).isFalse();
    assertThat(firewall.slotCount()).isEqualTo(2);
    assertThat(firewall.ruleCount()).isEqualTo(2);
    assertThat(firewall.slots())
        .extracting(
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterSlot::code)
        .containsExactly(
            "ADAPTER_PREFLIGHT_20_RUNTIME_PAYLOAD_BLOCK",
            "ADAPTER_PREFLIGHT_21_IMPORT_PREVIEW_BLOCK");
    assertThat(firewall.rules())
        .extracting(
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterRule::code)
        .containsExactly(
            "ADAPTER_RULE_15_RUNTIME_PAYLOAD_BLOCKED", "ADAPTER_RULE_16_NO_STATE_WRITE");
    assertThat(firewall.checks())
        .contains(
            "value-supply-adapter-preflight-payload-firewall-runtime-payload-blocked",
            "value-supply-adapter-preflight-payload-firewall-import-preview-locked",
            "value-supply-adapter-preflight-payload-firewall-no-state-write");
    assertThat(firewall.status()).isEqualTo("passed");
  }

  @Test
  void buildsRuntimeSubmissionLockAcrossImportWriteAndExecutionBoundaries() {
    OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse lock =
        new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuntimeSubmissionLockService()
            .lock();

    assertThat(lock.version()).isEqualTo("Java v676");
    assertThat(lock.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/operator-evidence-value-supply-adapter-preflight-runtime-submission-lock");
    assertThat(lock.profile())
        .isEqualTo(
            "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-runtime-submission-lock.v1");
    assertThat(lock.submissionState()).isEqualTo("locked");
    assertThat(lock.readyForOperatorValueSubmission()).isFalse();
    assertThat(lock.readyForEvidenceImport()).isFalse();
    assertThat(lock.readyForLiveExecution()).isFalse();
    assertThat(lock.readyForProductionExecution()).isFalse();
    assertThat(lock.slotCount()).isEqualTo(4);
    assertThat(lock.ruleCount()).isEqualTo(2);
    assertThat(lock.slots())
        .extracting(
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterSlot::code)
        .containsExactly(
            "ADAPTER_PREFLIGHT_21_IMPORT_PREVIEW_BLOCK",
            "ADAPTER_PREFLIGHT_22_WRITE_SIDE_EFFECT_BLOCK",
            "ADAPTER_PREFLIGHT_23_LIVE_EXECUTION_BLOCK",
            "ADAPTER_PREFLIGHT_24_PRODUCTION_EXECUTION_BLOCK");
    assertThat(lock.rules())
        .extracting(
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterRule::code)
        .containsExactly(
            "ADAPTER_RULE_16_NO_STATE_WRITE", "ADAPTER_RULE_17_NO_IMPORT_OR_LIVE_EXECUTION");
    assertThat(lock.checks())
        .contains(
            "value-supply-adapter-preflight-runtime-submission-operator-values-locked",
            "value-supply-adapter-preflight-runtime-submission-no-state-write",
            "value-supply-adapter-preflight-runtime-submission-live-execution-locked",
            "value-supply-adapter-preflight-runtime-submission-production-locked");
    assertThat(lock.status()).isEqualTo("passed");
  }

  @Test
  void buildsOperatorRehearsalChecklistWithoutApprovalGrant() {
    OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse checklist =
        new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightOperatorRehearsalChecklistService()
            .checklist();

    assertThat(checklist.version()).isEqualTo("Java v678");
    assertThat(checklist.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/operator-evidence-value-supply-adapter-preflight-operator-rehearsal-checklist");
    assertThat(checklist.profile())
        .isEqualTo(
            "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-operator-rehearsal-checklist.v1");
    assertThat(checklist.readyForAdapterImplementation()).isFalse();
    assertThat(checklist.readyForOperatorValueSubmission()).isFalse();
    assertThat(checklist.slotCount()).isEqualTo(13);
    assertThat(checklist.ruleCount()).isEqualTo(18);
    assertThat(checklist.slots())
        .extracting(
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterSlot::code)
        .contains(
            "ADAPTER_PREFLIGHT_01_ENVELOPE_ID_COMPATIBILITY",
            "ADAPTER_PREFLIGHT_13_PROVENANCE_SOURCE_ID",
            "ADAPTER_PREFLIGHT_21_IMPORT_PREVIEW_BLOCK",
            "ADAPTER_PREFLIGHT_25_CLOSEOUT_LOCKS_HELD");
    assertThat(checklist.checks())
        .contains(
            "value-supply-adapter-preflight-rehearsal-envelope-metadata-reviewed",
            "value-supply-adapter-preflight-rehearsal-provenance-reviewed",
            "value-supply-adapter-preflight-rehearsal-runtime-locks-reviewed",
            "value-supply-adapter-preflight-rehearsal-no-approval-grant",
            "value-supply-adapter-preflight-rehearsal-no-adapter-implementation");
    assertThat(checklist.status()).isEqualTo("passed");
  }

  @Test
  void buildsDigestBlueprintWithoutValueHashOrImportReadiness() {
    OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse blueprint =
        new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightDigestBlueprintService()
            .blueprint();

    assertThat(blueprint.version()).isEqualTo("Java v680");
    assertThat(blueprint.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/operator-evidence-value-supply-adapter-preflight-digest-blueprint");
    assertThat(blueprint.profile())
        .isEqualTo(
            "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-digest-blueprint.v1");
    assertThat(blueprint.readyForEvidenceImport()).isFalse();
    assertThat(blueprint.readyForRuntimePayload()).isFalse();
    assertThat(blueprint.slotCount()).isEqualTo(25);
    assertThat(blueprint.ruleCount()).isEqualTo(18);
    assertThat(blueprint.slots().get(0).code())
        .isEqualTo("ADAPTER_PREFLIGHT_01_ENVELOPE_ID_COMPATIBILITY");
    assertThat(blueprint.slots().get(24).code())
        .isEqualTo("ADAPTER_PREFLIGHT_25_CLOSEOUT_LOCKS_HELD");
    assertThat(blueprint.checks())
        .contains(
            "value-supply-adapter-preflight-digest-blueprint-no-value-hash",
            "value-supply-adapter-preflight-digest-blueprint-source-supply-v658",
            "value-supply-adapter-preflight-digest-blueprint-node-v986-approval-draft-boundary",
            "value-supply-adapter-preflight-digest-blueprint-lock-flags-covered");
    assertThat(blueprint.status()).isEqualTo("passed");
  }

  @Test
  void buildsArchivePlanWithoutWritingFilesOrStartingProcesses() {
    OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse plan =
        new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightArchivePlanService().plan();

    assertThat(plan.version()).isEqualTo("Java v682");
    assertThat(plan.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/operator-evidence-value-supply-adapter-preflight-archive-plan");
    assertThat(plan.profile())
        .isEqualTo(
            "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-archive-plan.v1");
    assertThat(plan.readOnly()).isTrue();
    assertThat(plan.executionAllowed()).isFalse();
    assertThat(plan.readyForRuntimePayload()).isFalse();
    assertThat(plan.slotCount()).isEqualTo(5);
    assertThat(plan.ruleCount()).isEqualTo(18);
    assertThat(plan.slots().get(0).code()).isEqualTo("ADAPTER_PREFLIGHT_21_IMPORT_PREVIEW_BLOCK");
    assertThat(plan.slots().get(4).code()).isEqualTo("ADAPTER_PREFLIGHT_25_CLOSEOUT_LOCKS_HELD");
    assertThat(plan.checks())
        .contains(
            "value-supply-adapter-preflight-archive-plan-external-capture",
            "value-supply-adapter-preflight-archive-plan-no-file-write",
            "value-supply-adapter-preflight-archive-plan-no-runtime-process",
            "value-supply-adapter-preflight-archive-plan-lock-summary-required");
    assertThat(plan.status()).isEqualTo("passed");
  }

  @Test
  void buildsCloseoutWithAllAdapterPreflightLocksHeld() {
    OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse closeout =
        new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService()
            .closeout();

    assertThat(closeout.version()).isEqualTo("Java v684");
    assertThat(closeout.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/operator-evidence-value-supply-adapter-preflight-closeout");
    assertThat(closeout.profile())
        .isEqualTo(
            "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-closeout.v1");
    assertThat(closeout.sourcePlan()).isEqualTo("Node v986");
    assertThat(closeout.sourceSupplyVersion()).isEqualTo("Java v658");
    assertThat(closeout.adapterState()).isEqualTo("disabled-preflight");
    assertThat(closeout.acceptedValueState()).isEqualTo("not-accepted");
    assertThat(closeout.readyForAdapterImplementation()).isFalse();
    assertThat(closeout.readyForOperatorValueSubmission()).isFalse();
    assertThat(closeout.readyForEvidenceImport()).isFalse();
    assertThat(closeout.readyForRuntimePayload()).isFalse();
    assertThat(closeout.readyForLiveExecution()).isFalse();
    assertThat(closeout.readyForProductionExecution()).isFalse();
    assertThat(closeout.slotCount()).isEqualTo(25);
    assertThat(closeout.passedSlotCount()).isEqualTo(25);
    assertThat(closeout.ruleCount()).isEqualTo(18);
    assertThat(closeout.slots().get(0).code())
        .isEqualTo("ADAPTER_PREFLIGHT_01_ENVELOPE_ID_COMPATIBILITY");
    assertThat(closeout.slots().get(24).code())
        .isEqualTo("ADAPTER_PREFLIGHT_25_CLOSEOUT_LOCKS_HELD");
    assertThat(closeout.checks())
        .contains(
            "value-supply-adapter-preflight-closeout-versions-v660-v684",
            "value-supply-adapter-preflight-closeout-support-carried-forward-v659",
            "value-supply-adapter-preflight-closeout-foundation-and-assurance-split",
            "value-supply-adapter-preflight-closeout-node-v986-approval-draft-boundary",
            "value-supply-adapter-preflight-closeout-no-approval-captured",
            "value-supply-adapter-preflight-closeout-import-runtime-live-production-locked",
            "value-supply-adapter-preflight-closeout-all-locks-held");
    assertThat(closeout.status()).isEqualTo("passed");
  }
}
