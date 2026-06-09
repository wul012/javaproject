package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryControllerMarkdownTests {

    @Test
    void registryRouteExposesArchiveDigestEvidence() {
        assertThat(OpsShardReadinessRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_REGISTRY)
                .isEqualTo("/minimal-read-only-gate-operator-ci-handoff-archive-digest-registry");

        var response =
                new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryController(
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryTestSupport
                                .service())
                        .registry();

        assertThat(response.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-digest-registry");
        assertThat(response.version()).isEqualTo("Java v1402");
        assertThat(response.sourceArchiveVersion()).isEqualTo("Java v1377");
        assertThat(response.executionAllowed()).isFalse();
    }

    @Test
    void rendersStableArchiveDigestMarkdownSections() {
        var response =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryTestSupport
                        .registry();

        assertThat(response.markdownSectionCount()).isEqualTo(6);
        assertThat(response.markdownSections())
                .extracting(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                        .MarkdownSection::heading)
                .containsExactly(
                        "Source Archive",
                        "Digest Sections",
                        "Consumer Packets",
                        "Replay Instructions",
                        "Boundary Locks",
                        "Scorecard"
                );
        assertThat(response.markdownSections().get(1).lines().get(0))
                .isEqualTo("digest-section-count=6");
        assertThat(response.markdownSections().get(3).lines().get(0))
                .isEqualTo("replay-instruction-count=5");
        assertThat(response.markdownSections().get(4).lines())
                .anySatisfy(line -> assertThat(line).contains("no-write-routing", "locked=true"));
    }
}
