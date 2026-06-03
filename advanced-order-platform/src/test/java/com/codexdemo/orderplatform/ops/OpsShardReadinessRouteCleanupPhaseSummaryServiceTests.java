package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupPhaseSummaryServiceTests {

    @Test
    void groupsRouteCleanupEvidenceByStableSegments() {
        OpsShardReadinessRouteCleanupPhaseSummaryResponse summary =
                new OpsShardReadinessRouteCleanupPhaseSummaryService().summary();

        assertThat(summary.project()).isEqualTo("advanced-order-platform");
        assertThat(summary.version()).isEqualTo("Java v327");
        assertThat(summary.readOnly()).isTrue();
        assertThat(summary.executionAllowed()).isFalse();
        assertThat(summary.summaryProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-phase-summary.v1");
        assertThat(summary.entryCount()).isEqualTo(22);
        assertThat(summary.phaseCount()).isEqualTo(summary.phases().size());
        assertThat(summary.phases())
                .extracting(OpsShardReadinessRouteCleanupPhaseSummaryResponse.PhaseSummary::segment)
                .contains(
                        "contract-freeze",
                        "latest-sibling",
                        "readiness-handoff",
                        "handoff-suite"
                );
        assertThat(summary.phases().getLast().segment()).isEqualTo("handoff-suite");
        assertThat(summary.phases().getLast().firstJavaVersion()).isEqualTo(326);
        assertThat(summary.phases().getLast().lastJavaVersion()).isEqualTo(327);
        assertThat(summary.phases().getLast().sourceNodePlans()).containsExactly("Node v549");
        assertThat(summary.phases())
                .allSatisfy(phase -> {
                    assertThat(phase.readOnly()).isTrue();
                    assertThat(phase.executionAllowed()).isFalse();
                    assertThat(phase.status()).isEqualTo("passed");
                });
        assertThat(summary.status()).isEqualTo("passed");
    }
}
