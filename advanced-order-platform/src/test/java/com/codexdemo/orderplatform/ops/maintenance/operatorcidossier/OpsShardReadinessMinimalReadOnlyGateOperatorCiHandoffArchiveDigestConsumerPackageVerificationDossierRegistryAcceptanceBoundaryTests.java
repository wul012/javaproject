package com.codexdemo.orderplatform.ops.maintenance.operatorcidossier;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryAcceptanceBoundaryTests {

  @Test
  void carriesAcceptanceGatesWithVerificationArtifacts() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryTestSupport
            .registry();

    assertThat(response.acceptanceGateCount()).isEqualTo(5);
    assertThat(response.passedAcceptanceGateCount()).isEqualTo(5);
    assertThat(response.acceptanceGates())
        .allSatisfy(
            gate -> {
              assertThat(gate.passed()).isTrue();
              assertThat(gate.verifyingArtifact()).endsWith("-verification-dossier");
              assertThat(gate.status()).isEqualTo("passed");
            });
  }

  @Test
  void carriesBoundaryAuditsWithoutOpeningRuntimePaths() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryTestSupport
            .registry();

    assertThat(response.boundaryAuditCount()).isEqualTo(8);
    assertThat(response.lockedBoundaryAuditCount()).isEqualTo(8);
    assertThat(response.boundaryAudits())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .BoundaryAudit
                ::code)
        .contains("no-java-autostart", "no-mini-kv-autostart", "no-write-routing");
    assertThat(response.boundaryAudits())
        .allSatisfy(
            audit -> {
              assertThat(audit.locked()).isTrue();
              assertThat(audit.auditEvidence()).startsWith("consumer-package-boundary-lock:");
              assertThat(audit.status()).isEqualTo("passed");
            });
  }
}
