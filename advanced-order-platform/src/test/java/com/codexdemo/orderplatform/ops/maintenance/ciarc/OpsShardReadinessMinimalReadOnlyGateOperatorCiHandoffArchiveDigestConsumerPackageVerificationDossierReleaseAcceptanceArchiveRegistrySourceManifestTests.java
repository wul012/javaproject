package com.codexdemo.orderplatform.ops.maintenance.ciarc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistrySourceManifestTests {

  @Test
  void buildsArchiveRegistryFromReleaseAcceptanceRegistry() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryTestSupport
            .registry();

    assertThat(response.project()).isEqualTo("advanced-order-platform");
    assertThat(response.version()).isEqualTo("Java v1522");
    assertThat(response.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-archive-registry");
    assertThat(response.profile())
        .isEqualTo(
            "java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-archive-registry.v1");
    assertThat(response.sourcePlan()).isEqualTo("Node v367");
    assertThat(response.requiredArchiveVerificationPlan()).isEqualTo("Node v368");
    assertThat(response.operatorHandoffPlan()).isEqualTo("Node v369");
    assertThat(response.sourceReleaseAcceptanceVersion()).isEqualTo("Java v1502");
    assertThat(response.sourceReleaseAcceptanceEndpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-registry");
    assertThat(response.sourceReleaseAcceptanceState())
        .isEqualTo(
            "minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-ready");
    assertThat(response.archiveRegistryState())
        .isEqualTo(
            "minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-archive-registry-ready");
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void carriesSourceSnapshotAndArtifactManifest() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryTestSupport
            .registry();

    assertThat(response.sourceArchiveSnapshotCount()).isEqualTo(1);
    assertThat(response.sourceArchiveSnapshots())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                    .SourceArchiveSnapshot
                ::releaseAcceptanceVersion)
        .containsExactly("Java v1502");
    assertThat(response.artifactManifestCount()).isEqualTo(7);
    assertThat(response.passedArtifactManifestCount()).isEqualTo(7);
    assertThat(response.artifactManifest())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                    .ArtifactManifestEntry
                ::name)
        .containsExactly(
            "source-release-acceptance-version",
            "source-release-acceptance-state",
            "readiness-gates-passed",
            "evidence-chain-passed",
            "signoff-lanes-ready",
            "ci-replay-lanes-read-only",
            "closeout-checkpoints-ready");
  }
}
