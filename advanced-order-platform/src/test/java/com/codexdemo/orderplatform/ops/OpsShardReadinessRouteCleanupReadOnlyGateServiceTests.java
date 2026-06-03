package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupReadOnlyGateServiceTests {

    @Test
    void buildsReadOnlyConsumerHandoffGate() {
        OpsShardReadinessRouteCleanupReadOnlyGateResponse gate =
                new OpsShardReadinessRouteCleanupReadOnlyGateService(
                        new OpsShardReadinessRouteCleanupReleaseHandoffService(
                                new OpsShardReadinessRouteCleanupHandoffChecklistService(
                                        new OpsShardReadinessRouteCleanupPhaseSummaryService(),
                                        new OpsShardReadinessRouteCleanupBoundaryMatrixService()
                                ),
                                new OpsShardReadinessRouteCleanupArchivePlanService(),
                                new OpsShardReadinessRouteCleanupDigestService(),
                                new OpsShardReadinessRouteCleanupSourcePlanAlignmentService()
                        ),
                        new OpsShardReadinessRouteCleanupOperatorRunbookService()
                ).gate();

        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion()).isGreaterThanOrEqualTo(343);
        assertThat(gate.project()).isEqualTo("advanced-order-platform");
        assertThat(gate.version())
                .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(gate.readOnly()).isTrue();
        assertThat(gate.executionAllowed()).isFalse();
        assertThat(gate.gateEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-read-only-gate");
        assertThat(gate.gateProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-read-only-gate.v1");
        assertThat(gate.releaseHandoffEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-release-handoff");
        assertThat(gate.operatorRunbookEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-operator-runbook");
        assertThat(gate.gateCheckCount()).isEqualTo(4);
        assertThat(gate.gateChecks())
                .extracting(OpsShardReadinessRouteCleanupReadOnlyGateResponse.GateCheck::name)
                .containsExactly(
                        "catalog-continuity",
                        "release-handoff-passed",
                        "operator-runbook-passed",
                        "runtime-execution-disabled"
                );
        assertThat(gate.gateChecks())
                .allSatisfy(check -> {
                    assertThat(check.passed()).isTrue();
                    assertThat(check.status()).isEqualTo("passed");
                });
        assertThat(gate.decision()).isEqualTo("ready-for-read-only-consumer-handoff");
        assertThat(gate.status()).isEqualTo("passed");
    }
}
