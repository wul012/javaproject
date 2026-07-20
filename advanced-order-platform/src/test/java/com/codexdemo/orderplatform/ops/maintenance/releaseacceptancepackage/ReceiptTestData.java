package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

public final class ReceiptTestData {

  private ReceiptTestData() {}

  public static
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptService
      service() {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptService(
        PackageTestData.service());
  }

  public static
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptResponse
      receipt() {
    return service().receipt();
  }
}
