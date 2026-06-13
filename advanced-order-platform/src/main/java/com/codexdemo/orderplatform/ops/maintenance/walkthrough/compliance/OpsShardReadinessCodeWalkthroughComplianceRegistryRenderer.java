package com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessCodeWalkthroughComplianceRegistryRenderer {

  private OpsShardReadinessCodeWalkthroughComplianceRegistryRenderer() {}

  static List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.MarkdownSection> render(
      List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.VersionEntry> versions,
      List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.RequiredHeading>
          requiredHeadings,
      List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.ArchiveRange> archiveRanges,
      List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.DocumentationRule>
          documentationRules,
      List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.BoundaryRule> boundaryRules,
      List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.TestCoverage> testCoverages) {
    List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.MarkdownSection> sections =
        new ArrayList<>();
    sections.add(versionLineage(versions));
    sections.add(requiredHeadingSection(requiredHeadings));
    sections.add(archiveRangeSection(archiveRanges));
    sections.add(documentationRuleSection(documentationRules));
    sections.add(boundaryRuleSection(boundaryRules));
    sections.add(testCoverageSection(testCoverages));
    return List.copyOf(sections);
  }

  private static OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.MarkdownSection
      versionLineage(
          List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.VersionEntry> versions) {
    List<String> lines = new ArrayList<>();
    lines.add("version-count=" + versions.size());
    versions.forEach(
        version ->
            lines.add(
                version.javaVersion()
                    + " | "
                    + version.tag()
                    + " | "
                    + version.focus()
                    + " | "
                    + version.status()));
    return section("Version Lineage", lines);
  }

  private static OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.MarkdownSection
      requiredHeadingSection(
          List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.RequiredHeading>
              requiredHeadings) {
    List<String> lines = new ArrayList<>();
    lines.add("required-heading-count=" + requiredHeadings.size());
    requiredHeadings.forEach(
        heading ->
            lines.add(heading.order() + ". " + heading.heading() + " -> " + heading.intent()));
    return section("Required Walkthrough Headings", lines);
  }

  private static OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.MarkdownSection
      archiveRangeSection(
          List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.ArchiveRange>
              archiveRanges) {
    List<String> lines = new ArrayList<>();
    lines.add("archive-range-count=" + archiveRanges.size());
    archiveRanges.forEach(
        range ->
            lines.add(
                range.name()
                    + " | "
                    + range.directory()
                    + " | "
                    + range.versionRange()
                    + " | "
                    + range.retentionRule()));
    return section("Archive Ranges", lines);
  }

  private static OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.MarkdownSection
      documentationRuleSection(
          List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.DocumentationRule>
              documentationRules) {
    List<String> lines = new ArrayList<>();
    lines.add("documentation-rule-count=" + documentationRules.size());
    documentationRules.forEach(
        rule ->
            lines.add(
                rule.code()
                    + " | owner="
                    + rule.owner()
                    + " | required="
                    + rule.required()
                    + " | "
                    + rule.rule()));
    return section("Documentation Rules", lines);
  }

  private static OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.MarkdownSection
      boundaryRuleSection(
          List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.BoundaryRule>
              boundaryRules) {
    List<String> lines = new ArrayList<>();
    lines.add("boundary-rule-count=" + boundaryRules.size());
    boundaryRules.forEach(
        rule ->
            lines.add(
                rule.code()
                    + " | owner="
                    + rule.owner()
                    + " | forbidden="
                    + rule.forbiddenAction()
                    + " | allowed="
                    + rule.allowed()
                    + " | "
                    + rule.rationale()));
    return section("Runtime Boundary Rules", lines);
  }

  private static OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.MarkdownSection
      testCoverageSection(
          List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.TestCoverage>
              testCoverages) {
    List<String> lines = new ArrayList<>();
    lines.add("test-coverage-count=" + testCoverages.size());
    testCoverages.forEach(
        test ->
            lines.add(
                test.testClass()
                    + " | "
                    + test.scope()
                    + " | blocks-release="
                    + test.blocksRelease()
                    + " | "
                    + test.assertion()));
    return section("Test Coverage", lines);
  }

  private static OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.MarkdownSection section(
      String heading, List<String> lines) {
    return new OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.MarkdownSection(
        heading, List.copyOf(lines));
  }
}
