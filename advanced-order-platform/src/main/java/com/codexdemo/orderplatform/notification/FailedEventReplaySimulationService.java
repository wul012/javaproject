package com.codexdemo.orderplatform.notification;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FailedEventReplaySimulationService {

    private static final FailedEventReplayApprovalStatus REQUIRED_APPROVAL_STATUS =
            FailedEventReplayApprovalStatus.APPROVED;

    private final FailedEventReplayReadinessService failedEventReplayReadinessService;

    public FailedEventReplaySimulationService(FailedEventReplayReadinessService failedEventReplayReadinessService) {
        this.failedEventReplayReadinessService = failedEventReplayReadinessService;
    }

    @Transactional(readOnly = true)
    public FailedEventReplaySimulationResponse simulation(Long id) {
        FailedEventReplayReadinessResponse readiness = failedEventReplayReadinessService.readiness(id);
        boolean wouldReplay = readiness.exists() && readiness.eligibleForReplay();
        return new FailedEventReplaySimulationResponse(
                readiness.sampledAt(),
                readiness.failedEventId(),
                readiness.exists(),
                readiness.eligibleForReplay(),
                wouldReplay,
                wouldReplay,
                false,
                REQUIRED_APPROVAL_STATUS,
                idempotencyKeyHint(readiness),
                readiness.aggregateId(),
                expectedSideEffects(wouldReplay),
                readiness.blockedBy(),
                readiness.warnings(),
                readiness.nextAllowedActions()
        );
    }

    private String idempotencyKeyHint(FailedEventReplayReadinessResponse readiness) {
        if (!readiness.exists()) {
            return null;
        }
        String aggregate = readiness.aggregateId() == null || readiness.aggregateId().isBlank()
                ? "unknown-aggregate"
                : readiness.aggregateId();
        return "failed-event-replay:%s:%s".formatted(readiness.failedEventId(), aggregate);
    }

    private List<String> expectedSideEffects(boolean wouldReplay) {
        if (!wouldReplay) {
            return List.of();
        }
        return List.of(
                "PUBLISH_RABBITMQ_REPLAY_MESSAGE",
                "SAVE_REPLAY_ATTEMPT_AUDIT",
                "MARK_FAILED_EVENT_REPLAYED_ON_SUCCESS",
                "MARK_FAILED_EVENT_REPLAY_FAILED_ON_BROKER_ERROR"
        );
    }
}
