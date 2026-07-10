package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import java.util.List;

final
class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageScorecardRenderer {

  private
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageScorecardRenderer() {}

  static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                      .ScorecardEntry>
              scorecard) {
    return OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRendererSupport
        .section(
            "Acceptance Scorecard",
            scorecard.stream()
                .map(
                    entry ->
                        "- "
                            + entry.category()
                            + " passed="
                            + entry.passed()
                            + " detail="
                            + entry.detail())
                .toList());
  }
}
