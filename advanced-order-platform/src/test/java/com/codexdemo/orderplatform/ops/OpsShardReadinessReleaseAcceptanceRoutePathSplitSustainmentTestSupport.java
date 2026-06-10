package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentTestSupport {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentTestSupport() {
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService service() {
        return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService(
                OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutTestSupport.service()
        );
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse registry() {
        return service().registry();
    }
}
