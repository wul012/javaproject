package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReadabilityUpkeepRoutePathsTests {

    @Test
    void buildsReadabilityUpkeepEndpoint() {
        assertThat(ReadabilityUpkeepRoutePaths.BASE_PATH)
                .isEqualTo("/api/v1/ops/readability");
        assertThat(ReadabilityUpkeepRoutePaths.UPKEEP_REGISTRY)
                .isEqualTo("/upkeep-registry");
        assertThat(ReadabilityUpkeepRegistryService.ENDPOINT)
                .isEqualTo("/api/v1/ops/readability/upkeep-registry");
    }
}
