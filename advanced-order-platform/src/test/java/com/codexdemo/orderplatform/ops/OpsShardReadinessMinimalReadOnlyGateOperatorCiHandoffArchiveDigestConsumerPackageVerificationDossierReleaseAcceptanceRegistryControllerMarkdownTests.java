package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.ciaccept.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse;
import com.codexdemo.orderplatform.ops.maintenance.ciaccept.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryTestSupport;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryControllerMarkdownTests {

  @Test
  void registryRouteExposesReleaseAcceptanceEvidence() {
    assertThat(
            OpsShardReadinessReleaseAcceptanceRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_REGISTRY)
        .isEqualTo(
            "/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-registry");

    var response =
        new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryController(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryTestSupport
                    .service())
            .registry();

    assertThat(response.version()).isEqualTo("Java v1502");
    assertThat(response.sourceDossierVersion()).isEqualTo("Java v1467");
    assertThat(response.executionAllowed()).isFalse();
  }

  @Test
  void rendersStableReleaseAcceptanceMarkdownSectionsAndChecks() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryTestSupport
            .registry();

    assertThat(response.markdownSectionCount()).isEqualTo(10);
    assertThat(response.markdownSections())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                    .MarkdownSection
                ::heading)
        .containsExactly(
            "Source Dossier",
            "Readiness Gates",
            "Evidence Chain",
            "Signoff Lanes",
            "CI Replay Lanes",
            "Boundary Controls",
            "Retention Policies",
            "Replay Decisions",
            "Closeout Checkpoints",
            "Scorecard");
    assertThat(response.checks()).hasSize(33);
    assertThat(response.checks())
        .contains(
            "minimal-read-only-gate-operator-ci-handoff-release-acceptance-source-dossier-version-Java v1467",
            "minimal-read-only-gate-operator-ci-handoff-release-acceptance-readiness-gate-count-6",
            "minimal-read-only-gate-operator-ci-handoff-release-acceptance-ci-replay-lane-count-5",
            "minimal-read-only-gate-operator-ci-handoff-release-acceptance-boundary-control-count-8",
            "minimal-read-only-gate-operator-ci-handoff-release-acceptance-consumes-verification-dossier",
            "minimal-read-only-gate-operator-ci-handoff-release-acceptance-no-runtime-execution",
            "minimal-read-only-gate-operator-ci-handoff-release-acceptance-no-deployment-rollback");
  }
}
