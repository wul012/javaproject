package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentDriftCatalog {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentDriftCatalog() {
    }

    static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.DriftGuard> guards(
            OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse source
    ) {
        return List.of(
                guard(
                        "source-closeout-version",
                        source.version(),
                        "Java v1579",
                        "Java v1579".equals(source.version())
                ),
                guard(
                        "source-split-version",
                        source.sourceSplitVersion(),
                        "Java v1570",
                        "Java v1570".equals(source.sourceSplitVersion())
                ),
                guard(
                        "route-path-count",
                        String.valueOf(source.routePathCount()),
                        "11",
                        source.routePathCount() == 11
                ),
                guard(
                        "compatibility-check-count",
                        String.valueOf(source.compatibilityCheckCount()),
                        "11",
                        source.compatibilityCheckCount() == 11
                ),
                guard(
                        "closeout-item-count",
                        String.valueOf(source.closeoutItemCount()),
                        "6",
                        source.closeoutItemCount() == 6
                ),
                guard(
                        "parallel-node-plan",
                        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentSupport.NODE_PARALLEL_PLAN,
                        "Node v1867-v1878",
                        "Node v1867-v1878".equals(
                                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentSupport
                                        .NODE_PARALLEL_PLAN)
                )
        );
    }

    private static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.DriftGuard guard(
            String guard,
            String signal,
            String expected,
            boolean locked
    ) {
        return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.DriftGuard(
                guard,
                signal,
                expected,
                locked
        );
    }
}
