package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessComparedEvidenceEvaluationPreflightResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean readyForComparedEvidenceEvaluationPreflight,
        String sourcePlan,
        String sourceNodeEvidenceIntakeVersion,
        String sourceJavaReviewHandoffVersion,
        String evaluationContractState,
        String candidateEvidenceState,
        String evidenceAcceptanceState,
        String approvalCaptureState,
        String runtimePayloadState,
        String siblingMutationState,
        boolean readyForCandidateEvaluation,
        boolean readyForEvidenceAcceptance,
        boolean readyForApprovalCapture,
        boolean readyForRuntimePayload,
        boolean siblingMutationAllowed,
        String endpoint,
        String profile,
        int evaluationRuleCount,
        int passedEvaluationRuleCount,
        int guardCount,
        int passedGuardCount,
        List<EvaluationRule> evaluationRules,
        List<EvaluationGuard> guards,
        List<String> checks,
        String status
) {
    public record EvaluationRule(
            String code,
            String sourceNodeVersion,
            String evaluationArea,
            String rule,
            String missingCandidateGuard,
            String sourceEndpoint,
            String status
    ) {
    }

    public record EvaluationGuard(
            String code,
            String category,
            String guard,
            String rejectionCode,
            String enforcement,
            String status
    ) {
    }
}
