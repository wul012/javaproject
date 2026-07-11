package com.codexdemo.orderplatform.ops.maintenance.readonlyevidence;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationServiceTests {

  @Test
  void verifiesFrozenCatalogAndHandoffWithoutChangingRuntimeBoundaries() {
    OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationResponse verification =
        OpsShardReadinessReadOnlyEvidenceTestSupport.handoffVerificationService().verification();

    assertThat(verification.project()).isEqualTo("advanced-order-platform");
    assertThat(verification.version()).isEqualTo("Java v179");
    assertThat(verification.readOnly()).isTrue();
    assertThat(verification.executionAllowed()).isFalse();
    assertThat(verification.shardEnabled()).isFalse();
    assertThat(verification.sourceCatalogVersion()).isEqualTo("Java v175");
    assertThat(verification.sourceHandoffVersion()).isEqualTo("Java v177");
    assertThat(verification.sourceCatalogReceiptId())
        .isEqualTo("java-shard-readiness-read-only-evidence-catalog-receipt-v175");
    assertThat(verification.sourceHandoffReceiptId())
        .isEqualTo("java-shard-readiness-read-only-evidence-catalog-handoff-receipt-v177");
    assertThat(verification.sourceCatalogPassed()).isTrue();
    assertThat(verification.sourceHandoffPassed()).isTrue();
    assertThat(verification.sourceCatalogFrozen()).isTrue();
    assertThat(verification.frozenCatalogLiveEndpointCount()).isEqualTo(20);
    assertThat(verification.frozenCatalogFixtureEndpointCount()).isEqualTo(20);
    assertThat(verification.currentLiveEndpointCount()).isEqualTo(22);
    assertThat(verification.currentFixtureEndpointCount()).isEqualTo(22);
    assertThat(verification.currentRegistryIncludesVerification()).isTrue();
    assertThat(verification.futureEndpointGrowthPreservesV175Catalog()).isTrue();
    assertThat(verification.sourceBoundariesHeld()).isTrue();
    assertThat(verification.writeRoutingAllowed()).isFalse();
    assertThat(verification.activeShardRouterAllowed()).isFalse();
    assertThat(verification.credentialValueRead()).isFalse();
    assertThat(verification.rawEndpointParsed()).isFalse();
    assertThat(verification.managedAuditConnectionAllowed()).isFalse();
    assertThat(verification.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
    assertThat(verification.receiptId())
        .isEqualTo(
            "java-shard-readiness-read-only-evidence-catalog-handoff-verification-receipt-v179");
    assertThat(verification.verifiedArtifacts())
        .contains(
            "/api/v1/ops/shard-readiness/read-only-evidence-catalog",
            "/api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff",
            "e/176/evidence/java-shard-readiness-read-only-evidence-catalog-snapshot-freeze-v176.json");
    assertThat(verification.verificationChecks())
        .contains(
            "source-catalog-status:passed",
            "source-handoff-status:passed",
            "frozen-catalog-live-endpoint-count:20",
            "current-live-endpoint-count:22",
            "v175-catalog-does-not-include-v179-verification:true");
    assertThat(verification.failClosedRules())
        .contains(
            "block-if-source-catalog-status-is-not-passed",
            "block-if-v175-catalog-endpoint-count-drifts",
            "block-if-node-starts-or-stops-java-or-mini-kv");
    assertThat(verification.blockedOperations())
        .contains(
            "write-routing",
            "active-shard-router",
            "credential-value-read",
            "node-start-or-stop-java-or-mini-kv");
    assertThat(verification.evidencePath())
        .isEqualTo(
            "e/179/evidence/"
                + "java-shard-readiness-read-only-evidence-catalog-handoff-verification-v179.json");
    assertThat(verification.status()).isEqualTo("passed");
  }
}
