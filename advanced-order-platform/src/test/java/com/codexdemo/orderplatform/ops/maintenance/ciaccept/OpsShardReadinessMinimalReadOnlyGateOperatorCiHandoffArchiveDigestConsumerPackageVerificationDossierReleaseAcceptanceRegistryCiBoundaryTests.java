package com.codexdemo.orderplatform.ops.maintenance.ciaccept;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryCiBoundaryTests {

  @Test
  void preservesReadOnlyCiReplayLaneOrder() {
    var response = ReleaseAcceptanceTestData.registry();

    assertThat(response.ciReplayLaneCount()).isEqualTo(5);
    assertThat(response.readOnlyCiReplayLaneCount()).isEqualTo(5);
    assertThat(response.ciReplayLanes())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                    .CiReplayLane
                ::commandFamily)
        .containsExactly("focused", "focused", "grouped", "build", "smoke");
  }

  @Test
  void carriesBoundaryControlsWithoutOpeningRuntime() {
    var response = ReleaseAcceptanceTestData.registry();

    assertThat(response.boundaryControlCount()).isEqualTo(8);
    assertThat(response.lockedBoundaryControlCount()).isEqualTo(8);
    assertThat(response.boundaryControls())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                    .BoundaryControl
                ::code)
        .contains("no-java-autostart", "no-mini-kv-autostart", "no-write-routing");
    assertThat(response.boundaryControls())
        .allSatisfy(
            control -> {
              assertThat(control.locked()).isTrue();
              assertThat(control.status()).isEqualTo("passed");
            });
  }
}
