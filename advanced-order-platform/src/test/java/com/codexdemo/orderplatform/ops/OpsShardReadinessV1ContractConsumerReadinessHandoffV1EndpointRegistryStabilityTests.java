package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffV1EndpointRegistryStabilityTests {

    @Test
    void keepsConsumerReadinessHandoffAsTheFinalV1ContractEndpointPair() {
        var endpointPairs = OpsShardReadinessV1ContractEndpointPairs.endpointPairs();
        var readinessHandoffPair = endpointPairs.getLast();

        assertThat(endpointPairs).hasSize(11);
        assertThat(readinessHandoffPair.liveEndpoint())
                .isEqualTo(OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT);
        assertThat(readinessHandoffPair.fixtureEndpoint())
                .isEqualTo(OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT);
    }

    @Test
    void keepsReadOnlyCatalogAndRuntimeCloseoutOutsideTheV1ContractPairList() {
        assertThat(OpsShardReadinessV1ContractEndpointPairs.liveEndpoints())
                .doesNotContain(
                        OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT,
                        OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.ENDPOINT
                );
        assertThat(OpsShardReadinessV1ContractEndpointPairs.fixtureEndpoints())
                .doesNotContain(
                        OpsShardReadinessReadOnlyEvidenceCatalogService.FIXTURE_ENDPOINT,
                        OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.FIXTURE_ENDPOINT
                );
    }

    @Test
    void keepsV1EndpointRegistryStabilityPathVersionedToV270() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_V1_ENDPOINT_REGISTRY_STABILITY_EVIDENCE_PATH)
                .isEqualTo(
                        "e/270/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "v1-endpoint-registry-stability-v270.json"
                );
    }
}
