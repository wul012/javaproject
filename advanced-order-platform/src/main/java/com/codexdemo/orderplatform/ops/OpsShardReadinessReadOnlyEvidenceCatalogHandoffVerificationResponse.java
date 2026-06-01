package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean shardEnabled,
        String sourceCatalogVersion,
        String sourceHandoffVersion,
        String sourceCatalogReceiptId,
        String sourceHandoffReceiptId,
        boolean sourceCatalogPassed,
        boolean sourceHandoffPassed,
        boolean sourceCatalogFrozen,
        int frozenCatalogLiveEndpointCount,
        int frozenCatalogFixtureEndpointCount,
        int currentLiveEndpointCount,
        int currentFixtureEndpointCount,
        boolean currentRegistryIncludesVerification,
        boolean futureEndpointGrowthPreservesV175Catalog,
        boolean sourceBoundariesHeld,
        boolean writeRoutingAllowed,
        boolean activeShardRouterAllowed,
        boolean credentialValueRead,
        boolean rawEndpointParsed,
        boolean managedAuditConnectionAllowed,
        boolean deploymentAllowed,
        boolean rollbackAllowed,
        boolean nodeMayStartOrStopJavaOrMiniKv,
        String verificationProfile,
        String receiptId,
        List<String> verifiedArtifacts,
        List<String> verificationChecks,
        List<String> failClosedRules,
        List<String> blockedOperations,
        String evidencePath,
        String status
) {
}
