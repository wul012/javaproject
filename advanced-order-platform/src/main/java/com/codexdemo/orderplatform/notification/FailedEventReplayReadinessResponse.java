package com.codexdemo.orderplatform.notification;

import java.time.Instant;
import java.util.List;

public record FailedEventReplayReadinessResponse(
        Instant sampledAt,
        Long failedEventId,
        boolean exists,
        String eventType,
        String aggregateType,
        String aggregateId,
        Instant failedAt,
        FailedEventManagementStatus managementStatus,
        FailedEventReplayApprovalStatus replayApprovalStatus,
        Long replayBacklogPosition,
        boolean eligibleForReplay,
        boolean requiresApproval,
        List<String> blockedBy,
        List<String> warnings,
        List<String> nextAllowedActions,
        LatestReplayAttempt latestReplayAttempt,
        LatestApproval latestApproval
) {

    public static FailedEventReplayReadinessResponse notFound(Long failedEventId, Instant sampledAt) {
        return new FailedEventReplayReadinessResponse(
                sampledAt,
                failedEventId,
                false,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                false,
                false,
                List.of("FAILED_EVENT_NOT_FOUND"),
                List.of(),
                List.of(),
                null,
                null
        );
    }

    public record LatestReplayAttempt(
            Long id,
            FailedEventReplayAttemptStatus status,
            String operatorId,
            String operatorRole,
            String errorMessage,
            Instant attemptedAt
    ) {

        static LatestReplayAttempt from(FailedEventReplayAttempt attempt) {
            return new LatestReplayAttempt(
                    attempt.getId(),
                    attempt.getStatus(),
                    attempt.getOperatorId(),
                    attempt.getOperatorRole(),
                    attempt.getErrorMessage(),
                    attempt.getAttemptedAt()
            );
        }
    }

    public record LatestApproval(
            FailedEventReplayApprovalHistoryAction action,
            FailedEventReplayApprovalStatus status,
            String operatorId,
            String operatorRole,
            String note,
            Instant changedAt
    ) {

        static LatestApproval from(FailedEventReplayApprovalHistory history) {
            FailedEventReplayApprovalStatus status = switch (history.getAction()) {
                case REQUESTED -> FailedEventReplayApprovalStatus.PENDING;
                case APPROVED -> FailedEventReplayApprovalStatus.APPROVED;
                case REJECTED -> FailedEventReplayApprovalStatus.REJECTED;
            };
            return new LatestApproval(
                    history.getAction(),
                    status,
                    history.getOperatorId(),
                    history.getOperatorRole(),
                    history.getNote(),
                    history.getChangedAt()
            );
        }

        static LatestApproval fromMessage(FailedEventMessage failedMessage) {
            if (failedMessage.getReplayApprovalReviewedAt() != null) {
                FailedEventReplayApprovalHistoryAction action = switch (failedMessage.getReplayApprovalStatus()) {
                    case APPROVED -> FailedEventReplayApprovalHistoryAction.APPROVED;
                    case REJECTED -> FailedEventReplayApprovalHistoryAction.REJECTED;
                    case NOT_REQUESTED, PENDING -> FailedEventReplayApprovalHistoryAction.REQUESTED;
                };
                return new LatestApproval(
                        action,
                        failedMessage.getReplayApprovalStatus(),
                        failedMessage.getReplayApprovalReviewedBy(),
                        null,
                        failedMessage.getReplayApprovalReviewNote(),
                        failedMessage.getReplayApprovalReviewedAt()
                );
            }
            if (failedMessage.getReplayApprovalRequestedAt() != null) {
                return new LatestApproval(
                        FailedEventReplayApprovalHistoryAction.REQUESTED,
                        FailedEventReplayApprovalStatus.PENDING,
                        failedMessage.getReplayApprovalRequestedBy(),
                        null,
                        failedMessage.getReplayApprovalReason(),
                        failedMessage.getReplayApprovalRequestedAt()
                );
            }
            return null;
        }
    }
}
