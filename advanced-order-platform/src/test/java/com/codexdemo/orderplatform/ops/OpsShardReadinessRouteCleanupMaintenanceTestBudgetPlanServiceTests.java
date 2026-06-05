package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanServiceTests {

    @Test
    void buildsReadOnlyMaintenanceTestBudgetPlan() {
        OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanResponse plan =
                new OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanService().plan();

        assertThat(plan.version()).isEqualTo("Java v483");
        assertThat(plan.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/route-cleanup-maintenance-test-budget-plan");
        assertThat(plan.profile()).isEqualTo(
                "java-shard-readiness-route-cleanup-maintenance-test-budget-plan.v1");
        assertThat(plan.stepCount()).isEqualTo(5);
        assertThat(plan.steps())
                .extracting(OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanResponse.TestStep::name)
                .containsExactly(
                        "focused-maintenance-services",
                        "maintenance-route-integration",
                        "route-path-mapping",
                        "full-maven-regression",
                        "github-actions-ci"
                );
        assertThat(plan.steps()).allSatisfy(step -> {
            assertThat(step.startsJavaService()).isFalse();
            assertThat(step.startsMiniKvService()).isFalse();
            assertThat(step.startsNodeService()).isFalse();
        });
        assertThat(plan.forbiddenOperations()).contains("node-start-or-stop-java-or-mini-kv");
        assertThat(plan.status()).isEqualTo("passed");
    }
}
