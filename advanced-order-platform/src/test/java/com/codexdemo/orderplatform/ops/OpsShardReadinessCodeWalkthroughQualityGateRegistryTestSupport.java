package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessCodeWalkthroughQualityGateRegistryTestSupport {

    private OpsShardReadinessCodeWalkthroughQualityGateRegistryTestSupport() {
    }

    static OpsShardReadinessCodeWalkthroughQualityGateRegistryService service() {
        return new OpsShardReadinessCodeWalkthroughQualityGateRegistryService();
    }

    static OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse registry() {
        return service().registry();
    }
}
