package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshotTests {

  @Test
  void freezesV215ConsumerVerificationChecklistSnapshot() {
    OpsShardReadinessV1ContractConsumerHandoffBundleResponse bundle =
        OpsShardReadinessV1ContractConsumerHandoffBundleSnapshot.v211Bundle();
    OpsShardReadinessV1ContractConsumerVerificationChecklistResponse checklist =
        OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot.v215Checklist();

    assertThat(checklist.version()).isEqualTo("Java v215");
    assertThat(checklist.handoffBundleEndpoint()).isEqualTo(bundle.handoffBundleEndpoint());
    assertThat(checklist.handoffBundleReceiptId()).isEqualTo(bundle.receiptId());
    assertThat(checklist.verificationItems())
        .containsExactlyElementsOf(
            OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot
                .v215VerificationItems());
    assertThat(checklist.requiredEvidence())
        .containsExactlyElementsOf(
            OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot.v215RequiredEvidence(
                bundle));
    assertThat(checklist.verificationChecks())
        .containsExactlyElementsOf(
            OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot.v215VerificationChecks(
                bundle));
    assertThat(checklist.evidencePath())
        .isEqualTo(OpsShardReadinessV1ContractConsumerVerificationChecklistService.EVIDENCE_PATH);
  }

  @Test
  void serviceReturnsTheFrozenV215Checklist() {
    OpsShardReadinessV1ContractConsumerVerificationChecklistResponse serviceResponse =
        new OpsShardReadinessV1ContractConsumerVerificationChecklistService().checklist();

    assertThat(serviceResponse)
        .isEqualTo(
            OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot.v215Checklist());
  }
}
