package com.codexdemo.orderplatform.notification;

import java.time.Instant;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FailedEventSummaryService {

    private final FailedEventMessageRepository failedEventMessageRepository;

    public FailedEventSummaryService(FailedEventMessageRepository failedEventMessageRepository) {
        this.failedEventMessageRepository = failedEventMessageRepository;
    }

    @Transactional(readOnly = true)
    public FailedEventSummaryResponse summary() {
        return new FailedEventSummaryResponse(
                Instant.now(),
                failedEventMessageRepository.count(),
                failedEventMessageRepository.countByReplayApprovalStatus(FailedEventReplayApprovalStatus.PENDING),
                failedEventMessageRepository.countByReplayApprovalStatus(FailedEventReplayApprovalStatus.APPROVED),
                failedEventMessageRepository.countByReplayApprovalStatus(FailedEventReplayApprovalStatus.REJECTED),
                latestFailedAt(),
                latestApprovalAt(),
                failedEventMessageRepository.countByStatusNot(FailedEventMessageStatus.REPLAYED)
        );
    }

    private Instant latestFailedAt() {
        return failedEventMessageRepository.findTopByOrderByFailedAtDescIdDesc()
                .map(FailedEventMessage::getFailedAt)
                .orElse(null);
    }

    private Instant latestApprovalAt() {
        Instant latestRequest = failedEventMessageRepository
                .findTopByReplayApprovalRequestedAtIsNotNullOrderByReplayApprovalRequestedAtDescIdDesc()
                .map(FailedEventMessage::getReplayApprovalRequestedAt)
                .orElse(null);
        Instant latestReview = failedEventMessageRepository
                .findTopByReplayApprovalReviewedAtIsNotNullOrderByReplayApprovalReviewedAtDescIdDesc()
                .map(FailedEventMessage::getReplayApprovalReviewedAt)
                .orElse(null);
        return latest(latestRequest, latestReview).orElse(null);
    }

    private Optional<Instant> latest(Instant first, Instant second) {
        if (first == null) {
            return Optional.ofNullable(second);
        }
        if (second == null || first.isAfter(second)) {
            return Optional.of(first);
        }
        return Optional.of(second);
    }
}
