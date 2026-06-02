package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffReadOnlyAdjacencyTests {

    @Test
    void keepsReadOnlyCatalogAdjacentAfterReadinessHandoffOnlyInRollingEvidenceRegistry() {
        assertThat(OpsShardReadinessEvidenceEndpoints.liveEndpoints())
                .containsSubsequence(
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT,
                        OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT
                );
        assertThat(OpsShardReadinessV1ContractEndpointPairs.liveEndpoints())
                .contains(OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT)
                .doesNotContain(OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT);
    }

    @Test
    void keepsReadOnlyAdjacencyEvidencePathVersionedToV254() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_READ_ONLY_ADJACENCY_EVIDENCE_PATH)
                .isEqualTo(
                        "e/254/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "read-only-adjacency-v254.json"
                );
    }
}
