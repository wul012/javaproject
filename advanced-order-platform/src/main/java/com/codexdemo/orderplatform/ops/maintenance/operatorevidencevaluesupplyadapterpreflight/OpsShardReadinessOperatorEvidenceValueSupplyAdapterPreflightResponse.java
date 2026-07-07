package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight;

import java.util.List;

public record OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean readyForDisabledAdapterPreflight,
    String sourcePlan,
    String sourceSupplyVersion,
    String adapterState,
    String acceptedValueState,
    String compatibilityState,
    String redactionState,
    String provenanceState,
    String submissionState,
    boolean readyForAdapterImplementation,
    boolean readyForOperatorValueSubmission,
    boolean readyForEvidenceImport,
    boolean readyForRuntimePayload,
    boolean readyForLiveExecution,
    boolean readyForProductionExecution,
    String endpoint,
    String profile,
    int slotCount,
    int passedSlotCount,
    int ruleCount,
    List<AdapterSlot> slots,
    List<AdapterRule> rules,
    List<String> checks,
    String status) {
  public record AdapterSlot(
      String code,
      String sourceSupplySlot,
      String adapterStage,
      String preflightRequirement,
      String blockedReason,
      String sourceEndpoint,
      String status) {}

  public record AdapterRule(String code, String category, String rule, String enforcement) {}
}
