package com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth;

final class WalkthroughTestData {

  private WalkthroughTestData() {}

  static OpsShardReadinessCodeWalkthroughDepthRegistryService service() {
    return new OpsShardReadinessCodeWalkthroughDepthRegistryService();
  }

  static OpsShardReadinessCodeWalkthroughDepthRegistryResponse registry() {
    return service().registry();
  }
}
