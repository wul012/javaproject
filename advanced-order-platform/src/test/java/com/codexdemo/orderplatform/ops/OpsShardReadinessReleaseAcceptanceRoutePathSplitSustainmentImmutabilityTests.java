package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentImmutabilityTests {

    @Test
    void sustainmentCollectionsAreImmutable() {
        var response = OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentTestSupport.registry();

        assertThatThrownBy(() -> response.sourceSnapshots().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.ownershipRules().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.driftGuards().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.boundaryGuards().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.ciGates().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.consumerHandoffs().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.scorecard().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.markdownSections().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.markdownSections().get(0).lines().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.checks().clear())
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
