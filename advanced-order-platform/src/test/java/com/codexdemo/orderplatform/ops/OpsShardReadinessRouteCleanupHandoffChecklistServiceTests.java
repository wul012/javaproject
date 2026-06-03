package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupHandoffChecklistServiceTests {

    @Test
    void buildsReadOnlyHandoffChecklistFromSummaryAndBoundaryMatrix() {
        OpsShardReadinessRouteCleanupHandoffChecklistResponse checklist =
                new OpsShardReadinessRouteCleanupHandoffChecklistService(
                        new OpsShardReadinessRouteCleanupPhaseSummaryService(),
                        new OpsShardReadinessRouteCleanupBoundaryMatrixService()
                ).checklist();

        assertThat(checklist.project()).isEqualTo("advanced-order-platform");
        assertThat(checklist.version())
                .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion()).isGreaterThanOrEqualTo(331);
        assertThat(checklist.readOnly()).isTrue();
        assertThat(checklist.executionAllowed()).isFalse();
        assertThat(checklist.checklistProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-handoff-checklist.v1");
        assertThat(checklist.checkCount()).isEqualTo(5);
        assertThat(checklist.checks())
                .extracting(OpsShardReadinessRouteCleanupHandoffChecklistResponse.CheckItem::name)
                .contains(
                        "catalog-continuity",
                        "phase-summary-ready",
                        "boundary-matrix-fail-closed",
                        "execution-disabled",
                        "sibling-start-disabled"
                );
        assertThat(checklist.checks())
                .allSatisfy(check -> {
                    assertThat(check.passed()).isTrue();
                    assertThat(check.status()).isEqualTo("passed");
                    assertThat(check.evidence()).isNotBlank();
                });
        assertThat(checklist.status()).isEqualTo("passed");
    }
}
