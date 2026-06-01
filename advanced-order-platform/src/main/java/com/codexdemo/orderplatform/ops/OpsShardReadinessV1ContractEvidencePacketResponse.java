package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessV1ContractEvidencePacketResponse(
        String project,
        String version,
        String contractName,
        boolean readOnly,
        boolean executionAllowed,
        boolean shardEnabled,
        String packetEndpoint,
        String packetFixtureEndpoint,
        String sourceReadinessEndpoint,
        String sourceReadinessFixtureEndpoint,
        String alignmentEndpoint,
        String alignmentFixtureEndpoint,
        String handoffEndpoint,
        String handoffFixtureEndpoint,
        String handoffSnapshotFreezeEvidencePath,
        String handoffHistoricalCompatibilityEvidencePath,
        List<String> evidenceChain,
        List<String> nodeConsumableEndpoints,
        List<String> nodeConsumableFixtureEndpoints,
        List<String> blockedOperations,
        List<String> verificationChecks,
        boolean minimalFieldsFrozen,
        boolean historicalSnapshotsProtected,
        boolean writeRoutingAllowed,
        boolean activeShardRouterAllowed,
        boolean credentialValueRead,
        boolean rawEndpointParsed,
        boolean managedAuditConnectionAllowed,
        boolean deploymentOrRollbackAllowed,
        boolean nodeMayStartOrStopJavaOrMiniKv,
        String receiptId,
        String evidencePath,
        String status
) {
}
