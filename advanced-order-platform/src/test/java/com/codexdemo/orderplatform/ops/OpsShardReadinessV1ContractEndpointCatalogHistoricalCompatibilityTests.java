package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceTestSupport;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractEndpointCatalogHistoricalCompatibilityTests {

  @Test
  void v208CatalogSnapshotDoesNotSelfIncludeOrBackfillOlderRegistries() {
    OpsShardReadinessV1ContractEndpointCatalogResponse catalog =
        OpsShardReadinessV1ContractEndpointCatalogSnapshot.v208Catalog();

    assertThat(catalog.endpoints())
        .extracting(OpsShardReadinessV1ContractEndpointCatalogResponse.EndpointEntry::liveEndpoint)
        .containsExactly(
            OpsShardReadinessV1ContractAlignmentService.ENDPOINT,
            OpsShardReadinessV1ContractAlignmentHandoffService.ENDPOINT,
            OpsShardReadinessV1ContractEvidencePacketService.ENDPOINT,
            OpsShardReadinessV1ContractOperatorChecklistService.ENDPOINT,
            OpsShardReadinessV1ContractHandoffManifestService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerProbePlanService.ENDPOINT)
        .doesNotContain(
            OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT,
            OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT);
    assertThat(catalog.endpoints())
        .extracting(
            OpsShardReadinessV1ContractEndpointCatalogResponse.EndpointEntry::fixtureEndpoint)
        .doesNotContain(
            OpsShardReadinessV1ContractEndpointCatalogService.FIXTURE_ENDPOINT,
            OpsShardReadinessReadOnlyEvidenceCatalogService.FIXTURE_ENDPOINT);

    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT);
  }

  @Test
  void rollingRegistryPositionsV208CatalogAfterConsumerProbePlan() {
    assertThat(OpsShardReadinessEvidenceEndpoints.liveEndpoints())
        .containsSubsequence(
            OpsShardReadinessV1ContractConsumerProbePlanService.ENDPOINT,
            OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT,
            OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT);
    assertThat(OpsShardReadinessEvidenceEndpoints.fixtureEndpoints())
        .containsSubsequence(
            OpsShardReadinessV1ContractConsumerProbePlanService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractEndpointCatalogService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerHandoffBundleService.FIXTURE_ENDPOINT,
            OpsShardReadinessReadOnlyEvidenceCatalogService.FIXTURE_ENDPOINT);
  }
}
