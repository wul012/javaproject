package com.codexdemo.orderplatform.ops.maintenance.operatorcidossier;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistrySourceProvenanceTests {

  @Test
  void buildsVerificationDossierFromConsumerPackageRegistry() {
    var response = DossierTestData.registry();

    assertThat(response.project()).isEqualTo("advanced-order-platform");
    assertThat(response.version()).isEqualTo("Java v1467");
    assertThat(response.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-registry");
    assertThat(response.profile())
        .isEqualTo(
            "java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-registry.v1");
    assertThat(response.sourcePlan()).isEqualTo("Node v367");
    assertThat(response.requiredArchiveVerificationPlan()).isEqualTo("Node v368");
    assertThat(response.operatorHandoffPlan()).isEqualTo("Node v369");
    assertThat(response.sourceConsumerPackageVersion()).isEqualTo("Java v1432");
    assertThat(response.sourceConsumerPackageState())
        .isEqualTo(
            "minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-ready");
    assertThat(response.verificationDossierState())
        .isEqualTo(
            "minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-ready");
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void keepsDossierStrictlyReadOnly() {
    var response = DossierTestData.registry();

    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.startsJavaService()).isFalse();
    assertThat(response.startsMiniKvService()).isFalse();
    assertThat(response.readsCredentialValue()).isFalse();
    assertThat(response.resolvesRawEndpointUrl()).isFalse();
    assertThat(response.managedAuditHttpAllowed()).isFalse();
  }

  @Test
  void carriesRequiredProvenanceFields() {
    var response = DossierTestData.registry();

    assertThat(response.sourcePackageSnapshotCount()).isEqualTo(1);
    assertThat(response.provenanceEntryCount()).isEqualTo(6);
    assertThat(response.passedProvenanceEntryCount()).isEqualTo(6);
    assertThat(response.provenance())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .ProvenanceEntry
                ::name)
        .containsExactly(
            "source-consumer-package-version",
            "source-consumer-package-endpoint",
            "source-consumer-package-profile",
            "source-digest-version",
            "source-digest-state",
            "source-consumer-package-state");
  }
}
