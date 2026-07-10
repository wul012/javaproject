package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import java.util.List;

final
class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRendererSupport {

  private
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRendererSupport() {}

  static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
          .MarkdownSection
      section(String heading, List<String> lines) {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
        .MarkdownSection(heading, List.copyOf(lines));
  }
}
