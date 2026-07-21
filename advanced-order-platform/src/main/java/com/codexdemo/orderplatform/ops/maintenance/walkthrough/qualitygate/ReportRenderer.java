package com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualitygate;

import static com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownSections.counted;

import com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualitygate.OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.BoundaryRule;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualitygate.OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.EvidenceAnchor;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualitygate.OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.ExplanationRubric;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualitygate.OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualitygate.OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.ReviewChecklist;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualitygate.OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.VersionRule;
import java.util.List;

final class ReportRenderer {

  private ReportRenderer() {}

  static List<MarkdownSection> render(
      List<VersionRule> versionRules,
      List<ExplanationRubric> explanationRubrics,
      List<EvidenceAnchor> evidenceAnchors,
      List<ReviewChecklist> reviewChecklists,
      List<BoundaryRule> boundaryRules) {
    return List.of(
        counted(
            "Version Granularity Rules",
            "version-rule-count",
            versionRules,
            ReportRenderer::versionLine,
            MarkdownSection::new),
        counted(
            "Explanation Rubric",
            "explanation-rubric-count",
            explanationRubrics,
            ReportRenderer::rubricLine,
            MarkdownSection::new),
        counted(
            "Evidence Anchors",
            "evidence-anchor-count",
            evidenceAnchors,
            ReportRenderer::anchorLine,
            MarkdownSection::new),
        counted(
            "Review Checklist",
            "review-checklist-count",
            reviewChecklists,
            ReportRenderer::checklistLine,
            MarkdownSection::new),
        counted(
            "Runtime Boundary Rules",
            "boundary-rule-count",
            boundaryRules,
            ReportRenderer::boundaryLine,
            MarkdownSection::new));
  }

  private static String versionLine(VersionRule rule) {
    return rule.code()
        + " | required="
        + rule.required()
        + " | minimum="
        + rule.minimumScope()
        + " | explanation="
        + rule.explanationRequirement()
        + " | split="
        + rule.splitGuidance();
  }

  private static String rubricLine(ExplanationRubric rubric) {
    return rubric.section()
        + " | minimum-evidence-points="
        + rubric.minimumEvidencePoints()
        + " | must="
        + rubric.mustExplain()
        + " | standout="
        + rubric.standoutSignal();
  }

  private static String anchorLine(EvidenceAnchor anchor) {
    return anchor.anchor()
        + " | owner="
        + anchor.owner()
        + " | runtime-free="
        + anchor.runtimeFree()
        + " | source="
        + anchor.source()
        + " | proof="
        + anchor.requiredProof();
  }

  private static String checklistLine(ReviewChecklist checklist) {
    return checklist.item()
        + " | blocks-release="
        + checklist.blocksRelease()
        + " | question="
        + checklist.reviewerQuestion()
        + " | blocker="
        + checklist.releaseBlocker();
  }

  private static String boundaryLine(BoundaryRule rule) {
    return rule.code()
        + " | owner="
        + rule.owner()
        + " | forbidden="
        + rule.forbiddenAction()
        + " | allowed="
        + rule.allowed()
        + " | "
        + rule.rationale();
  }
}
