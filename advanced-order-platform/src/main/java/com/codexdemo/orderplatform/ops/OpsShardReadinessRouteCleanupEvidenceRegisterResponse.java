package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupEvidenceRegisterResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String registerEndpoint,
        String registerProfile,
        int registeredEvidenceCount,
        List<RegisteredEvidence> registeredEvidence,
        String digestValue,
        String status
) {

    public record RegisteredEvidence(
            String name,
            String endpoint,
            String category,
            boolean readOnly,
            boolean executionAllowed,
            String status
    ) {
    }
}
