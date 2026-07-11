package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.OpsShardReadinessEvidenceEndpointsTestSupport;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceTestSupport;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerHandoffBundleHistoricalCompatibilityTests {

  @Test
  void v211BundleDoesNotBackfillV208CatalogOrOlderRegistries() {
    OpsShardReadinessV1ContractEndpointCatalogResponse catalog =
        OpsShardReadinessV1ContractEndpointCatalogSnapshot.v208Catalog();
    OpsShardReadinessV1ContractConsumerHandoffBundleResponse bundle =
        OpsShardReadinessV1ContractConsumerHandoffBundleSnapshot.v211Bundle();

    assertThat(catalog.endpoints())
        .extracting(OpsShardReadinessV1ContractEndpointCatalogResponse.EndpointEntry::liveEndpoint)
        .doesNotContain(OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT);
    assertThat(catalog.endpoints())
        .extracting(
            OpsShardReadinessV1ContractEndpointCatalogResponse.EndpointEntry::fixtureEndpoint)
        .doesNotContain(OpsShardReadinessV1ContractConsumerHandoffBundleService.FIXTURE_ENDPOINT);

    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT);

    assertThat(bundle.requiredEvidence())
        .contains(
            OpsShardReadinessV1ContractEndpointCatalogService.EVIDENCE_PATH,
            OpsShardReadinessV1ContractConsumerHandoffBundleService
                .ENDPOINT_CATALOG_SNAPSHOT_FREEZE_EVIDENCE_PATH,
            OpsShardReadinessV1ContractConsumerHandoffBundleService
                .ENDPOINT_CATALOG_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH)
        .doesNotContain(
            "e/212/evidence/java-shard-readiness-v211-consumer-handoff-bundle-snapshot-freeze-v212.json");
  }

  @Test
  void rollingRegistryPositionsV211BundleAfterEndpointCatalog() {
    assertThat(OpsShardReadinessEvidenceEndpointsTestSupport.liveEndpoints())
        .containsSubsequence(
            OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT,
            OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT);
    assertThat(OpsShardReadinessEvidenceEndpointsTestSupport.fixtureEndpoints())
        .containsSubsequence(
            OpsShardReadinessV1ContractEndpointCatalogService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerHandoffBundleService.FIXTURE_ENDPOINT,
            OpsShardReadinessReadOnlyEvidenceCatalogService.FIXTURE_ENDPOINT);
  }
}
