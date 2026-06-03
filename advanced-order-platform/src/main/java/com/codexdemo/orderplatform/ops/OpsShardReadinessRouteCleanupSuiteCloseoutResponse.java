package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupSuiteCloseoutResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String closeoutEndpoint,
        String closeoutProfile,
        int firstSuiteVersion,
        int lastSuiteVersion,
        int suiteVersionCount,
        int publishedEndpointCount,
        List<String> publishedEndpoints,
        String releaseHandoffEndpoint,
        String readOnlyGateEndpoint,
        String digestValue,
        String decision,
        String status
) {
}
