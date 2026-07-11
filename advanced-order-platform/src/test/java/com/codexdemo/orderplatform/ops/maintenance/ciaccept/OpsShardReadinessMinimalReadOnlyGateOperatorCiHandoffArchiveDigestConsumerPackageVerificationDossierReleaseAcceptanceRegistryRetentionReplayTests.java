package com.codexdemo.orderplatform.ops.maintenance.ciaccept;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryRetentionReplayTests {

  @Test
  void carriesRetentionPoliciesForReleaseEvidence() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryTestSupport
            .registry();

    assertThat(response.retentionPolicyCount()).isEqualTo(5);
    assertThat(response.readyRetentionPolicyCount()).isEqualTo(5);
    assertThat(response.retentionPolicies())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                    .RetentionPolicy
                ::name)
        .containsExactly(
            "source-dossier-snapshot",
            "provenance-chain",
            "section-digests",
            "ci-replay-lanes",
            "boundary-controls");
  }

  @Test
  void carriesReplayDecisionsWithRuntimeClosed() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryTestSupport
            .registry();

    assertThat(response.replayDecisionCount()).isEqualTo(5);
    assertThat(response.passedReplayDecisionCount()).isEqualTo(5);
    assertThat(response.replayDecisions())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                    .ReplayDecision
                ::code)
        .containsExactly(
            "focused-first", "grouped-second", "build-third", "smoke-last", "runtime-closed");
  }
}
