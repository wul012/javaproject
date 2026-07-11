package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorServiceLifecycleService {

  public static final String ENDPOINT = "/api/v1/ops/shard-readiness/operator-service-lifecycle";
  public static final String FIXTURE_ENDPOINT =
      "/contracts/java-shard-readiness-operator-service-lifecycle-v160.fixture.json";
  public static final String EVIDENCE_PATH =
      "e/160/evidence/java-shard-readiness-operator-service-lifecycle-v160.json";

  private final OpsShardReadinessLiveReadGatePlanService liveReadGatePlanService;

  public OpsShardReadinessOperatorServiceLifecycleService(
      OpsShardReadinessLiveReadGatePlanService liveReadGatePlanService) {
    this.liveReadGatePlanService = liveReadGatePlanService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorServiceLifecycleResponse lifecycle() {
    OpsShardReadinessLiveReadGatePlanResponse sourcePlan = liveReadGatePlanService.plan();

    return new OpsShardReadinessOperatorServiceLifecycleResponse(
        "advanced-order-platform",
        "Java v160",
        true,
        false,
        true,
        false,
        false,
        false,
        sourcePlan.version(),
        "Node v385",
        "Node v386",
        "java-service-operator-placeholder",
        "java-service-operator-placeholder",
        "java-service-operator-placeholder",
        "operator-declared-port-before-window",
        "http://127.0.0.1:{java-port}",
        operatorPrerequisites(),
        getOnlySmokeTargets(),
        failClosedRules(),
        cleanupResponsibilities(),
        stopConditions(),
        EVIDENCE_PATH,
        lifecycleStatus(sourcePlan));
  }

  private List<String> operatorPrerequisites() {
    return List.of(
        "operator-confirms-java-service-owner",
        "operator-confirms-start-command-and-port-before-window",
        "operator-confirms-stop-responsibility-before-window",
        "operator-confirms-get-only-smoke-targets",
        "operator-confirms-no-credential-or-raw-endpoint-value-read");
  }

  private List<String> getOnlySmokeTargets() {
    return List.of(
        "GET /actuator/health",
        "GET /api/v1/ops/shard-readiness/operator-service-lifecycle",
        "GET /api/v1/ops/shard-readiness/live-read-gate-plan",
        "GET /api/v1/ops/shard-readiness/active-shard-plan-handoff");
  }

  private List<String> failClosedRules() {
    return List.of(
        "missing-operator-owner-blocks-runtime-probe",
        "missing-operator-declared-port-blocks-runtime-probe",
        "missing-cleanup-owner-blocks-runtime-probe",
        "non-get-smoke-target-blocks-runtime-probe",
        "failed-smoke-blocks-node-consumption");
  }

  private List<String> cleanupResponsibilities() {
    return List.of(
        "operator-stops-java-if-operator-started-java",
        "node-must-not-stop-java-from-this-evidence",
        "node-may-clean-only-processes-started-by-a-separate-approved-runtime-plan",
        "archive-runtime-smoke-output-before-cleanup");
  }

  private List<String> stopConditions() {
    return List.of(
        "source-gate-plan-status-not-passed",
        "request-would-start-java-from-this-evidence",
        "request-would-stop-java-from-this-evidence",
        "request-would-run-runtime-probe-before-operator-port-confirmation",
        "request-would-run-non-get-smoke",
        "request-would-read-credential-or-raw-endpoint-value");
  }

  private String lifecycleStatus(OpsShardReadinessLiveReadGatePlanResponse sourcePlan) {
    boolean passed =
        "passed".equals(sourcePlan.status())
            && sourcePlan.readOnly()
            && !sourcePlan.executionAllowed()
            && !sourcePlan.serviceStartAllowedByNode()
            && !sourcePlan.serviceStopAllowedByNode();
    return passed ? "passed" : "blocked";
  }
}
