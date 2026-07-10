package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import java.util.List;

final
class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageLineageRenderer {

  private
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageLineageRenderer() {}

  static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                      .VersionLineage>
              lineage) {
    return OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRendererSupport
        .section(
            "Version Lineage",
            lineage.stream()
                .map(
                    item -> "- " + item.stage() + " " + item.version() + " status=" + item.status())
                .toList());
  }
}
