package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRuntimeExecutionApprovalInputValueValidationServiceTests {

    @Test
    void buildsJavaValueValidationReceiptWithoutStartingRuntimeSmoke() {
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
        OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService templateCompatibilityService =
                new OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService(contractHandoffService);
        OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService compatibilityIntakeService =
                new OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService(
                        templateCompatibilityService
                );

        OpsShardReadinessRuntimeExecutionApprovalInputValueValidationResponse receipt =
                new OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService(compatibilityIntakeService)
                        .validation();

        assertThat(receipt.project()).isEqualTo("advanced-order-platform");
        assertThat(receipt.version()).isEqualTo("Java v168");
        assertThat(receipt.readOnly()).isTrue();
        assertThat(receipt.executionAllowed()).isFalse();
        assertThat(receipt.valueValidationReceiptPresent()).isTrue();
        assertThat(receipt.valueValidationReceiptComplete()).isTrue();
        assertThat(receipt.sourceCompatibilityIntakePresent()).isTrue();
        assertThat(receipt.sourceCompatibilityIntakeComplete()).isTrue();
        assertThat(receipt.nodeCanonicalApprovalInputValueValidationPresent()).isTrue();
        assertThat(receipt.nodeCanonicalApprovalInputValueValidationAccepted()).isTrue();
        assertThat(receipt.canonicalApprovalInputsPresent()).isTrue();
        assertThat(receipt.canonicalApprovalInputsValueValid()).isTrue();
        assertThat(receipt.sharedApprovalCorrelationIdValidated()).isTrue();
        assertThat(receipt.runtimeExecutionPacketPresent()).isTrue();
        assertThat(receipt.runtimeExecutionPacketExecutable()).isTrue();
        assertThat(receipt.runtimeGateApprovalPresent()).isTrue();
        assertThat(receipt.concreteLoopbackPortsAssigned()).isTrue();
        assertThat(receipt.readyForRuntimeExecutionPacket()).isTrue();
        assertThat(receipt.readyForRuntimeLiveReadGate()).isTrue();
        assertThat(receipt.executionAttempted()).isFalse();
        assertThat(receipt.startsJavaService()).isFalse();
        assertThat(receipt.startsMiniKvService()).isFalse();
        assertThat(receipt.mutatesJavaState()).isFalse();
        assertThat(receipt.mutatesMiniKvState()).isFalse();
        assertThat(receipt.connectsManagedAudit()).isFalse();
        assertThat(receipt.credentialValueRead()).isFalse();
        assertThat(receipt.rawEndpointUrlParsed()).isFalse();
        assertThat(receipt.writeOperationsAllowed()).isFalse();
        assertThat(receipt.activeShardPrototypeEnabled()).isFalse();
        assertThat(receipt.sourceCompatibilityIntakeVersion()).isEqualTo("Java v167");
        assertThat(receipt.sourceNodePrecheckVersion()).isEqualTo("Node v404");
        assertThat(receipt.nodeValueValidationVersion()).isEqualTo("Node v405");
        assertThat(receipt.miniKvPrecheckVersion()).isEqualTo("mini-kv v158");
        assertThat(receipt.nextNodeConsumerHint()).isEqualTo("Node v406");
        assertThat(receipt.validationState())
                .isEqualTo("runtime-execution-canonical-approval-input-value-validation-ready");
        assertThat(receipt.validationDecision())
                .isEqualTo("accept-canonical-approval-input-values-for-next-live-read-gate");
        assertThat(receipt.receiptId())
                .isEqualTo("java-runtime-execution-approval-input-value-validation-receipt-v168");
        assertThat(receipt.presentTargetInputCount()).isEqualTo(3);
        assertThat(receipt.validTargetInputCount()).isEqualTo(3);
        assertThat(receipt.nodeCheckCount()).isEqualTo(32);
        assertThat(receipt.nodePassedCheckCount()).isEqualTo(32);
        assertThat(receipt.nodeProductionBlockerCount()).isZero();
        assertThat(receipt.canonicalInputPaths())
                .containsExactly(
                        "e/398/input/node-approved-runtime-window-v398.json",
                        "e/398/input/correlated-operator-approval-record-v398.json",
                        "e/398/input/cross-project-runtime-execution-packet-v398.json"
                );
        assertThat(receipt.acceptedNodeValidationFields())
                .contains(
                        "readyForRuntimeExecutionPacket:true",
                        "sharedApprovalCorrelationIdValidated:true",
                        "executionAttempted:false"
                );
        assertThat(receipt.javaValidationChecks())
                .contains(
                        "node-v405-accepts-real-canonical-input-values",
                        "runtime-packet-present-but-java-does-not-start-service",
                        "live-read-gate-ready-but-smoke-requires-separate-node-v406-gate"
                );
        assertThat(receipt.allowedRuntimeSmokeCommands())
                .containsExactly(
                        "java:GET:http://127.0.0.1:8080/actuator/health",
                        "mini-kv:GET:127.0.0.1:6424:/health"
                );
        assertThat(receipt.serviceOwnershipBoundaries())
                .contains(
                        "java-owner:java-platform-operator",
                        "cleanup-proof-required-after-run"
                );
        assertThat(receipt.failClosedRules())
                .contains(
                        "node-v405-value-validation-is-not-java-service-startup",
                        "java-v168-does-not-run-runtime-smoke",
                        "node-v406-live-read-gate-required-before-any-approved-smoke"
                );
        assertThat(receipt.stopConditions())
                .contains(
                        "request-would-start-java-from-value-validation-receipt",
                        "request-would-run-smoke-before-node-v406-live-read-gate"
                );
        assertThat(receipt.evidencePath())
                .isEqualTo("e/168/evidence/java-shard-readiness-runtime-execution-approval-input-value-validation-v168.json");
        assertThat(receipt.status()).isEqualTo("passed");
    }
}
