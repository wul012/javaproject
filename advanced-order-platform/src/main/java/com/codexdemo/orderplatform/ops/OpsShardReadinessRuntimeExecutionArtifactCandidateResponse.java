package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRuntimeExecutionArtifactCandidateResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean javaRuntimeArtifactCandidatePresent,
        boolean javaRuntimeArtifactsDeclared,
        boolean javaRuntimeArtifactsComplete,
        boolean crossProjectRuntimeArtifactsComplete,
        boolean runtimeExecutionPacketPresent,
        boolean runtimeExecutionPacketExecutable,
        boolean readyForRuntimeExecutionPacket,
        boolean readyForRuntimeLiveReadGate,
        boolean executionAttempted,
        boolean startsJavaService,
        boolean startsMiniKvService,
        boolean activeShardPrototypeEnabled,
        String sourceDeclaredLifecycleVersion,
        String lastVerifiedByNodeVersion,
        String nextNodeConsumerHint,
        String operatorApprovalRecord,
        String operatorApprovalScope,
        String serviceOwner,
        String startupCommandOwner,
        String cleanupOwner,
        String declaredWorkingDirectory,
        String declaredStartupCommand,
        String javaLoopbackPort,
        String miniKvLoopbackPort,
        String javaBaseUrlHandle,
        List<String> getOnlySmokeCommands,
        List<String> cleanupProofs,
        List<String> processCleanupRules,
        List<String> failClosedRules,
        List<String> missingCrossProjectArtifacts,
        List<String> stopConditions,
        String evidencePath,
        String status
) {
}
