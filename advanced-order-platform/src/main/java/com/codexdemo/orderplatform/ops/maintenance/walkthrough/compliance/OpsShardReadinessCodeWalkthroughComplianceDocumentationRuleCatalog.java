package com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance;

import java.util.List;

final class OpsShardReadinessCodeWalkthroughComplianceDocumentationRuleCatalog {

  private OpsShardReadinessCodeWalkthroughComplianceDocumentationRuleCatalog() {}

  static List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.DocumentationRule>
      documentationRules() {
    return List.of(
        rule(
            "future-nine-heading-standard",
            "java-docs",
            "every walkthrough after v289 includes the nine required headings"),
        rule(
            "future-no-legacy-marker",
            "java-docs",
            "legacy-nonstandard-walkthrough is forbidden for walkthroughs after v289"),
        rule(
            "historical-nonstandard-explicit",
            "java-docs",
            "non-standard historical walkthroughs remain explicitly legacy-marked"),
        rule(
            "continuation-directory",
            "java-docs",
            "large walkthrough batches move into a clear continuation directory"),
        rule(
            "index-maintained",
            "java-docs",
            "the archive index names current ranges and the standard"),
        rule(
            "read-only-evidence-only",
            "java-ops",
            "walkthrough compliance never starts services or performs runtime writes"),
        rule("utf8-source", "java-docs", "new walkthrough files are stored as UTF-8 markdown"));
  }

  private static OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.DocumentationRule rule(
      String code, String owner, String rule) {
    return new OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.DocumentationRule(
        code, owner, rule, true);
  }
}
