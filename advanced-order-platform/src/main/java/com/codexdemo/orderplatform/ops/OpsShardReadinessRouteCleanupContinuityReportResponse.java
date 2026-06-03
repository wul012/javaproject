package com.codexdemo.orderplatform.ops;

public record OpsShardReadinessRouteCleanupContinuityReportResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String reportEndpoint,
        String reportProfile,
        int firstVersion,
        int latestVersion,
        int versionCount,
        int endpointCount,
        int phaseCount,
        boolean versionsContinuous,
        boolean readOnlyBoundaryHeld,
        String status
) {
}
