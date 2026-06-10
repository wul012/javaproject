package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitTestSupport {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitTestSupport() {
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitService service() {
        return new OpsShardReadinessReleaseAcceptanceRoutePathSplitService(
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffTestSupport.service()
        );
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse registry() {
        return service().registry();
    }
}
