package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupSourcePlanAlignmentServiceTests {

    @Test
    void recordsCurrentNodePlanAlignmentWithoutStartingSiblingServices() {
        OpsShardReadinessRouteCleanupSourcePlanAlignmentResponse alignment =
                new OpsShardReadinessRouteCleanupSourcePlanAlignmentService().alignment();

        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion()).isGreaterThanOrEqualTo(337);
        assertThat(alignment.project()).isEqualTo("advanced-order-platform");
        assertThat(alignment.version())
                .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(alignment.readOnly()).isTrue();
        assertThat(alignment.executionAllowed()).isFalse();
        assertThat(alignment.alignmentProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-source-plan-alignment.v1");
        assertThat(alignment.sourcePlan()).isEqualTo("Node v549");
        assertThat(alignment.sourcePlanPath()).contains("docs/plans3", "v549-post-java-mini-kv-route-catalog-cleanup");
        assertThat(alignment.alignmentCount()).isEqualTo(4);
        assertThat(alignment.alignments())
                .extracting(OpsShardReadinessRouteCleanupSourcePlanAlignmentResponse.AlignmentItem::subject)
                .contains(
                        "source-plan",
                        "sibling-startup",
                        "runtime-boundary",
                        "collaboration-mode"
                );
        assertThat(alignment.alignments())
                .allSatisfy(item -> {
                    assertThat(item.aligned()).isTrue();
                    assertThat(item.status()).isEqualTo("passed");
                });
        assertThat(alignment.status()).isEqualTo("passed");
    }
}
