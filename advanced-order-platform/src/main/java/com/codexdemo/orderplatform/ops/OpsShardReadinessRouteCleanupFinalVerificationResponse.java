package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupFinalVerificationResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String finalVerificationEndpoint,
        String finalVerificationProfile,
        int verificationCount,
        List<Verification> verifications,
        String digestValue,
        String decision,
        String status
) {

    public record Verification(
            String name,
            String evidence,
            String status
    ) {
    }
}
