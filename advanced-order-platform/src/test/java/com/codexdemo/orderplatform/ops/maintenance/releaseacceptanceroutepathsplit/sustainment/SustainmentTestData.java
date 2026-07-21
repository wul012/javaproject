package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.CloseoutTestData;

public final class SustainmentTestData {

  private SustainmentTestData() {}

  public static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService service() {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService(
        CloseoutTestData.service());
  }

  public static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse registry() {
    return service().registry();
  }
}
