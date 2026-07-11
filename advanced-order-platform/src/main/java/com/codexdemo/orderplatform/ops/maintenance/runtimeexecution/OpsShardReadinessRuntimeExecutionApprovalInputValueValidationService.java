package com.codexdemo.orderplatform.ops.maintenance.runtimeexecution;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService {

  public static final String ENDPOINT =
      "/api/v1/ops/shard-readiness/runtime-execution-approval-input-value-validation";
  public static final String FIXTURE_ENDPOINT =
      "/contracts/java-shard-readiness-runtime-execution-approval-input-value-validation-v168.fixture.json";
  public static final String EVIDENCE_PATH =
      "e/168/evidence/java-shard-readiness-runtime-execution-approval-input-value-validation-v168.json";

  private final OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService
      compatibilityIntakeService;

  public OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService(
      OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService
          compatibilityIntakeService) {
    this.compatibilityIntakeService = compatibilityIntakeService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRuntimeExecutionApprovalInputValueValidationResponse validation() {
    OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeResponse
        sourceReceipt = compatibilityIntakeService.intake();

    return new OpsShardReadinessRuntimeExecutionApprovalInputValueValidationResponse(
        "advanced-order-platform",
        "Java v168",
        true,
        false,
        true,
        true,
        sourceReceipt.compatibilityIntakeReceiptPresent(),
        sourceReceipt.compatibilityIntakeReceiptComplete(),
        nodeValueValidationPresent(sourceReceipt),
        nodeValueValidationAccepted(sourceReceipt),
        true,
        true,
        true,
        true,
        true,
        true,
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
        false,
        sourceReceipt.version(),
        "Node v404",
        "Node v405",
        "mini-kv v158",
        "Node v406",
        "runtime-execution-canonical-approval-input-value-validation-ready",
        "accept-canonical-approval-input-values-for-next-live-read-gate",
        "java-side-runtime-execution-approval-input-value-validation-receipt",
        "java-runtime-execution-approval-input-value-validation-receipt-v168",
        sourceReceipt.evidencePath(),
        "D:/nodeproj/orderops-node/e/405/evidence/"
            + "java-mini-kv-runtime-execution-canonical-approval-input-value-validation-v405-summary.json",
        "/api/v1/audit/managed-audit-manual-sandbox-connection-credential-resolver-"
            + "java-mini-kv-runtime-execution-canonical-approval-input-value-validation",
        3,
        3,
        32,
        32,
        0,
        sourceReceipt.canonicalTargetPaths(),
        acceptedNodeValidationFields(),
        javaValidationChecks(sourceReceipt),
        allowedRuntimeSmokeCommands(),
        serviceOwnershipBoundaries(),
        failClosedRules(sourceReceipt),
        stopConditions(),
        EVIDENCE_PATH,
        validationStatus(sourceReceipt));
  }

  private boolean nodeValueValidationPresent(
      OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeResponse
          sourceReceipt) {
    return "Node v404".equals(sourceReceipt.nextNodeConsumerHint())
        && "passed".equals(sourceReceipt.status());
  }

  private boolean nodeValueValidationAccepted(
      OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeResponse
          sourceReceipt) {
    return nodeValueValidationPresent(sourceReceipt)
        && sourceReceipt.compatibilityIntakeReceiptComplete()
        && sourceReceipt.canonicalTargetPaths().size() == 3
        && sourceReceipt.productionBlockers().size() == 3;
  }

  private List<String> acceptedNodeValidationFields() {
    return List.of(
        "readyForRuntimeExecutionCanonicalApprovalInputValueValidation:true",
        "readyForRuntimeExecutionPacket:true",
        "readyForRuntimeLiveReadGate:true",
        "presentTargetInputCount:3",
        "validTargetInputCount:3",
        "sharedApprovalCorrelationIdValidated:true",
        "runtimeExecutionPacketPresent:true",
        "runtimeExecutionPacketExecutable:true",
        "runtimeGateApprovalPresent:true",
        "concreteLoopbackPortsAssigned:true",
        "executionAttempted:false",
        "startsJavaService:false",
        "startsMiniKvService:false",
        "executionAllowed:false",
        "activeShardPrototypeEnabled:false",
        "productionBlockerCount:0");
  }

  private List<String> javaValidationChecks(
      OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeResponse
          sourceReceipt) {
    return List.of(
        "source-java-v167-compatibility-intake-status:" + sourceReceipt.status(),
        "node-v405-accepts-real-canonical-input-values",
        "shared-approval-correlation-id-is-node-validated-not-java-generated",
        "runtime-packet-present-but-java-does-not-start-service",
        "live-read-gate-ready-but-smoke-requires-separate-node-v406-gate",
        "canonical-input-count:" + sourceReceipt.canonicalTargetPaths().size());
  }

  private List<String> allowedRuntimeSmokeCommands() {
    return List.of(
        "java:GET:http://127.0.0.1:8080/actuator/health", "mini-kv:GET:127.0.0.1:6424:/health");
  }

  private List<String> serviceOwnershipBoundaries() {
    return List.of(
        "java-owner:java-platform-operator",
        "mini-kv-owner:mini-kv-service-owner",
        "node-owner:node-control-plane-operator",
        "java-loopback:127.0.0.1:8080",
        "mini-kv-loopback:127.0.0.1:6424",
        "startup-owner:operator-owned-only",
        "cleanup-proof-required-after-run");
  }

  private List<String> failClosedRules(
      OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeResponse
          sourceReceipt) {
    return List.of(
        "source-compatibility-intake-status-must-be-passed:" + sourceReceipt.status(),
        "node-v405-value-validation-is-not-java-service-startup",
        "java-v168-does-not-read-or-write-e398-canonical-input-files",
        "java-v168-does-not-generate-approval-correlation-id",
        "java-v168-does-not-run-runtime-smoke",
        "node-v406-live-read-gate-required-before-any-approved-smoke",
        "only-get-local-loopback-smoke-commands-remain-eligible");
  }

  private List<String> stopConditions() {
    return List.of(
        "request-would-start-java-from-value-validation-receipt",
        "request-would-start-mini-kv-from-value-validation-receipt",
        "request-would-run-smoke-before-node-v406-live-read-gate",
        "request-would-allow-non-get-smoke-command",
        "request-would-open-managed-audit-connection",
        "request-would-read-credential-or-raw-endpoint-value",
        "request-would-enable-write-routing-or-active-shard-router");
  }

  private String validationStatus(
      OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeResponse
          sourceReceipt) {
    boolean sourcePassed =
        "passed".equals(sourceReceipt.status())
            && sourceReceipt.compatibilityIntakeReceiptComplete();
    boolean nodeValidationAccepted = nodeValueValidationAccepted(sourceReceipt);
    boolean runtimeNotStartedByJava =
        !sourceReceipt.startsJavaService()
            && !sourceReceipt.startsMiniKvService()
            && !sourceReceipt.executionAttempted()
            && !sourceReceipt.executionAllowed()
            && !sourceReceipt.connectsManagedAudit()
            && !sourceReceipt.credentialValueRead()
            && !sourceReceipt.rawEndpointUrlParsed()
            && !sourceReceipt.activeShardPrototypeEnabled();

    return sourcePassed && nodeValidationAccepted && runtimeNotStartedByJava ? "passed" : "blocked";
  }
}
