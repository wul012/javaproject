package com.codexdemo.orderplatform.notification;

import java.time.Instant;
import java.util.List;

public record FailedEventReplaySimulationResponse(
        Instant sampledAt,
        Long failedEventId,
        boolean exists,
        boolean eligibleForReplay,
        boolean wouldReplay,
        boolean wouldPublishOutbox,
        boolean wouldChangeManagementStatus,
        FailedEventReplayApprovalStatus requiredApprovalStatus,
        String idempotencyKeyHint,
        String expectedAggregateId,
        List<String> expectedSideEffects,
        List<String> blockedBy,
        List<String> warnings,
        List<String> nextAllowedActions
) {
}
