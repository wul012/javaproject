package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean readyForDraftPreflight,
        String sourcePlan,
        String sourceArtifactPreflightVersion,
        String sourceJavaDraftReadinessVersion,
        String sourceCapturePreflightVersion,
        String draftPreflightState,
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
        int fieldCount,
        int passedFieldCount,
        int guardCount,
        int passedGuardCount,
        int gateCount,
        List<DraftField> fields,
        List<DraftGuard> guards,
        List<DraftPreflightGate> gates,
        List<String> checks,
        String status
) {
    public record DraftField(
            String code,
            String sourceReadinessItem,
            String draftStage,
            String fieldRequirement,
            String materializationBlocker,
            String guardCode,
            String sourceEndpoint,
            String status
    ) {
    }

    public record DraftGuard(
            String code,
            String category,
            String guardRequirement,
            String rejectionCode,
            String enforcement,
            String status
    ) {
    }

    public record DraftPreflightGate(
            String code,
            String category,
            String gate,
            String enforcement
    ) {
    }
}
