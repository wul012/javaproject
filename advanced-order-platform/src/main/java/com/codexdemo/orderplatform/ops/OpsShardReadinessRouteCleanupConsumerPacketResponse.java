package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupConsumerPacketResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String packetProfile,
        String readOnlyGateEndpoint,
        String archiveVerificationEndpoint,
        String releaseHandoffEndpoint,
        int endpointCount,
        List<String> endpoints,
        List<String> blockedOperations,
        String decision,
        String status
) {
}
