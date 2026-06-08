package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessCandidateDocumentHandoffResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean readyForCandidateDocumentHandoff,
        String sourcePlan,
        String sourceNodeCandidateIntakeVersion,
        String sourceJavaCandidateIntakeVersion,
        String sourceRequestPackageVersion,
        String sourceRequestPackageEndpoint,
        String handoffState,
        String endpoint,
        String profile,
        int sourceLineageCount,
        int moduleCount,
        int artifactHandleCount,
        int policyLockCount,
        int archiveEntryCount,
        int consumerRuleCount,
        int gateCount,
        int realDocumentCount,
        int syntheticDocumentCount,
        int stagedDocumentCount,
        int importedDocumentCount,
        int evaluatedDocumentCount,
        int acceptedDocumentCount,
        int rejectedDocumentCount,
        int payloadCount,
        boolean importAllowed,
        boolean evaluationAllowed,
        boolean approvalGrantAllowed,
        boolean signedApprovalCaptureAllowed,
        boolean runtimePayloadAllowed,
        boolean writeAllowed,
        boolean siblingMutationAllowed,
        List<SourceLineage> sourceLineage,
        List<ModuleEntry> modules,
        List<ArtifactHandle> artifactHandles,
        List<PolicyLock> policyLocks,
        List<ArchiveEntry> archiveEntries,
        List<ConsumerRule> consumerRules,
        List<String> gates,
        List<String> checks,
        String status
) {
    public record SourceLineage(
            String code,
            String version,
            String source,
            String endpoint,
            String role,
            String status
    ) {
    }

    public record ModuleEntry(
            int order,
            String code,
            String responsibility,
            String owner,
            String status
    ) {
    }

    public record ArtifactHandle(
            String requestCode,
            String sourceIntakeSlot,
            String requestedFields,
            String evidenceRef,
            String digestRef,
            String archiveRef,
            String state,
            String status
    ) {
    }

    public record PolicyLock(
            String acceptanceCode,
            String category,
            String rejectionCode,
            String freeze,
            String enforcement,
            String status
    ) {
    }

    public record ArchiveEntry(
            String code,
            String path,
            String retention,
            String purpose,
            String status
    ) {
    }

    public record ConsumerRule(
            String code,
            String rule,
            String enforcement,
            String status
    ) {
    }
}
