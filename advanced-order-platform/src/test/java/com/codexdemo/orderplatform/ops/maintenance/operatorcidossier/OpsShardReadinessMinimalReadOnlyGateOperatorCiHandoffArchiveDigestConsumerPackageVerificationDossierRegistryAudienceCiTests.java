package com.codexdemo.orderplatform.ops.maintenance.operatorcidossier;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryAudienceCiTests {

  @Test
  void carriesAudienceRoutesForAllConsumerPackets() {
    var response = DossierTestData.registry();

    assertThat(response.audienceRouteCount()).isEqualTo(4);
    assertThat(response.readyAudienceRouteCount()).isEqualTo(4);
    assertThat(response.audienceRoutes())
        .allSatisfy(
            route -> {
              assertThat(route.ready()).isTrue();
              assertThat(route.status()).isEqualTo("passed");
              assertThat(route.reviewerLane()).isNotBlank();
            });
  }

  @Test
  void preservesFocusedGroupedBuildSmokeCiLaneOrder() {
    var response = DossierTestData.registry();

    assertThat(response.ciLaneCount()).isEqualTo(5);
    assertThat(response.readOnlyCiLaneCount()).isEqualTo(5);
    assertThat(response.ciLanes())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .CiLane
                ::commandFamily)
        .containsExactly("focused", "focused", "grouped", "build", "smoke");
    assertThat(response.ciLanes())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .CiLane
                ::replayGroup)
        .contains(
            "focused-preflight",
            "grouped-non-docker-regression",
            "package-build",
            "read-only-smoke");
  }
}
