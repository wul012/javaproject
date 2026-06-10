package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistrySourceReadinessTests {

    @Test
    void buildsReleaseAcceptanceFromVerificationDossier() {
        var response =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryTestSupport
                        .registry();

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v1502");
        assertThat(response.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-registry");
        assertThat(response.profile()).isEqualTo(
                "java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-registry.v1");
        assertThat(response.sourceDossierVersion()).isEqualTo("Java v1467");
        assertThat(response.sourceDossierState())
                .isEqualTo("minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-ready");
        assertThat(response.releaseAcceptanceState())
                .isEqualTo("minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-ready");
        assertThat(response.status()).isEqualTo("passed");
    }

    @Test
    void keepsReleaseAcceptanceStrictlyReadOnly() {
        var response =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryTestSupport
                        .registry();

        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.startsJavaService()).isFalse();
        assertThat(response.startsMiniKvService()).isFalse();
        assertThat(response.readsCredentialValue()).isFalse();
        assertThat(response.resolvesRawEndpointUrl()).isFalse();
        assertThat(response.managedAuditHttpAllowed()).isFalse();
    }

    @Test
    void carriesSourceSnapshotAndReadinessGates() {
        var response =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryTestSupport
                        .registry();

        assertThat(response.sourceDossierSnapshotCount()).isEqualTo(1);
        assertThat(response.readinessGateCount()).isEqualTo(6);
        assertThat(response.passedReadinessGateCount()).isEqualTo(6);
        assertThat(response.readinessGates())
                .extracting(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                        .ReleaseReadinessGate::code)
                .containsExactly(
                        "source-dossier-status",
                        "source-package-snapshot",
                        "section-digests",
                        "audience-routes",
                        "ci-lanes",
                        "boundary-audits"
                );
    }
}
