package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessV1ContractConsumerVerificationChecklistResponse(
        String project,
        String version,
        String contractName,
        boolean readOnly,
        boolean executionAllowed,
        boolean shardEnabled,
        String verificationChecklistEndpoint,
        String verificationChecklistFixtureEndpoint,
        String handoffBundleEndpoint,
        String handoffBundleFixtureEndpoint,
        String handoffBundleEvidencePath,
        String handoffBundleReceiptId,
        int catalogedArtifactCount,
        List<String> verificationItems,
        List<String> requiredEvidence,
        List<String> blockedOperations,
        List<String> verificationChecks,
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
        String status
) {
}
