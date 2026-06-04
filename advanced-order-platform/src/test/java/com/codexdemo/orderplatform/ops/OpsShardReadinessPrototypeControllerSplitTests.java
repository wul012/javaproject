package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

class OpsShardReadinessPrototypeControllerSplitTests {

    @Test
    void keepsPrototypeRoutesInDedicatedController() {
        assertThat(OpsShardReadinessPrototypeController.class)
                .hasAnnotation(RestController.class);

        assertThat(getMappings(OpsShardReadinessPrototypeController.class))
                .containsExactlyInAnyOrder(
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CATALOG,
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_FIXTURE_ECHO,
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_FIELD_ALIGNMENT,
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_READ_ONLY_INTEGRATION_BRIDGE,
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_ROUTE_CLEANUP_BRIDGE,
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_READ_WINDOW_HANDOFF
                );
        assertThat(getMappings(OpsShardReadinessController.class))
                .doesNotContain(
                        "/shard-readiness/prototype-catalog",
                        "/shard-readiness/prototype-fixture-echo",
                        "/shard-readiness/prototype-field-alignment",
                        "/shard-readiness/prototype-read-only-integration-bridge",
                        "/shard-readiness/prototype-route-cleanup-bridge",
                        "/shard-readiness/prototype-read-window-handoff"
                );
    }

    private static String[] getMappings(Class<?> controllerClass) {
        return Arrays.stream(controllerClass.getDeclaredMethods())
                .map(method -> method.getAnnotation(GetMapping.class))
                .filter(mapping -> mapping != null && mapping.value().length > 0)
                .map(mapping -> mapping.value()[0])
                .toArray(String[]::new);
    }
}
