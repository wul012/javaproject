package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutTestSupport;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentTestSupport {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentTestSupport() {}

  static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService service() {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService(
        OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutTestSupport.service());
  }

  static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse registry() {
    return service().registry();
  }
}
