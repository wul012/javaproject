package com.codexdemo.orderplatform.ops.maintenance.operatorcidossier;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryChecklistReceiptTests {

  @Test
  void carriesReleaseChecklistFromConsumerPackageHandoffChecklist() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryTestSupport
            .registry();

    assertThat(response.releaseChecklistCount()).isEqualTo(5);
    assertThat(response.readyReleaseChecklistCount()).isEqualTo(5);
    assertThat(response.releaseChecklist())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .ReleaseChecklistItem
                ::item)
        .containsExactly(
            "read-source-digest",
            "confirm-boundary-locks",
            "run-focused-first",
            "preserve-read-only-env",
            "archive-ci-conclusion");
  }

  @Test
  void carriesHandoffReceiptsForExpectedConsumers() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryTestSupport
            .registry();

    assertThat(response.handoffReceiptCount()).isEqualTo(4);
    assertThat(response.readyHandoffReceiptCount()).isEqualTo(4);
    assertThat(response.handoffReceipts())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .HandoffReceipt
                ::receiver)
        .containsExactly(
            "operator-ci-handoff-owner",
            "node-v368-archive-verifier",
            "node-v369-operator-ci",
            "java-read-only-boundary-owner");
  }
}
