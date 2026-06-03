package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupDigestResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String digestEndpoint,
        String digestProfile,
        String digestAlgorithm,
        String digestInput,
        String digestValue,
        int sourceCount,
        List<String> sourceEndpoints,
        String status
) {
}
