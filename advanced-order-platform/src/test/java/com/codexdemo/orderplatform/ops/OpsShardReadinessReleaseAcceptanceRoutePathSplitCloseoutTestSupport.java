package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutTestSupport {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutTestSupport() {
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService service() {
        return new OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService(
                OpsShardReadinessReleaseAcceptanceRoutePathSplitTestSupport.service()
        );
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse closeout() {
        return service().closeout();
    }
}
