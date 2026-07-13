package com.codexdemo.orderplatform.ops;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.codexdemo.orderplatform.notification.FailedEventSummaryResponse;
import com.codexdemo.orderplatform.notification.FailedEventSummaryService;
import com.codexdemo.orderplatform.order.IdempotencyStore;
import com.codexdemo.orderplatform.order.IdempotencyStoreDescriptor;
import com.codexdemo.orderplatform.outbox.OutboxPublisherProperties;
import com.codexdemo.orderplatform.outbox.OutboxRabbitMqProperties;
import com.codexdemo.orderplatform.outbox.OutboxRepository;
import java.time.Instant;
import org.springframework.mock.env.MockEnvironment;

abstract class OpsEvidenceContractTestSupport {

  protected Scenario scenario() {
    Instant latestFailedAt = Instant.parse("2026-05-12T01:00:00Z");
    Instant latestApprovalAt = Instant.parse("2026-05-12T01:05:00Z");
    FailedEventSummaryService summaryService = mock(FailedEventSummaryService.class);
    OutboxRepository outboxRepository = mock(OutboxRepository.class);
    IdempotencyStore idempotencyStore = mock(IdempotencyStore.class);
    when(summaryService.summary())
        .thenReturn(
            new FailedEventSummaryResponse(
                Instant.parse("2026-05-12T01:10:00Z"),
                4,
                2,
                1,
                1,
                latestFailedAt,
                latestApprovalAt,
                3));
    when(outboxRepository.countByPublishedAtIsNull()).thenReturn(6L);
    when(idempotencyStore.descriptor())
        .thenReturn(
            new IdempotencyStoreDescriptor(
                "java-idempotency-store.v1",
                "jpa-order-idempotency-store",
                "JpaIdempotencyStore",
                "JPA_DATABASE",
                "orders table",
                "orders.idempotency_key",
                "orders.idempotency_request_fingerprint",
                true,
                false,
                false,
                true,
                false,
                "DISABLED_CANDIDATE_ONLY",
                "mini-kv-ttl-token-adapter is documented for later TTL-token experiments, not wired into create-order.",
                false));

    OutboxPublisherProperties publisher = new OutboxPublisherProperties();
    publisher.setEnabled(false);
    OutboxRabbitMqProperties rabbitMq = new OutboxRabbitMqProperties();
    rabbitMq.setEnabled(false);
    rabbitMq.setExchange("order-platform.outbox");
    rabbitMq.setQueue("order-platform.outbox.events");
    rabbitMq.setDeadLetterQueue("order-platform.outbox.events.dlq");
    MockEnvironment environment =
        new MockEnvironment()
            .withProperty("spring.application.name", "advanced-order-platform")
            .withProperty("info.app.version", "0.1.0-test");
    environment.setActiveProfiles("local", "ops");

    OpsEvidenceService service =
        new OpsEvidenceService(
            summaryService, outboxRepository, publisher, rabbitMq, idempotencyStore, environment);
    return new Scenario(service.evidence(), latestFailedAt, latestApprovalAt);
  }

  protected record Scenario(
      OpsEvidenceResponse evidence, Instant latestFailedAt, Instant latestApprovalAt) {}
}
