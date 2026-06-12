package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReadabilityUpkeepRegistryControllerTests {

    @Test
    void registryRouteExposesReadabilityEvidence() {
        var response = new ReadabilityUpkeepRegistryController(
                ReadabilityUpkeepRegistryTestSupport.service())
                .registry();

        assertThat(response.endpoint()).isEqualTo("/api/v1/ops/readability/upkeep-registry");
        assertThat(response.version()).isEqualTo("Java v1783");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.packageRoot())
                .isEqualTo("com.codexdemo.orderplatform.ops.maintenance.readability");
    }
}
