package com.codexdemo.orderplatform.ops.maintenance.prototype;

import java.util.List;

public record OpsShardReadinessPrototypeHandoffCatalogResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    String sourcePrototypeVersion,
    String sourcePrototypeEndpoint,
    String contractName,
    int entryCount,
    List<OpsShardReadinessPrototypeHandoffEvidenceCatalog.Entry> entries,
    List<String> forbiddenOperations,
    String status) {}
