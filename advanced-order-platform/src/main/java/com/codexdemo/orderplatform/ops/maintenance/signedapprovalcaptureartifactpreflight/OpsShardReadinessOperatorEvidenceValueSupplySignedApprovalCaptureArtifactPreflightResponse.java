package com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight;

import java.util.List;

public
record OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean readyForArtifactPreflight,
    String sourcePlan,
    String sourceCapturePreflightVersion,
    String sourceJavaCapturePreflightVersion,
    String sourceTemplateVersion,
    String sourceApprovalPacketReviewVersion,
    String artifactPreflightState,
    String artifactMaterializationState,
    String signedApprovalCaptureState,
    String approvalGrantState,
    String valueImportState,
    String runtimeState,
    String siblingMutationState,
    boolean readyForArtifactDraft,
    boolean readyForSignedApprovalCapture,
    boolean readyForApprovalGrant,
    boolean readyForOperatorValueSubmission,
    boolean readyForEvidenceImport,
    boolean readyForRuntimePayload,
    boolean readyForProductionExecution,
    boolean siblingMutationAllowed,
    String endpoint,
    String profile,
    int fragmentCount,
    int passedFragmentCount,
    int sealCount,
    int passedSealCount,
    int gateCount,
    List<ArtifactFragment> fragments,
    List<ArtifactSeal> seals,
    List<ArtifactGate> gates,
    List<String> checks,
    String status) {
  public record ArtifactFragment(
      String code,
      String sourceCaptureInput,
      String artifactStage,
      String fragmentRequirement,
      String materializationBlocker,
      String sealCode,
      String evidenceFileId,
      String evidenceSnippetId,
      String sourceEndpoint,
      String status) {}

  public record ArtifactSeal(
      String code,
      String category,
      String sealRequirement,
      String rejectionCode,
      String enforcement,
      String status) {}

  public record ArtifactGate(String code, String category, String gate, String enforcement) {}
}
