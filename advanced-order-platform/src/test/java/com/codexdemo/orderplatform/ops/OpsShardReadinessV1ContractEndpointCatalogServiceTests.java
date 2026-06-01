package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractEndpointCatalogServiceTests {

    @Test
    void buildsReadOnlyEndpointCatalogForV1ContractConsumers() {
        OpsShardReadinessV1ContractEndpointCatalogResponse catalog =
                new OpsShardReadinessV1ContractEndpointCatalogService().catalog();

        assertThat(catalog.project()).isEqualTo("advanced-order-platform");
        assertThat(catalog.version()).isEqualTo("Java v208");
        assertThat(catalog.contractName()).isEqualTo("shard-readiness.v1");
        assertThat(catalog.readOnly()).isTrue();
        assertThat(catalog.executionAllowed()).isFalse();
        assertThat(catalog.shardEnabled()).isFalse();
        assertThat(catalog.endpointCatalogEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-endpoint-catalog");
        assertThat(catalog.endpointCatalogFixtureEndpoint())
                .isEqualTo("/contracts/java-shard-readiness-v1-contract-endpoint-catalog-v208.fixture.json");
        assertThat(catalog.contractEndpointCount()).isEqualTo(6);
        assertThat(catalog.endpoints())
                .extracting(OpsShardReadinessV1ContractEndpointCatalogResponse.EndpointEntry::name)
                .containsExactly(
                        "alignment",
                        "alignment-handoff",
                        "evidence-packet",
                        "operator-checklist",
                        "handoff-manifest",
                        "consumer-probe-plan"
                );
        assertThat(catalog.liveProbeEndpoints())
                .containsExactly(
                        "GET /api/v1/ops/shard-readiness/v1-contract-alignment",
                        "GET /api/v1/ops/shard-readiness/v1-contract-alignment-handoff",
                        "GET /api/v1/ops/shard-readiness/v1-contract-evidence-packet",
                        "GET /api/v1/ops/shard-readiness/v1-contract-operator-checklist",
                        "GET /api/v1/ops/shard-readiness/v1-contract-handoff-manifest",
                        "GET /api/v1/ops/shard-readiness/v1-contract-consumer-probe-plan"
                );
        assertThat(catalog.fixtureProbeEndpoints())
                .containsExactly(
                        "GET /contracts/java-shard-readiness-v1-contract-alignment-v187.fixture.json",
                        "GET /contracts/java-shard-readiness-v1-contract-alignment-handoff-v190.fixture.json",
                        "GET /contracts/java-shard-readiness-v1-contract-evidence-packet-v193.fixture.json",
                        "GET /contracts/java-shard-readiness-v1-contract-operator-checklist-v196.fixture.json",
                        "GET /contracts/java-shard-readiness-v1-contract-handoff-manifest-v199.fixture.json",
                        "GET /contracts/java-shard-readiness-v1-contract-consumer-probe-plan-v202.fixture.json"
                );
        assertThat(catalog.evidencePaths())
                .containsExactly(
                        "e/187/evidence/java-shard-readiness-v1-contract-alignment-v187.json",
                        "e/190/evidence/java-shard-readiness-v1-contract-alignment-handoff-v190.json",
                        "e/193/evidence/java-shard-readiness-v1-contract-evidence-packet-v193.json",
                        "e/196/evidence/java-shard-readiness-v1-contract-operator-checklist-v196.json",
                        "e/199/evidence/java-shard-readiness-v1-contract-handoff-manifest-v199.json",
                        "e/202/evidence/java-shard-readiness-v1-contract-consumer-probe-plan-v202.json"
                );
        assertThat(catalog.blockedOperations())
                .containsExactly(
                        "write-routing",
                        "active-shard-router",
                        "credential-value-read",
                        "raw-endpoint-parse",
                        "managed-audit-connection",
                        "deployment-or-rollback",
                        "node-start-or-stop-java-or-mini-kv"
                );
        assertThat(catalog.probesAreGetOnly()).isTrue();
        assertThat(catalog.upstreamActionsAllowed()).isFalse();
        assertThat(catalog.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
        assertThat(catalog.receiptId())
                .isEqualTo("java-shard-readiness-v1-contract-endpoint-catalog-receipt-v208");
        assertThat(catalog.evidencePath())
                .isEqualTo("e/208/evidence/java-shard-readiness-v1-contract-endpoint-catalog-v208.json");
        assertThat(catalog.status()).isEqualTo("passed");
    }
}
