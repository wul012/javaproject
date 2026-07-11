package com.codexdemo.orderplatform.ops.maintenance.runtimeexecution;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessActiveShardPlanHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessDeclaredOperatorLifecycleService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceIndexService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceVerificationService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessLiveReadGatePlanService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessOperatorServiceLifecycleService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeServiceTests {

  @Test
  void buildsJavaCompatibilityIntakeReceiptWithoutCreatingApprovalInputs() {
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

    OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeResponse receipt =
        new OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService(
                templateCompatibilityService)
            .intake();

    assertThat(receipt.project()).isEqualTo("advanced-order-platform");
    assertThat(receipt.version()).isEqualTo("Java v167");
    assertThat(receipt.readOnly()).isTrue();
    assertThat(receipt.executionAllowed()).isFalse();
    assertThat(receipt.compatibilityIntakeReceiptPresent()).isTrue();
    assertThat(receipt.compatibilityIntakeReceiptComplete()).isTrue();
    assertThat(receipt.nodeCompatibilityIntakePresent()).isTrue();
    assertThat(receipt.nodeCompatibilityIntakeComplete()).isTrue();
    assertThat(receipt.sourceTemplateCompatibilityReceiptPresent()).isTrue();
    assertThat(receipt.sourceTemplateCompatibilityReceiptComplete()).isTrue();
    assertThat(receipt.sourceJavaInputCanonical()).isTrue();
    assertThat(receipt.nodeTemplateValidatorPresent()).isTrue();
    assertThat(receipt.templatesAreApprovalInputs()).isFalse();
    assertThat(receipt.canonicalApprovalInputsCreatedByJava()).isFalse();
    assertThat(receipt.canonicalApprovalInputsCreatedByNodeV403()).isFalse();
    assertThat(receipt.nodeApprovedRuntimeWindowPresent()).isFalse();
    assertThat(receipt.correlatedOperatorApprovalRecordPresent()).isFalse();
    assertThat(receipt.completeCrossProjectRuntimeExecutionPacketPresent()).isFalse();
    assertThat(receipt.runtimeExecutionPacketPresent()).isFalse();
    assertThat(receipt.runtimeGateApprovalPresent()).isFalse();
    assertThat(receipt.crossProjectRuntimeExecutionPacketExecutable()).isFalse();
    assertThat(receipt.readyForRuntimeExecutionPacket()).isFalse();
    assertThat(receipt.readyForRuntimeLiveReadGate()).isFalse();
    assertThat(receipt.executionAttempted()).isFalse();
    assertThat(receipt.startsJavaService()).isFalse();
    assertThat(receipt.startsMiniKvService()).isFalse();
    assertThat(receipt.stopsJavaService()).isFalse();
    assertThat(receipt.stopsMiniKvService()).isFalse();
    assertThat(receipt.mutatesJavaState()).isFalse();
    assertThat(receipt.mutatesMiniKvState()).isFalse();
    assertThat(receipt.connectsManagedAudit()).isFalse();
    assertThat(receipt.credentialValueRead()).isFalse();
    assertThat(receipt.rawEndpointUrlParsed()).isFalse();
    assertThat(receipt.activeShardPrototypeEnabled()).isFalse();
    assertThat(receipt.sourceTemplateCompatibilityVersion()).isEqualTo("Java v166");
    assertThat(receipt.sourceContractHandoffVersion()).isEqualTo("Java v165");
    assertThat(receipt.sourceCanonicalJavaInputVersion()).isEqualTo("Java v164");
    assertThat(receipt.sourceNodeTemplateValidatorVersion()).isEqualTo("Node v402");
    assertThat(receipt.nodeCompatibilityIntakeVersion()).isEqualTo("Node v403");
    assertThat(receipt.miniKvTemplateEchoVersion()).isEqualTo("mini-kv v157");
    assertThat(receipt.nextNodeConsumerHint()).isEqualTo("Node v404");
    assertThat(receipt.intakeState())
        .isEqualTo("runtime-execution-approval-input-template-compatibility-intake-blocked");
    assertThat(receipt.intakeDecision())
        .isEqualTo("record-upstream-template-compatibility-and-keep-runtime-blocked");
    assertThat(receipt.receiptScope())
        .isEqualTo(
            "java-side-runtime-execution-approval-input-template-compatibility-intake-receipt");
    assertThat(receipt.receiptId())
        .isEqualTo(
            "java-runtime-execution-approval-input-template-compatibility-intake-receipt-v167");
    assertThat(receipt.sourceJavaTemplateCompatibilityEvidencePath())
        .isEqualTo(
            "e/166/evidence/java-shard-readiness-runtime-execution-approval-input-template-compatibility-v166.json");
    assertThat(receipt.nodeCompatibilityIntakeEvidencePath())
        .isEqualTo(
            "D:/nodeproj/orderops-node/e/403/evidence/"
                + "java-mini-kv-runtime-execution-approval-input-template-compatibility-intake-v403-summary.json");
    assertThat(receipt.templateMatrix()).hasSize(3);
    assertThat(receipt.canonicalTargetPaths())
        .containsExactly(
            "e/398/input/node-approved-runtime-window-v398.json",
            "e/398/input/correlated-operator-approval-record-v398.json",
            "e/398/input/cross-project-runtime-execution-packet-v398.json");
    assertThat(receipt.templateArchivePaths()).hasSize(3);
    assertThat(receipt.compatibilityIntakeChecks())
        .contains(
            "node-v403-intake-consumes-java-v166-template-compatibility",
            "node-v403-intake-leaves-e398-canonical-inputs-missing",
            "next-node-version-requires-real-canonical-inputs:Node v404");
    assertThat(receipt.nodeV403IntakeFields())
        .contains(
            "readyForRuntimeExecutionApprovalInputTemplateCompatibilityIntake:true",
            "presentCanonicalInputCount:0",
            "missingCanonicalInputCount:3");
    assertThat(receipt.blockedCanonicalInputs())
        .containsExactly(
            "e/398/input/node-approved-runtime-window-v398.json:missing-real-approval-input",
            "e/398/input/correlated-operator-approval-record-v398.json:missing-real-approval-input",
            "e/398/input/cross-project-runtime-execution-packet-v398.json:missing-real-approval-input");
    assertThat(receipt.productionBlockers())
        .containsExactly(
            "NODE_APPROVED_RUNTIME_WINDOW_INPUT_NOT_PRESENT",
            "CORRELATED_OPERATOR_APPROVAL_RECORD_INPUT_NOT_PRESENT",
            "CROSS_PROJECT_RUNTIME_EXECUTION_PACKET_INPUT_NOT_PRESENT");
    assertThat(receipt.failClosedRules())
        .contains(
            "node-v403-intake-is-read-only-and-not-runtime-approval",
            "java-v167-does-not-create-e398-canonical-approval-input-files",
            "node-v404-may-run-only-after-real-canonical-approval-inputs-exist");
    assertThat(receipt.stopConditions())
        .contains(
            "request-would-treat-node-v403-intake-as-runtime-approval",
            "request-would-synthesize-approval-correlation-id",
            "request-would-open-managed-audit-connection");
    assertThat(receipt.evidencePath())
        .isEqualTo(
            "e/167/evidence/java-shard-readiness-runtime-execution-approval-input-template-compatibility-intake-v167.json");
    assertThat(receipt.status()).isEqualTo("passed");
  }
}
