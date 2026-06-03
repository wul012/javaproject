package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupSourcePlanAlignmentResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String alignmentEndpoint,
        String alignmentProfile,
        String sourcePlan,
        String sourcePlanPath,
        int alignmentCount,
        List<AlignmentItem> alignments,
        String status
) {

    public record AlignmentItem(
            String subject,
            String expected,
            String actual,
            boolean aligned,
            String status
    ) {
    }
}
