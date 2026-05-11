package com.codexdemo.orderplatform.notification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class FailedEventSummaryServiceTests {

    private final FailedEventMessageRepository failedEventMessageRepository =
            org.mockito.Mockito.mock(FailedEventMessageRepository.class);

    @Test
    void summarizesFailedEventGovernanceSignals() {
        FailedEventMessage latestFailed = failedEvent("summary-latest-failed");
        FailedEventMessage latestRequest = failedEvent("summary-latest-request");
        latestRequest.requestReplayApproval("request", "ops-user", Instant.parse("2026-05-11T08:10:00Z"));
        FailedEventMessage latestReview = failedEvent("summary-latest-review");
        latestReview.requestReplayApproval("review", "ops-user", Instant.parse("2026-05-11T08:11:00Z"));
        latestReview.approveReplay("sre-user", "approved", Instant.parse("2026-05-11T08:12:00Z"));
        FailedEventSummaryService service = new FailedEventSummaryService(failedEventMessageRepository);

        when(failedEventMessageRepository.count()).thenReturn(8L);
        when(failedEventMessageRepository.countByReplayApprovalStatus(FailedEventReplayApprovalStatus.PENDING))
                .thenReturn(2L);
        when(failedEventMessageRepository.countByReplayApprovalStatus(FailedEventReplayApprovalStatus.APPROVED))
                .thenReturn(3L);
        when(failedEventMessageRepository.countByReplayApprovalStatus(FailedEventReplayApprovalStatus.REJECTED))
                .thenReturn(1L);
        when(failedEventMessageRepository.findTopByOrderByFailedAtDescIdDesc())
                .thenReturn(Optional.of(latestFailed));
        when(failedEventMessageRepository
                .findTopByReplayApprovalRequestedAtIsNotNullOrderByReplayApprovalRequestedAtDescIdDesc())
                .thenReturn(Optional.of(latestRequest));
        when(failedEventMessageRepository
                .findTopByReplayApprovalReviewedAtIsNotNullOrderByReplayApprovalReviewedAtDescIdDesc())
                .thenReturn(Optional.of(latestReview));
        when(failedEventMessageRepository.countByStatusNot(FailedEventMessageStatus.REPLAYED)).thenReturn(5L);

        FailedEventSummaryResponse summary = service.summary();

        assertThat(summary.sampledAt()).isNotNull();
        assertThat(summary.totalFailedEvents()).isEqualTo(8L);
        assertThat(summary.pendingReplayApprovals()).isEqualTo(2L);
        assertThat(summary.approvedReplayApprovals()).isEqualTo(3L);
        assertThat(summary.rejectedReplayApprovals()).isEqualTo(1L);
        assertThat(summary.latestFailedAt()).isEqualTo(latestFailed.getFailedAt());
        assertThat(summary.latestApprovalAt()).isEqualTo(Instant.parse("2026-05-11T08:12:00Z"));
        assertThat(summary.replayBacklog()).isEqualTo(5L);
    }

    private FailedEventMessage failedEvent(String messageId) {
        return FailedEventMessage.record(
                messageId,
                "event-" + messageId,
                "OrderNotificationFailed",
                "ORDER",
                messageId,
                "order-platform.outbox.events",
                "order-platform.outbox.events.dlq",
                "summary test",
                "{\"messageId\":\"%s\"}".formatted(messageId)
        );
    }
}
