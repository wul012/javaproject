package com.codexdemo.orderplatform.ops.maintenance.ciarc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryCiBoundaryTests {

  @Test
  void preservesReadOnlyCiAttestations() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryTestSupport
            .registry();

    assertThat(response.ciAttestationCount()).isEqualTo(5);
    assertThat(response.passedCiAttestationCount()).isEqualTo(5);
    assertThat(response.ciAttestations())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                    .CiAttestationEntry
                ::commandFamily)
        .containsExactly("focused", "focused", "grouped", "build", "smoke");
    assertThat(response.ciAttestations())
        .allSatisfy(
            attestation -> {
              assertThat(attestation.readOnly()).isTrue();
              assertThat(attestation.sourcePassed()).isTrue();
              assertThat(attestation.status()).isEqualTo("passed");
            });
  }

  @Test
  void locksBoundarySealsWithoutOpeningRuntimeExecution() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryTestSupport
            .registry();

    assertThat(response.boundarySealCount()).isEqualTo(8);
    assertThat(response.lockedBoundarySealCount()).isEqualTo(8);
    assertThat(response.boundarySeals())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                    .BoundarySealEntry
                ::code)
        .contains("no-java-autostart", "no-mini-kv-autostart", "no-write-routing");
    assertThat(response.boundarySeals())
        .allSatisfy(
            seal -> {
              assertThat(seal.locked()).isTrue();
              assertThat(seal.status()).isEqualTo("passed");
            });
  }
}
