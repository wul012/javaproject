package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentSourceCatalog {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentSourceCatalog() {}

  static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.SourceSnapshot>
      snapshots(OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse source) {
    return List.of(
        new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.SourceSnapshot(
            "release-acceptance-route-path-split-closeout",
            source.version(),
            source.endpoint(),
            source.status(),
            "route-path-split-sustainment"));
  }
}
