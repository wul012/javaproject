package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.ciarc.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse;
import com.codexdemo.orderplatform.ops.maintenance.ciarc.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryTestSupport;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryControllerMarkdownTests {

  @Test
  void registryRouteExposesArchiveRegistryEvidence() {
    assertThat(
            OpsShardReadinessReleaseAcceptanceRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_ARCHIVE_REGISTRY)
        .isEqualTo(
            "/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-archive-registry");

    var response =
        new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryController(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryTestSupport
                    .service())
            .registry();

    assertThat(response.version()).isEqualTo("Java v1522");
    assertThat(response.sourceReleaseAcceptanceVersion()).isEqualTo("Java v1502");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
  }

  @Test
  void rendersStableArchiveRegistryMarkdownSectionsAndChecks() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryTestSupport
            .registry();

    assertThat(response.markdownSectionCount()).isEqualTo(9);
    assertThat(response.markdownSections())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                    .MarkdownSection
                ::heading)
        .containsExactly(
            "Source Archive",
            "Artifact Manifest",
            "Route Packages",
            "Operator Packs",
            "CI Attestations",
            "Boundary Seals",
            "Retention Windows",
            "Closeout Ledger",
            "Scorecard");
    assertThat(response.checks()).hasSize(31);
    assertThat(response.checks())
        .contains(
            "minimal-read-only-gate-operator-ci-handoff-archive-registry-source-version-Java v1502",
            "minimal-read-only-gate-operator-ci-handoff-archive-registry-artifact-manifest-count-7",
            "minimal-read-only-gate-operator-ci-handoff-archive-registry-ci-attestation-count-5",
            "minimal-read-only-gate-operator-ci-handoff-archive-registry-boundary-seal-count-8",
            "minimal-read-only-gate-operator-ci-handoff-archive-registry-consumes-release-acceptance",
            "minimal-read-only-gate-operator-ci-handoff-archive-registry-no-runtime-execution",
            "minimal-read-only-gate-operator-ci-handoff-archive-registry-no-deployment-rollback");
  }
}
