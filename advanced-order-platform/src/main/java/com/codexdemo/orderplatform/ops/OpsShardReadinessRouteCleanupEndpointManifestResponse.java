package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupEndpointManifestResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String manifestEndpoint,
        String manifestProfile,
        int endpointCount,
        List<EndpointEntry> endpoints,
        String status
) {

    public record EndpointEntry(
            String constantName,
            String route,
            String endpoint,
            boolean readOnly,
            boolean executionAllowed,
            String status
    ) {
    }
}
