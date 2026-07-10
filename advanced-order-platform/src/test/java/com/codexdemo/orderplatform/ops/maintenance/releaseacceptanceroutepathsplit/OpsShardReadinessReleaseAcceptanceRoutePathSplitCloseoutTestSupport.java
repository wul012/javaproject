package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

public final class OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutTestSupport {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutTestSupport() {}

  public static OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService service() {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService(
        OpsShardReadinessReleaseAcceptanceRoutePathSplitTestSupport.service());
  }

  public static OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse closeout() {
    return service().closeout();
  }
}
