package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessV1ContractConsumerProbePlanResponse(
        String project,
        String version,
        String contractName,
        boolean readOnly,
        boolean executionAllowed,
        boolean shardEnabled,
        String probePlanEndpoint,
        String probePlanFixtureEndpoint,
        String manifestEndpoint,
        String manifestFixtureEndpoint,
        String manifestEvidencePath,
        String manifestReceiptId,
        List<String> readTargets,
        List<String> fixtureTargets,
        List<String> probeSequence,
        List<String> requiredEvidence,
        List<String> stopConditions,
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
