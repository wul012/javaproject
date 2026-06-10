package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryControllerMarkdownAggregateTests {

    @Test
    void registryRouteExposesConsumerPackageEvidence() {
        assertThat(OpsShardReadinessRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_REGISTRY)
                .isEqualTo("/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-registry");

        var response =
                new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryController(
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryTestSupport
                                .service())
                        .registry();

        assertThat(response.version()).isEqualTo("Java v1432");
        assertThat(response.sourceDigestVersion()).isEqualTo("Java v1402");
        assertThat(response.executionAllowed()).isFalse();
    }

    @Test
    void rendersStableConsumerPackageMarkdownSectionsAndChecks() {
        var response =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryTestSupport
                        .registry();

        assertThat(response.markdownSectionCount()).isEqualTo(9);
        assertThat(response.markdownSections())
                .extracting(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                        .MarkdownSection::heading)
                .containsExactly(
                        "Source Digest",
                        "Manifest",
                        "Consumer Audiences",
                        "Package Sections",
                        "Acceptance Criteria",
                        "CI Matrix",
                        "Boundary Locks",
                        "Handoff Checklist",
                        "Scorecard"
                );
        assertThat(response.checks()).hasSize(28);
        assertThat(response.checks()).contains(
                "minimal-read-only-gate-operator-ci-handoff-consumer-package-source-digest-version-Java v1402",
                "minimal-read-only-gate-operator-ci-handoff-consumer-package-manifest-count-5",
                "minimal-read-only-gate-operator-ci-handoff-consumer-package-ci-matrix-count-5",
                "minimal-read-only-gate-operator-ci-handoff-consumer-package-boundary-lock-count-8",
                "minimal-read-only-gate-operator-ci-handoff-consumer-package-no-upstream-autostart",
                "minimal-read-only-gate-operator-ci-handoff-consumer-package-no-write-routing",
                "minimal-read-only-gate-operator-ci-handoff-consumer-package-no-secret-value"
        );
    }
}
