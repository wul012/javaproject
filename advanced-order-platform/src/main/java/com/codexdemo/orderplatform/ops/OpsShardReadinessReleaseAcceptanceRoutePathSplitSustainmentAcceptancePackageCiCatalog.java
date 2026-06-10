package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCiCatalog {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCiCatalog() {
    }

    static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
            .CiEvidence> evidence(
            OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source
    ) {
        return source.ciGates().stream()
                .map(gate -> new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                        .CiEvidence(gate.gate(), gate.command(), gate.required() ? "required" : "optional",
                        gate.required()))
                .toList();
    }
}
