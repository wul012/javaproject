package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageTestSupport {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageTestSupport() {
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageService service() {
        return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageService(
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentTestSupport.service()
        );
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse registry() {
        return service().registry();
    }
}
