package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean readyForArchiveHandoff,
        String sourcePlan,
        String sourceNodeMaterialSubmissionPrecheckVersion,
        String sourceJavaMaterialSubmissionPrecheckVersion,
        String sourceMaterialSubmissionPrecheckEndpoint,
        String handoffState,
        String endpoint,
        String profile,
        int sourceLineageCount,
        int moduleCount,
        int archiveHandleCount,
        int policyLockCount,
        int artifactReferenceCount,
        int consumerRuleCount,
        int sourceCheckpointCount,
        int sourceValidatorCount,
        int sourceArtifactCount,
        int sourceGateCount,
        int gateCount,
        int realDocumentCount,
        int syntheticDocumentCount,
        int stagedDocumentCount,
        int importedDocumentCount,
        int evaluatedDocumentCount,
        int acceptedDocumentCount,
        int rejectedDocumentCount,
        int payloadCount,
        boolean materialSubmissionAccepted,
        boolean importAllowed,
        boolean evaluationAllowed,
        boolean approvalGrantAllowed,
        boolean signedApprovalCaptureAllowed,
        boolean runtimePayloadAllowed,
        boolean writeAllowed,
        boolean siblingMutationAllowed,
        List<SourceLineage> sourceLineage,
        List<ModuleEntry> modules,
        List<ArchiveHandle> archiveHandles,
        List<PolicyLock> policyLocks,
        List<ArtifactReference> artifactReferences,
        List<ConsumerRule> consumerRules,
        List<String> gates,
        List<String> checks,
        String status
) {
    public record SourceLineage(
            int order,
            String code,
            String source,
            String reference,
            String status
    ) {
    }

    public record ModuleEntry(
            int order,
            String code,
            String responsibility,
            String status
    ) {
    }

    public record ArchiveHandle(
            String code,
            String checkpointCode,
            String reference,
            String retention,
            String status
    ) {
    }

    public record PolicyLock(
            String code,
            String validatorCode,
            String rejectionCode,
            String lockReason,
            String enforcement,
            String status
    ) {
    }

    public record ArtifactReference(
            String code,
            String sourceReference,
            String archiveReference,
            String purpose,
            String status
    ) {
    }

    public record ConsumerRule(
            String code,
            String checkpointCode,
            String allowedAction,
            String blockedAction,
            String status
    ) {
    }
}
