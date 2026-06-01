package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerHandoffBundleServiceTests {

    @Test
    void buildsReadOnlyConsumerHandoffBundleFromFrozenEndpointCatalog() {
        OpsShardReadinessV1ContractConsumerHandoffBundleResponse bundle =
                new OpsShardReadinessV1ContractConsumerHandoffBundleService().bundle();

        assertThat(bundle.project()).isEqualTo("advanced-order-platform");
        assertThat(bundle.version()).isEqualTo("Java v211");
        assertThat(bundle.contractName()).isEqualTo("shard-readiness.v1");
        assertThat(bundle.readOnly()).isTrue();
        assertThat(bundle.executionAllowed()).isFalse();
        assertThat(bundle.shardEnabled()).isFalse();
        assertThat(bundle.handoffBundleEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-consumer-handoff-bundle");
        assertThat(bundle.handoffBundleFixtureEndpoint())
                .isEqualTo("/contracts/java-shard-readiness-v1-contract-consumer-handoff-bundle-v211.fixture.json");
        assertThat(bundle.endpointCatalogEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-endpoint-catalog");
        assertThat(bundle.endpointCatalogReceiptId())
                .isEqualTo("java-shard-readiness-v1-contract-endpoint-catalog-receipt-v208");
        assertThat(bundle.catalogedArtifactCount()).isEqualTo(6);
        assertThat(bundle.consumerReadTargets())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractEndpointCatalogSnapshot.v208Catalog().liveProbeEndpoints()
                );
        assertThat(bundle.fixtureReadTargets())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractEndpointCatalogSnapshot.v208Catalog().fixtureProbeEndpoints()
                );
        assertThat(bundle.requiredEvidence())
                .containsExactly(
                        "e/187/evidence/java-shard-readiness-v1-contract-alignment-v187.json",
                        "e/190/evidence/java-shard-readiness-v1-contract-alignment-handoff-v190.json",
                        "e/193/evidence/java-shard-readiness-v1-contract-evidence-packet-v193.json",
                        "e/196/evidence/java-shard-readiness-v1-contract-operator-checklist-v196.json",
                        "e/199/evidence/java-shard-readiness-v1-contract-handoff-manifest-v199.json",
                        "e/202/evidence/java-shard-readiness-v1-contract-consumer-probe-plan-v202.json",
                        "e/208/evidence/java-shard-readiness-v1-contract-endpoint-catalog-v208.json",
                        "e/209/evidence/java-shard-readiness-v208-endpoint-catalog-snapshot-freeze-v209.json",
                        "e/210/evidence/java-shard-readiness-v208-endpoint-catalog-historical-compatibility-v210.json"
                );
        assertThat(bundle.handoffEvidence())
                .containsExactly(
                        "e/208/evidence/java-shard-readiness-v1-contract-endpoint-catalog-v208.json",
                        "e/209/evidence/java-shard-readiness-v208-endpoint-catalog-snapshot-freeze-v209.json",
                        "e/210/evidence/java-shard-readiness-v208-endpoint-catalog-historical-compatibility-v210.json",
                        "e/211/evidence/java-shard-readiness-v1-contract-consumer-handoff-bundle-v211.json"
                );
        assertThat(bundle.probesAreGetOnly()).isTrue();
        assertThat(bundle.upstreamActionsAllowed()).isFalse();
        assertThat(bundle.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
        assertThat(bundle.receiptId())
                .isEqualTo("java-shard-readiness-v1-contract-consumer-handoff-bundle-receipt-v211");
        assertThat(bundle.evidencePath())
                .isEqualTo("e/211/evidence/java-shard-readiness-v1-contract-consumer-handoff-bundle-v211.json");
        assertThat(bundle.status()).isEqualTo("passed");
    }
}
