package com.codexdemo.orderplatform.ops.maintenance.screenshotexplanationarchive;

import java.util.List;

final class OpsShardReadinessScreenshotExplanationArchiveNamingRuleCatalog {

  private OpsShardReadinessScreenshotExplanationArchiveNamingRuleCatalog() {}

  static List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.NamingRule>
      namingRules() {
    return List.of(
        rule(
            "range-before-version",
            "f/v<start>-v<end>/<version>/...",
            "keep the canonical screenshot/explanation root small by grouping evidence into version bands"),
        rule(
            "separate-images-and-explanations",
            "f/v<range>/<version>/images/*.png and explanations/summary.md",
            "screenshots and explanations stay adjacent but not mixed in one directory"),
        rule(
            "no-root-dumping",
            "never write screenshots or explanation markdown directly under d, d_runtime_screenshot_archive_next, or f",
            "root folders stay navigational indexes only"),
        rule(
            "old-d-root-read-only",
            "d/<version>/pictures and d/<version>/explanations stay historical",
            "avoid moving old evidence unless explicitly requested"),
        rule(
            "code-walkthrough-separate",
            "code walkthroughs remain under code walkthrough archives, not screenshot archives",
            "runtime screenshot explanations and code walkthroughs have different audiences"),
        rule(
            "readme-per-segment",
            "each version-range segment carries a README with range, purpose, and boundary",
            "future reviewers can find the right archive without scanning every version folder"));
  }

  private static OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.NamingRule rule(
      String code, String pattern, String rationale) {
    return new OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.NamingRule(
        code, pattern, rationale, true);
  }
}
