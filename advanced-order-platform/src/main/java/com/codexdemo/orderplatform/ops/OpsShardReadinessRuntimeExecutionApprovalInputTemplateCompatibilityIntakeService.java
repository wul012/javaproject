package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService {

  public static final String ENDPOINT =
      "/api/v1/ops/shard-readiness/runtime-execution-approval-input-template-compatibility-intake";
  static final String FIXTURE_ENDPOINT =
      "/contracts/java-shard-readiness-runtime-execution-approval-input-template-compatibility-intake-v167.fixture.json";
  static final String EVIDENCE_PATH =
      "e/167/evidence/java-shard-readiness-runtime-execution-approval-input-template-compatibility-intake-v167.json";

  private final OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService
      templateCompatibilityService;

  public OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService(
      OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService
          templateCompatibilityService) {
    this.templateCompatibilityService = templateCompatibilityService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeResponse
      intake() {
    OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityResponse sourceReceipt =
        templateCompatibilityService.compatibility();

    return new OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeResponse(
        "advanced-order-platform",
        "Java v167",
        true,
        false,
        true,
        true,
        nodeCompatibilityIntakePresent(sourceReceipt),
        nodeCompatibilityIntakeComplete(sourceReceipt),
        sourceReceipt.templateCompatibilityReceiptPresent(),
        sourceReceipt.templateCompatibilityReceiptComplete(),
        sourceReceipt.sourceJavaInputCanonical(),
        sourceReceipt.nodeTemplateValidatorPresent(),
        sourceReceipt.templatesAreApprovalInputs(),
        sourceReceipt.canonicalApprovalInputsCreatedByJava(),
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
        sourceReceipt.sourceContractHandoffVersion(),
        sourceReceipt.sourceCanonicalJavaInputVersion(),
        sourceReceipt.lastTemplateValidatorNodeVersion(),
        "Node v403",
        "mini-kv v157",
        "Node v404",
        "runtime-execution-approval-input-template-compatibility-intake-blocked",
        "record-upstream-template-compatibility-and-keep-runtime-blocked",
        "java-side-runtime-execution-approval-input-template-compatibility-intake-receipt",
        "java-runtime-execution-approval-input-template-compatibility-intake-receipt-v167",
        sourceReceipt.evidencePath(),
        "D:/nodeproj/orderops-node/e/403/evidence/"
            + "java-mini-kv-runtime-execution-approval-input-template-compatibility-intake-v403-summary.json",
        "/api/v1/audit/managed-audit-manual-sandbox-connection-credential-resolver-"
            + "java-mini-kv-runtime-execution-approval-input-template-compatibility-intake",
        sourceReceipt.templateMatrix(),
        sourceReceipt.canonicalTargetPaths(),
        sourceReceipt.templateArchivePaths(),
        compatibilityIntakeChecks(sourceReceipt),
        nodeV403IntakeFields(),
        blockedCanonicalInputs(sourceReceipt),
        productionBlockers(),
        failClosedRules(sourceReceipt),
        stopConditions(),
        EVIDENCE_PATH,
        intakeStatus(sourceReceipt));
  }

  private boolean nodeCompatibilityIntakePresent(
      OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityResponse sourceReceipt) {
    return "Node v403".equals(sourceReceipt.nextNodeConsumerHint());
  }

  private boolean nodeCompatibilityIntakeComplete(
      OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityResponse sourceReceipt) {
    return nodeCompatibilityIntakePresent(sourceReceipt)
        && "passed".equals(sourceReceipt.status())
        && sourceReceipt.templateCompatibilityReceiptComplete()
        && sourceReceipt.canonicalTargetPaths().size() == 3
        && sourceReceipt.templateArchivePaths().size() == 3;
  }

  private List<String> compatibilityIntakeChecks(
      OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityResponse sourceReceipt) {
    return List.of(
        "node-v403-intake-consumes-java-v166-template-compatibility",
        "node-v403-intake-consumes-mini-kv-v157-template-echo",
        "node-v403-intake-keeps-runtime-execution-packet-blocked",
        "node-v403-intake-keeps-runtime-live-read-gate-blocked",
        "node-v403-intake-leaves-e398-canonical-inputs-missing",
        "java-v166-receipt-status:" + sourceReceipt.status(),
        "java-v166-template-matrix-count:" + sourceReceipt.templateMatrix().size(),
        "java-v166-canonical-target-count:" + sourceReceipt.canonicalTargetPaths().size(),
        "java-v166-template-archive-count:" + sourceReceipt.templateArchivePaths().size(),
        "next-node-version-requires-real-canonical-inputs:Node v404");
  }

  private List<String> nodeV403IntakeFields() {
    return List.of(
        "readyForRuntimeExecutionApprovalInputTemplateCompatibilityIntake:true",
        "readyForRuntimeExecutionPacket:false",
        "readyForRuntimeLiveReadGate:false",
        "javaCompatibilityReady:true",
        "miniKvTemplateEchoReady:true",
        "upstreamCompatibilityReceiptCount:2",
        "compatibleUpstreamCount:2",
        "canonicalInputCount:3",
        "presentCanonicalInputCount:0",
        "validCanonicalInputCount:0",
        "missingCanonicalInputCount:3",
        "productionBlockerCount:3");
  }

  private List<String> blockedCanonicalInputs(
      OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityResponse sourceReceipt) {
    return sourceReceipt.canonicalTargetPaths().stream()
        .map(target -> target + ":missing-real-approval-input")
        .toList();
  }

  private List<String> productionBlockers() {
    return List.of(
        "NODE_APPROVED_RUNTIME_WINDOW_INPUT_NOT_PRESENT",
        "CORRELATED_OPERATOR_APPROVAL_RECORD_INPUT_NOT_PRESENT",
        "CROSS_PROJECT_RUNTIME_EXECUTION_PACKET_INPUT_NOT_PRESENT");
  }

  private List<String> failClosedRules(
      OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityResponse sourceReceipt) {
    return List.of(
        "source-template-compatibility-status-must-be-passed:" + sourceReceipt.status(),
        "node-v403-intake-is-read-only-and-not-runtime-approval",
        "java-v167-does-not-create-e398-canonical-approval-input-files",
        "missing-node-approved-runtime-window-blocks-runtime-execution",
        "missing-correlated-operator-approval-record-blocks-runtime-execution",
        "missing-complete-cross-project-runtime-execution-packet-blocks-runtime-execution",
        "node-v404-may-run-only-after-real-canonical-approval-inputs-exist");
  }

  private List<String> stopConditions() {
    return List.of(
        "request-would-treat-node-v403-intake-as-runtime-approval",
        "request-would-copy-template-to-canonical-input-path",
        "request-would-synthesize-approval-correlation-id",
        "request-would-start-java-from-compatibility-intake",
        "request-would-start-mini-kv-from-compatibility-intake",
        "request-would-run-runtime-probe-from-compatibility-intake",
        "request-would-open-managed-audit-connection",
        "request-would-read-credential-or-raw-endpoint-value",
        "request-would-enable-active-shard-router-or-write-routing");
  }

  private String intakeStatus(
      OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityResponse sourceReceipt) {
    boolean sourcePassed = "passed".equals(sourceReceipt.status());
    boolean sourceComplete =
        sourceReceipt.templateCompatibilityReceiptPresent()
            && sourceReceipt.templateCompatibilityReceiptComplete()
            && sourceReceipt.sourceJavaInputCanonical()
            && sourceReceipt.nodeTemplateValidatorPresent();
    boolean canonicalInputsStillMissing =
        !sourceReceipt.nodeApprovedRuntimeWindowPresent()
            && !sourceReceipt.correlatedOperatorApprovalRecordPresent()
            && !sourceReceipt.completeCrossProjectRuntimeExecutionPacketPresent()
            && !sourceReceipt.canonicalApprovalInputsCreatedByJava();
    boolean runtimeStillClosed =
        !sourceReceipt.runtimeGateApprovalPresent()
            && !sourceReceipt.crossProjectRuntimeExecutionPacketExecutable()
            && !sourceReceipt.readyForRuntimeExecutionPacket()
            && !sourceReceipt.readyForRuntimeLiveReadGate()
            && !sourceReceipt.executionAllowed()
            && !sourceReceipt.executionAttempted()
            && !sourceReceipt.startsJavaService()
            && !sourceReceipt.startsMiniKvService()
            && !sourceReceipt.activeShardPrototypeEnabled();

    return sourcePassed && sourceComplete && canonicalInputsStillMissing && runtimeStillClosed
        ? "passed"
        : "blocked";
  }
}
