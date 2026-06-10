package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutCatalogTests {

    @Test
    void closeoutConsumesSplitRegistryAndKeepsParallelPlanReadOnly() {
        var response = OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutTestSupport.closeout();

        assertThat(response.version()).isEqualTo("Java v1579");
        assertThat(response.sourceSplitVersion()).isEqualTo("Java v1570");
        assertThat(response.sourcePlan()).isEqualTo("Node v1846");
        assertThat(response.nodeParallelPlan()).isEqualTo("Node v1847-v1866");
        assertThat(response.routePathCount()).isEqualTo(11);
        assertThat(response.compatibilityCheckCount()).isEqualTo(11);
        assertThat(response.closeoutItemCount()).isEqualTo(6);
        assertThat(response.boundaryAssertionCount()).isEqualTo(7);
        assertThat(response.status()).isEqualTo("passed");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
    }

    @Test
    void closeoutItemsAndBoundariesRemainPassed() {
        var response = OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutTestSupport.closeout();

        assertThat(response.closeoutItems())
                .allSatisfy(item -> assertThat(item.passed()).isTrue());
        assertThat(response.closeoutItems())
                .extracting(OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse.CloseoutItem::item)
                .contains(
                        "stable-barrel-preserved",
                        "compatibility-catalog-extracted",
                        "future-route-owner-rule"
                );
        assertThat(response.boundaryAssertions())
                .allSatisfy(boundary -> assertThat(boundary.locked()).isTrue());
    }
}
