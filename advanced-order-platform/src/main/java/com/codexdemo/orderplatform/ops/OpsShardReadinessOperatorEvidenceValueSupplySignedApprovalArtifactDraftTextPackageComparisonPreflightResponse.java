package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean readyForDraftTextPackageComparisonPreflight,
        String sourcePlan,
        String sourceNodeSubmissionPreflightVersion,
        String sourceJavaSubmissionPreflightVersion,
        String sourceJavaSubmissionCloseoutVersion,
        String comparisonPreflightState,
        String submittedPackageState,
        String comparisonState,
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
        boolean readyForEvidenceImport,
        boolean readyForRuntimePayload,
        boolean siblingMutationAllowed,
        String endpoint,
        String profile,
        int comparisonLaneCount,
        int passedComparisonLaneCount,
        int acceptanceControlCount,
        int passedAcceptanceControlCount,
        int gateCount,
        List<ComparisonLane> comparisonLanes,
        List<AcceptanceControl> acceptanceControls,
        List<ComparisonGate> gates,
        List<String> checks,
        String status
) {
    public record ComparisonLane(
            String code,
            String versionRange,
            String comparisonLane,
            String comparisonQuestion,
            String acceptanceControl,
            String sourceEndpoint,
            String status
    ) {
    }

    public record AcceptanceControl(
            String code,
            String category,
            String control,
            String rejectionCode,
            String enforcement,
            String status
    ) {
    }

    public record ComparisonGate(
            String code,
            String category,
            String gate,
            String enforcement
    ) {
    }
}

