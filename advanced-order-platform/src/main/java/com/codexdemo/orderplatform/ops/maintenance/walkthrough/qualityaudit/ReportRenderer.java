package com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualityaudit;

import static com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownSections.counted;

import com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualityaudit.OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.BatchAssessment;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualityaudit.OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.BoundaryAudit;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualityaudit.OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualityaudit.OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.ReviewFinding;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualityaudit.OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.RubricScore;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualityaudit.OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.VerificationStep;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualityaudit.OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.VersionAudit;
import java.util.List;

final class ReportRenderer {

  private ReportRenderer() {}

  static List<MarkdownSection> render(
      List<BatchAssessment> batchAssessments,
      List<VersionAudit> versionAudits,
      List<RubricScore> rubricScores,
      List<ReviewFinding> reviewFindings,
      List<BoundaryAudit> boundaryAudits,
      List<VerificationStep> verificationSteps) {
    return List.of(
        counted(
            "Batch Assessments",
            "batch-assessment-count",
            batchAssessments,
            ReportRenderer::batchLine,
            MarkdownSection::new),
        counted(
            "Version Audits",
            "version-audit-count",
            versionAudits,
            ReportRenderer::versionLine,
            MarkdownSection::new),
        counted(
            "Rubric Scores",
            "rubric-score-count",
            rubricScores,
            ReportRenderer::scoreLine,
            MarkdownSection::new),
        counted(
            "Review Findings",
            "review-finding-count",
            reviewFindings,
            ReportRenderer::findingLine,
            MarkdownSection::new),
        counted(
            "Boundary Audits",
            "boundary-audit-count",
            boundaryAudits,
            ReportRenderer::boundaryLine,
            MarkdownSection::new),
        counted(
            "Verification Steps",
            "verification-step-count",
            verificationSteps,
            ReportRenderer::verificationLine,
            MarkdownSection::new));
  }

  private static String batchLine(BatchAssessment batch) {
    return batch.batch()
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
        + batch.assessment();
  }

  private static String versionLine(VersionAudit audit) {
    return audit.javaVersion()
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
        + audit.scope();
  }

  private static String scoreLine(RubricScore score) {
    return score.section()
        + " | required="
        + score.requiredEvidencePoints()
        + " | observed="
        + score.observedEvidencePoints()
        + " | passed="
        + score.passed()
        + " | "
        + score.rationale();
  }

  private static String findingLine(ReviewFinding finding) {
    return finding.code()
        + " | severity="
        + finding.severity()
        + " | blocking="
        + finding.blocking()
        + " | finding="
        + finding.finding()
        + " | action="
        + finding.action();
  }

  private static String boundaryLine(BoundaryAudit boundary) {
    return boundary.code()
        + " | forbidden="
        + boundary.forbiddenAction()
        + " | allowed="
        + boundary.allowed()
        + " | evidence="
        + boundary.evidence();
  }

  private static String verificationLine(VerificationStep step) {
    return step.name()
        + " | required="
        + step.required()
        + " | command="
        + step.commandOrClass()
        + " | scope="
        + step.scope();
  }
}
