package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean readyForDraftTextPackageSubmissionPreflight,
        String sourcePlan,
        String sourceNodeReviewPreflightVersion,
        String sourceJavaReviewPreflightVersion,
        String submissionPreflightState,
        String submittedPackageState,
        String draftTextParseState,
        String detachedSignatureParseState,
        String approvalGrantState,
        String valueImportState,
        String runtimeState,
        String siblingMutationState,
        boolean readyForSubmittedPackageAcceptance,
        boolean readyForSignedDraftTextParsing,
        boolean readyForDetachedSignatureParsing,
        boolean readyForApprovalGrant,
        boolean readyForOperatorValueSubmission,
        boolean readyForEvidenceImport,
        boolean readyForRuntimePayload,
        boolean siblingMutationAllowed,
        String endpoint,
        String profile,
        int slotCount,
        int passedSlotCount,
        int comparisonControlCount,
        int passedComparisonControlCount,
        int gateCount,
        List<SubmissionSlot> slots,
        List<ComparisonControl> comparisonControls,
        List<SubmissionGate> gates,
        List<String> checks,
        String status
) {
    public record SubmissionSlot(
            String code,
            String versionRange,
            String submissionSlot,
            String comparisonQuestion,
            String materialComparisonControl,
            String sourceEndpoint,
            String status
    ) {
    }

    public record ComparisonControl(
            String code,
            String category,
            String control,
            String rejectionCode,
            String enforcement,
            String status
    ) {
    }

    public record SubmissionGate(
            String code,
            String category,
            String gate,
            String enforcement
    ) {
    }
}
