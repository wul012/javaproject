package com.codexdemo.orderplatform.notification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class FailedEventReplayApprovalStatusServiceTests {

    private final FailedEventMessageRepository failedEventMessageRepository =
            org.mockito.Mockito.mock(FailedEventMessageRepository.class);

    private final FailedEventReplayApprovalHistoryRepository failedEventReplayApprovalHistoryRepository =
            org.mockito.Mockito.mock(FailedEventReplayApprovalHistoryRepository.class);

    private final FailedEventReplayApprovalStatusService service = new FailedEventReplayApprovalStatusService(
            failedEventMessageRepository,
            failedEventReplayApprovalHistoryRepository
    );

    @Test
    void returnsStableNotFoundApprovalStatusBody() {
        when(failedEventMessageRepository.findById(404L)).thenReturn(Optional.empty());

        FailedEventReplayApprovalStatusResponse response = service.approvalStatus(404L);

        assertThat(response.exists()).isFalse();
        assertThat(response.failedEventId()).isEqualTo(404L);
        assertThat(response.evidenceVersion()).isEqualTo("failed-event-approval-status.v1");
        assertThat(response.approvalDigest()).startsWith("sha256:");
        assertThat(response.replayEligibilityDigest()).startsWith("sha256:");
        assertThat(response.requiredApprovalStatus()).isEqualTo(FailedEventReplayApprovalStatus.APPROVED);
        assertThat(response.approvedForReplay()).isFalse();
        assertThat(response.historyCount()).isZero();
        assertThat(response.approvalBlockedBy()).containsExactly("FAILED_EVENT_NOT_FOUND");
        assertThat(response.nextAllowedActions()).isEmpty();
    }

    @Test
    void exposesApprovedApprovalStateFromLatestHistory() {
        FailedEventMessage failedEvent = failedEvent("approval-approved", Instant.parse("2026-05-12T08:00:00Z"));
        failedEvent.requestReplayApproval("need replay", "ops-user", Instant.parse("2026-05-12T08:05:00Z"));
        failedEvent.approveReplay("sre-user", "approved", Instant.parse("2026-05-12T08:10:00Z"));
        ReflectionTestUtils.setField(failedEvent, "id", 10L);
        FailedEventReplayApprovalHistory latestHistory = FailedEventReplayApprovalHistory.record(
                failedEvent,
                FailedEventReplayApprovalHistoryAction.APPROVED,
                "sre-user",
                "SRE",
                "approved",
                Instant.parse("2026-05-12T08:10:00Z")
        );

        when(failedEventMessageRepository.findById(10L)).thenReturn(Optional.of(failedEvent));
        when(failedEventReplayApprovalHistoryRepository.countByFailedEventMessageId(10L)).thenReturn(2L);
        when(failedEventReplayApprovalHistoryRepository.findTopByFailedEventMessageIdOrderByChangedAtDescIdDesc(10L))
                .thenReturn(Optional.of(latestHistory));

        FailedEventReplayApprovalStatusResponse response = service.approvalStatus(10L);

        assertThat(response.exists()).isTrue();
        assertThat(response.approvalStatus()).isEqualTo(FailedEventReplayApprovalStatus.APPROVED);
        assertThat(response.evidenceVersion()).isEqualTo("failed-event-approval-status.v1");
        assertThat(response.approvalDigest()).startsWith("sha256:");
        assertThat(response.replayEligibilityDigest()).startsWith("sha256:");
        assertThat(response.approvalRequested()).isTrue();
        assertThat(response.approvedForReplay()).isTrue();
        assertThat(response.approvalBlockedBy()).isEmpty();
        assertThat(response.nextAllowedActions()).containsExactly("REPLAY_FAILED_EVENT");
        assertThat(response.historyCount()).isEqualTo(2L);
        assertThat(response.latestApproval().action()).isEqualTo(FailedEventReplayApprovalHistoryAction.APPROVED);
        assertThat(response.latestApproval().operatorRole()).isEqualTo("SRE");

        FailedEventReplayApprovalStatusResponse repeated = service.approvalStatus(10L);
        assertThat(repeated.approvalDigest()).isEqualTo(response.approvalDigest());
        assertThat(repeated.replayEligibilityDigest()).isEqualTo(response.replayEligibilityDigest());
    }

    @Test
    void fallsBackToMessageApprovalFieldsWhenHistoryIsMissing() {
        FailedEventMessage failedEvent = failedEvent("approval-pending", Instant.parse("2026-05-12T09:00:00Z"));
        failedEvent.requestReplayApproval("need replay", "ops-user", Instant.parse("2026-05-12T09:05:00Z"));
        ReflectionTestUtils.setField(failedEvent, "id", 11L);

        when(failedEventMessageRepository.findById(11L)).thenReturn(Optional.of(failedEvent));
        when(failedEventReplayApprovalHistoryRepository.countByFailedEventMessageId(11L)).thenReturn(0L);
        when(failedEventReplayApprovalHistoryRepository.findTopByFailedEventMessageIdOrderByChangedAtDescIdDesc(11L))
                .thenReturn(Optional.empty());

        FailedEventReplayApprovalStatusResponse response = service.approvalStatus(11L);

        assertThat(response.approvalStatus()).isEqualTo(FailedEventReplayApprovalStatus.PENDING);
        assertThat(response.approvalDigest()).startsWith("sha256:");
        assertThat(response.replayEligibilityDigest()).startsWith("sha256:");
        assertThat(response.approvalPending()).isTrue();
        assertThat(response.approvalBlockedBy()).containsExactly("REPLAY_APPROVAL_PENDING");
        assertThat(response.nextAllowedActions()).containsExactly("REVIEW_REPLAY_APPROVAL");
        assertThat(response.latestApproval().action()).isEqualTo(FailedEventReplayApprovalHistoryAction.REQUESTED);
        assertThat(response.latestApproval().operatorId()).isEqualTo("ops-user");
    }

    private FailedEventMessage failedEvent(String messageId, Instant failedAt) {
        FailedEventMessage failedEvent = FailedEventMessage.record(
                messageId,
                "event-" + messageId,
                "OrderNotificationFailed",
                "ORDER",
                messageId,
                "order-platform.outbox.events",
                "order-platform.outbox.events.dlq",
                "approval status test",
                "{\"messageId\":\"%s\"}".formatted(messageId)
        );
        ReflectionTestUtils.setField(failedEvent, "failedAt", failedAt);
        return failedEvent;
    }
}
