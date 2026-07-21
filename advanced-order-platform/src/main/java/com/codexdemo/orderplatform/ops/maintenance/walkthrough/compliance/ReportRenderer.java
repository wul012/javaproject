package com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance;

import static com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownSections.counted;

import com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance.OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.ArchiveRange;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance.OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.BoundaryRule;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance.OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.DocumentationRule;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance.OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance.OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.RequiredHeading;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance.OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.TestCoverage;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance.OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.VersionEntry;
import java.util.List;

final class ReportRenderer {

  private ReportRenderer() {}

  static List<MarkdownSection> render(
      List<VersionEntry> versions,
      List<RequiredHeading> requiredHeadings,
      List<ArchiveRange> archiveRanges,
      List<DocumentationRule> documentationRules,
      List<BoundaryRule> boundaryRules,
      List<TestCoverage> testCoverages) {
    return List.of(
        counted(
            "Version Lineage",
            "version-count",
            versions,
            ReportRenderer::versionLine,
            MarkdownSection::new),
        counted(
            "Required Walkthrough Headings",
            "required-heading-count",
            requiredHeadings,
            ReportRenderer::headingLine,
            MarkdownSection::new),
        counted(
            "Archive Ranges",
            "archive-range-count",
            archiveRanges,
            ReportRenderer::archiveLine,
            MarkdownSection::new),
        counted(
            "Documentation Rules",
            "documentation-rule-count",
            documentationRules,
            ReportRenderer::documentationLine,
            MarkdownSection::new),
        counted(
            "Runtime Boundary Rules",
            "boundary-rule-count",
            boundaryRules,
            ReportRenderer::boundaryLine,
            MarkdownSection::new),
        counted(
            "Test Coverage",
            "test-coverage-count",
            testCoverages,
            ReportRenderer::testLine,
            MarkdownSection::new));
  }

  private static String versionLine(VersionEntry version) {
    return version.javaVersion()
        + " | "
        + version.tag()
        + " | "
        + version.focus()
        + " | "
        + version.status();
  }

  private static String headingLine(RequiredHeading heading) {
    return heading.order() + ". " + heading.heading() + " -> " + heading.intent();
  }

  private static String archiveLine(ArchiveRange range) {
    return range.name()
        + " | "
        + range.directory()
        + " | "
        + range.versionRange()
        + " | "
        + range.retentionRule();
  }

  private static String documentationLine(DocumentationRule rule) {
    return rule.code()
        + " | owner="
        + rule.owner()
        + " | required="
        + rule.required()
        + " | "
        + rule.rule();
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

  private static String testLine(TestCoverage test) {
    return test.testClass()
        + " | "
        + test.scope()
        + " | blocks-release="
        + test.blocksRelease()
        + " | "
        + test.assertion();
  }
}
