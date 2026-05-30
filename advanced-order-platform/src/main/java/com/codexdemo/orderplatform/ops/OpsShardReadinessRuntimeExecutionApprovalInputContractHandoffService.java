package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService {

    static final String ENDPOINT =
            "/api/v1/ops/shard-readiness/runtime-execution-approval-input-contract-handoff";
    static final String FIXTURE_ENDPOINT =
            "/contracts/java-shard-readiness-runtime-execution-approval-input-contract-handoff-v165.fixture.json";
    static final String EVIDENCE_PATH =
            "e/165/evidence/java-shard-readiness-runtime-execution-approval-input-contract-handoff-v165.json";

    private final OpsShardReadinessRuntimeExecutionApprovalGateInputService approvalGateInputService;

    public OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService(
            OpsShardReadinessRuntimeExecutionApprovalGateInputService approvalGateInputService
    ) {
        this.approvalGateInputService = approvalGateInputService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffResponse handoff() {
        OpsShardReadinessRuntimeExecutionApprovalGateInputResponse sourceInput =
                approvalGateInputService.approvalGateInput();

        return new OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffResponse(
                "advanced-order-platform",
                "Java v165",
                true,
                false,
                true,
                true,
                sourceInput.javaApprovalGateInputPresent(),
                sourceInput.javaApprovalGateInputComplete(),
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
                sourceInput.version(),
                "Node v400",
                "Node v401",
                "java-side-runtime-execution-approval-input-contract-handoff",
                "java-runtime-execution-approval-input-contract-handoff-v165",
                sourceInput.approvalGateInputPath(),
                OpsShardReadinessRuntimeExecutionApprovalGateInputService.ENDPOINT,
                OpsShardReadinessRuntimeExecutionApprovalGateInputService.FIXTURE_ENDPOINT,
                javaOwnedArtifacts(sourceInput),
                ownerByOwnerHandoff(sourceInput),
                nonJavaMissingInputs(),
                finalPacketBindingRequirements(),
                failClosedRules(sourceInput),
                stopConditions(),
                EVIDENCE_PATH,
                handoffStatus(sourceInput)
        );
    }

    private List<String> javaOwnedArtifacts(
            OpsShardReadinessRuntimeExecutionApprovalGateInputResponse sourceInput
    ) {
        return List.of(
                "canonical-java-approval-gate-input:" + sourceInput.version(),
                "canonical-java-approval-gate-input-path:" + sourceInput.approvalGateInputPath(),
                "canonical-java-approval-gate-input-endpoint:"
                        + OpsShardReadinessRuntimeExecutionApprovalGateInputService.ENDPOINT,
                "canonical-java-approval-gate-input-fixture:"
                        + OpsShardReadinessRuntimeExecutionApprovalGateInputService.FIXTURE_ENDPOINT,
                "java-loopback-port:" + sourceInput.javaLoopbackPort(),
                "java-service-owner:" + sourceInput.javaServiceOwner(),
                "java-startup-command-owner:java-platform-operator"
        );
    }

    private List<String> ownerByOwnerHandoff(
            OpsShardReadinessRuntimeExecutionApprovalGateInputResponse sourceInput
    ) {
        return List.of(
                "java:canonical-approval-gate-input-present:" + sourceInput.version(),
                "mini-kv:final-approval-gate-input-required-not-owned-by-java",
                "node:node-approved-runtime-window-required-not-owned-by-java",
                "operator:correlated-operator-approval-record-required-not-owned-by-java",
                "cross-project:complete-runtime-execution-packet-required-not-owned-by-java",
                "cleanup:cleanup-proof-accepted-only-after-approved-runtime-start"
        );
    }

    private List<String> nonJavaMissingInputs() {
        return List.of(
                "final-mini-kv-approval-gate-input",
                "node-approved-runtime-window",
                "correlated-operator-approval-record",
                "complete-cross-project-runtime-execution-packet"
        );
    }

    private List<String> finalPacketBindingRequirements() {
        return List.of(
                "bind-java-v164-approval-gate-input",
                "bind-final-mini-kv-approval-gate-input",
                "bind-node-approved-runtime-window",
                "bind-correlated-operator-approval-record",
                "bind-java-and-mini-kv-get-only-smoke-commands",
                "bind-owner-confirmations",
                "bind-cleanup-proof-after-approved-runtime-start",
                "bind-stop-only-owned-process-rules"
        );
    }

    private List<String> failClosedRules(
            OpsShardReadinessRuntimeExecutionApprovalGateInputResponse sourceInput
    ) {
        return List.of(
                "source-java-approval-gate-input-status-must-be-passed:" + sourceInput.status(),
                "java-v164-remains-canonical-no-new-java-input-issued",
                "missing-final-mini-kv-approval-gate-input-blocks-runtime-execution",
                "missing-node-approved-runtime-window-blocks-runtime-execution",
                "missing-correlated-operator-approval-record-blocks-runtime-execution",
                "missing-complete-cross-project-runtime-execution-packet-blocks-runtime-execution",
                "contract-handoff-alone-is-not-runtime-approval"
        );
    }

    private List<String> stopConditions() {
        return List.of(
                "request-would-start-java-from-contract-handoff",
                "request-would-stop-java-from-contract-handoff",
                "request-would-run-runtime-probe-from-contract-handoff",
                "request-would-treat-contract-handoff-as-node-approved-runtime-window",
                "request-would-treat-contract-handoff-as-correlated-operator-approval",
                "request-would-treat-contract-handoff-as-complete-cross-project-packet",
                "request-would-read-credential-or-raw-endpoint-value",
                "request-would-enable-active-shard-router-or-write-routing"
        );
    }

    private String handoffStatus(
            OpsShardReadinessRuntimeExecutionApprovalGateInputResponse sourceInput
    ) {
        boolean sourcePassed = "passed".equals(sourceInput.status());
        boolean sourceComplete = sourceInput.javaApprovalGateInputPresent()
                && sourceInput.javaApprovalGateInputComplete();
        boolean runtimeStillClosed = !sourceInput.runtimeGateApprovalPresent()
                && !sourceInput.nodeApprovedRuntimeWindowPresent()
                && !sourceInput.correlatedOperatorApprovalRecordPresent()
                && !sourceInput.crossProjectRuntimeExecutionPacketPresent()
                && !sourceInput.crossProjectRuntimeExecutionPacketExecutable()
                && !sourceInput.readyForRuntimeExecutionPacket()
                && !sourceInput.readyForRuntimeLiveReadGate()
                && !sourceInput.executionAllowed()
                && !sourceInput.executionAttempted();

        return sourcePassed && sourceComplete && runtimeStillClosed ? "passed" : "blocked";
    }
}
