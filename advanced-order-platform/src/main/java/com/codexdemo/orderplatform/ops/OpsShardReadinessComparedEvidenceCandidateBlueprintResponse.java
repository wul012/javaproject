package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessComparedEvidenceCandidateBlueprintResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean readyForComparedEvidenceCandidateBlueprint,
        String sourcePlan,
        String sourceNodePreflightVersion,
        String sourceJavaPreflightVersion,
        String candidateBlueprintState,
        String realCandidateState,
        String evidenceImportState,
        String approvalGrantState,
        String signedApprovalCaptureState,
        String runtimePayloadState,
        String siblingMutationState,
        boolean readyForRealCandidateIntake,
        boolean readyForEvidenceImport,
        boolean readyForApprovalGrant,
        boolean readyForSignedApprovalCapture,
        boolean readyForRuntimePayload,
        boolean siblingMutationAllowed,
        String endpoint,
        String profile,
        int candidateSectionCount,
        int passedCandidateSectionCount,
        int blockerCount,
        int passedBlockerCount,
        List<CandidateSection> candidateSections,
        List<CandidateBlocker> blockers,
        List<String> checks,
        String status
) {
    public record CandidateSection(
            String code,
            String sourceNodeVersion,
            String section,
            String requiredFields,
            String owner,
            String sourceEndpoint,
            String blockerCode,
            String status
    ) {
    }

    public record CandidateBlocker(
            String code,
            String category,
            String blocker,
            String rejectionCode,
            String enforcement,
            String status
    ) {
    }
}
