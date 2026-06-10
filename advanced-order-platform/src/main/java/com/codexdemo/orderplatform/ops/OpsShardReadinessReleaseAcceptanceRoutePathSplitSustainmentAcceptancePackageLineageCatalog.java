package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageLineageCatalog {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageLineageCatalog() {
    }

    static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
            .VersionLineage> lineage(
            OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source
    ) {
        boolean passed = "passed".equals(source.status());
        return List.of(
                lineage("route-path-split", source.sourceSplitVersion(), source.sourceSplitEndpoint(), passed),
                lineage("route-path-split-closeout", source.sourceCloseoutVersion(),
                        source.sourceCloseoutEndpoint(), passed),
                lineage("route-path-split-sustainment", source.version(), source.endpoint(), passed)
        );
    }

    private static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
            .VersionLineage lineage(
            String stage,
            String version,
            String endpoint,
            boolean passed
    ) {
        return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                .VersionLineage(stage, version, endpoint, passed ? "passed" : "blocked");
    }
}
