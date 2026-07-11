package com.codexdemo.orderplatform.ops.maintenance.readonlyevidence;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReadOnlyEvidenceCatalogServiceTests {

  @Test
  void buildsReadOnlyEvidenceCatalogForBatchConsumption() {
    OpsShardReadinessReadOnlyEvidenceCatalogResponse catalog =
        OpsShardReadinessReadOnlyEvidenceTestSupport.catalogService().catalog();

    assertThat(catalog.project()).isEqualTo("advanced-order-platform");
    assertThat(catalog.version()).isEqualTo("Java v175");
    assertThat(catalog.readOnly()).isTrue();
    assertThat(catalog.executionAllowed()).isFalse();
    assertThat(catalog.shardEnabled()).isFalse();
    assertThat(catalog.writeRoutingAllowed()).isFalse();
    assertThat(catalog.activeShardRouterAllowed()).isFalse();
    assertThat(catalog.credentialValueRead()).isFalse();
    assertThat(catalog.rawEndpointParsed()).isFalse();
    assertThat(catalog.managedAuditConnectionAllowed()).isFalse();
    assertThat(catalog.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
    assertThat(catalog.sourceEchoVersion()).isEqualTo("Java v174");
    assertThat(catalog.sourceRuntimePassEvidenceCloseoutVersion()).isEqualTo("Java v170");
    assertThat(catalog.sourceEchoReceiptId()).isEqualTo("java-shard-readiness-echo-receipt-v174");
    assertThat(catalog.sourceRuntimePassEvidenceCloseoutReceiptId())
        .isEqualTo("java-runtime-execution-pass-evidence-closeout-receipt-v170");
    assertThat(catalog.schemaCompatibilityMode())
        .isEqualTo("append-only-read-only-evidence-catalog-preserves-v153-root-schema");
    assertThat(catalog.receiptId())
        .isEqualTo("java-shard-readiness-read-only-evidence-catalog-receipt-v175");
    assertThat(catalog.catalogEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/read-only-evidence-catalog");
    assertThat(catalog.fixtureEndpoint())
        .isEqualTo("/contracts/java-shard-readiness-read-only-evidence-catalog-v175.fixture.json");
    assertThat(catalog.liveEndpointCount()).isEqualTo(20);
    assertThat(catalog.fixtureEndpointCount()).isEqualTo(20);
    assertThat(catalog.liveEndpoints())
        .contains(
            "/api/v1/ops/shard-readiness/echo",
            "/api/v1/ops/shard-readiness/read-only-evidence-catalog",
            "/api/v1/ops/shard-readiness/runtime-execution-pass-evidence-closeout");
    assertThat(catalog.fixtureEndpoints())
        .contains(
            "/contracts/java-shard-readiness-echo-v174.fixture.json",
            "/contracts/java-shard-readiness-read-only-evidence-catalog-v175.fixture.json",
            "/contracts/java-shard-readiness-runtime-execution-pass-evidence-closeout-v170.fixture.json");
    assertThat(catalog.evidenceArchivePaths())
        .contains(
            "e/153/evidence/java-shard-readiness-v153.json",
            "e/170/evidence/java-shard-readiness-runtime-execution-pass-evidence-closeout-v170.json",
            "e/174/evidence/java-shard-readiness-echo-v174.json",
            "e/175/evidence/java-shard-readiness-read-only-evidence-catalog-v175.json");
    assertThat(catalog.sourceReceipts())
        .contains(
            "java-shard-readiness-echo-receipt-v174",
            "java-runtime-execution-pass-evidence-closeout-receipt-v170",
            "Java v173:evidence-controller-split");
    assertThat(catalog.consumerBatches())
        .containsExactly(
            "java-v153-v157:baseline-readiness-index-verification-handoff",
            "java-v158-v161:active-shard-plan-and-operator-lifecycle-read-only",
            "java-v162-v170:runtime-execution-read-only-pass-evidence",
            "java-v171-v174:controller-split-and-echo-readiness",
            "java-v175:read-only-evidence-catalog-for-batch-node-consumption");
    assertThat(catalog.failClosedRules())
        .contains(
            "source-echo-status-must-be-passed:passed",
            "source-runtime-pass-evidence-closeout-status-must-be-passed:passed",
            "catalog-does-not-enable-write-routing-or-active-shard-router");
    assertThat(catalog.forbiddenOperations())
        .contains(
            "write-routing",
            "active-shard-router",
            "credential-value-read",
            "node-start-or-stop-java-or-mini-kv");
    assertThat(catalog.evidencePath())
        .isEqualTo("e/175/evidence/java-shard-readiness-read-only-evidence-catalog-v175.json");
    assertThat(catalog.status()).isEqualTo("passed");
  }
}
