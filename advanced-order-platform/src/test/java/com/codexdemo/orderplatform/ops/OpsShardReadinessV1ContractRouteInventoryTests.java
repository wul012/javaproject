package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;

class OpsShardReadinessV1ContractRouteInventoryTests {

    @Test
    void keepsControllerRoutesAlignedWithV1EndpointPairs() {
        List<String> controllerRoutes = controllerRoutes();
        List<String> endpointPairRoutes = OpsShardReadinessV1ContractEndpointPairs.liveEndpoints().stream()
                .map(endpoint -> endpoint.substring(OpsShardReadinessRoutePaths.BASE_PATH.length()))
                .toList();

        assertThat(controllerRoutes)
                .hasSize(10)
                .doesNotHaveDuplicates()
                .allSatisfy(route -> assertThat(route).startsWith("/v1-contract-"))
                .containsExactlyInAnyOrderElementsOf(endpointPairRoutes);
        assertThat(OpsShardReadinessV1ContractEndpointPairs.fixtureEndpoints())
                .hasSize(controllerRoutes.size())
                .doesNotHaveDuplicates()
                .allSatisfy(endpoint -> assertThat(endpoint).contains("v1-contract-"));
    }

    @Test
    void keepsV1RouteConstantsFocusedAndDistinctFromNonContractGroups() {
        assertThat(List.of(
                OpsShardReadinessRoutePaths.V1_CONTRACT_ALIGNMENT,
                OpsShardReadinessRoutePaths.V1_CONTRACT_ALIGNMENT_HANDOFF,
                OpsShardReadinessRoutePaths.V1_CONTRACT_EVIDENCE_PACKET,
                OpsShardReadinessRoutePaths.V1_CONTRACT_OPERATOR_CHECKLIST,
                OpsShardReadinessRoutePaths.V1_CONTRACT_HANDOFF_MANIFEST,
                OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_PROBE_PLAN,
                OpsShardReadinessRoutePaths.V1_CONTRACT_ENDPOINT_CATALOG,
                OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_HANDOFF_BUNDLE,
                OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_VERIFICATION_CHECKLIST,
                OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_EVIDENCE_DIGEST
        ))
                .hasSize(10)
                .doesNotHaveDuplicates()
                .allSatisfy(route -> assertThat(route).startsWith("/v1-contract-"))
                .doesNotContain(
                        OpsShardReadinessRoutePaths.READ_ONLY_EVIDENCE_CATALOG,
                        OpsShardReadinessRoutePaths.EVIDENCE_INDEX
                );
    }

    private static List<String> controllerRoutes() {
        return Arrays.stream(OpsShardReadinessV1ContractController.class.getDeclaredMethods())
                .map(method -> method.getAnnotation(GetMapping.class))
                .filter(mapping -> mapping != null && mapping.value().length > 0)
                .map(mapping -> mapping.value()[0])
                .toList();
    }
}
