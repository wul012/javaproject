package com.codexdemo.orderplatform.ops.maintenance.ciaccept;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryEvidenceSignoffTests {

  @Test
  void carriesProvenanceEvidenceIntoReleaseTargets() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryTestSupport
            .registry();

    assertThat(response.evidenceChainEntryCount()).isEqualTo(6);
    assertThat(response.passedEvidenceChainEntryCount()).isEqualTo(6);
    assertThat(response.evidenceChain())
        .allSatisfy(
            entry -> {
              assertThat(entry.releaseTarget()).startsWith("release-acceptance:");
              assertThat(entry.passed()).isTrue();
              assertThat(entry.status()).isEqualTo("passed");
            });
  }

  @Test
  void carriesExpectedSignoffLanes() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryTestSupport
            .registry();

    assertThat(response.signoffLaneCount()).isEqualTo(4);
    assertThat(response.readySignoffLaneCount()).isEqualTo(4);
    assertThat(response.signoffLanes())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                    .SignoffLane
                ::receiver)
        .containsExactly(
            "operator-ci-handoff-owner",
            "node-v368-archive-verifier",
            "node-v369-operator-ci",
            "java-read-only-boundary-owner");
  }
}
