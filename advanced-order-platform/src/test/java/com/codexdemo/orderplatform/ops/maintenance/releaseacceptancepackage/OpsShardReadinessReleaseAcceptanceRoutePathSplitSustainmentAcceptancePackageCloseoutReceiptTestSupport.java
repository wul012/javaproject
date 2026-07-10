package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

public final
class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptTestSupport {

  private
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptTestSupport() {}

  public static
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptService
      service() {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptService(
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageTestSupport
            .service());
  }

  public static
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptResponse
      receipt() {
    return service().receipt();
  }
}
