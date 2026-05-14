package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.codexdemo.orderplatform.notification.FailedEventSummaryResponse;
import com.codexdemo.orderplatform.notification.FailedEventSummaryService;
import com.codexdemo.orderplatform.outbox.OutboxPublisherProperties;
import com.codexdemo.orderplatform.outbox.OutboxRabbitMqProperties;
import com.codexdemo.orderplatform.outbox.OutboxRepository;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

class OpsEvidenceServiceTests {

    private final FailedEventSummaryService failedEventSummaryService =
            org.mockito.Mockito.mock(FailedEventSummaryService.class);

    private final OutboxRepository outboxRepository = org.mockito.Mockito.mock(OutboxRepository.class);

    @Test
    void buildsReadOnlyEvidenceForControlPlane() {
        Instant latestFailedAt = Instant.parse("2026-05-12T01:00:00Z");
        Instant latestApprovalAt = Instant.parse("2026-05-12T01:05:00Z");
        when(failedEventSummaryService.summary()).thenReturn(new FailedEventSummaryResponse(
                Instant.parse("2026-05-12T01:10:00Z"),
                4,
                2,
                1,
                1,
                latestFailedAt,
                latestApprovalAt,
                3
        ));
        when(outboxRepository.countByPublishedAtIsNull()).thenReturn(6L);

        OutboxPublisherProperties outboxPublisherProperties = new OutboxPublisherProperties();
        outboxPublisherProperties.setEnabled(false);
        OutboxRabbitMqProperties outboxRabbitMqProperties = new OutboxRabbitMqProperties();
        outboxRabbitMqProperties.setEnabled(false);
        outboxRabbitMqProperties.setExchange("order-platform.outbox");
        outboxRabbitMqProperties.setQueue("order-platform.outbox.events");
        outboxRabbitMqProperties.setDeadLetterQueue("order-platform.outbox.events.dlq");
        MockEnvironment environment = new MockEnvironment()
                .withProperty("spring.application.name", "advanced-order-platform")
                .withProperty("info.app.version", "0.1.0-test");
        environment.setActiveProfiles("local", "ops");

        OpsEvidenceService service = new OpsEvidenceService(
                failedEventSummaryService,
                outboxRepository,
                outboxPublisherProperties,
                outboxRabbitMqProperties,
                environment
        );

        OpsEvidenceResponse evidence = service.evidence();

        assertThat(evidence.evidenceVersion()).isEqualTo("java-ops-evidence.v1");
        assertThat(evidence.readOnly()).isTrue();
        assertThat(evidence.executionAllowed()).isFalse();
        assertThat(evidence.service().name()).isEqualTo("advanced-order-platform");
        assertThat(evidence.service().version()).isEqualTo("0.1.0-test");
        assertThat(evidence.service().profiles()).containsExactly("local", "ops");
        assertThat(evidence.healthProbe().endpoint()).isEqualTo("/actuator/health");
        assertThat(evidence.healthProbe().method()).isEqualTo("GET");
        assertThat(evidence.healthProbe().expectedStatus()).isEqualTo("UP");
        assertThat(evidence.healthProbe().evidenceEndpoint()).isEqualTo("/api/v1/ops/evidence");
        assertThat(evidence.healthProbe().additionalProbeEndpoints())
                .containsExactly(
                        "/api/v1/ops/overview",
                        "/contracts/ops-read-only-evidence.sample.json",
                        "/contracts/order-idempotency-boundary.sample.json"
                );
        assertThat(evidence.healthProbe().liveProbeRequiredForPass()).isTrue();
        assertThat(evidence.healthProbe().staticSampleOnly()).isFalse();
        assertThat(evidence.readOnlyWindow().windowVersion()).isEqualTo("java-read-only-window.v1");
        assertThat(evidence.readOnlyWindow().operatorStartRequired()).isTrue();
        assertThat(evidence.readOnlyWindow().nodeAutoStartAllowed()).isFalse();
        assertThat(evidence.readOnlyWindow().upstreamProbesRequired()).isTrue();
        assertThat(evidence.readOnlyWindow().upstreamActionsAllowed()).isFalse();
        assertThat(evidence.readOnlyWindow().readyForReadOnlyLiveProbe()).isTrue();
        assertThat(evidence.readOnlyWindow().readyForProductionOperations()).isFalse();
        assertThat(evidence.readOnlyWindow().allowedProbeEndpoints())
                .containsExactly(
                        "GET /actuator/health",
                        "GET /api/v1/ops/overview",
                        "GET /api/v1/ops/evidence",
                        "GET /contracts/ops-read-only-evidence.sample.json",
                        "GET /contracts/order-idempotency-boundary.sample.json"
                );
        assertThat(evidence.readOnlyWindow().forbiddenOperations())
                .contains(
                        "POST /api/v1/failed-events/{id}/replay",
                        "Any non-GET Node upstream action"
                );
        assertThat(evidence.readOnlyWindow().requiredNodeEnvironment())
                .containsExactly("UPSTREAM_PROBES_ENABLED=true", "UPSTREAM_ACTIONS_ENABLED=false");
        assertThat(evidence.readOnlyWindow().replayPostBoundary())
                .contains("must not call POST /api/v1/failed-events/{id}/replay");
        assertThat(evidence.orderIdempotency().boundaryVersion()).isEqualTo("java-order-idempotency-boundary.v1");
        assertThat(evidence.orderIdempotency().createOrderEndpoint()).isEqualTo("/api/v1/orders");
        assertThat(evidence.orderIdempotency().requiredHeader()).isEqualTo("Idempotency-Key");
        assertThat(evidence.orderIdempotency().requestFingerprintVersion())
                .isEqualTo("order-create-request-sha256.v1");
        assertThat(evidence.orderIdempotency().sameKeyDifferentRequestErrorCode())
                .isEqualTo("IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST");
        assertThat(evidence.orderIdempotency().miniKvConnected()).isFalse();
        assertThat(evidence.orderIdempotency().externalTokenStoreConnected()).isFalse();
        assertThat(evidence.orderIdempotency().changesPaymentOrInventoryTransaction()).isFalse();
        assertThat(evidence.failedEventReplay().totalFailedEvents()).isEqualTo(4);
        assertThat(evidence.failedEventReplay().pendingReplayApprovals()).isEqualTo(2);
        assertThat(evidence.failedEventReplay().approvedReplayApprovals()).isEqualTo(1);
        assertThat(evidence.failedEventReplay().rejectedReplayApprovals()).isEqualTo(1);
        assertThat(evidence.failedEventReplay().replayBacklog()).isEqualTo(3);
        assertThat(evidence.failedEventReplay().latestFailedAt()).isEqualTo(latestFailedAt);
        assertThat(evidence.failedEventReplay().latestApprovalAt()).isEqualTo(latestApprovalAt);
        assertThat(evidence.failedEventReplay().realReplayAllowedByEvidence()).isFalse();
        assertThat(evidence.outbox().pendingEvents()).isEqualTo(6);
        assertThat(evidence.outbox().publisherEnabled()).isFalse();
        assertThat(evidence.outbox().rabbitMqEnabled()).isFalse();
        assertThat(evidence.outbox().blockers())
                .containsExactly("OUTBOX_PUBLISHER_DISABLED", "RABBITMQ_OUTBOX_DISABLED");
        assertThat(evidence.approvalExecution().requiredApprovalStatus()).isEqualTo("APPROVED");
        assertThat(evidence.approvalExecution().approvalRequired()).isTrue();
        assertThat(evidence.approvalExecution().dryRun()).isTrue();
        assertThat(evidence.approvalExecution().executionBlockers())
                .containsExactly(
                        "READ_ONLY_EVIDENCE_ENDPOINT",
                        "REPLAY_APPROVAL_PENDING",
                        "REPLAY_APPROVAL_REJECTED",
                        "REPLAY_BACKLOG_PRESENT"
                );
        assertThat(evidence.blockers())
                .contains(
                        "READ_ONLY_EVIDENCE_ENDPOINT",
                        "OUTBOX_PUBLISHER_DISABLED",
                        "RABBITMQ_OUTBOX_DISABLED"
                );
        assertThat(evidence.warnings())
                .containsExactly("OUTBOX_PENDING_EVENTS", "APPROVED_REPLAY_REQUIRES_DIGEST_CHECK");
        assertThat(evidence.evidenceEndpoints())
                .contains(
                        "/api/v1/ops/evidence",
                        "/contracts/ops-read-only-evidence.sample.json",
                        "/contracts/ops-evidence-field-guide.sample.json",
                        "/contracts/order-idempotency-boundary.sample.json",
                        "/api/v1/failed-events/{id}/replay-execution-contract",
                        "/api/v1/failed-events/replay-evidence-index"
                );
    }
}
