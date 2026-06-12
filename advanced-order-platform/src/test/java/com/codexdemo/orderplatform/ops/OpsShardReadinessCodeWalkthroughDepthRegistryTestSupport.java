package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessCodeWalkthroughDepthRegistryTestSupport {

    private OpsShardReadinessCodeWalkthroughDepthRegistryTestSupport() {
    }

    static OpsShardReadinessCodeWalkthroughDepthRegistryService service() {
        return new OpsShardReadinessCodeWalkthroughDepthRegistryService();
    }

    static OpsShardReadinessCodeWalkthroughDepthRegistryResponse registry() {
        return service().registry();
    }
}
