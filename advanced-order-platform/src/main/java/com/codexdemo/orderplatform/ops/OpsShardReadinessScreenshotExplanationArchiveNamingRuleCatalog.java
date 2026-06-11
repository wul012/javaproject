package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessScreenshotExplanationArchiveNamingRuleCatalog {

    private OpsShardReadinessScreenshotExplanationArchiveNamingRuleCatalog() {
    }

    static List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.NamingRule>
            namingRules() {
        return List.of(
                rule(
                        "range-before-version",
                        "d_runtime_screenshot_archive_next/v<start>-v<end>/<version>/...",
                        "keep the root small by grouping later evidence into version bands"
                ),
                rule(
                        "separate-images-and-explanations",
                        "d_runtime_screenshot_archive_next/v<range>/<version>/images/*.png and explanations/summary.md",
                        "screenshots and explanations stay adjacent but not mixed in one directory"
                ),
                rule(
                        "no-root-dumping",
                        "never write screenshots or explanation markdown directly under d or the next root",
                        "root folders stay navigational indexes only"
                ),
                rule(
                        "old-d-root-read-only",
                        "d/<version>/pictures and d/<version>/explanations stay historical",
                        "avoid moving old evidence unless explicitly requested"
                ),
                rule(
                        "code-walkthrough-separate",
                        "code walkthroughs remain under code walkthrough archives, not screenshot archives",
                        "runtime screenshot explanations and code walkthroughs have different audiences"
                ),
                rule(
                        "readme-per-segment",
                        "each version-range segment carries a README with range, purpose, and boundary",
                        "future reviewers can find the right archive without scanning every version folder"
                )
        );
    }

    private static OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.NamingRule
            rule(String code, String pattern, String rationale) {
        return new OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.NamingRule(
                code,
                pattern,
                rationale,
                true
        );
    }
}
