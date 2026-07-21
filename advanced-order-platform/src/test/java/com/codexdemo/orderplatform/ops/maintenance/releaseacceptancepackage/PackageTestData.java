package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.SustainmentTestData;

public final class PackageTestData {

  private PackageTestData() {}

  public static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageService
      service() {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageService(
        SustainmentTestData.service());
  }

  public static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
      registry() {
    return service().registry();
  }
}
