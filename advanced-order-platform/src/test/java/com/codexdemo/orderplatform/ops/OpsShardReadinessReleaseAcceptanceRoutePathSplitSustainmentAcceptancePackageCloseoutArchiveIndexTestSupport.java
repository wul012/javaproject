package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexTestSupport {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexTestSupport() {
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexService
            service() {
        return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexService(
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptTestSupport
                        .service()
        );
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
            index() {
        return service().index();
    }
}
