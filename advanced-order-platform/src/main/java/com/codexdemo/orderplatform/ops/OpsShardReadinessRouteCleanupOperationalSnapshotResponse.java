package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupOperationalSnapshotResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String snapshotEndpoint,
        String snapshotProfile,
        int latestVersion,
        int endpointCount,
        int phaseCount,
        String receipt,
        int boundarySignalCount,
        List<BoundarySignal> boundarySignals,
        String status
) {

    public record BoundarySignal(
            String name,
            String value,
            String status
    ) {
    }
}
