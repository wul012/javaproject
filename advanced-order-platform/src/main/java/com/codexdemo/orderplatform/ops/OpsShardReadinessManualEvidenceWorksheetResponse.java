package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessManualEvidenceWorksheetResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean readyForOperatorEntryWorksheet,
        boolean readyForManualEvidenceEntry,
        boolean readyForLiveExecution,
        boolean readyForProductionExecution,
        String endpoint,
        String profile,
        String sourcePlan,
        int itemCount,
        int passedItemCount,
        List<WorksheetItem> items,
        List<String> checks,
        String status
) {
    public record WorksheetItem(
            String name,
            String owner,
            String evidence,
            String sourceEndpoint,
            String status
    ) {
    }
}
