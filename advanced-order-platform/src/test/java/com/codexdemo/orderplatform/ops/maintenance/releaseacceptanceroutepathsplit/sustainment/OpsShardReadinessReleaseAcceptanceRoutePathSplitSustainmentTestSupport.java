package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutTestSupport;

public final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentTestSupport {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentTestSupport() {}

  public static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService service() {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService(
        OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutTestSupport.service());
  }

  public static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse registry() {
    return service().registry();
  }
}
