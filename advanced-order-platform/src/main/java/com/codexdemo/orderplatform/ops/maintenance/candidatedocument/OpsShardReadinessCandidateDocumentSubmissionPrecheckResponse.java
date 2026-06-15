package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;

public record OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean readyForSubmissionPrecheck,
    String sourcePlan,
    String sourceNodeRequestPackageVersion,
    String sourceJavaRequestPackageVersion,
    String sourceJavaHandoffVersion,
    String sourceRequestPackageEndpoint,
    String sourceHandoffEndpoint,
    String precheckState,
    String endpoint,
    String profile,
    int checkpointCount,
    int passedCheckpointCount,
    int validatorCount,
    int passedValidatorCount,
    int requestedCandidateFieldCount,
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
    boolean importAllowed,
    boolean evaluationAllowed,
    boolean approvalGrantAllowed,
    boolean signedApprovalCaptureAllowed,
    boolean runtimePayloadAllowed,
    boolean writeAllowed,
    boolean siblingMutationAllowed,
    List<Checkpoint> checkpoints,
    List<Validator> validators,
    List<Artifact> artifacts,
    List<String> gates,
    List<String> checks,
    String status) {
  public record Checkpoint(
      String code,
      String sourceCode,
      String category,
      String instruction,
      String owner,
      String status) {}

  public record Validator(
      String code,
      String checkpointCode,
      String rejectionCode,
      String check,
      String enforcement,
      String status) {}

  public record Artifact(String code, String reference, String purpose, String status) {}
}
