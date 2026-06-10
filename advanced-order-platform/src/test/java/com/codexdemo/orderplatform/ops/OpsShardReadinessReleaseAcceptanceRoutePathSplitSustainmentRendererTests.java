package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentRendererTests {

    @Test
    void markdownAndChecksDescribeSustainmentContract() {
        var response = OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentTestSupport.registry();

        assertThat(response.markdownSections())
                .extracting(OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse
                        .MarkdownSection::heading)
                .containsExactly(
                        "Source Closeout",
                        "Ownership Rules",
                        "Drift Guards",
                        "Boundary Guards",
                        "CI Gates",
                        "Consumer Handoffs",
                        "Sustainment Scorecard"
                );
        assertThat(response.checks()).hasSize(30);
        assertThat(response.checks()).contains(
                "release-acceptance-route-path-split-sustainment-source-plan-Node v1878",
                "release-acceptance-route-path-split-sustainment-node-parallel-plan-Node v1867-v1878",
                "release-acceptance-route-path-split-sustainment-source-closeout-version-Java v1579",
                "release-acceptance-route-path-split-sustainment-source-split-version-Java v1570",
                "release-acceptance-route-path-split-sustainment-ownership-rule-count-6",
                "release-acceptance-route-path-split-sustainment-no-runtime-execution",
                "release-acceptance-route-path-split-sustainment-no-node-or-minikv-auto-start"
        );
    }

    @Test
    void ciRendererKeepsCommandsOutOfStatusLogic() {
        var response = OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentTestSupport.registry();

        assertThat(response.ciGates())
                .extracting(OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.CiGate::gate)
                .containsExactly(
                        "focused-sustainment-tests",
                        "related-route-path-split-tests",
                        "full-java-regression",
                        "git-diff-whitespace-check",
                        "remote-ci-confirmation"
                );
        assertThat(response.markdownSections().get(4).lines())
                .allSatisfy(line -> assertThat(line).contains("required=true"));
    }
}
