package com.codexdemo.orderplatform.notification;

import java.time.Instant;

public record FailedEventSummaryResponse(
        Instant sampledAt,
        long totalFailedEvents,
        long pendingReplayApprovals,
        long approvedReplayApprovals,
        long rejectedReplayApprovals,
        Instant latestFailedAt,
        Instant latestApprovalAt,
        long replayBacklog
) {
}
