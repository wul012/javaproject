package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessCodeWalkthroughQualityAuditRegistryTestSupport {

    private OpsShardReadinessCodeWalkthroughQualityAuditRegistryTestSupport() {
    }

    static OpsShardReadinessCodeWalkthroughQualityAuditRegistryService service() {
        return new OpsShardReadinessCodeWalkthroughQualityAuditRegistryService();
    }

    static OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse registry() {
        return service().registry();
    }
}
