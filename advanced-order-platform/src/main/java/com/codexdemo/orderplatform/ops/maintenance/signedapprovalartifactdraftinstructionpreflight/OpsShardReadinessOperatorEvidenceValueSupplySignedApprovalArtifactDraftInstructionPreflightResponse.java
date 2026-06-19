package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight;

import java.util.List;

public
record OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean readyForInstructionPreflight,
    String sourcePlan,
    String sourceNodeAuthoringReadinessVersion,
    String sourceJavaAuthoringReadinessVersion,
    String instructionPreflightState,
    String instructionArtifactState,
    String signedDraftState,
    String signatureCaptureState,
    String approvalGrantState,
    String valueImportState,
    String runtimeState,
    String siblingMutationState,
    boolean readyForDraftTextPackage,
    boolean readyForSignedDraftText,
    boolean readyForSignatureCapture,
    boolean readyForApprovalGrant,
    boolean readyForOperatorValueSubmission,
    boolean readyForEvidenceImport,
    boolean readyForRuntimePayload,
    boolean siblingMutationAllowed,
    String endpoint,
    String profile,
    int slotCount,
    int passedSlotCount,
    int guardCount,
    int passedGuardCount,
    int gateCount,
    List<InstructionSlot> slots,
    List<InstructionGuard> guards,
    List<InstructionGate> gates,
    List<String> checks,
    String status) {
  public record InstructionSlot(
      String code,
      String sourceAuthoringRequirement,
      String futureInstruction,
      String instructionPurpose,
      String materializationBlocker,
      String guardCode,
      String sourceEndpoint,
      String status) {}

  public record InstructionGuard(
      String code,
      String category,
      String guard,
      String rejectionCode,
      String enforcement,
      String status) {}

  public record InstructionGate(String code, String category, String gate, String enforcement) {}
}
