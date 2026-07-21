package com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualitygate;

final class WalkthroughTestData {

  private WalkthroughTestData() {}

  static OpsShardReadinessCodeWalkthroughQualityGateRegistryService service() {
    return new OpsShardReadinessCodeWalkthroughQualityGateRegistryService();
  }

  static OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse registry() {
    return service().registry();
  }
}
