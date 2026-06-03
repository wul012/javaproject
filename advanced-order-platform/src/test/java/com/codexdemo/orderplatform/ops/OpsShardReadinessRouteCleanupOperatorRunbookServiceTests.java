package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupOperatorRunbookServiceTests {

    @Test
    void buildsReadOnlyOperatorRunbookForRouteCleanupHandoffSuite() {
        OpsShardReadinessRouteCleanupOperatorRunbookResponse runbook =
                new OpsShardReadinessRouteCleanupOperatorRunbookService().runbook();

        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion()).isGreaterThanOrEqualTo(341);
        assertThat(runbook.project()).isEqualTo("advanced-order-platform");
        assertThat(runbook.version())
                .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(runbook.readOnly()).isTrue();
        assertThat(runbook.executionAllowed()).isFalse();
        assertThat(runbook.runbookProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-operator-runbook.v1");
        assertThat(runbook.stepCount()).isEqualTo(5);
        assertThat(runbook.steps())
                .extracting(OpsShardReadinessRouteCleanupOperatorRunbookResponse.RunbookStep::name)
                .containsExactly(
                        "read-catalog",
                        "review-phase-summary",
                        "review-boundary-matrix",
                        "review-release-handoff",
                        "export-archive-plan"
                );
        assertThat(runbook.steps())
                .allSatisfy(step -> {
                    assertThat(step.allowed()).isTrue();
                    assertThat(step.action()).startsWith("GET ");
                    assertThat(step.status()).isEqualTo("passed");
                });
        assertThat(runbook.blockedOperations())
                .contains("write-routing", "managed-audit-connection", "node-start-or-stop-java-or-mini-kv");
        assertThat(runbook.status()).isEqualTo("passed");
    }
}
