package com.codexdemo.orderplatform.ops.maintenance.prototype;

import java.util.List;

public record OpsShardReadinessPrototypeConsumerGateCatalogResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    String sourceHandoffVersion,
    String sourceHandoffEndpoint,
    int sourceHandoffEntryCount,
    String contractName,
    int entryCount,
    List<OpsShardReadinessPrototypeConsumerGateEvidenceCatalog.Entry> entries,
    List<String> forbiddenOperations,
    String status) {}
