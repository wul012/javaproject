package com.codexdemo.orderplatform.notification;

import java.time.Instant;
import java.util.List;

public record FailedEventReplayExecutionContractResponse(
        Instant sampledAt,
        Long failedEventId,
        boolean exists,
        String contractVersion,
        String contractDigest,
        String approvalEvidenceVersion,
        String approvalDigest,
        String replayEligibilityDigest,
        FailedEventMessageStatus failedEventStatus,
        FailedEventManagementStatus managementStatus,
        FailedEventReplayApprovalStatus approvalStatus,
        FailedEventReplayApprovalStatus requiredApprovalStatus,
        boolean replayPreconditionsSatisfied,
        boolean realReplayEndpointEnforcesApprovalDigest,
        boolean realReplayEndpointEnforcesReplayEligibilityDigest,
        String digestVerificationMode,
        String realExecutionMethod,
        String realExecutionPath,
        String requiredOperatorAction,
        String idempotencyKeyHint,
        String expectedAggregateId,
        List<ExecutionCheck> executionChecks,
        List<RequestRequirement> requestRequirements,
        List<String> blockedBy,
        List<String> warnings,
        List<String> expectedSideEffects,
        List<String> nextAllowedActions
) {

    public record ExecutionCheck(
            String checkId,
            String source,
            String category,
            boolean required,
            String status,
            String requiredValue,
            String currentValue,
            String evidenceDigest,
            List<String> blockedBy
    ) {
    }

    public record RequestRequirement(
            String field,
            boolean requiredForPost,
            String rule,
            String fallback
    ) {
    }
}
