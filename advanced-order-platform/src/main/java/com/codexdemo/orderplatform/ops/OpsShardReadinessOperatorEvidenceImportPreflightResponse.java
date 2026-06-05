package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessOperatorEvidenceImportPreflightResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean readyForOperatorEvidenceImportPreflight,
        boolean readyForEvidenceImport,
        boolean readyForManualEvidenceEntry,
        boolean readyForLiveExecution,
        boolean readyForProductionExecution,
        String endpoint,
        String profile,
        String sourcePlan,
        int itemCount,
        int passedItemCount,
        List<PreflightItem> items,
        List<String> checks,
        String status
) {
    public record PreflightItem(
            String name,
            String owner,
            String evidence,
            String sourceEndpoint,
            String status
    ) {
    }
}
