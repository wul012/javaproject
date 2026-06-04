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
                .containsExactlyInAnyOrder(
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_CATALOG,
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_SOURCE_INVENTORY,
                        OpsShardReadinessRoutePaths
                                .SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_MINIMAL_FIELD_CHECKLIST,
                        OpsShardReadinessRoutePaths
                                .SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_ROUTE_TOPOLOGY_PREVIEW,
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_BOUNDARY_MATRIX,
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_DIGEST_ACCEPTANCE,
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_CI_BATCH_PLAN,
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_ARCHIVE_MANIFEST
                );
        assertThat(getMappings(OpsShardReadinessController.class))
                .doesNotContain(
                        "/shard-readiness/prototype-consumer-gate-catalog",
                        "/shard-readiness/prototype-consumer-gate-source-inventory",
                        "/shard-readiness/prototype-consumer-gate-minimal-field-checklist",
                        "/shard-readiness/prototype-consumer-gate-route-topology-preview",
                        "/shard-readiness/prototype-consumer-gate-boundary-matrix",
                        "/shard-readiness/prototype-consumer-gate-digest-acceptance",
                        "/shard-readiness/prototype-consumer-gate-ci-batch-plan",
                        "/shard-readiness/prototype-consumer-gate-archive-manifest"
                );
        assertThat(getMappings(OpsShardReadinessPrototypeHandoffController.class))
                .doesNotContain(
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_CATALOG,
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_SOURCE_INVENTORY,
                        OpsShardReadinessRoutePaths
                                .SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_MINIMAL_FIELD_CHECKLIST,
                        OpsShardReadinessRoutePaths
                                .SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_ROUTE_TOPOLOGY_PREVIEW,
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_BOUNDARY_MATRIX,
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_DIGEST_ACCEPTANCE,
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_CI_BATCH_PLAN,
                        OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_ARCHIVE_MANIFEST
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
