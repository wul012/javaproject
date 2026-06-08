package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean readyForComparisonAcceptancePrecheck,
        String sourcePlan,
        String sourceNodeComparisonPreflightVersion,
        String sourceJavaComparisonPreflightVersion,
        String acceptancePrecheckState,
        String comparedPackageState,
        String signedDraftTextParseState,
        String detachedSignatureParseState,
        String approvalGrantState,
        String runtimePayloadState,
        String siblingMutationState,
        boolean readyForComparedPackageAcceptance,
        boolean readyForSignedDraftTextParsing,
        boolean readyForDetachedSignatureParsing,
        boolean readyForApprovalGrant,
        boolean readyForRuntimePayload,
        boolean siblingMutationAllowed,
        String endpoint,
        String profile,
        int checkpointCount,
        int passedCheckpointCount,
        int guardCount,
        int passedGuardCount,
        List<AcceptanceCheckpoint> checkpoints,
        List<MissingEvidenceGuard> guards,
        List<String> checks,
        String status
) {
    public record AcceptanceCheckpoint(
            String code,
            String sourceVersion,
            String checkpoint,
            String acceptanceQuestion,
            String missingEvidenceGuard,
            String sourceEndpoint,
            String status
    ) {
    }

    public record MissingEvidenceGuard(
            String code,
            String category,
            String guard,
            String rejectionCode,
            String enforcement,
            String status
    ) {
    }
}

