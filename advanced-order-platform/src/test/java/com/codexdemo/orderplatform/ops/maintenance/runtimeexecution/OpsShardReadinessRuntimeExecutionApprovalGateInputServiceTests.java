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

class OpsShardReadinessRuntimeExecutionApprovalGateInputServiceTests {

  @Test
  void buildsJavaSideApprovalGateInputWithoutRuntimeApproval() {
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

    OpsShardReadinessRuntimeExecutionApprovalGateInputResponse input =
        new OpsShardReadinessRuntimeExecutionApprovalGateInputService(packetContributionService)
            .approvalGateInput();

    assertThat(input.project()).isEqualTo("advanced-order-platform");
    assertThat(input.version()).isEqualTo("Java v164");
    assertThat(input.readOnly()).isTrue();
    assertThat(input.executionAllowed()).isFalse();
    assertThat(input.javaApprovalGateInputPresent()).isTrue();
    assertThat(input.javaApprovalGateInputComplete()).isTrue();
    assertThat(input.runtimeGateApprovalPresent()).isFalse();
    assertThat(input.nodeApprovedRuntimeWindowPresent()).isFalse();
    assertThat(input.correlatedOperatorApprovalRecordPresent()).isFalse();
    assertThat(input.crossProjectRuntimeExecutionPacketPresent()).isFalse();
    assertThat(input.crossProjectRuntimeExecutionPacketExecutable()).isFalse();
    assertThat(input.readyForRuntimeExecutionPacket()).isFalse();
    assertThat(input.readyForRuntimeLiveReadGate()).isFalse();
    assertThat(input.executionAttempted()).isFalse();
    assertThat(input.startsJavaService()).isFalse();
    assertThat(input.startsMiniKvService()).isFalse();
    assertThat(input.activeShardPrototypeEnabled()).isFalse();
    assertThat(input.sourcePacketContributionVersion()).isEqualTo("Java v163");
    assertThat(input.lastReviewedByNodeVersion()).isEqualTo("Node v397");
    assertThat(input.lastArchiveVerifiedByNodeVersion()).isEqualTo("Node v399");
    assertThat(input.nextNodeConsumerHint()).isEqualTo("Node v400");
    assertThat(input.inputScope()).isEqualTo("java-side-runtime-execution-approval-gate-input");
    assertThat(input.approvalGateInputId())
        .isEqualTo("java-runtime-execution-approval-gate-input-v164");
    assertThat(input.approvalGateInputPath())
        .isEqualTo(
            "e/164/evidence/java-shard-readiness-runtime-execution-approval-gate-input-v164.json");
    assertThat(input.javaOperatorApprovalRecordId())
        .isEqualTo("java-runtime-packet-contribution-approval-record-v163");
    assertThat(input.approvalCorrelationRequirement())
        .isEqualTo("node-v400-must-correlate-java-mini-kv-and-node-approved-runtime-window");
    assertThat(input.javaLoopbackPort()).isEqualTo("8080");
    assertThat(input.javaServiceOwner()).isEqualTo("java-platform-operator-confirmed");
    assertThat(input.javaStartupCommand())
        .isEqualTo("mvn spring-boot:run -Dspring-boot.run.profiles=local");
    assertThat(input.javaApprovalInputArtifacts())
        .containsExactly(
            "source-packet-contribution:Java v163",
            "java-operator-approval-record:java-runtime-packet-contribution-approval-record-v163",
            "java-loopback-port:8080",
            "java-service-owner:java-platform-operator-confirmed",
            "java-get-only-smoke-commands:3",
            "java-stop-only-owned-process-rules:4");
    assertThat(input.javaPacketRowsForCorrelation())
        .containsExactly(
            "java-approval-input:operator-approval-record:java-side-record-present-cross-project-signature-required",
            "java-approval-input:concrete-loopback-ports:java-8080-present-mini-kv-required",
            "java-approval-input:get-only-smoke-command:java-get-only-present-mini-kv-required",
            "java-approval-input:cleanup-proof:java-cleanup-proof-reference-present-runtime-start-archive-required",
            "java-approval-input:service-owner-confirmation:java-platform-operator-confirmed-mini-kv-required",
            "java-approval-input:process-cleanup-rules:java-stop-only-owned-process-rules-present-mini-kv-required");
    assertThat(input.requiredSiblingInputs())
        .containsExactly(
            "mini-kv-approval-gate-input",
            "node-approved-runtime-window",
            "correlated-operator-approval-record",
            "complete-cross-project-runtime-execution-packet");
    assertThat(input.nodeApprovalGateInputPaths())
        .containsExactly(
            "e/398/input/node-approved-runtime-window-v398.json",
            "e/398/input/correlated-operator-approval-record-v398.json",
            "e/398/input/cross-project-runtime-execution-packet-v398.json");
    assertThat(input.failClosedRules())
        .contains(
            "source-contribution-status-must-be-passed:passed",
            "missing-node-approved-runtime-window-blocks-runtime-execution",
            "java-approval-gate-input-alone-is-not-runtime-approval");
    assertThat(input.stopConditions())
        .contains(
            "request-would-start-java-from-approval-gate-input",
            "request-would-treat-java-only-input-as-correlated-approval",
            "request-would-enable-active-shard-router-or-write-routing");
    assertThat(input.evidencePath())
        .isEqualTo(
            "e/164/evidence/java-shard-readiness-runtime-execution-approval-gate-input-v164.json");
    assertThat(input.status()).isEqualTo("passed");
  }
}
