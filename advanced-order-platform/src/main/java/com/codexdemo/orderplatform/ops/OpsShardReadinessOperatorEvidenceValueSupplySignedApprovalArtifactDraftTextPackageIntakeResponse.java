package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean readyForDraftTextPackageIntake,
        String sourcePlan,
        String sourceNodeInstructionPreflightVersion,
        String sourceJavaInstructionPreflightVersion,
        String draftTextPackageIntakeState,
        String draftTextArtifactState,
        String signedDraftState,
        String signatureEnvelopeState,
        String approvalGrantState,
        String valueImportState,
        String runtimeState,
        String siblingMutationState,
        boolean readyForDraftTextPackageReview,
        boolean readyForSignedDraftText,
        boolean readyForDetachedSignature,
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
        List<IntakeField> fields,
        List<IntakeGuard> guards,
        List<IntakeGate> gates,
        List<String> checks,
        String status
) {
    public record IntakeField(
            String code,
            String versionRange,
            String expectedField,
            String intakePurpose,
            String materializationBlocker,
            String guardCode,
            String sourceEndpoint,
            String status
    ) {
    }

    public record IntakeGuard(
            String code,
            String category,
            String guard,
            String rejectionCode,
            String enforcement,
            String status
    ) {
    }

    public record IntakeGate(
            String code,
            String category,
            String gate,
            String enforcement
    ) {
    }
}
