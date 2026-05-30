package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRuntimeExecutionPacketContributionResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean javaPacketContributionPresent,
        boolean javaPacketContributionComplete,
        boolean crossProjectRuntimeExecutionPacketPresent,
        boolean crossProjectRuntimeExecutionPacketExecutable,
        boolean readyForRuntimeExecutionPacket,
        boolean readyForRuntimeLiveReadGate,
        boolean executionAttempted,
        boolean startsJavaService,
        boolean startsMiniKvService,
        boolean activeShardPrototypeEnabled,
        String sourceRuntimeArtifactCandidateVersion,
        String lastClarifiedByNodeVersion,
        String nextNodeConsumerHint,
        String contributionScope,
        String operatorApprovalRecordId,
        String operatorApprovalCorrelationRequirement,
        String javaLoopbackPort,
        String miniKvLoopbackPortRequirement,
        String serviceOwnerConfirmation,
        String startupCommand,
        String startupCommandOwner,
        String cleanupOwner,
        List<String> acceptedRequirementRows,
        List<String> getOnlySmokeCommands,
        List<String> cleanupProofArtifacts,
        List<String> processCleanupRules,
        List<String> crossProjectMissingArtifacts,
        List<String> failClosedRules,
        List<String> stopConditions,
        String evidencePath,
        String status
) {
}
