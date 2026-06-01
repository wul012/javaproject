package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

class OpsShardReadinessV1ContractControllerSplitTests {

    @Test
    void keepsV1ContractRoutesInDedicatedController() {
        assertThat(OpsShardReadinessV1ContractController.class)
                .hasAnnotation(RestController.class);

        assertThat(getMappings(OpsShardReadinessV1ContractController.class))
                .containsExactlyInAnyOrder(
                        OpsShardReadinessRoutePaths.V1_CONTRACT_ALIGNMENT,
                        OpsShardReadinessRoutePaths.V1_CONTRACT_ALIGNMENT_HANDOFF,
                        OpsShardReadinessRoutePaths.V1_CONTRACT_EVIDENCE_PACKET,
                        OpsShardReadinessRoutePaths.V1_CONTRACT_OPERATOR_CHECKLIST,
                        OpsShardReadinessRoutePaths.V1_CONTRACT_HANDOFF_MANIFEST,
                        OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_PROBE_PLAN,
                        OpsShardReadinessRoutePaths.V1_CONTRACT_ENDPOINT_CATALOG,
                        OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_HANDOFF_BUNDLE,
                        OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_VERIFICATION_CHECKLIST
                );
        assertThat(getMappings(OpsShardReadinessController.class))
                .doesNotContain(
                        "/shard-readiness/v1-contract-alignment",
                        "/shard-readiness/v1-contract-alignment-handoff",
                        "/shard-readiness/v1-contract-evidence-packet",
                        "/shard-readiness/v1-contract-operator-checklist",
                        "/shard-readiness/v1-contract-handoff-manifest",
                        "/shard-readiness/v1-contract-consumer-probe-plan",
                        "/shard-readiness/v1-contract-endpoint-catalog",
                        "/shard-readiness/v1-contract-consumer-handoff-bundle",
                        "/shard-readiness/v1-contract-consumer-verification-checklist"
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
