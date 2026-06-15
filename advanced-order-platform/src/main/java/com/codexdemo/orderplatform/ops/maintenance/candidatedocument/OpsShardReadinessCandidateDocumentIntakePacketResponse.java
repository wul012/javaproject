package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;

public record OpsShardReadinessCandidateDocumentIntakePacketResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean readyForIntakePacket,
    String sourcePlan,
    String sourceNodeSubmissionPrecheckVersion,
    String sourceJavaSubmissionPrecheckVersion,
    String sourcePrecheckEndpoint,
    String intakePacketState,
    String endpoint,
    String profile,
    int sourceLineageCount,
    int moduleCount,
    int intakeSlotCount,
    int passedIntakeSlotCount,
    int intakeGuardCount,
    int passedIntakeGuardCount,
    int coveredSourceCheckpointCount,
    int coveredSourceValidatorCount,
    int carriedCandidateFieldCount,
    int artifactCount,
    int gateCount,
    int realDocumentCount,
    int syntheticDocumentCount,
    int stagedDocumentCount,
    int importedDocumentCount,
    int evaluatedDocumentCount,
    int acceptedDocumentCount,
    int rejectedDocumentCount,
    int payloadCount,
    boolean materialAccepted,
    boolean importAllowed,
    boolean evaluationAllowed,
    boolean approvalGrantAllowed,
    boolean signedApprovalCaptureAllowed,
    boolean runtimePayloadAllowed,
    boolean writeAllowed,
    boolean siblingMutationAllowed,
    List<SourceLineage> sourceLineage,
    List<ModuleEntry> modules,
    List<IntakeSlot> intakeSlots,
    List<IntakeGuard> intakeGuards,
    List<Artifact> artifacts,
    List<String> gates,
    List<String> checks,
    String status) {
  public record SourceLineage(
      String code, String version, String source, String role, String status) {}

  public record ModuleEntry(
      int order, String code, String responsibility, String owner, String status) {}

  public record IntakeSlot(
      int order,
      String code,
      String category,
      String coveredCheckpointCodes,
      int coveredCheckpointCount,
      int carriedFieldCount,
      String envelopePlaceholder,
      String status) {}

  public record IntakeGuard(
      String code,
      String slotCode,
      String rejectionCode,
      String guard,
      String enforcement,
      String status) {}

  public record Artifact(String code, String reference, String purpose, String status) {}
}
