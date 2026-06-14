package com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualityaudit;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessCodeWalkthroughQualityAuditRegistryRenderer {

  private OpsShardReadinessCodeWalkthroughQualityAuditRegistryRenderer() {}

  static List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.MarkdownSection> render(
      List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.BatchAssessment>
          batchAssessments,
      List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.VersionAudit> versionAudits,
      List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.RubricScore> rubricScores,
      List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.ReviewFinding>
          reviewFindings,
      List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.BoundaryAudit>
          boundaryAudits,
      List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.VerificationStep>
          verificationSteps) {
    List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.MarkdownSection> sections =
        new ArrayList<>();
    sections.add(batchAssessmentSection(batchAssessments));
    sections.add(versionAuditSection(versionAudits));
    sections.add(rubricScoreSection(rubricScores));
    sections.add(reviewFindingSection(reviewFindings));
    sections.add(boundaryAuditSection(boundaryAudits));
    sections.add(verificationStepSection(verificationSteps));
    return List.copyOf(sections);
  }

  private static OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.MarkdownSection
      batchAssessmentSection(
          List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.BatchAssessment>
              batchAssessments) {
    List<String> lines = new ArrayList<>();
    lines.add("batch-assessment-count=" + batchAssessments.size());
    batchAssessments.forEach(
        batch ->
            lines.add(
                batch.batch()
                    + " | range="
                    + batch.versionRange()
                    + " | version-count="
                    + batch.versionCount()
                    + " | standard="
                    + batch.standardWalkthroughs()
                    + " | medium="
                    + batch.mediumGranularity()
                    + " | status="
                    + batch.status()
                    + " | "
                    + batch.assessment()));
    return section("Batch Assessments", lines);
  }

  private static OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.MarkdownSection
      versionAuditSection(
          List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.VersionAudit>
              versionAudits) {
    List<String> lines = new ArrayList<>();
    lines.add("version-audit-count=" + versionAudits.size());
    versionAudits.forEach(
        audit ->
            lines.add(
                audit.javaVersion()
                    + " | "
                    + audit.tag()
                    + " | surfaces="
                    + audit.implementationSurfaceCount()
                    + " | evidence-points="
                    + audit.explanationEvidencePoints()
                    + " | tests="
                    + audit.namedTestCount()
                    + " | medium="
                    + audit.mediumGranularity()
                    + " | status="
                    + audit.status()
                    + " | "
                    + audit.scope()));
    return section("Version Audits", lines);
  }

  private static OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.MarkdownSection
      rubricScoreSection(
          List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.RubricScore>
              rubricScores) {
    List<String> lines = new ArrayList<>();
    lines.add("rubric-score-count=" + rubricScores.size());
    rubricScores.forEach(
        score ->
            lines.add(
                score.section()
                    + " | required="
                    + score.requiredEvidencePoints()
                    + " | observed="
                    + score.observedEvidencePoints()
                    + " | passed="
                    + score.passed()
                    + " | "
                    + score.rationale()));
    return section("Rubric Scores", lines);
  }

  private static OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.MarkdownSection
      reviewFindingSection(
          List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.ReviewFinding>
              reviewFindings) {
    List<String> lines = new ArrayList<>();
    lines.add("review-finding-count=" + reviewFindings.size());
    reviewFindings.forEach(
        finding ->
            lines.add(
                finding.code()
                    + " | severity="
                    + finding.severity()
                    + " | blocking="
                    + finding.blocking()
                    + " | finding="
                    + finding.finding()
                    + " | action="
                    + finding.action()));
    return section("Review Findings", lines);
  }

  private static OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.MarkdownSection
      boundaryAuditSection(
          List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.BoundaryAudit>
              boundaryAudits) {
    List<String> lines = new ArrayList<>();
    lines.add("boundary-audit-count=" + boundaryAudits.size());
    boundaryAudits.forEach(
        boundary ->
            lines.add(
                boundary.code()
                    + " | forbidden="
                    + boundary.forbiddenAction()
                    + " | allowed="
                    + boundary.allowed()
                    + " | evidence="
                    + boundary.evidence()));
    return section("Boundary Audits", lines);
  }

  private static OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.MarkdownSection
      verificationStepSection(
          List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.VerificationStep>
              verificationSteps) {
    List<String> lines = new ArrayList<>();
    lines.add("verification-step-count=" + verificationSteps.size());
    verificationSteps.forEach(
        step ->
            lines.add(
                step.name()
                    + " | required="
                    + step.required()
                    + " | command="
                    + step.commandOrClass()
                    + " | scope="
                    + step.scope()));
    return section("Verification Steps", lines);
  }

  private static OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.MarkdownSection
      section(String heading, List<String> lines) {
    return new OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.MarkdownSection(
        heading, List.copyOf(lines));
  }
}
