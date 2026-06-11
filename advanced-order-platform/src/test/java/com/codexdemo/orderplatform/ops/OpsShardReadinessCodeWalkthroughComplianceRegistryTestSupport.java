package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessCodeWalkthroughComplianceRegistryTestSupport {

    private OpsShardReadinessCodeWalkthroughComplianceRegistryTestSupport() {
    }

    static OpsShardReadinessCodeWalkthroughComplianceRegistryService service() {
        return new OpsShardReadinessCodeWalkthroughComplianceRegistryService();
    }

    static OpsShardReadinessCodeWalkthroughComplianceRegistryResponse registry() {
        return service().registry();
    }
}
