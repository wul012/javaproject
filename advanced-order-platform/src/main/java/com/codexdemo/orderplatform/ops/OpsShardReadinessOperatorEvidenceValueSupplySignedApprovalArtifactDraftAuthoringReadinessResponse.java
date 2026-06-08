package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean readyForAuthoringReadiness,
        String sourcePlan,
        String sourceNodeReviewPackagePreflightVersion,
        String sourceJavaReviewPackagePreflightVersion,
        String authoringReadinessState,
        String authoringArtifactState,
        String signedDraftState,
        String signatureCaptureState,
        String approvalGrantState,
        String valueImportState,
        String runtimeState,
        String siblingMutationState,
        boolean readyForHumanDraftAuthoring,
        boolean readyForSignedDraftText,
        boolean readyForSignatureCapture,
        boolean readyForApprovalGrant,
        boolean readyForOperatorValueSubmission,
        boolean readyForEvidenceImport,
        boolean readyForRuntimePayload,
        boolean siblingMutationAllowed,
        String endpoint,
        String profile,
        int requirementCount,
        int passedRequirementCount,
        int blockerCount,
        int passedBlockerCount,
        int gateCount,
        List<AuthoringRequirement> requirements,
        List<AuthoringBlocker> blockers,
        List<AuthoringGate> gates,
        List<String> checks,
        String status
) {
    public record AuthoringRequirement(
            String code,
            String sourceReviewPackageSlot,
            String sourceField,
            String authoringPurpose,
            String authoringBlocker,
            String blockerCode,
            String sourceEndpoint,
            String status
    ) {
    }

    public record AuthoringBlocker(
            String code,
            String category,
            String blocker,
            String rejectionCode,
            String enforcement,
            String status
    ) {
    }

    public record AuthoringGate(
            String code,
            String category,
            String gate,
            String enforcement
    ) {
    }
}
