package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.notification.FailedEventSummaryResponse;
import com.codexdemo.orderplatform.notification.FailedEventSummaryService;
import com.codexdemo.orderplatform.order.IdempotencyStore;
import com.codexdemo.orderplatform.order.IdempotencyStoreDescriptor;
import com.codexdemo.orderplatform.order.JpaIdempotencyStore;
import com.codexdemo.orderplatform.outbox.OutboxPublisherProperties;
import com.codexdemo.orderplatform.outbox.OutboxRabbitMqProperties;
import com.codexdemo.orderplatform.outbox.OutboxRepository;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsEvidenceService {

    static final String EVIDENCE_VERSION = "java-ops-evidence.v1";

    static final String RELEASE_VERIFICATION_MANIFEST_VERSION = "java-release-verification-manifest.v1";

    static final String RELEASE_VERIFICATION_MANIFEST_ENDPOINT =
            "/contracts/release-verification-manifest.sample.json";

    static final String DEPLOYMENT_ROLLBACK_EVIDENCE_VERSION = "java-deployment-rollback-evidence.v1";

    static final String DEPLOYMENT_ROLLBACK_EVIDENCE_ENDPOINT =
            "/contracts/deployment-rollback-evidence.sample.json";

    static final String RELEASE_BUNDLE_MANIFEST_VERSION = "java-release-bundle-manifest.v1";

    static final String RELEASE_BUNDLE_MANIFEST_ENDPOINT =
            "/contracts/release-bundle-manifest.sample.json";

    static final String ROLLBACK_APPROVAL_HANDOFF_VERSION = "java-rollback-approval-handoff.v1";

    static final String ROLLBACK_APPROVAL_HANDOFF_ENDPOINT =
            "/contracts/rollback-approval-handoff.sample.json";

    private static final String REAL_REPLAY_ENDPOINT = "/api/v1/failed-events/{id}/replay";

    private final Instant startedAt = Instant.now();

    private final FailedEventSummaryService failedEventSummaryService;

    private final OutboxRepository outboxRepository;

    private final OutboxPublisherProperties outboxPublisherProperties;

    private final OutboxRabbitMqProperties outboxRabbitMqProperties;

    private final IdempotencyStore idempotencyStore;

    private final Environment environment;

    public OpsEvidenceService(
            FailedEventSummaryService failedEventSummaryService,
            OutboxRepository outboxRepository,
            OutboxPublisherProperties outboxPublisherProperties,
            OutboxRabbitMqProperties outboxRabbitMqProperties,
            IdempotencyStore idempotencyStore,
            Environment environment
    ) {
        this.failedEventSummaryService = failedEventSummaryService;
        this.outboxRepository = outboxRepository;
        this.outboxPublisherProperties = outboxPublisherProperties;
        this.outboxRabbitMqProperties = outboxRabbitMqProperties;
        this.idempotencyStore = idempotencyStore;
        this.environment = environment;
    }

    @Transactional(readOnly = true)
    public OpsEvidenceResponse evidence() {
        Instant sampledAt = Instant.now();
        FailedEventSummaryResponse failedEventSummary = failedEventSummaryService.summary();
        long pendingOutboxEvents = outboxRepository.countByPublishedAtIsNull();
        List<String> outboxBlockers = outboxBlockers();
        List<String> executionBlockers = executionBlockers(failedEventSummary);
        List<String> blockers = blockers(outboxBlockers, executionBlockers);

        return new OpsEvidenceResponse(
                sampledAt,
                EVIDENCE_VERSION,
                service(sampledAt),
                healthProbe(false),
                true,
                false,
                readOnlyWindow(true),
                orderIdempotency(),
                releaseVerification(),
                deploymentRollback(),
                releaseBundle(),
                rollbackApprovalHandoff(),
                failedEventReplay(failedEventSummary),
                outbox(pendingOutboxEvents, outboxBlockers),
                approvalExecution(executionBlockers),
                blockers,
                warnings(failedEventSummary, pendingOutboxEvents),
                evidenceEndpoints()
        );
    }

    private OpsEvidenceResponse.Service service(Instant sampledAt) {
        return new OpsEvidenceResponse.Service(
                environment.getProperty("spring.application.name", "advanced-order-platform"),
                environment.getProperty("info.app.version", "0.1.0-SNAPSHOT"),
                profiles(),
                startedAt,
                Math.max(Duration.between(startedAt, sampledAt).toSeconds(), 0)
        );
    }

    private OpsEvidenceResponse.HealthProbe healthProbe(boolean staticSampleOnly) {
        return new OpsEvidenceResponse.HealthProbe(
                "/actuator/health",
                "GET",
                "UP",
                "/api/v1/ops/evidence",
                List.of(
                        "/api/v1/ops/overview",
                        "/contracts/ops-read-only-evidence.sample.json",
                        "/contracts/order-idempotency-boundary.sample.json",
                        "/contracts/order-idempotency-store-abstraction.sample.json",
                        RELEASE_VERIFICATION_MANIFEST_ENDPOINT,
                        DEPLOYMENT_ROLLBACK_EVIDENCE_ENDPOINT,
                        RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                        ROLLBACK_APPROVAL_HANDOFF_ENDPOINT
                ),
                true,
                staticSampleOnly
        );
    }

    private OpsEvidenceResponse.ReadOnlyWindow readOnlyWindow(boolean readyForReadOnlyLiveProbe) {
        return new OpsEvidenceResponse.ReadOnlyWindow(
                "java-read-only-window.v1",
                true,
                false,
                true,
                false,
                readyForReadOnlyLiveProbe,
                false,
                List.of(
                        "GET /actuator/health",
                        "GET /api/v1/ops/overview",
                        "GET /api/v1/ops/evidence",
                        "GET /contracts/ops-read-only-evidence.sample.json",
                        "GET /contracts/order-idempotency-boundary.sample.json",
                        "GET /contracts/order-idempotency-store-abstraction.sample.json",
                        "GET " + RELEASE_VERIFICATION_MANIFEST_ENDPOINT,
                        "GET " + DEPLOYMENT_ROLLBACK_EVIDENCE_ENDPOINT,
                        "GET " + RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                        "GET " + ROLLBACK_APPROVAL_HANDOFF_ENDPOINT
                ),
                List.of(
                        "POST /api/v1/orders",
                        "POST /api/v1/failed-events/{id}/replay",
                        "RabbitMQ replay publish",
                        "Outbox mutation",
                        "Any non-GET Node upstream action"
                ),
                List.of(
                        "UPSTREAM_PROBES_ENABLED=true",
                        "UPSTREAM_ACTIONS_ENABLED=false"
                ),
                "Node real-read window must not call POST /api/v1/failed-events/{id}/replay"
        );
    }

    private OpsEvidenceResponse.OrderIdempotency orderIdempotency() {
        IdempotencyStoreDescriptor descriptor = idempotencyStore.descriptor();
        return new OpsEvidenceResponse.OrderIdempotency(
                "java-order-idempotency-boundary.v1",
                descriptor.abstractionVersion(),
                "/api/v1/orders",
                "POST",
                "Idempotency-Key",
                120,
                "order-create-request-sha256.v1",
                "customerId plus aggregated productId:quantity pairs sorted by productId",
                "HTTP 200 replay of the existing order without a second inventory reservation or outbox event",
                "HTTP 409 conflict before inventory reservation and before outbox mutation",
                "IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST",
                descriptor.activeStore(),
                descriptor.activeImplementation(),
                descriptor.activeMode(),
                descriptor.authoritativeStore() + " via " + descriptor.keyColumn()
                        + " and " + descriptor.fingerprintColumn(),
                List.of(
                        new OpsEvidenceResponse.IdempotencyStoreCandidate(
                                descriptor.activeStore(),
                                "ORDER_CREATE_IDEMPOTENCY_AUTHORITY",
                                true,
                                true,
                                descriptor.activeMode(),
                                "Default Java database-backed idempotency store"
                        ),
                        new OpsEvidenceResponse.IdempotencyStoreCandidate(
                                JpaIdempotencyStore.MINI_KV_CANDIDATE,
                                "TTL_TOKEN_CANDIDATE",
                                descriptor.miniKvAdapterEnabled(),
                                descriptor.miniKvConnected(),
                                descriptor.miniKvCandidateMode(),
                                descriptor.disabledCandidateReason()
                        )
                ),
                descriptor.miniKvConnected(),
                descriptor.externalTokenStoreConnected(),
                descriptor.changesPaymentOrInventoryTransaction()
        );
    }

    private OpsEvidenceResponse.ReleaseVerification releaseVerification() {
        return new OpsEvidenceResponse.ReleaseVerification(
                RELEASE_VERIFICATION_MANIFEST_VERSION,
                RELEASE_VERIFICATION_MANIFEST_ENDPOINT,
                "LOCAL_OPERATOR_EXECUTES_AND_ARCHIVES_RESULTS",
                List.of(
                        "focused-maven-tests",
                        "non-docker-regression-tests",
                        "maven-package",
                        "http-smoke",
                        "static-contract-json-validation"
                ),
                List.of(
                        "/contracts/ops-read-only-evidence.sample.json",
                        "/contracts/ops-evidence-field-guide.sample.json",
                        "/contracts/order-idempotency-boundary.sample.json",
                        "/contracts/order-idempotency-store-abstraction.sample.json",
                        RELEASE_VERIFICATION_MANIFEST_ENDPOINT,
                        DEPLOYMENT_ROLLBACK_EVIDENCE_ENDPOINT,
                        RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                        ROLLBACK_APPROVAL_HANDOFF_ENDPOINT
                ),
                false,
                false,
                false,
                false
        );
    }

    private OpsEvidenceResponse.DeploymentRollback deploymentRollback() {
        return new OpsEvidenceResponse.DeploymentRollback(
                DEPLOYMENT_ROLLBACK_EVIDENCE_VERSION,
                DEPLOYMENT_ROLLBACK_EVIDENCE_ENDPOINT,
                "READ_ONLY_BOUNDARY_SAMPLE",
                List.of(
                        "java-package",
                        "runtime-configuration",
                        "database-migrations",
                        "static-contracts"
                ),
                List.of(
                        "artifact-version-target",
                        "configuration-secret-source",
                        "database-migration-direction",
                        "rollback-approval-handoff"
                ),
                true,
                true,
                false,
                true,
                false,
                false,
                false
        );
    }

    private OpsEvidenceResponse.ReleaseBundle releaseBundle() {
        return new OpsEvidenceResponse.ReleaseBundle(
                RELEASE_BUNDLE_MANIFEST_VERSION,
                RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                "READ_ONLY_RELEASE_BUNDLE",
                "target/advanced-order-platform-0.1.0-SNAPSHOT.jar",
                staticContractEndpoints(),
                List.of(
                        "focused-maven-tests",
                        "non-docker-regression-tests",
                        "maven-package",
                        "http-smoke",
                        "static-contract-json-validation"
                ),
                true,
                false,
                false,
                false,
                false
        );
    }

    private OpsEvidenceResponse.RollbackApprovalHandoff rollbackApprovalHandoff() {
        return new OpsEvidenceResponse.RollbackApprovalHandoff(
                ROLLBACK_APPROVAL_HANDOFF_VERSION,
                ROLLBACK_APPROVAL_HANDOFF_ENDPOINT,
                "OPERATOR_CONFIRMATION_REQUIRED",
                List.of(
                        "artifact-version-target",
                        "runtime-config-profile",
                        "configuration-secret-source",
                        "database-migration-direction",
                        "release-bundle-manifest",
                        "deployment-rollback-evidence"
                ),
                List.of(
                        RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                        DEPLOYMENT_ROLLBACK_EVIDENCE_ENDPOINT,
                        RELEASE_VERIFICATION_MANIFEST_ENDPOINT
                ),
                true,
                false,
                false,
                false,
                false,
                false
        );
    }

    private List<String> staticContractEndpoints() {
        return List.of(
                "/contracts/ops-read-only-evidence.sample.json",
                "/contracts/ops-evidence-field-guide.sample.json",
                "/contracts/order-idempotency-boundary.sample.json",
                "/contracts/order-idempotency-store-abstraction.sample.json",
                RELEASE_VERIFICATION_MANIFEST_ENDPOINT,
                DEPLOYMENT_ROLLBACK_EVIDENCE_ENDPOINT,
                RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                ROLLBACK_APPROVAL_HANDOFF_ENDPOINT
        );
    }

    private List<String> profiles() {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length > 0) {
            return List.copyOf(Arrays.asList(activeProfiles));
        }
        return List.copyOf(Arrays.asList(environment.getDefaultProfiles()));
    }

    private OpsEvidenceResponse.FailedEventReplay failedEventReplay(FailedEventSummaryResponse summary) {
        return new OpsEvidenceResponse.FailedEventReplay(
                summary.totalFailedEvents(),
                summary.replayBacklog(),
                summary.pendingReplayApprovals(),
                summary.approvedReplayApprovals(),
                summary.rejectedReplayApprovals(),
                summary.latestFailedAt(),
                summary.latestApprovalAt(),
                REAL_REPLAY_ENDPOINT,
                false
        );
    }

    private OpsEvidenceResponse.Outbox outbox(long pendingOutboxEvents, List<String> outboxBlockers) {
        return new OpsEvidenceResponse.Outbox(
                pendingOutboxEvents,
                outboxPublisherProperties.isEnabled(),
                outboxRabbitMqProperties.isEnabled(),
                outboxRabbitMqProperties.getExchange(),
                outboxRabbitMqProperties.getQueue(),
                outboxRabbitMqProperties.getDeadLetterQueue(),
                outboxBlockers
        );
    }

    private OpsEvidenceResponse.ApprovalExecution approvalExecution(List<String> executionBlockers) {
        return new OpsEvidenceResponse.ApprovalExecution(
                "APPROVED",
                "contractDigest must match latest approval-status/readiness evidence before POST /replay",
                true,
                true,
                executionBlockers,
                List.of(
                        "GET /api/v1/failed-events/summary",
                        "GET /api/v1/failed-events/{id}/replay-readiness",
                        "GET /api/v1/failed-events/{id}/replay-execution-contract"
                )
        );
    }

    private List<String> outboxBlockers() {
        List<String> blockers = new ArrayList<>();
        if (!outboxPublisherProperties.isEnabled()) {
            blockers.add("OUTBOX_PUBLISHER_DISABLED");
        }
        if (!outboxRabbitMqProperties.isEnabled()) {
            blockers.add("RABBITMQ_OUTBOX_DISABLED");
        }
        return List.copyOf(blockers);
    }

    private List<String> executionBlockers(FailedEventSummaryResponse summary) {
        List<String> blockers = new ArrayList<>();
        blockers.add("READ_ONLY_EVIDENCE_ENDPOINT");
        if (summary.pendingReplayApprovals() > 0) {
            blockers.add("REPLAY_APPROVAL_PENDING");
        }
        if (summary.rejectedReplayApprovals() > 0) {
            blockers.add("REPLAY_APPROVAL_REJECTED");
        }
        if (summary.replayBacklog() > 0) {
            blockers.add("REPLAY_BACKLOG_PRESENT");
        }
        return List.copyOf(blockers);
    }

    private List<String> blockers(List<String> outboxBlockers, List<String> executionBlockers) {
        List<String> blockers = new ArrayList<>();
        blockers.addAll(executionBlockers);
        blockers.addAll(outboxBlockers);
        return List.copyOf(blockers);
    }

    private List<String> warnings(FailedEventSummaryResponse summary, long pendingOutboxEvents) {
        List<String> warnings = new ArrayList<>();
        if (pendingOutboxEvents > 0) {
            warnings.add("OUTBOX_PENDING_EVENTS");
        }
        if (summary.approvedReplayApprovals() > 0) {
            warnings.add("APPROVED_REPLAY_REQUIRES_DIGEST_CHECK");
        }
        return List.copyOf(warnings);
    }

    private List<String> evidenceEndpoints() {
        return List.of(
                "/api/v1/ops/overview",
                "/api/v1/ops/evidence",
                "/contracts/ops-read-only-evidence.sample.json",
                "/contracts/ops-evidence-field-guide.sample.json",
                "/contracts/order-idempotency-boundary.sample.json",
                "/contracts/order-idempotency-store-abstraction.sample.json",
                RELEASE_VERIFICATION_MANIFEST_ENDPOINT,
                DEPLOYMENT_ROLLBACK_EVIDENCE_ENDPOINT,
                RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                ROLLBACK_APPROVAL_HANDOFF_ENDPOINT,
                "/api/v1/failed-events/summary",
                "/api/v1/failed-events/{id}/approval-status",
                "/api/v1/failed-events/{id}/replay-readiness",
                "/api/v1/failed-events/{id}/replay-execution-contract",
                "/api/v1/failed-events/replay-evidence-index",
                "/contracts/failed-event-replay-execution-contract-approved.sample.json",
                "/contracts/failed-event-replay-execution-contract-blocked.sample.json"
        );
    }
}
