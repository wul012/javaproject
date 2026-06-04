package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessPrototypeHandoffEvidenceResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String endpoint,
        String profile,
        String entryKey,
        String phase,
        String nodePlanVersion,
        String sourceCatalogVersion,
        String sourceCloseoutVersion,
        String contractName,
        int evidenceCount,
        List<String> evidenceRefs,
        List<String> checks,
        List<String> forbiddenOperations,
        String digestValue,
        String evidencePath,
        String status
) {
}
