package com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualityaudit;

final class WalkthroughTestData {

  private WalkthroughTestData() {}

  static OpsShardReadinessCodeWalkthroughQualityAuditRegistryService service() {
    return new OpsShardReadinessCodeWalkthroughQualityAuditRegistryService();
  }

  static OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse registry() {
    return service().registry();
  }
}
