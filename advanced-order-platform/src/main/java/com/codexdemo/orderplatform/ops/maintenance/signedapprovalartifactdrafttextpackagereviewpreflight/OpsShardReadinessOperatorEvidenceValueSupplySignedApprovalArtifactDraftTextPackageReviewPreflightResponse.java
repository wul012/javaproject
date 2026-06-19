package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagereviewpreflight;

import java.util.List;

public
record OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean readyForDraftTextPackageReviewPreflight,
    String sourcePlan,
    String sourceNodeTextPackageIntakeVersion,
    String sourceJavaTextPackageIntakeVersion,
    String reviewPreflightState,
    String draftTextParseState,
    String detachedSignatureParseState,
    String approvalGrantState,
    String valueImportState,
    String runtimeState,
    String siblingMutationState,
    boolean readyForDraftTextPackageAcceptance,
    boolean readyForSignedDraftTextParsing,
    boolean readyForDetachedSignatureParsing,
    boolean readyForApprovalGrant,
    boolean readyForOperatorValueSubmission,
    boolean readyForEvidenceImport,
    boolean readyForRuntimePayload,
    boolean siblingMutationAllowed,
    String endpoint,
    String profile,
    int criterionCount,
    int passedCriterionCount,
    int rejectionControlCount,
    int passedRejectionControlCount,
    int gateCount,
    List<ReviewCriterion> criteria,
    List<RejectionControl> rejectionControls,
    List<ReviewGate> gates,
    List<String> checks,
    String status) {
  public record ReviewCriterion(
      String code,
      String versionRange,
      String reviewCriterion,
      String reviewQuestion,
      String materialRejectionControl,
      String sourceEndpoint,
      String status) {}

  public record RejectionControl(
      String code,
      String category,
      String control,
      String rejectionCode,
      String enforcement,
      String status) {}

  public record ReviewGate(String code, String category, String gate, String enforcement) {}
}
