package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRuntimeExecutionArtifactCandidateServiceTests {

    @Test
    void buildsJavaSideRuntimeExecutionArtifactCandidateWithoutRuntimeApproval() {
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

        OpsShardReadinessRuntimeExecutionArtifactCandidateResponse candidate =
                new OpsShardReadinessRuntimeExecutionArtifactCandidateService(declaredLifecycleService).candidate();

        assertThat(candidate.project()).isEqualTo("advanced-order-platform");
        assertThat(candidate.version()).isEqualTo("Java v162");
        assertThat(candidate.readOnly()).isTrue();
        assertThat(candidate.executionAllowed()).isFalse();
        assertThat(candidate.javaRuntimeArtifactCandidatePresent()).isTrue();
        assertThat(candidate.javaRuntimeArtifactsDeclared()).isTrue();
        assertThat(candidate.javaRuntimeArtifactsComplete()).isTrue();
        assertThat(candidate.crossProjectRuntimeArtifactsComplete()).isFalse();
        assertThat(candidate.runtimeExecutionPacketPresent()).isFalse();
        assertThat(candidate.runtimeExecutionPacketExecutable()).isFalse();
        assertThat(candidate.readyForRuntimeExecutionPacket()).isFalse();
        assertThat(candidate.readyForRuntimeLiveReadGate()).isFalse();
        assertThat(candidate.executionAttempted()).isFalse();
        assertThat(candidate.startsJavaService()).isFalse();
        assertThat(candidate.startsMiniKvService()).isFalse();
        assertThat(candidate.activeShardPrototypeEnabled()).isFalse();
        assertThat(candidate.sourceDeclaredLifecycleVersion()).isEqualTo("Java v161");
        assertThat(candidate.lastVerifiedByNodeVersion()).isEqualTo("Node v395");
        assertThat(candidate.nextNodeConsumerHint()).isEqualTo("Node v396");
        assertThat(candidate.operatorApprovalRecord())
                .isEqualTo("java-runtime-artifact-candidate-operator-record-v162");
        assertThat(candidate.operatorApprovalScope()).isEqualTo("java-side-artifact-candidate-only");
        assertThat(candidate.serviceOwner()).isEqualTo("java-platform-operator");
        assertThat(candidate.startupCommandOwner()).isEqualTo("java-platform-operator");
        assertThat(candidate.cleanupOwner()).isEqualTo("java-platform-operator");
        assertThat(candidate.declaredWorkingDirectory()).isEqualTo("advanced-order-platform");
        assertThat(candidate.declaredStartupCommand())
                .isEqualTo("mvn spring-boot:run -Dspring-boot.run.profiles=local");
        assertThat(candidate.javaLoopbackPort()).isEqualTo("8080");
        assertThat(candidate.miniKvLoopbackPort()).isEqualTo("requires-mini-kv-runtime-artifact");
        assertThat(candidate.javaBaseUrlHandle()).isEqualTo("java-local-readonly-base-url");
        assertThat(candidate.getOnlySmokeCommands())
                .containsExactly(
                        "GET java-loopback-port-8080 /actuator/health",
                        "GET java-loopback-port-8080 /api/v1/ops/shard-readiness/runtime-execution-artifact-candidate",
                        "GET java-loopback-port-8080 /api/v1/ops/shard-readiness/declared-operator-lifecycle"
                );
        assertThat(candidate.cleanupProofs())
                .contains(
                        "java-operator-owns-cleanup-if-java-operator-starts-service",
                        "no-cleanup-executed-by-this-read-only-candidate"
                );
        assertThat(candidate.processCleanupRules())
                .contains(
                        "record-java-process-id-before-approved-runtime-start",
                        "do-not-stop-pre-existing-java-service"
                );
        assertThat(candidate.failClosedRules())
                .contains(
                        "missing-mini-kv-runtime-artifact-blocks-runtime-execution-packet",
                        "failed-smoke-command-blocks-node-consumption"
                );
        assertThat(candidate.missingCrossProjectArtifacts())
                .containsExactly(
                        "mini-kv-v153-runtime-artifact-candidate",
                        "cross-project-runtime-execution-packet",
                        "node-approved-runtime-execution-window"
                );
        assertThat(candidate.stopConditions())
                .contains(
                        "request-would-start-java-from-this-candidate",
                        "request-would-use-candidate-as-cross-project-runtime-approval",
                        "request-would-enable-active-shard-router-or-write-routing"
                );
        assertThat(candidate.evidencePath())
                .isEqualTo("e/162/evidence/java-shard-readiness-runtime-execution-artifact-candidate-v162.json");
        assertThat(candidate.status()).isEqualTo("passed");
    }
}
