package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparedpackageevidenceintake;

import java.util.List;

public
record OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean readyForComparedPackageEvidenceIntake,
    String sourcePlan,
    String sourceNodeAcceptancePrecheckVersion,
    String sourceJavaAcceptancePrecheckVersion,
    String intakeContractState,
    String comparedEvidenceState,
    String signedDraftTextParseState,
    String detachedSignatureParseState,
    String approvalGrantState,
    String runtimePayloadState,
    String siblingMutationState,
    boolean readyForComparedEvidenceAcceptance,
    boolean readyForSignedDraftTextParsing,
    boolean readyForDetachedSignatureParsing,
    boolean readyForApprovalGrant,
    boolean readyForRuntimePayload,
    boolean siblingMutationAllowed,
    String endpoint,
    String profile,
    int evidenceSlotCount,
    int passedEvidenceSlotCount,
    int guardCount,
    int passedGuardCount,
    List<EvidenceSlot> evidenceSlots,
    List<IntakeGuard> guards,
    List<String> checks,
    String status) {
  public record EvidenceSlot(
      String code,
      String sourceVersion,
      String evidenceSlot,
      String evidenceQuestion,
      String missingEvidenceGuard,
      String sourceEndpoint,
      String status) {}

  public record IntakeGuard(
      String code,
      String category,
      String guard,
      String rejectionCode,
      String enforcement,
      String status) {}
}
