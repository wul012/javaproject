package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceContinuityResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String endpoint,
        String profile,
        int firstJavaVersion,
        int latestJavaVersion,
        int expectedEntryCount,
        int actualEntryCount,
        int segmentCount,
        int gapCount,
        List<String> checks,
        String status
) {
}
