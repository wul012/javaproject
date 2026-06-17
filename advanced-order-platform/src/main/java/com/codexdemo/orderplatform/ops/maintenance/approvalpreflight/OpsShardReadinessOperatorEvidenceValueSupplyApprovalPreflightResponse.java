package com.codexdemo.orderplatform.ops.maintenance.approvalpreflight;

import java.util.List;

public record OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean readyForApprovalPreflight,
    String sourcePlan,
    String sourceEnvelopeVersion,
    String sourceValueSupplyVersion,
    String sourceAdapterPreflightVersion,
    String approvalPacketState,
    String approvalCaptureState,
    String acceptedValueState,
    String importState,
    String redactionDigestState,
    String provenanceState,
    String malformedValueState,
    String receiptState,
    boolean readyForSignedApprovalCapture,
    boolean readyForApprovalGrant,
    boolean readyForOperatorValueSubmission,
    boolean readyForEvidenceImport,
    boolean readyForRuntimePayload,
    boolean readyForLiveExecution,
    boolean readyForProductionExecution,
    String endpoint,
    String profile,
    int itemCount,
    int passedItemCount,
    int policyCount,
    List<ApprovalItem> items,
    List<ApprovalPolicy> policies,
    List<String> checks,
    String status) {
  public record ApprovalItem(
      String code,
      String sourceEnvelopeSlot,
      String packetStage,
      String packetRequirement,
      String blockedReason,
      String evidenceFileId,
      String evidenceSnippetId,
      String sourceEndpoint,
      String status) {}

  public record ApprovalPolicy(String code, String category, String policy, String enforcement) {}
}
