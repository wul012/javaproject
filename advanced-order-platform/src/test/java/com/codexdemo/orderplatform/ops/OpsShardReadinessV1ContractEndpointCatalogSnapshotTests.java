package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractEndpointCatalogSnapshotTests {

    @Test
    void freezesV208EndpointCatalogReceipt() {
        OpsShardReadinessV1ContractEndpointCatalogResponse catalog =
                OpsShardReadinessV1ContractEndpointCatalogSnapshot.v208Catalog();

        assertThat(catalog.version()).isEqualTo("Java v208");
        assertThat(catalog.contractEndpointCount()).isEqualTo(6);
        assertThat(catalog.endpointCatalogEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-endpoint-catalog");
        assertThat(catalog.endpointCatalogFixtureEndpoint())
                .isEqualTo("/contracts/java-shard-readiness-v1-contract-endpoint-catalog-v208.fixture.json");
        assertThat(catalog.endpoints())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractEndpointCatalogSnapshot.v208EndpointEntries()
                );
        assertThat(catalog.blockedOperations())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractEndpointCatalogSnapshot.v208BlockedOperations()
                );
        assertThat(catalog.receiptId())
                .isEqualTo("java-shard-readiness-v1-contract-endpoint-catalog-receipt-v208");
        assertThat(catalog.evidencePath())
                .isEqualTo("e/208/evidence/java-shard-readiness-v1-contract-endpoint-catalog-v208.json");
        assertThat(catalog.status()).isEqualTo("passed");
    }
}
