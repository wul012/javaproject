package com.codexdemo.orderplatform.ops.maintenance.runtimeexecution;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRuntimeExecutionApprovalGateInputService {

  public static final String ENDPOINT =
      "/api/v1/ops/shard-readiness/runtime-execution-approval-gate-input";
  public static final String FIXTURE_ENDPOINT =
      "/contracts/java-shard-readiness-runtime-execution-approval-gate-input-v164.fixture.json";
  public static final String EVIDENCE_PATH =
      "e/164/evidence/java-shard-readiness-runtime-execution-approval-gate-input-v164.json";

  private final OpsShardReadinessRuntimeExecutionPacketContributionService
      packetContributionService;

  public OpsShardReadinessRuntimeExecutionApprovalGateInputService(
      OpsShardReadinessRuntimeExecutionPacketContributionService packetContributionService) {
    this.packetContributionService = packetContributionService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRuntimeExecutionApprovalGateInputResponse approvalGateInput() {
    OpsShardReadinessRuntimeExecutionPacketContributionResponse sourceContribution =
        packetContributionService.contribution();

    return new OpsShardReadinessRuntimeExecutionApprovalGateInputResponse(
        "advanced-order-platform",
        "Java v164",
        true,
        false,
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
        sourceContribution.version(),
        "Node v397",
        "Node v399",
        "Node v400",
        "java-side-runtime-execution-approval-gate-input",
        "java-runtime-execution-approval-gate-input-v164",
        "e/164/evidence/java-shard-readiness-runtime-execution-approval-gate-input-v164.json",
        sourceContribution.operatorApprovalRecordId(),
        "node-v400-must-correlate-java-mini-kv-and-node-approved-runtime-window",
        sourceContribution.javaLoopbackPort(),
        sourceContribution.serviceOwnerConfirmation(),
        sourceContribution.startupCommand(),
        javaApprovalInputArtifacts(sourceContribution),
        javaPacketRowsForCorrelation(sourceContribution),
        requiredSiblingInputs(),
        nodeApprovalGateInputPaths(),
        failClosedRules(sourceContribution),
        stopConditions(),
        EVIDENCE_PATH,
        approvalGateInputStatus(sourceContribution));
  }

  private List<String> javaApprovalInputArtifacts(
      OpsShardReadinessRuntimeExecutionPacketContributionResponse sourceContribution) {
    return List.of(
        "source-packet-contribution:" + sourceContribution.version(),
        "java-operator-approval-record:" + sourceContribution.operatorApprovalRecordId(),
        "java-loopback-port:" + sourceContribution.javaLoopbackPort(),
        "java-service-owner:" + sourceContribution.serviceOwnerConfirmation(),
        "java-get-only-smoke-commands:" + sourceContribution.getOnlySmokeCommands().size(),
        "java-stop-only-owned-process-rules:" + sourceContribution.processCleanupRules().size());
  }

  private List<String> javaPacketRowsForCorrelation(
      OpsShardReadinessRuntimeExecutionPacketContributionResponse sourceContribution) {
    return sourceContribution.acceptedRequirementRows().stream()
        .map(row -> "java-approval-input:" + row)
        .toList();
  }

  private List<String> requiredSiblingInputs() {
    return List.of(
        "mini-kv-approval-gate-input",
        "node-approved-runtime-window",
        "correlated-operator-approval-record",
        "complete-cross-project-runtime-execution-packet");
  }

  private List<String> nodeApprovalGateInputPaths() {
    return List.of(
        "e/398/input/node-approved-runtime-window-v398.json",
        "e/398/input/correlated-operator-approval-record-v398.json",
        "e/398/input/cross-project-runtime-execution-packet-v398.json");
  }

  private List<String> failClosedRules(
      OpsShardReadinessRuntimeExecutionPacketContributionResponse sourceContribution) {
    return List.of(
        "source-contribution-status-must-be-passed:" + sourceContribution.status(),
        "missing-node-approved-runtime-window-blocks-runtime-execution",
        "missing-correlated-operator-approval-record-blocks-runtime-execution",
        "missing-mini-kv-approval-gate-input-blocks-runtime-execution",
        "missing-complete-cross-project-runtime-execution-packet-blocks-runtime-execution",
        "java-approval-gate-input-alone-is-not-runtime-approval");
  }

  private List<String> stopConditions() {
    return List.of(
        "request-would-start-java-from-approval-gate-input",
        "request-would-stop-java-from-approval-gate-input",
        "request-would-run-runtime-probe-from-approval-gate-input",
        "request-would-treat-java-only-input-as-correlated-approval",
        "request-would-claim-node-approved-runtime-window-present",
        "request-would-claim-complete-cross-project-runtime-packet-present",
        "request-would-read-credential-or-raw-endpoint-value",
        "request-would-enable-active-shard-router-or-write-routing");
  }

  private String approvalGateInputStatus(
      OpsShardReadinessRuntimeExecutionPacketContributionResponse sourceContribution) {
    boolean sourcePassed = "passed".equals(sourceContribution.status());
    boolean sourceContributionComplete =
        sourceContribution.javaPacketContributionPresent()
            && sourceContribution.javaPacketContributionComplete();
    boolean runtimeStillClosed =
        !sourceContribution.crossProjectRuntimeExecutionPacketPresent()
            && !sourceContribution.crossProjectRuntimeExecutionPacketExecutable()
            && !sourceContribution.readyForRuntimeExecutionPacket()
            && !sourceContribution.readyForRuntimeLiveReadGate()
            && !sourceContribution.executionAllowed()
            && !sourceContribution.executionAttempted();

    return sourcePassed && sourceContributionComplete && runtimeStillClosed ? "passed" : "blocked";
  }
}
