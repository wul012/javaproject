package com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview;

import java.util.List;

public record OpsShardReadinessComparedPackageReviewResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean readyForComparedPackageReviewHandoff,
    String sourcePlan,
    String sourceNodeEvidenceIntakeVersion,
    String sourceJavaEvidenceIntakeVersion,
    String reviewContractState,
    String evidenceAcceptanceState,
    String reviewDecisionState,
    String approvalGrantState,
    String runtimePayloadState,
    String siblingMutationState,
    boolean readyForEvidenceAcceptance,
    boolean readyForReviewDecision,
    boolean readyForApprovalGrant,
    boolean readyForRuntimePayload,
    boolean siblingMutationAllowed,
    String endpoint,
    String profile,
    int reviewSlotCount,
    int passedReviewSlotCount,
    int guardCount,
    int passedGuardCount,
    int reviewerGroupCount,
    List<ReviewSlot> reviewSlots,
    List<ReviewGuard> guards,
    List<ReviewerGroup> reviewerGroups,
    List<String> checks,
    String status) {
  public record ReviewSlot(
      String code,
      String sourceVersion,
      String reviewArea,
      String expectedEvidence,
      String reviewerQuestion,
      String missingEvidenceGuard,
      String sourceEndpoint,
      String status) {}

  public record ReviewGuard(
      String code,
      String category,
      String guard,
      String rejectionCode,
      String enforcement,
      String status) {}

  public record ReviewerGroup(
      String code, String owner, String responsibility, String blockedAction, String status) {}
}
