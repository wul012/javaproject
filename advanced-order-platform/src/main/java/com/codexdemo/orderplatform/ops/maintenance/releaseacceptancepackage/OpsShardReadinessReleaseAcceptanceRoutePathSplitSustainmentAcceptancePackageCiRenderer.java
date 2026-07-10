package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCiRenderer {

  private
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCiRenderer() {}

  static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                      .CiEvidence>
              ciEvidence) {
    return OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRendererSupport
        .section(
            "CI Evidence",
            ciEvidence.stream()
                .map(
                    item ->
                        "- "
                            + item.gate()
                            + " result="
                            + item.result()
                            + " passed="
                            + item.passed())
                .toList());
  }
}
