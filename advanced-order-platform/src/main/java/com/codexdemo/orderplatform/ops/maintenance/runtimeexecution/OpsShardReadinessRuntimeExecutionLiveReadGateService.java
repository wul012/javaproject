package com.codexdemo.orderplatform.ops.maintenance.runtimeexecution;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRuntimeExecutionLiveReadGateService {
  public static final String ENDPOINT =
      "/api/v1/ops/shard-readiness/runtime-execution-live-read-gate";
  public static final String FIXTURE_ENDPOINT =
      "/contracts/java-shard-readiness-runtime-execution-live-read-gate-v169.fixture.json";
  public static final String EVIDENCE_PATH =
      "e/169/evidence/java-shard-readiness-runtime-execution-live-read-gate-v169.json";

  private final OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService
      valueValidationService;

  public OpsShardReadinessRuntimeExecutionLiveReadGateService(
      OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService valueValidationService) {
    this.valueValidationService = valueValidationService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRuntimeExecutionLiveReadGateResponse gate() {
    OpsShardReadinessRuntimeExecutionApprovalInputValueValidationResponse sourceReceipt =
        valueValidationService.validation();

    return new OpsShardReadinessRuntimeExecutionLiveReadGateResponse(
        "advanced-order-platform",
        "Java v169",
        true,
        false,
        true,
        true,
        sourceReceipt.valueValidationReceiptPresent(),
        sourceReceipt.valueValidationReceiptComplete(),
        nodeLiveReadGatePresent(sourceReceipt),
        nodeLiveReadGateAccepted(sourceReceipt),
        sourceReceipt.readyForRuntimeExecutionPacket(),
        sourceReceipt.readyForRuntimeLiveReadGate(),
        true,
        sourceReceipt.runtimeExecutionPacketPresent(),
        sourceReceipt.runtimeExecutionPacketExecutable(),
        sourceReceipt.runtimeGateApprovalPresent(),
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        true,
        sourceReceipt.version(),
        sourceReceipt.nodeValueValidationVersion(),
        "Node v406",
        "Node v407",
        "runtime-execution-live-read-gate-ready",
        "accept-live-read-gate-for-approved-local-loopback-read-only-smoke",
        "java-side-runtime-execution-live-read-gate-receipt",
        "java-runtime-execution-live-read-gate-receipt-v169",
        sourceReceipt.evidencePath(),
        "D:/nodeproj/orderops-node/e/406/evidence/"
            + "java-mini-kv-runtime-execution-live-read-gate-v406-summary.json",
        "/api/v1/audit/managed-audit-manual-sandbox-connection-credential-resolver-"
            + "java-mini-kv-runtime-execution-live-read-gate",
        2,
        2,
        33,
        33,
        0,
        runtimeTargets(),
        acceptedNodeGateFields(),
        javaGateChecks(sourceReceipt),
        serviceOwnershipBoundaries(),
        cleanupProofRequirements(),
        failClosedRules(sourceReceipt),
        stopConditions(),
        EVIDENCE_PATH,
        gateStatus(sourceReceipt));
  }

  private boolean nodeLiveReadGatePresent(
      OpsShardReadinessRuntimeExecutionApprovalInputValueValidationResponse sourceReceipt) {
    return "Node v406".equals(sourceReceipt.nextNodeConsumerHint())
        && "passed".equals(sourceReceipt.status());
  }

  private boolean nodeLiveReadGateAccepted(
      OpsShardReadinessRuntimeExecutionApprovalInputValueValidationResponse sourceReceipt) {
    return nodeLiveReadGatePresent(sourceReceipt)
        && sourceReceipt.canonicalApprovalInputsValueValid()
        && sourceReceipt.sharedApprovalCorrelationIdValidated()
        && sourceReceipt.readyForRuntimeLiveReadGate()
        && sourceReceipt.allowedRuntimeSmokeCommands().size() == 2;
  }

  private List<String> runtimeTargets() {
    return List.of(
        "java:owner=java-platform-operator:GET:http://127.0.0.1:8080/actuator/health",
        "mini-kv:owner=mini-kv-service-owner:GET:127.0.0.1:6424:/health");
  }

  private List<String> acceptedNodeGateFields() {
    return List.of(
        "readyForRuntimeExecutionLiveReadGate:true",
        "readyForApprovedLocalLoopbackReadOnlySmoke:true",
        "targetCount:2",
        "readyTargetCount:2",
        "runtimeSmokeAttempted:false",
        "startsJavaService:false",
        "startsMiniKvService:false",
        "executionAllowed:false",
        "checkCount:33",
        "passedCheckCount:33",
        "productionBlockerCount:0");
  }

  private List<String> javaGateChecks(
      OpsShardReadinessRuntimeExecutionApprovalInputValueValidationResponse sourceReceipt) {
    return List.of(
        "source-java-v168-value-validation-status:" + sourceReceipt.status(),
        "node-v406-gate-consumes-node-v405-value-validation",
        "java-loopback-target-is-actuator-health-get-only",
        "mini-kv-loopback-target-remains-health-get-only",
        "service-startup-owner-is-operator-only",
        "cleanup-proof-is-required-after-any-later-smoke",
        "java-v169-does-not-attempt-runtime-smoke");
  }

  private List<String> serviceOwnershipBoundaries() {
    return List.of(
        "java-owner:java-platform-operator",
        "mini-kv-owner:mini-kv-service-owner",
        "node-owner:node-control-plane-operator",
        "java-port:8080",
        "mini-kv-port:6424",
        "node-route-does-not-start-or-stop-services",
        "java-v169-does-not-start-or-stop-services");
  }

  private List<String> cleanupProofRequirements() {
    return List.of(
        "record-owned-java-pid-if-started-by-later-smoke",
        "record-owned-mini-kv-pid-if-started-by-later-smoke",
        "record-owned-node-pid-if-started-by-later-smoke",
        "stop-only-owned-processes",
        "verify-port-8080-not-listening-after-cleanup",
        "verify-port-6424-not-listening-after-cleanup");
  }

  private List<String> failClosedRules(
      OpsShardReadinessRuntimeExecutionApprovalInputValueValidationResponse sourceReceipt) {
    return List.of(
        "source-value-validation-status-must-be-passed:" + sourceReceipt.status(),
        "node-v406-live-read-gate-is-not-smoke-pass-evidence",
        "java-v169-does-not-start-java-service",
        "java-v169-does-not-start-mini-kv-service",
        "java-v169-does-not-run-runtime-smoke",
        "cleanup-proof-required-before-pass-evidence-closeout",
        "node-v407-must-capture-approved-local-loopback-read-only-smoke");
  }

  private List<String> stopConditions() {
    return List.of(
        "request-would-treat-live-read-gate-as-smoke-pass",
        "request-would-run-non-get-smoke-command",
        "request-would-start-unowned-java-process",
        "request-would-start-unowned-mini-kv-process",
        "request-would-skip-owned-process-cleanup-proof",
        "request-would-open-managed-audit-connection",
        "request-would-read-credential-or-raw-endpoint-value",
        "request-would-enable-write-routing-or-active-shard-router");
  }

  private String gateStatus(
      OpsShardReadinessRuntimeExecutionApprovalInputValueValidationResponse sourceReceipt) {
    boolean sourcePassed =
        "passed".equals(sourceReceipt.status())
            && sourceReceipt.valueValidationReceiptComplete()
            && sourceReceipt.readyForRuntimeLiveReadGate();
    boolean gateAccepted = nodeLiveReadGateAccepted(sourceReceipt);
    boolean smokeNotAttempted =
        !sourceReceipt.executionAttempted()
            && !sourceReceipt.startsJavaService()
            && !sourceReceipt.startsMiniKvService()
            && !sourceReceipt.executionAllowed()
            && !sourceReceipt.connectsManagedAudit()
            && !sourceReceipt.writeOperationsAllowed();

    return sourcePassed && gateAccepted && smokeNotAttempted ? "passed" : "blocked";
  }
}
