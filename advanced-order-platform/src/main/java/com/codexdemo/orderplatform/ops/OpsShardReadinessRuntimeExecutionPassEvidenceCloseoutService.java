package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService {

  public static final String ENDPOINT =
      "/api/v1/ops/shard-readiness/runtime-execution-pass-evidence-closeout";
  static final String FIXTURE_ENDPOINT =
      "/contracts/java-shard-readiness-runtime-execution-pass-evidence-closeout-v170.fixture.json";
  static final String EVIDENCE_PATH =
      "e/170/evidence/java-shard-readiness-runtime-execution-pass-evidence-closeout-v170.json";

  private final OpsShardReadinessRuntimeExecutionLiveReadGateService liveReadGateService;

  public OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService(
      OpsShardReadinessRuntimeExecutionLiveReadGateService liveReadGateService) {
    this.liveReadGateService = liveReadGateService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutResponse closeout() {
    OpsShardReadinessRuntimeExecutionLiveReadGateResponse sourceReceipt =
        liveReadGateService.gate();

    return new OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutResponse(
        "advanced-order-platform",
        "Java v170",
        true,
        false,
        true,
        true,
        sourceReceipt.liveReadGateReceiptPresent(),
        sourceReceipt.liveReadGateReceiptComplete(),
        nodeApprovedSmokePresent(sourceReceipt),
        nodeApprovedSmokePassed(sourceReceipt),
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
        false,
        false,
        sourceReceipt.version(),
        sourceReceipt.nodeLiveReadGateVersion(),
        "Node v407",
        "Node v408",
        "Node v409",
        "Node v410",
        "runtime-execution-pass-evidence-closeout-ready",
        "close-runtime-execution-pass-evidence-chain",
        "java-side-runtime-execution-pass-evidence-closeout-receipt",
        "java-runtime-execution-pass-evidence-closeout-receipt-v170",
        sourceReceipt.evidencePath(),
        "D:/nodeproj/orderops-node/e/407/evidence/"
            + "java-mini-kv-runtime-execution-approved-local-loopback-read-only-smoke-v407-summary.json",
        "D:/nodeproj/orderops-node/e/408/evidence/"
            + "java-mini-kv-runtime-execution-pass-evidence-archive-verification-v408-summary.json",
        "D:/nodeproj/orderops-node/e/409/evidence/"
            + "java-mini-kv-runtime-execution-pass-evidence-closeout-v409-summary.json",
        4,
        4,
        114,
        114,
        0,
        7,
        7,
        sourceStageSummaries(),
        smokePassEvidenceFields(),
        cleanupProofFields(),
        archiveVerificationFields(),
        closeoutHandoffChecks(sourceReceipt),
        failClosedRules(sourceReceipt),
        stopConditions(),
        EVIDENCE_PATH,
        closeoutStatus(sourceReceipt));
  }

  private boolean nodeApprovedSmokePresent(
      OpsShardReadinessRuntimeExecutionLiveReadGateResponse sourceReceipt) {
    return "Node v407".equals(sourceReceipt.nextNodeConsumerHint())
        && "passed".equals(sourceReceipt.status());
  }

  private boolean nodeApprovedSmokePassed(
      OpsShardReadinessRuntimeExecutionLiveReadGateResponse sourceReceipt) {
    return nodeApprovedSmokePresent(sourceReceipt)
        && sourceReceipt.readyForApprovedLocalLoopbackReadOnlySmoke()
        && sourceReceipt.targetCount() == 2
        && sourceReceipt.readyTargetCount() == 2
        && sourceReceipt.cleanupProofRequired();
  }

  private List<String> sourceStageSummaries() {
    return List.of(
        "node-v405:canonical-approval-input-value-validation-ready",
        "node-v406:runtime-execution-live-read-gate-ready",
        "node-v407:approved-local-loopback-read-only-smoke-passed",
        "node-v408:runtime-execution-pass-evidence-archive-verified",
        "node-v409:runtime-execution-pass-evidence-closeout-ready");
  }

  private List<String> smokePassEvidenceFields() {
    return List.of(
        "attemptedTargetCount:2",
        "passedTargetCount:2",
        "failedTargetCount:0",
        "skippedTargetCount:0",
        "startsJavaServiceFromRoute:false",
        "startsMiniKvServiceFromRoute:false",
        "executionAllowed:false");
  }

  private List<String> cleanupProofFields() {
    return List.of(
        "cleanupProofPath:e/407/evidence/java-mini-kv-runtime-execution-approved-local-loopback-read-only-smoke-v407-cleanup-proof.json",
        "cleanupPassed:true",
        "checkedPort:8080",
        "checkedPort:6424",
        "checkedPort:4407",
        "checkedPort:8407",
        "afterListeningSocketCount:0",
        "stop-only-owned-processes:true");
  }

  private List<String> archiveVerificationFields() {
    return List.of(
        "node-v408-archiveReferenceCount:7",
        "node-v408-presentArchiveReferenceCount:7",
        "node-v408-cleanupPassed:true",
        "node-v408-rerunsSmoke:false",
        "node-v409-sourceSummaryCount:4",
        "node-v409-readyStageCount:4",
        "node-v409-totalSourceProductionBlockerCount:0");
  }

  private List<String> closeoutHandoffChecks(
      OpsShardReadinessRuntimeExecutionLiveReadGateResponse sourceReceipt) {
    return List.of(
        "source-java-v169-live-read-gate-status:" + sourceReceipt.status(),
        "node-v407-smoke-pass-evidence-consumed",
        "node-v408-archive-verification-consumed",
        "node-v409-closeout-ledger-consumed",
        "cleanup-proof-confirms-no-checked-port-left-listening",
        "java-v170-does-not-rerun-runtime-smoke",
        "runtime-execution-chain-ready-for-route-group-refactor-consumption");
  }

  private List<String> failClosedRules(
      OpsShardReadinessRuntimeExecutionLiveReadGateResponse sourceReceipt) {
    return List.of(
        "source-live-read-gate-status-must-be-passed:" + sourceReceipt.status(),
        "node-v409-closeout-is-not-new-runtime-permission",
        "java-v170-does-not-rerun-smoke",
        "java-v170-does-not-start-or-stop-java-service",
        "java-v170-does-not-start-or-stop-mini-kv-service",
        "cleanup-proof-must-remain-present-before-chain-handoff",
        "future-route-group-refactors-must-not-change-api-paths");
  }

  private List<String> stopConditions() {
    return List.of(
        "request-would-rerun-smoke-from-closeout-receipt",
        "request-would-start-java-from-pass-evidence-closeout",
        "request-would-start-mini-kv-from-pass-evidence-closeout",
        "request-would-ignore-cleanup-proof",
        "request-would-open-managed-audit-connection",
        "request-would-read-credential-or-raw-endpoint-value",
        "request-would-enable-write-routing-or-active-shard-router");
  }

  private String closeoutStatus(
      OpsShardReadinessRuntimeExecutionLiveReadGateResponse sourceReceipt) {
    boolean sourcePassed =
        "passed".equals(sourceReceipt.status())
            && sourceReceipt.liveReadGateReceiptComplete()
            && sourceReceipt.readyForApprovedLocalLoopbackReadOnlySmoke();
    boolean smokePassed = nodeApprovedSmokePassed(sourceReceipt);
    boolean javaDidNotRerun =
        !sourceReceipt.runtimeSmokeAttempted()
            && !sourceReceipt.startsJavaService()
            && !sourceReceipt.startsMiniKvService()
            && !sourceReceipt.executionAllowed()
            && !sourceReceipt.connectsManagedAudit()
            && !sourceReceipt.writeOperationsAllowed();

    return sourcePassed && smokePassed && javaDidNotRerun ? "passed" : "blocked";
  }
}
