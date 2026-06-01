package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessV1ContractOperatorChecklistResponse(
        String project,
        String version,
        String contractName,
        boolean readOnly,
        boolean executionAllowed,
        boolean shardEnabled,
        String checklistEndpoint,
        String checklistFixtureEndpoint,
        String packetEndpoint,
        String packetFixtureEndpoint,
        String packetEvidencePath,
        String packetSnapshotFreezeEvidencePath,
        String packetHistoricalCompatibilityEvidencePath,
        List<String> operatorChecklistItems,
        List<String> requiredReadOnlyEvidence,
        List<String> nodeResponsibilities,
        List<String> javaResponsibilities,
        List<String> blockedOperations,
        List<String> verificationChecks,
        boolean packetFrozen,
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
