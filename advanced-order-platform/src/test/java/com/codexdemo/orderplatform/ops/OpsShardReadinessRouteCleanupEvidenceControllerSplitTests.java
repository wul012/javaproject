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
                        "/route-cleanup-source-plan-alignment",
                        "/route-cleanup-endpoint-manifest",
                        "/route-cleanup-continuity-report",
                        "/route-cleanup-final-digest"
                );
        assertThat(getMappings(OpsShardReadinessRouteCleanupGovernanceController.class))
                .contains(
                        "/route-cleanup-boundary-matrix",
                        "/route-cleanup-operator-runbook",
                        "/route-cleanup-read-only-gate",
                        "/route-cleanup-ci-evidence",
                        "/route-cleanup-regression-guard",
                        "/route-cleanup-policy-guard"
                );
        assertThat(getMappings(OpsShardReadinessRouteCleanupHandoffController.class))
                .contains(
                        "/route-cleanup-handoff-checklist",
                        "/route-cleanup-archive-plan",
                        "/route-cleanup-release-handoff",
                        "/route-cleanup-suite-closeout",
                        "/route-cleanup-archive-verification",
                        "/route-cleanup-consumer-packet",
                        "/route-cleanup-handoff-bundle",
                        "/route-cleanup-consumer-checklist",
                        "/route-cleanup-extended-closeout"
                );
        assertThat(getMappings(OpsShardReadinessRouteCleanupAssuranceController.class))
                .contains(
                        "/route-cleanup-audit-trail",
                        "/route-cleanup-acceptance-receipt",
                        "/route-cleanup-evidence-register",
                        "/route-cleanup-operational-snapshot"
                );
        assertThat(getMappings(OpsShardReadinessRouteCleanupCompletionController.class))
                .contains(
                        "/route-cleanup-reviewer-packet",
                        "/route-cleanup-transition-brief",
                        "/route-cleanup-final-verification",
                        "/route-cleanup-final-archive-plan"
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
