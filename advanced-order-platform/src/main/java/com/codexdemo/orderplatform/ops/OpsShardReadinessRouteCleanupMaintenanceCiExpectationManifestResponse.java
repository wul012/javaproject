package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String endpoint,
        String profile,
        int expectationCount,
        int laneCount,
        boolean startsJavaService,
        boolean startsMiniKvService,
        List<CiExpectation> expectations,
        List<String> checks,
        String status
) {

    public record CiExpectation(
            String itemName,
            String focusedTestClass,
            String routeRegressionClass,
            String fullRegressionCommand,
            String githubActionsJob,
            String status
    ) {
    }
}
