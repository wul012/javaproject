package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean readyForReviewPackagePreflight,
        String sourcePlan,
        String sourceNodeReadinessLaneVersion,
        String sourceJavaReadinessLaneVersion,
        String reviewPackageState,
        String reviewArtifactState,
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
        int slotCount,
        int passedSlotCount,
        int guardCount,
        int passedGuardCount,
        int gateCount,
        List<PackageSlot> slots,
        List<PackageGuard> guards,
        List<ReviewPackageGate> gates,
        List<String> checks,
        String status
) {
    public record PackageSlot(
            String code,
            String sourceLane,
            String sourceField,
            String packagePurpose,
            String materializationBlocker,
            String guardCode,
            String sourceEndpoint,
            String status
    ) {
    }

    public record PackageGuard(
            String code,
            String category,
            String guard,
            String rejectionCode,
            String enforcement,
            String status
    ) {
    }

    public record ReviewPackageGate(
            String code,
            String category,
            String gate,
            String enforcement
    ) {
    }
}
