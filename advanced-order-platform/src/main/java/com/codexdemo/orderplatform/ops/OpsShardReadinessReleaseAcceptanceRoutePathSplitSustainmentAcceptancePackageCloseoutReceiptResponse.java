package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String sourcePlan,
        String nodeParallelPlan,
        String sourceAcceptancePackageVersion,
        String sourceAcceptancePackageEndpoint,
        String endpoint,
        String profile,
        int acceptedCriteriaCount,
        int markdownLineCount,
        List<AcceptedCriterion> acceptedCriteria,
        List<String> markdownLines,
        List<String> checks,
        String receipt,
        String status
) {

    public record AcceptedCriterion(
            String name,
            String evidence,
            boolean required,
            String status
    ) {
    }
}
