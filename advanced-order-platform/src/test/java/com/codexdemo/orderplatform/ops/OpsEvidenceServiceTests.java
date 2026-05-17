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
                        "/api/v1/ops/release-approval-rehearsal",
                        "/contracts/ops-read-only-evidence.sample.json",
                        "/contracts/order-idempotency-boundary.sample.json",
                        "/contracts/order-idempotency-store-abstraction.sample.json",
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/deployment-rollback-evidence.sample.json",
                        "/contracts/release-bundle-manifest.sample.json",
                        "/contracts/release-handoff-checklist.fixture.json",
                        "/contracts/release-audit-retention.fixture.json",
                        "/contracts/release-operator-signoff.fixture.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/rollback-approval-handoff.sample.json",
                        "/contracts/rollback-approval-record.fixture.json",
                        "/contracts/rollback-sql-review-gate.sample.json",
                        "/contracts/production-secret-source-contract.sample.json",
                        "/contracts/production-deployment-runbook-contract.sample.json"
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
                        "GET /api/v1/ops/release-approval-rehearsal",
                        "GET /contracts/ops-read-only-evidence.sample.json",
                        "GET /contracts/order-idempotency-boundary.sample.json",
                        "GET /contracts/order-idempotency-store-abstraction.sample.json",
                        "GET /contracts/release-verification-manifest.sample.json",
                        "GET /contracts/deployment-rollback-evidence.sample.json",
                        "GET /contracts/release-bundle-manifest.sample.json",
                        "GET /contracts/release-handoff-checklist.fixture.json",
                        "GET /contracts/release-audit-retention.fixture.json",
                        "GET /contracts/release-operator-signoff.fixture.json",
                        "GET /contracts/rollback-approver-evidence.fixture.json",
                        "GET /contracts/rollback-approval-handoff.sample.json",
                        "GET /contracts/rollback-approval-record.fixture.json",
                        "GET /contracts/rollback-sql-review-gate.sample.json",
                        "GET /contracts/production-secret-source-contract.sample.json",
                        "GET /contracts/production-deployment-runbook-contract.sample.json"
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
                        "deployment-window-owner",
                        "rollback-approver",
                        "configuration-secret-source",
                        "production-secret-source-contract",
                        "production-deployment-runbook-contract",
                        "database-migration-direction",
                        "release-handoff-checklist-fixture",
                        "release-audit-retention-fixture",
                        "release-operator-signoff-fixture",
                        "rollback-approver-evidence-fixture",
                        "rollback-approval-handoff",
                        "rollback-approval-record-fixture",
                        "rollback-sql-review-gate"
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
                        "/contracts/release-bundle-manifest.sample.json",
                        "/contracts/release-handoff-checklist.fixture.json",
                        "/contracts/release-audit-retention.fixture.json",
                        "/contracts/release-operator-signoff.fixture.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/rollback-approval-handoff.sample.json",
                        "/contracts/rollback-approval-record.fixture.json",
                        "/contracts/rollback-sql-review-gate.sample.json",
                        "/contracts/production-secret-source-contract.sample.json",
                        "/contracts/production-deployment-runbook-contract.sample.json"
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
        assertThat(evidence.releaseHandoffChecklistFixture().fixtureVersion())
                .isEqualTo("java-release-handoff-checklist-fixture.v1");
        assertThat(evidence.releaseHandoffChecklistFixture().fixtureEndpoint())
                .isEqualTo("/contracts/release-handoff-checklist.fixture.json");
        assertThat(evidence.releaseHandoffChecklistFixture().fixtureMode())
                .isEqualTo("READ_ONLY_RELEASE_HANDOFF_CHECKLIST_FIXTURE");
        assertThat(evidence.releaseHandoffChecklistFixture().releaseOperator())
                .isEqualTo("release-operator-placeholder");
        assertThat(evidence.releaseHandoffChecklistFixture().rollbackApprover())
                .isEqualTo("rollback-approver-placeholder");
        assertThat(evidence.releaseHandoffChecklistFixture().artifactTarget())
                .isEqualTo("release-tag-or-artifact-version-placeholder");
        assertThat(evidence.releaseHandoffChecklistFixture().migrationDirectionOptions())
                .containsExactly(
                        "forward-only",
                        "rollback-script-reviewed",
                        "no-database-change"
                );
        assertThat(evidence.releaseHandoffChecklistFixture().selectedMigrationDirection())
                .isEqualTo("no-database-change");
        assertThat(evidence.releaseHandoffChecklistFixture().secretSourceConfirmation())
                .isEqualTo("/contracts/production-secret-source-contract.sample.json");
        assertThat(evidence.releaseHandoffChecklistFixture().requiredChecklistFields())
                .containsExactly(
                        "release-operator",
                        "rollback-approver",
                        "artifact-target",
                        "database-migration-direction",
                        "secret-source-confirmation",
                        "deployment-runbook-contract",
                        "rollback-approval-record-fixture",
                        "release-audit-retention-fixture",
                        "release-operator-signoff-fixture",
                        "rollback-approver-evidence-fixture",
                        "no-secret-value-boundary"
                );
        assertThat(evidence.releaseHandoffChecklistFixture().checklistArtifacts())
                .containsExactly(
                        "/contracts/release-bundle-manifest.sample.json",
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/release-audit-retention.fixture.json",
                        "/contracts/release-operator-signoff.fixture.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/production-deployment-runbook-contract.sample.json",
                        "/contracts/production-secret-source-contract.sample.json",
                        "/contracts/rollback-approval-record.fixture.json",
                        "/contracts/rollback-sql-review-gate.sample.json"
                );
        assertThat(evidence.releaseHandoffChecklistFixture().noSecretValueBoundaries())
                .containsExactly(
                        "checklist-fixture-stores-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-handoff-checklist",
                        "node-may-render-release-handoff-review-only"
                );
        assertThat(evidence.releaseHandoffChecklistFixture().nodeMayConsume()).isTrue();
        assertThat(evidence.releaseHandoffChecklistFixture().nodeMayTriggerDeployment()).isFalse();
        assertThat(evidence.releaseHandoffChecklistFixture().nodeMayTriggerRollback()).isFalse();
        assertThat(evidence.releaseHandoffChecklistFixture().deploymentExecutionAllowed()).isFalse();
        assertThat(evidence.releaseHandoffChecklistFixture().rollbackSqlExecutionAllowed()).isFalse();
        assertThat(evidence.releaseHandoffChecklistFixture().requiresProductionDatabase()).isFalse();
        assertThat(evidence.releaseHandoffChecklistFixture().requiresProductionSecrets()).isFalse();
        assertThat(evidence.releaseHandoffChecklistFixture().changesOrderTransactionSemantics()).isFalse();
        assertThat(evidence.releaseAuditRetentionFixture().fixtureVersion())
                .isEqualTo("java-release-audit-retention-fixture.v1");
        assertThat(evidence.releaseAuditRetentionFixture().fixtureEndpoint())
                .isEqualTo("/contracts/release-audit-retention.fixture.json");
        assertThat(evidence.releaseAuditRetentionFixture().fixtureMode())
                .isEqualTo("READ_ONLY_RELEASE_AUDIT_RETENTION_FIXTURE");
        assertThat(evidence.releaseAuditRetentionFixture().retentionId())
                .isEqualTo("release-retention-record-placeholder");
        assertThat(evidence.releaseAuditRetentionFixture().releaseOperator())
                .isEqualTo("release-operator-placeholder");
        assertThat(evidence.releaseAuditRetentionFixture().artifactTarget())
                .isEqualTo("release-tag-or-artifact-version-placeholder");
        assertThat(evidence.releaseAuditRetentionFixture().retentionDays()).isEqualTo(180);
        assertThat(evidence.releaseAuditRetentionFixture().evidenceEndpoints())
                .containsExactly(
                        "/api/v1/ops/evidence",
                        "/api/v1/ops/release-approval-rehearsal",
                        "/api/v1/failed-events/replay-evidence-index",
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/release-bundle-manifest.sample.json",
                        "/contracts/release-handoff-checklist.fixture.json",
                        "/contracts/release-operator-signoff.fixture.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/production-deployment-runbook-contract.sample.json"
                );
        assertThat(evidence.releaseAuditRetentionFixture().auditExportFields())
                .containsExactly(
                        "retention-id",
                        "release-operator",
                        "artifact-target",
                        "retention-days",
                        "evidence-endpoints",
                        "release-operator-signoff-fixture",
                        "rollback-approver-evidence-fixture",
                        "audit-export-location-placeholder",
                        "no-secret-value-boundary"
                );
        assertThat(evidence.releaseAuditRetentionFixture().retainedArtifacts())
                .containsExactly(
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/release-bundle-manifest.sample.json",
                        "/contracts/release-handoff-checklist.fixture.json",
                        "/contracts/release-operator-signoff.fixture.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/production-deployment-runbook-contract.sample.json",
                        "/contracts/production-secret-source-contract.sample.json"
                );
        assertThat(evidence.releaseAuditRetentionFixture().noSecretValueBoundaries())
                .containsExactly(
                        "retention-fixture-stores-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-retention-record",
                        "node-may-render-retention-gate-only"
                );
        assertThat(evidence.releaseAuditRetentionFixture().nodeMayConsume()).isTrue();
        assertThat(evidence.releaseAuditRetentionFixture().nodeMayTriggerDeployment()).isFalse();
        assertThat(evidence.releaseAuditRetentionFixture().nodeMayTriggerRollback()).isFalse();
        assertThat(evidence.releaseAuditRetentionFixture().auditExportReadOnly()).isTrue();
        assertThat(evidence.releaseAuditRetentionFixture().deploymentExecutionAllowed()).isFalse();
        assertThat(evidence.releaseAuditRetentionFixture().rollbackSqlExecutionAllowed()).isFalse();
        assertThat(evidence.releaseAuditRetentionFixture().requiresProductionDatabase()).isFalse();
        assertThat(evidence.releaseAuditRetentionFixture().requiresProductionSecrets()).isFalse();
        assertThat(evidence.releaseAuditRetentionFixture().changesOrderTransactionSemantics()).isFalse();
        assertThat(evidence.releaseOperatorSignoffFixture().fixtureVersion())
                .isEqualTo("java-release-operator-signoff-fixture.v1");
        assertThat(evidence.releaseOperatorSignoffFixture().fixtureEndpoint())
                .isEqualTo("/contracts/release-operator-signoff.fixture.json");
        assertThat(evidence.releaseOperatorSignoffFixture().fixtureMode())
                .isEqualTo("READ_ONLY_RELEASE_OPERATOR_SIGNOFF_FIXTURE");
        assertThat(evidence.releaseOperatorSignoffFixture().releaseOperator())
                .isEqualTo("release-operator-placeholder");
        assertThat(evidence.releaseOperatorSignoffFixture().rollbackApprover())
                .isEqualTo("rollback-approver-placeholder");
        assertThat(evidence.releaseOperatorSignoffFixture().releaseWindow())
                .isEqualTo("release-window-placeholder");
        assertThat(evidence.releaseOperatorSignoffFixture().artifactTarget())
                .isEqualTo("release-tag-or-artifact-version-placeholder");
        assertThat(evidence.releaseOperatorSignoffFixture().operatorSignoffPlaceholder())
                .isEqualTo("operator-signoff-placeholder");
        assertThat(evidence.releaseOperatorSignoffFixture().requiredSignoffFields())
                .containsExactly(
                        "release-operator",
                        "rollback-approver",
                        "release-window",
                        "artifact-target",
                        "operator-signoff-placeholder",
                        "release-audit-retention-fixture",
                        "rollback-approver-evidence-fixture",
                        "no-secret-value-boundary"
                );
        assertThat(evidence.releaseOperatorSignoffFixture().signoffArtifacts())
                .containsExactly(
                        "/contracts/release-handoff-checklist.fixture.json",
                        "/contracts/release-audit-retention.fixture.json",
                        "/contracts/release-bundle-manifest.sample.json",
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/production-deployment-runbook-contract.sample.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/rollback-approval-handoff.sample.json"
                );
        assertThat(evidence.releaseOperatorSignoffFixture().noSecretValueBoundaries())
                .containsExactly(
                        "signoff-fixture-stores-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-signoff",
                        "node-may-render-approval-prerequisite-gate-only"
                );
        assertThat(evidence.releaseOperatorSignoffFixture().nodeMayConsume()).isTrue();
        assertThat(evidence.releaseOperatorSignoffFixture().nodeMayCreateApprovalDecision()).isFalse();
        assertThat(evidence.releaseOperatorSignoffFixture().nodeMayTriggerDeployment()).isFalse();
        assertThat(evidence.releaseOperatorSignoffFixture().nodeMayTriggerRollback()).isFalse();
        assertThat(evidence.releaseOperatorSignoffFixture().deploymentExecutionAllowed()).isFalse();
        assertThat(evidence.releaseOperatorSignoffFixture().rollbackSqlExecutionAllowed()).isFalse();
        assertThat(evidence.releaseOperatorSignoffFixture().requiresProductionDatabase()).isFalse();
        assertThat(evidence.releaseOperatorSignoffFixture().requiresProductionSecrets()).isFalse();
        assertThat(evidence.releaseOperatorSignoffFixture().changesOrderTransactionSemantics()).isFalse();
        assertThat(evidence.rollbackApproverEvidenceFixture().fixtureVersion())
                .isEqualTo("java-rollback-approver-evidence-fixture.v1");
        assertThat(evidence.rollbackApproverEvidenceFixture().fixtureEndpoint())
                .isEqualTo("/contracts/rollback-approver-evidence.fixture.json");
        assertThat(evidence.rollbackApproverEvidenceFixture().fixtureMode())
                .isEqualTo("READ_ONLY_ROLLBACK_APPROVER_EVIDENCE_FIXTURE");
        assertThat(evidence.rollbackApproverEvidenceFixture().rollbackApprover())
                .isEqualTo("rollback-approver-placeholder");
        assertThat(evidence.rollbackApproverEvidenceFixture().migrationDirectionOptions())
                .containsExactly(
                        "forward-only",
                        "rollback-script-reviewed",
                        "no-database-change"
                );
        assertThat(evidence.rollbackApproverEvidenceFixture().selectedMigrationDirection())
                .isEqualTo("no-database-change");
        assertThat(evidence.rollbackApproverEvidenceFixture().rollbackSqlArtifactReference())
                .isEqualTo("rollback-sql-artifact-reference-placeholder");
        assertThat(evidence.rollbackApproverEvidenceFixture().productionDatabaseBoundary())
                .isEqualTo("production-database-connection-outside-this-fixture");
        assertThat(evidence.rollbackApproverEvidenceFixture().requiredEvidenceFields())
                .containsExactly(
                        "rollback-approver",
                        "database-migration-direction",
                        "rollback-sql-artifact-reference",
                        "production-database-access-boundary",
                        "rollback-sql-review-gate",
                        "no-secret-value-boundary"
                );
        assertThat(evidence.rollbackApproverEvidenceFixture().evidenceArtifacts())
                .containsExactly(
                        "/contracts/rollback-sql-review-gate.sample.json",
                        "/contracts/rollback-approval-handoff.sample.json",
                        "/contracts/rollback-approval-record.fixture.json",
                        "/contracts/production-deployment-runbook-contract.sample.json",
                        "/contracts/production-secret-source-contract.sample.json",
                        "/contracts/release-bundle-manifest.sample.json"
                );
        assertThat(evidence.rollbackApproverEvidenceFixture().noSecretValueBoundaries())
                .containsExactly(
                        "rollback-approver-fixture-stores-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-approver-evidence",
                        "node-may-render-decision-rehearsal-input-only"
                );
        assertThat(evidence.rollbackApproverEvidenceFixture().nodeMayConsume()).isTrue();
        assertThat(evidence.rollbackApproverEvidenceFixture().nodeMayCreateApprovalDecision()).isFalse();
        assertThat(evidence.rollbackApproverEvidenceFixture().nodeMayTriggerRollback()).isFalse();
        assertThat(evidence.rollbackApproverEvidenceFixture().rollbackExecutionAllowed()).isFalse();
        assertThat(evidence.rollbackApproverEvidenceFixture().rollbackSqlExecutionAllowed()).isFalse();
        assertThat(evidence.rollbackApproverEvidenceFixture().requiresProductionDatabase()).isFalse();
        assertThat(evidence.rollbackApproverEvidenceFixture().requiresProductionSecrets()).isFalse();
        assertThat(evidence.rollbackApproverEvidenceFixture().changesOrderTransactionSemantics()).isFalse();
        assertThat(evidence.rollbackApprovalHandoff().handoffVersion())
                .isEqualTo("java-rollback-approval-handoff.v1");
        assertThat(evidence.rollbackApprovalHandoff().handoffEndpoint())
                .isEqualTo("/contracts/rollback-approval-handoff.sample.json");
        assertThat(evidence.rollbackApprovalHandoff().approvalMode())
                .isEqualTo("OPERATOR_CONFIRMATION_REQUIRED");
        assertThat(evidence.rollbackApprovalHandoff().requiredConfirmationFields())
                .containsExactly(
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
                        "rollback-approver-evidence-fixture",
                        "rollback-approval-record-fixture",
                        "rollback-sql-review-gate",
                        "release-bundle-manifest",
                        "deployment-rollback-evidence"
                );
        assertThat(evidence.rollbackApprovalHandoff().handoffArtifacts())
                .containsExactly(
                        "/contracts/release-handoff-checklist.fixture.json",
                        "/contracts/release-audit-retention.fixture.json",
                        "/contracts/release-operator-signoff.fixture.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/release-bundle-manifest.sample.json",
                        "/contracts/deployment-rollback-evidence.sample.json",
                        "/contracts/rollback-approval-record.fixture.json",
                        "/contracts/rollback-sql-review-gate.sample.json",
                        "/contracts/production-secret-source-contract.sample.json",
                        "/contracts/production-deployment-runbook-contract.sample.json",
                        "/contracts/release-verification-manifest.sample.json"
                );
        assertThat(evidence.rollbackApprovalHandoff().nodeMayConsume()).isTrue();
        assertThat(evidence.rollbackApprovalHandoff().nodeMayTriggerRollback()).isFalse();
        assertThat(evidence.rollbackApprovalHandoff().rollbackSqlExecutionAllowed()).isFalse();
        assertThat(evidence.rollbackApprovalHandoff().requiresProductionDatabase()).isFalse();
        assertThat(evidence.rollbackApprovalHandoff().requiresProductionSecrets()).isFalse();
        assertThat(evidence.rollbackApprovalHandoff().changesOrderTransactionSemantics()).isFalse();
        assertThat(evidence.rollbackApprovalRecordFixture().fixtureVersion())
                .isEqualTo("java-rollback-approval-record-fixture.v1");
        assertThat(evidence.rollbackApprovalRecordFixture().fixtureEndpoint())
                .isEqualTo("/contracts/rollback-approval-record.fixture.json");
        assertThat(evidence.rollbackApprovalRecordFixture().fixtureMode())
                .isEqualTo("READ_ONLY_APPROVAL_RECORD_FIXTURE");
        assertThat(evidence.rollbackApprovalRecordFixture().reviewer())
                .isEqualTo("rollback-reviewer-placeholder");
        assertThat(evidence.rollbackApprovalRecordFixture().approvalTimestampPlaceholder())
                .isEqualTo("approval-timestamp-placeholder");
        assertThat(evidence.rollbackApprovalRecordFixture().rollbackTarget())
                .isEqualTo("release-tag-or-artifact-version-placeholder");
        assertThat(evidence.rollbackApprovalRecordFixture().migrationDirectionOptions())
                .containsExactly(
                        "forward-only",
                        "rollback-script-reviewed",
                        "no-database-change"
                );
        assertThat(evidence.rollbackApprovalRecordFixture().selectedMigrationDirection())
                .isEqualTo("no-database-change");
        assertThat(evidence.rollbackApprovalRecordFixture().requiredRecordFields())
                .containsExactly(
                        "reviewer",
                        "approval-timestamp-placeholder",
                        "rollback-target",
                        "database-migration-direction",
                        "rollback-sql-review-gate",
                        "no-secret-value-boundary"
                );
        assertThat(evidence.rollbackApprovalRecordFixture().recordArtifacts())
                .containsExactly(
                        "/contracts/rollback-approval-handoff.sample.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/rollback-sql-review-gate.sample.json",
                        "/contracts/production-deployment-runbook-contract.sample.json",
                        "/contracts/production-secret-source-contract.sample.json",
                        "/contracts/release-bundle-manifest.sample.json"
                );
        assertThat(evidence.rollbackApprovalRecordFixture().noSecretValueBoundaries())
                .containsExactly(
                        "record-fixture-stores-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-approval-record",
                        "node-may-render-release-window-packet-only"
                );
        assertThat(evidence.rollbackApprovalRecordFixture().nodeMayConsume()).isTrue();
        assertThat(evidence.rollbackApprovalRecordFixture().nodeMayTriggerRollback()).isFalse();
        assertThat(evidence.rollbackApprovalRecordFixture().rollbackExecutionAllowed()).isFalse();
        assertThat(evidence.rollbackApprovalRecordFixture().rollbackSqlExecutionAllowed()).isFalse();
        assertThat(evidence.rollbackApprovalRecordFixture().requiresProductionDatabase()).isFalse();
        assertThat(evidence.rollbackApprovalRecordFixture().requiresProductionSecrets()).isFalse();
        assertThat(evidence.rollbackApprovalRecordFixture().changesOrderTransactionSemantics()).isFalse();
        assertThat(evidence.rollbackSqlReviewGate().gateVersion())
                .isEqualTo("java-rollback-sql-review-gate.v1");
        assertThat(evidence.rollbackSqlReviewGate().gateEndpoint())
                .isEqualTo("/contracts/rollback-sql-review-gate.sample.json");
        assertThat(evidence.rollbackSqlReviewGate().gateMode())
                .isEqualTo("READ_ONLY_SQL_REVIEW_GATE");
        assertThat(evidence.rollbackSqlReviewGate().reviewOwner()).isEqualTo("database-release-owner");
        assertThat(evidence.rollbackSqlReviewGate().requiredReviewFields())
                .containsExactly(
                        "rollback-sql-review-owner",
                        "migration-direction",
                        "operator-approval-placeholder",
                        "rollback-sql-artifact-reference",
                        "production-database-access-boundary"
                );
        assertThat(evidence.rollbackSqlReviewGate().migrationDirectionOptions())
                .containsExactly(
                        "forward-only",
                        "rollback-script-reviewed",
                        "no-database-change"
                );
        assertThat(evidence.rollbackSqlReviewGate().operatorApprovalPlaceholder())
                .isEqualTo("operator-approval-required-before-any-sql-execution");
        assertThat(evidence.rollbackSqlReviewGate().nodeMayConsume()).isTrue();
        assertThat(evidence.rollbackSqlReviewGate().nodeMayTriggerRollback()).isFalse();
        assertThat(evidence.rollbackSqlReviewGate().sqlExecutionAllowed()).isFalse();
        assertThat(evidence.rollbackSqlReviewGate().requiresProductionDatabase()).isFalse();
        assertThat(evidence.rollbackSqlReviewGate().changesOrderTransactionSemantics()).isFalse();
        assertThat(evidence.productionSecretSourceContract().contractVersion())
                .isEqualTo("java-production-secret-source-contract.v1");
        assertThat(evidence.productionSecretSourceContract().contractEndpoint())
                .isEqualTo("/contracts/production-secret-source-contract.sample.json");
        assertThat(evidence.productionSecretSourceContract().contractMode())
                .isEqualTo("READ_ONLY_SECRET_SOURCE_CONTRACT");
        assertThat(evidence.productionSecretSourceContract().sourceTypes())
                .containsExactly(
                        "external-secret-manager",
                        "environment-injected-secret",
                        "platform-managed-secret"
                );
        assertThat(evidence.productionSecretSourceContract().selectedSourceType())
                .isEqualTo("external-secret-manager");
        assertThat(evidence.productionSecretSourceContract().secretManagerOwner())
                .isEqualTo("platform-security-owner");
        assertThat(evidence.productionSecretSourceContract().rotationOwner())
                .isEqualTo("security-operations-owner");
        assertThat(evidence.productionSecretSourceContract().reviewCadence())
                .isEqualTo("quarterly-or-before-production-cutover");
        assertThat(evidence.productionSecretSourceContract().requiredConfirmationFields())
                .containsExactly(
                        "secret-manager-or-source-type",
                        "secret-manager-owner",
                        "rotation-owner",
                        "review-cadence",
                        "secret-value-access-boundary"
                );
        assertThat(evidence.productionSecretSourceContract().secretValueBoundaries())
                .containsExactly(
                        "contract-records-source-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-static-contracts",
                        "node-may-render-checklist-only"
                );
        assertThat(evidence.productionSecretSourceContract().nodeMayConsume()).isTrue();
        assertThat(evidence.productionSecretSourceContract().nodeMayReadSecretValues()).isFalse();
        assertThat(evidence.productionSecretSourceContract().requiresProductionSecrets()).isFalse();
        assertThat(evidence.productionSecretSourceContract().requiresProductionDatabase()).isFalse();
        assertThat(evidence.productionSecretSourceContract().changesOrderTransactionSemantics()).isFalse();
        assertThat(evidence.productionDeploymentRunbookContract().contractVersion())
                .isEqualTo("java-production-deployment-runbook-contract.v1");
        assertThat(evidence.productionDeploymentRunbookContract().contractEndpoint())
                .isEqualTo("/contracts/production-deployment-runbook-contract.sample.json");
        assertThat(evidence.productionDeploymentRunbookContract().contractMode())
                .isEqualTo("READ_ONLY_DEPLOYMENT_RUNBOOK_CONTRACT");
        assertThat(evidence.productionDeploymentRunbookContract().deploymentWindowOwner())
                .isEqualTo("release-window-owner");
        assertThat(evidence.productionDeploymentRunbookContract().rollbackApprover())
                .isEqualTo("rollback-approval-owner");
        assertThat(evidence.productionDeploymentRunbookContract().databaseMigrationDirectionOptions())
                .containsExactly(
                        "forward-only",
                        "rollback-script-reviewed",
                        "no-database-change"
                );
        assertThat(evidence.productionDeploymentRunbookContract().selectedDatabaseMigrationDirection())
                .isEqualTo("no-database-change");
        assertThat(evidence.productionDeploymentRunbookContract().secretSourceConfirmation())
                .isEqualTo("/contracts/production-secret-source-contract.sample.json");
        assertThat(evidence.productionDeploymentRunbookContract().requiredConfirmationFields())
                .containsExactly(
                        "deployment-window-owner",
                        "rollback-approver",
                        "database-migration-direction",
                        "secret-source-confirmation",
                        "rollback-sql-review-gate",
                        "operator-approval-placeholder",
                        "release-audit-retention-fixture",
                        "release-operator-signoff-fixture",
                        "rollback-approver-evidence-fixture"
                );
        assertThat(evidence.productionDeploymentRunbookContract().runbookArtifacts())
                .containsExactly(
                        "/contracts/release-bundle-manifest.sample.json",
                        "/contracts/deployment-rollback-evidence.sample.json",
                        "/contracts/release-handoff-checklist.fixture.json",
                        "/contracts/release-audit-retention.fixture.json",
                        "/contracts/release-operator-signoff.fixture.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/rollback-approval-handoff.sample.json",
                        "/contracts/rollback-approval-record.fixture.json",
                        "/contracts/rollback-sql-review-gate.sample.json",
                        "/contracts/production-secret-source-contract.sample.json"
                );
        assertThat(evidence.productionDeploymentRunbookContract().nodeMayConsume()).isTrue();
        assertThat(evidence.productionDeploymentRunbookContract().nodeMayTriggerDeployment()).isFalse();
        assertThat(evidence.productionDeploymentRunbookContract().nodeMayTriggerRollback()).isFalse();
        assertThat(evidence.productionDeploymentRunbookContract().sqlExecutionAllowed()).isFalse();
        assertThat(evidence.productionDeploymentRunbookContract().requiresProductionDatabase()).isFalse();
        assertThat(evidence.productionDeploymentRunbookContract().requiresProductionSecrets()).isFalse();
        assertThat(evidence.productionDeploymentRunbookContract().changesOrderTransactionSemantics()).isFalse();
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
                        "/api/v1/ops/release-approval-rehearsal",
                        "/contracts/ops-read-only-evidence.sample.json",
                        "/contracts/ops-evidence-field-guide.sample.json",
                        "/contracts/order-idempotency-boundary.sample.json",
                        "/contracts/order-idempotency-store-abstraction.sample.json",
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/deployment-rollback-evidence.sample.json",
                        "/contracts/release-bundle-manifest.sample.json",
                        "/contracts/release-handoff-checklist.fixture.json",
                        "/contracts/release-audit-retention.fixture.json",
                        "/contracts/release-operator-signoff.fixture.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/rollback-approval-handoff.sample.json",
                        "/contracts/rollback-approval-record.fixture.json",
                        "/contracts/rollback-sql-review-gate.sample.json",
                        "/contracts/production-secret-source-contract.sample.json",
                        "/contracts/production-deployment-runbook-contract.sample.json",
                        "/api/v1/failed-events/{id}/replay-execution-contract",
                        "/api/v1/failed-events/replay-evidence-index"
                );

        ReleaseApprovalRehearsalResponse rehearsal = service.releaseApprovalRehearsal();
        assertThat(rehearsal.rehearsalVersion()).isEqualTo("java-release-approval-rehearsal.v1");
        assertThat(rehearsal.sourceEvidenceEndpoint()).isEqualTo("/api/v1/ops/evidence");
        assertThat(rehearsal.rehearsalMode()).isEqualTo("READ_ONLY_RELEASE_APPROVAL_REHEARSAL");
        assertThat(rehearsal.readOnly()).isTrue();
        assertThat(rehearsal.executionAllowed()).isFalse();
        assertThat(rehearsal.requestContext().contextVersion())
                .isEqualTo("java-release-approval-rehearsal-context.v1");
        assertThat(rehearsal.requestContext().requestId()).isEqualTo("rehearsal-request-id-not-supplied");
        assertThat(rehearsal.requestContext().requestIdSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.requestContext().operatorIdentity()).isEqualTo("operator-identity-not-supplied");
        assertThat(rehearsal.requestContext().operatorIdentitySource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.requestContext().auditCorrelationId()).isEqualTo("audit-correlation-id-not-supplied");
        assertThat(rehearsal.requestContext().auditCorrelationSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.requestContext().operatorAuthenticatedByJava()).isFalse();
        assertThat(rehearsal.requestContext().persistedByJava()).isFalse();
        assertThat(rehearsal.requestContext().approvalLedgerWritten()).isFalse();
        assertThat(rehearsal.requestContext().requiresProductionIdentityProvider()).isFalse();
        assertThat(rehearsal.requestContext().acceptedReadOnlyHeaders())
                .containsExactly(
                        "X-Rehearsal-Request-Id",
                        "X-Operator-Identity",
                        "X-Audit-Correlation-Id"
                );
        assertThat(rehearsal.requestContext().contextWarnings())
                .containsExactly(
                        "REHEARSAL_REQUEST_ID_MISSING",
                        "OPERATOR_IDENTITY_MISSING",
                        "AUDIT_CORRELATION_ID_MISSING"
                );
        assertThat(rehearsal.operatorWindowHint().hintVersion())
                .isEqualTo("java-release-approval-rehearsal-operator-window-hint.v1");
        assertThat(rehearsal.operatorWindowHint().operatorId()).isEqualTo("orderops-operator-id-not-supplied");
        assertThat(rehearsal.operatorWindowHint().operatorIdSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.operatorWindowHint().operatorRoles()).isEqualTo("orderops-roles-not-supplied");
        assertThat(rehearsal.operatorWindowHint().operatorRolesSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.operatorWindowHint().operatorVerifiedClaim())
                .isEqualTo("orderops-operator-verified-not-supplied");
        assertThat(rehearsal.operatorWindowHint().operatorVerifiedClaimSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.operatorWindowHint().approvalCorrelationId())
                .isEqualTo("orderops-approval-correlation-id-not-supplied");
        assertThat(rehearsal.operatorWindowHint().approvalCorrelationIdSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.operatorWindowHint().operatorIdentityEchoed()).isFalse();
        assertThat(rehearsal.operatorWindowHint().operatorRolesEchoed()).isFalse();
        assertThat(rehearsal.operatorWindowHint().operatorVerifiedClaimEchoed()).isFalse();
        assertThat(rehearsal.operatorWindowHint().approvalCorrelationEchoed()).isFalse();
        assertThat(rehearsal.operatorWindowHint().operatorWindowContextComplete()).isFalse();
        assertThat(rehearsal.operatorWindowHint().productionIdpVerifiedByJava()).isFalse();
        assertThat(rehearsal.operatorWindowHint().persistedApprovalRecordByJava()).isFalse();
        assertThat(rehearsal.operatorWindowHint().nodeMayTreatAsProductionIdentity()).isFalse();
        assertThat(rehearsal.operatorWindowHint().acceptedOperatorWindowHeaders())
                .containsExactly(
                        "x-orderops-operator-id",
                        "x-orderops-roles",
                        "x-orderops-operator-verified",
                        "x-orderops-approval-correlation-id"
                );
        assertThat(rehearsal.operatorWindowHint().echoWarnings())
                .containsExactly(
                        "ORDEROPS_OPERATOR_ID_MISSING",
                        "ORDEROPS_OPERATOR_ROLES_MISSING",
                        "ORDEROPS_OPERATOR_VERIFIED_CLAIM_MISSING",
                        "ORDEROPS_APPROVAL_CORRELATION_ID_MISSING"
                );
        assertThat(rehearsal.operatorWindowHint().nodeVerificationActions())
                .contains(
                        "Compare operatorWindowHint.operatorId with Node v198 operatorIdentity.operatorId",
                        "Keep nodeMayTreatAsProductionIdentity=false"
                );
        assertThat(rehearsal.ciEvidenceHint().hintVersion())
                .isEqualTo("java-release-approval-rehearsal-ci-evidence-hint.v1");
        assertThat(rehearsal.ciEvidenceHint().manifestProfileVersion())
                .isEqualTo("ci-manifest-profile-version-not-supplied");
        assertThat(rehearsal.ciEvidenceHint().manifestProfileVersionSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.ciEvidenceHint().manifestDigest()).isEqualTo("ci-manifest-digest-not-supplied");
        assertThat(rehearsal.ciEvidenceHint().manifestDigestSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.ciEvidenceHint().manifestEndpoint()).isEqualTo("ci-manifest-endpoint-not-supplied");
        assertThat(rehearsal.ciEvidenceHint().manifestEndpointSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.ciEvidenceHint().artifactRecordCount())
                .isEqualTo("ci-artifact-record-count-not-supplied");
        assertThat(rehearsal.ciEvidenceHint().artifactRecordCountSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.ciEvidenceHint().approvalCorrelationId())
                .isEqualTo("ci-approval-correlation-id-not-supplied");
        assertThat(rehearsal.ciEvidenceHint().approvalCorrelationIdSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.ciEvidenceHint().manifestProfileVersionEchoed()).isFalse();
        assertThat(rehearsal.ciEvidenceHint().manifestDigestEchoed()).isFalse();
        assertThat(rehearsal.ciEvidenceHint().manifestEndpointEchoed()).isFalse();
        assertThat(rehearsal.ciEvidenceHint().artifactRecordCountEchoed()).isFalse();
        assertThat(rehearsal.ciEvidenceHint().approvalCorrelationEchoed()).isFalse();
        assertThat(rehearsal.ciEvidenceHint().ciEvidenceContextComplete()).isFalse();
        assertThat(rehearsal.ciEvidenceHint().noLedgerWriteProof())
                .isEqualTo("NO_LEDGER_WRITE_PROOF_BY_RESPONSE_FIELDS");
        assertThat(rehearsal.ciEvidenceHint().noLedgerWriteProved()).isTrue();
        assertThat(rehearsal.ciEvidenceHint().ciArtifactUploadedByJava()).isFalse();
        assertThat(rehearsal.ciEvidenceHint().githubArtifactAccessedByJava()).isFalse();
        assertThat(rehearsal.ciEvidenceHint().productionWindowAllowedByJava()).isFalse();
        assertThat(rehearsal.ciEvidenceHint().nodeMayTreatAsCiArtifactPublication()).isFalse();
        assertThat(rehearsal.ciEvidenceHint().acceptedCiEvidenceHeaders())
                .containsExactly(
                        "x-orderops-ci-manifest-version",
                        "x-orderops-ci-manifest-digest",
                        "x-orderops-ci-manifest-endpoint",
                        "x-orderops-ci-artifact-record-count",
                        "x-orderops-ci-approval-correlation-id"
                );
        assertThat(rehearsal.ciEvidenceHint().echoWarnings())
                .containsExactly(
                        "ORDEROPS_CI_MANIFEST_VERSION_MISSING",
                        "ORDEROPS_CI_MANIFEST_DIGEST_MISSING",
                        "ORDEROPS_CI_MANIFEST_ENDPOINT_MISSING",
                        "ORDEROPS_CI_ARTIFACT_RECORD_COUNT_MISSING",
                        "ORDEROPS_CI_APPROVAL_CORRELATION_ID_MISSING"
                );
        assertThat(rehearsal.ciEvidenceHint().nodeVerificationActions())
                .contains(
                        "Compare ciEvidenceHint.manifestDigest with Node v200 manifest.manifestDigest",
                        "Keep ciArtifactUploadedByJava=false and githubArtifactAccessedByJava=false"
                );
        assertThat(rehearsal.artifactRetentionHint().hintVersion())
                .isEqualTo("java-release-approval-rehearsal-artifact-retention-hint.v1");
        assertThat(rehearsal.artifactRetentionHint().sourceRetentionFixtureVersion())
                .isEqualTo("java-release-audit-retention-fixture.v1");
        assertThat(rehearsal.artifactRetentionHint().sourceRetentionFixtureEndpoint())
                .isEqualTo("/contracts/release-audit-retention.fixture.json");
        assertThat(rehearsal.artifactRetentionHint().retentionId())
                .isEqualTo("release-retention-record-placeholder");
        assertThat(rehearsal.artifactRetentionHint().artifactTarget())
                .isEqualTo("release-tag-or-artifact-version-placeholder");
        assertThat(rehearsal.artifactRetentionHint().javaRetentionDays()).isEqualTo(180);
        assertThat(rehearsal.artifactRetentionHint().ciUploadContractVersion())
                .isEqualTo("ci-upload-contract-version-not-supplied");
        assertThat(rehearsal.artifactRetentionHint().ciUploadContractVersionSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.artifactRetentionHint().ciUploadContractDigest())
                .isEqualTo("ci-upload-contract-digest-not-supplied");
        assertThat(rehearsal.artifactRetentionHint().ciUploadContractDigestSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.artifactRetentionHint().ciArtifactName())
                .isEqualTo("ci-artifact-name-not-supplied");
        assertThat(rehearsal.artifactRetentionHint().ciArtifactNameSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.artifactRetentionHint().ciArtifactRoot())
                .isEqualTo("ci-artifact-root-not-supplied");
        assertThat(rehearsal.artifactRetentionHint().ciArtifactRootSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.artifactRetentionHint().ciRetentionDays())
                .isEqualTo("ci-retention-days-not-supplied");
        assertThat(rehearsal.artifactRetentionHint().ciRetentionDaysSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.artifactRetentionHint().ciUploadMode())
                .isEqualTo("ci-upload-mode-not-supplied");
        assertThat(rehearsal.artifactRetentionHint().ciUploadModeSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.artifactRetentionHint().uploadContractVersionEchoed()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().uploadContractDigestEchoed()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().artifactNameEchoed()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().artifactRootEchoed()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().retentionDaysEchoed()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().uploadModeEchoed()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().artifactRetentionContextComplete()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().retentionDaysWithinJavaRetention()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().javaRetentionFixtureReadOnly()).isTrue();
        assertThat(rehearsal.artifactRetentionHint().auditExportReadOnly()).isTrue();
        assertThat(rehearsal.artifactRetentionHint().ciArtifactUploadedByJava()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().githubArtifactAccessedByJava()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().productionWindowAllowedByJava()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().nodeMayTreatAsRetentionAuthorization()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().acceptedArtifactRetentionHeaders())
                .containsExactly(
                        "x-orderops-ci-upload-contract-version",
                        "x-orderops-ci-upload-contract-digest",
                        "x-orderops-ci-artifact-name",
                        "x-orderops-ci-artifact-root",
                        "x-orderops-ci-retention-days",
                        "x-orderops-ci-upload-mode"
                );
        assertThat(rehearsal.artifactRetentionHint().releaseEvidenceEndpoints())
                .contains(
                        "/api/v1/ops/evidence",
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/release-bundle-manifest.sample.json"
                );
        assertThat(rehearsal.artifactRetentionHint().echoWarnings())
                .containsExactly(
                        "ORDEROPS_CI_UPLOAD_CONTRACT_VERSION_MISSING",
                        "ORDEROPS_CI_UPLOAD_CONTRACT_DIGEST_MISSING",
                        "ORDEROPS_CI_ARTIFACT_NAME_MISSING",
                        "ORDEROPS_CI_ARTIFACT_ROOT_MISSING",
                        "ORDEROPS_CI_RETENTION_DAYS_MISSING",
                        "ORDEROPS_CI_UPLOAD_MODE_MISSING"
                );
        assertThat(rehearsal.artifactRetentionHint().nodeVerificationActions())
                .contains(
                        "Compare artifactRetentionHint.ciUploadContractDigest with Node v202 dryRunContract.contractDigest",
                        "Require artifactRetentionHint.retentionDaysWithinJavaRetention=true before Node v203 retention gate",
                        "Keep ciArtifactUploadedByJava=false and githubArtifactAccessedByJava=false"
                );
        assertThat(rehearsal.liveReadinessHint().hintVersion())
                .isEqualTo("java-release-approval-rehearsal-live-readiness-hint.v1");
        assertThat(rehearsal.liveReadinessHint().serverTimestamp()).isEqualTo(rehearsal.sampledAt());
        assertThat(rehearsal.liveReadinessHint().serverTimestampSource()).isEqualTo("sampledAt");
        assertThat(rehearsal.liveReadinessHint().readOnlyEndpointVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v17");
        assertThat(rehearsal.liveReadinessHint().readOnlyEndpoint())
                .isEqualTo("/api/v1/ops/release-approval-rehearsal");
        assertThat(rehearsal.liveReadinessHint().healthEndpoint()).isEqualTo("/actuator/health");
        assertThat(rehearsal.liveReadinessHint().sourcePreflightVersion())
                .isEqualTo("runtime-preflight-version-not-supplied");
        assertThat(rehearsal.liveReadinessHint().sourcePreflightVersionSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.liveReadinessHint().sourcePreflightDigest())
                .isEqualTo("runtime-preflight-digest-not-supplied");
        assertThat(rehearsal.liveReadinessHint().sourcePreflightDigestSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.liveReadinessHint().runtimeSmokeSessionId())
                .isEqualTo("runtime-smoke-session-id-not-supplied");
        assertThat(rehearsal.liveReadinessHint().runtimeReadTargetId())
                .isEqualTo("runtime-read-target-id-not-supplied");
        assertThat(rehearsal.liveReadinessHint().runtimeWindowMode())
                .isEqualTo("runtime-window-mode-not-supplied");
        assertThat(rehearsal.liveReadinessHint().sourcePreflightVersionEchoed()).isFalse();
        assertThat(rehearsal.liveReadinessHint().sourcePreflightDigestEchoed()).isFalse();
        assertThat(rehearsal.liveReadinessHint().runtimeSmokeSessionIdEchoed()).isFalse();
        assertThat(rehearsal.liveReadinessHint().runtimeReadTargetIdEchoed()).isFalse();
        assertThat(rehearsal.liveReadinessHint().runtimeWindowModeEchoed()).isFalse();
        assertThat(rehearsal.liveReadinessHint().liveReadinessContextComplete()).isFalse();
        assertThat(rehearsal.liveReadinessHint().readyForRuntimeSmokeRead()).isTrue();
        assertThat(rehearsal.liveReadinessHint().readOnlyEndpointReady()).isTrue();
        assertThat(rehearsal.liveReadinessHint().runtimeSmokeExecutedByJava()).isFalse();
        assertThat(rehearsal.liveReadinessHint().nodeMustRecordPidAndCleanup()).isTrue();
        assertThat(rehearsal.liveReadinessHint().javaStartedProcessForNode()).isFalse();
        assertThat(rehearsal.liveReadinessHint().processCleanupRecordedByJava()).isFalse();
        assertThat(rehearsal.liveReadinessHint().nodeMayTreatAsProductionAuthorization()).isFalse();
        assertThat(rehearsal.liveReadinessHint().acceptedLiveReadinessHeaders())
                .containsExactly(
                        "x-orderops-runtime-preflight-version",
                        "x-orderops-runtime-preflight-digest",
                        "x-orderops-runtime-smoke-session-id",
                        "x-orderops-runtime-read-target-id",
                        "x-orderops-runtime-window-mode"
                );
        assertThat(rehearsal.liveReadinessHint().allowedReadTargets())
                .containsExactly(
                        "GET /actuator/health",
                        "GET /api/v1/ops/release-approval-rehearsal"
                );
        assertThat(rehearsal.liveReadinessHint().forbiddenRuntimeOperations())
                .contains(
                        "POST /api/v1/orders",
                        "POST /api/v1/failed-events/{id}/replay",
                        "Java process start/stop is owned by Node v205 smoke orchestration"
                );
        assertThat(rehearsal.liveReadinessHint().echoWarnings())
                .containsExactly(
                        "ORDEROPS_RUNTIME_PREFLIGHT_VERSION_MISSING",
                        "ORDEROPS_RUNTIME_PREFLIGHT_DIGEST_MISSING",
                        "ORDEROPS_RUNTIME_SMOKE_SESSION_ID_MISSING",
                        "ORDEROPS_RUNTIME_READ_TARGET_ID_MISSING",
                        "ORDEROPS_RUNTIME_WINDOW_MODE_MISSING"
                );
        assertThat(rehearsal.liveReadinessHint().nodeVerificationActions())
                .contains(
                        "Compare liveReadinessHint.sourcePreflightDigest with Node v204 runtimeWindow.preflightDigest",
                        "Require liveReadinessHint.readOnlyEndpointReady=true before counting Java read target as ready",
                        "Keep runtimeSmokeExecutedByJava=false and javaStartedProcessForNode=false"
                );
        assertThat(rehearsal.auditPersistenceHandoffHint().hintVersion())
                .isEqualTo("java-release-approval-rehearsal-audit-persistence-handoff-hint.v1");
        assertThat(rehearsal.auditPersistenceHandoffHint().sourceRetentionFixtureVersion())
                .isEqualTo("java-release-audit-retention-fixture.v1");
        assertThat(rehearsal.auditPersistenceHandoffHint().sourceRetentionFixtureEndpoint())
                .isEqualTo("/contracts/release-audit-retention.fixture.json");
        assertThat(rehearsal.auditPersistenceHandoffHint().javaRetentionDays()).isEqualTo(180);
        assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditCandidateVersion())
                .isEqualTo("managed-audit-candidate-version-not-supplied");
        assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditCandidateVersionSource())
                .isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditCandidateDigest())
                .isEqualTo("managed-audit-candidate-digest-not-supplied");
        assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditCandidateDigestSource())
                .isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditSinkMode())
                .isEqualTo("managed-audit-sink-mode-not-supplied");
        assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditRetentionDays())
                .isEqualTo("managed-audit-retention-days-not-supplied");
        assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditRotationPolicy())
                .isEqualTo("managed-audit-rotation-policy-not-supplied");
        assertThat(rehearsal.auditPersistenceHandoffHint().candidateVersionEchoed()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().candidateDigestEchoed()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().sinkModeEchoed()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().retentionDaysEchoed()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().rotationPolicyEchoed()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().auditPersistenceHandoffContextComplete()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditRetentionWithinJavaRetention()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().javaAuditSourceReadOnly()).isTrue();
        assertThat(rehearsal.auditPersistenceHandoffHint().javaLedgerWriteAllowed()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().javaManagedAuditWriteAllowed()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().javaExternalAuditSystemAccessed()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().productionAuditStoreRequired()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().nodeMayUseAsManagedAuditInput()).isTrue();
        assertThat(rehearsal.auditPersistenceHandoffHint().nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().acceptedAuditPersistenceHeaders())
                .containsExactly(
                        "x-orderops-managed-audit-candidate-version",
                        "x-orderops-managed-audit-candidate-digest",
                        "x-orderops-managed-audit-sink-mode",
                        "x-orderops-managed-audit-retention-days",
                        "x-orderops-managed-audit-rotation-policy"
                );
        assertThat(rehearsal.auditPersistenceHandoffHint().handoffFieldPaths())
                .contains(
                        "requestContext.requestId",
                        "operatorWindowHint.operatorId",
                        "verificationHint.warningDigest",
                        "executionBoundaries.nodeMayWriteApprovalLedger"
                );
        assertThat(rehearsal.auditPersistenceHandoffHint().readOnlySourceEndpoints())
                .containsExactly(
                        "/api/v1/ops/release-approval-rehearsal",
                        "/contracts/release-audit-retention.fixture.json",
                        "/api/v1/ops/evidence"
                );
        assertThat(rehearsal.auditPersistenceHandoffHint().echoWarnings())
                .containsExactly(
                        "ORDEROPS_MANAGED_AUDIT_CANDIDATE_VERSION_MISSING",
                        "ORDEROPS_MANAGED_AUDIT_CANDIDATE_DIGEST_MISSING",
                        "ORDEROPS_MANAGED_AUDIT_SINK_MODE_MISSING",
                        "ORDEROPS_MANAGED_AUDIT_RETENTION_DAYS_MISSING",
                        "ORDEROPS_MANAGED_AUDIT_ROTATION_POLICY_MISSING"
                );
        assertThat(rehearsal.auditPersistenceHandoffHint().nodeVerificationActions())
                .contains(
                        "Compare auditPersistenceHandoffHint.managedAuditCandidateDigest with Node v208 adapter digest",
                        "Persist only the listed handoffFieldPaths in Node managed audit dry-run storage",
                        "Keep javaManagedAuditWriteAllowed=false and nodeMayTreatAsProductionAuditRecord=false"
                );
        assertThat(rehearsal.approvalRecordHandoffHint().hintVersion())
                .isEqualTo("java-release-approval-rehearsal-approval-record-handoff-hint.v1");
        assertThat(rehearsal.approvalRecordHandoffHint().sourceApprovalRecordFixtureVersion())
                .isEqualTo("java-rollback-approval-record-fixture.v1");
        assertThat(rehearsal.approvalRecordHandoffHint().sourceApprovalRecordFixtureEndpoint())
                .isEqualTo("/contracts/rollback-approval-record.fixture.json");
        assertThat(rehearsal.approvalRecordHandoffHint().reviewerPlaceholder())
                .isEqualTo("rollback-reviewer-placeholder");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalTimestampPlaceholder())
                .isEqualTo("approval-timestamp-placeholder");
        assertThat(rehearsal.approvalRecordHandoffHint().rollbackTarget())
                .isEqualTo("release-tag-or-artifact-version-placeholder");
        assertThat(rehearsal.approvalRecordHandoffHint().selectedMigrationDirection())
                .isEqualTo("no-database-change");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalBindingContractVersion())
                .isEqualTo("approval-binding-contract-version-not-supplied");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalBindingContractVersionSource())
                .isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalBindingContractDigest())
                .isEqualTo("approval-binding-contract-digest-not-supplied");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalBindingContractDigestSource())
                .isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalRequestId())
                .isEqualTo("approval-request-id-not-supplied");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalRequestIdSource())
                .isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalDecisionState())
                .isEqualTo("approval-decision-state-not-supplied");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalDecisionStateSource())
                .isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalRecordCorrelationId())
                .isEqualTo("approval-record-correlation-id-not-supplied");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalRecordCorrelationIdSource())
                .isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalBindingContractVersionEchoed()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().approvalBindingContractDigestEchoed()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().approvalRequestIdEchoed()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().approvalDecisionStateEchoed()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().approvalRecordCorrelationEchoed()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().approvalRecordHandoffContextComplete()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().approvalRecordFixtureReadOnly()).isTrue();
        assertThat(rehearsal.approvalRecordHandoffHint().javaApprovalDecisionCreated()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().javaApprovalLedgerWritten()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().javaApprovalRecordPersisted()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().javaApprovalRecordAuthenticated()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().productionApprovalStoreRequired()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().nodeMayUseAsAuditApprovalInput()).isTrue();
        assertThat(rehearsal.approvalRecordHandoffHint().nodeMayTreatAsProductionApprovalRecord()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().acceptedApprovalRecordHeaders())
                .containsExactly(
                        "x-orderops-approval-binding-contract-version",
                        "x-orderops-approval-binding-contract-digest",
                        "x-orderops-approval-request-id",
                        "x-orderops-approval-decision-state",
                        "x-orderops-approval-record-correlation-id"
                );
        assertThat(rehearsal.approvalRecordHandoffHint().handoffFieldPaths())
                .contains(
                        "operatorWindowHint.operatorId",
                        "approvalRecordHandoffHint.approvalRequestId",
                        "verificationHint.warningDigest"
                );
        assertThat(rehearsal.approvalRecordHandoffHint().sourceRecordArtifacts())
                .contains(
                        "/contracts/rollback-approval-handoff.sample.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/rollback-sql-review-gate.sample.json"
                );
        assertThat(rehearsal.approvalRecordHandoffHint().echoWarnings())
                .containsExactly(
                        "ORDEROPS_APPROVAL_BINDING_CONTRACT_VERSION_MISSING",
                        "ORDEROPS_APPROVAL_BINDING_CONTRACT_DIGEST_MISSING",
                        "ORDEROPS_APPROVAL_REQUEST_ID_MISSING",
                        "ORDEROPS_APPROVAL_DECISION_STATE_MISSING",
                        "ORDEROPS_APPROVAL_RECORD_CORRELATION_ID_MISSING"
                );
        assertThat(rehearsal.approvalRecordHandoffHint().nodeVerificationActions())
                .contains(
                        "Compare approvalRecordHandoffHint.approvalBindingContractVersion with Node v210 binding contract",
                        "Compare approvalRecordHandoffHint.approvalBindingContractDigest with Node v210 binding digest",
                        "Keep javaApprovalRecordPersisted=false and nodeMayTreatAsProductionApprovalRecord=false"
                );
        assertThat(rehearsal.approvalHandoffVerificationMarker().markerVersion())
                .isEqualTo("java-release-approval-rehearsal-approval-handoff-verification-marker.v1");
        assertThat(rehearsal.approvalHandoffVerificationMarker().sourceApprovalRecordHandoffHintVersion())
                .isEqualTo("java-release-approval-rehearsal-approval-record-handoff-hint.v1");
        assertThat(rehearsal.approvalHandoffVerificationMarker().sourceApprovalRecordHandoffSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v9");
        assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodeProfileVersion())
                .isEqualTo("managed-audit-identity-approval-provenance-dry-run-packet.v1");
        assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodePacketState())
                .isEqualTo("dry-run-packet-verified");
        assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodeEndpoint())
                .isEqualTo("/api/v1/audit/managed-identity-approval-provenance-dry-run-packet");
        assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodeRequestId())
                .isEqualTo("managed-audit-v211-identity-approval-provenance-request");
        assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodePacketVersion())
                .isEqualTo("managed-audit-dry-run-record.v2-candidate");
        assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodeBindingContractVersion())
                .isEqualTo("managed-audit-identity-approval-binding-contract.v1");
        assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodeDryRunDirectoryLabel())
                .isEqualTo(".tmp");
        assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodeDryRunDirectoryPrefix())
                .isEqualTo("managed-audit-v211-");
        assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodeDryRunFileName())
                .isEqualTo("managed-audit-packet.jsonl");
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211MayConsume()).isTrue();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211HandoffAccepted()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211NoWriteBoundaryAccepted()).isTrue();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211PacketAppendCovered()).isTrue();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211PacketQueryCovered()).isTrue();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211PacketDigestCovered()).isTrue();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211PacketCleanupCovered()).isTrue();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211JavaWriteAttempted()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211MiniKvWriteAttempted()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211ExternalAuditSystemAccessed()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211RealApprovalDecisionCreated()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211RealApprovalLedgerWritten()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211ProductionAuditRecordAllowed()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().javaApprovalRecordPersisted()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().javaApprovalLedgerWritten()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().readyForNodeV213RestoreDrillPlan()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().consumedHandoffFieldPaths())
                .containsExactly(
                        "requestContext.requestId",
                        "operatorWindowHint.operatorId",
                        "operatorWindowHint.operatorRoles",
                        "approvalRecordHandoffHint.approvalRequestId",
                        "approvalRecordHandoffHint.approvalDecisionState",
                        "approvalRecordHandoffHint.approvalRecordCorrelationId",
                        "approvalRecordHandoffHint.reviewerPlaceholder",
                        "approvalRecordHandoffHint.approvalTimestampPlaceholder",
                        "verificationHint.warningDigest"
                );
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211AcceptedChecks())
                .contains(
                        "javaV75HandoffAccepted",
                        "javaV75NoWriteBoundaryValid",
                        "appendCovered",
                        "cleanupCovered",
                        "noRealApprovalDecisionCreated"
                );
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV213Prerequisites())
                .contains(
                        "Java v76 marker readyForNodeV213RestoreDrillPlan must be true",
                        "mini-kv v85 retention provenance replay marker must be present",
                        "UPSTREAM_ACTIONS_ENABLED must remain false"
                );
        assertThat(rehearsal.approvalHandoffVerificationMarker().markerWarnings())
                .containsExactly("NODE_V211_APPROVAL_HANDOFF_CONTEXT_INCOMPLETE");
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeVerificationActions())
                .contains(
                        "Compare approvalHandoffVerificationMarker.consumedByNodeProfileVersion with Node v211 profileVersion",
                        "Require approvalHandoffVerificationMarker.nodeV211HandoffAccepted=true before Node v213 restore drill plan",
                        "Keep approvalHandoffVerificationMarker.nodeV211ProductionAuditRecordAllowed=false"
                );
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().receiptVersion())
                .isEqualTo("java-release-approval-rehearsal-managed-audit-adapter-boundary-receipt.v1");
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().sourceApprovalHandoffMarkerVersion())
                .isEqualTo("java-release-approval-rehearsal-approval-handoff-verification-marker.v1");
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().sourceApprovalHandoffSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v10");
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().consumedByNodeArchiveVerificationVersion())
                .isEqualTo("managed-audit-restore-drill-archive-verification.v1");
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().consumedByNodeArchiveVerificationState())
                .isEqualTo("verified-restore-drill-archive");
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().consumedByNodeArchiveVerificationEndpoint())
                .isEqualTo("/api/v1/audit/managed-audit-restore-drill-archive-verification");
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nextNodeCandidateVersion())
                .isEqualTo("Node v215");
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nextNodeCandidateProfile())
                .isEqualTo("managed-audit-dry-run-adapter-candidate.v1");
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayConsume()).isTrue();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayWriteLocalDryRunFiles()).isTrue();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayConnectManagedAudit()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayCreateApprovalDecision()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayWriteApprovalLedger()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayPersistApprovalRecord()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayExecuteSql()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayTriggerDeployment()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayTriggerRollback()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayExecuteRestore()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaApprovalDecisionCreated()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaApprovalLedgerWritten()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaApprovalRecordPersisted()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaManagedAuditWriteExecuted()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaRollbackSqlExecuted()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaDeploymentTriggered()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaRollbackTriggered()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaRestoreExecuted()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().readyForNodeV215DryRunAdapterCandidate())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().readyForProductionAudit()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().readyForProductionWindow()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().acceptedSourceReceipts())
                .contains(
                        "Node v214 managed audit restore drill archive verification",
                        "Java v76 approval handoff verification marker",
                        "mini-kv v86 managed audit adapter restore boundary receipt must be present before Node v215"
                );
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().adapterBoundaryClaims())
                .contains(
                        "Node v215 may only write Node local .tmp or controlled test files",
                        "Node v215 must not connect real managed audit storage",
                        "Node v215 must not create Java approval decision",
                        "Node v215 must not write Java approval ledger",
                        "Node v215 must not execute Java SQL deployment rollback or restore"
                );
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().forbiddenAdapterOperations())
                .contains(
                        "Connect real managed audit storage from Node v215",
                        "Create Java approval decision from Node v215",
                        "Write Java approval ledger from Node v215",
                        "Persist Java approval record from Node v215",
                        "Execute Java SQL from Node v215",
                        "Set UPSTREAM_ACTIONS_ENABLED=true for Node v215"
                );
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215Prerequisites())
                .contains(
                        "Node v214 managed audit restore drill archive verification must be verified",
                        "Java v77 managed audit adapter boundary receipt must be ready",
                        "mini-kv v86 managed audit adapter restore boundary receipt must be present",
                        "UPSTREAM_ACTIONS_ENABLED must remain false"
                );
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().receiptWarnings())
                .containsExactly("NODE_V215_SOURCE_APPROVAL_HANDOFF_MARKER_NOT_READY");
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeVerificationActions())
                .contains(
                        "Compare managedAuditAdapterBoundaryReceipt.consumedByNodeArchiveVerificationVersion with Node v214 profileVersion",
                        "Require managedAuditAdapterBoundaryReceipt.readyForNodeV215DryRunAdapterCandidate=true before Node v215",
                        "Keep managedAuditAdapterBoundaryReceipt.nodeV215MayConnectManagedAudit=false"
                );
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-production-adapter-prerequisite-receipt.v1"
                );
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .sourceManagedAuditAdapterBoundaryReceiptVersion())
                .isEqualTo("java-release-approval-rehearsal-managed-audit-adapter-boundary-receipt.v1");
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .sourceManagedAuditAdapterBoundarySchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v11");
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .consumedByNodeArchiveVerificationVersion())
                .isEqualTo("managed-audit-dry-run-adapter-archive-verification.v1");
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .consumedByNodeArchiveVerificationState())
                .isEqualTo("verified-dry-run-adapter-archive");
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .consumedByNodeArchiveVerificationEndpoint())
                .isEqualTo("/api/v1/audit/managed-audit-dry-run-adapter-archive-verification");
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nextNodeGateVersion())
                .isEqualTo("Node v217");
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nextNodeGateProfile())
                .isEqualTo("managed-audit-adapter-production-hardening-readiness-gate.v1");
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeV217MayConsume()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .operatorIdentityPrerequisiteDocumented()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .approvalDecisionSourcePrerequisiteDocumented()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .ledgerHandoffPrerequisiteDocumented()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .retentionOwnerPrerequisiteDocumented()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .failureHandlingPrerequisiteDocumented()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .rollbackReviewPrerequisiteDocumented()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .externalManagedAuditStorageConfigRequired()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .productionIdentityProviderRequired()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .approvalDecisionSourceRequired()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().ledgerHandoffRequired()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().retentionOwnerRequired()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().failureHandlingRequired()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().rollbackReviewRequired()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaCreatesApprovalDecision())
                .isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaWritesApprovalLedger())
                .isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaPersistsApprovalRecord())
                .isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaWritesManagedAuditStore())
                .isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaExecutesSql()).isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaTriggersDeployment())
                .isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaTriggersRollback()).isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaExecutesRestore()).isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeV217MayConnectManagedAudit())
                .isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeV217MayWriteApprovalLedger())
                .isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeV217MayExecuteSql()).isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeV217MayTriggerDeployment())
                .isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeV217MayTriggerRollback())
                .isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeV217MayExecuteRestore())
                .isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .readyForNodeV217ProductionHardeningReadinessGate()).isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().readyForProductionAudit()).isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().readyForProductionWindow()).isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .readyForProductionOperations()).isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().prerequisiteCategories())
                .contains(
                        "operator identity",
                        "approval decision source",
                        "ledger handoff",
                        "retention owner",
                        "failure handling",
                        "rollback review"
                );
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .prerequisiteEvidenceRequired())
                .contains(
                        "Production operator identity must be bound by a real IdP outside Java v78",
                        "Approval decision source must be a real approval workflow outside Java v78",
                        "Approval ledger handoff must define ownership and append semantics outside Java v78",
                        "Rollback review evidence must exist before production adapter work"
                );
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .forbiddenProductionAdapterOperations())
                .contains(
                        "Connect real managed audit storage from Java v78 or Node v217",
                        "Write approval ledger from Java v78 or Node v217",
                        "Execute Java SQL from Java v78 or Node v217",
                        "Open production audit window from this receipt"
                );
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeV217Prerequisites())
                .contains(
                        "Node v216 managed audit dry-run adapter archive verification must be verified",
                        "Java v78 managed audit production adapter prerequisite receipt must be ready",
                        "mini-kv v87 managed audit adapter non-authoritative storage receipt must be present",
                        "UPSTREAM_ACTIONS_ENABLED must remain false"
                );
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().receiptWarnings())
                .containsExactly("NODE_V217_SOURCE_MANAGED_AUDIT_ADAPTER_BOUNDARY_RECEIPT_NOT_READY");
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeVerificationActions())
                .contains(
                        "Compare managedAuditProductionAdapterPrerequisiteReceipt.consumedByNodeArchiveVerificationVersion with Node v216 profileVersion",
                        "Require managedAuditProductionAdapterPrerequisiteReceipt.readyForNodeV217ProductionHardeningReadinessGate=true before Node v217",
                        "Keep managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayConnectManagedAudit=false"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-ops-evidence-service-quality-split-receipt.v1"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt()
                .sourceProductionAdapterPrerequisiteReceiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-production-adapter-prerequisite-receipt.v1"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt()
                .sourceProductionAdapterPrerequisiteSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v12");
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().consumedByNodeQualityPassVersion())
                .isEqualTo("Node v218");
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().consumedByNodeQualityPassProfile())
                .isEqualTo("audit-route-managed-audit-helper-quality-pass.v1");
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().nextNodePrecheckVersion())
                .isEqualTo("Node v219");
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().nextNodePrecheckProfile())
                .isEqualTo("managed-audit-adapter-implementation-precheck-packet.v1");
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().nodeV219MayConsume()).isTrue();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().receiptResponsibilityDocumented()).isTrue();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().digestResponsibilityDocumented()).isTrue();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().hintResponsibilityDocumented()).isTrue();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().renderResponsibilityDocumented()).isTrue();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().recordResponsibilityDocumented()).isTrue();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().firstSafeSplitApplied()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().broadServiceSplitDeferred()).isTrue();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().apiShapeChanged()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().approvalDecisionCreated()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().approvalLedgerWritten()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().approvalRecordPersisted()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().managedAuditStoreWritten()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().sqlExecuted()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().deploymentTriggered()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().rollbackTriggered()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().restoreExecuted()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().readyForNodeV219ImplementationPrecheck())
                .isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().readyForProductionAudit()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().readyForProductionWindow()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().nodeMayTreatAsProductionAuditRecord())
                .isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().responsibilityBoundaries())
                .contains(
                        "receipt builders own Node-facing handoff and prerequisite response blocks",
                        "digest helpers own warningDigestInputs and proofClaims stability",
                        "hint builders own request/header echo and read-only readiness hints",
                        "record types own response shape and schema-versioned field names"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().safeSplitSequence())
                .contains(
                        "Extract receipt builders after Node v219 has consumed v79 schema v13",
                        "Extract digest helpers only after warningDigest repeatability tests stay green",
                        "Run focused release approval rehearsal tests after each split"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().deferredSplitReasons())
                .contains(
                        "OpsEvidenceService still coordinates many evidence families, so broad split is deferred",
                        "Receipt extraction must not change warningDigest ordering or response field names"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().forbiddenQualityPassOperations())
                .contains(
                        "Create approval decision during Java v79 quality pass",
                        "Write approval ledger during Java v79 quality pass",
                        "Execute SQL during Java v79 quality pass",
                        "Change release approval rehearsal API path during Java v79 quality pass"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().nodeV219Prerequisites())
                .contains(
                        "Node v218 audit route and managed audit helper quality pass must be complete",
                        "Java v79 quality split receipt must expose receipt digest hint render record boundaries",
                        "mini-kv v88 command dispatch quality receipt must be present before Node v219",
                        "UPSTREAM_ACTIONS_ENABLED must remain false"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().receiptWarnings())
                .containsExactly("NODE_V219_SOURCE_PRODUCTION_ADAPTER_PREREQUISITE_RECEIPT_NOT_READY");
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().nodeVerificationActions())
                .contains(
                        "Compare opsEvidenceServiceQualitySplitReceipt.consumedByNodeQualityPassVersion with Node v218",
                        "Require opsEvidenceServiceQualitySplitReceipt.readyForNodeV219ImplementationPrecheck=true before Node v219",
                        "Keep opsEvidenceServiceQualitySplitReceipt.apiShapeChanged=false"
                );
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-adapter-implementation-guard-receipt.v1"
                );
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().sourceQualitySplitReceiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-ops-evidence-service-quality-split-receipt.v1"
                );
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().sourceQualitySplitSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v13");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().consumedByNodeDisabledShellVersion())
                .isEqualTo("Node v220");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().consumedByNodeDisabledShellProfile())
                .isEqualTo("managed-audit-adapter-disabled-shell.v1");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().consumedByNodeDisabledShellEndpoint())
                .isEqualTo("/api/v1/audit/managed-audit-adapter-disabled-shell");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().consumedByNodeDisabledShellState())
                .isEqualTo("disabled-shell-ready");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nextNodeCandidateVersion())
                .isEqualTo("Node v221");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nextNodeCandidateProfile())
                .isEqualTo("managed-audit-local-adapter-candidate-dry-run.v1");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV221MayConsume()).isTrue();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220DisabledShellReady())
                .isTrue();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220SelectedAdapterDisabled())
                .isTrue();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220LocalDryRunOnlyDeclared())
                .isTrue();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220AppendWritten())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220QueryReturnedRecords())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220ExternalManagedAuditAccessed())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220LocalDryRunWritePerformed())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaApprovalDecisionCreated())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaApprovalLedgerWritten())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaApprovalRecordPersisted())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaManagedAuditStoreWritten())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaSqlExecuted()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaDeploymentTriggered()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaRollbackTriggered()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaRestoreExecuted()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt()
                .readyForNodeV221LocalAdapterCandidateDryRun()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().readyForProductionAudit()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().readyForProductionWindow()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().guardDigest())
                .startsWith("sha256:");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().acceptedAdapterShellChecks())
                .contains(
                        "Node v220 profileVersion must equal managed-audit-adapter-disabled-shell.v1",
                        "Node v220 shellState must equal disabled-shell-ready",
                        "Node v220 selectedAdapterKind must stay disabled",
                        "Node v220 acceptedCandidateKinds may declare local-dry-run but must not select it"
                );
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().forbiddenImplementationOperations())
                .contains(
                        "Write approval ledger during Java v80 implementation guard",
                        "Write managed audit store during Java v80 implementation guard",
                        "Execute SQL during Java v80 implementation guard",
                        "Select local-dry-run adapter from Java v80 guard"
                );
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV221Prerequisites())
                .contains(
                        "Node v220 managed audit adapter disabled shell must be complete",
                        "Java v80 managed audit adapter implementation guard receipt must be ready",
                        "mini-kv v89 adapter shell non-storage guard receipt must be present before Node v221",
                        "UPSTREAM_ACTIONS_ENABLED must remain false"
                );
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().guardWarnings())
                .containsExactly("NODE_V221_SOURCE_OPS_EVIDENCE_SERVICE_QUALITY_SPLIT_RECEIPT_NOT_READY");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeVerificationActions())
                .contains(
                        "Compare managedAuditAdapterImplementationGuardReceipt.consumedByNodeDisabledShellProfile with Node v220",
                        "Require managedAuditAdapterImplementationGuardReceipt.readyForNodeV221LocalAdapterCandidateDryRun=true before Node v221",
                        "Keep managedAuditAdapterImplementationGuardReceipt.javaApprovalLedgerWritten=false",
                        "Keep managedAuditAdapterImplementationGuardReceipt.nodeV220AppendWritten=false"
                );
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-external-adapter-migration-guard-receipt.v1"
                );
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .sourceImplementationGuardReceiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-adapter-implementation-guard-receipt.v1"
                );
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .sourceImplementationGuardSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v14");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .consumedByNodeVerificationReportVersion()).isEqualTo("Node v222");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .consumedByNodeVerificationReportProfile())
                .isEqualTo("managed-audit-local-adapter-candidate-verification-report.v1");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .consumedByNodeVerificationReportEndpoint())
                .isEqualTo("/api/v1/audit/managed-audit-local-adapter-candidate-verification-report");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .consumedByNodeVerificationReportState())
                .isEqualTo("local-adapter-candidate-verification-ready");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nextNodeReviewVersion())
                .isEqualTo("Node v223");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nextNodeReviewProfile())
                .isEqualTo("managed-audit-external-adapter-connection-readiness-review.v1");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nodeV223MayConsume()).isTrue();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nodeV222VerificationReportReady())
                .isTrue();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nodeV222ReadOnlyReport())
                .isTrue();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeV222SourceEndpointRerunPerformed()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeV222AdditionalLocalDryRunWritePerformed()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nodeV222ConnectsManagedAudit())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeV222ReadyForProductionAudit()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .ownerApprovalRequiredBeforeConnection()).isTrue();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .schemaMigrationReviewRequired()).isTrue();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().credentialReviewRequired())
                .isTrue();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().credentialValueReadByJava())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().credentialValueStoredByJava())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .externalManagedAuditConnectionOpened()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .externalManagedAuditSchemaMigrated()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaApprovalDecisionCreated())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaApprovalLedgerWritten())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaApprovalRecordPersisted())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaManagedAuditStoreWritten())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaSqlExecuted()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaDeploymentTriggered())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaRollbackTriggered())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaRestoreExecuted())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .readyForNodeV223ExternalAdapterConnectionReadinessReview()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().readyForProductionAudit())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().readyForProductionWindow())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().guardDigest())
                .startsWith("sha256:");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().requiredPreConnectionReviews())
                .contains(
                        "external managed audit owner approval",
                        "external managed audit schema migration review",
                        "external managed audit credential review"
                );
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().credentialBoundaryClaims())
                .contains(
                        "Java v81 must not read credential values",
                        "Java v81 must not store credential values",
                        "Java v81 must not open an external managed audit connection"
                );
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .forbiddenExternalAdapterOperations())
                .contains(
                        "Open external managed audit connection during Java v81 migration guard",
                        "Execute schema migration SQL during Java v81 migration guard",
                        "Write managed audit store during Java v81 migration guard"
                );
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nodeV223Prerequisites())
                .contains(
                        "Node v222 verification report must be ready and read-only",
                        "Java v81 external adapter migration guard receipt must be ready",
                        "mini-kv v90 external adapter non-participation receipt must be present before Node v223",
                        "UPSTREAM_ACTIONS_ENABLED must remain false"
                );
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().guardWarnings())
                .containsExactly("NODE_V223_SOURCE_IMPLEMENTATION_GUARD_RECEIPT_NOT_READY");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nodeVerificationActions())
                .contains(
                        "Compare managedAuditExternalAdapterMigrationGuardReceipt.consumedByNodeVerificationReportProfile with Node v222",
                        "Require managedAuditExternalAdapterMigrationGuardReceipt.readyForNodeV223ExternalAdapterConnectionReadinessReview=true before Node v223",
                        "Keep managedAuditExternalAdapterMigrationGuardReceipt.credentialValueReadByJava=false",
                        "Keep managedAuditExternalAdapterMigrationGuardReceipt.externalManagedAuditConnectionOpened=false"
                );
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-adapter-approval-schema-guard-receipt.v1"
                );
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .sourceExternalAdapterMigrationGuardReceiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-external-adapter-migration-guard-receipt.v1"
                );
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .sourceExternalAdapterMigrationGuardSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v15");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .consumedByNodeSandboxPlanVersion()).isEqualTo("Node v224");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .consumedByNodeSandboxPlanProfile())
                .isEqualTo("managed-audit-sandbox-adapter-dry-run-plan.v1");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .consumedByNodeSandboxPlanEndpoint())
                .isEqualTo("/api/v1/audit/managed-audit-sandbox-adapter-dry-run-plan");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .consumedByNodeSandboxPlanState())
                .isEqualTo("sandbox-adapter-dry-run-plan-ready");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().nextNodePackageVersion())
                .isEqualTo("Node v225");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().nextNodePackageProfile())
                .isEqualTo("managed-audit-sandbox-adapter-dry-run-package.v1");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().nodeV225MayConsume())
                .isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().readyForManagedAuditSandboxAdapterDryRunPlan()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().readyForManagedAuditSandboxAdapterDryRunPackage()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().readOnlyPlan()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().connectsManagedAudit()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().readsManagedAuditCredential()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().schemaMigrationExecuted()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .ownerApprovalBoundary().ownerApprovalArtifactRequired()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .ownerApprovalBoundary().ownerApprovalArtifactProvidedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .ownerApprovalBoundary().javaApprovalDecisionCreated()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .ownerApprovalBoundary().javaApprovalLedgerWritten()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .schemaRehearsalBoundary().schemaMigrationRehearsalRequired()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .schemaRehearsalBoundary().schemaMigrationChecklistRequired()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .schemaRehearsalBoundary().schemaMigrationExecutionAllowed()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .credentialBoundary().sandboxCredentialHandleRequired()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .credentialBoundary().sandboxCredentialHandleName())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .credentialBoundary().productionCredentialAllowed()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .credentialBoundary().credentialValueRequired()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .credentialBoundary().credentialValueReadByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .executionBoundary().externalManagedAuditConnectionOpened()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .executionBoundary().javaManagedAuditStoreWritten()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .executionBoundary().javaSqlExecuted()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .qualityGateBoundary().qualityGatesAreHardAcceptanceCriteria()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .qualityGateBoundary().builderOrHelperSplitApplied()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .qualityGateBoundary().longBooleanConstructorAvoided()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .qualityGateBoundary().receiptFieldsGroupedByBoundary()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .qualityGateBoundary().opsEvidenceServiceOnlyWiresReceipt()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .readyForNodeV225SandboxAdapterDryRunPackage()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().readyForProductionAudit())
                .isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().readyForProductionWindow())
                .isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().guardDigest())
                .startsWith("sha256:");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().requiredSandboxEvidence())
                .contains(
                        "Owner approval artifact identifier for sandbox rehearsal",
                        "Sandbox credential handle without credential value disclosure",
                        "Schema migration rehearsal checklist without SQL execution"
                );
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().forbiddenSandboxOperations())
                .contains(
                        "Read or print a production managed audit credential value during Java v82 guard",
                        "Open an external managed audit connection during Java v82 guard",
                        "Execute schema migration SQL during Java v82 guard"
                );
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().nodeV225Prerequisites())
                .contains(
                        "Node v224 sandbox adapter dry-run plan must be ready and read-only",
                        "Java v82 sandbox approval/schema guard receipt must be ready",
                        "mini-kv v91 sandbox runtime evidence non-participation receipt must be present"
                );
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().guardWarnings())
                .containsExactly("NODE_V225_SOURCE_EXTERNAL_ADAPTER_MIGRATION_GUARD_RECEIPT_NOT_READY");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxAdapterApprovalSchemaGuardReceipt.consumedByNodeSandboxPlanProfile with Node v224",
                        "Require managedAuditSandboxAdapterApprovalSchemaGuardReceipt.readyForNodeV225SandboxAdapterDryRunPackage=true before Node v225",
                        "Keep managedAuditSandboxAdapterApprovalSchemaGuardReceipt.credentialBoundary.credentialValueReadByJava=false",
                        "Verify managedAuditSandboxAdapterApprovalSchemaGuardReceipt.qualityGateBoundary.builderOrHelperSplitApplied=true"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().markerVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-connection-operator-handoff-marker.v1"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .sourceSandboxAdapterApprovalSchemaGuardReceiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-adapter-approval-schema-guard-receipt.v1"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .sourceSandboxAdapterApprovalSchemaGuardSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v16");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .consumedByNodeEvidenceChecklistVersion()).isEqualTo("Node v227");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .consumedByNodeEvidenceChecklistProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-evidence-checklist.v1");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .consumedByNodeOperatorPacketVersion()).isEqualTo("Node v228");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .consumedByNodeOperatorPacketProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-operator-packet.v1");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .consumedByNodeOperatorPacketEndpoint())
                .isEqualTo("/api/v1/audit/managed-audit-manual-sandbox-connection-operator-packet");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .consumedByNodeOperatorPacketState())
                .isEqualTo("manual-sandbox-connection-operator-packet-ready");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .nextNodePacketVerificationVersion()).isEqualTo("Node v229");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .nextNodePacketVerificationProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-packet-verification.v1");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().nodeV229MayConsume())
                .isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .sandboxConnectionWindowBoundary().manualSandboxConnectionWindowRequired()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .sandboxConnectionWindowBoundary().manualSandboxConnectionWindowOpenedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .sandboxConnectionWindowBoundary().javaStartsManagedAuditService()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .sandboxConnectionWindowBoundary().nodeAutoStartAllowed()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .operatorPacketBoundary().ownerApprovalArtifactIdField())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .operatorPacketBoundary().schemaRehearsalIdField())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SCHEMA_REHEARSAL_ID");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .operatorPacketBoundary().operatorPacketReadOnly()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .operatorPacketBoundary().ownerApprovalArtifactIdFieldRecognizedByJava()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .operatorPacketBoundary().schemaRehearsalIdFieldRecognizedByJava()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .operatorPacketBoundary().packetCreatesApprovalDecision()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .credentialBoundary().credentialHandleNameField())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .credentialBoundary().credentialHandleNameRecognizedByJava()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .credentialBoundary().credentialValueRequiredByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .credentialBoundary().credentialValueReadByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .rollbackPathBoundary().rollbackPathIdField())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_ROLLBACK_PATH_ID");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .rollbackPathBoundary().manualAbortMarkerField())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_MANUAL_ABORT");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .rollbackPathBoundary().timeoutBudgetMs()).isEqualTo(15000);
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .rollbackPathBoundary().rollbackExecutionAllowedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .javaExecutionBoundary().approvalLedgerWrittenByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .javaExecutionBoundary().externalManagedAuditConnectionOpenedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .javaExecutionBoundary().sqlExecutedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .readyForNodeV229ManualSandboxConnectionPacketVerification()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().readyForProductionAudit())
                .isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().readyForProductionWindow())
                .isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().markerDigest())
                .startsWith("sha256:");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().acceptedOperatorPacketFields())
                .contains(
                        "ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID",
                        "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE",
                        "ORDEROPS_MANAGED_AUDIT_SCHEMA_REHEARSAL_ID",
                        "ORDEROPS_MANAGED_AUDIT_ROLLBACK_PATH_ID",
                        "ORDEROPS_MANAGED_AUDIT_MANUAL_ABORT"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().forbiddenHandoffOperations())
                .contains(
                        "Open a managed audit sandbox connection during Java v87 marker",
                        "Execute schema migration SQL during Java v87 marker",
                        "Write approval ledger or managed audit state during Java v87 marker"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().nodeV229Prerequisites())
                .contains(
                        "Node v228 manual sandbox connection operator packet must be archived",
                        "Java v87 sandbox connection operator handoff marker must be ready",
                        "mini-kv v96 sandbox connection receipt echo marker must be ready"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().markerWarnings())
                .containsExactly("NODE_V229_SOURCE_SANDBOX_ADAPTER_APPROVAL_SCHEMA_GUARD_RECEIPT_NOT_READY");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxConnectionOperatorHandoffMarker.consumedByNodeOperatorPacketProfile with Node v228",
                        "Require managedAuditSandboxConnectionOperatorHandoffMarker.readyForNodeV229ManualSandboxConnectionPacketVerification=true before Node v229",
                        "Keep managedAuditSandboxConnectionOperatorHandoffMarker.credentialBoundary.credentialValueReadByJava=false",
                        "Keep managedAuditSandboxConnectionOperatorHandoffMarker.sandboxConnectionWindowBoundary.manualSandboxConnectionWindowOpenedByJava=false"
                );
        assertThat(rehearsal.failureTaxonomy().taxonomyVersion())
                .isEqualTo("java-release-approval-rehearsal-failure-taxonomy.v1");
        assertThat(rehearsal.failureTaxonomy().upstreamReadiness()).isEqualTo("READY");
        assertThat(rehearsal.failureTaxonomy().authContextReadiness()).isEqualTo("WARNING");
        assertThat(rehearsal.failureTaxonomy().auditCorrelationReadiness()).isEqualTo("WARNING");
        assertThat(rehearsal.failureTaxonomy().javaReadOnlyUpstreamReady()).isTrue();
        assertThat(rehearsal.failureTaxonomy().authContextComplete()).isFalse();
        assertThat(rehearsal.failureTaxonomy().auditCorrelationPresent()).isFalse();
        assertThat(rehearsal.failureTaxonomy().retryableByReadOnlyAdapter()).isTrue();
        assertThat(rehearsal.failureTaxonomy().writeActionRequired()).isFalse();
        assertThat(rehearsal.failureTaxonomy().failureCategories())
                .containsExactly(
                        "AUTH_CONTEXT_WARNING",
                        "AUDIT_CORRELATION_WARNING",
                        "READ_ONLY_EXECUTION_BLOCKED"
                );
        assertThat(rehearsal.failureTaxonomy().taxonomyWarnings())
                .containsExactly(
                        "REQUEST_ID_OR_OPERATOR_IDENTITY_MISSING",
                        "AUDIT_CORRELATION_ID_MISSING",
                        "REHEARSAL_REMAINS_READ_ONLY"
                );
        assertThat(rehearsal.verificationHint().hintVersion())
                .isEqualTo("java-release-approval-rehearsal-verification-hint.v1");
        assertThat(rehearsal.verificationHint().responseSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v17");
        assertThat(rehearsal.verificationHint().warningDigest()).startsWith("sha256:");
        assertThat(rehearsal.verificationHint().noLedgerWriteProof())
                .isEqualTo("NO_LEDGER_WRITE_PROOF_BY_RESPONSE_FIELDS");
        assertThat(rehearsal.verificationHint().noLedgerWriteProved()).isTrue();
        assertThat(rehearsal.verificationHint().nodeMayTreatAsProductionAuthorization()).isFalse();
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains(
                        "requestContext",
                        "operatorWindowHint",
                        "ciEvidenceHint",
                        "artifactRetentionHint",
                        "liveReadinessHint",
                        "auditPersistenceHandoffHint",
                        "approvalRecordHandoffHint",
                        "approvalHandoffVerificationMarker",
                        "managedAuditAdapterBoundaryReceipt",
                        "managedAuditProductionAdapterPrerequisiteReceipt",
                        "opsEvidenceServiceQualitySplitReceipt",
                        "managedAuditAdapterImplementationGuardReceipt",
                        "managedAuditExternalAdapterMigrationGuardReceipt",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceipt",
                        "managedAuditSandboxConnectionOperatorHandoffMarker",
                        "failureTaxonomy",
                        "verificationHint",
                        "executionBoundaries"
                );
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .containsExactly(
                        "contextWarnings",
                        "operatorWindowEchoWarnings",
                        "ciEvidenceEchoWarnings",
                        "artifactRetentionEchoWarnings",
                        "liveReadinessEchoWarnings",
                        "auditPersistenceHandoffEchoWarnings",
                        "approvalRecordHandoffEchoWarnings",
                        "approvalHandoffVerificationMarkerWarnings",
                        "managedAuditAdapterBoundaryReceiptWarnings",
                        "managedAuditProductionAdapterPrerequisiteReceiptWarnings",
                        "opsEvidenceServiceQualitySplitReceiptWarnings",
                        "managedAuditAdapterImplementationGuardReceiptWarnings",
                        "managedAuditExternalAdapterMigrationGuardReceiptWarnings",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceiptWarnings",
                        "managedAuditSandboxConnectionOperatorHandoffMarkerWarnings",
                        "failureCategories",
                        "taxonomyWarnings",
                        "executionAllowed",
                        "approvalLedgerWritten",
                        "javaManagedAuditWriteAllowed",
                        "javaApprovalRecordPersisted",
                        "nodeMayTreatAsProductionApprovalRecord",
                        "nodeMayTreatAsProductionAuditRecord",
                        "nodeV211ProductionAuditRecordAllowed",
                        "nodeV211RealApprovalDecisionCreated",
                        "nodeV215MayConnectManagedAudit",
                        "nodeV215MayCreateApprovalDecision",
                        "nodeV215MayWriteApprovalLedger",
                        "nodeV215MayExecuteSql",
                        "nodeV215MayTriggerDeployment",
                        "nodeV215MayTriggerRollback",
                        "nodeV215MayExecuteRestore",
                        "nodeV217MayConnectManagedAudit",
                        "nodeV217MayWriteApprovalLedger",
                        "nodeV217MayExecuteSql",
                        "nodeV217MayTriggerDeployment",
                        "nodeV217MayTriggerRollback",
                        "nodeV217MayExecuteRestore",
                        "qualitySplitApiShapeChanged",
                        "qualitySplitApprovalDecisionCreated",
                        "qualitySplitApprovalLedgerWritten",
                        "qualitySplitManagedAuditStoreWritten",
                        "qualitySplitSqlExecuted",
                        "implementationGuardDigest",
                        "implementationGuardJavaApprovalLedgerWritten",
                        "implementationGuardJavaManagedAuditStoreWritten",
                        "implementationGuardJavaSqlExecuted",
                        "implementationGuardNodeV220AppendWritten",
                        "implementationGuardNodeV220ExternalManagedAuditAccessed",
                        "implementationGuardNodeV220LocalDryRunWritePerformed",
                        "externalAdapterMigrationGuardDigest",
                        "externalAdapterMigrationCredentialValueReadByJava",
                        "externalAdapterMigrationConnectionOpened",
                        "externalAdapterMigrationSchemaMigrated",
                        "externalAdapterMigrationJavaManagedAuditStoreWritten",
                        "externalAdapterMigrationJavaSqlExecuted",
                        "externalAdapterMigrationNodeV222SourceEndpointRerunPerformed",
                        "externalAdapterMigrationNodeV222AdditionalLocalDryRunWritePerformed",
                        "sandboxAdapterApprovalSchemaGuardDigest",
                        "sandboxAdapterOwnerApprovalArtifactProvidedByJava",
                        "sandboxAdapterSchemaMigrationSqlExecutedByJava",
                        "sandboxAdapterCredentialValueReadByJava",
                        "sandboxAdapterExternalManagedAuditConnectionOpened",
                        "sandboxAdapterJavaManagedAuditStoreWritten",
                        "sandboxAdapterJavaSqlExecuted",
                        "sandboxAdapterQualityGateBuilderOrHelperSplitApplied",
                        "sandboxConnectionOperatorHandoffMarkerDigest",
                        "sandboxConnectionOperatorWindowOpenedByJava",
                        "sandboxConnectionOwnerArtifactIdFieldRecognizedByJava",
                        "sandboxConnectionCredentialValueReadByJava",
                        "sandboxConnectionSchemaMigrationSqlExecutedByJava",
                        "sandboxConnectionRollbackTriggeredByJava",
                        "sandboxConnectionExternalManagedAuditConnectionOpenedByJava",
                        "nodeMayWriteApprovalLedger"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "executionAllowed=false",
                        "requestContext.approvalLedgerWritten=false",
                        "ciEvidenceHint.noLedgerWriteProved=true",
                        "ciEvidenceHint.ciArtifactUploadedByJava=false",
                        "artifactRetentionHint.javaRetentionFixtureReadOnly=true",
                        "artifactRetentionHint.githubArtifactAccessedByJava=false",
                        "liveReadinessHint.readOnlyEndpointReady=true",
                        "liveReadinessHint.javaStartedProcessForNode=false",
                        "auditPersistenceHandoffHint.javaAuditSourceReadOnly=true",
                        "auditPersistenceHandoffHint.javaManagedAuditWriteAllowed=false",
                        "auditPersistenceHandoffHint.nodeMayTreatAsProductionAuditRecord=false",
                        "approvalRecordHandoffHint.approvalRecordFixtureReadOnly=true",
                        "approvalRecordHandoffHint.javaApprovalRecordPersisted=false",
                        "approvalRecordHandoffHint.nodeMayTreatAsProductionApprovalRecord=false",
                        "approvalHandoffVerificationMarker.nodeV211ProductionAuditRecordAllowed=false",
                        "approvalHandoffVerificationMarker.nodeV211RealApprovalDecisionCreated=false",
                        "approvalHandoffVerificationMarker.javaApprovalRecordPersisted=false",
                        "managedAuditAdapterBoundaryReceipt.nodeV215MayConnectManagedAudit=false",
                        "managedAuditAdapterBoundaryReceipt.nodeV215MayCreateApprovalDecision=false",
                        "managedAuditAdapterBoundaryReceipt.nodeV215MayWriteApprovalLedger=false",
                        "managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteSql=false",
                        "managedAuditAdapterBoundaryReceipt.nodeV215MayTriggerDeployment=false",
                        "managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteRestore=false",
                        "managedAuditProductionAdapterPrerequisiteReceipt.javaCreatesApprovalDecision=false",
                        "managedAuditProductionAdapterPrerequisiteReceipt.javaWritesApprovalLedger=false",
                        "managedAuditProductionAdapterPrerequisiteReceipt.javaExecutesSql=false",
                        "managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayConnectManagedAudit=false",
                        "managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayWriteApprovalLedger=false",
                        "managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayExecuteSql=false",
                        "opsEvidenceServiceQualitySplitReceipt.apiShapeChanged=false",
                        "opsEvidenceServiceQualitySplitReceipt.approvalLedgerWritten=false",
                        "opsEvidenceServiceQualitySplitReceipt.sqlExecuted=false",
                        "managedAuditAdapterImplementationGuardReceipt.nodeV220SelectedAdapterDisabled=true",
                        "managedAuditAdapterImplementationGuardReceipt.nodeV220AppendWritten=false",
                        "managedAuditAdapterImplementationGuardReceipt.javaApprovalLedgerWritten=false",
                        "managedAuditAdapterImplementationGuardReceipt.javaSqlExecuted=false",
                        "managedAuditExternalAdapterMigrationGuardReceipt.ownerApprovalRequiredBeforeConnection=true",
                        "managedAuditExternalAdapterMigrationGuardReceipt.credentialValueReadByJava=false",
                        "managedAuditExternalAdapterMigrationGuardReceipt.externalManagedAuditConnectionOpened=false",
                        "managedAuditExternalAdapterMigrationGuardReceipt.javaSqlExecuted=false",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceipt.ownerApprovalBoundary.ownerApprovalArtifactRequired=true",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceipt.ownerApprovalBoundary.ownerApprovalArtifactProvidedByJava=false",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceipt.schemaRehearsalBoundary.schemaMigrationSqlExecutedByJava=false",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceipt.credentialBoundary.sandboxCredentialHandleRequired=true",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceipt.credentialBoundary.credentialValueReadByJava=false",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceipt.executionBoundary.externalManagedAuditConnectionOpened=false",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceipt.executionBoundary.javaSqlExecuted=false",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceipt.qualityGateBoundary.builderOrHelperSplitApplied=true",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceipt.qualityGateBoundary.longBooleanConstructorAvoided=true",
                        "managedAuditSandboxConnectionOperatorHandoffMarker.sandboxConnectionWindowBoundary.manualSandboxConnectionWindowOpenedByJava=false",
                        "managedAuditSandboxConnectionOperatorHandoffMarker.operatorPacketBoundary.ownerApprovalArtifactIdFieldRecognizedByJava=true",
                        "managedAuditSandboxConnectionOperatorHandoffMarker.operatorPacketBoundary.schemaRehearsalIdFieldRecognizedByJava=true",
                        "managedAuditSandboxConnectionOperatorHandoffMarker.credentialBoundary.credentialValueReadByJava=false",
                        "managedAuditSandboxConnectionOperatorHandoffMarker.schemaRehearsalBoundary.schemaMigrationSqlExecutedByJava=false",
                        "managedAuditSandboxConnectionOperatorHandoffMarker.rollbackPathBoundary.rollbackExecutionAllowedByJava=false",
                        "managedAuditSandboxConnectionOperatorHandoffMarker.javaExecutionBoundary.externalManagedAuditConnectionOpenedByJava=false",
                        "managedAuditSandboxConnectionOperatorHandoffMarker.javaExecutionBoundary.approvalLedgerWrittenByJava=false",
                        "managedAuditSandboxConnectionOperatorHandoffMarker.javaExecutionBoundary.sqlExecutedByJava=false",
                        "executionBoundaries.nodeMayWriteApprovalLedger=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Verify responseSchemaVersion before importing operator window results",
                        "Compare ciEvidenceHint.manifestDigest with Node v200 manifest.manifestDigest",
                        "Compare artifactRetentionHint.ciArtifactName and ciRetentionDays with Node v202 dry-run contract",
                        "Compare liveReadinessHint.sourcePreflightVersion and runtimeSmokeSessionId with Node v204/v205 smoke context",
                        "Compare auditPersistenceHandoffHint.managedAuditCandidateVersion with Node v208 managed audit candidate",
                        "Compare approvalRecordHandoffHint.approvalBindingContractVersion with Node v210 binding contract",
                        "Compare approvalHandoffVerificationMarker.consumedByNodeProfileVersion with Node v211 packet profile",
                        "Compare managedAuditAdapterBoundaryReceipt.consumedByNodeArchiveVerificationVersion with Node v214 profileVersion",
                        "Require managedAuditAdapterBoundaryReceipt.readyForNodeV215DryRunAdapterCandidate=true before Node v215",
                        "Compare managedAuditProductionAdapterPrerequisiteReceipt.consumedByNodeArchiveVerificationVersion with Node v216 profileVersion",
                        "Require managedAuditProductionAdapterPrerequisiteReceipt.readyForNodeV217ProductionHardeningReadinessGate=true before Node v217",
                        "Compare opsEvidenceServiceQualitySplitReceipt.consumedByNodeQualityPassVersion with Node v218",
                        "Require opsEvidenceServiceQualitySplitReceipt.readyForNodeV219ImplementationPrecheck=true before Node v219",
                        "Compare managedAuditAdapterImplementationGuardReceipt.consumedByNodeDisabledShellProfile with Node v220",
                        "Require managedAuditAdapterImplementationGuardReceipt.readyForNodeV221LocalAdapterCandidateDryRun=true before Node v221",
                        "Compare managedAuditExternalAdapterMigrationGuardReceipt.consumedByNodeVerificationReportProfile with Node v222",
                        "Require managedAuditExternalAdapterMigrationGuardReceipt.readyForNodeV223ExternalAdapterConnectionReadinessReview=true before Node v223",
                        "Compare managedAuditSandboxAdapterApprovalSchemaGuardReceipt.consumedByNodeSandboxPlanProfile with Node v224",
                        "Require managedAuditSandboxAdapterApprovalSchemaGuardReceipt.readyForNodeV225SandboxAdapterDryRunPackage=true before Node v225",
                        "Compare managedAuditSandboxConnectionOperatorHandoffMarker.consumedByNodeOperatorPacketProfile with Node v228",
                        "Require managedAuditSandboxConnectionOperatorHandoffMarker.readyForNodeV229ManualSandboxConnectionPacketVerification=true before Node v229",
                        "Keep UPSTREAM_ACTIONS_ENABLED=false"
                );
        assertThat(rehearsal.releaseApprovalInputs().releaseOperatorSignoffFixtureEndpoint())
                .isEqualTo("/contracts/release-operator-signoff.fixture.json");
        assertThat(rehearsal.releaseApprovalInputs().rollbackApproverEvidenceFixtureEndpoint())
                .isEqualTo("/contracts/rollback-approver-evidence.fixture.json");
        assertThat(rehearsal.releaseApprovalInputs().rollbackApprovalRecordFixtureEndpoint())
                .isEqualTo("/contracts/rollback-approval-record.fixture.json");
        assertThat(rehearsal.releaseApprovalInputs().releaseBundleManifestEndpoint())
                .isEqualTo("/contracts/release-bundle-manifest.sample.json");
        assertThat(rehearsal.releaseApprovalInputs().requiredEvidenceEndpoints())
                .containsExactly(
                        "/contracts/release-operator-signoff.fixture.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/rollback-approval-record.fixture.json",
                        "/contracts/release-bundle-manifest.sample.json",
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/deployment-rollback-evidence.sample.json",
                        "/contracts/production-deployment-runbook-contract.sample.json",
                        "/contracts/production-secret-source-contract.sample.json",
                        "/contracts/rollback-sql-review-gate.sample.json"
                );
        assertThat(rehearsal.liveSignals().pendingReplayApprovals()).isEqualTo(2);
        assertThat(rehearsal.liveSignals().approvedReplayApprovals()).isEqualTo(1);
        assertThat(rehearsal.liveSignals().rejectedReplayApprovals()).isEqualTo(1);
        assertThat(rehearsal.liveSignals().replayBacklog()).isEqualTo(3);
        assertThat(rehearsal.liveSignals().pendingOutboxEvents()).isEqualTo(6);
        assertThat(rehearsal.liveSignals().realReplayAllowedByEvidence()).isFalse();
        assertThat(rehearsal.liveSignals().approvalExecutionDryRun()).isTrue();
        assertThat(rehearsal.liveSignals().evidenceExecutionAllowed()).isFalse();
        assertThat(rehearsal.executionBoundaries().nodeMayConsume()).isTrue();
        assertThat(rehearsal.executionBoundaries().nodeMayCreateApprovalDecision()).isFalse();
        assertThat(rehearsal.executionBoundaries().nodeMayWriteApprovalLedger()).isFalse();
        assertThat(rehearsal.executionBoundaries().nodeMayTriggerDeployment()).isFalse();
        assertThat(rehearsal.executionBoundaries().nodeMayTriggerRollback()).isFalse();
        assertThat(rehearsal.executionBoundaries().nodeMayExecuteRollbackSql()).isFalse();
        assertThat(rehearsal.executionBoundaries().requiresProductionDatabase()).isFalse();
        assertThat(rehearsal.executionBoundaries().requiresProductionSecrets()).isFalse();
        assertThat(rehearsal.executionBoundaries().changesOrderTransactionSemantics()).isFalse();
        assertThat(rehearsal.rehearsalBlockers())
                .contains(
                        "READ_ONLY_RELEASE_APPROVAL_REHEARSAL",
                        "APPROVAL_DECISION_CREATION_DISABLED",
                        "ROLLBACK_SQL_EXECUTION_DISABLED",
                        "REPLAY_APPROVAL_PENDING"
                );
        assertThat(rehearsal.requiredNodeEnvironment())
                .containsExactly("UPSTREAM_PROBES_ENABLED=true", "UPSTREAM_ACTIONS_ENABLED=false");
        assertThat(rehearsal.nextEvidenceActions())
                .containsExactly(
                        "GET /api/v1/ops/evidence",
                        "GET /api/v1/ops/release-approval-rehearsal",
                        "GET /contracts/release-operator-signoff.fixture.json",
                        "GET /contracts/rollback-approver-evidence.fixture.json",
                        "GET /contracts/rollback-approval-record.fixture.json",
                        "Keep UPSTREAM_ACTIONS_ENABLED=false"
                );

        ReleaseApprovalRehearsalResponse headerBackedRehearsal = service.releaseApprovalRehearsal(
                " rehearsal-v67-001 ",
                " release-operator@example.test ",
                " audit-correlation-v67 ",
                " operator-198 ",
                " operator,auditor ",
                " true ",
                " approval-v198-operator-window ",
                " real-read-window-ci-archive-artifact-manifest.v1 ",
                " sha256:node-v200-manifest-digest ",
                " /api/v1/production/real-read-window-ci-archive-artifact-manifest ",
                " 9 ",
                " approval-v198-operator-window ",
                " real-read-window-ci-artifact-upload-dry-run-contract.v1 ",
                " sha256:node-v202-upload-contract-digest ",
                " orderops-real-read-window-evidence-v191-v201 ",
                " c/ ",
                " 30 ",
                " dry-run-contract-only ",
                " three-project-real-read-runtime-smoke-preflight.v1 ",
                " sha256:node-v204-preflight-digest ",
                " runtime-smoke-v205-session-001 ",
                " java-release-approval-rehearsal ",
                " manual-open-window-plan ",
                " managed-audit-persistence-boundary-candidate.v1 ",
                " sha256:node-v208-managed-audit-candidate-digest ",
                " file-or-sqlite-dry-run-candidate ",
                " 30 ",
                " size-and-age-rotation-candidate ",
                " managed-audit-identity-approval-binding-contract.v1 ",
                " sha256:node-v210-approval-binding-digest ",
                " approval-request-v210-001 ",
                " APPROVED_DRY_RUN_ONLY ",
                " approval-record-correlation-v210 "
        );
        assertThat(headerBackedRehearsal.requestContext().requestId()).isEqualTo("rehearsal-v67-001");
        assertThat(headerBackedRehearsal.requestContext().requestIdSource())
                .isEqualTo("X-Rehearsal-Request-Id");
        assertThat(headerBackedRehearsal.requestContext().operatorIdentity())
                .isEqualTo("release-operator@example.test");
        assertThat(headerBackedRehearsal.requestContext().operatorIdentitySource())
                .isEqualTo("X-Operator-Identity");
        assertThat(headerBackedRehearsal.requestContext().auditCorrelationId())
                .isEqualTo("audit-correlation-v67");
        assertThat(headerBackedRehearsal.requestContext().auditCorrelationSource())
                .isEqualTo("X-Audit-Correlation-Id");
        assertThat(headerBackedRehearsal.requestContext().contextWarnings()).isEmpty();
        assertThat(headerBackedRehearsal.operatorWindowHint().operatorId()).isEqualTo("operator-198");
        assertThat(headerBackedRehearsal.operatorWindowHint().operatorIdSource())
                .isEqualTo("x-orderops-operator-id");
        assertThat(headerBackedRehearsal.operatorWindowHint().operatorRoles()).isEqualTo("operator,auditor");
        assertThat(headerBackedRehearsal.operatorWindowHint().operatorRolesSource())
                .isEqualTo("x-orderops-roles");
        assertThat(headerBackedRehearsal.operatorWindowHint().operatorVerifiedClaim()).isEqualTo("true");
        assertThat(headerBackedRehearsal.operatorWindowHint().operatorVerifiedClaimSource())
                .isEqualTo("x-orderops-operator-verified");
        assertThat(headerBackedRehearsal.operatorWindowHint().approvalCorrelationId())
                .isEqualTo("approval-v198-operator-window");
        assertThat(headerBackedRehearsal.operatorWindowHint().approvalCorrelationIdSource())
                .isEqualTo("x-orderops-approval-correlation-id");
        assertThat(headerBackedRehearsal.operatorWindowHint().operatorIdentityEchoed()).isTrue();
        assertThat(headerBackedRehearsal.operatorWindowHint().operatorRolesEchoed()).isTrue();
        assertThat(headerBackedRehearsal.operatorWindowHint().operatorVerifiedClaimEchoed()).isTrue();
        assertThat(headerBackedRehearsal.operatorWindowHint().approvalCorrelationEchoed()).isTrue();
        assertThat(headerBackedRehearsal.operatorWindowHint().operatorWindowContextComplete()).isTrue();
        assertThat(headerBackedRehearsal.operatorWindowHint().productionIdpVerifiedByJava()).isFalse();
        assertThat(headerBackedRehearsal.operatorWindowHint().persistedApprovalRecordByJava()).isFalse();
        assertThat(headerBackedRehearsal.operatorWindowHint().nodeMayTreatAsProductionIdentity()).isFalse();
        assertThat(headerBackedRehearsal.operatorWindowHint().echoWarnings()).isEmpty();
        assertThat(headerBackedRehearsal.ciEvidenceHint().manifestProfileVersion())
                .isEqualTo("real-read-window-ci-archive-artifact-manifest.v1");
        assertThat(headerBackedRehearsal.ciEvidenceHint().manifestProfileVersionSource())
                .isEqualTo("x-orderops-ci-manifest-version");
        assertThat(headerBackedRehearsal.ciEvidenceHint().manifestDigest())
                .isEqualTo("sha256:node-v200-manifest-digest");
        assertThat(headerBackedRehearsal.ciEvidenceHint().manifestDigestSource())
                .isEqualTo("x-orderops-ci-manifest-digest");
        assertThat(headerBackedRehearsal.ciEvidenceHint().manifestEndpoint())
                .isEqualTo("/api/v1/production/real-read-window-ci-archive-artifact-manifest");
        assertThat(headerBackedRehearsal.ciEvidenceHint().manifestEndpointSource())
                .isEqualTo("x-orderops-ci-manifest-endpoint");
        assertThat(headerBackedRehearsal.ciEvidenceHint().artifactRecordCount()).isEqualTo("9");
        assertThat(headerBackedRehearsal.ciEvidenceHint().artifactRecordCountSource())
                .isEqualTo("x-orderops-ci-artifact-record-count");
        assertThat(headerBackedRehearsal.ciEvidenceHint().approvalCorrelationId())
                .isEqualTo("approval-v198-operator-window");
        assertThat(headerBackedRehearsal.ciEvidenceHint().approvalCorrelationIdSource())
                .isEqualTo("x-orderops-ci-approval-correlation-id");
        assertThat(headerBackedRehearsal.ciEvidenceHint().manifestProfileVersionEchoed()).isTrue();
        assertThat(headerBackedRehearsal.ciEvidenceHint().manifestDigestEchoed()).isTrue();
        assertThat(headerBackedRehearsal.ciEvidenceHint().manifestEndpointEchoed()).isTrue();
        assertThat(headerBackedRehearsal.ciEvidenceHint().artifactRecordCountEchoed()).isTrue();
        assertThat(headerBackedRehearsal.ciEvidenceHint().approvalCorrelationEchoed()).isTrue();
        assertThat(headerBackedRehearsal.ciEvidenceHint().ciEvidenceContextComplete()).isTrue();
        assertThat(headerBackedRehearsal.ciEvidenceHint().noLedgerWriteProved()).isTrue();
        assertThat(headerBackedRehearsal.ciEvidenceHint().ciArtifactUploadedByJava()).isFalse();
        assertThat(headerBackedRehearsal.ciEvidenceHint().githubArtifactAccessedByJava()).isFalse();
        assertThat(headerBackedRehearsal.ciEvidenceHint().productionWindowAllowedByJava()).isFalse();
        assertThat(headerBackedRehearsal.ciEvidenceHint().nodeMayTreatAsCiArtifactPublication()).isFalse();
        assertThat(headerBackedRehearsal.ciEvidenceHint().echoWarnings()).isEmpty();
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciUploadContractVersion())
                .isEqualTo("real-read-window-ci-artifact-upload-dry-run-contract.v1");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciUploadContractVersionSource())
                .isEqualTo("x-orderops-ci-upload-contract-version");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciUploadContractDigest())
                .isEqualTo("sha256:node-v202-upload-contract-digest");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciUploadContractDigestSource())
                .isEqualTo("x-orderops-ci-upload-contract-digest");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciArtifactName())
                .isEqualTo("orderops-real-read-window-evidence-v191-v201");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciArtifactNameSource())
                .isEqualTo("x-orderops-ci-artifact-name");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciArtifactRoot()).isEqualTo("c/");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciArtifactRootSource())
                .isEqualTo("x-orderops-ci-artifact-root");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciRetentionDays()).isEqualTo("30");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciRetentionDaysSource())
                .isEqualTo("x-orderops-ci-retention-days");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciUploadMode())
                .isEqualTo("dry-run-contract-only");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciUploadModeSource())
                .isEqualTo("x-orderops-ci-upload-mode");
        assertThat(headerBackedRehearsal.artifactRetentionHint().artifactRetentionContextComplete()).isTrue();
        assertThat(headerBackedRehearsal.artifactRetentionHint().retentionDaysWithinJavaRetention()).isTrue();
        assertThat(headerBackedRehearsal.artifactRetentionHint().javaRetentionFixtureReadOnly()).isTrue();
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciArtifactUploadedByJava()).isFalse();
        assertThat(headerBackedRehearsal.artifactRetentionHint().githubArtifactAccessedByJava()).isFalse();
        assertThat(headerBackedRehearsal.artifactRetentionHint().productionWindowAllowedByJava()).isFalse();
        assertThat(headerBackedRehearsal.artifactRetentionHint().nodeMayTreatAsRetentionAuthorization()).isFalse();
        assertThat(headerBackedRehearsal.artifactRetentionHint().echoWarnings()).isEmpty();
        assertThat(headerBackedRehearsal.liveReadinessHint().sourcePreflightVersion())
                .isEqualTo("three-project-real-read-runtime-smoke-preflight.v1");
        assertThat(headerBackedRehearsal.liveReadinessHint().sourcePreflightVersionSource())
                .isEqualTo("x-orderops-runtime-preflight-version");
        assertThat(headerBackedRehearsal.liveReadinessHint().sourcePreflightDigest())
                .isEqualTo("sha256:node-v204-preflight-digest");
        assertThat(headerBackedRehearsal.liveReadinessHint().sourcePreflightDigestSource())
                .isEqualTo("x-orderops-runtime-preflight-digest");
        assertThat(headerBackedRehearsal.liveReadinessHint().runtimeSmokeSessionId())
                .isEqualTo("runtime-smoke-v205-session-001");
        assertThat(headerBackedRehearsal.liveReadinessHint().runtimeSmokeSessionIdSource())
                .isEqualTo("x-orderops-runtime-smoke-session-id");
        assertThat(headerBackedRehearsal.liveReadinessHint().runtimeReadTargetId())
                .isEqualTo("java-release-approval-rehearsal");
        assertThat(headerBackedRehearsal.liveReadinessHint().runtimeReadTargetIdSource())
                .isEqualTo("x-orderops-runtime-read-target-id");
        assertThat(headerBackedRehearsal.liveReadinessHint().runtimeWindowMode())
                .isEqualTo("manual-open-window-plan");
        assertThat(headerBackedRehearsal.liveReadinessHint().runtimeWindowModeSource())
                .isEqualTo("x-orderops-runtime-window-mode");
        assertThat(headerBackedRehearsal.liveReadinessHint().sourcePreflightVersionEchoed()).isTrue();
        assertThat(headerBackedRehearsal.liveReadinessHint().sourcePreflightDigestEchoed()).isTrue();
        assertThat(headerBackedRehearsal.liveReadinessHint().runtimeSmokeSessionIdEchoed()).isTrue();
        assertThat(headerBackedRehearsal.liveReadinessHint().runtimeReadTargetIdEchoed()).isTrue();
        assertThat(headerBackedRehearsal.liveReadinessHint().runtimeWindowModeEchoed()).isTrue();
        assertThat(headerBackedRehearsal.liveReadinessHint().liveReadinessContextComplete()).isTrue();
        assertThat(headerBackedRehearsal.liveReadinessHint().readyForRuntimeSmokeRead()).isTrue();
        assertThat(headerBackedRehearsal.liveReadinessHint().runtimeSmokeExecutedByJava()).isFalse();
        assertThat(headerBackedRehearsal.liveReadinessHint().javaStartedProcessForNode()).isFalse();
        assertThat(headerBackedRehearsal.liveReadinessHint().nodeMayTreatAsProductionAuthorization()).isFalse();
        assertThat(headerBackedRehearsal.liveReadinessHint().echoWarnings()).isEmpty();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditCandidateVersion())
                .isEqualTo("managed-audit-persistence-boundary-candidate.v1");
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditCandidateVersionSource())
                .isEqualTo("x-orderops-managed-audit-candidate-version");
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditCandidateDigest())
                .isEqualTo("sha256:node-v208-managed-audit-candidate-digest");
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditCandidateDigestSource())
                .isEqualTo("x-orderops-managed-audit-candidate-digest");
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditSinkMode())
                .isEqualTo("file-or-sqlite-dry-run-candidate");
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditSinkModeSource())
                .isEqualTo("x-orderops-managed-audit-sink-mode");
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditRetentionDays())
                .isEqualTo("30");
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditRetentionDaysSource())
                .isEqualTo("x-orderops-managed-audit-retention-days");
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditRotationPolicy())
                .isEqualTo("size-and-age-rotation-candidate");
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditRotationPolicySource())
                .isEqualTo("x-orderops-managed-audit-rotation-policy");
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().candidateVersionEchoed()).isTrue();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().candidateDigestEchoed()).isTrue();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().sinkModeEchoed()).isTrue();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().retentionDaysEchoed()).isTrue();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().rotationPolicyEchoed()).isTrue();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().auditPersistenceHandoffContextComplete())
                .isTrue();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditRetentionWithinJavaRetention())
                .isTrue();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().javaAuditSourceReadOnly()).isTrue();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().javaLedgerWriteAllowed()).isFalse();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().javaManagedAuditWriteAllowed()).isFalse();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().javaExternalAuditSystemAccessed()).isFalse();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().nodeMayUseAsManagedAuditInput()).isTrue();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().nodeMayTreatAsProductionAuditRecord())
                .isFalse();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().echoWarnings()).isEmpty();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalBindingContractVersion())
                .isEqualTo("managed-audit-identity-approval-binding-contract.v1");
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalBindingContractVersionSource())
                .isEqualTo("x-orderops-approval-binding-contract-version");
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalBindingContractDigest())
                .isEqualTo("sha256:node-v210-approval-binding-digest");
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalBindingContractDigestSource())
                .isEqualTo("x-orderops-approval-binding-contract-digest");
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalRequestId())
                .isEqualTo("approval-request-v210-001");
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalRequestIdSource())
                .isEqualTo("x-orderops-approval-request-id");
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalDecisionState())
                .isEqualTo("APPROVED_DRY_RUN_ONLY");
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalDecisionStateSource())
                .isEqualTo("x-orderops-approval-decision-state");
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalRecordCorrelationId())
                .isEqualTo("approval-record-correlation-v210");
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalRecordCorrelationIdSource())
                .isEqualTo("x-orderops-approval-record-correlation-id");
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalBindingContractVersionEchoed())
                .isTrue();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalBindingContractDigestEchoed())
                .isTrue();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalRequestIdEchoed()).isTrue();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalDecisionStateEchoed()).isTrue();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalRecordCorrelationEchoed()).isTrue();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalRecordHandoffContextComplete())
                .isTrue();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalRecordFixtureReadOnly()).isTrue();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().javaApprovalDecisionCreated()).isFalse();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().javaApprovalLedgerWritten()).isFalse();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().javaApprovalRecordPersisted()).isFalse();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().javaApprovalRecordAuthenticated()).isFalse();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().productionApprovalStoreRequired()).isFalse();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().nodeMayUseAsAuditApprovalInput()).isTrue();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().nodeMayTreatAsProductionApprovalRecord())
                .isFalse();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().echoWarnings()).isEmpty();
        assertThat(headerBackedRehearsal.approvalHandoffVerificationMarker().nodeV211HandoffAccepted()).isTrue();
        assertThat(headerBackedRehearsal.approvalHandoffVerificationMarker().nodeV211NoWriteBoundaryAccepted()).isTrue();
        assertThat(headerBackedRehearsal.approvalHandoffVerificationMarker().readyForNodeV213RestoreDrillPlan())
                .isTrue();
        assertThat(headerBackedRehearsal.approvalHandoffVerificationMarker().markerWarnings()).isEmpty();
        assertThat(headerBackedRehearsal.approvalHandoffVerificationMarker().nodeV211ProductionAuditRecordAllowed())
                .isFalse();
        assertThat(headerBackedRehearsal.approvalHandoffVerificationMarker().nodeV211RealApprovalDecisionCreated())
                .isFalse();
        assertThat(headerBackedRehearsal.approvalHandoffVerificationMarker().nodeV211RealApprovalLedgerWritten())
                .isFalse();
        assertThat(headerBackedRehearsal.approvalHandoffVerificationMarker().javaApprovalRecordPersisted())
                .isFalse();
        assertThat(headerBackedRehearsal.approvalHandoffVerificationMarker().nodeMayTreatAsProductionAuditRecord())
                .isFalse();
        assertThat(headerBackedRehearsal.approvalHandoffVerificationMarker().consumedHandoffFieldPaths())
                .contains(
                        "approvalRecordHandoffHint.approvalRecordCorrelationId",
                        "approvalRecordHandoffHint.approvalTimestampPlaceholder",
                        "verificationHint.warningDigest"
                );
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt()
                .readyForNodeV215DryRunAdapterCandidate()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().receiptWarnings()).isEmpty();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayConsume()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayWriteLocalDryRunFiles())
                .isTrue();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayConnectManagedAudit())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayCreateApprovalDecision())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayWriteApprovalLedger())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayPersistApprovalRecord())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayExecuteSql()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayTriggerDeployment())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayTriggerRollback())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayExecuteRestore())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().javaApprovalDecisionCreated())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().javaApprovalLedgerWritten())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().javaApprovalRecordPersisted())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().javaManagedAuditWriteExecuted())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().readyForProductionAudit()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().readyForProductionWindow()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .readyForNodeV217ProductionHardeningReadinessGate()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt().receiptWarnings())
                .isEmpty();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .operatorIdentityPrerequisiteDocumented()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .approvalDecisionSourcePrerequisiteDocumented()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .ledgerHandoffPrerequisiteDocumented()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .retentionOwnerPrerequisiteDocumented()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .failureHandlingPrerequisiteDocumented()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .rollbackReviewPrerequisiteDocumented()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .javaCreatesApprovalDecision()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .javaWritesApprovalLedger()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .javaWritesManagedAuditStore()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaExecutesSql())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .javaTriggersDeployment()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .javaTriggersRollback()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .javaExecutesRestore()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeV217MayConnectManagedAudit()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeV217MayWriteApprovalLedger()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeV217MayExecuteSql()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeV217MayTriggerDeployment()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeV217MayTriggerRollback()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeV217MayExecuteRestore()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt().readyForProductionAudit())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt().readyForProductionWindow())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .readyForProductionOperations()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt()
                .readyForNodeV219ImplementationPrecheck()).isTrue();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().receiptWarnings()).isEmpty();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().nodeV219MayConsume()).isTrue();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().firstSafeSplitApplied()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().broadServiceSplitDeferred()).isTrue();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().apiShapeChanged()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().approvalDecisionCreated()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().approvalLedgerWritten()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().approvalRecordPersisted()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().managedAuditStoreWritten()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().sqlExecuted()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().deploymentTriggered()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().rollbackTriggered()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().restoreExecuted()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().readyForProductionAudit()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().readyForProductionWindow())
                .isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt()
                .readyForNodeV221LocalAdapterCandidateDryRun()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().guardWarnings())
                .isEmpty();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt()
                .consumedByNodeDisabledShellProfile())
                .isEqualTo("managed-audit-adapter-disabled-shell.v1");
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt()
                .nodeV220SelectedAdapterDisabled()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt()
                .nodeV220LocalDryRunOnlyDeclared()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220AppendWritten())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt()
                .nodeV220ExternalManagedAuditAccessed()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt()
                .nodeV220LocalDryRunWritePerformed()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().javaApprovalDecisionCreated())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().javaApprovalLedgerWritten())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().javaApprovalRecordPersisted())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().javaManagedAuditStoreWritten())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().javaSqlExecuted())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().javaDeploymentTriggered())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().javaRollbackTriggered())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().javaRestoreExecuted())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().readyForProductionAudit())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().readyForProductionWindow())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().guardDigest())
                .startsWith("sha256:");
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .readyForNodeV223ExternalAdapterConnectionReadinessReview()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt().guardWarnings())
                .isEmpty();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .consumedByNodeVerificationReportProfile())
                .isEqualTo("managed-audit-local-adapter-candidate-verification-report.v1");
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeV222ReadOnlyReport()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeV222SourceEndpointRerunPerformed()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeV222AdditionalLocalDryRunWritePerformed()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeV222ConnectsManagedAudit()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .ownerApprovalRequiredBeforeConnection()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .schemaMigrationReviewRequired()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .credentialReviewRequired()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .credentialValueReadByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .externalManagedAuditConnectionOpened()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .externalManagedAuditSchemaMigrated()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .javaApprovalLedgerWritten()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .javaManagedAuditStoreWritten()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .javaSqlExecuted()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .readyForProductionAudit()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .readyForProductionWindow()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt().guardDigest())
                .startsWith("sha256:");
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .readyForNodeV225SandboxAdapterDryRunPackage()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().guardWarnings())
                .isEmpty();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .consumedByNodeSandboxPlanProfile())
                .isEqualTo("managed-audit-sandbox-adapter-dry-run-plan.v1");
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().readOnlyPlan()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().connectsManagedAudit()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .ownerApprovalBoundary().ownerApprovalArtifactRequired()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .ownerApprovalBoundary().ownerApprovalArtifactProvidedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .schemaRehearsalBoundary().schemaMigrationExecutionAllowed()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .credentialBoundary().sandboxCredentialHandleRequired()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .credentialBoundary().credentialValueReadByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .executionBoundary().externalManagedAuditConnectionOpened()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .executionBoundary().javaManagedAuditStoreWritten()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .executionBoundary().javaSqlExecuted()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .qualityGateBoundary().builderClassName())
                .isEqualTo("ReleaseApprovalManagedAuditSandboxAdapterApprovalSchemaGuardReceiptBuilder");
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .qualityGateBoundary().builderOrHelperSplitApplied()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .qualityGateBoundary().longBooleanConstructorAvoided()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .readyForProductionAudit()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .readyForProductionWindow()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().guardDigest())
                .startsWith("sha256:");
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .readyForNodeV229ManualSandboxConnectionPacketVerification()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().markerWarnings())
                .isEmpty();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .consumedByNodeOperatorPacketProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-operator-packet.v1");
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .sandboxConnectionWindowBoundary().manualSandboxConnectionWindowRequired()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .sandboxConnectionWindowBoundary().manualSandboxConnectionWindowOpenedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .sandboxConnectionWindowBoundary().connectionExecutionAllowed()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .operatorPacketBoundary().ownerApprovalArtifactIdFieldRecognizedByJava()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .operatorPacketBoundary().schemaRehearsalIdFieldRecognizedByJava()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .credentialBoundary().credentialHandleNameRecognizedByJava()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .credentialBoundary().credentialValueReadByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .rollbackPathBoundary().rollbackExecutionAllowedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .javaExecutionBoundary().externalManagedAuditConnectionOpenedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .javaExecutionBoundary().sqlExecutedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().markerDigest())
                .startsWith("sha256:");
        assertThat(headerBackedRehearsal.failureTaxonomy().upstreamReadiness()).isEqualTo("READY");
        assertThat(headerBackedRehearsal.failureTaxonomy().authContextReadiness()).isEqualTo("READY");
        assertThat(headerBackedRehearsal.failureTaxonomy().auditCorrelationReadiness()).isEqualTo("READY");
        assertThat(headerBackedRehearsal.failureTaxonomy().authContextComplete()).isTrue();
        assertThat(headerBackedRehearsal.failureTaxonomy().auditCorrelationPresent()).isTrue();
        assertThat(headerBackedRehearsal.failureTaxonomy().failureCategories())
                .containsExactly("READ_ONLY_EXECUTION_BLOCKED");
        assertThat(headerBackedRehearsal.failureTaxonomy().taxonomyWarnings())
                .containsExactly("REHEARSAL_REMAINS_READ_ONLY");
        assertThat(headerBackedRehearsal.verificationHint().hintVersion())
                .isEqualTo("java-release-approval-rehearsal-verification-hint.v1");
        assertThat(headerBackedRehearsal.verificationHint().responseSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v17");
        assertThat(headerBackedRehearsal.verificationHint().warningDigest()).startsWith("sha256:");
        assertThat(headerBackedRehearsal.verificationHint().warningDigest())
                .isNotEqualTo(rehearsal.verificationHint().warningDigest());
        ReleaseApprovalRehearsalResponse repeatedHeaderBackedRehearsal = service.releaseApprovalRehearsal(
                "rehearsal-v67-001",
                "release-operator@example.test",
                "audit-correlation-v67",
                "operator-198",
                "operator,auditor",
                "true",
                "approval-v198-operator-window",
                "real-read-window-ci-archive-artifact-manifest.v1",
                "sha256:node-v200-manifest-digest",
                "/api/v1/production/real-read-window-ci-archive-artifact-manifest",
                "9",
                "approval-v198-operator-window",
                "real-read-window-ci-artifact-upload-dry-run-contract.v1",
                "sha256:node-v202-upload-contract-digest",
                "orderops-real-read-window-evidence-v191-v201",
                "c/",
                "30",
                "dry-run-contract-only",
                "three-project-real-read-runtime-smoke-preflight.v1",
                "sha256:node-v204-preflight-digest",
                "runtime-smoke-v205-session-001",
                "java-release-approval-rehearsal",
                "manual-open-window-plan",
                "managed-audit-persistence-boundary-candidate.v1",
                "sha256:node-v208-managed-audit-candidate-digest",
                "file-or-sqlite-dry-run-candidate",
                "30",
                "size-and-age-rotation-candidate",
                "managed-audit-identity-approval-binding-contract.v1",
                "sha256:node-v210-approval-binding-digest",
                "approval-request-v210-001",
                "APPROVED_DRY_RUN_ONLY",
                "approval-record-correlation-v210"
        );
        assertThat(repeatedHeaderBackedRehearsal.verificationHint().warningDigest())
                .isEqualTo(headerBackedRehearsal.verificationHint().warningDigest());
        assertThat(repeatedHeaderBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().guardDigest())
                .isEqualTo(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().guardDigest());
        assertThat(repeatedHeaderBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt().guardDigest())
                .isEqualTo(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt().guardDigest());
        assertThat(repeatedHeaderBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().guardDigest())
                .isEqualTo(
                        headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().guardDigest()
                );
        assertThat(repeatedHeaderBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().markerDigest())
                .isEqualTo(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().markerDigest());
        assertThat(headerBackedRehearsal.verificationHint().noLedgerWriteProved()).isTrue();
        assertThat(headerBackedRehearsal.verificationHint().nodeMayTreatAsProductionAuthorization()).isFalse();
        assertThat(headerBackedRehearsal.requestContext().operatorAuthenticatedByJava()).isFalse();
        assertThat(headerBackedRehearsal.requestContext().persistedByJava()).isFalse();
        assertThat(headerBackedRehearsal.requestContext().approvalLedgerWritten()).isFalse();
        assertThat(headerBackedRehearsal.executionAllowed()).isFalse();
        assertThat(headerBackedRehearsal.executionBoundaries().nodeMayWriteApprovalLedger()).isFalse();
    }
}
