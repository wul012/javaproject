package com.codexdemo.orderplatform.notification;

import com.codexdemo.orderplatform.outbox.OutboxRabbitMqProperties;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FailedEventReplayReadinessService {

    private final FailedEventMessageRepository failedEventMessageRepository;

    private final FailedEventReplayAttemptRepository failedEventReplayAttemptRepository;

    private final FailedEventReplayApprovalHistoryRepository failedEventReplayApprovalHistoryRepository;

    private final OutboxRabbitMqProperties outboxRabbitMqProperties;

    public FailedEventReplayReadinessService(
            FailedEventMessageRepository failedEventMessageRepository,
            FailedEventReplayAttemptRepository failedEventReplayAttemptRepository,
            FailedEventReplayApprovalHistoryRepository failedEventReplayApprovalHistoryRepository,
            OutboxRabbitMqProperties outboxRabbitMqProperties
    ) {
        this.failedEventMessageRepository = failedEventMessageRepository;
        this.failedEventReplayAttemptRepository = failedEventReplayAttemptRepository;
        this.failedEventReplayApprovalHistoryRepository = failedEventReplayApprovalHistoryRepository;
        this.outboxRabbitMqProperties = outboxRabbitMqProperties;
    }

    @Transactional(readOnly = true)
    public FailedEventReplayReadinessResponse readiness(Long id) {
        if (id == null || id < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "failed event id must be positive");
        }
        Instant sampledAt = Instant.now();
        return failedEventMessageRepository.findById(id)
                .map(failedMessage -> readiness(failedMessage, sampledAt))
                .orElseGet(() -> FailedEventReplayReadinessResponse.notFound(id, sampledAt));
    }

    private FailedEventReplayReadinessResponse readiness(FailedEventMessage failedMessage, Instant sampledAt) {
        List<String> blockedBy = blockedBy(failedMessage);
        List<String> warnings = warnings(failedMessage);
        boolean eligibleForReplay = blockedBy.isEmpty();
        boolean requiresApproval = failedMessage.getReplayApprovalStatus() != FailedEventReplayApprovalStatus.APPROVED;
        return new FailedEventReplayReadinessResponse(
                sampledAt,
                failedMessage.getId(),
                true,
                failedMessage.getEventType(),
                failedMessage.getAggregateType(),
                failedMessage.getAggregateId(),
                failedMessage.getFailedAt(),
                failedMessage.getManagementStatus(),
                failedMessage.getReplayApprovalStatus(),
                replayBacklogPosition(failedMessage),
                eligibleForReplay,
                requiresApproval,
                blockedBy,
                warnings,
                nextAllowedActions(failedMessage, eligibleForReplay),
                latestReplayAttempt(failedMessage),
                latestApproval(failedMessage)
        );
    }

    private List<String> blockedBy(FailedEventMessage failedMessage) {
        List<String> blockedBy = new ArrayList<>();
        if (failedMessage.getStatus() == FailedEventMessageStatus.REPLAYED) {
            blockedBy.add("ALREADY_REPLAYED");
        }
        switch (failedMessage.getReplayApprovalStatus()) {
            case NOT_REQUESTED -> blockedBy.add("REPLAY_APPROVAL_NOT_REQUESTED");
            case PENDING -> blockedBy.add("REPLAY_APPROVAL_PENDING");
            case REJECTED -> blockedBy.add("REPLAY_APPROVAL_REJECTED");
            case APPROVED -> {
            }
        }
        if (!outboxRabbitMqProperties.isEnabled()) {
            blockedBy.add("RABBITMQ_OUTBOX_DISABLED");
        }
        if (isBlank(failedMessage.getEventType())) {
            blockedBy.add("EVENT_TYPE_REQUIRED");
        }
        if (isBlank(failedMessage.getAggregateType())) {
            blockedBy.add("AGGREGATE_TYPE_REQUIRED");
        }
        if (isBlank(failedMessage.getAggregateId())) {
            blockedBy.add("AGGREGATE_ID_REQUIRED");
        }
        if (isBlank(failedMessage.getPayload())) {
            blockedBy.add("PAYLOAD_REQUIRED");
        }
        return List.copyOf(blockedBy);
    }

    private List<String> warnings(FailedEventMessage failedMessage) {
        List<String> warnings = new ArrayList<>();
        if (failedMessage.getReplayCount() > 0) {
            warnings.add("HAS_PREVIOUS_REPLAY_ATTEMPTS");
        }
        if (failedMessage.getStatus() == FailedEventMessageStatus.REPLAY_FAILED) {
            warnings.add("LATEST_REPLAY_ATTEMPT_FAILED");
        }
        if (failedMessage.getManagementStatus() == FailedEventManagementStatus.IGNORED) {
            warnings.add("MANAGEMENT_STATUS_IGNORED");
        }
        if (failedMessage.getManagementStatus() == FailedEventManagementStatus.RESOLVED) {
            warnings.add("MANAGEMENT_STATUS_RESOLVED");
        }
        if (isBlank(failedMessage.getEventId())) {
            warnings.add("EVENT_ID_WILL_BE_GENERATED_IF_NOT_SUPPLIED");
        }
        return List.copyOf(warnings);
    }

    private List<String> nextAllowedActions(FailedEventMessage failedMessage, boolean eligibleForReplay) {
        if (eligibleForReplay) {
            return List.of("REPLAY_FAILED_EVENT");
        }
        if (failedMessage.getStatus() == FailedEventMessageStatus.REPLAYED) {
            return List.of();
        }
        return switch (failedMessage.getReplayApprovalStatus()) {
            case NOT_REQUESTED, REJECTED -> List.of("REQUEST_REPLAY_APPROVAL");
            case PENDING -> List.of("REVIEW_REPLAY_APPROVAL");
            case APPROVED -> replayRepairActions(failedMessage);
        };
    }

    private List<String> replayRepairActions(FailedEventMessage failedMessage) {
        List<String> actions = new ArrayList<>();
        if (!outboxRabbitMqProperties.isEnabled()) {
            actions.add("ENABLE_RABBITMQ_OUTBOX");
        }
        if (isBlank(failedMessage.getEventType())
                || isBlank(failedMessage.getAggregateType())
                || isBlank(failedMessage.getAggregateId())
                || isBlank(failedMessage.getPayload())) {
            actions.add("SUPPLY_REPLAY_REQUEST_FIELDS");
        }
        return List.copyOf(actions);
    }

    private Long replayBacklogPosition(FailedEventMessage failedMessage) {
        if (failedMessage.getStatus() == FailedEventMessageStatus.REPLAYED) {
            return null;
        }
        return failedEventMessageRepository.countReplayBacklogBefore(
                FailedEventMessageStatus.REPLAYED,
                failedMessage.getFailedAt(),
                failedMessage.getId()
        ) + 1;
    }

    private FailedEventReplayReadinessResponse.LatestReplayAttempt latestReplayAttempt(
            FailedEventMessage failedMessage
    ) {
        return failedEventReplayAttemptRepository
                .findTopByFailedEventMessageIdOrderByAttemptedAtDescIdDesc(failedMessage.getId())
                .map(FailedEventReplayReadinessResponse.LatestReplayAttempt::from)
                .orElse(null);
    }

    private FailedEventReplayReadinessResponse.LatestApproval latestApproval(FailedEventMessage failedMessage) {
        return failedEventReplayApprovalHistoryRepository
                .findTopByFailedEventMessageIdOrderByChangedAtDescIdDesc(failedMessage.getId())
                .map(FailedEventReplayReadinessResponse.LatestApproval::from)
                .orElseGet(() -> FailedEventReplayReadinessResponse.LatestApproval.fromMessage(failedMessage));
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
