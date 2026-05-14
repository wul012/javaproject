package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.codexdemo.orderplatform.notification.FailedEventSummaryResponse;
import com.codexdemo.orderplatform.notification.FailedEventSummaryService;
import com.codexdemo.orderplatform.order.IdempotencyStore;
import com.codexdemo.orderplatform.order.IdempotencyStoreDescriptor;
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

    private final IdempotencyStore idempotencyStore = org.mockito.Mockito.mock(IdempotencyStore.class);

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
        when(idempotencyStore.descriptor()).thenReturn(new IdempotencyStoreDescriptor(
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
                false
        ));

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
                idempotencyStore,
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
                        "/contracts/order-idempotency-boundary.sample.json",
                        "/contracts/order-idempotency-store-abstraction.sample.json",
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/deployment-rollback-evidence.sample.json",
                        "/contracts/release-bundle-manifest.sample.json"
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
                        "GET /contracts/order-idempotency-boundary.sample.json",
                        "GET /contracts/order-idempotency-store-abstraction.sample.json",
                        "GET /contracts/release-verification-manifest.sample.json",
                        "GET /contracts/deployment-rollback-evidence.sample.json",
                        "GET /contracts/release-bundle-manifest.sample.json"
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
        assertThat(evidence.orderIdempotency().storeAbstractionVersion()).isEqualTo("java-idempotency-store.v1");
        assertThat(evidence.orderIdempotency().createOrderEndpoint()).isEqualTo("/api/v1/orders");
        assertThat(evidence.orderIdempotency().requiredHeader()).isEqualTo("Idempotency-Key");
        assertThat(evidence.orderIdempotency().requestFingerprintVersion())
                .isEqualTo("order-create-request-sha256.v1");
        assertThat(evidence.orderIdempotency().sameKeyDifferentRequestErrorCode())
                .isEqualTo("IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST");
        assertThat(evidence.orderIdempotency().activeStore()).isEqualTo("jpa-order-idempotency-store");
        assertThat(evidence.orderIdempotency().activeStoreImplementation()).isEqualTo("JpaIdempotencyStore");
        assertThat(evidence.orderIdempotency().activeStoreMode()).isEqualTo("JPA_DATABASE");
        assertThat(evidence.orderIdempotency().authoritativeStore())
                .isEqualTo("orders table via orders.idempotency_key and orders.idempotency_request_fingerprint");
        assertThat(evidence.orderIdempotency().storeCandidates())
                .extracting(OpsEvidenceResponse.IdempotencyStoreCandidate::name)
                .containsExactly("jpa-order-idempotency-store", "mini-kv-ttl-token-adapter");
        assertThat(evidence.orderIdempotency().storeCandidates().get(1).enabled()).isFalse();
        assertThat(evidence.orderIdempotency().storeCandidates().get(1).connected()).isFalse();
        assertThat(evidence.orderIdempotency().storeCandidates().get(1).mode())
                .isEqualTo("DISABLED_CANDIDATE_ONLY");
        assertThat(evidence.orderIdempotency().miniKvConnected()).isFalse();
        assertThat(evidence.orderIdempotency().externalTokenStoreConnected()).isFalse();
        assertThat(evidence.orderIdempotency().changesPaymentOrInventoryTransaction()).isFalse();
        assertThat(evidence.releaseVerification().manifestVersion())
                .isEqualTo("java-release-verification-manifest.v1");
        assertThat(evidence.releaseVerification().manifestEndpoint())
                .isEqualTo("/contracts/release-verification-manifest.sample.json");
        assertThat(evidence.releaseVerification().verificationMode())
                .isEqualTo("LOCAL_OPERATOR_EXECUTES_AND_ARCHIVES_RESULTS");
        assertThat(evidence.releaseVerification().requiredChecks())
                .containsExactly(
                        "focused-maven-tests",
                        "non-docker-regression-tests",
                        "maven-package",
                        "http-smoke",
                        "static-contract-json-validation"
                );
        assertThat(evidence.releaseVerification().staticContractEndpoints())
                .contains(
                        "/contracts/order-idempotency-store-abstraction.sample.json",
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/deployment-rollback-evidence.sample.json",
                        "/contracts/release-bundle-manifest.sample.json"
                );
        assertThat(evidence.releaseVerification().nodeMayExecuteBuild()).isFalse();
        assertThat(evidence.releaseVerification().nodeMayTriggerWrites()).isFalse();
        assertThat(evidence.releaseVerification().changesBusinessSemantics()).isFalse();
        assertThat(evidence.releaseVerification().requiresProductionSecrets()).isFalse();
        assertThat(evidence.deploymentRollback().evidenceVersion())
                .isEqualTo("java-deployment-rollback-evidence.v1");
        assertThat(evidence.deploymentRollback().evidenceEndpoint())
                .isEqualTo("/contracts/deployment-rollback-evidence.sample.json");
        assertThat(evidence.deploymentRollback().rollbackMode()).isEqualTo("READ_ONLY_BOUNDARY_SAMPLE");
        assertThat(evidence.deploymentRollback().rollbackSubjects())
                .containsExactly(
                        "java-package",
                        "runtime-configuration",
                        "database-migrations",
                        "static-contracts"
                );
        assertThat(evidence.deploymentRollback().requiresOperatorConfirmation())
                .containsExactly(
                        "artifact-version-target",
                        "configuration-secret-source",
                        "database-migration-direction"
                );
        assertThat(evidence.deploymentRollback().packageRollbackSupported()).isTrue();
        assertThat(evidence.deploymentRollback().configRollbackSupported()).isTrue();
        assertThat(evidence.deploymentRollback().databaseMigrationRollbackAutomatic()).isFalse();
        assertThat(evidence.deploymentRollback().contractsRollbackByArtifactVersion()).isTrue();
        assertThat(evidence.deploymentRollback().nodeMayTriggerRollback()).isFalse();
        assertThat(evidence.deploymentRollback().requiresProductionDatabase()).isFalse();
        assertThat(evidence.deploymentRollback().changesOrderTransactionSemantics()).isFalse();
        assertThat(evidence.releaseBundle().manifestVersion())
                .isEqualTo("java-release-bundle-manifest.v1");
        assertThat(evidence.releaseBundle().manifestEndpoint())
                .isEqualTo("/contracts/release-bundle-manifest.sample.json");
        assertThat(evidence.releaseBundle().bundleMode()).isEqualTo("READ_ONLY_RELEASE_BUNDLE");
        assertThat(evidence.releaseBundle().artifact())
                .isEqualTo("target/advanced-order-platform-0.1.0-SNAPSHOT.jar");
        assertThat(evidence.releaseBundle().contractEndpoints())
                .containsExactly(
                        "/contracts/ops-read-only-evidence.sample.json",
                        "/contracts/ops-evidence-field-guide.sample.json",
                        "/contracts/order-idempotency-boundary.sample.json",
                        "/contracts/order-idempotency-store-abstraction.sample.json",
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/deployment-rollback-evidence.sample.json",
                        "/contracts/release-bundle-manifest.sample.json"
                );
        assertThat(evidence.releaseBundle().requiredEvidence())
                .containsExactly(
                        "focused-maven-tests",
                        "non-docker-regression-tests",
                        "maven-package",
                        "http-smoke",
                        "static-contract-json-validation"
                );
        assertThat(evidence.releaseBundle().nodeMayConsume()).isTrue();
        assertThat(evidence.releaseBundle().nodeMayExecuteBuild()).isFalse();
        assertThat(evidence.releaseBundle().nodeMayTriggerRollback()).isFalse();
        assertThat(evidence.releaseBundle().requiresProductionDatabase()).isFalse();
        assertThat(evidence.releaseBundle().changesOrderTransactionSemantics()).isFalse();
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
                        "/contracts/order-idempotency-store-abstraction.sample.json",
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/deployment-rollback-evidence.sample.json",
                        "/contracts/release-bundle-manifest.sample.json",
                        "/api/v1/failed-events/{id}/replay-execution-contract",
                        "/api/v1/failed-events/replay-evidence-index"
                );
    }
}
