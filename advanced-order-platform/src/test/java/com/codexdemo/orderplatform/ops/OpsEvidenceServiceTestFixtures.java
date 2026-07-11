package com.codexdemo.orderplatform.ops;

import static org.mockito.Mockito.when;

import com.codexdemo.orderplatform.notification.FailedEventSummaryResponse;
import com.codexdemo.orderplatform.notification.FailedEventSummaryService;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalRequest;
import com.codexdemo.orderplatform.order.IdempotencyStore;
import com.codexdemo.orderplatform.order.IdempotencyStoreDescriptor;
import com.codexdemo.orderplatform.outbox.OutboxPublisherProperties;
import com.codexdemo.orderplatform.outbox.OutboxRabbitMqProperties;
import com.codexdemo.orderplatform.outbox.OutboxRepository;
import java.time.Instant;
import org.springframework.mock.env.MockEnvironment;

public final class OpsEvidenceServiceTestFixtures {

  private OpsEvidenceServiceTestFixtures() {}

  public static OpsEvidenceService readOnlyFixtureService(
      FailedEventSummaryService failedEventSummaryService,
      OutboxRepository outboxRepository,
      IdempotencyStore idempotencyStore) {
    Instant latestFailedAt = Instant.parse("2026-05-12T01:00:00Z");
    Instant latestApprovalAt = Instant.parse("2026-05-12T01:05:00Z");
    when(failedEventSummaryService.summary())
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
    OutboxPublisherProperties outboxPublisherProperties = new OutboxPublisherProperties();
    outboxPublisherProperties.setEnabled(false);
    OutboxRabbitMqProperties outboxRabbitMqProperties = new OutboxRabbitMqProperties();
    outboxRabbitMqProperties.setEnabled(false);
    outboxRabbitMqProperties.setExchange("order-platform.outbox");
    outboxRabbitMqProperties.setQueue("order-platform.outbox.events");
    outboxRabbitMqProperties.setDeadLetterQueue("order-platform.outbox.events.dlq");
    MockEnvironment environment =
        new MockEnvironment()
            .withProperty("spring.application.name", "advanced-order-platform")
            .withProperty("info.app.version", "0.1.0-test");
    environment.setActiveProfiles("local", "ops");
    return new OpsEvidenceService(
        failedEventSummaryService,
        outboxRepository,
        outboxPublisherProperties,
        outboxRabbitMqProperties,
        idempotencyStore,
        environment);
  }

  public static ReleaseApprovalRehearsalRequest paddedHeaderBackedRehearsalRequest() {
    return new ReleaseApprovalRehearsalRequest(
        new ReleaseApprovalRehearsalRequest.Context(
            " rehearsal-v67-001 ", " release-operator@example.test ", " audit-correlation-v67 "),
        new ReleaseApprovalRehearsalRequest.OperatorWindow(
            " operator-198 ", " operator,auditor ", " true ", " approval-v198-operator-window "),
        new ReleaseApprovalRehearsalRequest.CiEvidence(
            " real-read-window-ci-archive-artifact-manifest.v1 ",
            " sha256:node-v200-manifest-digest ",
            " /api/v1/production/real-read-window-ci-archive-artifact-manifest ",
            " 9 ",
            " approval-v198-operator-window "),
        new ReleaseApprovalRehearsalRequest.ArtifactRetention(
            " real-read-window-ci-artifact-upload-dry-run-contract.v1 ",
            " sha256:node-v202-upload-contract-digest ",
            " orderops-real-read-window-evidence-v191-v201 ",
            " c/ ",
            " 30 ",
            " dry-run-contract-only "),
        new ReleaseApprovalRehearsalRequest.RuntimeReadiness(
            " three-project-real-read-runtime-smoke-preflight.v1 ",
            " sha256:node-v204-preflight-digest ",
            " runtime-smoke-v205-session-001 ",
            " java-release-approval-rehearsal ",
            " manual-open-window-plan "),
        new ReleaseApprovalRehearsalRequest.ManagedAudit(
            " managed-audit-persistence-boundary-candidate.v1 ",
            " sha256:node-v208-managed-audit-candidate-digest ",
            " file-or-sqlite-dry-run-candidate ",
            " 30 ",
            " size-and-age-rotation-candidate "),
        new ReleaseApprovalRehearsalRequest.ApprovalBinding(
            " managed-audit-identity-approval-binding-contract.v1 ",
            " sha256:node-v210-approval-binding-digest ",
            " approval-request-v210-001 ",
            " APPROVED_DRY_RUN_ONLY ",
            " approval-record-correlation-v210 "));
  }

  public static ReleaseApprovalRehearsalRequest headerBackedRehearsalRequest() {
    return new ReleaseApprovalRehearsalRequest(
        new ReleaseApprovalRehearsalRequest.Context(
            "rehearsal-v67-001", "release-operator@example.test", "audit-correlation-v67"),
        new ReleaseApprovalRehearsalRequest.OperatorWindow(
            "operator-198", "operator,auditor", "true", "approval-v198-operator-window"),
        new ReleaseApprovalRehearsalRequest.CiEvidence(
            "real-read-window-ci-archive-artifact-manifest.v1",
            "sha256:node-v200-manifest-digest",
            "/api/v1/production/real-read-window-ci-archive-artifact-manifest",
            "9",
            "approval-v198-operator-window"),
        new ReleaseApprovalRehearsalRequest.ArtifactRetention(
            "real-read-window-ci-artifact-upload-dry-run-contract.v1",
            "sha256:node-v202-upload-contract-digest",
            "orderops-real-read-window-evidence-v191-v201",
            "c/",
            "30",
            "dry-run-contract-only"),
        new ReleaseApprovalRehearsalRequest.RuntimeReadiness(
            "three-project-real-read-runtime-smoke-preflight.v1",
            "sha256:node-v204-preflight-digest",
            "runtime-smoke-v205-session-001",
            "java-release-approval-rehearsal",
            "manual-open-window-plan"),
        new ReleaseApprovalRehearsalRequest.ManagedAudit(
            "managed-audit-persistence-boundary-candidate.v1",
            "sha256:node-v208-managed-audit-candidate-digest",
            "file-or-sqlite-dry-run-candidate",
            "30",
            "size-and-age-rotation-candidate"),
        new ReleaseApprovalRehearsalRequest.ApprovalBinding(
            "managed-audit-identity-approval-binding-contract.v1",
            "sha256:node-v210-approval-binding-digest",
            "approval-request-v210-001",
            "APPROVED_DRY_RUN_ONLY",
            "approval-record-correlation-v210"));
  }
}
