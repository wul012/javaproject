package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

class OpsShardReadinessRouteCleanupEvidenceControllerSplitTests {

    @Test
    void keepsRouteCleanupEvidenceInDedicatedController() {
        assertThat(OpsShardReadinessRouteCleanupEvidenceController.class)
                .hasAnnotation(RestController.class);
        assertThat(getMappings(OpsShardReadinessRouteCleanupEvidenceController.class))
                .containsExactly("/route-cleanup-evidence-catalog");
        assertThat(getMappings(OpsShardReadinessRouteCleanupSummaryController.class))
                .contains(
                        "/route-cleanup-phase-summary",
                        "/route-cleanup-digest",
                        "/route-cleanup-source-plan-alignment"
                );
        assertThat(getMappings(OpsShardReadinessRouteCleanupGovernanceController.class))
                .containsExactly("/route-cleanup-boundary-matrix");
        assertThat(getMappings(OpsShardReadinessRouteCleanupHandoffController.class))
                .contains(
                        "/route-cleanup-handoff-checklist",
                        "/route-cleanup-archive-plan",
                        "/route-cleanup-release-handoff"
                );
        assertThat(getMappings(OpsShardReadinessV1ContractController.class))
                .doesNotContain("/route-cleanup-evidence-catalog");
    }

    private static String[] getMappings(Class<?> controllerClass) {
        return Arrays.stream(controllerClass.getDeclaredMethods())
                .map(method -> method.getAnnotation(GetMapping.class))
                .filter(mapping -> mapping != null && mapping.value().length > 0)
                .map(mapping -> mapping.value()[0])
                .toArray(String[]::new);
    }
}
