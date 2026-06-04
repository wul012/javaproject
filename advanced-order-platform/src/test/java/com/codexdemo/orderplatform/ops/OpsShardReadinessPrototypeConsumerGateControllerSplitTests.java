package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

class OpsShardReadinessPrototypeConsumerGateControllerSplitTests {

    @Test
    void keepsPrototypeConsumerGateRoutesInDedicatedController() {
        assertThat(OpsShardReadinessPrototypeConsumerGateController.class)
                .hasAnnotation(RestController.class);

        assertThat(getMappings(OpsShardReadinessPrototypeConsumerGateController.class))
                .containsExactly(
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_CATALOG
                );
        assertThat(getMappings(OpsShardReadinessController.class))
                .doesNotContain("/shard-readiness/prototype-consumer-gate-catalog");
        assertThat(getMappings(OpsShardReadinessPrototypeHandoffController.class))
                .doesNotContain(OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_CATALOG);
    }

    private static String[] getMappings(Class<?> controllerClass) {
        return Arrays.stream(controllerClass.getDeclaredMethods())
                .map(method -> method.getAnnotation(GetMapping.class))
                .filter(mapping -> mapping != null && mapping.value().length > 0)
                .map(mapping -> mapping.value()[0])
                .toArray(String[]::new);
    }
}
