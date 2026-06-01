package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessV1ContractAlignmentResponse(
        String project,
        String version,
        String contractName,
        boolean readOnly,
        boolean executionAllowed,
        boolean shardEnabled,
        String sourceReadinessVersion,
        String sourceEndpoint,
        String sourceFixtureEndpoint,
        String sourceEvidencePath,
        List<String> minimalFields,
        boolean minimalFieldsFrozen,
        boolean readOnlyMatches,
        boolean executionBlocked,
        boolean shardRoutingDisabled,
        boolean shardCountsClosed,
        boolean routingModeFixtureBacked,
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
        String status
) {
}
