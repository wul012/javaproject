package com.codexdemo.orderplatform.ops.maintenance.prototype;

import java.util.List;

public record OpsShardReadinessPrototypeEvidenceResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    String entryKey,
    String phase,
    String nodePlanVersion,
    String contractName,
    boolean shardEnabled,
    int shardCount,
    int slotCount,
    String routingMode,
    String rootReadinessVersion,
    String echoVersion,
    String routeCleanupCloseoutVersion,
    List<String> requiredFields,
    List<String> evidenceRefs,
    List<String> checks,
    List<String> forbiddenOperations,
    String digestValue,
    String evidencePath,
    String status) {}
