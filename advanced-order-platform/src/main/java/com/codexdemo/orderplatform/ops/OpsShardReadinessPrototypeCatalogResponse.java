package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessPrototypeCatalogResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String endpoint,
        String profile,
        String contractName,
        int entryCount,
        List<OpsShardReadinessPrototypeEvidenceCatalog.Entry> entries,
        List<String> requiredFields,
        List<String> forbiddenOperations,
        String status
) {
}
