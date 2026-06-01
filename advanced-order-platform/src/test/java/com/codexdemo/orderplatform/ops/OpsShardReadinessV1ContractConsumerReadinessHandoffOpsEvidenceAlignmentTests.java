package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffOpsEvidenceAlignmentTests {

    @Test
    void keepsReadinessHandoffEndpointPairVisibleInOpsEvidenceInventory() {
        assertThat(OpsShardReadinessEvidenceEndpoints.liveEndpoints())
                .contains(OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT);
        assertThat(OpsShardReadinessEvidenceEndpoints.fixtureEndpoints())
                .contains(OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT);
    }

    @Test
    void keepsReadinessHandoffProbePairVisibleInOpsEvidenceInventory() {
        assertThat(OpsShardReadinessEvidenceEndpoints.liveProbeEndpoints())
                .contains("GET " + OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT);
        assertThat(OpsShardReadinessEvidenceEndpoints.fixtureProbeEndpoints())
                .contains("GET " + OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT);
    }

    @Test
    void keepsOpsEvidenceOrderingAlignedWithV1ConsumerChain() {
        assertThat(OpsShardReadinessEvidenceEndpoints.liveEndpoints())
                .containsSubsequence(
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT,
                        OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT
                );
        assertThat(OpsShardReadinessEvidenceEndpoints.fixtureEndpoints())
                .containsSubsequence(
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT,
                        OpsShardReadinessReadOnlyEvidenceCatalogService.FIXTURE_ENDPOINT
                );
    }
}
