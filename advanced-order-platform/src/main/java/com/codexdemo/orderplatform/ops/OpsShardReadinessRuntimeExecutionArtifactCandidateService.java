package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRuntimeExecutionArtifactCandidateService {

  public static final String ENDPOINT =
      "/api/v1/ops/shard-readiness/runtime-execution-artifact-candidate";
  static final String FIXTURE_ENDPOINT =
      "/contracts/java-shard-readiness-runtime-execution-artifact-candidate-v162.fixture.json";
  static final String EVIDENCE_PATH =
      "e/162/evidence/java-shard-readiness-runtime-execution-artifact-candidate-v162.json";

  private final OpsShardReadinessDeclaredOperatorLifecycleService declaredOperatorLifecycleService;

  public OpsShardReadinessRuntimeExecutionArtifactCandidateService(
      OpsShardReadinessDeclaredOperatorLifecycleService declaredOperatorLifecycleService) {
    this.declaredOperatorLifecycleService = declaredOperatorLifecycleService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRuntimeExecutionArtifactCandidateResponse candidate() {
    OpsShardReadinessDeclaredOperatorLifecycleResponse sourceLifecycle =
        declaredOperatorLifecycleService.lifecycle();

    return new OpsShardReadinessRuntimeExecutionArtifactCandidateResponse(
        "advanced-order-platform",
        "Java v162",
        true,
        false,
        true,
        true,
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        sourceLifecycle.version(),
        "Node v395",
        "Node v396",
        "java-runtime-artifact-candidate-operator-record-v162",
        "java-side-artifact-candidate-only",
        "java-platform-operator",
        "java-platform-operator",
        "java-platform-operator",
        "advanced-order-platform",
        "mvn spring-boot:run -Dspring-boot.run.profiles=local",
        "8080",
        "requires-mini-kv-runtime-artifact",
        "java-local-readonly-base-url",
        getOnlySmokeCommands(),
        cleanupProofs(),
        processCleanupRules(),
        failClosedRules(),
        missingCrossProjectArtifacts(),
        stopConditions(),
        EVIDENCE_PATH,
        candidateStatus(sourceLifecycle));
  }

  private List<String> getOnlySmokeCommands() {
    return List.of(
        "GET java-loopback-port-8080 /actuator/health",
        "GET java-loopback-port-8080 /api/v1/ops/shard-readiness/runtime-execution-artifact-candidate",
        "GET java-loopback-port-8080 /api/v1/ops/shard-readiness/declared-operator-lifecycle");
  }

  private List<String> cleanupProofs() {
    return List.of(
        "java-operator-owns-cleanup-if-java-operator-starts-service",
        "cleanup-proof-must-be-archived-after-any-approved-runtime-execution",
        "no-cleanup-executed-by-this-read-only-candidate");
  }

  private List<String> processCleanupRules() {
    return List.of(
        "record-java-process-id-before-approved-runtime-start",
        "stop-only-process-id-started-by-approved-runtime-packet",
        "do-not-stop-pre-existing-java-service",
        "archive-smoke-output-before-process-cleanup");
  }

  private List<String> failClosedRules() {
    return List.of(
        "missing-operator-approval-record-blocks-runtime-execution-packet",
        "missing-mini-kv-runtime-artifact-blocks-runtime-execution-packet",
        "missing-cross-project-execution-packet-blocks-runtime-execution",
        "non-get-smoke-command-blocks-runtime-execution",
        "failed-smoke-command-blocks-node-consumption");
  }

  private List<String> missingCrossProjectArtifacts() {
    return List.of(
        "mini-kv-v153-runtime-artifact-candidate",
        "cross-project-runtime-execution-packet",
        "node-approved-runtime-execution-window");
  }

  private List<String> stopConditions() {
    return List.of(
        "source-declared-lifecycle-status-not-passed",
        "request-would-start-java-from-this-candidate",
        "request-would-stop-java-from-this-candidate",
        "request-would-run-runtime-probe-from-this-candidate",
        "request-would-use-candidate-as-cross-project-runtime-approval",
        "request-would-read-credential-or-raw-endpoint-value",
        "request-would-enable-active-shard-router-or-write-routing");
  }

  private String candidateStatus(
      OpsShardReadinessDeclaredOperatorLifecycleResponse sourceLifecycle) {
    boolean passed =
        "passed".equals(sourceLifecycle.status())
            && sourceLifecycle.readOnly()
            && !sourceLifecycle.executionAllowed()
            && sourceLifecycle.operatorLifecycleDeclared()
            && !sourceLifecycle.runtimeProbeAllowed()
            && !sourceLifecycle.nodeMayStartService()
            && !sourceLifecycle.nodeMayStopService();
    return passed ? "passed" : "blocked";
  }
}
