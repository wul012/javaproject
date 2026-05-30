package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean javaApprovalInputContractHandoffPresent,
        boolean javaApprovalInputContractHandoffComplete,
        boolean sourceJavaApprovalGateInputPresent,
        boolean sourceJavaApprovalGateInputComplete,
        boolean javaInputRemainsCanonical,
        boolean javaInputChangedByThisVersion,
        boolean runtimeGateApprovalPresent,
        boolean nodeApprovedRuntimeWindowPresent,
        boolean correlatedOperatorApprovalRecordPresent,
        boolean completeCrossProjectRuntimeExecutionPacketPresent,
        boolean crossProjectRuntimeExecutionPacketExecutable,
        boolean readyForRuntimeExecutionPacket,
        boolean readyForRuntimeLiveReadGate,
        boolean executionAttempted,
        boolean startsJavaService,
        boolean startsMiniKvService,
        boolean activeShardPrototypeEnabled,
        String sourceApprovalGateInputVersion,
        String lastContractedByNodeVersion,
        String nextNodeConsumerHint,
        String handoffScope,
        String handoffId,
        String canonicalJavaApprovalInputPath,
        String canonicalJavaApprovalInputEndpoint,
        String canonicalJavaApprovalInputFixture,
        List<String> javaOwnedArtifacts,
        List<String> ownerByOwnerHandoff,
        List<String> nonJavaMissingInputs,
        List<String> finalPacketBindingRequirements,
        List<String> failClosedRules,
        List<String> stopConditions,
        String evidencePath,
        String status
) {
}
