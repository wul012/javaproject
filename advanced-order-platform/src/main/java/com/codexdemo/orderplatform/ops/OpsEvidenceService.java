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

    static final String RELEASE_HANDOFF_CHECKLIST_FIXTURE_VERSION =
            "java-release-handoff-checklist-fixture.v1";

    static final String RELEASE_HANDOFF_CHECKLIST_FIXTURE_ENDPOINT =
            "/contracts/release-handoff-checklist.fixture.json";

    static final String RELEASE_AUDIT_RETENTION_FIXTURE_VERSION =
            "java-release-audit-retention-fixture.v1";

    static final String RELEASE_AUDIT_RETENTION_FIXTURE_ENDPOINT =
            "/contracts/release-audit-retention.fixture.json";

    static final String RELEASE_OPERATOR_SIGNOFF_FIXTURE_VERSION =
            "java-release-operator-signoff-fixture.v1";

    static final String RELEASE_OPERATOR_SIGNOFF_FIXTURE_ENDPOINT =
            "/contracts/release-operator-signoff.fixture.json";

    static final String ROLLBACK_APPROVAL_HANDOFF_VERSION = "java-rollback-approval-handoff.v1";

    static final String ROLLBACK_APPROVAL_HANDOFF_ENDPOINT =
            "/contracts/rollback-approval-handoff.sample.json";

    static final String ROLLBACK_APPROVAL_RECORD_FIXTURE_VERSION =
            "java-rollback-approval-record-fixture.v1";

    static final String ROLLBACK_APPROVAL_RECORD_FIXTURE_ENDPOINT =
            "/contracts/rollback-approval-record.fixture.json";

    static final String ROLLBACK_SQL_REVIEW_GATE_VERSION = "java-rollback-sql-review-gate.v1";

    static final String ROLLBACK_SQL_REVIEW_GATE_ENDPOINT =
            "/contracts/rollback-sql-review-gate.sample.json";

    static final String PRODUCTION_SECRET_SOURCE_CONTRACT_VERSION = "java-production-secret-source-contract.v1";

    static final String PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT =
            "/contracts/production-secret-source-contract.sample.json";

    static final String PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_VERSION =
            "java-production-deployment-runbook-contract.v1";

    static final String PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT =
            "/contracts/production-deployment-runbook-contract.sample.json";

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
                releaseHandoffChecklistFixture(),
                releaseAuditRetentionFixture(),
                releaseOperatorSignoffFixture(),
                rollbackApprovalHandoff(),
                rollbackApprovalRecordFixture(),
                rollbackSqlReviewGate(),
                productionSecretSourceContract(),
                productionDeploymentRunbookContract(),
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
        List<String> additionalProbeEndpoints = new ArrayList<>();
        additionalProbeEndpoints.add("/api/v1/ops/overview");
        additionalProbeEndpoints.addAll(staticContractEndpoints(false));

        return new OpsEvidenceResponse.HealthProbe(
                "/actuator/health",
                "GET",
                "UP",
                "/api/v1/ops/evidence",
                List.copyOf(additionalProbeEndpoints),
                true,
                staticSampleOnly
        );
    }

    private OpsEvidenceResponse.ReadOnlyWindow readOnlyWindow(boolean readyForReadOnlyLiveProbe) {
        List<String> allowedProbeEndpoints = new ArrayList<>();
        allowedProbeEndpoints.add("GET /actuator/health");
        allowedProbeEndpoints.add("GET /api/v1/ops/overview");
        allowedProbeEndpoints.add("GET /api/v1/ops/evidence");
        allowedProbeEndpoints.addAll(staticContractProbeEndpoints(false));

        return new OpsEvidenceResponse.ReadOnlyWindow(
                "java-read-only-window.v1",
                true,
                false,
                true,
                false,
                readyForReadOnlyLiveProbe,
                false,
                List.copyOf(allowedProbeEndpoints),
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
                staticContractEndpoints(true),
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
                        "deployment-window-owner",
                        "rollback-approver",
                        "configuration-secret-source",
                        "production-secret-source-contract",
                        "production-deployment-runbook-contract",
                        "database-migration-direction",
                        "release-handoff-checklist-fixture",
                        "release-audit-retention-fixture",
                        "release-operator-signoff-fixture",
                        "rollback-approval-handoff",
                        "rollback-approval-record-fixture",
                        "rollback-sql-review-gate"
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
                staticContractEndpoints(true),
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

    private OpsEvidenceResponse.ReleaseHandoffChecklistFixture releaseHandoffChecklistFixture() {
        return new OpsEvidenceResponse.ReleaseHandoffChecklistFixture(
                RELEASE_HANDOFF_CHECKLIST_FIXTURE_VERSION,
                RELEASE_HANDOFF_CHECKLIST_FIXTURE_ENDPOINT,
                "READ_ONLY_RELEASE_HANDOFF_CHECKLIST_FIXTURE",
                "release-operator-placeholder",
                "rollback-approver-placeholder",
                "release-tag-or-artifact-version-placeholder",
                List.of(
                        "forward-only",
                        "rollback-script-reviewed",
                        "no-database-change"
                ),
                "no-database-change",
                PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT,
                List.of(
                        "release-operator",
                        "rollback-approver",
                        "artifact-target",
                        "database-migration-direction",
                        "secret-source-confirmation",
                        "deployment-runbook-contract",
                        "rollback-approval-record-fixture",
                        "release-audit-retention-fixture",
                        "release-operator-signoff-fixture",
                        "no-secret-value-boundary"
                ),
                releaseHandoffChecklistArtifacts(),
                List.of(
                        "checklist-fixture-stores-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-handoff-checklist",
                        "node-may-render-release-handoff-review-only"
                ),
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false
        );
    }

    private OpsEvidenceResponse.ReleaseAuditRetentionFixture releaseAuditRetentionFixture() {
        return new OpsEvidenceResponse.ReleaseAuditRetentionFixture(
                RELEASE_AUDIT_RETENTION_FIXTURE_VERSION,
                RELEASE_AUDIT_RETENTION_FIXTURE_ENDPOINT,
                "READ_ONLY_RELEASE_AUDIT_RETENTION_FIXTURE",
                "release-retention-record-placeholder",
                "release-operator-placeholder",
                "release-tag-or-artifact-version-placeholder",
                180,
                releaseAuditRetentionEndpoints(),
                List.of(
                        "retention-id",
                        "release-operator",
                        "artifact-target",
                        "retention-days",
                        "evidence-endpoints",
                        "release-operator-signoff-fixture",
                        "audit-export-location-placeholder",
                        "no-secret-value-boundary"
                ),
                releaseAuditRetentionArtifacts(),
                List.of(
                        "retention-fixture-stores-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-retention-record",
                        "node-may-render-retention-gate-only"
                ),
                true,
                false,
                false,
                true,
                false,
                false,
                false,
                false,
                false
        );
    }

    private OpsEvidenceResponse.ReleaseOperatorSignoffFixture releaseOperatorSignoffFixture() {
        return new OpsEvidenceResponse.ReleaseOperatorSignoffFixture(
                RELEASE_OPERATOR_SIGNOFF_FIXTURE_VERSION,
                RELEASE_OPERATOR_SIGNOFF_FIXTURE_ENDPOINT,
                "READ_ONLY_RELEASE_OPERATOR_SIGNOFF_FIXTURE",
                "release-operator-placeholder",
                "rollback-approver-placeholder",
                "release-window-placeholder",
                "release-tag-or-artifact-version-placeholder",
                "operator-signoff-placeholder",
                List.of(
                        "release-operator",
                        "rollback-approver",
                        "release-window",
                        "artifact-target",
                        "operator-signoff-placeholder",
                        "release-audit-retention-fixture",
                        "no-secret-value-boundary"
                ),
                releaseOperatorSignoffArtifacts(),
                List.of(
                        "signoff-fixture-stores-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-signoff",
                        "node-may-render-approval-prerequisite-gate-only"
                ),
                true,
                false,
                false,
                false,
                false,
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
                        "deployment-window-owner",
                        "rollback-approver",
                        "runtime-config-profile",
                        "configuration-secret-source",
                        "production-secret-source-contract",
                        "production-deployment-runbook-contract",
                        "database-migration-direction",
                        "release-handoff-checklist-fixture",
                        "release-audit-retention-fixture",
                        "release-operator-signoff-fixture",
                        "rollback-approval-record-fixture",
                        "rollback-sql-review-gate",
                        "release-bundle-manifest",
                        "deployment-rollback-evidence"
                ),
                List.of(
                        RELEASE_HANDOFF_CHECKLIST_FIXTURE_ENDPOINT,
                        RELEASE_AUDIT_RETENTION_FIXTURE_ENDPOINT,
                        RELEASE_OPERATOR_SIGNOFF_FIXTURE_ENDPOINT,
                        RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                        DEPLOYMENT_ROLLBACK_EVIDENCE_ENDPOINT,
                        ROLLBACK_APPROVAL_RECORD_FIXTURE_ENDPOINT,
                        ROLLBACK_SQL_REVIEW_GATE_ENDPOINT,
                        PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT,
                        PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT,
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

    private OpsEvidenceResponse.RollbackApprovalRecordFixture rollbackApprovalRecordFixture() {
        return new OpsEvidenceResponse.RollbackApprovalRecordFixture(
                ROLLBACK_APPROVAL_RECORD_FIXTURE_VERSION,
                ROLLBACK_APPROVAL_RECORD_FIXTURE_ENDPOINT,
                "READ_ONLY_APPROVAL_RECORD_FIXTURE",
                "rollback-reviewer-placeholder",
                "approval-timestamp-placeholder",
                "release-tag-or-artifact-version-placeholder",
                List.of(
                        "forward-only",
                        "rollback-script-reviewed",
                        "no-database-change"
                ),
                "no-database-change",
                List.of(
                        "reviewer",
                        "approval-timestamp-placeholder",
                        "rollback-target",
                        "database-migration-direction",
                        "rollback-sql-review-gate",
                        "no-secret-value-boundary"
                ),
                List.of(
                        ROLLBACK_APPROVAL_HANDOFF_ENDPOINT,
                        ROLLBACK_SQL_REVIEW_GATE_ENDPOINT,
                        PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT,
                        PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT,
                        RELEASE_BUNDLE_MANIFEST_ENDPOINT
                ),
                List.of(
                        "record-fixture-stores-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-approval-record",
                        "node-may-render-release-window-packet-only"
                ),
                true,
                false,
                false,
                false,
                false,
                false,
                false
        );
    }

    private OpsEvidenceResponse.RollbackSqlReviewGate rollbackSqlReviewGate() {
        return new OpsEvidenceResponse.RollbackSqlReviewGate(
                ROLLBACK_SQL_REVIEW_GATE_VERSION,
                ROLLBACK_SQL_REVIEW_GATE_ENDPOINT,
                "READ_ONLY_SQL_REVIEW_GATE",
                "database-release-owner",
                List.of(
                        "rollback-sql-review-owner",
                        "migration-direction",
                        "operator-approval-placeholder",
                        "rollback-sql-artifact-reference",
                        "production-database-access-boundary"
                ),
                List.of(
                        "forward-only",
                        "rollback-script-reviewed",
                        "no-database-change"
                ),
                "operator-approval-required-before-any-sql-execution",
                true,
                false,
                false,
                false,
                false
        );
    }

    private List<String> staticContractEndpoints(boolean includeFieldGuide) {
        List<String> endpoints = new ArrayList<>();
        endpoints.add("/contracts/ops-read-only-evidence.sample.json");
        if (includeFieldGuide) {
            endpoints.add("/contracts/ops-evidence-field-guide.sample.json");
        }
        endpoints.addAll(List.of(
                "/contracts/order-idempotency-boundary.sample.json",
                "/contracts/order-idempotency-store-abstraction.sample.json",
                RELEASE_VERIFICATION_MANIFEST_ENDPOINT,
                DEPLOYMENT_ROLLBACK_EVIDENCE_ENDPOINT,
                RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                RELEASE_HANDOFF_CHECKLIST_FIXTURE_ENDPOINT,
                RELEASE_AUDIT_RETENTION_FIXTURE_ENDPOINT,
                RELEASE_OPERATOR_SIGNOFF_FIXTURE_ENDPOINT,
                ROLLBACK_APPROVAL_HANDOFF_ENDPOINT,
                ROLLBACK_APPROVAL_RECORD_FIXTURE_ENDPOINT,
                ROLLBACK_SQL_REVIEW_GATE_ENDPOINT,
                PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT,
                PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT
        ));
        return List.copyOf(endpoints);
    }

    private List<String> staticContractProbeEndpoints(boolean includeFieldGuide) {
        return staticContractEndpoints(includeFieldGuide).stream()
                .map(endpoint -> "GET " + endpoint)
                .toList();
    }

    private List<String> releaseHandoffChecklistArtifacts() {
        return List.of(
                RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                RELEASE_VERIFICATION_MANIFEST_ENDPOINT,
                RELEASE_AUDIT_RETENTION_FIXTURE_ENDPOINT,
                RELEASE_OPERATOR_SIGNOFF_FIXTURE_ENDPOINT,
                PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT,
                PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT,
                ROLLBACK_APPROVAL_RECORD_FIXTURE_ENDPOINT,
                ROLLBACK_SQL_REVIEW_GATE_ENDPOINT
        );
    }

    private List<String> releaseAuditRetentionEndpoints() {
        return List.of(
                "/api/v1/ops/evidence",
                "/api/v1/failed-events/replay-evidence-index",
                RELEASE_VERIFICATION_MANIFEST_ENDPOINT,
                RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                RELEASE_HANDOFF_CHECKLIST_FIXTURE_ENDPOINT,
                RELEASE_OPERATOR_SIGNOFF_FIXTURE_ENDPOINT,
                PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT
        );
    }

    private List<String> releaseAuditRetentionArtifacts() {
        return List.of(
                RELEASE_VERIFICATION_MANIFEST_ENDPOINT,
                RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                RELEASE_HANDOFF_CHECKLIST_FIXTURE_ENDPOINT,
                RELEASE_OPERATOR_SIGNOFF_FIXTURE_ENDPOINT,
                PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT,
                PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT
        );
    }

    private List<String> releaseOperatorSignoffArtifacts() {
        return List.of(
                RELEASE_HANDOFF_CHECKLIST_FIXTURE_ENDPOINT,
                RELEASE_AUDIT_RETENTION_FIXTURE_ENDPOINT,
                RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                RELEASE_VERIFICATION_MANIFEST_ENDPOINT,
                PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT,
                ROLLBACK_APPROVAL_HANDOFF_ENDPOINT
        );
    }

    private OpsEvidenceResponse.ProductionSecretSourceContract productionSecretSourceContract() {
        return new OpsEvidenceResponse.ProductionSecretSourceContract(
                PRODUCTION_SECRET_SOURCE_CONTRACT_VERSION,
                PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT,
                "READ_ONLY_SECRET_SOURCE_CONTRACT",
                List.of(
                        "external-secret-manager",
                        "environment-injected-secret",
                        "platform-managed-secret"
                ),
                "external-secret-manager",
                "platform-security-owner",
                "security-operations-owner",
                "quarterly-or-before-production-cutover",
                List.of(
                        "secret-manager-or-source-type",
                        "secret-manager-owner",
                        "rotation-owner",
                        "review-cadence",
                        "secret-value-access-boundary"
                ),
                List.of(
                        "contract-records-source-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-static-contracts",
                        "node-may-render-checklist-only"
                ),
                true,
                false,
                false,
                false,
                false
        );
    }

    private OpsEvidenceResponse.ProductionDeploymentRunbookContract productionDeploymentRunbookContract() {
        return new OpsEvidenceResponse.ProductionDeploymentRunbookContract(
                PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_VERSION,
                PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT,
                "READ_ONLY_DEPLOYMENT_RUNBOOK_CONTRACT",
                "release-window-owner",
                "rollback-approval-owner",
                List.of(
                        "forward-only",
                        "rollback-script-reviewed",
                        "no-database-change"
                ),
                "no-database-change",
                PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT,
                List.of(
                        "deployment-window-owner",
                        "rollback-approver",
                        "database-migration-direction",
                        "secret-source-confirmation",
                        "rollback-sql-review-gate",
                        "operator-approval-placeholder",
                        "release-audit-retention-fixture",
                        "release-operator-signoff-fixture"
                ),
                List.of(
                        RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                        DEPLOYMENT_ROLLBACK_EVIDENCE_ENDPOINT,
                        RELEASE_HANDOFF_CHECKLIST_FIXTURE_ENDPOINT,
                        RELEASE_AUDIT_RETENTION_FIXTURE_ENDPOINT,
                        RELEASE_OPERATOR_SIGNOFF_FIXTURE_ENDPOINT,
                        ROLLBACK_APPROVAL_HANDOFF_ENDPOINT,
                        ROLLBACK_APPROVAL_RECORD_FIXTURE_ENDPOINT,
                        ROLLBACK_SQL_REVIEW_GATE_ENDPOINT,
                        PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT
                ),
                true,
                false,
                false,
                false,
                false,
                false,
                false
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
        List<String> endpoints = new ArrayList<>();
        endpoints.add("/api/v1/ops/overview");
        endpoints.add("/api/v1/ops/evidence");
        endpoints.addAll(staticContractEndpoints(true));
        endpoints.addAll(List.of(
                "/api/v1/failed-events/summary",
                "/api/v1/failed-events/{id}/approval-status",
                "/api/v1/failed-events/{id}/replay-readiness",
                "/api/v1/failed-events/{id}/replay-execution-contract",
                "/api/v1/failed-events/replay-evidence-index",
                "/contracts/failed-event-replay-execution-contract-approved.sample.json",
                "/contracts/failed-event-replay-execution-contract-blocked.sample.json"
        ));
        return List.copyOf(endpoints);
    }
}
