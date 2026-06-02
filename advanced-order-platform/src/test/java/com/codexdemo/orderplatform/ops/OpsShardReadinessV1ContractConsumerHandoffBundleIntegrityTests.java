package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerHandoffBundleIntegrityTests {

    @Test
    void keepsEndpointCatalogAndConsumerBundleAlignedWithV1Registry() {
        OpsShardReadinessV1ContractEndpointCatalogResponse catalog =
                OpsShardReadinessV1ContractEndpointCatalogSnapshot.v208Catalog();
        OpsShardReadinessV1ContractConsumerHandoffBundleResponse bundle =
                OpsShardReadinessV1ContractConsumerHandoffBundleSnapshot.v211Bundle();

        assertThat(OpsShardReadinessV1ContractEndpointPairs.endpointPairs()).hasSize(11);
        assertThat(OpsShardReadinessV1ContractEndpointPairs.liveEndpoints())
                .doesNotHaveDuplicates()
                .containsSubsequence(
                        OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT
                )
                .doesNotContain(
                        OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT,
                        OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.ENDPOINT
                );
        assertThat(OpsShardReadinessV1ContractEndpointPairs.fixtureEndpoints())
                .doesNotHaveDuplicates()
                .containsSubsequence(
                        OpsShardReadinessV1ContractEndpointCatalogService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractConsumerHandoffBundleService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT
                )
                .doesNotContain(
                        OpsShardReadinessReadOnlyEvidenceCatalogService.FIXTURE_ENDPOINT,
                        OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.FIXTURE_ENDPOINT
                );

        assertThat(catalog.contractEndpointCount()).isEqualTo(6);
        assertThat(bundle.catalogedArtifactCount()).isEqualTo(catalog.contractEndpointCount());
        assertThat(bundle.endpointCatalogReceiptId()).isEqualTo(catalog.receiptId());
        assertThat(bundle.consumerReadTargets()).containsExactlyElementsOf(catalog.liveProbeEndpoints());
        assertThat(bundle.fixtureReadTargets()).containsExactlyElementsOf(catalog.fixtureProbeEndpoints());
        assertThat(bundle.requiredEvidence())
                .startsWith(catalog.evidencePaths().toArray(String[]::new))
                .contains(OpsShardReadinessV1ContractEndpointCatalogService.EVIDENCE_PATH)
                .doesNotContain(OpsShardReadinessV1ContractConsumerHandoffBundleService.EVIDENCE_PATH);
        assertThat(bundle.handoffEvidence())
                .containsExactly(
                        OpsShardReadinessV1ContractEndpointCatalogService.EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerHandoffBundleService
                                .ENDPOINT_CATALOG_SNAPSHOT_FREEZE_EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerHandoffBundleService
                                .ENDPOINT_CATALOG_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerHandoffBundleService.EVIDENCE_PATH
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
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT,
                        OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT
                );
    }
}
