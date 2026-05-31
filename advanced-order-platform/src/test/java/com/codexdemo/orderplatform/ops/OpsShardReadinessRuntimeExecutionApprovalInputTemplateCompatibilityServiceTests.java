package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityServiceTests {

    @Test
    void buildsJavaTemplateCompatibilityReceiptWithoutApprovalInputs() {
        OpsShardReadinessEvidenceIndexService indexService = new OpsShardReadinessEvidenceIndexService();
        OpsShardReadinessEvidenceVerificationService verificationService =
                new OpsShardReadinessEvidenceVerificationService(indexService);
        OpsShardReadinessEvidenceHandoffService evidenceHandoffService =
                new OpsShardReadinessEvidenceHandoffService(indexService, verificationService);
        OpsShardReadinessActiveShardPlanHandoffService activeShardPlanHandoffService =
                new OpsShardReadinessActiveShardPlanHandoffService(evidenceHandoffService);
        OpsShardReadinessLiveReadGatePlanService liveReadGatePlanService =
                new OpsShardReadinessLiveReadGatePlanService(activeShardPlanHandoffService);
        OpsShardReadinessOperatorServiceLifecycleService operatorLifecycleService =
                new OpsShardReadinessOperatorServiceLifecycleService(liveReadGatePlanService);
        OpsShardReadinessDeclaredOperatorLifecycleService declaredLifecycleService =
                new OpsShardReadinessDeclaredOperatorLifecycleService(operatorLifecycleService);
        OpsShardReadinessRuntimeExecutionArtifactCandidateService artifactCandidateService =
                new OpsShardReadinessRuntimeExecutionArtifactCandidateService(declaredLifecycleService);
        OpsShardReadinessRuntimeExecutionPacketContributionService packetContributionService =
                new OpsShardReadinessRuntimeExecutionPacketContributionService(artifactCandidateService);
        OpsShardReadinessRuntimeExecutionApprovalGateInputService approvalGateInputService =
                new OpsShardReadinessRuntimeExecutionApprovalGateInputService(packetContributionService);
        OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService contractHandoffService =
                new OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService(approvalGateInputService);

        OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityResponse receipt =
                new OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService(contractHandoffService)
                        .compatibility();

        assertThat(receipt.project()).isEqualTo("advanced-order-platform");
        assertThat(receipt.version()).isEqualTo("Java v166");
        assertThat(receipt.readOnly()).isTrue();
        assertThat(receipt.executionAllowed()).isFalse();
        assertThat(receipt.templateCompatibilityReceiptPresent()).isTrue();
        assertThat(receipt.templateCompatibilityReceiptComplete()).isTrue();
        assertThat(receipt.sourceContractHandoffPresent()).isTrue();
        assertThat(receipt.sourceJavaInputCanonical()).isTrue();
        assertThat(receipt.nodeTemplateValidatorPresent()).isTrue();
        assertThat(receipt.templatesAreApprovalInputs()).isFalse();
        assertThat(receipt.canonicalApprovalInputsCreatedByJava()).isFalse();
        assertThat(receipt.runtimeGateApprovalPresent()).isFalse();
        assertThat(receipt.nodeApprovedRuntimeWindowPresent()).isFalse();
        assertThat(receipt.correlatedOperatorApprovalRecordPresent()).isFalse();
        assertThat(receipt.completeCrossProjectRuntimeExecutionPacketPresent()).isFalse();
        assertThat(receipt.crossProjectRuntimeExecutionPacketExecutable()).isFalse();
        assertThat(receipt.readyForRuntimeExecutionPacket()).isFalse();
        assertThat(receipt.readyForRuntimeLiveReadGate()).isFalse();
        assertThat(receipt.executionAttempted()).isFalse();
        assertThat(receipt.startsJavaService()).isFalse();
        assertThat(receipt.startsMiniKvService()).isFalse();
        assertThat(receipt.activeShardPrototypeEnabled()).isFalse();
        assertThat(receipt.sourceContractHandoffVersion()).isEqualTo("Java v165");
        assertThat(receipt.sourceCanonicalJavaInputVersion()).isEqualTo("Java v164");
        assertThat(receipt.lastTemplateValidatorNodeVersion()).isEqualTo("Node v402");
        assertThat(receipt.nextNodeConsumerHint()).isEqualTo("Node v403");
        assertThat(receipt.receiptScope())
                .isEqualTo("java-side-runtime-execution-approval-input-template-compatibility-receipt");
        assertThat(receipt.receiptId())
                .isEqualTo("java-runtime-execution-approval-input-template-compatibility-receipt-v166");
        assertThat(receipt.canonicalJavaApprovalInputPath())
                .isEqualTo("e/164/evidence/java-shard-readiness-runtime-execution-approval-gate-input-v164.json");
        assertThat(receipt.canonicalJavaApprovalInputEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/runtime-execution-approval-gate-input");
        assertThat(receipt.canonicalJavaApprovalInputFixture())
                .isEqualTo("/contracts/java-shard-readiness-runtime-execution-approval-gate-input-v164.fixture.json");
        assertThat(receipt.templateMatrix())
                .containsExactly(
                        "node-approved-runtime-window:canonical=e/398/input/node-approved-runtime-window-v398.json:template=e/402/input-templates/node-approved-runtime-window-v402.template.json",
                        "correlated-operator-approval-record:canonical=e/398/input/correlated-operator-approval-record-v398.json:template=e/402/input-templates/correlated-operator-approval-record-v402.template.json",
                        "complete-cross-project-runtime-execution-packet:canonical=e/398/input/cross-project-runtime-execution-packet-v398.json:template=e/402/input-templates/cross-project-runtime-execution-packet-v402.template.json"
                );
        assertThat(receipt.canonicalTargetPaths())
                .containsExactly(
                        "e/398/input/node-approved-runtime-window-v398.json",
                        "e/398/input/correlated-operator-approval-record-v398.json",
                        "e/398/input/cross-project-runtime-execution-packet-v398.json"
                );
        assertThat(receipt.templateArchivePaths())
                .containsExactly(
                        "e/402/input-templates/node-approved-runtime-window-v402.template.json",
                        "e/402/input-templates/correlated-operator-approval-record-v402.template.json",
                        "e/402/input-templates/cross-project-runtime-execution-packet-v402.template.json"
                );
        assertThat(receipt.javaTemplateBindingFields())
                .contains(
                        "java-input-version:Java v164",
                        "java-loopback-port:8080",
                        "java-get-only-smoke-commands:required-by-final-packet"
                );
        assertThat(receipt.compatibilityChecks())
                .contains(
                        "approval-correlation-id-required-but-not-issued-by-java",
                        "template-archives-are-not-canonical-approval-inputs"
                );
        assertThat(receipt.blockedCanonicalInputs())
                .containsExactly(
                        "node-approved-runtime-window:canonical-file-missing-or-not-owned-by-java",
                        "correlated-operator-approval-record:canonical-file-missing-or-not-owned-by-java",
                        "complete-cross-project-runtime-execution-packet:canonical-file-missing-or-not-owned-by-java"
                );
        assertThat(receipt.failClosedRules())
                .contains(
                        "node-v402-templates-are-template-only-not-approval-inputs",
                        "java-does-not-create-e398-canonical-approval-input-files",
                        "template-compatibility-receipt-alone-is-not-runtime-approval"
                );
        assertThat(receipt.stopConditions())
                .contains(
                        "request-would-copy-template-to-canonical-input-path",
                        "request-would-treat-template-as-complete-cross-project-packet",
                        "request-would-enable-active-shard-router-or-write-routing"
                );
        assertThat(receipt.evidencePath())
                .isEqualTo("e/166/evidence/java-shard-readiness-runtime-execution-approval-input-template-compatibility-v166.json");
        assertThat(receipt.status()).isEqualTo("passed");
    }
}
