package com.codexdemo.orderplatform.notification;

import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FailedEventReplayApprovalStatusService {

    private static final FailedEventReplayApprovalStatus REQUIRED_APPROVAL_STATUS =
            FailedEventReplayApprovalStatus.APPROVED;

    private final FailedEventMessageRepository failedEventMessageRepository;

    private final FailedEventReplayApprovalHistoryRepository failedEventReplayApprovalHistoryRepository;

    public FailedEventReplayApprovalStatusService(
            FailedEventMessageRepository failedEventMessageRepository,
            FailedEventReplayApprovalHistoryRepository failedEventReplayApprovalHistoryRepository
    ) {
        this.failedEventMessageRepository = failedEventMessageRepository;
        this.failedEventReplayApprovalHistoryRepository = failedEventReplayApprovalHistoryRepository;
    }

    @Transactional(readOnly = true)
    public FailedEventReplayApprovalStatusResponse approvalStatus(Long id) {
        if (id == null || id < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "failed event id must be positive");
        }
        Instant sampledAt = Instant.now();
        return failedEventMessageRepository.findById(id)
                .map(failedMessage -> approvalStatus(failedMessage, sampledAt))
                .orElseGet(() -> FailedEventReplayApprovalStatusResponse.notFound(id, sampledAt));
    }

    private FailedEventReplayApprovalStatusResponse approvalStatus(
            FailedEventMessage failedMessage,
            Instant sampledAt
    ) {
        FailedEventReplayApprovalStatus status = failedMessage.getReplayApprovalStatus();
        return new FailedEventReplayApprovalStatusResponse(
                sampledAt,
                failedMessage.getId(),
                true,
                failedMessage.getStatus(),
                failedMessage.getManagementStatus(),
                status,
                REQUIRED_APPROVAL_STATUS,
                status != FailedEventReplayApprovalStatus.NOT_REQUESTED,
                status == FailedEventReplayApprovalStatus.PENDING,
                status == FailedEventReplayApprovalStatus.APPROVED,
                status == FailedEventReplayApprovalStatus.REJECTED,
                failedMessage.getReplayApprovalReason(),
                failedMessage.getReplayApprovalRequestedBy(),
                failedMessage.getReplayApprovalRequestedAt(),
                failedMessage.getReplayApprovalReviewedBy(),
                failedMessage.getReplayApprovalReviewedAt(),
                failedMessage.getReplayApprovalReviewNote(),
                failedEventReplayApprovalHistoryRepository.countByFailedEventMessageId(failedMessage.getId()),
                latestApproval(failedMessage),
                approvalBlockedBy(status),
                nextAllowedActions(failedMessage, status)
        );
    }

    private FailedEventReplayApprovalStatusResponse.LatestApproval latestApproval(FailedEventMessage failedMessage) {
        return failedEventReplayApprovalHistoryRepository
                .findTopByFailedEventMessageIdOrderByChangedAtDescIdDesc(failedMessage.getId())
                .map(FailedEventReplayApprovalStatusResponse.LatestApproval::fromHistory)
                .orElseGet(() -> FailedEventReplayApprovalStatusResponse.LatestApproval.fromMessage(failedMessage));
    }

    private List<String> approvalBlockedBy(FailedEventReplayApprovalStatus status) {
        return switch (status) {
            case NOT_REQUESTED -> List.of("REPLAY_APPROVAL_NOT_REQUESTED");
            case PENDING -> List.of("REPLAY_APPROVAL_PENDING");
            case REJECTED -> List.of("REPLAY_APPROVAL_REJECTED");
            case APPROVED -> List.of();
        };
    }

    private List<String> nextAllowedActions(
            FailedEventMessage failedMessage,
            FailedEventReplayApprovalStatus status
    ) {
        if (failedMessage.getStatus() == FailedEventMessageStatus.REPLAYED) {
            return List.of();
        }
        return switch (status) {
            case NOT_REQUESTED, REJECTED -> List.of("REQUEST_REPLAY_APPROVAL");
            case PENDING -> List.of("REVIEW_REPLAY_APPROVAL");
            case APPROVED -> List.of("REPLAY_FAILED_EVENT");
        };
    }
}
