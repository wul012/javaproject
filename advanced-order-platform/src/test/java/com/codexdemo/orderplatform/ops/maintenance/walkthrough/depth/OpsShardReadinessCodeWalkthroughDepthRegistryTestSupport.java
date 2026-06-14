package com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth;

final class OpsShardReadinessCodeWalkthroughDepthRegistryTestSupport {

  private OpsShardReadinessCodeWalkthroughDepthRegistryTestSupport() {}

  static OpsShardReadinessCodeWalkthroughDepthRegistryService service() {
    return new OpsShardReadinessCodeWalkthroughDepthRegistryService();
  }

  static OpsShardReadinessCodeWalkthroughDepthRegistryResponse registry() {
    return service().registry();
  }
}
