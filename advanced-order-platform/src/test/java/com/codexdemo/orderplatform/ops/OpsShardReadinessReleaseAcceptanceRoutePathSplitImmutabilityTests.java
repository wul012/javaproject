package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceRoutePathSplitImmutabilityTests {

    @Test
    void responseCollectionsAreImmutable() {
        var response = OpsShardReadinessReleaseAcceptanceRoutePathSplitTestSupport.registry();

        assertThatThrownBy(() -> response.routePaths().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.compatibilityChecks().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.boundaryGuards().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.consumerHandoffs().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.checks().clear())
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
