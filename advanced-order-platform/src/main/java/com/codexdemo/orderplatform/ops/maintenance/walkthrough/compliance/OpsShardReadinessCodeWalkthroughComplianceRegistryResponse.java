package com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance;

import java.util.List;

public record OpsShardReadinessCodeWalkthroughComplianceRegistryResponse(
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
    String archiveDirectory,
    String registryState,
    int versionCount,
    int requiredHeadingCount,
    int archiveRangeCount,
    int documentationRuleCount,
    int boundaryRuleCount,
    int deniedBoundaryRuleCount,
    int testCoverageCount,
    int markdownSectionCount,
    List<VersionEntry> versions,
    List<RequiredHeading> requiredHeadings,
    List<ArchiveRange> archiveRanges,
    List<DocumentationRule> documentationRules,
    List<BoundaryRule> boundaryRules,
    List<TestCoverage> testCoverages,
    List<MarkdownSection> markdownSections,
    List<String> checks,
    String status) {

  public OpsShardReadinessCodeWalkthroughComplianceRegistryResponse {
    versions = List.copyOf(versions);
    requiredHeadings = List.copyOf(requiredHeadings);
    archiveRanges = List.copyOf(archiveRanges);
    documentationRules = List.copyOf(documentationRules);
    boundaryRules = List.copyOf(boundaryRules);
    testCoverages = List.copyOf(testCoverages);
    markdownSections =
        markdownSections.stream()
            .map(section -> new MarkdownSection(section.heading(), section.lines()))
            .toList();
    markdownSections = List.copyOf(markdownSections);
    checks = List.copyOf(checks);
  }

  public record VersionEntry(
      String javaVersion, String tag, String focus, String evidenceFile, String status) {}

  public record RequiredHeading(int order, String heading, String intent) {}

  public record ArchiveRange(
      String name, String directory, String versionRange, String retentionRule) {}

  public record DocumentationRule(String code, String owner, String rule, boolean required) {}

  public record BoundaryRule(
      String code, String owner, String forbiddenAction, boolean allowed, String rationale) {}

  public record TestCoverage(
      String testClass, String scope, String assertion, boolean blocksRelease) {}

  public record MarkdownSection(String heading, List<String> lines) {
    public MarkdownSection {
      lines = List.copyOf(lines);
    }
  }
}
