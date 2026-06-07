package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean readyForReadinessLaneCloseout,
        String sourcePlan,
        String sourceNodeDraftPreflightVersion,
        String sourceJavaDraftPreflightVersion,
        String readinessLaneState,
        String manualPackageState,
        String manualDraftState,
        String draftMaterializationState,
        String signatureCaptureState,
        String approvalGrantState,
        String valueImportState,
        String runtimeState,
        String siblingMutationState,
        boolean readyForManualDraft,
        boolean readyForSignatureCapture,
        boolean readyForApprovalGrant,
        boolean readyForOperatorValueSubmission,
        boolean readyForEvidenceImport,
        boolean readyForRuntimePayload,
        boolean siblingMutationAllowed,
        String endpoint,
        String profile,
        int laneCount,
        int passedLaneCount,
        int blockerCount,
        int passedBlockerCount,
        int gateCount,
        List<ReadinessLane> lanes,
        List<ControlBlocker> blockers,
        List<ReadinessLaneGate> gates,
        List<String> checks,
        String status
) {
    public record ReadinessLane(
            String code,
            String sourceField,
            String reviewPurpose,
            String manualReviewBlocker,
            String blockerCode,
            String sourceEndpoint,
            String status
    ) {
    }

    public record ControlBlocker(
            String code,
            String category,
            String blocker,
            String rejectionCode,
            String enforcement,
            String status
    ) {
    }

    public record ReadinessLaneGate(
            String code,
            String category,
            String gate,
            String enforcement
    ) {
    }
}
