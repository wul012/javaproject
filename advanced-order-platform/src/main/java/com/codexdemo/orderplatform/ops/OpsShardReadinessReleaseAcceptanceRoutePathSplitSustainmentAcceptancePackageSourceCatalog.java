package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse;
import java.util.List;

final
class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageSourceCatalog {

  private
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageSourceCatalog() {}

  static List<
          OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
              .SourceSnapshot>
      snapshots(OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source) {
    return List.of(
        new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
            .SourceSnapshot(
            "release-acceptance-route-path-split-sustainment",
            source.version(),
            source.endpoint(),
            source.status(),
            source.profile()));
  }
}
