package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerHandoffBundleSnapshotTests {

  @Test
  void freezesV211ConsumerHandoffBundleReceipt() {
    OpsShardReadinessV1ContractConsumerHandoffBundleResponse bundle =
        OpsShardReadinessV1ContractConsumerHandoffBundleSnapshot.v211Bundle();
    OpsShardReadinessV1ContractEndpointCatalogResponse catalog =
        OpsShardReadinessV1ContractEndpointCatalogSnapshot.v208Catalog();

    assertThat(bundle.version()).isEqualTo("Java v211");
    assertThat(bundle.catalogedArtifactCount()).isEqualTo(6);
    assertThat(bundle.endpointCatalogReceiptId())
        .isEqualTo("java-shard-readiness-v1-contract-endpoint-catalog-receipt-v208");
    assertThat(bundle.requiredEvidence())
        .containsExactlyElementsOf(
            OpsShardReadinessV1ContractConsumerHandoffBundleSnapshot.v211RequiredEvidence(catalog));
    assertThat(bundle.handoffEvidence())
        .containsExactlyElementsOf(
            OpsShardReadinessV1ContractConsumerHandoffBundleSnapshot.v211HandoffEvidence(catalog));
    assertThat(bundle.receiptId())
        .isEqualTo("java-shard-readiness-v1-contract-consumer-handoff-bundle-receipt-v211");
    assertThat(bundle.evidencePath())
        .isEqualTo(
            "e/211/evidence/java-shard-readiness-v1-contract-consumer-handoff-bundle-v211.json");
    assertThat(bundle.status()).isEqualTo("passed");
  }
}
