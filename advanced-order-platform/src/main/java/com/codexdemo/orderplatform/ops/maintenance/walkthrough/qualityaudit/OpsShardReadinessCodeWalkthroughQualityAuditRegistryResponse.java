package com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualityaudit;

import java.util.List;

public record OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean startsJavaService,
    boolean startsMiniKvService,
    boolean readsCredentialValue,
    boolean resolvesRawEndpointUrl,
    boolean managedAuditHttpAllowed,
    String endpoint,
    String profile,
    String sourcePlan,
    String auditedBatch,
    String qualityGateRegistry,
    String registryState,
    int batchAssessmentCount,
    int versionAuditCount,
    int mediumGranularityVersionCount,
    int rubricScoreCount,
    int passedRubricScoreCount,
    int reviewFindingCount,
    int blockingReviewFindingCount,
    int boundaryAuditCount,
    int deniedBoundaryAuditCount,
    int verificationStepCount,
    int markdownSectionCount,
    List<BatchAssessment> batchAssessments,
    List<VersionAudit> versionAudits,
    List<RubricScore> rubricScores,
    List<ReviewFinding> reviewFindings,
    List<BoundaryAudit> boundaryAudits,
    List<VerificationStep> verificationSteps,
    List<MarkdownSection> markdownSections,
    List<String> checks,
    String status) {

  public record BatchAssessment(
      String batch,
      String versionRange,
      int versionCount,
      String assessment,
      boolean standardWalkthroughs,
      boolean mediumGranularity,
      String status) {}

  public record VersionAudit(
      String javaVersion,
      String tag,
      String scope,
      int implementationSurfaceCount,
      int explanationEvidencePoints,
      int namedTestCount,
      boolean mediumGranularity,
      String status) {}

  public record RubricScore(
      String section,
      int requiredEvidencePoints,
      int observedEvidencePoints,
      boolean passed,
      String rationale) {}

  public record ReviewFinding(
      String code, String severity, String finding, String action, boolean blocking) {}

  public record BoundaryAudit(
      String code, String forbiddenAction, boolean allowed, String evidence) {}

  public record VerificationStep(
      String name, String commandOrClass, String scope, boolean required) {}

  public record MarkdownSection(String heading, List<String> lines) {}
}
