package com.codexdemo.orderplatform.ops.maintenance.readinesscore;

import java.util.List;

public record OpsShardReadinessActiveShardPlanHandoffResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean activeShardPrototypeEnabled,
    boolean liveReadAllowed,
    String sourceHandoffVersion,
    String lastConsumedByNodeVersion,
    String nodeArchiveVerificationVersion,
    String javaRole,
    String activePrototypeAuthority,
    List<String> frozenJavaEvidence,
    List<String> nodeConsumptionReferences,
    List<String> javaBoundaryRules,
    List<String> stopConditions,
    String evidencePath,
    String status) {}
