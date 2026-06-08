package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean readyForSubmissionPreflightCloseout,
        String sourcePlan,
        String sourceJavaSubmissionPreflightVersion,
        String closeoutState,
        String submittedPackageAcceptanceState,
        String signedDraftTextParseState,
        String detachedSignatureParseState,
        String approvalGrantState,
        String runtimePayloadState,
        String siblingMutationState,
        boolean readyForSubmittedPackageAcceptance,
        boolean readyForSignedDraftTextParsing,
        boolean readyForDetachedSignatureParsing,
        boolean readyForApprovalGrant,
        boolean readyForRuntimePayload,
        boolean siblingMutationAllowed,
        String endpoint,
        String profile,
        int handoffItemCount,
        int passedHandoffItemCount,
        int guardrailCount,
        int passedGuardrailCount,
        int routeEvidenceCount,
        int passedRouteEvidenceCount,
        List<HandoffItem> handoffItems,
        List<Guardrail> guardrails,
        List<RouteEvidence> routeEvidence,
        List<String> checks,
        String status
) {
    public record HandoffItem(
            String code,
            String category,
            String item,
            String evidence,
            String sourceEndpoint,
            String status
    ) {
    }

    public record Guardrail(
            String code,
            String category,
            String rule,
            String enforcement,
            String status
    ) {
    }

    public record RouteEvidence(
            String code,
            String route,
            String purpose,
            String method,
            String status
    ) {
    }
}

