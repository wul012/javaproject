package com.codexdemo.orderplatform.ops.maintenance.prototype;

import java.util.List;

public record OpsShardReadinessPrototypeConsumerGateEvidenceResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    String entryKey,
    String phase,
    String nodePlanVersion,
    String sourceHandoffVersion,
    String sourceHandoffEndpoint,
    String sourceHandoffEvidencePath,
    String contractName,
    int evidenceCount,
    List<String> evidenceRefs,
    List<String> checks,
    List<String> forbiddenOperations,
    String digestValue,
    String evidencePath,
    String status) {}
