package com.codexdemo.orderplatform.notification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.codexdemo.orderplatform.outbox.OutboxRabbitMqProperties;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class FailedEventReplayReadinessServiceTests {

    private final FailedEventMessageRepository failedEventMessageRepository =
            org.mockito.Mockito.mock(FailedEventMessageRepository.class);

    private final FailedEventReplayAttemptRepository failedEventReplayAttemptRepository =
            org.mockito.Mockito.mock(FailedEventReplayAttemptRepository.class);

    private final FailedEventReplayApprovalHistoryRepository failedEventReplayApprovalHistoryRepository =
            org.mockito.Mockito.mock(FailedEventReplayApprovalHistoryRepository.class);

    private final OutboxRabbitMqProperties outboxRabbitMqProperties = new OutboxRabbitMqProperties();

    private final FailedEventReplayReadinessService service = new FailedEventReplayReadinessService(
            failedEventMessageRepository,
            failedEventReplayAttemptRepository,
            failedEventReplayApprovalHistoryRepository,
            outboxRabbitMqProperties
    );

    @Test
    void returnsStableNotFoundReadinessBody() {
        when(failedEventMessageRepository.findById(404L)).thenReturn(Optional.empty());

        FailedEventReplayReadinessResponse response = service.readiness(404L);

        assertThat(response.exists()).isFalse();
        assertThat(response.failedEventId()).isEqualTo(404L);
        assertThat(response.eligibleForReplay()).isFalse();
        assertThat(response.requiresApproval()).isFalse();
        assertThat(response.blockedBy()).containsExactly("FAILED_EVENT_NOT_FOUND");
        assertThat(response.nextAllowedActions()).isEmpty();
    }

    @Test
    void marksApprovedEventEligibleWhenReplayPreconditionsAreSatisfied() {
        outboxRabbitMqProperties.setEnabled(true);
        FailedEventMessage failedEvent = failedEvent("readiness-approved", Instant.parse("2026-05-11T09:00:00Z"));
        failedEvent.requestReplayApproval("need replay", "ops-user", Instant.parse("2026-05-11T09:05:00Z"));
        failedEvent.approveReplay("sre-user", "approved", Instant.parse("2026-05-11T09:10:00Z"));
        ReflectionTestUtils.setField(failedEvent, "id", 10L);
        FailedEventReplayAttempt latestAttempt = FailedEventReplayAttempt.record(
                failedEvent,
                "sre-user",
                "SRE",
                "replay attempt",
                new ReplayFailedEventRequest(null, null, null, null, null, "replay attempt"),
                "event-readiness-approved",
                "OrderNotificationFailed",
                "ORDER",
                "readiness-approved",
                "{\"messageId\":\"readiness-approved\"}",
                FailedEventReplayAttemptStatus.FAILED,
                "broker unavailable",
                Instant.parse("2026-05-11T09:20:00Z")
        );
        ReflectionTestUtils.setField(latestAttempt, "id", 77L);
        FailedEventReplayApprovalHistory latestApproval = FailedEventReplayApprovalHistory.record(
                failedEvent,
                FailedEventReplayApprovalHistoryAction.APPROVED,
                "sre-user",
                "SRE",
                "approved",
                Instant.parse("2026-05-11T09:10:00Z")
        );

        when(failedEventMessageRepository.findById(10L)).thenReturn(Optional.of(failedEvent));
        when(failedEventMessageRepository.countReplayBacklogBefore(
                FailedEventMessageStatus.REPLAYED,
                Instant.parse("2026-05-11T09:00:00Z"),
                10L
        )).thenReturn(2L);
        when(failedEventReplayAttemptRepository.findTopByFailedEventMessageIdOrderByAttemptedAtDescIdDesc(10L))
                .thenReturn(Optional.of(latestAttempt));
        when(failedEventReplayApprovalHistoryRepository.findTopByFailedEventMessageIdOrderByChangedAtDescIdDesc(10L))
                .thenReturn(Optional.of(latestApproval));

        FailedEventReplayReadinessResponse response = service.readiness(10L);

        assertThat(response.exists()).isTrue();
        assertThat(response.eligibleForReplay()).isTrue();
        assertThat(response.requiresApproval()).isFalse();
        assertThat(response.blockedBy()).isEmpty();
        assertThat(response.nextAllowedActions()).containsExactly("REPLAY_FAILED_EVENT");
        assertThat(response.replayBacklogPosition()).isEqualTo(3L);
        assertThat(response.latestReplayAttempt().status()).isEqualTo(FailedEventReplayAttemptStatus.FAILED);
        assertThat(response.latestApproval().status()).isEqualTo(FailedEventReplayApprovalStatus.APPROVED);
    }

    @Test
    void exposesApprovalBlockersAndNextActionsWithoutMutatingEvent() {
        outboxRabbitMqProperties.setEnabled(true);
        FailedEventMessage failedEvent = failedEvent("readiness-pending", Instant.parse("2026-05-11T10:00:00Z"));
        failedEvent.requestReplayApproval("need replay", "ops-user", Instant.parse("2026-05-11T10:05:00Z"));
        ReflectionTestUtils.setField(failedEvent, "id", 11L);

        when(failedEventMessageRepository.findById(11L)).thenReturn(Optional.of(failedEvent));
        when(failedEventMessageRepository.countReplayBacklogBefore(
                FailedEventMessageStatus.REPLAYED,
                Instant.parse("2026-05-11T10:00:00Z"),
                11L
        )).thenReturn(0L);
        when(failedEventReplayAttemptRepository.findTopByFailedEventMessageIdOrderByAttemptedAtDescIdDesc(11L))
                .thenReturn(Optional.empty());
        when(failedEventReplayApprovalHistoryRepository.findTopByFailedEventMessageIdOrderByChangedAtDescIdDesc(11L))
                .thenReturn(Optional.empty());

        FailedEventReplayReadinessResponse response = service.readiness(11L);

        assertThat(response.eligibleForReplay()).isFalse();
        assertThat(response.requiresApproval()).isTrue();
        assertThat(response.blockedBy()).containsExactly("REPLAY_APPROVAL_PENDING");
        assertThat(response.nextAllowedActions()).containsExactly("REVIEW_REPLAY_APPROVAL");
        assertThat(response.latestApproval().status()).isEqualTo(FailedEventReplayApprovalStatus.PENDING);
        assertThat(failedEvent.getReplayApprovalStatus()).isEqualTo(FailedEventReplayApprovalStatus.PENDING);
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
                "readiness test",
                "{\"messageId\":\"%s\"}".formatted(messageId)
        );
        ReflectionTestUtils.setField(failedEvent, "failedAt", failedAt);
        return failedEvent;
    }
}
