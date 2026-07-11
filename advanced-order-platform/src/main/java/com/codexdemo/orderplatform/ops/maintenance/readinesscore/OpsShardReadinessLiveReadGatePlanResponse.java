package com.codexdemo.orderplatform.ops.maintenance.readinesscore;

import java.util.List;

public record OpsShardReadinessLiveReadGatePlanResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean liveReadGateAllowed,
    boolean serviceStartAllowedByNode,
    boolean serviceStopAllowedByNode,
    boolean failClosedRequired,
    String sourceBoundaryHandoffVersion,
    String lastVerifiedByNodeVersion,
    String nextNodeConsumerHint,
    List<String> requiredServiceOwnershipFields,
    List<String> javaServiceLifecyclePlan,
    List<String> smokeTargets,
    List<String> failClosedRules,
    List<String> cleanupResponsibilities,
    List<String> stopConditions,
    String evidencePath,
    String status) {}
