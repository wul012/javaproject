package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupTransitionBriefResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String transitionBriefEndpoint,
        String transitionBriefProfile,
        int readinessSignalCount,
        List<ReadinessSignal> readinessSignals,
        String nextAction,
        String status
) {

    public record ReadinessSignal(
            String name,
            String evidence,
            String status
    ) {
    }
}
