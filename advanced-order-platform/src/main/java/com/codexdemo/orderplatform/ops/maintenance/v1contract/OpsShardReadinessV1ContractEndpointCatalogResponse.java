package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import java.util.List;

public record OpsShardReadinessV1ContractEndpointCatalogResponse(
    String project,
    String version,
    String contractName,
    boolean readOnly,
    boolean executionAllowed,
    boolean shardEnabled,
    String endpointCatalogEndpoint,
    String endpointCatalogFixtureEndpoint,
    int contractEndpointCount,
    List<EndpointEntry> endpoints,
    List<String> liveProbeEndpoints,
    List<String> fixtureProbeEndpoints,
    List<String> evidencePaths,
    List<String> blockedOperations,
    boolean probesAreGetOnly,
    boolean upstreamActionsAllowed,
    boolean startsJavaService,
    boolean startsMiniKvService,
    boolean writeRoutingAllowed,
    boolean activeShardRouterAllowed,
    boolean credentialValueRead,
    boolean rawEndpointParsed,
    boolean managedAuditConnectionAllowed,
    boolean deploymentOrRollbackAllowed,
    boolean nodeMayStartOrStopJavaOrMiniKv,
    String receiptId,
    String evidencePath,
    String status) {

  public record EndpointEntry(
      String name,
      String liveEndpoint,
      String fixtureEndpoint,
      String evidencePath,
      String receiptId) {}
}
