package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupConsumerChecklistResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String checklistProfile,
        int itemCount,
        List<ChecklistItem> items,
        String status
) {

    public record ChecklistItem(
            String name,
            boolean passed,
            String evidence,
            String status
    ) {
    }
}
