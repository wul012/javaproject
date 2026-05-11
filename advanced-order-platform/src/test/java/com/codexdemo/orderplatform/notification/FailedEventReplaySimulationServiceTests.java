package com.codexdemo.orderplatform.notification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

class FailedEventReplaySimulationServiceTests {

    private final FailedEventReplayReadinessService failedEventReplayReadinessService =
            org.mockito.Mockito.mock(FailedEventReplayReadinessService.class);

    private final FailedEventReplaySimulationService service =
            new FailedEventReplaySimulationService(failedEventReplayReadinessService);

    @Test
    void returnsNoSideEffectSimulationWhenFailedEventDoesNotExist() {
        when(failedEventReplayReadinessService.readiness(404L)).thenReturn(new FailedEventReplayReadinessResponse(
                Instant.parse("2026-05-11T13:00:00Z"),
                404L,
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
        ));

        FailedEventReplaySimulationResponse response = service.simulation(404L);

        assertThat(response.exists()).isFalse();
        assertThat(response.wouldReplay()).isFalse();
        assertThat(response.wouldPublishOutbox()).isFalse();
        assertThat(response.wouldChangeManagementStatus()).isFalse();
        assertThat(response.requiredApprovalStatus()).isEqualTo(FailedEventReplayApprovalStatus.APPROVED);
        assertThat(response.idempotencyKeyHint()).isNull();
        assertThat(response.expectedSideEffects()).isEmpty();
        assertThat(response.blockedBy()).containsExactly("FAILED_EVENT_NOT_FOUND");
    }

    @Test
    void simulatesReplaySideEffectsWhenReadinessIsEligible() {
        when(failedEventReplayReadinessService.readiness(10L)).thenReturn(new FailedEventReplayReadinessResponse(
                Instant.parse("2026-05-11T13:10:00Z"),
                10L,
                true,
                "OrderNotificationFailed",
                "ORDER",
                "order-1001",
                Instant.parse("2026-05-11T13:00:00Z"),
                FailedEventManagementStatus.OPEN,
                FailedEventReplayApprovalStatus.APPROVED,
                1L,
                true,
                false,
                List.of(),
                List.of(),
                List.of("REPLAY_FAILED_EVENT"),
                null,
                null
        ));

        FailedEventReplaySimulationResponse response = service.simulation(10L);

        assertThat(response.exists()).isTrue();
        assertThat(response.eligibleForReplay()).isTrue();
        assertThat(response.wouldReplay()).isTrue();
        assertThat(response.wouldPublishOutbox()).isTrue();
        assertThat(response.wouldChangeManagementStatus()).isFalse();
        assertThat(response.requiredApprovalStatus()).isEqualTo(FailedEventReplayApprovalStatus.APPROVED);
        assertThat(response.idempotencyKeyHint()).isEqualTo("failed-event-replay:10:order-1001");
        assertThat(response.expectedAggregateId()).isEqualTo("order-1001");
        assertThat(response.expectedSideEffects()).containsExactly(
                "PUBLISH_RABBITMQ_REPLAY_MESSAGE",
                "SAVE_REPLAY_ATTEMPT_AUDIT",
                "MARK_FAILED_EVENT_REPLAYED_ON_SUCCESS",
                "MARK_FAILED_EVENT_REPLAY_FAILED_ON_BROKER_ERROR"
        );
        assertThat(response.nextAllowedActions()).containsExactly("REPLAY_FAILED_EVENT");
    }
}
