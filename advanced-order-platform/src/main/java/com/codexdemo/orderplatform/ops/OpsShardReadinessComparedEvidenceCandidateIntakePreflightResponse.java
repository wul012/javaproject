package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean readyForComparedEvidenceCandidateIntakePreflight,
        String sourcePlan,
        String sourceNodeCandidateBlueprintVersion,
        String sourceJavaCandidateBlueprintVersion,
        String intakeState,
        int realDocumentCount,
        int requiredFieldCount,
        int passedGateCount,
        boolean syntheticDocumentAllowed,
        boolean payloadImportAllowed,
        boolean candidateEvaluationAllowed,
        boolean approvalGrantAllowed,
        boolean signedApprovalCaptureAllowed,
        boolean runtimePayloadAllowed,
        boolean writeAllowed,
        boolean siblingMutationAllowed,
        String endpoint,
        String profile,
        int intakeSlotCount,
        int passedIntakeSlotCount,
        int guardCount,
        int passedGuardCount,
        List<IntakeSlot> intakeSlots,
        List<IntakeGuard> guards,
        List<String> gates,
        List<String> checks,
        String status
) {
    public record IntakeSlot(
            String code,
            String sourceBlueprintSection,
            String requiredFields,
            String documentRequirement,
            String missingDocumentGuard,
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
}
