package com.codexdemo.orderplatform.ops.maintenance.readinesscore;

import java.util.List;

public record OpsShardReadinessEchoResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean shardEnabled,
    boolean writeRoutingAllowed,
    boolean activeShardRouterAllowed,
    boolean credentialValueRead,
    boolean rawEndpointParsed,
    boolean managedAuditConnectionAllowed,
    boolean deploymentAllowed,
    boolean rollbackAllowed,
    String sourceReadinessVersion,
    String sourceHardeningVersion,
    String sourceEvidenceIndexVersion,
    String sourceEvidenceHandoffVersion,
    String schemaCompatibilityMode,
    String echoProfile,
    String receiptId,
    List<String> preservedRootFields,
    List<String> controllerSplitReceipts,
    List<String> evidenceArchivePaths,
    List<String> readOnlyEvidenceCapabilities,
    List<String> forbiddenOperations,
    List<String> consumerGuidance,
    String evidencePath,
    String status) {}
