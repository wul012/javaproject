package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentTestSupport;

final
class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageTestSupport {

  private
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageTestSupport() {}

  static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageService
      service() {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageService(
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentTestSupport.service());
  }

  static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
      registry() {
    return service().registry();
  }
}
