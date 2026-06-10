package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceRoutePathSplitCatalogTests {

    @Test
    void registryReturnsSplitCatalogAndSourceSnapshot() {
        var response = OpsShardReadinessReleaseAcceptanceRoutePathSplitTestSupport.registry();

        assertThat(response.version()).isEqualTo("Java v1570");
        assertThat(response.sourcePlan()).isEqualTo("Node v1846");
        assertThat(response.nodeSplitPlan()).isEqualTo("Node v1822-v1846");
        assertThat(response.sourceHandoffVersion()).isEqualTo("Java v1547");
        assertThat(response.status()).isEqualTo("passed");
        assertThat(response.sourceSnapshotCount()).isEqualTo(1);
        assertThat(response.routePathCount()).isEqualTo(11);
        assertThat(response.compatibilityCheckCount()).isEqualTo(11);
        assertThat(response.boundaryGuardCount()).isEqualTo(7);
        assertThat(response.consumerHandoffCount()).isEqualTo(5);
        assertThat(response.scorecardEntryCount()).isEqualTo(8);
    }

    @Test
    void scorecardAndBoundaryGuardsRemainPassedAndLocked() {
        var response = OpsShardReadinessReleaseAcceptanceRoutePathSplitTestSupport.registry();

        assertThat(response.boundaryGuards())
                .allSatisfy(guard -> assertThat(guard.locked()).isTrue());
        assertThat(response.consumerHandoffs())
                .extracting(OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.ConsumerHandoff::consumer)
                .contains(
                        "release-acceptance-archive-verification-handoff-service",
                        "future-release-acceptance-services",
                        "node-v1846-parallel-review"
                );
        assertThat(response.scorecard())
                .allSatisfy(entry -> assertThat(entry.passed()).isTrue());
    }
}
