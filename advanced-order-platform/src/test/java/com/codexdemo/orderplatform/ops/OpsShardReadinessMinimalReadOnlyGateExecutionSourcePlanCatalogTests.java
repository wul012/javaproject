package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateExecutionSourcePlanCatalogTests {

    @Test
    void capturesNodeV349ThroughV367Lineage() {
        var sourcePlans = OpsShardReadinessMinimalReadOnlyGateExecutionSourcePlanCatalog.sourcePlans();

        assertThat(sourcePlans).hasSize(
                OpsShardReadinessMinimalReadOnlyGateExecutionRegistrySupport
                        .EXPECTED_SOURCE_PLAN_COUNT);
        assertThat(sourcePlans)
                .extracting(OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse
                        .SourcePlanEntry::nodeVersion)
                .containsExactly("Node v349", "Node v364", "Node v365", "Node v366", "Node v367");
        assertThat(sourcePlans.get(4).result())
                .isEqualTo("5/5 read targets and 20/20 checks passed");
        assertThat(sourcePlans.get(4).expectedChecks()).isEqualTo(20);
        assertThat(sourcePlans.get(4).passedChecks()).isEqualTo(20);
    }
}
