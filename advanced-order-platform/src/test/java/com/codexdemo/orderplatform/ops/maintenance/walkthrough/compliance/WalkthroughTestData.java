package com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance;

final class WalkthroughTestData {

  private WalkthroughTestData() {}

  static OpsShardReadinessCodeWalkthroughComplianceRegistryService service() {
    return new OpsShardReadinessCodeWalkthroughComplianceRegistryService();
  }

  static OpsShardReadinessCodeWalkthroughComplianceRegistryResponse registry() {
    return service().registry();
  }
}
