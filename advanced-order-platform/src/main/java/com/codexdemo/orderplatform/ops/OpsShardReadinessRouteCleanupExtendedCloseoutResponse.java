package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupExtendedCloseoutResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String closeoutProfile,
        int firstExtendedVersion,
        int latestVersion,
        int extendedVersionCount,
        String handoffBundleEndpoint,
        String consumerChecklistEndpoint,
        String finalDigestEndpoint,
        String continuityReportEndpoint,
        int evidenceCount,
        List<String> evidence,
        String decision,
        String status
) {
}
