package com.codexdemo.orderplatform.ops.maintenance.readonlyevidence;

import java.util.List;

public record OpsShardReadinessReadOnlyEndpointRegistryIntegrityResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean shardEnabled,
    int pairCount,
    int liveEndpointCount,
    int fixtureEndpointCount,
    boolean pairCountsAligned,
    boolean liveEndpointsDistinct,
    boolean fixtureEndpointsDistinct,
    boolean pairsHaveLiveAndFixture,
    boolean endpointRegistryIncludesIntegrity,
    boolean fixtureRegistryIncludesIntegrity,
    boolean writeRoutingAllowed,
    boolean activeShardRouterAllowed,
    boolean credentialValueRead,
    boolean rawEndpointParsed,
    boolean managedAuditConnectionAllowed,
    boolean deploymentOrRollbackAllowed,
    boolean nodeMayStartOrStopJavaOrMiniKv,
    String receiptId,
    List<String> verificationChecks,
    List<String> blockedOperations,
    String evidencePath,
    String status) {}
