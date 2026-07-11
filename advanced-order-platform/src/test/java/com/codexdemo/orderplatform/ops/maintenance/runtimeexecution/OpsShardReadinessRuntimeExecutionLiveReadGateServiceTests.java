package com.codexdemo.orderplatform.ops.maintenance.runtimeexecution;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.OpsShardReadinessActiveShardPlanHandoffService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessDeclaredOperatorLifecycleService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessEvidenceHandoffService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessEvidenceIndexService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessEvidenceVerificationService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessLiveReadGatePlanService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessOperatorServiceLifecycleService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessRuntimeExecutionLiveReadGateServiceTests {

  @Test
  void buildsJavaLiveReadGateReceiptWithoutRunningSmoke() {
    OpsShardReadinessEvidenceIndexService indexService =
        new OpsShardReadinessEvidenceIndexService();
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
        new OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService(
            approvalGateInputService);
    OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService
        templateCompatibilityService =
            new OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService(
                contractHandoffService);
    OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService
        compatibilityIntakeService =
            new OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService(
                templateCompatibilityService);
    OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService valueValidationService =
        new OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService(
            compatibilityIntakeService);

    OpsShardReadinessRuntimeExecutionLiveReadGateResponse receipt =
        new OpsShardReadinessRuntimeExecutionLiveReadGateService(valueValidationService).gate();

    assertThat(receipt.project()).isEqualTo("advanced-order-platform");
    assertThat(receipt.version()).isEqualTo("Java v169");
    assertThat(receipt.readOnly()).isTrue();
    assertThat(receipt.executionAllowed()).isFalse();
    assertThat(receipt.liveReadGateReceiptPresent()).isTrue();
    assertThat(receipt.liveReadGateReceiptComplete()).isTrue();
    assertThat(receipt.sourceValueValidationPresent()).isTrue();
    assertThat(receipt.sourceValueValidationComplete()).isTrue();
    assertThat(receipt.nodeLiveReadGatePresent()).isTrue();
    assertThat(receipt.nodeLiveReadGateAccepted()).isTrue();
    assertThat(receipt.readyForRuntimeExecutionPacket()).isTrue();
    assertThat(receipt.readyForRuntimeLiveReadGate()).isTrue();
    assertThat(receipt.readyForApprovedLocalLoopbackReadOnlySmoke()).isTrue();
    assertThat(receipt.runtimeExecutionPacketPresent()).isTrue();
    assertThat(receipt.runtimeExecutionPacketExecutable()).isTrue();
    assertThat(receipt.runtimeGateApprovalPresent()).isTrue();
    assertThat(receipt.runtimeSmokeAttempted()).isFalse();
    assertThat(receipt.startsJavaService()).isFalse();
    assertThat(receipt.startsMiniKvService()).isFalse();
    assertThat(receipt.mutatesJavaState()).isFalse();
    assertThat(receipt.mutatesMiniKvState()).isFalse();
    assertThat(receipt.connectsManagedAudit()).isFalse();
    assertThat(receipt.credentialValueRead()).isFalse();
    assertThat(receipt.rawEndpointUrlParsed()).isFalse();
    assertThat(receipt.writeOperationsAllowed()).isFalse();
    assertThat(receipt.activeShardPrototypeEnabled()).isFalse();
    assertThat(receipt.cleanupProofRequired()).isTrue();
    assertThat(receipt.sourceValueValidationVersion()).isEqualTo("Java v168");
    assertThat(receipt.sourceNodeValueValidationVersion()).isEqualTo("Node v405");
    assertThat(receipt.nodeLiveReadGateVersion()).isEqualTo("Node v406");
    assertThat(receipt.nextNodeConsumerHint()).isEqualTo("Node v407");
    assertThat(receipt.gateDecision())
        .isEqualTo("accept-live-read-gate-for-approved-local-loopback-read-only-smoke");
    assertThat(receipt.receiptId()).isEqualTo("java-runtime-execution-live-read-gate-receipt-v169");
    assertThat(receipt.targetCount()).isEqualTo(2);
    assertThat(receipt.readyTargetCount()).isEqualTo(2);
    assertThat(receipt.nodeCheckCount()).isEqualTo(33);
    assertThat(receipt.nodePassedCheckCount()).isEqualTo(33);
    assertThat(receipt.nodeProductionBlockerCount()).isZero();
    assertThat(receipt.runtimeTargets())
        .containsExactly(
            "java:owner=java-platform-operator:GET:http://127.0.0.1:8080/actuator/health",
            "mini-kv:owner=mini-kv-service-owner:GET:127.0.0.1:6424:/health");
    assertThat(receipt.acceptedNodeGateFields())
        .contains(
            "readyForRuntimeExecutionLiveReadGate:true",
            "readyForApprovedLocalLoopbackReadOnlySmoke:true",
            "runtimeSmokeAttempted:false");
    assertThat(receipt.javaGateChecks())
        .contains(
            "node-v406-gate-consumes-node-v405-value-validation",
            "java-loopback-target-is-actuator-health-get-only",
            "java-v169-does-not-attempt-runtime-smoke");
    assertThat(receipt.cleanupProofRequirements())
        .contains(
            "record-owned-java-pid-if-started-by-later-smoke",
            "stop-only-owned-processes",
            "verify-port-8080-not-listening-after-cleanup");
    assertThat(receipt.failClosedRules())
        .contains(
            "node-v406-live-read-gate-is-not-smoke-pass-evidence",
            "java-v169-does-not-run-runtime-smoke",
            "node-v407-must-capture-approved-local-loopback-read-only-smoke");
    assertThat(receipt.stopConditions())
        .contains(
            "request-would-treat-live-read-gate-as-smoke-pass",
            "request-would-skip-owned-process-cleanup-proof");
    assertThat(receipt.evidencePath())
        .isEqualTo(
            "e/169/evidence/java-shard-readiness-runtime-execution-live-read-gate-v169.json");
    assertThat(receipt.status()).isEqualTo("passed");
  }
}
