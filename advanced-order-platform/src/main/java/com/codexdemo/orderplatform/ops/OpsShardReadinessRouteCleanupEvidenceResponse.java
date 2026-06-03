package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupEvidenceResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String catalogEndpoint,
        String catalogProfile,
        int entryCount,
        List<Entry> entries,
        List<String> forbiddenOperations,
        String status
) {

    public record Entry(
            int javaVersion,
            String sourceNodePlan,
            String phase,
            String evidenceType,
            String evidencePath,
            boolean readOnly,
            boolean executionAllowed,
            boolean startsJavaService,
            boolean startsMiniKvService,
            boolean credentialValueRead,
            boolean rawEndpointParsed,
            boolean managedAuditConnectionOpened,
            boolean writeRoutingChanged,
            String status
    ) {
    }
}
