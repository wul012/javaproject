package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService {

    static final String ENDPOINT =
            "/api/v1/ops/shard-readiness/runtime-execution-approval-input-template-compatibility";
    static final String FIXTURE_ENDPOINT =
            "/contracts/java-shard-readiness-runtime-execution-approval-input-template-compatibility-v166.fixture.json";
    static final String EVIDENCE_PATH =
            "e/166/evidence/java-shard-readiness-runtime-execution-approval-input-template-compatibility-v166.json";

    private final OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService contractHandoffService;

    public OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService(
            OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService contractHandoffService
    ) {
        this.contractHandoffService = contractHandoffService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityResponse compatibility() {
        OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffResponse sourceHandoff =
                contractHandoffService.handoff();

        return new OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityResponse(
                "advanced-order-platform",
                "Java v166",
                true,
                false,
                true,
                true,
                sourceHandoff.javaApprovalInputContractHandoffPresent()
                        && sourceHandoff.javaApprovalInputContractHandoffComplete(),
                sourceHandoff.javaInputRemainsCanonical(),
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
                false,
                sourceHandoff.version(),
                sourceHandoff.sourceApprovalGateInputVersion(),
                "Node v402",
                "Node v403",
                "java-side-runtime-execution-approval-input-template-compatibility-receipt",
                "java-runtime-execution-approval-input-template-compatibility-receipt-v166",
                sourceHandoff.canonicalJavaApprovalInputPath(),
                sourceHandoff.canonicalJavaApprovalInputEndpoint(),
                sourceHandoff.canonicalJavaApprovalInputFixture(),
                templateMatrix(),
                canonicalTargetPaths(),
                templateArchivePaths(),
                javaTemplateBindingFields(sourceHandoff),
                compatibilityChecks(),
                blockedCanonicalInputs(),
                failClosedRules(sourceHandoff),
                stopConditions(),
                EVIDENCE_PATH,
                compatibilityStatus(sourceHandoff)
        );
    }

    private List<String> templateMatrix() {
        return List.of(
                "node-approved-runtime-window:canonical=e/398/input/node-approved-runtime-window-v398.json:template=e/402/input-templates/node-approved-runtime-window-v402.template.json",
                "correlated-operator-approval-record:canonical=e/398/input/correlated-operator-approval-record-v398.json:template=e/402/input-templates/correlated-operator-approval-record-v402.template.json",
                "complete-cross-project-runtime-execution-packet:canonical=e/398/input/cross-project-runtime-execution-packet-v398.json:template=e/402/input-templates/cross-project-runtime-execution-packet-v402.template.json"
        );
    }

    private List<String> canonicalTargetPaths() {
        return List.of(
                "e/398/input/node-approved-runtime-window-v398.json",
                "e/398/input/correlated-operator-approval-record-v398.json",
                "e/398/input/cross-project-runtime-execution-packet-v398.json"
        );
    }

    private List<String> templateArchivePaths() {
        return List.of(
                "e/402/input-templates/node-approved-runtime-window-v402.template.json",
                "e/402/input-templates/correlated-operator-approval-record-v402.template.json",
                "e/402/input-templates/cross-project-runtime-execution-packet-v402.template.json"
        );
    }

    private List<String> javaTemplateBindingFields(
            OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffResponse sourceHandoff
    ) {
        return List.of(
                "java-input-version:" + sourceHandoff.sourceApprovalGateInputVersion(),
                "java-input-path:" + sourceHandoff.canonicalJavaApprovalInputPath(),
                "java-input-endpoint:" + sourceHandoff.canonicalJavaApprovalInputEndpoint(),
                "java-input-fixture:" + sourceHandoff.canonicalJavaApprovalInputFixture(),
                "java-loopback-port:8080",
                "java-service-owner:java-platform-operator-confirmed",
                "java-startup-command-owner:java-platform-operator",
                "java-get-only-smoke-commands:required-by-final-packet",
                "java-stop-only-owned-process-rules:required-by-final-packet"
        );
    }

    private List<String> compatibilityChecks() {
        return List.of(
                "java-v164-canonical-input-path-template-bindable",
                "java-service-owner-template-field-present",
                "java-loopback-port-template-field-present",
                "java-get-only-smoke-commands-remain-get-only",
                "approval-correlation-id-required-but-not-issued-by-java",
                "template-archives-are-not-canonical-approval-inputs",
                "cleanup-proof-required-after-approved-runtime-start"
        );
    }

    private List<String> blockedCanonicalInputs() {
        return List.of(
                "node-approved-runtime-window:canonical-file-missing-or-not-owned-by-java",
                "correlated-operator-approval-record:canonical-file-missing-or-not-owned-by-java",
                "complete-cross-project-runtime-execution-packet:canonical-file-missing-or-not-owned-by-java"
        );
    }

    private List<String> failClosedRules(
            OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffResponse sourceHandoff
    ) {
        return List.of(
                "source-contract-handoff-status-must-be-passed:" + sourceHandoff.status(),
                "node-v402-templates-are-template-only-not-approval-inputs",
                "java-does-not-create-e398-canonical-approval-input-files",
                "missing-node-approved-runtime-window-blocks-runtime-execution",
                "missing-correlated-operator-approval-record-blocks-runtime-execution",
                "missing-complete-cross-project-runtime-execution-packet-blocks-runtime-execution",
                "template-compatibility-receipt-alone-is-not-runtime-approval"
        );
    }

    private List<String> stopConditions() {
        return List.of(
                "request-would-copy-template-to-canonical-input-path",
                "request-would-start-java-from-template-compatibility-receipt",
                "request-would-stop-java-from-template-compatibility-receipt",
                "request-would-run-runtime-probe-from-template-compatibility-receipt",
                "request-would-treat-template-as-node-approved-runtime-window",
                "request-would-treat-template-as-correlated-operator-approval",
                "request-would-treat-template-as-complete-cross-project-packet",
                "request-would-read-credential-or-raw-endpoint-value",
                "request-would-enable-active-shard-router-or-write-routing"
        );
    }

    private String compatibilityStatus(
            OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffResponse sourceHandoff
    ) {
        boolean sourcePassed = "passed".equals(sourceHandoff.status());
        boolean sourceComplete = sourceHandoff.javaApprovalInputContractHandoffPresent()
                && sourceHandoff.javaApprovalInputContractHandoffComplete()
                && sourceHandoff.javaInputRemainsCanonical();
        boolean runtimeStillClosed = !sourceHandoff.runtimeGateApprovalPresent()
                && !sourceHandoff.nodeApprovedRuntimeWindowPresent()
                && !sourceHandoff.correlatedOperatorApprovalRecordPresent()
                && !sourceHandoff.completeCrossProjectRuntimeExecutionPacketPresent()
                && !sourceHandoff.crossProjectRuntimeExecutionPacketExecutable()
                && !sourceHandoff.readyForRuntimeExecutionPacket()
                && !sourceHandoff.readyForRuntimeLiveReadGate()
                && !sourceHandoff.executionAllowed()
                && !sourceHandoff.executionAttempted();

        return sourcePassed && sourceComplete && runtimeStillClosed ? "passed" : "blocked";
    }
}
