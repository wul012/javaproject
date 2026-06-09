package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateExecutionMarkdownBoundaryTests {

    @Test
    void aggregateChecksRemainStableAndBoundaryFocused() {
        var response = OpsShardReadinessMinimalReadOnlyGateExecutionRegistryTestSupport.registry();

        assertThat(response.checks()).hasSize(20);
        assertThat(response.checks()).contains(
                "minimal-read-only-gate-execution-source-plan-Node v367",
                "minimal-read-only-gate-execution-read-target-count-5",
                "minimal-read-only-gate-execution-passed-read-target-count-5",
                "minimal-read-only-gate-execution-gate-check-count-20",
                "minimal-read-only-gate-execution-passed-gate-check-count-20",
                "minimal-read-only-gate-execution-no-upstream-autostart",
                "minimal-read-only-gate-execution-no-write-routing",
                "minimal-read-only-gate-execution-no-secret-value",
                "minimal-read-only-gate-execution-no-raw-endpoint-resolution",
                "minimal-read-only-gate-execution-no-managed-audit-http"
        );
        assertThat(response.archiveRequirementCount()).isEqualTo(
                OpsShardReadinessMinimalReadOnlyGateExecutionRegistrySupport
                        .EXPECTED_ARCHIVE_REQUIREMENT_COUNT);
        assertThat(response.operatorHandoffCount()).isEqualTo(
                OpsShardReadinessMinimalReadOnlyGateExecutionRegistrySupport
                        .EXPECTED_OPERATOR_HANDOFF_COUNT);
        assertThat(response.ciBatchCount()).isEqualTo(
                OpsShardReadinessMinimalReadOnlyGateExecutionRegistrySupport
                        .EXPECTED_CI_BATCH_COUNT);
    }
}
