package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupEvidenceAnalyzerTests {

    @Test
    void centralizesReadOnlyBoundaryAndSegmentChecks() {
        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion()).isGreaterThanOrEqualTo(326);
        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel())
                .isEqualTo("Java v" + OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion());
        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.versionsAreContinuous()).isTrue();
        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.allEntriesKeepReadOnlyBoundary()).isTrue();
        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus()).isEqualTo("passed");
        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.segmentFor(
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.entries().getLast()
        )).isEqualTo("handoff-suite");
        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.entries())
                .anySatisfy(entry -> assertThat(entry.phase()).isEqualTo("handoff-suite-evidence-analyzer"));
    }
}
