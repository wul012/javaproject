package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.DossierTestData;
import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryControllerMarkdownAggregateTests {

  @Test
  void registryRouteExposesVerificationDossierEvidence() {
    assertThat(
            OpsShardReadinessReleaseAcceptanceRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_REGISTRY)
        .isEqualTo(
            "/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-registry");

    var response =
        new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryController(
                DossierTestData.service())
            .registry();

    assertThat(response.version()).isEqualTo("Java v1467");
    assertThat(response.sourceConsumerPackageVersion()).isEqualTo("Java v1432");
    assertThat(response.executionAllowed()).isFalse();
  }

  @Test
  void rendersStableVerificationDossierMarkdownSectionsAndChecks() {
    var response = DossierTestData.registry();

    assertThat(response.markdownSectionCount()).isEqualTo(10);
    assertThat(response.markdownSections())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .MarkdownSection
                ::heading)
        .containsExactly(
            "Source Consumer Package",
            "Provenance",
            "Section Digests",
            "Audience Routes",
            "CI Lanes",
            "Acceptance Gates",
            "Boundary Audits",
            "Release Checklist",
            "Handoff Receipts",
            "Scorecard");
    assertThat(response.checks()).hasSize(34);
    assertThat(response.checks())
        .contains(
            "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-source-version-Java v1432",
            "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-section-digest-count-9",
            "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-ci-lane-count-5",
            "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-boundary-audit-count-8",
            "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-consumes-consumer-package",
            "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-no-runtime-execution",
            "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-no-deployment-rollback");
  }
}
