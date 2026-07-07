package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply;

import java.util.List;

public record OpsShardReadinessOperatorEvidenceValueSupplyResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean readyForOperatorValueSupplyEnvelope,
    String sourcePlan,
    String sourceDraftVersion,
    String envelopeState,
    String suppliedValueState,
    String redactionState,
    String provenanceState,
    boolean readyForOperatorValueSubmission,
    boolean readyForEvidenceImport,
    boolean readyForManualEvidenceEntry,
    boolean readyForRuntimePayload,
    boolean readyForLiveExecution,
    boolean readyForProductionExecution,
    String endpoint,
    String profile,
    int slotCount,
    int passedSlotCount,
    List<SupplySlot> slots,
    List<String> checks,
    String status) {
  public record SupplySlot(
      String code,
      String sourceDraftSlot,
      String evidenceSource,
      String envelopeRequirement,
      String valuePolicy,
      String provenanceRequirement,
      String status) {}
}
