package com.codexdemo.orderplatform.ops;

import java.time.Instant;
import java.util.List;

public record OpsEvidenceResponse(
        Instant sampledAt,
        String evidenceVersion,
        Service service,
        boolean readOnly,
        boolean executionAllowed,
        FailedEventReplay failedEventReplay,
        Outbox outbox,
        ApprovalExecution approvalExecution,
        List<String> blockers,
        List<String> warnings,
        List<String> evidenceEndpoints
) {

    public record Service(
            String name,
            String version,
            List<String> profiles,
            Instant startedAt,
            long uptimeSeconds
    ) {
    }

    public record FailedEventReplay(
            long totalFailedEvents,
            long replayBacklog,
            long pendingReplayApprovals,
            long approvedReplayApprovals,
            long rejectedReplayApprovals,
            Instant latestFailedAt,
            Instant latestApprovalAt,
            String realReplayEndpoint,
            boolean realReplayAllowedByEvidence
    ) {
    }

    public record Outbox(
            long pendingEvents,
            boolean publisherEnabled,
            boolean rabbitMqEnabled,
            String exchange,
            String queue,
            String deadLetterQueue,
            List<String> blockers
    ) {
    }

    public record ApprovalExecution(
            String requiredApprovalStatus,
            String digestVerificationMode,
            boolean approvalRequired,
            boolean dryRun,
            List<String> executionBlockers,
            List<String> nextEvidenceActions
    ) {
    }
}
