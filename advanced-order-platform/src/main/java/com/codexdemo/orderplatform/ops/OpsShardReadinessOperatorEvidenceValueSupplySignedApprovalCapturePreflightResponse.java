package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean readyForCapturePreflight,
        String sourcePlan,
        String sourceTemplateVersion,
        String sourceApprovalPacketReviewVersion,
        String sourceApprovalPreflightVersion,
        String capturePreflightState,
        String signedApprovalCaptureState,
        String approvalGrantState,
        String operatorValueSubmissionState,
        String evidenceImportState,
        String runtimeState,
        String siblingMutationState,
        boolean readyForSignedApprovalCapture,
        boolean readyForApprovalGrant,
        boolean readyForOperatorValueSubmission,
        boolean readyForEvidenceImport,
        boolean readyForRuntimePayload,
        boolean readyForLiveExecution,
        boolean readyForProductionExecution,
        boolean siblingMutationAllowed,
        String endpoint,
        String profile,
        int inputCount,
        int passedInputCount,
        int attestationCount,
        int passedAttestationCount,
        int policyCount,
        List<CaptureInput> inputs,
        List<CaptureAttestation> attestations,
        List<CapturePolicy> policies,
        List<String> checks,
        String status
) {
    public record CaptureInput(
            String code,
            String sourceTemplateField,
            String captureStage,
            String inputRequirement,
            String blockedReason,
            String evidenceFileId,
            String evidenceSnippetId,
            String sourceEndpoint,
            String status
    ) {
    }

    public record CaptureAttestation(
            String code,
            String category,
            String attestation,
            String enforcement,
            String status
    ) {
    }

    public record CapturePolicy(
            String code,
            String category,
            String policy,
            String enforcement
    ) {
    }
}
