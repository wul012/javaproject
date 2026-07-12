package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupEvidenceAnalyzerTests {

  @Test
  void exposesStableSegmentBoundariesForSplitEvidenceCatalog() {
    assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.segments())
        .extracting(OpsShardReadinessRouteCleanupEvidenceAnalyzer.Segment::name)
        .containsExactly(
            "latest-sibling",
            "readiness-seed",
            "handoff-core",
            "handoff-assurance",
            "handoff-governance",
            "post-completion");
    assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.segments())
        .allSatisfy(
            segment -> {
              assertThat(segment.entryCount()).isGreaterThan(0);
              assertThat(segment.status()).isEqualTo("passed");
              assertThat(segment.lastJavaVersion())
                  .isGreaterThanOrEqualTo(segment.firstJavaVersion());
              assertThat(segment.sourceNodePlans()).isNotEmpty();
            });
    assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.segments())
        .satisfies(
            segments -> {
              assertThat(segments.getFirst().firstJavaVersion()).isEqualTo(306);
              assertThat(segments.getFirst().lastJavaVersion()).isEqualTo(317);
              assertThat(segments.getLast().firstJavaVersion()).isEqualTo(386);
              assertThat(segments.getLast().lastJavaVersion()).isEqualTo(408);
              assertThat(
                      segments.stream()
                          .mapToInt(
                              OpsShardReadinessRouteCleanupEvidenceAnalyzer.Segment::entryCount)
                          .sum())
                  .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.entries().size());
              for (int index = 1; index < segments.size(); index++) {
                assertThat(segments.get(index).firstJavaVersion())
                    .isEqualTo(segments.get(index - 1).lastJavaVersion() + 1);
              }
            });
  }
}
