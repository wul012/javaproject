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

class OpsShardReadinessRuntimeExecutionPacketContributionServiceTests {

  @Test
  void buildsJavaSideRuntimeExecutionPacketContributionWithoutCrossProjectApproval() {
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

    OpsShardReadinessRuntimeExecutionPacketContributionResponse contribution =
        new OpsShardReadinessRuntimeExecutionPacketContributionService(artifactCandidateService)
            .contribution();

    assertThat(contribution.project()).isEqualTo("advanced-order-platform");
    assertThat(contribution.version()).isEqualTo("Java v163");
    assertThat(contribution.readOnly()).isTrue();
    assertThat(contribution.executionAllowed()).isFalse();
    assertThat(contribution.javaPacketContributionPresent()).isTrue();
    assertThat(contribution.javaPacketContributionComplete()).isTrue();
    assertThat(contribution.crossProjectRuntimeExecutionPacketPresent()).isFalse();
    assertThat(contribution.crossProjectRuntimeExecutionPacketExecutable()).isFalse();
    assertThat(contribution.readyForRuntimeExecutionPacket()).isFalse();
    assertThat(contribution.readyForRuntimeLiveReadGate()).isFalse();
    assertThat(contribution.executionAttempted()).isFalse();
    assertThat(contribution.startsJavaService()).isFalse();
    assertThat(contribution.startsMiniKvService()).isFalse();
    assertThat(contribution.activeShardPrototypeEnabled()).isFalse();
    assertThat(contribution.sourceRuntimeArtifactCandidateVersion()).isEqualTo("Java v162");
    assertThat(contribution.lastClarifiedByNodeVersion()).isEqualTo("Node v396");
    assertThat(contribution.nextNodeConsumerHint()).isEqualTo("Node v397");
    assertThat(contribution.contributionScope())
        .isEqualTo("java-side-runtime-execution-packet-contribution");
    assertThat(contribution.operatorApprovalRecordId())
        .isEqualTo("java-runtime-packet-contribution-approval-record-v163");
    assertThat(contribution.operatorApprovalCorrelationRequirement())
        .isEqualTo("must-be-correlated-by-node-approved-cross-project-runtime-window");
    assertThat(contribution.javaLoopbackPort()).isEqualTo("8080");
    assertThat(contribution.miniKvLoopbackPortRequirement())
        .isEqualTo("requires-mini-kv-runtime-packet-contribution");
    assertThat(contribution.serviceOwnerConfirmation())
        .isEqualTo("java-platform-operator-confirmed");
    assertThat(contribution.startupCommand())
        .isEqualTo("mvn spring-boot:run -Dspring-boot.run.profiles=local");
    assertThat(contribution.startupCommandOwner()).isEqualTo("java-platform-operator");
    assertThat(contribution.cleanupOwner()).isEqualTo("java-platform-operator");
    assertThat(contribution.acceptedRequirementRows())
        .containsExactly(
            "operator-approval-record:java-side-record-present-cross-project-signature-required",
            "concrete-loopback-ports:java-8080-present-mini-kv-required",
            "get-only-smoke-command:java-get-only-present-mini-kv-required",
            "cleanup-proof:java-cleanup-proof-reference-present-runtime-start-archive-required",
            "service-owner-confirmation:java-platform-operator-confirmed-mini-kv-required",
            "process-cleanup-rules:java-stop-only-owned-process-rules-present-mini-kv-required");
    assertThat(contribution.getOnlySmokeCommands())
        .containsExactly(
            "GET java-loopback-port-8080 /actuator/health",
            "GET java-loopback-port-8080 /api/v1/ops/shard-readiness/runtime-execution-packet-contribution",
            "GET java-loopback-port-8080 /api/v1/ops/shard-readiness/runtime-execution-artifact-candidate");
    assertThat(contribution.cleanupProofArtifacts())
        .contains(
            "java-cleanup-owner-confirmation:java-platform-operator",
            "java-cleanup-non-execution-proof:no-process-started-by-this-contribution");
    assertThat(contribution.processCleanupRules())
        .contains(
            "stop-only-java-process-started-by-approved-runtime-packet",
            "never-stop-pre-existing-java-service");
    assertThat(contribution.crossProjectMissingArtifacts())
        .containsExactly(
            "mini-kv-runtime-execution-packet-contribution",
            "node-approved-cross-project-runtime-window",
            "correlated-operator-approval-record-for-java-and-mini-kv");
    assertThat(contribution.failClosedRules())
        .contains(
            "missing-mini-kv-packet-contribution-blocks-runtime-execution",
            "uncorrelated-operator-approval-record-blocks-runtime-execution");
    assertThat(contribution.stopConditions())
        .contains(
            "request-would-start-java-from-this-contribution",
            "request-would-treat-java-only-contribution-as-cross-project-packet",
            "request-would-enable-active-shard-router-or-write-routing");
    assertThat(contribution.evidencePath())
        .isEqualTo(
            "e/163/evidence/java-shard-readiness-runtime-execution-packet-contribution-v163.json");
    assertThat(contribution.status()).isEqualTo("passed");
  }
}
