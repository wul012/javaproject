package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness;

import java.util.List;

public
record OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean readyForDraftReadiness,
    String sourcePlan,
    String sourceArtifactPreflightVersion,
    String sourceCapturePreflightVersion,
    String sourceTemplateVersion,
    String sourceApprovalPacketReviewVersion,
    String readinessState,
    String artifactDraftState,
    String artifactMaterializationState,
    String signedApprovalCaptureState,
    String approvalGrantState,
    String valueImportState,
    String runtimeState,
    String siblingMutationState,
    boolean readyForManualArtifactDraft,
    boolean readyForSignedApprovalCapture,
    boolean readyForApprovalGrant,
    boolean readyForOperatorValueSubmission,
    boolean readyForEvidenceImport,
    boolean readyForRuntimePayload,
    boolean readyForProductionExecution,
    boolean siblingMutationAllowed,
    String endpoint,
    String profile,
    int readinessItemCount,
    int passedReadinessItemCount,
    int ownershipRuleCount,
    int gateCount,
    List<ReadinessItem> readinessItems,
    List<OwnershipRule> ownershipRules,
    List<DraftGate> gates,
    List<String> checks,
    String status) {
  public record ReadinessItem(
      String code,
      String sourceArtifactFragment,
      String readinessStage,
      String readinessRequirement,
      String blockedReason,
      String ownershipCode,
      String evidenceFileId,
      String evidenceSnippetId,
      String sourceEndpoint,
      String status) {}

  public record OwnershipRule(
      String code, String category, String owner, String responsibility, String enforcement) {}

  public record DraftGate(String code, String category, String gate, String enforcement) {}
}
