package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;

public record OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean readyForMaterialSubmissionPrecheck,
    String sourcePlan,
    String sourceNodeMaterialRequestVersion,
    String sourceJavaMaterialRequestVersion,
    String sourceMaterialRequestEndpoint,
    String materialSubmissionPrecheckState,
    String endpoint,
    String profile,
    int moduleCount,
    int checkpointCount,
    int passedCheckpointCount,
    int validatorCount,
    int passedValidatorCount,
    int sourceRequestItemCount,
    int sourceAcceptanceCheckCount,
    int requiredMaterialFieldCount,
    int submissionMaterialFieldCount,
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
    boolean materialSubmissionAccepted,
    boolean importAllowed,
    boolean evaluationAllowed,
    boolean approvalGrantAllowed,
    boolean signedApprovalCaptureAllowed,
    boolean runtimePayloadAllowed,
    boolean writeAllowed,
    boolean siblingMutationAllowed,
    List<ModuleEntry> modules,
    List<SubmissionCheckpoint> checkpoints,
    List<Validator> validators,
    List<Artifact> artifacts,
    List<String> gates,
    List<String> checks,
    String status) {
  public record ModuleEntry(int order, String code, String responsibility, String status) {}

  public record SubmissionCheckpoint(
      int order,
      String code,
      String category,
      List<String> sourceRequestCodes,
      List<String> sourceAcceptanceCheckCodes,
      String requiredMaterialFields,
      String submissionMaterialFields,
      String precheck,
      String owner,
      String status) {}

  public record Validator(
      String code,
      String checkpointCode,
      String rejectionCode,
      String validation,
      String enforcement,
      String status) {}

  public record Artifact(String code, String reference, String purpose, String status) {}
}
