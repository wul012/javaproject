package com.codexdemo.orderplatform.ops.maintenance.screenshotexplanationarchive;

import static com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownSections.counted;

import com.codexdemo.orderplatform.ops.maintenance.screenshotexplanationarchive.OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.ArchiveSegmentPlan;
import com.codexdemo.orderplatform.ops.maintenance.screenshotexplanationarchive.OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.BoundaryRule;
import com.codexdemo.orderplatform.ops.maintenance.screenshotexplanationarchive.OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.CurrentArchiveAssessment;
import com.codexdemo.orderplatform.ops.maintenance.screenshotexplanationarchive.OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.screenshotexplanationarchive.OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.NamingRule;
import com.codexdemo.orderplatform.ops.maintenance.screenshotexplanationarchive.OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.VerificationStep;
import java.util.List;

final class ReportRenderer {

  private ReportRenderer() {}

  static List<MarkdownSection> render(
      List<CurrentArchiveAssessment> currentArchiveAssessments,
      List<ArchiveSegmentPlan> segmentPlans,
      List<NamingRule> namingRules,
      List<BoundaryRule> boundaryRules,
      List<VerificationStep> verificationSteps) {
    return List.of(
        counted(
            "Current Archive Assessments",
            "current-archive-assessment-count",
            currentArchiveAssessments,
            ReportRenderer::assessmentLine,
            MarkdownSection::new),
        counted(
            "Archive Segment Plans",
            "segment-plan-count",
            segmentPlans,
            ReportRenderer::segmentLine,
            MarkdownSection::new),
        counted(
            "Naming Rules",
            "naming-rule-count",
            namingRules,
            ReportRenderer::namingLine,
            MarkdownSection::new),
        counted(
            "Boundary Rules",
            "boundary-rule-count",
            boundaryRules,
            ReportRenderer::boundaryLine,
            MarkdownSection::new),
        counted(
            "Verification Steps",
            "verification-step-count",
            verificationSteps,
            ReportRenderer::verificationLine,
            MarkdownSection::new));
  }

  private static String assessmentLine(CurrentArchiveAssessment assessment) {
    return assessment.root()
        + " | version-directories="
        + assessment.versionDirectoryCount()
        + " | files="
        + assessment.fileCount()
        + " | status="
        + assessment.status()
        + " | next="
        + assessment.nextAction();
  }

  private static String segmentLine(ArchiveSegmentPlan segment) {
    return segment.segment()
        + " | path="
        + segment.path()
        + " | range="
        + segment.versionRange()
        + " | active="
        + segment.active()
        + " | "
        + segment.purpose();
  }

  private static String namingLine(NamingRule rule) {
    return rule.code()
        + " | required="
        + rule.required()
        + " | pattern="
        + rule.pattern()
        + " | "
        + rule.rationale();
  }

  private static String boundaryLine(BoundaryRule rule) {
    return rule.code()
        + " | forbidden="
        + rule.forbiddenAction()
        + " | allowed="
        + rule.allowed()
        + " | "
        + rule.rationale();
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
