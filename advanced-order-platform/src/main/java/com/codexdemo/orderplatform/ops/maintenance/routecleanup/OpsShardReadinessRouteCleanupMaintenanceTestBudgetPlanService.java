package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanService {

  public static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.MAINTENANCE_TEST_BUDGET_PLAN;
  static final String PROFILE =
      "java-shard-readiness-route-cleanup-maintenance-test-budget-plan.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanResponse plan() {
    List<OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanResponse.TestStep> steps =
        List.of(
            step("focused-maintenance-services", "new maintenance service tests", "passed"),
            step(
                "maintenance-route-integration", "maintenance MockMvc integration tests", "passed"),
            step("route-path-mapping", "shared route path mapping tests", "passed"),
            step("full-maven-regression", "mvn -q test before push", "passed"),
            step("github-actions-ci", "Java Maven CI after push", "passed"));
    return new OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanResponse(
        "advanced-order-platform",
        "Java v483",
        true,
        false,
        ENDPOINT,
        PROFILE,
        steps.size(),
        steps,
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.forbiddenOperations(),
        status(steps));
  }

  private OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanResponse.TestStep step(
      String name, String commandScope, String expectedResult) {
    return new OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanResponse.TestStep(
        name, commandScope, false, false, false, expectedResult);
  }

  private String status(
      List<OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanResponse.TestStep> steps) {
    boolean passed =
        steps.size() == 5
            && steps.stream()
                .noneMatch(
                    OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanResponse.TestStep
                        ::startsJavaService)
            && steps.stream()
                .noneMatch(
                    OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanResponse.TestStep
                        ::startsMiniKvService)
            && steps.stream()
                .noneMatch(
                    OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanResponse.TestStep
                        ::startsNodeService);
    return passed ? "passed" : "blocked";
  }
}
