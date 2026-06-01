package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractEndpointPairIntegrityTests {

    @Test
    void keepsV1ContractEndpointPairsFocusedAndPositionedInRollingRegistry() {
        assertThat(OpsShardReadinessV1ContractEndpointPairs.endpointPairs()).hasSize(9);
        assertThat(OpsShardReadinessV1ContractEndpointPairs.liveEndpoints())
                .doesNotHaveDuplicates()
                .allSatisfy(endpoint -> assertThat(endpoint).contains("/v1-contract-"))
                .doesNotContain(
                        OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT,
                        OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.ENDPOINT
                );
        assertThat(OpsShardReadinessV1ContractEndpointPairs.fixtureEndpoints())
                .doesNotHaveDuplicates()
                .allSatisfy(endpoint -> assertThat(endpoint).contains("v1-contract-"))
                .doesNotContain(
                        OpsShardReadinessReadOnlyEvidenceCatalogService.FIXTURE_ENDPOINT,
                        OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.FIXTURE_ENDPOINT
                );

        assertThat(OpsShardReadinessEvidenceEndpoints.liveEndpoints())
                .containsSubsequence(
                        OpsShardReadinessEchoService.ENDPOINT,
                        OpsShardReadinessV1ContractAlignmentService.ENDPOINT,
                        OpsShardReadinessV1ContractAlignmentHandoffService.ENDPOINT,
                        OpsShardReadinessV1ContractEvidencePacketService.ENDPOINT,
                        OpsShardReadinessV1ContractOperatorChecklistService.ENDPOINT,
                        OpsShardReadinessV1ContractHandoffManifestService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerProbePlanService.ENDPOINT,
                        OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService.ENDPOINT,
                        OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT
                );
        assertThat(OpsShardReadinessEvidenceEndpoints.fixtureEndpoints())
                .containsSubsequence(
                        OpsShardReadinessEchoService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractAlignmentHandoffService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractEvidencePacketService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractOperatorChecklistService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractHandoffManifestService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractConsumerProbePlanService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractEndpointCatalogService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractConsumerHandoffBundleService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService.FIXTURE_ENDPOINT,
                        OpsShardReadinessReadOnlyEvidenceCatalogService.FIXTURE_ENDPOINT
                );
    }
}
