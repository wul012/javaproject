package com.codexdemo.orderplatform.notification;

import java.time.Instant;
import java.util.List;

public record FailedEventReplayApprovalStatusResponse(
        Instant sampledAt,
        Long failedEventId,
        boolean exists,
        String evidenceVersion,
        String approvalDigest,
        String replayEligibilityDigest,
        FailedEventMessageStatus failedEventStatus,
        FailedEventManagementStatus managementStatus,
        FailedEventReplayApprovalStatus approvalStatus,
        FailedEventReplayApprovalStatus requiredApprovalStatus,
        boolean approvalRequested,
        boolean approvalPending,
        boolean approvedForReplay,
        boolean rejected,
        String requestReason,
        String requestedBy,
        Instant requestedAt,
        String reviewedBy,
        Instant reviewedAt,
        String reviewNote,
        long historyCount,
        LatestApproval latestApproval,
        List<String> approvalBlockedBy,
        List<String> nextAllowedActions
) {

    public static FailedEventReplayApprovalStatusResponse notFound(Long failedEventId, Instant sampledAt) {
        return new FailedEventReplayApprovalStatusResponse(
                sampledAt,
                failedEventId,
                false,
                FailedEventReplayApprovalEvidenceDigests.EVIDENCE_VERSION,
                FailedEventReplayApprovalEvidenceDigests.approvalDigest(
                        failedEventId,
                        false,
                        null,
                        FailedEventReplayApprovalStatus.APPROVED,
                        false,
                        false,
                        false,
                        false,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        0,
                        null
                ),
                FailedEventReplayApprovalEvidenceDigests.replayEligibilityDigest(
                        failedEventId,
                        false,
                        null,
                        null,
                        null,
                        FailedEventReplayApprovalStatus.APPROVED,
                        false,
                        List.of("FAILED_EVENT_NOT_FOUND"),
                        List.of()
                ),
                null,
                null,
                null,
                FailedEventReplayApprovalStatus.APPROVED,
                false,
                false,
                false,
                false,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                null,
                List.of("FAILED_EVENT_NOT_FOUND"),
                List.of()
        );
    }

    public record LatestApproval(
            FailedEventReplayApprovalHistoryAction action,
            FailedEventReplayApprovalStatus status,
            String operatorId,
            String operatorRole,
            String note,
            Instant changedAt
    ) {

        static LatestApproval fromHistory(FailedEventReplayApprovalHistory history) {
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
