package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffSourceRequirementTests {

    @Test
    void buildsArchiveVerificationHandoffFromArchiveRegistry() {
        var response =
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffTestSupport.registry();

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v1547");
        assertThat(response.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/release-acceptance-archive-verification-handoff-registry");
        assertThat(response.profile()).isEqualTo(
                "java-shard-readiness-release-acceptance-archive-verification-handoff-registry.v1");
        assertThat(response.sourcePlan()).isEqualTo("Node v367");
        assertThat(response.archiveVerificationPlan()).isEqualTo("Node v368");
        assertThat(response.operatorHandoffPlan()).isEqualTo("Node v369");
        assertThat(response.sourceArchiveVersion()).isEqualTo("Java v1522");
        assertThat(response.sourceArchiveState()).isEqualTo(
                "minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-archive-registry-ready");
        assertThat(response.handoffState()).isEqualTo("release-acceptance-archive-verification-handoff-ready");
        assertThat(response.status()).isEqualTo("passed");
    }

    @Test
    void keepsArchiveVerificationHandoffStrictlyReadOnly() {
        var response =
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffTestSupport.registry();

        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.startsJavaService()).isFalse();
        assertThat(response.startsMiniKvService()).isFalse();
        assertThat(response.readsCredentialValue()).isFalse();
        assertThat(response.resolvesRawEndpointUrl()).isFalse();
        assertThat(response.managedAuditHttpAllowed()).isFalse();
    }

    @Test
    void carriesSourceSnapshotAndVerificationRequirements() {
        var response =
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffTestSupport.registry();

        assertThat(response.sourceArchiveSnapshotCount()).isEqualTo(1);
        assertThat(response.sourceArchiveSnapshots())
                .extracting(OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                        .SourceArchiveSnapshot::version)
                .containsExactly("Java v1522");
        assertThat(response.verificationRequirementCount()).isEqualTo(8);
        assertThat(response.passedVerificationRequirementCount()).isEqualTo(8);
        assertThat(response.verificationRequirements())
                .extracting(OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                        .VerificationRequirement::code)
                .containsExactly(
                        "source-archive-status",
                        "artifact-manifest-passed",
                        "route-packages-ready",
                        "operator-packs-ready",
                        "ci-attestations-passed",
                        "boundary-seals-locked",
                        "retention-windows-ready",
                        "closeout-ledger-ready"
                );
    }
}
