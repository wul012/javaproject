package com.codexdemo.orderplatform.ops;

import java.time.Instant;
import java.util.List;

public record OpsEvidenceResponse(
        Instant sampledAt,
        String evidenceVersion,
        Service service,
        HealthProbe healthProbe,
        boolean readOnly,
        boolean executionAllowed,
        ReadOnlyWindow readOnlyWindow,
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

    public record HealthProbe(
            String endpoint,
            String method,
            String expectedStatus,
            String evidenceEndpoint,
            List<String> additionalProbeEndpoints,
            boolean liveProbeRequiredForPass,
            boolean staticSampleOnly
    ) {
    }

    public record ReadOnlyWindow(
            String windowVersion,
            boolean operatorStartRequired,
            boolean nodeAutoStartAllowed,
            boolean upstreamProbesRequired,
            boolean upstreamActionsAllowed,
            boolean readyForReadOnlyLiveProbe,
            boolean readyForProductionOperations,
            List<String> allowedProbeEndpoints,
            List<String> forbiddenOperations,
            List<String> requiredNodeEnvironment,
            String replayPostBoundary
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
