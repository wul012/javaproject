package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

public final class ArchiveIndexTestData {

  private ArchiveIndexTestData() {}

  public static
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexService
      service() {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexService(
        ReceiptTestData.service());
  }

  public static
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
      index() {
    return service().index();
  }
}
