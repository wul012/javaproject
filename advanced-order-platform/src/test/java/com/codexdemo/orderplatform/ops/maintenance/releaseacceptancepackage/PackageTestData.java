package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentTestSupport;

public final class PackageTestData {

  private PackageTestData() {}

  public static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageService
      service() {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageService(
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentTestSupport.service());
  }

  public static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
      registry() {
    return service().registry();
  }
}
