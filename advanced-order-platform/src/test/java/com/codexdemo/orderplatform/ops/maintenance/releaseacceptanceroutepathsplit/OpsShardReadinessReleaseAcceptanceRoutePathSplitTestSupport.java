package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff.OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffTestSupport;

public final class OpsShardReadinessReleaseAcceptanceRoutePathSplitTestSupport {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitTestSupport() {}

  public static OpsShardReadinessReleaseAcceptanceRoutePathSplitService service() {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitService(
        OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffTestSupport.service());
  }

  public static OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse registry() {
    return service().registry();
  }
}
