package com.codexdemo.orderplatform.ops.maintenance.ciarc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryRouteOperatorTests {

  @Test
  void carriesRoutePackagesFromReleaseAcceptanceSignoffLanes() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryTestSupport
            .registry();

    assertThat(response.routePackageCount()).isEqualTo(4);
    assertThat(response.readyRoutePackageCount()).isEqualTo(4);
    assertThat(response.routePackages())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                    .RoutePackageEntry
                ::receiver)
        .containsExactly(
            "operator-ci-handoff-owner",
            "node-v368-archive-verifier",
            "node-v369-operator-ci",
            "java-read-only-boundary-owner");
    assertThat(response.routePackages())
        .allSatisfy(
            route -> {
              assertThat(route.ready()).isTrue();
              assertThat(route.status()).isEqualTo("passed");
            });
  }

  @Test
  void buildsOperatorPacksWithStableOrdering() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryTestSupport
            .registry();

    assertThat(response.operatorPackCount()).isEqualTo(4);
    assertThat(response.readyOperatorPackCount()).isEqualTo(4);
    assertThat(response.operatorPacks())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                    .OperatorPackEntry
                ::order)
        .containsExactly(1, 2, 3, 4);
    assertThat(response.operatorPacks())
        .allSatisfy(
            pack -> {
              assertThat(pack.sourceEvidence()).isNotBlank();
              assertThat(pack.ready()).isTrue();
              assertThat(pack.status()).isEqualTo("passed");
            });
  }
}
