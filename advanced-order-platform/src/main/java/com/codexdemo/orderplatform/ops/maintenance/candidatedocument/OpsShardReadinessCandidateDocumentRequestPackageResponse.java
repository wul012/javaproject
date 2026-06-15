package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;

public record OpsShardReadinessCandidateDocumentRequestPackageResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean readyForCandidateDocumentRequestPackage,
    String sourcePlan,
    String sourceNodeCandidateIntakeVersion,
    String sourceJavaCandidateIntakeVersion,
    String requestPackageState,
    int requestItemCount,
    int passedRequestItemCount,
    int acceptanceCheckCount,
    int passedAcceptanceCheckCount,
    int requestedCandidateFieldCount,
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
    String endpoint,
    String profile,
    List<RequestItem> requestItems,
    List<AcceptanceCheck> acceptanceChecks,
    List<String> gates,
    List<String> checks,
    String status) {
  public record RequestItem(
      String code,
      String sourceIntakeSlot,
      String requestedFields,
      String instruction,
      String owner,
      String sourceEndpoint,
      String status) {}

  public record AcceptanceCheck(
      String code,
      String category,
      String check,
      String rejectionCode,
      String enforcement,
      String status) {}
}
