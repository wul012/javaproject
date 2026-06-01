package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessV1ContractConsumerEvidenceDigestResponse(
        String project,
        String version,
        String contractName,
        boolean readOnly,
        boolean executionAllowed,
        boolean shardEnabled,
        String evidenceDigestEndpoint,
        String evidenceDigestFixtureEndpoint,
        String verificationChecklistEndpoint,
        String verificationChecklistFixtureEndpoint,
        String verificationChecklistEvidencePath,
        String verificationChecklistReceiptId,
        int checklistItemCount,
        int requiredEvidenceCount,
        int verificationCheckCount,
        List<String> digestEvidence,
        List<String> digestChecks,
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
        String status
) {
}
