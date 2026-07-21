package com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth;

import static com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownSections.mapped;

import com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth.OpsShardReadinessCodeWalkthroughDepthRegistryResponse.BoundaryRule;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth.OpsShardReadinessCodeWalkthroughDepthRegistryResponse.DepthRule;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth.OpsShardReadinessCodeWalkthroughDepthRegistryResponse.EvidenceRule;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth.OpsShardReadinessCodeWalkthroughDepthRegistryResponse.LanguageRule;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth.OpsShardReadinessCodeWalkthroughDepthRegistryResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth.OpsShardReadinessCodeWalkthroughDepthRegistryResponse.VerificationStep;
import java.util.List;

final class ReportRenderer {

  private ReportRenderer() {}

  static List<MarkdownSection> render(
      List<DepthRule> depthRules,
      List<LanguageRule> languageRules,
      List<EvidenceRule> evidenceRules,
      List<BoundaryRule> boundaryRules,
      List<VerificationStep> verificationSteps) {
    return List.of(
        mapped("Depth Rules", depthRules, ReportRenderer::depthLine, MarkdownSection::new),
        mapped("Language Rules", languageRules, ReportRenderer::languageLine, MarkdownSection::new),
        mapped("Evidence Rules", evidenceRules, ReportRenderer::evidenceLine, MarkdownSection::new),
        mapped("Boundary Rules", boundaryRules, ReportRenderer::boundaryLine, MarkdownSection::new),
        mapped(
            "Verification Steps",
            verificationSteps,
            ReportRenderer::verificationLine,
            MarkdownSection::new));
  }

  private static String depthLine(DepthRule rule) {
    return "- "
        + rule.code()
        + ": minimumChineseCharacters="
        + rule.minimumChineseCharacters()
        + ", required="
        + rule.required()
        + ", requirement="
        + rule.requirement();
  }

  private static String languageLine(LanguageRule rule) {
    return "- "
        + rule.code()
        + ": required="
        + rule.required()
        + ", rejectionSignal="
        + rule.rejectionSignal()
        + ", requirement="
        + rule.requirement();
  }

  private static String evidenceLine(EvidenceRule rule) {
    return "- "
        + rule.code()
        + ": minimumMentions="
        + rule.minimumMentions()
        + ", evidence="
        + rule.requiredEvidence()
        + ", question="
        + rule.maintainerQuestion();
  }

  private static String boundaryLine(BoundaryRule rule) {
    return "- "
        + rule.code()
        + ": allowed="
        + rule.allowed()
        + ", forbiddenAction="
        + rule.forbiddenAction();
  }

  private static String verificationLine(VerificationStep step) {
    return "- "
        + step.name()
        + ": required="
        + step.required()
        + ", commandOrClass="
        + step.commandOrClass()
        + ", scope="
        + step.scope();
  }
}
