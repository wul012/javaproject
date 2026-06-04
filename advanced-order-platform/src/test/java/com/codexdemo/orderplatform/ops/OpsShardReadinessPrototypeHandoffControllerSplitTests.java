package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

class OpsShardReadinessPrototypeHandoffControllerSplitTests {

    @Test
    void keepsPrototypeHandoffRoutesInDedicatedController() {
        assertThat(OpsShardReadinessPrototypeHandoffController.class)
                .hasAnnotation(RestController.class);

        assertThat(getMappings(OpsShardReadinessPrototypeHandoffController.class))
                .containsExactlyInAnyOrder(
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_HANDOFF_CATALOG,
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_HANDOFF_ENDPOINT_INVENTORY
                );
        assertThat(getMappings(OpsShardReadinessController.class))
                .doesNotContain(
                        "/shard-readiness/prototype-handoff-catalog",
                        "/shard-readiness/prototype-handoff-endpoint-inventory"
                );
        assertThat(getMappings(OpsShardReadinessPrototypeController.class))
                .doesNotContain(
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_HANDOFF_CATALOG,
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_HANDOFF_ENDPOINT_INVENTORY
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
