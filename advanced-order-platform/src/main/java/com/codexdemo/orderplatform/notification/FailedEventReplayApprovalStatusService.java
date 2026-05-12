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
        boolean approvalRequested = status != FailedEventReplayApprovalStatus.NOT_REQUESTED;
        boolean approvalPending = status == FailedEventReplayApprovalStatus.PENDING;
        boolean approvedForReplay = status == FailedEventReplayApprovalStatus.APPROVED;
        boolean rejected = status == FailedEventReplayApprovalStatus.REJECTED;
        long historyCount = failedEventReplayApprovalHistoryRepository.countByFailedEventMessageId(failedMessage.getId());
        FailedEventReplayApprovalStatusResponse.LatestApproval latestApproval = latestApproval(failedMessage);
        List<String> approvalBlockedBy = approvalBlockedBy(status);
        List<String> nextAllowedActions = nextAllowedActions(failedMessage, status);
        return new FailedEventReplayApprovalStatusResponse(
                sampledAt,
                failedMessage.getId(),
                true,
                FailedEventReplayApprovalEvidenceDigests.EVIDENCE_VERSION,
                FailedEventReplayApprovalEvidenceDigests.approvalDigest(
                        failedMessage.getId(),
                        true,
                        status,
                        REQUIRED_APPROVAL_STATUS,
                        approvalRequested,
                        approvalPending,
                        approvedForReplay,
                        rejected,
                        failedMessage.getReplayApprovalReason(),
                        failedMessage.getReplayApprovalRequestedBy(),
                        failedMessage.getReplayApprovalRequestedAt(),
                        failedMessage.getReplayApprovalReviewedBy(),
                        failedMessage.getReplayApprovalReviewedAt(),
                        failedMessage.getReplayApprovalReviewNote(),
                        historyCount,
                        latestApproval
                ),
                FailedEventReplayApprovalEvidenceDigests.replayEligibilityDigest(
                        failedMessage.getId(),
                        true,
                        failedMessage.getStatus(),
                        failedMessage.getManagementStatus(),
                        status,
                        REQUIRED_APPROVAL_STATUS,
                        approvedForReplay,
                        approvalBlockedBy,
                        nextAllowedActions
                ),
                failedMessage.getStatus(),
                failedMessage.getManagementStatus(),
                status,
                REQUIRED_APPROVAL_STATUS,
                approvalRequested,
                approvalPending,
                approvedForReplay,
                rejected,
                failedMessage.getReplayApprovalReason(),
                failedMessage.getReplayApprovalRequestedBy(),
                failedMessage.getReplayApprovalRequestedAt(),
                failedMessage.getReplayApprovalReviewedBy(),
                failedMessage.getReplayApprovalReviewedAt(),
                failedMessage.getReplayApprovalReviewNote(),
                historyCount,
                latestApproval,
                approvalBlockedBy,
                nextAllowedActions
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
