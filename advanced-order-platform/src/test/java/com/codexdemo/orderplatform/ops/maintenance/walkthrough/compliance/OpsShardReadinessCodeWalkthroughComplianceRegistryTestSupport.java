package com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance;

final class OpsShardReadinessCodeWalkthroughComplianceRegistryTestSupport {

  private OpsShardReadinessCodeWalkthroughComplianceRegistryTestSupport() {}

  static OpsShardReadinessCodeWalkthroughComplianceRegistryService service() {
    return new OpsShardReadinessCodeWalkthroughComplianceRegistryService();
  }

  static OpsShardReadinessCodeWalkthroughComplianceRegistryResponse registry() {
    return service().registry();
  }
}
