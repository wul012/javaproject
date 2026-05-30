package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffServiceTests {

    @Test
    void buildsJavaSideApprovalInputContractHandoffWithoutRuntimeApproval() {
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

        OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffResponse handoff =
                new OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService(approvalGateInputService)
                        .handoff();

        assertThat(handoff.project()).isEqualTo("advanced-order-platform");
        assertThat(handoff.version()).isEqualTo("Java v165");
        assertThat(handoff.readOnly()).isTrue();
        assertThat(handoff.executionAllowed()).isFalse();
        assertThat(handoff.javaApprovalInputContractHandoffPresent()).isTrue();
        assertThat(handoff.javaApprovalInputContractHandoffComplete()).isTrue();
        assertThat(handoff.sourceJavaApprovalGateInputPresent()).isTrue();
        assertThat(handoff.sourceJavaApprovalGateInputComplete()).isTrue();
        assertThat(handoff.javaInputRemainsCanonical()).isTrue();
        assertThat(handoff.javaInputChangedByThisVersion()).isFalse();
        assertThat(handoff.runtimeGateApprovalPresent()).isFalse();
        assertThat(handoff.nodeApprovedRuntimeWindowPresent()).isFalse();
        assertThat(handoff.correlatedOperatorApprovalRecordPresent()).isFalse();
        assertThat(handoff.completeCrossProjectRuntimeExecutionPacketPresent()).isFalse();
        assertThat(handoff.crossProjectRuntimeExecutionPacketExecutable()).isFalse();
        assertThat(handoff.readyForRuntimeExecutionPacket()).isFalse();
        assertThat(handoff.readyForRuntimeLiveReadGate()).isFalse();
        assertThat(handoff.executionAttempted()).isFalse();
        assertThat(handoff.startsJavaService()).isFalse();
        assertThat(handoff.startsMiniKvService()).isFalse();
        assertThat(handoff.activeShardPrototypeEnabled()).isFalse();
        assertThat(handoff.sourceApprovalGateInputVersion()).isEqualTo("Java v164");
        assertThat(handoff.lastContractedByNodeVersion()).isEqualTo("Node v400");
        assertThat(handoff.nextNodeConsumerHint()).isEqualTo("Node v401");
        assertThat(handoff.handoffScope())
                .isEqualTo("java-side-runtime-execution-approval-input-contract-handoff");
        assertThat(handoff.handoffId())
                .isEqualTo("java-runtime-execution-approval-input-contract-handoff-v165");
        assertThat(handoff.canonicalJavaApprovalInputPath())
                .isEqualTo("e/164/evidence/java-shard-readiness-runtime-execution-approval-gate-input-v164.json");
        assertThat(handoff.canonicalJavaApprovalInputEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/runtime-execution-approval-gate-input");
        assertThat(handoff.canonicalJavaApprovalInputFixture())
                .isEqualTo("/contracts/java-shard-readiness-runtime-execution-approval-gate-input-v164.fixture.json");
        assertThat(handoff.javaOwnedArtifacts())
                .containsExactly(
                        "canonical-java-approval-gate-input:Java v164",
                        "canonical-java-approval-gate-input-path:e/164/evidence/java-shard-readiness-runtime-execution-approval-gate-input-v164.json",
                        "canonical-java-approval-gate-input-endpoint:/api/v1/ops/shard-readiness/runtime-execution-approval-gate-input",
                        "canonical-java-approval-gate-input-fixture:/contracts/java-shard-readiness-runtime-execution-approval-gate-input-v164.fixture.json",
                        "java-loopback-port:8080",
                        "java-service-owner:java-platform-operator-confirmed",
                        "java-startup-command-owner:java-platform-operator"
                );
        assertThat(handoff.ownerByOwnerHandoff())
                .containsExactly(
                        "java:canonical-approval-gate-input-present:Java v164",
                        "mini-kv:final-approval-gate-input-required-not-owned-by-java",
                        "node:node-approved-runtime-window-required-not-owned-by-java",
                        "operator:correlated-operator-approval-record-required-not-owned-by-java",
                        "cross-project:complete-runtime-execution-packet-required-not-owned-by-java",
                        "cleanup:cleanup-proof-accepted-only-after-approved-runtime-start"
                );
        assertThat(handoff.nonJavaMissingInputs())
                .containsExactly(
                        "final-mini-kv-approval-gate-input",
                        "node-approved-runtime-window",
                        "correlated-operator-approval-record",
                        "complete-cross-project-runtime-execution-packet"
                );
        assertThat(handoff.finalPacketBindingRequirements())
                .contains(
                        "bind-java-v164-approval-gate-input",
                        "bind-final-mini-kv-approval-gate-input",
                        "bind-cleanup-proof-after-approved-runtime-start"
                );
        assertThat(handoff.failClosedRules())
                .contains(
                        "source-java-approval-gate-input-status-must-be-passed:passed",
                        "java-v164-remains-canonical-no-new-java-input-issued",
                        "contract-handoff-alone-is-not-runtime-approval"
                );
        assertThat(handoff.stopConditions())
                .contains(
                        "request-would-start-java-from-contract-handoff",
                        "request-would-treat-contract-handoff-as-complete-cross-project-packet",
                        "request-would-enable-active-shard-router-or-write-routing"
                );
        assertThat(handoff.evidencePath())
                .isEqualTo("e/165/evidence/java-shard-readiness-runtime-execution-approval-input-contract-handoff-v165.json");
        assertThat(handoff.status()).isEqualTo("passed");
    }
}
