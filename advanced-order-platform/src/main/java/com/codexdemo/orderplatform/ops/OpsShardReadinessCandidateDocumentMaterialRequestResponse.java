package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessCandidateDocumentMaterialRequestResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean readyForMaterialRequest,
        String sourcePlan,
        String sourceNodeIntakePacketVersion,
        String sourceJavaIntakePacketVersion,
        String sourceIntakePacketEndpoint,
        String materialRequestState,
        String endpoint,
        String profile,
        int moduleCount,
        int requestItemCount,
        int passedRequestItemCount,
        int acceptanceCheckCount,
        int passedAcceptanceCheckCount,
        int sourceIntakeSlotCount,
        int sourceIntakeGuardCount,
        int requestedMaterialFieldCount,
        int artifactCount,
        int gateCount,
        int realDocumentCount,
        int syntheticDocumentCount,
        int stagedDocumentCount,
        int importedDocumentCount,
        int evaluatedDocumentCount,
        int acceptedDocumentCount,
        int rejectedDocumentCount,
        int payloadCount,
        boolean materialAccepted,
        boolean importAllowed,
        boolean evaluationAllowed,
        boolean approvalGrantAllowed,
        boolean signedApprovalCaptureAllowed,
        boolean runtimePayloadAllowed,
        boolean writeAllowed,
        boolean siblingMutationAllowed,
        List<ModuleEntry> modules,
        List<RequestItem> requestItems,
        List<AcceptanceCheck> acceptanceChecks,
        List<Artifact> artifacts,
        List<String> gates,
        List<String> checks,
        String status
) {
    public record ModuleEntry(
            int order,
            String code,
            String responsibility,
            String status
    ) {
    }

    public record RequestItem(
            String code,
            String sourceCode,
            String category,
            String requestedFields,
            String instruction,
            String owner,
            String status
    ) {
    }

    public record AcceptanceCheck(
            String code,
            String requestCode,
            String rejectionCode,
            String check,
            String enforcement,
            String status
    ) {
    }

    public record Artifact(
            String code,
            String reference,
            String purpose,
            String status
    ) {
    }
}
