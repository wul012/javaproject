package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

public final class CloseoutTestData {

  private CloseoutTestData() {}

  public static OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService service() {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService(
        SplitTestData.service());
  }

  public static OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse closeout() {
    return service().closeout();
  }
}
