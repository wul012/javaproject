package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessReadOnlyEvidenceCatalogResponse(
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
        boolean nodeMayStartOrStopJavaOrMiniKv,
        String sourceEchoVersion,
        String sourceRuntimePassEvidenceCloseoutVersion,
        String sourceEchoReceiptId,
        String sourceRuntimePassEvidenceCloseoutReceiptId,
        String schemaCompatibilityMode,
        String catalogProfile,
        String receiptId,
        String catalogEndpoint,
        String fixtureEndpoint,
        int liveEndpointCount,
        int fixtureEndpointCount,
        List<String> liveEndpoints,
        List<String> fixtureEndpoints,
        List<String> evidenceArchivePaths,
        List<String> sourceReceipts,
        List<String> consumerBatches,
        List<String> failClosedRules,
        List<String> forbiddenOperations,
        String evidencePath,
        String status
) {
}
