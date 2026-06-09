package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateExecutionRendererTests {

    @Test
    void rendersStableMarkdownSectionsForOperatorArchive() {
        var response = OpsShardReadinessMinimalReadOnlyGateExecutionRegistryTestSupport.registry();

        assertThat(response.markdownSections())
                .extracting(OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse
                        .MarkdownSection::heading)
                .containsExactly(
                        "Read Targets",
                        "Gate Checks",
                        "Boundary Rules",
                        "CI Batches",
                        "Archive Requirements",
                        "Operator Handoff"
                );
        assertThat(response.markdownSections().get(0).lines().get(0))
                .isEqualTo("read-target-count=5");
        assertThat(response.markdownSections().get(1).lines().get(0))
                .isEqualTo("gate-check-count=20");
        assertThat(response.markdownSections().get(2).lines())
                .anySatisfy(line -> assertThat(line).contains("no-write-routing", "allowed=false"));
        assertThat(response.markdownSections().get(5).lines())
                .anySatisfy(line -> assertThat(line).contains("stop-on-invalid-read-contract"));
    }
}
